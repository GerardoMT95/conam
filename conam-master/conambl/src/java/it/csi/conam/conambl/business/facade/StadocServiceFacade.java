/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.facade;

import it.csi.conam.conambl.common.annotation.LoggingType;
import it.csi.conam.conambl.common.annotation.NoLogging;
import it.csi.conam.conambl.integration.beans.*;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 09 mag 2018
 */
public interface StadocServiceFacade {

	public static final String TIPOLOGIA_DOC_ACTA_MASTER_INGRESSO_CON_ALLEGATI = "MASTER IN INGRESSO CON ALLEGATI";
	public static final String TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_INGRESSO = "ALLEGATI AL MASTER IN INGRESSO";
	public static final String TIPOLOGIA_DOC_ACTA_MASTER_USCITA_CON_ALLEGATI = "MASTER IN USCITA CON ALLEGATI ";
	public static final String TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_USCITA = "ALLEGATI AL MASTER IN USCITA";
	public static final String TIPOLOGIA_DOC_ACTA_DOC_INGRESSO_SENZA_ALLEGATI = "DOC IN INGRESSO SENZA ALLEGATI";
	public static final String TIPOLOGIA_DOC_ACTA_DOC_USCITA_SENZA_ALLEGATI_GENERERATI = "DOC IN USCITA SENZA ALLEGATI (generati da CONAM)";
	public static final String TIPOLOGIA_DOC_ACTA_DOC_USCITA_SENZA_ALLEGATI_CARICATI = "DOC IN USCITA SENZA ALLEGATI (caricati su CONAM ma in uscita)";

	@NoLogging(request = LoggingType.REQUIRED, response = LoggingType.NONE, measureTime = LoggingType.REQUIRED)
	ResponseRicercaAllegato recuperaDocumentoIndex(String idDocumento);

	ResponseSalvaDocumento salvaDocumentoIndex(String descrizionTipologiaAllegati, byte[] document, String fileName, String idEntitaFruitore);

	// ResponseProtocollaDocumento protocollaDocumentoMock();

	ResponseEliminaDocumento eliminaDocumentoIndex(String idIndex);

	ResponseAggiungiAllegato aggiungiAllegato(byte[] document, String nomeFile, String idArchivioAllegato, String idArchivioPadre, String idEntitaFruitore, String pkAllegato, String soggettoActa,
			String rootActa, long tipoDocumento, String tipoDocActa, List<CnmTSoggetto> cnmTSoggettoList);

	ResponseRicercaDocumento recuperaDocumentoActa(String idDocumento);

	ResponseProtocollaDocumento protocollaDocumentoFisico(String folder, byte[] document, String nomeFile, String idEntitaFruitore, boolean isMaster, boolean isProtocollazioneInUscita,
			String soggettoActa, String rootActa, long tipoDocumento, String tipoDocActa, List<CnmTSoggetto> cnmTSoggettoList, String idIndex);

	ResponseArchiviaDocumento archiviaDocumentoFisico(byte[] file, String nomeFile, String folder, String rootActa, int numeroAllegati, String idEntitaFruitore, long tipoDocumento);

}
