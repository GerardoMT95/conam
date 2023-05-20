/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.ws.impl.stas;

import it.csi.conam.conambl.integration.mapper.ws.stas.AnagraficaWsOutputMapper;
import it.csi.conam.conambl.integration.mapper.ws.stas.SoggettoImpresaWsOutputMapper;
import it.csi.conam.conambl.integration.mapper.ws.stas.SoggettoPFWsOutputMapper;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.gmscore.dto.Anagrafica;
import it.csi.gmscore.dto.SoggettoImpresa;
import it.csi.gmscore.dto.SoggettoPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnagraficaWsOutputMapperImpl implements AnagraficaWsOutputMapper {

	@Autowired
	private SoggettoPFWsOutputMapper soggettoPFWsOutputMapper;
	@Autowired
	private SoggettoImpresaWsOutputMapper soggettoImpresaWsOutputMapper;

	@Override
	public SoggettoVO mapWsTypeToVO(Anagrafica wsType) {
		if (wsType == null)
			return null;
		if (wsType instanceof SoggettoPF)
			return soggettoPFWsOutputMapper.mapWsTypeToVO((SoggettoPF) wsType);
		else if (wsType instanceof SoggettoImpresa)
			return soggettoImpresaWsOutputMapper.mapWsTypeToVO((SoggettoImpresa) wsType);
		else
			throw new RuntimeException("unknown instance");

	}

}
