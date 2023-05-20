/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import it.csi.conam.conambl.business.facade.StadocServiceFacade;
import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.ordinanza.AllegatoOrdinanzaService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleService;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleSoggettoService;
import it.csi.conam.conambl.business.service.verbale.UtilsVerbale;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.TipoProtocolloAllegato;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.AllegatoEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.TipoAllegatoEntityMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.integration.specification.CnmDTipoAllegatoSpecification;
import it.csi.conam.conambl.request.SalvaAllegatiMultipliRequest;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.request.verbale.SalvaAllegatoVerbaleRequest;
import it.csi.conam.conambl.scheduled.AllegatoScheduledService;
import it.csi.conam.conambl.security.AppGrantedAuthority;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.UploadUtils;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.verbale.allegato.*;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class AllegatoVerbaleServiceImpl implements AllegatoVerbaleService {

	@Autowired
	private CnmRAllegatoVerbaleRepository cnmRAllegatoVerbaleRepository;
	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;
	@Autowired
	private AllegatoEntityMapper allegatoEntityMapper;
	@Autowired
	private CnmDTipoAllegatoRepository cnmDTipoAllegatoRepository;
	@Autowired
	private TipoAllegatoEntityMapper tipoAllegatoEntityMapper;
	@Autowired
	private CommonAllegatoService commonAllegatoService;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmRAllegatoVerbSogRepository cnmRAllegatoVerbSogRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;
	@Autowired
	private CnmRFunzionarioIstruttoreRepository cnmRFunzionarioIstruttoreRepository;
	@Autowired
	private AllegatoOrdinanzaService allegatoOrdinanzaService;
	@Autowired
	private UtilsVerbale utilsVerbale;
	@Autowired
	private UtilsDoqui utilsDoqui;
	/*@Autowired
	private StadocServiceFacade stadocServiceFacade;*/
	@Autowired
	private AllegatoScheduledService allegatoScheduledService;
	@Autowired
	private CnmDStatoAllegatoRepository cnmDStatoAllegatoRepository;
	@Autowired
	private AllegatoVerbaleSoggettoService allegatoVerbaleSoggettoService;
	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;
	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;
	/*@Autowired
	private CnmRAllegatoSollecitoRepository cnmRAllegatoSollecitoRepository;*/
	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;
	/*@Autowired
	private CnmTRataRepository cnmTRataRepository;*/
	@Autowired
	private CnmRAllegatoPianoRateRepository cnmAllegatoPianoRateRepository;
	@Autowired
	private CnmTAllegatoFieldRepository cnmTAllegatoFieldRepository;
	
	@Override
	public RiepilogoAllegatoVO getAllegatiByIdVerbale(Integer id, UserDetails userDetails, boolean includiControlloUtenteProprietarioIstruttoreAssegnato, boolean verbaleAudizione) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);
		RiepilogoAllegatoVO riepilogo = new RiepilogoAllegatoVO();
		List<AllegatoVO> verbale = new ArrayList<>();
		List<AllegatoVO> istruttoria = new ArrayList<>();
		List<AllegatoVO> giurisdizionale = new ArrayList<>();
		List<AllegatoVO> rateizzazione = new ArrayList<>();
		riepilogo.setVerbale(verbale);
		riepilogo.setIstruttoria(istruttoria);
		riepilogo.setGiurisdizionale(giurisdizionale);
		riepilogo.setRateizzazione(rateizzazione);

		AppGrantedAuthority appGrantedAuthority = SecurityUtils.getRuolo(userDetails.getAuthorities());
		// aggiungi allegati enable
		if (includiControlloUtenteProprietarioIstruttoreAssegnato) {
			if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ISTRUTTORE)) {
				if (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() != Constants.STATO_VERBALE_INCOMPLETO) {
					CnmRFunzionarioIstruttore cnmRFunzionarioIstruttore = cnmRFunzionarioIstruttoreRepository.findByCnmTVerbaleAndDataAssegnazione(cnmTVerbale);
					if (cnmRFunzionarioIstruttore != null && cnmRFunzionarioIstruttore.getCnmTUser().getIdUser() != userDetails.getIdUser())
						throw new BusinessException(ErrorCode.UTENTE_NOACCESS_VERBALE);
				} else if (cnmTVerbale.getCnmTUser2().getIdUser() != userDetails.getIdUser())
					throw new BusinessException(ErrorCode.UTENTE_NOACCESS_VERBALE);
			} else if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ACCERTATORE) || appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_AMMINISTRATIVO)) {
				if (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() != Constants.STATO_VERBALE_INCOMPLETO)
					throw new BusinessException(ErrorCode.UTENTE_NOACCESS_VERBALE);
				else if (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_INCOMPLETO && cnmTVerbale.getCnmTUser2().getIdUser() != userDetails.getIdUser())
					throw new BusinessException(ErrorCode.UTENTE_NOACCESS_VERBALE);
			} else
				throw new SecurityException("Ruolo non riconosciuto dal sistema");
		}

		if (verbaleAudizione) {
			List<AllegatoVO> cnmTAllegatoAudizione = findCnmTAllegatoByIdVerbale(id);
			if (cnmTAllegatoAudizione != null && !cnmTAllegatoAudizione.isEmpty()) {
				riepilogo.getVerbale().addAll(cnmTAllegatoAudizione);
			}

		} else {
			//int cont = 0;
			List<CnmRAllegatoVerbale> cnmRAllegatoVerbaleList = cnmRAllegatoVerbaleRepository.findByCnmTVerbale(cnmTVerbale);
			if (cnmRAllegatoVerbaleList != null && !cnmRAllegatoVerbaleList.isEmpty()) {
				for (CnmRAllegatoVerbale cnmRAllegatoVerbale : cnmRAllegatoVerbaleList) {
					CnmTAllegato cnmTAllegato = cnmRAllegatoVerbale.getCnmTAllegato();
					AllegatoVO al = allegatoEntityMapper.mapEntityToVO(cnmTAllegato);
					if (al.getNumProtocollo() == null && al.getTipo().getId() != TipoAllegato.COMPARSA.getId() && al.getTipo().getId() != TipoAllegato.COMPARSA_ALLEGATO.getId()
							&& al.getTipo().getId() != TipoAllegato.RICEVUTA_PAGAMENTO_VERBALE.getId()
							&& al.getTipo().getId() != TipoAllegato.RELATA_NOTIFICA.getId())
						al.setNumProtocollo(cnmTVerbale.getNumeroProtocollo());
					splitAllegato(riepilogo, cnmTAllegato.getCnmDTipoAllegato().getCnmDCategoriaAllegato().getIdCategoriaAllegato(), al);
				}
			}
		}

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		if (cnmRVerbaleSoggettoList != null && !cnmRVerbaleSoggettoList.isEmpty()) {
			List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettoList);
			if (cnmROrdinanzaVerbSogList != null && !cnmROrdinanzaVerbSogList.isEmpty()) {
				Set<Integer> cnmTOrdinanzaSet = FluentIterable.from(cnmROrdinanzaVerbSogList).transform(new Function<CnmROrdinanzaVerbSog, Integer>() {
					@Override
					public Integer apply(CnmROrdinanzaVerbSog input) {
						return input.getCnmTOrdinanza().getIdOrdinanza();
					}
				}).toSet();
				Map<Long, List<AllegatoVO>> map = allegatoOrdinanzaService.getMapCategoriaAllegatiByIdOrdinanza(new ArrayList<>(cnmTOrdinanzaSet));
				for (Map.Entry<Long, List<AllegatoVO>> entry : map.entrySet()) {
					List<AllegatoVO> allegatiOrdinanza = entry.getValue();
					for (AllegatoVO al : allegatiOrdinanza)
						splitAllegato(riepilogo, entry.getKey(), al);
				}
			}
			// ALLEGATI VERBALE SOGGETTO
			List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogList = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettoList);
			if (cnmRAllegatoVerbSogList != null && !cnmRAllegatoVerbSogList.isEmpty()) {
				for (CnmRAllegatoVerbSog cnmRAllegatoVerbSog : cnmRAllegatoVerbSogList) {
					CnmTAllegato cnmTAllegato = cnmRAllegatoVerbSog.getCnmTAllegato();
					AllegatoVO al = allegatoEntityMapper.mapEntityToVO(cnmTAllegato);
					if (al.getNumProtocollo() == null && al.getTipo().getId() != TipoAllegato.VERBALE_AUDIZIONE.getId()
							&& al.getTipo().getId() != TipoAllegato.RELATA_NOTIFICA.getId())
						al.setNumProtocollo(cnmTVerbale.getNumeroProtocollo());
					splitAllegato(riepilogo, cnmTAllegato.getCnmDTipoAllegato().getCnmDCategoriaAllegato().getIdCategoriaAllegato(), al);
				}
			}
		}
		
		
		// 20210402_LC legge dalla cnmRAllegatoPianoRate
		Map<Integer, CnmTPianoRate> mapPiani = new HashMap<Integer, CnmTPianoRate>();
		if (cnmRVerbaleSoggettoList != null && !cnmRVerbaleSoggettoList.isEmpty()) {
			List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettoList);
			if (cnmROrdinanzaVerbSogList != null && !cnmROrdinanzaVerbSogList.isEmpty()) {
				List<CnmRSoggRata> cnmRSoggRataList = cnmRSoggRataRepository.findByCnmROrdinanzaVerbSogIn(cnmROrdinanzaVerbSogList);
				if (cnmRSoggRataList != null && !cnmRSoggRataList.isEmpty()) {
					for (CnmRSoggRata cnmRSoggRata : cnmRSoggRataList) {
						CnmTPianoRate piano = cnmRSoggRata.getCnmTRata().getCnmTPianoRate();
						if (piano != null && !mapPiani.containsKey(piano.getIdPianoRate())) {
							mapPiani.put(piano.getIdPianoRate(), piano);
							List<CnmRAllegatoPianoRate> cnmRAllegatoPianoRateList = cnmAllegatoPianoRateRepository.findByCnmTPianoRate(piano);
							if (cnmRAllegatoPianoRateList != null && !cnmRAllegatoPianoRateList.isEmpty()) {
								for (CnmRAllegatoPianoRate cnmRAllegatoPianoRate : cnmRAllegatoPianoRateList) {
									CnmTAllegato all = cnmRAllegatoPianoRate.getCnmTAllegato();
									AllegatoVO allVO = allegatoEntityMapper.mapEntityToVO(all);
									splitAllegato(riepilogo, all.getCnmDTipoAllegato().getCnmDCategoriaAllegato().getIdCategoriaAllegato(), allVO);
								}
							}
						}	
					}
				}
			}
		}


		riepilogo.setRateizzazione(eliminaDuplicati(riepilogo.getRateizzazione()));
		return riepilogo;
	}

	private List<AllegatoVO> eliminaDuplicati(List<AllegatoVO> finalAllegatiList) {
		Map<Integer, AllegatoVO> AllegatoVOMap = new HashMap<Integer, AllegatoVO>();
		for(AllegatoVO allegato : finalAllegatiList) {
			if(!AllegatoVOMap.containsKey(allegato.getId())) {
				AllegatoVOMap.put(allegato.getId(), allegato);
			}
		}
		return new ArrayList<AllegatoVO>(AllegatoVOMap.values());
	}
	
	private List<AllegatoVO> findCnmTAllegatoByIdVerbale(Integer id) {
		List<AllegatoVO> allegato = new ArrayList<AllegatoVO>();
		CnmTVerbale verbale = new CnmTVerbale();
		verbale.setIdVerbale(id);
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(verbale);
		if (cnmRVerbaleSoggettoList != null && cnmRVerbaleSoggettoList.size() > 0) {
			List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSog = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettoList);

			if (cnmRAllegatoVerbSog != null && cnmRAllegatoVerbSog.size() > 0) {
				List<CnmTAllegato> temp = cnmTAllegatoRepository.findByCnmRAllegatoVerbSogsIn(cnmRAllegatoVerbSog);

				if (temp != null && temp.size() > 0) {
					for (CnmTAllegato a : temp) {
						if (StringUtils.isNotBlank(a.getIdIndex()) && a.getNomeFile().equalsIgnoreCase("Verbale_audizione.pdf") && StringUtils.isBlank(a.getNumeroProtocollo())) {
							allegato.add(allegatoEntityMapper.mapEntityToVO(a));
						}
					}
				}

			}

		}

		return allegato;
	}

	@Override
	public RiepilogoAllegatoVO getAllegatiByIdVerbale(Integer id, UserDetails userDetails, boolean includiControlloUtenteProprietarioIstruttoreAssegnato) {
		return getAllegatiByIdVerbale(id, userDetails, includiControlloUtenteProprietarioIstruttoreAssegnato, false);
	}

	private void splitAllegato(RiepilogoAllegatoVO riepilogo, long idCategoria, AllegatoVO allegato) {
		if (idCategoria == Constants.ID_CATEGORIA_ALLEGATO_VERBALE) {
			riepilogo.getVerbale().add(allegato);
		}
		if (idCategoria == Constants.ID_CATEGORIA_ALLEGATO_ISTRUTTORIA) {
			riepilogo.getIstruttoria().add(allegato);
		}
		if (idCategoria == Constants.ID_CATEGORIA_ALLEGATO_GIURISDIZIONALE) {
			riepilogo.getGiurisdizionale().add(allegato);
		}
		if (idCategoria == Constants.ID_CATEGORIA_ALLEGATO_RATEIZZAZIONE) {
			riepilogo.getRateizzazione().add(allegato);
		}
	}

	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliVerbale(Integer idVerbale, String tipoDocumento, boolean aggiungiCategoriaEmail) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(idVerbale);

		//20211214 - Se il verbale è negli stati transitori non posso aggiungere nessun allegato
		if(cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_PROTOCOLLAZIONE_IN_ATTESA_VERIFICA_PAGAMENTO
			||cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_PROTOCOLLAZIONE_PER_MANCANZA_CF
			||cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_IN_ACQUISIZIONE
			||cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_IN_ACQUISIZIONE_CON_PAGAMENTO
			||cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_IN_ACQUISIZIONE_CON_SCRITTI_DIFENSIVI
			) {
			
			return new ArrayList<TipoAllegatoVO>();	
		}
		
		TipoAllegato tipo = TipoAllegato.getTipoAllegatoByTipoDocumento(tipoDocumento);
		Long idTipoDocumento = tipo != null ? tipo.getId() : null;

		List<TipoAllegatoVO> listAllegabili = tipoAllegatoEntityMapper.mapListEntityToListVO(cnmDTipoAllegatoRepository.findAll(CnmDTipoAllegatoSpecification.findBy(//
				idTipoDocumento, // idTipologiaDocumento
				null, // categoria non necessaria per i tipi
				Constants.ID_UTILIZZO_ALLEGATO_VERBALE, // utilizzo
				cnmTVerbale.getCnmDStatoVerbale(), // stato
				null // stato ordinanza
		)));

		List<TipoAllegatoVO> listAllegati;
		if (tipo != null && tipo.isAllegabileMultiplo())
			listAllegati = new ArrayList<>();
		else
			listAllegati = getTipologiaAllegatiByCnmVerbale(cnmTVerbale);

		TipoAllegatoVO scrittiDifensivi = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.SCRITTI_DIFENSIVI.getId()));
		TipoAllegatoVO controdeduzione = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.CONTRODEDUZIONE.getId()));
		TipoAllegatoVO comparsa = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.COMPARSA.getId()));
		while (listAllegati.remove(scrittiDifensivi)) {
		}
		while (listAllegati.remove(controdeduzione)) {
		}
		while (listAllegati.remove(comparsa)) {
		}
		listAllegabili.removeAll(listAllegati);
		
		// 20210921 PP - consente di inserire ancora una ricevuta di pagamento se l'importo pagato e' inferiore all'importo del verbale
		try {
			if(tipo==null || tipo == TipoAllegato.RICEVUTA_PAGAMENTO_VERBALE) {
				BigDecimal importoPagato = cnmTAllegatoFieldRepository.getImportoPagatoByIdVerbale(idVerbale);
				if(importoPagato == null) {
					importoPagato = new BigDecimal(0);
				}
				if(importoPagato.compareTo(cnmTVerbale.getImportoVerbale())<0) {
					TipoAllegatoVO ricevutaPagamentoVerbale = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.RICEVUTA_PAGAMENTO_VERBALE.getId()));
					while (listAllegabili.remove(ricevutaPagamentoVerbale)) {
					}
					listAllegabili.add(ricevutaPagamentoVerbale);
				}
			}
		}catch(Throwable t) {}
		
		//20200729_ET aggiunto blocco per gestione tipi doc EMAIL
		if(idTipoDocumento==null && aggiungiCategoriaEmail) {
			listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.EMAIL_VERBALE.getId())));
			//20200911_ET CONAM-95
			if(listAllegabili.contains(controdeduzione)) {
				listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.EMAIL_ISTRUTTORIA.getId())));
			}
		}
		else if(controdeduzione.getId()==idTipoDocumento && aggiungiCategoriaEmail) {
			listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.EMAIL_ISTRUTTORIA.getId())));
		}
		
		//20211108 PP - aggiungo la relata di notifica solo se ci sono soggetti che non hanno ancora una relata
		TipoAllegatoVO relataNotifica = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.RELATA_NOTIFICA.getId()));
		while (listAllegabili.remove(relataNotifica)) {
		}
//		listAllegabili.remove(relataNotifica);
		
		if (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO ) {
			listAllegabili = new ArrayList<TipoAllegatoVO> ();
		}
		
		if(idTipoDocumento == null || idTipoDocumento == TipoAllegato.RELATA_NOTIFICA.getId()) {
			List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettosWithoutAllegato = allegatoVerbaleSoggettoService.findCnmRVerbaleSoggettosWithoutAllegato(cnmTVerbale, TipoAllegato.RELATA_NOTIFICA);
			if(cnmRVerbaleSoggettosWithoutAllegato != null && cnmRVerbaleSoggettosWithoutAllegato.size()>0) {
				listAllegabili.add(relataNotifica);
			}		
		}

		// 20210909 PP - Evolutiva E1 Comunicazione Varie
		if(idTipoDocumento == null || idTipoDocumento == TipoAllegato.COMUNICAZIONI_VARIE.getId()) {
			TipoAllegatoVO comunicazioniVarie = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.COMUNICAZIONI_VARIE.getId()));
			while (listAllegabili.remove(comunicazioniVarie)) {
			}
			listAllegabili.add(comunicazioniVarie);
		}				

		return listAllegabili;
	}

	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliVerbalePregressi(Integer idVerbale, String tipoDocumento, boolean aggiungiCategoriaEmail) {
		
		//se idVerbale=null siamo nel caso di nuovo verbale, quindi restituisco solo il verbale di accertamento
		if(idVerbale==null) {
			List<TipoAllegatoVO> listAllegabili = new ArrayList<TipoAllegatoVO>();
			listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.VERBALE_ACCERTAMENTO.getId())));
			return listAllegabili;
		}
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(idVerbale);

		TipoAllegato tipo = TipoAllegato.getTipoAllegatoByTipoDocumento(tipoDocumento);
		Long idTipoDocumento = tipo != null ? tipo.getId() : null;

		List<TipoAllegatoVO> listAllegabili = tipoAllegatoEntityMapper.mapListEntityToListVO(cnmDTipoAllegatoRepository.findAll(CnmDTipoAllegatoSpecification.findBy(//
				idTipoDocumento, // idTipologiaDocumento
				null, // categoria non necessaria per i tipi
				Constants.ID_UTILIZZO_ALLEGATO_VERBALE, // utilizzo
				cnmTVerbale.getCnmDStatoVerbale(), // stato
				null // stato ordinanza
		)));

		List<TipoAllegatoVO> listAllegati;
		if (tipo != null && tipo.isAllegabileMultiplo())
			listAllegati = new ArrayList<>();
		else {
			listAllegati = getTipologiaAllegatiByCnmVerbale(cnmTVerbale);
			// se non e' ancora stato incluso il rapporto di trasmissione, restituisco solo quello e email verbale
			//if(listAllegati.size()==1 && listAllegati.get(0).getId() == TipoAllegato.VERBALE_ACCERTAMENTO.getId()) {
			TipoAllegatoVO rapportoTrasmissione = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.RAPPORTO_TRASMISSIONE.getId()));
			if(!listAllegati.contains(rapportoTrasmissione)) {
				listAllegabili = new ArrayList<TipoAllegatoVO>();
				listAllegabili.add(rapportoTrasmissione);
				listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.EMAIL_VERBALE.getId())));
				return listAllegabili;
			}
		}

		TipoAllegatoVO scrittiDifensivi = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.SCRITTI_DIFENSIVI.getId()));
		TipoAllegatoVO controdeduzione = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.CONTRODEDUZIONE.getId()));
		TipoAllegatoVO comparsa = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.COMPARSA.getId()));
		//20201127_ET prima di rimuovere la comparsa controllo che ci sia questa categoria per aggiungere la categoria comparsaAllegato, necessaria solo per i pregressi
		if(listAllegati.contains(comparsa)) {
			listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.COMPARSA_ALLEGATO.getId())));
		}
		while (listAllegati.remove(scrittiDifensivi)) {
		}
		while (listAllegati.remove(controdeduzione)) {
		}
		while (listAllegati.remove(comparsa)) {
		}

		listAllegabili.removeAll(listAllegati);
		
		// 20210921 PP - consente di inserire ancora una ricevuta di pagamento se l'importo pagato e' inferiore all'importo del verbale
		try {
			if(tipo!=null && tipo == TipoAllegato.RICEVUTA_PAGAMENTO_VERBALE) {
				BigDecimal importoPagato = cnmTAllegatoFieldRepository.getImportoPagatoByIdVerbale(idVerbale);
				if(importoPagato.intValue()!=0) {
					if(importoPagato.intValue()<cnmTVerbale.getImportoVerbale().intValue()) {
						TipoAllegatoVO ricevutaPagamentoVerbale = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.RICEVUTA_PAGAMENTO_VERBALE.getId()));
						listAllegabili.add(ricevutaPagamentoVerbale);
					}
				}
			}
		}catch(Throwable t) {}
		
		//20200729_ET aggiunto blocco per gestione tipi doc EMAIL
		if(idTipoDocumento==null && aggiungiCategoriaEmail) {
			listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.EMAIL_VERBALE.getId())));
			//20200911_ET CONAM-95
			if(listAllegabili.contains(controdeduzione)) {
				listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.EMAIL_ISTRUTTORIA.getId())));
			}
		}
		else if(controdeduzione.getId()==idTipoDocumento && aggiungiCategoriaEmail) {
			listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.EMAIL_ISTRUTTORIA.getId())));
		}
		
		// 20200511 PP - per i pregressi, in caso il verbale sia in stato ACQUISITO o ACQUISITO CON SCRITTI DIFENSIVI, 
		// visualizzo anche ORDINANZA_INGIUNZIONE_PAGAMENTO, ORDINANZA_ARCHIVIAZIONE, EMAIL_ISTRUTTORIA, LETTERA_ORDINANZA
		if(cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_ACQUISITO
				|| cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI) {
			listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId())));
			listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId())));
			// 20210304_LC ordinanza annullamento non è allegabile con il metodo "standard" e nel pregreso
//			listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.EMAIL_ISTRUTTORIA.getId())));
//			listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.LETTERA_ORDINANZA.getId())));
		}
		listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId())));
		listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.VERBALE_AUDIZIONE.getId())));

		//20211108 PP - aggiungo la relata di notifica solo se ci sono soggetti che non hanno ancora una relata
		TipoAllegatoVO relataNotifica = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.RELATA_NOTIFICA.getId()));
		while (listAllegabili.remove(relataNotifica)) {
		}
		
		if(idTipoDocumento == null || idTipoDocumento == TipoAllegato.RELATA_NOTIFICA.getId()) {
			List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettosWithoutAllegato = allegatoVerbaleSoggettoService.findCnmRVerbaleSoggettosWithoutAllegato(cnmTVerbale, TipoAllegato.RELATA_NOTIFICA);
			if(cnmRVerbaleSoggettosWithoutAllegato != null && cnmRVerbaleSoggettosWithoutAllegato.size()>0) {
				if (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO ) {
					listAllegabili = new ArrayList<TipoAllegatoVO> ();
				}
				listAllegabili.add(relataNotifica);
			}		
		}

		// 20210909 PP - Evolutiva E1 Comunicazione Varie
		if(idTipoDocumento == null || idTipoDocumento == TipoAllegato.COMUNICAZIONI_VARIE.getId()) {
			TipoAllegatoVO comunicazioniVarie = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.COMUNICAZIONI_VARIE.getId()));
			while (listAllegabili.remove(comunicazioniVarie)) {
			}
			listAllegabili.add(comunicazioniVarie);
		}	
		
		
		return listAllegabili;
	}
	
	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiVerbale(String tipoDocumento) {
		TipoAllegato tipo = TipoAllegato.getTipoAllegatoByTipoDocumento(tipoDocumento);
		Long idTipoDocumento = tipo != null ? tipo.getId() : null;
		CnmDTipoAllegato tipoAllegato = cnmDTipoAllegatoRepository.findOne(idTipoDocumento);
		List<CnmDTipoAllegato> lista = new ArrayList<CnmDTipoAllegato>();
		lista.add(tipoAllegato);
		List<TipoAllegatoVO> listAllegabili = tipoAllegatoEntityMapper.mapListEntityToListVO(lista);

		return listAllegabili;
	}

	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiByCnmVerbale(CnmTVerbale cnmTVerbale) {
		List<CnmRAllegatoVerbale> cnmRAllegatoVerbaleList = cnmRAllegatoVerbaleRepository.findByCnmTVerbale(cnmTVerbale);
		List<TipoAllegatoVO> listAllegati = new ArrayList<>();
		if (cnmRAllegatoVerbaleList != null && !cnmRAllegatoVerbaleList.isEmpty()) {
			for (CnmRAllegatoVerbale cnmRAllegatoVerbale : cnmRAllegatoVerbaleList) {
				listAllegati.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmRAllegatoVerbale.getCnmTAllegato().getCnmDTipoAllegato()));
			}
		}
		return listAllegati;
	}
	
	public String verificaNome(String nome) {
		Pattern pattern = Pattern.compile("[^A-Za-z0-9]+");
		String nomePDF = "";

		for (int i = 0; i < nome.length(); i++) {
			Matcher matcher = pattern.matcher("" + nome.charAt(i));
			if (matcher.matches()) {
				nomePDF = nomePDF + "_";
			} else {
				nomePDF = nomePDF + nome.charAt(i);
			}
		}

		return nomePDF;
	}

	// salva solo nella r_verbale
	@Override
	public AllegatoVO salvaAllegato(List<InputPart> data, List<InputPart> file, UserDetails userDetails, boolean pregresso) {
		Long idUser = userDetails.getIdUser();
		SalvaAllegatoVerbaleRequest request = commonAllegatoService.getRequest(data, file, SalvaAllegatoVerbaleRequest.class);
		byte[] byteFile = request.getFile();
		String fileName = request.getFilename();
		Long idTipoAllegato = request.getIdTipoAllegato() != null ? request.getIdTipoAllegato() : new Long(7);
		Integer idVerbale = request.getIdVerbale();
		List<AllegatoFieldVO> configAllegato = request.getAllegatoField();
		CnmTUser cnmTUser = cnmTUserRepository.findOne(idUser);
		boolean nofile = false;
		Integer idVerbaleSoggetto = null;
		if (fileName == null || idTipoAllegato == TipoAllegato.RELATA_NOTIFICA.getId()) {
			String s = "";
			for (AllegatoFieldVO all : configAllegato) {
				if (all.getFieldType().getId() == Constants.FIELD_TYPE_BOOLEAN) {
//					if(all.getBooleanValue() != null) {
//						s += "PAGAMENTO PARZIALE: " +all.getBooleanValue().toString() + " ";
//					}else {
//						s += "PAGAMENTO PARZIALE: " +"false" + " ";
//					}
				} else if (all.getFieldType().getId() == Constants.FIELD_TYPE_NUMERIC || all.getFieldType().getId() == Constants.FIELD_TYPE_ELENCO) {
					NumberFormat n = NumberFormat.getCurrencyInstance(Locale.ITALY);
					double money = 0;
					if(all.getNumberValue()!= null)
						money = all.getNumberValue().doubleValue();					
					String string = n.format(money);
					s += "IMPORTO PAGATO: " + string + " ";
				} else if (all.getFieldType().getId() == Constants.FIELD_TYPE_STRING) {
					if(all.getStringValue()!=null)
						s += "CONTO CORRENTE VERSAMENTO: " + all.getStringValue().toString() + " ";
				} else if (all.getFieldType().getId() == Constants.FIELD_TYPE_DATA_ORA) {
					if(all.getDateValue()!=null)
						s += all.getDateTimeValue().toString() + " ";
				} else if (all.getFieldType().getId() == Constants.FIELD_TYPE_DATA) {
					if(all.getDateValue()!=null)
						s += "DATA PAGAMENTO: " + all.getDateValue().toString() + " ";
				}	else if (all.getFieldType().getId() == Constants.FIELD_TYPE_ELENCO_SOGGETTI) {
					if(all.getNumberValue()!= null)
						idVerbaleSoggetto = all.getNumberValue().intValue();
				}
			}

			byteFile = s.getBytes();
			CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findByIdVerbale(idVerbale);
			if (idTipoAllegato != TipoAllegato.RELATA_NOTIFICA.getId()) {
				fileName = "Promemoria_pagamento_verbale_" + verificaNome(cnmTVerbale.getNumVerbale()) + ".txt";
				nofile = true;
			}
			
		}

		// controlli sicurezza
		CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findOne(idVerbale);
		if (cnmTVerbale == null)
			throw new SecurityException("Verbale non trovato");

		// controllo tipi allegabili per stato verbale
		if (idTipoAllegato != TipoAllegato.VERBALE_AUDIZIONE.getId()) {
			List<TipoAllegatoVO> allegati = getTipologiaAllegatiAllegabiliVerbale(idVerbale, TipoAllegato.getTipoDocumentoByIdTipoDocumento(idTipoAllegato), false);
			if (allegati == null)
				throw new SecurityException("non è possibile allegare allegati");
			else {
				TipoAllegatoVO allegato = Iterables.tryFind(allegati, new Predicate<TipoAllegatoVO>() {
					public boolean apply(TipoAllegatoVO tipoAllegatoVO) {
						return idTipoAllegato.equals(tipoAllegatoVO.getId());
					}
				}).orNull();
				if (allegato == null)
					throw new SecurityException("non è possibile allegare questo tipo  allegato");
			}
		}

		// controllo dimensione allegato
		UploadUtils.checkDimensioneAllegato(byteFile);

		// 20201026 PP- controllo se e' stato caricato un file firmato , con firma non valida senza firma
		utilsDoqui.checkFileSign(byteFile, fileName);
		
		// controllo regole allegati verbale
		controllaRegoleFieldAllegatiVerbale(cnmTVerbale, idTipoAllegato, configAllegato);

		long idStatoVerbale = cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale();
		CnmTAllegato cnmTAllegato;
		if (nofile) {
			cnmTAllegato = commonAllegatoService.salvaAllegato(byteFile, fileName, idTipoAllegato, configAllegato, cnmTUser, TipoProtocolloAllegato.NON_PROTOCOLLARE, null, null, false, false, null,
					null, 0, null, null, null);
		} else if (idStatoVerbale == Constants.STATO_VERBALE_INCOMPLETO) {
			
			// 2020-06-15 PP - INIZIO
			String folder = utilsDoqui.createOrGetfolder(cnmTVerbale);
			String idEntitaFruitore = utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmDTipoAllegatoRepository.findOne(idTipoAllegato));
//			String soggettoActa = "DEFAULT_SUBJECT";// = utilsStadoc.getSoggettoActa(cnmTVerbale);
			String rootActa = utilsDoqui.getRootActa(cnmTVerbale);

				cnmTAllegato = commonAllegatoService.salvaAllegato(byteFile, fileName, idTipoAllegato, configAllegato, cnmTUser, TipoProtocolloAllegato.DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO, folder, idEntitaFruitore,
					false, false, null, rootActa, 0, null, null, null);
			// 2020-06-15 PP - FINE
			
			
			
//			cnmTAllegato = commonAllegatoService.salvaAllegato(byteFile, fileName, idTipoAllegato, configAllegato, cnmTUser, TipoProtocolloAllegato.DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO, null, null,
//					false, false, null, null, 0, null, null, null);
			
		} else if (idTipoAllegato == TipoAllegato.VERBALE_AUDIZIONE.getId()) {
			List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);

			Integer idVerbaleAudizione = 0;
			for (CnmRVerbaleSoggetto c : cnmRVerbaleSoggettoList) {
				idVerbaleAudizione = allegatoVerbaleSoggettoService.getIdVerbaleAudizione(c, TipoAllegato.VERBALE_AUDIZIONE);
				if (idVerbaleAudizione != null && idVerbaleAudizione != 0)
					break;
			}

			CnmTAllegato cnmTAllegatoAudizione = cnmTAllegatoRepository.findOne(idVerbaleAudizione);
			List<CnmRAllegatoVerbSog> cnmRAllegatoVerbList = cnmRAllegatoVerbSogRepository.findByCnmTAllegato(cnmTAllegatoAudizione);
			List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoListA = cnmRVerbaleSoggettoRepository.findByCnmRAllegatoVerbSogsIn(cnmRAllegatoVerbList);
			List<CnmTSoggetto> cnmTSoggettoList = cnmTSoggettoRepository.findByCnmRVerbaleSoggettosIn(cnmRVerbaleSoggettoListA);

			cnmTAllegato = commonAllegatoService.salvaAllegato(byteFile, fileName, idTipoAllegato, configAllegato, cnmTUser, TipoProtocolloAllegato.PROTOCOLLARE,
					utilsDoqui.createOrGetfolder(cnmTVerbale), utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmDTipoAllegatoRepository.findOne(idTipoAllegato)), false, false,
					utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale), 0, idVerbaleAudizione, StadocServiceFacade.TIPOLOGIA_DOC_ACTA_DOC_USCITA_SENZA_ALLEGATI_GENERERATI,
					cnmTSoggettoList);

		} else if (idTipoAllegato == TipoAllegato.COMPARSA.getId()) {
			CnmDTipoAllegato cnmDTipoAllegato = new CnmDTipoAllegato();
			cnmDTipoAllegato.setIdTipoAllegato(23);
			cnmDTipoAllegato.setDescTipoAllegato("Lettera comparsa di costituzione e risposta");

			String fruitore = utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmDTipoAllegato);
			cnmTAllegato = commonAllegatoService.salvaAllegato(byteFile, fileName, idTipoAllegato, null, cnmTUser, TipoProtocolloAllegato.SALVA_MULTI_SENZA_PROTOCOLARE,
					utilsDoqui.createOrGetfolder(cnmTVerbale), fruitore, true, false, null, utilsDoqui.getRootActa(cnmTVerbale), 0, 0,
					StadocServiceFacade.TIPOLOGIA_DOC_ACTA_MASTER_USCITA_CON_ALLEGATI, null);

		} else {
			cnmTAllegato = commonAllegatoService.salvaAllegato(byteFile, fileName, idTipoAllegato, configAllegato, cnmTUser, TipoProtocolloAllegato.PROTOCOLLARE,
					utilsDoqui.createOrGetfolder(cnmTVerbale), utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmDTipoAllegatoRepository.findOne(idTipoAllegato)), false, false,
					utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale), 0, 0, StadocServiceFacade.TIPOLOGIA_DOC_ACTA_DOC_INGRESSO_SENZA_ALLEGATI, null);
		}

		if (request.getIdVerbaleAudizione() != null) {
			Integer idVerbaleAudizione = request.getIdVerbaleAudizione();

			List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs = cnmRAllegatoVerbSogRepository.findByCnmTAllegato(cnmTAllegatoRepository.findOne(idVerbaleAudizione));

			for (CnmRAllegatoVerbSog avs : cnmRAllegatoVerbSogs) {
				CnmRAllegatoVerbSog cnmRAllegatoVerbSog = new CnmRAllegatoVerbSog();
				CnmRVerbaleSoggetto cnmRVerbaleSoggetto = avs.getCnmRVerbaleSoggetto();
				CnmRAllegatoVerbSogPK cnmRAllegatoVerbSogPK = new CnmRAllegatoVerbSogPK();
				cnmRAllegatoVerbSogPK.setIdAllegato(cnmTAllegato.getIdAllegato());
				cnmRAllegatoVerbSogPK.setIdVerbaleSoggetto(cnmRVerbaleSoggetto.getIdVerbaleSoggetto());
				cnmRAllegatoVerbSog.setCnmTUser(cnmTUser);
				cnmRAllegatoVerbSog.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
				cnmRAllegatoVerbSog.setId(cnmRAllegatoVerbSogPK);
				cnmRAllegatoVerbSogRepository.save(cnmRAllegatoVerbSog);
			}
		} else {
			
			// 20211112 PP - controllo se si tratta di una relata di notifica, se si recupero dai metadati l'idVerbaleSoggetto e salvo in  CnmRAllegatoVerbSog
			if(idTipoAllegato == TipoAllegato.RELATA_NOTIFICA.getId()) {
				if(idVerbaleSoggetto != null) {
					CnmRAllegatoVerbSog cnmRAllegatoVerbSog = new CnmRAllegatoVerbSog();
					CnmRAllegatoVerbSogPK cnmRAllegatoVerbSogPK = new CnmRAllegatoVerbSogPK();
					cnmRAllegatoVerbSogPK.setIdAllegato(cnmTAllegato.getIdAllegato());
					cnmRAllegatoVerbSogPK.setIdVerbaleSoggetto(idVerbaleSoggetto);
					cnmRAllegatoVerbSog.setCnmTUser(cnmTUser);
					cnmRAllegatoVerbSog.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
					cnmRAllegatoVerbSog.setId(cnmRAllegatoVerbSogPK);
					cnmRAllegatoVerbSogRepository.save(cnmRAllegatoVerbSog);
				}
			}else {
				CnmRAllegatoVerbale cnmRAllegatoVerbale = new CnmRAllegatoVerbale();
	
				CnmRAllegatoVerbalePK cnmRAllegatoVerbalePK = new CnmRAllegatoVerbalePK();
				cnmRAllegatoVerbalePK.setIdAllegato(cnmTAllegato.getIdAllegato());
				cnmRAllegatoVerbalePK.setIdVerbale(idVerbale);
				cnmRAllegatoVerbale.setCnmTUser(cnmTUser);
				cnmRAllegatoVerbale.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
				cnmRAllegatoVerbale.setId(cnmRAllegatoVerbalePK);
				cnmRAllegatoVerbaleRepository.save(cnmRAllegatoVerbale);
			}
		}

		// 20201021 PP - Imposto il flag pregresso sull'allegato
		cnmTAllegato.setFlagDocumentoPregresso(pregresso);
		cnmTAllegatoRepository.save(cnmTAllegato);

		return allegatoEntityMapper.mapEntityToVO(cnmTAllegato);
	}

	@Override
	public AllegatoVO salvaAllegatiMultipli(List<InputPart> data, List<InputPart> file, UserDetails userDetails, boolean pregresso) {

		Long idUser = userDetails.getIdUser();
		SalvaAllegatiMultipliRequest request = commonAllegatoService.getRequest(data, file, SalvaAllegatiMultipliRequest.class, true);

		if (request == null || request.getAllegati() == null || request.getAllegati().size() == 0)
			throw new SecurityException("allegato non trovato");

		Integer idVerbale = request.getIdVerbale();
		CnmTUser cnmTUser = cnmTUserRepository.findOne(idUser);
		String idActa = null;

		// controlli sicurezza
		CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findOne(idVerbale);
		if (cnmTVerbale == null)
			throw new SecurityException("Verbale non trovato");

		CnmTAllegato cnmTAllegato = null, response = null;
		String folder = utilsDoqui.createOrGetfolder(cnmTVerbale), rootActa = utilsDoqui.getRootActa(cnmTVerbale);

		for (AllegatoMultiploVO allegato : request.getAllegati()) {
			if (allegato.isMaster()) {
				CnmDTipoAllegato cnmDTipoAllegato = new CnmDTipoAllegato();
				cnmDTipoAllegato.setIdTipoAllegato(TipoAllegato.COMPARSA.getId());
				cnmDTipoAllegato.setDescTipoAllegato("Lettera comparsa di costituzione e risposta");

				String fruitore = utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmDTipoAllegato);
				cnmTAllegato = salvaAllegatoMulti(allegato, cnmTUser, folder, rootActa, request.getAllegati().size() - 1, fruitore, pregresso);
				response = cnmTAllegato;
				idActa = cnmTAllegato.getIdActa();

				CnmRAllegatoVerbale cnmRAllegatoVerbale = new CnmRAllegatoVerbale();

				CnmRAllegatoVerbalePK cnmRAllegatoVerbalePK = new CnmRAllegatoVerbalePK();
				cnmRAllegatoVerbalePK.setIdAllegato(cnmTAllegato.getIdAllegato());
				cnmRAllegatoVerbalePK.setIdVerbale(idVerbale);
				cnmRAllegatoVerbale.setCnmTUser(cnmTUser);
				cnmRAllegatoVerbale.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
				cnmRAllegatoVerbale.setId(cnmRAllegatoVerbalePK);
				cnmRAllegatoVerbaleRepository.save(cnmRAllegatoVerbale);

				break;
			}
		}

		if (request.getAllegati().size() >= 2) {
			CnmDStatoAllegato avviospostamentoActa = cnmDStatoAllegatoRepository.findOne(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA);
			Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

			for (AllegatoMultiploVO allegato : request.getAllegati()) {
				if (!allegato.isMaster()) {
					cnmTAllegato = salvaAllegatoMulti(allegato, cnmTUser, folder, rootActa, 0, null, pregresso);
					cnmTAllegato.setCnmDStatoAllegato(avviospostamentoActa);
					cnmTAllegato.setDataOraUpdate(now);
					cnmTAllegato.setIdActaMaster(idActa);
					allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);
				}
			}
		}

		return allegatoEntityMapper.mapEntityToVO(response);
	}
	


	private CnmTAllegato salvaAllegatoMulti(AllegatoMultiploVO allegato, CnmTUser cnmTUser, String folder, String rootActa, int numeroAllegati, String fruitore, boolean pregresso) {
		CnmTAllegato cnmTAllegato = null;

		byte[] byteFile = allegato.getFile();
		String fileName = allegato.getFilename();
		Long idTipoAllegato = allegato.getIdTipoAllegato();

		if (allegato.isMaster()) {
			idTipoAllegato = allegato.getIdTipoAllegato();
		} else {
			idTipoAllegato = TipoAllegato.COMPARSA_ALLEGATO.getId();
		}

		// controllo dimensione allegato
		UploadUtils.checkDimensioneAllegato(byteFile);

		// 20201026 PP- controllo se e' stato caricato un file firmato , con firma non valida senza firma
		utilsDoqui.checkFileSign(byteFile, fileName);
		
		cnmTAllegato = commonAllegatoService.salvaAllegato(byteFile, fileName, idTipoAllegato, null, cnmTUser, TipoProtocolloAllegato.SALVA_MULTI_SENZA_PROTOCOLARE, folder, fruitore,
				allegato.isMaster(), false, null, rootActa, numeroAllegati, 0, StadocServiceFacade.TIPOLOGIA_DOC_ACTA_MASTER_USCITA_CON_ALLEGATI, null);

		// 20201021 PP - Imposto il flag pregresso sull'allegato
		cnmTAllegato.setFlagDocumentoPregresso(pregresso);
		cnmTAllegatoRepository.save(cnmTAllegato);
		
		return cnmTAllegato;

	}

	@Override
	@Transactional
	public void eliminaAllegato(Integer idVerbale, Integer idAllegato, UserDetails userDetails) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(idVerbale);

		long idStatoVerbale = cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale();
		if (idStatoVerbale != Constants.STATO_VERBALE_INCOMPLETO)
			throw new SecurityException("stato verbale non corretto per eliminare allegati");

		if (idAllegato == null)
			throw new SecurityException("idAllegato non esistente");
		CnmTAllegato cnmTAllegato = cnmTAllegatoRepository.findOne(idAllegato);
		if (cnmTAllegato == null)
			throw new SecurityException("cnmTAllegato non esistente");
		
		if(cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato() != TipoAllegato.RELATA_NOTIFICA.getId()) {
			CnmRAllegatoVerbalePK cnmRAllegatoVerbalePK = new CnmRAllegatoVerbalePK();
			cnmRAllegatoVerbalePK.setIdAllegato(cnmTAllegato.getIdAllegato());
			cnmRAllegatoVerbalePK.setIdVerbale(idVerbale);
			cnmRAllegatoVerbaleRepository.delete(cnmRAllegatoVerbalePK);
		}else {
			List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs = cnmRAllegatoVerbSogRepository.findByCnmTAllegato(cnmTAllegato);
			
			for(CnmRAllegatoVerbSog cnmRAllegatoVerbSog : cnmRAllegatoVerbSogs) {
				cnmRAllegatoVerbSogRepository.delete(cnmRAllegatoVerbSog);
			}
		}
		commonAllegatoService.eliminaAllegatoBy(cnmTAllegato);
		

	}

	@Override
	public void controllaRegoleFieldAllegatiVerbale(CnmTVerbale cnmTVerbale, Long idTipoAllegato, List<AllegatoFieldVO> configAllegato) {
		if (idTipoAllegato == TipoAllegato.RELATA_NOTIFICA.getId()) {
			for (AllegatoFieldVO c : configAllegato) {
				if (c.getIdField() == Constants.ID_FIELD_DATA_NOTIFICA && cnmTVerbale.getContestato())
					if (!c.getDateValue().isEqual(utilsDate.asLocalDate(cnmTVerbale.getDataOraAccertamento())))
						throw new BusinessException(ErrorCode.DATA_NOTIFICA_FIELD_DIVERSA);
			}
		}
	}

	@Override
	public IsCreatedVO isConvocazioneCreated(IsCreatedVO request) {
		List<Integer> idVerbaleSoggettoList = request.getIds();
		IsCreatedVO isCreatedVO = new IsCreatedVO();
		isCreatedVO.setCreated(false);
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettos = (List<CnmRVerbaleSoggetto>) cnmRVerbaleSoggettoRepository.findAll(idVerbaleSoggettoList);
		if (cnmRVerbaleSoggettos != null && !cnmRVerbaleSoggettos.isEmpty()) {
			List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettos);
			if (cnmRAllegatoVerbSogs != null && !cnmRAllegatoVerbSogs.isEmpty()) {
				for (CnmRAllegatoVerbSog a : cnmRAllegatoVerbSogs) {
					if (a.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()) {
						isCreatedVO.setCreated(true);
					}
				}
			}
		}
		return isCreatedVO;
	}

	@Override
	public IsCreatedVO isVerbaleAudizioneCreated(IsCreatedVO request) {
		List<Integer> idVerbaleSoggettoList = request.getIds();
		IsCreatedVO isCreatedVO = new IsCreatedVO();
		isCreatedVO.setCreated(false);
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettos = (List<CnmRVerbaleSoggetto>) cnmRVerbaleSoggettoRepository.findAll(idVerbaleSoggettoList);
		if (cnmRVerbaleSoggettos != null && !cnmRVerbaleSoggettos.isEmpty()) {
			List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettos);
			if (cnmRAllegatoVerbSogs != null && !cnmRAllegatoVerbSogs.isEmpty()) {
				for (CnmRAllegatoVerbSog a : cnmRAllegatoVerbSogs) {
					if (a.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.VERBALE_AUDIZIONE.getId()) {
						isCreatedVO.setCreated(true);
					}
				}
			}
		}
		return isCreatedVO;
	}

	
	
	// 20200706_LC
	@Override
	public MessageVO salvaAllegatoProtocollatoVerbale(SalvaAllegatiProtocollatiRequest request,	UserDetails userDetails, boolean pregresso) {
		
		Long idUser = userDetails.getIdUser();
		CnmTUser cnmTUser = cnmTUserRepository.findOne(idUser);
		//Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		Integer idVerbale = request.getIdVerbale();
//		List<AllegatoVO> response = new ArrayList<AllegatoVO>();
		
		// controlli sicurezza
		CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findOne(idVerbale);
		if (cnmTVerbale == null)
			throw new SecurityException("Verbale non trovato");

		if(request.getDocumentoProtocollato()==null) {
			throw new SecurityException("Allegati gia protocollati non trovati");
		}
		
		if(request.getAllegati()==null) {
			throw new SecurityException("Tipi degli allegati gia protocollati non trovati");
		}		
	
		//List<CnmTAllegato> cnmTallegatoList = 
		commonAllegatoService.salvaAllegatoProtocollatoVerbale(request, cnmTUser, cnmTVerbale, pregresso);

	
//		for (CnmTAllegato allegato : cnmTallegatoList) {
//	
//			response.add(allegatoEntityMapper.mapEntityToVO(allegato));
//		}

		MessageVO response = null;
		// 20200903_LC da fix rilascio ieri
		if(cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_INCOMPLETO || cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO
				|| pregresso) { // 20201109_ET per i PREGRESSI sposto anche se sto processando un allegato, quindi non devo dare il messaggio
			return response;
		}
		//20200918_ET se il protocollo e' stato gia' associato al verbale, niente messaggio!! quindi controllo anche isGiaPresenteSuActa
		if(!request.getDocumentoProtocollato().isGiaPresenteSuActa() && StringUtils.isBlank(request.getDocumentoProtocollato().getRegistrazioneId())) {
			CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.ALLEGATO_SALVATO_MASTER_DA_SALVARE);
			if(cnmDMessaggio!=null) {
				String msg=cnmDMessaggio.getDescMessaggio();
				msg = String.format(msg, request.getDocumentoProtocollato().getFilenameMaster(), cnmTVerbale.getNumVerbale());
				response = new MessageVO(msg, cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
			}else {
				throw new SecurityException("Messaggio non trovato");
			}
		}
		
		return response;
	}


}
