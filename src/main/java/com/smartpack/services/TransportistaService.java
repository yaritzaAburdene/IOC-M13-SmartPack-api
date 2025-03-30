package com.smartpack.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartpack.dto.TransportistaRequestDto;
import com.smartpack.dto.TransportistaResponseDto;
import com.smartpack.models.Rol;
import com.smartpack.models.Transportista;
import com.smartpack.models.Usuari;
import com.smartpack.repositories.UsuariRepository;
import com.smartpack.repositories.TransportistaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TransportistaService {

    private final TransportistaRepository transportistaRepository;
    private final UsuariRepository usuariRepository;

    /**
     * Constructor TransportistaService
     * 
     * @param transportistaRepository TransportistaRepository
     * @param usuariRepository        UsuariRepository
     */
    public TransportistaService(TransportistaRepository transportistaRepository, UsuariRepository usuariRepository) {
        this.transportistaRepository = transportistaRepository;
        this.usuariRepository = usuariRepository;
    }

    public TransportistaResponseDto crearTransportista(TransportistaRequestDto request) {
        System.out.println("usuariId: " + request.getUsuariId());
        System.out.println("llicencia: " + request.getLlicencia());
        if (request.getUsuariId() == null) {
            throw new IllegalArgumentException("usuariId és null");
        }

        Usuari usuari = usuariRepository.findById(request.getUsuariId())
                .orElseThrow(() -> new EntityNotFoundException("Usuari no trobat"));

        if (usuari.getRole() != Rol.ROLE_DELIVERYMAN) {
            throw new RuntimeException("Aquest usuari no te el rol de transportista");
        }

        Transportista transportista = new Transportista();
        transportista.setUsuari(usuari);
        transportista.setLlicencia(request.getLlicencia());

        transportistaRepository.save(transportista);
        return convertirADto(transportista);
    }

    public TransportistaResponseDto editarTransportista(Long id, TransportistaRequestDto request) {
        Transportista transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transportista no trobat"));

        transportista.setLlicencia(request.getLlicencia());
        transportistaRepository.save(transportista);
        return convertirADto(transportista);
    }

    public TransportistaResponseDto deshabilitarTransportista(Long id) {
        Transportista transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transportista no trobat"));

        Usuari usuari = transportista.getUsuari();
        usuari.setActive(false);
        usuariRepository.save(usuari);
        return convertirADto(transportista);
    }

    public TransportistaResponseDto getTransportista(Long id) {
        Transportista transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transportista no trobat"));

        return convertirADto(transportista);
    }

    private TransportistaResponseDto convertirADto(Transportista transportista) {
        TransportistaResponseDto dto = new TransportistaResponseDto();
        dto.setId(transportista.getId());
        dto.setUsuariId(transportista.getUsuari().getId());
        dto.setUsuariEmail(transportista.getUsuari().getEmail());
        dto.setLlicencia(transportista.getLlicencia());
        return dto;
    }
}
