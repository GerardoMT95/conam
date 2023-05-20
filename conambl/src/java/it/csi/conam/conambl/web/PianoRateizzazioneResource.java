/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.dispatcher.PianoRateizzazioneDispatcher;
import it.csi.conam.conambl.request.pianorateizzazione.RicercaPianoRequest;
import it.csi.conam.conambl.response.DocumentResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.pianorateizzazione.MinPianoRateizzazioneVO;
import it.csi.conam.conambl.vo.pianorateizzazione.PianoRateizzazioneVO;
import it.csi.conam.conambl.vo.pianorateizzazione.RataVO;
import it.csi.conam.conambl.vo.pianorateizzazione.StatoPianoVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 mar 2018
 */
@Path("pianoRateizzazione")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class PianoRateizzazioneResource extends SpringSupportedResource {

	@Autowired
	private PianoRateizzazioneDispatcher pianoRateizzazioneDispatcher;

	@GET
	@Path("/getStatiPiano")
	public Response getStatiPiano(@QueryParam("isRiconciliaPiano") Boolean isRiconciliaPiano) {
		List<StatoPianoVO> lista = pianoRateizzazioneDispatcher.getStatiPiano(isRiconciliaPiano);
		return Response.ok().entity(lista).build();
	}

	@POST
	@Path("/ricercaSoggetti")
	public Response ricercaSoggetti(RicercaPianoRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		List<SoggettoVO> lista = pianoRateizzazioneDispatcher.ricercaSoggetti(request, userDetails);
		return Response.ok().entity(lista).build();
	}

	@POST
	@Path("/ricercaPiani")
	public Response ricercaPiani(RicercaPianoRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		List<MinPianoRateizzazioneVO> lista = pianoRateizzazioneDispatcher.ricercaPiani(request, userDetails);
		return Response.ok().entity(lista).build();
	}

	@POST
	@Path("/precompilaPiano")
	public Response precompilaPiano(List<Integer> ordinanzaVerbaleSoggetto) {
		UserDetails userDetails = SecurityUtils.getUser();
		PianoRateizzazioneVO piano = pianoRateizzazioneDispatcher.precompilaPiano(ordinanzaVerbaleSoggetto, userDetails);
		return Response.ok().entity(piano).build();
	}

	@POST
	@Path("/ricalcolaRate")
	public Response ricalcolaRate(PianoRateizzazioneVO request) {
		PianoRateizzazioneVO piano = pianoRateizzazioneDispatcher.ricalcolaRate(request);
		return Response.ok().entity(piano).build();
	}

	@POST
	@Path("/calcolaRata")
	public Response calcolaRata(PianoRateizzazioneVO request) {
		PianoRateizzazioneVO piano = pianoRateizzazioneDispatcher.calcolaRate(request);
		return Response.ok().entity(piano).build();
	}

	@POST
	@Path("/salvaPiano")
	public Response salvaPiano(PianoRateizzazioneVO request) {
		UserDetails userDetails = SecurityUtils.getUser();
		Integer id = pianoRateizzazioneDispatcher.salvaPiano(request, userDetails);
		return Response.ok().entity(id).build();
	}

	@POST
	@Path("/creaPiano")
	public Response salvaPiano(Integer id) {
		UserDetails userDetails = SecurityUtils.getUser();
		id = pianoRateizzazioneDispatcher.creaPiano(id, userDetails);
		return Response.ok().entity(id).build();
	}

	@GET
	@Path("/dettaglioPianoById")
	public Response dettaglioPianoById(@QueryParam("idPiano") Integer id, @QueryParam("flagModifica") Boolean flagModifica) {
		UserDetails userDetails = SecurityUtils.getUser();
		PianoRateizzazioneVO piano = pianoRateizzazioneDispatcher.dettaglioPianoById(id, flagModifica, userDetails);
		return Response.ok().entity(piano).build();
	}

	@DELETE
	@Path("/deletePiano")
	public Response deletePiano(@QueryParam("idPiano") Integer id) {
		pianoRateizzazioneDispatcher.deletePiano(id);
		return Response.ok().build();
	}

	@GET
	@Path("/downloadBollettini")
	public Response downloadBollettini(@QueryParam("idPiano") Integer idPiano) {
		// 20200825_LC gestione doc multiplo
		List<DocumentoScaricatoVO> docList = pianoRateizzazioneDispatcher.dowloadBollettiniPiano(idPiano);
		
		// 20200827_LC
		byte[] byteArr = null;

		DocumentResponse response = new DocumentResponse();
		response.setFile("");
		if (docList.size()==1) {
			byteArr=docList.get(0).getFile();
		response.setFile(Base64.getEncoder().encodeToString(byteArr));
		}
		return Response.ok().entity(response).build();

//		byte[] byteArr = pianoRateizzazioneDispatcher.dowloadBollettiniPiano(idPiano);
//		DocumentResponse response = new DocumentResponse();
//		response.setFile(Base64.getEncoder().encodeToString(byteArr));
//		return Response.ok().entity(response).build();
	}

	@POST
	@Path("/riconciliaRata")
	public Response riconciliaRata(RataVO rata) {
		UserDetails userDetails = SecurityUtils.getUser();
		rata = pianoRateizzazioneDispatcher.riconcilaRata(rata, userDetails);
		return Response.ok().entity(rata).build();
	}

	@POST
	@Path("/inviaRichiestaBollettiniPiano/{idPiano}")
	public Response inviaRichiestaBollettiniOrdinanza(@PathParam("idPiano") Integer idPiano) {
		pianoRateizzazioneDispatcher.inviaRichiestaBollettiniByIdPiano(idPiano);
		return Response.ok().build();
	}

	@POST
	@Path("/isLetteraPianoSaved")
	public Response isLetteraPianoSaved(IsCreatedVO request) {
		IsCreatedVO response = pianoRateizzazioneDispatcher.isLetteraPianoSaved(request.getId());
		return Response.ok(response).build();
	}

}
