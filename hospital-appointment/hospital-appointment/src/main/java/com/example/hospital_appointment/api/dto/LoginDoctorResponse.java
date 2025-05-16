package com.example.hospital_appointment.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDoctorResponse
{
    private String token;
    private DoctorResponse doctorResponse;

    public LoginDoctorResponse(String token, DoctorResponse doctorResponse) {
        this.token = token;
        this.doctorResponse = doctorResponse;
    }
}
