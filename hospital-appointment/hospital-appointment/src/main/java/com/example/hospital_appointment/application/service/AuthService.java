package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.api.dto.ChangePasswordRequest;
import com.example.hospital_appointment.api.dto.ResetPasswordRequest;
import com.example.hospital_appointment.application.service.interfaces.IAuthService;
import com.example.hospital_appointment.domain.Enums.Role;
import com.example.hospital_appointment.domain.model.*;
import com.example.hospital_appointment.domain.repository.IDoctorRepo;
import com.example.hospital_appointment.domain.repository.IPasswordResetOtp;
import com.example.hospital_appointment.domain.repository.IPatientRepo;
import com.example.hospital_appointment.domain.repository.IUserRepo;
import com.example.hospital_appointment.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    @Autowired
    private IPatientRepo patientRepository;

    @Autowired
    private IDoctorRepo doctorRepository;

    @Autowired
    private IUserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IPasswordResetOtp passwordResetOtp;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private EmailSender emailSender;

    public Patient register(Patient patient) {
        User user = patient.getUser();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        patient.setUser(user);

        return patientRepository.save(patient);
    }

    public Optional<Patient> findByPatientEmail(String email) {
        return patientRepository.findByUserEmail(email);
    }

    public Optional<Doctor> findByDoctorEmail(String email) {
        return doctorRepository.findByEmail(email);
    }


    public Optional<User> CheckAdmin(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (passwordEncoder.matches(password, user.getPassword()) && "ADMIN".equalsIgnoreCase(String.valueOf(user.getRole()))) {
                return Optional.of(user);
            }

        }
        return Optional.empty();
    }


    public String login(String email, String rawPassword) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new RuntimeException("Email không tồn tại");
        }

        if (!passwordEncoder.matches(rawPassword, user.get().getPassword())) {
            throw new RuntimeException("Sai mật khẩu");
        }

        return jwtUtil.generateToken(email, user.get().getRole());
    }

    @Override
    public String changePassword(ChangePasswordRequest request) {
        User user = null;

        if (request.getRole() == Role.PATIENT) {
            Optional<Patient> optionalPatient = patientRepository.findById(request.getUser_id());
            if (optionalPatient.isEmpty()) {
                return "Patient not found";
            }
            Patient patient = optionalPatient.get();
            user = patient.getUser();
            if (!passwordEncoder.matches(request.getOld_password(), user.getPassword())) {
                return "Old password is incorrect";
            }
            String encodedNewPassword = passwordEncoder.encode(request.getNew_password());
            user.setPassword(encodedNewPassword);
            patient.setUser(user);
            patientRepository.save(patient);
        } else if (request.getRole() == Role.DOCTOR) {
            Optional<Doctor> optionalDoctor = doctorRepository.findById(request.getUser_id());
            if (optionalDoctor.isEmpty()) {
                return "Doctor not found";
            }
            Doctor doctor = optionalDoctor.get();
            user = doctor.getUser();

            if (!passwordEncoder.matches(request.getOld_password(), user.getPassword())) {
                return "Old password is incorrect";
            }

            String encodedNewPassword = passwordEncoder.encode(request.getNew_password());
            user.setPassword(encodedNewPassword);
            doctor.setUser(user);
            doctorRepository.save(doctor);
        } else {
            return "Invalid role";
        }

        return "Password changed successfully";
    }

    @Override
    public String forgotPassword(ResetPasswordRequest request) {
        User user = null;

        if (request.getRole() == Role.PATIENT) {
            Optional<Patient> optionalPatient = patientRepository.findByUserEmail(request.getEmail());
            if (optionalPatient.isEmpty()) {
                return "Patient not found";
            }
            Patient patient = optionalPatient.get();
            user = patient.getUser();
            String encodedNewPassword = passwordEncoder.encode(request.getPassword());
            user.setPassword(encodedNewPassword);
            patient.setUser(user);
            patientRepository.save(patient);
        } else if (request.getRole() == Role.DOCTOR) {
            Optional<Doctor> optionalDoctor = doctorRepository.findByEmail(request.getEmail());
            if (optionalDoctor.isEmpty()) {
                return "Doctor not found";
            }
            Doctor doctor = optionalDoctor.get();
            user = doctor.getUser();

            String encodedNewPassword = passwordEncoder.encode(request.getPassword());
            user.setPassword(encodedNewPassword);
            doctor.setUser(user);
            doctorRepository.save(doctor);
        } else {
            return "Invalid role";
        }

        return "Reset password successfully";
    }


}
