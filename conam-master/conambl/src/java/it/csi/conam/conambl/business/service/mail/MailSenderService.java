/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.mail;

/**
 * @author Paolo Piedepalumbo
 *
 */
public interface MailSenderService {
	
	public static final String TEMPLATE_FILE_ENCODING = "UTF-8";

	public void sendMail(PromemoriaMail mail) throws Throwable;
	

}
