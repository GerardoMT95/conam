/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.request.ordinanza.AzioneOrdinanzaRequest;
import it.csi.conam.conambl.request.ordinanza.SalvaOrdinanzaPregressiRequest;
import it.csi.conam.conambl.request.ordinanza.SalvaStatoOrdinanzaRequest;
import it.csi.conam.conambl.response.*;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.ordinanza.StatoSoggettoOrdinanzaVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface OrdinanzaPregressiDispatcher {

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	StatiOrdinanzaResponse getStatiOrdinanza(Integer idOrdinanza);
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	SalvaOrdinanzaPregressiResponse salvaOrdinanza(SalvaOrdinanzaPregressiRequest request, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<TipoAllegatoVO> getTipologiaAllegatiCreaOrdinanza();

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<StatoSoggettoOrdinanzaVO> getStatiOrdinanzaSoggettoInCreazioneOrdinanza();
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	MessageVO salvaStatoOrdinanza(SalvaStatoOrdinanzaRequest salvaStatoOrdinanza, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	AzioneOrdinanzaPregressiResponse azioneOrdinanza(AzioneOrdinanzaRequest azioneOrdinanzaRequest, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	AzioneOrdinanzaPregressiResponse azioneOrdinanzaSoggetto(AzioneOrdinanzaRequest azioneOrdinanzaRequest, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	MessageVO salvaAllegatoProtocollatoOrdinanzaSoggetto(SalvaAllegatiProtocollatiRequest request,
			UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	MessageVO salvaAllegatoProtocollatoOrdinanza(SalvaAllegatiProtocollatiRequest request, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliByOrdinanza(Integer id, String tipoDocumento,
			boolean aggiungiCategoriaEmail);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<DatiSentenzaPregressiResponse> getDatiSentenzaByIdOrdinanza(Integer idOrdinanza);
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<RicevutaPagamentoResponse> getRicevutePagamentoByIdOrdinanza(Integer idOrdinanza);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	AllegatoVO salvaAllegatoOrdinanza(List<InputPart> list, List<InputPart> list2, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	void salvaAllegatoOrdinanzaSoggetto(List<InputPart> data, List<InputPart> file, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.SALVATAGGIO_ALLEGATI_ORDINANZA)
	List<MessageVO> salvaAllegatiMultipli(List<InputPart> data, List<InputPart> file, UserDetails userDetails);
	
}
