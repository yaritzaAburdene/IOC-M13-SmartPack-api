package com.smartpack.services;

import com.smartpack.dto.UserRequestDto;
import com.smartpack.dto.UserResponseDto;
import com.smartpack.models.Rol;
import com.smartpack.models.Usuari;
import com.smartpack.repositories.UsuariRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuariService {
    @Autowired
    private UsuariRepository usuariRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UsuariService(UsuariRepository usuariRepository, PasswordEncoder passwordEncoder) {
        this.usuariRepository = usuariRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuari> allUsers() {
        List<Usuari> users = new ArrayList<>();

        usuariRepository.findAll().forEach(users::add);

        return users;
    }

    public List<UserResponseDto> getAllUsers() {
        return usuariRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id) {
        Usuari user = usuariRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuari no trobat"));
        return convertToResponseDto(user);
    }

    public UserResponseDto createUser(UserRequestDto request) {
        Usuari usuari = new Usuari();
        usuari.setEmail(request.getEmail());
        usuari.setPassword(passwordEncoder.encode(request.getPassword()));
        usuari.setNom(request.getNom());
        usuari.setCognom(request.getCognom());
        usuari.setTelefon(request.getTelefon());
        usuari.setObservacio(request.getObservacio());
        usuari.setActive(true);

        if (request.getAdreça() == null || request.getAdreça().isEmpty()) {
            usuari.setAdreça("No especificat"); // Valor por defecto
        } else {
            usuari.setAdreça(request.getAdreça());
        }

        if (request.getRole() == null || request.getRole().isEmpty()) {
            usuari.setRole(Rol.ROLE_USER); // Valor por defecto
        } else {
            try {
                usuari.setRole(Rol.valueOf(request.getRole().replace("ROLE_", "").toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("El rol proporcionado no es válido: " + request.getRole());
            }
        }
        Usuari savedUser = usuariRepository.save(usuari);
        return convertToResponseDto(savedUser);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto request) {
        Usuari user = usuariRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuari no trobat"));

        if (request.getEmail() != null)
            user.setEmail(request.getEmail());
        if (request.getPassword() != null)
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getNom() != null)
            user.setNom(request.getNom());
        if (request.getCognom() != null)
            user.setCognom(request.getCognom());
        if (request.getTelefon() != null)
            user.setTelefon(request.getTelefon());
        if (request.getAdreça() != null)
            user.setAdreça(request.getAdreça());
        if (request.getObservacio() != null)
            user.setObservacio(request.getObservacio());

        Usuari updatedUser = usuariRepository.save(user);
        return convertToResponseDto(updatedUser);
    }

    public void deactivateUser(Long id) {
        Usuari user = usuariRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuari no trobat"));
        user.setActive(false);
        usuariRepository.save(user);
    }

    private UserResponseDto convertToResponseDto(Usuari user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setNom(user.getNom());
        dto.setCognom(user.getCognom());
        dto.setTelefon(user.getTelefon());
        dto.setAdreça(user.getAdreça());
        dto.setObservacio(user.getObservacio());
        return dto;
    }
}