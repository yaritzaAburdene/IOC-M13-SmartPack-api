package com.smartpack.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartpack.dto.AdminDataResponse;
import com.smartpack.dto.ApiResponse;
import com.smartpack.dto.TransportistaRequestDto;
import com.smartpack.dto.TransportistaResponseDto;
import com.smartpack.models.Empresa;
import com.smartpack.models.Usuari;
import com.smartpack.models.Vehicle;
import com.smartpack.repositories.EmpresaRepository;
import com.smartpack.repositories.TransportistaRepository;
import com.smartpack.repositories.UsuariRepository;
import com.smartpack.repositories.VehicleRepository;
import com.smartpack.services.AdminService;
import com.smartpack.services.TransportistaService;

import org.springframework.web.bind.annotation.RequestBody;

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
    @Operation(summary = "Obté tota la informacio filtrada per usuari i empresa")
    public ResponseEntity<AdminDataResponse> getResumGeneral(@RequestParam(required = false) Long usuariId,
            @RequestParam(required = false) Long empresaId) {
        AdminDataResponse dades = adminService.getResumGeneral(usuariId, empresaId);
        return ResponseEntity.ok(dades);
    }

}
