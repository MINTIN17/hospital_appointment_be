package com.example.hospital_appointment.domain.repository;

import com.example.hospital_appointment.domain.model.Patient;

import java.util.Optional;

public interface IPatientRepo {
    Patient save(Patient patient);
    Optional<Patient> findByUserEmail(String email);
}
