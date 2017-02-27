package com.wilkestech.web.rest;

import com.wilkestech.WilkestechDemoApp;
import com.wilkestech.domain.DayPrice;
import com.wilkestech.repository.DayPriceRepository;
import com.wilkestech.service.DayPriceService;
import com.wilkestech.service.dto.DayPriceDTO;
import com.wilkestech.service.mapper.DayPriceMapper;

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
 * Test class for the DayPriceResource REST controller.
 *
 * @see DayPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WilkestechDemoApp.class)
public class DayPriceResourceIntTest {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_PRICE_OF_DAY = 1L;
    private static final Long UPDATED_PRICE_OF_DAY = 2L;

    @Inject
    private DayPriceRepository dayPriceRepository;

    @Inject
    private DayPriceMapper dayPriceMapper;

    @Inject
    private DayPriceService dayPriceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDayPriceMockMvc;

    private DayPrice dayPrice;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DayPriceResource dayPriceResource = new DayPriceResource();
        ReflectionTestUtils.setField(dayPriceResource, "dayPriceService", dayPriceService);
        this.restDayPriceMockMvc = MockMvcBuilders.standaloneSetup(dayPriceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DayPrice createEntity(EntityManager em) {
        DayPrice dayPrice = new DayPrice();
        dayPrice = new DayPrice()
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE)
                .priceOfDay(DEFAULT_PRICE_OF_DAY);
        return dayPrice;
    }

    @Before
    public void initTest() {
        dayPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createDayPrice() throws Exception {
        int databaseSizeBeforeCreate = dayPriceRepository.findAll().size();

        // Create the DayPrice
        DayPriceDTO dayPriceDTO = dayPriceMapper.dayPriceToDayPriceDTO(dayPrice);

        restDayPriceMockMvc.perform(post("/api/day-prices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dayPriceDTO)))
                .andExpect(status().isCreated());

        // Validate the DayPrice in the database
        List<DayPrice> dayPrices = dayPriceRepository.findAll();
        assertThat(dayPrices).hasSize(databaseSizeBeforeCreate + 1);
        DayPrice testDayPrice = dayPrices.get(dayPrices.size() - 1);
        assertThat(testDayPrice.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testDayPrice.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testDayPrice.getPriceOfDay()).isEqualTo(DEFAULT_PRICE_OF_DAY);
    }

    @Test
    @Transactional
    public void getAllDayPrices() throws Exception {
        // Initialize the database
        dayPriceRepository.saveAndFlush(dayPrice);

        // Get all the dayPrices
        restDayPriceMockMvc.perform(get("/api/day-prices?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dayPrice.getId().intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].priceOfDay").value(hasItem(DEFAULT_PRICE_OF_DAY.intValue())));
    }

    @Test
    @Transactional
    public void getDayPrice() throws Exception {
        // Initialize the database
        dayPriceRepository.saveAndFlush(dayPrice);

        // Get the dayPrice
        restDayPriceMockMvc.perform(get("/api/day-prices/{id}", dayPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dayPrice.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.priceOfDay").value(DEFAULT_PRICE_OF_DAY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDayPrice() throws Exception {
        // Get the dayPrice
        restDayPriceMockMvc.perform(get("/api/day-prices/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDayPrice() throws Exception {
        // Initialize the database
        dayPriceRepository.saveAndFlush(dayPrice);
        int databaseSizeBeforeUpdate = dayPriceRepository.findAll().size();

        // Update the dayPrice
        DayPrice updatedDayPrice = dayPriceRepository.findOne(dayPrice.getId());
        updatedDayPrice
                .startDate(UPDATED_START_DATE)
                .endDate(UPDATED_END_DATE)
                .priceOfDay(UPDATED_PRICE_OF_DAY);
        DayPriceDTO dayPriceDTO = dayPriceMapper.dayPriceToDayPriceDTO(updatedDayPrice);

        restDayPriceMockMvc.perform(put("/api/day-prices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dayPriceDTO)))
                .andExpect(status().isOk());

        // Validate the DayPrice in the database
        List<DayPrice> dayPrices = dayPriceRepository.findAll();
        assertThat(dayPrices).hasSize(databaseSizeBeforeUpdate);
        DayPrice testDayPrice = dayPrices.get(dayPrices.size() - 1);
        assertThat(testDayPrice.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testDayPrice.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testDayPrice.getPriceOfDay()).isEqualTo(UPDATED_PRICE_OF_DAY);
    }

    @Test
    @Transactional
    public void deleteDayPrice() throws Exception {
        // Initialize the database
        dayPriceRepository.saveAndFlush(dayPrice);
        int databaseSizeBeforeDelete = dayPriceRepository.findAll().size();

        // Get the dayPrice
        restDayPriceMockMvc.perform(delete("/api/day-prices/{id}", dayPrice.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DayPrice> dayPrices = dayPriceRepository.findAll();
        assertThat(dayPrices).hasSize(databaseSizeBeforeDelete - 1);
    }
}
