/**
 * EsitoAggiornamentoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class EsitoAggiornamentoType  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5584499601001443893L;
	private it.csi.conam.conambl.integration.epay.to.PosizioneDebitoriaType[] elencoPosizioniDebitorieAggiornate;

    public EsitoAggiornamentoType() {
    }

    public EsitoAggiornamentoType(
           it.csi.conam.conambl.integration.epay.to.PosizioneDebitoriaType[] elencoPosizioniDebitorieAggiornate) {
           this.elencoPosizioniDebitorieAggiornate = elencoPosizioniDebitorieAggiornate;
    }


    /**
     * Gets the elencoPosizioniDebitorieAggiornate value for this EsitoAggiornamentoType.
     * 
     * @return elencoPosizioniDebitorieAggiornate
     */
    public it.csi.conam.conambl.integration.epay.to.PosizioneDebitoriaType[] getElencoPosizioniDebitorieAggiornate() {
        return elencoPosizioniDebitorieAggiornate;
    }


    /**
     * Sets the elencoPosizioniDebitorieAggiornate value for this EsitoAggiornamentoType.
     * 
     * @param elencoPosizioniDebitorieAggiornate
     */
    public void setElencoPosizioniDebitorieAggiornate(it.csi.conam.conambl.integration.epay.to.PosizioneDebitoriaType[] elencoPosizioniDebitorieAggiornate) {
        this.elencoPosizioniDebitorieAggiornate = elencoPosizioniDebitorieAggiornate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof EsitoAggiornamentoType)) return false;
        EsitoAggiornamentoType other = (EsitoAggiornamentoType) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.elencoPosizioniDebitorieAggiornate==null && other.getElencoPosizioniDebitorieAggiornate()==null) || 
             (this.elencoPosizioniDebitorieAggiornate!=null &&
              java.util.Arrays.equals(this.elencoPosizioniDebitorieAggiornate, other.getElencoPosizioniDebitorieAggiornate())));
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
        if (getElencoPosizioniDebitorieAggiornate() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getElencoPosizioniDebitorieAggiornate());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getElencoPosizioniDebitorieAggiornate(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EsitoAggiornamentoType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "EsitoAggiornamentoType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elencoPosizioniDebitorieAggiornate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "ElencoPosizioniDebitorieAggiornate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "PosizioneDebitoriaType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "PosizioneDebitoriaAggiornata"));
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
