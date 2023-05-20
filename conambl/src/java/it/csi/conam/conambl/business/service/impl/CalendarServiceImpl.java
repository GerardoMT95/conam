/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl;

import it.csi.conam.conambl.business.service.CalendarService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.CnmDGruppo;
import it.csi.conam.conambl.integration.entity.CnmTCalendario;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.mapper.entity.CalendarioEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmCEmailRepository;
import it.csi.conam.conambl.integration.repositories.CnmDGruppoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTCalendarioRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.integration.specification.CnmTCalendarioSpecification;
import it.csi.conam.conambl.request.calendario.CalendarEventRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.calendario.CalendarEventVO;
import it.csi.conam.conambl.vo.calendario.CalendarReminderDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */

@Service
public class CalendarServiceImpl implements CalendarService {

	@Autowired
	private CnmTCalendarioRepository cnmTCalendarioRepository;
	@Autowired
	private CnmDGruppoRepository cnmDGruppoRepository;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CalendarioEntityMapper calendarioEntityMapper;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;

	@Autowired
	private CnmCEmailRepository cnmCEmailRepository;
	
	@Override
	public List<CalendarEventVO> getEvents(CalendarEventRequest request, UserDetails userDetails) {

		Long idGruppo = userDetails.getIdGruppo();
		if (idGruppo == null)
			throw new SecurityException("utente non autorizzato ad accedere al calendario");

		CnmDGruppo cnmDGruppo = cnmDGruppoRepository.findOne(idGruppo);
		List<CnmTCalendario> cnmTCalendario = cnmTCalendarioRepository
				.findAll(CnmTCalendarioSpecification.findEventsInCalendarioBy(utilsDate.asTimeStamp(request.getDataInizio()), utilsDate.asTimeStamp(request.getDataFine()), cnmDGruppo, null));

		return calendarioEntityMapper.mapListEntityToListVO(cnmTCalendario);

	}

	@Override
	public void deleteEvent(Integer id, UserDetails userDetails) {
		if (id == null)
			throw new RuntimeException("id è null");

		CnmTCalendario cnmTCalendario = cnmTCalendarioRepository.findOne(id);
		if (cnmTCalendario == null)
			throw new SecurityException("evento non trovato");
		cnmTCalendarioRepository.delete(id);
	}

	@Override
	public CalendarEventVO saveEvent(CalendarEventVO request, UserDetails userDetails) {
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		CnmTCalendario cnmTCalendario;

		if (cnmTUser.getCnmDGruppo() == null)
			throw new SecurityException("gruppo non valorizzato");

		if (request.getId() != null) {
			cnmTCalendario = cnmTCalendarioRepository.findOne(request.getId());
			if (cnmTCalendario == null)
				throw new SecurityException("evento non trovato");
			if (cnmTCalendario.getCnmTUser2().getIdUser() != userDetails.getIdUser())
				throw new SecurityException("modifica  non permessa");

			// Commentata gestione eventi sovrapposti
			// List<CnmTCalendario> cnmTCalendarioList =
			// cnmTCalendarioRepository.findAll(CnmTCalendarioSpecification.findEventsInCalendarioBy(//
			// utilsDate.asTimeStamp(request.getDataInizio()), // datainizio
			// utilsDate.asTimeStamp(request.getDataFine()), // datafine
			// cnmTUser.getCnmDGruppo(), // gruppo
			// request.getId() // idEvento
			// ));
			// if (cnmTCalendarioList != null && !cnmTCalendarioList.isEmpty())
			// throw new BusinessException(ErrorCode.EVENTO_SOVRAPPOSTO);

			cnmTCalendario = calendarioEntityMapper.mapVOtoEntityUpdate(request, cnmTCalendario);
			cnmTCalendario.setCnmDGruppo(cnmTUser.getCnmDGruppo());
			cnmTCalendario.setCognomeFunzionarioSanzion(cnmTUser.getCognome());
			cnmTCalendario.setNomeFunzionarioSanzion(cnmTUser.getNome());
			cnmTCalendario.setCnmTUser1(cnmTUser);
			cnmTCalendario.setDataOraUpdate(now);
			cnmTCalendario = cnmTCalendarioRepository.save(cnmTCalendario);
		} else {
			// Commentata gestione eventi sovrapposti
			// List<CnmTCalendario> cnmTCalendarioList =
			// cnmTCalendarioRepository.findAll(CnmTCalendarioSpecification.findEventsInCalendarioBy(//
			// utilsDate.asTimeStamp(request.getDataInizio()), // datainizio
			// utilsDate.asTimeStamp(request.getDataFine()), // datafine
			// cnmTUser.getCnmDGruppo(), // gruppo
			// null));
			// if (cnmTCalendarioList != null && !cnmTCalendarioList.isEmpty())
			// throw new BusinessException(ErrorCode.EVENTO_SOVRAPPOSTO);
			cnmTCalendario = calendarioEntityMapper.mapVOtoEntity(request);
			cnmTCalendario.setCnmDGruppo(cnmTUser.getCnmDGruppo());
			cnmTCalendario.setCnmTUser2(cnmTUser);
			cnmTCalendario.setDataOraInsert(now);
			cnmTCalendario = cnmTCalendarioRepository.save(cnmTCalendario);
		}

		return calendarioEntityMapper.mapEntityToVO(cnmTCalendario);

	}

	@Override
	public CalendarReminderDataVO getReminderData() {

		CalendarReminderDataVO reminder = new CalendarReminderDataVO(0L, 0L);
		String mailUdienzaAdvanceDay   = cnmCEmailRepository.findByParamId(Constants.ID_MAIL_UDIENZA_ADVANCE_DAY).getValue();
		String mailDocumentazioneAdvanceDay   = cnmCEmailRepository.findByParamId(Constants.ID_MAIL_DOCUMENTAZIONE_ADVANCE_DAY).getValue();
		
		try {
			reminder.setGiorniMailUdienza(Long.parseLong(mailUdienzaAdvanceDay));
			reminder.setGiorniMailDocumentazione(Long.parseLong(mailDocumentazioneAdvanceDay));
		}catch(Throwable t) {}
		
		return reminder;
	}

}
