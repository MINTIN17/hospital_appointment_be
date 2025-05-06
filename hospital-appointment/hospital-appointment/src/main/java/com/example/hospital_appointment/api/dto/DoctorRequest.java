package com.example.hospital_appointment.api.dto;

import com.example.hospital_appointment.domain.model.Specialization;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRequest {
    private RegisterRequest registerRequest;
    private String about;
    private Long specialization_id;
    private int yearsOfExperience;
}
