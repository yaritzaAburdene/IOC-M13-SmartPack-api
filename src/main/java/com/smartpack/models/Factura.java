package com.smartpack.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Classe Factura
 */
@Entity
@Data
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numFactura;

    @Column(nullable = false)
    private double preu;

    @Column(nullable = false)
    private double iva;

    @Column(nullable = false)
    private double total;

    @Column(nullable = false)
    private Date data;

    @Column(nullable = false)
    private boolean pagat = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "metode_pagament")
    private MetodePagament metodePagament;

    @OneToOne
    @JoinColumn(name = "servei_id", nullable = false, unique = true)
    private Servei servei;

    @ManyToOne
    @JoinColumn(name = "usuari_id", nullable = false)
    private Usuari usuari;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
