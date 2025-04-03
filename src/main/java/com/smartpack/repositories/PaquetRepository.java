package com.smartpack.repositories;

import com.smartpack.models.Paquet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interficie PaquetRepository
 */
@Repository
public interface PaquetRepository extends JpaRepository<Paquet, Long> {

}