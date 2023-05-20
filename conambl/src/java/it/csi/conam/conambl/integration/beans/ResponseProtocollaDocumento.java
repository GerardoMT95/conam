/**
 * ResponseProtocollaDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

import java.util.List;

public class ResponseProtocollaDocumento  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7115580596715872914L;

	private java.lang.String idDocumento;

    private java.lang.String indiceClassificazione;

    private java.lang.String protocollo;

    // 20201120_LC
    private java.lang.String objectIdDocumento;
    private java.lang.String idFolder;
    
    // 20201124_LC
    private List<String> objectIdDocumentoToTraceList;
    
    public ResponseProtocollaDocumento() {
    }

    public ResponseProtocollaDocumento(
           java.lang.String idDocumento,
           java.lang.String indiceClassificazione,
           java.lang.String protocollo,
           java.lang.String objectIdDocumento,
           java.lang.String idFolder,
           List<String> objectIdDocumentoToTraceList) {
           this.idDocumento = idDocumento;
           this.indiceClassificazione = indiceClassificazione;
           this.protocollo = protocollo;
           this.objectIdDocumento = objectIdDocumento;
           this.idFolder = idFolder;
           this.objectIdDocumentoToTraceList = objectIdDocumentoToTraceList;
    }


    /**
     * Gets the idDocumento value for this ResponseProtocollaDocumento.
     * 
     * @return idDocumento
     */
    public java.lang.String getIdDocumento() {
        return idDocumento;
    }


    /**
     * Sets the idDocumento value for this ResponseProtocollaDocumento.
     * 
     * @param idDocumento
     */
    public void setIdDocumento(java.lang.String idDocumento) {
        this.idDocumento = idDocumento;
    }


    /**
     * Gets the indiceClassificazione value for this ResponseProtocollaDocumento.
     * 
     * @return indiceClassificazione
     */
    public java.lang.String getIndiceClassificazione() {
        return indiceClassificazione;
    }


    /**
     * Sets the indiceClassificazione value for this ResponseProtocollaDocumento.
     * 
     * @param indiceClassificazione
     */
    public void setIndiceClassificazione(java.lang.String indiceClassificazione) {
        this.indiceClassificazione = indiceClassificazione;
    }


    /**
     * Gets the protocollo value for this ResponseProtocollaDocumento.
     * 
     * @return protocollo
     */
    public java.lang.String getProtocollo() {
        return protocollo;
    }


    /**
     * Sets the protocollo value for this ResponseProtocollaDocumento.
     * 
     * @param protocollo
     */
    public void setProtocollo(java.lang.String protocollo) {
        this.protocollo = protocollo;
    }

    
    
    
    /**
     * Gets the idDocumento value for this ResponseProtocollaDocumento.
     * 
     * @return idDocumento
     */
    public java.lang.String getObjectIdDocumento() {
        return objectIdDocumento;
    }


    /**
     * Sets the idDocumento value for this ResponseProtocollaDocumento.
     * 
     * @param idDocumento
     */
    public void setObjectIdDocumento(java.lang.String objectIdDocumento) {
        this.objectIdDocumento = objectIdDocumento;
    }
    
    
    
    
    /**
     * Gets the idDocumento value for this ResponseProtocollaDocumento.
     * 
     * @return idDocumento
     */
    public java.lang.String getIdFolder() {
        return idFolder;
    }


    /**
     * Sets the idDocumento value for this ResponseProtocollaDocumento.
     * 
     * @param idDocumento
     */
    public void setIdFolder(java.lang.String idFolder) {
        this.idFolder = idFolder;
    }
    
    
    
    
    
    

    
    

    public List<String> getObjectIdDocumentoToTraceList() {
		return objectIdDocumentoToTraceList;
	}

	public void setObjectIdDocumentoToTraceList(List<String> objectIdDocumentoToTraceList) {
		this.objectIdDocumentoToTraceList = objectIdDocumentoToTraceList;
	}

	
	
	
	
    
    
    
    
    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseProtocollaDocumento)) return false;
        ResponseProtocollaDocumento other = (ResponseProtocollaDocumento) obj;
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
              this.protocollo.equals(other.getProtocollo()))) &&
            ((this.objectIdDocumento==null && other.getObjectIdDocumento()==null) || 
             (this.objectIdDocumento!=null &&
              this.objectIdDocumento.equals(other.getObjectIdDocumento()))) &&
            ((this.idFolder==null && other.getIdFolder()==null) || 
             (this.idFolder!=null &&
              this.idFolder.equals(other.getIdFolder())));
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
        if (getObjectIdDocumento() != null) {
            _hashCode += getObjectIdDocumento().hashCode();
        }
        if (getIdFolder() != null) {
            _hashCode += getIdFolder().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseProtocollaDocumento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseProtocollaDocumento"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("objectIdDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "objectIdDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFolder");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idFolder"));
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
