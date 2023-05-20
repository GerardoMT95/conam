/**
 * Documento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

import java.util.Arrays;

public class Documento implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9094302384026137585L;

	private byte[] file;

	private byte[] fileXSL;

	private java.lang.String idDocumento;

	private java.lang.String nomeFile;

	private java.lang.String nomeFileXSL;

	private int numeroAllegati;
	
	private String objectIdDocumentoFisico;

	public Documento() {
	}

	public Documento(byte[] file, byte[] fileXSL, java.lang.String idDocumento, java.lang.String nomeFile, java.lang.String nomeFileXSL, int numeroAllegati,
			String objectIdDocumentoFisico) {
		this.file = file;
		this.fileXSL = fileXSL;
		this.idDocumento = idDocumento;
		this.nomeFile = nomeFile;
		this.nomeFileXSL = nomeFileXSL;
		this.numeroAllegati = numeroAllegati;
		this.objectIdDocumentoFisico = objectIdDocumentoFisico;
	}

	/**
	 * Gets the file value for this Documento.
	 * 
	 * @return file
	 */
	public byte[] getFile() {
		return file;
	}

	/**
	 * Sets the file value for this Documento.
	 * 
	 * @param file
	 */
	public void setFile(byte[] file) {
		this.file = file;
	}

	/**
	 * Gets the fileXSL value for this Documento.
	 * 
	 * @return fileXSL
	 */
	public byte[] getFileXSL() {
		return fileXSL;
	}

	/**
	 * Sets the fileXSL value for this Documento.
	 * 
	 * @param fileXSL
	 */
	public void setFileXSL(byte[] fileXSL) {
		this.fileXSL = fileXSL;
	}

	/**
	 * Gets the idDocumento value for this Documento.
	 * 
	 * @return idDocumento
	 */
	public java.lang.String getIdDocumento() {
		return idDocumento;
	}

	/**
	 * Sets the idDocumento value for this Documento.
	 * 
	 * @param idDocumento
	 */
	public void setIdDocumento(java.lang.String idDocumento) {
		this.idDocumento = idDocumento;
	}

	/**
	 * Gets the nomeFile value for this Documento.
	 * 
	 * @return nomeFile
	 */
	public java.lang.String getNomeFile() {
		return nomeFile;
	}

	/**
	 * Sets the nomeFile value for this Documento.
	 * 
	 * @param nomeFile
	 */
	public void setNomeFile(java.lang.String nomeFile) {
		this.nomeFile = nomeFile;
	}

	/**
	 * Gets the nomeFileXSL value for this Documento.
	 * 
	 * @return nomeFileXSL
	 */
	public java.lang.String getNomeFileXSL() {
		return nomeFileXSL;
	}

	/**
	 * Sets the nomeFileXSL value for this Documento.
	 * 
	 * @param nomeFileXSL
	 */
	public void setNomeFileXSL(java.lang.String nomeFileXSL) {
		this.nomeFileXSL = nomeFileXSL;
	}

	/**
	 * Gets the numeroAllegati value for this Documento.
	 * 
	 * @return numeroAllegati
	 */
	public int getNumeroAllegati() {
		return numeroAllegati;
	}

	/**
	 * Sets the numeroAllegati value for this Documento.
	 * 
	 * @param numeroAllegati
	 */
	public void setNumeroAllegati(int numeroAllegati) {
		this.numeroAllegati = numeroAllegati;
	}

	

	/**
	 * Gets the objectIdDocumentoFisico value for this Documento.
	 * 
	 * @return objectIdDocumentoFisico
	 */
	public String getObjectIdDocumentoFisico() {
		return objectIdDocumentoFisico;
	}

	/**
	 * Sets the objectIdDocumentoFisico value for this Documento.
	 * 
	 * @param objectIdDocumentoFisico
	 */
	public void setObjectIdDocumentoFisico (String objectIdDocumentoFisico) {
		this.objectIdDocumentoFisico = objectIdDocumentoFisico;
	}
	
	
	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Documento))
			return false;
		Documento other = (Documento) obj;
		/*if (obj == null)
			return false;*/
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.file == null && other.getFile() == null) || (this.file != null && java.util.Arrays.equals(this.file, other.getFile())))
				&& ((this.fileXSL == null && other.getFileXSL() == null) || (this.fileXSL != null && java.util.Arrays.equals(this.fileXSL, other.getFileXSL())))
				&& ((this.idDocumento == null && other.getIdDocumento() == null) || (this.idDocumento != null && this.idDocumento.equals(other.getIdDocumento())))
				&& ((this.nomeFile == null && other.getNomeFile() == null) || (this.nomeFile != null && this.nomeFile.equals(other.getNomeFile())))
				&& ((this.objectIdDocumentoFisico == null && other.getObjectIdDocumentoFisico() == null) || (this.objectIdDocumentoFisico != null && this.objectIdDocumentoFisico.equals(other.getObjectIdDocumentoFisico())))
				&& ((this.nomeFileXSL == null && other.getNomeFileXSL() == null) || (this.nomeFileXSL != null && this.nomeFileXSL.equals(other.getNomeFileXSL())))
				&& this.numeroAllegati == other.getNumeroAllegati();
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
		if (getFile() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getFile()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getFile(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getFileXSL() != null) {
			for (int i = 0; i < java.lang.reflect.Array.getLength(getFileXSL()); i++) {
				java.lang.Object obj = java.lang.reflect.Array.get(getFileXSL(), i);
				if (obj != null && !obj.getClass().isArray()) {
					_hashCode += obj.hashCode();
				}
			}
		}
		if (getIdDocumento() != null) {
			_hashCode += getIdDocumento().hashCode();
		}
		if (getNomeFile() != null) {
			_hashCode += getNomeFile().hashCode();
		}
		if (getNomeFileXSL() != null) {
			_hashCode += getNomeFileXSL().hashCode();
		}
		_hashCode += getNumeroAllegati();
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(Documento.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Documento"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("file");
		elemField.setXmlName(new javax.xml.namespace.QName("", "file"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "base64Binary"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("fileXSL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "fileXSL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "base64Binary"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idDocumento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "idDocumento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("nomeFile");
		elemField.setXmlName(new javax.xml.namespace.QName("", "nomeFile"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("nomeFileXSL");
		elemField.setXmlName(new javax.xml.namespace.QName("", "nomeFileXSL"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("numeroAllegati");
		elemField.setXmlName(new javax.xml.namespace.QName("", "numeroAllegati"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("objectIdDocumentoFisico");
		elemField.setXmlName(new javax.xml.namespace.QName("", "objectIdDocumentoFisico"));
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
		return "Documento [fileXSL=" + Arrays.toString(fileXSL) + ", idDocumento=" + idDocumento + ", nomeFile=" + nomeFile + 
				", nomeFileXSL=" + nomeFileXSL + ", numeroAllegati=" + numeroAllegati + ", objectIdDocumentoFisico=" + objectIdDocumentoFisico
				+ "]";
	}

}
