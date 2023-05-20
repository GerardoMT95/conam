/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.dispatcher.LuoghiDispatcher;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.luoghi.NazioneVO;
import it.csi.conam.conambl.vo.luoghi.ProvinciaVO;
import it.csi.conam.conambl.vo.luoghi.RegioneVO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 mar 2018
 */
@Path("luoghi")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class LuoghiResource extends SpringSupportedResource {

	@Autowired
	private LuoghiDispatcher luoghiDispatcher;

	@GET
	@Path("/nazioni")
	public Response getNazioni() {
		List<NazioneVO> lista = luoghiDispatcher.getNazioni();
		return Response.ok().entity(lista).build();
	}

	@GET
	@Path("/regioni")
	public Response getRegioni() {
		List<RegioneVO> lista = luoghiDispatcher.getRegioni();
		return Response.ok().entity(lista).build();
	}

	@GET
	@Path("/provinceByIdRegione")
	public Response getProvinceByIdRegione(@QueryParam("idRegione") Long idRegione) {
		List<ProvinciaVO> lista = luoghiDispatcher.getProviceByIdRegione(idRegione);
		return Response.ok().entity(lista).build();
	}

	@GET
	@Path("/comuniByIdProvincia")
	public Response getComuniByIdProvincia(@QueryParam("idProvincia") Long idProvincia) {
		List<ComuneVO> lista = luoghiDispatcher.getComuniByIdProvincia(idProvincia);
		return Response.ok().entity(lista).build();
	}

	@GET
	@Path("/regioniNascita")
	public Response getRegioniNascita() {
		List<RegioneVO> lista = luoghiDispatcher.getRegioniNascita();
		return Response.ok().entity(lista).build();
	}

	@GET
	@Path("/provinceByIdRegioneNascita")
	public Response getProvinceByIdRegioneNascita(@QueryParam("idRegione") Long idRegione) {
		List<ProvinciaVO> lista = luoghiDispatcher.getProviceByIdRegioneNascita(idRegione);
		return Response.ok().entity(lista).build();
	}

	@GET
	@Path("/comuniByIdProvinciaNascita")
	public Response getComuniByIdProvinciaNascita(@QueryParam("idProvincia") Long idProvincia) {
		List<ComuneVO> lista = luoghiDispatcher.getComuniByIdProvinciaNascita(idProvincia);
		return Response.ok().entity(lista).build();
	}

	@GET
	@Path("/provinceByIdRegioneValidInDate")
	public Response getProvinceByIdRegioneValidInDate(@QueryParam("idRegione") Long idRegione, @QueryParam("dataOraAccertamento") String dataOraAccertamento) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
		try {
			List<ProvinciaVO> lista = luoghiDispatcher.getProviceByIdRegione(idRegione, sdf.parse(dataOraAccertamento));
			return Response.ok().entity(lista).build();
		}catch(Throwable t) {
			List<ProvinciaVO> lista = luoghiDispatcher.getProviceByIdRegioneNascita(idRegione);
			return Response.ok().entity(lista).build();
		}
	}

	@GET
	@Path("/comuniByIdProvinciaValidInDate")
	public Response getComuniByIdProvinciaValidInDate(@QueryParam("idProvincia") Long idProvincia, @QueryParam("dataOraAccertamento") String dataOraAccertamento) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
		try {
			List<ComuneVO> lista = luoghiDispatcher.getComuniByIdProvincia(idProvincia, sdf.parse(dataOraAccertamento));
			return Response.ok().entity(lista).build();
		}catch(Throwable t) {
			List<ComuneVO> lista = luoghiDispatcher.getComuniByIdProvinciaNascita(idProvincia);
			return Response.ok().entity(lista).build();
		}
		
	}
	
	@GET
	@Path("/comuniEnteValidInDate")
	public Response comuniEnteValidInDate(@QueryParam("dataOraAccertamento") String dataOraAccertamento) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
		try {
			List<ComuneVO> lista = luoghiDispatcher.getComuniEnte(sdf.parse(dataOraAccertamento));
			return Response.ok().entity(lista).build();	
		}catch(Throwable t) {
			List<ComuneVO> lista = luoghiDispatcher.getComuniEnte(null);
			return Response.ok().entity(lista).build();
		}
	}
	

}
