/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTPersona;
import it.csi.conam.conambl.integration.entity.CnmTSocieta;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UtilsSoggetto {

	public static SoggettoVO findSoggetto(Collection<SoggettoVO> soggetti, Predicate<SoggettoVO> predicate) {
		return Iterables.tryFind(soggetti, predicate).orNull();
	}

	public static Predicate<SoggettoVO> findByIdSoggetto(Integer idSoggetto) {
		return new Predicate<SoggettoVO>() {
			@Override
			public boolean apply(SoggettoVO input) {
				return input.getId().intValue() == idSoggetto;
			}
		};
	}

	public static List<String> convertSoggettiToListString(List<CnmRVerbaleSoggetto> soggetti, Long tipoSoggettoAmmesso) {
		if (soggetti == null || soggetti.isEmpty())
			return null;
		List<String> nomeCognome = new ArrayList<>();

		for (CnmRVerbaleSoggetto cnmRVerbaleSoggetto : soggetti) {
			CnmTSoggetto cnmTSoggetto = cnmRVerbaleSoggetto.getCnmTSoggetto();
			CnmTPersona cnmTPersona = cnmTSoggetto.getCnmTPersona();
			CnmTSocieta cnmTSocieta = cnmTSoggetto.getCnmTSocieta();
			String nomeSoggetto = null;

			if (cnmTPersona != null) {
				nomeSoggetto = cnmTSoggetto.getCognome() + "  " + cnmTSoggetto.getNome();
			} else if (cnmTSocieta != null) {
				nomeSoggetto = cnmTSoggetto.getRagioneSociale();
			} else if (cnmTSoggetto.getIdStas() != null) {
				if (StringUtils.isBlank(cnmTSoggetto.getCodiceFiscale()))
					nomeSoggetto = cnmTSoggetto.getRagioneSociale();
				else
					nomeSoggetto = cnmTSoggetto.getCognome() + "  " + cnmTSoggetto.getNome();
			}

			if (tipoSoggettoAmmesso == null || tipoSoggettoAmmesso == cnmRVerbaleSoggetto.getCnmDRuoloSoggetto().getIdRuoloSoggetto())
				nomeCognome.add(nomeSoggetto);

		}
		return nomeCognome;

	}

}
