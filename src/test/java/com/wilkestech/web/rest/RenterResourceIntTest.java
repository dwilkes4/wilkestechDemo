package com.wilkestech.web.rest;

import com.wilkestech.WilkestechDemoApp;
import com.wilkestech.domain.Renter;
import com.wilkestech.repository.RenterRepository;
import com.wilkestech.service.RenterService;
import com.wilkestech.service.dto.RenterDTO;
import com.wilkestech.service.mapper.RenterMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RenterResource REST controller.
 *
 * @see RenterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WilkestechDemoApp.class)
public class RenterResourceIntTest {
    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_PHONE_NUMBER = "AAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBB";
    private static final String DEFAULT_PASSWORD = "AAAAA";
    private static final String UPDATED_PASSWORD = "BBBBB";

    @Inject
    private RenterRepository renterRepository;

    @Inject
    private RenterMapper renterMapper;

    @Inject
    private RenterService renterService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRenterMockMvc;

    private Renter renter;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RenterResource renterResource = new RenterResource();
        ReflectionTestUtils.setField(renterResource, "renterService", renterService);
        this.restRenterMockMvc = MockMvcBuilders.standaloneSetup(renterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Renter createEntity(EntityManager em) {
        Renter renter = new Renter();
        renter = new Renter()
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .email(DEFAULT_EMAIL)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .password(DEFAULT_PASSWORD);
        return renter;
    }

    @Before
    public void initTest() {
        renter = createEntity(em);
    }

    @Test
    @Transactional
    public void createRenter() throws Exception {
        int databaseSizeBeforeCreate = renterRepository.findAll().size();

        // Create the Renter
        RenterDTO renterDTO = renterMapper.renterToRenterDTO(renter);

        restRenterMockMvc.perform(post("/api/renters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(renterDTO)))
                .andExpect(status().isCreated());

        // Validate the Renter in the database
        List<Renter> renters = renterRepository.findAll();
        assertThat(renters).hasSize(databaseSizeBeforeCreate + 1);
        Renter testRenter = renters.get(renters.size() - 1);
        assertThat(testRenter.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testRenter.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testRenter.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRenter.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testRenter.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllRenters() throws Exception {
        // Initialize the database
        renterRepository.saveAndFlush(renter);

        // Get all the renters
        restRenterMockMvc.perform(get("/api/renters?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(renter.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    @Transactional
    public void getRenter() throws Exception {
        // Initialize the database
        renterRepository.saveAndFlush(renter);

        // Get the renter
        restRenterMockMvc.perform(get("/api/renters/{id}", renter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(renter.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRenter() throws Exception {
        // Get the renter
        restRenterMockMvc.perform(get("/api/renters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRenter() throws Exception {
        // Initialize the database
        renterRepository.saveAndFlush(renter);
        int databaseSizeBeforeUpdate = renterRepository.findAll().size();

        // Update the renter
        Renter updatedRenter = renterRepository.findOne(renter.getId());
        updatedRenter
                .firstName(UPDATED_FIRST_NAME)
                .lastName(UPDATED_LAST_NAME)
                .email(UPDATED_EMAIL)
                .phoneNumber(UPDATED_PHONE_NUMBER)
                .password(UPDATED_PASSWORD);
        RenterDTO renterDTO = renterMapper.renterToRenterDTO(updatedRenter);

        restRenterMockMvc.perform(put("/api/renters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(renterDTO)))
                .andExpect(status().isOk());

        // Validate the Renter in the database
        List<Renter> renters = renterRepository.findAll();
        assertThat(renters).hasSize(databaseSizeBeforeUpdate);
        Renter testRenter = renters.get(renters.size() - 1);
        assertThat(testRenter.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testRenter.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testRenter.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRenter.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testRenter.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void deleteRenter() throws Exception {
        // Initialize the database
        renterRepository.saveAndFlush(renter);
        int databaseSizeBeforeDelete = renterRepository.findAll().size();

        // Get the renter
        restRenterMockMvc.perform(delete("/api/renters/{id}", renter.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Renter> renters = renterRepository.findAll();
        assertThat(renters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
