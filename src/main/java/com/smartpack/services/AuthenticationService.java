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

@Service
public class AuthenticationService {
    private final UsuariRepository usuariRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UsuariRepository usuariRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.usuariRepository = usuariRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuari signup(RegistrarUsuariDto request) {
        Usuari usuari = new Usuari();
        usuari.setEmail(request.getEmail());  
        usuari.setPassword(passwordEncoder.encode(request.getPassword())); 
        
         if (request.getRole() == null || request.getRole().isEmpty()) {
            usuari.setRole(Rol.USER); // Valor por defecto
        } else {
            try {
                usuari.setRole(Rol.valueOf(request.getRole().toUpperCase())); // Convierte String a Enum
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
                        input.getPassword()
                )
        );

        return usuariRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
