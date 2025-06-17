package com.example.hospital_appointment.infrastructure.repository;

import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.domain.model.User;
import com.example.hospital_appointment.domain.repository.IDoctorRepo;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaDoctorRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DoctorRepo implements IDoctorRepo {

    private final JpaDoctorRepo jpaDoctorRepo;
    private final UserRepo userRepo;

    public DoctorRepo(JpaDoctorRepo jpaDoctorRepo, UserRepo userRepo) {
        this.jpaDoctorRepo = jpaDoctorRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Doctor save(Doctor doctor) {
        return jpaDoctorRepo.save(doctor);
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        return jpaDoctorRepo.findById(id);
    }

    @Override
    public List<Doctor> findByUser_Hospital_Id(Long hospitalId) {
        return jpaDoctorRepo.findBySpecialization_Hospital_Id(hospitalId);
    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        return jpaDoctorRepo.findByUserEmail(email);
    }

    @Override
    public Integer countByHospitalId(Long hospital_id) {
        return jpaDoctorRepo.countByHospitalId(hospital_id);
    }

    @Override
    @Transactional
    public String Ban(Long id) {
        Doctor doctor = jpaDoctorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        User user = doctor.getUser();
        user.setEnabled(false);
        userRepo.save(user);

        return "ban success";
    }

    @Override
    @Transactional
    public String unBan(Long id) {
        Doctor doctor = jpaDoctorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        User user = doctor.getUser();
        user.setEnabled(true);
        userRepo.save(user);

        return "unban success";
    }
}
