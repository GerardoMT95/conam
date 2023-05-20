/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import it.csi.conam.conambl.business.service.verbale.UtilsSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.integration.repositories.CnmTSoggettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilsSoggettoImpl implements UtilsSoggetto {

	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;

	@Override
	public CnmTSoggetto validateAndGetCnmTSoggetto(Integer idSoggetto) {
		if (idSoggetto == null)
			throw new IllegalArgumentException("idVerbale non valorizzato");

		CnmTSoggetto cnmTSoggetto = cnmTSoggettoRepository.findOne(idSoggetto);
		if (cnmTSoggetto == null)
			throw new IllegalArgumentException("cnmTSoggetto non valorizzato");

		return cnmTSoggetto;
	}
}
