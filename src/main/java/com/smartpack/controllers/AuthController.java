package com.smartpack.controllers;

import com.smartpack.dto.LoginUsuariDto;
import com.smartpack.dto.RegistrarUsuariDto;
import com.smartpack.models.Usuari;
import com.smartpack.services.AuthenticationService;
import com.smartpack.services.JwtService;
import com.smartpack.services.UsuariService;
import com.smartpack.utils.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
}
