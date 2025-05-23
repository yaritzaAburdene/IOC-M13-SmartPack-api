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
import com.smartpack.dto.TransportistaRequestDto;
import com.smartpack.dto.TransportistaResponseDto;
import com.smartpack.services.TransportistaService;

import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
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

    /**
     * Crear transportista
     * permet crear un nou transportista
     * 
     * @param request TransportistaRequestDto
     * @return TransportistaResponseDto
     */
    @PostMapping("/crear")
    @Operation(summary = "Crea un nou transportista", description = "permet crear un nou transportista.")
    public ResponseEntity<TransportistaResponseDto> crearTransportista(@RequestBody TransportistaRequestDto request) {
        TransportistaResponseDto transportista = transportistaService.crearTransportista(request);
        return ResponseEntity.ok(transportista);
    }

    /**
     * Modificar transportista
     * permet modificar un transportiste ja creat
     * 
     * @param id      Long
     * @param request TransportistaRequestDto
     * @return TransportistaResponseDto
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modificar transportista", description = "permet modificar un transportiste ja creat.")
    public ResponseEntity<TransportistaResponseDto> editarTransportista(@PathVariable Long id,
            @RequestBody TransportistaRequestDto request) {
        TransportistaResponseDto transportista = transportistaService.editarTransportista(id, request);
        return ResponseEntity.ok(transportista);
    }

    /**
     * Obtenir transportista
     * Obtenir transportista per Id
     * 
     * @param id Long
     * @return TransportistaResponseDto
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir transportista", description = "Obtenir transportista per Id")
    public ResponseEntity<TransportistaResponseDto> obtenirTransportista(@PathVariable Long id) {
        TransportistaResponseDto transportista = transportistaService.getTransportista(id);
        return ResponseEntity.ok(transportista);
    }

    /**
     * getAllTransportistes
     * 
     * @return TransportistaResponseDto List
     */
    @GetMapping("/list")
    @Operation(summary = "Obtenir tots els transportistes", description = "Mostrar tots els transportistes activats")
    public ResponseEntity<List<TransportistaResponseDto>> getAllTransportistes() {
        List<TransportistaResponseDto> transportista = transportistaService.getAllTransportistes();
        return ResponseEntity.ok(transportista);
    }

    /**
     * Obtenir transportista per Usuari
     * Obtenir transportista per Id Usuari
     * 
     * @param usuariId Long
     * @return TransportistaResponseDto
     */
    @GetMapping("/usuari/{usuariId}")
    @Operation(summary = "Obtenir transportista per Usuari", description = "Obtenir transportista per Id Usuari")
    public ResponseEntity<TransportistaResponseDto> obtenirPerUsuari(@PathVariable Long usuariId) {
        return ResponseEntity.ok(transportistaService.getTransportistaByUsuariId(usuariId));
    }

    /**
     * Obtenir transportistes per empresa
     * Obtenir tots els transportistes d'una empresa
     * 
     * @param empresaId Long
     * @return TransportistaResponseDto List
     */
    @GetMapping("/empresa/{empresaId}")
    @Operation(summary = "Obtenir transportistes per empresa", description = "Obtenir tots els transportistes d'una empresa")
    public ResponseEntity<List<TransportistaResponseDto>> obtenirPerEmpresa(@PathVariable Long empresaId) {
        return ResponseEntity.ok(transportistaService.getTransportistesByEmpresa(empresaId));
    }

    /**
     * Assignar vehicle a transportista
     * Assigna un vehicle a un transportista
     * 
     * @param transportistaId Long
     * @param vehicleId       Long
     * @return ApiResponse
     */
    @PostMapping("/{transportistaId}/assignar-vehicle/{vehicleId}")
    @Operation(summary = "Assignar vehicle a transportista", description = "Assigna un vehicle a un transportista")
    public ResponseEntity<ApiResponse> assignVehicle(@PathVariable Long transportistaId,
            @PathVariable Long vehicleId) {
        transportistaService.assignVehicleToTransportista(transportistaId, vehicleId);
        return ResponseEntity.ok(new ApiResponse("Vehicle assignat correctament al transportista."));
    }

    /**
     * Desassignar un vehicle
     * Pertmet desassignar un vehicle d'un transportista
     * 
     * @param id Long
     * @return ApiResponse
     */
    @PatchMapping("/desassignar-vehicle/{id}")
    @Operation(summary = "Desassigna un vehicle d'un transportista")
    public ResponseEntity<ApiResponse> desassignarVehicle(@PathVariable Long id) {
        transportistaService.desasignarVehicle(id);
        return ResponseEntity.ok(new ApiResponse("Vehicle desassignat correctament"));
    }

    /**
     * Desactiva un transportista
     * Desactiva un transportista filtrat pel ID
     * 
     * @param id Long
     * @return Void
     */
    @PatchMapping("/{id}/desactivate")
    @Operation(summary = "Desactiva un transportista", description = "Desactiva un transportista filtrat pel ID")
    public ResponseEntity<ApiResponse> desactivar(@PathVariable Long id) {
        transportistaService.deshabilitarTransportista(id);
        return ResponseEntity.ok(new ApiResponse("Transportista desactivat correctament."));
    }

}
