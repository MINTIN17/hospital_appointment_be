package com.example.hospital_appointment.infrastructure.mapper;

import com.example.hospital_appointment.api.dto.AppointmentRequest;
import com.example.hospital_appointment.api.dto.AppointmentResponse;
import com.example.hospital_appointment.api.dto.DoctorRequest;
import com.example.hospital_appointment.application.service.interfaces.IPatientService;
import com.example.hospital_appointment.domain.Enums.AppointmentStatus;
import com.example.hospital_appointment.domain.Enums.Role;
import com.example.hospital_appointment.domain.model.*;

public class AppointmentMapper {
    public static Appointment toAppointment(AppointmentRequest appointmentRequest, Patient patient, Doctor doctor) {
        return Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .status(AppointmentStatus.PENDING)
                .appointmentDate(appointmentRequest.getAppointmentDate())
                .startTime(appointmentRequest.getStartTime())
                .endTime(appointmentRequest.getEndTime())
                .description(appointmentRequest.getDescription())
                .build();
    }

    public static AppointmentResponse toAppointmentResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .patient_id(appointment.getPatient().getId())
                .status(appointment.getStatus())
                .appointmentDate(appointment.getAppointmentDate())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .description(appointment.getDescription())
                .build();
    }
}
