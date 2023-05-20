/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import it.csi.conam.conambl.vo.ParentVO;

import java.util.List;

/**
 * @author Lucio Rosadini
 * @date 21/04/2021
 */


public class VerbaleSoggettoVORaggruppatoPerSoggetto extends ParentVO {

	private static final long serialVersionUID = -884217214905682332L;

	private SoggettoVO soggetto;
	private List<MinSoggettoVerbaleVO> soggettiVerbale;
	private RuoloSoggettoVO ruoloSoggetto;
	
	
	public SoggettoVO getSoggetto() {
		return soggetto;
	}
	public void setSoggetto(SoggettoVO soggetto) {
		this.soggetto = soggetto;
	}
	public List<MinSoggettoVerbaleVO> getSoggettiVerbale() {
		return soggettiVerbale;
	}
	public void setSoggettiVerbale(List<MinSoggettoVerbaleVO> soggettiVerbale) {
		this.soggettiVerbale = soggettiVerbale;
	}
	public RuoloSoggettoVO getRuoloSoggetto() {
		return ruoloSoggetto;
	}
	public void setRuoloSoggetto(RuoloSoggettoVO ruoloSoggetto) {
		this.ruoloSoggetto = ruoloSoggetto;
	}
}
