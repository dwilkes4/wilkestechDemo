package com.wilkestech.service.impl;

import com.wilkestech.service.DatesRentedService;
import com.wilkestech.domain.DatesRented;
import com.wilkestech.repository.DatesRentedRepository;
import com.wilkestech.service.dto.DatesRentedDTO;
import com.wilkestech.service.mapper.DatesRentedMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DatesRented.
 */
@Service
@Transactional
public class DatesRentedServiceImpl implements DatesRentedService{

    private final Logger log = LoggerFactory.getLogger(DatesRentedServiceImpl.class);
    
    @Inject
    private DatesRentedRepository datesRentedRepository;

    @Inject
    private DatesRentedMapper datesRentedMapper;

    /**
     * Save a datesRented.
     *
     * @param datesRentedDTO the entity to save
     * @return the persisted entity
     */
    public DatesRentedDTO save(DatesRentedDTO datesRentedDTO) {
        log.debug("Request to save DatesRented : {}", datesRentedDTO);
        DatesRented datesRented = datesRentedMapper.datesRentedDTOToDatesRented(datesRentedDTO);
        datesRented = datesRentedRepository.save(datesRented);
        DatesRentedDTO result = datesRentedMapper.datesRentedToDatesRentedDTO(datesRented);
        return result;
    }

    /**
     *  Get all the datesRenteds.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<DatesRentedDTO> findAll() {
        log.debug("Request to get all DatesRenteds");
        List<DatesRentedDTO> result = datesRentedRepository.findAll().stream()
            .map(datesRentedMapper::datesRentedToDatesRentedDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one datesRented by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DatesRentedDTO findOne(Long id) {
        log.debug("Request to get DatesRented : {}", id);
        DatesRented datesRented = datesRentedRepository.findOne(id);
        DatesRentedDTO datesRentedDTO = datesRentedMapper.datesRentedToDatesRentedDTO(datesRented);
        return datesRentedDTO;
    }

    /**
     *  Delete the  datesRented by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DatesRented : {}", id);
        datesRentedRepository.delete(id);
    }
}
