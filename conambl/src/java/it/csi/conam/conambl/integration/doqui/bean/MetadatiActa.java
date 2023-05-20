/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.bean;

import java.io.Serializable;

public class MetadatiActa implements Serializable 
{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6556205213964288356L;
	private String 			idEntitaFruitore;
	private String 			codiceFiscale;
	private String 			targa;
	
	private String numeroRegistrazionePrecedente;
	private String annoRegistrazionePrecedente;
	
	private String scrittore;
	private String destinatarioFisico;
	private String destinatarioGiuridico;
	private String descrizioneTipoLettera;
	
	public String getScrittore() {
		return scrittore;
	}
	public void setScrittore(String scrittore) {
		this.scrittore = scrittore;
	}
	public String getDestinatarioFisico() {
		return destinatarioFisico;
	}
	public void setDestinatarioFisico(String destinatarioFisico) {
		this.destinatarioFisico = destinatarioFisico;
	}
	public String getDestinatarioGiuridico() {
		return destinatarioGiuridico;
	}
	public void setDestinatarioGiuridico(String destinatarioGiuridico) {
		this.destinatarioGiuridico = destinatarioGiuridico;
	}
	public String getNumeroRegistrazionePrecedente() {
		return numeroRegistrazionePrecedente;
	}
	public void setNumeroRegistrazionePrecedente(
			String numeroRegistrazionePrecedente) {
		this.numeroRegistrazionePrecedente = numeroRegistrazionePrecedente;
	}
	public String getAnnoRegistrazionePrecedente() {
		return annoRegistrazionePrecedente;
	}
	public void setAnnoRegistrazionePrecedente(String annoRegistrazionePrecedente) {
		this.annoRegistrazionePrecedente = annoRegistrazionePrecedente;
	}
	public String getIdEntitaFruitore()
	{
		return idEntitaFruitore;
	}
	public void setIdEntitaFruitore(String idEntitaFruitore)
	{
		this.idEntitaFruitore = idEntitaFruitore;
	}
	public String getCodiceFiscale()
	{
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) 
	{
		this.codiceFiscale = codiceFiscale;
	}
	public String getTarga()
	{
		return targa;
	}
	public void setTarga(String targa)
	{
		this.targa = targa;
	}
	
	public String getDescrizioneTipoLettera() {
		return descrizioneTipoLettera;
	}
	public void setDescrizioneTipoLettera(String descrizioneTipoLettera) {
		this.descrizioneTipoLettera = descrizioneTipoLettera;
	}
	
	public String toString()
	{
		String result = null;
		
		result = getIdEntitaFruitore();
		if (result == null)
			result = getCodiceFiscale();
		else
			if (getCodiceFiscale() != null)
				result = result + " - " + getCodiceFiscale();
		if (result == null)
			result = getTarga();
		else
			if (getTarga() != null)
				result = result + " - " + getTarga();
		
		
		return result;
	}
	
	
	
	
}
