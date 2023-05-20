/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.mapper.entity.*;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ROrdinanzaVerbSogEntityMapperImpl implements ROrdinanzaVerbSogEntityMapper {

	@Autowired
	private RuoloSoggettoEntityMapper ruoloSoggettoEntityMapper;

	@Autowired
	private MinVerbaleEntityMapper minVerbaleEntityMapper;
	
	@Autowired
	private SoggettoEntityMapper soggettoEntityMapper;
	@Autowired
	private StatoSoggettoOrdinanzaMapper statoSoggettoOrdinanzaMapper;
	@Autowired
	private StatoOrdinanzaMapper statoOrdinanzaMapper;

	@Override
	public SoggettoVO mapEntityToVO(CnmROrdinanzaVerbSog dto) {
		SoggettoVO soggetto = soggettoEntityMapper.mapEntityToVO(
			dto.getCnmRVerbaleSoggetto().getCnmTSoggetto()
		);
		soggetto.setIdSoggettoOrdinanza(dto.getIdOrdinanzaVerbSog());
		soggetto.setStatoSoggettoOrdinanza(statoSoggettoOrdinanzaMapper.mapEntityToVO(dto.getCnmDStatoOrdVerbSog()));
		soggetto.setRuolo(ruoloSoggettoEntityMapper.mapEntityToVO(dto.getCnmRVerbaleSoggetto().getCnmDRuoloSoggetto()));
		soggetto.setNoteSoggetto(dto.getCnmRVerbaleSoggetto().getNote());
		soggetto.setIdSoggettoVerbale(dto.getCnmRVerbaleSoggetto().getIdVerbaleSoggetto());
		soggetto.setVerbale(
			minVerbaleEntityMapper.mapEntityToVO(dto.getCnmRVerbaleSoggetto().getCnmTVerbale(), 0L)
		);
		soggetto.setNumDetOrdinanza(dto.getCnmTOrdinanza().getNumDeterminazione());
		soggetto.setStatoOrdinanza(statoOrdinanzaMapper.mapEntityToVO(dto.getCnmTOrdinanza().getCnmDStatoOrdinanza()));
		
		return soggetto;
	}

}
