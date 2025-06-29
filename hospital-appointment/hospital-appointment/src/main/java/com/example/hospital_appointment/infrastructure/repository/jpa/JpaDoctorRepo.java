package com.example.hospital_appointment.infrastructure.repository.jpa;

import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

public interface JpaDoctorRepo extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialization_Hospital_Id(Long hospitalId);
    @Query("SELECT d FROM Doctor d WHERE d.user.id = :userId")
    Doctor findByUserId(@Param("userId") Long userId);
    Optional<Doctor> findByUserEmail(String email);
    @Query("SELECT COUNT(d) FROM Doctor d WHERE d.specialization.hospital.id = :hospitalId")
    int countByHospitalId(@Param("hospitalId") Long hospitalId);
}
