/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.verbale;

import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.RuoloSoggettoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoPregressiVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

import java.util.List;

public interface SoggettoVerbaleService {

	SoggettoVO salvaSoggetto(Integer id, SoggettoVO soggetto, UserDetails userDetails);
	
	SoggettoVO salvaSoggettoPregressi(Integer id, SoggettoVO soggetto, UserDetails userDetails);

	SoggettoVO ricercaSoggetto(MinSoggettoVO minSoggettoVO, UserDetails userDetails);

	SoggettoPregressiVO ricercaSoggettoPregressi(MinSoggettoVO minSoggettoVO, UserDetails userDetails);
	
	SoggettoVO ricercaSoggettoPerPIva(MinSoggettoVO minSoggettoVO, UserDetails userDetails);

	void eliminaSoggettoByIdVerbaleSoggetto(Integer id, UserDetails userDetails);

	List<RuoloSoggettoVO> getRuoliSoggetto();

	List<SoggettoVO> getSoggettiByIdVerbale(Integer id, UserDetails userDetails, Boolean includiControlloUtenteProprietario);
	
	List<SoggettoVO> getSoggettiByIdVerbalePregressi(Integer id, UserDetails userDetails, Boolean includiControlloUtenteProprietario);

	List<SoggettoVO> getSoggettiByIdVerbaleConvocazione(Integer id, UserDetails userDetails, Boolean includiControlloUtenteProprietario);

	List<SoggettoVO> getSoggettiByIdVerbaleAudizione(Integer id);

	SoggettoPregressiVO ricercaSoggettoPerPIvaPregressi(MinSoggettoVO minSoggettoVO, UserDetails userDetails);

}
