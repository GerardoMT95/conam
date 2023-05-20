/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.dispatcher.RiferimentiNormativiDispatcher;
import it.csi.conam.conambl.integration.doqui.utils.DateFormat;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.leggi.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 mar 2018
 */
@Path("rifNormativi")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class RiferimentiNormativiResource extends SpringSupportedResource {

	@Autowired
	private RiferimentiNormativiDispatcher riferimentiNormativiDispatcher;
	@Autowired
	private UtilsDate utilsDate;

	@GET
	@Path("/ambitiByIdEnte")
	public Response getAmbitiByIdEnte(@QueryParam("idEnte") Long idEnte, @QueryParam("filterDataValidita") Boolean filterDataValidita, 
			@QueryParam("pregresso") boolean pregresso, @QueryParam("dataOraAccertamento") String dataOraAccertamento) {
		Date dataAccertamento = StringUtils.isNotBlank(dataOraAccertamento)? utilsDate.getDate(dataOraAccertamento, DateFormat.DATE_FORMAT_DDMMYY_HHMM) : null;
		List<AmbitoVO> lista = riferimentiNormativiDispatcher.getAmbitiByIdEnte(idEnte, filterDataValidita, pregresso, dataAccertamento);
		return Response.ok().entity(lista).build();
	}

	@GET
	@Path("/getNormeByIdAmbitoAndIdEnte")
	public Response getNormeByIdAmbitoAndIdEnte(@QueryParam("idAmbito") Integer idAmbito, @QueryParam("idEnte") Long idEnte, @QueryParam("filterDataValidita") Boolean filterDataValidita, 
			@QueryParam("pregresso") boolean pregresso, @QueryParam("dataOraAccertamento") String dataOraAccertamento) {
		Date dataAccertamento = StringUtils.isNotBlank(dataOraAccertamento)? utilsDate.getDate(dataOraAccertamento, DateFormat.DATE_FORMAT_DDMMYY_HHMM) : null;
		List<NormaVO> lista = riferimentiNormativiDispatcher.getNormeByIdAmbitoAndIdEnte(idAmbito, idEnte, filterDataValidita, pregresso, dataAccertamento);
		return Response.ok().entity(lista).build();
	}

	@GET
	@Path("/getArticoliByIdNormaAndIdEnte")
	public Response getArticoliByIdNormaAndIdEnte(@QueryParam("idNorma") Long idNorma, @QueryParam("idEnte") Long idEnte, @QueryParam("filterDataValidita") Boolean filterDataValidita, 
			@QueryParam("pregresso") boolean pregresso, @QueryParam("dataOraAccertamento" )String dataOraAccertamento) {
		Date dataAccertamento = StringUtils.isNotBlank(dataOraAccertamento)? utilsDate.getDate(dataOraAccertamento, DateFormat.DATE_FORMAT_DDMMYY_HHMM) : null;
		List<ArticoloVO> lista = riferimentiNormativiDispatcher.getArticoliByIdNormaAndIdEnte(idNorma, idEnte, filterDataValidita, pregresso, dataAccertamento);
		return Response.ok().entity(lista).build();
	}

	@GET
	@Path("/commaByIdArticolo")
	public Response commaByIdArticolo(@QueryParam("idArticolo") Long idArticolo, @QueryParam("filterDataValidita") Boolean filterDataValidita, 
			@QueryParam("pregresso") boolean pregresso, @QueryParam("dataOraAccertamento") String dataOraAccertamento) {
		Date dataAccertamento = StringUtils.isNotBlank(dataOraAccertamento)? utilsDate.getDate(dataOraAccertamento, DateFormat.DATE_FORMAT_DDMMYY_HHMM) : null;
		List<CommaVO> lista = riferimentiNormativiDispatcher.commaByIdArticolo(idArticolo, filterDataValidita, pregresso, dataAccertamento);
		return Response.ok().entity(lista).build();
	}

	@GET
	@Path("/letteraByIdComma")
	public Response letteraByIdComma(@QueryParam("idComma") Long idComma, @QueryParam("filterDataValidita") Boolean filterDataValidita, 
			@QueryParam("pregresso") boolean pregresso, @QueryParam("dataOraAccertamento") String dataOraAccertamento) {
		Date dataAccertamento = StringUtils.isNotBlank(dataOraAccertamento)? utilsDate.getDate(dataOraAccertamento, DateFormat.DATE_FORMAT_DDMMYY_HHMM) : null;
		List<LetteraVO> lista = riferimentiNormativiDispatcher.letteraByIdComma(idComma, filterDataValidita, pregresso, dataAccertamento);
		return Response.ok().entity(lista).build();
	}

}
