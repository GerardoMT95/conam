/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmDComune;
import it.csi.conam.conambl.integration.entity.CnmDNazione;
import it.csi.conam.conambl.integration.entity.CnmTResidenza;
import it.csi.conam.conambl.integration.mapper.entity.*;
import it.csi.conam.conambl.integration.repositories.CnmDComuneRepository;
import it.csi.conam.conambl.integration.repositories.CnmDNazioneRepository;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResidenzaEntityMapperImpl implements ResidenzaEntityMapper {
	@Autowired
	private ComuneEntityMapper comuneEntityMapper;
	@Autowired
	private ProvinciaEntityMapper provinciaEntityMapper;
	@Autowired
	private RegioneEntityMapper regioneEntityMapper;
	@Autowired
	private NazioneEntityMapper nazioneEntityMapper;
	@Autowired
	private CnmDComuneRepository cnmDComuneRepository;
	@Autowired
	private CnmDNazioneRepository cnmDNazioneRepository;

	@Override
	public SoggettoVO mapEntityToVO(CnmTResidenza cnmTResidenza) {
		if (cnmTResidenza == null)
			return null;
		SoggettoVO soggettoVO = new SoggettoVO();

		if (cnmTResidenza.getResidenzaEstera()) {
			CnmDNazione cnmDNazione = cnmTResidenza.getCnmDNazione();
			if (cnmDNazione != null)
				soggettoVO.setNazioneResidenza(nazioneEntityMapper.mapEntityToVO(cnmDNazione));
			soggettoVO.setDenomComuneResidenzaEstero(cnmTResidenza.getDenomComuneResidenzaEstero());
			soggettoVO.setResidenzaEstera(true);
		} else {
			CnmDComune cnmDComune = cnmTResidenza.getCnmDComune();
			if (cnmDComune != null) {
				soggettoVO.setComuneResidenza(comuneEntityMapper.mapEntityToVO(cnmDComune));
				soggettoVO.setProvinciaResidenza(provinciaEntityMapper.mapEntityToVO(cnmDComune.getCnmDProvincia()));
				soggettoVO.setRegioneResidenza(regioneEntityMapper.mapEntityToVO(cnmDComune.getCnmDProvincia().getCnmDRegione()));
			}
			soggettoVO.setResidenzaEstera(false);
		}
		soggettoVO.setIndirizzoResidenza(cnmTResidenza.getIndirizzoResidenza());
		soggettoVO.setCivicoResidenza(cnmTResidenza.getNumeroCivicoResidenza());
		soggettoVO.setCap(cnmTResidenza.getCapResidenza());

		return soggettoVO;
	}

	@Override
	public CnmTResidenza mapVOtoEntity(SoggettoVO soggettoVO) {
		if (soggettoVO == null)
			return null;
		CnmTResidenza cnmTResidenza = new CnmTResidenza();
		if (soggettoVO.getResidenzaEstera()) {
			cnmTResidenza.setCnmDNazione(soggettoVO.getNazioneResidenza() != null ? cnmDNazioneRepository.findOne(soggettoVO.getNazioneResidenza().getId()) : null);
			cnmTResidenza.setDenomComuneResidenzaEstero(soggettoVO.getDenomComuneResidenzaEstero());
			cnmTResidenza.setResidenzaEstera(true);
		} else {
			cnmTResidenza.setCnmDComune(
					soggettoVO.getComuneResidenza() != null && soggettoVO.getComuneResidenza().getId() != null ? cnmDComuneRepository.findOne(soggettoVO.getComuneResidenza().getId()) : null);
			cnmTResidenza.setResidenzaEstera(false);
		}
		cnmTResidenza.setIndirizzoResidenza(soggettoVO.getIndirizzoResidenza());
		cnmTResidenza.setNumeroCivicoResidenza(soggettoVO.getCivicoResidenza());
		cnmTResidenza.setCapResidenza(soggettoVO.getCap());

		return cnmTResidenza;
	}

	@Override
	public CnmTResidenza mapVOtoEntityUpdate(SoggettoVO soggetto, CnmTResidenza cnmTResidenza) {
		if (soggetto == null)
			return null;

		if (soggetto.getResidenzaEstera()) {
			cnmTResidenza.setCnmDNazione(soggetto.getNazioneResidenza() != null ? cnmDNazioneRepository.findOne(soggetto.getNazioneResidenza().getId()) : null);
			cnmTResidenza.setDenomComuneResidenzaEstero(soggetto.getDenomComuneResidenzaEstero());
		} else {
			cnmTResidenza
					.setCnmDComune(soggetto.getComuneResidenza() != null && soggetto.getComuneResidenza().getId() != null ? cnmDComuneRepository.findOne(soggetto.getComuneResidenza().getId()) : null);
		}
		cnmTResidenza.setIndirizzoResidenza(soggetto.getIndirizzoResidenza());
		cnmTResidenza.setNumeroCivicoResidenza(soggetto.getCivicoResidenza());
		cnmTResidenza.setCapResidenza(soggetto.getCap());

		return cnmTResidenza;
	}

	@Override
	public SoggettoVO mapEntitytoVOUpdate(SoggettoVO soggetto, CnmTResidenza cnmTResidenza) {
		if (soggetto == null || cnmTResidenza == null)
			return null;

		if (cnmTResidenza.getResidenzaEstera()) {
			CnmDNazione cnmDNazione = cnmTResidenza.getCnmDNazione();
			if (cnmDNazione != null)
				soggetto.setNazioneResidenza(nazioneEntityMapper.mapEntityToVO(cnmDNazione));
			soggetto.setDenomComuneResidenzaEstero(cnmTResidenza.getDenomComuneResidenzaEstero());
			soggetto.setResidenzaEstera(true);
			
			// 20201217_LC in caso si cambi residenza da italiana ad estera
			soggetto.setComuneResidenza(null);
			soggetto.setProvinciaResidenza(null);
			soggetto.setRegioneResidenza(null);			
			
		} else {
			CnmDComune cnmDComune = cnmTResidenza.getCnmDComune();
			if (cnmDComune != null) {
				soggetto.setComuneResidenza(comuneEntityMapper.mapEntityToVO(cnmDComune));
				soggetto.setProvinciaResidenza(provinciaEntityMapper.mapEntityToVO(cnmDComune.getCnmDProvincia()));
				soggetto.setRegioneResidenza(regioneEntityMapper.mapEntityToVO(cnmDComune.getCnmDProvincia().getCnmDRegione()));
			}
			soggetto.setResidenzaEstera(false);

			// 20201217_LC in caso si cambi residenza da estera ad italiana
			soggetto.setNazioneResidenza(null);
			soggetto.setDenomComuneResidenzaEstero(null);
			
		}
		soggetto.setIndirizzoResidenza(cnmTResidenza.getIndirizzoResidenza());
		soggetto.setCivicoResidenza(cnmTResidenza.getNumeroCivicoResidenza());
		soggetto.setCap(cnmTResidenza.getCapResidenza());

		return soggetto;
	}

}
