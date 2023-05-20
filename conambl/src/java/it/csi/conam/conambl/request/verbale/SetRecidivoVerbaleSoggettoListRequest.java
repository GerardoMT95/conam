/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.verbale;

import it.csi.conam.conambl.request.ParentRequest;

import java.util.List;

/**
 * @author Lucio Rosadini
 */
public class SetRecidivoVerbaleSoggettoListRequest extends ParentRequest {

	private static final long serialVersionUID = -3620699452083779279L;
	List<SetRecidivoVerbaleSoggettoRequest> lista;
	
	public List<SetRecidivoVerbaleSoggettoRequest> getLista() {
		return lista;
	}
	public void setLista(List<SetRecidivoVerbaleSoggettoRequest> lista) {
		this.lista = lista;
	}
}
