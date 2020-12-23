package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Aeroport;
import com.mycompany.myapp.service.AeroportService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Aeroport}.
 */
@RestController
@RequestMapping("/api")
public class AeroportResource {

    private final Logger log = LoggerFactory.getLogger(AeroportResource.class);

    private static final String ENTITY_NAME = "aeroport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AeroportService aeroportService;

    public AeroportResource(AeroportService aeroportService) {
        this.aeroportService = aeroportService;
    }

    /**
     * {@code POST  /aeroports} : Create a new aeroport.
     *
     * @param aeroport the aeroport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aeroport, or with status {@code 400 (Bad Request)} if the aeroport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aeroports")
    public ResponseEntity<Aeroport> createAeroport(@Valid @RequestBody Aeroport aeroport) throws URISyntaxException {
        log.debug("REST request to save Aeroport : {}", aeroport);
        if (aeroport.getId() != null) {
            throw new BadRequestAlertException("A new aeroport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Aeroport result = aeroportService.save(aeroport);
        return ResponseEntity.created(new URI("/api/aeroports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aeroports} : Updates an existing aeroport.
     *
     * @param aeroport the aeroport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aeroport,
     * or with status {@code 400 (Bad Request)} if the aeroport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aeroport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aeroports")
    public ResponseEntity<Aeroport> updateAeroport(@Valid @RequestBody Aeroport aeroport) throws URISyntaxException {
        log.debug("REST request to update Aeroport : {}", aeroport);
        if (aeroport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Aeroport result = aeroportService.save(aeroport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aeroport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aeroports} : get all the aeroports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aeroports in body.
     */
    @GetMapping("/aeroports")
    public ResponseEntity<List<Aeroport>> getAllAeroports(Pageable pageable) {
        log.debug("REST request to get a page of Aeroports");
        Page<Aeroport> page = aeroportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aeroports/:id} : get the "id" aeroport.
     *
     * @param id the id of the aeroport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aeroport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aeroports/{id}")
    public ResponseEntity<Aeroport> getAeroport(@PathVariable Long id) {
        log.debug("REST request to get Aeroport : {}", id);
        Optional<Aeroport> aeroport = aeroportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aeroport);
    }

    /**
     * {@code DELETE  /aeroports/:id} : delete the "id" aeroport.
     *
     * @param id the id of the aeroport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aeroports/{id}")
    public ResponseEntity<Void> deleteAeroport(@PathVariable Long id) {
        log.debug("REST request to delete Aeroport : {}", id);
        aeroportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
