package com.example.hospital_appointment.infrastructure.repository.jpa;

import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaSpecialization extends JpaRepository<Specialization, Long> {
    List<Specialization> findByHospitalId(Long hospitalId);
    boolean existsByNameAndHospital(String name, Hospital hospital);
    @Query("SELECT COUNT(s) FROM Specialization s WHERE s.hospital.id = :hospitalId")
    int countByHospitalId(@Param("hospitalId") Long hospitalId);
}
