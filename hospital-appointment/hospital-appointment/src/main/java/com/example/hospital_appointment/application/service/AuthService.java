package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.application.service.interfaces.IAuthService;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.domain.model.User;
import com.example.hospital_appointment.domain.repository.IPatientRepo;
import com.example.hospital_appointment.domain.repository.IUserRepo;
import com.example.hospital_appointment.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    @Autowired
    private IPatientRepo patientRepository;

    @Autowired
    private IUserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public Patient register(Patient patient) {
        User user = patient.getUser();
        System.out.println(user.getPassword());
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        System.out.println(hashedPassword);
        user.setPassword(hashedPassword);
        patient.setUser(user);

        return patientRepository.save(patient);
    }

    public Optional<Patient> findByUserEmail(String email) {
        return patientRepository.findByUserEmail(email);
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
        Optional<Patient> optionalPatient = findByUserEmail(email);
        if (optionalPatient.isEmpty()) {
            throw new RuntimeException("Email không tồn tại");
        }

        User user = optionalPatient.get().getUser();
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Sai mật khẩu");
        }

        return jwtUtil.generateToken(email, user.getRole());
    }
}
