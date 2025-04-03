package com.smartpack.repositories;

import com.smartpack.models.Transportista;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportistaRepository extends JpaRepository<Transportista, Long> {

    Optional<Transportista> findByUsuariId(Long usuariId);

}
