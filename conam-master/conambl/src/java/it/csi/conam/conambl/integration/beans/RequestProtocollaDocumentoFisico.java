/**
 * RequestProtocollaDocumentoFisico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class RequestProtocollaDocumentoFisico implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6647569906482005147L;

	private java.lang.String annoRegistrazionePrecedente;

	private java.lang.String applicativoAlimentante;

	private java.lang.String autoreFisico;

	private java.lang.String autoreGiuridico;

	private java.lang.String codiceFruitore;

	private java.lang.String descrizioneTipoLettera;

	private java.lang.String destinatarioFisico;

	private java.lang.String destinatarioGiuridico;

	private it.csi.conam.conambl.integration.beans.Documento documento;

	private java.lang.String folder;

	private it.csi.conam.conambl.integration.beans.Metadati metadati;

	private java.lang.String mimeType;

	private java.lang.String mittentiEsterni;

	private java.lang.String numeroRegistrazionePrecedente;

	private java.lang.String originatore;

	private boolean protocollazioneInUscitaSenzaDocumento;

	private java.lang.String rootActa;

	private java.lang.String scrittore;

	private it.csi.conam.conambl.integration.beans.Soggetto soggetto;

	private java.lang.String soggettoActa;

	private java.lang.String tipoDocumento;

	// 20200731_LC
    private java.lang.String collocazioneCartacea;

	// 20210506_LC
    private java.lang.String parolaChiaveFolderTemp;

	// 20211014
	private java.lang.String dataTopica;
	private java.util.Date dataCronica;
    

	public RequestProtocollaDocumentoFisico() {
	}

	public RequestProtocollaDocumentoFisico(java.lang.String annoRegistrazionePrecedente, java.lang.String applicativoAlimentante, java.lang.String autoreFisico, java.lang.String autoreGiuridico,
			java.lang.String codiceFruitore, java.lang.String descrizioneTipoLettera, java.lang.String destinatarioFisico, java.lang.String destinatarioGiuridico,
			it.csi.conam.conambl.integration.beans.Documento documento, java.lang.String folder, it.csi.conam.conambl.integration.beans.Metadati metadati, java.lang.String mimeType,
			java.lang.String mittentiEsterni, java.lang.String numeroRegistrazionePrecedente, java.lang.String originatore, boolean protocollazioneInUscitaSenzaDocumento, java.lang.String rootActa,
			java.lang.String scrittore, it.csi.conam.conambl.integration.beans.Soggetto soggetto, java.lang.String soggettoActa, java.lang.String tipoDocumento, java.lang.String collocazioneCartacea, 
			java.lang.String parolaChiaveFolderTemp, java.lang.String dataTopica, java.util.Date dataCronica) {
		this.annoRegistrazionePrecedente = annoRegistrazionePrecedente;
		this.applicativoAlimentante = applicativoAlimentante;
		this.autoreFisico = autoreFisico;
		this.autoreGiuridico = autoreGiuridico;
		this.codiceFruitore = codiceFruitore;
		this.descrizioneTipoLettera = descrizioneTipoLettera;
		this.destinatarioFisico = destinatarioFisico;
		this.destinatarioGiuridico = destinatarioGiuridico;
		this.documento = documento;
		this.folder = folder;
		this.metadati = metadati;
		this.mimeType = mimeType;
		this.mittentiEsterni = mittentiEsterni;
		this.numeroRegistrazionePrecedente = numeroRegistrazionePrecedente;
		this.originatore = originatore;
		this.protocollazioneInUscitaSenzaDocumento = protocollazioneInUscitaSenzaDocumento;
		this.rootActa = rootActa;
		this.scrittore = scrittore;
		this.soggetto = soggetto;
		this.soggettoActa = soggettoActa;
		this.tipoDocumento = tipoDocumento;
        this.collocazioneCartacea = collocazioneCartacea;
        this.parolaChiaveFolderTemp = parolaChiaveFolderTemp;
		this.dataTopica = dataTopica;
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
	 * Gets the annoRegistrazionePrecedente value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @return annoRegistrazionePrecedente
	 */
	public java.lang.String getAnnoRegistrazionePrecedente() {
		return annoRegistrazionePrecedente;
	}

	/**
	 * Sets the annoRegistrazionePrecedente value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @param annoRegistrazionePrecedente
	 */
	public void setAnnoRegistrazionePrecedente(java.lang.String annoRegistrazionePrecedente) {
		this.annoRegistrazionePrecedente = annoRegistrazionePrecedente;
	}

	/**
	 * Gets the applicativoAlimentante value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @return applicativoAlimentante
	 */
	public java.lang.String getApplicativoAlimentante() {
		return applicativoAlimentante;
	}

	/**
	 * Sets the applicativoAlimentante value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @param applicativoAlimentante
	 */
	public void setApplicativoAlimentante(java.lang.String applicativoAlimentante) {
		this.applicativoAlimentante = applicativoAlimentante;
	}

	/**
	 * Gets the autoreFisico value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return autoreFisico
	 */
	public java.lang.String getAutoreFisico() {
		return autoreFisico;
	}

	/**
	 * Sets the autoreFisico value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param autoreFisico
	 */
	public void setAutoreFisico(java.lang.String autoreFisico) {
		this.autoreFisico = autoreFisico;
	}

	/**
	 * Gets the autoreGiuridico value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return autoreGiuridico
	 */
	public java.lang.String getAutoreGiuridico() {
		return autoreGiuridico;
	}

	/**
	 * Sets the autoreGiuridico value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param autoreGiuridico
	 */
	public void setAutoreGiuridico(java.lang.String autoreGiuridico) {
		this.autoreGiuridico = autoreGiuridico;
	}

	/**
	 * Gets the codiceFruitore value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return codiceFruitore
	 */
	public java.lang.String getCodiceFruitore() {
		return codiceFruitore;
	}

	/**
	 * Sets the codiceFruitore value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param codiceFruitore
	 */
	public void setCodiceFruitore(java.lang.String codiceFruitore) {
		this.codiceFruitore = codiceFruitore;
	}

	/**
	 * Gets the descrizioneTipoLettera value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @return descrizioneTipoLettera
	 */
	public java.lang.String getDescrizioneTipoLettera() {
		return descrizioneTipoLettera;
	}

	/**
	 * Sets the descrizioneTipoLettera value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @param descrizioneTipoLettera
	 */
	public void setDescrizioneTipoLettera(java.lang.String descrizioneTipoLettera) {
		this.descrizioneTipoLettera = descrizioneTipoLettera;
	}

	/**
	 * Gets the destinatarioFisico value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @return destinatarioFisico
	 */
	public java.lang.String getDestinatarioFisico() {
		return destinatarioFisico;
	}

	/**
	 * Sets the destinatarioFisico value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @param destinatarioFisico
	 */
	public void setDestinatarioFisico(java.lang.String destinatarioFisico) {
		this.destinatarioFisico = destinatarioFisico;
	}

	/**
	 * Gets the destinatarioGiuridico value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @return destinatarioGiuridico
	 */
	public java.lang.String getDestinatarioGiuridico() {
		return destinatarioGiuridico;
	}

	/**
	 * Sets the destinatarioGiuridico value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @param destinatarioGiuridico
	 */
	public void setDestinatarioGiuridico(java.lang.String destinatarioGiuridico) {
		this.destinatarioGiuridico = destinatarioGiuridico;
	}

	/**
	 * Gets the documento value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return documento
	 */
	public it.csi.conam.conambl.integration.beans.Documento getDocumento() {
		return documento;
	}

	/**
	 * Sets the documento value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param documento
	 */
	public void setDocumento(it.csi.conam.conambl.integration.beans.Documento documento) {
		this.documento = documento;
	}

	/**
	 * Gets the folder value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return folder
	 */
	public java.lang.String getFolder() {
		return folder;
	}

	/**
	 * Sets the folder value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param folder
	 */
	public void setFolder(java.lang.String folder) {
		this.folder = folder;
	}

	/**
	 * Gets the metadati value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return metadati
	 */
	public it.csi.conam.conambl.integration.beans.Metadati getMetadati() {
		return metadati;
	}

	/**
	 * Sets the metadati value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param metadati
	 */
	public void setMetadati(it.csi.conam.conambl.integration.beans.Metadati metadati) {
		this.metadati = metadati;
	}

	/**
	 * Gets the mimeType value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return mimeType
	 */
	public java.lang.String getMimeType() {
		return mimeType;
	}

	/**
	 * Sets the mimeType value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param mimeType
	 */
	public void setMimeType(java.lang.String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * Gets the mittentiEsterni value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return mittentiEsterni
	 */
	public java.lang.String getMittentiEsterni() {
		return mittentiEsterni;
	}

	/**
	 * Sets the mittentiEsterni value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param mittentiEsterni
	 */
	public void setMittentiEsterni(java.lang.String mittentiEsterni) {
		this.mittentiEsterni = mittentiEsterni;
	}

	/**
	 * Gets the numeroRegistrazionePrecedente value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @return numeroRegistrazionePrecedente
	 */
	public java.lang.String getNumeroRegistrazionePrecedente() {
		return numeroRegistrazionePrecedente;
	}

	/**
	 * Sets the numeroRegistrazionePrecedente value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @param numeroRegistrazionePrecedente
	 */
	public void setNumeroRegistrazionePrecedente(java.lang.String numeroRegistrazionePrecedente) {
		this.numeroRegistrazionePrecedente = numeroRegistrazionePrecedente;
	}

	/**
	 * Gets the originatore value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return originatore
	 */
	public java.lang.String getOriginatore() {
		return originatore;
	}

	/**
	 * Sets the originatore value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param originatore
	 */
	public void setOriginatore(java.lang.String originatore) {
		this.originatore = originatore;
	}

	/**
	 * Gets the protocollazioneInUscitaSenzaDocumento value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @return protocollazioneInUscitaSenzaDocumento
	 */
	public boolean isProtocollazioneInUscitaSenzaDocumento() {
		return protocollazioneInUscitaSenzaDocumento;
	}

	/**
	 * Sets the protocollazioneInUscitaSenzaDocumento value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @param protocollazioneInUscitaSenzaDocumento
	 */
	public void setProtocollazioneInUscitaSenzaDocumento(boolean protocollazioneInUscitaSenzaDocumento) {
		this.protocollazioneInUscitaSenzaDocumento = protocollazioneInUscitaSenzaDocumento;
	}

	/**
	 * Gets the rootActa value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return rootActa
	 */
	public java.lang.String getRootActa() {
		return rootActa;
	}

	/**
	 * Sets the rootActa value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param rootActa
	 */
	public void setRootActa(java.lang.String rootActa) {
		this.rootActa = rootActa;
	}

	/**
	 * Gets the scrittore value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return scrittore
	 */
	public java.lang.String getScrittore() {
		return scrittore;
	}

	/**
	 * Sets the scrittore value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param scrittore
	 */
	public void setScrittore(java.lang.String scrittore) {
		this.scrittore = scrittore;
	}

	/**
	 * Gets the soggetto value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return soggetto
	 */
	public it.csi.conam.conambl.integration.beans.Soggetto getSoggetto() {
		return soggetto;
	}

	/**
	 * Sets the soggetto value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param soggetto
	 */
	public void setSoggetto(it.csi.conam.conambl.integration.beans.Soggetto soggetto) {
		this.soggetto = soggetto;
	}

	/**
	 * Gets the soggettoActa value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return soggettoActa
	 */
	public java.lang.String getSoggettoActa() {
		return soggettoActa;
	}

	/**
	 * Sets the soggettoActa value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param soggettoActa
	 */
	public void setSoggettoActa(java.lang.String soggettoActa) {
		this.soggettoActa = soggettoActa;
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
     * Gets the collocazioneCartacea value for this RequestProtocollaDocumentoLogico.
     * 
     * @return collocazioneCartacea
     */
    public java.lang.String getCollocazioneCartacea() {
        return collocazioneCartacea;
    }


    /**
     * Sets the collocazioneCartacea value for this RequestProtocollaDocumentoLogico.
     * 
     * @param collocazioneCartacea
     */
    public void setCollocazioneCartacea(java.lang.String collocazioneCartacea) {
        this.collocazioneCartacea = collocazioneCartacea;
    }
    
    
    
    
    
	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RequestProtocollaDocumentoFisico))
			return false;
		RequestProtocollaDocumentoFisico other = (RequestProtocollaDocumentoFisico) obj;
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
				&& ((this.annoRegistrazionePrecedente == null && other.getAnnoRegistrazionePrecedente() == null)
						|| (this.annoRegistrazionePrecedente != null && this.annoRegistrazionePrecedente.equals(other.getAnnoRegistrazionePrecedente())))
				&& ((this.applicativoAlimentante == null && other.getApplicativoAlimentante() == null)
						|| (this.applicativoAlimentante != null && this.applicativoAlimentante.equals(other.getApplicativoAlimentante())))
				&& ((this.autoreFisico == null && other.getAutoreFisico() == null) || (this.autoreFisico != null && this.autoreFisico.equals(other.getAutoreFisico())))
				&& ((this.autoreGiuridico == null && other.getAutoreGiuridico() == null) || (this.autoreGiuridico != null && this.autoreGiuridico.equals(other.getAutoreGiuridico())))
				&& ((this.codiceFruitore == null && other.getCodiceFruitore() == null) || (this.codiceFruitore != null && this.codiceFruitore.equals(other.getCodiceFruitore())))
				&& ((this.descrizioneTipoLettera == null && other.getDescrizioneTipoLettera() == null)
						|| (this.descrizioneTipoLettera != null && this.descrizioneTipoLettera.equals(other.getDescrizioneTipoLettera())))
				&& ((this.destinatarioFisico == null && other.getDestinatarioFisico() == null) || (this.destinatarioFisico != null && this.destinatarioFisico.equals(other.getDestinatarioFisico())))
				&& ((this.destinatarioGiuridico == null && other.getDestinatarioGiuridico() == null)
						|| (this.destinatarioGiuridico != null && this.destinatarioGiuridico.equals(other.getDestinatarioGiuridico())))
				&& ((this.documento == null && other.getDocumento() == null) || (this.documento != null && this.documento.equals(other.getDocumento())))
				&& ((this.folder == null && other.getFolder() == null) || (this.folder != null && this.folder.equals(other.getFolder())))
				&& ((this.metadati == null && other.getMetadati() == null) || (this.metadati != null && this.metadati.equals(other.getMetadati())))
				&& ((this.mimeType == null && other.getMimeType() == null) || (this.mimeType != null && this.mimeType.equals(other.getMimeType())))
				&& ((this.mittentiEsterni == null && other.getMittentiEsterni() == null) || (this.mittentiEsterni != null && this.mittentiEsterni.equals(other.getMittentiEsterni())))
				&& ((this.numeroRegistrazionePrecedente == null && other.getNumeroRegistrazionePrecedente() == null)
						|| (this.numeroRegistrazionePrecedente != null && this.numeroRegistrazionePrecedente.equals(other.getNumeroRegistrazionePrecedente())))
				&& ((this.originatore == null && other.getOriginatore() == null) || (this.originatore != null && this.originatore.equals(other.getOriginatore())))
				&& this.protocollazioneInUscitaSenzaDocumento == other.isProtocollazioneInUscitaSenzaDocumento()
				&& ((this.rootActa == null && other.getRootActa() == null) || (this.rootActa != null && this.rootActa.equals(other.getRootActa())))
				&& ((this.scrittore == null && other.getScrittore() == null) || (this.scrittore != null && this.scrittore.equals(other.getScrittore())))
				&& ((this.soggetto == null && other.getSoggetto() == null) || (this.soggetto != null && this.soggetto.equals(other.getSoggetto())))
				&& ((this.soggettoActa == null && other.getSoggettoActa() == null) || (this.soggettoActa != null && this.soggettoActa.equals(other.getSoggettoActa())))
				&& ((this.tipoDocumento == null && other.getTipoDocumento() == null) || (this.tipoDocumento != null && this.tipoDocumento.equals(other.getTipoDocumento()))) 
				&& ((this.parolaChiaveFolderTemp == null && other.getTipoDocumento() == null) || (this.parolaChiaveFolderTemp != null && this.parolaChiaveFolderTemp.equals(other.getTipoDocumento()))) 
				&& ((this.collocazioneCartacea==null && other.getCollocazioneCartacea()==null) || (this.collocazioneCartacea!=null && this.collocazioneCartacea.equals(other.getCollocazioneCartacea())));
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
		if (getAnnoRegistrazionePrecedente() != null) {
			_hashCode += getAnnoRegistrazionePrecedente().hashCode();
		}
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
		if (getDescrizioneTipoLettera() != null) {
			_hashCode += getDescrizioneTipoLettera().hashCode();
		}
		if (getDestinatarioFisico() != null) {
			_hashCode += getDestinatarioFisico().hashCode();
		}
		if (getDestinatarioGiuridico() != null) {
			_hashCode += getDestinatarioGiuridico().hashCode();
		}
		if (getDocumento() != null) {
			_hashCode += getDocumento().hashCode();
		}
		if (getFolder() != null) {
			_hashCode += getFolder().hashCode();
		}
		if (getMetadati() != null) {
			_hashCode += getMetadati().hashCode();
		}
		if (getMimeType() != null) {
			_hashCode += getMimeType().hashCode();
		}
		if (getMittentiEsterni() != null) {
			_hashCode += getMittentiEsterni().hashCode();
		}
		if (getNumeroRegistrazionePrecedente() != null) {
			_hashCode += getNumeroRegistrazionePrecedente().hashCode();
		}
		if (getOriginatore() != null) {
			_hashCode += getOriginatore().hashCode();
		}
		_hashCode += (isProtocollazioneInUscitaSenzaDocumento() ? Boolean.TRUE : Boolean.FALSE).hashCode();
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
		if (getTipoDocumento() != null) {
			_hashCode += getTipoDocumento().hashCode();
		}
        if (getCollocazioneCartacea() != null) {
            _hashCode += getCollocazioneCartacea().hashCode();
        }
        if (getParolaChiaveFolderTemp() != null) {
            _hashCode += getParolaChiaveFolderTemp().hashCode();
        }
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(RequestProtocollaDocumentoFisico.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "RequestProtocollaDocumentoFisico"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("annoRegistrazionePrecedente");
		elemField.setXmlName(new javax.xml.namespace.QName("", "annoRegistrazionePrecedente"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
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
		elemField.setFieldName("descrizioneTipoLettera");
		elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneTipoLettera"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("destinatarioFisico");
		elemField.setXmlName(new javax.xml.namespace.QName("", "destinatarioFisico"));
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
		elemField.setFieldName("folder");
		elemField.setXmlName(new javax.xml.namespace.QName("", "folder"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("metadati");
		elemField.setXmlName(new javax.xml.namespace.QName("", "metadati"));
		elemField.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Metadati"));
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
		elemField.setFieldName("numeroRegistrazionePrecedente");
		elemField.setXmlName(new javax.xml.namespace.QName("", "numeroRegistrazionePrecedente"));
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
		elemField.setFieldName("protocollazioneInUscitaSenzaDocumento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "protocollazioneInUscitaSenzaDocumento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
		elemField.setNillable(false);
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
		elemField.setFieldName("tipoDocumento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
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
        elemField.setFieldName("parolaChiaveFolderTemp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parolaChiaveFolderTemp"));
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
		return "RequestProtocollaDocumentoFisico [annoRegistrazionePrecedente=" + annoRegistrazionePrecedente + ", applicativoAlimentante=" + applicativoAlimentante + ", autoreFisico=" + autoreFisico
				+ ", autoreGiuridico=" + autoreGiuridico + ", codiceFruitore=" + codiceFruitore + ", descrizioneTipoLettera=" + descrizioneTipoLettera + ", destinatarioFisico=" + destinatarioFisico
				+ ", destinatarioGiuridico=" + destinatarioGiuridico + ", documento=" + documento + ", folder=" + folder + ", metadati=" + metadati + ", mimeType=" + mimeType + ", mittentiEsterni="
				+ mittentiEsterni + ", numeroRegistrazionePrecedente=" + numeroRegistrazionePrecedente + ", originatore=" + originatore + ", protocollazioneInUscitaSenzaDocumento="
				+ protocollazioneInUscitaSenzaDocumento + ", rootActa=" + rootActa + ", scrittore=" + scrittore + ", soggetto=" + soggetto + ", soggettoActa=" + soggettoActa + ", tipoDocumento="
				+ tipoDocumento + ", dataTopica="	+ dataTopica + ", dataCronica="	+ dataCronica + "]";
	}

	public java.lang.String getParolaChiaveFolderTemp() {
		return parolaChiaveFolderTemp;
	}

	public void setParolaChiaveFolderTemp(java.lang.String parolaChiaveFolderTemp) {
		this.parolaChiaveFolderTemp = parolaChiaveFolderTemp;
	}

}
