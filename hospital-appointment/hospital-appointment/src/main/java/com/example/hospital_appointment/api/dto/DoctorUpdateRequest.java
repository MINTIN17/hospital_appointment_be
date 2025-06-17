package com.example.hospital_appointment.api.dto;

import com.example.hospital_appointment.domain.Enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorUpdateRequest {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String avatarUrl;
    private String address;
    private String about;
    private Long specialization_id;
    private int yearsOfExperience;
}
