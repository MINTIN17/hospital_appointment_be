package com.example.hospital_appointment.infrastructure.repository.jpa;

import com.example.hospital_appointment.domain.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface JpaPatientRepo extends JpaRepository<Patient, Long>{
    Optional<Patient> findByUserEmail(String email);
}
