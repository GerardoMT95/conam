/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.ordinanza.*;
import it.csi.conam.conambl.dispatcher.OrdinanzaPregressiDispatcher;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.request.ordinanza.AzioneOrdinanzaRequest;
import it.csi.conam.conambl.request.ordinanza.SalvaOrdinanzaPregressiRequest;
import it.csi.conam.conambl.request.ordinanza.SalvaStatoOrdinanzaRequest;
import it.csi.conam.conambl.response.*;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.ordinanza.StatoSoggettoOrdinanzaVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrdinanzaPregressiDispatcherImpl implements OrdinanzaPregressiDispatcher {

	@Autowired
	private RicercaOrdinanzaService ricercaOrdinanzaService;

	@Autowired
	private OrdinanzaPregressiService ordinanzaPregressiService;

	@Autowired
	private SoggettoOrdinanzaService soggettoOrdinanzaService;

	@Autowired
	private AllegatoOrdinanzaService allegatoOrdinanzaService;

	@Autowired
	private AzioneOrdinanzaPregressiService azioneOrdinanzaPregressiService;

	@Autowired
	private AllegatoOrdinanzaPregressiService allegatoOrdinanzaPregressiService;

//	@Autowired
//	private AzioneOrdinanzaService azioneOrdinanzaService;

	@Override
	public StatiOrdinanzaResponse getStatiOrdinanza(Integer idOrdinanza) {
		return ricercaOrdinanzaService.getStatiOrdinanzaPregressi(idOrdinanza);
	}

	@Override
	public SalvaOrdinanzaPregressiResponse salvaOrdinanza(SalvaOrdinanzaPregressiRequest request, UserDetails userDetails) {
		return ordinanzaPregressiService.salvaOrdinanza(request, userDetails);
	}

	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiCreaOrdinanza() {
		return allegatoOrdinanzaService.getTipologiaAllegatiCreaOrdinanza();
	}

	@Override
	public List<StatoSoggettoOrdinanzaVO> getStatiOrdinanzaSoggettoInCreazioneOrdinanza() {
		return soggettoOrdinanzaService.getStatiOrdinanzaSoggettoInCreazioneOrdinanza();
	}

	@Override
	public MessageVO salvaStatoOrdinanza(SalvaStatoOrdinanzaRequest salvaStatoOrdinanza, UserDetails userDetails) {
		return ordinanzaPregressiService.salvaStatoOrdinanza(salvaStatoOrdinanza, userDetails);
	}
	
	@Override
	public MessageVO salvaAllegatoProtocollatoOrdinanzaSoggetto(SalvaAllegatiProtocollatiRequest request,
			UserDetails userDetails) {
		return allegatoOrdinanzaService.salvaAllegatoProtocollatoOrdinanzaSoggetto(request, userDetails, true);
	}

	@Override
	public AllegatoVO salvaAllegatoOrdinanza(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		return allegatoOrdinanzaService.salvaAllegatoOrdinanza(data, file, userDetails, true);
	}
	
	@Override
	public void salvaAllegatoOrdinanzaSoggetto(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		allegatoOrdinanzaService.salvaAllegatoOrdinanzaSoggetto(data, file, userDetails, true);
	}
	
	@Override
	public MessageVO salvaAllegatoProtocollatoOrdinanza(SalvaAllegatiProtocollatiRequest request, UserDetails userDetails) {
		return allegatoOrdinanzaService.salvaAllegatoProtocollatoOrdinanza(request, userDetails, true);
	}
	
	@Override
	public AzioneOrdinanzaPregressiResponse azioneOrdinanzaSoggetto(AzioneOrdinanzaRequest azioneOrdinanzaRequest, UserDetails userDetails) {
		return azioneOrdinanzaPregressiService.azioneOrdinanzaSoggetto(azioneOrdinanzaRequest, userDetails);
	}

	@Override
	public AzioneOrdinanzaPregressiResponse azioneOrdinanza(AzioneOrdinanzaRequest azioneOrdinanzaRequest, UserDetails userDetails) {
		return azioneOrdinanzaPregressiService.azioneOrdinanza(azioneOrdinanzaRequest, userDetails);
	}

	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliByOrdinanza(Integer idOrdinanza, String tipoDocumento, boolean aggiungiCategoriaEmail) {
		return allegatoOrdinanzaPregressiService.getTipologiaAllegatiAllegabiliByOrdinanza(idOrdinanza, tipoDocumento, aggiungiCategoriaEmail);
	}
	

	@Override
	public List<RicevutaPagamentoResponse> getRicevutePagamentoByIdOrdinanza(Integer idOrdinanza) {
		return ordinanzaPregressiService.getRicevutePagamentoByIdOrdinanza(idOrdinanza);
	}

	@Override
	public List<DatiSentenzaPregressiResponse> getDatiSentenzaByIdOrdinanza(Integer idOrdinanza) {
		return ordinanzaPregressiService.getDatiSentenzaByIdOrdinanza(idOrdinanza);
	}
	
	@Override
	public List<MessageVO> salvaAllegatiMultipli(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		return allegatoOrdinanzaService.salvaAllegatiMultipli(data, file, userDetails, false);
	}

}
