package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.application.service.interfaces.IAuthService;
import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.domain.model.User;
import com.example.hospital_appointment.domain.repository.IDoctorRepo;
import com.example.hospital_appointment.domain.repository.IPatientRepo;
import com.example.hospital_appointment.domain.repository.IUserRepo;
import com.example.hospital_appointment.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.Optional;

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
    private JwtUtil jwtUtil;

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
        return doctorRepository.findByemail(email);
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
    public String changePassword(String oldPassword, String newPassword, Long patient_id) {
        Optional<Patient> optionalPatient = patientRepository.findById(patient_id);
        if (optionalPatient.isEmpty()) {
            return "Patient not found";
        }

        Patient patient = optionalPatient.get();
        User user = patient.getUser();

        // Kiểm tra mật khẩu cũ có đúng không
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return "Old password is incorrect";
        }

        // Mã hóa mật khẩu mới
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        patient.setUser(user);

        // Lưu lại bệnh nhân đã đổi mật khẩu
        patientRepository.save(patient);

        return "Password changed successfully";
    }
}
