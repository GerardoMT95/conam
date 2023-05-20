/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.ordinanza;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import it.csi.conam.conambl.business.service.notifica.NotificaService;
import it.csi.conam.conambl.business.service.ordinanza.AllegatoOrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.AzioneOrdinanzaPregressiService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.request.ordinanza.AzioneOrdinanzaRequest;
import it.csi.conam.conambl.response.AzioneOrdinanzaPregressiResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class AzioneOrdinanzaPregressiServiceImpl implements AzioneOrdinanzaPregressiService {

	@Autowired
	private AllegatoOrdinanzaService allegatoOrdinanzaService;

	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;

	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;

	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;

	@Autowired
	private NotificaService ordinanzaNotificaService;
	
	@Autowired
	private CnmTSollecitoRepository cnmTSollecitoRepository;
	
	@Autowired
	private CnmRAllegatoOrdVerbSogRepository cnmRAllegatoOrdVerbSogRepository;

	@Override
	public AzioneOrdinanzaPregressiResponse azioneOrdinanza(AzioneOrdinanzaRequest azioniOrdinanzaRequest, UserDetails userDetails) {
		if (azioniOrdinanzaRequest == null) {
			throw new IllegalArgumentException("azioniOrdinanzaRequest ==null");
		}
		Integer idOrdinanza = azioniOrdinanzaRequest.getId();
		String tipoDocumento = azioniOrdinanzaRequest.getTipoDocumento();
		if (idOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza ==null");

		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza ==null");

		Boolean isAllegatoEnable = Boolean.FALSE;
		AzioneOrdinanzaPregressiResponse azioneOrdinanzaResponse = new AzioneOrdinanzaPregressiResponse();
		List<TipoAllegatoVO> listTipoAllegatiAllegabili = allegatoOrdinanzaService.getTipologiaAllegatiAllegabiliByOrdinanza(idOrdinanza, tipoDocumento, false);
		if (listTipoAllegatiAllegabili != null && !listTipoAllegatiAllegabili.isEmpty())
			isAllegatoEnable = Boolean.TRUE;

		Date dataScadenza = cnmTOrdinanza.getDataScadenzaOrdinanza();

		long idTipoOrdinanza = cnmTOrdinanza.getCnmDTipoOrdinanza().getIdTipoOrdinanza();
		boolean isRichiestaBollettiniInviata = allegatoOrdinanzaService.isRichiestaBollettiniInviata(cnmTOrdinanza);
		Boolean isLetteraOrdinanzaCreata = allegatoOrdinanzaService.isLetteraOrdinanzaCreata(cnmTOrdinanza);
		Boolean dowloadBollettiniEnable = isRichiestaBollettiniInviata && isLetteraOrdinanzaCreata && dataScadenza != null && idTipoOrdinanza != Constants.ID_TIPO_ORDINANZA_ARCHIVIATO;
//		20201117_ET per le ordinanze pregresse non e' possibile generare i bollettini e lettere
		Boolean bollettiniDaInviareEnable = false;
//		Boolean bollettiniDaInviareEnable = !isRichiestaBollettiniInviata && isLetteraOrdinanzaCreata && dataScadenza != null && idTipoOrdinanza != Constants.ID_TIPO_ORDINANZA_ARCHIVIATO;
		azioneOrdinanzaResponse.setLetteraDaCompilareEnable(false);
//		azioneOrdinanzaResponse.setLetteraDaCompilareEnable(!isLetteraOrdinanzaCreata);
		Boolean isNotificaOrdinanzaCreata = ordinanzaNotificaService.isNotificaCreata(cnmTOrdinanza);
		Boolean downloadNotificaOrdinanza = isNotificaOrdinanzaCreata && isLetteraOrdinanzaCreata;

		azioneOrdinanzaResponse.setAggiungiAllegatoEnable(isAllegatoEnable);

		azioneOrdinanzaResponse.setDownloadBollettiniEnable(dowloadBollettiniEnable);
		azioneOrdinanzaResponse.setBollettiniDaInviareEnable(bollettiniDaInviareEnable);

		azioneOrdinanzaResponse.setAggiungiNotificaEnable(!isNotificaOrdinanzaCreata && isLetteraOrdinanzaCreata);
		azioneOrdinanzaResponse.setVisualizzaNotificaEnable(downloadNotificaOrdinanza);

		azioneOrdinanzaResponse.setDettaglioSollecitoEnable(false);
		azioneOrdinanzaResponse.setDettaglioPianoEnable(false);
		azioneOrdinanzaResponse.setDettaglioDispGiudiceEnable(false);
		azioneOrdinanzaResponse.setDettaglioRicevutaPagamentoEnable(false);
		
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		for(@SuppressWarnings("unused") CnmRSoggRata cnmRSoggRata: cnmRSoggRataRepository.findByCnmROrdinanzaVerbSogIn(cnmROrdinanzaVerbSogList)) {
			azioneOrdinanzaResponse.setDettaglioPianoEnable(true);
			break;
		}
		for(@SuppressWarnings("unused") CnmTSollecito cnmTSollecito: cnmTSollecitoRepository.findByCnmROrdinanzaVerbSogIn(cnmROrdinanzaVerbSogList)) {
			azioneOrdinanzaResponse.setDettaglioSollecitoEnable(true);
			break;
		}
		
		List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogList = cnmRAllegatoOrdVerbSogRepository.findByCnmROrdinanzaVerbSogIn(cnmROrdinanzaVerbSogList);
		if (!cnmRAllegatoOrdVerbSogList.isEmpty()) {
			for(CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog : cnmRAllegatoOrdVerbSogList) {
				if(cnmRAllegatoOrdVerbSog.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getId()) {
					azioneOrdinanzaResponse.setDettaglioDispGiudiceEnable(true);
				}else if(cnmRAllegatoOrdVerbSog.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.RICEVUTA_PAGAMENTO_ORDINANZA.getId()) {
					azioneOrdinanzaResponse.setDettaglioRicevutaPagamentoEnable(true);
				}
			}
		}
		
		return azioneOrdinanzaResponse;
	}

	@Override
	public AzioneOrdinanzaPregressiResponse azioneOrdinanzaSoggetto(AzioneOrdinanzaRequest azioniOrdinanzaRequest, UserDetails userDetails) {
		if (azioniOrdinanzaRequest == null) {
			throw new IllegalArgumentException("azioniOrdinanzaRequest ==null");
		}
		Integer idOrdinanza = azioniOrdinanzaRequest.getId();
		String tipoDocumento = azioniOrdinanzaRequest.getTipoDocumento();
		if (idOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza ==null");

		AzioneOrdinanzaPregressiResponse azioneOrdinanzaResponse = new AzioneOrdinanzaPregressiResponse();
		azioneOrdinanzaResponse.setAggiungiAllegatoEnable(Boolean.FALSE);
		List<TipoAllegatoVO> listTipoAllegatiAllegabili;

		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		Collection<Integer> idcol = Collections2.transform(cnmROrdinanzaVerbSogList, new Function<CnmROrdinanzaVerbSog, Integer>() {
			@Override
			public Integer apply(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
				return cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog();
			}
		});
		for (Integer i : idcol) {
			listTipoAllegatiAllegabili = allegatoOrdinanzaService.getTipologiaAllegatiAllegabiliByOrdinanzaSoggetto( Collections.singletonList(i), tipoDocumento, false);
			if (listTipoAllegatiAllegabili != null && !listTipoAllegatiAllegabili.isEmpty()) {
				azioneOrdinanzaResponse.setAggiungiAllegatoEnable(Boolean.TRUE);
				return azioneOrdinanzaResponse;
			}
		}

		azioneOrdinanzaResponse.setDettaglioSollecitoEnable(false);
		azioneOrdinanzaResponse.setDettaglioPianoEnable(false);
		azioneOrdinanzaResponse.setDettaglioDispGiudiceEnable(false);
		azioneOrdinanzaResponse.setDettaglioRicevutaPagamentoEnable(false);

		for(@SuppressWarnings("unused") CnmRSoggRata cnmRSoggRata: cnmRSoggRataRepository.findByCnmROrdinanzaVerbSogIn(cnmROrdinanzaVerbSogList)) {
			azioneOrdinanzaResponse.setDettaglioPianoEnable(true);
			break;
		}
		for(@SuppressWarnings("unused") CnmTSollecito cnmTSollecito: cnmTSollecitoRepository.findByCnmROrdinanzaVerbSogIn(cnmROrdinanzaVerbSogList)) {
			azioneOrdinanzaResponse.setDettaglioSollecitoEnable(true);
			break;
		}
		
		for(TipoAllegatoVO tipoAllegato: allegatoOrdinanzaService.getTipologiaAllegatiOrdinanzaByCnmTOrdinanza(cnmTOrdinanza)) {
			if(tipoAllegato.getId() == TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getId()) {
				azioneOrdinanzaResponse.setDettaglioDispGiudiceEnable(true);
			}else if(tipoAllegato.getId() == TipoAllegato.RICEVUTA_PAGAMENTO_ORDINANZA.getId()) {
				azioneOrdinanzaResponse.setDettaglioRicevutaPagamentoEnable(true);
			}
		}
		
		return azioneOrdinanzaResponse;
	}

}
