/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.riscossione;

import it.csi.conam.conambl.request.ParentRequest;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author riccardo.bova
 * @date 11 feb 2019
 */
public class SalvaSoggettiRiscossioneCoattivaRequest extends ParentRequest {

	private static final long serialVersionUID = -6838324969449259337L;

	private Integer idRiscossione;
	private BigDecimal importoSanzione;
	private BigDecimal importoSpeseNotifica;
	private BigDecimal importoSpeseLegali;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataDecorrenzaInteressi;

	public Integer getIdRiscossione() {
		return idRiscossione;
	}

	public void setIdRiscossione(Integer idRiscossione) {
		this.idRiscossione = idRiscossione;
	}

	public BigDecimal getImportoSanzione() {
		return importoSanzione;
	}

	public void setImportoSanzione(BigDecimal importoSanzione) {
		this.importoSanzione = importoSanzione;
	}

	public BigDecimal getImportoSpeseNotifica() {
		return importoSpeseNotifica;
	}

	public void setImportoSpeseNotifica(BigDecimal importoSpeseNotifica) {
		this.importoSpeseNotifica = importoSpeseNotifica;
	}

	public LocalDate getDataDecorrenzaInteressi() {
		return dataDecorrenzaInteressi;
	}

	public void setDataDecorrenzaInteressi(LocalDate dataDecorrenzaInteressi) {
		this.dataDecorrenzaInteressi = dataDecorrenzaInteressi;
	}

	public BigDecimal getImportoSpeseLegali() {
		return importoSpeseLegali;
	}

	public void setImportoSpeseLegali(BigDecimal importoSpeseLegali) {
		this.importoSpeseLegali = importoSpeseLegali;
	}

}
