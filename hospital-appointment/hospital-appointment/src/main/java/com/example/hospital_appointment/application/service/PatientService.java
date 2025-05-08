package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.application.service.interfaces.IPatientService;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.domain.repository.IPatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService implements IPatientService {
    @Autowired
    private IPatientRepo patientRepository;

    @Override
    public Patient create(Patient patient) {
        patient = patientRepository.save(patient);
        return patient;
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public String Ban(Long patient_id) {
        return patientRepository.Ban(patient_id);
    }

    @Override
    public String unBan(Long patient_id) {
        return patientRepository.unBan(patient_id);
    }
}
