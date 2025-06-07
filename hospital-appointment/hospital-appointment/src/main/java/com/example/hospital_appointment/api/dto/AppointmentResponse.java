package com.example.hospital_appointment.api.dto;

import com.example.hospital_appointment.domain.Enums.AppointmentStatus;
import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.model.Patient;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
public class AppointmentResponse {
    private Long id;

    private Long patient_id;
    private Long doctor_id;
    private String patient_name;
    private String doctor_name;
    private AppointmentStatus status;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
}
