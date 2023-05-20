/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.ordinanza;

import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.ordinanza.SoggettoOrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.UtilsOrdinanza;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.ROrdinanzaVerbSogEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoSoggettoOrdinanzaMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.ordinanza.StatoSoggettoOrdinanzaVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class SoggettoOrdinanzaServiceImpl implements SoggettoOrdinanzaService {

	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private StatoSoggettoOrdinanzaMapper statoOrdVerbSogEntityMapper;
	@Autowired
	private CnmDStatoOrdVerbSogRepository cnmDStatoOrdVerbSogRepository;
	@Autowired
	private UtilsOrdinanza utilsOrdinanza;
	@Autowired
	private ROrdinanzaVerbSogEntityMapper rOrdinanzaVerbSogEntityMapper;
	@Autowired
	private CnmRAllegatoOrdVerbSogRepository cnmRAllegatoOrdVerbSogRepository;
	@Autowired
	private CnmDTipoAllegatoRepository cnmDTipoAllegatoRepository;
	@Autowired
	private CnmTAllegatoFieldRepository cnmTAllegatoFieldRepository;
	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;
	/*@Autowired
	private CnmTResidenzaRepository cnmTResidenzaRepository;
	@Autowired
	private ResidenzaEntityMapper residenzaEntityMapper;*/
	@Autowired
	private CommonSoggettoService commonSoggettoService;

	@Override
	public List<SoggettoVO> getSoggettiByIdOrdinanza(Integer id, UserDetails userDetails) {
		CnmTOrdinanza cnmTOrdinanza = utilsOrdinanza.validateAndGetCnmTOrdinanza(id);

		List<CnmROrdinanzaVerbSog> ordSog = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		if (ordSog == null || ordSog.isEmpty()) {
			return null;
		}
		List<SoggettoVO> soggetti = rOrdinanzaVerbSogEntityMapper.mapListEntityToListVO(ordSog);

		// 20201217_LC - JIRA 118	
		CnmTVerbale cnmTVerbale = ordSog.get(0).getCnmRVerbaleSoggetto().getCnmTVerbale();
		for (SoggettoVO sogVO : soggetti) {
			CnmTSoggetto cnmTSoggetto = cnmTSoggettoRepository.findOne(sogVO.getId());
			if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
				sogVO = commonSoggettoService.attachResidenzaPregressi(sogVO, cnmTSoggetto, cnmTVerbale.getIdVerbale());
			}			
		}
		
		


		CnmDTipoAllegato cnmDTipoAllegato = cnmDTipoAllegatoRepository.findOne(TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getId());
		List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogs = cnmRAllegatoOrdVerbSogRepository.findByCnmTOrdinanzaAndCnmDTipoAllegato(cnmTOrdinanza, cnmDTipoAllegato);
		if (cnmRAllegatoOrdVerbSogs != null && !cnmRAllegatoOrdVerbSogs.isEmpty()) {
			List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs = cnmROrdinanzaVerbSogRepository.findByCnmRAllegatoOrdVerbSogsIn(cnmRAllegatoOrdVerbSogs);
			List<SoggettoVO> soggAllegati = rOrdinanzaVerbSogEntityMapper.mapListEntityToListVO(cnmROrdinanzaVerbSogs);

			// 20201217_LC - JIRA 118
			for (SoggettoVO sogAllVO : soggAllegati) {
				CnmTSoggetto cnmTSoggetto = cnmTSoggettoRepository.findOne(sogAllVO.getId());
				if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
					sogAllVO = commonSoggettoService.attachResidenzaPregressi(sogAllVO, cnmTSoggetto, cnmTVerbale.getIdVerbale());
				}			
			}
			
			
			Map<Integer, SoggettoVO> soggAllegatiMap = new HashMap<>();
			for (SoggettoVO s : soggAllegati) {
				soggAllegatiMap.put(s.getIdSoggettoOrdinanza(), s);
			}
			for (CnmRAllegatoOrdVerbSog a : cnmRAllegatoOrdVerbSogs) {
				SoggettoVO s = soggAllegatiMap.get(a.getCnmROrdinanzaVerbSog().getIdOrdinanzaVerbSog());
				List<CnmTAllegatoField> cnmTAllegatoFields = cnmTAllegatoFieldRepository.findByCnmTAllegato(a.getCnmTAllegato());
				for (CnmTAllegatoField f : cnmTAllegatoFields) {
					if (f.getCnmCField().getIdField() == Constants.ID_FIELD_IMPORTO_SANZIONE_SENTENZA)
						s.setImportoTitoloSanzione(f.getValoreNumber());
					if (f.getCnmCField().getIdField() == Constants.ID_FIELD_IMPORTO_SPESE_PROCESSUALI_SENTENZA)
						s.setImportoSpeseProcessuali(f.getValoreNumber());
				}
			}
			Map<Integer, SoggettoVO> soggMap = new HashMap<>();
			for (SoggettoVO s : soggetti) {
				soggMap.put(s.getIdSoggettoOrdinanza(), s);
			}
			for (SoggettoVO s : soggAllegatiMap.values()) {
				soggMap.put(s.getIdSoggettoOrdinanza(), s);
			}
			return soggMap.values().stream().collect(Collectors.toList());

		}

		return soggetti;
	}

	@Override
	public List<StatoSoggettoOrdinanzaVO> getStatiOrdinanzaSoggettoInCreazioneOrdinanza() {
		return statoOrdVerbSogEntityMapper.mapListEntityToListVO((List<CnmDStatoOrdVerbSog>) cnmDStatoOrdVerbSogRepository.findAll(Constants.STATI_ORDINANZA_SOGGETTO));
	}

	
	
}
