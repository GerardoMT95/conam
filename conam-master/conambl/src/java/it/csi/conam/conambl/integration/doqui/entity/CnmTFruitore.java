/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.entity;

import java.io.Serializable;

public class CnmTFruitore implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long idFruitore;

	private String codFruitore;

	private String descrFruitore;

	private String cfActa;

	private String idAooActa;

	private String idStrutturaActa;

	private String idNodoActa;

	private String repositoryIndex;

	private String usernameIndex;

	private String passwordIndex;
	
	
	
	// 20200629_LC
	private String fruitoreIndex;

	private String customModelIndex;
	
	public String getFruitoreIndex() {
		return fruitoreIndex;
	}

	public void setFruitoreIndex(String fruitoreIndex) {
		this.fruitoreIndex = fruitoreIndex;
	}

	public String getCustomModelIndex() {
		return customModelIndex;
	}

	public void setCustomModelIndex(String customModelIndex) {
		this.customModelIndex = customModelIndex;
	}

	
	public CnmTFruitore(long idFruitore, String codFruitore, String descrFruitore, String cfActa, String idAooActa,
			String idStrutturaActa, String idNodoActa, String repositoryIndex, String usernameIndex,
			String passwordIndex, String fruitoreIndex, String customModelIndex) {
		super();
		this.idFruitore = idFruitore;
		this.codFruitore = codFruitore;
		this.descrFruitore = descrFruitore;
		this.cfActa = cfActa;
		this.idAooActa = idAooActa;
		this.idStrutturaActa = idStrutturaActa;
		this.idNodoActa = idNodoActa;
		this.repositoryIndex = repositoryIndex;
		this.usernameIndex = usernameIndex;
		this.passwordIndex = passwordIndex;
		this.fruitoreIndex = fruitoreIndex;
		this.customModelIndex = customModelIndex;
	}

	public long getIdFruitore() {
		return idFruitore;
	}

	public void setIdFruitore(long idFruitore) {
		this.idFruitore = idFruitore;
	}

	public String getCodFruitore() {
		return codFruitore;
	}

	public void setCodFruitore(String codFruitore) {
		this.codFruitore = codFruitore;
	}

	public String getDescrFruitore() {
		return descrFruitore;
	}

	public void setDescrFruitore(String descrFruitore) {
		this.descrFruitore = descrFruitore;
	}

	public String getCfActa() {
		return cfActa;
	}

	public void setCfActa(String cfActa) {
		this.cfActa = cfActa;
	}

	public String getIdAooActa() {
		return idAooActa;
	}

	public void setIdAooActa(String idAooActa) {
		this.idAooActa = idAooActa;
	}

	public String getIdStrutturaActa() {
		return idStrutturaActa;
	}

	public void setIdStrutturaActa(String idStrutturaActa) {
		this.idStrutturaActa = idStrutturaActa;
	}

	public String getIdNodoActa() {
		return idNodoActa;
	}

	public void setIdNodoActa(String idNodoActa) {
		this.idNodoActa = idNodoActa;
	}

	public String getRepositoryIndex() {
		return repositoryIndex;
	}

	public void setRepositoryIndex(String repositoryIndex) {
		this.repositoryIndex = repositoryIndex;
	}

	public String getUsernameIndex() {
		return usernameIndex;
	}

	public void setUsernameIndex(String usernameIndex) {
		this.usernameIndex = usernameIndex;
	}

	public String getPasswordIndex() {
		return passwordIndex;
	}

	public void setPasswordIndex(String passwordIndex) {
		this.passwordIndex = passwordIndex;
	}

}
