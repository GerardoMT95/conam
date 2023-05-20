/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request;

import java.util.List;

public class SalvaAllegatiProtocollatiSoggettiRequest extends SalvaAllegatiProtocollatiRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7834500331107359623L;
	
	List<Integer> idVerbaleSoggettoList;

	public List<Integer> getIdVerbaleSoggettoList() {
		return idVerbaleSoggettoList;
	}

	public void setIdVerbaleSoggettoList(List<Integer> idVerbaleSoggettoList) {
		this.idVerbaleSoggettoList = idVerbaleSoggettoList;
	}
	
	
}
