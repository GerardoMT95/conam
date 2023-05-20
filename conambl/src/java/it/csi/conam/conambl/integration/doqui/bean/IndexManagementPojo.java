/**
 * 
 */
package it.csi.conam.conambl.integration.doqui.bean;

import java.util.Arrays;

/**
 * @author giuganino
 *
 */
public class IndexManagementPojo {

	private String fruitore;
	private String documentRoot; 
	private String repostory; 
	private String folder; 
	private String nomeFile;
	private String mimeType;
	private byte[] file;

	private String idDocumento; 
	private String tipoDocumento; 
	private String idEntitaFruitore; 
//	private String targa; 
//	private String codiceFiscale; 
	
	private String usr; 
	private String psw; 
	private String utenteApplicativo; 
	
	
	// 20200615_LC
	private String identificativoFornitore;

	// 20200615_LC
	private String customModel;
	
	// 20200714_LC
	private String indexType;
	
	
	public String getIdentificativoFornitore() {
		return identificativoFornitore;
	}
	public void setIdentificativoFornitore(String identificativoFornitore) {
		this.identificativoFornitore = identificativoFornitore;
	}	
	
	public String getCustomModel() {
		return customModel;
	}
	public void setCustomModel(String customModel) {
		this.customModel = customModel;
	}
	
	// --
	
	
	
	
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public String getIdEntitaFruitore() {
		return idEntitaFruitore;
	}
	public void setIdEntitaFruitore(String idEntitaFruitore) {
		this.idEntitaFruitore = idEntitaFruitore;
	}
//	public String getTarga() {
//		return targa;
//	}
//	public void setTarga(String targa) {
//		this.targa = targa;
//	}
//	public String getCodiceFiscale() {
//		return codiceFiscale;
//	}
//	public void setCodiceFiscale(String codiceFiscale) {
//		this.codiceFiscale = codiceFiscale;
//	}

	public String getFruitore() {
		return fruitore;
	}
	public void setFruitore(String fruitore) {
		this.fruitore = fruitore;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getUsr() {
		return usr;
	}
	public void setUsr(String usr) {
		this.usr = usr;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	public String getUtenteApplicativo() {
		return utenteApplicativo;
	}
	public void setUtenteApplicativo(String utenteApplicativo) {
		this.utenteApplicativo = utenteApplicativo;
	}
	public String getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	public String getRepostory() {
		return repostory;
	}
	public void setRepostory(String repostory) {
		this.repostory = repostory;
	}
	public String getDocumentRoot() {
		return documentRoot;
	}
	public void setDocumentRoot(String documentRoot) {
		this.documentRoot = documentRoot;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getIndexType() {
		return indexType;
	}
	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IndexManagementPojo [fruitore=");
		builder.append(fruitore);
		builder.append(", documentRoot=");
		builder.append(documentRoot);
		builder.append(", repostory=");
		builder.append(repostory);
		builder.append(", folder=");
		builder.append(folder);
		builder.append(", nomeFile=");
		builder.append(nomeFile);
		builder.append(", mimeType=");
		builder.append(mimeType);
		builder.append(", file=");
		builder.append(Arrays.toString(file));
		builder.append(", idDocumento=");
		builder.append(idDocumento);
		builder.append(", tipoDocumento=");
		builder.append(tipoDocumento);
		builder.append(", idEntitaFruitore=");
		builder.append(idEntitaFruitore);
		builder.append(", usr=");
		builder.append(usr);
		builder.append(", psw=");
		builder.append(psw);
		builder.append(", utenteApplicativo=");
		builder.append(utenteApplicativo);
		builder.append(", identificativoFornitore=");
		builder.append(identificativoFornitore);
		builder.append(", customModel=");
		builder.append(customModel);
		builder.append(", indexType=");
		builder.append(indexType);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}