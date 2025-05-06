package com.example.hospital_appointment.infrastructure.repository.jpa;

import com.example.hospital_appointment.domain.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaHospitalRepo extends JpaRepository<Hospital, Long> {
}
