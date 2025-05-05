package com.smartpack.repositories;

import com.smartpack.models.Transportista;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe TransportistaRepository
 */
@Repository
public interface TransportistaRepository extends JpaRepository<Transportista, Long> {

    /**
     * findByActiveTrue
     * 
     * @return Transportista List
     */
    List<Transportista> findByActiveTrue();

    /**
     * findByUsuariId
     * 
     * @param usuariId Long
     * @return Transportista Optional
     */
    Optional<Transportista> findByUsuariId(Long usuariId);

    /**
     * findByUsuari_Empresa_Id
     * 
     * @param empresaId Long
     * @return Transportista List
     */
    List<Transportista> findByUsuari_Empresa_Id(Long empresaId);

}
