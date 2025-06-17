package com.example.hospital_appointment.infrastructure.repository;

import com.example.hospital_appointment.domain.model.Disease;
import com.example.hospital_appointment.domain.repository.IDiseaseRepo;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaDiseaserepo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DiseaseRepo implements IDiseaseRepo {
    private final JpaDiseaserepo jpaDiseaserepo;

    public DiseaseRepo(JpaDiseaserepo jpaDiseaserepo) {
        this.jpaDiseaserepo = jpaDiseaserepo;
    }

    @Override
    public void saveAll(List<Disease> diseases) {
        jpaDiseaserepo.saveAll(diseases);
    }

    @Override
    public Long count() {
        return jpaDiseaserepo.count();
    }

    @Override
    public Optional<Disease> findByName(String name) {
        return jpaDiseaserepo.findByName(name);
    }

}
