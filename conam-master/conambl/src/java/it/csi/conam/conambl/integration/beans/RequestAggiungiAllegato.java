/**
 * RequestAggiungiAllegato.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class RequestAggiungiAllegato implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7231651668021384615L;

	private java.lang.String applicativoAlimentante;

	private java.lang.String autoreFisico;

	private java.lang.String autoreGiuridico;

	private java.lang.String codiceFruitore;

	private java.lang.String collocazioneCartacea;

	private java.lang.String destinatarioFisicoCF;

	private java.lang.String destinatarioFisicoDenom;

	private java.lang.String destinatarioGiuridico;

	private it.csi.conam.conambl.integration.beans.Documento documento;

	private java.lang.String idArchivioAllegato;

	private java.lang.String idArchivioPadre;

	private it.csi.conam.conambl.integration.beans.MetadatiAllegato metadatiAllegato;

	private java.lang.String mimeType;

	private java.lang.String mittentiEsterni;

	private java.lang.String originatore;

	private java.lang.String pkAllegato;

	private java.lang.String rootActa;

	private java.lang.String scrittore;

	private it.csi.conam.conambl.integration.beans.Soggetto soggetto;

	private java.lang.String soggettoActa;

	private java.lang.String soggettoProduttoreCF;

	private java.lang.String soggettoProduttoreDenom;

	private java.lang.String tipoDocumento;

	// 20210415
	private java.lang.String dataTopica;
	private java.util.Date dataCronica;

	public RequestAggiungiAllegato() {
	}

	public RequestAggiungiAllegato(java.lang.String applicativoAlimentante, java.lang.String autoreFisico, java.lang.String autoreGiuridico, java.lang.String codiceFruitore,
			java.lang.String collocazioneCartacea, java.lang.String destinatarioFisicoCF, java.lang.String destinatarioFisicoDenom, java.lang.String destinatarioGiuridico,
			it.csi.conam.conambl.integration.beans.Documento documento, java.lang.String idArchivioAllegato, java.lang.String idArchivioPadre,
			it.csi.conam.conambl.integration.beans.MetadatiAllegato metadatiAllegato, java.lang.String mimeType, java.lang.String mittentiEsterni, java.lang.String originatore,
			java.lang.String pkAllegato, java.lang.String rootActa, java.lang.String scrittore, it.csi.conam.conambl.integration.beans.Soggetto soggetto, java.lang.String soggettoActa,
			java.lang.String soggettoProduttoreCF, java.lang.String soggettoProduttoreDenom, java.lang.String tipoDocumento, java.lang.String dataTopica, java.util.Date dataCronica) {
		this.applicativoAlimentante = applicativoAlimentante;
		this.autoreFisico = autoreFisico;
		this.autoreGiuridico = autoreGiuridico;
		this.codiceFruitore = codiceFruitore;
		this.collocazioneCartacea = collocazioneCartacea;
		this.destinatarioFisicoCF = destinatarioFisicoCF;
		this.destinatarioFisicoDenom = destinatarioFisicoDenom;
		this.destinatarioGiuridico = destinatarioGiuridico;
		this.documento = documento;
		this.idArchivioAllegato = idArchivioAllegato;
		this.idArchivioPadre = idArchivioPadre;
		this.metadatiAllegato = metadatiAllegato;
		this.mimeType = mimeType;
		this.mittentiEsterni = mittentiEsterni;
		this.originatore = originatore;
		this.pkAllegato = pkAllegato;
		this.rootActa = rootActa;
		this.scrittore = scrittore;
		this.soggetto = soggetto;
		this.soggettoActa = soggettoActa;
		this.soggettoProduttoreCF = soggettoProduttoreCF;
		this.soggettoProduttoreDenom = soggettoProduttoreDenom;
		this.tipoDocumento = tipoDocumento;
		this.dataTopica = dataTopica;
		this.dataCronica = dataCronica;
	}

	/**
	 * Gets the applicativoAlimentante value for this RequestAggiungiAllegato.
	 * 
	 * @return applicativoAlimentante
	 */
	public java.lang.String getApplicativoAlimentante() {
		return applicativoAlimentante;
	}

	/**
	 * Sets the applicativoAlimentante value for this RequestAggiungiAllegato.
	 * 
	 * @param applicativoAlimentante
	 */
	public void setApplicativoAlimentante(java.lang.String applicativoAlimentante) {
		this.applicativoAlimentante = applicativoAlimentante;
	}

	/**
	 * Gets the autoreFisico value for this RequestAggiungiAllegato.
	 * 
	 * @return autoreFisico
	 */
	public java.lang.String getAutoreFisico() {
		return autoreFisico;
	}

	/**
	 * Sets the autoreFisico value for this RequestAggiungiAllegato.
	 * 
	 * @param autoreFisico
	 */
	public void setAutoreFisico(java.lang.String autoreFisico) {
		this.autoreFisico = autoreFisico;
	}

	/**
	 * Gets the autoreGiuridico value for this RequestAggiungiAllegato.
	 * 
	 * @return autoreGiuridico
	 */
	public java.lang.String getAutoreGiuridico() {
		return autoreGiuridico;
	}

	/**
	 * Sets the autoreGiuridico value for this RequestAggiungiAllegato.
	 * 
	 * @param autoreGiuridico
	 */
	public void setAutoreGiuridico(java.lang.String autoreGiuridico) {
		this.autoreGiuridico = autoreGiuridico;
	}

	/**
	 * Gets the codiceFruitore value for this RequestAggiungiAllegato.
	 * 
	 * @return codiceFruitore
	 */
	public java.lang.String getCodiceFruitore() {
		return codiceFruitore;
	}

	/**
	 * Sets the codiceFruitore value for this RequestAggiungiAllegato.
	 * 
	 * @param codiceFruitore
	 */
	public void setCodiceFruitore(java.lang.String codiceFruitore) {
		this.codiceFruitore = codiceFruitore;
	}

	/**
	 * Gets the collocazioneCartacea value for this RequestAggiungiAllegato.
	 * 
	 * @return collocazioneCartacea
	 */
	public java.lang.String getCollocazioneCartacea() {
		return collocazioneCartacea;
	}

	/**
	 * Sets the collocazioneCartacea value for this RequestAggiungiAllegato.
	 * 
	 * @param collocazioneCartacea
	 */
	public void setCollocazioneCartacea(java.lang.String collocazioneCartacea) {
		this.collocazioneCartacea = collocazioneCartacea;
	}

	/**
	 * Gets the destinatarioFisicoCF value for this RequestAggiungiAllegato.
	 * 
	 * @return destinatarioFisicoCF
	 */
	public java.lang.String getDestinatarioFisicoCF() {
		return destinatarioFisicoCF;
	}

	/**
	 * Sets the destinatarioFisicoCF value for this RequestAggiungiAllegato.
	 * 
	 * @param destinatarioFisicoCF
	 */
	public void setDestinatarioFisicoCF(java.lang.String destinatarioFisicoCF) {
		this.destinatarioFisicoCF = destinatarioFisicoCF;
	}

	/**
	 * Gets the destinatarioFisicoDenom value for this RequestAggiungiAllegato.
	 * 
	 * @return destinatarioFisicoDenom
	 */
	public java.lang.String getDestinatarioFisicoDenom() {
		return destinatarioFisicoDenom;
	}

	/**
	 * Sets the destinatarioFisicoDenom value for this RequestAggiungiAllegato.
	 * 
	 * @param destinatarioFisicoDenom
	 */
	public void setDestinatarioFisicoDenom(java.lang.String destinatarioFisicoDenom) {
		this.destinatarioFisicoDenom = destinatarioFisicoDenom;
	}

	/**
	 * Gets the destinatarioGiuridico value for this RequestAggiungiAllegato.
	 * 
	 * @return destinatarioGiuridico
	 */
	public java.lang.String getDestinatarioGiuridico() {
		return destinatarioGiuridico;
	}

	/**
	 * Sets the destinatarioGiuridico value for this RequestAggiungiAllegato.
	 * 
	 * @param destinatarioGiuridico
	 */
	public void setDestinatarioGiuridico(java.lang.String destinatarioGiuridico) {
		this.destinatarioGiuridico = destinatarioGiuridico;
	}

	/**
	 * Gets the documento value for this RequestAggiungiAllegato.
	 * 
	 * @return documento
	 */
	public it.csi.conam.conambl.integration.beans.Documento getDocumento() {
		return documento;
	}

	/**
	 * Sets the documento value for this RequestAggiungiAllegato.
	 * 
	 * @param documento
	 */
	public void setDocumento(it.csi.conam.conambl.integration.beans.Documento documento) {
		this.documento = documento;
	}

	/**
	 * Gets the idArchivioAllegato value for this RequestAggiungiAllegato.
	 * 
	 * @return idArchivioAllegato
	 */
	public java.lang.String getIdArchivioAllegato() {
		return idArchivioAllegato;
	}

	/**
	 * Sets the idArchivioAllegato value for this RequestAggiungiAllegato.
	 * 
	 * @param idArchivioAllegato
	 */
	public void setIdArchivioAllegato(java.lang.String idArchivioAllegato) {
		this.idArchivioAllegato = idArchivioAllegato;
	}

	/**
	 * Gets the idArchivioPadre value for this RequestAggiungiAllegato.
	 * 
	 * @return idArchivioPadre
	 */
	public java.lang.String getIdArchivioPadre() {
		return idArchivioPadre;
	}

	/**
	 * Sets the idArchivioPadre value for this RequestAggiungiAllegato.
	 * 
	 * @param idArchivioPadre
	 */
	public void setIdArchivioPadre(java.lang.String idArchivioPadre) {
		this.idArchivioPadre = idArchivioPadre;
	}

	/**
	 * Gets the metadatiAllegato value for this RequestAggiungiAllegato.
	 * 
	 * @return metadatiAllegato
	 */
	public it.csi.conam.conambl.integration.beans.MetadatiAllegato getMetadatiAllegato() {
		return metadatiAllegato;
	}

	/**
	 * Sets the metadatiAllegato value for this RequestAggiungiAllegato.
	 * 
	 * @param metadatiAllegato
	 */
	public void setMetadatiAllegato(it.csi.conam.conambl.integration.beans.MetadatiAllegato metadatiAllegato) {
		this.metadatiAllegato = metadatiAllegato;
	}

	/**
	 * Gets the mimeType value for this RequestAggiungiAllegato.
	 * 
	 * @return mimeType
	 */
	public java.lang.String getMimeType() {
		return mimeType;
	}

	/**
	 * Sets the mimeType value for this RequestAggiungiAllegato.
	 * 
	 * @param mimeType
	 */
	public void setMimeType(java.lang.String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * Gets the mittentiEsterni value for this RequestAggiungiAllegato.
	 * 
	 * @return mittentiEsterni
	 */
	public java.lang.String getMittentiEsterni() {
		return mittentiEsterni;
	}

	/**
	 * Sets the mittentiEsterni value for this RequestAggiungiAllegato.
	 * 
	 * @param mittentiEsterni
	 */
	public void setMittentiEsterni(java.lang.String mittentiEsterni) {
		this.mittentiEsterni = mittentiEsterni;
	}

	/**
	 * Gets the originatore value for this RequestAggiungiAllegato.
	 * 
	 * @return originatore
	 */
	public java.lang.String getOriginatore() {
		return originatore;
	}

	/**
	 * Sets the originatore value for this RequestAggiungiAllegato.
	 * 
	 * @param originatore
	 */
	public void setOriginatore(java.lang.String originatore) {
		this.originatore = originatore;
	}

	/**
	 * Gets the pkAllegato value for this RequestAggiungiAllegato.
	 * 
	 * @return pkAllegato
	 */
	public java.lang.String getPkAllegato() {
		return pkAllegato;
	}

	/**
	 * Sets the pkAllegato value for this RequestAggiungiAllegato.
	 * 
	 * @param pkAllegato
	 */
	public void setPkAllegato(java.lang.String pkAllegato) {
		this.pkAllegato = pkAllegato;
	}

	/**
	 * Gets the rootActa value for this RequestAggiungiAllegato.
	 * 
	 * @return rootActa
	 */
	public java.lang.String getRootActa() {
		return rootActa;
	}

	/**
	 * Sets the rootActa value for this RequestAggiungiAllegato.
	 * 
	 * @param rootActa
	 */
	public void setRootActa(java.lang.String rootActa) {
		this.rootActa = rootActa;
	}

	/**
	 * Gets the scrittore value for this RequestAggiungiAllegato.
	 * 
	 * @return scrittore
	 */
	public java.lang.String getScrittore() {
		return scrittore;
	}

	/**
	 * Sets the scrittore value for this RequestAggiungiAllegato.
	 * 
	 * @param scrittore
	 */
	public void setScrittore(java.lang.String scrittore) {
		this.scrittore = scrittore;
	}

	/**
	 * Gets the soggetto value for this RequestAggiungiAllegato.
	 * 
	 * @return soggetto
	 */
	public it.csi.conam.conambl.integration.beans.Soggetto getSoggetto() {
		return soggetto;
	}

	/**
	 * Sets the soggetto value for this RequestAggiungiAllegato.
	 * 
	 * @param soggetto
	 */
	public void setSoggetto(it.csi.conam.conambl.integration.beans.Soggetto soggetto) {
		this.soggetto = soggetto;
	}

	/**
	 * Gets the soggettoActa value for this RequestAggiungiAllegato.
	 * 
	 * @return soggettoActa
	 */
	public java.lang.String getSoggettoActa() {
		return soggettoActa;
	}

	/**
	 * Sets the soggettoActa value for this RequestAggiungiAllegato.
	 * 
	 * @param soggettoActa
	 */
	public void setSoggettoActa(java.lang.String soggettoActa) {
		this.soggettoActa = soggettoActa;
	}

	/**
	 * Gets the soggettoProduttoreCF value for this RequestAggiungiAllegato.
	 * 
	 * @return soggettoProduttoreCF
	 */
	public java.lang.String getSoggettoProduttoreCF() {
		return soggettoProduttoreCF;
	}

	/**
	 * Sets the soggettoProduttoreCF value for this RequestAggiungiAllegato.
	 * 
	 * @param soggettoProduttoreCF
	 */
	public void setSoggettoProduttoreCF(java.lang.String soggettoProduttoreCF) {
		this.soggettoProduttoreCF = soggettoProduttoreCF;
	}

	/**
	 * Gets the soggettoProduttoreDenom value for this RequestAggiungiAllegato.
	 * 
	 * @return soggettoProduttoreDenom
	 */
	public java.lang.String getSoggettoProduttoreDenom() {
		return soggettoProduttoreDenom;
	}

	/**
	 * Sets the soggettoProduttoreDenom value for this RequestAggiungiAllegato.
	 * 
	 * @param soggettoProduttoreDenom
	 */
	public void setSoggettoProduttoreDenom(java.lang.String soggettoProduttoreDenom) {
		this.soggettoProduttoreDenom = soggettoProduttoreDenom;
	}

	/**
	 * Gets the tipoDocumento value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return tipoDocumento
	 */
	public java.lang.String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * Sets the tipoDocumento value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param tipoDocumento
	 */
	public void setTipoDocumento(java.lang.String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	
	/**
	 * Gets the dataCronica value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return dataCronica
	 */
	public java.util.Date getDataCronica() {
		return dataCronica;
	}

	/**
	 * Sets the dataCronica value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param dataCronica
	 */
	public void setDataCronica(java.util.Date dataCronica) {
		this.dataCronica = dataCronica;
	}

	
	
	
	/**
	 * Gets the dataTopica value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return dataTopica
	 */
	public java.lang.String getDataTopica() {
		return dataTopica;
	}

	/**
	 * Sets the dataTopica value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param dataTopica
	 */
	public void setDataTopica(java.lang.String dataTopica) {
		this.dataTopica = dataTopica;
	}
	
	
	
	
	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RequestAggiungiAllegato))
			return false;
		RequestAggiungiAllegato other = (RequestAggiungiAllegato) obj;
		/*if (obj == null)
			return false;*/
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.applicativoAlimentante == null && other.getApplicativoAlimentante() == null)
						|| (this.applicativoAlimentante != null && this.applicativoAlimentante.equals(other.getApplicativoAlimentante())))
				&& ((this.autoreFisico == null && other.getAutoreFisico() == null) || (this.autoreFisico != null && this.autoreFisico.equals(other.getAutoreFisico())))
				&& ((this.autoreGiuridico == null && other.getAutoreGiuridico() == null) || (this.autoreGiuridico != null && this.autoreGiuridico.equals(other.getAutoreGiuridico())))
				&& ((this.codiceFruitore == null && other.getCodiceFruitore() == null) || (this.codiceFruitore != null && this.codiceFruitore.equals(other.getCodiceFruitore())))
				&& ((this.collocazioneCartacea == null && other.getCollocazioneCartacea() == null)
						|| (this.collocazioneCartacea != null && this.collocazioneCartacea.equals(other.getCollocazioneCartacea())))
				&& ((this.destinatarioFisicoCF == null && other.getDestinatarioFisicoCF() == null)
						|| (this.destinatarioFisicoCF != null && this.destinatarioFisicoCF.equals(other.getDestinatarioFisicoCF())))
				&& ((this.destinatarioFisicoDenom == null && other.getDestinatarioFisicoDenom() == null)
						|| (this.destinatarioFisicoDenom != null && this.destinatarioFisicoDenom.equals(other.getDestinatarioFisicoDenom())))
				&& ((this.destinatarioGiuridico == null && other.getDestinatarioGiuridico() == null)
						|| (this.destinatarioGiuridico != null && this.destinatarioGiuridico.equals(other.getDestinatarioGiuridico())))
				&& ((this.documento == null && other.getDocumento() == null) || (this.documento != null && this.documento.equals(other.getDocumento())))
				&& ((this.idArchivioAllegato == null && other.getIdArchivioAllegato() == null) || (this.idArchivioAllegato != null && this.idArchivioAllegato.equals(other.getIdArchivioAllegato())))
				&& ((this.idArchivioPadre == null && other.getIdArchivioPadre() == null) || (this.idArchivioPadre != null && this.idArchivioPadre.equals(other.getIdArchivioPadre())))
				&& ((this.metadatiAllegato == null && other.getMetadatiAllegato() == null) || (this.metadatiAllegato != null && this.metadatiAllegato.equals(other.getMetadatiAllegato())))
				&& ((this.mimeType == null && other.getMimeType() == null) || (this.mimeType != null && this.mimeType.equals(other.getMimeType())))
				&& ((this.mittentiEsterni == null && other.getMittentiEsterni() == null) || (this.mittentiEsterni != null && this.mittentiEsterni.equals(other.getMittentiEsterni())))
				&& ((this.originatore == null && other.getOriginatore() == null) || (this.originatore != null && this.originatore.equals(other.getOriginatore())))
				&& ((this.pkAllegato == null && other.getPkAllegato() == null) || (this.pkAllegato != null && this.pkAllegato.equals(other.getPkAllegato())))
				&& ((this.rootActa == null && other.getRootActa() == null) || (this.rootActa != null && this.rootActa.equals(other.getRootActa())))
				&& ((this.scrittore == null && other.getScrittore() == null) || (this.scrittore != null && this.scrittore.equals(other.getScrittore())))
				&& ((this.soggetto == null && other.getSoggetto() == null) || (this.soggetto != null && this.soggetto.equals(other.getSoggetto())))
				&& ((this.soggettoActa == null && other.getSoggettoActa() == null) || (this.soggettoActa != null && this.soggettoActa.equals(other.getSoggettoActa())))
				&& ((this.soggettoProduttoreCF == null && other.getSoggettoProduttoreCF() == null)
						|| (this.soggettoProduttoreCF != null && this.soggettoProduttoreCF.equals(other.getSoggettoProduttoreCF())))
				&& ((this.soggettoProduttoreDenom == null && other.getSoggettoProduttoreDenom() == null)
						|| (this.soggettoProduttoreDenom != null && this.soggettoProduttoreDenom.equals(other.getSoggettoProduttoreDenom())))
				&& ((this.tipoDocumento == null && other.getTipoDocumento() == null) || (this.tipoDocumento != null && this.tipoDocumento.equals(other.getTipoDocumento())));
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		if (getApplicativoAlimentante() != null) {
			_hashCode += getApplicativoAlimentante().hashCode();
		}
		if (getAutoreFisico() != null) {
			_hashCode += getAutoreFisico().hashCode();
		}
		if (getAutoreGiuridico() != null) {
			_hashCode += getAutoreGiuridico().hashCode();
		}
		if (getCodiceFruitore() != null) {
			_hashCode += getCodiceFruitore().hashCode();
		}
		if (getCollocazioneCartacea() != null) {
			_hashCode += getCollocazioneCartacea().hashCode();
		}
		if (getDestinatarioFisicoCF() != null) {
			_hashCode += getDestinatarioFisicoCF().hashCode();
		}
		if (getDestinatarioFisicoDenom() != null) {
			_hashCode += getDestinatarioFisicoDenom().hashCode();
		}
		if (getDestinatarioGiuridico() != null) {
			_hashCode += getDestinatarioGiuridico().hashCode();
		}
		if (getDocumento() != null) {
			_hashCode += getDocumento().hashCode();
		}
		if (getIdArchivioAllegato() != null) {
			_hashCode += getIdArchivioAllegato().hashCode();
		}
		if (getIdArchivioPadre() != null) {
			_hashCode += getIdArchivioPadre().hashCode();
		}
		if (getMetadatiAllegato() != null) {
			_hashCode += getMetadatiAllegato().hashCode();
		}
		if (getMimeType() != null) {
			_hashCode += getMimeType().hashCode();
		}
		if (getMittentiEsterni() != null) {
			_hashCode += getMittentiEsterni().hashCode();
		}
		if (getOriginatore() != null) {
			_hashCode += getOriginatore().hashCode();
		}
		if (getPkAllegato() != null) {
			_hashCode += getPkAllegato().hashCode();
		}
		if (getRootActa() != null) {
			_hashCode += getRootActa().hashCode();
		}
		if (getScrittore() != null) {
			_hashCode += getScrittore().hashCode();
		}
		if (getSoggetto() != null) {
			_hashCode += getSoggetto().hashCode();
		}
		if (getSoggettoActa() != null) {
			_hashCode += getSoggettoActa().hashCode();
		}
		if (getSoggettoProduttoreCF() != null) {
			_hashCode += getSoggettoProduttoreCF().hashCode();
		}
		if (getSoggettoProduttoreDenom() != null) {
			_hashCode += getSoggettoProduttoreDenom().hashCode();
		}
		if (getTipoDocumento() != null) {
			_hashCode += getTipoDocumento().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(RequestAggiungiAllegato.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "RequestAggiungiAllegato"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("applicativoAlimentante");
		elemField.setXmlName(new javax.xml.namespace.QName("", "applicativoAlimentante"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("autoreFisico");
		elemField.setXmlName(new javax.xml.namespace.QName("", "autoreFisico"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("autoreGiuridico");
		elemField.setXmlName(new javax.xml.namespace.QName("", "autoreGiuridico"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("codiceFruitore");
		elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFruitore"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("collocazioneCartacea");
		elemField.setXmlName(new javax.xml.namespace.QName("", "collocazioneCartacea"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("destinatarioFisicoCF");
		elemField.setXmlName(new javax.xml.namespace.QName("", "destinatarioFisicoCF"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("destinatarioFisicoDenom");
		elemField.setXmlName(new javax.xml.namespace.QName("", "destinatarioFisicoDenom"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("destinatarioGiuridico");
		elemField.setXmlName(new javax.xml.namespace.QName("", "destinatarioGiuridico"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("documento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "documento"));
		elemField.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Documento"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idArchivioAllegato");
		elemField.setXmlName(new javax.xml.namespace.QName("", "idArchivioAllegato"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idArchivioPadre");
		elemField.setXmlName(new javax.xml.namespace.QName("", "idArchivioPadre"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("metadatiAllegato");
		elemField.setXmlName(new javax.xml.namespace.QName("", "metadatiAllegato"));
		elemField.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "MetadatiAllegato"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("mimeType");
		elemField.setXmlName(new javax.xml.namespace.QName("", "mimeType"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("mittentiEsterni");
		elemField.setXmlName(new javax.xml.namespace.QName("", "mittentiEsterni"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("originatore");
		elemField.setXmlName(new javax.xml.namespace.QName("", "originatore"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("pkAllegato");
		elemField.setXmlName(new javax.xml.namespace.QName("", "pkAllegato"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("rootActa");
		elemField.setXmlName(new javax.xml.namespace.QName("", "rootActa"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("scrittore");
		elemField.setXmlName(new javax.xml.namespace.QName("", "scrittore"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("soggetto");
		elemField.setXmlName(new javax.xml.namespace.QName("", "soggetto"));
		elemField.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Soggetto"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("soggettoActa");
		elemField.setXmlName(new javax.xml.namespace.QName("", "soggettoActa"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("soggettoProduttoreCF");
		elemField.setXmlName(new javax.xml.namespace.QName("", "soggettoProduttoreCF"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("soggettoProduttoreDenom");
		elemField.setXmlName(new javax.xml.namespace.QName("", "soggettoProduttoreDenom"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("tipoDocumento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("dataTopica");
		elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("dataCronica");
		elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "date"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Return type metadata object
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}

	/**
	 * Get Custom Serializer
	 */
	public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType, java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType, java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

	@Override
	public String toString() {
		return "RequestAggiungiAllegato [applicativoAlimentante=" + applicativoAlimentante + ", autoreFisico=" + autoreFisico + ", autoreGiuridico=" + autoreGiuridico + ", codiceFruitore="
				+ codiceFruitore + ", collocazioneCartacea=" + collocazioneCartacea + ", destinatarioFisicoCF=" + destinatarioFisicoCF + ", destinatarioFisicoDenom=" + destinatarioFisicoDenom
				+ ", destinatarioGiuridico=" + destinatarioGiuridico + ", documento=" + documento + ", idArchivioAllegato=" + idArchivioAllegato + ", idArchivioPadre=" + idArchivioPadre
				+ ", metadatiAllegato=" + metadatiAllegato + ", mimeType=" + mimeType + ", mittentiEsterni=" + mittentiEsterni + ", originatore=" + originatore + ", pkAllegato=" + pkAllegato
				+ ", rootActa=" + rootActa + ", scrittore=" + scrittore + ", soggetto=" + soggetto + ", soggettoActa=" + soggettoActa + ", soggettoProduttoreCF=" + soggettoProduttoreCF
				+ ", soggettoProduttoreDenom=" + soggettoProduttoreDenom + ", tipoDocumento="	+ tipoDocumento + ", dataTopica="	+ dataTopica + ", dataCronica="	+ dataCronica +   "]";
	}

}