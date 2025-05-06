package com.example.hospital_appointment.infrastructure.repository.jpa;

import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaSpecialization extends JpaRepository<Specialization, Long> {
    List<Specialization> findByHospitalId(Long hospitalId);
    boolean existsByNameAndHospital(String name, Hospital hospital);
}
