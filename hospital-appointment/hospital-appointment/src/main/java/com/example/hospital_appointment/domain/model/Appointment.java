package com.example.hospital_appointment.domain.model;

import com.example.hospital_appointment.domain.Enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "appointment")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne  // Nhiều cuộc hẹn có thể thuộc về một bác sĩ
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    private AppointmentStatus status;
    private LocalDateTime appointmentDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}

