package com.example.hospital_appointment.infrastructure.repository;

import com.example.hospital_appointment.domain.model.PasswordResetOtp;
import com.example.hospital_appointment.domain.repository.IPasswordResetOtp;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaPasswordResetOtp;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PasswordResetOtpRepo implements IPasswordResetOtp {
    private final JpaPasswordResetOtp jpaPasswordResetOtp;

    public PasswordResetOtpRepo(JpaPasswordResetOtp jpaPasswordResetOtp) {
        this.jpaPasswordResetOtp = jpaPasswordResetOtp;
    }


    @Override
    public PasswordResetOtp save(PasswordResetOtp passwordResetOtp) {
        return jpaPasswordResetOtp.save(passwordResetOtp);
    }

    @Override
    public Optional<PasswordResetOtp> findByEmailAndOtp(String email, String otp) {
        return jpaPasswordResetOtp.findByEmailAndOtp(email, otp);
    }
}
