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
import com.smartpack.dto.VehicleDto;
import com.smartpack.services.VehicleService;

import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Class VehicleController
 */
@RestController
@RequestMapping("/vehicle")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Vehicle", description = "Endpoints per gestionar vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    /**
     * Constructor VehicleController
     * 
     * @param vehicleService VehicleService
     */
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Crea un nou vehicle
     * 
     * @param dto VehicleDto
     * @return VehicleDto
     */
    @PostMapping("/crear")
    @Operation(summary = "Crea un nou vehicle", description = "permet crear un nou vehicle.")
    public ResponseEntity<VehicleDto> crear(@RequestBody VehicleDto dto) {
        return ResponseEntity.ok(vehicleService.crearVehicle(dto));
    }

    /**
     * Modificar vehicle
     * 
     * @param id  Long
     * @param dto VehicleDto
     * @return VehicleDto
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modificar vehicle", description = "permet modificar un vehicle ja creat.")
    public ResponseEntity<VehicleDto> editar(@PathVariable Long id, @RequestBody VehicleDto dto) {
        return ResponseEntity.ok(vehicleService.editarVehicle(id, dto));
    }

    /**
     * Obtenir vehicle
     * Obtenir vehicle per Id
     * 
     * @param id Long
     * @return VehicleDto
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir vehicle", description = "Obtenir vehicle per Id")
    public ResponseEntity<VehicleDto> obtenir(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.obtenirVehicle(id));
    }

    /**
     * Desactiva un vehicle
     * Desactiva un vehicle filtrat pel ID
     * 
     * @param id Long
     * @return Void
     */
    @PatchMapping("/{id}/desactivate")
    @Operation(summary = "Desactiva un vehicle", description = "Desactiva un vehicle filtrat pel ID")
    public ResponseEntity<ApiResponse> desactivar(@PathVariable Long id) {
        vehicleService.desactivarVehicle(id);
        return ResponseEntity.ok(new ApiResponse("Vehicle desactivat correctament."));
    }

    /**
     * llistar Vehicles
     * 
     * @return VehicleDto List
     */
    @GetMapping("/list")
    @Operation(summary = "Obtenir tots els vehicles", description = "Obtenir un llistat de tots els vehicles")
    public ResponseEntity<List<VehicleDto>> llistar() {
        return ResponseEntity.ok(vehicleService.llistarVehicles());
    }
}
