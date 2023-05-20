/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleIllecito;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.mapper.entity.*;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleIllecitoRepository;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVerbaleVO;
import it.csi.conam.conambl.vo.verbale.StatoVerbaleVO;
import it.csi.conam.conambl.vo.verbale.VerbaleSoggettoVO;
import it.csi.conam.conambl.vo.verbale.VerbaleSoggettoVORaggruppatoPerSoggetto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VerbaleSoggettoEntityMapperImpl implements VerbaleSoggettoEntityMapper {
	
	@Autowired
	private VerbaleEntityMapper verbaleEntityMapper;
	@Autowired
	private CnmRVerbaleIllecitoRepository cnmRVerbaleIllecitoRepository;
	
	
	@Autowired
	private StatoVerbaleEntityMapper statoEntityMapper;
	@Autowired
	private StatoManualeEntityMapper statoManualeEntityMapper;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private SoggettoEntityMapper soggettoEntityMapper;
	@Autowired
	private RuoloSoggettoEntityMapper ruoloSoggettoEntityMapper;
	@Autowired
	private EnteEntityMapper enteEntityMapper;
	
	@Override
	public VerbaleSoggettoVORaggruppatoPerSoggetto mapEntityToVORaggruppatoPerSoggetto(List<CnmRVerbaleSoggetto> dtoList) {
		VerbaleSoggettoVORaggruppatoPerSoggetto verbaleSoggettoVORaggruppatoPerSoggetto = new VerbaleSoggettoVORaggruppatoPerSoggetto();
		if (dtoList.size() >0) {
			//DA MIGLIROARE ADESSO IPOTIZZA CI SIA UN SOLO SOGGETTO
			verbaleSoggettoVORaggruppatoPerSoggetto.setSoggetto(soggettoEntityMapper.mapEntityToVO(dtoList.get(0).getCnmTSoggetto()));
			verbaleSoggettoVORaggruppatoPerSoggetto.setRuoloSoggetto(
				ruoloSoggettoEntityMapper.mapEntityToVO(dtoList.get(0).getCnmDRuoloSoggetto())
			);
	
			List<MinSoggettoVerbaleVO> soggettiVerbale = new ArrayList<MinSoggettoVerbaleVO>();
			for (CnmRVerbaleSoggetto verbaleSoggetto : dtoList) {
				MinSoggettoVerbaleVO minSoggettoVerbaleVO = mapCnmRVerbaleSoggettoToMinVO(verbaleSoggetto);
				soggettiVerbale.add(minSoggettoVerbaleVO);
			}
			verbaleSoggettoVORaggruppatoPerSoggetto.setSoggettiVerbale(soggettiVerbale);
		}
		return verbaleSoggettoVORaggruppatoPerSoggetto;
	}
	
	@Override
	public VerbaleSoggettoVO mapEntityToVO(CnmRVerbaleSoggetto dto) {
		VerbaleSoggettoVO verbaleSoggettoVO = new VerbaleSoggettoVO();
		verbaleSoggettoVO.setId(dto.getIdVerbaleSoggetto().longValue());
		verbaleSoggettoVO.setNote(dto.getNote());
		verbaleSoggettoVO.setRecidivo(dto.getRecidivo());
		verbaleSoggettoVO.setVerbale(
			verbaleEntityMapper.mapEntityToMinVO(dto.getCnmTVerbale())
		);
		verbaleSoggettoVO.setSoggetto(
			soggettoEntityMapper.mapEntityToMinVO(dto.getCnmTSoggetto())
		);
		verbaleSoggettoVO.setRuoloSoggetto(
			ruoloSoggettoEntityMapper.mapEntityToVO(dto.getCnmDRuoloSoggetto())
		);
		return verbaleSoggettoVO;
	}

	
	private MinSoggettoVerbaleVO mapCnmRVerbaleSoggettoToMinVO(CnmRVerbaleSoggetto verbaleSoggetto) {
		MinSoggettoVerbaleVO minSoggettoVerbaleVO = new MinSoggettoVerbaleVO();
		minSoggettoVerbaleVO.setId(verbaleSoggetto.getIdVerbaleSoggetto().longValue());
		
		
		minSoggettoVerbaleVO.setNote(verbaleSoggetto.getNote());
		minSoggettoVerbaleVO.setRecidivo(verbaleSoggetto.getRecidivo());
		minSoggettoVerbaleVO.setIdSoggetto(verbaleSoggetto.getCnmTSoggetto().getIdSoggetto().longValue());
		
		
		CnmTVerbale verbale = verbaleSoggetto.getCnmTVerbale();
		minSoggettoVerbaleVO.setEnteAccertatore(enteEntityMapper.mapEntityToVO(verbale.getCnmDEnte()));
		minSoggettoVerbaleVO.setIdVerbale(verbale.getIdVerbale().longValue());
		minSoggettoVerbaleVO.setNumero(verbale.getNumVerbale());
		StatoVerbaleVO stato = statoEntityMapper.mapEntityToVO(verbale.getCnmDStatoVerbale());
		minSoggettoVerbaleVO.setStato(stato);
		minSoggettoVerbaleVO.setNumeroProtocollo(verbale.getNumeroProtocollo());
		minSoggettoVerbaleVO.setDataCaricamento(utilsDate.asLocalDateTime(verbale.getDataOraInsert()));
		minSoggettoVerbaleVO.setUser(verbale.getCnmTUser2().getNome() + " " + verbale.getCnmTUser2().getCognome());
		minSoggettoVerbaleVO.setModificabile(Boolean.FALSE);
		minSoggettoVerbaleVO.setStatoManuale(statoManualeEntityMapper.mapEntityToVO(verbale.getCnmDStatoManuale()));

		minSoggettoVerbaleVO.setDescNormaViolata(getDescrizioneNormativaViolata(verbale));
		return minSoggettoVerbaleVO;
	}

	private String getDescrizioneNormativaViolata(CnmTVerbale verbale) {
		List<CnmRVerbaleIllecito> cnmRVerbaleIllecitos = cnmRVerbaleIllecitoRepository.findByCnmTVerbale(verbale);
		
		if (isValidVerbaleIllecitos(cnmRVerbaleIllecitos)) {

			// 20210521_LC segnalazione H1231, H1517
			String descNorma =
					cnmRVerbaleIllecitos.get(0).getCnmDLettera().getCnmDComma().getCnmDArticolo().getCnmREnteNorma().getCnmDNorma().getCnmDAmbito().getAcronimo() + " - " +
					cnmRVerbaleIllecitos.get(0).getCnmDLettera().getCnmDComma().getCnmDArticolo().getCnmREnteNorma().getCnmDNorma().getRiferimentoNormativo() + " - " +
					cnmRVerbaleIllecitos.get(0).getCnmDLettera().getCnmDComma().getCnmDArticolo().getDescArticolo() + " - " +
					cnmRVerbaleIllecitos.get(0).getCnmDLettera().getCnmDComma().getDescComma() + " - " +
					cnmRVerbaleIllecitos.get(0).getCnmDLettera().getLettera();
		
			return descNorma;
		}
		return null;
	}
	
	
	private Boolean isValidVerbaleIllecitos(List<CnmRVerbaleIllecito> verbaleIllecitos) {
		if (
			verbaleIllecitos != null &&
			verbaleIllecitos.size() > 0 &&
			verbaleIllecitos.get(0).getCnmDLettera() != null &&
			verbaleIllecitos.get(0).getCnmDLettera().getCnmDComma() != null &&
			verbaleIllecitos.get(0).getCnmDLettera().getCnmDComma().getCnmDArticolo() != null &&
			verbaleIllecitos.get(0).getCnmDLettera().getCnmDComma().getCnmDArticolo().getCnmREnteNorma() != null &&
			verbaleIllecitos.get(0).getCnmDLettera().getCnmDComma().getCnmDArticolo().getCnmREnteNorma().getCnmDNorma() != null &&
			verbaleIllecitos.get(0).getCnmDLettera().getCnmDComma().getCnmDArticolo().getCnmREnteNorma().getCnmDNorma().getCnmDAmbito() != null &&
			verbaleIllecitos.get(0).getCnmDLettera().getCnmDComma().getCnmDArticolo().getCnmREnteNorma().getCnmDNorma().getRiferimentoNormativo() != null &&
			!verbaleIllecitos.get(0).getCnmDLettera().getCnmDComma().getCnmDArticolo().getCnmREnteNorma().getCnmDNorma().getRiferimentoNormativo().isEmpty()
		) {
			return true;
		}
		return false;
	}
	
	@Override
	public CnmRVerbaleSoggetto mapVOtoEntity(VerbaleSoggettoVO vo) {
		CnmRVerbaleSoggetto cnmRVerbaleSoggetto = new CnmRVerbaleSoggetto();

		cnmRVerbaleSoggetto.setIdVerbaleSoggetto(vo.getId().intValue());
		cnmRVerbaleSoggetto.setNote(vo.getNote());
		cnmRVerbaleSoggetto.setRecidivo(vo.getRecidivo());
		
		/*cnmRVerbaleSoggetto.setCnmTVerbale(
			verbaleEntityMapper.mapVOtoEntity(vo.getVerbale(),null)
		);
		cnmRVerbaleSoggetto.setCnmTSoggetto(
			soggettoEntityMapper.mapVOtoEntity(vo.getSoggetto())
		);*/
		cnmRVerbaleSoggetto.setCnmDRuoloSoggetto(
			ruoloSoggettoEntityMapper.mapVOtoEntity(vo.getRuoloSoggetto())
		);
		
		return cnmRVerbaleSoggetto;
	}
}
