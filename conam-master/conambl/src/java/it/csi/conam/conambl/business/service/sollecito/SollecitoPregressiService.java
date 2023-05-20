/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.sollecito;

import it.csi.conam.conambl.request.riscossione.SalvaSollecitoPregressiRequest;
import it.csi.conam.conambl.response.SalvaSollecitoPregressiResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;
import it.csi.conam.conambl.vo.sollecito.StatoSollecitoVO;

import java.util.List;


public interface SollecitoPregressiService {
	
	// modificata
	SollecitoVO getSollecitoById(Integer id);
	
	// modificata
	List<SollecitoVO> getSollecitiByIdOrdinanzaSoggetto(Integer idOrdinanzaVerbaleSoggetto);

	// modificata
	SalvaSollecitoPregressiResponse salvaSollecito(SalvaSollecitoPregressiRequest salvaSollecitoPregressiRequest, UserDetails userDetails);

	// nuova
	List<StatoSollecitoVO> getStatiSollecitoPregressi(Integer id);

	// nuova
	List<SollecitoVO> getSollecitiByOrdinanza(Integer id);
	
	// nuova
	SalvaSollecitoPregressiResponse checkSalvaSollecito(SalvaSollecitoPregressiRequest salvaSollecitoPregressiRequest, UserDetails userDetails);	
	
	
}
