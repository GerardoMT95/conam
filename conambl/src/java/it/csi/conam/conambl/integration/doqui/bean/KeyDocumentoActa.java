/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.bean;

import java.io.Serializable;

public class KeyDocumentoActa implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1925287595977552532L;
	
	private String 	UUIDDocumento;
	private String 	numeroProtocollo;
	private String  parolaChiave;
	private String 	classificazioneId;
	private String 	registrazioneId;
	private String  objectIdClassificazione;
	private String  objectIdDocumento;
	private String indiceClassificazione;
	
	// 20201123_LC
	private String idFolderCreated;
	
	
	public String getUUIDDocumento() 
	{
		return UUIDDocumento;
	}

	public void setUUIDDocumento(String uUIDDocumento) 
	{
		UUIDDocumento = uUIDDocumento;
	}

	public KeyDocumentoActa(String parolaChiave) 
	{
		super();
		this.parolaChiave = parolaChiave;
	}
	
	// 20201123_LC per risultato di sposta
	public KeyDocumentoActa(String idFolderCreated, String objectIdClassificazione) {
		super();
		this.idFolderCreated = idFolderCreated;	
		this.objectIdClassificazione = objectIdClassificazione;		
		
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	public String getParolaChiave() {
		return parolaChiave;
	}
	public void setParolaChiave(String parolaChiave) {
		this.parolaChiave = parolaChiave;
	}
	
	public String getClassificazioneId()
	{
		return classificazioneId;
	}
	
	public void setClassificazioneId(String classificazioneId)
	{
		this.classificazioneId = classificazioneId;
	}
	
	public String getRegistrazioneId()
	{
		return registrazioneId;
	}
	
	public void setRegistrazioneId(String registrazioneId) 
	{
		this.registrazioneId = registrazioneId;
	}

	public String getObjectIdClassificazione() {
		return objectIdClassificazione;
	}

	public void setObjectIdClassificazione(String objectIdClassificazione) {
		this.objectIdClassificazione = objectIdClassificazione;
	}

	public String getObjectIdDocumento() {
		return objectIdDocumento;
	}

	public void setObjectIdDocumento(String objectIdDocumento) {
		this.objectIdDocumento = objectIdDocumento;
	}

	public String getIndiceClassificazione() {
		return indiceClassificazione;
	}

	public void setIndiceClassificazione(String indiceClassificazione) {
		this.indiceClassificazione = indiceClassificazione;
	}

	public String getIdFolderCreated() {
		return idFolderCreated;
	}

	public void setIdFolderCreated(String idFolderCreated) {
		this.idFolderCreated = idFolderCreated;
	}
	
	
	
}
