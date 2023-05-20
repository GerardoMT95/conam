/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.mail;

import it.csi.conam.conambl.business.service.mail.MailSenderService;
import it.csi.conam.conambl.business.service.mail.PromemoriaDocumentazioneMail;
import it.csi.conam.conambl.business.service.mail.PromemoriaMail;
import it.csi.conam.conambl.business.service.mail.PromemoriaUdienzaMail;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.CnmCEmail;
import it.csi.conam.conambl.integration.entity.CnmTCalendario;
import it.csi.conam.conambl.integration.entity.CnmTMailInviata;
import it.csi.conam.conambl.integration.repositories.CnmCEmailRepository;
import it.csi.conam.conambl.integration.repositories.CnmTMailInviataRepository;
import it.csi.conam.conambl.util.StringConamUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Paolo Piedepalumbo
 *
 */

@Service
public class MailSenderServiceImpl implements MailSenderService {
	
	private static Logger logger = Logger.getLogger(MailSenderServiceImpl.class);
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private CnmCEmailRepository cnmCEmailRepository;
	
	@Autowired
	private CnmTMailInviataRepository cnmTMailInviataRepository;
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	@Override
	public void sendMail(PromemoriaMail mail) throws Throwable {

		logger.debug("[sendMail] -> mailTo :: " + mail.geteMailTo());
		if(mail instanceof PromemoriaUdienzaMail) {
			sendDataUdienzaMail((PromemoriaUdienzaMail)mail);
		}else {
			sendDocumentazioneMail((PromemoriaDocumentazioneMail)mail);
		}
	}
	
	private String getParamValue(Long paramID) {
		CnmCEmail param = cnmCEmailRepository.findByParamId(paramID);
		if (param != null) return param.getValue();
		else return "";
	}
	
	public void sendDataUdienzaMail(PromemoriaUdienzaMail mail) throws Throwable {
		String subject = StringConamUtils.createStringFromList(
			Arrays.asList(
				getParamValue(Constants.ID_MAIL_UDIENZA_OBJECT_AUTORITA_GIUDIZIARIA),
				mail.getAutoritaGiudiziaria(),
				"; ",
				getParamValue(Constants.ID_MAIL_UDIENZA_OBJECT_GIORNO),
				dateFormat.format(mail.getData())
			)
		);
		
		String text = StringConamUtils.createStringFromList(
			Arrays.asList(
				getParamValue(Constants.ID_MAIL_UDIENZA_BODY_AUTORITA_GIUDIZIARIA),
				mail.getAutoritaGiudiziaria(),
				"; ",
				getParamValue(Constants.ID_MAIL_UDIENZA_BODY_GIORNO),
				dateFormat.format(mail.getData()),
				"; ",
				getParamValue(Constants.ID_MAIL_UDIENZA_BODY_GIUDICE),
				mail.getGiudiceCompetente(),
				"; ",
				getParamValue(Constants.ID_MAIL_UDIENZA_BODY_LUOGO),
				mail.getLuogoUdienza(),
				"; ",
				mail.getNote()!=null?getParamValue(Constants.ID_MAIL_UDIENZA_BODY_NOTE):"",
				mail.getNote()!=null?mail.getNote():""
			)
		);
		
	   	sendMail(mail.geteMailTo(), null, subject, text, mail.getCnmTCalendario());
	}
	
	public void sendDocumentazioneMail(PromemoriaDocumentazioneMail mail) throws Throwable {

		String subject = StringConamUtils.createStringFromList(
			Arrays.asList(
				getParamValue(Constants.ID_MAIL_DOCUMENTAZIONE_OBJECT_GIORNO),
				dateFormat.format(mail.getData())
			)
		);
		
		String text = StringConamUtils.createStringFromList(
			Arrays.asList(
				getParamValue(Constants.ID_MAIL_DOCUMENTAZIONE_BODY_GIORNO),
				dateFormat.format(mail.getData()),
				"; ",
				getParamValue(Constants.ID_MAIL_DOCUMENTAZIONE_BODY_AUTORITA_GIUDIZIARIA),
				mail.getAutoritaGiudiziaria(),
				"; ",
				getParamValue(Constants.ID_MAIL_DOCUMENTAZIONE_BODY_GIUDICE),
				mail.getGiudiceCompetente(),
				"; ",
				getParamValue(Constants.ID_MAIL_DOCUMENTAZIONE_BODY_LUOGO),
				mail.getLuogoUdienza(),
				"; ",
				mail.getNote()!=null?getParamValue(Constants.ID_MAIL_DOCUMENTAZIONE_BODY_NOTE):"",
				mail.getNote()!=null?mail.getNote():""
			)
		);
		
		sendMail(mail.geteMailTo(), null, subject, text, mail.getCnmTCalendario());
	}
	
	private void sendMail(String to, String cc, String subject, String text, CnmTCalendario cnmTCalendario) throws Throwable {
		
		MimeMessage message = mailSender.createMimeMessage();
		message.setSubject(subject);
		message.setText(text);
		
		message.setRecipients(Message.RecipientType.TO, to);
		message.setFrom(new InternetAddress(getParamValue(Constants.ID_MAIL_FROM), "CONAM - CSI Piemonte"));

		if(cc!= null){
			message.setRecipients(Message.RecipientType.CC, cc);
		}	
		
		CnmTMailInviata cnmTMailInviata = new CnmTMailInviata(to, subject, text, cnmTCalendario);
		try {
			mailSender.send(message);
			cnmTMailInviata.setEsito("OK");
			cnmTMailInviata.setDataInvio(new Date());			
		}catch(Throwable t) {

			cnmTMailInviata.setEsito("KO");
			cnmTMailInviata.setDataInvio(new Date());
			Integer maxSize = 499;
			String info = t.getMessage();
			if(info.length() > maxSize ){
				info = info.substring(0, maxSize);
			}
			cnmTMailInviata.setInfo(info);
		}
		
		cnmTMailInviataRepository.save(cnmTMailInviata);
	}
	


}
