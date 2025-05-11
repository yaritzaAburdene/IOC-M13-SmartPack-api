package com.smartpack.repositories;

import com.smartpack.models.Servei;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interficie ServeiRepository
 */
@Repository
public interface ServeiRepository extends JpaRepository<Servei, Long> {

    /**
     * findByActiveTrue
     * 
     * @return Servei List
     */
    List<Servei> findByActiveTrue();

    /**
     * findByIdAndActiveTrue
     * 
     * @param id Long
     * @return Servei
     */
    Optional<Servei> findByIdAndActiveTrue(Long id);

    /**
     * findByUsuariIdAndActiveTrue
     * 
     * @param usuariId Long
     * @return Servei List
     */
    List<Servei> findByUsuariIdAndActiveTrue(Long usuariId);

    /**
     * findByTransportistaIdAndActiveTrue
     * 
     * @param transportistaId Long
     * @return Servei List
     */
    List<Servei> findByTransportistaIdAndActiveTrue(Long transportistaId);

    /**
     * findByUsuariId
     * 
     * @param usuariId Long
     * @return Servei List
     */
    List<Servei> findByUsuariId(Long usuariId);

    /**
     * findByTransportistaId
     * 
     * @param transportistaId Long
     * @return Servei
     */
    List<Servei> findByTransportistaId(Long transportistaId);
}