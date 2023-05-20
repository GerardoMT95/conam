/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.integration.entity.CnmTNotifica;
import it.csi.conam.conambl.integration.mapper.entity.ModalitaNotificaEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.NotificaEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmDModalitaNotificaRepository;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;

@Component
public class NotificaEntityMapperImpl implements NotificaEntityMapper {

	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmDModalitaNotificaRepository cnmDModalitaNotificaRepository;
	@Autowired
	private ModalitaNotificaEntityMapper modalitaNotificaEntityMapper;

	@Override
	public NotificaVO mapEntityToVO(CnmTNotifica dto) {
		NotificaVO notificaVO = new NotificaVO();
		notificaVO.setDataNotifica(utilsDate.asLocalDate(dto.getDataNotifica()));
		notificaVO.setDataSpedizione(utilsDate.asLocalDate(dto.getDataSpedizione()));
		notificaVO.setImportoSpeseNotifica(dto.getImportoSpeseNotifica());
		notificaVO.setNumeroRaccomandata(dto.getNumeroRaccomandata());
		notificaVO.setModalita(modalitaNotificaEntityMapper.mapEntityToVO(dto.getCnmDModalitaNotifica()));
		return notificaVO;
	}

	@Override
	public CnmTNotifica mapVOtoEntity(NotificaVO notifica) {
		CnmTNotifica CnmTNotifica = new CnmTNotifica();
		CnmTNotifica.setDataNotifica(utilsDate.asDate(notifica.getDataNotifica()));
		CnmTNotifica.setDataSpedizione(utilsDate.asDate(notifica.getDataSpedizione()));
		CnmTNotifica.setImportoSpeseNotifica(notifica.getImportoSpeseNotifica() != null ? notifica.getImportoSpeseNotifica().setScale(2, RoundingMode.HALF_UP) : null);
		CnmTNotifica.setNumeroRaccomandata(notifica.getNumeroRaccomandata());
		if (notifica.getModalita() != null && notifica.getModalita().getId() != null)
			CnmTNotifica.setCnmDModalitaNotifica(cnmDModalitaNotificaRepository.findOne(notifica.getModalita().getId()));
		return CnmTNotifica;
	}

}
