/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.scheduled.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.CnmDStatoAllegato;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.repositories.CnmDStatoAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoRepository;
import it.csi.conam.conambl.scheduled.AllegatoScheduledService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 mar 2019
 */
@Service
public class AllegatoScheduledServiceImpl implements AllegatoScheduledService {

	protected static Logger logger = Logger.getLogger(AllegatoScheduledServiceImpl.class);

	@Autowired
	private CnmDStatoAllegatoRepository cnmDStatoAllegatoRepository;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<CnmTAllegato> updateCnmDStatoAllegatoInCoda(List<CnmTAllegato> cnmTAllegatos) {
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmDStatoAllegato cnmDStatoAllegatoInCoda = cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_IN_CODA);
		for (CnmTAllegato cnmTAllegato : cnmTAllegatos) {
			if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_AVVIA_SPOSTAMENTO_ACTA) {
				logger.info("Trovato allegato con id" + cnmTAllegato.getIdAllegato() + "in stato:" + cnmTAllegato.getCnmDStatoAllegato().getDescStatoAllegato());
				cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoInCoda);
				cnmTAllegato.setDataOraUpdate(now);
			}
		}
		return (List<CnmTAllegato>) cnmTAllegatoRepository.save(cnmTAllegatos);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public CnmTAllegato updateCnmDStatoAllegato(CnmTAllegato cnmTAllegato) {
		return cnmTAllegatoRepository.save(cnmTAllegato);
	}

}
