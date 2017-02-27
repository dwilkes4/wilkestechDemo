package com.wilkestech.service;

import com.wilkestech.service.dto.RenterDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Renter.
 */
public interface RenterService {

    /**
     * Save a renter.
     *
     * @param renterDTO the entity to save
     * @return the persisted entity
     */
    RenterDTO save(RenterDTO renterDTO);

    /**
     *  Get all the renters.
     *  
     *  @return the list of entities
     */
    List<RenterDTO> findAll();

    /**
     *  Get the "id" renter.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RenterDTO findOne(Long id);

    /**
     *  Delete the "id" renter.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
