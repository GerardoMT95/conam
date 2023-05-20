/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request;

import it.csi.conam.conambl.vo.verbale.allegato.AllegatoFieldVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class SalvaAllegatoRequest extends ParentRequest {

	private static final long serialVersionUID = -3620699452083779279L;

	private byte[] file;
	private String filename;
	private Long idTipoAllegato;
	private List<AllegatoFieldVO> allegatoField;

	public String getFilename() {
		return filename;
	}

	public Long getIdTipoAllegato() {
		return idTipoAllegato;
	}

	public List<AllegatoFieldVO> getAllegatoField() {
		return allegatoField;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setIdTipoAllegato(Long idTipoAllegato) {
		this.idTipoAllegato = idTipoAllegato;
	}

	public void setAllegatoField(List<AllegatoFieldVO> allegatoField) {
		this.allegatoField = allegatoField;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
