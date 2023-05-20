/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.entity.CnmTCalendario;
import it.csi.conam.conambl.integration.mapper.entity.CalendarioEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.ComuneEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.ProvinciaEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.RegioneEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmDComuneRepository;
import it.csi.conam.conambl.integration.repositories.CnmDGruppoRepository;
import it.csi.conam.conambl.util.DateConamUtils;
import it.csi.conam.conambl.vo.calendario.CalendarEventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CalendarioEntityMapperImpl implements CalendarioEntityMapper {

	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private ComuneEntityMapper comuneEntityMapper;
	@Autowired
	private ProvinciaEntityMapper provinciaEntityMapper;
	@Autowired
	private RegioneEntityMapper regioneEntityMapper;
	@Autowired
	private CnmDComuneRepository cnmDComuneRepository;
	@Autowired
	private CnmDGruppoRepository cnmDGruppoRepository;

	@Override
	public CalendarEventVO mapEntityToVO(CnmTCalendario dto) {
		if (dto == null)
			return null;
		CalendarEventVO cal = new CalendarEventVO();

		cal.setCap(dto.getCapUdienza());
		cal.setCivico(dto.getNumeroCivicoUdienza());
		cal.setCognomeFunzionarioSanzionatore(dto.getCognomeFunzionarioSanzion());
		cal.setNomeFunzionarioSanzionatore(dto.getNomeFunzionarioSanzion());
		cal.setColor(dto.getColore());
		cal.setCognomeGiudice(dto.getCognomeGiudice());
		cal.setComune(comuneEntityMapper.mapEntityToVO(dto.getCnmDComune()));
		cal.setDataFine(utilsDate.asLocalDateTime(dto.getFineUdienza()));
		cal.setDataInizio(utilsDate.asLocalDateTime(dto.getInizioUdienza()));
		cal.setId(dto.getIdCalendario());
		cal.setNomeGiudice(dto.getNomeGiudice());
		cal.setNote(dto.getNote());
		cal.setProvincia(provinciaEntityMapper.mapEntityToVO(dto.getCnmDComune().getCnmDProvincia()));
		cal.setRegione(regioneEntityMapper.mapEntityToVO(dto.getCnmDComune().getCnmDProvincia().getCnmDRegione()));
		cal.setTribunale(dto.getTribunale());
		cal.setVia(dto.getIndirizzoUdienza());
		cal.setCodiceFiscaleProprietario(dto.getCnmTUser2().getNome() + " " + dto.getCnmTUser2().getCognome());

		cal.setEmailGiudice(dto.getEmailGiudice());
		if (dto.getDataPromemoriaUdienza() != null) {
			cal.setSendPromemoriaUdienza(true);
			cal.setUdienzaAdvanceDay(
				DateConamUtils.getDayBetweenDates(
					dto.getDataPromemoriaUdienza(),
					dto.getInizioUdienza()
				)
			);
		}else {
			cal.setSendPromemoriaUdienza(false);
		}
		
		if (dto.getDataPromemoriaDocumentazione() != null) {
			cal.setSendPromemoriaDocumentazione(true);
			cal.setDocumentazioneAdvanceDay(
				DateConamUtils.getDayBetweenDates(
					dto.getDataPromemoriaDocumentazione(),
					dto.getInizioUdienza()
				)
			);
		}else {
			cal.setSendPromemoriaDocumentazione(false);
		}
		
		return cal;

	}

	@Override
	public CnmTCalendario mapVOtoEntity(CalendarEventVO vo) {
		CnmTCalendario cnmTCalendario = new CnmTCalendario();
		return mapVOtoEntityUpdate(vo, cnmTCalendario);
	}

	@Override
	public CnmTCalendario mapVOtoEntityUpdate(CalendarEventVO vo, CnmTCalendario cnmTCalendario) {
		long idGruppo = SecurityUtils.getUser().getIdGruppo();
		cnmTCalendario.setCapUdienza(vo.getCap());
		cnmTCalendario.setCnmDGruppo(cnmDGruppoRepository.findOne(idGruppo));
		cnmTCalendario.setCnmDComune(cnmDComuneRepository.findOne(vo.getComune().getId()));
		cnmTCalendario.setCognomeFunzionarioSanzion(vo.getCognomeFunzionarioSanzionatore());
		cnmTCalendario.setNomeFunzionarioSanzion(vo.getNomeFunzionarioSanzionatore());
		cnmTCalendario.setCognomeGiudice(vo.getCognomeGiudice());
		cnmTCalendario.setColore(vo.getColor());
		cnmTCalendario.setFineUdienza(utilsDate.asTimeStamp(vo.getDataFine()));
		cnmTCalendario.setIndirizzoUdienza(vo.getVia());
		cnmTCalendario.setInizioUdienza(utilsDate.asTimeStamp(vo.getDataInizio()));
		cnmTCalendario.setNomeGiudice(vo.getNomeGiudice());
		cnmTCalendario.setCognomeGiudice(vo.getCognomeGiudice());
		cnmTCalendario.setNote(vo.getNote());
		cnmTCalendario.setNumeroCivicoUdienza(vo.getCivico());
		cnmTCalendario.setTribunale(vo.getTribunale());
		
		//PROMEMORI UDIENZA
		Date dataPromemoriaUdienza = null;
		if (vo.getSendPromemoriaUdienza()) {
			dataPromemoriaUdienza = 
				DateConamUtils.subtractDaysToDate(
					utilsDate.asTimeStamp(vo.getDataInizio()),
					vo.getUdienzaAdvanceDay()
				);

			dataPromemoriaUdienza = DateConamUtils.getStartOfTheDay(dataPromemoriaUdienza);
		}
		cnmTCalendario.setDataPromemoriaUdienza(dataPromemoriaUdienza);
		
		//PROMEMORI DOCUMENTAZIONE
		Date dataPromemoriaDocumentazione = null;
		if (vo.getSendPromemoriaDocumentazione()) {
			dataPromemoriaDocumentazione =
				DateConamUtils.subtractDaysToDate(
					utilsDate.asTimeStamp(vo.getDataInizio()),
					vo.getDocumentazioneAdvanceDay()
				);
			dataPromemoriaDocumentazione = DateConamUtils.getStartOfTheDay(dataPromemoriaDocumentazione);
		}
		cnmTCalendario.setDataPromemoriaDocumentazione(dataPromemoriaDocumentazione);
		
		
		cnmTCalendario.setEmailGiudice(vo.getEmailGiudice());
		
		return cnmTCalendario;
	}

}
