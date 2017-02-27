package com.wilkestech.web.rest;

import com.wilkestech.WilkestechDemoApp;
import com.wilkestech.domain.Rentee;
import com.wilkestech.repository.RenteeRepository;
import com.wilkestech.service.RenteeService;
import com.wilkestech.service.dto.RenteeDTO;
import com.wilkestech.service.mapper.RenteeMapper;

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
 * Test class for the RenteeResource REST controller.
 *
 * @see RenteeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WilkestechDemoApp.class)
public class RenteeResourceIntTest {
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
    private RenteeRepository renteeRepository;

    @Inject
    private RenteeMapper renteeMapper;

    @Inject
    private RenteeService renteeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRenteeMockMvc;

    private Rentee rentee;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RenteeResource renteeResource = new RenteeResource();
        ReflectionTestUtils.setField(renteeResource, "renteeService", renteeService);
        this.restRenteeMockMvc = MockMvcBuilders.standaloneSetup(renteeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rentee createEntity(EntityManager em) {
        Rentee rentee = new Rentee();
        rentee = new Rentee()
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .email(DEFAULT_EMAIL)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .password(DEFAULT_PASSWORD);
        return rentee;
    }

    @Before
    public void initTest() {
        rentee = createEntity(em);
    }

    @Test
    @Transactional
    public void createRentee() throws Exception {
        int databaseSizeBeforeCreate = renteeRepository.findAll().size();

        // Create the Rentee
        RenteeDTO renteeDTO = renteeMapper.renteeToRenteeDTO(rentee);

        restRenteeMockMvc.perform(post("/api/rentees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(renteeDTO)))
                .andExpect(status().isCreated());

        // Validate the Rentee in the database
        List<Rentee> rentees = renteeRepository.findAll();
        assertThat(rentees).hasSize(databaseSizeBeforeCreate + 1);
        Rentee testRentee = rentees.get(rentees.size() - 1);
        assertThat(testRentee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testRentee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testRentee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRentee.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testRentee.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllRentees() throws Exception {
        // Initialize the database
        renteeRepository.saveAndFlush(rentee);

        // Get all the rentees
        restRenteeMockMvc.perform(get("/api/rentees?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rentee.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    @Transactional
    public void getRentee() throws Exception {
        // Initialize the database
        renteeRepository.saveAndFlush(rentee);

        // Get the rentee
        restRenteeMockMvc.perform(get("/api/rentees/{id}", rentee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rentee.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRentee() throws Exception {
        // Get the rentee
        restRenteeMockMvc.perform(get("/api/rentees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRentee() throws Exception {
        // Initialize the database
        renteeRepository.saveAndFlush(rentee);
        int databaseSizeBeforeUpdate = renteeRepository.findAll().size();

        // Update the rentee
        Rentee updatedRentee = renteeRepository.findOne(rentee.getId());
        updatedRentee
                .firstName(UPDATED_FIRST_NAME)
                .lastName(UPDATED_LAST_NAME)
                .email(UPDATED_EMAIL)
                .phoneNumber(UPDATED_PHONE_NUMBER)
                .password(UPDATED_PASSWORD);
        RenteeDTO renteeDTO = renteeMapper.renteeToRenteeDTO(updatedRentee);

        restRenteeMockMvc.perform(put("/api/rentees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(renteeDTO)))
                .andExpect(status().isOk());

        // Validate the Rentee in the database
        List<Rentee> rentees = renteeRepository.findAll();
        assertThat(rentees).hasSize(databaseSizeBeforeUpdate);
        Rentee testRentee = rentees.get(rentees.size() - 1);
        assertThat(testRentee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testRentee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testRentee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRentee.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testRentee.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void deleteRentee() throws Exception {
        // Initialize the database
        renteeRepository.saveAndFlush(rentee);
        int databaseSizeBeforeDelete = renteeRepository.findAll().size();

        // Get the rentee
        restRenteeMockMvc.perform(delete("/api/rentees/{id}", rentee.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Rentee> rentees = renteeRepository.findAll();
        assertThat(rentees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
