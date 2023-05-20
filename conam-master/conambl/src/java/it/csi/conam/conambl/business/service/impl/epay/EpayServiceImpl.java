/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.epay;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import it.csi.conam.conambl.business.service.epay.EpayService;
import it.csi.conam.conambl.business.service.ordinanza.AllegatoOrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.StatoPagamentoOrdinanzaService;
import it.csi.conam.conambl.business.service.pianorateizzazione.AllegatoPianoRateizzazioneService;
import it.csi.conam.conambl.business.service.sollecito.AllegatoSollecitoService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.epay.from.*;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.util.UtilsEpay;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 01 feb 2019
 */
@Service
public class EpayServiceImpl implements EpayService {

	private static final Logger logger = Logger.getLogger(EpayServiceImpl.class);

	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;
	@Autowired
	private CnmDStatoRataRepository cnmDStatoRataRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmTSollecitoRepository cnmTSollecitoRepository;

	@Autowired
	private AllegatoPianoRateizzazioneService allegatoPianoRateizzazioneService;
	@Autowired
	private AllegatoOrdinanzaService allegatoOrdinanzaService;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private AllegatoSollecitoService allegatoSollecitoService;
	@Autowired
	private CnmDStatoOrdVerbSogRepository cnmDStatoOrdVerbSogRepository;

	@Autowired
	private CnmDStatoSollecitoRepository cnmDStatoSollecitoRepository;
	@Autowired
	private StatoPagamentoOrdinanzaService statoPagamentoOrdinanzaService;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;

	@Override
	@Transactional
	public ResponseType esitoInserimentoListaDiCarico(EsitoInserimentoListaDiCaricoRequest request) {
		logger.info("chiamata ePay. Service trasmettiNotifichePagamento");
		if (request == null)
			throw new IllegalArgumentException("request = null");
		if (request.getEsitoInserimento() == null)
			throw new IllegalArgumentException("esito inserimento = null");
		if (request.getEsitoInserimento().getElencoPosizioniDebitorieInserite() == null)
			throw new IllegalArgumentException("elenco posizioni debitoie  = null");

		List<PosizioneDebitoriaType> posizioneDebitoriaTypeArray = request.getEsitoInserimento().getElencoPosizioniDebitorieInserite().getPosizioneDebitoriaInserita();

		List<String> codicePosizioneDebitoriaPianoRatList = new ArrayList<>();
		List<String> codicePosizioneDebitoriaOrdinanzaList = new ArrayList<>();
		List<String> codicePosizioneDebitoriaSollecitoList = new ArrayList<>();

		for (PosizioneDebitoriaType e : posizioneDebitoriaTypeArray) {
			String codicePosizioneDebitoria = e.getIdPosizioneDebitoria();
			logger.info("codicePosizioneDebitoria from ePay: " + codicePosizioneDebitoria);
			if (codicePosizioneDebitoria.contains(Constants.CODICE_PIANO_RATEIZZAZIONE)) {
				codicePosizioneDebitoriaPianoRatList.add(codicePosizioneDebitoria);
			} else if (codicePosizioneDebitoria.contains(Constants.CODICE_ORDINANZA)) {
				codicePosizioneDebitoriaOrdinanzaList.add(codicePosizioneDebitoria);
			} else if (codicePosizioneDebitoria.contains(Constants.CODICE_SOLLECITO)) {
				codicePosizioneDebitoriaSollecitoList.add(codicePosizioneDebitoria);
			} else {
				logger.error("codicePosizioneDebitoria sconosciuto");
			}
		}

		// PIANO RATEIZZAZIONE
		if (!codicePosizioneDebitoriaPianoRatList.isEmpty()) {
			List<CnmRSoggRata> cnmRSoggRataList = cnmRSoggRataRepository.findByCodPosizioneDebitoriaIn(codicePosizioneDebitoriaPianoRatList);
			for (PosizioneDebitoriaType posizioneDebitoriaType : posizioneDebitoriaTypeArray) {
				CnmRSoggRata cnmRSoggRata = Iterables.tryFind(cnmRSoggRataList, new Predicate<CnmRSoggRata>() {
					@Override
					public boolean apply(CnmRSoggRata input) {
						return posizioneDebitoriaType.getIdPosizioneDebitoria().equals(input.getCodPosizioneDebitoria());
					}
				}).orNull();

				if (cnmRSoggRata == null)
					logger.error("Per la seguente posizione debitoria non è stata trovata la rata:" + posizioneDebitoriaType.getIdPosizioneDebitoria());
				else {
					logger.info("Aggiornamento codIuv, codAvviso e codEsitoListaCarico per rSoggRata " + cnmRSoggRata.getId());
					cnmRSoggRata.setCodIuv(posizioneDebitoriaType.getIUV());
					logger.info("Esito inserimento lista di carico request - COD IUV restituito da Epay: " + posizioneDebitoriaType.getIUV());
					cnmRSoggRata.setCodEsitoListaCarico(posizioneDebitoriaType.getCodiceEsito());
					logger.info("Esito inserimento lista di carico request - codiceEsito restituito da Epay: " + posizioneDebitoriaType.getCodiceEsito());
					cnmRSoggRata.setCodAvviso(posizioneDebitoriaType.getCodiceAvviso());
					logger.info("Esito inserimento lista di carico request - codiceAvviso restituito da Epay: " + posizioneDebitoriaType.getCodiceAvviso());
				}
			}
			cnmRSoggRataList = cnmRSoggRataRepository.save(cnmRSoggRataList);
			allegatoPianoRateizzazioneService.creaBollettiniByCnmRSoggRata(cnmRSoggRataList);
		}

		// Ordinanza
		if (!codicePosizioneDebitoriaOrdinanzaList.isEmpty()) {
			List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCodPosizioneDebitoriaIn(codicePosizioneDebitoriaOrdinanzaList);
			for (PosizioneDebitoriaType posizioneDebitoriaType : posizioneDebitoriaTypeArray) {
				CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = Iterables.tryFind(cnmROrdinanzaVerbSogList, new Predicate<CnmROrdinanzaVerbSog>() {

					@Override
					public boolean apply(CnmROrdinanzaVerbSog input) {
						return posizioneDebitoriaType.getIdPosizioneDebitoria().equals(input.getCodPosizioneDebitoria());
					}
				}).orNull();

				if (cnmROrdinanzaVerbSog == null)
					logger.error("Per la seguente posizione debitoria non è stata trovato ordinanza soggetto:" + posizioneDebitoriaType.getIdPosizioneDebitoria());
				else {
					logger.info("Aggiornamento codIuv, codAvviso e codEsitoListaCarico per ordinanzaVerbSog " + cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog());
					cnmROrdinanzaVerbSog.setCodIuv(posizioneDebitoriaType.getIUV());
					logger.info("Esito inserimento lista di carico request - COD IUV restituito da Epay: " + posizioneDebitoriaType.getIUV());
					cnmROrdinanzaVerbSog.setCodEsitoListaCarico(posizioneDebitoriaType.getCodiceEsito());
					logger.info("Esito inserimento lista di carico request - codiceEsito restituito da Epay: " + posizioneDebitoriaType.getCodiceEsito());
					cnmROrdinanzaVerbSog.setCodAvviso(posizioneDebitoriaType.getCodiceAvviso());
					logger.info("Esito inserimento lista di carico request - codiceAvviso restituito da Epay: " + posizioneDebitoriaType.getCodiceAvviso());
				}
			}
			cnmROrdinanzaVerbSogList = (List<CnmROrdinanzaVerbSog>) cnmROrdinanzaVerbSogRepository.save(cnmROrdinanzaVerbSogList);
			allegatoOrdinanzaService.creaBollettiniByCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSogList);
		}

		// sollecito
		if (!codicePosizioneDebitoriaSollecitoList.isEmpty()) {

			List<CnmTSollecito> cnmTSollecitoList = cnmTSollecitoRepository.findByCodPosizioneDebitoriaIn(codicePosizioneDebitoriaSollecitoList);
			for (PosizioneDebitoriaType posizioneDebitoriaType : posizioneDebitoriaTypeArray) {
				CnmTSollecito cnmTSollecito = Iterables.tryFind(cnmTSollecitoList, new Predicate<CnmTSollecito>() {
					@Override
					public boolean apply(CnmTSollecito input) {
						return posizioneDebitoriaType.getIdPosizioneDebitoria().equals(input.getCodPosizioneDebitoria());
					}
				}).orNull();

				if (cnmTSollecito == null)
					logger.error("Per la seguente posizione debitoria non è stata trovato un sollecito:" + posizioneDebitoriaType.getIdPosizioneDebitoria());
				else {
					logger.info("Aggiornamento codIuv, codAvviso e codEsitoListaCarico per sollecito " + cnmTSollecito.getIdSollecito());
					cnmTSollecito.setCodIuv(posizioneDebitoriaType.getIUV());
					logger.info("Esito inserimento lista di carico request - COD IUV restituito da Epay: " + posizioneDebitoriaType.getIUV());
					cnmTSollecito.setCodEsitoListaCarico(posizioneDebitoriaType.getCodiceEsito());
					logger.info("Esito inserimento lista di carico request - codiceEsito restituito da Epay: " + posizioneDebitoriaType.getCodiceEsito());
					cnmTSollecito.setCodAvviso(posizioneDebitoriaType.getCodiceAvviso());
					logger.info("Esito inserimento lista di carico request - codiceAvviso restituito da Epay: " + posizioneDebitoriaType.getCodiceAvviso());
				}
			}
			cnmTSollecitoList = (List<CnmTSollecito>) cnmTSollecitoRepository.save(cnmTSollecitoList);

			for (CnmTSollecito cnmTSollecito : cnmTSollecitoList)
				allegatoSollecitoService.creaBollettiniByCnmTSollecito(cnmTSollecito);
		}

		return UtilsEpay.getSuccessResult();

	}

	@Override
	@Transactional
	public ResponseType trasmettiNotifichePagamento(TrasmettiNotifichePagamentoRequest request) {
		logger.info("chiamata ePay. Service trasmettiNotifichePagamento");
		
		if (request == null)
			throw new IllegalArgumentException("request = null");
		if (request.getCorpoNotifichePagamento() == null)
			throw new IllegalArgumentException("corpo notifiche = null");
		if (request.getCorpoNotifichePagamento().getElencoNotifichePagamento() == null)
			throw new IllegalArgumentException("elenco notifiche   = null");

		List<NotificaPagamentoType> notifichePagamentoTypeArray = request.getCorpoNotifichePagamento().getElencoNotifichePagamento().getNotificaPagamento();
		List<String> codiceNotificheOrdinanzaList = new ArrayList<>();
		List<String> codiceNotifichePagamentoPianoRatTypeList = new ArrayList<>();
		List<String> codiceNotifichePagamentoSollecitoTypeList = new ArrayList<>();

		for (NotificaPagamentoType e : notifichePagamentoTypeArray) {
			String codicePosizioneDebitoria = e.getIdPosizioneDebitoria();
			logger.info("codicePosizioneDebitoria from ePay: " + codicePosizioneDebitoria);
			if (codicePosizioneDebitoria.contains(Constants.CODICE_PIANO_RATEIZZAZIONE)) {
				codiceNotifichePagamentoPianoRatTypeList.add(codicePosizioneDebitoria);
			} else if (codicePosizioneDebitoria.contains(Constants.CODICE_ORDINANZA)) {
				codiceNotificheOrdinanzaList.add(codicePosizioneDebitoria);
			} else if (codicePosizioneDebitoria.contains(Constants.CODICE_SOLLECITO)) {
				codiceNotifichePagamentoSollecitoTypeList.add(codicePosizioneDebitoria);
			} else {
				logger.error("codicePosizioneDebitoria sconosciuto");
			}
		}

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(Constants.CFEPAY);
		CnmDStatoOrdVerbSog cnmDStatoOrdVerbSog = cnmDStatoOrdVerbSogRepository.findOne(Constants.ID_STATO_ORDINANZA_VERB_SOGG_PAGATO_ONLINE);

		// PIANO RATEIZZAZIONE
		if (!codiceNotifichePagamentoPianoRatTypeList.isEmpty()) {
			List<CnmRSoggRata> cnmRSoggRataList = cnmRSoggRataRepository.findByCodPosizioneDebitoriaIn(codiceNotifichePagamentoPianoRatTypeList);
			for (NotificaPagamentoType notificaPagamentoType : notifichePagamentoTypeArray) {
				CnmRSoggRata cnmRSoggRata = Iterables.tryFind(cnmRSoggRataList, new Predicate<CnmRSoggRata>() {
					@Override
					public boolean apply(CnmRSoggRata input) {
						return notificaPagamentoType.getIdPosizioneDebitoria().equals(input.getCodPosizioneDebitoria());
					}
				}).orNull();

				if (cnmRSoggRata == null)
					logger.error("Per la seguente posizione debitoria non è stata trovata la rata:" + notificaPagamentoType.getIdPosizioneDebitoria());
				else {
					logger.info("Aggiornamento importo e data pagamento per rSoggRata " + cnmRSoggRata.getId());
					logger.info("Importo pagato: " + notificaPagamentoType.getImportoPagato());
					cnmRSoggRata.setCnmDStatoRata(cnmDStatoRataRepository.findOne(Constants.ID_STATO_RATA_PAGATO_ONLINE));
					cnmRSoggRata.setCnmTUser1(cnmTUser);
					cnmRSoggRata.setDataOraUpdate(now);
					cnmRSoggRata.setImportoPagato(notificaPagamentoType.getImportoPagato());
					cnmRSoggRata.setDataPagamento(now);
					cnmRSoggRataRepository.save(cnmRSoggRata);

					statoPagamentoOrdinanzaService.verificaTerminePagamentoRate(cnmRSoggRata, cnmDStatoOrdVerbSog, cnmTUser);

				}
			}

		}

		// ORDINANZA
		if (!codiceNotificheOrdinanzaList.isEmpty()) {
			List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCodPosizioneDebitoriaIn(codiceNotificheOrdinanzaList);
			for (NotificaPagamentoType notificaPagamentoType : notifichePagamentoTypeArray) {
				CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = Iterables.tryFind(cnmROrdinanzaVerbSogList, new Predicate<CnmROrdinanzaVerbSog>() {

					@Override
					public boolean apply(CnmROrdinanzaVerbSog input) {
						return notificaPagamentoType.getIdPosizioneDebitoria().equals(input.getCodPosizioneDebitoria());
					}
				}).orNull();

				if (cnmROrdinanzaVerbSog == null)
					logger.error("Per la seguente posizione debitoria non è stata trovato ordinanza soggetto:" + notificaPagamentoType.getIdPosizioneDebitoria());
				else {
					logger.info("Aggiornamento importo e data pagamento per ordinanzaVerbSog " + cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog());
					logger.info("Importo pagato: " + notificaPagamentoType.getImportoPagato());
					// aggiorno lo stato del soggetto in pagato
					cnmROrdinanzaVerbSog.setCnmDStatoOrdVerbSog(cnmDStatoOrdVerbSog);
					cnmROrdinanzaVerbSog.setImportoPagato(notificaPagamentoType.getImportoPagato());
					cnmROrdinanzaVerbSog.setDataPagamento(now);
					cnmROrdinanzaVerbSog.setCnmTUser2(cnmTUser);
					cnmROrdinanzaVerbSog.setDataOraUpdate(now);
					cnmROrdinanzaVerbSogRepository.save(cnmROrdinanzaVerbSog);
					statoPagamentoOrdinanzaService.verificaTerminePagamentoOrdinanza(cnmROrdinanzaVerbSog, cnmTUser);

				}

			}

		}

		// SOLLECITO
		if (!codiceNotifichePagamentoSollecitoTypeList.isEmpty()) {
			List<CnmTSollecito> cnmTSollecitoList = cnmTSollecitoRepository.findByCodPosizioneDebitoriaIn(codiceNotifichePagamentoSollecitoTypeList);
			for (NotificaPagamentoType notificaPagamentoType : notifichePagamentoTypeArray) {
				CnmTSollecito cnmTSollecito = Iterables.tryFind(cnmTSollecitoList, new Predicate<CnmTSollecito>() {
					@Override
					public boolean apply(CnmTSollecito input) {
						return notificaPagamentoType.getIdPosizioneDebitoria().equals(input.getCodPosizioneDebitoria());
					}
				}).orNull();

				if (cnmTSollecito == null)
					logger.error("Per la seguente posizione debitoria non è stata trovato il sollecito:" + notificaPagamentoType.getIdPosizioneDebitoria());
				else {
					logger.info("Aggiornamento importo e data pagamento per sollecito " + cnmTSollecito.getIdSollecito());
					logger.info("Importo pagato: " + notificaPagamentoType.getImportoPagato());
					cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_SOLLECITO_PAGATO_ONLINE));
					cnmTSollecito.setCnmTUser1(cnmTUser);
					cnmTSollecito.setDataOraUpdate(now);
					cnmTSollecito.setImportoPagato(notificaPagamentoType.getImportoPagato());
					cnmTSollecito.setDataPagamento(now);
					cnmTSollecitoRepository.save(cnmTSollecito);

					// aggiorno ordinanza
					CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmTSollecito.getCnmROrdinanzaVerbSog();
					cnmROrdinanzaVerbSog.setCnmDStatoOrdVerbSog(cnmDStatoOrdVerbSog);
					cnmROrdinanzaVerbSog.setCnmTUser1(cnmTUser);
					cnmROrdinanzaVerbSog.setDataOraUpdate(now);
					cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.save(cnmROrdinanzaVerbSog);
					logger.info("Aggiornamento stato cnmROrdinanzaVerbSog " + cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog());

					statoPagamentoOrdinanzaService.verificaTerminePagamentoSollecito(
						cnmROrdinanzaVerbSog,
						cnmTUser,
						cnmTSollecito
					);
				}
			}

		}

		return UtilsEpay.getSuccessResult();
	}

	@Override
	public ResponseType esitoAggiornaPosizioniDebitorie(EsitoAggiornaPosizioniDebitorieRequest parameters) {
		logger.info("chiamata ePay. Service esitoAggiornaPosizioniDebitorie");
		return UtilsEpay.getSuccessResult();
	}
}
