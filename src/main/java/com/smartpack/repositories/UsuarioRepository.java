package com.smartpack.repositories;

import com.smartpack.models.Usuari;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuari, Long> {
    Optional<Usuari> findByEmail(String email);
}