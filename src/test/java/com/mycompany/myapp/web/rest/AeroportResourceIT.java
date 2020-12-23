package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AeroportApp;
import com.mycompany.myapp.domain.Aeroport;
import com.mycompany.myapp.repository.AeroportRepository;
import com.mycompany.myapp.service.AeroportService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AeroportResource} REST controller.
 */
@SpringBootTest(classes = AeroportApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AeroportResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_AVION = 1;
    private static final Integer UPDATED_MAX_AVION = 2;

    @Autowired
    private AeroportRepository aeroportRepository;

    @Autowired
    private AeroportService aeroportService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAeroportMockMvc;

    private Aeroport aeroport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aeroport createEntity(EntityManager em) {
        Aeroport aeroport = new Aeroport()
            .name(DEFAULT_NAME)
            .ville(DEFAULT_VILLE)
            .maxAvion(DEFAULT_MAX_AVION);
        return aeroport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aeroport createUpdatedEntity(EntityManager em) {
        Aeroport aeroport = new Aeroport()
            .name(UPDATED_NAME)
            .ville(UPDATED_VILLE)
            .maxAvion(UPDATED_MAX_AVION);
        return aeroport;
    }

    @BeforeEach
    public void initTest() {
        aeroport = createEntity(em);
    }

    @Test
    @Transactional
    public void createAeroport() throws Exception {
        int databaseSizeBeforeCreate = aeroportRepository.findAll().size();
        // Create the Aeroport
        restAeroportMockMvc.perform(post("/api/aeroports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aeroport)))
            .andExpect(status().isCreated());

        // Validate the Aeroport in the database
        List<Aeroport> aeroportList = aeroportRepository.findAll();
        assertThat(aeroportList).hasSize(databaseSizeBeforeCreate + 1);
        Aeroport testAeroport = aeroportList.get(aeroportList.size() - 1);
        assertThat(testAeroport.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAeroport.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testAeroport.getMaxAvion()).isEqualTo(DEFAULT_MAX_AVION);
    }

    @Test
    @Transactional
    public void createAeroportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aeroportRepository.findAll().size();

        // Create the Aeroport with an existing ID
        aeroport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAeroportMockMvc.perform(post("/api/aeroports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aeroport)))
            .andExpect(status().isBadRequest());

        // Validate the Aeroport in the database
        List<Aeroport> aeroportList = aeroportRepository.findAll();
        assertThat(aeroportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeroportRepository.findAll().size();
        // set the field null
        aeroport.setName(null);

        // Create the Aeroport, which fails.


        restAeroportMockMvc.perform(post("/api/aeroports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aeroport)))
            .andExpect(status().isBadRequest());

        List<Aeroport> aeroportList = aeroportRepository.findAll();
        assertThat(aeroportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeroportRepository.findAll().size();
        // set the field null
        aeroport.setVille(null);

        // Create the Aeroport, which fails.


        restAeroportMockMvc.perform(post("/api/aeroports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aeroport)))
            .andExpect(status().isBadRequest());

        List<Aeroport> aeroportList = aeroportRepository.findAll();
        assertThat(aeroportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxAvionIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeroportRepository.findAll().size();
        // set the field null
        aeroport.setMaxAvion(null);

        // Create the Aeroport, which fails.


        restAeroportMockMvc.perform(post("/api/aeroports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aeroport)))
            .andExpect(status().isBadRequest());

        List<Aeroport> aeroportList = aeroportRepository.findAll();
        assertThat(aeroportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAeroports() throws Exception {
        // Initialize the database
        aeroportRepository.saveAndFlush(aeroport);

        // Get all the aeroportList
        restAeroportMockMvc.perform(get("/api/aeroports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aeroport.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].maxAvion").value(hasItem(DEFAULT_MAX_AVION)));
    }
    
    @Test
    @Transactional
    public void getAeroport() throws Exception {
        // Initialize the database
        aeroportRepository.saveAndFlush(aeroport);

        // Get the aeroport
        restAeroportMockMvc.perform(get("/api/aeroports/{id}", aeroport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aeroport.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.maxAvion").value(DEFAULT_MAX_AVION));
    }
    @Test
    @Transactional
    public void getNonExistingAeroport() throws Exception {
        // Get the aeroport
        restAeroportMockMvc.perform(get("/api/aeroports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAeroport() throws Exception {
        // Initialize the database
        aeroportService.save(aeroport);

        int databaseSizeBeforeUpdate = aeroportRepository.findAll().size();

        // Update the aeroport
        Aeroport updatedAeroport = aeroportRepository.findById(aeroport.getId()).get();
        // Disconnect from session so that the updates on updatedAeroport are not directly saved in db
        em.detach(updatedAeroport);
        updatedAeroport
            .name(UPDATED_NAME)
            .ville(UPDATED_VILLE)
            .maxAvion(UPDATED_MAX_AVION);

        restAeroportMockMvc.perform(put("/api/aeroports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAeroport)))
            .andExpect(status().isOk());

        // Validate the Aeroport in the database
        List<Aeroport> aeroportList = aeroportRepository.findAll();
        assertThat(aeroportList).hasSize(databaseSizeBeforeUpdate);
        Aeroport testAeroport = aeroportList.get(aeroportList.size() - 1);
        assertThat(testAeroport.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAeroport.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAeroport.getMaxAvion()).isEqualTo(UPDATED_MAX_AVION);
    }

    @Test
    @Transactional
    public void updateNonExistingAeroport() throws Exception {
        int databaseSizeBeforeUpdate = aeroportRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAeroportMockMvc.perform(put("/api/aeroports")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aeroport)))
            .andExpect(status().isBadRequest());

        // Validate the Aeroport in the database
        List<Aeroport> aeroportList = aeroportRepository.findAll();
        assertThat(aeroportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAeroport() throws Exception {
        // Initialize the database
        aeroportService.save(aeroport);

        int databaseSizeBeforeDelete = aeroportRepository.findAll().size();

        // Delete the aeroport
        restAeroportMockMvc.perform(delete("/api/aeroports/{id}", aeroport.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aeroport> aeroportList = aeroportRepository.findAll();
        assertThat(aeroportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
