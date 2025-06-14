package com.example.hospital_appointment.infrastructure.repository;

import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Specialization;
import com.example.hospital_appointment.domain.repository.ISpecializationRepo;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaSpecialization;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpecializationRepo implements ISpecializationRepo {

    private final JpaSpecialization jpaSpecialization;

    public SpecializationRepo(JpaSpecialization jpaSpecialization) {
        this.jpaSpecialization = jpaSpecialization;
    }

    @Override
    public Specialization add(Specialization specialization) {
        return jpaSpecialization.save(specialization);
    }

    @Override
    public List<Specialization> findByHospital(Long hospital_id) {
        return jpaSpecialization.findByHospitalId(hospital_id);
    }

    @Override
    public Boolean existsByNameAndHospital(String name, Hospital hospital) {
        return jpaSpecialization.existsByNameAndHospital(name, hospital);
    }

    @Override
    public Specialization findById(Long id) {
        return jpaSpecialization.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialization not found"));
    }

    @Override
    public Integer countByHospitalId(Long id) {
        return jpaSpecialization.countByHospitalId(id);
    }

}
