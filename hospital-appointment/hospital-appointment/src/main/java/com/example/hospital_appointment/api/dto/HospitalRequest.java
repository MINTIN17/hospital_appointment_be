package com.example.hospital_appointment.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalRequest {
    private String avatarUrl;
    private String name;
    private String address;
    private String phone;
}
