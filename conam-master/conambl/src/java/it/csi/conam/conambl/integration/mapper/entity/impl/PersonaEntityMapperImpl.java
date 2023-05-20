/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.integration.entity.CnmDComune;
import it.csi.conam.conambl.integration.entity.CnmSComune;
import it.csi.conam.conambl.integration.entity.CnmTPersona;
import it.csi.conam.conambl.integration.mapper.entity.*;
import it.csi.conam.conambl.integration.repositories.CnmDComuneRepository;
import it.csi.conam.conambl.integration.repositories.CnmDNazioneRepository;
import it.csi.conam.conambl.integration.repositories.CnmSComuneRepository;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonaEntityMapperImpl implements PersonaEntityMapper {
	@Autowired
	private ComuneEntityMapper comuneEntityMapper;
	@Autowired
	private ProvinciaEntityMapper provinciaEntityMapper;
	@Autowired
	private RegioneEntityMapper regioneEntityMapper;
	@Autowired
	private NazioneEntityMapper nazioneEntityMapper;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmDComuneRepository cnmDComuneRepository;
	@Autowired
	private CnmDNazioneRepository cnmDNazioneRepository;
	@Autowired
	private CnmSComuneRepository cnmSComuneRepository;

	@Override
	public SoggettoVO mapEntityToVO(CnmTPersona cnmTPersona) {
		if (cnmTPersona == null)
			return null;
		SoggettoVO soggettoVO = new SoggettoVO();

		CnmDComune comuneNascita = cnmTPersona.getCnmDComune();
		if (comuneNascita != null) {
			soggettoVO.setComuneNascita(comuneEntityMapper.mapEntityToVO(comuneNascita));
			soggettoVO.setProvinciaNascita(provinciaEntityMapper.mapEntityToVO(comuneNascita.getCnmDProvincia()));
			soggettoVO.setRegioneNascita(regioneEntityMapper.mapEntityToVO(comuneNascita.getCnmDProvincia().getCnmDRegione()));
		} else {
			soggettoVO.setNazioneNascita(nazioneEntityMapper.mapEntityToVO(cnmTPersona.getCnmDNazione()));
			soggettoVO.setNazioneNascitaEstera(true);
		}
		soggettoVO.setDataNascita(cnmTPersona.getDataNascita() != null ? utilsDate.asLocalDate(cnmTPersona.getDataNascita()) : null);

		soggettoVO.setPersonaFisica(true);
		soggettoVO.setSesso(cnmTPersona.getSesso());

		return soggettoVO;
	}

	@Override
	public CnmTPersona mapVOtoEntity(SoggettoVO soggettoVO) {
		if (soggettoVO == null)
			return null;
		CnmTPersona cnmTPersona = new CnmTPersona();

		boolean isNazioneNascitaEstera = soggettoVO.getNazioneNascitaEstera() != null && soggettoVO.getNazioneNascitaEstera();
		if (!isNazioneNascitaEstera) {

			CnmDComune comune = null;
			if (soggettoVO.getComuneNascita() != null && soggettoVO.getDataNascita() != null) {
				CnmSComune comuneS = cnmSComuneRepository.findByidComuneAndDataNascita(soggettoVO.getComuneNascita().getId(), utilsDate.asDate(soggettoVO.getDataNascita()));
				comune = cnmDComuneRepository.findOne(soggettoVO.getComuneNascita().getId());
				if (comuneS != null && comuneS.getCnmDProvincia() != null)
					comune.setCnmDProvincia(comuneS.getCnmDProvincia());
			}

			cnmTPersona.setCnmDComune(comune);
		} else {
			cnmTPersona.setCnmDNazione(soggettoVO.getNazioneNascita() != null ? cnmDNazioneRepository.findOne(soggettoVO.getNazioneNascita().getId()) : null);
		}
		cnmTPersona.setDataNascita(soggettoVO.getDataNascita() != null ? utilsDate.asDate(soggettoVO.getDataNascita()) : null);
		cnmTPersona.setSesso(soggettoVO.getSesso());

		return cnmTPersona;
	}

	@Override
	public CnmTPersona mapVOtoEntityUpdate(SoggettoVO soggetto, CnmTPersona cnmTPersona) {
		if (soggetto == null)
			return null;

		boolean isNazioneNascitaEstera = soggetto.getNazioneNascitaEstera() != null && soggetto.getNazioneNascitaEstera();
		if (!isNazioneNascitaEstera) {
			cnmTPersona.setCnmDComune(soggetto.getComuneNascita() != null ? cnmDComuneRepository.findOne(soggetto.getComuneNascita().getId()) : null);
		} else {
			cnmTPersona.setCnmDNazione(soggetto.getNazioneNascita() != null ? cnmDNazioneRepository.findOne(soggetto.getNazioneNascita().getId()) : null);
		}
		cnmTPersona.setDataNascita(soggetto.getDataNascita() != null ? utilsDate.asDate(soggetto.getDataNascita()) : null);
		cnmTPersona.setSesso(soggetto.getSesso());

		return cnmTPersona;
	}

}
