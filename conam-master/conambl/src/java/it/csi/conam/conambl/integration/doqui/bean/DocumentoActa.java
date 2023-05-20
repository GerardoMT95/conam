/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.bean;

import java.io.Serializable;
import java.util.Date;

public class DocumentoActa implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1048804849834434703L;
	
	public static final Integer TIPO_STRUTTURA_FASCICOLO = 1;
	public static final Integer TIPO_STRUTTURA_VOLUME    = 2;
	public static final Integer TIPO_STRUTTURA_DOSSIER   = 3;
	
	
	public static final int TIPOLOGIA_DATI_PERSONALI = 1;
	public static final int TIPOLOGIA_DATI_RISERVATI = 2;
	public static final int TIPOLOGIA_DATI_SENSIBILI = 3;
	
	
	public static final int CONSERVAZIONE_GENERALE_10 = 10;
	public static final int CONSERVAZIONE_GENERALE_99 = 99;
	
	
	
	private String 			idDocumento;
	private String 			folder;
	private String 			autore;
	private String 			originatore;
	private String 			autoreFisico;
	private String 			autoreGiuridico;
	private String 			scrittore;
	private String 			destinatarioGiuridico;
	
	private String 			destinatarioFisico;
	private String 			codiceFiscaleDestinatarioFisico;
	
	private String          soggettoProduttore;
	private String          codiceSoggettoProduttore;

	//JIRA - Gestione Notifica
	//private String          dataTopica = "TORINO";
	//private Date            dataCronica = DateFormat.getCurrentDate();
	private String          dataTopica;
	private Date            dataCronica;
	
	private SoggettoActa 	soggettoActa;
	private String 			fruitore;
	private String       	mittentiEsterni;
	private String 			applicativoAlimentante;
	private MetadatiActa    metadatiActa;
	private String 			classificazioneId;
	private String 			registrazioneId;
	
	
	private int             tipologiaDati = TIPOLOGIA_DATI_PERSONALI;
	private int             conservazioneGenerale = CONSERVAZIONE_GENERALE_10;
	

	private Integer         tipoStrutturaFolder;
	private Integer         tipoStrutturaRoot;
	private int             numeroAllegati;
	private boolean 		documentoCartaceo;
	private String 			collocazioneCartacea;
	
	
	public int getNumeroAllegati() {
		return numeroAllegati;
	}

	public void setNumeroAllegati(int numeroAllegati) {
		this.numeroAllegati = numeroAllegati;
	}
	
	public MetadatiActa getMetadatiActa()
	{
		return metadatiActa;
	}
	public void setMetadatiActa(MetadatiActa metadatiActa) 
	{
		this.metadatiActa = metadatiActa;
	}
	
	public String getFruitore() 
	{
		return fruitore;
	}
	public void setFruitore(String fruitore)
	{
		this.fruitore = fruitore;
	}
	
	public String getIdDocumento()
	{
		return idDocumento;
	}
	public void setIdDocumento(String idDocumento)
	{
		this.idDocumento = idDocumento;
	}
	
	public String getFolder()
	{
		return folder;
	}
	public void setFolder(String folder) 
	{
		this.folder = folder;
	}
	public String getAutore()
	{
		return autore;
	}
	public void setAutore(String autore) 
	{
		this.autore = autore;
	}
	
	public SoggettoActa getSoggettoActa()
	{
		return soggettoActa;
	}
	public void setSoggettoActa(SoggettoActa soggettoActa)
	{
		this.soggettoActa = soggettoActa;
	}
	
	public String getClassificazioneId()
	{
		return classificazioneId;
	}
	
	public void setClassificazioneId(String classificazioneId)
	{
		this.classificazioneId = classificazioneId;
	}
	
	public String getRegistrazioneId()
	{
		return registrazioneId;
	}
	
	public void setRegistrazioneId(String registrazioneId) 
	{
		this.registrazioneId = registrazioneId;
	}
	
	public Integer getTipoStrutturaFolder() {
		return tipoStrutturaFolder;
	}
	
	public void setTipoStrutturaFolder(Integer tipoStrutturaFolder) {
		this.tipoStrutturaFolder = tipoStrutturaFolder;
	}
	
	public Integer getTipoStrutturaRoot() {
		return tipoStrutturaRoot;
	}
	
	public void setTipoStrutturaRoot(Integer tipoStrutturaRoot) {
		this.tipoStrutturaRoot = tipoStrutturaRoot;
	}

	public int getTipologiaDati() {
		return tipologiaDati;
	}

	/*
	public void setTipologiaDati(int tipologiaDati) {
		this.tipologiaDati = tipologiaDati;
	}
	*/
	
	public int getConservazioneGenerale() {
		return conservazioneGenerale;
	}

	public String getApplicativoAlimentante() {
		return applicativoAlimentante;
	}

	public void setApplicativoAlimentante(String applicativoAlimentante) {
		this.applicativoAlimentante = applicativoAlimentante;
	}

	public String getOriginatore() {
		return originatore;
	}

	public void setOriginatore(String originatore) {
		this.originatore = originatore;
	}

	public String getAutoreFisico() {
		return autoreFisico;
	}

	public void setAutoreFisico(String autoreFisico) {
		this.autoreFisico = autoreFisico;
	}

	public String getAutoreGiuridico() {
		return autoreGiuridico;
	}

	public void setAutoreGiuridico(String autoreGiuridico) {
		this.autoreGiuridico = autoreGiuridico;
	}

	public String getScrittore() {
		return scrittore;
	}

	public void setScrittore(String scrittore) {
		this.scrittore = scrittore;
	}

	public String getDestinatarioGiuridico() {
		return destinatarioGiuridico;
	}

	public void setDestinatarioGiuridico(String destinatarioGiuridico) {
		this.destinatarioGiuridico = destinatarioGiuridico;
	}

	public String getDestinatarioFisico() {
		return destinatarioFisico;
	}

	public void setDestinatarioFisico(String destinatarioFisico) {
		this.destinatarioFisico = destinatarioFisico;
	}

	public String getCodiceFiscaleDestinatarioFisico() {
		return codiceFiscaleDestinatarioFisico;
	}

	public void setCodiceFiscaleDestinatarioFisico(String codiceFiscaleDestinatarioFisico) {
		this.codiceFiscaleDestinatarioFisico = codiceFiscaleDestinatarioFisico;
	}

	public String getSoggettoProduttore() {
		return soggettoProduttore;
	}

	public void setSoggettoProduttore(String soggettoProduttore) {
		this.soggettoProduttore = soggettoProduttore;
	}

	public String getCodiceSoggettoProduttore() {
		return codiceSoggettoProduttore;
	}

	public void setCodiceSoggettoProduttore(String codiceSoggettoProduttore) {
		this.codiceSoggettoProduttore = codiceSoggettoProduttore;
	}

	public String getDataTopica() {
		return dataTopica;
	}

	public void setDataTopica(String dataTopica) {
		this.dataTopica = dataTopica;
	}

	public Date getDataCronica() {
		return dataCronica;
	}

	public void setDataCronica(Date dataCronica) {
		this.dataCronica = dataCronica;
	}

	public void setConservazioneGenerale(int conservazioneGenerale) {
		this.conservazioneGenerale = conservazioneGenerale;
	}

	public boolean isDocumentoCartaceo() {
		return documentoCartaceo;
	}

	public void setDocumentoCartaceo(boolean documentoCartaceo) {
		this.documentoCartaceo = documentoCartaceo;
	}

	public String getMittentiEsterni() {
		return mittentiEsterni;
	}

	public void setMittentiEsterni(String mittentiEsterni) {
		this.mittentiEsterni = mittentiEsterni;
	}
	

	public String getCollocazioneCartacea() {
		return collocazioneCartacea;
	}

	public void setCollocazioneCartacea(String collocazioneCartacea) {
		this.collocazioneCartacea = collocazioneCartacea;
	}
}
