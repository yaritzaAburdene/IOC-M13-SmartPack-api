package com.smartpack.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.smartpack.dto.FacturaResponseDto;
import com.smartpack.models.Estat;
import com.smartpack.models.Factura;
import com.smartpack.models.Servei;
import com.smartpack.repositories.FacturaRepository;
import com.smartpack.repositories.ServeiRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Classe FacturaService
 */
@Service
public class FacturaService {

    private final ServeiRepository serveiRepository;
    private final FacturaRepository facturaRepository;

    /**
     * Constructor FacturaService
     * 
     * @param serveiRepository  ServeiRepository
     * @param facturaRepository FacturaRepository
     */
    public FacturaService(ServeiRepository serveiRepository, FacturaRepository facturaRepository) {
        this.serveiRepository = serveiRepository;
        this.facturaRepository = facturaRepository;
    }

    /**
     * generarFactura
     * 
     * @param serveiId Long
     * @return FacturaResponseDto
     */
    public FacturaResponseDto generarFactura(Long serveiId) {
        Servei servei = serveiRepository.findById(serveiId)
                .orElseThrow(() -> new EntityNotFoundException("Servei no trobat"));

        if (servei.getEstat() != Estat.ENTREGAT) {
            throw new IllegalStateException("No es pot generar la factura si el servei no està ENTREGAT");
        }

        Optional<Factura> facturaExist = facturaRepository.findByServeiId(serveiId);
        Factura factura;
        if (!facturaExist.isPresent()) {
            // Calcular preu
            // 2€ es el estandar de correos
            double preu = servei.getPaquet().getPes() * 2; // 2€ por kilo
            double iva = preu * 0.21; // 21% de IVA
            double total = preu + iva; // suma total

            factura = new Factura();
            factura.setNumFactura(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            factura.setPreu(preu);
            factura.setIva(iva);
            factura.setTotal(total);
            factura.setData(new Date());
            factura.setServei(servei);
            factura.setUsuari(servei.getUsuari());

            facturaRepository.save(factura);
        } else {
            factura = facturaExist.get();
        }

        return new FacturaResponseDto(factura);
    }

    /**
     * pagar
     * 
     * @param id Long
     * @return FacturaResponseDto
     */
    public FacturaResponseDto pagar(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Factura no trobada"));

        // TODO: no enviar error si no info
        if (factura.isPagat()) {
            throw new IllegalStateException("La factura ja està marcada com a pagada.");
        }

        factura.setPagat(true);
        facturaRepository.save(factura);
        return new FacturaResponseDto(factura);
    }

    /**
     * getFacturaPerServei
     * 
     * @param serveiId Long
     * @return FacturaResponseDto
     */
    public FacturaResponseDto getFacturaPerServei(@PathVariable Long serveiId) {
        Factura factura = facturaRepository.findByServeiId(serveiId)
                .orElseThrow(() -> new EntityNotFoundException("Factura no trobada per aquest servei"));

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
