/**
 * ComponenteImportoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class ComponenteImportoType  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5783981909215379489L;

	private java.math.BigDecimal importo;

    private java.lang.String causaleDescrittiva;

    private java.lang.String datiSpecificiRiscossione;

    public ComponenteImportoType() {
    }

    public ComponenteImportoType(
           java.math.BigDecimal importo,
           java.lang.String causaleDescrittiva,
           java.lang.String datiSpecificiRiscossione) {
           this.importo = importo;
           this.causaleDescrittiva = causaleDescrittiva;
           this.datiSpecificiRiscossione = datiSpecificiRiscossione;
    }


    /**
     * Gets the importo value for this ComponenteImportoType.
     * 
     * @return importo
     */
    public java.math.BigDecimal getImporto() {
        return importo;
    }


    /**
     * Sets the importo value for this ComponenteImportoType.
     * 
     * @param importo
     */
    public void setImporto(java.math.BigDecimal importo) {
        this.importo = importo;
    }


    /**
     * Gets the causaleDescrittiva value for this ComponenteImportoType.
     * 
     * @return causaleDescrittiva
     */
    public java.lang.String getCausaleDescrittiva() {
        return causaleDescrittiva;
    }


    /**
     * Sets the causaleDescrittiva value for this ComponenteImportoType.
     * 
     * @param causaleDescrittiva
     */
    public void setCausaleDescrittiva(java.lang.String causaleDescrittiva) {
        this.causaleDescrittiva = causaleDescrittiva;
    }


    /**
     * Gets the datiSpecificiRiscossione value for this ComponenteImportoType.
     * 
     * @return datiSpecificiRiscossione
     */
    public java.lang.String getDatiSpecificiRiscossione() {
        return datiSpecificiRiscossione;
    }


    /**
     * Sets the datiSpecificiRiscossione value for this ComponenteImportoType.
     * 
     * @param datiSpecificiRiscossione
     */
    public void setDatiSpecificiRiscossione(java.lang.String datiSpecificiRiscossione) {
        this.datiSpecificiRiscossione = datiSpecificiRiscossione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ComponenteImportoType)) return false;
        ComponenteImportoType other = (ComponenteImportoType) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.importo==null && other.getImporto()==null) || 
             (this.importo!=null &&
              this.importo.equals(other.getImporto()))) &&
            ((this.causaleDescrittiva==null && other.getCausaleDescrittiva()==null) || 
             (this.causaleDescrittiva!=null &&
              this.causaleDescrittiva.equals(other.getCausaleDescrittiva()))) &&
            ((this.datiSpecificiRiscossione==null && other.getDatiSpecificiRiscossione()==null) || 
             (this.datiSpecificiRiscossione!=null &&
              this.datiSpecificiRiscossione.equals(other.getDatiSpecificiRiscossione())));
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
        if (getImporto() != null) {
            _hashCode += getImporto().hashCode();
        }
        if (getCausaleDescrittiva() != null) {
            _hashCode += getCausaleDescrittiva().hashCode();
        }
        if (getDatiSpecificiRiscossione() != null) {
            _hashCode += getDatiSpecificiRiscossione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ComponenteImportoType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ComponenteImportoType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "Importo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("causaleDescrittiva");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "CausaleDescrittiva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datiSpecificiRiscossione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "DatiSpecificiRiscossione"));
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
