/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.verbale.*;
import it.csi.conam.conambl.common.exception.VerbalePregressoValidationException;
import it.csi.conam.conambl.dispatcher.VerbalePregressiDispatcher;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiSoggettiRequest;
import it.csi.conam.conambl.request.verbale.SalvaAzioneRequest;
import it.csi.conam.conambl.response.AzioneVerbaleResponse;
import it.csi.conam.conambl.response.StatiVerbaleResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IstruttoreVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoPregressiVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.vo.verbale.VerbaleVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.RiepilogoAllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Paolo Piedeplaumbo
 * @date 15/09/2020
 */
@Component
public class VerbalePregressiDispatcherImpl implements VerbalePregressiDispatcher {

	@Autowired
	private SoggettoVerbaleService soggettoVerbaleService;

	@Autowired
	private VerbalePregressiService verbalePregressiService;
	
	@Autowired
	private AllegatoVerbaleSoggettoPregressiService allegatoVerbaleSoggettoPregressiService;

	@Autowired
	private AllegatoVerbaleService allegatoVerbaleService;
	
	@Autowired
	private AzioneVerbalePregressiService azioneVerbalePregressiService;
	
	@Override
	public Integer salvaVerbale(VerbaleVO verbale, UserDetails userDetails) throws VerbalePregressoValidationException {
		return verbalePregressiService.salvaVerbale(verbale, userDetails);
	}
	
	@Override
	public SoggettoPregressiVO ricercaSoggetto(MinSoggettoVO minSoggettoVO, UserDetails userDetails) {
		return soggettoVerbaleService.ricercaSoggettoPregressi(minSoggettoVO, userDetails);
	}
	
	@Override
	public SoggettoVO salvaSoggetto(Integer id, SoggettoVO soggetto, UserDetails userDetails) {
		return soggettoVerbaleService.salvaSoggettoPregressi(id, soggetto, userDetails);
	}

	@Override
	public List<SoggettoVO> getSoggettiByIdVerbale(Integer id, UserDetails userDetails, Boolean controlloUtente) {
		return soggettoVerbaleService.getSoggettiByIdVerbalePregressi(id, userDetails, controlloUtente);
	}

	@Override
	public SoggettoPregressiVO ricercaSoggettoPerPIva(MinSoggettoVO minSoggettoVO, UserDetails userDetails) {
		return soggettoVerbaleService.ricercaSoggettoPerPIvaPregressi(minSoggettoVO, userDetails);
	}

	@Override
	public VerbaleVO getVerbaleById(Integer id, UserDetails userDetails, boolean includeEliminati) {
		return verbalePregressiService.getVerbaleById(id, userDetails, includeEliminati, true, false);
	}

	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliVerbale(Integer id, String tipoDocumento, boolean aggiungiCategoriaEmail) {
		return allegatoVerbaleService.getTipologiaAllegatiAllegabiliVerbalePregressi(id, tipoDocumento, aggiungiCategoriaEmail);
	}

	@Override
	public List<IstruttoreVO> getIstruttoreByVerbale(Integer idVerbale, UserDetails userDetails) {
		return azioneVerbalePregressiService.getIstruttoreByVerbale(idVerbale, userDetails);
	}
	
	@Override
	public StatiVerbaleResponse getStatiVerbale(Integer id, UserDetails userDetails) {
		return azioneVerbalePregressiService.getStatiVerbalePregresso(id, userDetails, false);
	}

	@Override
	public MessageVO salvaStatoVerbale(SalvaAzioneRequest salvaAzione, UserDetails userDetails) {
		return azioneVerbalePregressiService.salvaStatoVerbalePregresso(salvaAzione, userDetails);
	}

	@Override
	public AllegatoVO salvaAllegatiMultipli(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		return allegatoVerbaleService.salvaAllegatiMultipli(data, file, userDetails, true);
	}

	@Override
	public AllegatoVO salvaAllegato(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		return allegatoVerbaleService.salvaAllegato(data, file, userDetails, true);
	}

	@Override
	public MessageVO salvaAllegatoProtocollatoVerbale(SalvaAllegatiProtocollatiRequest request, UserDetails userDetails) {
		return allegatoVerbaleService.salvaAllegatoProtocollatoVerbale(request, userDetails, true);	
	}

	@Override
	public void checkDatiVerbale(VerbaleVO verbale, UserDetails userDetails) throws VerbalePregressoValidationException {
		verbalePregressiService.checkDatiVerbale(verbale, userDetails);
	}

	@Override
	public AzioneVerbaleResponse getAzioniVerbale(Integer id, UserDetails userDetails) {
		return azioneVerbalePregressiService.getAzioniVerbale(id, userDetails);
		
	}

	@Override
	public RiepilogoAllegatoVO getAllegatiByIdVerbale(Integer id, UserDetails userDetails) {
		return allegatoVerbaleService.getAllegatiByIdVerbale(id, userDetails, false);
	}

	@Override
	public MessageVO salvaVerbaleAudizione(SalvaAllegatiProtocollatiSoggettiRequest request, UserDetails userDetails) {
		return allegatoVerbaleSoggettoPregressiService.salvaVerbaleAudizione(request, userDetails);
	}

	@Override
	public MessageVO salvaConvocazioneAudizione(SalvaAllegatiProtocollatiSoggettiRequest request, UserDetails userDetails) {
		return allegatoVerbaleSoggettoPregressiService.salvaConvocazioneAudizione(request, userDetails);
	}
}
