/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.util;

import it.csi.conam.conambl.integration.entity.CnmDAmbito;
import it.csi.conam.conambl.integration.entity.CnmDLettera;
import it.csi.conam.conambl.integration.entity.CnmDStatoManuale;
import it.csi.conam.conambl.integration.entity.CnmDStatoPregresso;
import it.csi.conam.conambl.integration.entity.CnmDStatoVerbale;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author Lucio Rosadini
 * @date 23 mar 2021
 */
public class VerbaleSearchParam{
	private String numeroProtocollo = null;
	private String numeroVerbale = null;
	private List<CnmDLettera> lettera = null;
	private List<CnmDStatoVerbale> statoVerbale = null;
	private List<CnmDStatoManuale> statoManuale = null;
	private List<CnmDStatoPregresso> statiPregresso = null;
	private CnmDAmbito ambito = null;	// 20211125_LC Jira 184
	

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public String getNumeroVerbale() {
		return numeroVerbale;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public void setNumeroVerbale(String numeroVerbale) {
		this.numeroVerbale = numeroVerbale;
	}

	public List<CnmDLettera> getLettera() {
		return lettera;
	}

	public void setLettera(List<CnmDLettera> lettera) {
		this.lettera = lettera;
	}

	public List<CnmDStatoVerbale> getStatoVerbale() {
		return statoVerbale;
	}

	public void setStatoVerbale(List<CnmDStatoVerbale> statoVerbale) {
		this.statoVerbale = statoVerbale;
	}

	public List<CnmDStatoManuale> getStatoManuale() {
		return statoManuale;
	}

	public void setStatoManuale(List<CnmDStatoManuale> statoManuale) {
		this.statoManuale = statoManuale;
	}

	public List<CnmDStatoPregresso> getStatiPregresso() {
		return statiPregresso;
	}

	public void setStatiPregresso(List<CnmDStatoPregresso> statiPregresso) {
		this.statiPregresso = statiPregresso;
	}

	
	public CnmDAmbito getAmbito() {
		return ambito;
	}

	public void setAmbito(CnmDAmbito ambito) {
		this.ambito = ambito;
	}

	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
