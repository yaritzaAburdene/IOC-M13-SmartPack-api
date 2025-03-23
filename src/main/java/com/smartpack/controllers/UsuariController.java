package com.smartpack.controllers;

import com.smartpack.dto.ApiResponse;
import com.smartpack.dto.UserRequestDto;
import com.smartpack.dto.UserResponseDto;
import com.smartpack.services.UsuariService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Classe UsuariController
 */
@RestController
@RequestMapping("/usuari")
@CrossOrigin(origins = "*")
@Tag(name = "Usuaris", description = "Endpoints per gestionar usuaris")
public class UsuariController {

    private final UsuariService usuariService;

    /**
     * Constructor UsuariController
     * 
     * @param usuariService UsuariService
     */
    public UsuariController(UsuariService usuariService) {
        this.usuariService = usuariService;
    }

    /**
     * Crear usuari
     * permet crear un nou usuari
     * 
     * @param userRequestDto UserResponseDto
     * @return UserResponseDto
     */
    @PostMapping("/create")
    @Operation(summary = "Crea un nou usuari", description = "permet crear un nou usuari.")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(this.usuariService.createUser(userRequestDto));
    }

    /**
     * Modificar un usuari
     * permet modificar un usuari ja creat
     * 
     * @param id             long
     * @param userRequestDto UserResponseDto
     * @return UserResponseDto
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modificar un usuari", description = "permet modificar un usuari ja creat.")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(usuariService.updateUser(id, userRequestDto));
    }

    /**
     * Get All Users
     * Llistar tots els usuaris
     * Mostrar tots els usuaris creats
     * 
     * @return UserResponseDto llistat
     */
    @GetMapping("/list")
    @Operation(summary = "Llistar tots els usuaris ", description = "Mostrar tots els usuaris creats")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(this.usuariService.getAllUsers());
    }

    /**
     * Get User By Id
     * Obtenir usuari per ID
     * 
     * @param id Long
     * @return UserResponseDto
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un usuari", description = "Mostrar un usuari filtrat pel ID")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(this.usuariService.getUserById(id));
    }

    /**
     * Deactivate User
     * Desactivar un usuari per ID
     * 
     * @param id long
     * @return ApiResponse
     */
    @PatchMapping("/{id}/desactivate")
    @Operation(summary = "Desactiva un usuari", description = "Desactiva un usuari filtrat pel ID")
    public ResponseEntity<ApiResponse> deactivateUser(@PathVariable Long id) {
        this.usuariService.deactivateUser(id);
        return ResponseEntity.ok(new ApiResponse("Usuari desactivat correctament."));
    }
}
