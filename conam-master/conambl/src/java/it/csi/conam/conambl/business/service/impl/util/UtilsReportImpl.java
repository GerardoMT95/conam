/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.util;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsReport;
import it.csi.conam.conambl.common.ImageReport;
import it.csi.conam.conambl.common.Report;
import it.csi.conam.conambl.integration.entity.custom.CnmCImmagine;
import it.csi.conam.conambl.integration.entity.custom.CnmCReport;
import it.csi.conam.conambl.integration.repositories.CnmCImmagineRepository;
import it.csi.conam.conambl.integration.repositories.CnmCReportRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.print.PrintException;
import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author riccardo.bova
 * @date 23 mag 2018
 */
@Service
public class UtilsReportImpl implements UtilsReport {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private CnmCReportRepository cnmCReportRepository;

	@Autowired
	private CnmCImmagineRepository cnmCImmagineRepository;

	@Autowired
	private UtilsDate utilsDate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.csi.conam.conambl.business.service.UtilsReport#printReportPDF(java.lang.String, java.util.Map, java.util.Map)
	 * 
	 * @param codReport codice del report in REPORT
	 * 
	 * @param jasperParam parametri jasper esclusi subreport
	 * 
	 * @param mapSubReport mappa dei subreport contente nome parametro input e codice report db
	 * 
	 * @return The result of the foo operation, usually within 0 and 1000.
	 */
	@Override
	public byte[] printReportPDF(String codReport, Map<String, Object> jasperParam, Map<String, String> mapSubReport) throws PrintException, IOException, SQLException, JRException {

		if (mapSubReport != null) {
			for (Map.Entry<String, String> entry : mapSubReport.entrySet()) {
				jasperParam.put(entry.getKey(), compileAndGetJasperFromDatabase(entry.getValue()));
			}
		}

		Report report = Report.getByCodiceDB(codReport);
		if (report!=null && report.getImage() != null && !report.getImage().isEmpty()) {
			for (ImageReport imageReport : report.getImage()) {
				CnmCImmagine cnmCImmagine = cnmCImmagineRepository.findByCodImmagine(imageReport.getCodiceDB());
				if (cnmCImmagine == null)
					throw new RuntimeException("immagine non trovata sul db");
				InputStream in = new ByteArrayInputStream(cnmCImmagine.getImmagine());
				jasperParam.put(imageReport.getParameterNameJasper(), ImageIO.read(in));
			}
		}

		JasperPrint jasperPrint = printJasper(codReport, jasperParam);

		return JasperExportManager.exportReportToPdf(jasperPrint);

	}

	private JasperPrint printJasper(String codReport, Map<String, Object> jasperParam) throws PrintException, IOException, SQLException, JRException {

		// Datasource per connessione a DB
		Connection connection = dataSource.getConnection();

		JasperReport jasperReport = compileAndGetJasperFromDatabase(codReport);

		// Generazione report da compilato
		return JasperFillManager.fillReport(jasperReport, jasperParam, connection);

	}

	private JasperReport compileAndGetJasperFromDatabase(String codReport) throws PrintException, IOException, SQLException, JRException {

		InputStream bais;
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

		CnmCReport cnmCReport = cnmCReportRepository.findByCodReport(codReport);
		if (null == cnmCReport.getJasper()) {
			bais = compileJRXML(cnmCReport.getJrxml().getBytes());
			cnmCReport.setJasper(IOUtils.toByteArray(bais));
			cnmCReport.setDataOraUpdate(now);
			cnmCReport = cnmCReportRepository.save(cnmCReport);
		}
		bais = new ByteArrayInputStream(cnmCReport.getJasper());

		return (JasperReport) JRLoader.loadObject(bais);
	}

	private ByteArrayInputStream compileJRXML(byte[] template) throws PrintException {
		ByteArrayInputStream result;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(template);
			JasperCompileManager.compileReportToStream(bais, os);
			result = new ByteArrayInputStream(os.toByteArray());
		} catch (JRException e) {
			throw new PrintException("Errore nella creazione del file jasper dal template jrxml", e);
		}
		return result;
	}

}
