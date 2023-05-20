/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.common.MessageVO;

public class SalvaSollecitoPregressiResponse extends ParentVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -807485520497991489L;
	Integer idSollecito;
	MessageVO confirmMsg;
	String result;
	
	public Integer getIdSollecito() {
		return idSollecito;
	}
	public void setIdSollecito(Integer idSollecito) {
		this.idSollecito = idSollecito;
	}
	public MessageVO getConfirmMsg() {
		return confirmMsg;
	}
	public void setConfirmMsg(MessageVO confirmMsg) {
		this.confirmMsg = confirmMsg;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
}
