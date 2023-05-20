/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.riscossione;

import it.csi.conam.conambl.request.ordinanza.RicercaSoggettiOrdinanzaRequest;
import it.csi.conam.conambl.vo.ordinanza.StatoOrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.StatoSentenzaVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.time.LocalDate;

/**
 * @author riccardo.bova
 * @date 11 feb 2019
 */
public class RicercaSoggettiRiscossioneCoattivaRequest extends RicercaSoggettiOrdinanzaRequest {

	private static final long serialVersionUID = -6838324969449259337L;

	private StatoOrdinanzaVO statoOrdinanza;
	private StatoSentenzaVO statoSentenza;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataSentenzaDa;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataSentenzaA;
	
	//private Boolean 

	public StatoSentenzaVO getStatoSentenza() {
		return statoSentenza;
	}

	public StatoOrdinanzaVO getStatoOrdinanza() {
		return statoOrdinanza;
	}

	public void setStatoOrdinanza(StatoOrdinanzaVO statoOrdinanza) {
		this.statoOrdinanza = statoOrdinanza;
	}

	public void setStatoSentenza(StatoSentenzaVO statoSentenza) {
		this.statoSentenza = statoSentenza;
	}

	public LocalDate getDataSentenzaDa() {
		return dataSentenzaDa;
	}

	public void setDataSentenzaDa(LocalDate dataSentenzaDa) {
		this.dataSentenzaDa = dataSentenzaDa;
	}

	public LocalDate getDataSentenzaA() {
		return dataSentenzaA;
	}

	public void setDataSentenzaA(LocalDate dataSentenzaA) {
		this.dataSentenzaA = dataSentenzaA;
	}

}
