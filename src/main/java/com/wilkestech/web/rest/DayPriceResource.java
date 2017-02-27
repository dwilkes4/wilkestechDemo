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
import com.wilkestech.service.DayPriceService;
import com.wilkestech.service.dto.DayPriceDTO;
import com.wilkestech.web.rest.util.HeaderUtil;

/**
 * REST controller for managing DayPrice.
 */
@RestController
@RequestMapping("/api")
public class DayPriceResource {

    private final Logger log = LoggerFactory.getLogger(DayPriceResource.class);
        
    @Inject
    private DayPriceService dayPriceService;

    /**
     * POST  /day-prices : Create a new dayPrice.
     *
     * @param dayPriceDTO the dayPriceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dayPriceDTO, or with status 400 (Bad Request) if the dayPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/day-prices",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DayPriceDTO> createDayPrice(@RequestBody DayPriceDTO dayPriceDTO) throws URISyntaxException {
        log.debug("REST request to save DayPrice : {}", dayPriceDTO);
        if (dayPriceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dayPrice", "idexists", "A new dayPrice cannot already have an ID")).body(null);
        }
        DayPriceDTO result = dayPriceService.save(dayPriceDTO);
        return ResponseEntity.created(new URI("/api/day-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dayPrice", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /day-prices : Updates an existing dayPrice.
     *
     * @param dayPriceDTO the dayPriceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dayPriceDTO,
     * or with status 400 (Bad Request) if the dayPriceDTO is not valid,
     * or with status 500 (Internal Server Error) if the dayPriceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/day-prices",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DayPriceDTO> updateDayPrice(@RequestBody DayPriceDTO dayPriceDTO) throws URISyntaxException {
        log.debug("REST request to update DayPrice : {}", dayPriceDTO);
        if (dayPriceDTO.getId() == null) {
            return createDayPrice(dayPriceDTO);
        }
        DayPriceDTO result = dayPriceService.save(dayPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dayPrice", dayPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /day-prices : get all the dayPrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dayPrices in body
     */
    @RequestMapping(value = "/day-prices",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DayPriceDTO> getAllDayPrices() {
        log.debug("REST request to get all DayPrices");
        return dayPriceService.findAll();
    }

    /**
     * GET  /day-prices/:id : get the "id" dayPrice.
     *
     * @param id the id of the dayPriceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dayPriceDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/day-prices/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DayPriceDTO> getDayPrice(@PathVariable Long id) {
        log.debug("REST request to get DayPrice : {}", id);
        DayPriceDTO dayPriceDTO = dayPriceService.findOne(id);
        return Optional.ofNullable(dayPriceDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /day-prices/:id : delete the "id" dayPrice.
     *
     * @param id the id of the dayPriceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/day-prices/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDayPrice(@PathVariable Long id) {
        log.debug("REST request to delete DayPrice : {}", id);
        dayPriceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dayPrice", id.toString())).build();
    }

}
