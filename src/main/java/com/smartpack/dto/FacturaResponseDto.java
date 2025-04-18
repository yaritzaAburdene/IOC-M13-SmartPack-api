package com.smartpack.dto;

import java.util.Date;

import com.smartpack.models.Factura;

import lombok.Data;

/**
 * Classe FacturaResponseDto
 */
@Data
public class FacturaResponseDto {
    private Long id;
    private String numFactura;
    private int preu;
    private int iva;
    private Date data;
    private Long serveiId;
    private Long usuariId;
    private boolean pagat;
    private String metodePagament;

    /**
     * FacturaResponseDto
     * 
     * @param factura Factura
     */
    public FacturaResponseDto(Factura factura) {
        this.id = factura.getId();
        this.numFactura = factura.getNumFactura();
        this.preu = factura.getPreu();
        this.iva = factura.getIva();
        this.data = factura.getData();
        this.serveiId = factura.getServei().getId();
        this.usuariId = factura.getUsuari().getId();
        this.pagat = factura.isPagat();
        this.metodePagament = factura.getMetodePagament();
    }
}
