/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.jasper;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author riccardo.bova
 * @date 24 gen 2019
 */
public class BollettinoJasper implements Serializable {

	private static final long serialVersionUID = -6463544503363887987L;

	private String denominazioneSoggetto;

	private BigDecimal numRata1;
	private BigDecimal importoRata1;
	private LocalDate dataScadenzaRata1;
	private String codiceAvvisoRata1;

	private BigDecimal numRata2;
	private BigDecimal importoRata2;
	private LocalDate dataScadenzaRata2;
	private String codiceAvvisoRata2;

	// COMUNI
	private String oggettoPagamento;
	private String cfEnteCreditore;

	// BANCA
	private String enteCreditore;
	private String cbill;

	// BOLLETTINO POSTALE
	private String numeroContoPostale;
	private String intestatarioContoCorrentePostale;
	private String autorizzazione;

	// QRCODE DATAMATRIX
	private BufferedImage qrcode1;
	private BufferedImage qrcode2;
	private BufferedImage dataMatrix1;
	private BufferedImage dataMatrix2;

	private String infoEnte;
	private String settoreEnte;
	private String cfEnteDebitore;
	private String indirizzoEnteDebitore;
	private String comuneEnteDebitore;

	public String getDenominazioneSoggetto() {
		return denominazioneSoggetto;
	}

	public BigDecimal getImportoRata1() {
		return importoRata1;
	}

	public LocalDate getDataScadenzaRata1() {
		return dataScadenzaRata1;
	}

	public String getCodiceAvvisoRata1() {
		return codiceAvvisoRata1;
	}

	public BigDecimal getImportoRata2() {
		return importoRata2;
	}

	public LocalDate getDataScadenzaRata2() {
		return dataScadenzaRata2;
	}

	public String getCodiceAvvisoRata2() {
		return codiceAvvisoRata2;
	}

	public String getOggettoPagamento() {
		return oggettoPagamento;
	}

	public String getCfEnteCreditore() {
		return cfEnteCreditore;
	}

	public String getEnteCreditore() {
		return enteCreditore;
	}

	public String getCbill() {
		return cbill;
	}

	public String getNumeroContoPostale() {
		return numeroContoPostale;
	}

	public String getIntestatarioContoCorrentePostale() {
		return intestatarioContoCorrentePostale;
	}

	public String getAutorizzazione() {
		return autorizzazione;
	}

	public void setDenominazioneSoggetto(String denominazioneSoggetto) {
		this.denominazioneSoggetto = denominazioneSoggetto;
	}

	public void setImportoRata1(BigDecimal importoRata1) {
		this.importoRata1 = importoRata1;
	}

	public void setDataScadenzaRata1(LocalDate dataScadenzaRata1) {
		this.dataScadenzaRata1 = dataScadenzaRata1;
	}

	public void setCodiceAvvisoRata1(String codiceAvvisoRata1) {
		this.codiceAvvisoRata1 = codiceAvvisoRata1;
	}

	public void setImportoRata2(BigDecimal importoRata2) {
		this.importoRata2 = importoRata2;
	}

	public void setDataScadenzaRata2(LocalDate dataScadenzaRata2) {
		this.dataScadenzaRata2 = dataScadenzaRata2;
	}

	public void setCodiceAvvisoRata2(String codiceAvvisoRata2) {
		this.codiceAvvisoRata2 = codiceAvvisoRata2;
	}

	public void setOggettoPagamento(String oggettoPagamento) {
		this.oggettoPagamento = oggettoPagamento;
	}

	public void setCfEnteCreditore(String cfEnteCreditore) {
		this.cfEnteCreditore = cfEnteCreditore;
	}

	public void setEnteCreditore(String enteCreditore) {
		this.enteCreditore = enteCreditore;
	}

	public void setCbill(String cbill) {
		this.cbill = cbill;
	}

	public void setNumeroContoPostale(String numeroContoPostale) {
		this.numeroContoPostale = numeroContoPostale;
	}

	public void setIntestatarioContoCorrentePostale(String intestatarioContoCorrentePostale) {
		this.intestatarioContoCorrentePostale = intestatarioContoCorrentePostale;
	}

	public void setAutorizzazione(String autorizzazione) {
		this.autorizzazione = autorizzazione;
	}

	public BigDecimal getNumRata1() {
		return numRata1;
	}

	public BigDecimal getNumRata2() {
		return numRata2;
	}

	public void setNumRata1(BigDecimal numRata1) {
		this.numRata1 = numRata1;
	}

	public void setNumRata2(BigDecimal numRata2) {
		this.numRata2 = numRata2;
	}

	public BufferedImage getQrcode1() {
		return qrcode1;
	}

	public BufferedImage getQrcode2() {
		return qrcode2;
	}

	public BufferedImage getDataMatrix1() {
		return dataMatrix1;
	}

	public BufferedImage getDataMatrix2() {
		return dataMatrix2;
	}

	public void setQrcode1(BufferedImage qrcode1) {
		this.qrcode1 = qrcode1;
	}

	public void setQrcode2(BufferedImage qrcode2) {
		this.qrcode2 = qrcode2;
	}

	public void setDataMatrix1(BufferedImage dataMatrix1) {
		this.dataMatrix1 = dataMatrix1;
	}

	public void setDataMatrix2(BufferedImage dataMatrix2) {
		this.dataMatrix2 = dataMatrix2;
	}

	public String getInfoEnte() {
		return infoEnte;
	}

	public String getSettoreEnte() {
		return settoreEnte;
	}

	public String getCfEnteDebitore() {
		return cfEnteDebitore;
	}

	public String getIndirizzoEnteDebitore() {
		return indirizzoEnteDebitore;
	}

	public void setInfoEnte(String infoEnte) {
		this.infoEnte = infoEnte;
	}

	public void setSettoreEnte(String settoreEnte) {
		this.settoreEnte = settoreEnte;
	}

	public void setCfEnteDebitore(String cfEnteDebitore) {
		this.cfEnteDebitore = cfEnteDebitore;
	}

	public void setIndirizzoEnteDebitore(String indirizzoEnteDebitore) {
		this.indirizzoEnteDebitore = indirizzoEnteDebitore;
	}

	public String getComuneEnteDebitore() {
		return comuneEnteDebitore;
	}

	public void setComuneEnteDebitore(String comuneEnteDebitore) {
		this.comuneEnteDebitore = comuneEnteDebitore;
	}

}
