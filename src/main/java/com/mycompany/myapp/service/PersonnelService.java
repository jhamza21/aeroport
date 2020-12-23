package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Personnel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Personnel}.
 */
public interface PersonnelService {

    /**
     * Save a personnel.
     *
     * @param personnel the entity to save.
     * @return the persisted entity.
     */
    Personnel save(Personnel personnel);

    /**
     * Get all the personnel.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Personnel> findAll(Pageable pageable);


    /**
     * Get the "id" personnel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Personnel> findOne(Long id);

    /**
     * Delete the "id" personnel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
