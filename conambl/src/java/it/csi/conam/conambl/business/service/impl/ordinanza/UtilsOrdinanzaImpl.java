/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.ordinanza;

import it.csi.conam.conambl.business.service.ordinanza.UtilsOrdinanza;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.repositories.CnmROrdinanzaVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleSoggettoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTOrdinanzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilsOrdinanzaImpl implements UtilsOrdinanza {

	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	//2021-12-15 - LUCIO ROSADINI
	/*@Autowired
	private MessaggioService messaggioService;*/

	@Override
	public CnmTOrdinanza validateAndGetCnmTOrdinanza(Integer idOrdinanza) {
		if (idOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza = null");
		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza non valido ");

		return cnmTOrdinanza;
	}
	
	@Override
	public boolean gestisciOrdinanzaComePregressa(Integer idOrdinanza) {
		if (idOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza = null");
		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza non valido ");

		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmROrdinanzaVerbSogsIn(cnmROrdinanzaVerbSogList);
		CnmTVerbale cnmTVerbale = cnmRVerbaleSoggettoList.get(0).getCnmTVerbale();
		
		return cnmTOrdinanza.isFlagDocumentoPregresso() && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso()==Constants.STATO_PREGRESSO_IN_LAVORAZIONE;
	}
	
	@Override
	public boolean soggettiVerbaleCompletiDiOrdinanza(CnmTVerbale cnmTVerbale) {
		List<CnmRVerbaleSoggetto>cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettoList);
		return cnmRVerbaleSoggettoList.size() == cnmROrdinanzaVerbSogList.size();
	}

	@Override
	public void isCFValid(CnmTOrdinanza cnmTOrdinanza) throws BusinessException {
		/*---------2021-12-15 - LUCIO ROSADINI - CONTROLLO CODICE FISCALE---------*/
		for (CnmROrdinanzaVerbSog ovs: cnmTOrdinanza.getCnmROrdinanzaVerbSogs())
			if (ovs.getCnmRVerbaleSoggetto()!= null && ovs.getCnmRVerbaleSoggetto().getCnmTSoggetto()!= null)
				testCodiceFiscale(ovs.getCnmRVerbaleSoggetto().getCnmTSoggetto().getCodiceFiscale());
	}

	@Override
	public void isCFValid(List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList) throws BusinessException {
		/*---------2021-12-15 - LUCIO ROSADINI - CONTROLLO CODICE FISCALE---------*/
		for (CnmRVerbaleSoggetto cnmRVerbaleSoggetto: cnmRVerbaleSoggettoList)
			if (cnmRVerbaleSoggetto.getCnmTSoggetto()!= null)
				testCodiceFiscale(cnmRVerbaleSoggetto.getCnmTSoggetto().getCodiceFiscale());
	}

	@Override
	public void isCFValidBySoggRata(List<CnmRSoggRata> cnmRSoggRataList) throws BusinessException {
		/*---------2021-12-15 - LUCIO ROSADINI - CONTROLLO CODICE FISCALE---------*/
		for (CnmRSoggRata cnmRSoggRata: cnmRSoggRataList){
			if (
				cnmRSoggRata.getCnmROrdinanzaVerbSog() != null &&
				cnmRSoggRata.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto()!= null &&
				cnmRSoggRata.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTSoggetto()!= null
			)
				testCodiceFiscale(cnmRSoggRata.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTSoggetto().getCodiceFiscale());
		}

	}
	private void testCodiceFiscale(String codiceFiscale){
		if (codiceFiscale == null || codiceFiscale.isEmpty()) throw new BusinessException(ErrorCode.NO_COD_FIS);
	}
}
