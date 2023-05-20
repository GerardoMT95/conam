/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;

import java.util.Arrays;
import java.util.List;

public class DocumentoProtocollatoVO extends ParentVO {

	private static final long serialVersionUID = -7714298531166937914L;

	private byte[] file;
	private String filename;
	private String classificazione;
	private String classificazioneId;
	private TipoAllegatoVO tipo;
	private String numProtocollo;
	private String numProtocolloMaster;
	private String dataOraProtocollo;
	private String idActa;
	private String objectIdDocumento;
	
	// 20200707_LC
	private String registrazioneId;
	private String folderId;
	private String idActaMaster;
	private String documentoUUID;
	
	// 20200711_LC
	private String parolaChiave;
	
	// 20200723_LC_P2
	private boolean giaSalvato;
	
	private List<Long> tipiAllegatoDuplicabili;
	
	
	// 20200825_LC
	private List<String> filenamesDocMultiplo;

	//20200918_ET aggiunta proprieta per non mostrare sul FE i messaggi "fuorvianti" al SALVA di "aggiungi allegato" se i doc sono gia stati spostati nel fascicolo su ACTA
	private boolean giaPresenteSuActa;
	//20200923_ET aggiunto campo per gestire correttamente i parametri del messaggio SAVEDOC01
	private String filenameMaster;
	
	public String getClassificazione() {
		return classificazione;
	}

	public void setClassificazione(String classificazione) {
		this.classificazione = classificazione;
	}
	
	public String getDataOraProtocollo() {
		return dataOraProtocollo;
	}

	public void setDataOraProtocollo(String dataOraProtocollo) {
		this.dataOraProtocollo = dataOraProtocollo;
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

	public TipoAllegatoVO getTipo() {
		return tipo;
	}

	public void setTipo(TipoAllegatoVO tipo) {
		this.tipo = tipo;
	}

	public String getNumProtocollo() {
		return numProtocollo;
	}

	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}

	public String getIdActa() {
		return idActa;
	}

	public void setIdActa(String idActa) {
		this.idActa = idActa;
	}
	
	public String getClassificazioneId() {
		return classificazioneId;
	}

	public void setClassificazioneId(String classificazioneId) {
		this.classificazioneId = classificazioneId;
	}

	public String getObjectIdDocumento() {
		return objectIdDocumento;
	}

	public void setObjectIdDocumento(String objectIdDocumento) {
		this.objectIdDocumento = objectIdDocumento;
	}

	public String getRegistrazioneId() {
		return registrazioneId;
	}

	public void setRegistrazioneId(String registrazioneId) {
		this.registrazioneId = registrazioneId;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	
	public String getIdActaMaster() {
		return idActaMaster;
	}

	public void setIdActaMaster(String idActaMaster) {
		this.idActaMaster = idActaMaster;
	}
	
	public String getDocumentoUUID() {
		return documentoUUID;
	}

	public void setDocumentoUUID(String documentoUUID) {
		this.documentoUUID = documentoUUID;
	}

	
	
	
	public String getParolaChiave() {
		return parolaChiave;
	}

	public void setParolaChiave(String parolaChiave) {
		this.parolaChiave = parolaChiave;
	}
	

	public String getNumProtocolloMaster() {
		return numProtocolloMaster;
	}

	public void setNumProtocolloMaster(String numProtocolloMaster) {
		this.numProtocolloMaster = numProtocolloMaster;
	}

	public boolean isGiaSalvato() {
		return giaSalvato;
	}

	public void setGiaSalvato(boolean giaSalvato) {
		this.giaSalvato = giaSalvato;
	}


	public List<Long> getTipiAllegatoDuplicabili() {
		return tipiAllegatoDuplicabili;
	}

	public void setTipiAllegatoDuplicabili(List<Long> tipiAllegatoDuplicabili) {
		this.tipiAllegatoDuplicabili = tipiAllegatoDuplicabili;
	}

	public List<String> getFilenamesDocMultiplo() {
		return filenamesDocMultiplo;
	}

	public void setFilenamesDocMultiplo(List<String> filenamesDocMultiplo) {
		this.filenamesDocMultiplo = filenamesDocMultiplo;
	}

	public boolean isGiaPresenteSuActa() {
		return giaPresenteSuActa;
	}

	public void setGiaPresenteSuActa(boolean giaPresenteSuActa) {
		this.giaPresenteSuActa = giaPresenteSuActa;
	}

	public String getFilenameMaster() {
		return filenameMaster;
	}

	public void setFilenameMaster(String filenameMaster) {
		this.filenameMaster = filenameMaster;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DocumentoProtocollatoVO [file=");
		builder.append(Arrays.toString(file));
		builder.append(", filename=");
		builder.append(filename);
		builder.append(", classificazione=");
		builder.append(classificazione);
		builder.append(", classificazioneId=");
		builder.append(classificazioneId);
		builder.append(", tipo=");
		builder.append(tipo);
		builder.append(", numProtocollo=");
		builder.append(numProtocollo);
		builder.append(", numProtocolloMaster=");
		builder.append(numProtocolloMaster);
		builder.append(", dataOraProtocollo=");
		builder.append(dataOraProtocollo);
		builder.append(", idActa=");
		builder.append(idActa);
		builder.append(", objectIdDocumento=");
		builder.append(objectIdDocumento);
		builder.append(", registrazioneId=");
		builder.append(registrazioneId);
		builder.append(", folderId=");
		builder.append(folderId);
		builder.append(", idActaMaster=");
		builder.append(idActaMaster);
		builder.append(", documentoUUID=");
		builder.append(documentoUUID);
		builder.append(", parolaChiave=");
		builder.append(parolaChiave);
		builder.append(", giaSalvato=");
		builder.append(giaSalvato);
		builder.append(", tipiAllegatoDuplicabili=");
		builder.append(tipiAllegatoDuplicabili);
		builder.append(", filenamesDocMultiplo=");
		builder.append(filenamesDocMultiplo);
		builder.append(", giaPresenteSuActa=");
		builder.append(giaPresenteSuActa);
		builder.append(", filenameMaster=");
		builder.append(filenameMaster);
		builder.append("]");
		return builder.toString();
	}





	
	
	
	


	
	
	
	

	


	
	


}
