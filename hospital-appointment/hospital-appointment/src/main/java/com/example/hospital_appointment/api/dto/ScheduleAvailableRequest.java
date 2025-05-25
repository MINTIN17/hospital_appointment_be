package com.example.hospital_appointment.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ScheduleAvailableRequest {
    private Long id;
    private boolean available;
}
