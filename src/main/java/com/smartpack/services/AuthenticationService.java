package com.smartpack.services;

import com.smartpack.dto.LoginUsuariDto;
import com.smartpack.dto.RegistrarUsuariDto;
import com.smartpack.models.Rol;
import com.smartpack.models.Usuari;
import com.smartpack.repositories.UsuariRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final UsuariRepository usuariRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UsuariRepository usuariRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.usuariRepository = usuariRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuari signup(RegistrarUsuariDto request) {
        Usuari usuari = new Usuari();
        usuari.setEmail(request.getEmail());
        usuari.setPassword(passwordEncoder.encode(request.getPassword()));
        usuari.setNom(request.getNom());
        usuari.setCognom(request.getCognom());
        usuari.setTelefon(request.getTelefon());

        if (request.getAdreça() == null || request.getAdreça().isEmpty()) {
            usuari.setAdreça("No especificat"); // Valor por defecto
        } else {
            usuari.setAdreça(request.getAdreça());
        }

        if (request.getRole() == null || request.getRole().isEmpty()) {
            usuari.setRole(Rol.ROLE_USER); // Valor por defecto
        } else {
            try {
                usuari.setRole(Rol.valueOf(request.getRole().replace("ROLE_", "").toUpperCase())); // Convierte String a
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("El rol proporcionado no es válido: " + request.getRole());
            }
        }

        return usuariRepository.save(usuari);
    }

    public Usuari authenticate(LoginUsuariDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));

        return usuariRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

    public String generateResetToken(String email) {
        Optional<Usuari> userOpt = usuariRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            Usuari user = userOpt.get();
            String token = UUID.randomUUID().toString();

            // Guardar el token en la base de datos
            user.setResetToken(token);
            usuariRepository.save(user);

            return token; // Ahora el token se devuelve en la respuesta
        } else {
            throw new RuntimeException("Email no registrado.");
        }
    }

    public void resetPassword(String token, String newPassword) {
        Optional<Usuari> userOpt = usuariRepository.findByResetToken(token);

        if (userOpt.isPresent()) {
            Usuari user = userOpt.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null); // Elimina el token tras el uso
            usuariRepository.save(user);
        } else {
            throw new RuntimeException("Token inválido o expirado.");
        }
    }
}
