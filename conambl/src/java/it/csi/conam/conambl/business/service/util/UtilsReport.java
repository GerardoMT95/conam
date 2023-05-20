/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.util;

import net.sf.jasperreports.engine.JRException;

import javax.print.PrintException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author riccardo.bova
 * @date 21 gen 2019
 */
public interface UtilsReport {

	byte[] printReportPDF(String codReport, Map<String, Object> jasperParam, Map<String, String> mapSubReport) throws PrintException, IOException, SQLException, JRException;

}
