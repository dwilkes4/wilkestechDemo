package com.wilkestech.web.rest;

import com.wilkestech.WilkestechDemoApp;
import com.wilkestech.domain.DatesRented;
import com.wilkestech.repository.DatesRentedRepository;
import com.wilkestech.service.DatesRentedService;
import com.wilkestech.service.dto.DatesRentedDTO;
import com.wilkestech.service.mapper.DatesRentedMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DatesRentedResource REST controller.
 *
 * @see DatesRentedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WilkestechDemoApp.class)
public class DatesRentedResourceIntTest {

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private DatesRentedRepository datesRentedRepository;

    @Inject
    private DatesRentedMapper datesRentedMapper;

    @Inject
    private DatesRentedService datesRentedService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDatesRentedMockMvc;

    private DatesRented datesRented;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DatesRentedResource datesRentedResource = new DatesRentedResource();
        ReflectionTestUtils.setField(datesRentedResource, "datesRentedService", datesRentedService);
        this.restDatesRentedMockMvc = MockMvcBuilders.standaloneSetup(datesRentedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatesRented createEntity(EntityManager em) {
        DatesRented datesRented = new DatesRented();
        datesRented = new DatesRented()
                .price(DEFAULT_PRICE)
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE);
        return datesRented;
    }

    @Before
    public void initTest() {
        datesRented = createEntity(em);
    }

    @Test
    @Transactional
    public void createDatesRented() throws Exception {
        int databaseSizeBeforeCreate = datesRentedRepository.findAll().size();

        // Create the DatesRented
        DatesRentedDTO datesRentedDTO = datesRentedMapper.datesRentedToDatesRentedDTO(datesRented);

        restDatesRentedMockMvc.perform(post("/api/dates-renteds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(datesRentedDTO)))
                .andExpect(status().isCreated());

        // Validate the DatesRented in the database
        List<DatesRented> datesRenteds = datesRentedRepository.findAll();
        assertThat(datesRenteds).hasSize(databaseSizeBeforeCreate + 1);
        DatesRented testDatesRented = datesRenteds.get(datesRenteds.size() - 1);
        assertThat(testDatesRented.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testDatesRented.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testDatesRented.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllDatesRenteds() throws Exception {
        // Initialize the database
        datesRentedRepository.saveAndFlush(datesRented);

        // Get all the datesRenteds
        restDatesRentedMockMvc.perform(get("/api/dates-renteds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(datesRented.getId().intValue())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getDatesRented() throws Exception {
        // Initialize the database
        datesRentedRepository.saveAndFlush(datesRented);

        // Get the datesRented
        restDatesRentedMockMvc.perform(get("/api/dates-renteds/{id}", datesRented.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(datesRented.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDatesRented() throws Exception {
        // Get the datesRented
        restDatesRentedMockMvc.perform(get("/api/dates-renteds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatesRented() throws Exception {
        // Initialize the database
        datesRentedRepository.saveAndFlush(datesRented);
        int databaseSizeBeforeUpdate = datesRentedRepository.findAll().size();

        // Update the datesRented
        DatesRented updatedDatesRented = datesRentedRepository.findOne(datesRented.getId());
        updatedDatesRented
                .price(UPDATED_PRICE)
                .startDate(UPDATED_START_DATE)
                .endDate(UPDATED_END_DATE);
        DatesRentedDTO datesRentedDTO = datesRentedMapper.datesRentedToDatesRentedDTO(updatedDatesRented);

        restDatesRentedMockMvc.perform(put("/api/dates-renteds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(datesRentedDTO)))
                .andExpect(status().isOk());

        // Validate the DatesRented in the database
        List<DatesRented> datesRenteds = datesRentedRepository.findAll();
        assertThat(datesRenteds).hasSize(databaseSizeBeforeUpdate);
        DatesRented testDatesRented = datesRenteds.get(datesRenteds.size() - 1);
        assertThat(testDatesRented.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testDatesRented.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testDatesRented.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void deleteDatesRented() throws Exception {
        // Initialize the database
        datesRentedRepository.saveAndFlush(datesRented);
        int databaseSizeBeforeDelete = datesRentedRepository.findAll().size();

        // Get the datesRented
        restDatesRentedMockMvc.perform(delete("/api/dates-renteds/{id}", datesRented.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DatesRented> datesRenteds = datesRentedRepository.findAll();
        assertThat(datesRenteds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
