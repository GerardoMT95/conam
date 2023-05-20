/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request;

import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

/**
 * @author riccardo.bova
 * @date 16 nov 2018
 */
public class SalvaSoggettoRequest extends ParentRequest {
	private static final long serialVersionUID = 2095585465549666270L;

	@NotNull(message = "REQCON04")
	private Integer idVerbale;
	@NotNull(message = "REQCON05")
	private SoggettoVO soggetto;

	public Integer getIdVerbale() {
		return idVerbale;
	}

	public SoggettoVO getSoggetto() {
		return soggetto;
	}

	public void setIdVerbale(Integer idVerbale) {
		this.idVerbale = idVerbale;
	}

	public void setSoggetto(SoggettoVO soggetto) {
		this.soggetto = soggetto;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
