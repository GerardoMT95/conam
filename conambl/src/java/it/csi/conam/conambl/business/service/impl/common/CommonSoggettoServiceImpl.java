/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.common;

import it.csi.conam.conambl.business.facade.StasServFacade;
import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.ResidenzaEntityMapper;
import it.csi.conam.conambl.integration.mapper.ws.stas.AnagraficaWsOutputMapper;
import it.csi.conam.conambl.integration.mapper.ws.stas.ModuloRicercaWsInputMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.integration.specification.CnmTSoggettoSpecification;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.gmscore.dto.ModuloRicercaPF;
import org.apache.axis.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class CommonSoggettoServiceImpl implements CommonSoggettoService {

	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private AnagraficaWsOutputMapper anagraficaWsOutputMapper;
	@Autowired
	private ModuloRicercaWsInputMapper moduloRicercaWsInputMapper;
	@Autowired
	private StasServFacade stasServFacade;
	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;

	@Autowired
	private CnmTSocietaRepository cnmTSocietaRepository;
	@Autowired
	private CnmTPersonaRepository cnmTPersonaRepository;
	@Autowired
	private CnmDComuneRepository cnmDComuneRepository;
	@Autowired
	private CnmDNazioneRepository cnmDNazioneRepository;
	@Autowired
	private CnmTResidenzaRepository cnmTResidenzaRepository;
	@Autowired
	private ResidenzaEntityMapper residenzaEntityMapper;

	@Override
	public SoggettoVO getSoggettoFromStas(MinSoggettoVO minSoggettoVO, String identita) {
		List<SoggettoVO> soggettoList;
		boolean isPersonaFisica = isRicercaPersonaFisica(minSoggettoVO);
		boolean isRicercaCodFiscale = isRicercaCodiceFiscale(minSoggettoVO);
		boolean isRicercaCodFiscaleOrPiva = isRicercaCodFiscaleOrPiva(minSoggettoVO);

		if (isPersonaFisica) {
			if (isRicercaCodFiscale) {
				soggettoList = anagraficaWsOutputMapper.mapArrayWsTypeToListVO(stasServFacade.ricercaSoggettoCF(minSoggettoVO.getCodiceFiscale(), identita));
			} else {
				ModuloRicercaPF moduloRicercaPF = moduloRicercaWsInputMapper.mapVOtoWsType(minSoggettoVO);
				soggettoList = anagraficaWsOutputMapper.mapArrayWsTypeToListVO(stasServFacade.ricercaPersonaFisicaCompleta(moduloRicercaPF, identita));
			}
		} else {
			if (isRicercaCodFiscaleOrPiva) {
				String codFiscale = StringUtils.isEmpty(minSoggettoVO.getCodiceFiscale()) ? minSoggettoVO.getPartitaIva() : minSoggettoVO.getCodiceFiscale();
				soggettoList = anagraficaWsOutputMapper.mapArrayWsTypeToListVO(stasServFacade.ricercaSoggettoCF(codFiscale, identita));
			} else
				soggettoList = anagraficaWsOutputMapper.mapArrayWsTypeToListVO(stasServFacade.ricercaImpresa(minSoggettoVO.getRagioneSociale(), identita));
		}

		if (soggettoList != null) {
			if (soggettoList.size() == 1)
				return soggettoList.get(0);
			else {
				for (SoggettoVO vo : soggettoList) {
					if (!isPersonaFisica && !vo.getPersonaFisica()) {
						return vo;
					} else if (isPersonaFisica && vo.getPersonaFisica()) {
						return vo;
					}
				}
			}
		}

		return null;
	}

	@Override
	public CnmTSoggetto getSoggettoFromDb(MinSoggettoVO minSoggettoVO, boolean isRicerca) {
		CnmTSoggetto cnmTSoggetto = null;
		boolean isPersonaFisica = isRicercaPersonaFisica(minSoggettoVO);
		boolean isRicercaCodFiscale = isRicercaCodiceFiscale(minSoggettoVO);
		boolean isRicercaCodFiscaleOrPiva = isRicercaCodFiscaleOrPiva(minSoggettoVO);
		if (isPersonaFisica) {
			if (isRicercaCodFiscale) {
				cnmTSoggetto = cnmTSoggettoRepository.findOne(
					CnmTSoggettoSpecification.findSoggettoFisicoByCodFiscaleOrModuloRicerca(
						minSoggettoVO.getCodiceFiscale(),
						null,
						null,
						null,
						null,
						null,
						null
					)
				);
			} else {
				List<CnmTSoggetto> soggettoList = cnmTSoggettoRepository.findAll(
					CnmTSoggettoSpecification.findSoggettoFisicoByCodFiscaleOrModuloRicerca(
						null, //
						minSoggettoVO.getCognome(), //
						minSoggettoVO.getNome(), //
						minSoggettoVO.getComuneNascita() != null ? minSoggettoVO.getComuneNascita().getId() : null, //
						minSoggettoVO.getSesso(), //
						utilsDate.asDate(minSoggettoVO.getDataNascita()), //
						minSoggettoVO.getNazioneNascita() != null ? minSoggettoVO.getNazioneNascita().getId() : null //
					)
				);
				if(soggettoList != null && soggettoList.size()>0) {
					cnmTSoggetto = soggettoList.get(0);
				}
			}
		} else {
			if (isRicercaCodFiscaleOrPiva) {
				cnmTSoggetto =
					cnmTSoggettoRepository.findOne(
						CnmTSoggettoSpecification.findSocietaByCodFiscaleOrPivaOrRagioneSociale(
							minSoggettoVO.getCodiceFiscale(),
							minSoggettoVO.getPartitaIva(),
							null,
							isRicerca
						)
					);
			} else {
				
				List<CnmTSoggetto> soggettoList = cnmTSoggettoRepository.findAll(
					CnmTSoggettoSpecification.findSocietaByCodFiscaleOrPivaOrRagioneSociale(
						null,
						null,
						minSoggettoVO.getRagioneSociale(),
						isRicerca
					)
				);
				if(soggettoList != null && soggettoList.size()>0) {
					cnmTSoggetto = soggettoList.get(0);
				}
			}
		}
		return cnmTSoggetto;
	}

	@Override
	public CnmTSoggetto updateSoggettoDBWithIdStas(CnmTSoggetto cnmTSoggetto, CnmTUser cnmTUser, SoggettoVO sogDb, SoggettoVO sogStas, SoggettoVO soggetto) {
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		cnmTSoggetto = cnmTSoggettoRepository.findOne(sogDb.getId());
		cnmTSoggetto.setIdStas(sogStas.getIdStas());
		CnmTPersona cnmTPersona = cnmTSoggetto.getCnmTPersona();
		CnmTSocieta cnmTSocieta = cnmTSoggetto.getCnmTSocieta();

		if (cnmTPersona != null) {
			cnmTPersonaRepository.delete(cnmTPersona);
		} else if (cnmTSocieta != null) {
			cnmTSocietaRepository.delete(cnmTSocieta);
		}
		cnmTSoggetto.setCnmTPersona(null);
		cnmTSoggetto.setCnmTSocieta(null);
		cnmTSoggetto.setCnmTUser1(cnmTUser);
		cnmTSoggetto.setDataOraUpdate(now);
		cnmTSoggetto = cnmTSoggettoRepository.saveAndFlush(cnmTSoggetto);

		cnmTPersonaRepository.flush();
		cnmTSocietaRepository.flush();

		if (soggetto.getDenomComuneNascitaEstero() != null && sogStas.getDenomComuneNascitaEstero() == null) {
			cnmTPersona = new CnmTPersona();
			cnmTPersona.setDenomComuneNascitaEstero(soggetto.getDenomComuneNascitaEstero());
			if (soggetto.getNazioneNascita().getId() != null && sogStas.getNazioneNascita() == null) {
				cnmTPersona.setCnmDNazione(cnmDNazioneRepository.findByIdNazione(soggetto.getNazioneNascita().getId()));
			}
			cnmTPersona.setCnmTUser2(cnmTUser);
			cnmTPersona.setDataOraInsert(now);
			cnmTPersonaRepository.save(cnmTPersona);
			cnmTSoggetto.setCnmTPersona(cnmTPersona);
		}

		if (soggetto.getRegioneNascita().getId() != null && sogStas.getRegioneNascita() == null) {
			cnmTPersona = new CnmTPersona();
			cnmTPersona.setCnmDComune(cnmDComuneRepository.findByIdComune(soggetto.getComuneNascita().getId()));
			cnmTPersona.setCnmTUser2(cnmTUser);
			cnmTPersona.setDataOraInsert(now);
			cnmTPersonaRepository.save(cnmTPersona);
			cnmTSoggetto.setCnmTPersona(cnmTPersona);
		}

		return cnmTSoggetto;
	}

	private boolean isRicercaPersonaFisica(MinSoggettoVO minSoggettoVO) {
		return minSoggettoVO.getPersonaFisica() != null && minSoggettoVO.getPersonaFisica();
	}

	private boolean isRicercaCodiceFiscale(MinSoggettoVO minSoggettoVO) {
		return !StringUtils.isEmpty(minSoggettoVO.getCodiceFiscale());
	}

	private boolean isRicercaCodFiscaleOrPiva(MinSoggettoVO minSoggettoVO) {
		return isRicercaCodiceFiscale(minSoggettoVO) || !StringUtils.isEmpty(minSoggettoVO.getPartitaIva());
	}
	
	
	@Override
	public SoggettoVO attachResidenzaPregressi(SoggettoVO sog, CnmTSoggetto cnmTSoggetto, Integer id) {
		// 20201217_LC - JIRA 118 - metodo centralizzato per mpostare sul soggetto la residenza relativa allo specifico verbale, se esiste
		CnmTResidenza cnmTResidenza = cnmTResidenzaRepository.findByCnmTSoggettoAndIdVerbale(cnmTSoggetto, id);
		if(cnmTResidenza != null) {
			sog = residenzaEntityMapper.mapEntitytoVOUpdate(sog, cnmTResidenza);				
		}
		return sog;
	}
	
}
