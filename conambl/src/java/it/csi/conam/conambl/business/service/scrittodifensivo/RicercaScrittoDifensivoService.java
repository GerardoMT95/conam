/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.scrittodifensivo;

import it.csi.conam.conambl.request.scrittodifensivo.RicercaScrittoDifensivoRequest;
import it.csi.conam.conambl.vo.verbale.ScrittoDifensivoVO;

import java.util.List;

public interface RicercaScrittoDifensivoService {

	List<ScrittoDifensivoVO> ricercaScrittoDifensivo(RicercaScrittoDifensivoRequest request);
	
}
