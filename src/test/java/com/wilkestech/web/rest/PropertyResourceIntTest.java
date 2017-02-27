package com.wilkestech.web.rest;

import com.wilkestech.WilkestechDemoApp;
import com.wilkestech.domain.Property;
import com.wilkestech.repository.PropertyRepository;
import com.wilkestech.service.PropertyService;
import com.wilkestech.service.dto.PropertyDTO;
import com.wilkestech.service.mapper.PropertyMapper;

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
 * Test class for the PropertyResource REST controller.
 *
 * @see PropertyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WilkestechDemoApp.class)
public class PropertyResourceIntTest {
    private static final String DEFAULT_PROPERTY_NAME = "AAAAA";
    private static final String UPDATED_PROPERTY_NAME = "BBBBB";

    @Inject
    private PropertyRepository propertyRepository;

    @Inject
    private PropertyMapper propertyMapper;

    @Inject
    private PropertyService propertyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPropertyMockMvc;

    private Property property;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PropertyResource propertyResource = new PropertyResource();
        ReflectionTestUtils.setField(propertyResource, "propertyService", propertyService);
        this.restPropertyMockMvc = MockMvcBuilders.standaloneSetup(propertyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Property createEntity(EntityManager em) {
        Property property = new Property();
        property = new Property()
                .propertyName(DEFAULT_PROPERTY_NAME);
        return property;
    }

    @Before
    public void initTest() {
        property = createEntity(em);
    }

    @Test
    @Transactional
    public void createProperty() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.propertyToPropertyDTO(property);

        restPropertyMockMvc.perform(post("/api/properties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
                .andExpect(status().isCreated());

        // Validate the Property in the database
        List<Property> properties = propertyRepository.findAll();
        assertThat(properties).hasSize(databaseSizeBeforeCreate + 1);
        Property testProperty = properties.get(properties.size() - 1);
        assertThat(testProperty.getPropertyName()).isEqualTo(DEFAULT_PROPERTY_NAME);
    }

    @Test
    @Transactional
    public void getAllProperties() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the properties
        restPropertyMockMvc.perform(get("/api/properties?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId().intValue())))
                .andExpect(jsonPath("$.[*].propertyName").value(hasItem(DEFAULT_PROPERTY_NAME.toString())));
    }

    @Test
    @Transactional
    public void getProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get the property
        restPropertyMockMvc.perform(get("/api/properties/{id}", property.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(property.getId().intValue()))
            .andExpect(jsonPath("$.propertyName").value(DEFAULT_PROPERTY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProperty() throws Exception {
        // Get the property
        restPropertyMockMvc.perform(get("/api/properties/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);
        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Update the property
        Property updatedProperty = propertyRepository.findOne(property.getId());
        updatedProperty
                .propertyName(UPDATED_PROPERTY_NAME);
        PropertyDTO propertyDTO = propertyMapper.propertyToPropertyDTO(updatedProperty);

        restPropertyMockMvc.perform(put("/api/properties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
                .andExpect(status().isOk());

        // Validate the Property in the database
        List<Property> properties = propertyRepository.findAll();
        assertThat(properties).hasSize(databaseSizeBeforeUpdate);
        Property testProperty = properties.get(properties.size() - 1);
        assertThat(testProperty.getPropertyName()).isEqualTo(UPDATED_PROPERTY_NAME);
    }

    @Test
    @Transactional
    public void deleteProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);
        int databaseSizeBeforeDelete = propertyRepository.findAll().size();

        // Get the property
        restPropertyMockMvc.perform(delete("/api/properties/{id}", property.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Property> properties = propertyRepository.findAll();
        assertThat(properties).hasSize(databaseSizeBeforeDelete - 1);
    }
}
