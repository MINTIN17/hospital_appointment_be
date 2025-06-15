package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.api.dto.AppointmentRequest;
import com.example.hospital_appointment.api.dto.AppointmentResponse;
import com.example.hospital_appointment.api.dto.ScheduleResponse;
import com.example.hospital_appointment.application.exception.ResourceNotFoundException;
import com.example.hospital_appointment.application.service.interfaces.IAppointmentService;
import com.example.hospital_appointment.domain.Enums.AppointmentStatus;
import com.example.hospital_appointment.domain.model.*;
import com.example.hospital_appointment.domain.repository.IAppointmentRepo;
import com.example.hospital_appointment.domain.repository.IDoctorRepo;
import com.example.hospital_appointment.domain.repository.IPatientRepo;
import com.example.hospital_appointment.infrastructure.mapper.AppointmentMapper;
import com.example.hospital_appointment.infrastructure.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AppointmentService implements IAppointmentService {
    @Autowired
    private IAppointmentRepo appointmentRepo;
    @Autowired
    private IDoctorRepo doctorRepo;
    @Autowired
    private IPatientRepo patientRepo;
    @Autowired
    private EmailSender emailSender;

    @Override
    public String BookAppointment(AppointmentRequest appointmentRequest) {
        Patient patient = patientRepo.findById(appointmentRequest.getPatient_id())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + appointmentRequest.getPatient_id()));

        Doctor doctor = doctorRepo.findById(appointmentRequest.getDoctor_id())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + appointmentRequest.getDoctor_id()));

        Optional<Appointment> appointmentOptional = appointmentRepo.findExist(appointmentRequest.getAppointmentDate(),
                                    appointmentRequest.getStartTime(), appointmentRequest.getEndTime(),
                                    appointmentRequest.getDoctor_id());
        if (appointmentOptional.isPresent()) {
            Appointment existingAppointment = appointmentOptional.get();
            if (existingAppointment.getStatus() == AppointmentStatus.PENDING ||
                    existingAppointment.getStatus() == AppointmentStatus.CONFIRMED) {

                return ("Appointment exist");
            }
        }

        Appointment appointment = AppointmentMapper.toAppointment(appointmentRequest, patient, doctor);
        return appointmentRepo.save(appointment);
    }

    @Override
    public List<AppointmentResponse> getAllAppointment() {
        return appointmentRepo.getAllAppointment()
                .stream()
                .map(AppointmentMapper::toAppointmentResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> getDoctorAppointment(Long doctor_id) {
        return appointmentRepo.getDoctorAppointment(doctor_id)
                .stream()
                .map(AppointmentMapper::toAppointmentResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> getPatientAppointment(Long patient_id) {
        return appointmentRepo.getPatientAppointment(patient_id)
                .stream()
                .map(AppointmentMapper::toAppointmentResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> getCurrentAppointment(Long doctor_id) {
        return appointmentRepo.getCurrentAppointment(doctor_id)
                .stream()
                .map(AppointmentMapper::toAppointmentResponse)
                .toList();
    }

    @Override
    public String confirmAppointment(Long id) {
        Optional<Appointment> appointmentOpt = appointmentRepo.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            Doctor doctor = appointment.getDoctor();
            Specialization specialization = doctor.getSpecialization();
            Hospital hospital = specialization.getHospital();
            Patient patient = appointment.getPatient();

            String content = String.format(
                    "Bệnh nhân: %s\n" +
                            "Bạn đã được xác nhận lịch khám với bác sĩ %s\n" +
                            "Tại bệnh viện: %s\n" +
                            "Địa chỉ: %s\n" +
                            "Thời gian: %s đến %s\n" +
                            "Ngày: %s",
                    patient.getUser().getName(),
                    doctor.getUser().getName(),
                    hospital.getName(),
                    hospital.getAddress(),
                    appointment.getStartTime().toString(),
                    appointment.getEndTime().toString(),
                    appointment.getAppointmentDate().toString()
            );

            emailSender.send(
                    patient.getUser().getEmail(),
                    "Xác nhận lịch hẹn",
                    content
            );
        }
        return appointmentRepo.confirmAppointment(id);
    }

    @Override
    public String cancelAppointment(Long id) {
        Optional<Appointment> appointmentOpt = appointmentRepo.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            Doctor doctor = appointment.getDoctor();
            Specialization specialization = doctor.getSpecialization();
            Hospital hospital = specialization.getHospital();
            Patient patient = appointment.getPatient();

            String content = String.format(
                    "Bệnh nhân: %s\n" +
                            "Bác sĩ %s đã hủy lịch hẹn với bạn\n" +
                            "Tại bệnh viện: %s\n" +
                            "Địa chỉ: %s\n" +
                            "Thời gian: %s đến %s\n" +
                            "Ngày: %s",
                    patient.getUser().getName(),
                    doctor.getUser().getName(),
                    hospital.getName(),
                    hospital.getAddress(),
                    appointment.getStartTime().toString(),
                    appointment.getEndTime().toString(),
                    appointment.getAppointmentDate().toString()
            );

            emailSender.send(
                    patient.getUser().getEmail(),
                    "Hủy lịch hẹn",
                    content
            );
        }
        return appointmentRepo.cancelAppointment(id);
    }

    @Override
    public String completeAppointment(Long id) {
        return appointmentRepo.completeAppointment(id);
    }

    @Override
    public Map<AppointmentStatus, Long> countAppointmentsByStatus(LocalDate startDate, LocalDate endDate) {
        List<AppointmentStatus> filterStatuses = Arrays.asList(
                AppointmentStatus.COMPLETED,
                AppointmentStatus.CANCELLED
        );

        List<Object[]> results = appointmentRepo.countAppointmentsByStatus(filterStatuses, startDate, endDate);
        Map<AppointmentStatus, Long> statusCounts = new EnumMap<>(AppointmentStatus.class);

        for (Object[] row : results) {
            AppointmentStatus status = (AppointmentStatus) row[0];
            Long count = (Long) row[1];
            statusCounts.put(status, count);
        }

        // Đảm bảo luôn có đủ key
        for (AppointmentStatus status : filterStatuses) {
            statusCounts.putIfAbsent(status, 0L);
        }

        return statusCounts;
    }

    @Override
    public Map<String, Long> countAppointmentsByHospital(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = appointmentRepo.countAppointmentsByHospital(AppointmentStatus.COMPLETED, startDate, endDate);
        Map<String, Long> hospitalCounts = new HashMap<>();

        for (Object[] row : results) {
            String hospitalName = (String) row[0];
            Long count = (Long) row[1];
            hospitalCounts.put(hospitalName, count);
        }

        return hospitalCounts;
    }

    @Override
    public List<Map<String, Object>> countAppointmentsByDoctor(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = appointmentRepo.countAppointmentsByDoctor(
                List.of(AppointmentStatus.COMPLETED, AppointmentStatus.CANCELLED), startDate, endDate
        );

        Map<String, Map<String, Object>> summary = new HashMap<>();
        for (Object[] row : results) {
            String doctorName = (String) row[1];
            AppointmentStatus status = (AppointmentStatus) row[2];
            Long count = (Long) row[3];

            summary.putIfAbsent(doctorName, new HashMap<>());
            Map<String, Object> doctorStats = summary.get(doctorName);
            doctorStats.put("doctorName", doctorName);
            doctorStats.put(status.name().toLowerCase(), count);
        }

        // Bổ sung nếu thiếu key
        for (Map<String, Object> stats : summary.values()) {
            stats.putIfAbsent("completed", 0L);
            stats.putIfAbsent("cancelled", 0L);
        }

        return new ArrayList<>(summary.values());
    }

    @Override
    public double calculateRevisitRate(int daysWindow) {
        List<Object[]> results = appointmentRepo.findCompletedAppointments(AppointmentStatus.COMPLETED);

        Map<Long, List<LocalDate>> patientAppointments = new HashMap<>();
        for (Object[] row : results) {
            Long patientId = (Long) row[0];
            LocalDate date = (LocalDate) row[1];

            patientAppointments.computeIfAbsent(patientId, k -> new ArrayList<>()).add(date);
        }

        int totalPatients = patientAppointments.size();
        int revisitCount = 0;

        for (List<LocalDate> dates : patientAppointments.values()) {
            if (dates.size() < 2) continue;
            dates.sort(Comparator.naturalOrder());

            boolean hasRevisit = false;
            for (int i = 1; i < dates.size(); i++) {
                long diff = ChronoUnit.DAYS.between(dates.get(i - 1), dates.get(i));
                if (diff <= daysWindow) {
                    hasRevisit = true;
                    break;
                }
            }

            if (hasRevisit) revisitCount++;
        }

        return totalPatients == 0 ? 0.0 : (revisitCount * 100.0 / totalPatients);
    }


}
