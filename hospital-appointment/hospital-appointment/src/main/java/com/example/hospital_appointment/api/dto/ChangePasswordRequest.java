package com.example.hospital_appointment.api.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private Long patient_id;
    private String old_password;
    private String new_password;
}
