package com.example.hospital_appointment.domain.repository;

import com.example.hospital_appointment.domain.model.Schedule;

import javax.swing.plaf.OptionPaneUI;
import java.util.List;
import java.util.Optional;

public interface IScheduleRepo {
    List<Schedule> save(List<Schedule> schedule);
    Schedule Change(Schedule schedule);
    List<Schedule> getSchedulesByDoctorId(Long doctorId);
    Optional<Schedule> getScheduleById(Long scheduleId);
}
