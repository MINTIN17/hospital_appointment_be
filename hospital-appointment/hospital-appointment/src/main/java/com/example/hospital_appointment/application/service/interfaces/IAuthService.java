package com.example.hospital_appointment.application.service.interfaces;

import com.example.hospital_appointment.api.dto.ChangePasswordRequest;
import com.example.hospital_appointment.api.dto.ResetPasswordRequest;
import com.example.hospital_appointment.domain.Enums.Role;
import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.domain.model.User;

import java.util.Objects;
import java.util.Optional;

public interface IAuthService {
    Patient register(Patient patient);
    Optional<Patient> findByPatientEmail(String email);
    Optional<Doctor> findByDoctorEmail(String email);
    Optional<User> CheckAdmin(String email, String password);
    String login(String username, String password);
    String changePassword(ChangePasswordRequest request);
    String forgotPassword(ResetPasswordRequest request);
}
