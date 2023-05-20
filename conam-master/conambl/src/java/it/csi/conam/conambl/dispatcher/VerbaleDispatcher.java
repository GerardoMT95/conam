/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.request.verbale.RicercaVerbaleRequest;
import it.csi.conam.conambl.request.verbale.SalvaAzioneRequest;
import it.csi.conam.conambl.request.verbale.SalvaStatoManualeRequest;
import it.csi.conam.conambl.request.verbale.SetRecidivoVerbaleSoggettoRequest;
import it.csi.conam.conambl.response.AzioneVerbaleResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.IstruttoreVO;
import it.csi.conam.conambl.vo.UtenteVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.verbale.*;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.RiepilogoAllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 23 mar 2018
 */
public interface VerbaleDispatcher {

	@PreAuthorize(value = AuthorizationRoles.CREAZIONE_VERBALE_SOGGETTI)
	Integer salvaVerbale(VerbaleVO verbale, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.CREAZIONE_VERBALE_SOGGETTI)
	void eliminaVerbale(Integer id, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.CREAZIONE_VERBALE_SOGGETTI)
	VerbaleVO getVerbaleById(Integer id, UserDetails userDetails, boolean includeEliminati);

	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	RiepilogoVerbaleVO riepilogo(Integer id, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	RiepilogoVerbaleVO riepilogoVerbaleAudizione(Integer id, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	List<SoggettoVO> getSoggettiByIdVerbale(Integer id, UserDetails userDetails, Boolean controlloUtente);
	
	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	List<SoggettoVO> getSoggettiByIdVerbaleConvocazione(Integer id, UserDetails userDetails, Boolean controlloUtente);

	@PreAuthorize(value = AuthorizationRoles.EMISSIONE_VERBALE_AUDIZIONE)
	List<SoggettoVO> getSoggettiByIdVerbaleAudizione(Integer id);

	@PreAuthorize(value = AuthorizationRoles.CREAZIONE_VERBALE_SOGGETTI)
	SoggettoVO salvaSoggetto(Integer id, SoggettoVO soggetto, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.CREAZIONE_VERBALE_SOGGETTI)
	SoggettoVO ricercaSoggetto(MinSoggettoVO minSoggettoVO, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.CREAZIONE_VERBALE_SOGGETTI)
	SoggettoVO ricercaSoggettoPerPIva(MinSoggettoVO minSoggettoVO, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.CREAZIONE_VERBALE_SOGGETTI)
	void eliminaSoggettoByIdVerbaleSoggetto(Integer id, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<RuoloSoggettoVO> getRuoliSoggetto();

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<StatoVerbaleVO> getStatiRicercaVerbale();
	
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<StatoManualeVO> getStatiManuali();

	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	List<MinVerbaleVO> ricercaVerbale(RicercaVerbaleRequest request, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	RiepilogoAllegatoVO getAllegatiByIdVerbale(Integer id, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliVerbale(Integer id, String tipoDocumento, boolean aggiungiCategoriaEmail);

	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	List<TipoAllegatoVO> getTipologiaAllegatiVerbale(String tipoDocumento);

	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	List<IstruttoreVO> getIstruttoreByVerbale(Integer idEnte, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	UtenteVO getUtenteRuolo(Integer idVerbale, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	AzioneVerbaleResponse getAzioniVerbale(Integer id, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_ALLEGATI_VERBALE)
	AllegatoVO salvaAllegato(List<InputPart> data, List<InputPart> file, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	void salvaAzioneVerbale(SalvaAzioneRequest salvaAzione, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_ALLEGATI_VERBALE)
	void eliminaAllegato(Integer idVerbale, Integer idAllegato, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI + " OR " + AuthorizationRoles.EMISSIONE_VERBALE_AUDIZIONE + " OR " + AuthorizationRoles.RICONCILIAZIONE_MANUALE_VERBALE)
	Boolean isTipoAllegatoAllegabile(Integer id, String codiceDocumento, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI + " OR " + AuthorizationRoles.EMISSIONE_VERBALE_AUDIZIONE + " OR " + AuthorizationRoles.RICONCILIAZIONE_MANUALE_VERBALE)
	Boolean isEnableCaricaVerbaleAudizione(Integer id, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_ALLEGATI_VERBALE)
	AllegatoVO salvaAllegatiMultipli(List<InputPart> data, List<InputPart> file, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_ALLEGATI_VERBALE)
	IsCreatedVO isConvocazioneCreated(IsCreatedVO request);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_ALLEGATI_VERBALE)
	IsCreatedVO isVerbaleAudizioneCreated(IsCreatedVO request);
	
	// 20200706_LC	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_ALLEGATI_VERBALE)
	MessageVO  salvaAllegatoProtocollatoVerbale(SalvaAllegatiProtocollatiRequest request, UserDetails userDetails);

	
	// 20201223_LC JIRA 118
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_ALLEGATI_VERBALE)
	VerbaleVO getVerbaleByIdOrdinanza(Integer idOrdinanza);
	

	//Gestione Stato manuale

	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	void salvaStatoManuale(SalvaStatoManualeRequest salvaStatoManuale, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	MessageVO getMessaggioManualeByIdOrdinanza(Integer idOrdinanza);

	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	MessageVO getMessaggioManualeByIdVerbale(Integer idVerbale);
	
	@PreAuthorize(value = AuthorizationRoles.CONSULTAZIONE_VARIAZIONE_VERBALI)
	MessageVO getMessaggioManualeByIdOrdinanzaVerbaleSoggetto(Integer IdOrdinanzaVerbaleSoggetto);
	
	//FINE - Gestione Stato manuale

	/*LUCIO 2021/04/21 - Gestione casi di recidività*/
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_ALLEGATI_VERBALE)
	List<VerbaleSoggettoVO> getVerbaleSoggettoByIdSoggetto(Integer idSoggetto);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_ALLEGATI_VERBALE)
	VerbaleSoggettoVORaggruppatoPerSoggetto getVerbaleSoggettoRaggruppatoPerSoggettoByIdSoggetto(
		Integer idSoggetto,
		Boolean recidivo
	);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_ALLEGATI_VERBALE)
	List<VerbaleSoggettoVO> setRecidivoVerbaleSoggetto(
		List<SetRecidivoVerbaleSoggettoRequest> setRecidivoVerbaleSoggettoRequest,
		UserDetails userDetails
	);
	/*LUCIO 2021/04/21 - FINE Gestione casi di recidività*/
	
}
