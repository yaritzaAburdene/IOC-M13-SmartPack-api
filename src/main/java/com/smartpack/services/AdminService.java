package com.smartpack.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.smartpack.dto.AdminDataResponse;
import com.smartpack.dto.TransportistaResponseDto;
import com.smartpack.dto.UserResponseDto;
import com.smartpack.dto.VehicleDto;
import com.smartpack.models.Transportista;
import com.smartpack.models.Usuari;
import com.smartpack.models.Vehicle;
import com.smartpack.repositories.UsuariRepository;
import com.smartpack.repositories.VehicleRepository;
import com.smartpack.repositories.TransportistaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UsuariRepository usuariRepository;
    private final TransportistaRepository transportistaRepository;
    private final VehicleRepository vehicleRepository;

    public AdminDataResponse getResumGeneral(Long usuariId, Long empresaId) {
        AdminDataResponse response = new AdminDataResponse();

        // usuarios
        List<Usuari> usuaris;
        if (usuariId != null) {
            usuariRepository.findById(usuariId).ifPresent(u -> response.setUsuaris(List.of(convertirUsuariADto(u))));
        } else if (empresaId != null) {
            usuaris = usuariRepository.findByEmpresaId(empresaId);
            response.setUsuaris(usuaris.stream().map(this::convertirUsuariADto).collect(Collectors.toList()));
        } else {
            usuaris = usuariRepository.findAll();
            response.setUsuaris(usuaris.stream().map(this::convertirUsuariADto).collect(Collectors.toList()));
        }

        // transportistas
        List<Transportista> transportistes;
        if (usuariId != null) {
            transportistaRepository.findByUsuariId(usuariId)
                    .ifPresent(t -> response.setTransportistes(List.of(convertirTransportistaADto(t))));
        } else if (empresaId != null) {
            transportistes = transportistaRepository.findByUsuari_Empresa_Id(empresaId);
            response.setTransportistes(
                    transportistes.stream().map(this::convertirTransportistaADto).collect(Collectors.toList()));
        } else {
            transportistes = transportistaRepository.findAll();
            response.setTransportistes(
                    transportistes.stream().map(this::convertirTransportistaADto).collect(Collectors.toList()));
        }

        // vehicles
        List<Vehicle> vehicles = vehicleRepository.findAll();
        response.setVehicles(vehicles.stream().map(this::convertirVehicleADto).collect(Collectors.toList()));

        return response;
    }

    private UserResponseDto convertirUsuariADto(Usuari usuari) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(usuari.getId());
        dto.setEmail(usuari.getEmail());
        dto.setNom(usuari.getNom());
        dto.setCognom(usuari.getCognom());
        dto.setTelefon(usuari.getTelefon());
        return dto;
    }

    private TransportistaResponseDto convertirTransportistaADto(Transportista t) {
        TransportistaResponseDto dto = new TransportistaResponseDto();
        dto.setId(t.getId());
        dto.setUsuariId(t.getUsuari().getId());
        dto.setUsuariEmail(t.getUsuari().getEmail());
        dto.setLlicencia(t.getLlicencia());
        if (t.getVehicle() != null) {
            dto.setVehicle(convertirVehicleADto(t.getVehicle()));
        }
        return dto;
    }

    private VehicleDto convertirVehicleADto(Vehicle v) {
        VehicleDto dto = new VehicleDto();
        dto.setId(v.getId());
        dto.setMatricula(v.getMatricula());
        dto.setModel(v.getModel());
        dto.setMarca(v.getMarca());
        return dto;
    }
}
