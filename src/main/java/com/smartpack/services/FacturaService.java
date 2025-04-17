package com.smartpack.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.smartpack.dto.FacturaResponseDto;
import com.smartpack.models.Estat;
import com.smartpack.models.Factura;
import com.smartpack.models.Servei;
import com.smartpack.repositories.FacturaRepository;
import com.smartpack.repositories.ServeiRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FacturaService {

    private final ServeiRepository serveiRepository;
    private final FacturaRepository facturaRepository;

    public FacturaService(ServeiRepository serveiRepository, FacturaRepository facturaRepository) {
        this.serveiRepository = serveiRepository;
        this.facturaRepository = facturaRepository;
    }

    public FacturaResponseDto generarFactura(Long serveiId) {
        Servei servei = serveiRepository.findById(serveiId)
                .orElseThrow(() -> new EntityNotFoundException("Servei no trobat"));

        if (servei.getEstat() != Estat.ENTREGAT) {
            throw new IllegalStateException("No es pot generar la factura si el servei no està ENTREGAT");
        }

        // Calcular preu
        // TODO: comentar amb els companys el preu del servei
        int preu = 20; // provicional
        int iva = (int) (preu * 0.21); // 21% de IVA

        Factura factura = new Factura();
        factura.setNumFactura(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        factura.setPreu(preu);
        factura.setIva(iva);
        factura.setData(new Date());
        factura.setServei(servei);
        factura.setUsuari(servei.getUsuari());

        facturaRepository.save(factura);

        return new FacturaResponseDto(factura);
    }

    /**
     * getFacturesFiltrades
     * 
     * @param usuariId Long
     * @param desde    LocalDate
     * @param fins     LocalDate
     * @return FacturaResponseDto List
     */
    public List<FacturaResponseDto> getFacturesFiltrades(Long usuariId, LocalDate desde, LocalDate fins) {
        List<Factura> factures;

        if (usuariId != null && desde != null && fins != null) {
            factures = facturaRepository.findByUsuariIdAndDataBetween(usuariId, java.sql.Date.valueOf(desde),
                    java.sql.Date.valueOf(fins));
        } else if (usuariId != null) {
            factures = facturaRepository.findByUsuariId(usuariId);
        } else {
            factures = facturaRepository.findAll();
        }

        return factures.stream().map(FacturaResponseDto::new).toList();
    }

}
