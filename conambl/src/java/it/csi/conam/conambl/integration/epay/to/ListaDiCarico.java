/**
 * ListaDiCarico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class ListaDiCarico  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -304565615002485355L;
	private it.csi.conam.conambl.integration.epay.to.PosizioneDaInserireType[] posizioniDaInserire;

    public ListaDiCarico() {
    }

    public ListaDiCarico(
           it.csi.conam.conambl.integration.epay.to.PosizioneDaInserireType[] posizioniDaInserire) {
           this.posizioniDaInserire = posizioniDaInserire;
    }


    /**
     * Gets the posizioniDaInserire value for this ListaDiCarico.
     * 
     * @return posizioniDaInserire
     */
    public it.csi.conam.conambl.integration.epay.to.PosizioneDaInserireType[] getPosizioniDaInserire() {
        return posizioniDaInserire;
    }


    /**
     * Sets the posizioniDaInserire value for this ListaDiCarico.
     * 
     * @param posizioniDaInserire
     */
    public void setPosizioniDaInserire(it.csi.conam.conambl.integration.epay.to.PosizioneDaInserireType[] posizioniDaInserire) {
        this.posizioniDaInserire = posizioniDaInserire;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ListaDiCarico)) return false;
        ListaDiCarico other = (ListaDiCarico) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.posizioniDaInserire==null && other.getPosizioniDaInserire()==null) || 
             (this.posizioniDaInserire!=null &&
              java.util.Arrays.equals(this.posizioniDaInserire, other.getPosizioniDaInserire())));
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
        if (getPosizioniDaInserire() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPosizioniDaInserire());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPosizioniDaInserire(), i);
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
        new org.apache.axis.description.TypeDesc(ListaDiCarico.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ListaDiCarico"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("posizioniDaInserire");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "PosizioniDaInserire"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "PosizioneDaInserireType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "PosizioneDaInserire"));
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
