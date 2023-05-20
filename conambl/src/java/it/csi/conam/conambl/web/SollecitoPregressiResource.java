/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.dispatcher.SollecitoPregressiDispatcher;
import it.csi.conam.conambl.request.riscossione.SalvaSollecitoPregressiRequest;
import it.csi.conam.conambl.response.DocumentResponse;
import it.csi.conam.conambl.response.SalvaSollecitoPregressiResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;
import it.csi.conam.conambl.vo.sollecito.StatoSollecitoVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import org.jboss.resteasy.spi.validation.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;
import java.util.List;

@Path("sollecitoPregressi")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class SollecitoPregressiResource extends SpringSupportedResource {

	@Autowired
	private SollecitoPregressiDispatcher sollecitoPregressiDispatcher;
	
	
	// modificata rispetto alla standard (non abilitata creazione bollettini se lettera è recuperata da Acta)	
	@GET
	@Path("/getSollecitoById")
	public Response getSollecitoById(@QueryParam("idSollecito") Integer id) {
		SollecitoVO response = sollecitoPregressiDispatcher.getSollecitoById(id);
		return Response.ok().entity(response).build();
	}
	
	
	// modificata rispetto alla standard (non abilitata creazione bollettini se lettera è recuperata da Acta)		
	@GET
	@Path("/getSollecitiByIdOrdinanzaSoggetto")
	public Response getSollecitiByIdOrdinanzaSoggetto(@QueryParam("idOrdinanzaVerbaleSoggetto") Integer idOrdinanzaVerbaleSoggetto) {
		List<SollecitoVO> response = sollecitoPregressiDispatcher.getSollecitiByIdOrdinanzaSoggetto(idOrdinanzaVerbaleSoggetto);
		return Response.ok().entity(response).build();
	}
	
	
	// modificata rispetto alla standard (salvaSollecito invocando lo spostamento da Acta)
	@POST
	@Path("/salvaSollecito")
	public Response salvaSollecito(SalvaSollecitoPregressiRequest salvaSollecitoPregressiRequest) {
		UserDetails userDetails = SecurityUtils.getUser();
		SalvaSollecitoPregressiResponse response = sollecitoPregressiDispatcher.salvaSollecito(salvaSollecitoPregressiRequest, userDetails);
		return Response.ok().entity(response).build();
	}
	
	
	// nuova (retrieve tutti gli stati per permettere scelta all'utente)
	@GET
	@Path("/getStatiSollecito")
	public Response getStatiSollecito(@QueryParam("idSollecito") Integer id) {
		List<StatoSollecitoVO> response = sollecitoPregressiDispatcher.getStatiSollecito(id);
		return Response.ok().entity(response).build();
	}

	// nuova
	@GET
	@Path("/getSollecitiByOrdinanza")
	public Response getSollecitiByOrdinanza(@QueryParam("idOrdinanza") Integer id) {
		List<SollecitoVO> piano = sollecitoPregressiDispatcher.getSollecitiByOrdinanza(id);
		return Response.ok().entity(piano).build();
	}
	
	
	// nuova (effettua controlli prima del salvataggio)
	@POST
	@Path("/checkSalvaSollecito")
	public Response checkSalvaSollecito(SalvaSollecitoPregressiRequest salvaSollecitoPregressiRequest) {
		UserDetails userDetails = SecurityUtils.getUser();
		SalvaSollecitoPregressiResponse response = sollecitoPregressiDispatcher.checkSalvaSollecito(salvaSollecitoPregressiRequest, userDetails);
		return Response.ok().entity(response).build();
	}
	
	
	
	
	
	// 20201109_LC verificare se tutte le seguenti servono ancora in gestione pregresso
	
	
	


	@POST
	@Path("/inviaRichiestaBollettiniSollecito/{idSollecito}")
	public Response inviaRichiestaBollettiniOrdinanza(@PathParam("idSollecito") Integer idSollecito) {
		sollecitoPregressiDispatcher.inviaRichiestaBollettiniByIdSollecito(idSollecito);
		return Response.ok().build();
	}

	@DELETE
	@Path("/eliminaSollecito")
	public Response eliminaSollecito(@QueryParam("idSollecito") Integer id) {
		sollecitoPregressiDispatcher.eliminaSollecito(id);
		return Response.ok().build();
	}


	@GET
	@Path("/downloadBollettini")
	public Response downloadBollettini(@QueryParam("idSollecito") Integer idSollecito) {
		// 20200825_LC gestione doc multiplo
		List<DocumentoScaricatoVO> docList =  sollecitoPregressiDispatcher.dowloadBollettiniSollecito(idSollecito);
		
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
		List<DocumentoScaricatoVO> docList = sollecitoPregressiDispatcher.dowloadLettera(idSollecito);
		
		// 20200827_LC
		byte[] file = null;
		
		if (docList.size()==1)
			file=docList.get(0).getFile();
		
		return Response.ok().entity(file).build();
		
//		byte[] response = sollecitoDispatcher.dowloadLettera(idSollecito);
//		return Response.ok().entity(response).build();
	}



	@POST
	@Path("/riconciliaSollecito")
	public Response riconciliaSollecito(SollecitoVO sollecito) {
		UserDetails user = SecurityUtils.getUser();
		return Response.ok().entity(sollecitoPregressiDispatcher.riconcilaSollecito(sollecito, user)).build();
	}

	@POST
	@Path("/isLetteraSollecitoSaved")
	public Response isLetteraSollecitoSaved(IsCreatedVO request) {
		IsCreatedVO response = sollecitoPregressiDispatcher.isLetteraSollecitoSaved(request.getId());
		return Response.ok(response).build();
	}


	
	
}
