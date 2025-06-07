package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.api.dto.ScheduleAvailableRequest;
import com.example.hospital_appointment.application.service.interfaces.IScheduleService;
import com.example.hospital_appointment.domain.Enums.AppointmentStatus;
import com.example.hospital_appointment.domain.model.Appointment;
import com.example.hospital_appointment.domain.model.Schedule;
import com.example.hospital_appointment.domain.repository.IAppointmentRepo;
import com.example.hospital_appointment.domain.repository.IScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ScheduleService implements IScheduleService {
    @Autowired
    private IScheduleRepo scheduleRepo;

    @Autowired
    IAppointmentRepo appointmentRepo;

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

    @Override
    public List<Schedule> getSchedulesWithAvailability(Long doctorId) {
        List<Schedule> schedules = scheduleRepo.getSchedulesByDoctorId(doctorId);

        List<LocalDate> next7Days = IntStream.rangeClosed(1, 7)
                .mapToObj(i -> LocalDate.now().plusDays(i))
                .collect(Collectors.toList());

        List<Appointment> appointments = appointmentRepo
                .findByDoctorIdAndAppointmentDateInAndStatusIn(
                        doctorId,
                        next7Days,
                        List.of(AppointmentStatus.PENDING, AppointmentStatus.CONFIRMED)
                );

        List<Schedule> modifiedSchedules = schedules.stream()
                .map(s -> Schedule.builder()
                        .id(s.getId())
                        .doctor(s.getDoctor())
                        .dayOfWeek(s.getDayOfWeek())
                        .startTime(s.getStartTime())
                        .endTime(s.getEndTime())
                        .available(s.isAvailable())
                        .build())
                .collect(Collectors.toList());

        for (Schedule schedule : modifiedSchedules) {
            boolean isBooked = appointments.stream().anyMatch(app ->
                    app.getAppointmentDate().getDayOfWeek() == schedule.getDayOfWeek() &&
                            app.getStartTime().equals(schedule.getStartTime()) &&
                            app.getEndTime().equals(schedule.getEndTime())
            );

            if (isBooked) {
                schedule.setAvailable(false);
            }
        }

        return modifiedSchedules;
    }

}
