/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.luoghi.NazioneVO;
import it.csi.conam.conambl.vo.luoghi.ProvinciaVO;
import it.csi.conam.conambl.vo.luoghi.RegioneVO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 23 mar 2018
 */
public interface LuoghiDispatcher {

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<RegioneVO> getRegioni();

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<ProvinciaVO> getProviceByIdRegione(Long idRegione);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<ComuneVO> getComuniByIdProvincia(Long idProvincia);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<RegioneVO> getRegioniNascita();

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<ProvinciaVO> getProviceByIdRegioneNascita(Long idRegione);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<ComuneVO> getComuniByIdProvinciaNascita(Long idProvincia);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<NazioneVO> getNazioni();

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<ProvinciaVO> getProviceByIdRegione(Long idRegione, Date dataOraAccertamento);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<ComuneVO> getComuniByIdProvincia(Long idProvincia, Date dataOraAccertamento);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<ComuneVO> getComuniEnte(Date dataOraAccertamento);

}
