/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.MinPianoRateizzazioneEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.SoggettoEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoPianoRateEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoPianoRateRepository;
import it.csi.conam.conambl.integration.repositories.CnmRSoggRataRepository;
import it.csi.conam.conambl.integration.repositories.CnmTRataRepository;
import it.csi.conam.conambl.vo.pianorateizzazione.MinPianoRateizzazioneVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class MinPianoRateizzazioneEntityMapperImpl implements MinPianoRateizzazioneEntityMapper {

	@Autowired
	private SoggettoEntityMapper soggettoEntityMapper;
	@Autowired
	private StatoPianoRateEntityMapper statoPianoRateEntityMapper;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;
	@Autowired
	private CnmTRataRepository cnmTRataRepository;
	@Autowired
	private CnmRAllegatoPianoRateRepository cnmRAllegatoPianoRateRepository;

	@Override
	public MinPianoRateizzazioneVO mapEntityToVO(CnmTPianoRate dto) {
		if (dto == null)
			return null;
		MinPianoRateizzazioneVO minPianoVO = new MinPianoRateizzazioneVO();
		minPianoVO.setDataCreazione(utilsDate.asLocalDateTime(dto.getDataOraInsert()));
		minPianoVO.setNumProtocollo(dto.getNumeroProtocollo());
		BigDecimal saldo = BigDecimal.ZERO;

		if (dto.getImportoSanzione() != null)
			saldo = saldo.add(dto.getImportoSanzione());
		if (dto.getImportoSpeseNotifica() != null)
			saldo = saldo.add(dto.getImportoSpeseNotifica());
		if (dto.getImportoSpeseProcessuali() != null)
			saldo = saldo.add(dto.getImportoSpeseProcessuali());
		if (dto.getImportoMaggiorazione() != null)
			saldo = saldo.add(dto.getImportoMaggiorazione());

		minPianoVO.setSaldo(saldo);
		List<CnmTSoggetto> soggettoList = new ArrayList<>();

		List<CnmTRata> cnmTRataList = cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(dto);
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmRSoggRataRepository.findDistinctCnmROrdinanzaVerbSogByCnmTRataIn(cnmTRataList);
		for (CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog : cnmROrdinanzaVerbSogList)
			soggettoList.add(cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTSoggetto());

		minPianoVO.setSoggetti(soggettoEntityMapper.mapListEntityToListVO(soggettoList));
		minPianoVO.setStato(statoPianoRateEntityMapper.mapEntityToVO(dto.getCnmDStatoPianoRate()));
		minPianoVO.setId(dto.getIdPianoRate());
		
		

		List<CnmRAllegatoPianoRate> cnmRAllegatoPianoRateList = cnmRAllegatoPianoRateRepository.findByCnmTPianoRate(dto);
		if (cnmRAllegatoPianoRateList != null && !cnmRAllegatoPianoRateList.isEmpty()) {
			for (CnmRAllegatoPianoRate cnmRAllegatoPianoRate : cnmRAllegatoPianoRateList) {
				CnmTAllegato cnmTAllegato = cnmRAllegatoPianoRate.getCnmTAllegato();
				if (cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.LETTERA_RATEIZZAZIONE.getId())
					minPianoVO.setNumProtocollo(cnmTAllegato.getNumeroProtocollo());
			}
		}

		return minPianoVO;
	}

}
