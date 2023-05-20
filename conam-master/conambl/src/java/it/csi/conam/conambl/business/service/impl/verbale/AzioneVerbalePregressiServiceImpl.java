/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.FluentIterable;

import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.ordinanza.UtilsOrdinanza;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleService;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleSoggettoService;
import it.csi.conam.conambl.business.service.verbale.AzioneVerbalePregressiService;
import it.csi.conam.conambl.business.service.verbale.SoggettoVerbaleService;
import it.csi.conam.conambl.business.service.verbale.StoricizzazioneVerbaleService;
import it.csi.conam.conambl.business.service.verbale.UtilsVerbale;
import it.csi.conam.conambl.common.AzioneVerbale;
import it.csi.conam.conambl.common.AzioneVerbalePregressi;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.RegoleAllegatiCambiamentoStato;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.common.exception.RemoteWebServiceException;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento;
import it.csi.conam.conambl.integration.entity.CnmDEnte;
import it.csi.conam.conambl.integration.entity.CnmDMessaggio;
import it.csi.conam.conambl.integration.entity.CnmDStatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmRFunzionarioIstruttore;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRUserEnte;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleIllecito;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.mapper.entity.IstruttoreEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoVerbaleEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmCParametroRepository;
import it.csi.conam.conambl.integration.repositories.CnmDMessaggioRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoPregressoRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmRFunzionarioIstruttoreRepository;
import it.csi.conam.conambl.integration.repositories.CnmROrdinanzaVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRUserEnteRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleIllecitoRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleSoggettoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoFieldRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.integration.repositories.CnmTVerbaleRepository;
import it.csi.conam.conambl.request.verbale.SalvaAzioneRequest;
import it.csi.conam.conambl.response.AzioneVerbaleResponse;
import it.csi.conam.conambl.response.StatiVerbaleResponse;
import it.csi.conam.conambl.security.AppGrantedAuthority;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.UtilsTipoAllegato;
import it.csi.conam.conambl.vo.AzioneVO;
import it.csi.conam.conambl.vo.IstruttoreVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.vo.verbale.StatoVerbaleVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;

@Service
public class AzioneVerbalePregressiServiceImpl implements AzioneVerbalePregressiService {

	@Autowired
	private IstruttoreEntityMapper istruttoreEntityMapper;
	@Autowired
	private CnmRVerbaleIllecitoRepository cnmRVerbaleIllecitoRepository;
	@Autowired
	private CnmRUserEnteRepository cnmRUserEnteRepository;
	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private AllegatoVerbaleService allegatoVerbaleService;
	@Autowired
	private CnmDStatoVerbaleRepository cnmDStatoVerbaleRepository;
	@Autowired
	private StoricizzazioneVerbaleService storicizzazioneVerbaleService;
	@Autowired
	private SoggettoVerbaleService soggettoVerbaleService;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmRFunzionarioIstruttoreRepository cnmRFunzionarioIstruttoreRepository;
	@Autowired
	private CnmRAllegatoVerbaleRepository cnmRAllegatoVerbaleRepository;
	@Autowired
	private CommonAllegatoService commonAllegatoService;
	@Autowired
	private UtilsVerbale utilsVerbale;
	
	
	@Autowired
	private StatoVerbaleEntityMapper statoVerbaleEntityMapper;
	@Autowired
	private CnmCParametroRepository cnmCParametroRepository;
	@Autowired
	private CnmDStatoPregressoRepository cnmDStatoPregressoRepository;
	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;
	@Autowired
	private UtilsOrdinanza utilsOrdinanza;

	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	/*@Autowired
	private UtilsTraceCsiLogAuditService utilsTraceCsiLogAuditService;*/
	@Autowired
	private CnmTAllegatoFieldRepository cnmTAllegatoFieldRepository;
	

	@Autowired
	private AllegatoVerbaleSoggettoService allegatoVerbaleSoggettoService;
	
	private static Logger logger = Logger.getLogger(AzioneVerbalePregressiService.class);

	
	@Override
	public StatiVerbaleResponse getStatiVerbalePregresso(Integer id, UserDetails userDetails, Boolean includiControlloUtenteProprietario) {
		
		
		if (includiControlloUtenteProprietario == null)
			throw new IllegalArgumentException("includiControlloUtenteProprietario is null");
		
		//mi predispongo a restituire una lista, anche se al momento lo stato avanzabile sara' al massimo 1
		List<StatoVerbaleVO> statiVerbale = new ArrayList<>();
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);

		if(Constants.STATO_PREGRESSO_IN_LAVORAZIONE != cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso()) {
			throw new BusinessException(ErrorCode.STATO_VERBALE_INCOMPATIBILE);
		}
		
		// 20210226_LC Jira 125 - non fa il check sull'utente creatore
		if(cnmTVerbale.getCnmTUser2().getIdUser() != userDetails.getIdUser() && includiControlloUtenteProprietario) {
			throw new SecurityException("Utente non abilitato ad accedere a questo servizio");
		}
		StatoVerbaleVO statoVerbaleFineLavorazionePregresso = new StatoVerbaleVO();
		statoVerbaleFineLavorazionePregresso.setDenominazione(cnmDStatoPregressoRepository.findOne(Constants.STATO_PREGRESSO_LAVORATO).getDescStatoPregresso());
		statoVerbaleFineLavorazionePregresso.setId(Constants.STATO_VERBALE_FINE_PREGRESSO);
		
		StatiVerbaleResponse response = new StatiVerbaleResponse();
		CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.CONFIRM_SALVA_STATO_FASCICOLO_PREGRESSO);
		if(cnmDMessaggio!=null) {
			MessageVO messaggio = new MessageVO(String.format(cnmDMessaggio.getDescMessaggio(), cnmTVerbale.getNumVerbale(), cnmTVerbale.getCnmDStatoVerbale().getDescStatoVerbale(), Constants.PLACEHOLDER_MSG_NUOVO_STATO), cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
			response.setMessaggio(messaggio);
		}else {
			throw new SecurityException("Messaggio non trovato");
		}
		// se la data e' successiva alla data termine pregresso restituisco solo lo stato per il termine lavorazione
		Date dataTerminePregresso = cnmCParametroRepository.findOne(Constants.ID_DATA_TERMINE_GESTIONE_PREGRESSO).getValoreDate();
		if(new Date().after(dataTerminePregresso)) {
			logger.info("Oltrepassata la data termine recupero pregresso, restituisco solo lo stato fine lavorazione");
			statiVerbale.add(statoVerbaleFineLavorazionePregresso);
			response.setStati(statiVerbale);
			return response;
		}
		List<Long> statiAvanzabiliByCnmTVerbale = getStatiAvanzabiliByCnmTVerbale(cnmTVerbale, userDetails);
		boolean aggiungiAttesaVerifica = false;
		boolean aggiungiMancanzaCF = false;
		if(statiAvanzabiliByCnmTVerbale != null) {
			if(statiAvanzabiliByCnmTVerbale.contains(Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO)) {
				statiAvanzabiliByCnmTVerbale.remove(Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO);
				aggiungiAttesaVerifica = true;
			}
			if(statiAvanzabiliByCnmTVerbale.contains(Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO)) {
				statiAvanzabiliByCnmTVerbale.remove(Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO);
				aggiungiMancanzaCF = true;
			}
		}
		if(statiAvanzabiliByCnmTVerbale != null && !statiAvanzabiliByCnmTVerbale.isEmpty()) {
			logger.debug("trovati "+statiAvanzabiliByCnmTVerbale.size()+" stati");
			statiVerbale = statoVerbaleEntityMapper.mapListEntityToListVO((List<CnmDStatoVerbale>) cnmDStatoVerbaleRepository.findAll(statiAvanzabiliByCnmTVerbale));
		}
		
		// visualizzo lo stato STATO_VERBALE_FINE_PREGRESSO, solo se il verbale e' in uno stato diverso da STATO_VERBALE_INCOMPLETO
		if(Constants.STATO_VERBALE_INCOMPLETO	 != cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()) {
			statiVerbale.add(statoVerbaleFineLavorazionePregresso);
		}
		
		if(aggiungiAttesaVerifica) {
			try {				
				StatoVerbaleVO statoVerbaleAttesaVerificaPagamentoPregresso = new StatoVerbaleVO();
				
				statoVerbaleAttesaVerificaPagamentoPregresso.setDenominazione(cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO).getDescStatoVerbale());
				statoVerbaleAttesaVerificaPagamentoPregresso.setId(Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO);
				
				CnmDMessaggio cnmDMessaggioWar = cnmDMessaggioRepository.findByCodMessaggio(AzioneVerbale.IN_ATTESA_VERIFICA_PAGAMENTO.getWarningMessageCode());
				String msg=cnmDMessaggioWar.getDescMessaggio();
				statoVerbaleAttesaVerificaPagamentoPregresso.setWarningMessage(new MessageVO(msg, cnmDMessaggioWar.getCnmDTipoMessaggio().getDescTipoMessaggio()));
				
				CnmDMessaggio cnmDMessaggioConf = cnmDMessaggioRepository.findByCodMessaggio(AzioneVerbale.IN_ATTESA_VERIFICA_PAGAMENTO.getConfirmMessageCode());
				msg=cnmDMessaggioConf.getDescMessaggio();
				msg = String.format(msg, cnmTVerbale.getNumVerbale());
				statoVerbaleAttesaVerificaPagamentoPregresso.setConfirmMessage(new MessageVO(msg, cnmDMessaggioConf.getCnmDTipoMessaggio().getDescTipoMessaggio()));
			
				statiVerbale.add(statoVerbaleAttesaVerificaPagamentoPregresso);
			}catch(Throwable t) {
				t.printStackTrace();
			}
		}

		if(aggiungiMancanzaCF) {
//		if ((cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_INCOMPLETO || cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO)) {
			// 20210908 PP - se lo stato assegnabile al verbale e' STATO_VERBALE_ACQUISITO, controllo se ci sono soggetti senza CF, se si restituisco lo stato STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO
			List<SoggettoVO> soggetti = soggettoVerbaleService.getSoggettiByIdVerbale(cnmTVerbale.getIdVerbale(), userDetails, false);
			boolean soggettiSenzaCF = false;
			for(SoggettoVO soggetto : soggetti) {
				if(soggetto.getPersonaFisica() && (soggetto.getCodiceFiscale()==null || soggetto.getCodiceFiscale().length()==0)) {
					soggettiSenzaCF = true;
					break;
				}
			}
			if(soggettiSenzaCF) {
//				idStato = Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO;
				StatoVerbaleVO statoVerbaleMancanzaCF = new StatoVerbaleVO();
				
				statoVerbaleMancanzaCF.setDenominazione(cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO).getDescStatoVerbale());
				statoVerbaleMancanzaCF.setId(Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO);
				
				CnmDMessaggio cnmDMessaggioWar = cnmDMessaggioRepository.findByCodMessaggio(AzioneVerbalePregressi.ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO.getWarningMessageCode());
				String msg=cnmDMessaggioWar.getDescMessaggio();
				statoVerbaleMancanzaCF.setWarningMessage(new MessageVO(msg, cnmDMessaggioWar.getCnmDTipoMessaggio().getDescTipoMessaggio()));
				
				CnmDMessaggio cnmDMessaggioConf = cnmDMessaggioRepository.findByCodMessaggio(AzioneVerbalePregressi.ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO.getConfirmMessageCode());
				msg=cnmDMessaggioConf.getDescMessaggio();
				msg = String.format(msg, cnmTVerbale.getNumVerbale());
				statoVerbaleMancanzaCF.setConfirmMessage(new MessageVO(msg, cnmDMessaggioConf.getCnmDTipoMessaggio().getDescTipoMessaggio()));
			
				statiVerbale.add(statoVerbaleMancanzaCF);
			}
		}
//		if(Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO	== cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()){
//			statiVerbale = new ArrayList<>();
//		}
		response.setStati(statiVerbale);
		return response;
	}

	@Override
	public List<IstruttoreVO> getIstruttoreByVerbale(Integer idVerbale, UserDetails userDetails) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(idVerbale);
		List<CnmRVerbaleIllecito> rel = cnmRVerbaleIllecitoRepository.findByCnmTVerbale(cnmTVerbale);
		if (rel.isEmpty())
			throw new SecurityException("errore non è possibile trovarsi in questa situazione");

		CnmDEnte cnmDEnte = rel.get(0).getCnmDLettera().getCnmDComma().getCnmDArticolo().getCnmREnteNorma().getCnmDEnte();
		if (cnmDEnte == null)
			throw new SecurityException("ente non trovato");

		Long idEnte = cnmDEnte.getIdEnte();
		List<IstruttoreVO> istruttoreList = new ArrayList<>();
		if (!SecurityUtils.isEnteNormaValido(idEnte))
			throw new SecurityException("Ente non abilitato");

		List<CnmRUserEnte> cnmRUserEnteList = cnmRUserEnteRepository.findByCnmDEnteAndTipoUtilizzoAndFineValidita(cnmDEnte, Constants.ID_UTILIZZO_ENTE_LEGGE);
		if (cnmRUserEnteList == null || cnmRUserEnteList.isEmpty())
			throw new SecurityException(" nessun utente con ente leggi corrispondenti");
		for (CnmRUserEnte cnmRUserEnte : cnmRUserEnteList) {
			CnmTUser cnmTUser = cnmRUserEnte.getCnmTUser();
			if (cnmTUser.getCnmDRuolo().getIdRuolo() == Long.parseLong(Constants.RUOLO_UTENTE_ISTRUTTORE) ||
					(cnmTUser.getCnmDRuolo().getIdRuolo() == Long.parseLong(Constants.RUOLO_UTENTE_AMMINISTRATIVO) && 
					(cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_INCOMPLETO||cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO))) {
				istruttoreList.add(istruttoreEntityMapper.mapEntityToVO(cnmTUser));
			}
		}

		return istruttoreList;
	}
	
	private List<Long> getStatiAvanzabiliByCnmTVerbale(CnmTVerbale cnmTVerbale, UserDetails userDetails) {
		Long idStatoVerbale = cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale();

		// controllo soggetti associati
		List<SoggettoVO> soggetti = soggettoVerbaleService.getSoggettiByIdVerbale(cnmTVerbale.getIdVerbale(), userDetails, false);
		if (soggetti == null || soggetti.isEmpty())
			return null;

		// controllo allegati associati
		List<TipoAllegatoVO> tipologiaAllegatiList = allegatoVerbaleService.getTipologiaAllegatiByCnmVerbale(cnmTVerbale);
		if (tipologiaAllegatiList == null || tipologiaAllegatiList.isEmpty())
			return null;

		List<Long> idAllegatoList = FluentIterable.from(tipologiaAllegatiList).transform(UtilsTipoAllegato.converTipoAllegatoToLong()).toList();

		List<Long> idStati = new ArrayList<>();

		// Controllo permessi azione
		long idProprietarioVerbale = cnmTVerbale.getCnmTUser2().getIdUser();
		long idUserConnesso = userDetails.getIdUser();
		Long idUserAssegnato = null;
		CnmRFunzionarioIstruttore istr = cnmRFunzionarioIstruttoreRepository.findByCnmTVerbaleAndDataAssegnazione(cnmTVerbale);
		if (istr != null)
			idUserAssegnato = istr.getCnmTUser().getIdUser();
		if (!controllaPermessiAzione(idStatoVerbale, idProprietarioVerbale, idUserConnesso, idUserAssegnato))
			return null;
		boolean aggiungiAttesaPagamento = false;
		boolean conScritti = false;
		Long idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI, idAllegatoList,
				RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI);
		if (idStato != null && idStato != cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()) {
			idStati.add(idStato);
			aggiungiAttesaPagamento = true;
			conScritti = true;
		}
		idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO, idAllegatoList,
				RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO);
		if (idStato != null && idStato != cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()) {
			idStati.add(idStato);
			aggiungiAttesaPagamento = true;
		}
		idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO, idAllegatoList, RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO);
		if (idStato != null && idStato != cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()) {
			idStati.add(idStato);
			aggiungiAttesaPagamento = true;
		}		
		
		idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA, idAllegatoList, RegoleAllegatiCambiamentoStato.STATO_VERBALE_ARCHIVIAZIONE);
		if (idStato != null && idStato != cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()) {
			idStati.add(idStato);
		}
		
		// se il fascicolo e' stato acquisito controllo anche se tutti i soggetti hanno una ordinanza, in caso devo restituire anche questo stato verbale
		if(Constants.STATO_VERBALE_INCOMPLETO != cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() 
				&& Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO != cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() 
				&& Constants.STATO_VERBALE_ORDINANZA != cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()
				&& utilsOrdinanza.soggettiVerbaleCompletiDiOrdinanza(cnmTVerbale)) {
			idStati.add(Constants.STATO_VERBALE_ORDINANZA);
		}
		if(aggiungiAttesaPagamento) {
			if( Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO != cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()) {
				idStati.add(Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO);
			}else {
				idStati = new ArrayList<>();
				// controllo se ci sono pagamenti totali per il verbale
				BigDecimal importoPagato = cnmTAllegatoFieldRepository.getImportoPagatoByIdVerbale(cnmTVerbale.getIdVerbale());
				if(importoPagato == null) {
					importoPagato = new BigDecimal(0);
				}
				if(importoPagato.compareTo(cnmTVerbale.getImportoVerbale()) == 0) {
					idStati.add(Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO);
				}else {
					if(conScritti)
						idStati.add(Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI);
					else
						idStati.add(Constants.STATO_VERBALE_ACQUISITO);
				}
			}
			
		}
		if (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_INCOMPLETO
//				|| cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO)
				) {
			idStati.add(Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO);
		}
		
		if (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO) {
			idStati = new ArrayList<>();
		}
		return idStati;

	}
	
	
	@Override
	@Transactional
	public MessageVO salvaStatoVerbalePregresso(SalvaAzioneRequest salvaAzione, UserDetails userDetails) {
		// per il momento anche se uso il model SalvaAzioneRequest all'interno c'e' l'idStato, non l'idAzione
		Long idStato = salvaAzione.getIdAzione();
		Integer idVerbale = salvaAzione.getId();
		Integer idFunzionario = salvaAzione.getIdFunzionario();
		
		if (idStato == null)
			throw new IllegalArgumentException("id stato = null");

		logger.debug("salvo lo stato con id "+idStato+" per il verbale "+idVerbale);
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(idVerbale);
		String statoPrec = cnmTVerbale.getCnmDStatoVerbale().getDescStatoVerbale();
		
		if(Constants.STATO_PREGRESSO_IN_LAVORAZIONE != cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso()) {
			throw new BusinessException(ErrorCode.STATO_VERBALE_INCOMPATIBILE);
		}

		Date dataTerminePregresso = cnmCParametroRepository.findOne(Constants.ID_DATA_TERMINE_GESTIONE_PREGRESSO).getValoreDate();
		if(new Date().after(dataTerminePregresso) && Constants.STATO_PREGRESSO_LAVORATO != idStato) {
			throw new BusinessException(ErrorCode.LAVORAZIONE_FASCICOLO_PREGRESSO_SCADUTA);
		}
		List<Long> idStatiAvanzabili= getStatiAvanzabiliByCnmTVerbale(cnmTVerbale, userDetails);
		if(idStato != Constants.STATO_VERBALE_FINE_PREGRESSO 
				&& (idStatiAvanzabili == null || !idStatiAvanzabili.contains(idStato))) {
			throw new SecurityException("Stato non permesso");
		}
		
		AzioneVO azioneVerbale = AzioneVerbalePregressi.getAzioneVerbaleByIdStato(idStato, cnmTVerbale.getNumVerbale(), cnmDMessaggioRepository);		

		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		storicizzazioneVerbaleService.storicizzaStatoVerbale(cnmTVerbale, cnmTUser);
		
		MessageVO responseMsg = null;

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmDStatoVerbale cnmDStatoVerbale;
		boolean assegnaEnable;
		boolean terminaLavorazionePregresso = false;
		if (AzioneVerbalePregressi.ACQUISISCI.getId().equals(azioneVerbale.getId())) {
			cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO);
			assegnaEnable = AzioneVerbalePregressi.ACQUISISCI.isListaIstruttori();
		} else if (AzioneVerbalePregressi.ACQUISISCI_CON_PAGAMENTO.getId().equals(azioneVerbale.getId())) {
			cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO);
			assegnaEnable = AzioneVerbalePregressi.ACQUISISCI_CON_PAGAMENTO.isListaIstruttori();
		} else if (AzioneVerbalePregressi.ACQUISISCI_CON_SCRITTI.getId().equals(azioneVerbale.getId())) {
			cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI);
			assegnaEnable = AzioneVerbalePregressi.ACQUISISCI_CON_SCRITTI.isListaIstruttori();
		} else if (AzioneVerbalePregressi.ARCHIVIATO_IN_AUTOTUTELA.getId().equals(azioneVerbale.getId())) {
			cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA);
			assegnaEnable = AzioneVerbalePregressi.ARCHIVIATO_IN_AUTOTUTELA.isListaIstruttori();
		} else if (AzioneVerbalePregressi.TERMINE_RECUPERO_PREGRESSO.getId().equals(azioneVerbale.getId())) {
			//queso stato non sta sulla cnmDStatoVerbale
//			cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_FINE_PREGRESSO);
			terminaLavorazionePregresso = true;
			cnmDStatoVerbale = cnmTVerbale.getCnmDStatoVerbale();
			assegnaEnable = AzioneVerbalePregressi.TERMINE_RECUPERO_PREGRESSO.isListaIstruttori();
		} else if (AzioneVerbalePregressi.ORDINANZA.getId().equals(azioneVerbale.getId())) {
			cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ORDINANZA);
			assegnaEnable = AzioneVerbalePregressi.ARCHIVIATO_IN_AUTOTUTELA.isListaIstruttori();
		} else if (AzioneVerbalePregressi.IN_ATTESA_VERIFICA_PAGAMENTO.getId().equals(azioneVerbale.getId())) {
			cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO);
			assegnaEnable = AzioneVerbalePregressi.IN_ATTESA_VERIFICA_PAGAMENTO.isListaIstruttori();
		} else if (AzioneVerbalePregressi.ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO.getId().equals(azioneVerbale.getId())) {
			cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO);
			assegnaEnable = AzioneVerbalePregressi.ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO.isListaIstruttori();
		} else
			throw new IllegalArgumentException("azione non gestita");

		String statoSucc = cnmDStatoVerbale.getDescStatoVerbale();
		// se la lista istruttori è presente controllo che sia valorizzato il
		// funzionario
		if (assegnaEnable && idFunzionario == null)
			throw new IllegalArgumentException("idFunzioanrio =null");

		if(terminaLavorazionePregresso) {
			cnmTVerbale.setCnmDStatoPregresso(cnmDStatoPregressoRepository.findOne(Constants.STATO_PREGRESSO_LAVORATO));
			
			responseMsg = null;
			CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.SALVA_STATO_FASCICOLO_PREGRESSO_OK_TERMINA_PREG);
			if(cnmDMessaggio!=null) {
				responseMsg = new MessageVO(String.format(cnmDMessaggio.getDescMessaggio(), statoPrec, statoSucc), cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
			}else {
				throw new SecurityException("Messaggio non trovato");
			}
		} else {
			if (cnmTVerbale.getNumeroProtocollo() == null) {
				List<CnmRAllegatoVerbale> cnmRAllegatoVerbaleList = cnmRAllegatoVerbaleRepository.findByCnmTVerbale(cnmTVerbale);
				if (!cnmRAllegatoVerbaleList.isEmpty()) {
					try {
						ResponseProtocollaDocumento response = commonAllegatoService.avviaProtocollazione(cnmRAllegatoVerbaleList, cnmTUser);
						cnmTVerbale.setNumeroProtocollo(response.getProtocollo());
						cnmTVerbale.setDataOraProtocollo(now);
						
						CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.SALVA_STATO_FASCICOLO_PREGRESSO_OK_SPOSTA_ACTA_OK);
						if(cnmDMessaggio!=null) {
							responseMsg = new MessageVO(String.format(cnmDMessaggio.getDescMessaggio(), statoPrec, statoSucc), cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
						}else {
							throw new SecurityException("Messaggio non trovato");
						}
					}catch (Throwable t) {
						if(t instanceof SecurityException) {
							responseMsg = new MessageVO(t.getMessage(), "danger");
						}else if(t instanceof RemoteWebServiceException) {
							CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(((RemoteWebServiceException)t).getCodice());
							if(cnmDMessaggio!=null) {

//								throw new BusinessException(((RemoteWebServiceException)t).getCodice());
								responseMsg = new MessageVO(cnmDMessaggio.getDescMessaggio(), cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
							}else {
								throw new SecurityException("Messaggio non trovato");
							}
						}else {
							responseMsg = new MessageVO("Attenzione! Il servizio di protocollazione non e' al momento disponibile.", "danger");
						}

//						return responseMsg;
					}
				}
			}

			cnmTVerbale.setCnmDStatoVerbale(cnmDStatoVerbale);
		}
		cnmTVerbale.setDataOraUpdate(now);
		cnmTVerbale.setCnmTUser1(cnmTUser);

		if (assegnaEnable) {
			logger.info("Assegno il funzionario istruttore con id="+idFunzionario);
			Boolean notUguale = Boolean.FALSE;
			CnmRFunzionarioIstruttore cnmRFunzionarioIstruttore = cnmRFunzionarioIstruttoreRepository.findByCnmTVerbaleAndDataAssegnazione(cnmTVerbale);
			if (cnmRFunzionarioIstruttore != null && cnmRFunzionarioIstruttore.getCnmTUser().getIdUser() != userDetails.getIdUser()) {
				cnmRFunzionarioIstruttore.setFineAssegnazione(new Date());
				cnmRFunzionarioIstruttoreRepository.save(cnmRFunzionarioIstruttore);
				notUguale = Boolean.TRUE;
			}

			if (cnmRFunzionarioIstruttore == null || notUguale) {	
				CnmTUser cnmTUserIstruttore = cnmTUserRepository.findOne(idFunzionario.longValue());

				cnmRFunzionarioIstruttore = cnmRFunzionarioIstruttoreRepository.findByCnmTUserAndCnmTVerbale(cnmTUserIstruttore, cnmTVerbale);
				if(cnmRFunzionarioIstruttore == null) {
					cnmRFunzionarioIstruttore = new CnmRFunzionarioIstruttore();
				}else {
					cnmRFunzionarioIstruttore.setFineAssegnazione(null);
				}
				cnmRFunzionarioIstruttore.setCnmTUser(cnmTUserIstruttore);
				cnmRFunzionarioIstruttore.setCnmTVerbale(cnmTVerbale);
				cnmRFunzionarioIstruttore.setInizioAssegnazione(new Date());
				cnmRFunzionarioIstruttoreRepository.save(cnmRFunzionarioIstruttore);
			}
		}

		cnmTVerbaleRepository.save(cnmTVerbale);

		// TODO: aggiungere logAudit
		// 20201124_LC qui il verbale è già creato, traccia solo su VerbaleServiceImpl.salvaVerbale

		if(responseMsg == null) {
			CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.SALVA_STATO_FASCICOLO_PREGRESSO_OK);
			if(cnmDMessaggio!=null) {
				responseMsg = new MessageVO(String.format(cnmDMessaggio.getDescMessaggio(), statoPrec, statoSucc), cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
			}else {
				throw new SecurityException("Messaggio non trovato");
			}
		}
		
		return responseMsg;
	}

	@Override
	public AzioneVerbaleResponse getAzioniVerbale(Integer id, UserDetails userDetails) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);
		AzioneVerbaleResponse azioneVerbale = new AzioneVerbaleResponse();

		Boolean isAllegatiEnable = Boolean.FALSE;
		AppGrantedAuthority appGrantedAuthority = SecurityUtils.getRuolo(userDetails.getAuthorities());
		Long idStatoVerbale = cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale();

		// aggiungi allegati enable
		List<TipoAllegatoVO> tipologiaAllegabili = allegatoVerbaleService.getTipologiaAllegatiAllegabiliVerbale(id, null, false);

//		20201130_ET aggiunto per fare in modo che spariscano tutti i bottoni dopo il salvataggio dello stato recupero pregresso terminato 
		if(cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != Constants.STATO_PREGRESSO_IN_LAVORAZIONE){
			List<AzioneVO> azioneList = new ArrayList<AzioneVO>();
			azioneList.add(AzioneVerbalePregressi.getAzioneVerbaleByIdStato(getStatoAvanzabileByCnmTVerbale(cnmTVerbale, userDetails), cnmTVerbale.getNumVerbale(), cnmDMessaggioRepository));
			azioneVerbale.setAzioneList(azioneList);
			azioneVerbale.setModificaVerbaleEnable(Boolean.FALSE);
			azioneVerbale.setEliminaAllegatoEnable(Boolean.FALSE);
			azioneVerbale.setAggiungiAllegatoEnable(Boolean.FALSE);
			azioneVerbale.setRiepilogoOrdinanzaEnable(Boolean.FALSE);
			return azioneVerbale;
		}
		else if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ISTRUTTORE)) {
			if ((Constants.STATO_VERBALE_INCOMPLETO != cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()
					&& Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO != cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale())) {
				CnmRFunzionarioIstruttore cnmRFunzionarioIstruttore = cnmRFunzionarioIstruttoreRepository.findByCnmTVerbaleAndDataAssegnazione(cnmTVerbale);
				if (cnmRFunzionarioIstruttore != null && cnmRFunzionarioIstruttore.getCnmTUser().getIdUser() == userDetails.getIdUser() && tipologiaAllegabili != null
						&& !tipologiaAllegabili.isEmpty())
					isAllegatiEnable = Boolean.TRUE;
			} else {
				if (tipologiaAllegabili != null && !tipologiaAllegabili.isEmpty() && cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser())
					isAllegatiEnable = Boolean.TRUE;
			}
		} else if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ACCERTATORE) || appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_AMMINISTRATIVO)) {
			if (cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser() && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != Constants.STATO_PREGRESSO_NON_PRESENTE)
				isAllegatiEnable = Boolean.TRUE;
		} else
			throw new SecurityException("Ruolo non riconosciuto dal sistema");

		Boolean modificaVerbaleEnable = (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_INCOMPLETO ) && cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser()
				? Boolean.TRUE
				: Boolean.FALSE;
		Boolean isEliminaAllegatoEnable = (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_INCOMPLETO )
				&& cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser() ? Boolean.TRUE : Boolean.FALSE;

		// restituisco isRiepilogoOrdinanzaEnable a true solo se sul verbale esiste almeno un allegato di tipo ORDINANZA
		Boolean isRiepilogoOrdinanzaEnable = false;
		
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		if (cnmRVerbaleSoggettoList != null && !cnmRVerbaleSoggettoList.isEmpty()) {
			List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettoList);
			if (cnmROrdinanzaVerbSogList != null && !cnmROrdinanzaVerbSogList.isEmpty()) {
				isRiepilogoOrdinanzaEnable = true;
			}
		}
		
//		List<CnmRAllegatoVerbale> cnmRAllegatoVerbaleList = cnmRAllegatoVerbaleRepository.findByCnmTVerbale(cnmTVerbale);
//		if (cnmRAllegatoVerbaleList != null && !cnmRAllegatoVerbaleList.isEmpty()) {
//			for (CnmRAllegatoVerbale cnmRAllegatoVerbale : cnmRAllegatoVerbaleList) {
//				if(cnmRAllegatoVerbale.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId() 
//						|| cnmRAllegatoVerbale.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId()) {
//					isRiepilogoOrdinanzaEnable = true;
//					break;
//				}
//			}
//		}
		List<AzioneVO> azioneList = new ArrayList<AzioneVO>();
		azioneList.add(AzioneVerbalePregressi.getAzioneVerbaleByIdStato(getStatoAvanzabileByCnmTVerbale(cnmTVerbale, userDetails), cnmTVerbale.getNumVerbale(), cnmDMessaggioRepository));
		azioneVerbale.setAzioneList(azioneList);
		azioneVerbale.setModificaVerbaleEnable(modificaVerbaleEnable);
		azioneVerbale.setEliminaAllegatoEnable(isEliminaAllegatoEnable);
		azioneVerbale.setAggiungiAllegatoEnable(isAllegatiEnable);
		azioneVerbale.setRiepilogoOrdinanzaEnable(isRiepilogoOrdinanzaEnable);
		

		if (idStatoVerbale == Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO ) {
//			azioneVerbale.setAzioneList(new ArrayList<AzioneVO>());
			azioneVerbale.setModificaVerbaleEnable(false);
			azioneVerbale.setEliminaAllegatoEnable(false);
//			azioneVerbale.setAggiungiAllegatoEnable(false);
			
//			List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettosWithoutAllegato = allegatoVerbaleSoggettoService.findCnmRVerbaleSoggettosWithoutAllegato(cnmTVerbale, TipoAllegato.RELATA_NOTIFICA);
//			if(cnmRVerbaleSoggettosWithoutAllegato != null && cnmRVerbaleSoggettosWithoutAllegato.size()>0) {
//				azioneVerbale.setAggiungiAllegatoEnable(true);
//			}		
		
		}
		
		
		return azioneVerbale;
	}
	private Long getStatoAvanzabileByCnmTVerbale(CnmTVerbale cnmTVerbale, UserDetails userDetails) {
		Long idStatoVerbale = cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale();

		// controllo soggetti associati
		List<SoggettoVO> soggetti = soggettoVerbaleService.getSoggettiByIdVerbale(cnmTVerbale.getIdVerbale(), userDetails, false);
		if (soggetti == null || soggetti.isEmpty())
			return null;

		// controllo allegati associati
		List<TipoAllegatoVO> tipologiaAllegatiList = allegatoVerbaleService.getTipologiaAllegatiByCnmVerbale(cnmTVerbale);
		if (tipologiaAllegatiList == null || tipologiaAllegatiList.isEmpty())
			return null;

		List<Long> idAllegatoList = FluentIterable.from(tipologiaAllegatiList).transform(UtilsTipoAllegato.converTipoAllegatoToLong()).toList();

		Long idStato;

		// Controllo permessi azione
		long idProprietarioVerbale = cnmTVerbale.getCnmTUser2().getIdUser();
		long idUserConnesso = userDetails.getIdUser();
		Long idUserAssegnato = null;
		CnmRFunzionarioIstruttore istr = cnmRFunzionarioIstruttoreRepository.findByCnmTVerbaleAndDataAssegnazione(cnmTVerbale);
		if (istr != null)
			idUserAssegnato = istr.getCnmTUser().getIdUser();
		if (!controllaPermessiAzione(idStatoVerbale, idProprietarioVerbale, idUserConnesso, idUserAssegnato))
			return null;

		if ((idStatoVerbale == Constants.STATO_VERBALE_INCOMPLETO || idStatoVerbale == Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO)) {
			
			// 20210908 PP - se lo stato assegnabile al verbale e' STATO_VERBALE_ACQUISITO, controllo se ci sono soggetti senza CF, se si restituisco lo stato STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO
			boolean soggettiSenzaCF = false;
			for(SoggettoVO soggetto : soggetti) {
				if(soggetto.getPersonaFisica() && (soggetto.getCodiceFiscale()==null || soggetto.getCodiceFiscale().length()==0)) {
					soggettiSenzaCF = true;
					break;
				}
			}
			if(soggettiSenzaCF) {
				idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO, idAllegatoList, RegoleAllegatiCambiamentoStato.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO);
			}else {
				idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI, idAllegatoList,
						RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI);
				if (idStato == null) {
					idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO, idAllegatoList,
							RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO);
				}
				if (idStato == null) {
					idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO, idAllegatoList, RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO);
				}
					
			}

			return idStato;
		} else if (idStatoVerbale == Constants.STATO_VERBALE_ACQUISITO) {
			idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA, idAllegatoList, RegoleAllegatiCambiamentoStato.STATO_VERBALE_ARCHIVIAZIONE);

			if (idStato == null) {
				idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI, idAllegatoList,
						RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI);
			}
			if (idStato == null) {
				idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO, idAllegatoList,
						RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO);

				// 20211207 PP - Anche in caso di STATO_VERBALE_ACQUISITO, imposto lo stato STATO_VERBALE_ACQUISITO_CON_PAGAMENTO, solo se i pagamenti sono totali
				// segnalazione via mail da Cacciuttolo il 07/12/2021 14:13
				if(idStato!= null) {
					// controllo se ci sono pagamenti totali per il verbale
					BigDecimal importoPagato = cnmTAllegatoFieldRepository.getImportoPagatoByIdVerbale(cnmTVerbale.getIdVerbale());
					if(importoPagato == null) {
						importoPagato = new BigDecimal(0);
					}
					if(importoPagato.compareTo(cnmTVerbale.getImportoVerbale()) != 0) {
						idStato = null;
					}
				}
			}

			return idStato;
		}

		return null;
	}

	/*
	 * true utente ha i permessi false utente non ha i permessi
	 */
	@Override
	public boolean controllaPermessiAzione(Long idStatoVerbale, long idProprietarioVerbale, long idUserConnesso, Long idUserAssegnato) {
		if ((idStatoVerbale == Constants.STATO_VERBALE_INCOMPLETO || idStatoVerbale == Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO)) {
			if (idProprietarioVerbale == idUserConnesso)
				return true;
		}
		if (idStatoVerbale == Constants.STATO_VERBALE_ACQUISITO || idStatoVerbale == Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI
				|| idStatoVerbale == Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO || idStatoVerbale == Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA) {
			// aggiungo la condizione idUserAssegnato == null && idProprietarioVerbale == idUserConnesso perche nei pregressi anche se il fascicolo e' acquisito potrei nn aver assegnato un funzionario
			return (idUserAssegnato != null && idUserAssegnato == idUserConnesso) || (idUserAssegnato == null && idProprietarioVerbale == idUserConnesso);
		}

		return false;
	}

}
