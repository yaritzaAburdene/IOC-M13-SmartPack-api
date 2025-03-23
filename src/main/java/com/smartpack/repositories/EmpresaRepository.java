package com.smartpack.repositories;

import com.smartpack.models.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interficie EmpresaRepository
 */
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}