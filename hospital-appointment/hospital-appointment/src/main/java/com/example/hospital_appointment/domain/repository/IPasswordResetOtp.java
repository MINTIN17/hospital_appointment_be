package com.example.hospital_appointment.domain.repository;

import com.example.hospital_appointment.domain.model.PasswordResetOtp;

import java.util.Optional;

public interface IPasswordResetOtp {
    PasswordResetOtp save(PasswordResetOtp passwordResetOtp);
    Optional<PasswordResetOtp> findByEmailAndOtp(String email, String otp);
}
