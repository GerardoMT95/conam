/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.common.MessageVO;

public class SalvaScrittoDifensivoResponse extends ParentVO{

	private static final long serialVersionUID = -807485520497991489L;
	
	Integer idScrittoDifensivo;
	MessageVO errorMsg;
	


	public Integer getIdScrittoDifensivo() {
		return idScrittoDifensivo;
	}
	public void setIdScrittoDifensivo(Integer idScrittoDifensivo) {
		this.idScrittoDifensivo = idScrittoDifensivo;
	}
	public MessageVO getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(MessageVO errorMsg) {
		this.errorMsg = errorMsg;
	}

	
	
}
