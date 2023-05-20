/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.bean;

import java.io.Serializable;

public class SoggettoActa implements Serializable 
{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3372847312731808307L;
	
	private String cognome;
	private String nome;
	private String denominazione;

	private boolean mittente;  //TRUE se mittente FALSE se destinatario
	
	
	public void setMittente(boolean mittente) {
		this.mittente = mittente;
	}
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	public boolean isMittente()
	{
		return this.mittente;
	}
	
	public boolean isDestinatario()
	{
		return !this.mittente;
	}
	
	
}
