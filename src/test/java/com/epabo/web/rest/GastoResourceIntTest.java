package com.epabo.web.rest;

import com.epabo.EpaboApp;

import com.epabo.domain.Gasto;
import com.epabo.repository.GastoRepository;
import com.epabo.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.epabo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GastoResource REST controller.
 *
 * @see GastoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpaboApp.class)
public class GastoResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_MODIFICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MODIFICACION = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MONTO_GASTO = 1D;
    private static final Double UPDATED_MONTO_GASTO = 2D;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restGastoMockMvc;

    private Gasto gasto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GastoResource gastoResource = new GastoResource(gastoRepository);
        this.restGastoMockMvc = MockMvcBuilders.standaloneSetup(gastoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gasto createEntity(EntityManager em) {
        Gasto gasto = new Gasto()
            .descripcion(DEFAULT_DESCRIPCION)
            .fecha(DEFAULT_FECHA)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .montoGasto(DEFAULT_MONTO_GASTO)
            .userId(DEFAULT_USER_ID);
        return gasto;
    }

    @Before
    public void initTest() {
        gasto = createEntity(em);
    }

    @Test
    @Transactional
    public void createGasto() throws Exception {
        int databaseSizeBeforeCreate = gastoRepository.findAll().size();

        // Create the Gasto
        restGastoMockMvc.perform(post("/api/gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasto)))
            .andExpect(status().isCreated());

        // Validate the Gasto in the database
        List<Gasto> gastoList = gastoRepository.findAll();
        assertThat(gastoList).hasSize(databaseSizeBeforeCreate + 1);
        Gasto testGasto = gastoList.get(gastoList.size() - 1);
        assertThat(testGasto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testGasto.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testGasto.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testGasto.getMontoGasto()).isEqualTo(DEFAULT_MONTO_GASTO);
        assertThat(testGasto.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createGastoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gastoRepository.findAll().size();

        // Create the Gasto with an existing ID
        gasto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGastoMockMvc.perform(post("/api/gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasto)))
            .andExpect(status().isBadRequest());

        // Validate the Gasto in the database
        List<Gasto> gastoList = gastoRepository.findAll();
        assertThat(gastoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = gastoRepository.findAll().size();
        // set the field null
        gasto.setDescripcion(null);

        // Create the Gasto, which fails.

        restGastoMockMvc.perform(post("/api/gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasto)))
            .andExpect(status().isBadRequest());

        List<Gasto> gastoList = gastoRepository.findAll();
        assertThat(gastoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontoGastoIsRequired() throws Exception {
        int databaseSizeBeforeTest = gastoRepository.findAll().size();
        // set the field null
        gasto.setMontoGasto(null);

        // Create the Gasto, which fails.

        restGastoMockMvc.perform(post("/api/gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasto)))
            .andExpect(status().isBadRequest());

        List<Gasto> gastoList = gastoRepository.findAll();
        assertThat(gastoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = gastoRepository.findAll().size();
        // set the field null
        gasto.setUserId(null);

        // Create the Gasto, which fails.

        restGastoMockMvc.perform(post("/api/gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasto)))
            .andExpect(status().isBadRequest());

        List<Gasto> gastoList = gastoRepository.findAll();
        assertThat(gastoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGastos() throws Exception {
        // Initialize the database
        gastoRepository.saveAndFlush(gasto);

        // Get all the gastoList
        restGastoMockMvc.perform(get("/api/gastos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gasto.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].montoGasto").value(hasItem(DEFAULT_MONTO_GASTO.doubleValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getGasto() throws Exception {
        // Initialize the database
        gastoRepository.saveAndFlush(gasto);

        // Get the gasto
        restGastoMockMvc.perform(get("/api/gastos/{id}", gasto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gasto.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.montoGasto").value(DEFAULT_MONTO_GASTO.doubleValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGasto() throws Exception {
        // Get the gasto
        restGastoMockMvc.perform(get("/api/gastos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGasto() throws Exception {
        // Initialize the database
        gastoRepository.saveAndFlush(gasto);

        int databaseSizeBeforeUpdate = gastoRepository.findAll().size();

        // Update the gasto
        Gasto updatedGasto = gastoRepository.findById(gasto.getId()).get();
        // Disconnect from session so that the updates on updatedGasto are not directly saved in db
        em.detach(updatedGasto);
        updatedGasto
            .descripcion(UPDATED_DESCRIPCION)
            .fecha(UPDATED_FECHA)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .montoGasto(UPDATED_MONTO_GASTO)
            .userId(UPDATED_USER_ID);

        restGastoMockMvc.perform(put("/api/gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGasto)))
            .andExpect(status().isOk());

        // Validate the Gasto in the database
        List<Gasto> gastoList = gastoRepository.findAll();
        assertThat(gastoList).hasSize(databaseSizeBeforeUpdate);
        Gasto testGasto = gastoList.get(gastoList.size() - 1);
        assertThat(testGasto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testGasto.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testGasto.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testGasto.getMontoGasto()).isEqualTo(UPDATED_MONTO_GASTO);
        assertThat(testGasto.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingGasto() throws Exception {
        int databaseSizeBeforeUpdate = gastoRepository.findAll().size();

        // Create the Gasto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGastoMockMvc.perform(put("/api/gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gasto)))
            .andExpect(status().isBadRequest());

        // Validate the Gasto in the database
        List<Gasto> gastoList = gastoRepository.findAll();
        assertThat(gastoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGasto() throws Exception {
        // Initialize the database
        gastoRepository.saveAndFlush(gasto);

        int databaseSizeBeforeDelete = gastoRepository.findAll().size();

        // Delete the gasto
        restGastoMockMvc.perform(delete("/api/gastos/{id}", gasto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gasto> gastoList = gastoRepository.findAll();
        assertThat(gastoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gasto.class);
        Gasto gasto1 = new Gasto();
        gasto1.setId(1L);
        Gasto gasto2 = new Gasto();
        gasto2.setId(gasto1.getId());
        assertThat(gasto1).isEqualTo(gasto2);
        gasto2.setId(2L);
        assertThat(gasto1).isNotEqualTo(gasto2);
        gasto1.setId(null);
        assertThat(gasto1).isNotEqualTo(gasto2);
    }
}
