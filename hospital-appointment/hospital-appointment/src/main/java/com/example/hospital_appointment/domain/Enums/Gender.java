package com.example.hospital_appointment.domain.Enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ"),
    OTHER("Khác");

    private String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

}

