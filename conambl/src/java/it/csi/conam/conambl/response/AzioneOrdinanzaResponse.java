/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

import it.csi.conam.conambl.vo.ParentVO;

/**
 * @author riccardo.bova
 * @date 21 nov 2018
 */
public class AzioneOrdinanzaResponse extends ParentVO {

	private static final long serialVersionUID = 5502699540493590617L;

	private Boolean aggiungiAllegatoEnable;
	private Boolean letteraDaCompilareEnable;
	private Boolean bollettiniDaInviareEnable;
	private Boolean downloadBollettiniEnable;
	private Boolean aggiungiNotificaEnable;
	private Boolean visualizzaNotificaEnable;

	public Boolean getAggiungiAllegatoEnable() {
		return aggiungiAllegatoEnable;
	}

	public void setAggiungiAllegatoEnable(Boolean aggiungiAllegatoEnable) {
		this.aggiungiAllegatoEnable = aggiungiAllegatoEnable;
	}

	public Boolean getLetteraDaCompilareEnable() {
		return letteraDaCompilareEnable;
	}

	public void setLetteraDaCompilareEnable(Boolean letteraDaCompilareEnable) {
		this.letteraDaCompilareEnable = letteraDaCompilareEnable;
	}

	public Boolean getBollettiniDaInviareEnable() {
		return bollettiniDaInviareEnable;
	}

	public Boolean getDownloadBollettiniEnable() {
		return downloadBollettiniEnable;
	}

	public void setBollettiniDaInviareEnable(Boolean bollettiniDaInviareEnable) {
		this.bollettiniDaInviareEnable = bollettiniDaInviareEnable;
	}

	public void setDownloadBollettiniEnable(Boolean downloadBollettiniEnable) {
		this.downloadBollettiniEnable = downloadBollettiniEnable;
	}

	public Boolean getAggiungiNotificaEnable() {
		return aggiungiNotificaEnable;
	}

	public void setAggiungiNotificaEnable(Boolean aggiungiNotificaEnable) {
		this.aggiungiNotificaEnable = aggiungiNotificaEnable;
	}

	public Boolean getVisualizzaNotificaEnable() {
		return visualizzaNotificaEnable;
	}

	public void setVisualizzaNotificaEnable(Boolean visualizzaNotificaEnable) {
		this.visualizzaNotificaEnable = visualizzaNotificaEnable;
	}

}
