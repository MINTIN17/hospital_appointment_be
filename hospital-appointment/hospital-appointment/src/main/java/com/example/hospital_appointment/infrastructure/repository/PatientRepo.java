package com.example.hospital_appointment.infrastructure.repository;

import com.example.hospital_appointment.domain.model.User;
import com.example.hospital_appointment.domain.repository.IPatientRepo;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.domain.repository.IUserRepo;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaPatientRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepo implements IPatientRepo {

    private final JpaPatientRepo jpaPatientRepo;
    private  final IUserRepo userRepo;

    public PatientRepo(JpaPatientRepo jpaPatientRepo, UserRepo userRepo) {
        this.jpaPatientRepo = jpaPatientRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Patient save(Patient patient) {
        return jpaPatientRepo.save(patient);
    }

    @Override
    public Optional<Patient> findByUserEmail(String email) {
        return jpaPatientRepo.findByUserEmail(email);
    }

    @Override
    public List<Patient> findAll() {
        return jpaPatientRepo.findAll();
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return jpaPatientRepo.findById(id);
    }

    @Override
    @Transactional
    public String Ban(Long id) {
        Patient patient = jpaPatientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        User user = patient.getUser();
        user.setEnabled(false);
        userRepo.save(user);

        return "ban success";
    }

    @Override
    @Transactional
    public String unBan(Long id) {
        Patient patient = jpaPatientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        User user = patient.getUser();
        user.setEnabled(true);
        userRepo.save(user);

        return "unban success";
    }

}
