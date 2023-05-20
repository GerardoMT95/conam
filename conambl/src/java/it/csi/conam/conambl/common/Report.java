/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 03 mag 2018
 */
public enum Report {
	REPORT_VERBALE_AUDIZIONE(2, "VERBAU", "Template-02-Verbale-Audizione.jrxml", "Verbale audizione", null), //
	REPORT_LETTERA_ACCOMPAGNAMENTO_INGIUNZIONE(3, "LETING", "Template-03-Lettera-Accompagnamento-Ingiunzione.jrxml", "Lettera di accompagnamento - ingiunzione", null), //
	REPORT_LETTERA_RATEIZZAZIONE(4, "LETRAT", "Template-04-Rateizzazione.jrxml", "Lettera del piano di rateizzazione", null), //
	REPORT_LETTERA_SOLLECITO(5, "LETSOL", "Template-05-Sollecito-Pagamento.jrxml", "Lettera del sollecito pagamento", null), //
	REPORT_CONVOCAZIONE_AUDIZIONE(6, "CONVAU", "Template-06-Convocazione-Audizione.jrxml", "convocazione audizione", null), //
	REPORT_LETTERA_ACCOMPAGNAMENTO_ANNULLAMENTO(9, "LETANN", "Template-09-Lettera-Accompagnamento-Annullamento.jrxml", "Lettera di accompagnamento - annullamento", null), //

	REPORT_LETTERA_ACCOMPAGNAMENTO_ARCHIVIAZIONE(10, "LETARC", "Template-10-Lettera-Accompagnamento-Archiviazione.jrxml", "Lettera di accompagnamento - archiviazione", null), //
	REPORT_LETTERA_SOLLECITO_RATE(11, "LETSOLRAT", "Template-11-Sollecito-Pagamento-Rate.jrxml", "Lettera sollecito rate", null), //

	REPORT_STAMPA_BOLLETTINI_RATEIZZAZIONE(7, "BOLRAT", "Template-Bollettino-Rateizzazione.jrxml", "Bollettini da stampare", Arrays.asList(ImageReport.IMAGE_BOLLETTINO_INTESTAZIONE, //
			ImageReport.IMAGE_BOLLETTINO_POSTALE, //
			ImageReport.IMAGE_BOLLETTINO_BANCARIO_2X_MEZZO)), //

	REPORT_STAMPA_BOLLETTINO_ORDINANZA_SOLLECITO(8, "BOLORDSOL", "Template-Bollettino-Ordinanza-Sollecito.jrxml", "Bollettini Ordinanza Sollecito da stampare",
			Collections.singletonList(ImageReport.IMAGE_BOLLETTINO_SOLLECITO)); //

	
	
	
	private Integer codiceFrontend;
	private String codiceDB;
	private String fileName;
	private String descrizione;
	private List<ImageReport> image;

	Report(Integer codiceFrontend, String codiceDB, String fileName, String descrizione, List<ImageReport> image) {
		this.codiceFrontend = codiceFrontend;
		this.codiceDB = codiceDB;
		this.fileName = fileName;
		this.descrizione = descrizione;
		this.image = image;
	}

	static public Report getByCodiceFrontend(Integer codiceFrontend) {
		if (null == codiceFrontend)
			throw new IllegalArgumentException("codiceFrontend null");
		for (Report e : Report.values()) {
			if (e.codiceFrontend.equals(codiceFrontend))
				return e;
		}
		throw new IllegalArgumentException("codice null");
	}

	static public Report getByCodiceDB(String codiceDB) {
		if (null == codiceDB)
			throw new IllegalArgumentException("codiceDB null");
		for (Report e : Report.values()) {
			if (e.codiceDB.equals(codiceDB))
				return e;
		}
		return null;
	}

	public static Report getByFileName(String name) {
		if (null == name)
			throw new IllegalArgumentException("name null");
		for (Report e : Report.values()) {
			if (e.fileName.equals(name))
				return e;
		}
		return null;
	}

	public Integer getCodiceFrontend() {
		return codiceFrontend;
	}

	public String getCodiceDB() {
		return codiceDB;
	}

	public void setCodiceFrontend(Integer codiceFrontend) {
		this.codiceFrontend = codiceFrontend;
	}

	public void setCodiceDB(String codiceDB) {
		this.codiceDB = codiceDB;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<ImageReport> getImage() {
		return image;
	}

	public void setImage(List<ImageReport> image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
