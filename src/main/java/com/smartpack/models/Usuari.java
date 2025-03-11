package com.smartpack.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuari")
public class Usuari {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role; // ADMIN, USER
}