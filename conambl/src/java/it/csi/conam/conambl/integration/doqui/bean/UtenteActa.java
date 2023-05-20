/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.bean;

import java.io.Serializable;

public class UtenteActa implements Serializable 
{

	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8848178621232944177L;

	private String 		codiceFiscale;
	private Integer 	idAoo;
	private Integer 	idStruttura;
	private Integer 	idNodo;
	private String  	applicationKeyActa;
	private String 		repositoryName;
	private String 		rootActa;
	private Integer     idvitalrecordcodetype;
	private Integer 	idStatoDiEfficacia;
	private String 	    descFormaDocumentaria;
	private String 	    descEnte;
	
	
	public String getDescEnte() {
		return descEnte;
	}

	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}

	public String getDescFormaDocumentaria() {
		return descFormaDocumentaria;
	}

	public void setDescFormaDocumentaria(String descFormaDocumentaria) {
		this.descFormaDocumentaria = descFormaDocumentaria;
	}

	public Integer getIdStatoDiEfficacia() 
	{
		return idStatoDiEfficacia;
	}

	public void setIdStatoDiEfficacia(Integer idStatoDiEfficacia)
	{
		this.idStatoDiEfficacia = idStatoDiEfficacia;
	}

	public Integer getIdvitalrecordcodetype()
	{
		return idvitalrecordcodetype;
	}

	public void setIdvitalrecordcodetype(Integer idvitalrecordcodetype)
	{
		this.idvitalrecordcodetype = idvitalrecordcodetype;
	}

	public String getRootActa()
	{
		return rootActa;
	}

	public void setRootActa(String rootActa)
	{
		this.rootActa = rootActa;
	}

	public String getRepositoryName()
	{
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) 
	{
		this.repositoryName = repositoryName;
	}

	public String getApplicationKeyActa() {
		return applicationKeyActa;
	}

	public void setApplicationKeyActa(String applicationKeyActa) {
		this.applicationKeyActa = applicationKeyActa;
	}

	public String getCodiceFiscale() 
	{
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale)
	{
		this.codiceFiscale = codiceFiscale;
	}

	public Integer getIdAoo()
	{
		return idAoo;
	}

	public void setIdAoo(Integer idAoo) 
	{
		this.idAoo = idAoo;
	}

	public Integer getIdStruttura()
	{
		return idStruttura;
	}

	public void setIdStruttura(Integer idStruttura)
	{
		this.idStruttura = idStruttura;
	}

	public Integer getIdNodo()
	{
		return idNodo;
	}

	public void setIdNodo(Integer idNodo) 
	{
		this.idNodo = idNodo;
	}

	public String toString()
	{
		return "applicationKeyActa == "+ this.applicationKeyActa + " codiceFiscale == "+ this.codiceFiscale + 
			   " repositoryName == " + this.repositoryName + " rootActa == " + this.rootActa;
	}
}
