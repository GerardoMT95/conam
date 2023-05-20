/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.pianorateizzazione;

import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.notifica.NotificaService;
import it.csi.conam.conambl.business.service.ordinanza.UtilsOrdinanza;
import it.csi.conam.conambl.business.service.pianorateizzazione.PianoRateizzazionePregressiService;
import it.csi.conam.conambl.business.service.pianorateizzazione.UtilsPianoRateizzazioneService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.PianoRateizzazioneEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.RataEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoPianoRateEntityMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.request.pianorateizzazione.SalvaPianoPregressiRequest;
import it.csi.conam.conambl.response.SalvaPianoPregressiResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import it.csi.conam.conambl.vo.pianorateizzazione.PianoRateizzazioneVO;
import it.csi.conam.conambl.vo.pianorateizzazione.RataVO;
import it.csi.conam.conambl.vo.pianorateizzazione.StatoPianoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PianoRateizzazionePregressiServiceImpl implements PianoRateizzazionePregressiService {

	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmDStatoPianoRateRepository cnmDStatoPianoRateRepository;
	@Autowired
	private StatoPianoRateEntityMapper statoPianoRateEntityMapper;
	@Autowired
	private PianoRateizzazioneEntityMapper pianoRateizzazioneEntityMapper;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CnmTPianoRateRepository cnmTPianoRateRepository;
	@Autowired
	private NotificaService motificaService;
	@Autowired
	private CommonAllegatoService commonAllegatoService;
	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;
	@Autowired
	private CnmRAllegatoPianoRateRepository cnmRAllegatoPianoRateRepository;
	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;
	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private RataEntityMapper rataEntityMapper;
	@Autowired
	private CnmTRataRepository cnmTRataRepository;
	@Autowired
	private CnmDStatoRataRepository cnmDStatoRataRepository;
	
	@Autowired
	private UtilsPianoRateizzazioneService utilsPianoRateizzazione;
	@Autowired
	private CnmRAllegatoOrdVerbSogRepository cnmRAllegatoOrdVerbSogRepository;
	@Autowired
	private UtilsOrdinanza utilsOrdinanza;
	
	@Override
	@Transactional
	public SalvaPianoPregressiResponse salvaPiano(SalvaPianoPregressiRequest pianoRequest, UserDetails userDetails) {
		PianoRateizzazioneVO piano = pianoRequest.getPiano();
		Integer idVerbale = pianoRequest.getIdVerbale();
		Integer idOrdinanza = pianoRequest.getIdOrdinanza();
		
		if (piano == null)
			throw new IllegalArgumentException("piano = null");
		if (piano.getSoggetti() == null)
			throw new IllegalArgumentException("soggetti = null");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

		Integer idPianoRata = piano.getId();
		CnmTPianoRate cnmTPianoRate;
		List<CnmRSoggRata> cnmRSoggRataList;
		List<CnmTRata> cnmTRataList;

		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList;
		if (idPianoRata == null) {
			
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
			
			
			// controllo soggetti
			List<Integer> idOrdinanzaVerbaleSoggettoList = new ArrayList<>();
			for (SoggettoVO sog : piano.getSoggetti()) {
				idOrdinanzaVerbaleSoggettoList.add(sog.getIdSoggettoOrdinanza());
			}
			cnmROrdinanzaVerbSogList = (List<CnmROrdinanzaVerbSog>) cnmROrdinanzaVerbSogRepository.findAll(idOrdinanzaVerbaleSoggettoList);
			if (cnmROrdinanzaVerbSogList == null || cnmROrdinanzaVerbSogList.isEmpty())
				throw new IllegalArgumentException("cnmROrdinanzaVerbSoggetti==null");

			// Se esiste almeno una rata per i soggetti
			cnmRSoggRataList = cnmRSoggRataRepository.findByCnmROrdinanzaVerbSogIn(cnmROrdinanzaVerbSogList);
			if (cnmRSoggRataList != null && !cnmRSoggRataList.isEmpty())
				throw new SecurityException("Piano esistente per uno dei soggetti ricercati");


			cnmTPianoRate = pianoRateizzazioneEntityMapper.mapVOtoEntity(piano);
			// setto lo stato selezionato dall'utente
			cnmTPianoRate.setCnmDStatoPianoRate(cnmDStatoPianoRateRepository.findOne(Constants.ID_STATO_PIANO_CONSOLIDATO));
			cnmTPianoRate.setCnmTUser2(cnmTUser);
			cnmTPianoRate.setDataOraInsert(now);
			cnmTPianoRate.setFlagDocumentoPregresso(true);
			cnmTPianoRate = cnmTPianoRateRepository.save(cnmTPianoRate);

			// parte comune a modifica e inserimento
			cnmTRataList = new ArrayList<>();
			for (RataVO rata : piano.getRate()) {
				CnmTRata cnmTRate = rataEntityMapper.mapVOtoEntity(rata);
				cnmTRate.setCnmTUser2(cnmTUser);
				cnmTRate.setDataOraInsert(now);
				cnmTRate.setCnmTPianoRate(cnmTPianoRate);
				cnmTRataList.add(cnmTRate);
			}
			cnmTRataList = cnmTRataRepository.save(cnmTRataList);
			
			// salva allegato 
			List<CnmTAllegato> salvaAllegatoProtocollatoOrdinanza = commonAllegatoService.salvaAllegatoProtocollatoOrdinanza(pianoRequest, cnmTUser, cnmTOrdinanza, cnmTVerbale, true);
			CnmTAllegato cnmTAllegato = salvaAllegatoProtocollatoOrdinanza.get(0);
//			List<CnmTAllegato> salvaAllegatoProtocollatoVerbale = commonAllegatoService.salvaAllegatoProtocollatoVerbale(pianoRequest, cnmTUser, cnmTVerbale, true);
//			CnmTAllegato cnmTAllegato = salvaAllegatoProtocollatoVerbale.get(0);

			// salva relazione CnmRAllegatoPianoRatePK
			CnmRAllegatoPianoRatePK cnmRAllegatoPianoRatePK = new CnmRAllegatoPianoRatePK();
			cnmRAllegatoPianoRatePK.setIdAllegato(cnmTAllegato.getIdAllegato());
			cnmRAllegatoPianoRatePK.setIdPianoRate(cnmTPianoRate.getIdPianoRate());

			CnmRAllegatoPianoRate cnmRAllegatoPianoRate = new CnmRAllegatoPianoRate();
			cnmRAllegatoPianoRate.setId(cnmRAllegatoPianoRatePK);
			cnmRAllegatoPianoRate.setCnmTAllegato(cnmTAllegato);
			cnmRAllegatoPianoRate.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
			cnmRAllegatoPianoRate.setCnmTUser(cnmTUser);
			cnmRAllegatoPianoRateRepository.save(cnmRAllegatoPianoRate);

			for(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog:cnmROrdinanzaVerbSogList) {
				CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog = new CnmRAllegatoOrdVerbSog();
				CnmRAllegatoOrdVerbSogPK cnmRAllegatoOrdVerbSogPK = new CnmRAllegatoOrdVerbSogPK();
				cnmRAllegatoOrdVerbSogPK.setIdAllegato(cnmTAllegato.getIdAllegato());
				cnmRAllegatoOrdVerbSogPK.setIdOrdinanzaVerbSog(cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog());
				cnmRAllegatoOrdVerbSog.setCnmTUser(cnmTUser);
				cnmRAllegatoOrdVerbSog.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
				cnmRAllegatoOrdVerbSog.setId(cnmRAllegatoOrdVerbSogPK);
				cnmRAllegatoOrdVerbSogRepository.save(cnmRAllegatoOrdVerbSog);
			}

		} else {
			cnmTPianoRate = cnmTPianoRateRepository.findOne(idPianoRata);
			if (cnmTPianoRate == null)
				throw new IllegalArgumentException("id non valido ");
//			if (cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() != Constants.ID_STATO_PIANO_IN_DEFINIZIONE)
//				throw new SecurityException("piano in stato non valido ");

			pianoRateizzazioneEntityMapper.mapVOtoEntityUpdate(piano, cnmTPianoRate);
			// setto lo stato selezionato dall'utente
			cnmTPianoRate.setCnmDStatoPianoRate(cnmDStatoPianoRateRepository.findOne(piano.getStato().getId()));
			cnmTPianoRate.setCnmTUser1(cnmTUser);
			cnmTPianoRate.setDataOraUpdate(now);
			cnmTPianoRate = cnmTPianoRateRepository.save(cnmTPianoRate);

			// cerco le rate associate al piano
			cnmTRataList = cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate);
//
//			// cerco i soggetti
			cnmROrdinanzaVerbSogList = cnmRSoggRataRepository.findDistinctCnmROrdinanzaVerbSogByCnmTRataIn(cnmTRataList);
//
//			// elimino i soggetti dalla r rata
			cnmRSoggRataList = cnmRSoggRataRepository.findByCnmTRataIn(cnmTRataList);
			cnmRSoggRataRepository.delete(cnmRSoggRataList);

			List<RataVO> rateRicalcolate = piano.getRate();
			int differenza = rateRicalcolate.size() - cnmTRataList.size();

			List<CnmTRata> cnmTRataListDelete = new ArrayList<>();
			if (differenza > 0) {
				for (int i = 0; i < differenza; i++) {
					cnmTRataList.add(new CnmTRata());
				}
			} else if (differenza < 0) {
				int index = rateRicalcolate.size() - 1;
				for (int i = index; i > index + differenza; i--) {
					cnmTRataListDelete.add(cnmTRataList.get(i));
					cnmTRataList.remove(cnmTRataList.get(i));
				}
			}

			// elimino le rate obsolete
			cnmTRataRepository.delete(cnmTRataListDelete);
			cnmTRataRepository.flush();

			List<CnmTRata> cnmTRataUpdate = new ArrayList<>();
			for (int i = 0; i < rateRicalcolate.size(); i++) {
				CnmTRata cnmTRate = rataEntityMapper.mapVOtoEntityUpdate(rateRicalcolate.get(i), cnmTRataList.get(i));
				if (cnmTRate.getIdRata() == null) {
					cnmTRate.setCnmTUser2(cnmTUser);
					cnmTRate.setDataOraInsert(now);
				} else {
					cnmTRate.setCnmTUser1(cnmTUser);
					cnmTRate.setDataOraUpdate(now);
				}
				cnmTRate.setCnmTPianoRate(cnmTPianoRate);
				cnmTRataUpdate.add(cnmTRate);
			}

			cnmTRataList = cnmTRataRepository.save(cnmTRataUpdate);
			cnmRSoggRataRepository.flush();

		}
		
		
		// parte comune a modifica e inserimento
		// Le rate per il pregresso non vengono inserite
		cnmRSoggRataList = new ArrayList<>();
		for (CnmROrdinanzaVerbSog sog : cnmROrdinanzaVerbSogList) {
			for (CnmTRata cnmTRata : cnmTRataList) {
				CnmRSoggRata cnmRSoggRata = new CnmRSoggRata();

				CnmRSoggRataPK cnmRSoggRataPK = new CnmRSoggRataPK();
				cnmRSoggRataPK.setIdOrdinanzaVerbSog(sog.getIdOrdinanzaVerbSog());
				cnmRSoggRataPK.setIdRata(cnmTRata.getIdRata());

				cnmRSoggRata.setId(cnmRSoggRataPK);
				cnmRSoggRata.setCnmDStatoRata(cnmDStatoRataRepository.findOne(Constants.ID_STATO_RATA_NON_PAGATO));
				cnmRSoggRata.setCnmTUser1(cnmTUser);
				cnmRSoggRata.setDataOraInsert(now);
				cnmRSoggRataList.add(cnmRSoggRata);
			}
		}
		cnmRSoggRataRepository.save(cnmRSoggRataList);
				
		//Save della notifca se presente
		if(piano.getImportoSpeseNotifica()!=null) {
			NotificaVO notifica = new NotificaVO();
			notifica.setIdPiano(cnmTPianoRate.getIdPianoRate());
			notifica.setImportoSpeseNotifica(piano.getImportoSpeseNotifica());
			notifica.setPregresso(true);
			motificaService.salvaNotifica(notifica, userDetails);
		}

		SalvaPianoPregressiResponse response = new SalvaPianoPregressiResponse();
		
		response.setIdPiano(cnmTPianoRate.getIdPianoRate());		
		CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.CONFIRM_RECUPERO_PROTOCOLLO);
		if(cnmDMessaggio!=null) {
			String msg = String.format(cnmDMessaggio.getDescMessaggio(), pianoRequest.getDocumentoProtocollato()!=null?pianoRequest.getDocumentoProtocollato().getNumProtocollo():"");
			response.setConfirmMsg(new MessageVO(msg, cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio()));
		}else {
			throw new SecurityException("Messaggio non trovato");
		}

		return response;

	}

	@Override
	public List<StatoPianoVO> getStatiPianoRateizzazionePregressi(Integer id) {

		List<Long> statiToExclude = new ArrayList<Long>(); 
		statiToExclude.add(Constants.ID_STATO_PIANO_IN_DEFINIZIONE);
		statiToExclude.add(Constants.ID_STATO_PIANO_CONSOLIDATO);
		statiToExclude.add(Constants.ID_STATO_PIANO_PROTOCOLLATO);
		if(id != null) {
			CnmTPianoRate piano = cnmTPianoRateRepository.findOne(id);	
			if(piano != null) {
				statiToExclude.add(piano.getCnmDStatoPianoRate().getIdStatoPianoRate());
				// Se lo stato e' ID_STATO_PIANO_NOTIFICATO o ID_STATO_PIANO_NON_NOTIFICATO, posso passare solo in ID_STATO_PIANO_ESTINTO
				if(piano.getCnmDStatoPianoRate().getIdStatoPianoRate() == Constants.ID_STATO_PIANO_NOTIFICATO
						|| piano.getCnmDStatoPianoRate().getIdStatoPianoRate() == Constants.ID_STATO_PIANO_NON_NOTIFICATO) {

					List<Long> statiToInclude = new ArrayList<Long>(); 
					statiToInclude.add(Constants.ID_STATO_PIANO_ESTINTO);
					return statoPianoRateEntityMapper.mapListEntityToListVO((List<CnmDStatoPianoRate>) cnmDStatoPianoRateRepository.findByIdStatoPianoRateIn(statiToInclude));
				}else if(piano.getCnmDStatoPianoRate().getIdStatoPianoRate() == Constants.ID_STATO_PIANO_ESTINTO) {
					return new ArrayList<StatoPianoVO>();
				}
			}
		}
		return statoPianoRateEntityMapper.mapListEntityToListVO((List<CnmDStatoPianoRate>) cnmDStatoPianoRateRepository.findByIdStatoPianoRateNotIn(statiToExclude));
	}

	@Override
	public List<PianoRateizzazioneVO> getPianiByOrdinanza(Integer id) {
		
		List<PianoRateizzazioneVO> pianoRateizzazioneVOList = new ArrayList<PianoRateizzazioneVO>();
		
		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(id); 
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		List<CnmRSoggRata> cnmRSoggRataList = cnmRSoggRataRepository.findByCnmROrdinanzaVerbSogIn(cnmROrdinanzaVerbSog);
		
		HashMap <Integer, Integer> pianiAdded = new HashMap<Integer, Integer>();
		for(CnmRSoggRata cnmRSoggRata : cnmRSoggRataList) {
			if(cnmRSoggRata.getCnmTRata() != null && cnmRSoggRata.getCnmTRata().getCnmTPianoRate()!= null 
					&& !pianiAdded.containsValue(cnmRSoggRata.getCnmTRata().getCnmTPianoRate().getIdPianoRate())) {
				PianoRateizzazioneVO pianoVOToAdd = pianoRateizzazioneEntityMapper.mapEntityToVO(cnmRSoggRata.getCnmTRata().getCnmTPianoRate());

				// 20201117 PP - per i pregressi non consento la compilazione della lettera e la creazione dei bollettini
				pianoVOToAdd.setIsLetteraCreata(true);
				pianoVOToAdd.setIsBollettiniEnable(false);
				
				pianoRateizzazioneVOList.add(pianoVOToAdd);
//				pianoRateizzazioneVOList.add(updateBollettiniDaCreare(cnmRSoggRata.getCnmTRata().getCnmTPianoRate().getIdPianoRate(), pianoVOToAdd));	// disabilita creazione bollettini se lettera da Acta
				pianiAdded.put(cnmRSoggRata.getCnmTRata().getCnmTPianoRate().getIdPianoRate(), cnmRSoggRata.getCnmTRata().getCnmTPianoRate().getIdPianoRate());
			}
		}
		
		return pianoRateizzazioneVOList;
	}

	
	
	@Override
	public PianoRateizzazioneVO dettaglioPianoById(Integer idPiano, Boolean flagModifica, UserDetails userDetails) {
		CnmTPianoRate cnmTPianoRate = utilsPianoRateizzazione.validateAndGetCnmTPianoRate(idPiano);
		if (flagModifica == null)
			throw new IllegalArgumentException("flagModifica non valorizzato");

		if (flagModifica && cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() != Constants.ID_STATO_PIANO_IN_DEFINIZIONE 
							&& cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() != Constants.ID_STATO_PIANO_NON_NOTIFICATO )
			throw new SecurityException("non si può modificare il piano in questo stato");
		PianoRateizzazioneVO pianoVO = pianoRateizzazioneEntityMapper.mapEntityToVO(cnmTPianoRate);
		
		// 20201117 PP - per i pregressi non consento la compilazione della lettera e la creazione dei bollettini
		pianoVO.setIsLetteraCreata(true);
		pianoVO.setIsBollettiniEnable(false);

		return pianoVO;
//		return updateBollettiniDaCreare(idPiano, pianoVO);
	}
	
	// per fascicoli pregressi, disabilitare creazione bollettini se lettera è recuperata da Acta
	/*private PianoRateizzazioneVO updateBollettiniDaCreare(Integer idPiano, PianoRateizzazioneVO pianoVO) {

		List<CnmRAllegatoPianoRate> cnmRAllegatoPianoList = cnmRAllegatoPianoRateRepository.findByCnmTPianoRate(utilsPianoRateizzazione.validateAndGetCnmTPianoRate(idPiano));
		
		if (cnmRAllegatoPianoList != null && !cnmRAllegatoPianoList.isEmpty()) {
			
			for (CnmRAllegatoPianoRate allPia : cnmRAllegatoPianoList) {

				if (allPia.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.LETTERA_RATEIZZAZIONE.getId() && allPia.getCnmTAllegato().isFlagRecuperatoPec())

					pianoVO.setIsBollettiniEnable(false);
				 	return pianoVO;
			}
			
		}

		 return pianoVO;
	}*/
	
	
}
