package com.example.hospital_appointment.domain.repository;

import com.example.hospital_appointment.domain.model.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientRepo {
    Patient save(Patient patient);
    Optional<Patient> findByUserEmail(String email);
    List<Patient> findAll();
    Optional<Patient> findById(Long id);
    String updateName(Long patient_id, String newName);
    String Ban(Long id);
    String unBan(Long id);
}
