/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.request.ordinanza.AzioneOrdinanzaRequest;
import it.csi.conam.conambl.request.ordinanza.RicercaOrdinanzaRequest;
import it.csi.conam.conambl.request.ordinanza.RicercaSoggettiOrdinanzaRequest;
import it.csi.conam.conambl.response.AzioneOrdinanzaResponse;
import it.csi.conam.conambl.response.DatiSentenzaResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.ordinanza.MinOrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.OrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.StatoOrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.StatoSoggettoOrdinanzaVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface OrdinanzaDispatcher {

	@PreAuthorize(value = AuthorizationRoles.RICERCA_ORDINANZA_DETTAGLIO)
	List<MinOrdinanzaVO> ricercaOrdinanza(RicercaOrdinanzaRequest request, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.RICERCA_ORDINANZA_DETTAGLIO)
	OrdinanzaVO dettaglioOrdinanzaById(Integer idOrdinanza, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.RICERCA_ORDINANZA_DETTAGLIO)
	List<SoggettoVO> getSoggettiByIdOrdinanza(Integer id, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.RICERCA_ORDINANZA_DETTAGLIO)
	List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliByOrdinanza(Integer idOrdinanza, String tipoDocumento, boolean aggiungiCategoriaEmail);

	@PreAuthorize(value = AuthorizationRoles.RICERCA_ORDINANZA_DETTAGLIO)
	List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliByOrdinanzaSoggetto(List<Integer> idSoggettoOrdinanza, String tipoDocumento, boolean aggiungiCategoriaEmail);

	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	AllegatoVO salvaAllegatoOrdinanza(List<InputPart> data, List<InputPart> file, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA_SOGGETTO)
	void salvaAllegatoOrdinanzaSoggetto(List<InputPart> data, List<InputPart> file, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA_SOGGETTO)
	MessageVO salvaAllegatoProtocollatoOrdinanzaSoggetto(SalvaAllegatiProtocollatiRequest request, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	AzioneOrdinanzaResponse azioneOrdinanza(AzioneOrdinanzaRequest azioneOrdinanzaRequest, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA_SOGGETTO)
	AzioneOrdinanzaResponse azioneOrdinanzaSoggetto(AzioneOrdinanzaRequest azioneOrdinanzaRequest, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.RICERCA_ORDINANZA_DETTAGLIO)
	OrdinanzaVO dettaglioOrdinanzaByIdOrdinanzaSoggetti(List<Integer> idOrdinanzaSoggetto, UserDetails utente);

	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	List<TipoAllegatoVO> getTipologiaAllegatiCreaOrdinanza();

	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	List<StatoSoggettoOrdinanzaVO> getStatiOrdinanzaSoggettoInCreazioneOrdinanza();

	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	Integer salvaOrdinanza(List<InputPart> data, List<InputPart> file, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.RICERCA_ORDINANZA_DETTAGLIO)
	List<AllegatoVO> getAllegatiByIdOrdinanza(Integer idOrdinanza);
	
	@PreAuthorize(value = AuthorizationRoles.RICERCA_ORDINANZA_DETTAGLIO)
	List<AllegatoVO> getAllegatiByIdOrdinanzaVerbaleSoggetto(List<Integer> idOrdinanzaVerbaleSoggettoList);

	@PreAuthorize(value = AuthorizationRoles.RICERCA_ORDINANZA_DETTAGLIO)
	List<SoggettoVO> ricercaSoggetti(RicercaSoggettiOrdinanzaRequest ricercaSoggettiOrdinanzaRequest, boolean pregressi);

	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	void inviaRichiestaBollettiniByIdOrdinanza(Integer idOrdinanza);

	// 20200825_LC nuovo type per doc multiplo
	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	List<DocumentoScaricatoVO> dowloadBollettiniOrdinanza(Integer idOrdinanza);

	// 20200825_LC nuovo type per doc multiplo
	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	List<DocumentoScaricatoVO> dowloadLettera(Integer idOrdinanza);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<StatoOrdinanzaVO> getStatiOrdinanza();

	@PreAuthorize(value = AuthorizationRoles.RICERCA_ORDINANZA_DETTAGLIO)
	DatiSentenzaResponse getDatiSentenzaByIdOrdinanzaVerbaleSoggetto(Integer idOrdinanzaVerbaleSoggetto);

	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	IsCreatedVO isLetteraSaved(IsCreatedVO request);
	
	// 20200715_LC
	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	MessageVO salvaAllegatoProtocollatoOrdinanza(SalvaAllegatiProtocollatiRequest request, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	List<TipoAllegatoVO> getTipologiaAllegatiCreaOrdinanzaAnnullamento(Integer idOrdinanzaAnnullata);

	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	Integer salvaOrdinanzaAnnullamento(List<InputPart> data, List<InputPart> file, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	List<MessageVO> salvaAllegatiMultipli(List<InputPart> data, List<InputPart> file, UserDetails userDetails);
}
