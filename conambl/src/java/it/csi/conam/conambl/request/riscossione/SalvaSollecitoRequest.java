/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.riscossione;

import it.csi.conam.conambl.vo.notifica.NotificaVO;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;

public class SalvaSollecitoRequest {
	
	private SollecitoVO sollecito;
	private NotificaVO notifica;
	private Integer idPianoRateizzazione;
	
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
	public Integer getIdPianoRateizzazione() {
		return idPianoRateizzazione;
	}
	public void setIdPianoRateizzazione(Integer idPianoRateizzazione) {
		this.idPianoRateizzazione = idPianoRateizzazione;
	}

}
