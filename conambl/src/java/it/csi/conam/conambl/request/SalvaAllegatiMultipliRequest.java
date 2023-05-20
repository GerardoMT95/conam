/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request;

import it.csi.conam.conambl.vo.verbale.allegato.AllegatoMultiploVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class SalvaAllegatiMultipliRequest extends ParentRequest {

	private static final long serialVersionUID = -3620699452083779279L;

	private List<AllegatoMultiploVO> allegati;
	private Integer idVerbale;
	private List<Integer> idOrdinanzaVerbaleSoggettoList;

	public List<AllegatoMultiploVO> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<AllegatoMultiploVO> allegati) {
		this.allegati = allegati;
	}

	public Integer getIdVerbale() {
		return idVerbale;
	}

	public void setIdVerbale(Integer idVerbale) {
		this.idVerbale = idVerbale;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public List<Integer> getIdOrdinanzaVerbaleSoggettoList() {
		return idOrdinanzaVerbaleSoggettoList;
	}

	public void setIdOrdinanzaVerbaleSoggettoList(List<Integer> idOrdinanzaVerbaleSoggettoList) {
		this.idOrdinanzaVerbaleSoggettoList = idOrdinanzaVerbaleSoggettoList;
	}



}
