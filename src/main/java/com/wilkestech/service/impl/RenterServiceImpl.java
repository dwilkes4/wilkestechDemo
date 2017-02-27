package com.wilkestech.service.impl;

import com.wilkestech.service.RenterService;
import com.wilkestech.domain.Renter;
import com.wilkestech.repository.RenterRepository;
import com.wilkestech.service.dto.RenterDTO;
import com.wilkestech.service.mapper.RenterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Renter.
 */
@Service
@Transactional
public class RenterServiceImpl implements RenterService{

    private final Logger log = LoggerFactory.getLogger(RenterServiceImpl.class);
    
    @Inject
    private RenterRepository renterRepository;

    @Inject
    private RenterMapper renterMapper;

    /**
     * Save a renter.
     *
     * @param renterDTO the entity to save
     * @return the persisted entity
     */
    public RenterDTO save(RenterDTO renterDTO) {
        log.debug("Request to save Renter : {}", renterDTO);
        Renter renter = renterMapper.renterDTOToRenter(renterDTO);
        renter = renterRepository.save(renter);
        RenterDTO result = renterMapper.renterToRenterDTO(renter);
        return result;
    }

    /**
     *  Get all the renters.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<RenterDTO> findAll() {
        log.debug("Request to get all Renters");
        List<RenterDTO> result = renterRepository.findAll().stream()
            .map(renterMapper::renterToRenterDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one renter by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public RenterDTO findOne(Long id) {
        log.debug("Request to get Renter : {}", id);
        Renter renter = renterRepository.findOne(id);
        RenterDTO renterDTO = renterMapper.renterToRenterDTO(renter);
        return renterDTO;
    }

    /**
     *  Delete the  renter by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Renter : {}", id);
        renterRepository.delete(id);
    }
}
