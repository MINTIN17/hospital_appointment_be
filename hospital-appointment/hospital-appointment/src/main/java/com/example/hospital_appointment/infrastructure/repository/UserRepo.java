package com.example.hospital_appointment.infrastructure.repository;

import com.example.hospital_appointment.domain.model.User;
import com.example.hospital_appointment.domain.repository.IUserRepo;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaPatientRepo;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaUserRepo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepo implements IUserRepo {

    private final JpaUserRepo jpaUserRepo ;

    public UserRepo(JpaUserRepo jpaUserRepo) {
        this.jpaUserRepo = jpaUserRepo;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepo.findByEmail(email);
    }

    @Override
    public User CreateAdmin(User user) {
        return jpaUserRepo.save(user);
    }

    @Override
    public User save(User user) {
        return user;
    }
}
