/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.ordinanza;

import it.csi.conam.conambl.vo.common.SelectVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PosizioneVO extends SelectVO {

	private static final long serialVersionUID = -6580396484906960147L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
