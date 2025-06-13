package com.example.hospital_appointment.application.service.interfaces;

import com.example.hospital_appointment.api.dto.AppointmentRequest;
import com.example.hospital_appointment.api.dto.AppointmentResponse;
import com.example.hospital_appointment.domain.model.Appointment;

import java.util.List;

public interface IAppointmentService {
    String BookAppointment(AppointmentRequest appointmentRequest);
    List<AppointmentResponse> getAllAppointment();
    List<AppointmentResponse> getDoctorAppointment(Long doctor_id);
    List<AppointmentResponse> getPatientAppointment(Long patient_id);
    List<AppointmentResponse> getCurrentAppointment(Long doctor_id);
    String confirmAppointment(Long id);
    String cancelAppointment(Long id);
    String completeAppointment(Long id);

}
