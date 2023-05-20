/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.scheduled.impl;

import it.csi.conam.conambl.business.service.mail.MailSenderService;
import it.csi.conam.conambl.business.service.mail.PromemoriaDocumentazioneMail;
import it.csi.conam.conambl.business.service.mail.PromemoriaUdienzaMail;
import it.csi.conam.conambl.integration.entity.CnmTCalendario;
import it.csi.conam.conambl.integration.repositories.CnmTCalendarioRepository;
import it.csi.conam.conambl.scheduled.EmailScheduledService;
import it.csi.conam.conambl.util.DateConamUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Lucio Rosadini
 */
@Service
public class EmailScheduledServiceImpl implements EmailScheduledService {

	protected static Logger logger = Logger.getLogger(EmailScheduledServiceImpl.class);

	@Autowired
	private CnmTCalendarioRepository cnmTCalendarioRepository;

	@Autowired
	private MailSenderService mailSenderService;
	
	
	private void sendMailFromCalendarioList(List<CnmTCalendario> calendarioList, Boolean isDocumentazione) throws Throwable {
		for (CnmTCalendario calendario : calendarioList) {
			try {
				mailSenderService.sendMail(
					isDocumentazione ?
						new PromemoriaDocumentazioneMail(calendario)
					:
						new PromemoriaUdienzaMail(calendario)
				);
			} catch (Throwable e) {
				throw e;
			}
		}
	}
	
	private List<CnmTCalendario> getListaCalendario(Boolean isDocumentazione) {
		Date fromDate = DateConamUtils.getStartOfTheDay(new Date());
		Date toDate   = DateConamUtils.getEndOfTheDay(new Date());
		List<CnmTCalendario> calendariDaInviare =
			isDocumentazione ?
				cnmTCalendarioRepository.findDocumentazioneNotificationToSend(
						fromDate,
						toDate
					)
			:

				cnmTCalendarioRepository.findUdienzaNotificationToSend(
					fromDate,
					toDate
				)
		;
		return calendariDaInviare;
	}
	
	
	public void sendAllPromemoriaDocumentazione() throws Throwable {
		Boolean isDocumentazione = true;
		List<CnmTCalendario> calendariDaInviare = getListaCalendario(isDocumentazione);
		sendMailFromCalendarioList(calendariDaInviare, isDocumentazione);
	}

	public void sendAllPromemoriaUdienze() throws Throwable {
		Boolean isDocumentazione = false;
		List<CnmTCalendario> calendariDaInviare = getListaCalendario(isDocumentazione);
		sendMailFromCalendarioList(calendariDaInviare, isDocumentazione);
	}

	public void sendAllPromemoria() throws Throwable {
		sendAllPromemoriaDocumentazione();
		sendAllPromemoriaUdienze();
	}
}
