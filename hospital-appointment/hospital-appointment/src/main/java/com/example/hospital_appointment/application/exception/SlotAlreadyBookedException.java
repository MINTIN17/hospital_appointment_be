package com.example.hospital_appointment.application.exception;

public class SlotAlreadyBookedException extends RuntimeException {
    public SlotAlreadyBookedException() {
        super("Khung giờ này đã được đặt bởi người khác.");
    }

    public SlotAlreadyBookedException(String message) {
        super(message);
    }
}
