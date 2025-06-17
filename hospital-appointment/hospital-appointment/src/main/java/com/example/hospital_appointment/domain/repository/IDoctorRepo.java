package com.example.hospital_appointment.domain.repository;

import com.example.hospital_appointment.domain.model.Doctor;

import java.util.List;
import java.util.Optional;

public interface IDoctorRepo {
    Doctor save(Doctor doctor);
    Optional<Doctor> findById(Long id);
    List<Doctor> findByUser_Hospital_Id(Long hospitalId);
    Optional<Doctor> findByEmail(String email);
    Integer countByHospitalId(Long hospital_id);
    String Ban(Long id);
    String unBan(Long id);
}
