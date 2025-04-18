package com.smartpack.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Classe ServeiHistorial
 */
@Entity
@Data
@Table(name = "servei_historial")
public class ServeiHistorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "servei_id", nullable = false)
    private Servei servei;

    @Column(nullable = false)
    private String estat;

    private String descripcioCanvi;

    private String adreçaDestinatari;

    private LocalDateTime dataCanvi;

    private String tipusCanvi;
}
