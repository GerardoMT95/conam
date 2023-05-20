/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.scheduled.impl;

import it.csi.conam.conambl.business.service.ordinanza.OrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.UtilsOrdinanza;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.scheduled.SorisScheduledService;
import it.csi.conam.conambl.security.UserDetails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SorisScheduledServiceImpl implements SorisScheduledService {

	protected static Logger logger = Logger.getLogger(SorisScheduledServiceImpl.class);

	@Autowired
	private CnmTRiscossioneRepository cnmTRiscossioneRepository;
	@Autowired
	private CnmDStatoRiscossioneRepository cnmDStatoRiscossioneRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmDStatoOrdVerbSogRepository cnmDStatoOrdVerbSogRepository;
	@Autowired
	private CnmTSollecitoRepository cnmTSollecitoRepository;
	@Autowired
	private CnmDStatoSollecitoRepository cnmDStatoSollecitoRepository;
	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CnmDStatoOrdinanzaRepository cnmDStatoOrdinanzaRepository;
	@Autowired
	private CnmTRataRepository cnmTRataRepository;
	@Autowired
	private CnmTPianoRateRepository cnmTPianoRateRepository;
	@Autowired
	private CnmDStatoPianoRateRepository cnmDStatoPianoRateRepository;
	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;
	@Autowired
	private CnmDStatoRataRepository cnmDStatoRataRepository;
	@Autowired
	private CnmDStatoRecordRepository cnmDStatoRecordRepository;
	@Autowired
	private CnmTRecordRitornoRepository cnmTRecordRitornoRepository;
	@Autowired
	private OrdinanzaService ordinanzaService;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	/*@Autowired
	private CnmRSollecitoSoggRataRepository cnmRSollecitoSoggRataRepository;*/
	@Autowired
	private CnmDTipoSollecitoRepository cnmDTipoSollecitoRepository;

	@Autowired
	private UtilsOrdinanza utilsOrdinanza;

	@Override
	public void elaboraStatoDellaRiscossione() {
		List<CnmTRiscossione> cnmTRiscossioneList = cnmTRiscossioneRepository.findByCnmTRecordCnmTRiscossione();
		Map<Integer, CnmTRiscossione> mapRis = new HashMap<Integer, CnmTRiscossione>();

		if (cnmTRiscossioneList != null && cnmTRiscossioneList.size() > 0) {
			for (CnmTRiscossione nmTRiscossione : cnmTRiscossioneList) {

				if (mapRis.isEmpty() || !mapRis.containsKey(nmTRiscossione.getIdRiscossione())) {
					mapRis.put(nmTRiscossione.getIdRiscossione(), nmTRiscossione);

					CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.findByCnmTRiscossioneByIdRiscossione(nmTRiscossione.getIdRiscossione());
					if (cnmROrdinanzaVerbSog != null) {
						logger.info("cnmROrdinanzaVerbSog[id] :: " + cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog() + " trovata.");

						CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findByIdOrdinanzaVerbSog(cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog());
						if (cnmTOrdinanza != null && cnmTOrdinanza.getCnmDStatoOrdinanza().getIdStatoOrdinanza() == 1 || cnmTOrdinanza.getCnmDStatoOrdinanza().getIdStatoOrdinanza() == 2
								|| cnmTOrdinanza.getCnmDStatoOrdinanza().getIdStatoOrdinanza() == 3 || cnmTOrdinanza.getCnmDStatoOrdinanza().getIdStatoOrdinanza() == 4) {

							// ************************** UPDATE STATO ORDINANZA
							logger.info("update stato - cnmTOrdinanza[id] :: " + cnmTOrdinanza.getIdOrdinanza());
							UserDetails user = SecurityUtils.getUser();
							CnmTUser cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
							ordinanzaService.saveSStatoOrdinanza(cnmTOrdinanza, cnmTUser);
							CnmDStatoOrdinanza cnmDStatoOrdinanza = cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_RISCOSSO_SORIS);
							cnmTOrdinanza.setCnmDStatoOrdinanza(cnmDStatoOrdinanza);
							cnmTOrdinanzaRepository.save(cnmTOrdinanza);
							// ***************************************************
						}

						// 20210402_LC tipo sollecito
						CnmDTipoSollecito cnmDTipoSollecito = cnmDTipoSollecitoRepository.findOne(Constants.ID_TIPO_SOLLECITO_ORDINANZA);
						List<CnmTSollecito> cnmTSollecitoList = cnmTSollecitoRepository.findByCnmROrdinanzaVerbSogAndCnmDTipoSollecito(cnmROrdinanzaVerbSog, cnmDTipoSollecito);
						//List<CnmTSollecito> cnmTSollecitoList = cnmTSollecitoRepository.findByCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);
						
						
						if (cnmTSollecitoList != null && cnmTSollecitoList.size() > 0) {	
							
							for (CnmTSollecito cnmTSollecito : cnmTSollecitoList) {
								if (cnmTSollecito.getCnmDStatoSollecito().getIdStatoSollecito() == 1 || cnmTSollecito.getCnmDStatoSollecito().getIdStatoSollecito() == 2
										|| cnmTSollecito.getCnmDStatoSollecito().getIdStatoSollecito() == 3 || cnmTSollecito.getCnmDStatoSollecito().getIdStatoSollecito() == 4) {

									// ********************_UPDATE_STATO_SOLLECITO
									logger.info("update stato - cnmTSollecito[id] :: " + cnmTSollecito.getIdSollecito());
									CnmDStatoSollecito cnmDStatoSollecito = cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_ESTINTO_SOLLECITO);
									cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecito);
									cnmTSollecitoRepository.save(cnmTSollecito);
									// ***************************************************
								}

							}
						}

						List<CnmTRata> cnmTRataList = cnmTRataRepository.findByIdOrdinanzaVerbSogStatoRata(cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog());
						if (cnmTRataList != null && cnmTRataList.size() > 0) {
							List<CnmRSoggRata> cnmRSoggRataList = cnmRSoggRataRepository.findByCnmTRataIn(cnmTRataList);
							for (CnmRSoggRata cnmRSoggRata : cnmRSoggRataList) {

								// ********************_UPDATE_STATO_RATA
								logger.info("update stato - cnmRSoggRata[id] :: " + cnmRSoggRata.getId());
								CnmDStatoRata cnmDStatoRata = cnmDStatoRataRepository.findOne(Constants.ID_STATO_ESTINTO_RATA);
								cnmRSoggRata.setCnmDStatoRata(cnmDStatoRata);
								cnmRSoggRataRepository.save(cnmRSoggRata);
								// ***************************************************
							}

							Map<Integer, CnmTRata> map = new HashMap<Integer, CnmTRata>();
							for (CnmTRata cnmTRata : cnmTRataList) {
								map.put(cnmTRata.getCnmTPianoRate().getIdPianoRate(), cnmTRata);
							}

							if (!map.isEmpty()) {
								for (Map.Entry<Integer, CnmTRata> entry : map.entrySet()) {

									CnmTPianoRate cnmTPianoRate = cnmTPianoRateRepository.findOne(entry.getKey());
									if (cnmTPianoRate != null) {
										// ********************_UPDATE_STATO_PIANO_RATA
										logger.info("update stato - cnmTPianoRate[id] :: " + cnmTPianoRate.getIdPianoRate());
										CnmDStatoPianoRate cnmDStatoPianoRate = cnmDStatoPianoRateRepository.findOne(Constants.ID_STATO_ESTINTO_PIANO_RATA);
										cnmTPianoRate.setCnmDStatoPianoRate(cnmDStatoPianoRate);
										cnmTPianoRateRepository.save(cnmTPianoRate);
										// ***************************************************
									}
								}
							}

						}

						// ********************_UPDATE_ORDINANZA_VERB_SOGG
						logger.info("update stato - cnmROrdinanzaVerbSog[id] :: " + cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog());
						CnmDStatoOrdVerbSog cnmDStatoOrdVerbSog = cnmDStatoOrdVerbSogRepository.findOne(Constants.ID_STATO_ORDINANZA_VERB_SOG_RISCOSSO_SORIS);
						cnmROrdinanzaVerbSog.setCnmDStatoOrdVerbSog(cnmDStatoOrdVerbSog);
						cnmROrdinanzaVerbSogRepository.save(cnmROrdinanzaVerbSog);
						// ***************************************************

						// ************************** UPDATE STATO RISCOSSIONE
						logger.info("update stato - nmTRiscossione[id] :: " + nmTRiscossione.getIdRiscossione());
						CnmDStatoRiscossione cnmDStatoRiscossione = cnmDStatoRiscossioneRepository.findOne(Constants.ID_STATO_RISCOSSO);
						nmTRiscossione.setCnmDStatoRiscossione(cnmDStatoRiscossione);
						cnmTRiscossioneRepository.save(nmTRiscossione);
						// ***************************************************

						List<CnmTRecordRitorno> cnmTRecordRitornoList = cnmTRecordRitornoRepository.findByCnmRiscossione(nmTRiscossione.getIdRiscossione());
						if (cnmTRecordRitornoList != null && cnmTRecordRitornoList.size() > 0) {
							for (CnmTRecordRitorno cnmTRecordRitorno : cnmTRecordRitornoList) {

								// **************************UPDATE_STATO_RECORD
								logger.info("update stato - cnmTRecordRitorno[id] :: " + cnmTRecordRitorno.getIdRecordRitorno());
								CnmDStatoRecord cnmDStatoRecord = cnmDStatoRecordRepository.findOne(Constants.ID_STATO_RECORD_ELAB);
								cnmTRecordRitorno.setCnmDStatoRecord(cnmDStatoRecord);
								// ***************************************************

							}
						} else {
							throw new RuntimeException("nessun record trovato x Riscossione[id] :: " + nmTRiscossione.getIdRiscossione());
						}

					} else {
						logger.error("nessuna cnmROrdinanzaVerbSog trovata x id_riscossione :: " + nmTRiscossione.getIdRiscossione());
					}
				}

			}

		} else
			logger.info("nessun dato da elaborare.");

	}
}
