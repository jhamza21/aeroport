package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.AeroportService;
import com.mycompany.myapp.domain.Aeroport;
import com.mycompany.myapp.repository.AeroportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Aeroport}.
 */
@Service
@Transactional
public class AeroportServiceImpl implements AeroportService {

    private final Logger log = LoggerFactory.getLogger(AeroportServiceImpl.class);

    private final AeroportRepository aeroportRepository;

    public AeroportServiceImpl(AeroportRepository aeroportRepository) {
        this.aeroportRepository = aeroportRepository;
    }

    @Override
    public Aeroport save(Aeroport aeroport) {
        log.debug("Request to save Aeroport : {}", aeroport);
        return aeroportRepository.save(aeroport);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Aeroport> findAll(Pageable pageable) {
        log.debug("Request to get all Aeroports");
        return aeroportRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Aeroport> findOne(Long id) {
        log.debug("Request to get Aeroport : {}", id);
        return aeroportRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Aeroport : {}", id);
        aeroportRepository.deleteById(id);
    }
}
