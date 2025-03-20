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

@RestController
@RequestMapping("/usuari")
@CrossOrigin(origins = "*")
@Tag(name = "Usuaris", description = "Endpoints per gestionar usuaris")
public class UsuariController {

    private final UsuariService usuariService;

    public UsuariController(UsuariService usuariService) {
        this.usuariService = usuariService;
    }

    @PostMapping("/create")
    @Operation(summary = "Crea un nou usuari", description = "permet crear un nou usuari.")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(this.usuariService.createUser(userRequestDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar un usuari", description = "permet modificar un usuari ja creat.")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(usuariService.updateUser(id, userRequestDto));
    }

    @GetMapping("/list")
    @Operation(summary = "Llistar tots els usuaris ", description = "Mostrar tots els usuaris creats")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(this.usuariService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un usuari", description = "Mostrar un usuari filtrat pel ID")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(this.usuariService.getUserById(id));
    }

    @PatchMapping("/{id}/desactivate")
    @Operation(summary = "Desactiva un usuari", description = "Desactiva un usuari filtrat pel ID")
    public ResponseEntity<ApiResponse> deactivateUser(@PathVariable Long id) {
        this.usuariService.deactivateUser(id);
        return ResponseEntity.ok(new ApiResponse("Usuari desactivat correctament."));
    }
}
