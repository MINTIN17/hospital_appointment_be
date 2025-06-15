package com.example.hospital_appointment.infrastructure.repository;

import com.example.hospital_appointment.domain.Enums.AppointmentStatus;
import com.example.hospital_appointment.domain.model.Appointment;
import com.example.hospital_appointment.domain.repository.IAppointmentRepo;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaAppointment;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentRepo implements IAppointmentRepo {
    private final JpaAppointment jpaAppointment;

    public AppointmentRepo(JpaAppointment jpaAppointment) {
        this.jpaAppointment = jpaAppointment;
    }

    public String save(Appointment appointment) {
        Appointment appointment1 = jpaAppointment.save(appointment);
        return "Success";
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return jpaAppointment.findById(id);
    }

    @Override
    public Boolean CheckAppointment(Appointment appointment) {
        AppointmentStatus status = appointment.getStatus();
        return status == AppointmentStatus.PENDING || status == AppointmentStatus.CONFIRMED;
    }

    @Override
    public Optional<Appointment> findExist(LocalDate appointmentDate, LocalTime startTime, LocalTime endTime, Long doctor_id) {
        return jpaAppointment.findByAppointmentDateAndStartTimeAndEndTimeAndDoctor_Id(appointmentDate, startTime,
                                         endTime,
                                         doctor_id);
    }

    @Override
    public List<Appointment> getAllAppointment() {
        return jpaAppointment.findAll();
    }

    @Override
    public List<Appointment> getDoctorAppointment(Long doctor_id) {
        return jpaAppointment.findByDoctor_Id(doctor_id);
    }

    @Override
    public List<Appointment> getPatientAppointment(Long patient_id) {
        return jpaAppointment.findByPatient_Id(patient_id);
    }

    @Override
    public List<Appointment> getCurrentAppointment(Long doctor_id) {
        return jpaAppointment.findByDoctorIdAndStatusIn(
                doctor_id,
                List.of(AppointmentStatus.CONFIRMED, AppointmentStatus.COMPLETED)
        );
    }

    @Override
    public String confirmAppointment(Long id) {
        Appointment appointment = jpaAppointment.findById(id)
                .orElseThrow(() -> new RuntimeException("appointment not found"));
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        jpaAppointment.save(appointment);
        return "confirm appointment";
    }

    @Override
    public String cancelAppointment(Long id) {
        Appointment appointment = jpaAppointment.findById(id)
                .orElseThrow(() -> new RuntimeException("appointment not found"));
        appointment.setStatus(AppointmentStatus.CANCELLED);
        jpaAppointment.save(appointment);
        return "cancelled appointment";
    }

    @Override
    public String completeAppointment(Long id) {
        Appointment appointment = jpaAppointment.findById(id)
                .orElseThrow(() -> new RuntimeException("appointment not found"));
        appointment.setStatus(AppointmentStatus.COMPLETED);
        jpaAppointment.save(appointment);
        return "complete appointment";
    }

    @Override
    public List<Appointment> findByDoctorIdAndAppointmentDateInAndStatusIn(Long doctorId, List<LocalDate> next7Days, List<AppointmentStatus> appointmentStatus) {
        return jpaAppointment.findByDoctorIdAndAppointmentDateInAndStatusIn(doctorId, next7Days, appointmentStatus);
    }

    @Override
    public List<Object[]> countAppointmentsByStatus(List<AppointmentStatus> statuses, LocalDate startDate, LocalDate endDate) {
        return jpaAppointment.countAppointmentsByStatuses(statuses, startDate, endDate);
    }

    @Override
    public List<Object[]> countAppointmentsByHospital(AppointmentStatus status, LocalDate startDate, LocalDate endDate) {
        return jpaAppointment.countAppointmentsByHospital(status, startDate, endDate);
    }

    @Override
    public List<Object[]> countAppointmentsByDoctor(List<AppointmentStatus> statuses, LocalDate startDate, LocalDate endDate) {
        return jpaAppointment.countAppointmentsByDoctor(statuses, startDate, endDate);
    }

    @Override
    public List<Object[]> findCompletedAppointments(AppointmentStatus status) {
        return jpaAppointment.findCompletedAppointments(status);
    }


}
