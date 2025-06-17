package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.api.dto.DoctorUpdateRequest;
import com.example.hospital_appointment.application.service.interfaces.IDoctorService;
import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.model.Schedule;
import com.example.hospital_appointment.domain.model.Specialization;
import com.example.hospital_appointment.domain.model.User;
import com.example.hospital_appointment.domain.repository.IDoctorRepo;
import com.example.hospital_appointment.domain.repository.IScheduleRepo;
import com.example.hospital_appointment.domain.repository.ISpecializationRepo;
import com.example.hospital_appointment.infrastructure.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService implements IDoctorService {
    @Autowired
    private IDoctorRepo doctorRepo;

    @Autowired
    private IScheduleRepo scheduleRepo;

    @Autowired
    private ISpecializationRepo specializationRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;

    @Override
    public Doctor save(Doctor doctor) {
        User user = doctor.getUser();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        doctor = doctorRepo.save(doctor);

        List<Schedule> schedules = new ArrayList<>();

        for (DayOfWeek day : DayOfWeek.values()) { // Từ MONDAY đến SUNDAY
            LocalTime start = LocalTime.of(8, 0);
            LocalTime end = LocalTime.of(17, 0);

            while (start.isBefore(end)) {
                LocalTime slotEnd = start.plusHours(1);
                Schedule schedule = new Schedule();
                schedule.setDoctor(doctor);
                schedule.setDayOfWeek(day);
                schedule.setStartTime(start);
                schedule.setEndTime(slotEnd);
                schedule.setAvailable(false);
                schedules.add(schedule);

                start = slotEnd;
            }
        }

        scheduleRepo.save(schedules);
        return doctor;
    }

    @Override
    public List<Doctor> getDoctorsByHospital(Long hospital_id) {
        return doctorRepo.findByUser_Hospital_Id(hospital_id);
    }

    @Override
    public Optional<Doctor> getDoctorByEmail(String email) {
        return doctorRepo.findByEmail(email);
    }

    @Override
    @Transactional
    public String updateDoctor(DoctorUpdateRequest request) {

        Optional<Doctor> doctor = doctorRepo.findById(request.getId());
        if (doctor.isEmpty()) {
            return "invalid doctor";
        }
        Doctor doc = doctor.get();
        User user = doc.getUser();

        Specialization specialization = specializationRepo.findById(request.getSpecialization_id());



        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());
        user.setPhone(request.getPhone());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setAddress(request.getAddress());
        user.setAvatarUrl(request.getAvatarUrl());

        doc.setAbout(request.getAbout());
        doc.setSpecialization(specialization);
        doc.setYearsOfExperience(request.getYearsOfExperience());
        doc.setUser(user);

        doctorRepo.save(doc);

        return "Update success";
    }


    @Override
    public String Ban(Long patient_id) {
        return doctorRepo.Ban(patient_id);
    }

    @Override
    public String unBan(Long patient_id) {
        return doctorRepo.unBan(patient_id);
    }
}
