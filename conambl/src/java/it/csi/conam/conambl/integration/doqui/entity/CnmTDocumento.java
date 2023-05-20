/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.entity;

import it.csi.conam.conambl.integration.entity.CnmTUser;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="cnm_t_documento")
@NamedQuery(name="CnmTDocumento.findAll", query="SELECT c FROM CnmTDocumento c")
public class CnmTDocumento implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_documento")
	private Integer idDocumento;
	
	// 20200630_LC
	
//	@Column(name="id_tipo_documento")
//	private long idTipoDocumento;
	
	// bi-directional many-to-one association to CnmDTipoDocumento
	@ManyToOne
	@JoinColumn(name = "id_tipo_documento")
	private CnmDTipoDocumento cnmDTipoDocumento;
	
	
	@Column(name="identificativo_archiviazione")
	private String identificativoArchiviazione;
	
	@Column(name="protocollo_fornitore")
	private String protocolloFornitore;
	
	@Column(name="identificativo_fornitore")
	private String identificativoFornitore;
	
	@Column(name="identificativo_entita_fruitore")
	private String identificativoEntitaFruitore;
	
	// 20200630_LC
//	@Column(name="targa")
//	private String targa;
//	
//	@Column(name="codice_fiscale")
//	private String codiceFiscale;
	
	@Column(name="folder")
	private String folder;
	
	@Column(name="classificazione_id")
	private String classificazioneId;
	
	@Column(name="registrazione_id")
	private String registrazioneId;
	
	@Column(name="objectidclassificazione")
	private String objectidclassificazione;
	
	@Column(name="objectiddocumento")
	private String objectiddocumento;
	
	// 20200630_LC
	@Column(name = "data_ora_insert")
	private Timestamp dataOraInsert;
	
	@Column(name = "data_ora_update")
	private Timestamp dataOraUpdate;
	
	
	
	
	// 20200702_LC

	// bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name = "id_user_update")
	private CnmTUser cnmTUser1;

	// bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name = "id_user_insert")
	private CnmTUser cnmTUser2;

	
	
	// --
	
	
	
	
	public Integer getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}

	// 20200630_06
//	public long getIdTipoDocumento() {
//		return idTipoDocumento;
//	}
//
//	public void setIdTipoDocumento(long idTipoDocumento) {
//		this.idTipoDocumento = idTipoDocumento;
//	}
	
	public CnmDTipoDocumento getCnmDTipoDocumento() {
		return this.cnmDTipoDocumento;
	}

	public void setCnmDTipoDocumento(CnmDTipoDocumento cnmDTipoDocumento) {
		this.cnmDTipoDocumento = cnmDTipoDocumento;
	}

	public String getIdentificativoArchiviazione() {
		return identificativoArchiviazione;
	}

	public void setIdentificativoArchiviazione(String identificativoArchiviazione) {
		this.identificativoArchiviazione = identificativoArchiviazione;
	}

	public String getProtocolloFornitore() {
		return protocolloFornitore;
	}

	public void setProtocolloFornitore(String protocolloFornitore) {
		this.protocolloFornitore = protocolloFornitore;
	}

	public String getIdentificativoFornitore() {
		return identificativoFornitore;
	}

	public void setIdentificativoFornitore(String identificativoFornitore) {
		this.identificativoFornitore = identificativoFornitore;
	}

	public String getIdentificativoEntitaFruitore() {
		return identificativoEntitaFruitore;
	}

	public void setIdentificativoEntitaFruitore(String identificativoEntitaFruitore) {
		this.identificativoEntitaFruitore = StringUtils.left(identificativoEntitaFruitore, 500);
	}

//	public String getTarga() {
//		return targa;
//	}
//
//	public void setTarga(String targa) {
//		this.targa = targa;
//	}
//
//	public String getCodiceFiscale() {
//		return codiceFiscale;
//	}
//
//	public void setCodiceFiscale(String codiceFiscale) {
//		this.codiceFiscale = codiceFiscale;
//	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getClassificazioneId() {
		return classificazioneId;
	}

	public void setClassificazioneId(String classificazioneId) {
		this.classificazioneId = classificazioneId;
	}

	public String getRegistrazioneId() {
		return registrazioneId;
	}

	public void setRegistrazioneId(String registrazioneId) {
		this.registrazioneId = registrazioneId;
	}

	public String getObjectidclassificazione() {
		return objectidclassificazione;
	}

	public void setObjectidclassificazione(String objectidclassificazione) {
		this.objectidclassificazione = objectidclassificazione;
	}

	public String getObjectiddocumento() {
		return objectiddocumento;
	}

	public void setObjectiddocumento(String objectiddocumento) {
		this.objectiddocumento = objectiddocumento;
	}

	public Timestamp getDataOraInsert() {
		return dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}


	public CnmTUser getCnmTUser1() {
		return this.cnmTUser1;
	}

	public void setCnmTUser1(CnmTUser cnmTUser1) {
		this.cnmTUser1 = cnmTUser1;
	}

	public CnmTUser getCnmTUser2() {
		return this.cnmTUser2;
	}

	public void setCnmTUser2(CnmTUser cnmTUser2) {
		this.cnmTUser2 = cnmTUser2;
	}
	
	
	

	public Timestamp getDataOraUpdate() {
		return dataOraUpdate;
	}

	public void setDataOraUpdate(Timestamp dataOraUpdate) {
		this.dataOraUpdate = dataOraUpdate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CnmTDocumento [idDocumento=");
		builder.append(idDocumento);
		builder.append(", cnmDTipoDocumento=");
		builder.append(cnmDTipoDocumento);
		builder.append(", identificativoArchiviazione=");
		builder.append(identificativoArchiviazione);
		builder.append(", protocolloFornitore=");
		builder.append(protocolloFornitore);
		builder.append(", identificativoFornitore=");
		builder.append(identificativoFornitore);
		builder.append(", identificativoEntitaFruitore=");
		builder.append(identificativoEntitaFruitore);
		builder.append(", folder=");
		builder.append(folder);
		builder.append(", classificazioneId=");
		builder.append(classificazioneId);
		builder.append(", registrazioneId=");
		builder.append(registrazioneId);
		builder.append(", objectidclassificazione=");
		builder.append(objectidclassificazione);
		builder.append(", objectiddocumento=");
		builder.append(objectiddocumento);
		builder.append(", dataOraInsert=");
		builder.append(dataOraInsert);
		builder.append(", dataOraUpdate=");
		builder.append(dataOraUpdate);
		builder.append(", cnmTUser1=");
		builder.append(cnmTUser1);
		builder.append(", cnmTUser2=");
		builder.append(cnmTUser2);
		builder.append("]");
		return builder.toString();
	}



	
	
	
	

	

	
	

	

}
