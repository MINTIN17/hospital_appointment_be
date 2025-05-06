package com.example.hospital_appointment.api.dto;

import com.example.hospital_appointment.domain.Enums.Gender;
import com.example.hospital_appointment.domain.Enums.Role;
import com.example.hospital_appointment.domain.model.Specialization;
import com.example.hospital_appointment.domain.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class DoctorResponse
{
    private Long id;
    private String about;
    private String name;
    private String email;
    private String phone;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String avatarUrl;
    private String address;
    private String specialization_name;
    private String hospital_name;
    private Role role;
    private int yearsOfExperience;
}
