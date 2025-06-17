package com.example.hospital_appointment.domain.model;

import jakarta.persistence.*;

@Entity
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    public Disease() {}
    public Disease(String name, Specialty specialty) {
        this.name = name;
        this.specialty = specialty;
    }
}

