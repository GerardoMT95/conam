/**
 * InserisciListaDiCaricoRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class InserisciListaDiCaricoRequest  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private it.csi.conam.conambl.integration.epay.to.TestataListaCarico testata;

    private it.csi.conam.conambl.integration.epay.to.ListaDiCarico listaDiCarico;

    public InserisciListaDiCaricoRequest() {
    }

    public InserisciListaDiCaricoRequest(
           it.csi.conam.conambl.integration.epay.to.TestataListaCarico testata,
           it.csi.conam.conambl.integration.epay.to.ListaDiCarico listaDiCarico) {
           this.testata = testata;
           this.listaDiCarico = listaDiCarico;
    }


    /**
     * Gets the testata value for this InserisciListaDiCaricoRequest.
     * 
     * @return testata
     */
    public it.csi.conam.conambl.integration.epay.to.TestataListaCarico getTestata() {
        return testata;
    }


    /**
     * Sets the testata value for this InserisciListaDiCaricoRequest.
     * 
     * @param testata
     */
    public void setTestata(it.csi.conam.conambl.integration.epay.to.TestataListaCarico testata) {
        this.testata = testata;
    }


    /**
     * Gets the listaDiCarico value for this InserisciListaDiCaricoRequest.
     * 
     * @return listaDiCarico
     */
    public it.csi.conam.conambl.integration.epay.to.ListaDiCarico getListaDiCarico() {
        return listaDiCarico;
    }


    /**
     * Sets the listaDiCarico value for this InserisciListaDiCaricoRequest.
     * 
     * @param listaDiCarico
     */
    public void setListaDiCarico(it.csi.conam.conambl.integration.epay.to.ListaDiCarico listaDiCarico) {
        this.listaDiCarico = listaDiCarico;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InserisciListaDiCaricoRequest)) return false;
        InserisciListaDiCaricoRequest other = (InserisciListaDiCaricoRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.testata==null && other.getTestata()==null) || 
             (this.testata!=null &&
              this.testata.equals(other.getTestata()))) &&
            ((this.listaDiCarico==null && other.getListaDiCarico()==null) || 
             (this.listaDiCarico!=null &&
              this.listaDiCarico.equals(other.getListaDiCarico())));
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
        if (getTestata() != null) {
            _hashCode += getTestata().hashCode();
        }
        if (getListaDiCarico() != null) {
            _hashCode += getListaDiCarico().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InserisciListaDiCaricoRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", ">InserisciListaDiCaricoRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("testata");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "Testata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "TestataListaCarico"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaDiCarico");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ListaDiCarico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ListaDiCarico"));
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
