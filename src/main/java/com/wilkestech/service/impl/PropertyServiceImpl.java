package com.wilkestech.service.impl;

import com.wilkestech.service.PropertyService;
import com.wilkestech.domain.Property;
import com.wilkestech.repository.PropertyRepository;
import com.wilkestech.service.dto.PropertyDTO;
import com.wilkestech.service.mapper.PropertyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Property.
 */
@Service
@Transactional
public class PropertyServiceImpl implements PropertyService{

    private final Logger log = LoggerFactory.getLogger(PropertyServiceImpl.class);
    
    @Inject
    private PropertyRepository propertyRepository;

    @Inject
    private PropertyMapper propertyMapper;

    /**
     * Save a property.
     *
     * @param propertyDTO the entity to save
     * @return the persisted entity
     */
    public PropertyDTO save(PropertyDTO propertyDTO) {
        log.debug("Request to save Property : {}", propertyDTO);
        Property property = propertyMapper.propertyDTOToProperty(propertyDTO);
        property = propertyRepository.save(property);
        PropertyDTO result = propertyMapper.propertyToPropertyDTO(property);
        return result;
    }

    /**
     *  Get all the properties.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PropertyDTO> findAll() {
        log.debug("Request to get all Properties");
        List<PropertyDTO> result = propertyRepository.findAll().stream()
            .map(propertyMapper::propertyToPropertyDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one property by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PropertyDTO findOne(Long id) {
        log.debug("Request to get Property : {}", id);
        Property property = propertyRepository.findOne(id);
        PropertyDTO propertyDTO = propertyMapper.propertyToPropertyDTO(property);
        return propertyDTO;
    }

    /**
     *  Delete the  property by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Property : {}", id);
        propertyRepository.delete(id);
    }
}
