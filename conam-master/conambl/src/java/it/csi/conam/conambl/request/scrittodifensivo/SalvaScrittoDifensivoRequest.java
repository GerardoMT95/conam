/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.scrittodifensivo;

import it.csi.conam.conambl.request.ParentRequest;
import it.csi.conam.conambl.vo.verbale.DocumentoProtocollatoVO;
import it.csi.conam.conambl.vo.verbale.ScrittoDifensivoVO;

// 20210317_LC lotto2scen1
public class SalvaScrittoDifensivoRequest extends ParentRequest {

	private static final long serialVersionUID = -3620699452083779279L;

	// in caso di doc da FS 
	private byte[] file;	

	// in caso di doc da Acta
	private DocumentoProtocollatoVO documentoProtocollato;

	// scrittoVO
	private ScrittoDifensivoVO scrittoDifensivo;
	
	
	

	
	

	public byte[] getFile() {
		return file;
	}


	public void setFile(byte[] file) {
		this.file = file;
	}


	public DocumentoProtocollatoVO getDocumentoProtocollato() {
		return documentoProtocollato;
	}


	public void setDocumentoProtocollato(DocumentoProtocollatoVO documentoProtocollato) {
		this.documentoProtocollato = documentoProtocollato;
	}


	public ScrittoDifensivoVO getScrittoDifensivo() {
		return scrittoDifensivo;
	}


	public void setScrittoDifensivo(ScrittoDifensivoVO scrittoDifensivo) {
		this.scrittoDifensivo = scrittoDifensivo;
	}





}

