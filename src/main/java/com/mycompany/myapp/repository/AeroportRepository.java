package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Aeroport;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Aeroport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AeroportRepository extends JpaRepository<Aeroport, Long> {
}
