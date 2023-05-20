/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.facade.StasServFacade;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.*;
import it.csi.conam.conambl.integration.mapper.ws.stas.AnagraficaWsOutputMapper;
import it.csi.conam.conambl.integration.repositories.CnmTResidenzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmTSoggettoRepository;
import it.csi.conam.conambl.vo.verbale.ResidenzaVO;
import it.csi.conam.conambl.vo.verbale.SoggettoPregressiVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SoggettoPregressiEntityMapperImpl implements SoggettoPregressiEntityMapper {
	@Autowired
	private PersonaEntityMapper personaEntityMapper;
	@Autowired
	private SocietaEntityMapper societaEntityMapper;
	@Autowired
	private AnagraficaWsOutputMapper anagraficaWsOutputMapper;
	@Autowired
	private ResidenzaEntityMapper residenzaEntityMapper;
	@Autowired
	private NazioneEntityMapper nazioneEntityMapper;
	@Autowired
	private RegioneEntityMapper regioneEntityMapper;
	@Autowired
	private ProvinciaEntityMapper provinciaEntityMapper;
	@Autowired
	private ComuneEntityMapper comuneEntityMapper;
	@Autowired
	private StasServFacade stasServFacade;
	@Autowired
	private CnmTResidenzaRepository cnmTResidenzaRepository;
	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;

	@Override
	public SoggettoPregressiVO mapEntityToVO(CnmTSoggetto dto) {
		if (dto == null)
			return null;
		SoggettoVO soggettoVO = new SoggettoVO();
		CnmTPersona cnmTPersona = dto.getCnmTPersona();
		CnmTSocieta cnmTSocieta = dto.getCnmTSocieta();
		CnmTResidenza cnmTResidenza = cnmTResidenzaRepository.findByCnmTSoggetto(dto);
		SoggettoVO sogStas = new SoggettoVO();
		// soggetto presente su stas
		if (dto.getIdStas() != null) {
			sogStas = anagraficaWsOutputMapper.mapWsTypeToVO(stasServFacade.ricercaSoggettoID(dto.getIdStas().longValue()));
			if (cnmTPersona != null) {
				if (cnmTPersona.getCnmDNazione() != null) {
					sogStas.setNazioneNascitaEstera(true);
					sogStas.setNazioneNascita(nazioneEntityMapper.mapEntityToVO(cnmTPersona.getCnmDNazione()));
				}
				if (cnmTPersona.getDenomComuneNascitaEstero() != null) {
					sogStas.setDenomComuneNascitaEstero(cnmTPersona.getDenomComuneNascitaEstero());
				}
				if (cnmTPersona.getCnmDComune() != null) {
					sogStas.setRegioneNascita(regioneEntityMapper.mapEntityToVO(cnmTPersona.getCnmDComune().getCnmDProvincia().getCnmDRegione()));
					sogStas.setProvinciaNascita(provinciaEntityMapper.mapEntityToVO(cnmTPersona.getCnmDComune().getCnmDProvincia()));
					sogStas.setComuneNascita(comuneEntityMapper.mapEntityToVO(cnmTPersona.getCnmDComune()));
				}
			}
			if (cnmTResidenza != null) {
				sogStas.setIndirizzoResidenzaStas(sogStas.getIndirizzoResidenza());
				sogStas.setCivicoResidenzaStas(sogStas.getCivicoResidenza());
				sogStas.setCapStas(sogStas.getCap());
				sogStas = residenzaEntityMapper.mapEntitytoVOUpdate(sogStas, cnmTResidenza);
			}
			sogStas.setId(dto.getIdSoggetto());
			sogStas.setIdStas(dto.getIdStas());
			return createSoggettoPregressiVO(sogStas, dto);
		}
		// soggetto non presente su stas
		if (cnmTPersona != null) {
			soggettoVO = personaEntityMapper.mapEntityToVO(cnmTPersona);
			soggettoVO.setCodiceFiscale(dto.getCodiceFiscale());
			soggettoVO.setCognome(dto.getCognome());
			soggettoVO.setNome(dto.getNome());
			if (cnmTPersona != null && cnmTPersona.getCnmDNazione() != null) {
				soggettoVO.setNazioneNascitaEstera(true);
				soggettoVO.setNazioneNascita(nazioneEntityMapper.mapEntityToVO(cnmTPersona.getCnmDNazione()));
			}
			if (cnmTPersona.getDenomComuneNascitaEstero() != null) {
				soggettoVO.setDenomComuneNascitaEstero(cnmTPersona.getDenomComuneNascitaEstero());
			}
			if (cnmTPersona.getCnmDComune() != null) {
				soggettoVO.setRegioneNascita(regioneEntityMapper.mapEntityToVO(cnmTPersona.getCnmDComune().getCnmDProvincia().getCnmDRegione()));
				soggettoVO.setProvinciaNascita(provinciaEntityMapper.mapEntityToVO(cnmTPersona.getCnmDComune().getCnmDProvincia()));
				soggettoVO.setComuneNascita(comuneEntityMapper.mapEntityToVO(cnmTPersona.getCnmDComune()));
			}
		} else if (cnmTSocieta != null) {
			soggettoVO = societaEntityMapper.mapEntityToVO(cnmTSocieta);
			soggettoVO.setPartitaIva(dto.getPartitaIva());
			soggettoVO.setRagioneSociale(dto.getRagioneSociale());
			soggettoVO.setCodiceFiscale(dto.getCodiceFiscaleGiuridico());
		} else {
			soggettoVO.setCodiceFiscale(!StringUtils.isBlank(dto.getCodiceFiscale()) ? dto.getCodiceFiscale() : dto.getCodiceFiscaleGiuridico());
			soggettoVO.setPartitaIva(dto.getPartitaIva());
			soggettoVO.setRagioneSociale(dto.getRagioneSociale());
		}

		if (cnmTResidenza != null) {
			soggettoVO = residenzaEntityMapper.mapEntitytoVOUpdate(soggettoVO, cnmTResidenza);
		}
		soggettoVO.setId(dto.getIdSoggetto());
		soggettoVO.setIdStas(dto.getIdStas());

		return createSoggettoPregressiVO(soggettoVO, dto);
	}

	@Override
	public SoggettoPregressiVO createSoggettoPregressiVO(SoggettoVO soggettoVO, CnmTSoggetto dto) {

		if(dto == null) {
			if(soggettoVO.getIdStas() != null) {
				dto = cnmTSoggettoRepository.findByIdStas(soggettoVO.getIdStas());
			}else if(soggettoVO.getCodiceFiscale() != null) {
				dto = cnmTSoggettoRepository.findByCodiceFiscale(soggettoVO.getCodiceFiscale());				
			}else if(soggettoVO.getPartitaIva() != null) {
				dto = cnmTSoggettoRepository.findByPartitaIva(soggettoVO.getPartitaIva());				
			}
		}
		SoggettoPregressiVO soggettoPregressiVO = new SoggettoPregressiVO();
		
		soggettoPregressiVO.setCognome(soggettoVO.getCognome());

		soggettoPregressiVO.setNome(soggettoVO.getNome());
		soggettoPregressiVO.setDataNascita(soggettoVO.getDataNascita());
		soggettoPregressiVO.setRegioneNascita(soggettoVO.getRegioneNascita());
		soggettoPregressiVO.setComuneNascita(soggettoVO.getComuneNascita());
		soggettoPregressiVO.setProvinciaNascita(soggettoVO.getProvinciaNascita());
		soggettoPregressiVO.setNazioneNascita(soggettoVO.getNazioneNascita());
		soggettoPregressiVO.setNazioneNascitaEstera(soggettoVO.getNazioneNascitaEstera());
		soggettoPregressiVO.setPersonaFisica(soggettoVO.getPersonaFisica());
		soggettoPregressiVO.setSesso(soggettoVO.getSesso());
		soggettoPregressiVO.setCodiceFiscale(soggettoVO.getCodiceFiscale());
		soggettoPregressiVO.setRagioneSociale(soggettoVO.getRagioneSociale());
		soggettoPregressiVO.setPartitaIva(soggettoVO.getPartitaIva());
		
		soggettoPregressiVO.setId(soggettoVO.getId());
		soggettoPregressiVO.setComuneNascitaValido(soggettoVO.getComuneNascitaValido());
//		soggettoPregressiVO.setIdStas(soggettoVO.getIdStas());
			
		if(soggettoVO.getIdStas() != null) {
			ResidenzaVO residenzaVO = new ResidenzaVO();
			residenzaVO.setIdStas(soggettoVO.getIdStas());
			residenzaVO.setIndirizzoResidenza(soggettoVO.getIndirizzoResidenza());
			residenzaVO.setCivicoResidenza(soggettoVO.getCivicoResidenza());
			residenzaVO.setCap(soggettoVO.getCap());
			residenzaVO.setProvinciaResidenza(soggettoVO.getProvinciaResidenza());
			residenzaVO.setRegioneResidenza(soggettoVO.getRegioneResidenza());
			residenzaVO.setNazioneResidenza(soggettoVO.getNazioneResidenza());
			
			if (soggettoVO.getResidenzaEstera()) {
				residenzaVO.setDenomComuneResidenzaEstero(soggettoVO.getDenomComuneResidenzaEstero());
				residenzaVO.setResidenzaEstera(true);
			}else {
				residenzaVO.setComuneResidenza(soggettoVO.getComuneResidenza());
				residenzaVO.setResidenzaEstera(false);				
			}
			soggettoPregressiVO.addResidenza(residenzaVO);
		}
		if(dto!=null) {
			// valorizzo le altre residenze legate ai verbali pregressi
			List<CnmTResidenza> cnmTResidenzaList = cnmTResidenzaRepository.getByCnmTSoggettoPregressi(dto);
			for(CnmTResidenza residenzaDto : cnmTResidenzaList) {
				ResidenzaVO rVO = new ResidenzaVO();
				
				if (residenzaDto.getResidenzaEstera()) {
					CnmDNazione cnmDNazione = residenzaDto.getCnmDNazione();
					if (cnmDNazione != null)
						rVO.setNazioneResidenza(nazioneEntityMapper.mapEntityToVO(cnmDNazione));
					rVO.setDenomComuneResidenzaEstero(residenzaDto.getDenomComuneResidenzaEstero());
					rVO.setResidenzaEstera(true);
				} else {
					CnmDComune cnmDComune = residenzaDto.getCnmDComune();
					if (cnmDComune != null) {
						rVO.setComuneResidenza(comuneEntityMapper.mapEntityToVO(cnmDComune));
						rVO.setProvinciaResidenza(provinciaEntityMapper.mapEntityToVO(cnmDComune.getCnmDProvincia()));
						rVO.setRegioneResidenza(regioneEntityMapper.mapEntityToVO(cnmDComune.getCnmDProvincia().getCnmDRegione()));
					}
					rVO.setResidenzaEstera(false);
				}
				rVO.setIndirizzoResidenza(residenzaDto.getIndirizzoResidenza());
				rVO.setCivicoResidenza(residenzaDto.getNumeroCivicoResidenza());
				rVO.setCap(residenzaDto.getCapResidenza());
				
				soggettoPregressiVO.addResidenza(rVO);
	//			
	//			
	//			rVO.setIndirizzoResidenza(residenzaDto.getIndirizzoResidenza());
	//			rVO.setCivicoResidenza(residenzaDto.getNumeroCivicoResidenza());
	//			rVO.setCap(residenzaDto.getCapResidenza());
	//			
	//			CnmDComune cnmDComune = residenzaDto.getCnmDComune();
	//			if (cnmDComune != null) {
	//				rVO.setComuneResidenza(comuneEntityMapper.mapEntityToVO(cnmDComune));
	//				rVO.setProvinciaResidenza(provinciaEntityMapper.mapEntityToVO(cnmDComune.getCnmDProvincia()));
	//				rVO.setRegioneResidenza(regioneEntityMapper.mapEntityToVO(cnmDComune.getCnmDProvincia().getCnmDRegione()));
	//			}
			}
		}
		return soggettoPregressiVO;
	
	}

	@Override
	public CnmTSoggetto mapVOtoEntity(SoggettoPregressiVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mapVOtoUpdateEntity(SoggettoPregressiVO soggetto, CnmTSoggetto cnmTSoggetto) {
		// TODO Auto-generated method stub
		
	}


}
