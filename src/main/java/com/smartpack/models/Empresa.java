package com.smartpack.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Classe Empresa
 */
@Entity
@Data
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nif;

    @Column(nullable = false)
    private String nom;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefon;

    @Column(nullable = false)
    private String adreça;

    @OneToMany(mappedBy = "empresa")
    private List<Usuari> usuaris;

    /**
     * Activacio empresa
     */
    @Column(name = "active", nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
