package com.smartpack.controllers;

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
import com.smartpack.dto.TransportistaRequestDto;
import com.smartpack.dto.TransportistaResponseDto;
import com.smartpack.services.TransportistaService;

import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Class TransportistaController
 */
@RestController
@RequestMapping("/transportista")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Transportista", description = "Endpoints per gestionar transportistes")
public class TransportistaController {

    private final TransportistaService transportistaService;

    /**
     * Constructor TransportistaController
     * 
     * @param transportistaService transportistaService
     */
    public TransportistaController(TransportistaService transportistaService) {
        this.transportistaService = transportistaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<TransportistaResponseDto> crearTransportista(@RequestBody TransportistaRequestDto request) {
        TransportistaResponseDto transportista = transportistaService.crearTransportista(request);
        return ResponseEntity.ok(transportista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransportistaResponseDto> editarTransportista(@PathVariable Long id,
            @RequestBody TransportistaRequestDto request) {
        TransportistaResponseDto transportista = transportistaService.editarTransportista(id, request);
        return ResponseEntity.ok(transportista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportistaResponseDto> obtenirTransportista(@PathVariable Long id) {
        TransportistaResponseDto transportista = transportistaService.getTransportista(id);
        return ResponseEntity.ok(transportista);
    }
}
