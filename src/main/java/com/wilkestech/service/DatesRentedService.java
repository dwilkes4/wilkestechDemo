package com.wilkestech.service;

import com.wilkestech.service.dto.DatesRentedDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing DatesRented.
 */
public interface DatesRentedService {

    /**
     * Save a datesRented.
     *
     * @param datesRentedDTO the entity to save
     * @return the persisted entity
     */
    DatesRentedDTO save(DatesRentedDTO datesRentedDTO);

    /**
     *  Get all the datesRenteds.
     *  
     *  @return the list of entities
     */
    List<DatesRentedDTO> findAll();

    /**
     *  Get the "id" datesRented.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DatesRentedDTO findOne(Long id);

    /**
     *  Delete the "id" datesRented.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
