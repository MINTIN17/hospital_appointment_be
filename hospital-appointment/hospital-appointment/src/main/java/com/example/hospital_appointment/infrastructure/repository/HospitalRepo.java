package com.example.hospital_appointment.infrastructure.repository;

import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.domain.model.User;
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

    public long count() {
        return jpaHospitalRepo.count();
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

    @Override
    public String Ban(Long id) {
        Hospital hospital = jpaHospitalRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        hospital.setEnabled(false);
        jpaHospitalRepo.save(hospital);
        System.out.println(hospital.isEnabled());
        return "ban success";
    }

    @Override
    public String unBan(Long id) {
        Hospital hospital = jpaHospitalRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        hospital.setEnabled(true);
        jpaHospitalRepo.save(hospital);

        return "unban success";
    }


}
