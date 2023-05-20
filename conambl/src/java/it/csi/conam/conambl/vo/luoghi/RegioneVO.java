/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.luoghi;

import it.csi.conam.conambl.vo.common.SelectVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RegioneVO extends SelectVO {

	private static final long serialVersionUID = -8072892131795840119L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
