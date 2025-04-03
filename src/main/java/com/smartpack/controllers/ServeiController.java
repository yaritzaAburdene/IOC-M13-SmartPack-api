package com.smartpack.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartpack.dto.ApiResponse;
import com.smartpack.dto.CanviarEstatServeiRequestDto;
import com.smartpack.dto.ServeiRequestDto;
import com.smartpack.dto.ServeiResponseDto;
import com.smartpack.services.ServeiService;

import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Class ServeiController
 */
@RestController
@RequestMapping("/servei")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Servei", description = "Endpoints per gestionar serveis")
public class ServeiController {

    private final ServeiService ServeiService;

    /**
     * Constructor ServeiController
     * 
     * @param ServeiService ServeiService
     */
    public ServeiController(ServeiService ServeiService) {
        this.ServeiService = ServeiService;
    }

    @PostMapping("/crear")
    @Operation(summary = "Crea un nou servei", description = "permet crear un nou servei.")
    public ResponseEntity<ServeiResponseDto> crearServei(@RequestBody ServeiRequestDto request) {
        ServeiResponseDto servei = ServeiService.crearServei(request);
        return ResponseEntity.ok(servei);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar un servei", description = "permet modificar un servei ja existent.")
    public ResponseEntity<ServeiResponseDto> editarServei(@PathVariable Long id,
            @RequestBody ServeiRequestDto request) {
        ServeiResponseDto servei = ServeiService.editarServei(id, request);
        return ResponseEntity.ok(servei);
    }

    @PatchMapping("/{serveiId}/estat")
    @Operation(summary = "Canviar estat a servei", description = "permet modificar l'estat al servei.")
    public ResponseEntity<ServeiResponseDto> canviarEstatServei(@PathVariable Long serveiId,
            @RequestBody CanviarEstatServeiRequestDto request) {
        ServeiResponseDto serveiActualitzat = ServeiService.canviarEstatServei(serveiId, request.getEstat());
        return ResponseEntity.ok(serveiActualitzat);
    }

    @GetMapping("/usuari/{usuariId}")
    @Operation(summary = "Obtenir servei usuari", description = "Obtenir servei per usuari Id")
    public ResponseEntity<List<ServeiResponseDto>> obtenirServeisPerUsuari(@PathVariable Long usuariId) {
        List<ServeiResponseDto> serveis = ServeiService.getServeisByUsuariId(usuariId);
        return ResponseEntity.ok(serveis);
    }

    @GetMapping("/transportista/{transportistaId}")
    @Operation(summary = "Obtenir servei transportista", description = "Obtenir servei per transportista Id")
    public ResponseEntity<List<ServeiResponseDto>> obtenirServeisPerTransportista(@PathVariable Long transportistaId) {
        List<ServeiResponseDto> serveis = ServeiService.getServeisByTransportistaId(transportistaId);
        return ResponseEntity.ok(serveis);
    }
}
