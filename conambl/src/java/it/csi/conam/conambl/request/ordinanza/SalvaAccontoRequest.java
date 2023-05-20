/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.ordinanza;

import it.csi.conam.conambl.request.SalvaAllegatoRequest;
import it.csi.conam.conambl.vo.ordinanza.AccontoVO;

/**
 * @author Rosadini Lucio
 */
public class SalvaAccontoRequest extends SalvaAllegatoRequest {

	private static final long serialVersionUID = -3620699452083779279L;
	private AccontoVO acconto;
	
	public AccontoVO getAcconto() {
		return acconto;
	}
	public void setAcconto(AccontoVO acconto) {
		this.acconto = acconto;
	}
}
