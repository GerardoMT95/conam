/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="csi_log_audit")
@NamedQuery(name="CsiLogAudit.findAll", query="SELECT a FROM CsiLogAudit a")
public class CsiLogAudit implements Serializable{
	// 20200622_LC
	
	
	
	private static final long serialVersionUID = 1L;

	// 20200626_LC possibili operazioni (in aggiornamento?)
	public enum TraceOperation{
		INSERIMENTO_FASCICOLO("INSERIMENTO FASCICOLO"),
		INSERIMENTO_ALLEGATO("INSERIMENTO ALLEGATO"),
		RICERCA_PROTOCOLLO("RICERCA PROTOCOLLO"),
		INSERIMENTO_METADATI_ALLEGATO("INSERIMENTO METADATI ALLEGATO"),
		VISUALIZZA_ANTEPRIMA_DOCUMENTO("VISUALIZZA ANTEPRIMA DOCUMENTO"),
		SPOSTAMENTO_ALLEGATO_DA_INDEX("SPOSTAMENTO ALLEGATO DA INDEX"),
		INSERIMENTO_ALLEGATO_TI("INSERIMENTO ALLEGATO T/I"),
		INSERIMENTO_ALLEGATO_CI("INSERIMENTO ALLEGATO PREGRESSO C/I"),
		INSERIMENTO_FASCICOLO_TI("INSERIMENTO FASCICOLO PREGRESSO T/I"),
		INSERIMENTO_FASCICOLO_CI("INSERIMENTO FASCICOLO PREGRESSO C/I"),
		INSERIMENTO_FASCICOLO_PREGRESSO_TI("INSERIMENTO FASCICOLO PREGRESSO T/I"),
		INSERIMENTO_FASCICOLO_PREGRESSO_CI("INSERIMENTO FASCICOLO PREGRESSO C/I"),
		INSERIMENTO_ALLEGATO_PREGRESSO_TI("INSERIMENTO ALLEGATO PREGRESSO T/I"),
		INSERIMENTO_ALLEGATO_PREGRESSO_CI("INSERIMENTO ALLEGATO PREGRESSO C/I"),
		INSERIMENTO_FASCICOLO_PREGRESSO("INSERIMENTO FASCICOLO PREGRESSO"),
		INSERIMENTO_ALLEGATO_PREGRESSO("INSERIMENTO ALLEGATO PREGRESSO");
		
		private String operation;

		private TraceOperation(String operation) {
			this.operation = operation;
		}

		public String getOperation() {
			return operation;
		}

		public void setOperation(String operation) {
			this.operation = operation;
		}
		
	}
  
	
	// --
  
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_log")
	private Integer idLog;
	
	@Column(name="data_ora")
	private Timestamp dataOra;

	@Column(name="id_app")
	private String idApp;

	@Column(name="ip_address")
	private String ipAddress;

	@Column(name="utente")
	private String utente;

	@Column(name="operazione")
	private String operazione;

	@Column(name="ogg_oper")
	private String oggOper;

	@Column(name="key_oper")
	private String keyOper;
	
	
	// --
	
	
	public Integer getIdLog() {
		return idLog;
	}

	public void setIdLog(Integer idLog) {
		this.idLog = idLog;
	}

	public Timestamp getDataOra() {
		return dataOra;
	}

	public void setDataOra(Timestamp dataOra) {
		this.dataOra = dataOra;
	}

	public String getIdApp() {
		return idApp;
	}

	public void setIdApp(String idApp) {
		this.idApp = idApp;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public String getOperazione() {
		return operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}

	public String getOggOper() {
		return oggOper;
	}

	public void setOggOper(String oggOper) {
		this.oggOper = oggOper;
	}

	public String getKeyOper() {
		return keyOper;
	}

	public void setKeyOper(String keyOper) {
		this.keyOper = keyOper;
	}

	
	// --


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CsiLogAudit [idLog=");
		builder.append(idLog);
		builder.append(", dataOra=");
		builder.append(dataOra);
		builder.append(", idApp=");
		builder.append(idApp);
		builder.append(", ipAddress=");
		builder.append(ipAddress);
		builder.append(", utente=");
		builder.append(utente);
		builder.append(", operazione=");
		builder.append(operazione);
		builder.append(", oggOper=");
		builder.append(oggOper);
		builder.append(", key_oper=");
		builder.append(keyOper);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

