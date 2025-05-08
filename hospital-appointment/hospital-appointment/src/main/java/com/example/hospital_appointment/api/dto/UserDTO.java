package com.example.hospital_appointment.api.dto;

import com.example.hospital_appointment.domain.Enums.Gender;
import com.example.hospital_appointment.domain.model.Patient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String email;
    private String phone;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String avatarUrl;
    private String address;
    private boolean enabled;
    // constructors, getters, setters
}


