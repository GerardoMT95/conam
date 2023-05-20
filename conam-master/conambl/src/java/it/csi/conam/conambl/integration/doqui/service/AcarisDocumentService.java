/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.service;


import it.csi.conam.conambl.integration.doqui.bean.DocumentoActa;
import it.csi.conam.conambl.integration.doqui.bean.DocumentoElettronicoActa;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PrincipalIdType;
import it.doqui.acta.actasrv.dto.acaris.type.document.IdentificatoreDocumento;
import it.doqui.acta.actasrv.dto.acaris.type.document.IdentificazioneTrasformazione;
import it.doqui.acta.actasrv.dto.acaris.type.management.VitalRecordCodeType;

public interface AcarisDocumentService 
{	
	public IdentificatoreDocumento creaDocumentoSoloMetadati(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType folderId, VitalRecordCodeType vitalRecordCodeType, Integer idFormaDocumentaria, Integer idStatoDiEfficacia, DocumentoActa documentoActa) throws IntegrationException;
	public IdentificazioneTrasformazione[] trasformaDocumentoPlaceholderInDocumentoElettronico(ObjectIdType repositoryId, PrincipalIdType principalId, DocumentoElettronicoActa documentoElettronicoActa, int idStatoDiEfficacia) throws IntegrationException;
	public IdentificatoreDocumento creaDocumentoElettronico(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType folderId, VitalRecordCodeType vitalRecordCodeType, Integer idStatoDiEfficacia, Integer idFormaDocumentaria, String numeroProtocolloPadre, String pkAllegato, DocumentoElettronicoActa documentoElettronicoActa,boolean isProtocollazioneInUscitaSenzaDocumento) throws IntegrationException;
	
	public void init();
	
}
