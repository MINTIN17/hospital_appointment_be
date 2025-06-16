package com.example.hospital_appointment.application.service.interfaces;

public interface IPasswordResetOtpService {
    String sendOtp(String email);
    boolean verifyOtp(String email, String otp);
}
