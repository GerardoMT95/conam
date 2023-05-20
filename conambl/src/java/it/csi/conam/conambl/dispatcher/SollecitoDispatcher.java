/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.response.RiconciliaSollecitoResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface SollecitoDispatcher {

	@PreAuthorize(value = AuthorizationRoles.SOLLECITO_PAGAMENTO)
	List<SollecitoVO> getSollecitiByIdOrdinanzaSoggetto(Integer idOrdinanzaVerbaleSoggetto);

	@PreAuthorize(value = AuthorizationRoles.SOLLECITO_PAGAMENTO)
	Integer salvaSollecito(SollecitoVO sollecitoVO, NotificaVO notificaVO, UserDetails user);

	@PreAuthorize(value = AuthorizationRoles.SOLLECITO_PAGAMENTO)
	void eliminaSollecito(Integer idSollecito);

	@PreAuthorize(value = AuthorizationRoles.SOLLECITO_PAGAMENTO)
	void inviaRichiestaBollettiniByIdSollecito(Integer idSollecito);

	// 20200825_LC nuovo type per doc multiplo
	@PreAuthorize(value = AuthorizationRoles.SOLLECITO_PAGAMENTO)
	List<DocumentoScaricatoVO> dowloadBollettiniSollecito(Integer idSollecito);

	// 20200825_LC nuovo type per doc multiplo
	@PreAuthorize(value = AuthorizationRoles.SOLLECITO_PAGAMENTO)
	List<DocumentoScaricatoVO> dowloadLettera(Integer idSollecito);

	@PreAuthorize(value = AuthorizationRoles.SOLLECITO_PAGAMENTO)
	SollecitoVO getSollecitoById(Integer id);

	@PreAuthorize(value = AuthorizationRoles.SOLLECITO_PAGAMENTO)
	RiconciliaSollecitoResponse riconcilaSollecito(SollecitoVO sollecito, UserDetails user);

	@PreAuthorize(value = AuthorizationRoles.SOLLECITO_PAGAMENTO)
	IsCreatedVO isLetteraSollecitoSaved(Integer idSollecito);

	@PreAuthorize(value = AuthorizationRoles.SOLLECITO_PAGAMENTO)
	Integer salvaSollecitoRate(SollecitoVO sollecitoVO, NotificaVO notificaVO, Integer idPianoRateizzazione, UserDetails user);
	
	@PreAuthorize(value = AuthorizationRoles.SOLLECITO_PAGAMENTO)
	List<SollecitoVO> getSollecitiByIdPianoRateizzazione(Integer idPianoRateizzazione);
}
