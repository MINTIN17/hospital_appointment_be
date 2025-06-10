package com.example.hospital_appointment.infrastructure.config;

import com.example.hospital_appointment.domain.Enums.Gender;
import com.example.hospital_appointment.domain.Enums.Role;
import com.example.hospital_appointment.domain.model.*;
import com.example.hospital_appointment.domain.repository.*;
import com.example.hospital_appointment.infrastructure.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final IUserRepo userRepository;
    private final IHospitalRepo hospitalRepository;
    private final ISpecializationRepo specializationRepository;
    private final IDoctorRepo doctorRepository;
    private final IPatientRepo patientRepository;

    @Override
    public void run(String... args) {
        if (hospitalRepository.count() > 0) return; // Đã seed rồi thì bỏ qua

        List<String> specializationNames = List.of(
                "Nội tổng quát", "Ngoại tổng quát", "Tim mạch", "Thần kinh", "Tiêu hóa",
                "Da liễu", "Nhi", "Sản khoa", "Tai mũi họng", "Ung bướu", "Chấn thương chỉnh hình"
        );

        int userCounter = 1;
        for (int i = 1; i <= 10; i++) {
            Hospital hospital = Hospital.builder()
                    .name("Hospital " + i)
                    .address("Số " + (100 + i) + " Đường A")
                    .avatarUrl("https://example.com/hospital" + i + ".png")
                    .phone("09" + (int)(Math.random() * 1_000_000_000))
                    .build();
            hospitalRepository.save(hospital);

            List<String> specs = new ArrayList<>(specializationNames);
            Collections.shuffle(specs);
            for (int j = 0; j < 5; j++) {
                String specName = specs.get(j);
                Specialization specialization = Specialization.builder()
                        .name(specName)
                        .hospital(hospital)
                        .build();
                specializationRepository.add(specialization);

                for (int k = 0; k < 3; k++) {
                    User doctorUser = User.builder()
                            .name("Dr. " + (char)('A' + i) + k)
                            .email("doctor" + i + j + k + "@example.com")
                            .password("hashed_password")
                            .phone("09" + (int)(Math.random() * 1_000_000_000))
                            .gender(Math.random() > 0.5 ? Gender.MALE : Gender.FEMALE)
                            .dateOfBirth(LocalDate.of(1980 + new Random().nextInt(20), 1, 1))
                            .avatarUrl("https://example.com/doctor" + i + j + k + ".png")
                            .address("Số " + (100 + k) + " Bác sĩ")
                            .role(Role.DOCTOR)
                            .enabled(true)
                            .deleted(false)
                            .build();
                    userRepository.save(doctorUser);

                    Doctor doctor = Doctor.builder()
                            .user(doctorUser)
                            .about("Experienced in " + specName)
                            .specialization(specialization)
                            .yearsOfExperience(5 + new Random().nextInt(25))
                            .build();
                    doctorRepository.save(doctor);
                }
            }
        }

        for (int i = 1; i <= 10; i++) {
            User patientUser = User.builder()
                    .name("Patient " + i)
                    .email("patient" + i + "@example.com")
                    .password("hashed_password")
                    .phone("09" + (int)(Math.random() * 1_000_000_000))
                    .gender(Math.random() > 0.5 ? Gender.MALE : Gender.FEMALE)
                    .dateOfBirth(LocalDate.of(1990 + new Random().nextInt(15), 1, 1))
                    .avatarUrl("https://example.com/patient" + i + ".png")
                    .address("Số " + (100 + i) + " Đường bệnh nhân")
                    .role(Role.PATIENT)
                    .enabled(true)
                    .deleted(false)
                    .build();
            userRepository.save(patientUser);

            Patient patient = Patient.builder()
                    .user(patientUser)
                    .build();
            patientRepository.save(patient);
        }
    }
}

