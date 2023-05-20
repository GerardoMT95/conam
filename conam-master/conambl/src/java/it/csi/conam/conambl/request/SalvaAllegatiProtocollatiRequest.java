/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request;

import it.csi.conam.conambl.vo.verbale.DocumentoProtocollatoVO;

import java.util.List;

/**
 * 
 * @author A135722
 *
 */
public class SalvaAllegatiProtocollatiRequest extends ParentRequest {

	// 20200711_LC un solo documento protocollato
	
	private static final long serialVersionUID = -3620699452083779279L;

	private Integer idVerbale;
	private DocumentoProtocollatoVO documentoProtocollato; 
	
	// 20200711_LC
//	private List<TipoAllegatoVO> tipiAllegato;
	private List<SalvaAllegatoRequest> allegati;
	
	private List<Integer> idOrdinanzaVerbaleSoggetto;

	

	// 20200715_LC
	private Integer idOrdinanza;
	
	
	public Integer getIdVerbale() {
		return idVerbale;
	}

	public void setIdVerbale(Integer idVerbale) {
		this.idVerbale = idVerbale;
	}

	public DocumentoProtocollatoVO getDocumentoProtocollato() {
		return documentoProtocollato;
	}

	public void setDocumentoProtocollato(DocumentoProtocollatoVO documentoProtocollato) {
		this.documentoProtocollato = documentoProtocollato;
	}
	

//	public List<TipoAllegatoVO> getTipiAllegato() {
//		return tipiAllegato;
//	}
//
//	public void setTipiAllegato(List<TipoAllegatoVO> tipiAllegato) {
//		this.tipiAllegato = tipiAllegato;
//	}
	

	public List<SalvaAllegatoRequest> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<SalvaAllegatoRequest> allegati) {
		this.allegati = allegati;
	}

	public List<Integer> getIdOrdinanzaVerbaleSoggetto() {
		return idOrdinanzaVerbaleSoggetto;
	}

	public void setIdOrdinanzaVerbaleSoggetto(List<Integer> idOrdinanzaVerbaleSoggetto) {
		this.idOrdinanzaVerbaleSoggetto = idOrdinanzaVerbaleSoggetto;
	}

	public Integer getIdOrdinanza() {
		return idOrdinanza;
	}

	public void setIdOrdinanza(Integer idOrdinanza) {
		this.idOrdinanza = idOrdinanza;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SalvaAllegatiProtocollatiRequest [idVerbale=");
		builder.append(idVerbale);
		builder.append(", documentoProtocollato=");
		builder.append(documentoProtocollato);
//		builder.append(", tipiAllegato=");
//		builder.append(tipiAllegato);
		builder.append(", allegati=");
		builder.append(allegati);
		builder.append(", idOrdinanzaVerbaleSoggetto=");
		builder.append(idOrdinanzaVerbaleSoggetto);
		builder.append(", idOrdinanza=");
		builder.append(idOrdinanza);
		builder.append("]");
		return builder.toString();
	}



	


	
	}

