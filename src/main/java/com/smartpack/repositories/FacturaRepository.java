package com.smartpack.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartpack.models.Factura;

/**
 * Interficie FacturaRepository
 */
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    /**
     * find By UsuariId
     * 
     * @param usuariId Long
     * @return Factura List
     */
    List<Factura> findByUsuariId(Long usuariId);

    /**
     * find By UsuariId And Data Between
     * 
     * @param usuariId Long
     * @param desde    Date
     * @param fins     Date
     * @return Factura List
     */
    List<Factura> findByUsuariIdAndDataBetween(Long usuariId, Date desde, Date fins);

    /**
     * find By ServeiId
     * 
     * @param serveiId Long
     * @return Factura Optional
     */
    Optional<Factura> findByServeiId(Long serveiId);

    /**
     * exists By ServeiId
     * 
     * @param serveiId Long
     * @return boolean
     */
    boolean existsByServeiId(Long serveiId);

}
