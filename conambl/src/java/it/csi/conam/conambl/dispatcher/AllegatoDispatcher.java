/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.response.RicercaProtocolloSuActaResponse;
import it.csi.conam.conambl.vo.common.SelectVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.ConfigAllegatoVO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 23 mar 2018
 */
public interface AllegatoDispatcher {

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<ConfigAllegatoVO> getConfigAllegati();

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<DocumentoScaricatoVO> downloadAllegatoById(Integer id);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<SelectVO> getDecodificaSelectAllegato(Long decodificaSelect);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<SelectVO> getDecodificaSelectSoggettiAllegato(Integer idverbale);
	
	// 20200903_LC gestione pregresso (nuova resposne)
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	RicercaProtocolloSuActaResponse ricercaProtocolloSuACTA(String numProtocollo, Integer idVerbale, Boolean flagPregresso);

	// 20200717_LC
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<DocumentoScaricatoVO> downloadAllegatoByObjectIdDocumento(String objectIdDocumento);

	// 20200825_LC
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<DocumentoScaricatoVO> downloadAllegatoByObjectIdDocumentoFisico(String objectIdDocumentoFisico);

	// 20200827_LC
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<DocumentoScaricatoVO> getDocFisiciByIdAllegato(Integer id);
	
	// 20200827_LC
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<DocumentoScaricatoVO> getDocFisiciByObjectIdDocumento(String objectIdDocumento);

}
