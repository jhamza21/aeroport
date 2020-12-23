package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AeroportApp;
import com.mycompany.myapp.domain.Personnel;
import com.mycompany.myapp.repository.PersonnelRepository;
import com.mycompany.myapp.service.PersonnelService;

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
 * Integration tests for the {@link PersonnelResource} REST controller.
 */
@SpringBootTest(classes = AeroportApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PersonnelResourceIT {

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private PersonnelService personnelService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonnelMockMvc;

    private Personnel personnel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personnel createEntity(EntityManager em) {
        Personnel personnel = new Personnel()
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .nationality(DEFAULT_NATIONALITY);
        return personnel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personnel createUpdatedEntity(EntityManager em) {
        Personnel personnel = new Personnel()
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .nationality(UPDATED_NATIONALITY);
        return personnel;
    }

    @BeforeEach
    public void initTest() {
        personnel = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonnel() throws Exception {
        int databaseSizeBeforeCreate = personnelRepository.findAll().size();
        // Create the Personnel
        restPersonnelMockMvc.perform(post("/api/personnel")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personnel)))
            .andExpect(status().isCreated());

        // Validate the Personnel in the database
        List<Personnel> personnelList = personnelRepository.findAll();
        assertThat(personnelList).hasSize(databaseSizeBeforeCreate + 1);
        Personnel testPersonnel = personnelList.get(personnelList.size() - 1);
        assertThat(testPersonnel.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPersonnel.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPersonnel.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
    }

    @Test
    @Transactional
    public void createPersonnelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personnelRepository.findAll().size();

        // Create the Personnel with an existing ID
        personnel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonnelMockMvc.perform(post("/api/personnel")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personnel)))
            .andExpect(status().isBadRequest());

        // Validate the Personnel in the database
        List<Personnel> personnelList = personnelRepository.findAll();
        assertThat(personnelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personnelRepository.findAll().size();
        // set the field null
        personnel.setLastName(null);

        // Create the Personnel, which fails.


        restPersonnelMockMvc.perform(post("/api/personnel")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personnel)))
            .andExpect(status().isBadRequest());

        List<Personnel> personnelList = personnelRepository.findAll();
        assertThat(personnelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personnelRepository.findAll().size();
        // set the field null
        personnel.setFirstName(null);

        // Create the Personnel, which fails.


        restPersonnelMockMvc.perform(post("/api/personnel")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personnel)))
            .andExpect(status().isBadRequest());

        List<Personnel> personnelList = personnelRepository.findAll();
        assertThat(personnelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = personnelRepository.findAll().size();
        // set the field null
        personnel.setNationality(null);

        // Create the Personnel, which fails.


        restPersonnelMockMvc.perform(post("/api/personnel")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personnel)))
            .andExpect(status().isBadRequest());

        List<Personnel> personnelList = personnelRepository.findAll();
        assertThat(personnelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonnel() throws Exception {
        // Initialize the database
        personnelRepository.saveAndFlush(personnel);

        // Get all the personnelList
        restPersonnelMockMvc.perform(get("/api/personnel?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personnel.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)));
    }
    
    @Test
    @Transactional
    public void getPersonnel() throws Exception {
        // Initialize the database
        personnelRepository.saveAndFlush(personnel);

        // Get the personnel
        restPersonnelMockMvc.perform(get("/api/personnel/{id}", personnel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personnel.getId().intValue()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY));
    }
    @Test
    @Transactional
    public void getNonExistingPersonnel() throws Exception {
        // Get the personnel
        restPersonnelMockMvc.perform(get("/api/personnel/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonnel() throws Exception {
        // Initialize the database
        personnelService.save(personnel);

        int databaseSizeBeforeUpdate = personnelRepository.findAll().size();

        // Update the personnel
        Personnel updatedPersonnel = personnelRepository.findById(personnel.getId()).get();
        // Disconnect from session so that the updates on updatedPersonnel are not directly saved in db
        em.detach(updatedPersonnel);
        updatedPersonnel
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .nationality(UPDATED_NATIONALITY);

        restPersonnelMockMvc.perform(put("/api/personnel")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonnel)))
            .andExpect(status().isOk());

        // Validate the Personnel in the database
        List<Personnel> personnelList = personnelRepository.findAll();
        assertThat(personnelList).hasSize(databaseSizeBeforeUpdate);
        Personnel testPersonnel = personnelList.get(personnelList.size() - 1);
        assertThat(testPersonnel.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPersonnel.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPersonnel.getNationality()).isEqualTo(UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonnel() throws Exception {
        int databaseSizeBeforeUpdate = personnelRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonnelMockMvc.perform(put("/api/personnel")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personnel)))
            .andExpect(status().isBadRequest());

        // Validate the Personnel in the database
        List<Personnel> personnelList = personnelRepository.findAll();
        assertThat(personnelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonnel() throws Exception {
        // Initialize the database
        personnelService.save(personnel);

        int databaseSizeBeforeDelete = personnelRepository.findAll().size();

        // Delete the personnel
        restPersonnelMockMvc.perform(delete("/api/personnel/{id}", personnel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Personnel> personnelList = personnelRepository.findAll();
        assertThat(personnelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
