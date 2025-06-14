package com.example.hospital_appointment.infrastructure.repository;

import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.repository.IDoctorRepo;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaDoctorRepo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DoctorRepo implements IDoctorRepo {

    private final JpaDoctorRepo jpaDoctorRepo;

    public DoctorRepo(JpaDoctorRepo jpaDoctorRepo) {
        this.jpaDoctorRepo = jpaDoctorRepo;
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
    public Optional<Doctor> findByemail(String email) {
        return jpaDoctorRepo.findByUserEmail(email);
    }

    @Override
    public Integer countByHospitalId(Long hospital_id) {
        return jpaDoctorRepo.countByHospitalId(hospital_id);
    }
}
