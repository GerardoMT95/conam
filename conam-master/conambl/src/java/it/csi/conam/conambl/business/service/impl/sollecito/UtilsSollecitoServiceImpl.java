/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.sollecito;

import it.csi.conam.conambl.business.service.sollecito.UtilsSollecitoService;
import it.csi.conam.conambl.integration.entity.CnmTSollecito;
import it.csi.conam.conambl.integration.repositories.CnmTSollecitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilsSollecitoServiceImpl implements UtilsSollecitoService {

	@Autowired
	private CnmTSollecitoRepository cnmTSollecitoRepository;

	@Override
	public CnmTSollecito validateAndGetCnmTSollecito(Integer idSollecito) {
		if (idSollecito == null)
			throw new IllegalArgumentException("id ==null");
		CnmTSollecito cnmTSollecito = cnmTSollecitoRepository.findOne(idSollecito);
		if (cnmTSollecito == null)
			throw new IllegalArgumentException("cnmTSollecito ==null");
		return cnmTSollecito;
	}
}
