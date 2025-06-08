package com.example.hospital_appointment.application.service.interfaces;

import com.example.hospital_appointment.domain.Enums.Gender;
import com.example.hospital_appointment.domain.model.Patient;

import java.time.LocalDate;
import java.util.List;

public interface IPatientService {
    Patient create(Patient patient);
    List<Patient> getAllPatients();
    Patient getPatientById(Long id);
    String updateName(Long id, String name);
    String updateBirthday(Long id, LocalDate birthday);
    String updateGender(Long id, Gender gender);
    String updateAddress(Long id, String address);
    String Ban(Long patient_id);
    String unBan(Long patient_id);
}
