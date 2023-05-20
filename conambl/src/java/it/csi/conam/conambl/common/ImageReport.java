/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author riccardo.bova
 * @date 03 mag 2018
 */
public enum ImageReport {
	IMAGE_BOLLETTINO_BANCARIO_2X_MEZZO("BBANCM2XM", "Bollettino-Bancario-2x-mezzo.png", "Bollettino Bancario 2x - mezzo", "bollettinoBancario2xMezzo"), //
	// IMAGE_BOLLETTINO_BANCARIO_2X("BBANCM2X", "Bollettino-Bancario-2x.png", "Bollettino Bancario 2x"), //
	IMAGE_BOLLETTINO_INTESTAZIONE("BINT", "Bollettino-Intestazione.png", "Bollettino Intestazione", "bollettinoIntestazione"), //
	IMAGE_BOLLETTINO_POSTALE("BPOST", "Bollettino-Postale-1x.png", "Bollettino Postale-1x", "bollettinoPostale"), //
	IMAGE_BOLLETTINO_SOLLECITO("BSOLL", "Bollettino-Sollecito.png", "Bollettino Sollecito", "bollettinoSollecito");

	private String codiceDB;
	private String fileName;
	private String descrizione;
	private String parameterNameJasper;

	ImageReport(String codiceDB, String fileName, String descrizione, String parameterNameJasper) {
		this.codiceDB = codiceDB;
		this.fileName = fileName;
		this.descrizione = descrizione;
		this.parameterNameJasper = parameterNameJasper;
	}

	static public ImageReport getByCodiceDB(String codiceDB) {
		if (null == codiceDB)
			throw new IllegalArgumentException("codiceDB null");
		for (ImageReport e : ImageReport.values()) {
			if (e.codiceDB.equals(codiceDB))
				return e;
		}
		return null;
	}

	public static ImageReport getByFileName(String name) {
		if (null == name)
			throw new IllegalArgumentException("name null");
		for (ImageReport e : ImageReport.values()) {
			if (e.fileName.equals(name))
				return e;
		}
		return null;
	}

	public String getCodiceDB() {
		return codiceDB;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	public String getParameterNameJasper() {
		return parameterNameJasper;
	}

}
