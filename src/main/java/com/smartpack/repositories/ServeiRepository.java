package com.smartpack.repositories;

import com.smartpack.models.Servei;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interficie ServeiRepository
 */
@Repository
public interface ServeiRepository extends JpaRepository<Servei, Long> {
    List<Servei> findByUsuariId(Long usuariId);

    List<Servei> findByTransportistaId(Long transportistaId);
}