/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.common.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.doqui.common.StatoRichiestaService;
import it.csi.conam.conambl.integration.doqui.entity.CnmDStatoRichiesta;
import it.csi.conam.conambl.integration.doqui.entity.CnmTRichiesta;
import it.csi.conam.conambl.integration.doqui.exception.FruitoreException;
import it.csi.conam.conambl.integration.doqui.repositories.CnmDStatoRichiestaRepository;
import it.csi.conam.conambl.integration.doqui.repositories.CnmTRichiestaRepository;
import it.csi.conam.conambl.integration.doqui.utils.XmlSerializer;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.security.UserDetails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StatoRichiestaServiceImpl implements StatoRichiestaService {


//	protected static final String LOGGER_PREFIX = DoquiConstants.LOGGER_PREFIX + ".helper";	
//	protected static final Logger log = Logger.getLogger(LOGGER_PREFIX);
	private static Logger log = Logger.getLogger(StatoRichiestaServiceImpl.class);
	
	@Autowired
	private CnmTRichiestaRepository cnmTRichiestaRepository;
	
	// 20200630_LC
	@Autowired
	private CnmDStatoRichiestaRepository cnmDStatoRichiestaRepository;

	@Autowired
	private UtilsDate utilsDate;
	
	// 20200702_LC
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	
	
	//commentante le transactional perche le tabelle sono lockate pre commit
	//@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void changeStatoRichiesta(Integer idRichiesta, long newStatoRichiesta) {
		String method = "changeStatoRichiesta";

			log.debug(method + ". idRichiesta      = " + idRichiesta);
			log.debug(method + ". newStatoRichiesta = " + newStatoRichiesta);

		try {
			
			CnmTRichiesta dto = cnmTRichiestaRepository.findOne(idRichiesta);
			

			// 20200711_LC 
			CnmTUser cnmTUser = null;
			if (SecurityUtils.getAuthentication()==null) {
				cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
			} else {
				UserDetails user = SecurityUtils.getUser();
				cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
			}
			
			// 20200630_LC
			//dto.setDataUpdRichiesta(DateFormat.getCurrentSqlDate());
			dto.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));
			dto.setCnmTUser1(cnmTUser);		// 20200702_LC 
			
			// 20200630_LC
			CnmDStatoRichiesta statoRichiesta = cnmDStatoRichiestaRepository.findOne(newStatoRichiesta);
			//dto.setIdStatoRichiesta(newStatoRichiesta);
			dto.setCnmDStatoRichiesta(statoRichiesta);
			
			cnmTRichiestaRepository.save(dto);
			
			log.debug(method + ". stato richiesta cambiato correttamente");
		}
		catch (Exception e) {
			log.error(method + ". Accesso al DAO Fallito " + e);
			throw new RuntimeException(e.getMessage());
		}	
	}

	//@Transactional(propagation=Propagation.REQUIRES_NEW)
	public CnmTRichiesta insertRichiesta(CnmTRichiesta docTRichiestaDto) {
		return cnmTRichiestaRepository.save(docTRichiestaDto);
	}
	
	
	public CnmTRichiesta getRichiesta(Integer idRichiesta) throws FruitoreException 
	{
		String method = "getRichiesta";
		if(log.isDebugEnabled())
			log.debug(method + ". idRichiesta = " + idRichiesta);
		
		CnmTRichiesta docTRichiestaDto = null;
		
		try
		{
			//List<DocTFruitoreDto> elencoFruitori = docTFruitoreDao.findByCodiceFruitore(codiceFruitore);
			
			docTRichiestaDto = cnmTRichiestaRepository.findOne(idRichiesta);
				
			if(log.isDebugEnabled())
				log.debug(method + ". cnmTRichiestaDto \n " + XmlSerializer.objectToXml(docTRichiestaDto));
			
			if (docTRichiestaDto == null) 
				throw new FruitoreException("Richiesta non censita in anagrafica ");
		
		}
		catch (Exception e)
		{
			log.error(method + ". Accesso al DAO Fallito " + e);
			throw new FruitoreException(e.getMessage());
		}

		return docTRichiestaDto;
	
	}
	
	
	
	
}
