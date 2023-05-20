/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDModalitaCaricamento;
import it.csi.conam.conambl.integration.mapper.entity.ModalitaCaricamentoEntityMapper;
import it.csi.conam.conambl.vo.verbale.ModalitaCaricamentoVO;
import org.springframework.stereotype.Component;

@Component
public class ModalitaCaricamentoEntityMapperImpl implements ModalitaCaricamentoEntityMapper {

	@Override
	public ModalitaCaricamentoVO mapEntityToVO(CnmDModalitaCaricamento dto) {
		if (dto == null)
			return null;
		ModalitaCaricamentoVO modalitaCaricamento = new ModalitaCaricamentoVO();
		modalitaCaricamento.setDenominazione(dto.getDescModalitaCaricamento());
		modalitaCaricamento.setId(dto.getIdModalitaCaricamento());
		return modalitaCaricamento;
	}

	@Override
	public CnmDModalitaCaricamento mapVOtoEntity(ModalitaCaricamentoVO vo) {
		if (vo == null)
			return null;
		CnmDModalitaCaricamento dto = new CnmDModalitaCaricamento();
		dto.setDescModalitaCaricamento(vo.getDenominazione());
		return dto;
	}

}
