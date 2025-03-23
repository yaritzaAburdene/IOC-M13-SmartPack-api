package com.smartpack.controllers;

import com.smartpack.dto.ForgotPasswordRequest;
import com.smartpack.dto.LoginUsuariDto;
import com.smartpack.dto.RegistrarUsuariDto;
import com.smartpack.dto.ResetPasswordRequest;
import com.smartpack.models.Usuari;
import com.smartpack.services.AuthenticationService;
import com.smartpack.services.JwtService;
import com.smartpack.utils.LoginResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;

/**
 * AuthController
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Autenticació", description = "Endpoints per autenticació")
public class AuthController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    /**
     * Constructor AuthController
     * 
     * @param jwtService            JwtService
     * @param authenticationService AuthenticationService
     */
    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    /**
     * Registrar nou usuari
     * permet fer un registre a un nou usuari.
     * 
     * @param registerUserDto RegistrarUsuariDto
     * @return ResponseEntity
     */
    @PostMapping("/registrar")
    @Operation(summary = "Registrar nou usuari", description = "permet fer un registre a un nou usuari.")
    public ResponseEntity<Usuari> register(@Valid @RequestBody RegistrarUsuariDto registerUserDto) {
        Usuari registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Iniciar sessió
     * Autentica un usuari i retorna un token JWT.
     * 
     * @param loginUserDto LoginUsuariDto
     * @return ResponseEntity
     */
    @PostMapping("/login")
    @Operation(summary = "Iniciar sessió", description = "Autentica un usuari i retorna un token JWT.")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginUsuariDto loginUserDto) {
        Usuari authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.extractExpiration(jwtToken));

        return ResponseEntity.ok(loginResponse);
    }

    /**
     * Solicitar recuperació de contrasenya
     * Genera un token para restablir la contrasenya.
     * 
     * @param request ForgotPasswordRequest
     * @return ResponseEntity
     */
    @PostMapping("/forgot-password")
    @Operation(summary = "Solicitar recuperació de contrasenya", description = "Genera un token para restablir la contrasenya.")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String resetToken = authenticationService.generateResetToken(request.getEmail());
        return ResponseEntity.ok("Token de recuperació generat: " + resetToken);
    }

    /**
     * Restablir contrasenya
     * Permite canviara la contrasenya amb un token vàlid.
     * 
     * @param request ResetPasswordRequest
     * @return ResponseEntity
     */
    @PostMapping("/reset-password")
    @Operation(summary = "Restablir contrasenya", description = "Permite canviara la contrasenya amb un token vàlid.")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        authenticationService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Contrasenya actualitzada correctament.");
    }
}
