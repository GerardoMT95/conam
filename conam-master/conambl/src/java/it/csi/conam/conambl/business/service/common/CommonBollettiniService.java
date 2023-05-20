/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.common;

import it.csi.conam.conambl.common.Report;
import it.csi.conam.conambl.jasper.BollettinoJasper;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 13 feb 2019
 */
public interface CommonBollettiniService {

	String createTextDataMatrix(String codiceAvviso, String contoCorrentePostale, BigDecimal importo, String codiceFiscaleEnte, String codiceFiscalePivaSoggetto, String denominazioneSoggetto,
			String oggettoPagamento);

	String createTextQrCode(String codiceAvviso, String codiceFiscaleEnteCreditore, BigDecimal importo);

	String generaCodicePosizioneDebitoria(String identificativoFiscale, BigDecimal numRata, String codiceAmbito);

	String generaCodiceMessaggioEpay(Integer id, String codice);

	byte[] printBollettini(List<BollettinoJasper> bollettini, Report reportE);

}
