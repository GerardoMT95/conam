/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.sollecito;

import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.notifica.NotificaService;
import it.csi.conam.conambl.business.service.ordinanza.StatoPagamentoOrdinanzaService;
import it.csi.conam.conambl.business.service.pianorateizzazione.UtilsPianoRateizzazioneService;
import it.csi.conam.conambl.business.service.sollecito.SollecitoService;
import it.csi.conam.conambl.business.service.sollecito.UtilsSollecitoService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.ROrdinanzaVerbSogEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.SollecitoEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoSollecitoEntityMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.response.ImportoResponse;
import it.csi.conam.conambl.response.RiconciliaSollecitoResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author riccardo.bova
 * @date 11 feb 2019
 */
@Service
public class SollecitoServiceImpl implements SollecitoService {

	@Autowired
	private CnmTSollecitoRepository cnmTSollecitoRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private SollecitoEntityMapper sollecitoEntityMapper;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CnmDStatoSollecitoRepository cnmDStatoSollecitoRepository;
	@Autowired
	private CnmDTipoSollecitoRepository cnmDTipoSollecitoRepository;
	@Autowired
	private UtilsSollecitoService utilsSollecito;
	@Autowired
	private StatoSollecitoEntityMapper statoSollecitoEntityMapper;
	@Autowired
	private CnmDStatoOrdVerbSogRepository cnmDStatoOrdVerbSogRepository;
	@Autowired
	private ROrdinanzaVerbSogEntityMapper rOrdinanzaVerbSogEntityMapper;
	@Autowired
	private StatoPagamentoOrdinanzaService statoPagamentoOrdinanzaService;
	@Autowired
	private NotificaService notificaService;
	
	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;
	@Autowired
	private CommonSoggettoService commonSoggettoService;
	
	@Autowired
	private UtilsPianoRateizzazioneService utilsPianoRateizzazione;

	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;
	@Autowired
	private CnmTRataRepository cnmTRataRepository;
	@Autowired
	private CnmRSollecitoSoggRataRepository cnmRSollecitoSoggRataRepository;
	
	
	@Override
	@Transactional
	public Integer salvaSollecito(SollecitoVO sollecitoVO, NotificaVO notificaVO, UserDetails userDetails) {
		if (sollecitoVO == null)
			throw new IllegalArgumentException("sollecitoVO = null");
		Integer idOrdinanzaVerbaleSoggetto = sollecitoVO.getIdSoggettoOrdinanza();
		if (idOrdinanzaVerbaleSoggetto == null)
			throw new IllegalArgumentException("idOrdinanzaVerbaleSoggetto = null");

		Integer idSollecito = sollecitoVO.getIdSollecito();
		CnmTSollecito cnmTSollecito;

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.findOne(idOrdinanzaVerbaleSoggetto);
		CnmDStatoSollecito cnmDStatoSollecito = cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_SOLLECITO_BOZZA);
		CnmDTipoSollecito cnmDTipoSollecito = cnmDTipoSollecitoRepository.findOne(Constants.ID_TIPO_SOLLECITO_ORDINANZA);

		if (idSollecito == null) {
			cnmTSollecito = sollecitoEntityMapper.mapVOtoEntity(sollecitoVO);
			cnmTSollecito.setCnmTUser2(cnmTUser);
			cnmTSollecito.setDataOraInsert(now);
			cnmTSollecito.setCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);
			cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecito);
			cnmTSollecito.setCnmDTipoSollecito(cnmDTipoSollecito);
			cnmTSollecito = cnmTSollecitoRepository.save(cnmTSollecito);
		} else {
			cnmTSollecito = cnmTSollecitoRepository.findOne(idSollecito);
			cnmTSollecito = sollecitoEntityMapper.mapVOtoEntityUpdate(sollecitoVO, cnmTSollecito);
			cnmTSollecito.setCnmTUser1(cnmTUser);
			cnmTSollecito.setDataOraUpdate(now);
			cnmTSollecito.setCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);
			cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecito);
			cnmTSollecito = cnmTSollecitoRepository.save(cnmTSollecito);
		}
		
		//Marts
		//Save della notifca se presente	-	20210415 se != null
		if(notificaVO != null) {
			notificaVO.setIdSollecito(cnmTSollecito.getIdSollecito());
			notificaService.salvaNotifica(notificaVO, userDetails);
		}

		return cnmTSollecito.getIdSollecito();

	}

	@Override
	public List<SollecitoVO> getSollecitiByIdOrdinanzaSoggetto(Integer idOrdinanzaVerbaleSoggetto) {
		if (idOrdinanzaVerbaleSoggetto == null)
			throw new IllegalArgumentException("id ordinanza soggetto = null");

		CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.findOne(idOrdinanzaVerbaleSoggetto);
		if (cnmROrdinanzaVerbSog == null)
			throw new SecurityException("id ordinanza soggetto = null");

		List<SollecitoVO> sollecitoVOList  = new ArrayList<>();
		// 20210402_LC tipo sollecito
		CnmDTipoSollecito cnmDTipoSollecito = cnmDTipoSollecitoRepository.findOne(Constants.ID_TIPO_SOLLECITO_ORDINANZA);
		List<CnmTSollecito> cnmTSollecitoList = cnmTSollecitoRepository.findByCnmROrdinanzaVerbSogAndCnmDTipoSollecito(cnmROrdinanzaVerbSog, cnmDTipoSollecito);	
		
		if (cnmTSollecitoList != null && !cnmTSollecitoList.isEmpty()) {
			for (CnmTSollecito soll : cnmTSollecitoList) {
				SollecitoVO sollecitoVO = sollecitoEntityMapper.mapEntityToVO(soll);
				
				if (soll.isFlagDocumentoPregresso()) {
					sollecitoVO.setBollettinoDaCreare(false);
				}
				
				sollecitoVOList.add(sollecitoVO);
			}
		}
								
		
		return sollecitoVOList;
	}

	@Override
	public void eliminaSollecito(Integer idSollecito) {
		CnmTSollecito cnmTSollecito = utilsSollecito.validateAndGetCnmTSollecito(idSollecito);

		if (cnmTSollecito.getCnmDStatoSollecito().getIdStatoSollecito() != Constants.ID_STATO_SOLLECITO_BOZZA)
			throw new SecurityException("il sollecito non si trova nello stato bozza");

		for(CnmRSollecitoSoggRata sollecitoRata : cnmRSollecitoSoggRataRepository.findByCnmTSollecito(cnmTSollecito)) {
			cnmRSollecitoSoggRataRepository.delete(sollecitoRata);
		}
		
		cnmTSollecitoRepository.delete(cnmTSollecito);
	}

	@Override
	public SollecitoVO getSollecitoById(Integer id) {
		CnmTSollecito cnmTSollecito = utilsSollecito.validateAndGetCnmTSollecito(id);
		SollecitoVO sollecitoVO = sollecitoEntityMapper.mapEntityToVO(cnmTSollecito);
		
		// 20201118_LC
		if(cnmTSollecito.isFlagDocumentoPregresso()) {
			sollecitoVO.setBollettinoDaCreare(false);
		}
		
		return sollecitoVO;
	}

	@Override
	@Transactional
	public RiconciliaSollecitoResponse riconcilaSollecito(SollecitoVO sollecito, UserDetails userDetails) {
		if (sollecito == null)
			throw new IllegalArgumentException("sollecito non valorizzata");
		if (sollecito.getIdSollecito() == null)
			throw new IllegalArgumentException("id sollecito non valorizzato");

		CnmTSollecito cnmTSollecito = cnmTSollecitoRepository.findOne(sollecito.getIdSollecito());
		if (cnmTSollecito == null)
			throw new SecurityException("cnmTSollecito non trovata");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

		cnmTSollecito.setImportoPagato(sollecito.getImportoPagato().setScale(2, RoundingMode.HALF_UP));
		cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_SOLLECITO_PAGATO_OFFLINE));
		cnmTSollecito.setDataPagamento(utilsDate.asDate(sollecito.getDataPagamento()));
		cnmTSollecito.setCnmTUser1(cnmTUser);
		cnmTSollecito.setDataOraUpdate(now);

		cnmTSollecito = cnmTSollecitoRepository.save(cnmTSollecito);

		sollecito.setStatoSollecito(statoSollecitoEntityMapper.mapEntityToVO(cnmTSollecito.getCnmDStatoSollecito()));
		sollecito.setIsRiconciliaEnable(Boolean.FALSE);

		// aggiorno stato ordinanza verbale soggetto
		CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmTSollecito.getCnmROrdinanzaVerbSog();
		cnmROrdinanzaVerbSog.setCnmTUser2(cnmTUser);
		cnmROrdinanzaVerbSog.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));
		cnmROrdinanzaVerbSog.setCnmDStatoOrdVerbSog(cnmDStatoOrdVerbSogRepository.findOne(Constants.ID_STATO_ORDINANZA_VERB_SOGG_PAGATO_OFFLINE));
		cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.save(cnmROrdinanzaVerbSog);
		
		statoPagamentoOrdinanzaService.verificaTerminePagamentoSollecito(
			cnmROrdinanzaVerbSog,
			cnmTUser,
			cnmTSollecito
		);
		
		
		RiconciliaSollecitoResponse riconciliaSollecitoResponse = new RiconciliaSollecitoResponse();
		riconciliaSollecitoResponse.setSollecito(sollecito);
		
		
		SoggettoVO soggettoVOToSet =
			rOrdinanzaVerbSogEntityMapper.mapEntityToVO(
				cnmROrdinanzaVerbSog
			);
		
		// 20201217_LC - JIRA 118
		CnmTVerbale cnmTVerbale = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTVerbale();
		CnmTSoggetto cnmTSoggetto = cnmTSoggettoRepository.findOne(soggettoVOToSet.getId());	
		if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
			soggettoVOToSet = commonSoggettoService.attachResidenzaPregressi(
				soggettoVOToSet,
				cnmTSoggetto,
				cnmTVerbale.getIdVerbale()
			);
		}	
		
		riconciliaSollecitoResponse.setSoggetto(soggettoVOToSet);

		return riconciliaSollecitoResponse;

	}

	@Override
	public ImportoResponse getImportiSollecitoByCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		ImportoResponse importoPianoResponse = new ImportoResponse();
		// 20210402_LC tipo sollecito
		CnmDTipoSollecito cnmDTipoSollecito = cnmDTipoSollecitoRepository.findOne(Constants.ID_TIPO_SOLLECITO_ORDINANZA);
		List<CnmTSollecito> cnmTSollecitos = cnmTSollecitoRepository.findByCnmROrdinanzaVerbSogAndCnmDTipoSollecito(cnmROrdinanzaVerbSog, cnmDTipoSollecito);
		BigDecimal importoDaPagare = BigDecimal.ZERO;
		BigDecimal importoPagato = BigDecimal.ZERO;

		if (!cnmTSollecitos.isEmpty()) {
			for (CnmTSollecito cnmTSollecito : cnmTSollecitos) {
				long statoSollecito = cnmTSollecito.getCnmDStatoSollecito().getIdStatoSollecito();
				if (statoSollecito == Constants.ID_STATO_SOLLECITO_PAGATO_ONLINE || //
						statoSollecito == Constants.ID_STATO_SOLLECITO_PAGATO_OFFLINE || //
						statoSollecito == Constants.ID_STATO_SOLLECITO_PROTOCOLLATO || //
						statoSollecito == Constants.ID_STATO_SOLLECITO_NOTIFICATO || //
						statoSollecito == Constants.ID_STATO_SOLLECITO_NON_NOTIFICATO || //
						statoSollecito == Constants.ID_STATO_SOLLECITO_ESTINTO)//
				{
					importoPagato = importoPagato.add(cnmTSollecito.getImportoPagato() != null ? cnmTSollecito.getImportoPagato() : BigDecimal.ZERO);
					importoDaPagare = importoDaPagare.add(cnmTSollecito.getImportoSollecito());
				}
			}
		}

		importoPianoResponse.setImportoDaPagare(importoDaPagare);
		importoPianoResponse.setImportoPagato(importoPagato);

		return importoPianoResponse;
	}

	
	
	// 20210325_LC lotto2scenario5
	@Override
	@Transactional
	public Integer salvaSollecitoRate(SollecitoVO sollecitoVO, NotificaVO notificaVO, Integer idPianoRateizzazione, UserDetails userDetails) {
		if (sollecitoVO == null)
			throw new IllegalArgumentException("sollecitoVO = null");
		Integer idOrdinanzaVerbaleSoggetto = sollecitoVO.getIdSoggettoOrdinanza();
		if (idOrdinanzaVerbaleSoggetto == null)
			throw new IllegalArgumentException("idOrdinanzaVerbaleSoggetto = null");

		Integer idSollecito = sollecitoVO.getIdSollecito();
		CnmTSollecito cnmTSollecito;

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.findOne(idOrdinanzaVerbaleSoggetto);

		CnmDStatoSollecito cnmDStatoSollecito = cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_SOLLECITO_BOZZA);
		CnmDTipoSollecito cnmDTipoSollecito = cnmDTipoSollecitoRepository.findOne(Constants.ID_TIPO_SOLLECITO_RATE);
				
		// creazione sollecito
		if (idSollecito == null) {
			cnmTSollecito = sollecitoEntityMapper.mapVOtoEntity(sollecitoVO);
			cnmTSollecito.setCnmTUser2(cnmTUser);
			cnmTSollecito.setDataOraInsert(now);
			cnmTSollecito.setCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);
			cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecito);
			cnmTSollecito.setCnmDTipoSollecito(cnmDTipoSollecito);
			cnmTSollecito = cnmTSollecitoRepository.save(cnmTSollecito);
		

			// salva relazione tra sollecito e soggetto-rata
			CnmTPianoRate cnmTPianoRate = utilsPianoRateizzazione.validateAndGetCnmTPianoRate(idPianoRateizzazione);
			List<CnmTRata> cnmTRataList = cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate);	
			if (cnmTRataList != null && !cnmTRataList.isEmpty()) {
				List<CnmRSoggRata> cnmRSoggRataList = cnmRSoggRataRepository.findByCnmTRataIn(cnmTRataList);
				if (cnmRSoggRataList != null && !cnmRSoggRataList.isEmpty()) {				
					for (CnmRSoggRata cnmRSoggRata : cnmRSoggRataList) {	
						CnmRSollecitoSoggRata cnmRSollecitoSoggRata = new CnmRSollecitoSoggRata();
						CnmRSollecitoSoggRataPK cnmRSollecitoSoggRataPK = new CnmRSollecitoSoggRataPK();
						cnmRSollecitoSoggRataPK.setIdOrdinanzaVerbSog(cnmRSoggRata.getCnmROrdinanzaVerbSog().getIdOrdinanzaVerbSog());
						cnmRSollecitoSoggRataPK.setIdRata(cnmRSoggRata.getCnmTRata().getIdRata());
						cnmRSollecitoSoggRataPK.setIdSollecito(cnmTSollecito.getIdSollecito());
						cnmRSollecitoSoggRata.setId(cnmRSollecitoSoggRataPK);
						cnmRSollecitoSoggRata.setDataOraInsert(now);
						cnmRSollecitoSoggRata.setCnmTUser1(cnmTUser);
						cnmRSollecitoSoggRataRepository.save(cnmRSollecitoSoggRata);
					}							
				}		
			}			
			
		} else {
			// aggiornamento sollecito
			cnmTSollecito = cnmTSollecitoRepository.findOne(idSollecito);
			cnmTSollecito = sollecitoEntityMapper.mapVOtoEntityUpdate(sollecitoVO, cnmTSollecito);
			cnmTSollecito.setCnmTUser1(cnmTUser);
			cnmTSollecito.setDataOraUpdate(now);
			
			// 20210416 PP - lascio lo stato precedente
//			cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecito);
			
			cnmTSollecito = cnmTSollecitoRepository.save(cnmTSollecito);
		}

		//Save della notifca se presente	-	20210415 se != null
		if(notificaVO != null) {
			notificaVO.setIdSollecito(cnmTSollecito.getIdSollecito());
			notificaService.salvaNotifica(notificaVO, userDetails);

			// 20210416 PP - Salvo lo stato precedente del sollecito se era in BOZZA
			if(cnmDStatoSollecito.getIdStatoSollecito() == Constants.ID_STATO_SOLLECITO_BOZZA ) {
				cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecito);
				cnmTSollecito = cnmTSollecitoRepository.save(cnmTSollecito);
			}
		}

		return cnmTSollecito.getIdSollecito();

	}
	
	
	// 20210325_LC lotto2scenario5
	@Override
	public List<SollecitoVO> getSollecitiByIdPianoRateizzazione(Integer idPianoRateizzazione) {
		if (idPianoRateizzazione == null)
			throw new IllegalArgumentException("id ordinanza soggetto = null");

		CnmTPianoRate cnmTPianoRate = utilsPianoRateizzazione.validateAndGetCnmTPianoRate(idPianoRateizzazione);
		if (cnmTPianoRate == null)
			throw new SecurityException("cnmTPianoRate non trovato");

		List<SollecitoVO> sollecitoVOList  = new ArrayList<>();
		Map<Integer, SollecitoVO> mapSollecitoVO = new HashMap<Integer, SollecitoVO>();

		// estrae rate dal piano
		List<CnmTRata> cnmTRataList = cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate);		

		if (cnmTRataList != null && !cnmTRataList.isEmpty()) {
	
			// estrae solleciti da rate
			List<CnmTSollecito> cnmTSollecitoList = cnmRSollecitoSoggRataRepository.findDistinctCnmTSollecitoByCnmTRataIn(cnmTRataList);
			if (cnmTSollecitoList != null && !cnmTSollecitoList.isEmpty()) {
				for (CnmTSollecito soll : cnmTSollecitoList) {
					SollecitoVO sollecitoVO = sollecitoEntityMapper.mapEntityToVO(soll);
					if (sollecitoVO != null && !mapSollecitoVO.containsKey(sollecitoVO.getIdSollecito()))
						mapSollecitoVO.put(sollecitoVO.getIdSollecito(), sollecitoVO);					
				}				
			}
			
//			List<CnmRSoggRata> cnmRSoggRataList = cnmRSoggRataRepository.findByCnmTRataIn(cnmTRataList);
//			// estrae lista di sollecito_sogg_rata; dove idOrdVerbSog e idRata sono quelli della sogg_rata
//			if (cnmRSoggRataList != null && !cnmRSoggRataList.isEmpty()) {				
//				for (CnmRSoggRata cnmRSoggRata : cnmRSoggRataList) {		
//					CnmRSollecitoSoggRata cnmRSollecitoSoggRata = cnmRSollecitoSoggRataRepository.findByCnmROrdinanzaVerbSogAndCnmTRata(cnmRSoggRata.getCnmROrdinanzaVerbSog(),cnmRSoggRata.getCnmTRata());
//					if (cnmRSollecitoSoggRata != null) {
//						CnmTSollecito cnmTSollecito = cnmRSollecitoSoggRata.getCnmTSollecito();
//						SollecitoVO sollecitoVO = sollecitoEntityMapper.mapEntityToVO(cnmTSollecito);
//						if (sollecitoVO != null && !mapSollecitoVO.containsKey(sollecitoVO.getIdSollecito()))
//							mapSollecitoVO.put(sollecitoVO.getIdSollecito(), sollecitoVO);
//					}				
//				}							
//			}
			
		}		
		
		sollecitoVOList = new ArrayList<SollecitoVO>(mapSollecitoVO.values());
		
		return sollecitoVOList;
	}

	
	
}
