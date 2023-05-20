/**
 * ResponseRecuperaRiferimentoDocumentoFisico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class ResponseRecuperaRiferimentoDocumentoFisico  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8761226686930930263L;

	private it.csi.conam.conambl.integration.beans.Context cxt;

    private long idRichiesta;

    private java.lang.String uuid;

    public ResponseRecuperaRiferimentoDocumentoFisico() {
    }

    public ResponseRecuperaRiferimentoDocumentoFisico(
           it.csi.conam.conambl.integration.beans.Context cxt,
           long idRichiesta,
           java.lang.String uuid) {
           this.cxt = cxt;
           this.idRichiesta = idRichiesta;
           this.uuid = uuid;
    }


    /**
     * Gets the cxt value for this ResponseRecuperaRiferimentoDocumentoFisico.
     * 
     * @return cxt
     */
    public it.csi.conam.conambl.integration.beans.Context getCxt() {
        return cxt;
    }


    /**
     * Sets the cxt value for this ResponseRecuperaRiferimentoDocumentoFisico.
     * 
     * @param cxt
     */
    public void setCxt(it.csi.conam.conambl.integration.beans.Context cxt) {
        this.cxt = cxt;
    }


    /**
     * Gets the idRichiesta value for this ResponseRecuperaRiferimentoDocumentoFisico.
     * 
     * @return idRichiesta
     */
    public long getIdRichiesta() {
        return idRichiesta;
    }


    /**
     * Sets the idRichiesta value for this ResponseRecuperaRiferimentoDocumentoFisico.
     * 
     * @param idRichiesta
     */
    public void setIdRichiesta(long idRichiesta) {
        this.idRichiesta = idRichiesta;
    }


    /**
     * Gets the uuid value for this ResponseRecuperaRiferimentoDocumentoFisico.
     * 
     * @return uuid
     */
    public java.lang.String getUuid() {
        return uuid;
    }


    /**
     * Sets the uuid value for this ResponseRecuperaRiferimentoDocumentoFisico.
     * 
     * @param uuid
     */
    public void setUuid(java.lang.String uuid) {
        this.uuid = uuid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseRecuperaRiferimentoDocumentoFisico)) return false;
        ResponseRecuperaRiferimentoDocumentoFisico other = (ResponseRecuperaRiferimentoDocumentoFisico) obj;
        //if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cxt==null && other.getCxt()==null) || 
             (this.cxt!=null &&
              this.cxt.equals(other.getCxt()))) &&
            this.idRichiesta == other.getIdRichiesta() &&
            ((this.uuid==null && other.getUuid()==null) || 
             (this.uuid!=null &&
              this.uuid.equals(other.getUuid())));
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
        if (getCxt() != null) {
            _hashCode += getCxt().hashCode();
        }
        _hashCode += new Long(getIdRichiesta()).hashCode();
        if (getUuid() != null) {
            _hashCode += getUuid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseRecuperaRiferimentoDocumentoFisico.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseRecuperaRiferimentoDocumentoFisico"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cxt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cxt"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Context"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRichiesta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idRichiesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uuid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uuid"));
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
