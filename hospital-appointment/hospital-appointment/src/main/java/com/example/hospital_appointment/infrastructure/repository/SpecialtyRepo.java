package com.example.hospital_appointment.infrastructure.repository;

import com.example.hospital_appointment.domain.model.Specialty;
import com.example.hospital_appointment.domain.repository.ISpecialtyRepo;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaSpecialtyRepo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpecialtyRepo implements ISpecialtyRepo {
    private final JpaSpecialtyRepo jpaSpecialtyRepo;

    public SpecialtyRepo(JpaSpecialtyRepo jpaSpecialtyRepo) {
        this.jpaSpecialtyRepo = jpaSpecialtyRepo;
    }

    public void saveAll(List<Specialty> specialties){
        jpaSpecialtyRepo.saveAll(specialties);
    }

    public Long count(){
        return jpaSpecialtyRepo.count();
    }
}
