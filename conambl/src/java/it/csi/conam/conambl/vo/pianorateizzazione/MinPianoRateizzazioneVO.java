/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.pianorateizzazione;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.web.serializer.CustomDateTimeDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateTimeSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class MinPianoRateizzazioneVO extends ParentVO {
	private static final long serialVersionUID = 6750806329297833918L;

	private Integer id;
	private String numProtocollo;
	private StatoPianoVO stato;
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private LocalDateTime dataCreazione;
	private BigDecimal saldo;
	private List<SoggettoVO> soggetti;
	private boolean superatoIlMassimo;

	public String getNumProtocollo() {
		return numProtocollo;
	}

	public StatoPianoVO getStato() {
		return stato;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}

	public void setStato(StatoPianoVO stato) {
		this.stato = stato;
	}

	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<SoggettoVO> getSoggetti() {
		return soggetti;
	}

	public void setSoggetti(List<SoggettoVO> soggetti) {
		this.soggetti = soggetti;
	}

	public boolean isSuperatoIlMassimo() {
		return superatoIlMassimo;
	}

	public void setSuperatoIlMassimo(boolean superatoIlMassimo) {
		this.superatoIlMassimo = superatoIlMassimo;
	}

}
