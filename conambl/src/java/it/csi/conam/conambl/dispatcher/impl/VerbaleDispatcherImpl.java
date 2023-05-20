/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.verbale.*;
import it.csi.conam.conambl.dispatcher.VerbaleDispatcher;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.request.verbale.RicercaVerbaleRequest;
import it.csi.conam.conambl.request.verbale.SalvaAzioneRequest;
import it.csi.conam.conambl.request.verbale.SalvaStatoManualeRequest;
import it.csi.conam.conambl.request.verbale.SetRecidivoVerbaleSoggettoRequest;
import it.csi.conam.conambl.response.AzioneVerbaleResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.IstruttoreVO;
import it.csi.conam.conambl.vo.UtenteVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.verbale.*;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.RiepilogoAllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Component
public class VerbaleDispatcherImpl implements VerbaleDispatcher {

	@Autowired
	private VerbaleService verbaleService;

	@Autowired
	private SoggettoVerbaleService soggettoVerbaleService;

	@Autowired
	private RicercaVerbaleService ricercaVerbaleService;

	@Autowired
	private AllegatoVerbaleService allegatoVerbaleService;

	@Autowired
	private AzioneVerbaleService azioneVerbaleService;

	@Override
	public Integer salvaVerbale(VerbaleVO verbale, UserDetails userDetails) {
		return verbaleService.salvaVerbale(verbale, userDetails);
	}

	@Override
	public VerbaleVO getVerbaleById(Integer id, UserDetails userDetails, boolean includeEliminati) {
		return verbaleService.getVerbaleById(id, userDetails, includeEliminati, true, true);
	}

	@Override
	public void eliminaVerbale(Integer id, UserDetails userDetails) {
		verbaleService.eliminaVerbale(id, userDetails);
	}

	@Override
	public SoggettoVO ricercaSoggetto(MinSoggettoVO minSoggettoVO, UserDetails userDetails) {
		return soggettoVerbaleService.ricercaSoggetto(minSoggettoVO, userDetails);
	}

	@Override
	public SoggettoVO ricercaSoggettoPerPIva(MinSoggettoVO minSoggettoVO, UserDetails userDetails) {
		return soggettoVerbaleService.ricercaSoggettoPerPIva(minSoggettoVO, userDetails);
	}

	@Override
	public List<RuoloSoggettoVO> getRuoliSoggetto() {
		return soggettoVerbaleService.getRuoliSoggetto();
	}

	@Override
	public RiepilogoVerbaleVO riepilogo(Integer id, UserDetails userDetails) {
		return verbaleService.riepilogo(id, userDetails);
	}

	@Override
	public RiepilogoVerbaleVO riepilogoVerbaleAudizione(Integer id, UserDetails userDetails) {
		return verbaleService.riepilogoVerbaleAudizione(id, userDetails);
	}

	@Override
	public List<SoggettoVO> getSoggettiByIdVerbale(Integer id, UserDetails userDetails, Boolean controlloUtente) {
		return soggettoVerbaleService.getSoggettiByIdVerbale(id, userDetails, controlloUtente);
	}
	
	@Override
	public List<SoggettoVO> getSoggettiByIdVerbaleConvocazione(Integer id, UserDetails userDetails, Boolean controlloUtente) {
		return soggettoVerbaleService.getSoggettiByIdVerbaleConvocazione(id, userDetails, controlloUtente);
	}

	@Override
	public List<SoggettoVO> getSoggettiByIdVerbaleAudizione(Integer id) {
		return soggettoVerbaleService.getSoggettiByIdVerbaleAudizione(id);
	}

	@Override
	public SoggettoVO salvaSoggetto(Integer id, SoggettoVO soggetto, UserDetails userDetails) {
		return soggettoVerbaleService.salvaSoggetto(id, soggetto, userDetails);
//		return soggettoVerbaleService.salvaSoggettoPregressi(id, soggetto, userDetails);
	}

	@Override
	public void eliminaSoggettoByIdVerbaleSoggetto(Integer id, UserDetails userDetails) {
		soggettoVerbaleService.eliminaSoggettoByIdVerbaleSoggetto(id, userDetails);
	}

	@Override
	public List<StatoVerbaleVO> getStatiRicercaVerbale() {
		return ricercaVerbaleService.getStatiRicercaVerbale();
	}

	@Override
	public List<StatoManualeVO> getStatiManuali() {
		return ricercaVerbaleService.getStatiManuali();
	}
	
	@Override
	public List<MinVerbaleVO> ricercaVerbale(RicercaVerbaleRequest request, UserDetails userDetails) {
		return ricercaVerbaleService.ricercaVerbale(request, userDetails);
	}

	@Override
	public RiepilogoAllegatoVO getAllegatiByIdVerbale(Integer id, UserDetails userDetails) {
		return allegatoVerbaleService.getAllegatiByIdVerbale(id, userDetails, true);
	}

	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliVerbale(Integer id, String tipoDocumento, boolean aggiungiCategoriaEmail) {
		return allegatoVerbaleService.getTipologiaAllegatiAllegabiliVerbale(id, tipoDocumento, aggiungiCategoriaEmail);
	}

	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiVerbale(String tipoDocumento) {
		return allegatoVerbaleService.getTipologiaAllegatiVerbale(tipoDocumento);
	}

	@Override
	public List<IstruttoreVO> getIstruttoreByVerbale(Integer idVerbale, UserDetails userDetails) {
		return azioneVerbaleService.getIstruttoreByVerbale(idVerbale, userDetails);
	}

	@Override
	public UtenteVO getUtenteRuolo(Integer idVerbale, UserDetails userDetails) {
		return azioneVerbaleService.getUtenteRuolo(idVerbale, userDetails);
	}

	@Override
	public AzioneVerbaleResponse getAzioniVerbale(Integer id, UserDetails userDetails) {
		return azioneVerbaleService.getAzioniVerbale(id, userDetails);
	}

	@Override
	public AllegatoVO salvaAllegato(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		return allegatoVerbaleService.salvaAllegato(data, file, userDetails, false);
	}

	@Override
	public AllegatoVO salvaAllegatiMultipli(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		return allegatoVerbaleService.salvaAllegatiMultipli(data, file, userDetails, false);
		// TODO - 20210311 PP, test per riportare numero protocollo su allegati al master
//		return allegatoVerbaleService.salvaAllegatiMultipliNew(data, file, userDetails, false);
	}

	@Override
	public void salvaAzioneVerbale(SalvaAzioneRequest salvaAzione, UserDetails userDetails) {
		azioneVerbaleService.salvaAzioneVerbale(salvaAzione, userDetails);
	}

	@Override
	public void eliminaAllegato(Integer idVerbale, Integer idAllegato, UserDetails userDetails) {
		allegatoVerbaleService.eliminaAllegato(idVerbale, idAllegato, userDetails);
	}

	@Override
	public Boolean isTipoAllegatoAllegabile(Integer id, String codiceDocumento, UserDetails userDetails) {
		return azioneVerbaleService.isTipoAllegatoAllegabile(id, codiceDocumento, userDetails);
	}

	@Override
	public Boolean isEnableCaricaVerbaleAudizione(Integer id, UserDetails userDetails) {
		return azioneVerbaleService.isEnableCaricaVerbaleAudizione(id, userDetails);
	}

	@Override
	public IsCreatedVO isConvocazioneCreated(IsCreatedVO request) {
		return allegatoVerbaleService.isConvocazioneCreated(request);
	}

	@Override
	public IsCreatedVO isVerbaleAudizioneCreated(IsCreatedVO request) {
		return allegatoVerbaleService.isVerbaleAudizioneCreated(request);
	}

	// 20200706_LC
	@Override
	public MessageVO salvaAllegatoProtocollatoVerbale(SalvaAllegatiProtocollatiRequest request, UserDetails userDetails) {
		return allegatoVerbaleService.salvaAllegatoProtocollatoVerbale(request, userDetails, false);	
	}
	
	
	// 20201223_LC
	@Override
	public VerbaleVO getVerbaleByIdOrdinanza(Integer idOrdinanza) {
		return verbaleService.getVerbaleByIdOrdinanza(idOrdinanza);
	}

	//Gestione Stato manuale
	
	@Override
	public void salvaStatoManuale(SalvaStatoManualeRequest salvaStatoManuale, UserDetails userDetails) {
		Long idStatoManuale=Long.valueOf(salvaStatoManuale.getIdStatoManuale());  
		verbaleService.salvaStatoManuale(salvaStatoManuale.getId(), idStatoManuale, userDetails);
	}

	@Override
	public MessageVO getMessaggioManualeByIdOrdinanza(Integer idOrdinanza){  
		return verbaleService.getMessaggioManualeByIdOrdinanza(idOrdinanza);
	}

	@Override
	public MessageVO getMessaggioManualeByIdVerbale(Integer idVerbale){
		return verbaleService.getMessaggioManualeByIdVerbale(idVerbale);
	}

	@Override
	public MessageVO getMessaggioManualeByIdOrdinanzaVerbaleSoggetto(Integer IdOrdinanzaVerbaleSoggetto){
		return verbaleService.getMessaggioManualeByIdOrdinanzaVerbaleSoggetto(IdOrdinanzaVerbaleSoggetto);
	}
	
	
	//FINE - Gestione Stato manuale

	/*LUCIO 2021/04/21 - Gestione casi di recidività*/
	@Override
	public List<VerbaleSoggettoVO> getVerbaleSoggettoByIdSoggetto(Integer idSoggetto) {
		return verbaleService.getVerbaleSoggettoByIdSoggetto(idSoggetto);
	}
	
	
	@Override
	public VerbaleSoggettoVORaggruppatoPerSoggetto getVerbaleSoggettoRaggruppatoPerSoggettoByIdSoggetto(
		Integer idSoggetto,
		Boolean recidivo
	) {
		return verbaleService.getVerbaleSoggettoRaggruppatoPerSoggettoByIdSoggetto(
			idSoggetto,
			recidivo
		);
	}
	

	@Override
	public List<VerbaleSoggettoVO> setRecidivoVerbaleSoggetto(
		List<SetRecidivoVerbaleSoggettoRequest> setRecidivoVerbaleSoggettoRequest,
		UserDetails userDetails
	) {
		List<VerbaleSoggettoVO> responseList = new ArrayList<VerbaleSoggettoVO>();
		for (SetRecidivoVerbaleSoggettoRequest singleRequest : setRecidivoVerbaleSoggettoRequest) {
			VerbaleSoggettoVO soggetto = verbaleService.setRecidivoVerbaleSoggetto(
				singleRequest.getId(),
				singleRequest.getRecidivo(),
				userDetails
			);
			responseList.add(soggetto);
		}
		return responseList;
	}
	/*LUCIO 2021/04/21 - FINE Gestione casi di recidività*/
}
