/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.luoghi;

import it.csi.conam.conambl.vo.common.SelectVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProvinciaVO extends SelectVO {

	private static final long serialVersionUID = 8413965598052128710L;
	
	private String sigla;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

}
