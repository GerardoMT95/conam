/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.luoghi;

import it.csi.conam.conambl.vo.common.SelectVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ComuneVO extends SelectVO implements Comparable<ComuneVO> {

	private static final long serialVersionUID = -6580396484906960147L;
	private String codBelfiore;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getCodBelfiore() {
		return codBelfiore;
	}

	public void setCodBelfiore(String codBelfiore) {
		this.codBelfiore = codBelfiore;
	}

	@Override
	public int compareTo(ComuneVO o) {
		return this.getDenominazione().compareTo(o.getDenominazione());
	}

}
