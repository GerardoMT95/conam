/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import it.csi.conam.conambl.business.service.util.UtilsCodeWriter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author riccardo.bova
 * @date 23 mag 2018
 */
@Service
public class UtilsCodeWriterImpl implements UtilsCodeWriter {

	private static final Logger logger = Logger.getLogger(UtilsCodeWriterImpl.class);

	@Override
	public byte[] generateCode128Image(String text, int width, int height) {
		Code128Writer code128Writer = new Code128Writer();
		BitMatrix bitMatrix;
		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		try {
			bitMatrix = code128Writer.encode(text, BarcodeFormat.CODE_128, width, height);
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
		} catch (IOException | WriterException e) {
			logger.error("ERRORE GENERAZIONE CODE 128", e);
		}

		return pngOutputStream.toByteArray();
	}

	@Override
	public byte[] generateDataMatrixImage(String text, int width, int height) {
		DataMatrixWriter dataMatrixWriter = new DataMatrixWriter();

		Map<EncodeHintType, SymbolShapeHint> hints = new HashMap<>();
		hints.put(EncodeHintType.DATA_MATRIX_SHAPE, SymbolShapeHint.FORCE_SQUARE);
		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		try {
			BitMatrix bitMatrix = dataMatrixWriter.encode(text, BarcodeFormat.DATA_MATRIX, width, height, hints);
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
		} catch (IOException e) {
			logger.error("ERRORE GENERAZIONE DATAMATRIX", e);
		}
		return pngOutputStream.toByteArray();
	}

	@Override
	public byte[] generateQRCodeImage(String text, int width, int height) {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		try {
			BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
		} catch (IOException | WriterException e) {
			logger.error("ERRORE GENERAZIONE QRCODE", e);
		}

		return pngOutputStream.toByteArray();
	}

	@Override
	public BufferedImage convertCodeToBufferImage(byte[] code) {
		BufferedImage im = null;
		if (code == null)
			return im;
		try {
			im = ImageIO.read(new ByteArrayInputStream(code));
		} catch (IOException e) {
			logger.error("ERRORE conversione", e);
		}
		return im;
	}

}
