package com.example.hospital_appointment.domain.model;

public interface EmailSender {
    void send(String to, String subject, String content);
}
