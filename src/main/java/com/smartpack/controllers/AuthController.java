package com.smartpack.controllers;

import com.smartpack.models.Usuari;
import com.smartpack.services.UsuariService;
import com.smartpack.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuariService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Usuari register(@RequestBody Usuari usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String email, @RequestParam String password) {
        Optional<Usuari> usuario = usuarioService.obtenerPorEmail(email);

        if (usuario.isPresent() && passwordEncoder.matches(password, usuario.get().getPassword())) {
            String token = jwtUtil.generateToken(email);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;
        }
        throw new RuntimeException("Credenciales incorrectas");
    }
}
