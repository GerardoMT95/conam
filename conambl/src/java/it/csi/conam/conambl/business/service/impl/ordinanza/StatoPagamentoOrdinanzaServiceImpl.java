/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.ordinanza;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import it.csi.conam.conambl.business.service.ordinanza.OrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.StatoPagamentoOrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.UtilsOrdinanza;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.util.UtilsRata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 25 mar 2019
 */
@Service
public class StatoPagamentoOrdinanzaServiceImpl implements StatoPagamentoOrdinanzaService {

	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmDStatoOrdVerbSogRepository cnmDStatoOrdVerbSogRepository;
	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;
	@Autowired
	private CnmDStatoOrdinanzaRepository cnmDStatoOrdinanzaRepository;
	@Autowired
	private CnmTSollecitoRepository cnmTSollecitoRepository;
	@Autowired
	private CnmDStatoSollecitoRepository cnmDStatoSollecitoRepository;
	@Autowired
	private CnmDStatoRataRepository cnmDStatoRataRepository;
	@Autowired
	private CnmDStatoPianoRateRepository cnmDStatoPianoRateRepository;
	@Autowired
	private CnmTPianoRateRepository cnmTPianoRateRepository;
	@Autowired
	private OrdinanzaService ordinanzaService;
	@Autowired
	private CnmRSollecitoSoggRataRepository cnmRSollecitoSoggRataRepository;
	@Autowired
	private CnmDTipoSollecitoRepository cnmDTipoSollecitoRepository;

	@Autowired
	private UtilsDate utilsDate;


	@Autowired
	private UtilsOrdinanza utilsOrdinanza;

	@Override
	@Transactional
	public void verificaTerminePagamentoOrdinanza(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog, CnmTUser cnmTUser) {
		// se il soggetto ha pagato aggiorno lo stato ordinanza
		if (verificaPagamentoSoggetto(cnmROrdinanzaVerbSog)) {
			aggiornaPagamentoOrdinanza(cnmROrdinanzaVerbSog.getCnmTOrdinanza(), cnmTUser);
			estinguiRateSoggetto(cnmROrdinanzaVerbSog, cnmTUser);
			estinguiSollecitiSoggetto(cnmTUser, cnmROrdinanzaVerbSog);
		}

	}

	@Override
	@Transactional
	public void verificaTerminePagamentoSollecito(
		CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog,
		CnmTUser cnmTUser,
		CnmTSollecito cnmTSollecito
	) {
		// se il soggetto ha pagato aggiorno lo stato ordinanza
		Boolean isRate =
			cnmTSollecito.getCnmDTipoSollecito().getIdTipoSollecito() == 
			Constants.ID_TIPO_SOLLECITO_RATE;
		if (
			isRate ||
			verificaPagamentoSoggetto(cnmROrdinanzaVerbSog)
		) {
			aggiornaPagamentoOrdinanza(cnmROrdinanzaVerbSog.getCnmTOrdinanza(), cnmTUser);
			estinguiRateSoggetto(cnmROrdinanzaVerbSog, cnmTUser);
			estinguiSollecitiSoggetto(cnmTUser, cnmROrdinanzaVerbSog);
		}
		if (isRate) {
			CnmRSollecitoSoggRata rata = cnmRSollecitoSoggRataRepository.findByCnmTSollecito(cnmTSollecito).get(0);
			if (
				rata != null &&
				rata.getCnmTRata() != null &&
				rata.getCnmTRata().getCnmTPianoRate() != null
			) {
				CnmDStatoPianoRate statoPianoRate = cnmDStatoPianoRateRepository.findOne(Constants.ID_STATO_ESTINTO_PIANO_RATA);
				CnmTPianoRate pianoRata = rata.getCnmTRata().getCnmTPianoRate();
				pianoRata.setCnmDStatoPianoRate(statoPianoRate);
				cnmTPianoRateRepository.save(pianoRata);
			}
		}
	}

	@Override
	@Transactional
	public void verificaTerminePagamentoRate(CnmRSoggRata cnmRSoggRata, CnmDStatoOrdVerbSog cnmDStatoOrdVerbSog, CnmTUser cnmTUser) {
		// nel caso abbia pagato tutte le rate aggiorno lo stato della ordinanza
		// verbale soggetto e della ordinanza
		CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmRSoggRata.getCnmROrdinanzaVerbSog();
		List<CnmRSoggRata> cnmRSoggRatas = cnmRSoggRataRepository.findByCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);
		CnmTPianoRate cnmTPianoRate = cnmRSoggRata.getCnmTRata().getCnmTPianoRate();
		CnmDStatoPianoRate statoPianoRateEstinto = cnmDStatoPianoRateRepository.findOne(Constants.ID_STATO_PIANO_ESTINTO);
		if (Collections2.filter(cnmRSoggRatas, UtilsRata.filtraRatePagate()).isEmpty()) {
			if (!this.verificaPagamentoSoggetto(cnmROrdinanzaVerbSog))
				cnmROrdinanzaVerbSog.setCnmDStatoOrdVerbSog(cnmDStatoOrdVerbSog);
			cnmROrdinanzaVerbSog.setCnmTUser2(cnmTUser);
			cnmROrdinanzaVerbSog.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));
			cnmROrdinanzaVerbSogRepository.save(cnmROrdinanzaVerbSog);

			if (verificaPagamentoSoggetto(cnmROrdinanzaVerbSog)) {
				aggiornaPagamentoOrdinanza(cnmROrdinanzaVerbSog.getCnmTOrdinanza(), cnmTUser);
				estinguiSollecitiSoggetto(cnmTUser, cnmROrdinanzaVerbSog);
			}
		}
		List<CnmTRata> cnmTRatas = cnmTPianoRate.getCnmTRatas();
		cnmRSoggRatas.clear();
		cnmRSoggRatas = cnmRSoggRataRepository.findByCnmTRataIn(cnmTRatas);
		if (Collections2.filter(cnmRSoggRatas, UtilsRata.filtraRatePagate()).isEmpty()) {
			cnmTPianoRate.setCnmDStatoPianoRate(statoPianoRateEstinto);
			cnmTPianoRateRepository.save(cnmTPianoRate);
		}

	}

	@Transactional
	private boolean verificaPagamentoSoggetti(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs) {
		return Collections2.filter(cnmROrdinanzaVerbSogs, new Predicate<CnmROrdinanzaVerbSog>() {
			@Override
			public boolean apply(CnmROrdinanzaVerbSog input) {
				long idStatoOrdinanzaVerbSog = input.getCnmDStatoOrdVerbSog().getIdStatoOrdVerbSog();
				return !(idStatoOrdinanzaVerbSog == Constants.ID_STATO_ORDINANZA_VERB_SOGG_PAGATO_ONLINE //
						|| idStatoOrdinanzaVerbSog == Constants.ID_STATO_ORDINANZA_VERB_SOGG_PAGATO_OFFLINE //
						|| idStatoOrdinanzaVerbSog == Constants.ID_STATO_ORDINANZA_VERB_SOGG_ARCHIVIATO //
						|| idStatoOrdinanzaVerbSog == Constants.ID_STATO_ORDINANZA_VERB_SOGG_RICORSO_ACCOLTO //
				);
			}
		}).isEmpty();
	}

	@Transactional
	private boolean verificaPagamentoSoggetto(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		long idStatoOrdinanzaVerbSog = cnmROrdinanzaVerbSog.getCnmDStatoOrdVerbSog().getIdStatoOrdVerbSog();
		return (idStatoOrdinanzaVerbSog == Constants.ID_STATO_ORDINANZA_VERB_SOGG_PAGATO_ONLINE //
				|| idStatoOrdinanzaVerbSog == Constants.ID_STATO_ORDINANZA_VERB_SOGG_PAGATO_OFFLINE //
				|| idStatoOrdinanzaVerbSog == Constants.ID_STATO_ORDINANZA_VERB_SOGG_ARCHIVIATO //
				|| idStatoOrdinanzaVerbSog == Constants.ID_STATO_ORDINANZA_VERB_SOGG_RICORSO_ACCOLTO //
		);
	}

	@Transactional
	private void estinguiSolleciti(CnmTUser cnmTUser, List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs) {
		// chiudo i solleciti aperti estinguendoli
		List<Long> stati = Arrays.asList(Constants.ID_STATO_SOLLECITO_PAGATO_ONLINE, Constants.ID_STATO_SOLLECITO_PAGATO_OFFLINE);
		List<CnmDStatoSollecito> cnmDStatoSollecitos = (List<CnmDStatoSollecito>) cnmDStatoSollecitoRepository.findAll(stati);
		// 20210402_LC tipo sollecito
		CnmDTipoSollecito cnmDTipoSollecito = cnmDTipoSollecitoRepository.findOne(Constants.ID_TIPO_SOLLECITO_ORDINANZA);

		List<CnmTSollecito> cnmTSollecitos = cnmTSollecitoRepository.findByCnmROrdinanzaVerbSogInAndCnmDStatoSollecitoNotInAndCnmDTipoSollecito(cnmROrdinanzaVerbSogs, cnmDStatoSollecitos, cnmDTipoSollecito);
		
		CnmDStatoSollecito cnmDStatoSollecitoEstinto = cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_SOLLECITO_ESTINTO);
		Timestamp dataOraUpdate = utilsDate.asTimeStamp(LocalDateTime.now());
		for (CnmTSollecito c : cnmTSollecitos) {
			c.setImportoPagato(BigDecimal.ZERO);
			c.setCnmDStatoSollecito(cnmDStatoSollecitoEstinto);
			c.setCnmTUser1(cnmTUser);
			c.setDataOraUpdate(dataOraUpdate);
		}
		cnmTSollecitoRepository.save(cnmTSollecitos);
	}

	@Transactional
	private void estinguiSollecitiSoggetto(CnmTUser cnmTUser, CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		// chiudo i solleciti aperti estinguendoli
		List<Long> stati = Arrays.asList(Constants.ID_STATO_SOLLECITO_PAGATO_ONLINE, Constants.ID_STATO_SOLLECITO_PAGATO_OFFLINE);
		List<CnmDStatoSollecito> cnmDStatoSollecitos = (List<CnmDStatoSollecito>) cnmDStatoSollecitoRepository.findAll(stati);
		// 20210402_LC tipo sollecito
		CnmDTipoSollecito cnmDTipoSollecito = cnmDTipoSollecitoRepository.findOne(Constants.ID_TIPO_SOLLECITO_ORDINANZA);
		
		List<CnmTSollecito> cnmTSollecitos = cnmTSollecitoRepository.findByCnmROrdinanzaVerbSogAndCnmDStatoSollecitoNotInAndCnmDTipoSollecito(cnmROrdinanzaVerbSog, cnmDStatoSollecitos, cnmDTipoSollecito);
		
		CnmDStatoSollecito cnmDStatoSollecitoEstinto = cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_SOLLECITO_ESTINTO);
		Timestamp dataOraUpdate = utilsDate.asTimeStamp(LocalDateTime.now());
		for (CnmTSollecito c : cnmTSollecitos) {
			c.setImportoPagato(BigDecimal.ZERO);
			c.setCnmDStatoSollecito(cnmDStatoSollecitoEstinto);
			c.setCnmTUser1(cnmTUser);
			c.setDataOraUpdate(dataOraUpdate);
		}
		cnmTSollecitoRepository.save(cnmTSollecitos);
	}

	@Transactional
	private void estinguiRate(CnmTOrdinanza cnmTOrdinanza, CnmTUser cnmTUser) {
		List<CnmDStatoRata> cnmDStatoRatas = (List<CnmDStatoRata>) cnmDStatoRataRepository.findAll(Arrays.asList(Constants.ID_STATO_RATA_PAGATO_OFFLINE, Constants.ID_STATO_RATA_PAGATO_ONLINE));
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		List<CnmRSoggRata> cnmRSoggRatas = cnmRSoggRataRepository.findByCnmROrdinanzaVerbSogInAndCnmDStatoRataNotIn(cnmROrdinanzaVerbSogs, cnmDStatoRatas);
		CnmDStatoRata cnmDStatoRataEstinto = cnmDStatoRataRepository.findOne(Constants.ID_STATO_RATA_ESTINTO);
		Timestamp dataOraUpdate = utilsDate.asTimeStamp(LocalDateTime.now());
		for (CnmRSoggRata c : cnmRSoggRatas) {
			c.setCnmDStatoRata(cnmDStatoRataEstinto);
			// metto l'importo a zero
			c.setImportoPagato(BigDecimal.ZERO);
			c.setCnmTUser1(cnmTUser);
			c.setDataOraUpdate(dataOraUpdate);
		}
		cnmRSoggRataRepository.save(cnmRSoggRatas);
	}

	@Transactional
	private void estinguiRateSoggetto(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog, CnmTUser cnmTUser) {
		List<CnmDStatoRata> cnmDStatoRatas = (List<CnmDStatoRata>) cnmDStatoRataRepository.findAll(Arrays.asList(Constants.ID_STATO_RATA_PAGATO_OFFLINE, Constants.ID_STATO_RATA_PAGATO_ONLINE));
		List<CnmRSoggRata> cnmRSoggRatas = cnmRSoggRataRepository.findByCnmROrdinanzaVerbSogAndCnmDStatoRataNotIn(cnmROrdinanzaVerbSog, cnmDStatoRatas);
		CnmDStatoRata cnmDStatoRataEstinto = cnmDStatoRataRepository.findOne(Constants.ID_STATO_RATA_ESTINTO);
		Timestamp dataOraUpdate = utilsDate.asTimeStamp(LocalDateTime.now());
		for (CnmRSoggRata c : cnmRSoggRatas) {
			c.setCnmDStatoRata(cnmDStatoRataEstinto);
			// metto l'importo a zero
			c.setImportoPagato(BigDecimal.ZERO);
			c.setCnmTUser1(cnmTUser);
			c.setDataOraUpdate(dataOraUpdate);
		}
		cnmRSoggRataRepository.save(cnmRSoggRatas);
		CnmDStatoOrdVerbSog cnmDStatoOrdVerbSog = cnmDStatoOrdVerbSogRepository.findOne(Constants.ID_STATO_ORDINANZA_VERB_SOGG_PAGATO_OFFLINE);
		if (!cnmRSoggRatas.isEmpty())
			this.verificaTerminePagamentoRate(cnmRSoggRatas.get(0), cnmDStatoOrdVerbSog, cnmTUser);
	}

	@Transactional
	private void aggiornaPagamentoOrdinanza(CnmTOrdinanza cnmTOrdinanza, CnmTUser cnmTUser1) {
		Timestamp dataOraUpdate = utilsDate.asTimeStamp(LocalDateTime.now());
		if (cnmTOrdinanza.getCnmDStatoOrdinanza().getIdStatoOrdinanza() != Constants.ID_STATO_ORDINANZA_PAGATA) {
			ordinanzaService.saveSStatoOrdinanza(cnmTOrdinanza, cnmTUser1);
			cnmTOrdinanza.setCnmDStatoOrdinanza(cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_PAGATA));
			cnmTOrdinanza.setCnmTUser1(cnmTUser1);
			cnmTOrdinanza.setDataOraUpdate(dataOraUpdate);
			cnmTOrdinanzaRepository.save(cnmTOrdinanza);
		}
	}

}
