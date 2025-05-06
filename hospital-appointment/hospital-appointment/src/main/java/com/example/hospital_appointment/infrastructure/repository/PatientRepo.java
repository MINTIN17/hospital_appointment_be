package com.example.hospital_appointment.infrastructure.repository;

import com.example.hospital_appointment.domain.repository.IPatientRepo;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaPatientRepo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PatientRepo implements IPatientRepo {

    private final JpaPatientRepo jpaPatientRepo;

    public PatientRepo(JpaPatientRepo jpaPatientRepo) {
        this.jpaPatientRepo = jpaPatientRepo;
    }

    @Override
    public Patient save(Patient patient) {
        return jpaPatientRepo.save(patient);
    }

    @Override
    public Optional<Patient> findByUserEmail(String email) {
        return jpaPatientRepo.findByUserEmail(email);
    }

}
