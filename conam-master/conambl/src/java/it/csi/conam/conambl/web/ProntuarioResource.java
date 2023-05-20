/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.dispatcher.ProntuarioDispatcher;
import it.csi.conam.conambl.request.leggi.RicercaLeggeProntuarioRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.vo.ExceptionVO;
import it.csi.conam.conambl.vo.leggi.AmbitoVO;
import it.csi.conam.conambl.vo.leggi.ProntuarioVO;
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
 * @date 14 mar 2018
 */
@Path("prontuario")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class ProntuarioResource extends SpringSupportedResource {

	@Autowired
	private ProntuarioDispatcher prontuarioDispatcher;

	@POST
	@Path("/ricercaLeggeProntuario")
	public Response ricercaLeggeProntuario(@Valid @NotNull(message = "RESCON07") RicercaLeggeProntuarioRequest ricercaLeggeProntuarioRequest) {
		List<ProntuarioVO> lista = prontuarioDispatcher.ricercaLeggeProntuario(ricercaLeggeProntuarioRequest);
		return Response.ok().entity(lista).build();
	}

	@POST
	@Path("/salvaLeggeProntuario")
	public Response salvaLeggeProntuario(ProntuarioVO prontuario) {
		UserDetails utente = SecurityUtils.getUser();
		prontuario = prontuarioDispatcher.salvaLeggeProntuario(prontuario, utente);
		return Response.ok().entity(prontuario).build();
	}

	@DELETE
	@Path("/eliminaLeggeProntuario")
	public Response eliminaLeggeProntuario(@QueryParam("idLettera") Long idLettera) {
		UserDetails utente = SecurityUtils.getUser();
		prontuarioDispatcher.eliminaLeggeProntuario(idLettera, utente);
		return Response.ok().build();
	}

	@GET
	@Path("/getAmbiti")
	public Response getAmbiti() {
		List<AmbitoVO> lista = prontuarioDispatcher.getAmbiti();
		return Response.ok().entity(lista).build();
	}

	@POST
	@Path("/salvaAmbito")
	public Response salvaAmbito(@Valid @NotNull(message = "RESCON16") AmbitoVO ambito) {
		UserDetails utente = SecurityUtils.getUser();
		try {
			prontuarioDispatcher.salvaAmbito(ambito, utente);
		}catch(Exception ex) {
			//CONAM-79: gestione errore in caso di ambito duplicato
			ExceptionVO exceptionVO = new ExceptionVO(ErrorCode.INSERIMENTO_AMBITO, "Errore in fase di creazione ambito. Verificare i parametri insertiti. Possibile ambito esistente ", null);
			return Response.ok().entity(exceptionVO).build();
		}		
		
		return Response.ok().build();
	}

	@GET
	@Path("/getAmbitiEliminabili")
	public Response getAmbitiEliminabili() {
		List<AmbitoVO> lista = prontuarioDispatcher.getAmbitiEliminabili();
		return Response.ok().entity(lista).build();
	}

	@DELETE
	@Path("/eliminaAmbito")
	public Response eliminaAmbito(@QueryParam("idAmbito") Integer idAmbito) {
		UserDetails utente = SecurityUtils.getUser();
		prontuarioDispatcher.eliminaAmbito(idAmbito, utente);
		return Response.ok().build();
	}

}
