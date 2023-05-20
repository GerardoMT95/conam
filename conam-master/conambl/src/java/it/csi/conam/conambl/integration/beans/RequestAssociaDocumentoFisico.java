/**
 * RequestAssociaDocumentoFisico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class RequestAssociaDocumentoFisico  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8966454291706417665L;

	private java.lang.String applicativoAlimentante;

    private java.lang.String autoreGiuridico;

    private java.lang.String codiceFruitore;

    private java.lang.String dataTopica;

    private it.csi.conam.conambl.integration.beans.Documento documento;

    private java.lang.String idDocumento;

    private java.lang.String originatore;

    public RequestAssociaDocumentoFisico() {
    }

    public RequestAssociaDocumentoFisico(
           java.lang.String applicativoAlimentante,
           java.lang.String autoreGiuridico,
           java.lang.String codiceFruitore,
           java.lang.String dataTopica,
           it.csi.conam.conambl.integration.beans.Documento documento,
           java.lang.String idDocumento,
           java.lang.String originatore) {
           this.applicativoAlimentante = applicativoAlimentante;
           this.autoreGiuridico = autoreGiuridico;
           this.codiceFruitore = codiceFruitore;
           this.dataTopica = dataTopica;
           this.documento = documento;
           this.idDocumento = idDocumento;
           this.originatore = originatore;
    }


    /**
     * Gets the applicativoAlimentante value for this RequestAssociaDocumentoFisico.
     * 
     * @return applicativoAlimentante
     */
    public java.lang.String getApplicativoAlimentante() {
        return applicativoAlimentante;
    }


    /**
     * Sets the applicativoAlimentante value for this RequestAssociaDocumentoFisico.
     * 
     * @param applicativoAlimentante
     */
    public void setApplicativoAlimentante(java.lang.String applicativoAlimentante) {
        this.applicativoAlimentante = applicativoAlimentante;
    }


    /**
     * Gets the autoreGiuridico value for this RequestAssociaDocumentoFisico.
     * 
     * @return autoreGiuridico
     */
    public java.lang.String getAutoreGiuridico() {
        return autoreGiuridico;
    }


    /**
     * Sets the autoreGiuridico value for this RequestAssociaDocumentoFisico.
     * 
     * @param autoreGiuridico
     */
    public void setAutoreGiuridico(java.lang.String autoreGiuridico) {
        this.autoreGiuridico = autoreGiuridico;
    }


    /**
     * Gets the codiceFruitore value for this RequestAssociaDocumentoFisico.
     * 
     * @return codiceFruitore
     */
    public java.lang.String getCodiceFruitore() {
        return codiceFruitore;
    }


    /**
     * Sets the codiceFruitore value for this RequestAssociaDocumentoFisico.
     * 
     * @param codiceFruitore
     */
    public void setCodiceFruitore(java.lang.String codiceFruitore) {
        this.codiceFruitore = codiceFruitore;
    }


    /**
     * Gets the dataTopica value for this RequestAssociaDocumentoFisico.
     * 
     * @return dataTopica
     */
    public java.lang.String getDataTopica() {
        return dataTopica;
    }


    /**
     * Sets the dataTopica value for this RequestAssociaDocumentoFisico.
     * 
     * @param dataTopica
     */
    public void setDataTopica(java.lang.String dataTopica) {
        this.dataTopica = dataTopica;
    }


    /**
     * Gets the documento value for this RequestAssociaDocumentoFisico.
     * 
     * @return documento
     */
    public it.csi.conam.conambl.integration.beans.Documento getDocumento() {
        return documento;
    }


    /**
     * Sets the documento value for this RequestAssociaDocumentoFisico.
     * 
     * @param documento
     */
    public void setDocumento(it.csi.conam.conambl.integration.beans.Documento documento) {
        this.documento = documento;
    }


    /**
     * Gets the idDocumento value for this RequestAssociaDocumentoFisico.
     * 
     * @return idDocumento
     */
    public java.lang.String getIdDocumento() {
        return idDocumento;
    }


    /**
     * Sets the idDocumento value for this RequestAssociaDocumentoFisico.
     * 
     * @param idDocumento
     */
    public void setIdDocumento(java.lang.String idDocumento) {
        this.idDocumento = idDocumento;
    }


    /**
     * Gets the originatore value for this RequestAssociaDocumentoFisico.
     * 
     * @return originatore
     */
    public java.lang.String getOriginatore() {
        return originatore;
    }


    /**
     * Sets the originatore value for this RequestAssociaDocumentoFisico.
     * 
     * @param originatore
     */
    public void setOriginatore(java.lang.String originatore) {
        this.originatore = originatore;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RequestAssociaDocumentoFisico)) return false;
        RequestAssociaDocumentoFisico other = (RequestAssociaDocumentoFisico) obj;
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
            ((this.autoreGiuridico==null && other.getAutoreGiuridico()==null) || 
             (this.autoreGiuridico!=null &&
              this.autoreGiuridico.equals(other.getAutoreGiuridico()))) &&
            ((this.codiceFruitore==null && other.getCodiceFruitore()==null) || 
             (this.codiceFruitore!=null &&
              this.codiceFruitore.equals(other.getCodiceFruitore()))) &&
            ((this.dataTopica==null && other.getDataTopica()==null) || 
             (this.dataTopica!=null &&
              this.dataTopica.equals(other.getDataTopica()))) &&
            ((this.documento==null && other.getDocumento()==null) || 
             (this.documento!=null &&
              this.documento.equals(other.getDocumento()))) &&
            ((this.idDocumento==null && other.getIdDocumento()==null) || 
             (this.idDocumento!=null &&
              this.idDocumento.equals(other.getIdDocumento()))) &&
            ((this.originatore==null && other.getOriginatore()==null) || 
             (this.originatore!=null &&
              this.originatore.equals(other.getOriginatore())));
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
        if (getAutoreGiuridico() != null) {
            _hashCode += getAutoreGiuridico().hashCode();
        }
        if (getCodiceFruitore() != null) {
            _hashCode += getCodiceFruitore().hashCode();
        }
        if (getDataTopica() != null) {
            _hashCode += getDataTopica().hashCode();
        }
        if (getDocumento() != null) {
            _hashCode += getDocumento().hashCode();
        }
        if (getIdDocumento() != null) {
            _hashCode += getIdDocumento().hashCode();
        }
        if (getOriginatore() != null) {
            _hashCode += getOriginatore().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RequestAssociaDocumentoFisico.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "RequestAssociaDocumentoFisico"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("applicativoAlimentante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "applicativoAlimentante"));
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
        elemField.setFieldName("dataTopica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataTopica"));
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
        elemField.setFieldName("idDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originatore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "originatore"));
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
