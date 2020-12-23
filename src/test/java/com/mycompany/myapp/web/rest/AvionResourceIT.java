package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AeroportApp;
import com.mycompany.myapp.domain.Avion;
import com.mycompany.myapp.repository.AvionRepository;
import com.mycompany.myapp.service.AvionService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AvionResource} REST controller.
 */
@SpringBootTest(classes = AeroportApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AvionResourceIT {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_ARR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ARR = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_DEP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DEP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private AvionService avionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvionMockMvc;

    private Avion avion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avion createEntity(EntityManager em) {
        Avion avion = new Avion()
            .matricule(DEFAULT_MATRICULE)
            .company(DEFAULT_COMPANY)
            .dateArr(DEFAULT_DATE_ARR)
            .dateDep(DEFAULT_DATE_DEP);
        return avion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avion createUpdatedEntity(EntityManager em) {
        Avion avion = new Avion()
            .matricule(UPDATED_MATRICULE)
            .company(UPDATED_COMPANY)
            .dateArr(UPDATED_DATE_ARR)
            .dateDep(UPDATED_DATE_DEP);
        return avion;
    }

    @BeforeEach
    public void initTest() {
        avion = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvion() throws Exception {
        int databaseSizeBeforeCreate = avionRepository.findAll().size();
        // Create the Avion
        restAvionMockMvc.perform(post("/api/avions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isCreated());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeCreate + 1);
        Avion testAvion = avionList.get(avionList.size() - 1);
        assertThat(testAvion.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testAvion.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testAvion.getDateArr()).isEqualTo(DEFAULT_DATE_ARR);
        assertThat(testAvion.getDateDep()).isEqualTo(DEFAULT_DATE_DEP);
    }

    @Test
    @Transactional
    public void createAvionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avionRepository.findAll().size();

        // Create the Avion with an existing ID
        avion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvionMockMvc.perform(post("/api/avions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isBadRequest());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMatriculeIsRequired() throws Exception {
        int databaseSizeBeforeTest = avionRepository.findAll().size();
        // set the field null
        avion.setMatricule(null);

        // Create the Avion, which fails.


        restAvionMockMvc.perform(post("/api/avions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isBadRequest());

        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompanyIsRequired() throws Exception {
        int databaseSizeBeforeTest = avionRepository.findAll().size();
        // set the field null
        avion.setCompany(null);

        // Create the Avion, which fails.


        restAvionMockMvc.perform(post("/api/avions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isBadRequest());

        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateArrIsRequired() throws Exception {
        int databaseSizeBeforeTest = avionRepository.findAll().size();
        // set the field null
        avion.setDateArr(null);

        // Create the Avion, which fails.


        restAvionMockMvc.perform(post("/api/avions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isBadRequest());

        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDepIsRequired() throws Exception {
        int databaseSizeBeforeTest = avionRepository.findAll().size();
        // set the field null
        avion.setDateDep(null);

        // Create the Avion, which fails.


        restAvionMockMvc.perform(post("/api/avions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isBadRequest());

        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvions() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList
        restAvionMockMvc.perform(get("/api/avions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avion.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].dateArr").value(hasItem(sameInstant(DEFAULT_DATE_ARR))))
            .andExpect(jsonPath("$.[*].dateDep").value(hasItem(sameInstant(DEFAULT_DATE_DEP))));
    }
    
    @Test
    @Transactional
    public void getAvion() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get the avion
        restAvionMockMvc.perform(get("/api/avions/{id}", avion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avion.getId().intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.dateArr").value(sameInstant(DEFAULT_DATE_ARR)))
            .andExpect(jsonPath("$.dateDep").value(sameInstant(DEFAULT_DATE_DEP)));
    }
    @Test
    @Transactional
    public void getNonExistingAvion() throws Exception {
        // Get the avion
        restAvionMockMvc.perform(get("/api/avions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvion() throws Exception {
        // Initialize the database
        avionService.save(avion);

        int databaseSizeBeforeUpdate = avionRepository.findAll().size();

        // Update the avion
        Avion updatedAvion = avionRepository.findById(avion.getId()).get();
        // Disconnect from session so that the updates on updatedAvion are not directly saved in db
        em.detach(updatedAvion);
        updatedAvion
            .matricule(UPDATED_MATRICULE)
            .company(UPDATED_COMPANY)
            .dateArr(UPDATED_DATE_ARR)
            .dateDep(UPDATED_DATE_DEP);

        restAvionMockMvc.perform(put("/api/avions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvion)))
            .andExpect(status().isOk());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
        Avion testAvion = avionList.get(avionList.size() - 1);
        assertThat(testAvion.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testAvion.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testAvion.getDateArr()).isEqualTo(UPDATED_DATE_ARR);
        assertThat(testAvion.getDateDep()).isEqualTo(UPDATED_DATE_DEP);
    }

    @Test
    @Transactional
    public void updateNonExistingAvion() throws Exception {
        int databaseSizeBeforeUpdate = avionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvionMockMvc.perform(put("/api/avions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(avion)))
            .andExpect(status().isBadRequest());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAvion() throws Exception {
        // Initialize the database
        avionService.save(avion);

        int databaseSizeBeforeDelete = avionRepository.findAll().size();

        // Delete the avion
        restAvionMockMvc.perform(delete("/api/avions/{id}", avion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
