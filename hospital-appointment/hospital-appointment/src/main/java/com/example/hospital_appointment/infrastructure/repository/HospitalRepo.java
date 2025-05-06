package com.example.hospital_appointment.infrastructure.repository;

import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.repository.IHospitalRepo;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaHospitalRepo;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaPatientRepo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HospitalRepo implements IHospitalRepo {
    private final JpaHospitalRepo jpaHospitalRepo;

    public HospitalRepo(JpaHospitalRepo jpaHospitalRepo) {
        this.jpaHospitalRepo = jpaHospitalRepo;
    }

    @Override
    public Hospital save(Hospital hospital) {
        return jpaHospitalRepo.save(hospital);
    }

    @Override
    public Hospital getHospitalById(Long id) {
        return jpaHospitalRepo.getById(id);
    }

    @Override
    public List<Hospital> findAll() {
        return jpaHospitalRepo.findAll();
    }


}
