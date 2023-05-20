/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.beans;

// 20200706_LC
public class ResponseSpostaDocumento  extends ResponseProtocollaDocumento implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 333388785900288354L;

	private java.lang.String idDocumento;

    private java.lang.String indiceClassificazione;

    private java.lang.String protocollo;
    


    public ResponseSpostaDocumento() {
    }

    public ResponseSpostaDocumento(
           java.lang.String idDocumento,
           java.lang.String indiceClassificazione,
           java.lang.String protocollo) {
           this.idDocumento = idDocumento;
           this.indiceClassificazione = indiceClassificazione;
           this.protocollo = protocollo;
    }


    /**
     * Gets the idDocumento value for this ResponseSpostaDocumento.
     * 
     * @return idDocumento
     */
    public java.lang.String getIdDocumento() {
        return idDocumento;
    }


    /**
     * Sets the idDocumento value for this ResponseSpostaDocumento.
     * 
     * @param idDocumento
     */
    public void setIdDocumento(java.lang.String idDocumento) {
        this.idDocumento = idDocumento;
    }


    /**
     * Gets the indiceClassificazione value for this ResponseSpostaDocumento.
     * 
     * @return indiceClassificazione
     */
    public java.lang.String getIndiceClassificazione() {
        return indiceClassificazione;
    }


    /**
     * Sets the indiceClassificazione value for this ResponseSpostaDocumento.
     * 
     * @param indiceClassificazione
     */
    public void setIndiceClassificazione(java.lang.String indiceClassificazione) {
        this.indiceClassificazione = indiceClassificazione;
    }


    /**
     * Gets the protocollo value for this ResponseSpostaDocumento.
     * 
     * @return protocollo
     */
    public java.lang.String getProtocollo() {
        return protocollo;
    }

    


	
	/**
     * Sets the protocollo value for this ResponseSpostaDocumento.
     * 
     * @param protocollo
     */
    public void setProtocollo(java.lang.String protocollo) {
        this.protocollo = protocollo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseSpostaDocumento)) return false;
        ResponseSpostaDocumento other = (ResponseSpostaDocumento) obj;
        //if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idDocumento==null && other.getIdDocumento()==null) || 
             (this.idDocumento!=null &&
              this.idDocumento.equals(other.getIdDocumento()))) &&
            ((this.indiceClassificazione==null && other.getIndiceClassificazione()==null) || 
             (this.indiceClassificazione!=null &&
              this.indiceClassificazione.equals(other.getIndiceClassificazione()))) &&
            ((this.protocollo==null && other.getProtocollo()==null) || 
             (this.protocollo!=null &&
              this.protocollo.equals(other.getProtocollo())));
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
        if (getIdDocumento() != null) {
            _hashCode += getIdDocumento().hashCode();
        }
        if (getIndiceClassificazione() != null) {
            _hashCode += getIndiceClassificazione().hashCode();
        }
        if (getProtocollo() != null) {
            _hashCode += getProtocollo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseSpostaDocumento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseSpostaDocumento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indiceClassificazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "indiceClassificazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protocollo"));
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
