/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.notifica.NotificaService;
import it.csi.conam.conambl.dispatcher.NotificaDispatcher;
import it.csi.conam.conambl.request.notifica.NotificaRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.notifica.ModalitaNotificaVO;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificaDispatcherImpl implements NotificaDispatcher {

	@Autowired
	private NotificaService notificaService;

	@Override
	public List<ModalitaNotificaVO> getModalitaNotifica() {
		return notificaService.getModalitaNotifica();
	}

	@Override
	public Integer salvaNotifica(NotificaVO notifica, UserDetails user) {
		return notificaService.salvaNotifica(notifica, user);
	}

	@Override
	public NotificaVO getNotificaBy(NotificaRequest notificaRequest) {
		return notificaService.getNotificaBy(notificaRequest);
	}

}
