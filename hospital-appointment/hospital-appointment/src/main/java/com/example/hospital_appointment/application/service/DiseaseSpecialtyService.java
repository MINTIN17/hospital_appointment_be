package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.application.service.interfaces.IDiseaseSpecialtyService;
import com.example.hospital_appointment.domain.model.Disease;
import com.example.hospital_appointment.domain.model.Specialty;
import com.example.hospital_appointment.domain.repository.IDiseaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiseaseSpecialtyService implements IDiseaseSpecialtyService {
    @Autowired
    private IDiseaseRepo diseaseRepo;

    @Override
    public String getSpecialty(String diseaseName) {
        Optional<Disease> optDisease = diseaseRepo.findByName(diseaseName);
        if (optDisease.isPresent()) {
            Disease disease = optDisease.get();
            Specialty specialty = disease.getSpecialty();
            return specialty != null ? specialty.getName() : "Chưa phân loại khoa";
        }
        return "Không tìm thấy bệnh";
    }

}
