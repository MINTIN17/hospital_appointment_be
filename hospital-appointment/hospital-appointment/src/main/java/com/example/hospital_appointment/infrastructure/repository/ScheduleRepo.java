package com.example.hospital_appointment.infrastructure.repository;

import com.example.hospital_appointment.domain.model.Schedule;
import com.example.hospital_appointment.domain.repository.IScheduleRepo;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaPatientRepo;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaScheduleRepo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleRepo implements IScheduleRepo {

    private final JpaScheduleRepo jpaScheduleRepo;

    public ScheduleRepo(JpaScheduleRepo jpaScheduleRepo) {
        this.jpaScheduleRepo = jpaScheduleRepo;
    }

    @Override
    public List<Schedule> save(List<Schedule> schedules) {
        return jpaScheduleRepo.saveAll(schedules);
    }

    @Override
    public Schedule Change(Schedule schedule) {
        return jpaScheduleRepo.save(schedule);
    }

    @Override
    public List<Schedule> getSchedulesByDoctorId(Long doctorId) {
        return jpaScheduleRepo.findByDoctorId(doctorId);
    }

    @Override
    public Optional<Schedule> getScheduleById(Long scheduleId) {
        return jpaScheduleRepo.findById(scheduleId);
    }
}
