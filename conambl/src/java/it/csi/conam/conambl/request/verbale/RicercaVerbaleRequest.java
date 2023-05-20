/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.verbale;

import it.csi.conam.conambl.request.ParentRequest;
import it.csi.conam.conambl.request.SoggettoRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class RicercaVerbaleRequest extends ParentRequest {
	
	private static final long serialVersionUID = -3627336339314304078L;
	
	private DatiVerbaleRequest datiVerbale;
	private List<SoggettoRequest> soggettoVerbale;
	private Boolean statoManualeDiCompetenza;
	
	public Boolean getStatoManualeDiCompetenza() {
		return statoManualeDiCompetenza;
	}

	public void setStatoManualeDiCompetenza(Boolean statoManualeDiCompetenza) {
		this.statoManualeDiCompetenza = statoManualeDiCompetenza;
		if (datiVerbale == null) datiVerbale = new DatiVerbaleRequest();
		datiVerbale.setStatoManualeDiCompetenza(statoManualeDiCompetenza);
	}

	public DatiVerbaleRequest getDatiVerbale() {
		return datiVerbale;
	}

	public List<SoggettoRequest> getSoggettoVerbale() {
		return soggettoVerbale;
	}

	public void setDatiVerbale(DatiVerbaleRequest datiVerbale) {
		this.datiVerbale = datiVerbale;
	}

	public void setSoggettoVerbale(List<SoggettoRequest> soggettoVerbale) {
		this.soggettoVerbale = soggettoVerbale;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
