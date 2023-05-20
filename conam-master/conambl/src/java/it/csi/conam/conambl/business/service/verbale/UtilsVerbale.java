/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.verbale;

import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.leggi.RiferimentiNormativiVO;
import it.csi.conam.conambl.vo.verbale.VerbaleVO;

import java.sql.Timestamp;
import java.util.List;

public interface UtilsVerbale {
	CnmTVerbale validateAndGetCnmTVerbale(Integer idVerbale);

	CnmDEnte getEnteAccertatore(VerbaleVO verbale, UserDetails user);

	CnmRVerbaleIllecito getNewCnmRVerbaleIllecito(CnmTUser cnmTUser, CnmTVerbale cnmTVerbale, Timestamp now, RiferimentiNormativiVO rif);
	
	public List<CnmTAllegato> filtraAllegatiPerVerbale(Integer idVerbale, List<CnmTAllegato> allegatiDaFiltrare);
}
