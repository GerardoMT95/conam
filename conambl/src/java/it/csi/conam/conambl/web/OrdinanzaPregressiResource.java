/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.dispatcher.OrdinanzaDispatcher;
import it.csi.conam.conambl.dispatcher.OrdinanzaPregressiDispatcher;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.request.TipologiaAllegabiliRequest;
import it.csi.conam.conambl.request.ordinanza.*;
import it.csi.conam.conambl.response.*;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.common.MessageVO;
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

@Path("ordinanzaPregressi")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class OrdinanzaPregressiResource extends SpringSupportedResource {

	@Autowired
	private OrdinanzaDispatcher ordinanzaDispatcher;

	@Autowired
	private OrdinanzaPregressiDispatcher ordinanzaPregressiDispatcher;

	@POST
	@Path("/ricercaOrdinanza")
	public Response ricercaOrdinanza(@Valid @NotNull(message = "RESCON04") RicercaOrdinanzaRequest request) {
		UserDetails utente = SecurityUtils.getUser();
		request.setPregresso(true);
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
		List<TipoAllegatoVO> tipoAllegati = ordinanzaPregressiDispatcher.getTipologiaAllegatiAllegabiliByOrdinanza(request.getId(), request.getTipoDocumento(), request.isAggiungiCategoriaEmail());
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
		AllegatoVO allegato = ordinanzaPregressiDispatcher.salvaAllegatoOrdinanza(map.get("data"), map.get("files"), userDetails);
		return Response.ok(allegato).build();
	}

	@POST
	@Path("/salvaAllegatoOrdinanzaSoggetto")
	@Consumes("multipart/form-data")
	public Response salvaAllegatoOrdinanzaSoggetto(MultipartFormDataInput input) {
		UserDetails userDetails = SecurityUtils.getUser();
		Map<String, List<InputPart>> map = input.getFormDataMap();
		ordinanzaPregressiDispatcher.salvaAllegatoOrdinanzaSoggetto(map.get("data"), map.get("files"), userDetails);
		return Response.ok().build();
	}
	
	@POST
	@Path("/salvaAllegatoProtocollatoOrdinanzaSoggetto")
	public Response salvaAllegatoProtocollatoOrdinanzaSoggetto(@Valid @NotNull(message = "RESCON21") SalvaAllegatiProtocollatiRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		MessageVO response = ordinanzaPregressiDispatcher.salvaAllegatoProtocollatoOrdinanzaSoggetto(request, userDetails);
		return Response.ok(response).build();
	}

	@POST
	@Path("/azioneOrdinanza")
	public Response azioneOrdinanza(AzioneOrdinanzaRequest azioneOrdinanzaRequest) {
		UserDetails userDetails = SecurityUtils.getUser();
		AzioneOrdinanzaPregressiResponse resp = ordinanzaPregressiDispatcher.azioneOrdinanza(azioneOrdinanzaRequest, userDetails);
		return Response.ok(resp).build();
	}

	@POST
	@Path("/azioneOrdinanzaSoggetto")
	public Response azioneOrdinanzaSoggetto(AzioneOrdinanzaRequest azioneOrdinanzaRequest) {
		UserDetails userDetails = SecurityUtils.getUser();
		AzioneOrdinanzaPregressiResponse resp = ordinanzaPregressiDispatcher.azioneOrdinanzaSoggetto(azioneOrdinanzaRequest, userDetails);
		return Response.ok(resp).build();
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
		List<SoggettoVO> lista = ordinanzaDispatcher.ricercaSoggetti(request, true);
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

//	@GET
//	@Path("/getStatiOrdinanza")
//	public Response getStatiOrdinanza() {
//		List<StatoOrdinanzaVO> response = ordinanzaDispatcher.getStatiOrdinanza();
//		return Response.ok().entity(response).build();
//	}

	@GET
	@Path("/getDatiSentenzaByIdOrdinanzaVerbaleSoggetto")
	public Response ricercaSoggetti(@QueryParam("idOrdinanzaVerbaleSoggetto") Integer idOrdinanzaVerbaleSoggetto) {
		DatiSentenzaResponse response = ordinanzaDispatcher.getDatiSentenzaByIdOrdinanzaVerbaleSoggetto(idOrdinanzaVerbaleSoggetto);
		return Response.ok().entity(response).build();
	}

	@GET
	@Path("/getDatiSentenzaByIdOrdinanza")
	public Response getDatiSentenzaByIdOrdinanza(@QueryParam("idOrdinanza") Integer idOrdinanza) {
		List<DatiSentenzaPregressiResponse> response = ordinanzaPregressiDispatcher.getDatiSentenzaByIdOrdinanza(idOrdinanza);
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
		MessageVO response = ordinanzaPregressiDispatcher.salvaAllegatoProtocollatoOrdinanza(request, userDetails);
		return Response.ok(response).build();
	}

	//TODO controllare se le API precedenti servono tutte, altrimenti eliminarle
	
	//20201106_ET API ####  gia' esistenti, ma usate anche per fascicoli pregressi #######################

	
	@GET
	@Path("/getTipologiaAllegatiCreaOrdinanza")
	public Response getTipologiaAllegatiAllegabiliByOrdinanzaSoggetto() {
		List<TipoAllegatoVO> tipoAllegati = ordinanzaPregressiDispatcher.getTipologiaAllegatiCreaOrdinanza();
		return Response.ok(tipoAllegati).build();
	}

	@GET
	@Path("/getStatiOrdinanzaSoggettoInCreazioneOrdinanza")
	public Response getStatiOrdinanzaSoggettoInCreazioneOrdinanza() {
		List<StatoSoggettoOrdinanzaVO> statoSoggettoOrdinanzaVO = ordinanzaPregressiDispatcher.getStatiOrdinanzaSoggettoInCreazioneOrdinanza();
		return Response.ok(statoSoggettoOrdinanzaVO).build();
	}
	
	@POST
	@Path("/salvaOrdinanza")
	public Response salvaAllegato(SalvaOrdinanzaPregressiRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		SalvaOrdinanzaPregressiResponse id = ordinanzaPregressiDispatcher.salvaOrdinanza(request, userDetails);
		return Response.ok(id).build();
	}

	//20201102_ET ####  NUOVE API per fascicoli pregressi ##############################################
	
	@GET
	@Path("/getStatiOrdinanza")
	public Response getStatiOrdinanza(@QueryParam("idOrdinanza") Integer id) {
		StatiOrdinanzaResponse response = ordinanzaPregressiDispatcher.getStatiOrdinanza(id);
		return Response.ok().entity(response).build();
	}
	
	@POST
	@Path("/salvaStatoOrdinanza")
	public Response salvaStatoOrdinanza(SalvaStatoOrdinanzaRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		MessageVO response = ordinanzaPregressiDispatcher.salvaStatoOrdinanza(request, userDetails);
		return Response.ok(response).build();
	}
	
	
	
	@GET
	@Path("/getRicevutePagamentoByIdOrdinanza")
	public Response getRicevutePagamentoByIdOrdinanza(@QueryParam("idOrdinanza") Integer idOrdinanza) {
		List<RicevutaPagamentoResponse> response = ordinanzaPregressiDispatcher.getRicevutePagamentoByIdOrdinanza(idOrdinanza);
		return Response.ok().entity(response).build();
	}

	
	@POST
	@Path("/salvaAllegatoOrdinanzaMaster")
	@Consumes("multipart/form-data")
	public Response salvaAllegatiMultipli(MultipartFormDataInput input) {
		UserDetails userDetails = SecurityUtils.getUser();
		Map<String, List<InputPart>> map = input.getFormDataMap();
		List<MessageVO> msgList = ordinanzaPregressiDispatcher.salvaAllegatiMultipli(map.get("data"), map.get("files"), userDetails);
		return Response.ok(msgList).build();
	}	
	
}
