/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.dispatcher.AllegatoDispatcher;
import it.csi.conam.conambl.response.RicercaProtocolloSuActaResponse;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.common.SelectVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.ConfigAllegatoVO;
import org.jboss.resteasy.spi.validation.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Path("allegato")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class AllegatoResource extends SpringSupportedResource {

	@Autowired
	private AllegatoDispatcher allegatoDispatcher;
	
	@Autowired
	private UtilsDoqui utilsDoqui;
	

	@GET
	@Path("/getConfigAllegatiByIdVerbale")
	public Response getConfigAllegatiByIdVerbale() {
		List<ConfigAllegatoVO> config = allegatoDispatcher.getConfigAllegati();
		return Response.ok(config).build();
	}

	@GET
	@Path("/downloadAllegato")
	//@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadAllegato(@Valid @QueryParam("idAllegato") @NotNull(message = "RESCON01") Integer idAllegato) {
		// 20200804_LC gestione multidocumento
		List<DocumentoScaricatoVO> docList = allegatoDispatcher.downloadAllegatoById(idAllegato);
		
		// 20200827_LC
		byte[] file = null;
		
		if (docList.size()==1)
			file=docList.get(0).getFile();
		
		return Response.ok().entity(file).build();
	}

	@GET
	@Path("/getDecodificaSelectAllegato/{idDecodifica}")
	public Response getDecodificaSelectAllegato(@Valid @PathParam("idDecodifica") @NotNull(message = "RESCON02") Long decodificaSelect) {
		List<SelectVO> select = allegatoDispatcher.getDecodificaSelectAllegato(decodificaSelect);
		return Response.ok(select).build();
	}
	
	@GET
	@Path("/getDecodificaSelectSoggettiAllegato/{idverbale}")
	public Response getDecodificaSelectSoggettiAllegato(@Valid @PathParam("idverbale") @NotNull(message = "RESCON02") Integer idverbale) {
		List<SelectVO> select = allegatoDispatcher.getDecodificaSelectSoggettiAllegato(idverbale);
		return Response.ok(select).build();
	}
	
	@GET
	@Path("/ricercaProtocolloSuACTA")
	public Response ricercaProtocolloSuACTA(@Valid @QueryParam("protocollo") @NotNull(message = "RESCON20") String numProtocollo, @QueryParam("idVerbale") Integer idVerbale, @QueryParam("flagPregresso") Boolean flagPregresso) {
		// 20200903_LC gestione pregresso
		RicercaProtocolloSuActaResponse response = allegatoDispatcher.ricercaProtocolloSuACTA(numProtocollo, idVerbale, flagPregresso);
		return Response.ok(response).build();
	}

	// 20200711_LC
	@GET
	@Path("/downloadAllegatoByObjectIdDocumento")
	public Response downloadAllegatoByObjectIdDocumento(@Valid @QueryParam("objectIdDocumento") @NotNull(message = "RESCON01") String objectIdDocumento) {
		// 20200804_LC gestione doc multiplo
		List<DocumentoScaricatoVO> docList = allegatoDispatcher.downloadAllegatoByObjectIdDocumento(objectIdDocumento);

		// 20200827_LC
		byte[] file = null;
		String fileName = "";
		String mimeType = ""; 
		if (docList.size()==1) {
			file=docList.get(0).getFile();
			fileName=docList.get(0).getNomeFile();	
			mimeType = utilsDoqui.getMimeType(fileName);
		}
		//20200724_ET ripristinata la valorizzazione degli header	
		return Response.ok().entity(file).header("Content-Disposition", "inline; filename=\"" + fileName   + "\"").header("Content-Type", mimeType).build();
		// 20200722_LC download invece di visualizzazione in nuovo tab
		//		return Response.ok().entity(file).build();
	}

	
	
	
	
	// 20200825_LC
	@GET
	@Path("/downloadAllegatoByObjectIdDocumentoFisico")
	public Response downloadAllegatoByObjectIdDocumentoFisico(@Valid @QueryParam("objectIdDocumentoFisico") @NotNull(message = "RESCON01") String objectIdDocumentoFisico) {
		List<DocumentoScaricatoVO> docList = allegatoDispatcher.downloadAllegatoByObjectIdDocumentoFisico(objectIdDocumentoFisico);
		
		// 20200827_LC
		byte[] file = null;	
		String fileName = "";
		String mimeType = ""; 
		// qui dovrebbe esser sempre 1 documento
		if (docList.size()==1) {
			file=docList.get(0).getFile();
			fileName=docList.get(0).getNomeFile();	
			mimeType = utilsDoqui.getMimeType(fileName);			
		}
		return Response.ok().entity(file).header("Content-Disposition", "inline; filename=\"" + fileName   + "\"").header("Content-Type", mimeType).build();
		
	}
	
	
	
	// 20200827_LC
	@GET
	@Path("/getDocFisiciByIdAllegato")
	public Response getDocFisiciByIdAllegato(@Valid @QueryParam("idAllegato") @NotNull(message = "RESCON01") Integer idAllegato) {
		List<DocumentoScaricatoVO> docList = allegatoDispatcher.getDocFisiciByIdAllegato(idAllegato);
		return Response.ok(docList).build();
		//return Response.ok().entity(docList).build();
	}

	// 20200827_LC
	@GET
	@Path("/getDocFisiciByObjectIdDocumento")
	public Response getDocFisiciByObjectIdDocumento(@Valid @QueryParam("objectIdDocumento") @NotNull(message = "RESCON01") String objectIdDocumento) {
		List<DocumentoScaricatoVO> docList = allegatoDispatcher.getDocFisiciByObjectIdDocumento(objectIdDocumento);
		return Response.ok(docList).build();
		//return Response.ok().entity(docList).build();
	}	
	
	
	
}
