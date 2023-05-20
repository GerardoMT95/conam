/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.verbale;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.common.MessageVO;

/**
 * 
 * @author A135722
 *
 * creato oggetto di response per gestire messaggio di success configurabile
 */
public class SalvaVerbalePregressiResponse extends ParentVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -807485520497991489L;
	
	Integer idVerbale;
	MessageVO confirmMsg;
	
	public Integer getIdVerbale() {
		return idVerbale;
	}
	public void setIdVerbale(Integer idVerbale) {
		this.idVerbale = idVerbale;
	}
	public MessageVO getConfirmMsg() {
		return confirmMsg;
	}
	public void setConfirmMsg(MessageVO confirmMsg) {
		this.confirmMsg = confirmMsg;
	}
	
	
}
