/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.common;

import it.csi.conam.conambl.business.service.common.CommonBollettiniService;
import it.csi.conam.conambl.business.service.util.UtilsReport;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.Report;
import it.csi.conam.conambl.jasper.BollettinoJasper;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.PrintException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author riccardo.bova
 * @date 13 feb 2019
 */
@Service
public class CommonBollettiniServiceImpl implements CommonBollettiniService {

	@Autowired
	private UtilsReport utilsReport;

	@Override
	public String generaCodicePosizioneDebitoria(String identificativoFiscale, BigDecimal numRata, String codiceAmbito) {
		String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmm"));
		return  dateString+identificativoFiscale+StringUtils.leftPad(numRata.toString(), 2, "0")+codiceAmbito;
	}

	@Override
	public String generaCodiceMessaggioEpay(Integer id, String codice) {
		String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmm"));
		return dateString+StringUtils.leftPad(id.toString(), 2, "0")+codice;
	}

	@Override
	public String createTextQrCode(String codiceAvviso, String codiceFiscaleEnteCreditore, BigDecimal importo) {
		StringBuilder text = new StringBuilder(Constants.PAGO_PA);
		String sep = Constants.SEPARATORE_QRCODE;
		importo = importo.setScale(2, BigDecimal.ROUND_UP);
		String importoFomatoDatamatrix = StringUtils.leftPad(importo.toString().replace(".", ""), 10, "0");

		text.append(sep).append(Constants.VERSIONE).append(sep).append(codiceAvviso).append(sep).append(codiceFiscaleEnteCreditore).append(sep).append(importoFomatoDatamatrix);
		return text.toString();
	}

	@Override
	public String createTextDataMatrix(String codiceAvviso, String contoCorrentePostale, BigDecimal importo, String codiceFiscaleEnte, String codiceFiscalePivaSoggetto, String denominazioneSoggetto,
			String oggettoPagamento) {
		StringBuilder text = new StringBuilder(Constants.CODFASE);
		String sep = Constants.SEPARATORE_DATAMATRIX;

		importo = importo.setScale(2, BigDecimal.ROUND_UP);
		String importoFomatoDatamatrix = StringUtils.leftPad(importo.toString().replace(".", ""), 10, "0");

		String filler = StringUtils.rightPad("", 12, " ");

		StringBuilder codeline = new StringBuilder();
		codeline.append(Constants.LUNGHEZZA_CODAVVISO).append(codiceAvviso).append(Constants.LUNGHEZZA_CONTO).append(contoCorrentePostale).append(Constants.LUNGHEZZA_IMPORTO).append(importoFomatoDatamatrix)
				.append(Constants.LUNGHEZZA_TIPO_DOCUMENTO).append(Constants.TIPO_DOCUMENTO);

		codiceFiscalePivaSoggetto = StringUtils.rightPad(codiceFiscalePivaSoggetto, 16, " ");
		denominazioneSoggetto = denominazioneSoggetto.length() <= 40 ? StringUtils.rightPad(denominazioneSoggetto, 40, " ") : denominazioneSoggetto.substring(0, 40);
		oggettoPagamento = oggettoPagamento.length() <= 110 ? StringUtils.rightPad(oggettoPagamento, 110, " ") : oggettoPagamento.substring(0, 110);

		text.append(Constants.NBPA).append(sep).append(codeline).append(Constants.ID_DATAMATRIX).append(Constants.FASE_PAGAMENTO).append(codiceFiscaleEnte).append(codiceFiscalePivaSoggetto)
				.append(denominazioneSoggetto).append(oggettoPagamento).append(filler).append(Constants.VALORE_FINALE_DATAMATRIX);

		return text.toString();
	}

	@Override
	public byte[] printBollettini(List<BollettinoJasper> bollettini, Report reportE) {
		byte[] report;
		Map<String, Object> jasperParam = new HashMap<>();
		Map<String, String> subReport = new HashMap<>();

		// compilati da utente
		jasperParam.put("listaBollettiniJasper", new JRBeanCollectionDataSource(bollettini));

		try {
			report = utilsReport.printReportPDF(reportE.getCodiceDB(), jasperParam, subReport);
		} catch (PrintException | IOException | SQLException | JRException e) {
			throw new RuntimeException(e);
		}
		return report;
	}

}
