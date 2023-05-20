/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.util;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.exception.BusinessException;

@XmlType(name = "enumMimeTypeType")
@XmlEnum
public enum StadocMimeType {
	@XmlEnumValue("image/tiff")
	IMAGE_TIFF("image/tiff"), @XmlEnumValue("image/x-tiff")
	IMAGE_X_TIFF("image/x-tiff"), @XmlEnumValue("image/jpeg")
	IMAGE_JPEG("image/jpeg"), @XmlEnumValue("image/pjpeg")
	IMAGE_PJPEG("image/pjpeg"), @XmlEnumValue("application/pdf")
	APPLICATION_PDF("application/pdf"), @XmlEnumValue("text/plain")
	TEXT_PLAIN("text/plain"), @XmlEnumValue("application/x-compressed")
	APPLICATION_X_COMPRESSED("application/x-compressed"), @XmlEnumValue("application/x-zip-compressed")
	APPLICATION_X_ZIP_COMPRESSED("application/x-zip-compressed"), @XmlEnumValue("application/zip")
	APPLICATION_ZIP("application/zip"), @XmlEnumValue("multipart/x-zip")
	MULTIPART_X_ZIP("multipart/x-zip"), @XmlEnumValue("application/x-tar")
	APPLICATION_X_TAR("application/x-tar"), @XmlEnumValue("application/gnutar")
	APPLICATION_GNUTAR("application/gnutar"), @XmlEnumValue("application/pkcs7-mime")
	APPLICATION_PKCS_7_MIME("application/pkcs7-mime"), @XmlEnumValue("application/timestamp-reply")
	APPLICATION_TIMESTAMP_REPLY("application/timestamp-reply"), @XmlEnumValue("multipart/mixed")
	MULTIPART_MIXED("multipart/mixed"), @XmlEnumValue("application/msword")
	APPLICATION_MSWORD("application/msword"), @XmlEnumValue("application/rtf")
	APPLICATION_RTF("application/rtf"), @XmlEnumValue("application/x-rtf")
	APPLICATION_X_RTF("application/x-rtf"), @XmlEnumValue("text/richtext")
	TEXT_RICHTEXT("text/richtext"), @XmlEnumValue("application/excel")
	APPLICATION_EXCEL("application/excel"), @XmlEnumValue("application/vnd.ms-excel")
	APPLICATION_VND_MS_EXCEL("application/vnd.ms-excel"), @XmlEnumValue("application/x-excel")
	APPLICATION_X_EXCEL("application/x-excel"), @XmlEnumValue("application/x-msexcel")
	APPLICATION_X_MSEXCEL("application/x-msexcel"), @XmlEnumValue("application/mspowerpoint")
	APPLICATION_MSPOWERPOINT("application/mspowerpoint"), @XmlEnumValue("application/powerpoint")
	APPLICATION_POWERPOINT("application/powerpoint"), @XmlEnumValue("application/vnd.ms-powerpoint")
	APPLICATION_VND_MS_POWERPOINT("application/vnd.ms-powerpoint"), @XmlEnumValue("application/x-mspowerpoint")
	APPLICATION_X_MSPOWERPOINT("application/x-mspowerpoint"), @XmlEnumValue("application/vnd.oasis.opendocument.chart")
	APPLICATION_VND_OASIS_OPENDOCUMENT_CHART("application/vnd.oasis.opendocument.chart"), @XmlEnumValue("application/vnd.oasis.opendocument.graphics")
	APPLICATION_VND_OASIS_OPENDOCUMENT_GRAPHICS("application/vnd.oasis.opendocument.graphics"), @XmlEnumValue("application/vnd.oasis.opendocument.image")
	APPLICATION_VND_OASIS_OPENDOCUMENT_IMAGE("application/vnd.oasis.opendocument.image"), @XmlEnumValue("application/vnd.oasis.opendocument.presentation")
	APPLICATION_VND_OASIS_OPENDOCUMENT_PRESENTATION("application/vnd.oasis.opendocument.presentation"), @XmlEnumValue("application/vnd.oasis.opendocument.spreadsheet")
	APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET("application/vnd.oasis.opendocument.spreadsheet"), @XmlEnumValue("application/vnd.oasis.opendocument.text")
	APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT("application/vnd.oasis.opendocument.text"), @XmlEnumValue("application/xml")
	APPLICATION_XML("application/xml"), @XmlEnumValue("application/xsl")
	APPLICATION_XSL("application/xsl"), @XmlEnumValue("application/timestamped-data")
	APPLICATION_TIMESTAMPED_DATA("application/timestamped-data"), @XmlEnumValue("text/html")
	TEXT_HTML("text/html"), @XmlEnumValue("message/rfc822")
	ELECTRONIC_MAIL("message/rfc822"), @XmlEnumValue("image/png")
	IMAGE_PNG("image/png"), @XmlEnumValue("text/xml")
	TEXT_XML("text/xml");
	// 20210603_LC Jira-146 aggiunto text/xml

	private final String value;

	StadocMimeType(String v) {
		this.value = v;
	}

	public String value() {
		return this.value;
	}

	public static StadocMimeType fromValue(String v) {
		StadocMimeType[] var1 = values();
		int var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			StadocMimeType c = var1[var3];
			if (c.value.equals(v)) {
				return c;
			}
		}
		
		// 20210603_LC Jira-146 se non trova il mimetype torna msg errore
		throw new BusinessException(ErrorCode.DOQUI_MIMETYPE_NON_CORRETTO);
		//throw new IllegalArgumentException(v);
	}
}
