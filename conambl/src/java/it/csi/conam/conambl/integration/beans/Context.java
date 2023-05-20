/**
 * Context.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class Context  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5975691884811336487L;

	private java.lang.String fruitore;

    private java.lang.String nomeFisico;

    private java.lang.String password;

    private java.lang.String prefixedName;

    private java.lang.String repository;

    private java.lang.String username;

    public Context() {
    }

    public Context(
           java.lang.String fruitore,
           java.lang.String nomeFisico,
           java.lang.String password,
           java.lang.String prefixedName,
           java.lang.String repository,
           java.lang.String username) {
           this.fruitore = fruitore;
           this.nomeFisico = nomeFisico;
           this.password = password;
           this.prefixedName = prefixedName;
           this.repository = repository;
           this.username = username;
    }


    /**
     * Gets the fruitore value for this Context.
     * 
     * @return fruitore
     */
    public java.lang.String getFruitore() {
        return fruitore;
    }


    /**
     * Sets the fruitore value for this Context.
     * 
     * @param fruitore
     */
    public void setFruitore(java.lang.String fruitore) {
        this.fruitore = fruitore;
    }


    /**
     * Gets the nomeFisico value for this Context.
     * 
     * @return nomeFisico
     */
    public java.lang.String getNomeFisico() {
        return nomeFisico;
    }


    /**
     * Sets the nomeFisico value for this Context.
     * 
     * @param nomeFisico
     */
    public void setNomeFisico(java.lang.String nomeFisico) {
        this.nomeFisico = nomeFisico;
    }


    /**
     * Gets the password value for this Context.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this Context.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the prefixedName value for this Context.
     * 
     * @return prefixedName
     */
    public java.lang.String getPrefixedName() {
        return prefixedName;
    }


    /**
     * Sets the prefixedName value for this Context.
     * 
     * @param prefixedName
     */
    public void setPrefixedName(java.lang.String prefixedName) {
        this.prefixedName = prefixedName;
    }


    /**
     * Gets the repository value for this Context.
     * 
     * @return repository
     */
    public java.lang.String getRepository() {
        return repository;
    }


    /**
     * Sets the repository value for this Context.
     * 
     * @param repository
     */
    public void setRepository(java.lang.String repository) {
        this.repository = repository;
    }


    /**
     * Gets the username value for this Context.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this Context.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Context)) return false;
        Context other = (Context) obj;
        //if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fruitore==null && other.getFruitore()==null) || 
             (this.fruitore!=null &&
              this.fruitore.equals(other.getFruitore()))) &&
            ((this.nomeFisico==null && other.getNomeFisico()==null) || 
             (this.nomeFisico!=null &&
              this.nomeFisico.equals(other.getNomeFisico()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.prefixedName==null && other.getPrefixedName()==null) || 
             (this.prefixedName!=null &&
              this.prefixedName.equals(other.getPrefixedName()))) &&
            ((this.repository==null && other.getRepository()==null) || 
             (this.repository!=null &&
              this.repository.equals(other.getRepository()))) &&
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername())));
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
        if (getFruitore() != null) {
            _hashCode += getFruitore().hashCode();
        }
        if (getNomeFisico() != null) {
            _hashCode += getNomeFisico().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getPrefixedName() != null) {
            _hashCode += getPrefixedName().hashCode();
        }
        if (getRepository() != null) {
            _hashCode += getRepository().hashCode();
        }
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Context.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Context"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fruitore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fruitore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeFisico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeFisico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prefixedName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prefixedName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("repository");
        elemField.setXmlName(new javax.xml.namespace.QName("", "repository"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("", "username"));
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

}
