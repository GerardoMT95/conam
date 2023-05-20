/**
 * SoggettoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class SoggettoType  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7891797021613470231L;

	private it.csi.conam.conambl.integration.epay.to.PersonaFisicaType personaFisica;

    private it.csi.conam.conambl.integration.epay.to.PersonaGiuridicaType personaGiuridica;

    private java.lang.String identificativoUnivocoFiscale;

    private java.lang.String indirizzo;

    private java.lang.String civico;

    private java.lang.String CAP;

    private java.lang.String localita;

    private java.lang.String provincia;

    private java.lang.String nazione;

    private java.lang.String EMail;

    public SoggettoType() {
    }

    public SoggettoType(
           it.csi.conam.conambl.integration.epay.to.PersonaFisicaType personaFisica,
           it.csi.conam.conambl.integration.epay.to.PersonaGiuridicaType personaGiuridica,
           java.lang.String identificativoUnivocoFiscale,
           java.lang.String indirizzo,
           java.lang.String civico,
           java.lang.String CAP,
           java.lang.String localita,
           java.lang.String provincia,
           java.lang.String nazione,
           java.lang.String EMail) {
           this.personaFisica = personaFisica;
           this.personaGiuridica = personaGiuridica;
           this.identificativoUnivocoFiscale = identificativoUnivocoFiscale;
           this.indirizzo = indirizzo;
           this.civico = civico;
           this.CAP = CAP;
           this.localita = localita;
           this.provincia = provincia;
           this.nazione = nazione;
           this.EMail = EMail;
    }


    /**
     * Gets the personaFisica value for this SoggettoType.
     * 
     * @return personaFisica
     */
    public it.csi.conam.conambl.integration.epay.to.PersonaFisicaType getPersonaFisica() {
        return personaFisica;
    }


    /**
     * Sets the personaFisica value for this SoggettoType.
     * 
     * @param personaFisica
     */
    public void setPersonaFisica(it.csi.conam.conambl.integration.epay.to.PersonaFisicaType personaFisica) {
        this.personaFisica = personaFisica;
    }


    /**
     * Gets the personaGiuridica value for this SoggettoType.
     * 
     * @return personaGiuridica
     */
    public it.csi.conam.conambl.integration.epay.to.PersonaGiuridicaType getPersonaGiuridica() {
        return personaGiuridica;
    }


    /**
     * Sets the personaGiuridica value for this SoggettoType.
     * 
     * @param personaGiuridica
     */
    public void setPersonaGiuridica(it.csi.conam.conambl.integration.epay.to.PersonaGiuridicaType personaGiuridica) {
        this.personaGiuridica = personaGiuridica;
    }


    /**
     * Gets the identificativoUnivocoFiscale value for this SoggettoType.
     * 
     * @return identificativoUnivocoFiscale
     */
    public java.lang.String getIdentificativoUnivocoFiscale() {
        return identificativoUnivocoFiscale;
    }


    /**
     * Sets the identificativoUnivocoFiscale value for this SoggettoType.
     * 
     * @param identificativoUnivocoFiscale
     */
    public void setIdentificativoUnivocoFiscale(java.lang.String identificativoUnivocoFiscale) {
        this.identificativoUnivocoFiscale = identificativoUnivocoFiscale;
    }


    /**
     * Gets the indirizzo value for this SoggettoType.
     * 
     * @return indirizzo
     */
    public java.lang.String getIndirizzo() {
        return indirizzo;
    }


    /**
     * Sets the indirizzo value for this SoggettoType.
     * 
     * @param indirizzo
     */
    public void setIndirizzo(java.lang.String indirizzo) {
        this.indirizzo = indirizzo;
    }


    /**
     * Gets the civico value for this SoggettoType.
     * 
     * @return civico
     */
    public java.lang.String getCivico() {
        return civico;
    }


    /**
     * Sets the civico value for this SoggettoType.
     * 
     * @param civico
     */
    public void setCivico(java.lang.String civico) {
        this.civico = civico;
    }


    /**
     * Gets the CAP value for this SoggettoType.
     * 
     * @return CAP
     */
    public java.lang.String getCAP() {
        return CAP;
    }


    /**
     * Sets the CAP value for this SoggettoType.
     * 
     * @param CAP
     */
    public void setCAP(java.lang.String CAP) {
        this.CAP = CAP;
    }


    /**
     * Gets the localita value for this SoggettoType.
     * 
     * @return localita
     */
    public java.lang.String getLocalita() {
        return localita;
    }


    /**
     * Sets the localita value for this SoggettoType.
     * 
     * @param localita
     */
    public void setLocalita(java.lang.String localita) {
        this.localita = localita;
    }


    /**
     * Gets the provincia value for this SoggettoType.
     * 
     * @return provincia
     */
    public java.lang.String getProvincia() {
        return provincia;
    }


    /**
     * Sets the provincia value for this SoggettoType.
     * 
     * @param provincia
     */
    public void setProvincia(java.lang.String provincia) {
        this.provincia = provincia;
    }


    /**
     * Gets the nazione value for this SoggettoType.
     * 
     * @return nazione
     */
    public java.lang.String getNazione() {
        return nazione;
    }


    /**
     * Sets the nazione value for this SoggettoType.
     * 
     * @param nazione
     */
    public void setNazione(java.lang.String nazione) {
        this.nazione = nazione;
    }


    /**
     * Gets the EMail value for this SoggettoType.
     * 
     * @return EMail
     */
    public java.lang.String getEMail() {
        return EMail;
    }


    /**
     * Sets the EMail value for this SoggettoType.
     * 
     * @param EMail
     */
    public void setEMail(java.lang.String EMail) {
        this.EMail = EMail;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof SoggettoType)) return false;
        SoggettoType other = (SoggettoType) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.personaFisica==null && other.getPersonaFisica()==null) || 
             (this.personaFisica!=null &&
              this.personaFisica.equals(other.getPersonaFisica()))) &&
            ((this.personaGiuridica==null && other.getPersonaGiuridica()==null) || 
             (this.personaGiuridica!=null &&
              this.personaGiuridica.equals(other.getPersonaGiuridica()))) &&
            ((this.identificativoUnivocoFiscale==null && other.getIdentificativoUnivocoFiscale()==null) || 
             (this.identificativoUnivocoFiscale!=null &&
              this.identificativoUnivocoFiscale.equals(other.getIdentificativoUnivocoFiscale()))) &&
            ((this.indirizzo==null && other.getIndirizzo()==null) || 
             (this.indirizzo!=null &&
              this.indirizzo.equals(other.getIndirizzo()))) &&
            ((this.civico==null && other.getCivico()==null) || 
             (this.civico!=null &&
              this.civico.equals(other.getCivico()))) &&
            ((this.CAP==null && other.getCAP()==null) || 
             (this.CAP!=null &&
              this.CAP.equals(other.getCAP()))) &&
            ((this.localita==null && other.getLocalita()==null) || 
             (this.localita!=null &&
              this.localita.equals(other.getLocalita()))) &&
            ((this.provincia==null && other.getProvincia()==null) || 
             (this.provincia!=null &&
              this.provincia.equals(other.getProvincia()))) &&
            ((this.nazione==null && other.getNazione()==null) || 
             (this.nazione!=null &&
              this.nazione.equals(other.getNazione()))) &&
            ((this.EMail==null && other.getEMail()==null) || 
             (this.EMail!=null &&
              this.EMail.equals(other.getEMail())));
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
        if (getPersonaFisica() != null) {
            _hashCode += getPersonaFisica().hashCode();
        }
        if (getPersonaGiuridica() != null) {
            _hashCode += getPersonaGiuridica().hashCode();
        }
        if (getIdentificativoUnivocoFiscale() != null) {
            _hashCode += getIdentificativoUnivocoFiscale().hashCode();
        }
        if (getIndirizzo() != null) {
            _hashCode += getIndirizzo().hashCode();
        }
        if (getCivico() != null) {
            _hashCode += getCivico().hashCode();
        }
        if (getCAP() != null) {
            _hashCode += getCAP().hashCode();
        }
        if (getLocalita() != null) {
            _hashCode += getLocalita().hashCode();
        }
        if (getProvincia() != null) {
            _hashCode += getProvincia().hashCode();
        }
        if (getNazione() != null) {
            _hashCode += getNazione().hashCode();
        }
        if (getEMail() != null) {
            _hashCode += getEMail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SoggettoType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "SoggettoType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personaFisica");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "PersonaFisica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "PersonaFisicaType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personaGiuridica");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "PersonaGiuridica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "PersonaGiuridicaType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificativoUnivocoFiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "IdentificativoUnivocoFiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indirizzo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "Indirizzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("civico");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "Civico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CAP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "CAP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "Localita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provincia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "Provincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nazione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "Nazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EMail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "EMail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
