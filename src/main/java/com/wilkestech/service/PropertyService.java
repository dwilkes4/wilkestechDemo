package com.wilkestech.service;

import com.wilkestech.service.dto.PropertyDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Property.
 */
public interface PropertyService {

    /**
     * Save a property.
     *
     * @param propertyDTO the entity to save
     * @return the persisted entity
     */
    PropertyDTO save(PropertyDTO propertyDTO);

    /**
     *  Get all the properties.
     *  
     *  @return the list of entities
     */
    List<PropertyDTO> findAll();

    /**
     *  Get the "id" property.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PropertyDTO findOne(Long id);

    /**
     *  Delete the "id" property.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
