package com.smartpack.services;

import com.smartpack.models.Usuari;
import com.smartpack.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UsuariService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Optional<Usuari> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuari registrarUsuario(Usuari usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Encripta la contraseña
        return usuarioRepository.save(usuario);
    }
}