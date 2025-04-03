package com.smartpack.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Classe Vehicle
 */
@Entity
@Data
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String tipus;

    @Column(nullable = true)
    private String marca;

    @Column(nullable = true)
    private String model;

    @Column(nullable = true, unique = true)
    private String matricula;

    @Column(nullable = true)
    private String color;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @OneToOne(mappedBy = "vehicle")
    private Transportista transportista;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
