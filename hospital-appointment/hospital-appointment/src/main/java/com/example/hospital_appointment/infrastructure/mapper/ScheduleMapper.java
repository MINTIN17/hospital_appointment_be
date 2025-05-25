package com.example.hospital_appointment.infrastructure.mapper;

import com.example.hospital_appointment.api.dto.RegisterRequest;
import com.example.hospital_appointment.api.dto.ScheduleResponse;
import com.example.hospital_appointment.domain.Enums.Role;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.domain.model.Schedule;
import com.example.hospital_appointment.domain.model.User;

public class ScheduleMapper {
    public static ScheduleResponse toScheduleResponse(Schedule schedule) {
         ScheduleResponse scheduleResponse = ScheduleResponse.builder()
                 .id(schedule.getId())
                 .available(schedule.isAvailable())
                 .dayOfWeek(schedule.getDayOfWeek())
                 .endTime(schedule.getEndTime())
                 .startTime(schedule.getStartTime())
                .build();

        return scheduleResponse;
    }
}
