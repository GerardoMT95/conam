/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.ordinanza;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccontoVO extends ParentVO {

	private static final long serialVersionUID = 852151050709076476L;

	private Long id;
	private Integer idOrdinanza;
	private Integer idSoggetto;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataPagamento;
	private BigDecimal importo;
	private String contoCorrenteVersamento;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getIdOrdinanza() {
		return idOrdinanza;
	}
	public void setIdOrdinanza(Integer idOrdinanza) {
		this.idOrdinanza = idOrdinanza;
	}
	public Integer getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(Integer idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getContoCorrenteVersamento() {
		return contoCorrenteVersamento;
	}
	public void setContoCorrenteVersamento(String contoCorrenteVersamento) {
		this.contoCorrenteVersamento = contoCorrenteVersamento;
	}
}
