/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="cnm_c_proprieta", uniqueConstraints=@UniqueConstraint(name="cnm_c_proprieta_UN1", columnNames = {"nome"}))
@NamedQuery(name="CnmCProprieta.findAll", query="SELECT c FROM CnmCProprieta c")
public class CnmCProprieta implements Serializable{

	/**
	 * 	
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_proprieta")
	private long idProprieta;

	@Column(name="nome")
	private String nome;

	@Column(name="valore")
	private String valore;

	public enum PropKey{
		IS_DOQUI_DIRECT,
		ACTA_SERVER,
		ACTA_CONTEXT,
		ACTA_PORT,
		ACTA_IS_WS,
		ACTA_ID_AOO,
		ACTA_CF,
		ACTA_ID_STRUTTURA,
		ACTA_ID_NODO, 
		ACTA_CODE_FRUITORE,
		ACTA_SIGN_STEP_BYPASS_FLAG,
		INDEX_REPOSITORY,
		INDEX_USERNAME,
		INDEX_PASSWORD,
		INDEX_ENDPOINT,
		INDEX_FRUITORE,
		INDEX_CUSTOM_MODEL,
		SCHEDULED_TOACTA_FIXED_RATE,
		SCHEDULED_TOACTA_POOL_SIZE,
		APIMANAGER_OAUTHURL,
		APIMANAGER_CONSUMERKEY,
		APIMANAGER_CONSUMERSECRET,
		APIMANAGER_ENABLED,
		APIMANAGER_URL,
		APIMANAGER_URL_END,
		ACTA_TEMP_FOLDER
	}


	public long getIdProprieta() {
		return idProprieta;
	}

	public void setIdProprieta(long idProprieta) {
		this.idProprieta = idProprieta;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}
	
	
}
