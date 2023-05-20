/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl;

import it.csi.conam.conambl.business.service.LuoghiService;
import it.csi.conam.conambl.integration.entity.CnmDNazione;
import it.csi.conam.conambl.integration.entity.CnmDProvincia;
import it.csi.conam.conambl.integration.entity.CnmDRegione;
import it.csi.conam.conambl.integration.entity.CnmSComune;
import it.csi.conam.conambl.integration.mapper.entity.*;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.luoghi.NazioneVO;
import it.csi.conam.conambl.vo.luoghi.ProvinciaVO;
import it.csi.conam.conambl.vo.luoghi.RegioneVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class LuoghiServiceImpl implements LuoghiService {

	@Autowired
	private CnmDComuneRepository cnmDComuneRepository;
	@Autowired
	private CnmSComuneRepository cnmSComuneRepository;
	@Autowired
	private CnmDNazioneRepository cmDNazioneRepository;
	@Autowired
	private CnmDProvinciaRepository cnmDProvinciaRepository;
	@Autowired
	private CnmDRegioneRepository cnmDRegioneRepository;
	@Autowired
	private RegioneEntityMapper regioneEntityMapper;
	@Autowired
	private ProvinciaEntityMapper provinciaEntityMapper;
	@Autowired
	private ComuneEntityMapper comuneEntityMapper;
	@Autowired
	private SComuneEntityMapper sComuneEntityMapper;
	@Autowired
	private NazioneEntityMapper nazioneEntityMapper;

	@Override
	public List<RegioneVO> getRegioni() {
		return regioneEntityMapper.mapListEntityToListVO((List<CnmDRegione>) cnmDRegioneRepository.findAllByFineValiditaOrderByDenomRegioneAsc());
	}

	@Override
	public List<ProvinciaVO> getProviceByIdRegione(Long idRegione) {
		CnmDRegione cnmDRegione = cnmDRegioneRepository.findOne(idRegione);
		if (cnmDRegione == null)
			return null;
		return provinciaEntityMapper.mapListEntityToListVO(cnmDProvinciaRepository.findByCnmDRegioneAndFineValiditaOrderByDenomProvinciaAsc(cnmDRegione));
	}

	@Override
	public List<ComuneVO> getComuniByIdProvincia(Long idProvincia) {
		CnmDProvincia cnmDProvincia = cnmDProvinciaRepository.findOne(idProvincia);
		if (cnmDProvincia == null)
			return null;
		return comuneEntityMapper.mapListEntityToListVO(cnmDComuneRepository.findByCnmDProvinciaAndFineValiditaOrderByDenomComuneAsc(cnmDProvincia));
	}

	@Override
	public List<RegioneVO> getRegioniNascita() {
		return regioneEntityMapper.mapListEntityToListVO((List<CnmDRegione>) cnmDRegioneRepository.findAllOrderByDenomRegioneAsc());
	}

	@Override
	public List<ProvinciaVO> getProviceByIdRegioneNascita(Long idRegione) {
		CnmDRegione cnmDRegione = cnmDRegioneRepository.findOne(idRegione);
		if (cnmDRegione == null)
			return null;
		return provinciaEntityMapper.mapListEntityToListVO(cnmDProvinciaRepository.findByCnmDRegioneOrderByDenomProvinciaAsc(cnmDRegione));
	}

	@Override
	public List<ComuneVO> getComuniByIdProvinciaNascita(Long idProvincia) {
		CnmDProvincia cnmDProvincia = cnmDProvinciaRepository.findOne(idProvincia);
		if (cnmDProvincia == null)
			return null;

		List<ComuneVO> comuniVO = comuneEntityMapper.mapListEntityToListVO(cnmDComuneRepository.findByCnmDProvinciaOrderByDenomComuneAsc(cnmDProvincia));
		List<CnmSComune> comunes = cnmSComuneRepository.findByCnmDProvinciaOrderByDenomComuneAsc(cnmDProvincia);

		Map<String, ComuneVO> mappa = new HashMap<String, ComuneVO>();
		//2021-07-16 - ROSADINI LUCIO - POPOLO A PRIORI CON TUTTI I COMUNI - CONAM-161
		for (ComuneVO comuneVO : comuniVO) mappa.put(comuneVO.getDenominazione(), null);
		//2021-07-16 - ROSADINI LUCIO - AGGIUNGO I COMUNI MANCANTI DALLA TABELLA CNM_S_COMUNI - CONAM-161
		if (comunes != null && comunes.size() > 0) {
			for (CnmSComune cnmSComune : comunes) {
				if (mappa.isEmpty() || !mappa.containsKey(cnmSComune.getDenomComune())) {
					ComuneVO comuneVO = sComuneEntityMapper.mapEntityToVO(cnmSComune);
					comuniVO.add(comuneVO);
					mappa.put(cnmSComune.getDenomComune(), null);
				}
			}
		}

		Collections.sort(comuniVO);

		return comuniVO;
	}


	@Override
	public List<NazioneVO> getNazioni() {
		return nazioneEntityMapper.mapListEntityToListVO((List<CnmDNazione>) cmDNazioneRepository.findAllByFineValiditaOrderByDenomNazioneAsc());
	}

	@Override
	public List<ProvinciaVO> getProviceByIdRegione(Long idRegione, Date dataOraAccertamento) {
		CnmDRegione cnmDRegione = cnmDRegioneRepository.findOne(idRegione);
		if (cnmDRegione == null)
			return null;
		return provinciaEntityMapper.mapListEntityToListVO(cnmDProvinciaRepository.findByCnmDRegioneAndValidaInDataOrderByDenomProvinciaAsc(cnmDRegione, dataOraAccertamento));
	}

	@Override
	public List<ComuneVO> getComuniByIdProvincia(Long idProvincia, Date dataOraAccertamento) {
		CnmDProvincia cnmDProvincia = cnmDProvinciaRepository.findOne(idProvincia);
		if (cnmDProvincia == null)
			return null;
		return comuneEntityMapper.mapListEntityToListVO(cnmDComuneRepository.findByCnmDProvinciaAndValidaOrderByDenomComuneAsc(cnmDProvincia, dataOraAccertamento));
	}

	@Override
	public List<ComuneVO> getComuniEnte(Date dataOraAccertamento) {
		if(dataOraAccertamento == null) {
			CnmDRegione cnmDRegione = cnmDRegioneRepository.findOne(1L);
			List<CnmDProvincia> cnmDProvincias = cnmDProvinciaRepository.findByCnmDRegioneOrderByDenomProvinciaAsc(cnmDRegione);
			if (cnmDProvincias == null)
				return null;
			
			List<ComuneVO> comuni = comuneEntityMapper.mapListEntityToListVO(cnmDComuneRepository.findByCnmDProvinciasInOrderByDenomComuneAsc(cnmDProvincias));
			comuni.add(0, new ComuneVO());
			return comuni; 
		}
		CnmDRegione cnmDRegione = cnmDRegioneRepository.findOne(1L);
		List<CnmDProvincia> cnmDProvincias = cnmDProvinciaRepository.findByCnmDRegioneAndValidaInDataOrderByDenomProvinciaAsc(cnmDRegione, dataOraAccertamento);
		if (cnmDProvincias == null)
			return null;

		List<ComuneVO> comuni = comuneEntityMapper.mapListEntityToListVO(cnmDComuneRepository.findByCnmDProvinciasInAndValidaOrderByDenomComuneAsc(cnmDProvincias, dataOraAccertamento));
		comuni.add(0, new ComuneVO());
		return comuni; 
	}
	

}
