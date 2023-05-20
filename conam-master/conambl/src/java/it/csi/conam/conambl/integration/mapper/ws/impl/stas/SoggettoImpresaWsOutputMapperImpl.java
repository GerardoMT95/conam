/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.ws.impl.stas;

import it.csi.conam.conambl.integration.mapper.ws.stas.CommonSoggettoWsOutputMapper;
import it.csi.conam.conambl.integration.mapper.ws.stas.SoggettoImpresaWsOutputMapper;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.gmscore.dto.IdentitaImpresa;
import it.csi.gmscore.dto.IndirizzoNazionale;
import it.csi.gmscore.dto.LuogoNazionale;
import it.csi.gmscore.dto.SoggettoImpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SoggettoImpresaWsOutputMapperImpl implements SoggettoImpresaWsOutputMapper {

	@Autowired
	private CommonSoggettoWsOutputMapper commonSoggettoWsOutputMapper;

	@Override
	public SoggettoVO mapWsTypeToVO(SoggettoImpresa wsType) {
		if (wsType == null)
			return null;
		SoggettoVO soggetto = new SoggettoVO();
		IdentitaImpresa identita = wsType.getIdentitaImpresa();
		LuogoNazionale luogoCostituzione = null;

		// capita in alcuni casi
		if (identita == null || identita.getIdSoggetto() == null)
			return null;

		if (identita.getLuogoCostituzione() instanceof LuogoNazionale) {
			luogoCostituzione = (LuogoNazionale) identita.getLuogoCostituzione();
		}

		IndirizzoNazionale indirizzoResidenza = null;
		if (wsType.getSedeLegale() instanceof IndirizzoNazionale) {
			indirizzoResidenza = (IndirizzoNazionale) wsType.getSedeLegale();
		}

		commonSoggettoWsOutputMapper.mapResidenza(soggetto, indirizzoResidenza);
		commonSoggettoWsOutputMapper.mapLuogoNascita(soggetto, luogoCostituzione);

		soggetto.setCodiceFiscale(identita.getCodiceFiscaleString());
		soggetto.setPartitaIva(identita.getPartitaIva());
		soggetto.setIdStas(new BigDecimal(identita.getIdSoggetto()));
		soggetto.setPersonaFisica(false);
		soggetto.setRagioneSociale(identita.getDenominazione());

		return soggetto;
	}

}
