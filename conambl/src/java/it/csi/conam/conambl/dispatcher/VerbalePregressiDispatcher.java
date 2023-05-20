/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import it.csi.conam.conambl.common.exception.VerbalePregressoValidationException;
import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiSoggettiRequest;
import it.csi.conam.conambl.request.verbale.SalvaAzioneRequest;
import it.csi.conam.conambl.response.AzioneVerbaleResponse;
import it.csi.conam.conambl.response.StatiVerbaleResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IstruttoreVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoPregressiVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.vo.verbale.VerbaleVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.RiepilogoAllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author Paolo Piedeplaumbo
 * @date 15/09/2020
 */
public interface VerbalePregressiDispatcher {
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	SoggettoVO salvaSoggetto(Integer id, SoggettoVO soggetto, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	SoggettoPregressiVO ricercaSoggetto(MinSoggettoVO minSoggettoVO, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<SoggettoVO> getSoggettiByIdVerbale(Integer id, UserDetails userDetails, Boolean controlloUtente);
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	SoggettoPregressiVO ricercaSoggettoPerPIva(MinSoggettoVO minSoggettoVO, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	StatiVerbaleResponse getStatiVerbale(Integer id, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	MessageVO salvaStatoVerbale(SalvaAzioneRequest salvaAzione, UserDetails userDetails);
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	Integer salvaVerbale(VerbaleVO verbale, UserDetails userDetails) throws VerbalePregressoValidationException;

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	VerbaleVO getVerbaleById(Integer id, UserDetails userDetails, boolean includeEliminati);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliVerbale(Integer id, String tipoDocumento, boolean aggiungiCategoriaEmail);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	List<IstruttoreVO> getIstruttoreByVerbale(Integer idEnte, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	AllegatoVO salvaAllegato(List<InputPart> list, List<InputPart> list2, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	AllegatoVO salvaAllegatiMultipli(List<InputPart> data, List<InputPart> file, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	MessageVO  salvaAllegatoProtocollatoVerbale(SalvaAllegatiProtocollatiRequest request, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	void checkDatiVerbale(VerbaleVO verbale, UserDetails userDetails) throws VerbalePregressoValidationException;

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	AzioneVerbaleResponse getAzioniVerbale(Integer id, UserDetails userDetails);

	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	RiepilogoAllegatoVO getAllegatiByIdVerbale(Integer id, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	MessageVO  salvaVerbaleAudizione(SalvaAllegatiProtocollatiSoggettiRequest request, UserDetails userDetails);
	
	@PreAuthorize(value = AuthorizationRoles.INSERIMENTO_PREGRESSI)
	MessageVO  salvaConvocazioneAudizione(SalvaAllegatiProtocollatiSoggettiRequest request, UserDetails userDetails);
	

}

