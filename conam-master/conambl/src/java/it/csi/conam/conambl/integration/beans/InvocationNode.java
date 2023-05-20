/**
 * InvocationNode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class InvocationNode  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3398032510012424580L;

	private it.csi.conam.conambl.integration.beans.InvocationNode[] childs;

    private it.csi.conam.conambl.integration.beans.Outcome outcome;

    private it.csi.conam.conambl.integration.beans.CalledResource resource;

    private long startTime;

    private long stopTime;

    public InvocationNode() {
    }

    public InvocationNode(
           it.csi.conam.conambl.integration.beans.InvocationNode[] childs,
           it.csi.conam.conambl.integration.beans.Outcome outcome,
           it.csi.conam.conambl.integration.beans.CalledResource resource,
           long startTime,
           long stopTime) {
           this.childs = childs;
           this.outcome = outcome;
           this.resource = resource;
           this.startTime = startTime;
           this.stopTime = stopTime;
    }


    /**
     * Gets the childs value for this InvocationNode.
     * 
     * @return childs
     */
    public it.csi.conam.conambl.integration.beans.InvocationNode[] getChilds() {
        return childs;
    }


    /**
     * Sets the childs value for this InvocationNode.
     * 
     * @param childs
     */
    public void setChilds(it.csi.conam.conambl.integration.beans.InvocationNode[] childs) {
        this.childs = childs;
    }


    /**
     * Gets the outcome value for this InvocationNode.
     * 
     * @return outcome
     */
    public it.csi.conam.conambl.integration.beans.Outcome getOutcome() {
        return outcome;
    }


    /**
     * Sets the outcome value for this InvocationNode.
     * 
     * @param outcome
     */
    public void setOutcome(it.csi.conam.conambl.integration.beans.Outcome outcome) {
        this.outcome = outcome;
    }


    /**
     * Gets the resource value for this InvocationNode.
     * 
     * @return resource
     */
    public it.csi.conam.conambl.integration.beans.CalledResource getResource() {
        return resource;
    }


    /**
     * Sets the resource value for this InvocationNode.
     * 
     * @param resource
     */
    public void setResource(it.csi.conam.conambl.integration.beans.CalledResource resource) {
        this.resource = resource;
    }


    /**
     * Gets the startTime value for this InvocationNode.
     * 
     * @return startTime
     */
    public long getStartTime() {
        return startTime;
    }


    /**
     * Sets the startTime value for this InvocationNode.
     * 
     * @param startTime
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }


    /**
     * Gets the stopTime value for this InvocationNode.
     * 
     * @return stopTime
     */
    public long getStopTime() {
        return stopTime;
    }


    /**
     * Sets the stopTime value for this InvocationNode.
     * 
     * @param stopTime
     */
    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InvocationNode)) return false;
        InvocationNode other = (InvocationNode) obj;
        //if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.childs==null && other.getChilds()==null) || 
             (this.childs!=null &&
              java.util.Arrays.equals(this.childs, other.getChilds()))) &&
            ((this.outcome==null && other.getOutcome()==null) || 
             (this.outcome!=null &&
              this.outcome.equals(other.getOutcome()))) &&
            ((this.resource==null && other.getResource()==null) || 
             (this.resource!=null &&
              this.resource.equals(other.getResource()))) &&
            this.startTime == other.getStartTime() &&
            this.stopTime == other.getStopTime();
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
        if (getChilds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getChilds());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getChilds(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getOutcome() != null) {
            _hashCode += getOutcome().hashCode();
        }
        if (getResource() != null) {
            _hashCode += getResource().hashCode();
        }
        _hashCode += new Long(getStartTime()).hashCode();
        _hashCode += new Long(getStopTime()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InvocationNode.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "InvocationNode"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("childs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "childs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "InvocationNode"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outcome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "outcome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "Outcome"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resource");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resource"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.coopdiag.csi.it", "CalledResource"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stopTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stopTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
