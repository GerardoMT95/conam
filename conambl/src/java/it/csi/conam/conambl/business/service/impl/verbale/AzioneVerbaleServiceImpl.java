/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;

import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleService;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleSoggettoService;
import it.csi.conam.conambl.business.service.verbale.AzioneVerbaleService;
import it.csi.conam.conambl.business.service.verbale.SoggettoVerbaleService;
import it.csi.conam.conambl.business.service.verbale.StoricizzazioneVerbaleService;
import it.csi.conam.conambl.business.service.verbale.UtilsVerbale;
import it.csi.conam.conambl.common.AzioneVerbale;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.RegoleAllegatiCambiamentoStato;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento;
import it.csi.conam.conambl.integration.entity.CnmDEnte;
import it.csi.conam.conambl.integration.entity.CnmDStatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmRFunzionarioIstruttore;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRUserEnte;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleIllecito;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.mapper.entity.IstruttoreEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmDMessaggioRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmRFunzionarioIstruttoreRepository;
import it.csi.conam.conambl.integration.repositories.CnmROrdinanzaVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRUserEnteRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleIllecitoRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleSoggettoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoFieldRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.integration.repositories.CnmTVerbaleRepository;
import it.csi.conam.conambl.request.verbale.SalvaAzioneRequest;
import it.csi.conam.conambl.response.AzioneVerbaleResponse;
import it.csi.conam.conambl.security.AppGrantedAuthority;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.UtilsTipoAllegato;
import it.csi.conam.conambl.vo.AzioneVO;
import it.csi.conam.conambl.vo.IstruttoreVO;
import it.csi.conam.conambl.vo.UtenteVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class AzioneVerbaleServiceImpl implements AzioneVerbaleService {

	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;

	@Autowired
	private CnmRUserEnteRepository cnmRUserEnteRepository;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	protected AllegatoVerbaleService allegatoVerbaleService;
	@Autowired
	private IstruttoreEntityMapper istruttoreEntityMapper;
	@Autowired
	private CnmDStatoVerbaleRepository cnmDStatoVerbaleRepository;
	@Autowired
	private StoricizzazioneVerbaleService storicizzazioneVerbaleService;
	@Autowired
	protected SoggettoVerbaleService soggettoVerbaleService;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmRVerbaleIllecitoRepository cnmRVerbaleIllecitoRepository;
	@Autowired
	private CnmRFunzionarioIstruttoreRepository cnmRFunzionarioIstruttoreRepository;
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private CnmRAllegatoVerbSogRepository cnmRAllegatoVerbSogRepository;
	@Autowired
	private CnmRAllegatoVerbaleRepository cnmRAllegatoVerbaleRepository;
	@Autowired
	private CommonAllegatoService commonAllegatoService;
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;

	@Autowired
	private AllegatoVerbaleSoggettoService allegatoVerbaleSoggettoService;
	
	@Autowired
	protected UtilsVerbale utilsVerbale;

	@Autowired
	private CnmTAllegatoFieldRepository cnmTAllegatoFieldRepository;
	
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
			boolean userValid = cnmTUser.getFineValidita() == null || cnmTUser.getFineValidita().after(new Date());	// 20211126_LC Jira 176 esclude utenti non validi
			if (cnmTUser.getCnmDRuolo().getIdRuolo() == Long.parseLong(Constants.RUOLO_UTENTE_ISTRUTTORE) && userValid) {
				istruttoreList.add(istruttoreEntityMapper.mapEntityToVO(cnmTUser));
			}
		}

		return istruttoreList;
	}

	@Override
	public UtenteVO getUtenteRuolo(Integer idVerbale, UserDetails userDetails) {
		if (idVerbale == null)
			throw new SecurityException("Request non valida");

		CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findOne(idVerbale);

		UtenteVO utente = new UtenteVO();

		UserDetails user = SecurityUtils.getUser();
		CnmTUser cnmTUser = cnmTUserRepository.findOne(user.getIdUser());

		utente.setIdRuolo(cnmTUser.getCnmDRuolo().getIdRuolo());
		utente.setDescRuolo(cnmTUser.getCnmDRuolo().getDescRuolo());
		utente.setCodiceFiscale(cnmTUser.getCodiceFiscale());
		utente.setNome(cnmTUser.getNome());
		utente.setCognome(cnmTUser.getCognome());
		utente.setId(cnmTUser.getIdUser());

		if (cnmTUser.getCnmDRuolo().getIdRuolo() == Long.parseLong(Constants.RUOLO_UTENTE_ISTRUTTORE) && cnmTUser.getIdUser() == cnmTVerbale.getCnmTUser2().getIdUser())
			utente.setIsIstruttore(1);
		else
			utente.setIsIstruttore(0);

		return utente;
	}

	@Override
	public AzioneVerbaleResponse getAzioniVerbale(Integer id, UserDetails userDetails) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);
		AzioneVerbaleResponse azioneVerbale = new AzioneVerbaleResponse();

		Boolean isAllegatiEnable = Boolean.FALSE;
		AppGrantedAuthority appGrantedAuthority = SecurityUtils.getRuolo(userDetails.getAuthorities());
		Long idStatoVerbale = cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale();
		List<AzioneVO> azioneList = new ArrayList<AzioneVO>();
//		if (idStatoVerbale == Constants.STATO_VERBALE_INCOMPLETO) {
//			azioneList.add(AzioneVerbale.getAzioneVerbaleByIdStato(Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO, cnmTVerbale.getNumVerbale(), cnmDMessaggioRepository));
//		}
		
		// aggiungi allegati enable
		List<TipoAllegatoVO> tipologiaAllegabili = allegatoVerbaleService.getTipologiaAllegatiAllegabiliVerbale(id, null, false);

		if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ISTRUTTORE)) {
			if (idStatoVerbale != Constants.STATO_VERBALE_INCOMPLETO 
					&& idStatoVerbale != Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO 
					&& idStatoVerbale != Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO) {
				CnmRFunzionarioIstruttore cnmRFunzionarioIstruttore = cnmRFunzionarioIstruttoreRepository.findByCnmTVerbaleAndDataAssegnazione(cnmTVerbale);
				if (cnmRFunzionarioIstruttore != null && cnmRFunzionarioIstruttore.getCnmTUser().getIdUser() == userDetails.getIdUser() && tipologiaAllegabili != null
						&& !tipologiaAllegabili.isEmpty())
					isAllegatiEnable = Boolean.TRUE;
			} else {
				if (tipologiaAllegabili != null && !tipologiaAllegabili.isEmpty() && cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser())
					isAllegatiEnable = Boolean.TRUE;
			}
		} else if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ACCERTATORE) || appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_AMMINISTRATIVO)) {
			if (idStatoVerbale == Constants.STATO_VERBALE_INCOMPLETO && cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser())
				isAllegatiEnable = Boolean.TRUE;
		} else
			throw new SecurityException("Ruolo non riconosciuto dal sistema");

		Boolean modificaVerbaleEnable = (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_INCOMPLETO)
				&& cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser()
				? Boolean.TRUE
				: Boolean.FALSE;
		Boolean isEliminaAllegatoEnable = (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_INCOMPLETO)
				&& cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser() ? Boolean.TRUE : Boolean.FALSE;

		// 20210911 PP - jira 174
		Long statoAvanzabile =  getStatoAvanzabileByCnmTVerbale(cnmTVerbale, userDetails);
		
		if(statoAvanzabile != null && (statoAvanzabile == Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO 
				|| statoAvanzabile == Constants.STATO_VERBALE_ACQUISITO
				|| statoAvanzabile == Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI)
				&& idStatoVerbale != Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO
				&& idStatoVerbale != Constants.STATO_VERBALE_ACQUISITO
				&& idStatoVerbale != Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI
				&& idStatoVerbale != Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO){
			azioneList.add(AzioneVerbale.getAzioneVerbaleByIdStato(Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO, cnmTVerbale.getNumVerbale(), cnmDMessaggioRepository));
			
			// 20211124 PP - controllo se ci sono soggetti senza CF, se si restituisco lo stato STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO
			// controllo soggetti associati
			if (idStatoVerbale != Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO) {
				List<SoggettoVO> soggetti = soggettoVerbaleService.getSoggettiByIdVerbale(cnmTVerbale.getIdVerbale(), userDetails, false);
				if (soggetti == null || soggetti.isEmpty()) {}
				else {
					boolean soggettiSenzaCF = false;
					for(SoggettoVO soggetto : soggetti) {
						if(soggetto.getPersonaFisica() && (soggetto.getCodiceFiscale()==null || soggetto.getCodiceFiscale().length()==0)) {
							soggettiSenzaCF = true;
							break;
						}
					}
					if(soggettiSenzaCF) {
			//			idStato = Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO;
						AzioneVO azioneB = AzioneVerbale.getAzioneVerbaleByIdStato(Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO, cnmTVerbale.getNumVerbale(), cnmDMessaggioRepository);
						if(azioneB!=null) {
							azioneList.add(azioneB);
						}
					}
				}
			}
		}
		
		AzioneVO azione = AzioneVerbale.getAzioneVerbaleByIdStato(statoAvanzabile, cnmTVerbale.getNumVerbale(), cnmDMessaggioRepository);
		
		if(azione!=null) {
			azioneList.add(azione);
		}
				
		azioneVerbale.setAzioneList(azioneList);
		azioneVerbale.setModificaVerbaleEnable(modificaVerbaleEnable);
		azioneVerbale.setEliminaAllegatoEnable(isEliminaAllegatoEnable);
		azioneVerbale.setAggiungiAllegatoEnable(isAllegatiEnable);		

		if (idStatoVerbale == Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO ) {
//			azioneVerbale.setAzioneList(new ArrayList<AzioneVO>());
			azioneVerbale.setModificaVerbaleEnable(false);
			azioneVerbale.setEliminaAllegatoEnable(false);
//			azioneVerbale.setAggiungiAllegatoEnable(false);
			
			List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettosWithoutAllegato = allegatoVerbaleSoggettoService.findCnmRVerbaleSoggettosWithoutAllegato(cnmTVerbale, TipoAllegato.RELATA_NOTIFICA);
			if(cnmRVerbaleSoggettosWithoutAllegato != null && cnmRVerbaleSoggettosWithoutAllegato.size()>0) {
				azioneVerbale.setAggiungiAllegatoEnable(true);
			}		
		
		}
		return azioneVerbale;
	}

	@Override
	@Transactional
	public void salvaAzioneVerbale(SalvaAzioneRequest salvaAzione, UserDetails userDetails) {
		Long idAzione = salvaAzione.getIdAzione();
		Integer idVerbale = salvaAzione.getId();
		Integer idFunzionario = salvaAzione.getIdFunzionario();
		if (idAzione == null)
			throw new IllegalArgumentException("id azione = null");

		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(idVerbale);

		if (!isAzionePermessa(idVerbale, userDetails, idAzione))
			throw new SecurityException("Azione non permessa");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		storicizzazioneVerbaleService.storicizzaStatoVerbale(cnmTVerbale, cnmTUser);

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmDStatoVerbale cnmDStatoVerbale;
		boolean assegnaEnable;
		
		boolean protocollaDiretto = false;
		// 20211125 PP - controllo se il master è già protocollato, se si non passo per gli stati intermedi
		List<CnmRAllegatoVerbale> cnmRAllegatoVerbaleList = cnmRAllegatoVerbaleRepository.findByCnmTVerbale(cnmTVerbale);
		CnmRAllegatoVerbale cnmRAllegatoVerbale = Iterables.tryFind(cnmRAllegatoVerbaleList, UtilsTipoAllegato.findAllegatoInCnmRAllegatoVerbaleByTipoAllegato(TipoAllegato.RAPPORTO_TRASMISSIONE))
				.orNull();
		CnmTAllegato cnmTAllegato = cnmRAllegatoVerbale.getCnmTAllegato();
		if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_PROTOCOLLATO || 
				cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_PROTOCOLLATO_IN_ALTRA_STRUTTURA)
		{
			protocollaDiretto = true;
		}
		
		if (AzioneVerbale.ACQUISISCI.getId().equals(idAzione)) {
			// 20210804 PP - imposto lo stato in STATO_VERBALE_IN_ACQUISIZIONE poiche' sara' imostato a STATO_VERBALE_ACQUISITO in seguito dallo scheduler
			if(cnmTVerbale.getNumeroProtocollo() != null && cnmTVerbale.getNumeroProtocollo().length()>0) {
				cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO);
			}else {
				if(protocollaDiretto) {
					cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO);					
				}else {
					cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_IN_ACQUISIZIONE);
				}
			}
			assegnaEnable = AzioneVerbale.ACQUISISCI.isListaIstruttori();
		} else if (AzioneVerbale.ACQUISISCI_CON_PAGAMENTO.getId().equals(idAzione)) {
			// 20210804 PP - imposto lo stato in STATO_VERBALE_IN_ACQUISIZIONE_CON_PAGAMENTO (se il verbale non è stato ancora acquisito CONAM-165 20211005) poiche' sara' imostato a STATO_VERBALE_ACQUISITO_CON_PAGAMENTO in seguito dallo scheduler
			if(cnmTVerbale.getNumeroProtocollo() != null && cnmTVerbale.getNumeroProtocollo().length()>0) {
				cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO);
			}else {
				if(protocollaDiretto) {
					cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO);					
				}else {
					cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_IN_ACQUISIZIONE_CON_PAGAMENTO);
				}
			}
			assegnaEnable = AzioneVerbale.ACQUISISCI_CON_PAGAMENTO.isListaIstruttori();
		} else if (AzioneVerbale.ACQUISISCI_CON_SCRITTI.getId().equals(idAzione)) {
			// 20210804 PP - imposto lo stato in STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI (se il verbale non è stato ancora acquisito CONAM-165 20211005) poiche' sara' imostato a STATO_VERBALE_IN_ACQUISIZIONE_CON_SCRITTI_DIFENSIVI in seguito dallo scheduler
			if(cnmTVerbale.getNumeroProtocollo() != null && cnmTVerbale.getNumeroProtocollo().length()>0) {
				cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI);
			}else{
				if(protocollaDiretto) {
					cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI);					
				}else {
					cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_IN_ACQUISIZIONE_CON_SCRITTI_DIFENSIVI);
				}
			}
			assegnaEnable = AzioneVerbale.ACQUISISCI_CON_SCRITTI.isListaIstruttori();
		} else if (AzioneVerbale.ARCHIVIATO_IN_AUTOTUTELA.getId().equals(idAzione)) {
			// 20210804 PP - imposto lo stato in STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA (se il verbale non è stato ancora acquisito CONAM-165 20211005) poiche' sara' imostato a STATO_VERBALE_IN_ARCHIVIATO_IN_AUTOTUTELA in seguito dallo scheduler
			if(cnmTVerbale.getNumeroProtocollo() != null && cnmTVerbale.getNumeroProtocollo().length()>0) {
				cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA);
			}else{
				if(protocollaDiretto) {
					cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA);					
				}else {
					cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_IN_ARCHIVIATO_IN_AUTOTUTELA);
				}
			}
			assegnaEnable = AzioneVerbale.ARCHIVIATO_IN_AUTOTUTELA.isListaIstruttori();
		}else if (AzioneVerbale.ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO.getId().equals(idAzione)) {
			if(protocollaDiretto) {
				cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO);		
			}else {
				cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_PROTOCOLLAZIONE_PER_MANCANZA_CF);
			}
			assegnaEnable = AzioneVerbale.ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO.isListaIstruttori();
		} else if (AzioneVerbale.IN_ATTESA_VERIFICA_PAGAMENTO.getId().equals(idAzione)) {
			if(protocollaDiretto) {
				cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO);		
			}else {
				cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_PROTOCOLLAZIONE_IN_ATTESA_VERIFICA_PAGAMENTO);
			}
			assegnaEnable = AzioneVerbale.IN_ATTESA_VERIFICA_PAGAMENTO.isListaIstruttori();
		} else
			throw new IllegalArgumentException("azione non gestita");

		// se la lista istruttori è presente controllo che sia valorizzato il
		// funzionario
		if (assegnaEnable && idFunzionario == null)
			throw new IllegalArgumentException("idFunzioanrio =null");

		// 20210908 PP - escludo la protocollazione per ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO e IN_ATTESA_VERIFICA_PAGAMENTO
		// TODO - da verificare
//		if (!AzioneVerbale.ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO.getId().equals(idAzione)
//				&& !AzioneVerbale.IN_ATTESA_VERIFICA_PAGAMENTO.getId().equals(idAzione)
//				) {
			if (cnmTVerbale.getNumeroProtocollo() == null) {
				if (!cnmRAllegatoVerbaleList.isEmpty()) {
					ResponseProtocollaDocumento response = commonAllegatoService.avviaProtocollazione(cnmRAllegatoVerbaleList, cnmTUser);
					cnmTVerbale.setNumeroProtocollo(response.getProtocollo());
					cnmTVerbale.setDataOraProtocollo(now);
				}
			}
//		}

		cnmTVerbale.setCnmDStatoVerbale(cnmDStatoVerbale);
		cnmTVerbale.setDataOraUpdate(now);
		cnmTVerbale.setCnmTUser1(cnmTUser);

		if (assegnaEnable) {
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
		if(idStatoVerbale != Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO) {
			if (!controllaPermessiAzione(idStatoVerbale, idProprietarioVerbale, idUserConnesso, idUserAssegnato))
				return null;
		}
		
		if (idStatoVerbale == Constants.STATO_VERBALE_INCOMPLETO || idStatoVerbale == Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO) {
			
				idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI, idAllegatoList,
						RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI);
				if (idStato == null) {
					idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO, idAllegatoList,
							RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO);
				}
				if (idStato == null) {
					idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO, idAllegatoList, RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO);
				}
				if(idStatoVerbale == Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO && idStato!= null) {
					// controllo se ci sono pagamenti totali per il verbale
					BigDecimal importoPagato = cnmTAllegatoFieldRepository.getImportoPagatoByIdVerbale(cnmTVerbale.getIdVerbale());
					if(importoPagato == null) {
						importoPagato = new BigDecimal(0);
					}
					if(importoPagato.compareTo(cnmTVerbale.getImportoVerbale()) == 0) {
						idStato = Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO;
					}else {
						idStato = Constants.STATO_VERBALE_ACQUISITO;
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
//		if (idStatoVerbale == Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO) {
//				return true;
//		}
		if (idStatoVerbale == Constants.STATO_VERBALE_INCOMPLETO) {
			if (idProprietarioVerbale == idUserConnesso)
				return true;
		}
		if (idStatoVerbale == Constants.STATO_VERBALE_ACQUISITO 
				|| idStatoVerbale == Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO 
				|| idStatoVerbale == Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA) {
			return idUserAssegnato != null && idUserAssegnato == idUserConnesso;
		}

		return false;
	}

	private boolean isAzionePermessa(Integer id, UserDetails userDetails, Long idAzioneRichiesta) {
		AzioneVerbaleResponse az = getAzioniVerbale(id, userDetails);
		for(AzioneVO azione : az.getAzioneList()) {
			if(azione.getId() != null && azione.getId().equals(idAzioneRichiesta))
				return true;
		}
		return false;
	}

	@Override
	public Boolean isTipoAllegatoAllegabile(Integer id, String codiceDocumento, UserDetails userDetails) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);
		Boolean isAllegatiEnable = Boolean.FALSE;
		TipoAllegato tipo = TipoAllegato.getTipoAllegatoByTipoDocumento(codiceDocumento);

		// aggiungi allegati enable
		List<TipoAllegatoVO> tipologiaAllegabili = allegatoVerbaleService.getTipologiaAllegatiAllegabiliVerbale(id, tipo.getTipoDocumento(), false);

		switch (tipo) {
		case RICEVUTA_PAGAMENTO_VERBALE:
			isAllegatiEnable = isAggiugiAllegatoRiconciliazioneVerbale(cnmTVerbale, userDetails, tipologiaAllegabili);
			break;
		case VERBALE_AUDIZIONE:
			isAllegatiEnable = isAggiungiVerbaleDiAudizione(cnmTVerbale);
			break;
		case CONVOCAZIONE_AUDIZIONE:
			isAllegatiEnable = isAggiungiConvocazioneAudizione(cnmTVerbale);
			break;
		case CONTRODEDUZIONE:
			isAllegatiEnable = isAggiugiAllegatoVerbaleControdeduzioni(cnmTVerbale, userDetails, tipologiaAllegabili);
			break;
		case COMPARSA:
			isAllegatiEnable = isAggiugiAllegatoVerbaleControdeduzioni(cnmTVerbale, userDetails, tipologiaAllegabili);
			break;
		default:
			throw new RuntimeException("Tipo Allegato non dichiarato");
		}

		return isAllegatiEnable;

	}

	@Override
	public Boolean isEnableCaricaVerbaleAudizione(Integer id, UserDetails userDetails) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		if (cnmRVerbaleSoggettoList == null || cnmRVerbaleSoggettoList.isEmpty())
			return false;

		for (CnmRVerbaleSoggetto cnmRVerbaleSoggetto : cnmRVerbaleSoggettoList) {
			List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogList = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggetto(cnmRVerbaleSoggetto);
			if (!cnmRAllegatoVerbSogList.isEmpty()) {
				Collection<CnmRAllegatoVerbSog> list = Collections2.filter(cnmRAllegatoVerbSogList, new Predicate<CnmRAllegatoVerbSog>() {
					public boolean apply(CnmRAllegatoVerbSog cnmRAllegatoVerbSog) {
						return cnmRAllegatoVerbSog.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.VERBALE_AUDIZIONE.getId();
					}
				});

				List<CnmRAllegatoVerbSog> lista = new ArrayList<CnmRAllegatoVerbSog>(list);

				if (list != null && list.size() > 0) {
					List<CnmTAllegato> temp = cnmTAllegatoRepository.findByCnmRAllegatoVerbSogsIn(lista);

					if (temp != null && temp.size() > 0) {
						for (CnmTAllegato a : temp) {
							if (StringUtils.isNotBlank(a.getIdIndex()) && StringUtils.isBlank(a.getNumeroProtocollo())) {
								return true;
							}
						}
					}

				}

			}
		}

		return false;

	}

	private Boolean isAggiungiVerbaleDiAudizione(CnmTVerbale cnmTVerbale) {
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		if (cnmRVerbaleSoggettoList.isEmpty())
			return Boolean.FALSE;

		for (CnmRVerbaleSoggetto vs : cnmRVerbaleSoggettoList) {
			List<CnmRAllegatoVerbSog> allegato = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggetto(vs);
			if (allegato == null || allegato.isEmpty())
				return true;
			boolean flag = false;
			for (CnmRAllegatoVerbSog a : allegato) {
				if (a.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.VERBALE_AUDIZIONE.getId())
					flag = true;
			}
			if (!flag)
				return Boolean.TRUE;

		}
		return Boolean.FALSE;

		/*
		 * List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogList =
		 * cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggettoIn(
		 * cnmRVerbaleSoggettoList); if (cnmRAllegatoVerbSogList.isEmpty())
		 * return true;
		 * 
		 * Collection<CnmRAllegatoVerbSog> collectionsFiltered =
		 * Collections2.filter(cnmRAllegatoVerbSogList, new
		 * Predicate<CnmRAllegatoVerbSog>() { public boolean
		 * apply(CnmRAllegatoVerbSog cnmRAllegatoVerbSog) { return
		 * cnmRAllegatoVerbSog.getCnmTAllegato().getCnmDTipoAllegato().
		 * getIdTipoAllegato() == TipoAllegato.VERBALE_AUDIZIONE.getId(); } });
		 * 
		 * return !(cnmRAllegatoVerbSogList.size() ==
		 * collectionsFiltered.size());
		 */
	}

	private Boolean isAggiungiConvocazioneAudizione(CnmTVerbale cnmTVerbale) {
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		if (cnmRVerbaleSoggettoList.isEmpty())
			return false;

		for (CnmRVerbaleSoggetto vs : cnmRVerbaleSoggettoList) {
			List<CnmRAllegatoVerbSog> allegato = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggetto(vs);
			if (allegato == null || allegato.isEmpty())
				return true;
			boolean flag = false;
			for (CnmRAllegatoVerbSog a : allegato) {
				if (a.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId())
					flag = true;
			}
			if (!flag)
				return true;
		}
		return false;
	}

	private Boolean isAggiugiAllegatoRiconciliazioneVerbale(CnmTVerbale cnmTVerbale, UserDetails userDetails, List<TipoAllegatoVO> tipologiaAllegabili) {
		Boolean isAllegatiEnable = Boolean.FALSE;
		AppGrantedAuthority appGrantedAuthority = SecurityUtils.getRuolo(userDetails.getAuthorities());

		if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ISTRUTTORE)) {
			CnmRFunzionarioIstruttore cnmRFunzionarioIstruttore = cnmRFunzionarioIstruttoreRepository.findByCnmTVerbaleAndDataAssegnazione(cnmTVerbale);
			if (cnmRFunzionarioIstruttore != null) {
				if (cnmRFunzionarioIstruttore.getCnmTUser().getIdUser() == userDetails.getIdUser() && tipologiaAllegabili != null && !tipologiaAllegabili.isEmpty())
					isAllegatiEnable = Boolean.TRUE;
			} else if (tipologiaAllegabili != null && !tipologiaAllegabili.isEmpty() && cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser())
				isAllegatiEnable = Boolean.TRUE;
		} else if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_AMMINISTRATIVO)) {
			if (tipologiaAllegabili != null && !tipologiaAllegabili.isEmpty() && cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser())
				isAllegatiEnable = Boolean.TRUE;
		} else
			throw new SecurityException("Ruolo non abilitato ad accedere a questo servizio");

		return isAllegatiEnable;
	}

	private Boolean isAggiugiAllegatoVerbaleControdeduzioni(CnmTVerbale cnmTVerbale, UserDetails userDetails, List<TipoAllegatoVO> tipologiaAllegabili) {
		Boolean isAllegatiEnable = Boolean.FALSE;

		List<CnmRVerbaleSoggetto> verbaleSoggetto = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		if (verbaleSoggetto != null && verbaleSoggetto.size() > 0) {
			List<CnmROrdinanzaVerbSog> ordinanzaVerbSog = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggettoIn(verbaleSoggetto);
			if (ordinanzaVerbSog != null && ordinanzaVerbSog.size() > 0) {
				for (CnmROrdinanzaVerbSog tmp : ordinanzaVerbSog) {
					Long stato = new Long(tmp.getCnmDStatoOrdVerbSog().getIdStatoOrdVerbSog());
					if (stato.compareTo(new Long(7)) != 0 || stato.compareTo(new Long(8)) != 0 || stato.compareTo(new Long(9)) != 0) {
						return Boolean.TRUE;
					}
				}
			} else
				isAllegatiEnable = Boolean.TRUE;
		} else
			isAllegatiEnable = Boolean.TRUE;

		/*
		 * AppGrantedAuthority appGrantedAuthority =
		 * SecurityUtils.getRuolo(userDetails.getAuthorities());
		 * 
		 * if (appGrantedAuthority.getCodice().equals(Constants.
		 * RUOLO_UTENTE_ISTRUTTORE)) { CnmRFunzionarioIstruttore
		 * cnmRFunzionarioIstruttore = cnmRFunzionarioIstruttoreRepository.
		 * findByCnmTVerbaleAndDataAssegnazione(cnmTVerbale); if
		 * (cnmRFunzionarioIstruttore != null) { if
		 * (cnmRFunzionarioIstruttore.getCnmTUser().getIdUser() ==
		 * userDetails.getIdUser() && tipologiaAllegabili != null &&
		 * !tipologiaAllegabili.isEmpty()) isAllegatiEnable = Boolean.TRUE; }
		 * else if (tipologiaAllegabili != null &&
		 * !tipologiaAllegabili.isEmpty() &&
		 * cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser())
		 * isAllegatiEnable = Boolean.TRUE; }
		 */

		return isAllegatiEnable;
	}

	


	
}
