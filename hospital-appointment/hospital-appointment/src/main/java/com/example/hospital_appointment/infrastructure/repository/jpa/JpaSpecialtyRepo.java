package com.example.hospital_appointment.infrastructure.repository.jpa;

import com.example.hospital_appointment.domain.model.Appointment;
import com.example.hospital_appointment.domain.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaSpecialtyRepo extends JpaRepository<Specialty, Long> {
//    List<Specialty> saveAll();
}
