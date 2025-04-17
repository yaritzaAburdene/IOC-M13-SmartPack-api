package com.smartpack.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartpack.models.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    List<Factura> findByUsuariId(Long usuariId);

    List<Factura> findByUsuariIdAndDataBetween(Long usuariId, Date desde, Date fins);
}
