package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.api.dto.ScheduleAvailableRequest;
import com.example.hospital_appointment.application.service.interfaces.IScheduleService;
import com.example.hospital_appointment.domain.model.Schedule;
import com.example.hospital_appointment.domain.repository.IScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService implements IScheduleService {
    @Autowired
    private IScheduleRepo scheduleRepo;

    @Override
    public List<Schedule> getSchedules(Long doctorId) {
        return scheduleRepo.getSchedulesByDoctorId(doctorId);
    }

    @Override
    public void updateAvailability(List<ScheduleAvailableRequest> updates) {
        for (ScheduleAvailableRequest update : updates) {
            scheduleRepo.getScheduleById(update.getId()).ifPresent(schedule -> {
                schedule.setAvailable(update.isAvailable());
                scheduleRepo.Change(schedule);
            });
        }
    }
}
