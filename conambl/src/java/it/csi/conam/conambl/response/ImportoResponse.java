/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

import it.csi.conam.conambl.vo.ParentVO;

import java.math.BigDecimal;

/**
 * @author riccardo.bova
 * @date 21 nov 2018
 */
public class ImportoResponse extends ParentVO {

	private static final long serialVersionUID = 5502699540493590617L;

	private BigDecimal importoDaPagare;
	private BigDecimal importoPagato;

	public BigDecimal getImportoPagato() {
		return importoPagato;
	}

	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}

	public BigDecimal getImportoDaPagare() {
		return importoDaPagare;
	}

	public void setImportoDaPagare(BigDecimal importoDaPagare) {
		this.importoDaPagare = importoDaPagare;
	}

}
