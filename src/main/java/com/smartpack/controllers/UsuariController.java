package com.smartpack.controllers;

import com.smartpack.dto.ApiResponse;
import com.smartpack.dto.UserRequestDto;
import com.smartpack.dto.UserResponseDto;
import com.smartpack.services.UsuariService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(this.usuariService.createUser(userRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(usuariService.updateUser(id, userRequestDto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(this.usuariService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(this.usuariService.getUserById(id));
    }

    @PatchMapping("/{id}/desactivate")
    public ResponseEntity<ApiResponse> deactivateUser(@PathVariable Long id) {
        this.usuariService.deactivateUser(id);
        return ResponseEntity.ok(new ApiResponse("Usuari desactivat correctament."));
    }
}
