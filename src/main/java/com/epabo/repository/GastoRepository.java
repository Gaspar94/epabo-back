package com.epabo.repository;

import com.epabo.domain.Gasto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Gasto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {

}
