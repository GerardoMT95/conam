/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.mail;

import it.csi.conam.conambl.integration.entity.CnmTCalendario;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Paolo Piedepalumbo
 * 
 */
public class PromemoriaMail implements Serializable {

	private static final long serialVersionUID = -3074097152117011147L;
		
	private String eMailTo;
	private CnmTCalendario cnmTCalendario;

	private String autoritaGiudiziaria;
	private Date data;
	private String giudiceCompetente;
	private String luogoUdienza;
	private String note;
	
	

	public String geteMailTo() {
		return eMailTo;
	}

	public void seteMailTo(String eMailTo) {
		this.eMailTo = eMailTo;
	}

	public CnmTCalendario getCnmTCalendario() {
		return cnmTCalendario;
	}

	public void setCnmTCalendario(CnmTCalendario cnmTCalendario) {
		this.cnmTCalendario = cnmTCalendario;
	}

	protected PromemoriaMail(CnmTCalendario cnmTCalendario) {
		super();
		this.cnmTCalendario = cnmTCalendario;
		this.eMailTo = cnmTCalendario.getEmailGiudice();
		this.autoritaGiudiziaria = cnmTCalendario.getTribunale();
		this.data = cnmTCalendario.getInizioUdienza();
		this.giudiceCompetente = cnmTCalendario.getNomeGiudice() + " " + cnmTCalendario.getCognomeGiudice();
		this.luogoUdienza = cnmTCalendario.getIndirizzoUdienza();
		this.note = cnmTCalendario.getNote();
	}

	public String getAutoritaGiudiziaria() {
		return autoritaGiudiziaria;
	}

	public void setAutoritaGiudiziaria(String autoritaGiudiziaria) {
		this.autoritaGiudiziaria = autoritaGiudiziaria;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getGiudiceCompetente() {
		return giudiceCompetente;
	}

	public void setGiudiceCompetente(String giudiceCompetente) {
		this.giudiceCompetente = giudiceCompetente;
	}

	public String getLuogoUdienza() {
		return luogoUdienza;
	}

	public void setLuogoUdienza(String luogoUdienza) {
		this.luogoUdienza = luogoUdienza;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	

	@Override
	public String toString() {
		return "PromemoriaMail [eMailTo=" + eMailTo + ", cnmTCalendario=" + cnmTCalendario + ", autoritaGiudiziaria="
				+ autoritaGiudiziaria + ", data=" + data + ", giudiceCompetente=" + giudiceCompetente
				+ ", luogoUdienza=" + luogoUdienza + ", note=" + note + "]";
	}

}
