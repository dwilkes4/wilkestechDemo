package com.wilkestech.service;

import com.wilkestech.service.dto.RenteeDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Rentee.
 */
public interface RenteeService {

    /**
     * Save a rentee.
     *
     * @param renteeDTO the entity to save
     * @return the persisted entity
     */
    RenteeDTO save(RenteeDTO renteeDTO);

    /**
     *  Get all the rentees.
     *  
     *  @return the list of entities
     */
    List<RenteeDTO> findAll();

    /**
     *  Get the "id" rentee.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RenteeDTO findOne(Long id);

    /**
     *  Delete the "id" rentee.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
