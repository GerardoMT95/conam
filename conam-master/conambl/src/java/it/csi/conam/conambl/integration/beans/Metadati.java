/**
 * Metadati.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class Metadati implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1679102125496117172L;

	private java.lang.String codiceFiscale;

	private java.lang.String idEntitaFruitore;

	private java.lang.String targa;

	public Metadati() {
	}

	public Metadati(java.lang.String codiceFiscale, java.lang.String idEntitaFruitore, java.lang.String targa) {
		this.codiceFiscale = codiceFiscale;
		this.idEntitaFruitore = idEntitaFruitore;
		this.targa = targa;
	}

	/**
	 * Gets the codiceFiscale value for this Metadati.
	 * 
	 * @return codiceFiscale
	 */
	public java.lang.String getCodiceFiscale() {
		return codiceFiscale;
	}

	/**
	 * Sets the codiceFiscale value for this Metadati.
	 * 
	 * @param codiceFiscale
	 */
	public void setCodiceFiscale(java.lang.String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	/**
	 * Gets the idEntitaFruitore value for this Metadati.
	 * 
	 * @return idEntitaFruitore
	 */
	public java.lang.String getIdEntitaFruitore() {
		return idEntitaFruitore;
	}

	/**
	 * Sets the idEntitaFruitore value for this Metadati.
	 * 
	 * @param idEntitaFruitore
	 */
	public void setIdEntitaFruitore(java.lang.String idEntitaFruitore) {
		this.idEntitaFruitore = idEntitaFruitore;
	}

	/**
	 * Gets the targa value for this Metadati.
	 * 
	 * @return targa
	 */
	public java.lang.String getTarga() {
		return targa;
	}

	/**
	 * Sets the targa value for this Metadati.
	 * 
	 * @param targa
	 */
	public void setTarga(java.lang.String targa) {
		this.targa = targa;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Metadati))
			return false;
		Metadati other = (Metadati) obj;
		/*if (obj == null)
			return false;*/
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.codiceFiscale == null && other.getCodiceFiscale() == null) || (this.codiceFiscale != null && this.codiceFiscale.equals(other.getCodiceFiscale())))
				&& ((this.idEntitaFruitore == null && other.getIdEntitaFruitore() == null) || (this.idEntitaFruitore != null && this.idEntitaFruitore.equals(other.getIdEntitaFruitore())))
				&& ((this.targa == null && other.getTarga() == null) || (this.targa != null && this.targa.equals(other.getTarga())));
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
		if (getCodiceFiscale() != null) {
			_hashCode += getCodiceFiscale().hashCode();
		}
		if (getIdEntitaFruitore() != null) {
			_hashCode += getIdEntitaFruitore().hashCode();
		}
		if (getTarga() != null) {
			_hashCode += getTarga().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(Metadati.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Metadati"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("codiceFiscale");
		elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFiscale"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idEntitaFruitore");
		elemField.setXmlName(new javax.xml.namespace.QName("", "idEntitaFruitore"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("targa");
		elemField.setXmlName(new javax.xml.namespace.QName("", "targa"));
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
	public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType, java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType, java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

	@Override
	public String toString() {
		return "Metadati [codiceFiscale=" + codiceFiscale + ", idEntitaFruitore=" + idEntitaFruitore + ", targa=" + targa + "]";
	}

}
