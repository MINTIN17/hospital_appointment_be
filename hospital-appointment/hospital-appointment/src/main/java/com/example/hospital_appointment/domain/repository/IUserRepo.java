package com.example.hospital_appointment.domain.repository;

import com.example.hospital_appointment.domain.model.User;

import java.util.Optional;

public interface IUserRepo {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    User CreateAdmin(User user);
    User save(User user);
}
