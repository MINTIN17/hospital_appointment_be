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
    private final ISpecialtyRepo specialtyRepository;
    private final IDiseaseRepo diseaseRepository;

    @Override
    public void run(String... args) {
        if (specialtyRepository.count() == 0 && diseaseRepository.count() == 0) {

            // Chuyên khoa
            Specialty hosinh = new Specialty("Hô hấp");
            Specialty noitiet = new Specialty("Nội tiết");
            Specialty diung = new Specialty("Dị ứng – Miễn dịch");
            Specialty timmach = new Specialty("Tim mạch");
            Specialty tieudung = new Specialty("Tiêu hóa");
            Specialty phukhoa = new Specialty("Phụ khoa");
            Specialty tamthan = new Specialty("Tâm thần – Thần kinh");
            Specialty huyethoc = new Specialty("Huyết học – Dinh dưỡng");
            Specialty coxuongkhop = new Specialty("Cơ – Xương – Khớp");
            Specialty taimuithong = new Specialty("Tai – Mũi – Họng");
            Specialty ganthan = new Specialty("Gan – Mật – Tiết niệu");
            Specialty khac = new Specialty("Khác");
            Specialty dalieu = new Specialty("Da liễu");
            Specialty mat = new Specialty("Mắt");
            Specialty nhiemtrung = new Specialty("Nhiễm trùng");

            specialtyRepository.saveAll(List.of(hosinh, noitiet, diung, timmach, tieudung, phukhoa,
                    tamthan, huyethoc, coxuongkhop, taimuithong, ganthan,
                    khac, dalieu, mat, nhiemtrung));

            diseaseRepository.saveAll(List.of(
                    new Disease("COVID-19", hosinh),
                    new Disease("Cúm", hosinh),
                    new Disease("Cường giáp", noitiet),
                    new Disease("Dị ứng", diung),
                    new Disease("Dị ứng thực phẩm", diung),
                    new Disease("Huyết áp cao", timmach),
                    new Disease("Hội chứng ruột kích thích", tieudung),
                    new Disease("Không xác định", khac),
                    new Disease("Lao phổi", hosinh),
                    new Disease("Lạc nội mạc tử cung", phukhoa),
                    new Disease("Ngộ độc thực phẩm", tieudung),
                    new Disease("Nhiễm khuẩn đường ruột", tieudung),
                    new Disease("Nhồi máu cơ tim", timmach),
                    new Disease("Rối loạn lo âu", tamthan),
                    new Disease("Suy giáp", noitiet),
                    new Disease("Sốt xuất huyết", nhiemtrung),
                    new Disease("Thiếu máu", huyethoc),
                    new Disease("Thiếu vitamin B12", huyethoc),
                    new Disease("Thoát vị đĩa đệm", coxuongkhop),
                    new Disease("Tiểu đường", noitiet),
                    new Disease("Trầm cảm", tamthan),
                    new Disease("Viêm amidan", taimuithong),
                    new Disease("Viêm da dị ứng", dalieu),
                    new Disease("Viêm gan B", ganthan),
                    new Disease("Viêm khớp dạng thấp", coxuongkhop),
                    new Disease("Viêm kết mạc", mat),
                    new Disease("Viêm loét dạ dày", tieudung),
                    new Disease("Viêm phổi", hosinh),
                    new Disease("Viêm xoang", taimuithong),
                    new Disease("Viêm đường tiết niệu", ganthan),
                    new Disease("Đau nửa đầu", tamthan)
            ));
        }

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
                    .phone("09" + (int) (Math.random() * 1_000_000_000))
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
                            .name("Dr. " + (char) ('A' + i) + k)
                            .email("doctor" + i + j + k + "@example.com")
                            .password("hashed_password")
                            .phone("09" + (int) (Math.random() * 1_000_000_000))
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
                    .phone("09" + (int) (Math.random() * 1_000_000_000))
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

//        System.out.println(specialtyRepository.count());
//        System.out.println("hello");

    }
}

