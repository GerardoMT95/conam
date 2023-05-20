/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.exception.VerbalePregressoValidationException;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.dispatcher.VerbaleDispatcher;
import it.csi.conam.conambl.dispatcher.VerbalePregressiDispatcher;
import it.csi.conam.conambl.request.*;
import it.csi.conam.conambl.request.verbale.RicercaVerbaleRequest;
import it.csi.conam.conambl.request.verbale.SalvaAzioneRequest;
import it.csi.conam.conambl.response.AzioneVerbaleResponse;
import it.csi.conam.conambl.response.StatiVerbaleResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.ExceptionVO;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.IstruttoreVO;
import it.csi.conam.conambl.vo.UtenteVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.MinVerbaleVO;
import it.csi.conam.conambl.vo.verbale.StatoVerbaleVO;
import it.csi.conam.conambl.vo.verbale.VerbaleVO;
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
 * @author Paolo Piedepalumbo
 * @date 14/09/2020
 */
@Path("verbalePregressi")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class VerbalePregressiResource extends SpringSupportedResource {

	@Autowired
	private VerbaleDispatcher verbaleDispatcher;

	@Autowired
	private VerbalePregressiDispatcher verbalePregressiDispatcher;
	
	@POST
	@Path("/salvaVerbale")
	public Response salvaVerbale(@Valid @NotNull(message = "RESCON08") VerbaleVO verbale) {
		UserDetails utente = SecurityUtils.getUser();
		try {
			return Response.ok(verbalePregressiDispatcher.salvaVerbale(verbale, utente)).build();
		}catch(VerbalePregressoValidationException ex) {
			return Response.ok(new ExceptionVO("", ex.getMessage(), ErrorCode.ERROR_DANGER_DESC)).build();
		}
	}

	@GET
	@Path("/dettaglioVerbale")
	public Response dettaglioVerbale(@QueryParam("idVerbale") Integer id) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(verbalePregressiDispatcher.getVerbaleById(id, utente, false)).build();
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
		return Response.ok(verbalePregressiDispatcher.ricercaSoggetto(soggetto, utente)).build();
	}

	@POST
	@Path("/ricercaSoggettoPerPIva")
	public Response ricercaSoggettoPerPIva(@Valid @NotNull(message = "RESCON09") MinSoggettoVO soggetto) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(verbalePregressiDispatcher.ricercaSoggettoPerPIva(soggetto, utente)).build();
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
		return Response.ok(verbalePregressiDispatcher.getSoggettiByIdVerbale(id, utente, controlloUtente)).build();
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
		return Response.ok(verbalePregressiDispatcher.salvaSoggetto(salvaSoggettoRequest.getIdVerbale(), salvaSoggettoRequest.getSoggetto(), utente)).build();
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
		RiepilogoAllegatoVO riepilogo = verbalePregressiDispatcher.getAllegatiByIdVerbale(id, userDetails);
		return Response.ok(riepilogo).build();
	}

	@POST
	@Path("/getTipologiaAllegatiAllegabiliVerbale")
	public Response getTipologiaAllegatiAllegabiliVerbale(@Valid @NotNull(message = "RESCON12") TipologiaAllegabiliPregressiRequest request) {
		List<TipoAllegatoVO> tipoAllegati = verbalePregressiDispatcher.getTipologiaAllegatiAllegabiliVerbale(request.getId(), request.getTipoDocumento(), request.isAggiungiCategoriaEmail());
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
		List<IstruttoreVO> istruttori = verbalePregressiDispatcher.getIstruttoreByVerbale(idVerbale, userDetails);
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
		AzioneVerbaleResponse stati = verbalePregressiDispatcher.getAzioniVerbale(id, userDetails);
		return Response.ok(stati).build();
	}

	@POST
	@Path("/salvaAllegatoVerbale")
	@Consumes("multipart/form-data")
	public Response salvaAllegato(MultipartFormDataInput input) {
		UserDetails userDetails = SecurityUtils.getUser();
		Map<String, List<InputPart>> map = input.getFormDataMap();
		AllegatoVO allegato = verbalePregressiDispatcher.salvaAllegato(map.get("data"), map.get("files"), userDetails);
		return Response.ok(allegato).build();
	}

	@POST
	@Path("/salvaAllegatoVerbaleMaster")
	@Consumes("multipart/form-data")
	public Response salvaAllegatiMultipli(MultipartFormDataInput input) {
		UserDetails userDetails = SecurityUtils.getUser();
		Map<String, List<InputPart>> map = input.getFormDataMap();
		AllegatoVO allegato = verbalePregressiDispatcher.salvaAllegatiMultipli(map.get("data"), map.get("files"), userDetails);
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
	
	
	
	// 20201223_LC JIRA 118
	@GET
	@Path("/getVerbaleByIdOrdinanza")
	public Response getVerbaleByIdOrdinanza(@QueryParam("idOrdinanza") Integer idOrdinanza) {
		VerbaleVO response = verbaleDispatcher.getVerbaleByIdOrdinanza(idOrdinanza);
		return Response.ok().entity(response).build();
	}
	
	
	
	
	// 20200706_LC
	@POST
	@Path("/salvaAllegatoProtocollatoVerbale")
	public Response salvaAllegatoProtocollatoVerbale(@Valid @NotNull(message = "RESCON21") SalvaAllegatiProtocollatiRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		MessageVO response= verbalePregressiDispatcher.salvaAllegatoProtocollatoVerbale(request, userDetails);
		return Response.ok(response).build();
	}
	
	@GET
	@Path("/getStatiVerbale")
	public Response getStatiVerbale(@QueryParam("idVerbale") Integer id) {
		UserDetails userDetails = SecurityUtils.getUser();
		StatiVerbaleResponse response = verbalePregressiDispatcher.getStatiVerbale(id, userDetails);
		return Response.ok(response).build();
	}
	
	@POST
	@Path("/salvaStatoVerbale")
	public Response salvaStatoVerbale(@Valid @NotNull(message = "RESCON13") SalvaAzioneRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		MessageVO response=verbalePregressiDispatcher.salvaStatoVerbale(request, userDetails);
		return Response.ok(response).build();
	}
	
	@POST
	@Path("/checkDatiVerbale")
	public Response checkDatiVerbale(@Valid @NotNull(message = "RESCON08") VerbaleVO verbale) {
		UserDetails utente = SecurityUtils.getUser();
		try {
			verbalePregressiDispatcher.checkDatiVerbale(verbale, utente);
			return Response.ok().build();
		}catch(VerbalePregressoValidationException ex) {
			return Response.ok(new ExceptionVO("", ex.getMessage(), ErrorCode.ERROR_DANGER_DESC)).build();
		}
	}
	
	@POST
	@Path("/salvaVerbaleAudizione")
	public Response salvaVerbaleAudizione(@Valid @NotNull(message = "RESCON21") SalvaAllegatiProtocollatiSoggettiRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		MessageVO response= verbalePregressiDispatcher.salvaVerbaleAudizione(request, userDetails);
		return Response.ok(response).build();
	}
	
	@POST
	@Path("/salvaConvocazioneAudizione")
	public Response salvaConvocazioneAudizione(@Valid @NotNull(message = "RESCON21") SalvaAllegatiProtocollatiSoggettiRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		MessageVO response= verbalePregressiDispatcher.salvaConvocazioneAudizione(request, userDetails);
		return Response.ok(response).build();
	}
	
	

	
	
	
}
