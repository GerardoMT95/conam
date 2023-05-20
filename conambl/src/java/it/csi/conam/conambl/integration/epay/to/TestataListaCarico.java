/**
 * TestataListaCarico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class TestataListaCarico  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2924464854071939145L;

	private java.lang.String idMessaggio;

    private java.lang.String CFEnteCreditore;

    private java.lang.String codiceVersamento;

    private java.math.BigInteger numeroPosizioniDebitorie;

    private java.math.BigDecimal importoTotaleListaDiCarico;

    public TestataListaCarico() {
    }

    public TestataListaCarico(
           java.lang.String idMessaggio,
           java.lang.String CFEnteCreditore,
           java.lang.String codiceVersamento,
           java.math.BigInteger numeroPosizioniDebitorie,
           java.math.BigDecimal importoTotaleListaDiCarico) {
           this.idMessaggio = idMessaggio;
           this.CFEnteCreditore = CFEnteCreditore;
           this.codiceVersamento = codiceVersamento;
           this.numeroPosizioniDebitorie = numeroPosizioniDebitorie;
           this.importoTotaleListaDiCarico = importoTotaleListaDiCarico;
    }


    /**
     * Gets the idMessaggio value for this TestataListaCarico.
     * 
     * @return idMessaggio
     */
    public java.lang.String getIdMessaggio() {
        return idMessaggio;
    }


    /**
     * Sets the idMessaggio value for this TestataListaCarico.
     * 
     * @param idMessaggio
     */
    public void setIdMessaggio(java.lang.String idMessaggio) {
        this.idMessaggio = idMessaggio;
    }


    /**
     * Gets the CFEnteCreditore value for this TestataListaCarico.
     * 
     * @return CFEnteCreditore
     */
    public java.lang.String getCFEnteCreditore() {
        return CFEnteCreditore;
    }


    /**
     * Sets the CFEnteCreditore value for this TestataListaCarico.
     * 
     * @param CFEnteCreditore
     */
    public void setCFEnteCreditore(java.lang.String CFEnteCreditore) {
        this.CFEnteCreditore = CFEnteCreditore;
    }


    /**
     * Gets the codiceVersamento value for this TestataListaCarico.
     * 
     * @return codiceVersamento
     */
    public java.lang.String getCodiceVersamento() {
        return codiceVersamento;
    }


    /**
     * Sets the codiceVersamento value for this TestataListaCarico.
     * 
     * @param codiceVersamento
     */
    public void setCodiceVersamento(java.lang.String codiceVersamento) {
        this.codiceVersamento = codiceVersamento;
    }


    /**
     * Gets the numeroPosizioniDebitorie value for this TestataListaCarico.
     * 
     * @return numeroPosizioniDebitorie
     */
    public java.math.BigInteger getNumeroPosizioniDebitorie() {
        return numeroPosizioniDebitorie;
    }


    /**
     * Sets the numeroPosizioniDebitorie value for this TestataListaCarico.
     * 
     * @param numeroPosizioniDebitorie
     */
    public void setNumeroPosizioniDebitorie(java.math.BigInteger numeroPosizioniDebitorie) {
        this.numeroPosizioniDebitorie = numeroPosizioniDebitorie;
    }


    /**
     * Gets the importoTotaleListaDiCarico value for this TestataListaCarico.
     * 
     * @return importoTotaleListaDiCarico
     */
    public java.math.BigDecimal getImportoTotaleListaDiCarico() {
        return importoTotaleListaDiCarico;
    }


    /**
     * Sets the importoTotaleListaDiCarico value for this TestataListaCarico.
     * 
     * @param importoTotaleListaDiCarico
     */
    public void setImportoTotaleListaDiCarico(java.math.BigDecimal importoTotaleListaDiCarico) {
        this.importoTotaleListaDiCarico = importoTotaleListaDiCarico;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof TestataListaCarico)) return false;
        TestataListaCarico other = (TestataListaCarico) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idMessaggio==null && other.getIdMessaggio()==null) || 
             (this.idMessaggio!=null &&
              this.idMessaggio.equals(other.getIdMessaggio()))) &&
            ((this.CFEnteCreditore==null && other.getCFEnteCreditore()==null) || 
             (this.CFEnteCreditore!=null &&
              this.CFEnteCreditore.equals(other.getCFEnteCreditore()))) &&
            ((this.codiceVersamento==null && other.getCodiceVersamento()==null) || 
             (this.codiceVersamento!=null &&
              this.codiceVersamento.equals(other.getCodiceVersamento()))) &&
            ((this.numeroPosizioniDebitorie==null && other.getNumeroPosizioniDebitorie()==null) || 
             (this.numeroPosizioniDebitorie!=null &&
              this.numeroPosizioniDebitorie.equals(other.getNumeroPosizioniDebitorie()))) &&
            ((this.importoTotaleListaDiCarico==null && other.getImportoTotaleListaDiCarico()==null) || 
             (this.importoTotaleListaDiCarico!=null &&
              this.importoTotaleListaDiCarico.equals(other.getImportoTotaleListaDiCarico())));
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
        if (getIdMessaggio() != null) {
            _hashCode += getIdMessaggio().hashCode();
        }
        if (getCFEnteCreditore() != null) {
            _hashCode += getCFEnteCreditore().hashCode();
        }
        if (getCodiceVersamento() != null) {
            _hashCode += getCodiceVersamento().hashCode();
        }
        if (getNumeroPosizioniDebitorie() != null) {
            _hashCode += getNumeroPosizioniDebitorie().hashCode();
        }
        if (getImportoTotaleListaDiCarico() != null) {
            _hashCode += getImportoTotaleListaDiCarico().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TestataListaCarico.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "TestataListaCarico"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMessaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "IdMessaggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CFEnteCreditore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "CFEnteCreditore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceVersamento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "CodiceVersamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroPosizioniDebitorie");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "NumeroPosizioniDebitorie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importoTotaleListaDiCarico");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ImportoTotaleListaDiCarico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
