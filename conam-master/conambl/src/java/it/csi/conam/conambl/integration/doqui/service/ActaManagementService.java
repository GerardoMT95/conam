/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.service;

import it.csi.conam.conambl.integration.doqui.bean.*;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;

import java.util.List;

public interface ActaManagementService {
	
	
	public void init();
	
	
	public KeyDocumentoActa archiviaDocumentoLogico(DocumentoActa documentoActa, UtenteActa utenteActa) throws IntegrationException;
	
	public KeyDocumentoActa protocollaDocumentoLogico(DocumentoActa documentoActa, UtenteActa utenteActa) throws IntegrationException;
	
	public KeyDocumentoActa archiviaDocumentoFisico(DocumentoElettronicoActa documentoElettronicoActa, UtenteActa utenteActa) throws IntegrationException;
	
	public KeyDocumentoActa protocollaDocumentoFisico(DocumentoElettronicoActa documentoElettronicoActa, UtenteActa utenteActa,boolean isProtocollazioneInUscitaSenzaDocumento, String parolaChiaveFolderTemp) throws IntegrationException;
	
	public KeyDocumentoActa protocollaDocumentoFisicoEsistente(DocumentoElettronicoActa documentoElettronicoActa, UtenteActa utenteActa,boolean isProtocollazioneInUscitaSenzaDocumento, String objectIdClassificazioneEsistente, String objectIdDocumentoEsistente) throws IntegrationException;
	
	public List<DocumentoElettronicoActa> ricercaDocumentoByParolaChiave(String parolaChiave, UtenteActa utenteActa) throws IntegrationException;	// 20200717_LC
	
	public void associaDocumentoFisico(DocumentoElettronicoActa documentoActa, UtenteActa utenteActa) throws IntegrationException;
	
	public KeyDocumentoActa aggiungiAllegato(DocumentoElettronicoActa documentoElettronicoActa, UtenteActa utenteActa, String objectIdClassificazionePadre, String numeroProtocolloPadre, String pkAllegato) throws IntegrationException;
	
	public List<DocumentoProtocollatoActa>  ricercaDocumentoProtocollato(String numProtocollo, UtenteActa utenteActa) throws IntegrationException;
	
	// 20200626_LC
	public  KeyDocumentoActa spostaDocumento(DocumentoElettronicoActa documentoElettronicoActa, String numeroProtocollo, UtenteActa utenteActa, String parolaChiaveFolderTemp) throws IntegrationException;

	// 20200709_LC
	public void aggiornaPropertiesActaPostSpostamento(String documentoId, String newParolaChiave, String newOggetto, UtenteActa utenteActa) throws IntegrationException;

	// 20200717_LC
	public List<DocumentoElettronicoActa> ricercaDocumentoByObjectIdDocumento(String objectIdDocumento, UtenteActa utenteActa) throws IntegrationException;

	// 20200825_LC
	public List<DocumentoElettronicoActa> ricercaDocumentoByObjectIdDocumentoFisico(String objectIdDocumentoFisico, UtenteActa utenteActa) throws IntegrationException;
	
	
	// 20200728_LC
	public  KeyDocumentoActa copiaDocumento(DocumentoElettronicoActa documentoElettronicoActa, String numeroProtocollo, UtenteActa utenteActa, String parolaChiaveFolderTemp) throws IntegrationException;

	// 20200804_LC
	public String getParolaChiaveByObjectIdDocumento(String objectIdDocumento, UtenteActa utenteActa) throws IntegrationException;
} 
