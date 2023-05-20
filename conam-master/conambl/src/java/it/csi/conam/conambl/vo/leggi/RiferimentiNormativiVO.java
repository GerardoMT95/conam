/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.leggi;

import it.csi.conam.conambl.vo.ParentVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public class RiferimentiNormativiVO extends ParentVO {

	private static final long serialVersionUID = 2468599877818942767L;

	private Integer id;
	private AmbitoVO ambito;
	private NormaVO norma;
	private ArticoloVO articolo;
	private CommaVO comma;
	private LetteraVO lettera;

	public NormaVO getNorma() {
		return norma;
	}

	public ArticoloVO getArticolo() {
		return articolo;
	}

	public CommaVO getComma() {
		return comma;
	}

	public LetteraVO getLettera() {
		return lettera;
	}

	public void setNorma(NormaVO norma) {
		this.norma = norma;
	}

	public void setArticolo(ArticoloVO articolo) {
		this.articolo = articolo;
	}

	public void setComma(CommaVO comma) {
		this.comma = comma;
	}

	public void setLettera(LetteraVO lettera) {
		this.lettera = lettera;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public AmbitoVO getAmbito() {
		return ambito;
	}

	public void setAmbito(AmbitoVO ambito) {
		this.ambito = ambito;
	}

	public String toStringForMessage() {
		return getAmbito().getAcronimo() + " - " + 
				getNorma().getDenominazione() + " - " +  
				getArticolo().getDenominazione() + " - " +
				getComma().getDenominazione() + " - " + 
				getLettera().getDenominazione();
	}
}
