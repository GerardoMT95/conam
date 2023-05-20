/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.leggi;

import it.csi.conam.conambl.vo.common.SelectVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class EnteVO extends SelectVO {

	private static final long serialVersionUID = -7599263766833466869L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
