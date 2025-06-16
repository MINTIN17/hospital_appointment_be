package com.example.hospital_appointment.infrastructure.repository.jpa;

import com.example.hospital_appointment.domain.model.PasswordResetOtp;
import com.example.hospital_appointment.domain.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPasswordResetOtp extends JpaRepository<PasswordResetOtp, Long> {
    Optional<PasswordResetOtp> findByEmailAndOtp(String email, String otp);
}
