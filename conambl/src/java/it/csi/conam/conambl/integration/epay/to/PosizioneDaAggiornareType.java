/**
 * PosizioneDaAggiornareType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class PosizioneDaAggiornareType  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7803161379780192979L;

	private it.csi.conam.conambl.integration.epay.to.TipoAggiornamentoType tipoAggiornamento;

    private java.lang.String idPosizioneDebitoria;

    private java.lang.String motivazione;

    public PosizioneDaAggiornareType() {
    }

    public PosizioneDaAggiornareType(
           it.csi.conam.conambl.integration.epay.to.TipoAggiornamentoType tipoAggiornamento,
           java.lang.String idPosizioneDebitoria,
           java.lang.String motivazione) {
           this.tipoAggiornamento = tipoAggiornamento;
           this.idPosizioneDebitoria = idPosizioneDebitoria;
           this.motivazione = motivazione;
    }


    /**
     * Gets the tipoAggiornamento value for this PosizioneDaAggiornareType.
     * 
     * @return tipoAggiornamento
     */
    public it.csi.conam.conambl.integration.epay.to.TipoAggiornamentoType getTipoAggiornamento() {
        return tipoAggiornamento;
    }


    /**
     * Sets the tipoAggiornamento value for this PosizioneDaAggiornareType.
     * 
     * @param tipoAggiornamento
     */
    public void setTipoAggiornamento(it.csi.conam.conambl.integration.epay.to.TipoAggiornamentoType tipoAggiornamento) {
        this.tipoAggiornamento = tipoAggiornamento;
    }


    /**
     * Gets the idPosizioneDebitoria value for this PosizioneDaAggiornareType.
     * 
     * @return idPosizioneDebitoria
     */
    public java.lang.String getIdPosizioneDebitoria() {
        return idPosizioneDebitoria;
    }


    /**
     * Sets the idPosizioneDebitoria value for this PosizioneDaAggiornareType.
     * 
     * @param idPosizioneDebitoria
     */
    public void setIdPosizioneDebitoria(java.lang.String idPosizioneDebitoria) {
        this.idPosizioneDebitoria = idPosizioneDebitoria;
    }


    /**
     * Gets the motivazione value for this PosizioneDaAggiornareType.
     * 
     * @return motivazione
     */
    public java.lang.String getMotivazione() {
        return motivazione;
    }


    /**
     * Sets the motivazione value for this PosizioneDaAggiornareType.
     * 
     * @param motivazione
     */
    public void setMotivazione(java.lang.String motivazione) {
        this.motivazione = motivazione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof PosizioneDaAggiornareType)) return false;
        PosizioneDaAggiornareType other = (PosizioneDaAggiornareType) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.tipoAggiornamento==null && other.getTipoAggiornamento()==null) || 
             (this.tipoAggiornamento!=null &&
              this.tipoAggiornamento.equals(other.getTipoAggiornamento()))) &&
            ((this.idPosizioneDebitoria==null && other.getIdPosizioneDebitoria()==null) || 
             (this.idPosizioneDebitoria!=null &&
              this.idPosizioneDebitoria.equals(other.getIdPosizioneDebitoria()))) &&
            ((this.motivazione==null && other.getMotivazione()==null) || 
             (this.motivazione!=null &&
              this.motivazione.equals(other.getMotivazione())));
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
        if (getTipoAggiornamento() != null) {
            _hashCode += getTipoAggiornamento().hashCode();
        }
        if (getIdPosizioneDebitoria() != null) {
            _hashCode += getIdPosizioneDebitoria().hashCode();
        }
        if (getMotivazione() != null) {
            _hashCode += getMotivazione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PosizioneDaAggiornareType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "PosizioneDaAggiornareType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoAggiornamento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "TipoAggiornamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "TipoAggiornamentoType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPosizioneDebitoria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "IdPosizioneDebitoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("motivazione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "Motivazione"));
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
