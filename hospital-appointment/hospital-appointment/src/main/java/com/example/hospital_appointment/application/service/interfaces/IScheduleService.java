package com.example.hospital_appointment.application.service.interfaces;

import com.example.hospital_appointment.domain.model.Schedule;

import java.util.List;

public interface IScheduleService {
    List<Schedule> getSchedules(Long doctorId);
}
