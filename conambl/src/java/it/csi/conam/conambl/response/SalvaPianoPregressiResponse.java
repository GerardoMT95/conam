/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.common.MessageVO;

public class SalvaPianoPregressiResponse extends ParentVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -807485520497991489L;
	Integer idPiano;
	MessageVO confirmMsg;
	
	public Integer getIdPiano() {
		return idPiano;
	}
	public void setIdPiano(Integer idPiano) {
		this.idPiano = idPiano;
	}
	public MessageVO getConfirmMsg() {
		return confirmMsg;
	}
	public void setConfirmMsg(MessageVO confirmMsg) {
		this.confirmMsg = confirmMsg;
	}
	
	
}
