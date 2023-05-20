/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale.allegato;

import it.csi.conam.conambl.vo.ParentVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 16 nov 2018
 */
public class RiepilogoAllegatoVO extends ParentVO {

	private static final long serialVersionUID = -7714298531166937914L;

	private List<AllegatoVO> verbale;
	private List<AllegatoVO> istruttoria;
	private List<AllegatoVO> giurisdizionale;
	private List<AllegatoVO> rateizzazione;

	public List<AllegatoVO> getVerbale() {
		return verbale;
	}

	public List<AllegatoVO> getIstruttoria() {
		return istruttoria;
	}

	public List<AllegatoVO> getGiurisdizionale() {
		return giurisdizionale;
	}

	public void setVerbale(List<AllegatoVO> verbale) {
		this.verbale = verbale;
	}

	public void setIstruttoria(List<AllegatoVO> istruttoria) {
		this.istruttoria = istruttoria;
	}

	public void setGiurisdizionale(List<AllegatoVO> giurisdizionale) {
		this.giurisdizionale = giurisdizionale;
	}	

	public List<AllegatoVO> getRateizzazione() {
		return rateizzazione;
	}

	public void setRateizzazione(List<AllegatoVO> rateizzazione) {
		this.rateizzazione = rateizzazione;
	}
	

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
