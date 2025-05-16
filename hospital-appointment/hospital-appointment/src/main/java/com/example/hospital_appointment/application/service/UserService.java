package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.application.service.interfaces.IUserService;
import com.example.hospital_appointment.domain.model.User;
import com.example.hospital_appointment.domain.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepo userRepo;

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        return user.orElse(null);
    }
}
