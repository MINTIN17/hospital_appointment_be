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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public boolean isTimeOverlap(LocalTime start1, LocalTime end1,
                                 LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    private List<LocalDate> getUpcomingDatesForDay(DayOfWeek dayOfWeek, LocalDate from, int daysAhead) {
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < daysAhead; i++) {
            LocalDate date = from.plusDays(i);
            if (date.getDayOfWeek() == dayOfWeek) {
                dates.add(date);
            }
        }
        return dates;
    }


    @Override
    public String updateAvailability(List<ScheduleAvailableRequest> updates) {
        LocalDate today = LocalDate.now();
        int checkDays = 7;

        for (ScheduleAvailableRequest update : updates) {
            if (!update.isAvailable()) {
                Optional<Schedule> optionalSchedule = scheduleRepo.getScheduleById(update.getId());
                if (optionalSchedule.isPresent()) {
                    Schedule schedule = optionalSchedule.get();
                    List<LocalDate> targetDates = getUpcomingDatesForDay(schedule.getDayOfWeek(), today, checkDays);

                    List<Appointment> appointments = appointmentRepo
                            .findByDoctorIdAndAppointmentDateInAndStatusIn(
                                    schedule.getDoctor().getId(),
                                    targetDates,
                                    List.of(AppointmentStatus.PENDING, AppointmentStatus.CONFIRMED)
                            );

                    for (Appointment appt : appointments) {
                        System.out.println("Checking overlap:");
                        System.out.println("Schedule: " + schedule.getStartTime() + " - " + schedule.getEndTime());
                        System.out.println("Appointment: " + appt.getStartTime() + " - " + appt.getEndTime());
                        if (isTimeOverlap(schedule.getStartTime(), schedule.getEndTime(),
                                appt.getStartTime(), appt.getEndTime())) {
                            System.out.println("Status: " + appt.getStatus() + appt.getId());
                            return "Conflict schedule";
                        }
                    }
                }
            }
        }

        // Nếu không có trùng -> cập nhật
        for (ScheduleAvailableRequest update : updates) {
            scheduleRepo.getScheduleById(update.getId()).ifPresent(schedule -> {
                schedule.setAvailable(update.isAvailable());
                scheduleRepo.Change(schedule);
            });
        }

        return "Update success";
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
                        List.of(AppointmentStatus.PENDING, AppointmentStatus.CONFIRMED, AppointmentStatus.COMPLETED)
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
