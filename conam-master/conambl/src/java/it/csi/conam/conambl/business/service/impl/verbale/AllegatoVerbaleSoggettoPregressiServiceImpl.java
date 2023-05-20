/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleSoggettoPregressiService;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleSoggettoService;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.repositories.CnmDMessaggioRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleSoggettoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiSoggettiRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.common.MessageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 
 * @author A135722
 *
 */
@Service
public class AllegatoVerbaleSoggettoPregressiServiceImpl implements AllegatoVerbaleSoggettoPregressiService {
	
	@Autowired
	private CommonAllegatoService commonAllegatoService;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmRAllegatoVerbSogRepository cnmRAllegatoVerbSogRepository;
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	/*@Autowired
	private UtilsDoqui utilsDoqui;*/
//	@Autowired
//	private CnmDTipoAllegatoRepository cnmDTipoAllegatoRepository;
//	@Autowired
//	private CnmTAllegatoRepository cnmTAllegatoRepository;
	@Autowired
	private AllegatoVerbaleSoggettoService allegatoVerbaleSoggettoService;
	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;
	
	@Override
	@Transactional
	public MessageVO salvaVerbaleAudizione(SalvaAllegatiProtocollatiSoggettiRequest request, UserDetails user) {
		
		if (request == null)
			throw new IllegalArgumentException("request==null");
		List<Integer> idVerbaleSoggettoList = request.getIdVerbaleSoggettoList();
		if (idVerbaleSoggettoList  == null)
			throw new IllegalArgumentException("idVerbaleSoggettoList ==null");
		
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = (List<CnmRVerbaleSoggetto>) cnmRVerbaleSoggettoRepository.findAll(idVerbaleSoggettoList);
		if (cnmRVerbaleSoggettoList == null)
			throw new IllegalArgumentException("cnmTOrdinanza ==null");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
//		List<CnmTAllegato> allegatos = new ArrayList<>();
		CnmTAllegato cnmTAllegato = null;
		for (CnmRVerbaleSoggetto cnmRVerbaleSoggetto : cnmRVerbaleSoggettoList) {
			if (allegatoVerbaleSoggettoService.isAllegatoVerbaleSoggettoCreato(cnmRVerbaleSoggetto, TipoAllegato.VERBALE_AUDIZIONE))
				throw new SecurityException("verbale audizione giàesistente");
			else {
				if(cnmTAllegato == null)
					cnmTAllegato = commonAllegatoService.salvaAllegatoProtocollatoVerbaleSoggetto(request, cnmTUser, cnmRVerbaleSoggettoList);
						
				// aggiungo alla tabella
				CnmRAllegatoVerbSogPK cnmRAllegatoVerbSogPK = new CnmRAllegatoVerbSogPK();
				cnmRAllegatoVerbSogPK.setIdAllegato(cnmTAllegato.getIdAllegato());
				cnmRAllegatoVerbSogPK.setIdVerbaleSoggetto(cnmRVerbaleSoggetto.getIdVerbaleSoggetto());

				CnmRAllegatoVerbSog cnmRAllegatoVerbSog = new CnmRAllegatoVerbSog();
				cnmRAllegatoVerbSog.setId(cnmRAllegatoVerbSogPK);
				cnmRAllegatoVerbSog.setCnmTAllegato(cnmTAllegato);
				cnmRAllegatoVerbSog.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
				cnmRAllegatoVerbSog.setCnmTUser(cnmTUser);
				cnmRAllegatoVerbSogRepository.save(cnmRAllegatoVerbSog);					
						
//				allegatos.add(cnmTAllegato);
//						
//				if (allegatos != null && allegatos.size() == 0) {
//					List<CnmTSoggetto> cnmTSoggettoList = cnmTSoggettoRepository.findByCnmRVerbaleSoggettosIn(cnmRVerbaleSoggettoList);
//					allegatos.add(salvaAllegatoVerbaleSoggetto(cnmRVerbaleSoggetto, file, cnmTUser, "Verbale_audizione.pdf", TipoAllegato.VERBALE_AUDIZIONE, false, false, false, cnmTSoggettoList));
//				} else
//					salvaAllegatoVerbaleSoggetto(cnmRVerbaleSoggetto, allegatos.get(0), cnmTUser);

			}
		}
		String protocollo = StringUtils.isNotBlank(request.getDocumentoProtocollato().getNumProtocollo())?request.getDocumentoProtocollato().getNumProtocollo():request.getDocumentoProtocollato().getNumProtocolloMaster();
		CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.CONFIRM_RECUPERO_PROTOCOLLO);
		if(cnmDMessaggio!=null) {			
			String msg = String.format(cnmDMessaggio.getDescMessaggio(), protocollo);
			 return new MessageVO(msg, cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
		}else {
			throw new SecurityException("Messaggio non trovato");
		}
	}

	@Transactional
	public MessageVO salvaConvocazioneAudizione(SalvaAllegatiProtocollatiSoggettiRequest request, UserDetails user) {
		if (request == null)
			throw new IllegalArgumentException("request==null");
		List<Integer> idVerbaleSoggettoList = request.getIdVerbaleSoggettoList();
		if (idVerbaleSoggettoList == null)
			throw new IllegalArgumentException("idVerbaleSoggettoList ==null");
		
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = (List<CnmRVerbaleSoggetto>) cnmRVerbaleSoggettoRepository.findAll(idVerbaleSoggettoList);
		if (cnmRVerbaleSoggettoList == null)
			throw new IllegalArgumentException("cnmTOrdinanza ==null");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
//		List<CnmTAllegato> allegatos = new ArrayList<>();
		CnmTAllegato cnmTAllegato = null;
		for (CnmRVerbaleSoggetto cnmRVerbaleSoggetto : cnmRVerbaleSoggettoList) {
			if (allegatoVerbaleSoggettoService.isAllegatoVerbaleSoggettoCreato(cnmRVerbaleSoggetto, TipoAllegato.CONVOCAZIONE_AUDIZIONE))
				throw new SecurityException("convocazione audizione già  esistente");
			else {
				if(cnmTAllegato == null)
					cnmTAllegato = commonAllegatoService.salvaAllegatoProtocollatoVerbaleSoggetto(request, cnmTUser, cnmRVerbaleSoggettoList);
						
				// aggiungo alla tabella
				CnmRAllegatoVerbSogPK cnmRAllegatoVerbSogPK = new CnmRAllegatoVerbSogPK();
				cnmRAllegatoVerbSogPK.setIdAllegato(cnmTAllegato.getIdAllegato());
				cnmRAllegatoVerbSogPK.setIdVerbaleSoggetto(cnmRVerbaleSoggetto.getIdVerbaleSoggetto());

				CnmRAllegatoVerbSog cnmRAllegatoVerbSog = new CnmRAllegatoVerbSog();
				cnmRAllegatoVerbSog.setId(cnmRAllegatoVerbSogPK);
				cnmRAllegatoVerbSog.setCnmTAllegato(cnmTAllegato);
				cnmRAllegatoVerbSog.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
				cnmRAllegatoVerbSog.setCnmTUser(cnmTUser);
				cnmRAllegatoVerbSogRepository.save(cnmRAllegatoVerbSog);	

//				if (allegatos != null && allegatos.size() == 0) {
//					List<CnmTSoggetto> cnmTSoggettoList = cnmTSoggettoRepository.findByCnmRVerbaleSoggettosIn(cnmRVerbaleSoggettoList);
//					String nome = "Convocazione_audizione_" + cnmRVerbaleSoggetto.getCnmTVerbale().getNumVerbale();
//					String result = verificaNome(nome) + ".pdf";
//					allegatos.add(salvaAllegatoVerbaleSoggetto(cnmRVerbaleSoggetto, file, cnmTUser, result, TipoAllegato.CONVOCAZIONE_AUDIZIONE, true, true, false, cnmTSoggettoList));
//				} else
//					salvaAllegatoVerbaleSoggetto(cnmRVerbaleSoggetto, allegatos.get(0), cnmTUser);
			}
		}
		String protocollo = StringUtils.isNotBlank(request.getDocumentoProtocollato().getNumProtocollo())?request.getDocumentoProtocollato().getNumProtocollo():request.getDocumentoProtocollato().getNumProtocolloMaster();
		CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.CONFIRM_RECUPERO_PROTOCOLLO);
		if(cnmDMessaggio!=null) {			
			String msg = String.format(cnmDMessaggio.getDescMessaggio(), protocollo);
			 return new MessageVO(msg, cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
		}else {
			throw new SecurityException("Messaggio non trovato");
		}
	}

}
