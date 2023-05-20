/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.pianorateizzazione;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import it.csi.conam.conambl.business.service.ordinanza.RicercaOrdinanzaService;
import it.csi.conam.conambl.business.service.pianorateizzazione.RicercaPianoRateizzazioneService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.MinPianoRateizzazioneEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoPianoRateEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.VerbaleEntityMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.integration.specification.CnmTPianoRateSpecification;
import it.csi.conam.conambl.request.pianorateizzazione.RicercaPianoRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.VerbaleSearchParam;
import it.csi.conam.conambl.vo.pianorateizzazione.MinPianoRateizzazioneVO;
import it.csi.conam.conambl.vo.pianorateizzazione.StatoPianoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author riccardo.bova
 * @date 03 dic 2018
 */
@Service
public class RicercaPianoRateizzazioneServiceImpl implements RicercaPianoRateizzazioneService {

	/*@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;*/
	@Autowired
	private CnmTPianoRateRepository cnmTPianoRateRepository;
	@Autowired
	private CnmDStatoPianoRateRepository cnmDStatoPianoRateRepository;
	/*@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;*/
	@Autowired
	private MinPianoRateizzazioneEntityMapper minPianoEntityMapper;
	@Autowired
	private StatoPianoRateEntityMapper statoPianoRateEntityMapper;
	@Autowired
	private CnmTNotificaRepository cnmTNotificaRepository;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private RicercaOrdinanzaService ricercaOrdinanzaService;
	@Autowired
	private CnmTAllegatoFieldRepository cnmTAllegatoFieldRepository;
	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;


	@Autowired
	private VerbaleEntityMapper verbaleEntityMapper;
	
	private static Long COUNT = new Long(100);

	@Override
	public List<SoggettoVO> ricercaSoggetti(RicercaPianoRequest request, UserDetails userDetails, boolean pregressi) {
		if (request == null)
			throw new IllegalArgumentException("request is null");

		// per il piano le ordinanze ricercate devono essere protocollate
		request.setOrdinanzaProtocollata(Boolean.TRUE);
	
		BigInteger count = null;

		if (request.getDataNotificaDa() != null && request.getDataNotificaA() != null) {
			count = cnmTNotificaRepository.countByDataNotificaCreaPiano(utilsDate.asDate(request.getDataNotificaDa()), utilsDate.asDate(request.getDataNotificaA()));

		} else if (request.getDataSentenzaDa() != null && request.getDataSentenzaA() != null) {
			if (request.getStatoSentenza() == null) {
				count = cnmTAllegatoFieldRepository.countByDataSentenzaCreaPiano(utilsDate.asDate(request.getDataSentenzaDa()), utilsDate.asDate(request.getDataSentenzaA()));
			} else {
				count = cnmTAllegatoFieldRepository.countByDataSentenzaAndIdStatoSentenzaCreaPiano(utilsDate.asDate(request.getDataSentenzaDa()), utilsDate.asDate(request.getDataSentenzaA()),
						request.getStatoSentenza().getId());
			}
		}

		if (count != null && count.longValue() > COUNT) {
			List<SoggettoVO> esito = new ArrayList<SoggettoVO>();
			SoggettoVO vo = new SoggettoVO();
			vo.setSuperatoIlMassimo(true);
			esito.add(vo);
			return esito;
		}
		
		List<SoggettoVO> soggetti = ricercaOrdinanzaService.ricercaSoggetti(request, request.getStatoOrdinanza(), request.getDataNotificaDa(), request.getDataNotificaA(), request.getTipoRicerca(), pregressi, 
				request.getNumeroProtocolloPiano(), request.getStatoPiano());

		
		
		// 20210331_LC se tipoRicerca: "RICERCA_SOLLECITO_RATE", idPianorateizzazione in ogni SOggettoVO
		if (request.getTipoRicerca().equals("RICERCA_SOLLECITO_RATE")) {
			String idPiano = null;
			for (SoggettoVO sogg : soggetti) {	
				if (sogg.getIdSoggettoOrdinanza() != null) {
					CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.findOne(sogg.getIdSoggettoOrdinanza());						
					if (cnmROrdinanzaVerbSog != null) {
						List<CnmRSoggRata> cnmRSoggRataList = cnmRSoggRataRepository.findByCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);
						if (cnmRSoggRataList != null && !cnmRSoggRataList.isEmpty()) {
							CnmTRata cnmTRata = cnmRSoggRataList.get(0).getCnmTRata();
							if (cnmTRata != null) {
								idPiano = String.valueOf(cnmTRata.getCnmTPianoRate().getIdPianoRate());		
							}						
						}					
					}				
				}	
				
				if (idPiano != null)
					sogg.setIdPianoRateizzazione(idPiano);
			
			}
		}
		
				
		return soggetti;
	}

	//TODO LUCIO
	@Override
	public List<MinPianoRateizzazioneVO> ricercaPiani(RicercaPianoRequest request,UserDetails userDetails) {
		if (request == null)
			throw new IllegalArgumentException("request is null");
		if (request.getDatiVerbale() == null) request.getDatiVerbale();
		VerbaleSearchParam parametriVerbale = verbaleEntityMapper.getVerbaleParamFromRequest(request.getDatiVerbale());
		
		Function<StatoPianoVO, Long> statoPianoToLong = new Function<StatoPianoVO, Long>() {
			public Long apply(StatoPianoVO i) {
				return i.getId();
			}
		};
		List<Long> statoPiano = request.getStatoPiano() != null ? Lists.transform(request.getStatoPiano(), statoPianoToLong) : null;
		List<Long> statoOridnanzaVerSog = new ArrayList<Long>();
		if (request.getTipoRicerca() != null && request.getTipoRicerca().equals("RICONCILIA_PIANO")) {
			if (statoPiano == null) {
				statoPiano = new ArrayList<Long>();
				statoPiano.add(new Long(4));
				statoPiano.add(new Long(5));
			}

			statoOridnanzaVerSog.add(new Long(2));
			statoOridnanzaVerSog.add(new Long(3));
			statoOridnanzaVerSog.add(new Long(7));
			statoOridnanzaVerSog.add(new Long(8));
			statoOridnanzaVerSog.add(new Long(9));

		} else if (request.getTipoRicerca() != null && request.getTipoRicerca().equals("RICERCA_PIANO")) {
			if (statoPiano == null) {
				statoPiano = new ArrayList<Long>();
				statoPiano.add(new Long(1));
				statoPiano.add(new Long(2));
				statoPiano.add(new Long(3));
				statoPiano.add(new Long(4));
				statoPiano.add(new Long(5));
				statoPiano.add(new Long(6));
			}

			statoOridnanzaVerSog.add(new Long(3));
		}

		Long count = new Long(0);
		if (request.getDataNotificaA() != null && request.getDataNotificaDa() != null) {
			count = cnmTPianoRateRepository.countByDataNotificaOrdinanzaAndIdStatoPianoIn(
				utilsDate.asDate(request.getDataNotificaDa()),
				utilsDate.asDate(request.getDataNotificaA()),
				statoPiano,
				statoOridnanzaVerSog
			).longValue();
		}
		if (request.getDataSentenzaA() != null && request.getDataSentenzaDa() != null) {
			if (request.getStatoSentenza() != null && request.getStatoSentenza().getId() != null)
				count = cnmTPianoRateRepository.countByDataSentenzaAndIdStatoSentenzaAndIdStatoPianoIn(
					utilsDate.asDate(request.getDataSentenzaDa()),
					utilsDate.asDate(request.getDataSentenzaA()),
					new BigDecimal(request.getStatoSentenza().getId()),
					statoPiano,
					statoOridnanzaVerSog
				).longValue();
			else
				count = cnmTPianoRateRepository.countByDataSentenzaAndIdStatoPianoIn(
					utilsDate.asDate(request.getDataSentenzaDa()),
					utilsDate.asDate(request.getDataSentenzaA()),
					statoPiano,
					statoOridnanzaVerSog
				).longValue();
		}

		if (request.getDataCreazioneA() != null && request.getDataCreazioneDa() != null) {
			count = cnmTPianoRateRepository.countByDataCreazioneAndIdStatoPianoIn(
				utilsDate.asDate(request.getDataCreazioneDa()),
				utilsDate.asDate(request.getDataCreazioneA()),
				statoPiano,
				statoOridnanzaVerSog
			).longValue();
		}

		if (count > COUNT) {
			List<MinPianoRateizzazioneVO> esito = new ArrayList<MinPianoRateizzazioneVO>();
			MinPianoRateizzazioneVO vo = new MinPianoRateizzazioneVO();
			vo.setSuperatoIlMassimo(true);
			esito.add(vo);
			return esito;
		}

		List<CnmTPianoRate> cnmTRateizzazioneList = null;
		Map<Integer, CnmTPianoRate> map = new HashMap<Integer, CnmTPianoRate>();
		if (request.getDataCreazioneA() != null && request.getDataCreazioneDa() != null) {
			List<CnmTPianoRate> cnmTRateizzazione = 
				cnmTPianoRateRepository.findByDataCreazioneAndIdStatoPianoIn(
					utilsDate.asDate(request.getDataCreazioneDa()),
					utilsDate.asDate(request.getDataCreazioneA()),
					statoPiano,
					statoOridnanzaVerSog
				);
			if (cnmTRateizzazione != null && cnmTRateizzazione.size() > 0) {
				cnmTRateizzazioneList = new ArrayList<CnmTPianoRate>();
				for (CnmTPianoRate rate : cnmTRateizzazione) {
					if (
						map.isEmpty() ||
						(!map.isEmpty() && !map.containsKey(rate.getIdPianoRate()))
					) {
						cnmTRateizzazioneList.add(rate);
					}

					map.put(rate.getIdPianoRate(), rate);
				}
			}

		} else {
			cnmTRateizzazioneList =
				cnmTPianoRateRepository.findAll(
					CnmTPianoRateSpecification.findPianoBy(
						request.getNumeroDeterminazione(),
						request.getNumeroProtocolloSentenza(),
						TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getId(),
						request.getNumeroProtocolloPiano(),
						statoPiano,
						utilsDate.asDate(request.getDataNotificaA()),
						utilsDate.asDate(request.getDataNotificaDa()),
						utilsDate.asDate(request.getDataSentenzaA()),
						utilsDate.asDate(request.getDataSentenzaDa()),
						request.getStatoSentenza() != null ? request.getStatoSentenza().getId() : null,
						request.getTipoRicerca(),
						parametriVerbale
					)
				);
		}
		return minPianoEntityMapper.mapListEntityToListVO(cnmTRateizzazioneList);

	}

	@Override
	public List<StatoPianoVO> getStatiPiano(Boolean isRiconciliaPiano) {
		if (isRiconciliaPiano) {
			List<Long> id = new ArrayList<>();
			id.add(Constants.ID_STATO_PIANO_NON_NOTIFICATO);
			id.add(Constants.ID_STATO_PIANO_NOTIFICATO);
			return statoPianoRateEntityMapper.mapListEntityToListVO((List<CnmDStatoPianoRate>) cnmDStatoPianoRateRepository.findByIdStatoPianoRateIn(id));
		} else {
			return statoPianoRateEntityMapper.mapListEntityToListVO((List<CnmDStatoPianoRate>) cnmDStatoPianoRateRepository.findAll());
		}
	}

}
