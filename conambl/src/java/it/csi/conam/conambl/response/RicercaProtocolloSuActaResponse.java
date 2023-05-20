/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.verbale.DocumentoProtocollatoVO;

import java.util.List;


//20200903_LC per gestione messaggio in caso di pregresso
public class RicercaProtocolloSuActaResponse extends ParentVO {
	
	private static final long serialVersionUID = -4060006032304909549L;
	
	private List<DocumentoProtocollatoVO> documentoProtocollatoVOList;
	
	private MessageVO messaggio;

	public List<DocumentoProtocollatoVO> getDocumentoProtocollatoVOList() {
		return documentoProtocollatoVOList;
	}

	public void setDocumentoProtocollatoVOList(List<DocumentoProtocollatoVO> documentoProtocollatoVOList) {
		this.documentoProtocollatoVOList = documentoProtocollatoVOList;
	}

	public MessageVO getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(MessageVO messaggio) {
		this.messaggio = messaggio;
	}
	
	
	
	
}
