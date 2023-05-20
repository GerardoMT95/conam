/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.integration.entity.CnmTAcconto;
import it.csi.conam.conambl.integration.mapper.entity.AccontoEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmTOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmTSoggettoRepository;
import it.csi.conam.conambl.vo.ordinanza.AccontoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccontoEntityMapperImpl implements AccontoEntityMapper {

	@Autowired
	private UtilsDate utilsDate;
	

	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;
	
	@Override
	public AccontoVO mapEntityToVO(CnmTAcconto dto) {
		AccontoVO accontoVO = new AccontoVO();
		accontoVO.setId(dto.getId());
		accontoVO.setIdOrdinanza(dto.getCnmTOrdinanza().getIdOrdinanza());
		accontoVO.setIdSoggetto(dto.getCnmTSoggetto().getIdSoggetto());
		accontoVO.setImporto(dto.getImporto());
		accontoVO.setDataPagamento(utilsDate.asLocalDate(dto.getDataPagamento()));
		accontoVO.setContoCorrenteVersamento(dto.getContoCorrenteVersamento());
		return accontoVO;
	}

	@Override
	public CnmTAcconto mapVOtoEntity(AccontoVO accontoVO) {
		CnmTAcconto cnmTAcconto = new CnmTAcconto();
		cnmTAcconto.setId(accontoVO.getId());
		cnmTAcconto.setCnmTOrdinanza(cnmTOrdinanzaRepository.findOne(accontoVO.getIdOrdinanza()));
		cnmTAcconto.setCnmTSoggetto(cnmTSoggettoRepository.findOne(accontoVO.getIdSoggetto()));
		cnmTAcconto.setImporto(accontoVO.getImporto());
		cnmTAcconto.setDataPagamento(utilsDate.asDate(accontoVO.getDataPagamento()));
		cnmTAcconto.setContoCorrenteVersamento(accontoVO.getContoCorrenteVersamento());
		return cnmTAcconto;
	}

	@Override
	public List<AccontoVO> mapEntityListToVOList(List<CnmTAcconto> dtoList) {
		List<AccontoVO> listaAcconti = new ArrayList<AccontoVO>();
		for (CnmTAcconto cnmTAcconto: dtoList) {
			listaAcconti.add(mapEntityToVO(cnmTAcconto));
		}
		return listaAcconti;
	}

}
