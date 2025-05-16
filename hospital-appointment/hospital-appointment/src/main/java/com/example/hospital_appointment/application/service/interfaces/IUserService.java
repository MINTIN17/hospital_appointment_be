package com.example.hospital_appointment.application.service.interfaces;

import com.example.hospital_appointment.domain.model.User;

public interface IUserService {
    User findByEmail(String email);
}
