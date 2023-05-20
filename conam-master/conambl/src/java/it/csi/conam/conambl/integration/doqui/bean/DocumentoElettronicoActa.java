/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.bean;

import java.io.Serializable;
import java.util.Arrays;

public class DocumentoElettronicoActa extends DocumentoActa implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7468115589042440306L;
	
	private byte[] stream;
	private String nomeFile;
	private String descrizione;
	private String mimeType;
	
	private String idDocFisico;
	
	public byte[] getStream() 
	{
		return stream;
	}
	
	public void setStream(byte[] stream)
	{
		this.stream = stream;
	}
	
	public String getNomeFile()
	{
		return nomeFile;
	}
	
	public void setNomeFile(String nomeFile)
	{
		this.nomeFile = nomeFile;
	}

	public String getDescrizione() 
	{
		return descrizione;
	}

	public void setDescrizione(String descrizione) 
	{
		this.descrizione = descrizione;
	}

	public String getMimeType() 
	{
		return mimeType;
	}

	public void setMimeType(String mimeType) 
	{
		this.mimeType = mimeType;
	}


	private String nomeFileXSL;
	private byte[] fileXSL;

	public String getNomeFileXSL() {
		return nomeFileXSL;
	}

	public void setNomeFileXSL(String nomeFileXSL) {
		this.nomeFileXSL = nomeFileXSL;
	}

	public byte[] getFileXSL() {
		return fileXSL;
	}

	public void setFileXSL(byte[] fileXSL) {
		this.fileXSL = fileXSL;
	}

	public String getIdDocFisico() {
		return idDocFisico;
	}

	public void setIdDocFisico(String idDocFisico) {
		this.idDocFisico = idDocFisico;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DocumentoElettronicoActa [stream=");
		builder.append(Arrays.toString(stream));
		builder.append(", nomeFile=");
		builder.append(nomeFile);
		builder.append(", descrizione=");
		builder.append(descrizione);
		builder.append(", mimeType=");
		builder.append(mimeType);
		builder.append(", idDocFisico=");
		builder.append(idDocFisico);
		builder.append(", nomeFileXSL=");
		builder.append(nomeFileXSL);
		builder.append(", fileXSL=");
		builder.append(Arrays.toString(fileXSL));
		builder.append("]");
		return builder.toString();
	}

	
	
	
}
