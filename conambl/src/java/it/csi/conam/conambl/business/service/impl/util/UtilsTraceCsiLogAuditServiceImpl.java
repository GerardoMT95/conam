/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.util;


import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsTraceCsiLogAuditService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.entity.CsiLogAudit;
import it.csi.conam.conambl.integration.repositories.CnmCParametroRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.integration.repositories.CsiLogAuditRepository;
import it.csi.conam.conambl.security.UserDetails;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.time.LocalDateTime;



@Service
public class UtilsTraceCsiLogAuditServiceImpl implements UtilsTraceCsiLogAuditService {
	
	// 20200622_LC

	private static final Logger log = Logger.getLogger(UtilsTraceCsiLogAuditServiceImpl.class);
	
	@Autowired
	private CnmCParametroRepository cnmCParametroRepository;

	@Autowired
	private CnmTUserRepository cnmTUserRepository;

	@Autowired
	private CsiLogAuditRepository csiLogAuditRepository;	

	@Autowired
	private UtilsDate utilsDate;
	
	
	
	@Override
	public void traceCsiLogAudit(String operazione, String tabella, String key, String caller, String dettaglioOperazione) {
	
		try {
			
			CsiLogAudit csiLogAudit = new CsiLogAudit();
			csiLogAudit.setDataOra(utilsDate.asTimeStamp(LocalDateTime.now()));
			csiLogAudit.setIdApp(getIdAppToTrace());
			csiLogAudit.setIpAddress(getIpAddressToTrace());
			
			csiLogAudit.setUtente(StringUtils.left(getUtenteToTrace(caller), 100));
		
			String operazioneDb = operazione + (StringUtils.isNotBlank(dettaglioOperazione)? " - " + dettaglioOperazione:"");	
			csiLogAudit.setOperazione(StringUtils.left(operazioneDb, 100));	
			
			csiLogAudit.setOggOper(tabella);
			csiLogAudit.setKeyOper(StringUtils.left(key, 500));
			
			csiLogAuditRepository.save(csiLogAudit);
			
			log.debug("Operazione [" + operazioneDb + "] tracciata correttamente");
		}
		catch (Exception e) {
			log.error("Impossibile tracciare l'operazione. " + e);
		}	

		
	}
	
	
	
	// --
	
	

	public String getIdAppToTrace() {
		StringBuilder idApp = new StringBuilder();
		try {
		idApp.append(cnmCParametroRepository.findOne(Constants.ID_CODICE_PRODOTTO).getValoreString() + "_");
		idApp.append(cnmCParametroRepository.findOne(Constants.ID_CODICE_LINEA_CLIENTE).getValoreString() + "_");
		idApp.append(cnmCParametroRepository.findOne(Constants.ID_CODICE_AMBIENTE).getValoreString() + "_");
		idApp.append(cnmCParametroRepository.findOne(Constants.ID_CODICE_UNITA_INSTALLAZIONE).getValoreString());		
		} catch (Exception e) {
			log.error("Impossibile reperire l'id_app");			
		}
		return idApp.toString();
	}
	
	// non scrive mai l'ip del client
	public String getIpAddressToTrace() {		
		String ip = "";		
		try {
			ip = InetAddress.getLocalHost().getHostAddress();			
		} catch (Exception e) {
			log.error("Impossibile reperire l'ip");
		}
		return ip;		
	}

	public String getUtenteToTrace(String caller) {
		String cf = "";		
		try {
			
			// 20200711_LC 
			if (SecurityUtils.getAuthentication()==null) {
//				20200722_ET modificato recupero dell'utente per concatenare il metodo in caso di operazioni batch
				cf = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK).getCodiceFiscale() + " / "+ caller;
			} else {
				UserDetails user = SecurityUtils.getUser();
				cf = cnmTUserRepository.findOne(user.getIdUser()).getCodiceFiscale();
			}
			
			// 20200728_LC max 100 caratteri
			if (cf.length()>100) {
				cf=cf.substring(0,99);
			}
			
		} catch (Exception e) {
			log.error("Impossibile reperire l'utente");			
		}
		return cf;		
	}	
	
	
	

}
