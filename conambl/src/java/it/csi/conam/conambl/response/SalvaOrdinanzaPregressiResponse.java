/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.common.MessageVO;

public class SalvaOrdinanzaPregressiResponse extends ParentVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -807485520497991489L;
	Integer idOrdinanza;
	MessageVO confirmMsg;
	
	// 20210118 JIRA 110
	String numDeterminazione;
	
	public Integer getIdOrdinanza() {
		return idOrdinanza;
	}
	public void setIdOrdinanza(Integer idOrdinanza) {
		this.idOrdinanza = idOrdinanza;
	}
	public MessageVO getConfirmMsg() {
		return confirmMsg;
	}
	public void setConfirmMsg(MessageVO confirmMsg) {
		this.confirmMsg = confirmMsg;
	}
	public String getNumDeterminazione() {
		return numDeterminazione;
	}
	public void setNumDeterminazione(String numDeterminazione) {
		this.numDeterminazione = numDeterminazione;
	}
	
	
}
