/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service;

import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.ProfiloVO;

/***
 *
 * @author Riccardo Bova
 * @date 12 nov 2018
 ***/

public interface UserService {

	 ProfiloVO getProfilo(UserDetails userDetails);

}
