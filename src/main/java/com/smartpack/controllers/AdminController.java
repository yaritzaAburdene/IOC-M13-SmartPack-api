package com.smartpack.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartpack.dto.AdminDataResponse;
import com.smartpack.dto.FacturaResponseDto;
import com.smartpack.services.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Class TransportistaController
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Admin", description = "Endpoints per gestionar dades complet del sistema ")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * getResumGeneral
     * 
     * @param usuariId  Long
     * @param empresaId Long
     * @return AdminDataResponse
     */
    @GetMapping("/resum")
    @Operation(summary = "Obté Resum", description = "Obté tota la informacio filtrada per usuari i empresa")
    public ResponseEntity<AdminDataResponse> getResumGeneral(@RequestParam(required = false) Long usuariId,
            @RequestParam(required = false) Long empresaId) {
        AdminDataResponse dades = adminService.getResumGeneral(usuariId, empresaId);
        return ResponseEntity.ok(dades);
    }

    /**
     * getAllFactures
     * 
     * @return FacturaResponseDto List
     */
    @GetMapping("/factures")
    @Operation(summary = "Obté factures", description = "Obté totes les factures")
    public ResponseEntity<List<FacturaResponseDto>> getAllFactures() {
        List<FacturaResponseDto> factures = adminService.getAllFactures();
        return ResponseEntity.ok(factures);
    }

}
