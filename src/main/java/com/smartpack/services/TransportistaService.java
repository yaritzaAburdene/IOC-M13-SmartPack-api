package com.smartpack.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.smartpack.dto.TransportistaRequestDto;
import com.smartpack.dto.TransportistaResponseDto;
import com.smartpack.dto.VehicleDto;
import com.smartpack.models.Rol;
import com.smartpack.models.Transportista;
import com.smartpack.models.Usuari;
import com.smartpack.models.Vehicle;
import com.smartpack.repositories.UsuariRepository;
import com.smartpack.repositories.VehicleRepository;
import com.smartpack.repositories.EmpresaRepository;
import com.smartpack.repositories.TransportistaRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Classe TransportistaService
 */
@Service
public class TransportistaService {

    private final TransportistaRepository transportistaRepository;
    private final UsuariRepository usuariRepository;
    private final EmpresaRepository empresaRepository;
    private final VehicleRepository vehicleRepository;

    /**
     * Constructor TransportistaService
     * 
     * @param transportistaRepository TransportistaRepository
     * @param usuariRepository        UsuariRepository
     * @param empresaRepository       empresaRepository
     * @param vehicleRepository       VehicleRepository
     */
    public TransportistaService(TransportistaRepository transportistaRepository,
            UsuariRepository usuariRepository, EmpresaRepository empresaRepository,
            VehicleRepository vehicleRepository) {
        this.transportistaRepository = transportistaRepository;
        this.usuariRepository = usuariRepository;
        this.empresaRepository = empresaRepository;
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * crearTransportista
     * 
     * @param request TransportistaRequestDto
     * @return TransportistaResponseDto
     */
    public TransportistaResponseDto crearTransportista(TransportistaRequestDto request) {
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

    /**
     * editarTransportista
     * 
     * @param id      Long
     * @param request TransportistaRequestDto
     * @return TransportistaResponseDto
     */
    public TransportistaResponseDto editarTransportista(Long id, TransportistaRequestDto request) {

        if (request.getLlicencia() == null || request.getLlicencia().isBlank()) {
            throw new IllegalArgumentException("La llicencia és obligatòria.");
        }

        Transportista transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transportista no trobat"));

        transportista.setLlicencia(request.getLlicencia());
        transportistaRepository.save(transportista);
        return convertirADto(transportista);
    }

    /**
     * deshabilitarTransportista
     * 
     * @param id Long
     * @return TransportistaResponseDto
     */
    public TransportistaResponseDto deshabilitarTransportista(Long id) {
        Transportista transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transportista no trobat"));

        Usuari usuari = transportista.getUsuari();
        usuari.setActive(false);
        usuariRepository.save(usuari);
        return convertirADto(transportista);
    }

    /**
     * getTransportista
     * 
     * @param id Long
     * @return TransportistaResponseDto
     */
    public TransportistaResponseDto getTransportista(Long id) {
        Transportista transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transportista no trobat"));

        return convertirADto(transportista);
    }

    /**
     * getAllTransportistes
     * 
     * @return TransportistaResponseDto
     */
    public List<TransportistaResponseDto> getAllTransportistes() {
        return transportistaRepository.findByActiveTrue().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    /**
     * getTransportistaByUsuariId
     * 
     * @param usuariId Long
     * @return TransportistaResponseDto
     */
    public TransportistaResponseDto getTransportistaByUsuariId(Long usuariId) {
        Transportista transportista = transportistaRepository.findByUsuariId(usuariId)
                .orElseThrow(() -> new EntityNotFoundException("Transportista no trobat per aquest usuari"));

        return convertirADto(transportista);
    }

    /**
     * getTransportistesByEmpresa
     * 
     * @param empresaId Long
     * @return TransportistaResponseDto List
     */
    public List<TransportistaResponseDto> getTransportistesByEmpresa(Long empresaId) {
        this.empresaRepository.findById(empresaId)
                .orElseThrow(() -> new EntityNotFoundException("Empresa no trobada"));

        List<Transportista> transportistes = transportistaRepository.findByUsuari_Empresa_Id(empresaId);

        return transportistes.stream()
                .map(this::convertirADto)
                .toList();
    }

    /**
     * Desactivar Transportista
     * 
     * @param id long
     */
    public void deactivateTransportista(Long id) {
        Transportista transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transportista no trobat"));
        transportista.setActive(false);
        transportistaRepository.save(transportista);
    }

    /**
     * assignVehicleToTransportista
     * 
     * @param transportistaId Long
     * @param vehicleId       Long
     */
    public void assignVehicleToTransportista(Long transportistaId, Long vehicleId) {
        Transportista transportista = transportistaRepository.findById(transportistaId)
                .orElseThrow(() -> new EntityNotFoundException("Transportista no trobat"));

        Vehicle vehicle = this.vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle no trobat"));

        transportista.setVehicle(vehicle);
        transportistaRepository.save(transportista);
    }

    /**
     * desasignarVehicle
     * 
     * @param transportistaId Long
     */
    public void desasignarVehicle(Long transportistaId) {
        Transportista transportista = transportistaRepository.findById(transportistaId)
                .orElseThrow(() -> new EntityNotFoundException("Transportista no trobat"));

        if (transportista.getVehicle() == null) {
            throw new IllegalStateException("Aquest transportista no té cap vehicle assignat");
        }

        transportista.setVehicle(null);
        transportistaRepository.save(transportista);
    }

    /**
     * convertirADto
     * 
     * @param transportista Transportista
     * @return TransportistaResponseDto
     */
    private TransportistaResponseDto convertirADto(Transportista transportista) {
        TransportistaResponseDto dto = new TransportistaResponseDto();
        dto.setId(transportista.getId());
        dto.setUsuariId(transportista.getUsuari().getId());
        dto.setUsuariEmail(transportista.getUsuari().getEmail());
        dto.setLlicencia(transportista.getLlicencia());
        if (transportista.getVehicle() != null) {
            VehicleDto vehicleDto = new VehicleDto();
            vehicleDto.setId(transportista.getVehicle().getId());
            vehicleDto.setMarca(transportista.getVehicle().getMarca());
            vehicleDto.setModel(transportista.getVehicle().getModel());
            vehicleDto.setMatricula(transportista.getVehicle().getMatricula());

            dto.setVehicle(vehicleDto);
        }
        return dto;
    }
}
