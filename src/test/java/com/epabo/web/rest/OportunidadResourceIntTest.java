package com.epabo.web.rest;

import com.epabo.EpaboApp;

import com.epabo.domain.Oportunidad;
import com.epabo.repository.OportunidadRepository;
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
 * Test class for the OportunidadResource REST controller.
 *
 * @see OportunidadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpaboApp.class)
public class OportunidadResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UBICACION = "AAAAAAAAAA";
    private static final String UPDATED_UBICACION = "BBBBBBBBBB";

    private static final Long DEFAULT_AUTOR = 1L;
    private static final Long UPDATED_AUTOR = 2L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final String DEFAULT_URL_IMAGEN = "AAAAAAAAAA";
    private static final String UPDATED_URL_IMAGEN = "BBBBBBBBBB";

    @Autowired
    private OportunidadRepository oportunidadRepository;

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

    private MockMvc restOportunidadMockMvc;

    private Oportunidad oportunidad;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OportunidadResource oportunidadResource = new OportunidadResource(oportunidadRepository);
        this.restOportunidadMockMvc = MockMvcBuilders.standaloneSetup(oportunidadResource)
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
    public static Oportunidad createEntity(EntityManager em) {
        Oportunidad oportunidad = new Oportunidad()
            .descripcion(DEFAULT_DESCRIPCION)
            .fecha(DEFAULT_FECHA)
            .ubicacion(DEFAULT_UBICACION)
            .autor(DEFAULT_AUTOR)
            .email(DEFAULT_EMAIL)
            .tags(DEFAULT_TAGS)
            .urlImagen(DEFAULT_URL_IMAGEN);
        return oportunidad;
    }

    @Before
    public void initTest() {
        oportunidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createOportunidad() throws Exception {
        int databaseSizeBeforeCreate = oportunidadRepository.findAll().size();

        // Create the Oportunidad
        restOportunidadMockMvc.perform(post("/api/oportunidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oportunidad)))
            .andExpect(status().isCreated());

        // Validate the Oportunidad in the database
        List<Oportunidad> oportunidadList = oportunidadRepository.findAll();
        assertThat(oportunidadList).hasSize(databaseSizeBeforeCreate + 1);
        Oportunidad testOportunidad = oportunidadList.get(oportunidadList.size() - 1);
        assertThat(testOportunidad.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testOportunidad.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testOportunidad.getUbicacion()).isEqualTo(DEFAULT_UBICACION);
        assertThat(testOportunidad.getAutor()).isEqualTo(DEFAULT_AUTOR);
        assertThat(testOportunidad.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOportunidad.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testOportunidad.getUrlImagen()).isEqualTo(DEFAULT_URL_IMAGEN);
    }

    @Test
    @Transactional
    public void createOportunidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = oportunidadRepository.findAll().size();

        // Create the Oportunidad with an existing ID
        oportunidad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOportunidadMockMvc.perform(post("/api/oportunidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oportunidad)))
            .andExpect(status().isBadRequest());

        // Validate the Oportunidad in the database
        List<Oportunidad> oportunidadList = oportunidadRepository.findAll();
        assertThat(oportunidadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = oportunidadRepository.findAll().size();
        // set the field null
        oportunidad.setDescripcion(null);

        // Create the Oportunidad, which fails.

        restOportunidadMockMvc.perform(post("/api/oportunidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oportunidad)))
            .andExpect(status().isBadRequest());

        List<Oportunidad> oportunidadList = oportunidadRepository.findAll();
        assertThat(oportunidadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUbicacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = oportunidadRepository.findAll().size();
        // set the field null
        oportunidad.setUbicacion(null);

        // Create the Oportunidad, which fails.

        restOportunidadMockMvc.perform(post("/api/oportunidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oportunidad)))
            .andExpect(status().isBadRequest());

        List<Oportunidad> oportunidadList = oportunidadRepository.findAll();
        assertThat(oportunidadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAutorIsRequired() throws Exception {
        int databaseSizeBeforeTest = oportunidadRepository.findAll().size();
        // set the field null
        oportunidad.setAutor(null);

        // Create the Oportunidad, which fails.

        restOportunidadMockMvc.perform(post("/api/oportunidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oportunidad)))
            .andExpect(status().isBadRequest());

        List<Oportunidad> oportunidadList = oportunidadRepository.findAll();
        assertThat(oportunidadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = oportunidadRepository.findAll().size();
        // set the field null
        oportunidad.setEmail(null);

        // Create the Oportunidad, which fails.

        restOportunidadMockMvc.perform(post("/api/oportunidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oportunidad)))
            .andExpect(status().isBadRequest());

        List<Oportunidad> oportunidadList = oportunidadRepository.findAll();
        assertThat(oportunidadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTagsIsRequired() throws Exception {
        int databaseSizeBeforeTest = oportunidadRepository.findAll().size();
        // set the field null
        oportunidad.setTags(null);

        // Create the Oportunidad, which fails.

        restOportunidadMockMvc.perform(post("/api/oportunidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oportunidad)))
            .andExpect(status().isBadRequest());

        List<Oportunidad> oportunidadList = oportunidadRepository.findAll();
        assertThat(oportunidadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOportunidads() throws Exception {
        // Initialize the database
        oportunidadRepository.saveAndFlush(oportunidad);

        // Get all the oportunidadList
        restOportunidadMockMvc.perform(get("/api/oportunidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oportunidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].ubicacion").value(hasItem(DEFAULT_UBICACION.toString())))
            .andExpect(jsonPath("$.[*].autor").value(hasItem(DEFAULT_AUTOR.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS.toString())))
            .andExpect(jsonPath("$.[*].urlImagen").value(hasItem(DEFAULT_URL_IMAGEN.toString())));
    }
    
    @Test
    @Transactional
    public void getOportunidad() throws Exception {
        // Initialize the database
        oportunidadRepository.saveAndFlush(oportunidad);

        // Get the oportunidad
        restOportunidadMockMvc.perform(get("/api/oportunidads/{id}", oportunidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(oportunidad.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.ubicacion").value(DEFAULT_UBICACION.toString()))
            .andExpect(jsonPath("$.autor").value(DEFAULT_AUTOR.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS.toString()))
            .andExpect(jsonPath("$.urlImagen").value(DEFAULT_URL_IMAGEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOportunidad() throws Exception {
        // Get the oportunidad
        restOportunidadMockMvc.perform(get("/api/oportunidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOportunidad() throws Exception {
        // Initialize the database
        oportunidadRepository.saveAndFlush(oportunidad);

        int databaseSizeBeforeUpdate = oportunidadRepository.findAll().size();

        // Update the oportunidad
        Oportunidad updatedOportunidad = oportunidadRepository.findById(oportunidad.getId()).get();
        // Disconnect from session so that the updates on updatedOportunidad are not directly saved in db
        em.detach(updatedOportunidad);
        updatedOportunidad
            .descripcion(UPDATED_DESCRIPCION)
            .fecha(UPDATED_FECHA)
            .ubicacion(UPDATED_UBICACION)
            .autor(UPDATED_AUTOR)
            .email(UPDATED_EMAIL)
            .tags(UPDATED_TAGS)
            .urlImagen(UPDATED_URL_IMAGEN);

        restOportunidadMockMvc.perform(put("/api/oportunidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOportunidad)))
            .andExpect(status().isOk());

        // Validate the Oportunidad in the database
        List<Oportunidad> oportunidadList = oportunidadRepository.findAll();
        assertThat(oportunidadList).hasSize(databaseSizeBeforeUpdate);
        Oportunidad testOportunidad = oportunidadList.get(oportunidadList.size() - 1);
        assertThat(testOportunidad.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testOportunidad.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testOportunidad.getUbicacion()).isEqualTo(UPDATED_UBICACION);
        assertThat(testOportunidad.getAutor()).isEqualTo(UPDATED_AUTOR);
        assertThat(testOportunidad.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOportunidad.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testOportunidad.getUrlImagen()).isEqualTo(UPDATED_URL_IMAGEN);
    }

    @Test
    @Transactional
    public void updateNonExistingOportunidad() throws Exception {
        int databaseSizeBeforeUpdate = oportunidadRepository.findAll().size();

        // Create the Oportunidad

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOportunidadMockMvc.perform(put("/api/oportunidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oportunidad)))
            .andExpect(status().isBadRequest());

        // Validate the Oportunidad in the database
        List<Oportunidad> oportunidadList = oportunidadRepository.findAll();
        assertThat(oportunidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOportunidad() throws Exception {
        // Initialize the database
        oportunidadRepository.saveAndFlush(oportunidad);

        int databaseSizeBeforeDelete = oportunidadRepository.findAll().size();

        // Delete the oportunidad
        restOportunidadMockMvc.perform(delete("/api/oportunidads/{id}", oportunidad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Oportunidad> oportunidadList = oportunidadRepository.findAll();
        assertThat(oportunidadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Oportunidad.class);
        Oportunidad oportunidad1 = new Oportunidad();
        oportunidad1.setId(1L);
        Oportunidad oportunidad2 = new Oportunidad();
        oportunidad2.setId(oportunidad1.getId());
        assertThat(oportunidad1).isEqualTo(oportunidad2);
        oportunidad2.setId(2L);
        assertThat(oportunidad1).isNotEqualTo(oportunidad2);
        oportunidad1.setId(null);
        assertThat(oportunidad1).isNotEqualTo(oportunidad2);
    }
}
