/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.request.riscossione.SalvaSollecitoPregressiRequest;
import it.csi.conam.conambl.response.RiconciliaSollecitoResponse;
import it.csi.conam.conambl.response.SalvaSollecitoPregressiResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;
import it.csi.conam.conambl.vo.sollecito.StatoSollecitoVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface SollecitoPregressiDispatcher {

	// modificata
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	SollecitoVO getSollecitoById(Integer id);
	
	// modificata
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<SollecitoVO> getSollecitiByIdOrdinanzaSoggetto(Integer idOrdinanzaVerbaleSoggetto);
	
	// modificata
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	SalvaSollecitoPregressiResponse salvaSollecito(SalvaSollecitoPregressiRequest salvaSollecitoPregressiRequest, UserDetails user);
	
	// nuova
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<StatoSollecitoVO> getStatiSollecito(Integer id);
	
	// nuova
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<SollecitoVO> getSollecitiByOrdinanza(Integer id);

	// nuova
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	SalvaSollecitoPregressiResponse checkSalvaSollecito(SalvaSollecitoPregressiRequest salvaSollecitoPregressiRequest, UserDetails user);
	
	
	
	// as standard

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	void eliminaSollecito(Integer idSollecito);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	void inviaRichiestaBollettiniByIdSollecito(Integer idSollecito);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<DocumentoScaricatoVO> dowloadBollettiniSollecito(Integer idSollecito);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<DocumentoScaricatoVO> dowloadLettera(Integer idSollecito);


	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	RiconciliaSollecitoResponse riconcilaSollecito(SollecitoVO sollecito, UserDetails user);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	IsCreatedVO isLetteraSollecitoSaved(Integer idSollecito);


}
