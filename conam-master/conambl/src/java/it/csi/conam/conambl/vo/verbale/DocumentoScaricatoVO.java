/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import it.csi.conam.conambl.vo.ParentVO;

import java.util.Arrays;

// 20200803_LC per gestione doc multiplo
public class DocumentoScaricatoVO extends ParentVO {

	private static final long serialVersionUID = 2810282616229598206L;
	
	
	private byte[] file;
	private String nomeFile;
	private String objectIdDocumentoFisico;
	
	
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}


	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
	public String getObjectIdDocumentoFisico() {
		return objectIdDocumentoFisico;
	}
	public void setObjectIdDocumentoFisico(String objectIdDocumentoFisico) {
		this.objectIdDocumentoFisico = objectIdDocumentoFisico;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DocumentoScaricatoVO [file=");
		builder.append(Arrays.toString(file));
		builder.append(", nomeFile=");
		builder.append(nomeFile);
		builder.append(", objectIdDocumentoFisico=");
		builder.append(objectIdDocumentoFisico);
		builder.append("]");
		return builder.toString();
	}
	
	
	

	

	


	
	


}
