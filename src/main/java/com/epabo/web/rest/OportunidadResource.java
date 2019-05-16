package com.epabo.web.rest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epabo.domain.Oportunidad;
import com.epabo.repository.OportunidadRepository;
import com.epabo.service.GastoOportunidadService;
import com.epabo.web.rest.errors.BadRequestAlertException;
import com.epabo.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Oportunidad.
 */
@RestController
@RequestMapping("/api")
public class OportunidadResource {

    private final Logger log = LoggerFactory.getLogger(OportunidadResource.class);
    
    @Autowired
    GastoOportunidadService oportunidadService;

    private static final String ENTITY_NAME = "oportunidad";

    private final OportunidadRepository oportunidadRepository;

    public OportunidadResource(OportunidadRepository oportunidadRepository) {
        this.oportunidadRepository = oportunidadRepository;
    }

    /**
     * POST  /oportunidads : Create a new oportunidad.
     *
     * @param oportunidad the oportunidad to create
     * @return the ResponseEntity with status 201 (Created) and with body the new oportunidad, or with status 400 (Bad Request) if the oportunidad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/oportunidads")
    public ResponseEntity<Oportunidad> createOportunidad(@Valid @RequestBody Oportunidad oportunidad) throws URISyntaxException {
        log.debug("REST request to save Oportunidad : {}", oportunidad);
        if (oportunidad.getId() != null) {
            throw new BadRequestAlertException("A new oportunidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Oportunidad result = oportunidadRepository.save(oportunidad);
        return ResponseEntity.created(new URI("/api/oportunidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /oportunidads : Updates an existing oportunidad.
     *
     * @param oportunidad the oportunidad to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated oportunidad,
     * or with status 400 (Bad Request) if the oportunidad is not valid,
     * or with status 500 (Internal Server Error) if the oportunidad couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/oportunidads")
    public ResponseEntity<Oportunidad> updateOportunidad(@Valid @RequestBody Oportunidad oportunidad) throws URISyntaxException {
        log.debug("REST request to update Oportunidad : {}", oportunidad);
        if (oportunidad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Oportunidad result = oportunidadRepository.save(oportunidad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, oportunidad.getId().toString()))
            .body(result);
    }

    /**
     * GET  /oportunidads : get all the oportunidads.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of oportunidads in body
     */
    @GetMapping("/oportunidads")
    public List<Oportunidad> getAllOportunidads() {
        log.debug("REST request to get all Oportunidads");
        return oportunidadRepository.findAll();
    }

    /**
     * GET  /oportunidads/:id : get the "id" oportunidad.
     *
     * @param id the id of the oportunidad to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the oportunidad, or with status 404 (Not Found)
     */
    @GetMapping("/oportunidads/{id}")
    public ResponseEntity<Oportunidad> getOportunidad(@PathVariable Long id) {
        log.debug("REST request to get Oportunidad : {}", id);
        Optional<Oportunidad> oportunidad = oportunidadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(oportunidad);
    }
    
    @GetMapping("/oportunidads/getForUser/{id}")
    public List<Oportunidad> getForUser(@PathVariable Long id){
    	return oportunidadService.getAllOportunidades(id);
    }

    /**
     * DELETE  /oportunidads/:id : delete the "id" oportunidad.
     *
     * @param id the id of the oportunidad to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/oportunidads/{id}")
    public ResponseEntity<Void> deleteOportunidad(@PathVariable Long id) {
        log.debug("REST request to delete Oportunidad : {}", id);
        oportunidadRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
