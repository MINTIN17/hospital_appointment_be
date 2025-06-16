package com.example.hospital_appointment.api.dto;

import com.example.hospital_appointment.domain.Enums.Role;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String password;
    private Role role;
}
