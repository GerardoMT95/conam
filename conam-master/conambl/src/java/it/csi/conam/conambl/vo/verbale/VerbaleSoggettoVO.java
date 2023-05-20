/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import it.csi.conam.conambl.vo.ParentVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Lucio Rosadini
 * @date 21/04/2021
 */


public class VerbaleSoggettoVO extends ParentVO {

	private static final long serialVersionUID = -884217214905682332L;

	private Long id;
	private MinVerbaleVO verbale;
	private MinSoggettoVO soggetto;
	private RuoloSoggettoVO ruoloSoggetto;
	private String note;
	private Boolean recidivo;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public MinVerbaleVO getVerbale() {
		return verbale;
	}


	public void setVerbale(MinVerbaleVO verbale) {
		this.verbale = verbale;
	}

	public MinSoggettoVO getSoggetto() {
		return soggetto;
	}

	public void setSoggetto(MinSoggettoVO soggetto) {
		this.soggetto = soggetto;
	}

	public RuoloSoggettoVO getRuoloSoggetto() {
		return ruoloSoggetto;
	}

	public void setRuoloSoggetto(RuoloSoggettoVO ruoloSoggetto) {
		this.ruoloSoggetto = ruoloSoggetto;
	}
	
	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public Boolean getRecidivo() {
		return recidivo;
	}


	public void setRecidivo(Boolean recidivo) {
		this.recidivo = recidivo;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
