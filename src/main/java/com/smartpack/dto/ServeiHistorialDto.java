package com.smartpack.dto;

import java.time.LocalDateTime;

import com.smartpack.models.ServeiHistorial;

import lombok.Data;

/**
 * Classe ServeiHistorialDto
 */
@Data
public class ServeiHistorialDto {

    private Long id;
    private Long serveId;
    private Long tranpostistaId;
    private String estat;
    private String descripcioCanvi;
    private String adreçaDestinatari;
    private String tipusCanvi;
    private LocalDateTime dataCanvi;

    /**
     * Constructor ServeiHistorialDto
     * 
     * @param h ServeiHistorial
     */
    public ServeiHistorialDto(ServeiHistorial h) {
        this.id = h.getId();
        this.serveId = (h.getServei() != null) ? h.getServei().getId() : null;
        this.tranpostistaId = (h.getServei() != null)
                ? (h.getServei().getTransportista() != null) ? h.getServei().getTransportista().getId() : null
                : null;
        this.estat = h.getEstat();
        this.descripcioCanvi = h.getDescripcioCanvi();
        this.adreçaDestinatari = h.getAdreçaDestinatari();
        this.tipusCanvi = h.getTipusCanvi();
        this.dataCanvi = h.getDataCanvi();
    }
}
