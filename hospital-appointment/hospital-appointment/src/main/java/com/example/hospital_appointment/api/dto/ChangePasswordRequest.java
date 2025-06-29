package com.example.hospital_appointment.api.dto;

import com.example.hospital_appointment.domain.Enums.Role;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    private Long user_id;
    private String old_password;
    private String new_password;
    private Role role;
}
