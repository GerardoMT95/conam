/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.dispatcher.SollecitoDispatcher;
import it.csi.conam.conambl.request.riscossione.SalvaSollecitoRequest;
import it.csi.conam.conambl.response.DocumentResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import org.jboss.resteasy.spi.validation.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;
import java.util.List;

@Path("sollecito")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class SollecitoResource extends SpringSupportedResource {

	@Autowired
	private SollecitoDispatcher sollecitoDispatcher;

	@GET
	@Path("/getSollecitiByIdOrdinanzaSoggetto")
	public Response getSollecitiByIdOrdinanzaSoggetto(@QueryParam("idOrdinanzaVerbaleSoggetto") Integer idOrdinanzaVerbaleSoggetto) {
		List<SollecitoVO> response = sollecitoDispatcher.getSollecitiByIdOrdinanzaSoggetto(idOrdinanzaVerbaleSoggetto);
		return Response.ok().entity(response).build();
	}

	@POST
	@Path("/salvaSollecito")
	public Response salvaSollecito(SalvaSollecitoRequest salvaSollecitoRequest) {
		UserDetails userDetails = SecurityUtils.getUser();
		Integer response = sollecitoDispatcher.salvaSollecito(salvaSollecitoRequest.getSollecito(), salvaSollecitoRequest.getNotifica(), userDetails);
		return Response.ok().entity(response).build();
	}

	@DELETE
	@Path("/eliminaSollecito")
	public Response eliminaSollecito(@QueryParam("idSollecito") Integer id) {
		sollecitoDispatcher.eliminaSollecito(id);
		return Response.ok().build();
	}

	@POST
	@Path("/inviaRichiestaBollettiniSollecito/{idSollecito}")
	public Response inviaRichiestaBollettiniOrdinanza(@PathParam("idSollecito") Integer idSollecito) {
		sollecitoDispatcher.inviaRichiestaBollettiniByIdSollecito(idSollecito);
		return Response.ok().build();
	}

	@GET
	@Path("/downloadBollettini")
	public Response downloadBollettini(@QueryParam("idSollecito") Integer idSollecito) {
		// 20200825_LC gestione doc multiplo
		List<DocumentoScaricatoVO> docList =  sollecitoDispatcher.dowloadBollettiniSollecito(idSollecito);
		
		// 20200827_LC
		byte[] byteArr = null;

		DocumentResponse response = new DocumentResponse();
		response.setFile("");
		if (docList.size()==1) {
			byteArr=docList.get(0).getFile();
		response.setFile(Base64.getEncoder().encodeToString(byteArr));
		}
		return Response.ok().entity(response).build();
		
//		byte[] byteArr = sollecitoDispatcher.dowloadBollettiniSollecito(idSollecito);
//		DocumentResponse response = new DocumentResponse();
//		response.setFile(Base64.getEncoder().encodeToString(byteArr));
//		return Response.ok().entity(response).build();
	}

	@GET
	@Path("/downloadLettera")
	//@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadLettera(@QueryParam("idSollecito") Integer idSollecito) {
		// 20200825_LC gestione doc multiplo
		List<DocumentoScaricatoVO> docList = sollecitoDispatcher.dowloadLettera(idSollecito);
		
		// 20200827_LC
		byte[] file = null;
		
		if (docList.size()==1)
			file=docList.get(0).getFile();
		
		return Response.ok().entity(file).build();
		
//		byte[] response = sollecitoDispatcher.dowloadLettera(idSollecito);
//		return Response.ok().entity(response).build();
	}

	@GET
	@Path("/getSollecitoById")
	public Response getSollecitoById(@QueryParam("idSollecito") Integer id) {
		SollecitoVO response = sollecitoDispatcher.getSollecitoById(id);
		return Response.ok().entity(response).build();
	}

	@POST
	@Path("/riconciliaSollecito")
	public Response riconciliaSollecito(SollecitoVO sollecito) {
		UserDetails user = SecurityUtils.getUser();
		return Response.ok().entity(sollecitoDispatcher.riconcilaSollecito(sollecito, user)).build();
	}

	@POST
	@Path("/isLetteraSollecitoSaved")
	public Response isLetteraSollecitoSaved(IsCreatedVO request) {
		IsCreatedVO response = sollecitoDispatcher.isLetteraSollecitoSaved(request.getId());
		return Response.ok(response).build();
	}

	
	
	// 20210325_LC seguenti per sollecito pagamento rate
	
	@GET
	@Path("/getSollecitiByIdPianoRateizzazione")
	public Response getSollecitiByIdPianoRateizzazione(@QueryParam("idPianoRateizzazione") Integer idPianoRateizzazione) {
		List<SollecitoVO> response = sollecitoDispatcher.getSollecitiByIdPianoRateizzazione(idPianoRateizzazione);
		return Response.ok().entity(response).build();
	}
	
	@POST
	@Path("/salvaSollecitoRate")
	public Response salvaSollecitoRate(SalvaSollecitoRequest salvaSollecitoRequest) {
		UserDetails userDetails = SecurityUtils.getUser();
		Integer response = sollecitoDispatcher.salvaSollecitoRate(salvaSollecitoRequest.getSollecito(), salvaSollecitoRequest.getNotifica(), salvaSollecitoRequest.getIdPianoRateizzazione(), userDetails);
		return Response.ok().entity(response).build();
	}
	
	
	
	
	
}
