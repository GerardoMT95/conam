/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.ProfiloVO;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author riccardo.bova
 * @date 23 mar 2018
 */
public interface UserDispatcher {

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	ProfiloVO getProfilo(UserDetails userDetails);

}
