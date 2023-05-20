/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.pianorateizzazione;

import it.csi.conam.conambl.request.ordinanza.RicercaSoggettiOrdinanzaRequest;
import it.csi.conam.conambl.vo.ordinanza.StatoOrdinanzaVO;
import it.csi.conam.conambl.vo.pianorateizzazione.StatoPianoVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.time.LocalDate;
import java.util.List;

public class RicercaPianoRequest extends RicercaSoggettiOrdinanzaRequest {

	private static final long serialVersionUID = -8983739551591344381L;

	private String numeroProtocolloPiano;
	private List<StatoPianoVO> statoPiano;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataNotificaDa;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataNotificaA;
	private StatoOrdinanzaVO statoOrdinanza;
	private String tipoRicerca;

	public String getNumeroProtocolloPiano() {
		return numeroProtocolloPiano;
	}

	public void setNumeroProtocolloPiano(String numeroProtocolloPiano) {
		this.numeroProtocolloPiano = numeroProtocolloPiano;
	}

	public List<StatoPianoVO> getStatoPiano() {
		return statoPiano;
	}

	public void setStatoPiano(List<StatoPianoVO> statoPiano) {
		this.statoPiano = statoPiano;
	}

	public LocalDate getDataNotificaDa() {
		return dataNotificaDa;
	}

	public void setDataNotificaDa(LocalDate dataNotificaDa) {
		this.dataNotificaDa = dataNotificaDa;
	}

	public LocalDate getDataNotificaA() {
		return dataNotificaA;
	}

	public void setDataNotificaA(LocalDate dataNotificaA) {
		this.dataNotificaA = dataNotificaA;
	}

	public StatoOrdinanzaVO getStatoOrdinanza() {
		return statoOrdinanza;
	}

	public void setStatoOrdinanza(StatoOrdinanzaVO statoOrdinanza) {
		this.statoOrdinanza = statoOrdinanza;
	}

	public String getTipoRicerca() {
		return tipoRicerca;
	}

	public void setTipoRicerca(String tipoRicerca) {
		this.tipoRicerca = tipoRicerca;
	}

}
