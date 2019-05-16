package com.epabo.repository;

import com.epabo.domain.Oportunidad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Oportunidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OportunidadRepository extends JpaRepository<Oportunidad, Long> {

}
