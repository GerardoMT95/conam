/**
 * ResponseArchiviaDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class ResponseArchiviaDocumento  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3417521236199084820L;

	private java.lang.String idDocumento;
    
    // 20201120_LC
    private java.lang.String objectIdDocumento;

    // 20210804_PP
    private java.lang.String idFolder;
    
    public ResponseArchiviaDocumento() {
    }

    public ResponseArchiviaDocumento(
           java.lang.String idDocumento,
           java.lang.String objectIdDocumento) {
           this.idDocumento = idDocumento;
           this.objectIdDocumento = objectIdDocumento;
    }


    /**
     * Gets the idDocumento value for this ResponseArchiviaDocumento.
     * 
     * @return idDocumento
     */
    public java.lang.String getIdDocumento() {
        return idDocumento;
    }


    /**
     * Sets the idDocumento value for this ResponseArchiviaDocumento.
     * 
     * @param idDocumento
     */
    public void setIdDocumento(java.lang.String idDocumento) {
        this.idDocumento = idDocumento;
    }

    
    /**
     * Gets the objectIdDocumento value for this ResponseArchiviaDocumento.
     * 
     * @return objectIdDocumento
     */
    public java.lang.String getObjectIdDocumento() {
        return objectIdDocumento;
    }


    /**
     * Sets the objectIdDocumento value for this ResponseArchiviaDocumento.
     * 
     * @param objectIdDocumento
     */
    public void setObjectIdDocumento(java.lang.String objectIdDocumento) {
        this.objectIdDocumento = objectIdDocumento;
    }
    
    
    
    
    public java.lang.String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(java.lang.String idFolder) {
		this.idFolder = idFolder;
	}




	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseArchiviaDocumento)) return false;
        ResponseArchiviaDocumento other = (ResponseArchiviaDocumento) obj;
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
            ((this.objectIdDocumento==null && other.getObjectIdDocumento()==null) || 
             (this.objectIdDocumento!=null &&
              this.objectIdDocumento.equals(other.getObjectIdDocumento())));
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
        if (getObjectIdDocumento() != null) {
            _hashCode += getObjectIdDocumento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseArchiviaDocumento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseArchiviaDocumento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("objectIdDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "objectIdDocumento"));
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
