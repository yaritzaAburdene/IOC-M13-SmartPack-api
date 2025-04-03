package com.smartpack.services;

import java.util.List;

import com.smartpack.dto.VehicleDto;
import com.smartpack.models.Vehicle;
import com.smartpack.repositories.VehicleRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Classe VehicleService
 */
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    /**
     * Constructor VehicleService
     * 
     * @param vehicleRepository VehicleRepository
     */
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * crearVehicle
     * 
     * @param dto VehicleDto
     * @return VehicleDto
     */
    public VehicleDto crearVehicle(VehicleDto dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setMarca(dto.getMarca());
        vehicle.setModel(dto.getModel());
        vehicle.setMatricula(dto.getMatricula());
        return convertirADto(vehicleRepository.save(vehicle));
    }

    /**
     * editarVehicle
     * 
     * @param id  Long
     * @param dto VehicleDto
     * @return VehicleDto
     */
    public VehicleDto editarVehicle(Long id, VehicleDto dto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle no trobat"));
        vehicle.setMarca(dto.getMarca());
        vehicle.setModel(dto.getModel());
        vehicle.setMatricula(dto.getMatricula());
        return convertirADto(vehicleRepository.save(vehicle));
    }

    /**
     * obtenirVehicle
     * 
     * @param id Long
     * @return VehicleDto
     */
    public VehicleDto obtenirVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle no trobat"));
        return convertirADto(vehicle);
    }

    /**
     * llistarVehicles
     * 
     * @return VehicleDto
     */
    public List<VehicleDto> llistarVehicles() {
        return vehicleRepository.findAll().stream()
                .map(this::convertirADto)
                .toList();
    }

    /**
     * Desactivar Vehicle
     * 
     * @param id long
     */
    public void desactivarVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle no trobat"));
        vehicle.setActive(false);
        vehicleRepository.save(vehicle);
    }

    /**
     * convertirADto
     * 
     * @param vehicle Vehicle
     * @return VehicleDto
     */
    private VehicleDto convertirADto(Vehicle vehicle) {
        VehicleDto dto = new VehicleDto();
        dto.setId(vehicle.getId());
        dto.setMarca(vehicle.getMarca());
        dto.setModel(vehicle.getModel());
        dto.setMatricula(vehicle.getMatricula());
        return dto;
    }
}
