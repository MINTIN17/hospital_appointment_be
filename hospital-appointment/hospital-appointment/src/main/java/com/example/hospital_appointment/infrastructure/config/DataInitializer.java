package com.example.hospital_appointment.infrastructure.config;

import com.example.hospital_appointment.domain.Enums.Role;
import com.example.hospital_appointment.domain.model.User;
import com.example.hospital_appointment.domain.repository.IUserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(IUserRepo userRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@example.com";

            if (userRepo.findByEmail(adminEmail).isEmpty()) {
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("1234")); // Mã hóa mật khẩu
                admin.setRole(Role.ADMIN);
                admin.setEnabled(true);
                admin.setDeleted(false);
                admin.setCreatedAt(LocalDateTime.now());
                admin.setUpdatedAt(LocalDateTime.now());

                userRepo.CreateAdmin(admin);
                System.out.println("Admin user created.");
            } else {
                System.out.println("ℹAdmin user already exists.");
            }
        };
    }
}

