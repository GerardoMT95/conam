/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.dispatcher.AccontoDispatcher;
import it.csi.conam.conambl.dispatcher.OrdinanzaDispatcher;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.request.TipologiaAllegabiliRequest;
import it.csi.conam.conambl.request.ordinanza.AzioneOrdinanzaRequest;
import it.csi.conam.conambl.request.ordinanza.RicercaOrdinanzaRequest;
import it.csi.conam.conambl.request.ordinanza.RicercaSoggettiOrdinanzaRequest;
import it.csi.conam.conambl.request.ordinanza.TipologiaAllegabiliOrdinanzaSoggettoRequest;
import it.csi.conam.conambl.response.AzioneOrdinanzaResponse;
import it.csi.conam.conambl.response.DatiSentenzaResponse;
import it.csi.conam.conambl.response.DocumentResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.ordinanza.StatoOrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.StatoSoggettoOrdinanzaVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
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
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Path("ordinanza")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class OrdinanzaResource extends SpringSupportedResource {

	@Autowired
	private OrdinanzaDispatcher ordinanzaDispatcher;

	@Autowired
	private AccontoDispatcher accontoDispatcher;
	

	@POST
	@Path("/ricercaOrdinanza")
	public Response ricercaOrdinanza(@Valid @NotNull(message = "RESCON04") RicercaOrdinanzaRequest request) {
		UserDetails utente = SecurityUtils.getUser();
		request.setPregresso(false);
		request.setAnnullamento(false);
		request.setPerAcconto(false);
		return Response.ok(ordinanzaDispatcher.ricercaOrdinanza(request, utente)).build();
	}

	@GET
	@Path("/dettaglioOrdinanza")
	public Response dettaglioOrdinanza(@QueryParam("idOrdinanza") Integer idOrdinanza) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(ordinanzaDispatcher.dettaglioOrdinanzaById(idOrdinanza, utente)).build();
	}

	@POST
	@Path("/dettaglioOrdinanzaByIdOrdinanzaSoggetto")
	public Response dettaglioOrdinanzaByIdOrdinanzaSoggetto(List<Integer> idOrdinanzaSoggetti) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(ordinanzaDispatcher.dettaglioOrdinanzaByIdOrdinanzaSoggetti(idOrdinanzaSoggetti, utente)).build();
	}

	@GET
	@Path("/getSoggettiByIdOrdinanza")
	public Response getSoggettiByIdOrdinanza(@QueryParam("idOrdinanza") Integer idOrdinanza) {
		UserDetails utente = SecurityUtils.getUser();
		return Response.ok(ordinanzaDispatcher.getSoggettiByIdOrdinanza(idOrdinanza, utente)).build();
	}

	@POST
	@Path("/getTipologiaAllegatiAllegabiliByOrdinanza") // questa
	public Response getTipologiaAllegatiAllegabiliByOrdinanza(@Valid @NotNull(message = "RESCON05") TipologiaAllegabiliRequest request) {
		List<TipoAllegatoVO> tipoAllegati = ordinanzaDispatcher.getTipologiaAllegatiAllegabiliByOrdinanza(request.getId(), request.getTipoDocumento(), request.isAggiungiCategoriaEmail());
		return Response.ok(tipoAllegati).build();
	}

	@POST
	@Path("/getTipologiaAllegatiAllegabiliByOrdinanzaVerbaleSoggetto")
	public Response getTipologiaAllegatiAllegabiliByOrdinanzaSoggetto(@Valid @NotNull(message = "RESCON06") TipologiaAllegabiliOrdinanzaSoggettoRequest request) {
		List<TipoAllegatoVO> tipoAllegati = ordinanzaDispatcher.getTipologiaAllegatiAllegabiliByOrdinanzaSoggetto(request.getId(), request.getTipoDocumento(), request.isAggiungiCategoriaEmail());
		return Response.ok(tipoAllegati).build();
	}

	@POST
	@Path("/salvaAllegatoOrdinanza")
	@Consumes("multipart/form-data")
	public Response salvaAllegatoOrdinanza(MultipartFormDataInput input) {
		UserDetails userDetails = SecurityUtils.getUser();
		Map<String, List<InputPart>> map = input.getFormDataMap();
		AllegatoVO allegato = ordinanzaDispatcher.salvaAllegatoOrdinanza(map.get("data"), map.get("files"), userDetails);
		return Response.ok(allegato).build();
	}

	@POST
	@Path("/salvaAllegatoOrdinanzaSoggetto")
	@Consumes("multipart/form-data")
	public Response salvaAllegatoOrdinanzaSoggetto(MultipartFormDataInput input) {
		UserDetails userDetails = SecurityUtils.getUser();
		Map<String, List<InputPart>> map = input.getFormDataMap();
		ordinanzaDispatcher.salvaAllegatoOrdinanzaSoggetto(map.get("data"), map.get("files"), userDetails);
		return Response.ok().build();
	}
	
	@POST
	@Path("/salvaAllegatoProtocollatoOrdinanzaSoggetto")
	public Response salvaAllegatoProtocollatoOrdinanzaSoggetto(@Valid @NotNull(message = "RESCON21") SalvaAllegatiProtocollatiRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		MessageVO response = ordinanzaDispatcher.salvaAllegatoProtocollatoOrdinanzaSoggetto(request, userDetails);
		return Response.ok(response).build();
	}

	@POST
	@Path("/azioneOrdinanza")
	public Response azioneOrdinanza(AzioneOrdinanzaRequest azioneOrdinanzaRequest) {
		UserDetails userDetails = SecurityUtils.getUser();
		AzioneOrdinanzaResponse resp = ordinanzaDispatcher.azioneOrdinanza(azioneOrdinanzaRequest, userDetails);
		return Response.ok(resp).build();
	}

	@POST
	@Path("/azioneOrdinanzaSoggetto")
	public Response azioneOrdinanzaSoggetto(AzioneOrdinanzaRequest azioneOrdinanzaRequest) {
		UserDetails userDetails = SecurityUtils.getUser();
		AzioneOrdinanzaResponse resp = ordinanzaDispatcher.azioneOrdinanzaSoggetto(azioneOrdinanzaRequest, userDetails);
		return Response.ok(resp).build();
	}

	@GET
	@Path("/getTipologiaAllegatiCreaOrdinanza")
	public Response getTipologiaAllegatiAllegabiliByOrdinanzaSoggetto() {
		List<TipoAllegatoVO> tipoAllegati = ordinanzaDispatcher.getTipologiaAllegatiCreaOrdinanza();
		return Response.ok(tipoAllegati).build();
	}

	@GET
	@Path("/getStatiOrdinanzaSoggettoInCreazioneOrdinanza")
	public Response getStatiOrdinanzaSoggettoInCreazioneOrdinanza() {
		List<StatoSoggettoOrdinanzaVO> statoSoggettoOrdinanzaVO = ordinanzaDispatcher.getStatiOrdinanzaSoggettoInCreazioneOrdinanza();
		return Response.ok(statoSoggettoOrdinanzaVO).build();
	}

	@POST
	@Path("/salvaOrdinanza")
	@Consumes("multipart/form-data")
	public Response salvaAllegato(MultipartFormDataInput input) {
		UserDetails userDetails = SecurityUtils.getUser();
		Map<String, List<InputPart>> map = input.getFormDataMap();
		Integer id = ordinanzaDispatcher.salvaOrdinanza(map.get("data"), map.get("files"), userDetails);
		return Response.ok(id).build();
	}

	@GET
	@Path("/getAllegatiByIdOrdinanza")
	public Response getAllegatiByIdOrdinanza(@QueryParam("idOrdinanza") Integer id) {
		return Response.ok(ordinanzaDispatcher.getAllegatiByIdOrdinanza(id)).build();
	}

	@POST
	@Path("/getAllegatiByIdOrdinanzaVerbaleSoggetto")
	public Response getAllegatiByIdOrdinanzaVerbaleSoggetto(List<Integer> idList) {
		return Response.ok(ordinanzaDispatcher.getAllegatiByIdOrdinanzaVerbaleSoggetto(idList)).build();
	}
	
	@POST
	@Path("/ricercaSoggetti")
	public Response ricercaSoggetti(RicercaSoggettiOrdinanzaRequest request) {
		List<SoggettoVO> lista = ordinanzaDispatcher.ricercaSoggetti(request, false);
		return Response.ok().entity(lista).build();
	}

	@POST
	@Path("/inviaRichiestaBollettiniOrdinanza/{idOrdinanza}")
	public Response inviaRichiestaBollettiniOrdinanza(@PathParam("idOrdinanza") Integer idOrdinanza) {
		ordinanzaDispatcher.inviaRichiestaBollettiniByIdOrdinanza(idOrdinanza);
		return Response.ok().build();
	}

	@GET
	@Path("/downloadBollettini")
	public Response downloadBollettini(@QueryParam("idOrdinanza") Integer idOrdinanza) {
		// 20200825_LC gestione doc multiplo
		List<DocumentoScaricatoVO> docList = ordinanzaDispatcher.dowloadBollettiniOrdinanza(idOrdinanza);
		
		// 20200827_LC
		byte[] byteArr = null;

		DocumentResponse response = new DocumentResponse();
		response.setFile("");
		if (docList.size()==1) {
			byteArr=docList.get(0).getFile();
		response.setFile(Base64.getEncoder().encodeToString(byteArr));
		}
		return Response.ok().entity(response).build();

//		byte[] byteArr = ordinanzaDispatcher.dowloadBollettiniOrdinanza(idOrdinanza);
//		DocumentResponse response = new DocumentResponse();
//		response.setFile(Base64.getEncoder().encodeToString(byteArr));
//		return Response.ok().entity(response).build();
	}

	@GET
	@Path("/downloadLettera")
	//@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadLettera(@QueryParam("idOrdinanza") Integer idOrdinanza) {
		// 20200825_LC gestione doc multiplo
		List<DocumentoScaricatoVO> docList = ordinanzaDispatcher.dowloadLettera(idOrdinanza);
		
		// 20200827_LC
		byte[] file = null;
		
		if (docList.size()==1)
			file=docList.get(0).getFile();
		
		return Response.ok().entity(file).build();
		
//		byte[] response = ordinanzaDispatcher.dowloadLettera(idOrdinanza);
//		return Response.ok().entity(response).build();
	}

	@GET
	@Path("/getStatiOrdinanza")
	public Response getStatiOrdinanza() {
		List<StatoOrdinanzaVO> response = ordinanzaDispatcher.getStatiOrdinanza();
		return Response.ok().entity(response).build();
	}

	@GET
	@Path("/getDatiSentenzaByIdOrdinanzaVerbaleSoggetto")
	public Response ricercaSoggetti(@QueryParam("idOrdinanzaVerbaleSoggetto") Integer idOrdinanzaVerbaleSoggetto) {
		DatiSentenzaResponse response = ordinanzaDispatcher.getDatiSentenzaByIdOrdinanzaVerbaleSoggetto(idOrdinanzaVerbaleSoggetto);
		return Response.ok().entity(response).build();
	}

	@POST
	@Path("/isLetteraSaved")
	public Response isLetteraSaved(IsCreatedVO request) {
		IsCreatedVO response = ordinanzaDispatcher.isLetteraSaved(request);
		return Response.ok(response).build();
	}

	@POST
	@Path("/salvaAllegatoProtocollatoOrdinanza")
	public Response salvaAllegatoProtocollatoOrdinanza(@Valid @NotNull(message = "RESCON21") SalvaAllegatiProtocollatiRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		MessageVO response = ordinanzaDispatcher.salvaAllegatoProtocollatoOrdinanza(request, userDetails);
		return Response.ok(response).build();
	}
	
	
	@GET
	@Path("/getTipologiaAllegatiCreaOrdinanzaAnnullamento")
	public Response getTipologiaAllegatiCreaOrdinanzaAnnullamento(@QueryParam("idOrdinanzaAnnullata") Integer idOrdinanzaAnnullata) {
		List<TipoAllegatoVO> tipoAllegati = ordinanzaDispatcher.getTipologiaAllegatiCreaOrdinanzaAnnullamento(idOrdinanzaAnnullata);
		return Response.ok(tipoAllegati).build();
	}

	
	@POST
	@Path("/salvaOrdinanzaAnnullamento")
	@Consumes("multipart/form-data")
	public Response salvaOrdinanzaAnnullamento(MultipartFormDataInput input) {
		UserDetails userDetails = SecurityUtils.getUser();
		Map<String, List<InputPart>> map = input.getFormDataMap();
		Integer id = ordinanzaDispatcher.salvaOrdinanzaAnnullamento(map.get("data"), map.get("files"), userDetails);
		return Response.ok(id).build();
	}
	
	@POST
	@Path("/ricercaOrdinanzaPerAnnullamento")
	public Response ricercaOrdinanzaPerAnnullamento(@Valid @NotNull(message = "RESCON04") RicercaOrdinanzaRequest request) {
		UserDetails utente = SecurityUtils.getUser();
		request.setPregresso(false);
		request.setAnnullamento(true);
		request.setPerAcconto(false);
		return Response.ok(ordinanzaDispatcher.ricercaOrdinanza(request, utente)).build();
	}
	
	/*LUCIO - 2021/04/19 - Gestione pagamenti definiti in autonomia (Scenario 8)*/
	@POST
	@Path("/ricercaOrdinanzaNonPagata")
	public Response ricercaOrdinanzaNonPagata(@Valid @NotNull(message = "RESCON04") RicercaOrdinanzaRequest request) {
		UserDetails utente = SecurityUtils.getUser();
		request.setPregresso(false);
		request.setAnnullamento(false);
		request.setPerAcconto(true);
		return Response.ok(ordinanzaDispatcher.ricercaOrdinanza(request, utente)).build();
	}
	
	@POST
	@Path("/salvaAcconto")
	@Consumes("multipart/form-data")
	public Response salvaAcconto(MultipartFormDataInput input) {
		UserDetails userDetails = SecurityUtils.getUser();
		Map<String, List<InputPart>> map = input.getFormDataMap();
		return Response.ok(
			accontoDispatcher.salvaAcconto(
				map.get("data"),
				map.get("files"),
				userDetails
			)
		).build();
	}
	

//	@POST
//	@Path("/salvaAcconto")
//	public Response salvaAcconto(SalvaAccontoRequest request) {
//		UserDetails userDetails = SecurityUtils.getUser();
//		return Response.ok(
//			accontoDispatcher.salvaAcconto(
//				request,
//				userDetails
//			)
//		).build();
//	}
	/*LUCIO - 2021/04/19 - Fine Gestione pagamenti definiti in autonomia (Scenario 8)*/
	
	
	@POST
	@Path("/salvaAllegatoOrdinanzaMaster")
	@Consumes("multipart/form-data")
	public Response salvaAllegatiMultipli(MultipartFormDataInput input) {
		UserDetails userDetails = SecurityUtils.getUser();
		Map<String, List<InputPart>> map = input.getFormDataMap();
		List<MessageVO> msgList = ordinanzaDispatcher.salvaAllegatiMultipli(map.get("data"), map.get("files"), userDetails);
		return Response.ok(msgList).build();
	}	
	
}
