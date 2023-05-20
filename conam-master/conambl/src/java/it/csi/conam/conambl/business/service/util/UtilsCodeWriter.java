/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.util;

import java.awt.image.BufferedImage;

public interface UtilsCodeWriter {

	byte[] generateCode128Image(String text, int width, int height);

	byte[] generateDataMatrixImage(String text, int width, int height);

	byte[] generateQRCodeImage(String text, int width, int height);

	BufferedImage convertCodeToBufferImage(byte[] code);

}
