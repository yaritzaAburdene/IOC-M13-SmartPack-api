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
    private double preu;
    private double iva;
    private double total;
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
        this.total = factura.getTotal();
        this.data = factura.getData();
        this.serveiId = factura.getServei().getId();
        this.usuariId = factura.getUsuari().getId();
        this.pagat = factura.isPagat();
        this.metodePagament = factura.getMetodePagament();
    }
}
