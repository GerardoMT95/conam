/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.UserService;
import it.csi.conam.conambl.dispatcher.UserDispatcher;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.ProfiloVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Component
public class UserDispatcherImpl implements UserDispatcher {

	@Autowired
	private UserService userService;

	@Override
	public ProfiloVO getProfilo(UserDetails userDetails) {
		return userService.getProfilo(userDetails);
	}


}
