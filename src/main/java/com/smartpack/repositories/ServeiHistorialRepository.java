package com.smartpack.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.smartpack.models.ServeiHistorial;

/**
 * Interficie ServeiHistorialRepository
 */
@Repository
public interface ServeiHistorialRepository extends JpaRepository<ServeiHistorial, Long> {

    /**
     * findByServeiIdOrderByDataCanviAsc
     * 
     * @param serveiId Long
     * @return ServeiHistorial List
     */
    List<ServeiHistorial> findByServeiIdOrderByDataCanviAsc(Long serveiId);
}
