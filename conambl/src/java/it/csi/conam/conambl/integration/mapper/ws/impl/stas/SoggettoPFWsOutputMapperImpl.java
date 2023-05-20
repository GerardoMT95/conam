/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.ws.impl.stas;

import it.csi.conam.conambl.integration.mapper.ws.stas.CommonSoggettoWsOutputMapper;
import it.csi.conam.conambl.integration.mapper.ws.stas.SoggettoPFWsOutputMapper;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.gmscore.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class SoggettoPFWsOutputMapperImpl implements SoggettoPFWsOutputMapper {

	@Autowired
	private CommonSoggettoWsOutputMapper commonSoggettoWsOutputMapper;

	@Override
	public SoggettoVO mapWsTypeToVO(SoggettoPF wsType) {
		if (wsType == null)
			return null;
		SoggettoVO soggetto = new SoggettoVO();
		IdentitaPersonaFisica identita = wsType.getIdentitaPersonaFisica();
		DateGMS wsDataNascita = wsType.getDataNascita();
		Luogo luogoNascita = identita != null ? identita.getLuogoNascita() : null;

		// capita in alcuni casi
		if (identita == null || identita.getIdSoggetto() == null)
			return null;

		soggetto.setIdStas(new BigDecimal(identita.getIdSoggetto()));

		LocalDate dataNascita = LocalDate.of(Integer.parseInt(wsDataNascita.getAnno()), Integer.parseInt(wsDataNascita.getMese()), Integer.parseInt(wsDataNascita.getGiorno()));
		soggetto.setDataNascita(dataNascita);

		IndirizzoNazionale indirizzoResidenza = null;
		if (wsType.getIndirizzoPrimario() instanceof IndirizzoNazionale) {
			indirizzoResidenza = (IndirizzoNazionale) wsType.getIndirizzoPrimario();
		}
		commonSoggettoWsOutputMapper.mapResidenza(soggetto, indirizzoResidenza);

		if (luogoNascita != null) {
			if (luogoNascita.getNazione().equals("ITALIA")) {
				commonSoggettoWsOutputMapper.mapLuogoNascita(soggetto, luogoNascita);
			} else {
				commonSoggettoWsOutputMapper.mapLuogoOrigineEstero(soggetto, luogoNascita);
			}
		}

		soggetto.setCodiceFiscale(identita.getCodiceFiscaleString());
		soggetto.setCognome(wsType.getCognome());
		soggetto.setNome(wsType.getNome());
		soggetto.setPersonaFisica(true);
		soggetto.setSesso(wsType.getSesso());

		return soggetto;
	}

}
