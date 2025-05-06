package com.example.hospital_appointment.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse
{
    private String token;
    private PatientDTO patient;

    public LoginResponse(String token, PatientDTO patient) {
        this.token = token;
        this.patient = patient;
    }
}
