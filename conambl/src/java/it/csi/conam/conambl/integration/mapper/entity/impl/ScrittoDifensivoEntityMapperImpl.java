/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.*;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.vo.leggi.RiferimentiNormativiVO;
import it.csi.conam.conambl.vo.verbale.ScrittoDifensivoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScrittoDifensivoEntityMapperImpl implements ScrittoDifensivoEntityMapper {


	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private AllegatoEntityMapper allegatoEntityMapper;
	@Autowired
	private AmbitoEntityMapper ambitoEntityMapper;
	@Autowired
	private NormaEntityMapper normaEntityMapper;
	@Autowired
	private ArticoloEntityMapper articoloEntityMapper;
	@Autowired
	private LetteraEntityMapper letteraEntityMapper;
	@Autowired
	private CommaEntityMapper commaEntityMapper;
	@Autowired
	private EnteEntityMapper enteEntityMapper;
	@Autowired
	private CnmDAmbitoRepository cnmDAmbitoRepository;
	@Autowired
	private CnmDEnteRepository cnmDEnteRepository;
	@Autowired
	private CnmDNormaRepository cnmDNormaRepository;
	@Autowired
	private CnmDArticoloRepository cnmDArticoloRepository;
	@Autowired
	private CnmDCommaRepository cnmDCommaRepository;
	@Autowired
	private CnmDLetteraRepository cnmDLetteraRepository;
	@Autowired
	private ModalitaCaricamentoEntityMapper modalitaCaricamentoEntityMapper;

	
	
	
	@Override
	public ScrittoDifensivoVO mapEntityToVO(CnmTScrittoDifensivo dto) {
		return mapEntityToVO(dto, true); 	// filtra le norme scadute
	}	
	
	
	@Override
	public ScrittoDifensivoVO mapEntityToVO(CnmTScrittoDifensivo dto, boolean filtraNormeScadute) {
		if (dto == null) return null;
		
		ScrittoDifensivoVO scrittoDifensivoVO = new ScrittoDifensivoVO();
		
		scrittoDifensivoVO.setId(dto.getIdScrittoDifensivo());
		scrittoDifensivoVO.setNomeFile(dto.getNomeFile());
		scrittoDifensivoVO.setNumeroProtocollo(dto.getNumeroProtocollo());
		scrittoDifensivoVO.setNumVerbaleAccertamento(dto.getNumVerbaleAccertamento());
		scrittoDifensivoVO.setNome(dto.getNome());
		scrittoDifensivoVO.setCognome(dto.getCognome());
		scrittoDifensivoVO.setRagioneSociale(dto.getRagioneSociale());
		scrittoDifensivoVO.setFlagAssociato(dto.isFlagAssociato());
		scrittoDifensivoVO.setDataProtocollo(utilsDate.asLocalDateTime(dto.getDataOraProtocollo()));
		
		scrittoDifensivoVO.setAllegato(allegatoEntityMapper.mapEntityToVO(dto.getCnmTAllegato()));
		scrittoDifensivoVO.setAmbito(ambitoEntityMapper.mapEntityToVO(dto.getCnmDAmbito()));	
		scrittoDifensivoVO.setEnteRiferimentiNormativi(enteEntityMapper.mapEntityToVO(dto.getCnmDEnte()));		
		
		scrittoDifensivoVO.setModalitaCaricamento(modalitaCaricamentoEntityMapper.mapEntityToVO(dto.getCnmDModalitaCaricamento()));

		
		
		// rif normativo
		RiferimentiNormativiVO rifNorm = new RiferimentiNormativiVO();
		rifNorm.setAmbito(ambitoEntityMapper.mapEntityToVO(dto.getCnmDAmbito()));
		if (dto.getCnmDNorma() != null) rifNorm.setNorma(normaEntityMapper.mapEntityToVO(dto.getCnmDNorma()));
		if (dto.getCnmDArticolo() != null) rifNorm.setArticolo(articoloEntityMapper.mapEntityToVO(dto.getCnmDArticolo()));
		if (dto.getCnmDComma() != null) rifNorm.setComma(commaEntityMapper.mapEntityToVO(dto.getCnmDComma()));
		if (dto.getCnmDLettera() != null) rifNorm.setLettera(letteraEntityMapper.mapEntityToVO(dto.getCnmDLettera()));
		scrittoDifensivoVO.setRiferimentoNormativo(rifNorm);	
		
		// rif normativi
//		List<CnmRScrittoIllecito> cnmRScrittoIllecitoList = cnmRScrittoIllecitoRepository.findByCnmTScrittoDifensivo(dto);
//		List<RiferimentiNormativiVO> listRifNorm = new ArrayList<>();
//		EnteVO enteRiferimentiNormativi = null;
//		if (cnmRScrittoIllecitoList != null && !cnmRScrittoIllecitoList.isEmpty()) {
//			for (CnmRScrittoIllecito cnmRScrittoIllecito : cnmRScrittoIllecitoList) {
//				RiferimentiNormativiVO rifNorm = new RiferimentiNormativiVO();
//				CnmDLettera cnmDLettera = cnmRScrittoIllecito.getCnmDLettera();
//				CnmDComma cnmDComma = cnmDLettera.getCnmDComma();
//				CnmDArticolo cnmDArticolo = cnmDComma.getCnmDArticolo();
//				CnmREnteNorma cnmREnteNorma = cnmDArticolo.getCnmREnteNorma();
//				CnmDNorma cnmDNorma = cnmREnteNorma.getCnmDNorma();
//				CnmDAmbito cnmDAmbito = cnmDNorma.getCnmDAmbito();
//				if (enteRiferimentiNormativi == null) {
//					enteRiferimentiNormativi = enteEntityMapper.mapEntityToVO(cnmREnteNorma.getCnmDEnte());
//				}
//				if (!filtraNormeScadute) {
//					rifNorm.setLettera(letteraEntityMapper.mapEntityToVO(cnmDLettera));
//					rifNorm.setArticolo(articoloEntityMapper.mapEntityToVO(cnmDArticolo));
//					rifNorm.setComma(commaEntityMapper.mapEntityToVO(cnmDComma));
//					rifNorm.setNorma(normaEntityMapper.mapEntityToVO(cnmDNorma));
//					rifNorm.setAmbito(ambitoEntityMapper.mapEntityToVO(cnmDAmbito));
//					rifNorm.setId(cnmRScrittoIllecito.getIdScrittoIllecito());
//					listRifNorm.add(rifNorm);
//				} else {
//					rifNorm.setAmbito(ambitoEntityMapper.mapEntityToVO(cnmDAmbito));
//					LocalDate dataFineValiditaEnteNorma = utilsDate.asLocalDate(cnmREnteNorma.getFineValidita());
//					LocalDate now = LocalDate.now();
//					if (dataFineValiditaEnteNorma == null || dataFineValiditaEnteNorma.isAfter(now)) {
//						rifNorm.setNorma(normaEntityMapper.mapEntityToVO(cnmDNorma));
//						rifNorm.setId(cnmRScrittoIllecito.getIdScrittoIllecito());
//						LocalDate dataFineValiditaArticolo = utilsDate.asLocalDate(cnmDArticolo.getFineValidita());
//						if (dataFineValiditaArticolo == null || dataFineValiditaArticolo.isAfter(now)) {
//							rifNorm.setArticolo(articoloEntityMapper.mapEntityToVO(cnmDArticolo));
//							LocalDate dataFineValiditaComma = utilsDate.asLocalDate(cnmDComma.getFineValidita());
//							if (dataFineValiditaComma == null || dataFineValiditaComma.isAfter(now)) {
//								rifNorm.setComma(commaEntityMapper.mapEntityToVO(cnmDComma));
//								LocalDate dataFineValiditaLettera = utilsDate.asLocalDate(cnmDLettera.getFineValidita());
//								if (dataFineValiditaLettera == null || dataFineValiditaLettera.isAfter(now))
//									rifNorm.setLettera(letteraEntityMapper.mapEntityToVO(cnmDLettera));
//							}
//						}
//						listRifNorm.add(rifNorm);
//					}
//				}
//			}
//		}
//		
//		// ha un solo riferimento (o nessuno)
//		RiferimentiNormativiVO riferimentoNormativo = listRifNorm != null && !listRifNorm.isEmpty() ? listRifNorm.get(0) : null;
//		scrittoDifensivoVO.setRiferimentoNormativo(riferimentoNormativo);	
		
		return scrittoDifensivoVO;
	}
	
	
	
	@Override
	public CnmTScrittoDifensivo mapVOtoEntity(ScrittoDifensivoVO vo) {
		if (vo == null) return null;
		CnmTScrittoDifensivo cnmTScrittoDifensivo = new CnmTScrittoDifensivo();
		mapVOtoEntityUpdate(vo, cnmTScrittoDifensivo);
		return cnmTScrittoDifensivo;
	}
	
	
	@Override
	public CnmTScrittoDifensivo mapVOtoEntityUpdate(ScrittoDifensivoVO vo, CnmTScrittoDifensivo cnmTScrittoDifensivo) {
		if (vo == null) return null;

		// campi editabili dall'utente
		
		if (vo.getNumVerbaleAccertamento() != null && !vo.getNumVerbaleAccertamento().isEmpty())
			cnmTScrittoDifensivo.setNumVerbaleAccertamento(vo.getNumVerbaleAccertamento());		

		// 20210503 update anche se NULL
		
		if (vo.getNome() != null) {
			cnmTScrittoDifensivo.setNome(vo.getNome().toUpperCase());
		} else {
			cnmTScrittoDifensivo.setNome(null);
		}
			

		if (vo.getCognome() != null) {
			cnmTScrittoDifensivo.setCognome(vo.getCognome().toUpperCase());	
		} else {
			cnmTScrittoDifensivo.setCognome(null);
		}

		if (vo.getRagioneSociale() != null) {
			cnmTScrittoDifensivo.setRagioneSociale(vo.getRagioneSociale().toUpperCase());	
		} else {
			cnmTScrittoDifensivo.setRagioneSociale(null);	
		}
		
		
		// ambito
		if (vo.getAmbito() != null && vo.getAmbito().getId() != null ) {
			CnmDAmbito cnmDAmbito = cnmDAmbitoRepository.findOne(vo.getAmbito().getId().intValue());
			if (cnmDAmbito == null) throw new SecurityException("idAmbito inesistente");
			cnmTScrittoDifensivo.setCnmDAmbito(cnmDAmbito);
		}
		
		// ente
		if (vo.getEnteRiferimentiNormativi() != null && vo.getEnteRiferimentiNormativi().getId() != null) {
			CnmDEnte cnmDEnte = cnmDEnteRepository.findOne(vo.getEnteRiferimentiNormativi().getId());
			if (cnmDEnte == null) throw new SecurityException("idEnte inesistente");
			cnmTScrittoDifensivo.setCnmDEnte(cnmDEnte);			
		}
		
		// altri campi (facoltativi del riferimento normativo)
		cnmTScrittoDifensivo.setCnmDNorma(null);
		cnmTScrittoDifensivo.setCnmDArticolo(null);
		cnmTScrittoDifensivo.setCnmDComma(null);
		cnmTScrittoDifensivo.setCnmDLettera(null);
		
		if (vo.getRiferimentoNormativo() != null) {
			if (vo.getRiferimentoNormativo().getNorma() != null && vo.getRiferimentoNormativo().getNorma().getId() != null) {
				CnmDNorma cnmDNorma = cnmDNormaRepository.findOne(vo.getRiferimentoNormativo().getNorma().getId().intValue());
				if (cnmDNorma == null) throw new SecurityException("idNorma inesistente");
				cnmTScrittoDifensivo.setCnmDNorma(cnmDNorma);
			}		
			if (vo.getRiferimentoNormativo().getArticolo() != null && vo.getRiferimentoNormativo().getArticolo().getId() != null) {
				CnmDArticolo cnmDArticolo = cnmDArticoloRepository.findOne(vo.getRiferimentoNormativo().getArticolo().getId().intValue());
				if (cnmDArticolo == null) throw new SecurityException("idArticolo inesistente");
				cnmTScrittoDifensivo.setCnmDArticolo(cnmDArticolo);
			}	
			if (vo.getRiferimentoNormativo().getComma() != null && vo.getRiferimentoNormativo().getComma().getId() != null) {
				CnmDComma cnmDComma = cnmDCommaRepository.findOne(vo.getRiferimentoNormativo().getComma().getId().intValue());
				if (cnmDComma == null) throw new SecurityException("idComma inesistente");
				cnmTScrittoDifensivo.setCnmDComma(cnmDComma);
			}	
			if (vo.getRiferimentoNormativo().getLettera() != null && vo.getRiferimentoNormativo().getLettera().getId() != null) {
				CnmDLettera cnmDLettera = cnmDLetteraRepository.findOne(vo.getRiferimentoNormativo().getLettera().getId().intValue());
				if (cnmDLettera == null) throw new SecurityException("idLettera inesistente");
				cnmTScrittoDifensivo.setCnmDLettera(cnmDLettera);
			}	
		}
		

		
			
		return cnmTScrittoDifensivo;
	}




}
