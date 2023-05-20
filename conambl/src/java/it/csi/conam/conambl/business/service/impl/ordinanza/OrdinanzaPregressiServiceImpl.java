/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.ordinanza;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.notifica.NotificaService;
import it.csi.conam.conambl.business.service.ordinanza.OrdinanzaPregressiService;
import it.csi.conam.conambl.business.service.ordinanza.OrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.UtilsOrdinanza;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.OrdinanzaEntityMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.request.ordinanza.SalvaOrdinanzaPregressiRequest;
import it.csi.conam.conambl.request.ordinanza.SalvaStatoOrdinanzaRequest;
import it.csi.conam.conambl.request.ordinanza.SoggettoOrdinanzaRequest;
import it.csi.conam.conambl.response.DatiSentenzaPregressiResponse;
import it.csi.conam.conambl.response.RicevutaPagamentoResponse;
import it.csi.conam.conambl.response.SalvaOrdinanzaPregressiResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.UtilsFieldAllegato;
import it.csi.conam.conambl.util.UtilsTipoAllegato;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import it.csi.conam.conambl.vo.ordinanza.MinOrdinanzaVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrdinanzaPregressiServiceImpl implements OrdinanzaPregressiService {

	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
//	@Autowired
//	private CnmTVerbaleRepository cnmTVerbaleRepository;
//	@Autowired
//	private CnmDStatoVerbaleRepository cnmDStatoVerbaleRepository;

	@Autowired
	private CnmDStatoOrdinanzaRepository cnmDStatoOrdinanzaRepository;
	@Autowired
	private CnmDTipoOrdinanzaRepository cnmDTipoOrdinanzaRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmDStatoOrdVerbSogRepository cnmDStatoOrdVerbSogRepository;

	@Autowired
	private OrdinanzaEntityMapper ordinanzaEntityMapper;
	@Autowired
	private CommonAllegatoService commonAllegatoService;
//	@Autowired
//	private CnmRAllegatoOrdinanzaRepository cnmRAllegatoOrdinanzaRepository;
//	@Autowired
//	private UtilsOrdinanza utilsOrdinanza;
//	@Autowired
//	private CnmTAllegatoFieldRepository cnmTAllegatoFieldRepository;
//	@Autowired
//	private CnmRAllegatoOrdVerbSogRepository cnmRAllegatoOrdVerbSogRepository;
//	@Autowired
//	private CnmSStatoOrdinanzaRepository cnmSStatoOrdinanzaRepository;
//	@Autowired
//	private UtilsDate utilsDate;
//	@Autowired
//	private StoricizzazioneVerbaleService storicizzazioneVerbaleService;
	@Autowired
	private NotificaService notificaService;
//	@Autowired
//	private UtilsDoqui utilsDoqui;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmCParametroRepository cnmCParametroRepository;
	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;
	@Autowired
	private UtilsOrdinanza utilsOrdinanza;
	@Autowired
	private OrdinanzaService ordinanzaService;

	@Autowired
	private CnmTAllegatoFieldRepository cnmTAllegatoFieldRepository;
	@Autowired
	private CnmRAllegatoOrdVerbSogRepository cnmRAllegatoOrdVerbSogRepository;

	@Autowired
	private CnmDElementoElencoRepository cnmDElementoElencoRepository;
	
	private static Logger logger = Logger.getLogger(OrdinanzaPregressiServiceImpl.class);
	
	@Override
	@Transactional
	public SalvaOrdinanzaPregressiResponse salvaOrdinanza(SalvaOrdinanzaPregressiRequest request, UserDetails userDetails) {
		Long idUser = userDetails.getIdUser();
//		SalvaOrdinanzaRequest request = commonAllegatoService.getRequest(data, file, SalvaOrdinanzaRequest.class);
//		byte[] byteFile = request.getFile();
//		String fileName = request.getFilename();
		// TODO: verificare se e' corretto prendere il valore da qua
		Long idTipoAllegato = request.getAllegati().get(0).getIdTipoAllegato();
//		Long idTipoAllegato = request.getIdTipoAllegato();
//		List<AllegatoFieldVO> configAllegato = request.getAllegatoField();
		MinOrdinanzaVO ordinanza = request.getOrdinanza();
		List<SoggettoOrdinanzaRequest> soggetti = request.getSoggetti();
		NotificaVO notifica = request.getNotifica();
		
		if (ordinanza == null)
			throw new IllegalArgumentException("ordinanza=null");
		
		Date dataDiscriminantePregresso = cnmCParametroRepository.findOne(Constants.ID_DATA_DISCRIMINANTE_GESTIONE_PREGRESSO).getValoreDate();
		
		if(dataDiscriminantePregresso.before(utilsDate.asDate(ordinanza.getDataDeterminazione())) || dataDiscriminantePregresso.before(utilsDate.asDate(ordinanza.getDataOrdinanza()))) {
			throw new BusinessException(ErrorCode.DATE_ORDINANZA_NON_PREGRESSE);
		}
		CnmTUser cnmTUser = cnmTUserRepository.findOne(idUser);
		Timestamp now = new Timestamp(new Date().getTime());

		SalvaOrdinanzaPregressiResponse response = new SalvaOrdinanzaPregressiResponse();
		CnmTOrdinanza cnmTOrdinanza = null;
		// SIAMO in INSERT
		if (ordinanza.getId() == null) {
			
			// controlli di sicurezza
			if (soggetti == null)
				throw new IllegalArgumentException("soggetti=null");
			
			
	//		UploadUtils.checkDimensioneAllegato(byteFile);
	
			// 20201026 PP- controllo se e' stato caricato un file firmato , con firma non valida senza firma
	//		utilsDoqui.checkFileSign(byteFile, fileName);
			
			List<Integer> idVerbaleSoggettoList = new ArrayList<>();
			for (SoggettoOrdinanzaRequest s : soggetti)
				idVerbaleSoggettoList.add(s.getIdVerbaleSoggetto());
	
			List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = (List<CnmRVerbaleSoggetto>) cnmRVerbaleSoggettoRepository.findAll(idVerbaleSoggettoList);
			if (cnmRVerbaleSoggettoList == null || cnmRVerbaleSoggettoList.isEmpty())
				throw new IllegalArgumentException("soggetti non trovati");



			/*---------2021-12-15 - LUCIO ROSADINI - CONTROLLO CODICE FISCALE---------*/
			if (idTipoAllegato.equals(TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId())) {
				utilsOrdinanza.isCFValid(cnmRVerbaleSoggettoList);
			}
			
			CnmTVerbale cnmTVerbale = cnmRVerbaleSoggettoList.get(0).getCnmTVerbale();
	
			List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettoList);
			if (cnmROrdinanzaVerbSogList != null && !cnmROrdinanzaVerbSogList.isEmpty())
				throw new SecurityException("ordinanza gia esistente per i soggetti passati in input");
	
			if (ordinanza.getNumDeterminazione() == null)
				throw new IllegalArgumentException("numero determinazione non valorizzato");
	
			cnmTOrdinanza = cnmTOrdinanzaRepository.findByNumDeterminazione(ordinanza.getNumDeterminazione().toUpperCase());
			if (cnmTOrdinanza != null)
				throw new BusinessException(ErrorCode.INSERIMENTO_ORDINANZA_NUMERO_DETERMINAZIONE_ESISTENTE);
			// fine controlli
	
			CnmDTipoOrdinanza cnmDTipoOrdinanza;
			Long idTipoOrdinanza;
			Long idTipoOrdinanzaSoggettoDefault;
	
			// 20210304_LC l'ordinanza di annullamento non può essere allegato a fascicoli pregressi
			if (idTipoAllegato.equals(TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId())) {
				idTipoOrdinanza = Constants.ID_TIPO_ORDINANZA_INGIUNZIONE;
				idTipoOrdinanzaSoggettoDefault = Constants.ID_STATO_ORDINANZA_SOGGETTO_INGIUNZIONE;
			} else if (idTipoAllegato.equals(TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId())) {
				idTipoOrdinanza = Constants.ID_TIPO_ORDINANZA_ARCHIVIATO;
				idTipoOrdinanzaSoggettoDefault = Constants.ID_STATO_ORDINANZA_SOGGETTO_ARCHIVIATO;
			} else
				throw new IllegalArgumentException("stato ordinanza non valorizzato ");
	
			// salvo ordinanza
			cnmTOrdinanza = ordinanzaEntityMapper.mapVOtoEntity(ordinanza);
	//		cnmTOrdinanza.setCnmDStatoOrdinanza(cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_IN_ATTESA_DI_NOTIFICA));
			cnmTOrdinanza.setCnmDStatoOrdinanza(cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_NOTIFICATA));
			cnmDTipoOrdinanza = cnmDTipoOrdinanzaRepository.findOne(idTipoOrdinanza);
			cnmTOrdinanza.setCnmDTipoOrdinanza(cnmDTipoOrdinanza);
			cnmTOrdinanza.setFlagDocumentoPregresso(true);
			String protocollo = StringUtils.isNotBlank(request.getDocumentoProtocollato().getNumProtocollo())?request.getDocumentoProtocollato().getNumProtocollo():request.getDocumentoProtocollato().getNumProtocolloMaster();
			//aggiungo il num protocollo visto che l'oridanza e' stata recuperata da ACTA, anche se non viene settato per le ordinanze standard
//			rimuovo il salvataggio del num protocollo in quanto c'e una unique sulla cnmTOrdinanza
//			cnmTOrdinanza.setNumeroProtocollo(protocollo);
//			if(StringUtils.isNotBlank(request.getDocumentoProtocollato().getDataOraProtocollo()))
//				cnmTOrdinanza.setDataOraProtocollo(new Timestamp(utilsDate.getDate(request.getDocumentoProtocollato().getDataOraProtocollo(), DateFormat.DATE_FORMAT_DDMMYY).getTime()));
			cnmTOrdinanza.setCnmTUser2(cnmTUser);
			cnmTOrdinanza.setDataOraInsert(now);
			cnmTOrdinanza = cnmTOrdinanzaRepository.save(cnmTOrdinanza);
	
//			SalvaAllegatiProtocollatiRequest allegatiProtocollatiRequest = new SalvaAllegatiProtocollatiRequest();
//			allegatiProtocollatiRequest.setAllegati(request.getAllegati());
//			allegatiProtocollatiRequest.setDocumentoProtocollato(request.getDocumentoProtocollato());
			
			
			request.setIdVerbale(cnmTVerbale.getIdVerbale());
			// salvo allegato
	//		CnmTAllegato cnmTAllegato = commonAllegatoService.salvaAllegato(byteFile, fileName, idTipoAllegato, configAllegato, cnmTUser, TipoProtocolloAllegato.DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO,
	//				null, null, false, false, null, null, 0, null, null, null);
			@SuppressWarnings("unused")
			List<CnmTAllegato> salvaAllegatoProtocollatoVerbale = commonAllegatoService.salvaAllegatoProtocollatoOrdinanza(request, cnmTUser, cnmTOrdinanza, cnmTVerbale, true);
			/*
			CnmTAllegato cnmTAllegato = salvaAllegatoProtocollatoVerbale.get(0);
	
			// salvo relazione allegato ordinanza
			CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza = new CnmRAllegatoOrdinanza();
			CnmRAllegatoOrdinanzaPK cnmRAllegatoOrdinanzaPK = new CnmRAllegatoOrdinanzaPK();
			cnmRAllegatoOrdinanzaPK.setIdAllegato(cnmTAllegato.getIdAllegato());
			cnmRAllegatoOrdinanzaPK.setIdOrdinanza(cnmTOrdinanza.getIdOrdinanza());
			cnmRAllegatoOrdinanza.setCnmTUser(cnmTUser);
			cnmRAllegatoOrdinanza.setDataOraInsert(now);
			cnmRAllegatoOrdinanza.setId(cnmRAllegatoOrdinanzaPK);
			cnmRAllegatoOrdinanzaRepository.save(cnmRAllegatoOrdinanza);
			 */
			
			// salvo la posizione dei soggetti
			for (CnmRVerbaleSoggetto cnmRVerbaleSoggetto : cnmRVerbaleSoggettoList) {
				SoggettoOrdinanzaRequest soggettoOrdinanzaRequest = Iterables.tryFind(soggetti, new Predicate<SoggettoOrdinanzaRequest>() {
					@Override
					public boolean apply(SoggettoOrdinanzaRequest input) {
						return input.getIdVerbaleSoggetto().equals(cnmRVerbaleSoggetto.getIdVerbaleSoggetto());
					}
				}).orNull();
				Long stato = soggettoOrdinanzaRequest != null && soggettoOrdinanzaRequest.getIdTipoOrdinanza() != null ? soggettoOrdinanzaRequest.getIdTipoOrdinanza() : idTipoOrdinanzaSoggettoDefault;
				CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = new CnmROrdinanzaVerbSog();
				cnmROrdinanzaVerbSog.setCnmDStatoOrdVerbSog(cnmDStatoOrdVerbSogRepository.findOne(stato));
				cnmROrdinanzaVerbSog.setCnmRVerbaleSoggetto(cnmRVerbaleSoggetto);
				cnmROrdinanzaVerbSog.setCnmTOrdinanza(cnmTOrdinanza);
				cnmROrdinanzaVerbSog.setCnmTUser2(cnmTUser);
				cnmROrdinanzaVerbSog.setDataOraInsert(now);
				cnmROrdinanzaVerbSogRepository.save(cnmROrdinanzaVerbSog);
			}						
	
			// per il pregresso NON aggiorna stato verbale
			/*
			cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
			cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettoList);
			if (cnmRVerbaleSoggettoList.size() == cnmROrdinanzaVerbSogList.size()) {
				storicizzazioneVerbaleService.storicizzaStatoVerbale(cnmTVerbale, cnmTUser);
				CnmDStatoVerbale cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ORDINANZA);
				cnmTVerbale.setCnmDStatoVerbale(cnmDStatoVerbale);
				cnmTVerbaleRepository.save(cnmTVerbale);
			}	
			*/	
	
			//Save della notifca se presente	-	20210415 se != null
			if(notifica != null) {
				notifica.setIdOrdinanza(cnmTOrdinanza.getIdOrdinanza());
				notifica.setPregresso(true);
				notificaService.salvaNotifica(notifica, userDetails);
			}
			
			CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.CONFIRM_RECUPERO_PROTOCOLLO);
			if(cnmDMessaggio!=null) {
				
				String msg = String.format(cnmDMessaggio.getDescMessaggio(), protocollo);
				response.setConfirmMsg(new MessageVO(msg, cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio()));
			}else {
				throw new SecurityException("Messaggio non trovato");
			}
		}
		// SIAMO in UPDATE
		else {
			cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(ordinanza.getId().intValue());
			if(cnmTOrdinanza == null)
				throw new SecurityException("ordinanza non valida");
			if(!cnmTOrdinanza.getNumDeterminazione().toUpperCase().equals(ordinanza.getNumDeterminazione().toUpperCase())) {
				//se e' stato modificato il num determinazione controllo che il nuovo non sia gia presente
				CnmTOrdinanza findByNumDeterminazione = cnmTOrdinanzaRepository.findByNumDeterminazione(ordinanza.getNumDeterminazione().toUpperCase());
				if (findByNumDeterminazione != null)
					throw new BusinessException(ErrorCode.INSERIMENTO_ORDINANZA_NUMERO_DETERMINAZIONE_ESISTENTE);
			}				
			// fine controlli
			
			cnmTOrdinanza = ordinanzaEntityMapper.mapVOtoEntityUpdate(ordinanza, cnmTOrdinanza);
			cnmTOrdinanza.setCnmTUser2(cnmTUser);
			cnmTOrdinanza.setDataOraInsert(now);
			cnmTOrdinanza = cnmTOrdinanzaRepository.save(cnmTOrdinanza);
		}
		response.setIdOrdinanza(cnmTOrdinanza.getIdOrdinanza());

		// 20210118 JIRA 110
		response.setNumDeterminazione(cnmTOrdinanza.getNumDeterminazione());
		
		return response;
	}


	@Override
	@Transactional
	public MessageVO salvaStatoOrdinanza(SalvaStatoOrdinanzaRequest salvaStatoOrdinanza, UserDetails userDetails) {
		
		Long idStato = salvaStatoOrdinanza.getIdStato();
		Integer idOrdinanza = salvaStatoOrdinanza.getId();
		
		if (idStato == null)
			throw new IllegalArgumentException("id stato = null");

		logger.debug("salvo lo stato con id "+idStato+" per l'ordinanza "+idOrdinanza);
		CnmTOrdinanza cnmTOrdinanza = utilsOrdinanza.validateAndGetCnmTOrdinanza(idOrdinanza);
		if(!utilsOrdinanza.gestisciOrdinanzaComePregressa(idOrdinanza)) {
			throw new BusinessException(ErrorCode.STATO_VERBALE_INCOMPATIBILE);
		}
		
		Date dataTerminePregresso = cnmCParametroRepository.findOne(Constants.ID_DATA_TERMINE_GESTIONE_PREGRESSO).getValoreDate();
		if(new Date().after(dataTerminePregresso)) {
			throw new BusinessException(ErrorCode.LAVORAZIONE_FASCICOLO_PREGRESSO_SCADUTA);
		}
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		
		CnmDStatoOrdinanza cnmDStatoOrdinanza = cnmDStatoOrdinanzaRepository.findOne(idStato);

		ordinanzaService.saveSStatoOrdinanza(cnmTOrdinanza, cnmTUser);
		cnmTOrdinanza.setCnmDStatoOrdinanza(cnmDStatoOrdinanza);
		cnmTOrdinanza.setCnmTUser1(cnmTUser);
		cnmTOrdinanza.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));
		cnmTOrdinanzaRepository.save(cnmTOrdinanza);	
				
		MessageVO response = null;
		CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.UPDATE_STATO_OK);
		if(cnmDMessaggio!=null) {
			response = new MessageVO(cnmDMessaggio.getDescMessaggio(), cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
		}else {
			throw new SecurityException("Messaggio non trovato");
		}
		return response;
	}

	@Override
	public List<DatiSentenzaPregressiResponse> getDatiSentenzaByIdOrdinanza(Integer idOrdinanza) {
		
		List<DatiSentenzaPregressiResponse> datiSentenzaPregressiResponseList = new ArrayList<DatiSentenzaPregressiResponse>();
		if (idOrdinanza == null)
			throw new IllegalArgumentException("id ordinanza = null");

		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza = null");

		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		if (cnmROrdinanzaVerbSogList == null)
			throw new IllegalArgumentException("cnmROrdinanzaVerbSogList = null");

		for(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog : cnmROrdinanzaVerbSogList) {

			DatiSentenzaPregressiResponse datiSentenzaResponse = new DatiSentenzaPregressiResponse();
			datiSentenzaResponse.setImportoTitoloSanzione(BigDecimal.ZERO);
		
			CnmTSoggetto soggetto = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTSoggetto();
			
			if(soggetto == null)
				continue;
			
			if(soggetto.getPartitaIva()!= null && soggetto.getPartitaIva().length()>0) {
				datiSentenzaResponse.setNome(soggetto.getRagioneSociale());
				datiSentenzaResponse.setIdentificativoSoggetto(soggetto.getPartitaIva());
			}else {
				datiSentenzaResponse.setNome(soggetto.getCognome() +" "+ soggetto.getNome());
				datiSentenzaResponse.setIdentificativoSoggetto(soggetto.getCodiceFiscale());				
			}
			
			List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogList = cnmRAllegatoOrdVerbSogRepository.findByCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);
			if (cnmRAllegatoOrdVerbSogList.isEmpty())
				continue;
	
			CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog = Iterables
					.tryFind(cnmRAllegatoOrdVerbSogList, UtilsTipoAllegato.findCnmRAllegatoOrdVerbSogInCnmRAllegatoOrdVerbSogsByTipoAllegato(TipoAllegato.DISPOSIZIONE_DEL_GIUDICE)).orNull();
			if (cnmRAllegatoOrdVerbSog == null)
				continue;
	
			List<CnmTAllegatoField> field = cnmTAllegatoFieldRepository.findByCnmTAllegato(cnmRAllegatoOrdVerbSog.getCnmTAllegato());
	
			CnmTAllegatoField importoSanzioneSentenza = Iterables.tryFind(field, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_IMPORTO_SANZIONE_SENTENZA))
					.orNull();
	
			datiSentenzaResponse.setImportoTitoloSanzione(importoSanzioneSentenza != null ? importoSanzioneSentenza.getValoreNumber() : BigDecimal.ZERO);

			CnmTAllegatoField dataSentenza = Iterables.tryFind(field, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_DATA_SENTENZA))
					.orNull();
	
			datiSentenzaResponse.setDataDisposizione(dataSentenza != null ? dataSentenza.getValoreData() : null);

			CnmTAllegatoField posizione = Iterables.tryFind(field, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_ESITO_SENTENZA))
					.orNull();
			if (posizione != null && posizione.getValoreNumber() != null) {
				BigDecimal idElenco = new BigDecimal(posizione.getCnmCField().getCnmDElenco().getIdElenco());
				List<CnmDElementoElenco> cnmDElementoElencoList = cnmDElementoElencoRepository.findByIdElenco(idElenco);
				CnmDElementoElenco cnmDElementoElenco = Iterables.tryFind(cnmDElementoElencoList, new Predicate<CnmDElementoElenco>() {
					public boolean apply(CnmDElementoElenco cnmDElementoElenco) {
						return cnmDElementoElenco.getIdElementoElenco() == posizione.getValoreNumber().longValue();
					}
				}).orNull();
				CnmDStatoOrdVerbSog cnmDStatoOrdVerbSog = cnmDStatoOrdVerbSogRepository.findByCnmDElementoElenco(cnmDElementoElenco);

				if (cnmDStatoOrdVerbSog != null) {
					datiSentenzaResponse.setPosizione(cnmDStatoOrdVerbSog.getDescStatoOrdVerbSog());
				}
			}
			
			datiSentenzaPregressiResponseList.add(datiSentenzaResponse);
		}
		
		return datiSentenzaPregressiResponseList;
	}

	

	@Override
	public List<RicevutaPagamentoResponse> getRicevutePagamentoByIdOrdinanza(Integer idOrdinanza) {
		
		List<RicevutaPagamentoResponse> ricevutaPagamentoResponseList = new ArrayList<RicevutaPagamentoResponse>();
		
		if (idOrdinanza == null)
			throw new IllegalArgumentException("id ordinanza = null");

		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza = null");

		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		if (cnmROrdinanzaVerbSogList == null)
			throw new IllegalArgumentException("cnmROrdinanzaVerbSogList = null");

		
		
		for(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog : cnmROrdinanzaVerbSogList) {

			RicevutaPagamentoResponse ricevutaPagamentoResponse = new RicevutaPagamentoResponse();
		
			List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogList = cnmRAllegatoOrdVerbSogRepository.findByCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);
			if (cnmRAllegatoOrdVerbSogList.isEmpty())
				continue;

			CnmTSoggetto  cnmTSoggetto = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTSoggetto();
			if (cnmTSoggetto == null)
				throw new IllegalArgumentException("cnmTSoggetto = null");
	
			CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog = Iterables
					.tryFind(cnmRAllegatoOrdVerbSogList, UtilsTipoAllegato.findCnmRAllegatoOrdVerbSogInCnmRAllegatoOrdVerbSogsByTipoAllegato(TipoAllegato.RICEVUTA_PAGAMENTO_ORDINANZA)).orNull();
			if (cnmRAllegatoOrdVerbSog == null)
				continue;
	
			List<CnmTAllegatoField> allegatoFields = cnmTAllegatoFieldRepository.findByCnmTAllegato(cnmRAllegatoOrdVerbSog.getCnmTAllegato());

			CnmTAllegatoField importoPagato = Iterables.tryFind(allegatoFields, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_IMPORTO_PAGATO_RICEVUTA_ORDINANZA)).orNull();
			CnmTAllegatoField dataPagamento = Iterables.tryFind(allegatoFields, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_DATA_PAGAMENTO_RICEVUTA_ORDINANZA)).orNull();
			CnmTAllegatoField contoCorrentePagamento = Iterables.tryFind(allegatoFields, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_CONTO_CORRENTE_VERSAMENTO)).orNull();

			// fields
			ricevutaPagamentoResponse.setImportoPagato(importoPagato != null ? importoPagato.getValoreNumber() : BigDecimal.ZERO);
			ricevutaPagamentoResponse.setDataPagamento(dataPagamento != null ? dataPagamento.getValoreData() : null );
			ricevutaPagamentoResponse.setContoCorrenteVersamento(contoCorrentePagamento != null ? contoCorrentePagamento.getValoreString() : null);

			// info soggetto
			ricevutaPagamentoResponse.setIdentificativoSoggetto(cnmTSoggetto.getCodiceFiscale() != null ? cnmTSoggetto.getCodiceFiscale() : cnmTSoggetto.getPartitaIva());		
			ricevutaPagamentoResponse.setNomeCognomeRagioneSociale((cnmTSoggetto.getNome() != null && cnmTSoggetto.getCognome() != null) ? cnmTSoggetto.getNome() + " " + cnmTSoggetto.getCognome() : cnmTSoggetto.getRagioneSociale());
		
			ricevutaPagamentoResponseList.add(ricevutaPagamentoResponse);

		}
		
		return ricevutaPagamentoResponseList;
	}
	
	
}
