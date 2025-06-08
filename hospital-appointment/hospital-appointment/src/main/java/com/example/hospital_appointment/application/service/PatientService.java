package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.application.service.interfaces.IPatientService;
import com.example.hospital_appointment.domain.Enums.Gender;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.domain.model.User;
import com.example.hospital_appointment.domain.repository.IPatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).get();
    }

    @Override
    public String updateBirthday(Long id, LocalDate birthday) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân với ID: " + id));

        User user = patient.getUser();
        user.setDateOfBirth(birthday);

        patientRepository.save(patient);

        return "update date of birth success";
    }

    @Override
    public String updateGender(Long id, Gender gender) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân với ID: " + id));

        User user = patient.getUser();
        user.setGender(gender);

        patientRepository.save(patient);

        return "update gender success";
    }

    @Override
    public String updateAddress(Long id, String address) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân với ID: " + id));

        User user = patient.getUser();
        user.setAddress(address);

        patientRepository.save(patient);

        return "update address success";
    }

    @Override
    public String updateName(Long id, String name) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh nhân với ID: " + id));

        User user = patient.getUser();
        user.setName(name);

        patientRepository.save(patient);

        return "update name success";
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
