package com.smartpack.repositories;

import com.smartpack.models.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interficie VehicleRepository
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}