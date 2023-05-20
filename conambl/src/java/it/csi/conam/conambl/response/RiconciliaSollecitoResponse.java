/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

/**
 * @author riccardo.bova
 * @date 21 nov 2018
 */
public class RiconciliaSollecitoResponse extends ParentVO {

	private static final long serialVersionUID = 5502699540493590617L;

	private SollecitoVO sollecito;
	private SoggettoVO soggetto;

	public SollecitoVO getSollecito() {
		return sollecito;
	}

	public SoggettoVO getSoggetto() {
		return soggetto;
	}

	public void setSollecito(SollecitoVO sollecito) {
		this.sollecito = sollecito;
	}

	public void setSoggetto(SoggettoVO soggetto) {
		this.soggetto = soggetto;
	}

}
