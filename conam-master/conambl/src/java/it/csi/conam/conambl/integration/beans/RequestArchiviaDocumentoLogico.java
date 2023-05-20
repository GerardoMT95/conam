/**
 * RequestArchiviaDocumentoLogico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class RequestArchiviaDocumentoLogico  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6965731995622764202L;

	private java.lang.String applicativoAlimentante;

    private java.lang.String autoreFisico;

    private java.lang.String codiceFruitore;

    private java.lang.String folder;

    private it.csi.conam.conambl.integration.beans.Metadati metadati;

    private it.csi.conam.conambl.integration.beans.Soggetto soggetto;

    private java.lang.String tipoDocumento;

	// 20200731_LC
    private java.lang.String collocazioneCartacea;
    
    
    

    public RequestArchiviaDocumentoLogico() {
    }

    public RequestArchiviaDocumentoLogico(
           java.lang.String applicativoAlimentante,
           java.lang.String autoreFisico,
           java.lang.String codiceFruitore,
           java.lang.String folder,
           it.csi.conam.conambl.integration.beans.Metadati metadati,
           it.csi.conam.conambl.integration.beans.Soggetto soggetto,
           java.lang.String tipoDocumento, 
           java.lang.String collocazioneCartacea) {
           this.applicativoAlimentante = applicativoAlimentante;
           this.autoreFisico = autoreFisico;
           this.codiceFruitore = codiceFruitore;
           this.folder = folder;
           this.metadati = metadati;
           this.soggetto = soggetto;
           this.tipoDocumento = tipoDocumento;
           this.collocazioneCartacea = collocazioneCartacea;
    }


    /**
     * Gets the applicativoAlimentante value for this RequestArchiviaDocumentoLogico.
     * 
     * @return applicativoAlimentante
     */
    public java.lang.String getApplicativoAlimentante() {
        return applicativoAlimentante;
    }


    /**
     * Sets the applicativoAlimentante value for this RequestArchiviaDocumentoLogico.
     * 
     * @param applicativoAlimentante
     */
    public void setApplicativoAlimentante(java.lang.String applicativoAlimentante) {
        this.applicativoAlimentante = applicativoAlimentante;
    }


    /**
     * Gets the autoreFisico value for this RequestArchiviaDocumentoLogico.
     * 
     * @return autoreFisico
     */
    public java.lang.String getAutoreFisico() {
        return autoreFisico;
    }


    /**
     * Sets the autoreFisico value for this RequestArchiviaDocumentoLogico.
     * 
     * @param autoreFisico
     */
    public void setAutoreFisico(java.lang.String autoreFisico) {
        this.autoreFisico = autoreFisico;
    }


    /**
     * Gets the codiceFruitore value for this RequestArchiviaDocumentoLogico.
     * 
     * @return codiceFruitore
     */
    public java.lang.String getCodiceFruitore() {
        return codiceFruitore;
    }


    /**
     * Sets the codiceFruitore value for this RequestArchiviaDocumentoLogico.
     * 
     * @param codiceFruitore
     */
    public void setCodiceFruitore(java.lang.String codiceFruitore) {
        this.codiceFruitore = codiceFruitore;
    }


    /**
     * Gets the folder value for this RequestArchiviaDocumentoLogico.
     * 
     * @return folder
     */
    public java.lang.String getFolder() {
        return folder;
    }


    /**
     * Sets the folder value for this RequestArchiviaDocumentoLogico.
     * 
     * @param folder
     */
    public void setFolder(java.lang.String folder) {
        this.folder = folder;
    }


    /**
     * Gets the metadati value for this RequestArchiviaDocumentoLogico.
     * 
     * @return metadati
     */
    public it.csi.conam.conambl.integration.beans.Metadati getMetadati() {
        return metadati;
    }


    /**
     * Sets the metadati value for this RequestArchiviaDocumentoLogico.
     * 
     * @param metadati
     */
    public void setMetadati(it.csi.conam.conambl.integration.beans.Metadati metadati) {
        this.metadati = metadati;
    }


    /**
     * Gets the soggetto value for this RequestArchiviaDocumentoLogico.
     * 
     * @return soggetto
     */
    public it.csi.conam.conambl.integration.beans.Soggetto getSoggetto() {
        return soggetto;
    }


    /**
     * Sets the soggetto value for this RequestArchiviaDocumentoLogico.
     * 
     * @param soggetto
     */
    public void setSoggetto(it.csi.conam.conambl.integration.beans.Soggetto soggetto) {
        this.soggetto = soggetto;
    }


    /**
     * Gets the tipoDocumento value for this RequestArchiviaDocumentoLogico.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this RequestArchiviaDocumentoLogico.
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
        if (!(obj instanceof RequestArchiviaDocumentoLogico)) return false;
        RequestArchiviaDocumentoLogico other = (RequestArchiviaDocumentoLogico) obj;
        //if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.applicativoAlimentante==null && other.getApplicativoAlimentante()==null) || 
             (this.applicativoAlimentante!=null &&
              this.applicativoAlimentante.equals(other.getApplicativoAlimentante()))) &&
            ((this.autoreFisico==null && other.getAutoreFisico()==null) || 
             (this.autoreFisico!=null &&
              this.autoreFisico.equals(other.getAutoreFisico()))) &&
            ((this.codiceFruitore==null && other.getCodiceFruitore()==null) || 
             (this.codiceFruitore!=null &&
              this.codiceFruitore.equals(other.getCodiceFruitore()))) &&
            ((this.folder==null && other.getFolder()==null) || 
             (this.folder!=null &&
              this.folder.equals(other.getFolder()))) &&
            ((this.metadati==null && other.getMetadati()==null) || 
             (this.metadati!=null &&
              this.metadati.equals(other.getMetadati()))) &&
            ((this.soggetto==null && other.getSoggetto()==null) || 
             (this.soggetto!=null &&
              this.soggetto.equals(other.getSoggetto()))) &&
            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento())))	&& 
            ((this.collocazioneCartacea==null && other.getCollocazioneCartacea()==null) || 
             (this.collocazioneCartacea!=null && 
              this.collocazioneCartacea.equals(other.getCollocazioneCartacea())));
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
        if (getCodiceFruitore() != null) {
            _hashCode += getCodiceFruitore().hashCode();
        }
        if (getFolder() != null) {
            _hashCode += getFolder().hashCode();
        }
        if (getMetadati() != null) {
            _hashCode += getMetadati().hashCode();
        }
        if (getSoggetto() != null) {
            _hashCode += getSoggetto().hashCode();
        }
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
        }
        if (getCollocazioneCartacea() != null) {
            _hashCode += getCollocazioneCartacea().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RequestArchiviaDocumentoLogico.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "RequestArchiviaDocumentoLogico"));
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
        elemField.setFieldName("codiceFruitore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFruitore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
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
        elemField.setFieldName("soggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Soggetto"));
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
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
