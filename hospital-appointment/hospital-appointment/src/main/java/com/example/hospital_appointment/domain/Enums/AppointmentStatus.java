package com.example.hospital_appointment.domain.Enums;

public enum AppointmentStatus {
    PENDING("Chờ xác nhận"),
    CONFIRMED("Đã xác nhận"),
    CANCELLED("Đã hủy"),
    COMPLETED("Đã hoàn thành");

    private String displayName;

    AppointmentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean canBeUpdatedTo(AppointmentStatus newStatus) {
        if (this == PENDING && (newStatus == CONFIRMED || newStatus == CANCELLED)) {
            return true;
        } else if (this == CONFIRMED && newStatus == COMPLETED) {
            return true;
        } else if (this == CANCELLED) {
            return false;
        } else if (this == COMPLETED) {
            return false;
        }
        return false;
    }
}
