/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.request.scrittodifensivo.RicercaScrittoDifensivoRequest;
import it.csi.conam.conambl.response.SalvaScrittoDifensivoResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.verbale.ScrittoDifensivoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import java.util.List;

public interface ScrittoDifensivoDispatcher {
	
	SalvaScrittoDifensivoResponse salvaScrittoDifensivo(List<InputPart> data, List<InputPart> file, UserDetails userDetails);
	
	ScrittoDifensivoVO dettaglioScrittoDifensivo(Integer idScrittoDifensivo, UserDetails userDetails);

	List<ScrittoDifensivoVO> ricercaScrittoDifensivo(RicercaScrittoDifensivoRequest request);
	
	void associaScrittoDifensivo(Integer idScrittoDifensivo, Integer idVerbale, UserDetails userDetails);

}
