/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.ordinanza.*;
import it.csi.conam.conambl.dispatcher.OrdinanzaDispatcher;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.request.ordinanza.AzioneOrdinanzaRequest;
import it.csi.conam.conambl.request.ordinanza.RicercaOrdinanzaRequest;
import it.csi.conam.conambl.request.ordinanza.RicercaSoggettiOrdinanzaRequest;
import it.csi.conam.conambl.response.AzioneOrdinanzaResponse;
import it.csi.conam.conambl.response.DatiSentenzaResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.ordinanza.MinOrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.OrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.StatoOrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.StatoSoggettoOrdinanzaVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrdinanzaDispatcherImpl implements OrdinanzaDispatcher {

	@Autowired
	private RicercaOrdinanzaService ricercaOrdinanzaService;

	@Autowired
	private OrdinanzaService ordinanzaService;

	@Autowired
	private SoggettoOrdinanzaService soggettoOrdinanzaService;

	@Autowired
	private AllegatoOrdinanzaService allegatoOrdinanzaService;

	@Autowired
	private AzioneOrdinanzaService azioneOrdinanzaService;

	@Override
	public List<MinOrdinanzaVO> ricercaOrdinanza(RicercaOrdinanzaRequest request, UserDetails userDetails) {
		return ricercaOrdinanzaService.ricercaOrdinanza(request, userDetails);
	}

	@Override
	public OrdinanzaVO dettaglioOrdinanzaById(Integer idOrdinanza, UserDetails userDetails) {
		return ordinanzaService.dettaglioOrdinanzaById(idOrdinanza, userDetails);
	}

	@Override
	public List<SoggettoVO> getSoggettiByIdOrdinanza(Integer id, UserDetails userDetails) {
		return soggettoOrdinanzaService.getSoggettiByIdOrdinanza(id, userDetails);
	}

	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliByOrdinanza(Integer idOrdinanza, String tipoDocumento, boolean aggiungiCategoriaEmail) {
		return allegatoOrdinanzaService.getTipologiaAllegatiAllegabiliByOrdinanza(idOrdinanza, tipoDocumento, aggiungiCategoriaEmail);
	}

	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliByOrdinanzaSoggetto(List<Integer> idSoggettoOrdinanza, String tipoDocumento, boolean aggiungiCategoriaEmail) {
		return allegatoOrdinanzaService.getTipologiaAllegatiAllegabiliByOrdinanzaSoggetto(idSoggettoOrdinanza, tipoDocumento, aggiungiCategoriaEmail);
	}

	@Override
	public AllegatoVO salvaAllegatoOrdinanza(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		return allegatoOrdinanzaService.salvaAllegatoOrdinanza(data, file, userDetails, false);
	}

	@Override
	public void salvaAllegatoOrdinanzaSoggetto(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		allegatoOrdinanzaService.salvaAllegatoOrdinanzaSoggetto(data, file, userDetails, false);
	}

	@Override
	public AzioneOrdinanzaResponse azioneOrdinanzaSoggetto(AzioneOrdinanzaRequest azioneOrdinanzaRequest, UserDetails userDetails) {
		return azioneOrdinanzaService.azioneOrdinanzaSoggetto(azioneOrdinanzaRequest, userDetails);
	}

	@Override
	public AzioneOrdinanzaResponse azioneOrdinanza(AzioneOrdinanzaRequest azioneOrdinanzaRequest, UserDetails userDetails) {
		return azioneOrdinanzaService.azioneOrdinanza(azioneOrdinanzaRequest, userDetails);

	}

	@Override
	public OrdinanzaVO dettaglioOrdinanzaByIdOrdinanzaSoggetti(List<Integer> idOrdinanzaSoggetti, UserDetails utente) {
		return ordinanzaService.dettaglioOrdinanzaByIdOrdinanzaSoggetti(idOrdinanzaSoggetti, utente);
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
	public Integer salvaOrdinanza(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		return ordinanzaService.salvaOrdinanza(data, file, userDetails);
	}

	@Override
	public List<AllegatoVO> getAllegatiByIdOrdinanza(Integer idOrdinanza) {
		return allegatoOrdinanzaService.getAllegatiByIdOrdinanza(idOrdinanza);
	}

	@Override
	public List<AllegatoVO> getAllegatiByIdOrdinanzaVerbaleSoggetto(List<Integer> idOrdinanzaVerbaleSoggettoList) {
		return allegatoOrdinanzaService.getAllegatiByIdOrdinanzaVerbaleSoggetto(idOrdinanzaVerbaleSoggettoList);
	}

	@Override
	public List<SoggettoVO> ricercaSoggetti(RicercaSoggettiOrdinanzaRequest ricercaSoggettiOrdinanzaRequest, boolean pregressi) {
		return ricercaOrdinanzaService.ricercaSoggetti(ricercaSoggettiOrdinanzaRequest, pregressi);
	}

	@Override
	public void inviaRichiestaBollettiniByIdOrdinanza(Integer idOrdinanza) {
		allegatoOrdinanzaService.inviaRichiestaBollettiniByIdOrdinanza(idOrdinanza);
	}

	// 20200825_LC nuovo type per doc multiplo
	@Override
	public List<DocumentoScaricatoVO> dowloadBollettiniOrdinanza(Integer idOrdinanza) {
		return allegatoOrdinanzaService.downloadBollettiniByIdOrdinanza(idOrdinanza);
	}

	// 20200825_LC nuovo type per doc multiplo
	@Override
	public List<DocumentoScaricatoVO> dowloadLettera(Integer idOrdinanza) {
		return allegatoOrdinanzaService.downloadLetteraOrdinanza(idOrdinanza);
	}

	@Override
	public List<StatoOrdinanzaVO> getStatiOrdinanza() {
		return ricercaOrdinanzaService.getStatiOrdinanza();
	}

	@Override
	public DatiSentenzaResponse getDatiSentenzaByIdOrdinanzaVerbaleSoggetto(Integer idOrdinanzaVerbaleSoggetto) {
		return ordinanzaService.getDatiSentenzaByIdOrdinanzaVerbaleSoggetto(idOrdinanzaVerbaleSoggetto);
	}

	@Override
	public IsCreatedVO isLetteraSaved(IsCreatedVO request) {
		return allegatoOrdinanzaService.isLetteraSaved(request);
	}

	@Override
	public MessageVO salvaAllegatoProtocollatoOrdinanzaSoggetto(SalvaAllegatiProtocollatiRequest request,
			UserDetails userDetails) {
		return allegatoOrdinanzaService.salvaAllegatoProtocollatoOrdinanzaSoggetto(request, userDetails, false);
		
	}
	
	// 20200715_LC
	@Override
	public MessageVO salvaAllegatoProtocollatoOrdinanza(SalvaAllegatiProtocollatiRequest request, UserDetails userDetails) {
		return allegatoOrdinanzaService.salvaAllegatoProtocollatoOrdinanza(request, userDetails, false);
	}

	
	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiCreaOrdinanzaAnnullamento(Integer idOrdinanzaAnnullata) {
		return allegatoOrdinanzaService.getTipologiaAllegatiCreaOrdinanzaAnnullamento(idOrdinanzaAnnullata);
	}
	
	@Override
	public Integer salvaOrdinanzaAnnullamento(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		return ordinanzaService.salvaOrdinanzaAnnullamento(data, file, userDetails);
	}
	
	@Override
	public List<MessageVO> salvaAllegatiMultipli(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		return allegatoOrdinanzaService.salvaAllegatiMultipli(data, file, userDetails, false);
	}
	
}
