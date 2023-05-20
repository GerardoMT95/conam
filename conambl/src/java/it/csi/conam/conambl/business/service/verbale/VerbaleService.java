/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.verbale;

import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import it.csi.conam.conambl.vo.verbale.RiepilogoVerbaleVO;
import it.csi.conam.conambl.vo.verbale.VerbaleSoggettoVO;
import it.csi.conam.conambl.vo.verbale.VerbaleSoggettoVORaggruppatoPerSoggetto;
import it.csi.conam.conambl.vo.verbale.VerbaleVO;

import java.util.List;

public interface VerbaleService {

	Integer salvaVerbale(VerbaleVO verbale, UserDetails userDetails);

	void eliminaVerbale(Integer id, UserDetails userDetails);

	RiepilogoVerbaleVO riepilogo(Integer id, UserDetails userDetails);

	RiepilogoVerbaleVO riepilogoVerbaleAudizione(Integer id, UserDetails userDetails);

	EnteVO getEnteLeggeByCnmTVerbale(CnmTVerbale cnmTVerbale);

	VerbaleVO getVerbaleById(Integer id, UserDetails userDetails, boolean includeEliminati, boolean includiControlloUtenteProprietario, boolean filtraNormeScadute);

	VerbaleVO getVerbaleByIdOrdinanza(Integer idOrdinanza);

	//Gestione Stato manuale
	
	CnmTVerbale salvaStatoManuale(Integer idVerbale,Long idStatoManuale, UserDetails userDetails);
	
	MessageVO getMessaggioManualeByIdOrdinanza(Integer idOrdinanza);
	
	MessageVO getMessaggioManualeByIdVerbale(Integer idVerbale);
	
	MessageVO getMessaggioManualeByIdOrdinanzaVerbaleSoggetto(Integer IdOrdinanzaVerbaleSoggetto);
	
	
	
	//FINE - Gestione Stato manuale
	/*LUCIO 2021/04/21 - Gestione casi di recidività*/
	List<VerbaleSoggettoVO> getVerbaleSoggettoByIdSoggetto(Integer idSoggetto);
	
	VerbaleSoggettoVORaggruppatoPerSoggetto getVerbaleSoggettoRaggruppatoPerSoggettoByIdSoggetto(
		Integer idSoggetto,
		Boolean recidivo
	);
	
	VerbaleSoggettoVO setRecidivoVerbaleSoggetto(
		Integer idVerbaleSoggetto,
		Boolean recidivo,
		UserDetails userDetails
	);
	/*LUCIO 2021/04/21 - FINE Gestione casi di recidività*/

	CnmTVerbale salvaNumeroProtocollo(Integer idVerbale, String numeroProtocollo, CnmTUser cnmTUser);
}
