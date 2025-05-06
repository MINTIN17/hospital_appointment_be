package com.example.hospital_appointment.domain.model;

import com.example.hospital_appointment.domain.Enums.Gender;
import com.example.hospital_appointment.domain.Enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @JsonIgnore
    private String password;
    private String phone;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String avatarUrl;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled = true;
    private boolean deleted = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
