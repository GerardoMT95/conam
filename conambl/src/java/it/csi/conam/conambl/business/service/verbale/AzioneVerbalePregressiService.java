/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.verbale;

import it.csi.conam.conambl.request.verbale.SalvaAzioneRequest;
import it.csi.conam.conambl.response.AzioneVerbaleResponse;
import it.csi.conam.conambl.response.StatiVerbaleResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IstruttoreVO;
import it.csi.conam.conambl.vo.common.MessageVO;

import java.util.List;

public interface AzioneVerbalePregressiService {

	public StatiVerbaleResponse getStatiVerbalePregresso(Integer id, UserDetails userDetails, Boolean includiControlloUtenteProprietario);
	
	MessageVO salvaStatoVerbalePregresso(SalvaAzioneRequest salvaAzione, UserDetails userDetails);

	boolean controllaPermessiAzione(Long idStatoVerbale, long idProprietarioVerbale, long idUserConnesso,
			Long idUserAssegnato);

	AzioneVerbaleResponse getAzioniVerbale(Integer id, UserDetails userDetails);

	List<IstruttoreVO> getIstruttoreByVerbale(Integer idVerbale, UserDetails userDetails);
}
