/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.*;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleIllecitoRepository;
import it.csi.conam.conambl.vo.leggi.ProntuarioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProntuarioEntityMapperImpl implements ProntuarioEntityMapper {

	@Autowired
	private LetteraEntityMapper letteraEntityMapper;
	@Autowired
	private CommaEntityMapper commaEntityMapper;
	@Autowired
	private ArticoloEntityMapper articoloEntityMapper;
	@Autowired
	private NormaEntityMapper normaEntityMapper;
	@Autowired
	private AmbitoEntityMapper ambitoEntityMapper;
	@Autowired
	private CnmRVerbaleIllecitoRepository cnmRVerbaleIllecitoRepository;

	@Autowired
	private UtilsDate utilsDate;

	@Override
	public ProntuarioVO mapEntityToVO(CnmDLettera l) {
		if (l == null)
			return null;
		ProntuarioVO p = new ProntuarioVO();

		CnmDComma cnmDComma = l.getCnmDComma();
		CnmDArticolo cnmDArticolo = cnmDComma.getCnmDArticolo();
		CnmREnteNorma cnmREnteNorma = cnmDArticolo.getCnmREnteNorma();
		CnmDNorma cnmDNorma = cnmREnteNorma.getCnmDNorma();
		p.setLettera(letteraEntityMapper.mapEntityToVO(l));
		p.setComma(commaEntityMapper.mapEntityToVO(cnmDComma));
		p.setArticolo(articoloEntityMapper.mapEntityToVO(cnmDArticolo));
		p.setNorma(normaEntityMapper.mapEntityToVO(cnmDNorma));
		p.getNorma().setDataFineValidita(utilsDate.asLocalDate(cnmREnteNorma.getFineValidita()));
		
		// 20201110 PP - Jira CONAM-102 - consente di restituire in output anche la data di inizio validita' norma
		p.getNorma().setDataInizioValidita(utilsDate.asLocalDate(cnmREnteNorma.getInizioValidita()));
		
		p.setAmbito(ambitoEntityMapper.mapEntityToVO(cnmDNorma.getCnmDAmbito()));
		Long numVerbIllecito = cnmRVerbaleIllecitoRepository.countByCnmDLettera(l);
		p.setEliminaEnable(numVerbIllecito != null && numVerbIllecito > 0 ? Boolean.FALSE : Boolean.TRUE);

		return p;
	}

}
