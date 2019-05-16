package com.epabo.web.rest;
import com.epabo.domain.Gasto;
import com.epabo.repository.GastoRepository;
import com.epabo.web.rest.errors.BadRequestAlertException;
import com.epabo.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Gasto.
 */
@RestController
@RequestMapping("/api")
public class GastoResource {

    private final Logger log = LoggerFactory.getLogger(GastoResource.class);

    private static final String ENTITY_NAME = "gasto";

    private final GastoRepository gastoRepository;

    public GastoResource(GastoRepository gastoRepository) {
        this.gastoRepository = gastoRepository;
    }

    /**
     * POST  /gastos : Create a new gasto.
     *
     * @param gasto the gasto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gasto, or with status 400 (Bad Request) if the gasto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gastos")
    public ResponseEntity<Gasto> createGasto(@Valid @RequestBody Gasto gasto) throws URISyntaxException {
        log.debug("REST request to save Gasto : {}", gasto);
        if (gasto.getId() != null) {
            throw new BadRequestAlertException("A new gasto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gasto result = gastoRepository.save(gasto);
        return ResponseEntity.created(new URI("/api/gastos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gastos : Updates an existing gasto.
     *
     * @param gasto the gasto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gasto,
     * or with status 400 (Bad Request) if the gasto is not valid,
     * or with status 500 (Internal Server Error) if the gasto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gastos")
    public ResponseEntity<Gasto> updateGasto(@Valid @RequestBody Gasto gasto) throws URISyntaxException {
        log.debug("REST request to update Gasto : {}", gasto);
        if (gasto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Gasto result = gastoRepository.save(gasto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gasto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gastos : get all the gastos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gastos in body
     */
    @GetMapping("/gastos")
    public List<Gasto> getAllGastos() {
        log.debug("REST request to get all Gastos");
        return gastoRepository.findAll();
    }

    /**
     * GET  /gastos/:id : get the "id" gasto.
     *
     * @param id the id of the gasto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gasto, or with status 404 (Not Found)
     */
    @GetMapping("/gastos/{id}")
    public ResponseEntity<Gasto> getGasto(@PathVariable Long id) {
        log.debug("REST request to get Gasto : {}", id);
        Optional<Gasto> gasto = gastoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gasto);
    }

    /**
     * DELETE  /gastos/:id : delete the "id" gasto.
     *
     * @param id the id of the gasto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gastos/{id}")
    public ResponseEntity<Void> deleteGasto(@PathVariable Long id) {
        log.debug("REST request to delete Gasto : {}", id);
        gastoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
