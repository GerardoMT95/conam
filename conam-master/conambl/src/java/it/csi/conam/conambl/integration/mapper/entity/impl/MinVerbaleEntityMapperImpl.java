/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.mapper.entity.EnteEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.MinVerbaleEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoManualeEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoVerbaleEntityMapper;
import it.csi.conam.conambl.vo.verbale.MinVerbaleVO;
import it.csi.conam.conambl.vo.verbale.StatoVerbaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MinVerbaleEntityMapperImpl implements MinVerbaleEntityMapper {

	@Autowired
	private StatoManualeEntityMapper statoManualeEntityMapper;
	@Autowired
	private StatoVerbaleEntityMapper statoEntityMapper;
	@Autowired
	private EnteEntityMapper enteEntityMapper;
	@Autowired
	private UtilsDate utilsDate;

	@Override
	public MinVerbaleVO mapEntityToVO(CnmTVerbale dto, Long idUser) {
		if (dto == null)
			return null;
		MinVerbaleVO verbale = new MinVerbaleVO();

		verbale.setEnteAccertatore(enteEntityMapper.mapEntityToVO(dto.getCnmDEnte()));
		verbale.setId(dto.getIdVerbale().longValue());
		verbale.setNumero(dto.getNumVerbale());
		StatoVerbaleVO stato = statoEntityMapper.mapEntityToVO(dto.getCnmDStatoVerbale());
		verbale.setStato(stato);
		verbale.setId(dto.getIdVerbale().longValue());
		verbale.setNumeroProtocollo(dto.getNumeroProtocollo());
		verbale.setDataCaricamento(utilsDate.asLocalDateTime(dto.getDataOraInsert()));
		verbale.setUser(dto.getCnmTUser2().getNome() + " " + dto.getCnmTUser2().getCognome());

		verbale.setModificabile(Boolean.FALSE);
		if ((stato.getId() == Constants.STATO_VERBALE_INCOMPLETO) && dto.getCnmTUser2().getIdUser() == idUser)
			verbale.setModificabile(Boolean.TRUE);

		verbale.setStatoManuale(statoManualeEntityMapper.mapEntityToVO(dto.getCnmDStatoManuale()));
		
		verbale.setDataOraAccertamento(utilsDate.asLocalDateTime(dto.getDataOraAccertamento()));
		
		return verbale;
	}

}
