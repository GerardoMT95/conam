/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.bean;

import java.io.Serializable;
import java.util.List;

public class DocumentoProtocollatoActa extends DocumentoElettronicoActa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String classificazioneEstesa;
	private String dataProtocollo;
	
	// 20200707_LC
	private String 			folderId;
	
	// 20200711_LC
	private String parolaChiave;
	private String uiidDocumento;

	// 20200825_LC
	private List<String> filenamesDocMultiplo;
	
	
	
	
	
	public String getClassificazioneEstesa() {
		return classificazioneEstesa;
	}
	public void setClassificazioneEstesa(String classificazioneEstesa) {
		this.classificazioneEstesa = classificazioneEstesa;
	}
	public String getDataProtocollo() {
		return dataProtocollo;
	}
	public void setDataProtocollo(String dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	public String getParolaChiave() {
		return parolaChiave;
	}
	public void setParolaChiave(String parolaChiave) {
		this.parolaChiave = parolaChiave;
	}
	public String getUiidDocumento() {
		return uiidDocumento;
	}
	public void setUiidDocumento(String uiidDocumento) {
		this.uiidDocumento = uiidDocumento;
	}
	public List<String> getFilenamesDocMultiplo() {
		return filenamesDocMultiplo;
	}
	public void setFilenamesDocMultiplo(List<String> filenamesDocMultiplo) {
		this.filenamesDocMultiplo = filenamesDocMultiplo;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DocumentoProtocollatoActa [classificazioneEstesa=");
		builder.append(classificazioneEstesa);
		builder.append(", dataProtocollo=");
		builder.append(dataProtocollo);
		builder.append(", folderId=");
		builder.append(folderId);
		builder.append(", parolaChiave=");
		builder.append(parolaChiave);
		builder.append(", uiidDocumento=");
		builder.append(uiidDocumento);
		builder.append(", filenamesDocMultiplo=");
		builder.append(filenamesDocMultiplo);
		builder.append("]");
		return builder.toString();
	}


	
	
	

	
	
	}
	
	
