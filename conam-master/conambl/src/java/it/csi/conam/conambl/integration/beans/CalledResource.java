/**
 * CalledResource.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class CalledResource  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7976114757175905L;

	private java.lang.String codRisorsa;

    private java.lang.String codSistema;

    private it.csi.conam.conambl.integration.beans.ResourceType tipoRisorsa;

    public CalledResource() {
    }

    public CalledResource(
           java.lang.String codRisorsa,
           java.lang.String codSistema,
           it.csi.conam.conambl.integration.beans.ResourceType tipoRisorsa) {
           this.codRisorsa = codRisorsa;
           this.codSistema = codSistema;
           this.tipoRisorsa = tipoRisorsa;
    }


    /**
     * Gets the codRisorsa value for this CalledResource.
     * 
     * @return codRisorsa
     */
    public java.lang.String getCodRisorsa() {
        return codRisorsa;
    }


    /**
     * Sets the codRisorsa value for this CalledResource.
     * 
     * @param codRisorsa
     */
    public void setCodRisorsa(java.lang.String codRisorsa) {
        this.codRisorsa = codRisorsa;
    }


    /**
     * Gets the codSistema value for this CalledResource.
     * 
     * @return codSistema
     */
    public java.lang.String getCodSistema() {
        return codSistema;
    }


    /**
     * Sets the codSistema value for this CalledResource.
     * 
     * @param codSistema
     */
    public void setCodSistema(java.lang.String codSistema) {
        this.codSistema = codSistema;
    }


    /**
     * Gets the tipoRisorsa value for this CalledResource.
     * 
     * @return tipoRisorsa
     */
    public it.csi.conam.conambl.integration.beans.ResourceType getTipoRisorsa() {
        return tipoRisorsa;
    }


    /**
     * Sets the tipoRisorsa value for this CalledResource.
     * 
     * @param tipoRisorsa
     */
    public void setTipoRisorsa(it.csi.conam.conambl.integration.beans.ResourceType tipoRisorsa) {
        this.tipoRisorsa = tipoRisorsa;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CalledResource)) return false;
        CalledResource other = (CalledResource) obj;
        //if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codRisorsa==null && other.getCodRisorsa()==null) || 
             (this.codRisorsa!=null &&
              this.codRisorsa.equals(other.getCodRisorsa()))) &&
            ((this.codSistema==null && other.getCodSistema()==null) || 
             (this.codSistema!=null &&
              this.codSistema.equals(other.getCodSistema()))) &&
            ((this.tipoRisorsa==null && other.getTipoRisorsa()==null) || 
             (this.tipoRisorsa!=null &&
              this.tipoRisorsa.equals(other.getTipoRisorsa())));
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
        if (getCodRisorsa() != null) {
            _hashCode += getCodRisorsa().hashCode();
        }
        if (getCodSistema() != null) {
            _hashCode += getCodSistema().hashCode();
        }
        if (getTipoRisorsa() != null) {
            _hashCode += getTipoRisorsa().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CalledResource.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "CalledResource"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codRisorsa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codRisorsa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codSistema");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codSistema"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRisorsa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRisorsa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "ResourceType"));
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
