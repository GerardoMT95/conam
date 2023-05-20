/**
 * RequestCambiaStatoRichiesta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class RequestCambiaStatoRichiesta  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -9031296723252278391L;

	private java.lang.String codiceFruitore;

    private java.lang.String idDocumento;

    private java.lang.Long idRichiesta;

    private java.lang.String statoRichiesta;

	public static final String STATO_RICHIESTA_OK = "STATO_RICHIESTA_OK";

	public static final String STATO_RICHIESTA_KO = "STATO_RICHIESTA_KO";
	
    public RequestCambiaStatoRichiesta() {
    }

    public RequestCambiaStatoRichiesta(
           java.lang.String codiceFruitore,
           java.lang.String idDocumento,
           java.lang.Long idRichiesta,
           java.lang.String statoRichiesta) {
           this.codiceFruitore = codiceFruitore;
           this.idDocumento = idDocumento;
           this.idRichiesta = idRichiesta;
           this.statoRichiesta = statoRichiesta;
    }


    /**
     * Gets the codiceFruitore value for this RequestCambiaStatoRichiesta.
     * 
     * @return codiceFruitore
     */
    public java.lang.String getCodiceFruitore() {
        return codiceFruitore;
    }


    /**
     * Sets the codiceFruitore value for this RequestCambiaStatoRichiesta.
     * 
     * @param codiceFruitore
     */
    public void setCodiceFruitore(java.lang.String codiceFruitore) {
        this.codiceFruitore = codiceFruitore;
    }


    /**
     * Gets the idDocumento value for this RequestCambiaStatoRichiesta.
     * 
     * @return idDocumento
     */
    public java.lang.String getIdDocumento() {
        return idDocumento;
    }


    /**
     * Sets the idDocumento value for this RequestCambiaStatoRichiesta.
     * 
     * @param idDocumento
     */
    public void setIdDocumento(java.lang.String idDocumento) {
        this.idDocumento = idDocumento;
    }


    /**
     * Gets the idRichiesta value for this RequestCambiaStatoRichiesta.
     * 
     * @return idRichiesta
     */
    public java.lang.Long getIdRichiesta() {
        return idRichiesta;
    }


    /**
     * Sets the idRichiesta value for this RequestCambiaStatoRichiesta.
     * 
     * @param idRichiesta
     */
    public void setIdRichiesta(java.lang.Long idRichiesta) {
        this.idRichiesta = idRichiesta;
    }


    /**
     * Gets the statoRichiesta value for this RequestCambiaStatoRichiesta.
     * 
     * @return statoRichiesta
     */
    public java.lang.String getStatoRichiesta() {
        return statoRichiesta;
    }


    /**
     * Sets the statoRichiesta value for this RequestCambiaStatoRichiesta.
     * 
     * @param statoRichiesta
     */
    public void setStatoRichiesta(java.lang.String statoRichiesta) {
        this.statoRichiesta = statoRichiesta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RequestCambiaStatoRichiesta)) return false;
        RequestCambiaStatoRichiesta other = (RequestCambiaStatoRichiesta) obj;
        //if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceFruitore==null && other.getCodiceFruitore()==null) || 
             (this.codiceFruitore!=null &&
              this.codiceFruitore.equals(other.getCodiceFruitore()))) &&
            ((this.idDocumento==null && other.getIdDocumento()==null) || 
             (this.idDocumento!=null &&
              this.idDocumento.equals(other.getIdDocumento()))) &&
            ((this.idRichiesta==null && other.getIdRichiesta()==null) || 
             (this.idRichiesta!=null &&
              this.idRichiesta.equals(other.getIdRichiesta()))) &&
            ((this.statoRichiesta==null && other.getStatoRichiesta()==null) || 
             (this.statoRichiesta!=null &&
              this.statoRichiesta.equals(other.getStatoRichiesta())));
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
        if (getCodiceFruitore() != null) {
            _hashCode += getCodiceFruitore().hashCode();
        }
        if (getIdDocumento() != null) {
            _hashCode += getIdDocumento().hashCode();
        }
        if (getIdRichiesta() != null) {
            _hashCode += getIdRichiesta().hashCode();
        }
        if (getStatoRichiesta() != null) {
            _hashCode += getStatoRichiesta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RequestCambiaStatoRichiesta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "RequestCambiaStatoRichiesta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFruitore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFruitore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRichiesta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idRichiesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "long"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoRichiesta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statoRichiesta"));
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
