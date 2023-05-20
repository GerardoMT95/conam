/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale.allegato;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.verbale.DocumentoProtocollatoVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AllegatoMultiploVO extends ParentVO {

	private static final long serialVersionUID = -7714298531166937914L;

	private byte[] file;
	private String filename;
	private Long idTipoAllegato;
	private boolean master;
	

	// 20210428_LC per gestire allegati (istanza) a Acta
	private DocumentoProtocollatoVO documentoProtocollato;
	

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getIdTipoAllegato() {
		return idTipoAllegato;
	}

	public void setIdTipoAllegato(Long idTipoAllegato) {
		this.idTipoAllegato = idTipoAllegato;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public DocumentoProtocollatoVO getDocumentoProtocollato() {
		return documentoProtocollato;
	}

	public void setDocumentoProtocollato(DocumentoProtocollatoVO documentoProtocollato) {
		this.documentoProtocollato = documentoProtocollato;
	}

}
