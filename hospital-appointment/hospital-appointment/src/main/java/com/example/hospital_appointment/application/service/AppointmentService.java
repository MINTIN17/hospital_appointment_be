package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.api.dto.AppointmentRequest;
import com.example.hospital_appointment.api.dto.AppointmentResponse;
import com.example.hospital_appointment.api.dto.ScheduleResponse;
import com.example.hospital_appointment.application.exception.ResourceNotFoundException;
import com.example.hospital_appointment.application.service.interfaces.IAppointmentService;
import com.example.hospital_appointment.domain.Enums.AppointmentStatus;
import com.example.hospital_appointment.domain.model.Appointment;
import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.domain.repository.IAppointmentRepo;
import com.example.hospital_appointment.domain.repository.IDoctorRepo;
import com.example.hospital_appointment.domain.repository.IPatientRepo;
import com.example.hospital_appointment.infrastructure.mapper.AppointmentMapper;
import com.example.hospital_appointment.infrastructure.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService implements IAppointmentService {
    @Autowired
    private IAppointmentRepo appointmentRepo;
    @Autowired
    private IDoctorRepo doctorRepo;
    @Autowired
    private IPatientRepo patientRepo;

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
    public String confirmAppointment(Long id) {
        return appointmentRepo.confirmAppointment(id);
    }

    @Override
    public String cancelAppointment(Long id) {
        return appointmentRepo.cancelAppointment(id);
    }

    @Override
    public String completeAppointment(Long id) {
        return appointmentRepo.completeAppointment(id);
    }
}
