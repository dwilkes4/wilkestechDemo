package com.wilkestech.service;

import com.wilkestech.service.dto.DayPriceDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing DayPrice.
 */
public interface DayPriceService {

    /**
     * Save a dayPrice.
     *
     * @param dayPriceDTO the entity to save
     * @return the persisted entity
     */
    DayPriceDTO save(DayPriceDTO dayPriceDTO);

    /**
     *  Get all the dayPrices.
     *  
     *  @return the list of entities
     */
    List<DayPriceDTO> findAll();

    /**
     *  Get the "id" dayPrice.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DayPriceDTO findOne(Long id);

    /**
     *  Delete the "id" dayPrice.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
