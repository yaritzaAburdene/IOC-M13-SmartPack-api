package com.smartpack.services;

import java.util.List;
import java.util.stream.Collectors;

import com.smartpack.dto.PaquetRequestDto;
import com.smartpack.dto.PaquetResponseDto;
import com.smartpack.dto.ServeiRequestDto;
import com.smartpack.dto.ServeiResponseDto;
import com.smartpack.models.Estat;
import com.smartpack.models.Paquet;
import com.smartpack.models.Servei;
import com.smartpack.models.Transportista;
import com.smartpack.models.Usuari;
import com.smartpack.repositories.ServeiRepository;
import com.smartpack.repositories.TransportistaRepository;
import com.smartpack.repositories.UsuariRepository;
import com.smartpack.repositories.PaquetRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Classe ServeiService
 */
@Service
public class ServeiService {

    private final ServeiRepository serveiRepository;
    private final UsuariRepository usuariRepository;
    private final TransportistaRepository transportistaRepository;
    private final PaquetRepository paquetRepository;

    /**
     * Constructor ServeiService
     * 
     * @param serveiRepository        ServeiRepository
     * @param usuariRepository        UsuariRepository
     * @param transportistaRepository TransportistaRepository
     * @param paquetRepository        PaquetRepository
     */
    public ServeiService(ServeiRepository serveiRepository, UsuariRepository usuariRepository,
            TransportistaRepository transportistaRepository, PaquetRepository paquetRepository) {
        this.serveiRepository = serveiRepository;
        this.usuariRepository = usuariRepository;
        this.transportistaRepository = transportistaRepository;
        this.paquetRepository = paquetRepository;
    }

    public ServeiResponseDto crearServei(ServeiRequestDto request) {
        Usuari usuari = usuariRepository.findById(request.getUsuariId())
                .orElseThrow(() -> new EntityNotFoundException("Usuari no trobat"));

        Transportista transportista = null;
        if (request.getTransportistaId() != null) {
            transportista = transportistaRepository.findById(request.getTransportistaId())
                    .orElseThrow(() -> new EntityNotFoundException("Transportista no trobat"));
        }

        // Crear paquet
        Paquet paquet = new Paquet();
        paquet.setDetalls(request.getPaquet().getDetalls());
        paquet.setPes(request.getPaquet().getPes());
        paquet.setMida(request.getPaquet().getMida());
        paquet.setNomDestinatari(request.getPaquet().getNomDestinatari());
        paquet.setAdreçadestinatari(request.getPaquet().getAdreçadestinatari());
        paquet.setTelefondestinatari(request.getPaquet().getTelefondestinatari());
        paquet.setCodiqr(request.getPaquet().getCodiqr());

        paquetRepository.save(paquet);

        Servei servei = new Servei();
        servei.setUsuari(usuari);
        servei.setTransportista(transportista);
        servei.setPaquet(paquet);
        servei.setEstat(request.getEstat());

        serveiRepository.save(servei);

        return convertirADto(servei);
    }

    public ServeiResponseDto editarServei(Long id, ServeiRequestDto request) {
        Servei servei = serveiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servei no trobat"));

        Usuari usuari = usuariRepository.findById(request.getUsuariId())
                .orElseThrow(() -> new EntityNotFoundException("Usuari no trobat"));

        Transportista transportista = null;
        if (request.getTransportistaId() != null) {
            transportista = transportistaRepository.findById(request.getTransportistaId())
                    .orElseThrow(() -> new EntityNotFoundException("Transportista no trobat"));
        }

        Paquet paquet = servei.getPaquet(); // mantenim el paquet actual
        PaquetRequestDto paquetDto = request.getPaquet();
        if (paquetDto != null) {
            paquet.setDetalls(paquetDto.getDetalls());
            paquet.setPes(paquetDto.getPes());
            paquet.setMida(paquetDto.getMida());
            paquet.setNomDestinatari(paquetDto.getNomDestinatari());
            paquet.setAdreçadestinatari(paquetDto.getAdreçadestinatari());
            paquet.setTelefondestinatari(paquetDto.getTelefondestinatari());
            paquet.setCodiqr(paquetDto.getCodiqr());
            paquetRepository.save(paquet);
        }

        servei.setEstat(request.getEstat());
        servei.setUsuari(usuari);
        servei.setTransportista(transportista);
        servei.setPaquet(paquet);

        serveiRepository.save(servei);
        return convertirADto(servei);
    }

    public ServeiResponseDto getServeiById(Long id) {
        Servei servei = serveiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servei no trobat"));
        return convertirADto(servei);
    }

    public List<ServeiResponseDto> getServeisByUsuariId(Long usuariId) {
        List<Servei> serveis = serveiRepository.findByUsuariId(usuariId);
        return serveis.stream().map(this::convertirADto).collect(Collectors.toList());
    }

    public List<ServeiResponseDto> getServeisByTransportistaId(Long transportistaId) {
        List<Servei> serveis = serveiRepository.findByTransportistaId(transportistaId);
        return serveis.stream().map(this::convertirADto).collect(Collectors.toList());
    }

    private ServeiResponseDto convertirADto(Servei servei) {
        ServeiResponseDto dto = new ServeiResponseDto();
        dto.setId(servei.getId());
        dto.setEstat(servei.getEstat());
        dto.setUsuariId(servei.getUsuari().getId());
        dto.setTransportistaId(servei.getTransportista() != null ? servei.getTransportista().getId() : null);
        dto.setPaquet(convertirPaquetADto(servei.getPaquet()));
        return dto;
    }

    public ServeiResponseDto canviarEstatServei(Long serveiId, Estat nouEstat) {
        Servei servei = serveiRepository.findById(serveiId)
                .orElseThrow(() -> new EntityNotFoundException("Servei no trobat"));

        servei.setEstat(nouEstat);
        serveiRepository.save(servei);
        return convertirADto(servei);
    }

    private PaquetResponseDto convertirPaquetADto(Paquet paquet) {
        PaquetResponseDto dto = new PaquetResponseDto();
        dto.setId(paquet.getId());
        dto.setDetalls(paquet.getDetalls());
        dto.setPes(paquet.getPes());
        dto.setMida(paquet.getMida());
        dto.setNomDestinatari(paquet.getNomDestinatari());
        dto.setAdreçadestinatari(paquet.getAdreçadestinatari());
        dto.setTelefondestinatari(paquet.getTelefondestinatari());
        dto.setCodiqr(paquet.getCodiqr());
        return dto;
    }
}
