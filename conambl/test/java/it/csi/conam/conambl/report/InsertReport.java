/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.report;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.ImageReport;
import it.csi.conam.conambl.common.Report;
import it.csi.conam.conambl.integration.entity.custom.CnmCImmagine;
import it.csi.conam.conambl.integration.entity.custom.CnmCReport;
import it.csi.conam.conambl.integration.repositories.CnmCImmagineRepository;
import it.csi.conam.conambl.integration.repositories.CnmCReportRepository;
import it.csi.conam.conambl.testbase.RestJunitClassRunner;
import it.csi.conam.conambl.testbase.TestBaseService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 29 mag 2018
 */
@RunWith(RestJunitClassRunner.class)
public class InsertReport extends TestBaseService {

	protected static Logger logger = Logger.getLogger(InsertReport.class);

	@Autowired
	private CnmCReportRepository cnmCReportRepository;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmCImmagineRepository cnmCImmagineRepository;

	@Test
	public void testInsertReport() throws IOException {
		try {
			Path currentRelativePath = Paths.get("");
			String s = currentRelativePath.toAbsolutePath().toString();
			File f = new File(s, "docs" + File.separator + "report");

			for (final File fileEntry : f.listFiles()) {
				if (fileEntry.isFile()) {
					Report e = Report.getByFileName(fileEntry.getName());
					if (e != null) {
						insertReport(e, fileEntry);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}

	}

	@Test
	public void testInsertImage() throws IOException {
		try {
			Path currentRelativePath = Paths.get("");
			String s = currentRelativePath.toAbsolutePath().toString();
			File f = new File(s, "docs" + File.separator + "report" + File.separator + "img");

			for (final File fileEntry : f.listFiles()) {
				if (fileEntry.isFile()) {
					ImageReport e = ImageReport.getByFileName(fileEntry.getName());
					if (e != null) {
						insertImage(e, fileEntry);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}

	}

	public void insertReport(Report e, File fileEntry) throws IOException {
		CnmCReport report = cnmCReportRepository.findByCodReport(e.getCodiceDB());
		if (report == null) {
			report = new CnmCReport();
			long id = cnmCReportRepository.count() + 1;
			report.setIdReport(id);
			report.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
			report.setCodReport(e.getCodiceDB());
		} else {
			report.setDataOraUpdate(new Timestamp(new Date().getTime()));
		}

		BufferedReader br = new BufferedReader(new FileReader(fileEntry));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}

		String everything = sb.toString();
		report.setJrxml(everything);
		report.setJasper(null);
		report.setDescReport(e.getDescrizione());

		List<String> codImmagini = new ArrayList<>();
		if (e.getImage() != null) {
			for (ImageReport img : e.getImage()) {
				codImmagini.add(img.getCodiceDB());
			}
			if (!codImmagini.isEmpty())
				report.setCnmCImmagines(cnmCImmagineRepository.findByCodImmagineIn(codImmagini));
		}

		cnmCReportRepository.save(report);
		br.close();
	}

	public void insertImage(ImageReport e, File fileEntry) throws IOException {
		CnmCImmagine cnmCImmagine = cnmCImmagineRepository.findByCodImmagine(e.getCodiceDB());
		if (cnmCImmagine == null) {
			cnmCImmagine = new CnmCImmagine();
			long id = cnmCImmagineRepository.count() + 1;
			cnmCImmagine.setIdImmagine(id);
			cnmCImmagine.setCodImmagine(e.getCodiceDB());
			cnmCImmagine.setDescImmagine(e.getDescrizione());
		}

		BufferedImage image = ImageIO.read(fileEntry);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);

		cnmCImmagine.setImmagine(baos.toByteArray());
		cnmCImmagine = cnmCImmagineRepository.save(cnmCImmagine);

		baos.flush();
		baos.close();
	}
}
