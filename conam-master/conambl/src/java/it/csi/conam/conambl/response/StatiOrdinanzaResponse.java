/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.ordinanza.StatoOrdinanzaVO;

import java.util.List;

public class StatiOrdinanzaResponse extends ParentVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2011900913976331124L;

	private MessageVO messaggio;
	private List<StatoOrdinanzaVO> stati;
	
	public MessageVO getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(MessageVO messaggio) {
		this.messaggio = messaggio;
	}
	public List<StatoOrdinanzaVO> getStati() {
		return stati;
	}
	public void setStati(List<StatoOrdinanzaVO> stati) {
		this.stati = stati;
	}
	
	
}
