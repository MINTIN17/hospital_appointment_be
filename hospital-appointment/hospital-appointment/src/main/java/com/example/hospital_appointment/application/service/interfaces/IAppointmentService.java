package com.example.hospital_appointment.application.service.interfaces;

import com.example.hospital_appointment.api.dto.AppointmentRequest;
import com.example.hospital_appointment.api.dto.AppointmentResponse;
import com.example.hospital_appointment.domain.Enums.AppointmentStatus;
import com.example.hospital_appointment.domain.model.Appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IAppointmentService {
    String BookAppointment(AppointmentRequest appointmentRequest);
    List<AppointmentResponse> getAllAppointment();
    List<AppointmentResponse> getDoctorAppointment(Long doctor_id);
    List<AppointmentResponse> getPatientAppointment(Long patient_id);
    List<AppointmentResponse> getCurrentAppointment(Long doctor_id);
    String confirmAppointment(Long id);
    String cancelAppointment(Long id);
    String completeAppointment(Long id);
    Map<AppointmentStatus, Long> countAppointmentsByStatus(LocalDate startDate, LocalDate endDate);
    Map<String, Long> countAppointmentsByHospital(LocalDate startDate, LocalDate endDate);
    List<Map<String, Object>> countAppointmentsByDoctor(LocalDate startDate, LocalDate endDate);
    double calculateRevisitRate(int daysWindow);
}
