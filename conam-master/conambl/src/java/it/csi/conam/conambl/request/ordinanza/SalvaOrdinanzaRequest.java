/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.ordinanza;

import it.csi.conam.conambl.request.SalvaAllegatoRequest;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import it.csi.conam.conambl.vo.ordinanza.MinOrdinanzaVO;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 07 feb 2019
 */
public class SalvaOrdinanzaRequest extends SalvaAllegatoRequest {

	private static final long serialVersionUID = 1946325489262854786L;

	private MinOrdinanzaVO ordinanza;
	private List<SoggettoOrdinanzaRequest> soggetti;
	private NotificaVO notifica;
	

	public MinOrdinanzaVO getOrdinanza() {
		return ordinanza;
	}

	public List<SoggettoOrdinanzaRequest> getSoggetti() {
		return soggetti;
	}

	public void setOrdinanza(MinOrdinanzaVO ordinanza) {
		this.ordinanza = ordinanza;
	}

	public void setSoggetti(List<SoggettoOrdinanzaRequest> soggetti) {
		this.soggetti = soggetti;
	}
	
	public NotificaVO getNotifica() {
		return notifica;
	}

	public void setNotifica(NotificaVO notifica) {
		this.notifica = notifica;
	}
}
