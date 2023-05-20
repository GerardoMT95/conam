/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.beans;

// 20200706_LC
public class RequestSpostaDocumento implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8366984668043514124L;

	private String codiceFruitore;

	private Documento documento;

	private String folder;

	private Metadati metadati;

	private String rootActa;

	private String soggettoActa;

	private String tipoDocumento;

	private String numeroProtocollo;

	private String idVerbale;

	private Soggetto soggetto;

	// 20210506_LC
    private String parolaChiaveFolderTemp;
    
    
    

	public RequestSpostaDocumento() {
	}






	public RequestSpostaDocumento(String codiceFruitore, Documento documento, String folder, Metadati metadati,
			String rootActa, String soggettoActa, String tipoDocumento, String numeroProtocollo, String idVerbale,
			Soggetto soggetto, String parolaChiaveFolderTemp) {
		super();
		this.codiceFruitore = codiceFruitore;
		this.documento = documento;
		this.folder = folder;
		this.metadati = metadati;
		this.rootActa = rootActa;
		this.soggettoActa = soggettoActa;
		this.tipoDocumento = tipoDocumento;
		this.numeroProtocollo = numeroProtocollo;
		this.idVerbale = idVerbale;
		this.soggetto = soggetto;
		this.parolaChiaveFolderTemp = parolaChiaveFolderTemp;
	}






	public String getCodiceFruitore() {
		return codiceFruitore;
	}



	public void setCodiceFruitore(String codiceFruitore) {
		this.codiceFruitore = codiceFruitore;
	}



	public Documento getDocumento() {
		return documento;
	}



	public void setDocumento(Documento documento) {
		this.documento = documento;
	}



	public Metadati getMetadati() {
		return metadati;
	}



	public void setMetadati(Metadati metadati) {
		this.metadati = metadati;
	}



	public String getRootActa() {
		return rootActa;
	}



	public void setRootActa(String rootActa) {
		this.rootActa = rootActa;
	}




	public String getSoggettoActa() {
		return soggettoActa;
	}



	public void setSoggettoActa(String soggettoActa) {
		this.soggettoActa = soggettoActa;
	}



	public String getTipoDocumento() {
		return tipoDocumento;
	}



	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	
	
	

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}



	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}



	public static void setTypeDesc(org.apache.axis.description.TypeDesc typeDesc) {
		RequestSpostaDocumento.typeDesc = typeDesc;
	}



	public String getFolder() {
		return folder;
	}



	public void setFolder(String folder) {
		this.folder = folder;
	}


	




	public String getIdVerbale() {
		return idVerbale;
	}



	public void setIdVerbale(String idVerbale) {
		this.idVerbale = idVerbale;
	}


	
	

	public Soggetto getSoggetto() {
		return soggetto;
	}






	public void setSoggetto(Soggetto soggetto) {
		this.soggetto = soggetto;
	}



	
	
	



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codiceFruitore == null) ? 0 : codiceFruitore.hashCode());
		result = prime * result + ((documento == null) ? 0 : documento.hashCode());
		result = prime * result + ((folder == null) ? 0 : folder.hashCode());
		result = prime * result + ((idVerbale == null) ? 0 : idVerbale.hashCode());
		result = prime * result + ((metadati == null) ? 0 : metadati.hashCode());
		result = prime * result + ((numeroProtocollo == null) ? 0 : numeroProtocollo.hashCode());
		result = prime * result + ((rootActa == null) ? 0 : rootActa.hashCode());
		result = prime * result + ((soggetto == null) ? 0 : soggetto.hashCode());
		result = prime * result + ((soggettoActa == null) ? 0 : soggettoActa.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		result = prime * result + ((parolaChiaveFolderTemp == null) ? 0 : parolaChiaveFolderTemp.hashCode());
		return result;
	}






	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestSpostaDocumento other = (RequestSpostaDocumento) obj;
		if (codiceFruitore == null) {
			if (other.codiceFruitore != null)
				return false;
		} else if (!codiceFruitore.equals(other.codiceFruitore))
			return false;
		if (documento == null) {
			if (other.documento != null)
				return false;
		} else if (!documento.equals(other.documento))
			return false;
		if (folder == null) {
			if (other.folder != null)
				return false;
		} else if (!folder.equals(other.folder))
			return false;
		if (idVerbale == null) {
			if (other.idVerbale != null)
				return false;
		} else if (!idVerbale.equals(other.idVerbale))
			return false;
		if (metadati == null) {
			if (other.metadati != null)
				return false;
		} else if (!metadati.equals(other.metadati))
			return false;
		if (numeroProtocollo == null) {
			if (other.numeroProtocollo != null)
				return false;
		} else if (!numeroProtocollo.equals(other.numeroProtocollo))
			return false;
		if (rootActa == null) {
			if (other.rootActa != null)
				return false;
		} else if (!rootActa.equals(other.rootActa))
			return false;
		if (soggetto == null) {
			if (other.soggetto != null)
				return false;
		} else if (!soggetto.equals(other.soggetto))
			return false;
		if (soggettoActa == null) {
			if (other.soggettoActa != null)
				return false;
		} else if (!soggettoActa.equals(other.soggettoActa))
			return false;
		if (tipoDocumento == null) {
			if (other.tipoDocumento != null)
				return false;
		} else if (!tipoDocumento.equals(other.tipoDocumento))
			return false;
		if (parolaChiaveFolderTemp == null) {
			if (other.parolaChiaveFolderTemp != null)
				return false;
		} else if (!parolaChiaveFolderTemp.equals(other.parolaChiaveFolderTemp))
			return false;
		return true;
	}









	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(RequestSpostaDocumento.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "RequestSpostaDocumentoProtocollato"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();

		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("codiceFruitore");
		elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFruitore"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("documento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "documento"));
		elemField.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Documento"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("folder");
		elemField.setXmlName(new javax.xml.namespace.QName("", "folder"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("metadati");
		elemField.setXmlName(new javax.xml.namespace.QName("", "metadati"));
		elemField.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Metadati"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("rootActa");
		elemField.setXmlName(new javax.xml.namespace.QName("", "rootActa"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);

		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("soggetto");
		elemField.setXmlName(new javax.xml.namespace.QName("", "soggetto"));
		elemField.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Soggetto"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("soggettoActa");
		elemField.setXmlName(new javax.xml.namespace.QName("", "soggettoActa"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("tipoDocumento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("numeroProtocollo");
		elemField.setXmlName(new javax.xml.namespace.QName("", "numeroProtocollo"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("idVerbale");
		elemField.setXmlName(new javax.xml.namespace.QName("", "idVerbale"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("parolaChiaveFolderTemp");
		elemField.setXmlName(new javax.xml.namespace.QName("", "parolaChiaveFolderTemp"));
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






	public String getParolaChiaveFolderTemp() {
		return parolaChiaveFolderTemp;
	}






	public void setParolaChiaveFolderTemp(String parolaChiaveFolderTemp) {
		this.parolaChiaveFolderTemp = parolaChiaveFolderTemp;
	}


}
