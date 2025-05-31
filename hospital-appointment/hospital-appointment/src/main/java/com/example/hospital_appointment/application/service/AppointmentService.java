package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.api.dto.AppointmentRequest;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
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
}
