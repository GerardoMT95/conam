/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.service;


import it.csi.conam.conambl.integration.doqui.bean.DocumentoActa;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PrincipalIdType;
import it.doqui.acta.actasrv.dto.acaris.type.officialbook.IdentificazioneRegistrazione;

public interface AcarisOfficialBookService 
{	
	public IdentificazioneRegistrazione creaRegistrazioneInArrivoDaDocumentoLogicoEsistente(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType classificazionePartenza, ObjectIdType idStruttura, ObjectIdType idNodo, ObjectIdType idAOO, DocumentoActa documentoActa) throws IntegrationException;
	public IdentificazioneRegistrazione creaRegistrazioneInPartenzaDaDocumentoLogicoEsistente(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType classificazionePartenza, ObjectIdType idStruttura, ObjectIdType idNodo, ObjectIdType idAOO, DocumentoActa documentoActa) throws IntegrationException;
	public IdentificazioneRegistrazione creaRegistrazioneInPartenzaDaDocumentoElettronicoEsistente(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType classificazionePartenza, ObjectIdType idStruttura, ObjectIdType idNodo, ObjectIdType idAOO, DocumentoActa documentoElettronicoActa) throws IntegrationException;
	public IdentificazioneRegistrazione creaRegistrazioneInArrivoDaDocumentoElettronicoEsistente(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType classificazionePartenza, ObjectIdType idStruttura, ObjectIdType idNodo, ObjectIdType idAOO, DocumentoActa documentoElettronicoActa) throws IntegrationException;
	
	public ObjectIdType recuperaIdFascicoloProtocollazioneInEntrataAssociata(ObjectIdType repositoryId, PrincipalIdType principalId,String codiceRegistrazione, String annoRegistrazione, ObjectIdType idAoo) throws IntegrationException;
	
	public void init();
}
