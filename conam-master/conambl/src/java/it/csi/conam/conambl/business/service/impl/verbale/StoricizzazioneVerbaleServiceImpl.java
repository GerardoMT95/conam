/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.verbale.StoricizzazioneVerbaleService;
import it.csi.conam.conambl.integration.entity.CnmSStatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.repositories.CnmSStatoVerbaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class StoricizzazioneVerbaleServiceImpl implements StoricizzazioneVerbaleService {

	@Autowired
	private CnmSStatoVerbaleRepository cnmSStatoVerbaleRepository;
	@Autowired
	private UtilsDate utilsDate;

	@Override
	@Transactional
	public void storicizzaStatoVerbale(CnmTVerbale cnmTVerbale, CnmTUser cnmTUser) {
		CnmSStatoVerbale cnmSStatoVerbale = new CnmSStatoVerbale();
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

		cnmSStatoVerbale.setDataOraInsert(now);
		cnmSStatoVerbale.setCnmDStatoVerbale(cnmTVerbale.getCnmDStatoVerbale());
		cnmSStatoVerbale.setCnmTUser(cnmTUser);
		cnmSStatoVerbale.setCnmTVerbale(cnmTVerbale);

		cnmSStatoVerbaleRepository.save(cnmSStatoVerbale);

	}
}
