package com.example.hospital_appointment.api.dto;

import lombok.Data;

@Data
public class HospitalUpdateRequest {
    private Long id;

    private String avatarUrl;
    private String name;
    private String address;
    private String phone;
}
