/**
 * PosizioneDebitoriaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class PosizioneDebitoriaType  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -423489186159321780L;

	private java.lang.String idPosizioneDebitoria;

    private java.lang.String IUV;

    private java.lang.String codiceAvviso;

    private java.lang.String codiceEsito;

    private java.lang.String descrizioneEsito;

    public PosizioneDebitoriaType() {
    }

    public PosizioneDebitoriaType(
           java.lang.String idPosizioneDebitoria,
           java.lang.String IUV,
           java.lang.String codiceAvviso,
           java.lang.String codiceEsito,
           java.lang.String descrizioneEsito) {
           this.idPosizioneDebitoria = idPosizioneDebitoria;
           this.IUV = IUV;
           this.codiceAvviso = codiceAvviso;
           this.codiceEsito = codiceEsito;
           this.descrizioneEsito = descrizioneEsito;
    }


    /**
     * Gets the idPosizioneDebitoria value for this PosizioneDebitoriaType.
     * 
     * @return idPosizioneDebitoria
     */
    public java.lang.String getIdPosizioneDebitoria() {
        return idPosizioneDebitoria;
    }


    /**
     * Sets the idPosizioneDebitoria value for this PosizioneDebitoriaType.
     * 
     * @param idPosizioneDebitoria
     */
    public void setIdPosizioneDebitoria(java.lang.String idPosizioneDebitoria) {
        this.idPosizioneDebitoria = idPosizioneDebitoria;
    }


    /**
     * Gets the IUV value for this PosizioneDebitoriaType.
     * 
     * @return IUV
     */
    public java.lang.String getIUV() {
        return IUV;
    }


    /**
     * Sets the IUV value for this PosizioneDebitoriaType.
     * 
     * @param IUV
     */
    public void setIUV(java.lang.String IUV) {
        this.IUV = IUV;
    }


    /**
     * Gets the codiceAvviso value for this PosizioneDebitoriaType.
     * 
     * @return codiceAvviso
     */
    public java.lang.String getCodiceAvviso() {
        return codiceAvviso;
    }


    /**
     * Sets the codiceAvviso value for this PosizioneDebitoriaType.
     * 
     * @param codiceAvviso
     */
    public void setCodiceAvviso(java.lang.String codiceAvviso) {
        this.codiceAvviso = codiceAvviso;
    }


    /**
     * Gets the codiceEsito value for this PosizioneDebitoriaType.
     * 
     * @return codiceEsito
     */
    public java.lang.String getCodiceEsito() {
        return codiceEsito;
    }


    /**
     * Sets the codiceEsito value for this PosizioneDebitoriaType.
     * 
     * @param codiceEsito
     */
    public void setCodiceEsito(java.lang.String codiceEsito) {
        this.codiceEsito = codiceEsito;
    }


    /**
     * Gets the descrizioneEsito value for this PosizioneDebitoriaType.
     * 
     * @return descrizioneEsito
     */
    public java.lang.String getDescrizioneEsito() {
        return descrizioneEsito;
    }


    /**
     * Sets the descrizioneEsito value for this PosizioneDebitoriaType.
     * 
     * @param descrizioneEsito
     */
    public void setDescrizioneEsito(java.lang.String descrizioneEsito) {
        this.descrizioneEsito = descrizioneEsito;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof PosizioneDebitoriaType)) return false;
        PosizioneDebitoriaType other = (PosizioneDebitoriaType) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idPosizioneDebitoria==null && other.getIdPosizioneDebitoria()==null) || 
             (this.idPosizioneDebitoria!=null &&
              this.idPosizioneDebitoria.equals(other.getIdPosizioneDebitoria()))) &&
            ((this.IUV==null && other.getIUV()==null) || 
             (this.IUV!=null &&
              this.IUV.equals(other.getIUV()))) &&
            ((this.codiceAvviso==null && other.getCodiceAvviso()==null) || 
             (this.codiceAvviso!=null &&
              this.codiceAvviso.equals(other.getCodiceAvviso()))) &&
            ((this.codiceEsito==null && other.getCodiceEsito()==null) || 
             (this.codiceEsito!=null &&
              this.codiceEsito.equals(other.getCodiceEsito()))) &&
            ((this.descrizioneEsito==null && other.getDescrizioneEsito()==null) || 
             (this.descrizioneEsito!=null &&
              this.descrizioneEsito.equals(other.getDescrizioneEsito())));
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
        if (getIdPosizioneDebitoria() != null) {
            _hashCode += getIdPosizioneDebitoria().hashCode();
        }
        if (getIUV() != null) {
            _hashCode += getIUV().hashCode();
        }
        if (getCodiceAvviso() != null) {
            _hashCode += getCodiceAvviso().hashCode();
        }
        if (getCodiceEsito() != null) {
            _hashCode += getCodiceEsito().hashCode();
        }
        if (getDescrizioneEsito() != null) {
            _hashCode += getDescrizioneEsito().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PosizioneDebitoriaType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "PosizioneDebitoriaType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPosizioneDebitoria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "IdPosizioneDebitoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IUV");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "IUV"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceAvviso");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "CodiceAvviso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceEsito");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "CodiceEsito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneEsito");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "DescrizioneEsito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
