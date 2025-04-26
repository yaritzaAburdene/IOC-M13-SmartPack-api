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
import com.smartpack.dto.ServeiHistorialDto;
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

    /**
     * crearServei
     * 
     * @param request ServeiRequestDto
     * @return ServeiResponseDto
     */
    @PostMapping("/crear")
    @Operation(summary = "Crea un nou servei", description = "permet crear un nou servei.")
    public ResponseEntity<ServeiResponseDto> crearServei(@RequestBody ServeiRequestDto request) {
        ServeiResponseDto servei = ServeiService.crearServei(request);
        return ResponseEntity.ok(servei);
    }

    /**
     * editarServei
     * 
     * @param id      Long
     * @param request ServeiRequestDto
     * @return ServeiResponseDto
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modificar un servei", description = "permet modificar un servei ja existent.")
    public ResponseEntity<ServeiResponseDto> editarServei(@PathVariable Long id,
            @RequestBody ServeiRequestDto request) {
        ServeiResponseDto servei = ServeiService.editarServei(id, request);
        return ResponseEntity.ok(servei);
    }

    /**
     * getAllServeis
     * 
     * @return ServeiResponseDto List
     */
    @GetMapping("/list")
    @Operation(summary = "Obtenir tots els serveis", description = "Mostrar tots els serveis activats")
    public ResponseEntity<List<ServeiResponseDto>> getAllServeis() {
        List<ServeiResponseDto> serveis = ServeiService.getAllServeis();
        return ResponseEntity.ok(serveis);
    }

    /**
     * getServeiById
     * 
     * @param id Long
     * @return ServeiResponseDto
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un servei", description = "Mostrar un servei filtrat pel ID")
    public ResponseEntity<ServeiResponseDto> getServeiById(@PathVariable Long id) {
        ServeiResponseDto servei = ServeiService.getServeiById(id);
        return ResponseEntity.ok(servei);
    }

    /**
     * canviarEstatServei
     * 
     * @param serveiId Long
     * @param request  CanviarEstatServeiRequestDto
     * @return ServeiResponseDto
     */
    @PatchMapping("/{serveiId}/estat")
    @Operation(summary = "Canviar estat a servei", description = "permet modificar l'estat al servei.")
    public ResponseEntity<ServeiResponseDto> canviarEstatServei(@PathVariable Long serveiId,
            @RequestBody CanviarEstatServeiRequestDto request) {
        ServeiResponseDto serveiActualitzat = ServeiService.canviarEstatServei(serveiId, request.getEstat());
        return ResponseEntity.ok(serveiActualitzat);
    }

    /**
     * obtenirServeisPerUsuari
     * 
     * @param usuariId Long
     * @return ServeiResponseDto List
     */
    @GetMapping("/usuari/{usuariId}")
    @Operation(summary = "Obtenir servei usuari", description = "Obtenir servei per usuari Id")
    public ResponseEntity<List<ServeiResponseDto>> obtenirServeisPerUsuari(@PathVariable Long usuariId) {
        List<ServeiResponseDto> serveis = ServeiService.getServeisByUsuariId(usuariId);
        return ResponseEntity.ok(serveis);
    }

    /**
     * obtenirServeisPerTransportista
     * 
     * @param transportistaId Long
     * @return ServeiResponseDto List
     */
    @GetMapping("/transportista/{transportistaId}")
    @Operation(summary = "Obtenir servei transportista", description = "Obtenir servei per transportista Id")
    public ResponseEntity<List<ServeiResponseDto>> obtenirServeisPerTransportista(@PathVariable Long transportistaId) {
        List<ServeiResponseDto> serveis = ServeiService.getServeisByTransportistaId(transportistaId);
        return ResponseEntity.ok(serveis);
    }

    /**
     * desactivarServei
     * 
     * @param id Long
     * @return ApiResponse
     */
    @PatchMapping("/{id}/desactivar")
    @Operation(summary = "Desactiva un servei", description = "Desactiva un servei filtrat pel ID")
    public ResponseEntity<ApiResponse> desactivarServei(@PathVariable Long id) {
        ServeiService.desactivarServei(id);
        return ResponseEntity.ok(new ApiResponse("Servei desactivat correctament."));
    }

    /**
     * regenerarQrPerServei
     * 
     * @param id Long
     * @return ServeiResponseDto
     */
    @PostMapping("/{id}/regenerar-qr")
    @Operation(summary = "Generar QR", description = "Generar QR amb Id service")
    public ResponseEntity<ServeiResponseDto> regenerarQrPerServei(@PathVariable Long id) {
        ServeiResponseDto qrBase64 = ServeiService.regenerarCodiQrPorServei(id);
        return ResponseEntity.ok(qrBase64);
    }

    /**
     * getHistorialPerServei
     * 
     * @param serveiId Long
     * @return ServeiHistorialDto List
     */
    @GetMapping("/{serveiId}/historial")
    @Operation(summary = "Obté historial d'un servei", description = "Generar QR amb Id service")
    public ResponseEntity<List<ServeiHistorialDto>> getHistorialPerServei(@PathVariable Long serveiId) {
        List<ServeiHistorialDto> historial = ServeiService.getHistorialPerServei(serveiId);
        return ResponseEntity.ok(historial);
    }
}
