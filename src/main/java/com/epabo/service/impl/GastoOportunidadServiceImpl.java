package com.epabo.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epabo.domain.Gasto;
import com.epabo.domain.Oportunidad;
import com.epabo.repository.GastoRepository;
import com.epabo.repository.OportunidadRepository;
import com.epabo.service.GastoOportunidadService;

@Service
@Transactional
public class GastoOportunidadServiceImpl implements GastoOportunidadService {

	private final Logger log = LoggerFactory.getLogger(GastoOportunidadServiceImpl.class);
	
	@Autowired
	GastoRepository gastoRepository;
	
	@Autowired
	OportunidadRepository oportunidadRepository;

	public List<Oportunidad> getAllOportunidades(Long id) {
		log.debug("Buscando oportunidades");
		List<Oportunidad> oportunidades = new ArrayList<Oportunidad>();
		List<Gasto> gastos = gastoRepository.findAll();
		for(Gasto gasto:gastos) {
			if(gasto.getUserId().equals(id)) {
				for(Oportunidad oportunidad: oportunidadRepository.findAll()) {
					List<String> tags = Arrays.asList(oportunidad.getTags().split(","));
					if(tags.stream().anyMatch(s -> gasto.getDescripcion().contains(s))) {
						oportunidades.add(oportunidad);
					}
				}
			}
		}
		
		return oportunidades;

	}

}
