/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

import it.csi.conam.conambl.vo.ParentVO;

import java.math.BigDecimal;

/**
 * @author riccardo.bova
 * @date 12 feb 2019
 */
public class DatiSentenzaResponse extends ParentVO {

	private static final long serialVersionUID = 1L;

	private BigDecimal importoTitoloSanzione;
	private BigDecimal importoSpeseProcessuali;

	public BigDecimal getImportoTitoloSanzione() {
		return importoTitoloSanzione;
	}

	public BigDecimal getImportoSpeseProcessuali() {
		return importoSpeseProcessuali;
	}

	public void setImportoTitoloSanzione(BigDecimal importoTitoloSanzione) {
		this.importoTitoloSanzione = importoTitoloSanzione;
	}

	public void setImportoSpeseProcessuali(BigDecimal importoSpeseProcessuali) {
		this.importoSpeseProcessuali = importoSpeseProcessuali;
	}

}
