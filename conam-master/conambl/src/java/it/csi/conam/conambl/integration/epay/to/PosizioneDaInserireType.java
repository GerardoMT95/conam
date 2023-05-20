/**
 * PosizioneDaInserireType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class PosizioneDaInserireType  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -719644676081490044L;

	private java.lang.String idPosizioneDebitoria;

    private java.math.BigInteger annoRiferimento;

    private java.math.BigDecimal importoTotale;

    private java.util.Date dataScadenza;

    private java.util.Date dataInizioValidita;

    private java.util.Date dataFineValidita;

    private java.lang.String descrizioneCausaleVersamento;

    private java.lang.String descrizioneRata;

    private it.csi.conam.conambl.integration.epay.to.ComponenteImportoType[] componentiImporto;

    private it.csi.conam.conambl.integration.epay.to.SoggettoType soggettoPagatore;

    private java.lang.String notePerIlPagatore;

    public PosizioneDaInserireType() {
    }

    public PosizioneDaInserireType(
           java.lang.String idPosizioneDebitoria,
           java.math.BigInteger annoRiferimento,
           java.math.BigDecimal importoTotale,
           java.util.Date dataScadenza,
           java.util.Date dataInizioValidita,
           java.util.Date dataFineValidita,
           java.lang.String descrizioneCausaleVersamento,
           java.lang.String descrizioneRata,
           it.csi.conam.conambl.integration.epay.to.ComponenteImportoType[] componentiImporto,
           it.csi.conam.conambl.integration.epay.to.SoggettoType soggettoPagatore,
           java.lang.String notePerIlPagatore) {
           this.idPosizioneDebitoria = idPosizioneDebitoria;
           this.annoRiferimento = annoRiferimento;
           this.importoTotale = importoTotale;
           this.dataScadenza = dataScadenza;
           this.dataInizioValidita = dataInizioValidita;
           this.dataFineValidita = dataFineValidita;
           this.descrizioneCausaleVersamento = descrizioneCausaleVersamento;
           this.descrizioneRata = descrizioneRata;
           this.componentiImporto = componentiImporto;
           this.soggettoPagatore = soggettoPagatore;
           this.notePerIlPagatore = notePerIlPagatore;
    }


    /**
     * Gets the idPosizioneDebitoria value for this PosizioneDaInserireType.
     * 
     * @return idPosizioneDebitoria
     */
    public java.lang.String getIdPosizioneDebitoria() {
        return idPosizioneDebitoria;
    }


    /**
     * Sets the idPosizioneDebitoria value for this PosizioneDaInserireType.
     * 
     * @param idPosizioneDebitoria
     */
    public void setIdPosizioneDebitoria(java.lang.String idPosizioneDebitoria) {
        this.idPosizioneDebitoria = idPosizioneDebitoria;
    }


    /**
     * Gets the annoRiferimento value for this PosizioneDaInserireType.
     * 
     * @return annoRiferimento
     */
    public java.math.BigInteger getAnnoRiferimento() {
        return annoRiferimento;
    }


    /**
     * Sets the annoRiferimento value for this PosizioneDaInserireType.
     * 
     * @param annoRiferimento
     */
    public void setAnnoRiferimento(java.math.BigInteger annoRiferimento) {
        this.annoRiferimento = annoRiferimento;
    }


    /**
     * Gets the importoTotale value for this PosizioneDaInserireType.
     * 
     * @return importoTotale
     */
    public java.math.BigDecimal getImportoTotale() {
        return importoTotale;
    }


    /**
     * Sets the importoTotale value for this PosizioneDaInserireType.
     * 
     * @param importoTotale
     */
    public void setImportoTotale(java.math.BigDecimal importoTotale) {
        this.importoTotale = importoTotale;
    }


    /**
     * Gets the dataScadenza value for this PosizioneDaInserireType.
     * 
     * @return dataScadenza
     */
    public java.util.Date getDataScadenza() {
        return dataScadenza;
    }


    /**
     * Sets the dataScadenza value for this PosizioneDaInserireType.
     * 
     * @param dataScadenza
     */
    public void setDataScadenza(java.util.Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }


    /**
     * Gets the dataInizioValidita value for this PosizioneDaInserireType.
     * 
     * @return dataInizioValidita
     */
    public java.util.Date getDataInizioValidita() {
        return dataInizioValidita;
    }


    /**
     * Sets the dataInizioValidita value for this PosizioneDaInserireType.
     * 
     * @param dataInizioValidita
     */
    public void setDataInizioValidita(java.util.Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }


    /**
     * Gets the dataFineValidita value for this PosizioneDaInserireType.
     * 
     * @return dataFineValidita
     */
    public java.util.Date getDataFineValidita() {
        return dataFineValidita;
    }


    /**
     * Sets the dataFineValidita value for this PosizioneDaInserireType.
     * 
     * @param dataFineValidita
     */
    public void setDataFineValidita(java.util.Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }


    /**
     * Gets the descrizioneCausaleVersamento value for this PosizioneDaInserireType.
     * 
     * @return descrizioneCausaleVersamento
     */
    public java.lang.String getDescrizioneCausaleVersamento() {
        return descrizioneCausaleVersamento;
    }


    /**
     * Sets the descrizioneCausaleVersamento value for this PosizioneDaInserireType.
     * 
     * @param descrizioneCausaleVersamento
     */
    public void setDescrizioneCausaleVersamento(java.lang.String descrizioneCausaleVersamento) {
        this.descrizioneCausaleVersamento = descrizioneCausaleVersamento;
    }


    /**
     * Gets the descrizioneRata value for this PosizioneDaInserireType.
     * 
     * @return descrizioneRata
     */
    public java.lang.String getDescrizioneRata() {
        return descrizioneRata;
    }


    /**
     * Sets the descrizioneRata value for this PosizioneDaInserireType.
     * 
     * @param descrizioneRata
     */
    public void setDescrizioneRata(java.lang.String descrizioneRata) {
        this.descrizioneRata = descrizioneRata;
    }


    /**
     * Gets the componentiImporto value for this PosizioneDaInserireType.
     * 
     * @return componentiImporto
     */
    public it.csi.conam.conambl.integration.epay.to.ComponenteImportoType[] getComponentiImporto() {
        return componentiImporto;
    }


    /**
     * Sets the componentiImporto value for this PosizioneDaInserireType.
     * 
     * @param componentiImporto
     */
    public void setComponentiImporto(it.csi.conam.conambl.integration.epay.to.ComponenteImportoType[] componentiImporto) {
        this.componentiImporto = componentiImporto;
    }


    /**
     * Gets the soggettoPagatore value for this PosizioneDaInserireType.
     * 
     * @return soggettoPagatore
     */
    public it.csi.conam.conambl.integration.epay.to.SoggettoType getSoggettoPagatore() {
        return soggettoPagatore;
    }


    /**
     * Sets the soggettoPagatore value for this PosizioneDaInserireType.
     * 
     * @param soggettoPagatore
     */
    public void setSoggettoPagatore(it.csi.conam.conambl.integration.epay.to.SoggettoType soggettoPagatore) {
        this.soggettoPagatore = soggettoPagatore;
    }


    /**
     * Gets the notePerIlPagatore value for this PosizioneDaInserireType.
     * 
     * @return notePerIlPagatore
     */
    public java.lang.String getNotePerIlPagatore() {
        return notePerIlPagatore;
    }


    /**
     * Sets the notePerIlPagatore value for this PosizioneDaInserireType.
     * 
     * @param notePerIlPagatore
     */
    public void setNotePerIlPagatore(java.lang.String notePerIlPagatore) {
        this.notePerIlPagatore = notePerIlPagatore;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof PosizioneDaInserireType)) return false;
        PosizioneDaInserireType other = (PosizioneDaInserireType) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idPosizioneDebitoria==null && other.getIdPosizioneDebitoria()==null) || 
             (this.idPosizioneDebitoria!=null &&
              this.idPosizioneDebitoria.equals(other.getIdPosizioneDebitoria()))) &&
            ((this.annoRiferimento==null && other.getAnnoRiferimento()==null) || 
             (this.annoRiferimento!=null &&
              this.annoRiferimento.equals(other.getAnnoRiferimento()))) &&
            ((this.importoTotale==null && other.getImportoTotale()==null) || 
             (this.importoTotale!=null &&
              this.importoTotale.equals(other.getImportoTotale()))) &&
            ((this.dataScadenza==null && other.getDataScadenza()==null) || 
             (this.dataScadenza!=null &&
              this.dataScadenza.equals(other.getDataScadenza()))) &&
            ((this.dataInizioValidita==null && other.getDataInizioValidita()==null) || 
             (this.dataInizioValidita!=null &&
              this.dataInizioValidita.equals(other.getDataInizioValidita()))) &&
            ((this.dataFineValidita==null && other.getDataFineValidita()==null) || 
             (this.dataFineValidita!=null &&
              this.dataFineValidita.equals(other.getDataFineValidita()))) &&
            ((this.descrizioneCausaleVersamento==null && other.getDescrizioneCausaleVersamento()==null) || 
             (this.descrizioneCausaleVersamento!=null &&
              this.descrizioneCausaleVersamento.equals(other.getDescrizioneCausaleVersamento()))) &&
            ((this.descrizioneRata==null && other.getDescrizioneRata()==null) || 
             (this.descrizioneRata!=null &&
              this.descrizioneRata.equals(other.getDescrizioneRata()))) &&
            ((this.componentiImporto==null && other.getComponentiImporto()==null) || 
             (this.componentiImporto!=null &&
              java.util.Arrays.equals(this.componentiImporto, other.getComponentiImporto()))) &&
            ((this.soggettoPagatore==null && other.getSoggettoPagatore()==null) || 
             (this.soggettoPagatore!=null &&
              this.soggettoPagatore.equals(other.getSoggettoPagatore()))) &&
            ((this.notePerIlPagatore==null && other.getNotePerIlPagatore()==null) || 
             (this.notePerIlPagatore!=null &&
              this.notePerIlPagatore.equals(other.getNotePerIlPagatore())));
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
        if (getIdPosizioneDebitoria() != null) {
            _hashCode += getIdPosizioneDebitoria().hashCode();
        }
        if (getAnnoRiferimento() != null) {
            _hashCode += getAnnoRiferimento().hashCode();
        }
        if (getImportoTotale() != null) {
            _hashCode += getImportoTotale().hashCode();
        }
        if (getDataScadenza() != null) {
            _hashCode += getDataScadenza().hashCode();
        }
        if (getDataInizioValidita() != null) {
            _hashCode += getDataInizioValidita().hashCode();
        }
        if (getDataFineValidita() != null) {
            _hashCode += getDataFineValidita().hashCode();
        }
        if (getDescrizioneCausaleVersamento() != null) {
            _hashCode += getDescrizioneCausaleVersamento().hashCode();
        }
        if (getDescrizioneRata() != null) {
            _hashCode += getDescrizioneRata().hashCode();
        }
        if (getComponentiImporto() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getComponentiImporto());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getComponentiImporto(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSoggettoPagatore() != null) {
            _hashCode += getSoggettoPagatore().hashCode();
        }
        if (getNotePerIlPagatore() != null) {
            _hashCode += getNotePerIlPagatore().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PosizioneDaInserireType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "PosizioneDaInserireType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPosizioneDebitoria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "IdPosizioneDebitoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoRiferimento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "AnnoRiferimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importoTotale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ImportoTotale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataScadenza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "DataScadenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizioValidita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "DataInizioValidita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFineValidita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "DataFineValidita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneCausaleVersamento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "DescrizioneCausaleVersamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneRata");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "DescrizioneRata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("componentiImporto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ComponentiImporto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ComponenteImportoType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ComponenteImporto"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soggettoPagatore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "SoggettoPagatore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "SoggettoType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notePerIlPagatore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "NotePerIlPagatore"));
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
