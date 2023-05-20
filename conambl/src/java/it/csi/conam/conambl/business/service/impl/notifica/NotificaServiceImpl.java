/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.notifica;

import it.csi.conam.conambl.business.service.notifica.NotificaService;
import it.csi.conam.conambl.business.service.ordinanza.OrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.UtilsOrdinanza;
import it.csi.conam.conambl.business.service.pianorateizzazione.UtilsPianoRateizzazioneService;
import it.csi.conam.conambl.business.service.sollecito.UtilsSollecitoService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.ModalitaNotificaEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.NotificaEntityMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.request.notifica.NotificaRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.notifica.ModalitaNotificaVO;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 21 feb 2019
 */
@Service
public class NotificaServiceImpl implements NotificaService {

	@Autowired
	private CnmTNotificaRepository cnmTNotificaRepository;
	@Autowired
	private CnmDModalitaNotificaRepository cnmDModalitaNotificaRepository;
	@Autowired
	private ModalitaNotificaEntityMapper modalitaNotificaEntityMapper;
	@Autowired
	private UtilsOrdinanza utilsOrdinanza;
	@Autowired
	private UtilsPianoRateizzazioneService utilsPianoRateizzazioneService;
	@Autowired
	private UtilsSollecitoService utilsSollecitoService;
	@Autowired
	private OrdinanzaService ordinanzaService;

	@Autowired
	private NotificaEntityMapper notificaEntityMapper;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmDStatoOrdinanzaRepository cnmDStatoOrdinanzaRepository;
	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CnmTPianoRateRepository cnmTPianoRateRepository;
	@Autowired
	private CnmDStatoPianoRateRepository cnmDStatoPianoRateRepository;
	@Autowired
	private CnmTSollecitoRepository cnmTSollecitoRepository;
	@Autowired
	private CnmDStatoSollecitoRepository cnmDStatoSollecitoRepository;

	@Override
	public List<ModalitaNotificaVO> getModalitaNotifica() {
		return modalitaNotificaEntityMapper.mapListEntityToListVO((List<CnmDModalitaNotifica>) cnmDModalitaNotificaRepository.findAll());
	}

	@Override
	@Transactional
	public Integer salvaNotifica(NotificaVO notifica, UserDetails user) {
		if (notifica == null)
			throw new IllegalArgumentException("notifica = null");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
		Integer idOrdinanza = notifica.getIdOrdinanza();
		Integer idPiano = notifica.getIdPiano();
		Integer idSollecito = notifica.getIdSollecito();

		
		/* JIRA - Gestione Notifica
		if (notifica.getDataNotifica() != null && notifica.getDataSpedizione().isAfter(notifica.getDataNotifica())) {
			throw new BusinessException(ErrorCode.NOTIFICA_DATA_SPEDIZIONE_MAGGIORE_DATA_NOTIFICA);
		}
		*/

		Integer result;
		if (idOrdinanza != null)
			result = createNotificaOrdinanza(notifica, cnmTUser);
		else if (idPiano != null)
			result = createNotificaPiano(notifica, cnmTUser);
		else if (idSollecito != null)
			result = createNotificaSollecito(notifica, cnmTUser);
		else
			throw new IllegalArgumentException("idPiano-idOrdinanza = null");

		return result;
	}

	@Override
	public NotificaVO getNotificaBy(NotificaRequest notificaRequest) {
		if (notificaRequest == null)
			throw new IllegalArgumentException("notificaRequest = null");

		Integer idOrdinanza = notificaRequest.getIdOrdinanza();
		Integer idPiano = notificaRequest.getIdPiano();
		Integer idSollecito = notificaRequest.getIdSollecito();

		List<CnmTNotifica> cnmTNotificaList = new ArrayList<CnmTNotifica>();;
		if (idOrdinanza != null) {
			CnmTOrdinanza cnmTOrdinanza = utilsOrdinanza.validateAndGetCnmTOrdinanza(idOrdinanza);
			cnmTNotificaList = cnmTNotificaRepository.findByCnmTOrdinanzas(cnmTOrdinanza);
		} else if (idPiano != null) {
			CnmTPianoRate cnmTPianoRate = utilsPianoRateizzazioneService.validateAndGetCnmTPianoRate(idPiano);
			cnmTNotificaList = cnmTNotificaRepository.findByCnmTPianoRates(cnmTPianoRate);
		} else if (idSollecito != null) {
			CnmTSollecito cnmTSollecito = utilsSollecitoService.validateAndGetCnmTSollecito(idSollecito);
			cnmTNotificaList = cnmTNotificaRepository.findByCnmTSollecitos(cnmTSollecito);
		}
		/*JIRA - Gestione Notifica
		else
			throw new IllegalArgumentException("idPiano-idOrdinanza = null");
		*/

		if (cnmTNotificaList.size() > 1)
			throw new RuntimeException("Sembra che esista più di una notifica ");
		
		if(cnmTNotificaList.isEmpty()) {
			return null;
		}else {
			return notificaEntityMapper.mapEntityToVO(cnmTNotificaList.get(0));
		}		
	}

	@Override
	public Boolean isNotificaCreata(CnmTOrdinanza cnmTOrdinanza) {
		List<CnmTNotifica> cnmTNotificaList = cnmTNotificaRepository.findByCnmTOrdinanzas(cnmTOrdinanza);
		if (!cnmTNotificaList.isEmpty())
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	@Override
	public Boolean isNotificaCreata(CnmTPianoRate cnmTPianoRate) {
		List<CnmTNotifica> cnmTNotificaList = cnmTNotificaRepository.findByCnmTPianoRates(cnmTPianoRate);
		if (!cnmTNotificaList.isEmpty())
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	@Override
	public Boolean isNotificaCreata(CnmTSollecito cnmTSollecito) {
		List<CnmTNotifica> cnmTNotificaList = cnmTNotificaRepository.findByCnmTSollecitos(cnmTSollecito);
		if (!cnmTNotificaList.isEmpty())
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	private Integer createNotificaPiano(NotificaVO notifica, CnmTUser cnmTUser) {
		CnmTPianoRate cnmTPianoRate = utilsPianoRateizzazioneService.validateAndGetCnmTPianoRate(notifica.getIdPiano());

		/* JIRA - Gestione Notifica
		if (isNotificaCreata(cnmTPianoRate))
			throw new SecurityException("notifica gia esistente ");
		*/
		CnmTNotifica cnmTNotifica = saveNotifica(notifica, cnmTUser);
		
		//JIRA - Gestione Notifica
		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//old code
		/*
		if (notifica.getDataNotifica() == null)
			cnmTPianoRate.setCnmDStatoPianoRate(cnmDStatoPianoRateRepository.findOne(Constants.ID_STATO_PIANO_NON_NOTIFICATO));
		else
			cnmTPianoRate.setCnmDStatoPianoRate(cnmDStatoPianoRateRepository.findOne(Constants.ID_STATO_PIANO_NOTIFICATO));
		*/
		if(!notifica.isPregresso() && (
		   cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() == Constants.ID_STATO_PIANO_IN_DEFINIZIONE ||
		   cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() == Constants.ID_STATO_PIANO_CONSOLIDATO ||
		   cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() == Constants.ID_STATO_PIANO_NON_NOTIFICATO ||
		   cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() == Constants.ID_STATO_PIANO_NOTIFICATO ||
		   cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() == Constants.ID_STATO_PIANO_PROTOCOLLATO)
		) {
				
			if(notifica!=null && notifica.modalita!=null && notifica.modalita.getId()==null) {
				notifica.modalita = null;
			}
			if(notifica.modalita==null) {
				// 20210415_LC stato invariato
				//cnmTPianoRate.setCnmDStatoPianoRate(cnmDStatoPianoRateRepository.findOne(Constants.ID_STATO_PIANO_NON_NOTIFICATO));
			} else if(notifica.modalita.getId().equals(Constants.ID_COMPIUTA_GIACENZA) || notifica.modalita.getId().equals(Constants.ID_CONSEGNA_A_MANI_PROPRIE)){
				cnmTPianoRate.setCnmDStatoPianoRate(cnmDStatoPianoRateRepository.findOne(Constants.ID_STATO_PIANO_NOTIFICATO));
			}else {
				cnmTPianoRate.setCnmDStatoPianoRate(cnmDStatoPianoRateRepository.findOne(Constants.ID_STATO_PIANO_NON_NOTIFICATO));
			}
					
		}
		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		List<CnmTNotifica> cnmTNotificaList = new ArrayList<>();
		cnmTNotificaList.add(cnmTNotifica);
		cnmTPianoRate.setCnmTNotificas(cnmTNotificaList);
		cnmTPianoRateRepository.save(cnmTPianoRate);

		return cnmTNotifica.getIdNotifica();
	}

	private Integer createNotificaSollecito(NotificaVO notifica, CnmTUser cnmTUser) {
		CnmTSollecito cnmTSollecito = utilsSollecitoService.validateAndGetCnmTSollecito(notifica.getIdSollecito());

		/* JIRA - Gestione Notifica
		if (isNotificaCreata(cnmTSollecito))
			throw new SecurityException("notifica gia esistente ");
		*/
		CnmTNotifica cnmTNotifica = saveNotifica(notifica, cnmTUser);
		
		//JIRA - Gestione Notifica
		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//old code
		/*
		if (notifica.getDataNotifica() == null)
			cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_SOLLECITO_NON_NOTIFICATO));
		else
			cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_SOLLECITO_NOTIFICATO));
		*/
		if(!notifica.isPregresso() && 
		   (cnmTSollecito.getCnmDStatoSollecito().getIdStatoSollecito() == Constants.ID_STATO_SOLLECITO_BOZZA || 
		   cnmTSollecito.getCnmDStatoSollecito().getIdStatoSollecito() == Constants.ID_STATO_SOLLECITO_NON_NOTIFICATO ||
		   cnmTSollecito.getCnmDStatoSollecito().getIdStatoSollecito() == Constants.ID_STATO_SOLLECITO_NOTIFICATO ||
		   cnmTSollecito.getCnmDStatoSollecito().getIdStatoSollecito() == Constants.ID_STATO_SOLLECITO_PROTOCOLLATO)) 
		{
			if(notifica!=null && notifica.modalita!=null && notifica.modalita.getId()==null) {
				notifica.modalita = null;
			}
			if(notifica.modalita==null) {
				// 20210415_LC stato invariato
				//cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_SOLLECITO_NON_NOTIFICATO));
			} else if(notifica.modalita.getId().equals(Constants.ID_COMPIUTA_GIACENZA) || notifica.modalita.getId().equals(Constants.ID_CONSEGNA_A_MANI_PROPRIE)){
				cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_SOLLECITO_NOTIFICATO));
			} else {
				cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_SOLLECITO_NON_NOTIFICATO));
			}			
		}
		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		List<CnmTNotifica> cnmTNotificaList = new ArrayList<>();
		cnmTNotificaList.add(cnmTNotifica);
		cnmTSollecito.setCnmTNotificas(cnmTNotificaList);
		cnmTSollecitoRepository.save(cnmTSollecito);

		return cnmTNotifica.getIdNotifica();
	}

	private Integer createNotificaOrdinanza(NotificaVO notifica, CnmTUser cnmTUser) {
		CnmTOrdinanza cnmTOrdinanza = utilsOrdinanza.validateAndGetCnmTOrdinanza(notifica.getIdOrdinanza());
		
		/*JIRA - Gestione Notifica
		if (isNotificaCreata(cnmTOrdinanza))
			throw new SecurityException("notifica gia esistente ");
		*/

		CnmTNotifica cnmTNotifica = saveNotifica(notifica, cnmTUser);			
		
		//JIRA - Gestione Notifica
		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//old code
		/*
		ordinanzaService.saveSStatoOrdinanza(cnmTOrdinanza, cnmTUser);

		if (notifica.getDataNotifica() == null)
			cnmTOrdinanza.setCnmDStatoOrdinanza(cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_NON_NOTIFICATA));
		else
			cnmTOrdinanza.setCnmDStatoOrdinanza(cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_NOTIFICATA));
		*/
		if(!notifica.isPregresso() &&
		   (cnmTOrdinanza.getCnmDStatoOrdinanza().getIdStatoOrdinanza()==Constants.ID_STATO_ORDINANZA_IN_ATTESA_DI_NOTIFICA ||
		   cnmTOrdinanza.getCnmDStatoOrdinanza().getIdStatoOrdinanza()==Constants.ID_STATO_ORDINANZA_NOTIFICATA ||
		   cnmTOrdinanza.getCnmDStatoOrdinanza().getIdStatoOrdinanza()==Constants.ID_STATO_ORDINANZA_NON_NOTIFICATA)
		){
			
			if(notifica!=null && notifica.modalita!=null && notifica.modalita.getId()==null) {
				notifica.modalita = null;
			}
			if(notifica.modalita==null) {
				// 20210415_LC stato invariato
				//cnmTOrdinanza.setCnmDStatoOrdinanza(cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_NON_NOTIFICATA));
			} else if(notifica.modalita.getId().equals(Constants.ID_COMPIUTA_GIACENZA) || notifica.modalita.getId().equals(Constants.ID_CONSEGNA_A_MANI_PROPRIE)) {
				cnmTOrdinanza.setCnmDStatoOrdinanza(cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_NOTIFICATA));
			} else {
				cnmTOrdinanza.setCnmDStatoOrdinanza(cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_NON_NOTIFICATA));
			}			
			ordinanzaService.saveSStatoOrdinanza(cnmTOrdinanza, cnmTUser);			
		}
		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		cnmTOrdinanza.setCnmTUser1(cnmTUser);
		cnmTOrdinanza.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));

		List<CnmTNotifica> cnmTNotificaList = new ArrayList<>();
		cnmTNotificaList.add(cnmTNotifica);
		cnmTOrdinanza.setCnmTNotificas(cnmTNotificaList);
		cnmTOrdinanzaRepository.save(cnmTOrdinanza);

		return cnmTNotifica.getIdNotifica();
	}

	private CnmTNotifica saveNotifica(NotificaVO notifica, CnmTUser cnmTUser) {		
		/*
		 * CONTROLLO NON PIU' NECESSARIO A VALLE DELLA RICHIESTA BERTINETTI 28/01/2020
		 * Nessun campo della notifca deve essere obbligatorio
		 * ********************************************************************
		CnmTNotifica cnmTNotifica = cnmTNotificaRepository.findByNumeroRaccomandata(notifica.getNumeroRaccomandata());  
		if (cnmTNotifica != null)
			throw new BusinessException(ErrorCode.NOTIFICA_NUMERO_RACCOMANDATA_GIA_PRESENTE);
		*/
		CnmTNotifica cnmTNotifica = new CnmTNotifica();
		cnmTNotifica = notificaEntityMapper.mapVOtoEntity(notifica);
		cnmTNotifica.setCnmTUser2(cnmTUser);
		cnmTNotifica.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
		return cnmTNotificaRepository.save(cnmTNotifica);
	}
}
