/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.util;

import it.csi.conam.conambl.integration.epay.from.ResponseType;
import it.csi.conam.conambl.integration.epay.from.ResultType;
import org.apache.log4j.Logger;

/**
 * @author riccardo.bova
 * @date 29 gen 2019
 */
public class UtilsEpay {
	
	protected static Logger logger = Logger.getLogger(UtilsEpay.class);

	public static ResponseType getSuccessResult() {
		ResponseType response = new ResponseType();
		ResultType resultType = new ResultType();
		resultType.setCodice("000");
		resultType.setMessaggio("L'invocazione del servizio si è conclusa correttamente");
		response.setResult(resultType);
		logger.info("chiamata da ePay: success");
		return response;
	}

	public static ResponseType getGenericErrorResult() {
		ResponseType response = new ResponseType();
		ResultType resultType = new ResultType();
		resultType.setCodice("001");
		resultType.setMessaggio("Si è verificato un errore generico");
		response.setResult(resultType);
		logger.info("chiamata da ePay: error");
		return response;
	}
}
