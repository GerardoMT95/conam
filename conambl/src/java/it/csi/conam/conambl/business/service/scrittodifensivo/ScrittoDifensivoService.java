/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.scrittodifensivo;

import it.csi.conam.conambl.integration.entity.CnmTScrittoDifensivo;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.response.SalvaScrittoDifensivoResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.verbale.ScrittoDifensivoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import java.util.List;

public interface ScrittoDifensivoService {
	
	SalvaScrittoDifensivoResponse salvaScrittoDifensivo(List<InputPart> data, List<InputPart> file, UserDetails userDetails);
	
	ScrittoDifensivoVO dettaglioScrittoDifensivo(Integer idScrittoDifensivo, UserDetails userDetails);
	
	void associaScrittoDifensivo(CnmTScrittoDifensivo cnmTScrittoDifensivo, CnmTVerbale cnmTVerbale, CnmTUser cnmTUser);
	
	void associaScrittoDifensivoById(Integer idScrittoDifensivo, Integer idVerbale, UserDetails userDetails);
}
