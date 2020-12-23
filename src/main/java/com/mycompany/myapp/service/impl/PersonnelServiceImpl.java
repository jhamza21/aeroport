package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PersonnelService;
import com.mycompany.myapp.domain.Personnel;
import com.mycompany.myapp.repository.PersonnelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Personnel}.
 */
@Service
@Transactional
public class PersonnelServiceImpl implements PersonnelService {

    private final Logger log = LoggerFactory.getLogger(PersonnelServiceImpl.class);

    private final PersonnelRepository personnelRepository;

    public PersonnelServiceImpl(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;
    }

    @Override
    public Personnel save(Personnel personnel) {
        log.debug("Request to save Personnel : {}", personnel);
        return personnelRepository.save(personnel);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Personnel> findAll(Pageable pageable) {
        log.debug("Request to get all Personnel");
        return personnelRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Personnel> findOne(Long id) {
        log.debug("Request to get Personnel : {}", id);
        return personnelRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personnel : {}", id);
        personnelRepository.deleteById(id);
    }
}
