/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.common;

import it.csi.conam.conambl.vo.ParentVO;

public class MessageVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2973985886556102874L;
	
	private String message;
	private String type;
	
	
	public MessageVO(String message, String type) {
		super();
		this.message = message;
		this.type = type;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	@Override
	public String toString() {
		return "MessageVO [message=" + message + ", type=" + type + "]";
	}

}
