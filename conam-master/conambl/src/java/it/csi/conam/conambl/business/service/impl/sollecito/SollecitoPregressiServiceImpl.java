/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.sollecito;

import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.notifica.NotificaService;
import it.csi.conam.conambl.business.service.sollecito.SollecitoPregressiService;
import it.csi.conam.conambl.business.service.sollecito.UtilsSollecitoService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.SollecitoEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoSollecitoEntityMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.request.riscossione.SalvaSollecitoPregressiRequest;
import it.csi.conam.conambl.response.SalvaSollecitoPregressiResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;
import it.csi.conam.conambl.vo.sollecito.StatoSollecitoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 20201109_LC 
@Service
public class SollecitoPregressiServiceImpl implements SollecitoPregressiService {

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
	private UtilsSollecitoService utilsSollecito;
	@Autowired
	private StatoSollecitoEntityMapper statoSollecitoEntityMapper;
	@Autowired
	private NotificaService notificaService;

	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;
	
	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;
	
	@Autowired
	private CommonAllegatoService commonAllegatoService;
	
	@Autowired
	private CnmRAllegatoSollecitoRepository cnmRAllegatoSollecitoRepository;

	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	
	/*@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;
	@Autowired
	private CnmRSollecitoSoggRataRepository cnmRSollecitoSoggRataRepository;*/
	@Autowired
	private CnmDTipoSollecitoRepository cnmDTipoSollecitoRepository;
	
	
	// modificata
	@Override
	@Transactional
	public SalvaSollecitoPregressiResponse salvaSollecito(SalvaSollecitoPregressiRequest salvaSollecitoPregressiRequest, UserDetails userDetails) {
		SollecitoVO sollecitoVO = salvaSollecitoPregressiRequest.getSollecito();
		NotificaVO notificaVO = salvaSollecitoPregressiRequest.getNotifica();
		
		if (sollecitoVO == null)
			throw new IllegalArgumentException("sollecitoVO = null");
		Integer idOrdinanzaVerbaleSoggetto = sollecitoVO.getIdSoggettoOrdinanza();
		if (idOrdinanzaVerbaleSoggetto == null)
			throw new IllegalArgumentException("idOrdinanzaVerbaleSoggetto = null");

		Integer idSollecito = sollecitoVO.getIdSollecito();
		Integer idVerbale = salvaSollecitoPregressiRequest.getIdVerbale();
		Integer idOrdinanza = salvaSollecitoPregressiRequest.getIdOrdinanza();
		
		CnmTSollecito cnmTSollecito;

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.findOne(idOrdinanzaVerbaleSoggetto);
		MessageVO confirmMsg = null;
		
		if (idSollecito == null) {
			
			// controllo verbale / ordinanza
			if (idVerbale == null)
				throw new IllegalArgumentException("idVerbale = null");
			if (idOrdinanza == null)
				throw new IllegalArgumentException("idOrdinanza = null");
			
			CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findOne(idVerbale);
			if (cnmTVerbale == null)
				throw new SecurityException("Verbale non trovato");			
			CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
			if (cnmTOrdinanza == null)
				throw new SecurityException("Ordinanza non trovata");
			
			
			cnmTSollecito = sollecitoEntityMapper.mapVOtoEntity(sollecitoVO);
			cnmTSollecito.setCnmTUser2(cnmTUser);
			cnmTSollecito.setDataOraInsert(now);
			cnmTSollecito.setCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);
			
			
//			20201202_ET per nuovi solleciti di default lo stato e' BOZZA
			CnmDStatoSollecito cnmDStatoSollecito = cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_SOLLECITO_BOZZA);
			cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecito);

			// 20210402_LC tipo sollecito
			CnmDTipoSollecito cnmDTipoSollecito = cnmDTipoSollecitoRepository.findOne(Constants.ID_TIPO_SOLLECITO_ORDINANZA);
			cnmTSollecito.setCnmDTipoSollecito(cnmDTipoSollecito);
					
			cnmTSollecito.setFlagDocumentoPregresso(true);
			cnmTSollecito = cnmTSollecitoRepository.save(cnmTSollecito);
			
			// salva allegato		-		cnmROrdinanzaVerbSog.getCnmTOrdinanza() / cnmTOrdinanza
			List<CnmTAllegato> salvaAllegatoProtocollatoOrdinanza = commonAllegatoService.salvaAllegatoProtocollatoOrdinanza(salvaSollecitoPregressiRequest, cnmTUser, cnmTOrdinanza, cnmTVerbale, true);
			CnmTAllegato cnmTAllegato = salvaAllegatoProtocollatoOrdinanza.get(0);
//			List<CnmTAllegato> salvaAllegatoProtocollatoVerbale = commonAllegatoService.salvaAllegatoProtocollatoVerbale(salvaSollecitoPregressiRequest, cnmTUser, cnmTVerbale, true);
//			CnmTAllegato cnmTAllegato = salvaAllegatoProtocollatoVerbale.get(0);
			
			// salva relazione CnmRAllegatoSollecito
			CnmRAllegatoSollecitoPK cnmRAllegatoSollecitoPK = new CnmRAllegatoSollecitoPK();
			cnmRAllegatoSollecitoPK.setIdAllegato(cnmTAllegato.getIdAllegato());
			cnmRAllegatoSollecitoPK.setIdSollecito(cnmTSollecito.getIdSollecito());

			CnmRAllegatoSollecito cnmRAllegatoSollecito = new CnmRAllegatoSollecito();
			cnmRAllegatoSollecito.setId(cnmRAllegatoSollecitoPK);
			cnmRAllegatoSollecito.setCnmTAllegato(cnmTAllegato);
			cnmRAllegatoSollecito.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
			cnmRAllegatoSollecito.setCnmTUser(cnmTUser);
			cnmRAllegatoSollecitoRepository.save(cnmRAllegatoSollecito);
			
			
			CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.CONFIRM_RECUPERO_PROTOCOLLO);
			if(cnmDMessaggio!=null) {
//				20201130_ET prendo il protocollo da salvaSollecitoPregressiRequest.getDocumentoProtocollato().getNumProtocollo() e se null da salvaSollecitoPregressiRequest.getDocumentoProtocollato().getNumProtocolloMaster()
				String protocollo = StringUtils.isNotBlank(salvaSollecitoPregressiRequest.getDocumentoProtocollato().getNumProtocollo())?salvaSollecitoPregressiRequest.getDocumentoProtocollato().getNumProtocollo():salvaSollecitoPregressiRequest.getDocumentoProtocollato().getNumProtocolloMaster();
				confirmMsg = new MessageVO(String.format(cnmDMessaggio.getDescMessaggio(), protocollo), cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
			}else {
				throw new SecurityException("Messaggio non trovato");
			}
			
		} else {
			
			cnmTSollecito = cnmTSollecitoRepository.findOne(idSollecito);
			cnmTSollecito = sollecitoEntityMapper.mapVOtoEntityUpdate(sollecitoVO, cnmTSollecito);
			cnmTSollecito.setCnmTUser1(cnmTUser);
			cnmTSollecito.setDataOraUpdate(now);
			cnmTSollecito.setCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);
			// stato sollecito scelto dall'utente (grestione pregresso)
			if(sollecitoVO.getStatoSollecito()!=null) {
				CnmDStatoSollecito cnmDStatoSollecito = cnmDStatoSollecitoRepository.findOne(sollecitoVO.getStatoSollecito().getId());
				cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecito);
			}
			cnmTSollecito = cnmTSollecitoRepository.save(cnmTSollecito);
			
			
		}
		
		
		//Marts
		//Save della notifca se presente	-	20210415 se != null
		if(notificaVO != null) {
			notificaVO.setIdSollecito(cnmTSollecito.getIdSollecito());
			notificaVO.setPregresso(true);
			notificaService.salvaNotifica(notificaVO, userDetails);
		}
		
		
		SalvaSollecitoPregressiResponse response = new SalvaSollecitoPregressiResponse();
		
		response.setIdSollecito(cnmTSollecito.getIdSollecito());		
		response.setConfirmMsg(confirmMsg);

		return response;

	}


	// modificata
	@Override
	public List<SollecitoVO> getSollecitiByIdOrdinanzaSoggetto(Integer idOrdinanzaVerbaleSoggetto) {
		if (idOrdinanzaVerbaleSoggetto == null)
			throw new IllegalArgumentException("id ordinanza soggetto = null");

		CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.findOne(idOrdinanzaVerbaleSoggetto);
		if (cnmROrdinanzaVerbSog == null)
			throw new SecurityException("id ordinanza soggetto = null");
		
		// 20210402_LC tipo sollecito
		CnmDTipoSollecito cnmDTipoSollecito = cnmDTipoSollecitoRepository.findOne(Constants.ID_TIPO_SOLLECITO_ORDINANZA);

		List<SollecitoVO> sollecitoVOList  = new ArrayList<>();
		List<CnmTSollecito> cnmTSollecitoList = cnmTSollecitoRepository.findByCnmROrdinanzaVerbSogAndCnmDTipoSollecito(cnmROrdinanzaVerbSog, cnmDTipoSollecito);		
		if (cnmTSollecitoList != null && !cnmTSollecitoList.isEmpty()) {
			for (CnmTSollecito soll : cnmTSollecitoList) {
				SollecitoVO sollecitoVO = sollecitoEntityMapper.mapEntityToVO(soll);
				
				sollecitoVO.setBollettinoDaCreare(false);
				
				sollecitoVOList.add(sollecitoVO);
			}
		}
		
		return sollecitoVOList;
	}
	
	// nuova
	@Override
	@Transactional
	public List<StatoSollecitoVO> getStatiSollecitoPregressi(Integer id) {
		
		List<Long> statiToExclude = new ArrayList<Long>(); 
//		statiToExclude.add(Constants.ID_STATO_PIANO_IN_DEFINIZIONE);
//		statiToExclude.add(Constants.ID_STATO_PIANO_IN_DEFINIZIONE);
		statiToExclude.add(Constants.ID_STATO_SOLLECITO_BOZZA);
		statiToExclude.add(Constants.ID_STATO_SOLLECITO_PROTOCOLLATO);
		if(id != null) {
			CnmTSollecito sollecito = cnmTSollecitoRepository.findOne(id);	
			if(sollecito != null) {
				statiToExclude.add(sollecito.getCnmDStatoSollecito().getIdStatoSollecito());	
				if(sollecito.getCnmDStatoSollecito().getIdStatoSollecito() == Constants.ID_STATO_SOLLECITO_BOZZA) {

					List<Long> statiToInclude = new ArrayList<Long>(); 
					statiToInclude.add(Constants.ID_STATO_SOLLECITO_NON_NOTIFICATO);
					statiToInclude.add(Constants.ID_STATO_SOLLECITO_NOTIFICATO);
					statiToInclude.add(Constants.ID_STATO_SOLLECITO_ESTINTO);
					statiToInclude.add(Constants.ID_STATO_SOLLECITO_PAGATO_ONLINE);
					statiToInclude.add(Constants.ID_STATO_SOLLECITO_PAGATO_OFFLINE);
					return statoSollecitoEntityMapper.mapListEntityToListVO((List<CnmDStatoSollecito>) cnmDStatoSollecitoRepository.findByIdStatoSollecitoIn(statiToInclude));
				}else if(sollecito.getCnmDStatoSollecito().getIdStatoSollecito() == Constants.ID_STATO_SOLLECITO_NON_NOTIFICATO
						|| sollecito.getCnmDStatoSollecito().getIdStatoSollecito() == Constants.ID_STATO_SOLLECITO_NOTIFICATO) {
					List<Long> statiToInclude = new ArrayList<Long>(); 
					statiToInclude.add(Constants.ID_STATO_SOLLECITO_ESTINTO);
					statiToInclude.add(Constants.ID_STATO_SOLLECITO_PAGATO_ONLINE);
					statiToInclude.add(Constants.ID_STATO_SOLLECITO_PAGATO_OFFLINE);
					return statoSollecitoEntityMapper.mapListEntityToListVO((List<CnmDStatoSollecito>) cnmDStatoSollecitoRepository.findByIdStatoSollecitoIn(statiToInclude));
				}
			}
		}
		// se sono in creazione torna solo BOZZA 
		List<Long> statiToInclude = new ArrayList<Long>(); 
		statiToInclude.add(Constants.ID_STATO_SOLLECITO_BOZZA);
		return statoSollecitoEntityMapper.mapListEntityToListVO((List<CnmDStatoSollecito>) cnmDStatoSollecitoRepository.findByIdStatoSollecitoIn(statiToInclude));
		
	}
	
	// modificata
	@Override
	public SollecitoVO getSollecitoById(Integer id) {
		SollecitoVO sollecitoVO = sollecitoEntityMapper.mapEntityToVO(utilsSollecito.validateAndGetCnmTSollecito(id));
		
		// 20201118_LC
		sollecitoVO.setBollettinoDaCreare(false);
		
		return sollecitoVO;
		//return updateBollettiniDaCreare(id, sollecitoVO);
	}

	

	// per fascicoli pregressi, disabilitare creazione bollettini se lettera è recuperata da Acta
	/*private SollecitoVO updateBollettiniDaCreare(Integer idSollecito, SollecitoVO sollecitoVO) {

		List<CnmRAllegatoSollecito> cnmRAllegatoSollecitoList = cnmRAllegatoSollecitoRepository.findByCnmTSollecito(utilsSollecito.validateAndGetCnmTSollecito(idSollecito));
		
		if (cnmRAllegatoSollecitoList != null && !cnmRAllegatoSollecitoList.isEmpty()) {
			
			for (CnmRAllegatoSollecito allSol : cnmRAllegatoSollecitoList) {

				if (allSol.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.LETTERA_SOLLECITO.getId() && allSol.getCnmTAllegato().isFlagRecuperatoPec())

					sollecitoVO.setBollettinoDaCreare(false);
				 	return sollecitoVO;
			}
			
		}

		 return sollecitoVO;
	}*/

	// nuova
	@Override
	public List<SollecitoVO> getSollecitiByOrdinanza(Integer id) {
		
		List<SollecitoVO> sollecitoVOList = new ArrayList<SollecitoVO>();
		
		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(id); 
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);

		// 20210402_LC tipo sollecito
		CnmDTipoSollecito cnmDTipoSollecito = cnmDTipoSollecitoRepository.findOne(Constants.ID_TIPO_SOLLECITO_ORDINANZA);		
		List<CnmTSollecito> cnmTSollecitoList = cnmTSollecitoRepository.findByCnmROrdinanzaVerbSogInAndCnmDTipoSollecito(cnmROrdinanzaVerbSog, cnmDTipoSollecito);
		
		HashMap <Integer, Integer> sollecitiAdded = new HashMap<Integer, Integer>();
		for(CnmTSollecito cnmTSollecito : cnmTSollecitoList) {
			if(!sollecitiAdded.containsValue(cnmTSollecito.getIdSollecito())) {
				SollecitoVO sollecitoVOToAdd = sollecitoEntityMapper.mapEntityToVO(cnmTSollecito);
				
				// 20201118_LC
				sollecitoVOToAdd.setBollettinoDaCreare(false);
				
				sollecitoVOList.add(sollecitoVOToAdd);
//				sollecitoVOList.add(updateBollettiniDaCreare(cnmTSollecito.getIdSollecito(), sollecitoVOToAdd));	// disabilita creazione bollettini se lettera da Acta
				sollecitiAdded.put(cnmTSollecito.getIdSollecito(), cnmTSollecito.getIdSollecito());
			}
		}
		
		return sollecitoVOList;
	}

	
	
	
	// nuova
	@Override
	@Transactional
	public SalvaSollecitoPregressiResponse checkSalvaSollecito(SalvaSollecitoPregressiRequest salvaSollecitoPregressiRequest, UserDetails userDetails) {
		SollecitoVO sollecitoVO = salvaSollecitoPregressiRequest.getSollecito();
		//NotificaVO notificaVO = salvaSollecitoPregressiRequest.getNotifica();
		
		if (sollecitoVO == null)
			throw new IllegalArgumentException("sollecitoVO = null");
		Integer idOrdinanzaVerbaleSoggetto = sollecitoVO.getIdSoggettoOrdinanza();
		if (idOrdinanzaVerbaleSoggetto == null)
			throw new IllegalArgumentException("idOrdinanzaVerbaleSoggetto = null");

		SalvaSollecitoPregressiResponse response = new SalvaSollecitoPregressiResponse();	
		response.setResult("OK");
		
		// 20201120_LC controllo su campi importoPagato e dataPagamento
		if(sollecitoVO.getIdSollecito() == null) {
			return response;
		}

		CnmDStatoSollecito cnmDStatoSollecitoSelected = cnmDStatoSollecitoRepository.findOne(sollecitoVO.getStatoSollecito().getId());		
		
		if ( (cnmDStatoSollecitoSelected.getIdStatoSollecito() == Constants.ID_STATO_SOLLECITO_PAGATO_OFFLINE || cnmDStatoSollecitoSelected.getIdStatoSollecito() == Constants.ID_STATO_SOLLECITO_PAGATO_ONLINE) 
				&& 	(sollecitoVO.getImportoPagato() == null || sollecitoVO.getDataPagamento() == null)) {

				// check esito negativo, torna msg SOLLPAGIMPDAT e result KO
				response.setResult("KO");				
				CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.SOLLECITO_PAGATO_SENZA_IMPORTO_DATA);
				if(cnmDMessaggio!=null) {		
					String msg = String.format(cnmDMessaggio.getDescMessaggio(), cnmDStatoSollecitoSelected.getDescStatoSollecito());
					response.setConfirmMsg(new MessageVO(msg, cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio()));
				}else {
					throw new SecurityException("Messaggio non trovato");
				}				
		
		} 

		// ha solo result ed (eventualmente) il msg
		return response;

	}



}
