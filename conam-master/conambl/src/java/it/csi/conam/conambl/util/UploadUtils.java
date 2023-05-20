/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.util;

import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.exception.FileSizeException;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import javax.ws.rs.core.MultivaluedMap;

public class UploadUtils {

	public static String getFileName(InputPart inputPart) {
		MultivaluedMap<String, String> headers = inputPart.getHeaders();
		String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");
		for (String name : contentDispositionHeader) {
			if ((name.trim().startsWith("filename"))) {
				String[] tmp = name.split("=");
				return tmp[1].trim().replaceAll("\"", "");
			}
		}
		return "Unknown";
	}

	public static void checkDimensioneAllegato(byte[] file) throws FileSizeException {
		if (file.length > Constants.ALLEGATO_MAX_SIZE)
			throw new FileSizeException("Attenzione la dimensione del file è troppo grossa");
	}

}
