/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.verbale;

import it.csi.conam.conambl.request.verbale.SalvaAzioneRequest;
import it.csi.conam.conambl.response.AzioneVerbaleResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IstruttoreVO;
import it.csi.conam.conambl.vo.UtenteVO;

import java.util.List;

public interface AzioneVerbaleService {

	void salvaAzioneVerbale(SalvaAzioneRequest salvaAzione, UserDetails UserDetails);

	List<IstruttoreVO> getIstruttoreByVerbale(Integer idVerbale, UserDetails userDetails);

	UtenteVO getUtenteRuolo(Integer idVerbale, UserDetails userDetails);

	AzioneVerbaleResponse getAzioniVerbale(Integer id, UserDetails userDetails);

	Boolean isTipoAllegatoAllegabile(Integer id, String codiceDocumento, UserDetails userDetails);

	Boolean isEnableCaricaVerbaleAudizione(Integer id, UserDetails userDetails);

	boolean controllaPermessiAzione(Long idStatoVerbale, long idProprietarioVerbale, long idUserConnesso, Long idUserAssegnato);

}
