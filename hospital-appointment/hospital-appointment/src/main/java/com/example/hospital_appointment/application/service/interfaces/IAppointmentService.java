package com.example.hospital_appointment.application.service.interfaces;

import com.example.hospital_appointment.api.dto.AppointmentRequest;
import com.example.hospital_appointment.domain.model.Appointment;

public interface IAppointmentService {
    String BookAppointment(AppointmentRequest appointmentRequest);
    String confirmAppointment(Long id);
    String cancelAppointment(Long id);

}
