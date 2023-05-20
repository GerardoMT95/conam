/**
 * AggiornaPosizioniDebitorieResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class AggiornaPosizioniDebitorieResponse  extends it.csi.conam.conambl.integration.epay.to.ResponseType  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7161921048970140004L;
	
	private it.csi.conam.conambl.integration.epay.to.EsitoAggiornamentoType esitoAggiornamento;

    public AggiornaPosizioniDebitorieResponse() {
    }

    public AggiornaPosizioniDebitorieResponse(
           it.csi.conam.conambl.integration.epay.to.ResultType result,
           it.csi.conam.conambl.integration.epay.to.EsitoAggiornamentoType esitoAggiornamento) {
        super(
            result);
        this.esitoAggiornamento = esitoAggiornamento;
    }


    /**
     * Gets the esitoAggiornamento value for this AggiornaPosizioniDebitorieResponse.
     * 
     * @return esitoAggiornamento
     */
    public it.csi.conam.conambl.integration.epay.to.EsitoAggiornamentoType getEsitoAggiornamento() {
        return esitoAggiornamento;
    }


    /**
     * Sets the esitoAggiornamento value for this AggiornaPosizioniDebitorieResponse.
     * 
     * @param esitoAggiornamento
     */
    public void setEsitoAggiornamento(it.csi.conam.conambl.integration.epay.to.EsitoAggiornamentoType esitoAggiornamento) {
        this.esitoAggiornamento = esitoAggiornamento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof AggiornaPosizioniDebitorieResponse)) return false;
        AggiornaPosizioniDebitorieResponse other = (AggiornaPosizioniDebitorieResponse) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.esitoAggiornamento==null && other.getEsitoAggiornamento()==null) || 
             (this.esitoAggiornamento!=null &&
              this.esitoAggiornamento.equals(other.getEsitoAggiornamento())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getEsitoAggiornamento() != null) {
            _hashCode += getEsitoAggiornamento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AggiornaPosizioniDebitorieResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", ">AggiornaPosizioniDebitorieResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esitoAggiornamento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "EsitoAggiornamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "EsitoAggiornamentoType"));
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
