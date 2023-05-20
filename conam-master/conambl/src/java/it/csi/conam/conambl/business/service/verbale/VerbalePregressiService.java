/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.verbale;

import it.csi.conam.conambl.common.exception.VerbalePregressoValidationException;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.verbale.VerbaleVO;

public interface VerbalePregressiService {

	Integer salvaVerbale(VerbaleVO verbale, UserDetails userDetails) throws VerbalePregressoValidationException;
	
	void checkDatiVerbale(VerbaleVO verbale, UserDetails userDetails) throws VerbalePregressoValidationException;

	VerbaleVO getVerbaleById(Integer id, UserDetails userDetails, boolean includeEliminati, boolean b, boolean c);

}
