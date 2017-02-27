package com.wilkestech.service.impl;

import com.wilkestech.service.RenteeService;
import com.wilkestech.domain.Rentee;
import com.wilkestech.repository.RenteeRepository;
import com.wilkestech.service.dto.RenteeDTO;
import com.wilkestech.service.mapper.RenteeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Rentee.
 */
@Service
@Transactional
public class RenteeServiceImpl implements RenteeService{

    private final Logger log = LoggerFactory.getLogger(RenteeServiceImpl.class);
    
    @Inject
    private RenteeRepository renteeRepository;

    @Inject
    private RenteeMapper renteeMapper;

    /**
     * Save a rentee.
     *
     * @param renteeDTO the entity to save
     * @return the persisted entity
     */
    public RenteeDTO save(RenteeDTO renteeDTO) {
        log.debug("Request to save Rentee : {}", renteeDTO);
        Rentee rentee = renteeMapper.renteeDTOToRentee(renteeDTO);
        rentee = renteeRepository.save(rentee);
        RenteeDTO result = renteeMapper.renteeToRenteeDTO(rentee);
        return result;
    }

    /**
     *  Get all the rentees.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<RenteeDTO> findAll() {
        log.debug("Request to get all Rentees");
        List<RenteeDTO> result = renteeRepository.findAll().stream()
            .map(renteeMapper::renteeToRenteeDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one rentee by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public RenteeDTO findOne(Long id) {
        log.debug("Request to get Rentee : {}", id);
        Rentee rentee = renteeRepository.findOne(id);
        RenteeDTO renteeDTO = renteeMapper.renteeToRenteeDTO(rentee);
        return renteeDTO;
    }

    /**
     *  Delete the  rentee by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Rentee : {}", id);
        renteeRepository.delete(id);
    }
}
