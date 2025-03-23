package com.smartpack.controllers;

import com.smartpack.dto.ApiResponse;
import com.smartpack.dto.EmpresaRequestDto;
import com.smartpack.dto.EmpresaResponseDto;
import com.smartpack.services.EmpresaService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Classe EmpresaController
 */
@RestController
@RequestMapping("/empresa")
@CrossOrigin(origins = "*")
@Tag(name = "Empresa", description = "Endpoints per gestionar empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    /**
     * Constructor EmpresaController
     * 
     * @param empresaService EmpresaService
     */
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    /**
     * Crear empresa
     * permet crear un nou empresa
     * 
     * @param empresaRequestDto EmpresaRequestDto
     * @return EmpresaRequestDto
     */
    @PostMapping("/create")
    @Operation(summary = "Crea un nou empresa", description = "permet crear una nova empresa.")
    public ResponseEntity<EmpresaResponseDto> crearEmpresa(@RequestBody EmpresaRequestDto empresaRequestDto) {
        return ResponseEntity.ok(this.empresaService.createEmpresa(empresaRequestDto));
    }

    /**
     * Modificar un empresa
     * permet modificar un empresa ja creat
     * 
     * @param id                long
     * @param empresaRequestDto EmpresaRequestDto
     * @return EmpresaResponseDto
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modificar empresa", description = "permet modificar empresa ja creada.")
    public ResponseEntity<EmpresaResponseDto> updateEmpresa(@PathVariable Long id,
            @RequestBody EmpresaRequestDto empresaRequestDto) {
        return ResponseEntity.ok(empresaService.updateEmpresa(id, empresaRequestDto));
    }

    /**
     * Get All Empresa
     * Llistar totes les empresa
     * Mostrar totes les empresa creades
     * 
     * @return EmpresaResponseDto llistat
     */
    @GetMapping("/list")
    @Operation(summary = "Llistar totes les empreses ", description = "Mostrar totes les empreses creades")
    public ResponseEntity<List<EmpresaResponseDto>> getAllEmpresas() {
        return ResponseEntity.ok(this.empresaService.getAllEmpreses());
    }

    /**
     * Get Empresa By Id
     * Obtenir empresa per ID
     * 
     * @param id Long
     * @return EmpresaResponseDto
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir una empresa", description = "Mostrar una empresa filtrat pel ID")
    public ResponseEntity<EmpresaResponseDto> getEmpresaById(@PathVariable Long id) {
        return ResponseEntity.ok(this.empresaService.getEmpresaById(id));
    }

    /**
     * Deactivate Empresa
     * Desactivar una empresa per ID
     * 
     * @param id long
     * @return ApiResponse
     */
    @PatchMapping("/{id}/desactivate")
    @Operation(summary = "Desactiva una empresa", description = "Desactiva una empresa filtrat pel ID")
    public ResponseEntity<ApiResponse> deactivateEmpresa(@PathVariable Long id) {
        this.empresaService.deactivateEmpresa(id);
        return ResponseEntity.ok(new ApiResponse("Empresa desactivada correctament."));
    }
}
