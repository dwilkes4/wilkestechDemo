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
import com.wilkestech.service.RenterService;
import com.wilkestech.service.dto.RenterDTO;
import com.wilkestech.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Renter.
 */
@RestController
@RequestMapping("/api")
public class RenterResource {

    private final Logger log = LoggerFactory.getLogger(RenterResource.class);
        
    @Inject
    private RenterService renterService;

    /**
     * POST  /renters : Create a new renter.
     *
     * @param renterDTO the renterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new renterDTO, or with status 400 (Bad Request) if the renter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/renters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RenterDTO> createRenter(@RequestBody RenterDTO renterDTO) throws URISyntaxException {
        log.debug("REST request to save Renter : {}", renterDTO);
        if (renterDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("renter", "idexists", "A new renter cannot already have an ID")).body(null);
        }
        RenterDTO result = renterService.save(renterDTO);
        return ResponseEntity.created(new URI("/api/renters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("renter", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /renters : Updates an existing renter.
     *
     * @param renterDTO the renterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated renterDTO,
     * or with status 400 (Bad Request) if the renterDTO is not valid,
     * or with status 500 (Internal Server Error) if the renterDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/renters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RenterDTO> updateRenter(@RequestBody RenterDTO renterDTO) throws URISyntaxException {
        log.debug("REST request to update Renter : {}", renterDTO);
        if (renterDTO.getId() == null) {
            return createRenter(renterDTO);
        }
        RenterDTO result = renterService.save(renterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("renter", renterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /renters : get all the renters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of renters in body
     */
    @RequestMapping(value = "/renters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RenterDTO> getAllRenters() {
        log.debug("REST request to get all Renters");
        return renterService.findAll();
    }

    /**
     * GET  /renters/:id : get the "id" renter.
     *
     * @param id the id of the renterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the renterDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/renters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RenterDTO> getRenter(@PathVariable Long id) {
        log.debug("REST request to get Renter : {}", id);
        RenterDTO renterDTO = renterService.findOne(id);
        return Optional.ofNullable(renterDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /renters/:id : delete the "id" renter.
     *
     * @param id the id of the renterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/renters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRenter(@PathVariable Long id) {
        log.debug("REST request to delete Renter : {}", id);
        renterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("renter", id.toString())).build();
    }

}
