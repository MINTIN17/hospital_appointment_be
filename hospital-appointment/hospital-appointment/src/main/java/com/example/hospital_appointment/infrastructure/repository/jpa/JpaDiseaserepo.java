package com.example.hospital_appointment.infrastructure.repository.jpa;

import com.example.hospital_appointment.domain.model.Disease;
import com.example.hospital_appointment.domain.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaDiseaserepo extends JpaRepository<Disease, Long> {
    Optional<Disease> findByName(String name);
}
