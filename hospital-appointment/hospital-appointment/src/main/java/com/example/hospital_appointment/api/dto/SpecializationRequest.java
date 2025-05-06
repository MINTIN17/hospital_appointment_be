package com.example.hospital_appointment.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecializationRequest {
    private String name;
    private Long hospital_id;
}
