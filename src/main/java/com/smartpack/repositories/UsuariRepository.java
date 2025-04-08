package com.smartpack.repositories;

import com.smartpack.models.Usuari;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interficie UsuariRepository
 */
@Repository
public interface UsuariRepository extends JpaRepository<Usuari, Long> {

    /**
     * findByEmail
     * Troba usuari per Email
     * 
     * @param email String
     * @return Usuari
     */
    Optional<Usuari> findByEmail(String email);

    /**
     * findByResetToken
     * Troba Usuari per reset token
     * 
     * @param resetToken String
     * @return Usuari
     */
    Optional<Usuari> findByResetToken(String resetToken);

    List<Usuari> findByEmpresaId(Long empresaId);

}