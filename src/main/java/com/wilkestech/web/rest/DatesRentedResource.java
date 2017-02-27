package com.wilkestech.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.wilkestech.service.DatesRentedService;
import com.wilkestech.service.dto.DatesRentedDTO;
import com.wilkestech.web.rest.util.HeaderUtil;

/**
 * REST controller for managing DatesRented.
 */
@RestController
@RequestMapping("/api")
public class DatesRentedResource {

    private final Logger log = LoggerFactory.getLogger(DatesRentedResource.class);
        
    @Inject
    private DatesRentedService datesRentedService;

    /**
     * POST  /dates-renteds : Create a new datesRented.
     *
     * @param datesRentedDTO the datesRentedDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new datesRentedDTO, or with status 400 (Bad Request) if the datesRented has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/dates-renteds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DatesRentedDTO> createDatesRented(@RequestBody DatesRentedDTO datesRentedDTO) throws URISyntaxException {
        log.debug("REST request to save DatesRented : {}", datesRentedDTO);
        if (datesRentedDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("datesRented", "idexists", "A new datesRented cannot already have an ID")).body(null);
        }
        DatesRentedDTO result = datesRentedService.save(datesRentedDTO);
        return ResponseEntity.created(new URI("/api/dates-renteds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("datesRented", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dates-renteds : Updates an existing datesRented.
     *
     * @param datesRentedDTO the datesRentedDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated datesRentedDTO,
     * or with status 400 (Bad Request) if the datesRentedDTO is not valid,
     * or with status 500 (Internal Server Error) if the datesRentedDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/dates-renteds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DatesRentedDTO> updateDatesRented(@RequestBody DatesRentedDTO datesRentedDTO) throws URISyntaxException {
        log.debug("REST request to update DatesRented : {}", datesRentedDTO);
        if (datesRentedDTO.getId() == null) {
            return createDatesRented(datesRentedDTO);
        }
        DatesRentedDTO result = datesRentedService.save(datesRentedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("datesRented", datesRentedDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dates-renteds : get all the datesRenteds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of datesRenteds in body
     */
    @RequestMapping(value = "/dates-renteds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DatesRentedDTO> getAllDatesRenteds() {
        log.debug("REST request to get all DatesRenteds");
        return datesRentedService.findAll();
    }

    /**
     * GET  /dates-renteds/:id : get the "id" datesRented.
     *
     * @param id the id of the datesRentedDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the datesRentedDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/dates-renteds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DatesRentedDTO> getDatesRented(@PathVariable Long id) {
        log.debug("REST request to get DatesRented : {}", id);
        DatesRentedDTO datesRentedDTO = datesRentedService.findOne(id);
        return Optional.ofNullable(datesRentedDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dates-renteds/:id : delete the "id" datesRented.
     *
     * @param id the id of the datesRentedDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/dates-renteds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDatesRented(@PathVariable Long id) {
        log.debug("REST request to delete DatesRented : {}", id);
        datesRentedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("datesRented", id.toString())).build();
    }

}
