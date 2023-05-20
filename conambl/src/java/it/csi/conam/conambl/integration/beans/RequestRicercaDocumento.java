/**
 * RequestRicercaDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class RequestRicercaDocumento  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3030303419763044508L;

	private java.lang.String codiceFruitore;

    private java.lang.String idDocumento;
    
    private java.lang.String rootActa;
    
    
    // 20200717_LC
    private String parolaChiave;
    private String objectIdDocumento;
    
    // 20200825_LC
    private String objectIdDocumentoFisico;
    
    

    public RequestRicercaDocumento() {
    }

    public RequestRicercaDocumento(
           java.lang.String codiceFruitore,
           java.lang.String idDocumento,
           java.lang.String rootActa,
           java.lang.String parolaChiave,
           java.lang.String objectIdDocumento,
           java.lang.String objectIdDocumentoFisico) {
           this.codiceFruitore = codiceFruitore;
           this.idDocumento = idDocumento;
           this.rootActa = rootActa;
           this.parolaChiave = parolaChiave;
           this.objectIdDocumento = objectIdDocumento;
           this.objectIdDocumentoFisico = objectIdDocumentoFisico;
    }


    /**
     * Gets the codiceFruitore value for this RequestRicercaDocumento.
     * 
     * @return codiceFruitore
     */
    public java.lang.String getCodiceFruitore() {
        return codiceFruitore;
    }


    /**
     * Sets the codiceFruitore value for this RequestRicercaDocumento.
     * 
     * @param codiceFruitore
     */
    public void setCodiceFruitore(java.lang.String codiceFruitore) {
        this.codiceFruitore = codiceFruitore;
    }


    /**
     * Gets the idDocumento value for this RequestRicercaDocumento.
     * 
     * @return idDocumento
     */
    public java.lang.String getIdDocumento() {
        return idDocumento;
    }


    /**
     * Sets the idDocumento value for this RequestRicercaDocumento.
     * 
     * @param idDocumento
     */
    public void setIdDocumento(java.lang.String idDocumento) {
        this.idDocumento = idDocumento;
    }

    
    

    /**
     * Gets the parolaChiave value for this RequestRicercaDocumento.
     * 
     * @return parolaChiave
     */
    public String getParolaChiave() {
        return parolaChiave;
    }


    /**
     * Sets the idDocumento value for this RequestRicercaDocumento.
     * 
     * @param parolaChiave
     */
    public void setParolaChiave(String parolaChiave) {
        this.parolaChiave = parolaChiave;
    }

    
    
    
    
    

    /**
     * Gets the objectIdDocumento value for this RequestRicercaDocumento.
     * 
     * @return objectIdDocumento
     */
    public String getObjectIdDocumento() {
        return objectIdDocumento;
    }


    /**
     * Sets the objectIdDocumento value for this RequestRicercaDocumento.
     * 
     * @param objectIdDocumento
     */
    public void setObjectIdDocumento(String objectIdDocumento) {
        this.objectIdDocumento = objectIdDocumento;
    }

    
    

    /**
     * Gets the objectIdDocumentoFisico value for this RequestRicercaDocumento.
     * 
     * @return objectIdDocumentoFisico
     */
    public String getObjectIdDocumentoFisico() {
        return objectIdDocumentoFisico;
    }


    /**
     * Sets the objectIdDocumentoFisico value for this RequestRicercaDocumento.
     * 
     * @param objectIdDocumentoFisico
     */
    public void setObjectIdDocumentoFisico(String objectIdDocumentoFisico) {
        this.objectIdDocumentoFisico = objectIdDocumentoFisico;
    }

    
       
    
    
    
    
    
    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RequestRicercaDocumento)) return false;
        RequestRicercaDocumento other = (RequestRicercaDocumento) obj;
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
            ((this.rootActa==null && other.getRootActa()==null) || 
                    (this.rootActa!=null &&
                     this.rootActa.equals(other.getRootActa()))) &&
            ((this.parolaChiave==null && other.getParolaChiave()==null) || 
                    (this.parolaChiave!=null &&
                     this.parolaChiave.equals(other.getParolaChiave()))) &&
            ((this.objectIdDocumento==null && other.getObjectIdDocumento()==null) || 
                    (this.objectIdDocumento!=null &&
                     this.objectIdDocumento.equals(other.getObjectIdDocumento()))) &&
            ((this.objectIdDocumentoFisico==null && other.getObjectIdDocumentoFisico()==null) || 
                    (this.objectIdDocumentoFisico!=null &&
                     this.objectIdDocumentoFisico.equals(other.getObjectIdDocumentoFisico())));
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
        if (getRootActa() != null) {
            _hashCode += getRootActa().hashCode();
        }
        if (getParolaChiave() != null) {
            _hashCode += getParolaChiave().hashCode();
        }
        if (getObjectIdDocumento() != null) {
            _hashCode += getObjectIdDocumento().hashCode();
        }
        if (getObjectIdDocumento() != null) {
            _hashCode += getObjectIdDocumentoFisico().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RequestRicercaDocumento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "RequestRicercaDocumento"));
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
        elemField.setFieldName("rootActa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rootActa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parolaChiave");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parolaChiave"));
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
        elemField.setFieldName("objectIdDocumentoFisico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "objectIdDocumentoFisico"));
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

	public java.lang.String getRootActa() {
		return rootActa;
	}

	public void setRootActa(java.lang.String rootActa) {
		this.rootActa = rootActa;
	}

    
    
}
