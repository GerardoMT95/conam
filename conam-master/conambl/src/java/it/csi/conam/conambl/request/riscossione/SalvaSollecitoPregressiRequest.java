/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.riscossione;

import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;

public class SalvaSollecitoPregressiRequest extends SalvaAllegatiProtocollatiRequest  {
	
	private static final long serialVersionUID = 1L;
	
	private SollecitoVO sollecito;
	private NotificaVO notifica;
	
	public SollecitoVO getSollecito() {
		return sollecito;
	}
	public void setSollecito(SollecitoVO sollecito) {
		this.sollecito = sollecito;
	}
	public NotificaVO getNotifica() {
		return notifica;
	}
	public void setNotifica(NotificaVO notifica) {
		this.notifica = notifica;
	}


}
