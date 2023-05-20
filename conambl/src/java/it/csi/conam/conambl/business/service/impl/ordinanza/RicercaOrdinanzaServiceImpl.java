/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.ordinanza;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import it.csi.conam.conambl.business.service.RiferimentiNormativiService;
import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.ordinanza.AllegatoOrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.RicercaOrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.UtilsOrdinanza;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.OrdinanzaEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.ROrdinanzaVerbSogEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoOrdinanzaMapper;
import it.csi.conam.conambl.integration.mapper.entity.VerbaleEntityMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.integration.specification.CnmTOrdinanzaSpecification;
import it.csi.conam.conambl.integration.specification.CnmTSoggettoOrdinanzaVerbaleSpecification;
import it.csi.conam.conambl.request.SoggettoRequest;
import it.csi.conam.conambl.request.ordinanza.RicercaOrdinanzaRequest;
import it.csi.conam.conambl.request.ordinanza.RicercaSoggettiOrdinanzaRequest;
import it.csi.conam.conambl.request.verbale.DatiVerbaleRequest;
import it.csi.conam.conambl.response.StatiOrdinanzaResponse;
import it.csi.conam.conambl.security.AppGrantedAuthority;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.VerbaleSearchParam;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import it.csi.conam.conambl.vo.ordinanza.MinOrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.StatoOrdinanzaVO;
import it.csi.conam.conambl.vo.pianorateizzazione.StatoPianoVO;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class RicercaOrdinanzaServiceImpl implements RicercaOrdinanzaService {

	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CommonSoggettoService commonSoggettoService;
	@Autowired
	private OrdinanzaEntityMapper ordinanzaEntityMapper;
	@Autowired
	private RiferimentiNormativiService riferimentiNormativiService;
	@Autowired
	private CnmTNotificaRepository cnmTNotificaRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private ROrdinanzaVerbSogEntityMapper rOrdinanzaVerbSogEntityMapper;
	@Autowired
	private CnmDStatoOrdinanzaRepository cnmDStatoOrdinanzaRepository;
	@Autowired
	private CnmTAllegatoFieldRepository cnmTAllegatoFieldRepository;
	@Autowired
	private StatoOrdinanzaMapper statoOrdinanzaMapper;
	@Autowired
	private UtilsDate utilsDate;
	
	@Autowired
	private AllegatoOrdinanzaService allegatoOrdinanzaService;
	@Autowired
	private UtilsOrdinanza utilsOrdinanza;
	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;
	

	@Autowired
	private VerbaleEntityMapper verbaleEntityMapper;
	
		
	private static Long COUNT = new Long(100);

	@Override
	public List<MinOrdinanzaVO> ricercaOrdinanza(RicercaOrdinanzaRequest request, UserDetails userDetails) {

		if (request == null)
			throw new IllegalArgumentException("request is null");

		
		if (request.getDatiVerbale() == null) request.setDatiVerbale(new DatiVerbaleRequest());
		//TODO LUCIO
		/*LUCIO - 2021/04/19 - Gestione pagamenti definiti in autonomia (Scenario 8)*/
		if (request.getPerAcconto()) {
			List<StatoOrdinanzaVO> listaStatiOrdinanza = new ArrayList<StatoOrdinanzaVO>();
			for (Long id: Constants.LISTA_STATI_ORDINANZA_NON_PAGATI) {
				CnmDStatoOrdinanza statoOrdinanza = cnmDStatoOrdinanzaRepository.findOne(id);
				if (statoOrdinanza != null) {
					StatoOrdinanzaVO statoOrdinanzaVO = statoOrdinanzaMapper.mapEntityToVO(statoOrdinanza);
					listaStatiOrdinanza.add(statoOrdinanzaVO);
				}
			}
			request.setStatoOrdinanza(listaStatiOrdinanza);
		}
		/*LUCIO - 2021/04/19 - FINE Gestione pagamenti definiti in autonomia (Scenario 8)*/
		
		request.getDatiVerbale().setNumeroVerbale(request.getNumeroVerbale());
		request.getDatiVerbale().setPregresso(request.getPregresso());
		
		
		String numeroDeterminazione = request.getNumeroDeterminazione();
		List<SoggettoRequest> soggetti = request.getSoggettoVerbale();
		List<MinOrdinanzaVO> ordinanzeList = new ArrayList<>();
		Boolean ordinanzaProtocollata = request.getOrdinanzaProtocollata() != null ? request.getOrdinanzaProtocollata().booleanValue() : Boolean.FALSE;

		Date dataOrdinanzaDa = request.getDataOrdinanzaDa() != null ? utilsDate.asDate(request.getDataOrdinanzaDa()) : null;
		Date dataOrdinanzaA = request.getDataOrdinanzaDa() != null ? utilsDate.asDate(request.getDataOrdinanzaA()) : null;

		Function<StatoOrdinanzaVO, Long> statoOrdinanzaToLong = new Function<StatoOrdinanzaVO, Long>() {
			public Long apply(StatoOrdinanzaVO i) {
				return i.getId();
			}
		};
		List<Long> statiOrdinanza = request.getStatoOrdinanza() != null ? Lists.transform(request.getStatoOrdinanza(), statoOrdinanzaToLong) : null;

		Long count = new Long(0);
		if (dataOrdinanzaDa != null && dataOrdinanzaA != null && (request.getSoggettoVerbale() != null || request.getSoggettoVerbale().isEmpty())) {
			count = cnmTOrdinanzaRepository.countByDataOrdinanzaAndStatiOrdinanzaIn(dataOrdinanzaDa, dataOrdinanzaA, statiOrdinanza);

			if (count > COUNT) {
				List<MinOrdinanzaVO> esito = new ArrayList<MinOrdinanzaVO>();
				MinOrdinanzaVO vo = new MinOrdinanzaVO();
				vo.setSuperatoIlMassimo(true);
				esito.add(vo);
				return esito;
			}
		}

		List<CnmDLettera> lettera = null;

		AppGrantedAuthority appGrantedAuthority = SecurityUtils.getRuolo(userDetails.getAuthorities());

		// Avra una sola legge
		if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_AMMINISTRATIVO) || appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ISTRUTTORE)) {
			List<EnteVO> enteLegge = userDetails.getEntiLegge();
			if (enteLegge != null && !enteLegge.isEmpty() && enteLegge.size() == 1) {
				lettera = riferimentiNormativiService.getLettereByEnte(enteLegge.get(0).getId(), false);
			} else
				throw new SecurityException("Errore l'utente ammistritativo o funzionario deve avere un solo ente legge, quello di sua competenza");
		}

		List<CnmTSoggetto> trasgressore = new ArrayList<>();
		List<CnmTSoggetto> obbligatoInSolido = new ArrayList<>();
		if (soggetti != null && !soggetti.isEmpty()) {
			for (SoggettoRequest s : soggetti) {
				CnmTSoggetto cnmTSoggetto = null;
				String tipo = s.getTipoSoggetto();
				if ((cnmTSoggetto = commonSoggettoService.getSoggettoFromDb(new MinSoggettoVO(s), true)) == null)
					return ordinanzeList;
				if (tipo != null && tipo.equals(Constants.TYPE_SOGGETTO_RICERCA_TRASGRESSORE))
					trasgressore.add(cnmTSoggetto);
				if (tipo != null && tipo.equals(Constants.TYPE_SOGGETTO_RICERCA_OBBLIGATO_IN_SOLIDO))
					obbligatoInSolido.add(cnmTSoggetto);
			}

		}

		VerbaleSearchParam parametriVerbale = verbaleEntityMapper.getVerbaleParamFromRequest(request.getDatiVerbale());
		parametriVerbale.setLettera(lettera);
		
		List<CnmTOrdinanza> ordinanza =
			cnmTOrdinanzaRepository.findAll(
				CnmTOrdinanzaSpecification.findBy(
					trasgressore,
					obbligatoInSolido,
					numeroDeterminazione,
					ordinanzaProtocollata,
					dataOrdinanzaDa,
					dataOrdinanzaA,
					statiOrdinanza,
					request.getTipoRicerca(),
					request.getAnnullamento(),
					request.getPerAcconto(),
					parametriVerbale
				)
			);
		return ordinanzaEntityMapper.mapListEntityToListVO(ordinanza);
	}

	@Override
	public List<SoggettoVO> ricercaSoggetti(RicercaSoggettiOrdinanzaRequest request, boolean pregressi) {
		return this.ricercaSoggetti(
			request,
			null,
			null,
			null,
			null,
			pregressi,
			null,
			null
		);
	}

	@Override
	public List<SoggettoVO> ricercaSoggetti(
		RicercaSoggettiOrdinanzaRequest request,
		StatoOrdinanzaVO statoOrdinanza,
		LocalDate dataNotificaDa,
		LocalDate dataNotificaA,
		String tipoRicerca,
		boolean pregresso,
		String numeroProtocolloPiano,
		List<StatoPianoVO> statoPiano
	) {
		if (request == null)
			throw new IllegalArgumentException("request is null");


		if (request.getDatiVerbale() == null) request.setDatiVerbale(new DatiVerbaleRequest());
		request.getDatiVerbale().setPregresso(pregresso);
		VerbaleSearchParam parametriVerbale = verbaleEntityMapper.getVerbaleParamFromRequest(request.getDatiVerbale());
		
		if (tipoRicerca != null && tipoRicerca.equals("CREA_PIANO")) {
			// per ora non passa gli stati piano
			return findSoggettoBy(
				request,
				statoOrdinanza,
				dataNotificaDa,
				dataNotificaA,
				tipoRicerca,
				pregresso,
				parametriVerbale,
				numeroProtocolloPiano
			);
		}

		Long count = new Long(0);

		tipoRicerca = tipoRicerca == null ? request.getTipoRicerca() : tipoRicerca;

		// request.getOrdinanzeSollecitate() == true => GESTIONE PAGAMENTI ->
		// Ricerca Sollecito di Pagamento

		if (request.getDataNotificaDa() != null && request.getDataNotificaA() != null) {
			if (request.getOrdinanzeSollecitate() != null && request.getOrdinanzeSollecitate()) {
				count = cnmTNotificaRepository.countByDataNotificaSollecito(utilsDate.asDate(request.getDataNotificaDa()), utilsDate.asDate(request.getDataNotificaA())).longValue();
			} else {
				count = cnmTNotificaRepository.countByDataNotifica(utilsDate.asDate(request.getDataNotificaDa()), utilsDate.asDate(request.getDataNotificaA())).longValue();
			}

			dataNotificaDa = request.getDataNotificaDa();
			dataNotificaA = request.getDataNotificaA();
		}

		if (request.getDataSentenzaDa() != null && request.getDataSentenzaA() != null) {
			if (request.getOrdinanzeSollecitate() != null && request.getOrdinanzeSollecitate()) {
				if (request.getStatoSentenza() == null) {
					count = cnmTAllegatoFieldRepository.countByDataSentenzaSollecito(utilsDate.asDate(request.getDataSentenzaDa()), utilsDate.asDate(request.getDataSentenzaA())).longValue();
				} else {
					count = cnmTAllegatoFieldRepository.countByDataSentenzaAndIdStatoSentenzaSollecito(utilsDate.asDate(request.getDataSentenzaDa()), utilsDate.asDate(request.getDataSentenzaA()),
							request.getStatoSentenza().getId()).longValue();
				}

			} else {
				if (request.getStatoSentenza() == null) {
					count = cnmTAllegatoFieldRepository.countByDataSentenza(utilsDate.asDate(request.getDataSentenzaDa()), utilsDate.asDate(request.getDataSentenzaA())).longValue();
				} else {
					count = cnmTAllegatoFieldRepository
							.countByDataSentenzaAndIdStatoSentenza(utilsDate.asDate(request.getDataSentenzaDa()), utilsDate.asDate(request.getDataSentenzaA()), request.getStatoSentenza().getId())
							.longValue();
				}
			}
		}

		if (count > COUNT) {
			List<SoggettoVO> esito = new ArrayList<SoggettoVO>();
			SoggettoVO vo = new SoggettoVO();
			vo.setSuperatoIlMassimo(true);
			esito.add(vo);
			return esito;
		}

		// per ora non passa gli stati piano
		return findSoggettoBy(
			request,
			statoOrdinanza,
			dataNotificaDa,
			dataNotificaA,
			tipoRicerca,
			pregresso,
			parametriVerbale,
			numeroProtocolloPiano
		);

	}

	private List<SoggettoVO> findSoggettoBy(
		RicercaSoggettiOrdinanzaRequest request,
		StatoOrdinanzaVO statoOrdinanza,
		LocalDate dataNotificaDa,
		LocalDate dataNotificaA,
		String tipoRicerca,
		boolean pregresso,
		VerbaleSearchParam parametriVerbale,
		String numeroProtocolloPiano
	) {
		if(pregresso) tipoRicerca = null;

		if (request.getDatiVerbale() == null) request.setDatiVerbale(new DatiVerbaleRequest());
		request.getDatiVerbale().setPregresso(pregresso);

		List<CnmROrdinanzaVerbSog> ordVerbSog = cnmROrdinanzaVerbSogRepository.findAll(
			CnmTSoggettoOrdinanzaVerbaleSpecification.findSoggettoBy(
				request.getNumeroDeterminazione(),
				request.getNumeroProtocolloSentenza(),
				TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getId(),
				numeroProtocolloPiano,
				request.getOrdinanzaProtocollata() != null ? request.getOrdinanzaProtocollata() : Boolean.FALSE,
				request.getOrdinanzeSollecitate(),
				request.getStatoSentenza() != null ? request.getStatoSentenza().getId() : null,
				utilsDate.asDate(dataNotificaDa),
				utilsDate.asDate(dataNotificaA),
				statoOrdinanza != null ? statoOrdinanza.getId() : null,
				utilsDate.asDate(request.getDataSentenzaDa()),
				utilsDate.asDate(request.getDataSentenzaA()),
				tipoRicerca,
				pregresso,
				parametriVerbale,
				utilsDate.asDate(request.getDataCreazioneDa()),
				utilsDate.asDate(request.getDataCreazioneA())
				
			)
		);
	
		if (ordVerbSog == null || ordVerbSog.isEmpty()) return new ArrayList<>();
		return rOrdinanzaVerbSogEntityMapper.mapListEntityToListVO(ordVerbSog);
	}

	@Override
	public List<StatoOrdinanzaVO> getStatiOrdinanza() {
		return statoOrdinanzaMapper.mapListEntityToListVO((List<CnmDStatoOrdinanza>) cnmDStatoOrdinanzaRepository.findAll());
	}

	@Override
	@Transactional
	public StatiOrdinanzaResponse getStatiOrdinanzaPregressi(Integer idOrdinanza) {
		
		CnmTOrdinanza cnmTOrdinanza = utilsOrdinanza.validateAndGetCnmTOrdinanza(idOrdinanza);
		if(!utilsOrdinanza.gestisciOrdinanzaComePregressa(idOrdinanza)) {
			throw new BusinessException(ErrorCode.STATO_VERBALE_INCOMPATIBILE);
		}
		StatiOrdinanzaResponse response = new StatiOrdinanzaResponse();
		CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.CONFIRM_SALVA_STATO_ORDINANZA_PREGRESSA);
		if(cnmDMessaggio!=null) {
			MessageVO messaggio = new MessageVO(String.format(cnmDMessaggio.getDescMessaggio(), cnmTOrdinanza.getNumDeterminazione(), cnmTOrdinanza.getCnmDStatoOrdinanza().getDescStatoOrdinanza(), Constants.PLACEHOLDER_MSG_NUOVO_STATO), cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
			response.setMessaggio(messaggio);
		}else {
			throw new SecurityException("Messaggio non trovato");
		}
		
		long idStatoOrdinanza = cnmTOrdinanza.getCnmDStatoOrdinanza().getIdStatoOrdinanza();
				
		Map<Long, CnmDStatoOrdinanza> statiMap=new HashMap<Long, CnmDStatoOrdinanza>();
		/*
		//cerco se esiste una notifica
		if(cnmTOrdinanza.getCnmTNotificas()!=null && !cnmTOrdinanza.getCnmTNotificas().isEmpty()) {
			Iterator<CnmTNotifica> iterNotifica = cnmTOrdinanza.getCnmTNotificas().iterator();
			while(iterNotifica.hasNext()) {
				CnmTNotifica notificaItem = iterNotifica.next();
				if(notificaItem.getCnmDModalitaNotifica()==null) {
					if(!statiMap.containsKey(Constants.ID_STATO_ORDINANZA_NON_NOTIFICATA)) 
						statiMap.put(Constants.ID_STATO_ORDINANZA_NON_NOTIFICATA, cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_NON_NOTIFICATA));
//							stati.add(cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_NON_NOTIFICATA));
				}else if (Long.valueOf(notificaItem.getCnmDModalitaNotifica().getIdModalitaNotifica()).equals(Constants.ID_COMPIUTA_GIACENZA) || Long.valueOf(notificaItem.getCnmDModalitaNotifica().getIdModalitaNotifica()).equals(Constants.ID_CONSEGNA_A_MANI_PROPRIE)){
					if(!statiMap.containsKey(Constants.ID_STATO_ORDINANZA_NOTIFICATA)) 
						statiMap.put(Constants.ID_STATO_ORDINANZA_NOTIFICATA, cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_NOTIFICATA));
//							stati.add(cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_NOTIFICATA));
				} else {
					if(!statiMap.containsKey(Constants.ID_STATO_ORDINANZA_NON_NOTIFICATA)) 
						statiMap.put(Constants.ID_STATO_ORDINANZA_NON_NOTIFICATA, cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_NON_NOTIFICATA));
//							stati.add(cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_NON_NOTIFICATA));
				}
			}
		}
		*/
		Map<Long, List<AllegatoVO>> mappaAllegatiOrdinanza = allegatoOrdinanzaService.getMapTipoAllegatiByIdOrdinanza(idOrdinanza);
		
		if(idStatoOrdinanza == Constants.ID_STATO_ORDINANZA_NOTIFICATA) {

			if(mappaAllegatiOrdinanza.containsKey(TipoAllegato.OPPOSIZIONE_GIURISDIZIONALE.getId())) {
				if(!statiMap.containsKey(Constants.ID_STATO_ORDINANZA_RICORSO_IN_ATTO)) 
					statiMap.put(Constants.ID_STATO_ORDINANZA_RICORSO_IN_ATTO, cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_RICORSO_IN_ATTO));
			}
			statiMap.put(Constants.ID_STATO_ORDINANZA_NON_NOTIFICATA, cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_NON_NOTIFICATA));
			statiMap.put(Constants.ID_STATO_RISCOSSO_SORIS, cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_RISCOSSO_SORIS));
			statiMap.put(Constants.ID_STATO_ORDINANZA_PAGATA, cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_PAGATA));
		} else if(idStatoOrdinanza == Constants.ID_STATO_ORDINANZA_NON_NOTIFICATA) {

			if(mappaAllegatiOrdinanza.containsKey(TipoAllegato.OPPOSIZIONE_GIURISDIZIONALE.getId())) {
				if(!statiMap.containsKey(Constants.ID_STATO_ORDINANZA_RICORSO_IN_ATTO)) 
					statiMap.put(Constants.ID_STATO_ORDINANZA_RICORSO_IN_ATTO, cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_RICORSO_IN_ATTO));
			}
			statiMap.put(Constants.ID_STATO_RISCOSSO_SORIS, cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_RISCOSSO_SORIS));
			statiMap.put(Constants.ID_STATO_ORDINANZA_PAGATA, cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_PAGATA));
		} else if(idStatoOrdinanza == Constants.ID_STATO_ORDINANZA_RICORSO_IN_ATTO) {

			statiMap.put(Constants.ID_STATO_RISCOSSO_SORIS, cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_RISCOSSO_SORIS));
			statiMap.put(Constants.ID_STATO_ORDINANZA_PAGATA, cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_PAGATA));
		}
		
		statiMap.remove(idStatoOrdinanza);
		
		List<CnmDStatoOrdinanza> stati = statiMap.isEmpty()?new ArrayList<>():new ArrayList<>(statiMap.values());
			
		response.setStati(statoOrdinanzaMapper.mapListEntityToListVO((List<CnmDStatoOrdinanza>) stati));
		return response;
	}

}
