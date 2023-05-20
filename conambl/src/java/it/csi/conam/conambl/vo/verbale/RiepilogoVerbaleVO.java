/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.verbale.allegato.RiepilogoAllegatoVO;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
public class RiepilogoVerbaleVO extends ParentVO {

	private static final long serialVersionUID = -1587543446222732603L;
	private VerbaleVO verbale;
	private List<SoggettoVO> soggetto;
	private RiepilogoAllegatoVO allegati;

	public VerbaleVO getVerbale() {
		return verbale;
	}

	public List<SoggettoVO> getSoggetto() {
		return soggetto;
	}

	public void setVerbale(VerbaleVO verbale) {
		this.verbale = verbale;
	}

	public void setSoggetto(List<SoggettoVO> soggetto) {
		this.soggetto = soggetto;
	}

	public RiepilogoAllegatoVO getAllegati() {
		return allegati;
	}

	public void setAllegati(RiepilogoAllegatoVO allegati) {
		this.allegati = allegati;
	}

}
