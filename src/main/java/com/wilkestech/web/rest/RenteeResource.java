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
import com.wilkestech.service.RenteeService;
import com.wilkestech.service.dto.RenteeDTO;
import com.wilkestech.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Rentee.
 */
@RestController
@RequestMapping("/api")
public class RenteeResource {

    private final Logger log = LoggerFactory.getLogger(RenteeResource.class);
        
    @Inject
    private RenteeService renteeService;

    /**
     * POST  /rentees : Create a new rentee.
     *
     * @param renteeDTO the renteeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new renteeDTO, or with status 400 (Bad Request) if the rentee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rentees",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RenteeDTO> createRentee(@RequestBody RenteeDTO renteeDTO) throws URISyntaxException {
        log.debug("REST request to save Rentee : {}", renteeDTO);
        if (renteeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rentee", "idexists", "A new rentee cannot already have an ID")).body(null);
        }
        RenteeDTO result = renteeService.save(renteeDTO);
        return ResponseEntity.created(new URI("/api/rentees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rentee", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rentees : Updates an existing rentee.
     *
     * @param renteeDTO the renteeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated renteeDTO,
     * or with status 400 (Bad Request) if the renteeDTO is not valid,
     * or with status 500 (Internal Server Error) if the renteeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/rentees",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RenteeDTO> updateRentee(@RequestBody RenteeDTO renteeDTO) throws URISyntaxException {
        log.debug("REST request to update Rentee : {}", renteeDTO);
        if (renteeDTO.getId() == null) {
            return createRentee(renteeDTO);
        }
        RenteeDTO result = renteeService.save(renteeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rentee", renteeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rentees : get all the rentees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rentees in body
     */
    @RequestMapping(value = "/rentees",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RenteeDTO> getAllRentees() {
        log.debug("REST request to get all Rentees");
        return renteeService.findAll();
    }

    /**
     * GET  /rentees/:id : get the "id" rentee.
     *
     * @param id the id of the renteeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the renteeDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/rentees/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RenteeDTO> getRentee(@PathVariable Long id) {
        log.debug("REST request to get Rentee : {}", id);
        RenteeDTO renteeDTO = renteeService.findOne(id);
        return Optional.ofNullable(renteeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rentees/:id : delete the "id" rentee.
     *
     * @param id the id of the renteeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/rentees/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRentee(@PathVariable Long id) {
        log.debug("REST request to delete Rentee : {}", id);
        renteeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rentee", id.toString())).build();
    }

}
