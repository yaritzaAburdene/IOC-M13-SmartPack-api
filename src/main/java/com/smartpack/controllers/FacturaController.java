package com.smartpack.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartpack.dto.ApiResponse;
import com.smartpack.dto.FacturaResponseDto;
import com.smartpack.services.FacturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Classe FacturaController
 */
@RestController
@RequestMapping("/factura")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Factura", description = "Endpoints per gestionar facturació")
public class FacturaController {

    private final FacturaService facturaService;

    /**
     * Constructor FacturaController
     * 
     * @param facturaService FacturaService
     */
    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    /**
     * Generar Factura
     * Genera un factura d'un servei en concret
     * 
     * @param serveiId Long
     * @return FacturaResponseDto
     */
    @PostMapping("/generar/{serveiId}")
    @Operation(summary = "Genera una factura", description = "Genera un factura per ID servei.")
    public ResponseEntity<FacturaResponseDto> generarFactura(@PathVariable Long serveiId) {
        FacturaResponseDto factura = facturaService.generarFactura(serveiId);
        return ResponseEntity.ok(factura);
    }

    /**
     * Mararcar Factura Com Pagada
     * Marca la facgura com pagada d'un servei
     * 
     * @param id Long
     * @return ApiResponse
     */
    @PutMapping("/{id}/pagar")
    @Operation(summary = "Pagar una factura", description = "Pagar factura per ID servei.")
    public ResponseEntity<ApiResponse> marcarFacturaComPagada(@PathVariable Long id) {
        facturaService.pagar(id);
        return ResponseEntity.ok(new ApiResponse("Factura pagada"));
    }

    /**
     * Get Factura Per Servei
     * 
     * @param serveiId Long
     * @return FacturaResponseDto
     */
    @GetMapping("/servei/{serveiId}")
    @Operation(summary = "Obtenir factura", description = "Obté la factura per servei ID.")
    public ResponseEntity<FacturaResponseDto> getFacturaPerServei(@PathVariable Long serveiId) {
        FacturaResponseDto factura = facturaService.getFacturaPerServei(serveiId);
        return ResponseEntity.ok(factura);
    }

    /**
     * Get Factures Filtrades
     * Retorna totes les factures filtrades per parametres enviats.
     * 
     * @param usuariId Long
     * @param desde    DateTimeFormat
     * @param fins     DateTimeFormat
     * @return FacturaResponseDto List
     */
    @GetMapping("/list")
    @Operation(summary = "Obté factures", description = "Obté totes les factures amb filtre.")
    public ResponseEntity<List<FacturaResponseDto>> getFacturesFiltrades(
            @RequestParam(required = false) Long usuariId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fins) {
        List<FacturaResponseDto> factures = facturaService.getFacturesFiltrades(usuariId, desde, fins);
        return ResponseEntity.ok(factures);
    }

}
