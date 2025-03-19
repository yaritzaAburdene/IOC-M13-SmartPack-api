package com.smartpack.services;

import com.smartpack.dto.UserRequestDto;
import com.smartpack.dto.UserResponseDto;
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
        Usuari user = new Usuari();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNom(request.getNom());
        user.setCognom(request.getCognom());
        user.setTelefon(request.getTelefon());
        user.setAdreça(request.getAdreça());
        user.setObservacio(request.getObservacio());
        user.setActive(true);

        System.out.println("Intentando guardar usuario: " + user.getEmail());

        Usuari savedUser = usuariRepository.save(user);

        System.out.println("Usuario guardado con ID: " + savedUser.getId());

        return convertToResponseDto(savedUser);
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