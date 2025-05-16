package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.application.service.interfaces.IDoctorService;
import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.model.Schedule;
import com.example.hospital_appointment.domain.model.User;
import com.example.hospital_appointment.domain.repository.IDoctorRepo;
import com.example.hospital_appointment.domain.repository.IScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private PasswordEncoder passwordEncoder;

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
        return doctorRepo.findByemail(email);
    }
}
