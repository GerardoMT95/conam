/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.util;

import it.csi.conam.conambl.business.service.util.UtilsCnmCProprietaService;
import it.csi.conam.conambl.integration.entity.CnmCProprieta;
import it.csi.conam.conambl.integration.entity.CnmCProprieta.PropKey;
import it.csi.conam.conambl.integration.repositories.CnmCProprietaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UtilsCnmCProprietaServiceImpl implements UtilsCnmCProprietaService {

	private Map<String, String> propertiesMap;
	
	@Autowired
	private CnmCProprietaRepository cnmCProprietaRepository;
	
	@Override
	public String getProprieta(PropKey propName) {

		if(propertiesMap== null) {
			Iterable<CnmCProprieta> findAll = cnmCProprietaRepository.findAll();
			propertiesMap = new HashMap<>();
			for (CnmCProprieta cnmCProprieta : findAll) {
				propertiesMap.put(cnmCProprieta.getNome(), cnmCProprieta.getValore());
			}
		}
		return propertiesMap.get(propName.name());
	}

}
