/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.leggi;

import it.csi.conam.conambl.vo.ParentVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author riccardo.bova
 * @date 02 gen 2019
 */
public class ProntuarioVO extends ParentVO {

	private static final long serialVersionUID = 789125469285988696L;

	private EnteVO enteLegge;
	private AmbitoVO ambito;
	private NormaVO norma;
	private ArticoloVO articolo;
	private CommaVO comma;
	private LetteraVO lettera;
	private Boolean eliminaEnable;

	public EnteVO getEnteLegge() {
		return enteLegge;
	}

	public AmbitoVO getAmbito() {
		return ambito;
	}

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

	public Boolean getEliminaEnable() {
		return eliminaEnable;
	}

	public void setEnteLegge(EnteVO enteLegge) {
		this.enteLegge = enteLegge;
	}

	public void setAmbito(AmbitoVO ambito) {
		this.ambito = ambito;
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

	public void setEliminaEnable(Boolean eliminaEnable) {
		this.eliminaEnable = eliminaEnable;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
