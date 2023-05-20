/**
 * Soggetto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class Soggetto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3691796122251569403L;

	private java.lang.String cognome;

	private java.lang.String denominazione;

	private java.lang.String nome;

	private java.lang.String tipologia;

	public static final String TOPOLOGIA_SOGGETTO_MITTENTE = "MITTENTE";
	public static final String TOPOLOGIA_SOGGETTO_DESTINATARIO = "DESTINATARIO";
	
	public Soggetto() {
	}

	public Soggetto(java.lang.String cognome, java.lang.String denominazione, java.lang.String nome, java.lang.String tipologia) {
		this.cognome = cognome;
		this.denominazione = denominazione;
		this.nome = nome;
		this.tipologia = tipologia;
	}

	/**
	 * Gets the cognome value for this Soggetto.
	 * 
	 * @return cognome
	 */
	public java.lang.String getCognome() {
		return cognome;
	}

	/**
	 * Sets the cognome value for this Soggetto.
	 * 
	 * @param cognome
	 */
	public void setCognome(java.lang.String cognome) {
		this.cognome = cognome;
	}

	/**
	 * Gets the denominazione value for this Soggetto.
	 * 
	 * @return denominazione
	 */
	public java.lang.String getDenominazione() {
		return denominazione;
	}

	/**
	 * Sets the denominazione value for this Soggetto.
	 * 
	 * @param denominazione
	 */
	public void setDenominazione(java.lang.String denominazione) {
		this.denominazione = denominazione;
	}

	/**
	 * Gets the nome value for this Soggetto.
	 * 
	 * @return nome
	 */
	public java.lang.String getNome() {
		return nome;
	}

	/**
	 * Sets the nome value for this Soggetto.
	 * 
	 * @param nome
	 */
	public void setNome(java.lang.String nome) {
		this.nome = nome;
	}

	/**
	 * Gets the tipologia value for this Soggetto.
	 * 
	 * @return tipologia
	 */
	public java.lang.String getTipologia() {
		return tipologia;
	}

	/**
	 * Sets the tipologia value for this Soggetto.
	 * 
	 * @param tipologia
	 */
	public void setTipologia(java.lang.String tipologia) {
		this.tipologia = tipologia;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Soggetto))
			return false;
		Soggetto other = (Soggetto) obj;
		/*if (obj == null)
			return false;*/
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.cognome == null && other.getCognome() == null) || (this.cognome != null && this.cognome.equals(other.getCognome())))
				&& ((this.denominazione == null && other.getDenominazione() == null) || (this.denominazione != null && this.denominazione.equals(other.getDenominazione())))
				&& ((this.nome == null && other.getNome() == null) || (this.nome != null && this.nome.equals(other.getNome())))
				&& ((this.tipologia == null && other.getTipologia() == null) || (this.tipologia != null && this.tipologia.equals(other.getTipologia())));
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
		if (getCognome() != null) {
			_hashCode += getCognome().hashCode();
		}
		if (getDenominazione() != null) {
			_hashCode += getDenominazione().hashCode();
		}
		if (getNome() != null) {
			_hashCode += getNome().hashCode();
		}
		if (getTipologia() != null) {
			_hashCode += getTipologia().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(Soggetto.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Soggetto"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("cognome");
		elemField.setXmlName(new javax.xml.namespace.QName("", "cognome"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("denominazione");
		elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("nome");
		elemField.setXmlName(new javax.xml.namespace.QName("", "nome"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("tipologia");
		elemField.setXmlName(new javax.xml.namespace.QName("", "tipologia"));
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
		return "Soggetto [cognome=" + cognome + ", denominazione=" + denominazione + ", nome=" + nome + ", tipologia=" + tipologia + "]";
	}

}
