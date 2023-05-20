/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.request.notifica.NotificaRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.notifica.ModalitaNotificaVO;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface NotificaDispatcher {

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<ModalitaNotificaVO> getModalitaNotifica();

	@PreAuthorize(value = AuthorizationRoles.RICERCA_NOTIFICHE)
	Integer salvaNotifica(NotificaVO notifica, UserDetails user);

	@PreAuthorize(value = AuthorizationRoles.RICERCA_NOTIFICHE)
	NotificaVO getNotificaBy(NotificaRequest notificaRequest);

}
