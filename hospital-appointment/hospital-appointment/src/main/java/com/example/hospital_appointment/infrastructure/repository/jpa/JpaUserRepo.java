package com.example.hospital_appointment.infrastructure.repository.jpa;

import com.example.hospital_appointment.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

