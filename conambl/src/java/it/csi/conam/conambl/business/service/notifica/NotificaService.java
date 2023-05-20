/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.notifica;

import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmTPianoRate;
import it.csi.conam.conambl.integration.entity.CnmTSollecito;
import it.csi.conam.conambl.request.notifica.NotificaRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.notifica.ModalitaNotificaVO;
import it.csi.conam.conambl.vo.notifica.NotificaVO;

import java.util.List;

public interface NotificaService {

	List<ModalitaNotificaVO> getModalitaNotifica();

	NotificaVO getNotificaBy(NotificaRequest NotificaRequest);

	Integer salvaNotifica(NotificaVO notifica, UserDetails user);

	Boolean isNotificaCreata(CnmTOrdinanza cnmTOrdinanza);

	Boolean isNotificaCreata(CnmTPianoRate cnmTPianoRate);

	Boolean isNotificaCreata(CnmTSollecito cnmTSollecito);

}
