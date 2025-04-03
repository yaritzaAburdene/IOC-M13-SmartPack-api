package com.smartpack.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Classe Paquet
 */
@Entity
@Data
@Table(name = "paquet")
public class Paquet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String detalls;

    @Column(nullable = true)
    private int pes;

    @Column(nullable = true)
    private String mida;

    @Column(nullable = false)
    private String nomDestinatari;

    @Column(nullable = false)
    private String adreçadestinatari;

    @Column(nullable = false)
    private String telefondestinatari;

    @Column(nullable = true)
    private String codiqr;

    @OneToOne(mappedBy = "paquet")
    private Servei servei;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
