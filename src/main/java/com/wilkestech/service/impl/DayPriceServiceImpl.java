package com.wilkestech.service.impl;

import com.wilkestech.service.DayPriceService;
import com.wilkestech.domain.DayPrice;
import com.wilkestech.repository.DayPriceRepository;
import com.wilkestech.service.dto.DayPriceDTO;
import com.wilkestech.service.mapper.DayPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DayPrice.
 */
@Service
@Transactional
public class DayPriceServiceImpl implements DayPriceService{

    private final Logger log = LoggerFactory.getLogger(DayPriceServiceImpl.class);
    
    @Inject
    private DayPriceRepository dayPriceRepository;

    @Inject
    private DayPriceMapper dayPriceMapper;

    /**
     * Save a dayPrice.
     *
     * @param dayPriceDTO the entity to save
     * @return the persisted entity
     */
    public DayPriceDTO save(DayPriceDTO dayPriceDTO) {
        log.debug("Request to save DayPrice : {}", dayPriceDTO);
        DayPrice dayPrice = dayPriceMapper.dayPriceDTOToDayPrice(dayPriceDTO);
        dayPrice = dayPriceRepository.save(dayPrice);
        DayPriceDTO result = dayPriceMapper.dayPriceToDayPriceDTO(dayPrice);
        return result;
    }

    /**
     *  Get all the dayPrices.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<DayPriceDTO> findAll() {
        log.debug("Request to get all DayPrices");
        List<DayPriceDTO> result = dayPriceRepository.findAll().stream()
            .map(dayPriceMapper::dayPriceToDayPriceDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one dayPrice by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DayPriceDTO findOne(Long id) {
        log.debug("Request to get DayPrice : {}", id);
        DayPrice dayPrice = dayPriceRepository.findOne(id);
        DayPriceDTO dayPriceDTO = dayPriceMapper.dayPriceToDayPriceDTO(dayPrice);
        return dayPriceDTO;
    }

    /**
     *  Delete the  dayPrice by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DayPrice : {}", id);
        dayPriceRepository.delete(id);
    }
}
