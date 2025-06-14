package com.example.hospital_appointment.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HospitalResponse {
    private Long id;

    private String avatarUrl;
    private String name;
    private String address;
    private String phone;
    private Integer doctorCount;
    private Integer specializationCount;
}
