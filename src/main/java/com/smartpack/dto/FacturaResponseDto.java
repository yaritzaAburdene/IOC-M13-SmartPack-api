package com.smartpack.dto;

import java.util.Date;

import com.smartpack.models.Factura;
import com.smartpack.models.MetodePagament;

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
    private String usuariDni;
    private String usuariNomComplet;
    private String usuariAdreça;
    private boolean pagat;
    private MetodePagament metodePagament;
    private String comptePerDomiciliacio;
    private String compteEmpresa;

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
        this.usuariDni = factura.getUsuari().getDni();
        this.usuariNomComplet = factura.getUsuari().getNom() + " " + factura.getUsuari().getCognom();
        this.usuariAdreça = factura.getUsuari().getAdreça();
        this.pagat = factura.isPagat();
        this.metodePagament = factura.getMetodePagament();
        this.comptePerDomiciliacio = factura.getUsuari().getCompteBancari();
        if (factura.getMetodePagament() == MetodePagament.TRANSFERENCIA) {
            this.compteEmpresa = "ES12 3456 7890 1234 5678 9012"; // aquest compte es inventat
        }
    }
}
