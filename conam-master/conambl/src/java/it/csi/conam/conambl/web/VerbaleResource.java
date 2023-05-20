/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.dispatcher.VerbaleDispatcher;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.request.SalvaSoggettoRequest;
import it.csi.conam.conambl.request.TipologiaAllegabiliRequest;
import it.csi.conam.conambl.request.verbale.RicercaVerbaleRequest;
import it.csi.conam.conambl.request.verbale.SalvaAzioneRequest;
import it.csi.conam.conambl.request.verbale.SalvaStatoManualeRequest;
import it.csi.conam.conambl.request.verbale.SetRecidivoVerbaleSoggettoListRequest;
import it.csi.conam.conambl.response.AzioneVerbaleResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.IstruttoreVO;
import it.csi.conam.conambl.vo.UtenteVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.verbale.*;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.RiepilogoAllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.spi.validation.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Path("verbale")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class VerbaleResource extends SpringSupportedResource {

	@Autowired
	private VerbaleDispatcher verbaleDispatcher;
	/*@Autowired
	private VerbalePregressiDispatcher verbalePregressiDispatcher;*/

	@POST
	@Path("/salvaVerbale")
	public Response salvaVerbale(@Valid @NotNull(message = "RESCON08") VerbaleVO verbale) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(verbaleDispatcher.salvaVerbale(verbale, utente)).build();
	}

	@GET
	@Path("/dettaglioVerbale")
	public Response dettaglioVerbale(@QueryParam("idVerbale") Integer id) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(verbaleDispatcher.getVerbaleById(id, utente, false)).build();
	}

	@GET
	@Path("/eliminaVerbale")
	public Response eliminaVerbale(@QueryParam("idVerbale") Integer id) {
		UserDetails utente = SecurityUtils.getUser();
		verbaleDispatcher.eliminaVerbale(id, utente);
		return Response.ok().build();
	}

	@POST
	@Path("/ricercaSoggetto")
	public Response ricercaSoggetto(@Valid @NotNull(message = "RESCON09") MinSoggettoVO soggetto) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(verbaleDispatcher.ricercaSoggetto(soggetto, utente)).build();
	}

	@POST
	@Path("/ricercaSoggettoPerPIva")
	public Response ricercaSoggettoPerPIva(@Valid @NotNull(message = "RESCON09") MinSoggettoVO soggetto) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(verbaleDispatcher.ricercaSoggettoPerPIva(soggetto, utente)).build();
	}

	@GET
	@Path("/ruoliSoggetto")
	public Response ruoliSoggetto() {
		return Response.ok(verbaleDispatcher.getRuoliSoggetto()).build();
	}

	@GET
	@Path("/riepilogo")
	public Response riepilogo(@QueryParam("idVerbale") Integer id) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(verbaleDispatcher.riepilogo(id, utente)).build();
	}

	@GET
	@Path("/riepilogoVerbaleAudizione")
	public Response riepilogoVerbaleAudizione(@QueryParam("idVerbale") Integer id) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(verbaleDispatcher.riepilogoVerbaleAudizione(id, utente)).build();
	}

	@GET
	@Path("/getSoggettiByIdVerbale")
	public Response getSoggettiByIdVerbale(@QueryParam("idVerbale") Integer id, @QueryParam("controlloUtente") Boolean controlloUtente) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(verbaleDispatcher.getSoggettiByIdVerbale(id, utente, controlloUtente)).build();
	}

	@GET
	@Path("/getSoggettiByIdVerbaleConvocazione")
	public Response getSoggettiByIdVerbaleConvocazione(@QueryParam("idVerbale") Integer id, @QueryParam("controlloUtente") Boolean controlloUtente) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(verbaleDispatcher.getSoggettiByIdVerbaleConvocazione(id, utente, controlloUtente)).build();
	}

	@GET
	@Path("/getSoggettiByIdVerbaleAudizione")
	public Response getSoggettiByIdVerbaleAudizione(@QueryParam("idAllegatoVerbaleAudizione") Integer id) {
		return Response.ok(verbaleDispatcher.getSoggettiByIdVerbaleAudizione(id)).build();
	}

	@POST
	@Path("/salvaSoggetto")
	public Response salvaSoggetto(@Valid @NotNull(message = "RESCON10") SalvaSoggettoRequest salvaSoggettoRequest) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(verbaleDispatcher.salvaSoggetto(salvaSoggettoRequest.getIdVerbale(), salvaSoggettoRequest.getSoggetto(), utente)).build();
	}

	@GET
	@Path("/eliminaSoggettoByIdVerbaleSoggetto")
	public Response eliminaSoggettoByIdVerbaleSoggetto(@QueryParam("idVerbaleSoggetto") Integer id) {
		UserDetails utente = SecurityUtils.getUser();
		verbaleDispatcher.eliminaSoggettoByIdVerbaleSoggetto(id, utente);
		return Response.ok().build();
	}

	@GET
	@Path("/getStatiRicercaVerbale")
	public Response getStatiRicercaVerbale() {
		List<StatoVerbaleVO> stati = verbaleDispatcher.getStatiRicercaVerbale();
		return Response.ok(stati).build();
	}

	@GET
	@Path("/getStatiManuali")
	public Response getStatiManuali() {
		List<StatoManualeVO> stati = verbaleDispatcher.getStatiManuali();
		return Response.ok(stati).build();
	}
	
	
	@POST
	@Path("/ricercaVerbale")
	public Response ricercaVerbale(@Valid @NotNull(message = "RESCON11") RicercaVerbaleRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		List<MinVerbaleVO> verb = verbaleDispatcher.ricercaVerbale(request, userDetails);
		return Response.ok(verb).build();
	}

	@GET
	@Path("/getAllegatiByIdVerbale")
	public Response ricercaVerbale(@QueryParam("idVerbale") Integer id) {
		UserDetails userDetails = SecurityUtils.getUser();
		RiepilogoAllegatoVO riepilogo = verbaleDispatcher.getAllegatiByIdVerbale(id, userDetails);
		return Response.ok(riepilogo).build();
	}

	@POST
	@Path("/getTipologiaAllegatiAllegabiliVerbale")
	public Response getTipologiaAllegatiAllegabiliVerbale(@Valid @NotNull(message = "RESCON12") TipologiaAllegabiliRequest request) {
		List<TipoAllegatoVO> tipoAllegati = verbaleDispatcher.getTipologiaAllegatiAllegabiliVerbale(request.getId(), request.getTipoDocumento(), request.isAggiungiCategoriaEmail());
		return Response.ok(tipoAllegati).build();
	}

	@POST
	@Path("/getTipologiaAllegatiVerbale")
	public Response getTipologiaAllegatiVerbale(@Valid @NotNull(message = "RESCON12") TipologiaAllegabiliRequest request) {
		List<TipoAllegatoVO> tipoAllegati = verbaleDispatcher.getTipologiaAllegatiVerbale(request.getTipoDocumento());
		return Response.ok(tipoAllegati).build();
	}

	@GET
	@Path("/getIstruttoreByVerbale")
	public Response getIstruttoreByVerbale(@QueryParam("idVerbale") Integer idVerbale) {
		UserDetails userDetails = SecurityUtils.getUser();
		List<IstruttoreVO> istruttori = verbaleDispatcher.getIstruttoreByVerbale(idVerbale, userDetails);
		return Response.ok(istruttori).build();
	}

	@GET
	@Path("/getUtenteRuolo")
	public Response getUtenteRuolo(@QueryParam("idVerbale") Integer idVerbale) {
		UserDetails userDetails = SecurityUtils.getUser();
		UtenteVO utente = verbaleDispatcher.getUtenteRuolo(idVerbale, userDetails);
		return Response.ok(utente).build();
	}

	@GET
	@Path("/getAzioniVerbale")
	public Response getAzioniVerbale(@QueryParam("idVerbale") Integer id) {
		UserDetails userDetails = SecurityUtils.getUser();
		AzioneVerbaleResponse stati = verbaleDispatcher.getAzioniVerbale(id, userDetails);
		return Response.ok(stati).build();
	}

	@POST
	@Path("/salvaAllegatoVerbale")
	@Consumes("multipart/form-data")
	public Response salvaAllegato(MultipartFormDataInput input) {
		UserDetails userDetails = SecurityUtils.getUser();
		Map<String, List<InputPart>> map = input.getFormDataMap();
		AllegatoVO allegato = verbaleDispatcher.salvaAllegato(map.get("data"), map.get("files"), userDetails);
		return Response.ok(allegato).build();
	}

	@POST
	@Path("/salvaAllegatoVerbaleMaster")
	@Consumes("multipart/form-data")
	public Response salvaAllegatiMultipli(MultipartFormDataInput input) {
		UserDetails userDetails = SecurityUtils.getUser();
		Map<String, List<InputPart>> map = input.getFormDataMap();
		AllegatoVO allegato = verbaleDispatcher.salvaAllegatiMultipli(map.get("data"), map.get("files"), userDetails);
		return Response.ok(allegato).build();
	}

	@POST
	@Path("/salvaAzioneVerbale")
	public Response salvaAzioneVerbale(@Valid @NotNull(message = "RESCON13") SalvaAzioneRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		verbaleDispatcher.salvaAzioneVerbale(request, userDetails);
		return Response.ok().build();
	}
	
	@GET
	@Path("/eliminaAllegato")
	public Response eliminaAllegato(@Valid @QueryParam("idAllegato") @NotNull(message = "RESCON14") Integer idAllegato,
			@Valid @QueryParam("idVerbale") @NotNull(message = "RESCON15") Integer idVerbale) {
		UserDetails userDetails = SecurityUtils.getUser();
		verbaleDispatcher.eliminaAllegato(idVerbale, idAllegato, userDetails);
		return Response.ok().build();
	}

	@GET
	@Path("/isTipoAllegatoAllegabile")
	public Response getAggiugiAllegatoRiconciliazioneVerbale(@QueryParam("idVerbale") Integer id, @QueryParam("codiceDocumento") String codice) {
		UserDetails userDetails = SecurityUtils.getUser();
		Boolean allegatiEnable = verbaleDispatcher.isTipoAllegatoAllegabile(id, codice, userDetails);
		return Response.ok(allegatiEnable).build();
	}

	@GET
	@Path("/isEnableCaricaVerbaleAudizione")
	public Response getEnableCaricaVerbaleAudizione(@QueryParam("idVerbale") Integer id) {
		UserDetails userDetails = SecurityUtils.getUser();
		Boolean allegatiEnable = verbaleDispatcher.isEnableCaricaVerbaleAudizione(id, userDetails);
		return Response.ok(allegatiEnable).build();
	}

	@POST
	@Path("/isConvocazioneCreated")
	public Response isConvocazioneCreated(IsCreatedVO request) {
		IsCreatedVO response = verbaleDispatcher.isConvocazioneCreated(request);
		return Response.ok(response).build();
	}

	@POST
	@Path("/isVerbaleAudizioneCreated")
	public Response isVerbaleAudizioneCreated(IsCreatedVO request) {
		IsCreatedVO response = verbaleDispatcher.isVerbaleAudizioneCreated(request);
		return Response.ok(response).build();
	}
	
	// 20200706_LC
	@POST
	@Path("/salvaAllegatoProtocollatoVerbale")
	public Response salvaAllegatoProtocollatoVerbale(@Valid @NotNull(message = "RESCON21") SalvaAllegatiProtocollatiRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		MessageVO response= verbaleDispatcher.salvaAllegatoProtocollatoVerbale(request, userDetails);
		return Response.ok(response).build();
	}
	
	
	
	// 20201223_LC JIRA 118
	@GET
	@Path("/getVerbaleByIdOrdinanza")
	public Response getVerbaleByIdOrdinanza(@QueryParam("idOrdinanza") Integer idOrdinanza) {
		VerbaleVO response = verbaleDispatcher.getVerbaleByIdOrdinanza(idOrdinanza);
		return Response.ok().entity(response).build();
	}
	

	//Gestione Stato manuale
	
	@POST
	@Path("/salvaStatoManuale")
	public Response salvaStatoManuale(@Valid @NotNull(message = "RESCON13") SalvaStatoManualeRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		verbaleDispatcher.salvaStatoManuale(request, userDetails);
		return Response.ok().build();
	}
	

	@GET
	@Path("/getMessaggioManualeByIdOrdinanza")
	public Response getMessaggioManualeByIdOrdinanza(@QueryParam("idOrdinanza") Integer idOrdinanza) {
		MessageVO response = verbaleDispatcher.getMessaggioManualeByIdOrdinanza(idOrdinanza);
		return Response.ok().entity(response).build();
	}

	@GET
	@Path("/getMessaggioManualeByIdVerbale")
	public Response getMessaggioManualeByIdVerbale(@QueryParam("idVerbale") Integer idVerbale) {
		MessageVO response = verbaleDispatcher.getMessaggioManualeByIdVerbale(idVerbale);
		return Response.ok().entity(response).build();
	}

	@GET
	@Path("/getMessaggioManualeByIdOrdinanzaVerbaleSoggetto")
	public Response getMessaggioManualeByIdOrdinanzaVerbaleSoggetto(@QueryParam("IdOrdinanzaVerbaleSoggetto") Integer IdOrdinanzaVerbaleSoggetto) {
		MessageVO response = verbaleDispatcher.getMessaggioManualeByIdOrdinanzaVerbaleSoggetto(IdOrdinanzaVerbaleSoggetto);
		return Response.ok().entity(response).build();
	}
	
	
	//fine - Gestione stato manuale
	/*LUCIO 2021/04/21 - Gestione casi di recidività*/
	@GET
	@Path("/getVerbaleSoggettoByIdSoggetto")
	public Response getVerbaleSoggettoByIdSoggetto(
		@QueryParam("idSoggetto") Integer idSoggetto
	) {
		List<VerbaleSoggettoVO> response = verbaleDispatcher.getVerbaleSoggettoByIdSoggetto(
			idSoggetto
		);
		return Response.ok(response).build();
	}
	
	@GET
	@Path("/getVerbaleSoggettoRaggruppatoPerSoggettoByIdSoggetto")
	public Response getVerbaleSoggettoRaggruppatoPerSoggettoByIdSoggetto(
		@QueryParam("idSoggetto") Integer idSoggetto,
		@QueryParam("recidivo") Boolean recidivo
	) {
		VerbaleSoggettoVORaggruppatoPerSoggetto response =
			verbaleDispatcher.getVerbaleSoggettoRaggruppatoPerSoggettoByIdSoggetto(
				idSoggetto,
				recidivo
			);
		return Response.ok(response).build();
	}
	
	/*@POST
	@Path("/setRecidivoVerbaleSoggetto")
	public Response setRecidivoVerbaleSoggetto(
		@Valid @NotNull(message = "RESCON13") SetRecidivoVerbaleSoggettoRequest request
	) {
		SetRecidivoVerbaleSoggettoListRequest requestList = new SetRecidivoVerbaleSoggettoListRequest();
		List<SetRecidivoVerbaleSoggettoRequest> lista =
			new ArrayList<SetRecidivoVerbaleSoggettoRequest>();
		lista.add(request);
		requestList.setLista(lista);
		return setRecidivoVerbaleSoggetto(requestList);
	}*/
	
	@POST
	@Path("/setRecidivoVerbaleSoggetto")
	public Response setRecidivoVerbaleSoggetto(
		@Valid @NotNull(message = "RESCON13") SetRecidivoVerbaleSoggettoListRequest request
	) {
		UserDetails userDetails = SecurityUtils.getUser();
		List<VerbaleSoggettoVO> response =
			verbaleDispatcher.setRecidivoVerbaleSoggetto(
				request.getLista(),
				userDetails
			);
		return Response.ok(response).build();
	}
	/*LUCIO 2021/04/21 - FINE Gestione casi di recidività*/
}
