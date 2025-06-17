package com.example.hospital_appointment.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "specialty")
    private List<Disease> diseases;

    public Specialty() {}
    public Specialty(String name) {
        this.name = name;
    }
}
