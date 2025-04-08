package com.smartpack.repositories;

import com.smartpack.models.Vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interficie VehicleRepository
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByTransportistaUsuariId(Long usuariId);

    List<Vehicle> findByTransportistaUsuariEmpresaId(Long empresaId);
}