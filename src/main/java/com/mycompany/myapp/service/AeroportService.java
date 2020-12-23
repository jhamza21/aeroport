package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Aeroport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Aeroport}.
 */
public interface AeroportService {

    /**
     * Save a aeroport.
     *
     * @param aeroport the entity to save.
     * @return the persisted entity.
     */
    Aeroport save(Aeroport aeroport);

    /**
     * Get all the aeroports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Aeroport> findAll(Pageable pageable);


    /**
     * Get the "id" aeroport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Aeroport> findOne(Long id);

    /**
     * Delete the "id" aeroport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
