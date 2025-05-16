package com.example.hospital_appointment.domain.repository;

import com.example.hospital_appointment.domain.model.Schedule;

import java.util.List;

public interface IScheduleRepo {
    List<Schedule> save(List<Schedule> schedule);
    List<Schedule> getSchedulesByDoctorId(Long doctorId);
}
