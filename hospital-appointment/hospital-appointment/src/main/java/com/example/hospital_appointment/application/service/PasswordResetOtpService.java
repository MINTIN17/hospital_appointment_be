package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.application.service.interfaces.IPasswordResetOtpService;
import com.example.hospital_appointment.domain.model.EmailSender;
import com.example.hospital_appointment.domain.model.PasswordResetOtp;
import com.example.hospital_appointment.domain.model.User;
import com.example.hospital_appointment.domain.repository.IPasswordResetOtp;
import com.example.hospital_appointment.infrastructure.repository.PasswordResetOtpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetOtpService implements IPasswordResetOtpService {
    @Autowired
    private IPasswordResetOtp otpRepo;

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private UserService userService;

    @Override
    public String sendOtp(String email) {
        User user = userService.findByEmail(email);
        if (user== null) {
            return "Invalid email";
        }
        String otp = String.format("%06d", new Random().nextInt(999999));

        PasswordResetOtp resetOtp = new PasswordResetOtp();
        resetOtp.setEmail(email);
        resetOtp.setOtp(otp);
        resetOtp.setExpirationTime(LocalDateTime.now().plusMinutes(2));

        otpRepo.save(resetOtp);

        // Gửi email
        String content = "Mã xác nhận của bạn là: " + otp + ". Mã này sẽ hết hạn sau 2 phút.";
        emailSender.send(email, "Mã xác nhận đặt lại mật khẩu", content);
        return "Send otp";
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        return otpRepo.findByEmailAndOtp(email, otp)
                .filter(record -> record.getExpirationTime().isAfter(LocalDateTime.now()))
                .isPresent();
    }
}
