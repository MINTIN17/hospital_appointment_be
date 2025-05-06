package com.example.hospital_appointment.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SpecializationResponse {
    private Long id;
    private String name;
}
