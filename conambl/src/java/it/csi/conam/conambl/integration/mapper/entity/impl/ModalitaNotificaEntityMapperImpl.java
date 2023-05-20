/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDModalitaNotifica;
import it.csi.conam.conambl.integration.mapper.entity.ModalitaNotificaEntityMapper;
import it.csi.conam.conambl.vo.notifica.ModalitaNotificaVO;
import org.springframework.stereotype.Component;

@Component
public class ModalitaNotificaEntityMapperImpl implements ModalitaNotificaEntityMapper {

	@Override
	public ModalitaNotificaVO mapEntityToVO(CnmDModalitaNotifica dto) {
		if (dto == null)
			return null;
		ModalitaNotificaVO modalitaNotificaOrdinanzaVO = new ModalitaNotificaVO();
		modalitaNotificaOrdinanzaVO.setDenominazione(dto.getDescModalitaNotifica());
		modalitaNotificaOrdinanzaVO.setId(dto.getIdModalitaNotifica());
		return modalitaNotificaOrdinanzaVO;

	}

}
