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

/**
 * Classe UsuariService
 */
@Service
public class UsuariService {
    @Autowired
    private UsuariRepository usuariRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor UsuariService
     * 
     * @param usuariRepository UsuariRepository
     * @param passwordEncoder  PasswordEncoder
     */
    public UsuariService(UsuariRepository usuariRepository, PasswordEncoder passwordEncoder) {
        this.usuariRepository = usuariRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * All users
     * Retorna tots els usuaris en llistat
     * 
     * @return Usuari llistat
     */
    public List<Usuari> allUsers() {
        // nou llistat
        List<Usuari> users = new ArrayList<>();

        usuariRepository.findAll().forEach(users::add); // afegeix elements a nou arrayList

        return users;
    }

    /**
     * Get all User
     * retorn tots els usuaris
     * 
     * @return UserResponseDto llistat
     */
    public List<UserResponseDto> getAllUsers() {
        // recupera el llistat d'usuari amb el format: convertToResponseDto
        return usuariRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Get User By Id
     * Obté l'usuari per Id
     * 
     * @param id long
     * @return UserResponseDto
     */
    public UserResponseDto getUserById(Long id) {
        Usuari user = usuariRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuari no trobat"));
        return convertToResponseDto(user);
    }

    /**
     * Get Usuari By Email
     * 
     * @param email String
     * @return UserResponseDto
     */
    public UserResponseDto getUserByEmail(String email) {
        Usuari user = usuariRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuari no trobat"));

        return convertToResponseDto(user);
    }

    /**
     * Create User
     * crear un usuari
     * 
     * @param request UserRequestDto
     * @return convertToResponseDto
     */
    public UserResponseDto createUser(UserRequestDto request) {
        Usuari usuari = new Usuari();
        usuari.setEmail(request.getEmail());
        usuari.setPassword(passwordEncoder.encode(request.getPassword()));
        usuari.setDni(request.getDni());
        usuari.setNom(request.getNom());
        usuari.setCognom(request.getCognom());
        usuari.setTelefon(request.getTelefon());
        usuari.setObservacio(request.getObservacio());
        usuari.setSecret(request.getSecret());
        usuari.setActive(true);

        if (request.getAdreça() == null || request.getAdreça().isEmpty()) {
            usuari.setAdreça("No especificat"); // valor per defecte
        } else {
            usuari.setAdreça(request.getAdreça());
        }

        if (request.getRole() == null || request.getRole().isEmpty()) {
            usuari.setRole(Rol.ROLE_USER); // valor per defecte
        } else {
            try {
                String rawRole = request.getRole().toUpperCase();
                if (!rawRole.startsWith("ROLE_")) {
                    rawRole = "ROLE_" + rawRole;
                }
                usuari.setRole(Rol.valueOf(rawRole));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("El rol proporcionat no es vàlid: " + request.getRole());
            }
        }
        Usuari savedUser = usuariRepository.save(usuari);
        return convertToResponseDto(savedUser);
    }

    /**
     * Update User
     * actualitzar usuari
     * 
     * @param id      long
     * @param request UserRequestDto
     * @return UserResponseDto
     */
    public UserResponseDto updateUser(Long id, UserRequestDto request) {
        Usuari user = usuariRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuari no trobat"));

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getDni() != null) {
            user.setDni(request.getDni());
        }
        if (request.getNom() != null) {
            user.setNom(request.getNom());
        }
        if (request.getCognom() != null) {
            user.setCognom(request.getCognom());
        }
        if (request.getTelefon() != null) {
            user.setTelefon(request.getTelefon());
        }
        if (request.getAdreça() != null) {
            user.setAdreça(request.getAdreça());
        }

        if (request.getObservacio() != null) {
            user.setObservacio(request.getObservacio());
        }

        if (request.getSecret() != null) {
            user.setSecret(request.getSecret());
        }

        if (request.getRole() != null) {
            String rawRole = request.getRole().toUpperCase();
            if (!rawRole.startsWith("ROLE_")) {
                rawRole = "ROLE_" + rawRole;
            }
            user.setRole(Rol.valueOf(rawRole));
        }

        Usuari updatedUser = usuariRepository.save(user);
        return convertToResponseDto(updatedUser);
    }

    /**
     * Desactivar Usuari
     * 
     * @param id long
     */
    public void deactivateUser(Long id) {
        Usuari user = usuariRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuari no trobat"));
        user.setActive(false);
        usuariRepository.save(user);
    }

    /**
     * Convert To Response DTO
     * 
     * @param user Usuari
     * @return UserResponseDto
     */
    private UserResponseDto convertToResponseDto(Usuari user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setDni(user.getDni());
        dto.setNom(user.getNom());
        dto.setCognom(user.getCognom());
        dto.setTelefon(user.getTelefon());
        dto.setAdreça(user.getAdreça());
        dto.setObservacio(user.getObservacio());
        dto.setEmpresaId(user.getEmpresa() != null ? user.getEmpresa().getId() : null);
        return dto;
    }
}