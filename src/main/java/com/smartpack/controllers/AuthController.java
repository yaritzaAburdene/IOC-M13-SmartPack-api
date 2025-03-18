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
import io.swagger.v3.oas.annotations.Operation;



@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Autenticació", description = "Endpoints per autenticació")
public class AuthController {

    private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Usuari> register(@RequestBody RegistrarUsuariDto registerUserDto) {
        Usuari registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuari i retorna un token JWT.")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUsuariDto loginUserDto) {
        Usuari authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);  
        loginResponse.setExpiresIn(jwtService.extractExpiration(jwtToken));

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Solicitar recuperación de contraseña", description = "Genera un token para restablecer la contraseña.")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String resetToken = authenticationService.generateResetToken(request.getEmail());
        return ResponseEntity.ok("Token de recuperación generado: " + resetToken);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Restablecer contraseña", description = "Permite cambiar la contraseña con un token válido.")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        authenticationService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }
}
