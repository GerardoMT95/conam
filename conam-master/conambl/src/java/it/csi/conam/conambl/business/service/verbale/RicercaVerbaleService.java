/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.verbale;

import it.csi.conam.conambl.request.verbale.RicercaVerbaleRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.verbale.MinVerbaleVO;
import it.csi.conam.conambl.vo.verbale.StatoManualeVO;
import it.csi.conam.conambl.vo.verbale.StatoVerbaleVO;

import java.util.List;

public interface RicercaVerbaleService {

	List<StatoVerbaleVO> getStatiRicercaVerbale();

	List<MinVerbaleVO> ricercaVerbale(RicercaVerbaleRequest request, UserDetails userDetails);

	List<StatoManualeVO> getStatiManuali();
}
