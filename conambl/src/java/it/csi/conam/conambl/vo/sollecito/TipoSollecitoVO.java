/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.sollecito;

import it.csi.conam.conambl.vo.common.SelectVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TipoSollecitoVO extends SelectVO {

	private static final long serialVersionUID = -4998515140612928921L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
