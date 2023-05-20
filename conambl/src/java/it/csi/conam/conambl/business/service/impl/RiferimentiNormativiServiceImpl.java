/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl;

import it.csi.conam.conambl.business.service.RiferimentiNormativiService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.*;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.vo.leggi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class RiferimentiNormativiServiceImpl implements RiferimentiNormativiService {

	@Autowired
	private CnmREnteNormaRepository cnmREnteNormaRepository;
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
	private CnmDAmbitoRepository cnmDAmbitoRepository;
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
	private CnmCParametroRepository cnmCParametroRepository;

	@Override
	public List<AmbitoVO> getAmbitiByIdEnte(Long idEnte, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento) {
		if (idEnte == null)
			throw new RuntimeException("id ente non deve essere null");
		if (filterDataValidita == null)
			throw new RuntimeException("filterDataValidita non deve essere null");

		if (!SecurityUtils.isEnteNormaValido(idEnte))
			throw new SecurityException("L'utente non puo' accedere a questo servizio: l'id ente non e' valido");

		List<AmbitoVO> ambitoVOList = new ArrayList<>();

		CnmDEnte cnmDEnte = cnmDEnteRepository.findOne(idEnte);
		if (cnmDEnte == null)
			throw new RuntimeException("idEnte inesistente");

		List<CnmREnteNorma> cnmREnteNormaList;
		if (filterPregresso) { 
			Date dataDiscriminantePregresso = cnmCParametroRepository.findOne(Constants.ID_DATA_DISCRIMINANTE_GESTIONE_PREGRESSO).getValoreDate();
			if(dataAccertamento!=null) {
				if(dataAccertamento.after(dataDiscriminantePregresso)) {
					throw new BusinessException(ErrorCode.DATA_ACCERTAMENTO_NON_PREGRESSA);
				}
				cnmREnteNormaList = cnmREnteNormaRepository.findByCnmDEnteAndFineValiditaAndNotEliminato(cnmDEnte, dataAccertamento);
			} else {
				cnmREnteNormaList = cnmREnteNormaRepository.findByCnmDEnteAndPregressoAndNotEliminato(cnmDEnte, dataDiscriminantePregresso);
			}
		}
		else if (filterDataValidita)
			cnmREnteNormaList = cnmREnteNormaRepository.findByCnmDEnteAndFineValiditaAndNotEliminato(cnmDEnte, new Date());
		else
			cnmREnteNormaList = cnmREnteNormaRepository.findByCnmDEnteAndNotEliminato(cnmDEnte);

		Map<String, String> map = new HashMap<String, String>();

		if (cnmREnteNormaList != null && !cnmREnteNormaList.isEmpty()) {
			for (CnmREnteNorma cnmREnteNorma : cnmREnteNormaList) {
				CnmDAmbito cnmDAmbito = cnmDAmbitoRepository.findByCnmDNormaAndNotEliminato(cnmREnteNorma.getCnmDNorma());
				if (map != null && !map.containsValue(cnmDAmbito.getAcronimo()))
					ambitoVOList.add(ambitoEntityMapper.mapEntityToVO(cnmDAmbito));

				map.put(cnmDAmbito.getAcronimo(), cnmDAmbito.getAcronimo());
			}
		}

		return ambitoVOList;
	}

	@Override
	public List<NormaVO> getNormeByIdAmbitoAndIdEnte(Integer idAmbito, Long idEnte, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento) {
		if (idEnte == null)
			throw new RuntimeException("id ente non deve essere null");
		if (filterDataValidita == null)
			throw new RuntimeException("filterDataValidita non deve essere null");

		if (!SecurityUtils.isEnteNormaValido(idEnte))
			throw new SecurityException("L'utente non può accedere a questo servizio: l'id ente non è valido");

		List<NormaVO> normaVOList = new ArrayList<>();

		CnmDEnte cnmDEnte = cnmDEnteRepository.findOne(idEnte);
		if (cnmDEnte == null)
			throw new RuntimeException("idEnte inesistente");

		CnmDAmbito cnmDAmbito = cnmDAmbitoRepository.findOne(idAmbito);
		if (cnmDAmbito == null)
			throw new RuntimeException("idAmbito inesistente");

		List<CnmREnteNorma> cnmREnteNormaList;
		if(filterPregresso) {
			Date dataDiscriminantePregresso = cnmCParametroRepository.findOne(Constants.ID_DATA_DISCRIMINANTE_GESTIONE_PREGRESSO).getValoreDate();
			if(dataAccertamento!=null) {
				if(dataAccertamento.after(dataDiscriminantePregresso)) {
					throw new BusinessException(ErrorCode.DATA_ACCERTAMENTO_NON_PREGRESSA);
				}
				cnmREnteNormaList = cnmREnteNormaRepository.findByCnmDEnteAndCnmDAmbitoAndFineValiditaAndNotEliminato(cnmDEnte, cnmDAmbito, dataAccertamento);
			}else {
				cnmREnteNormaList = cnmREnteNormaRepository.findByCnmDEnteAndCnmDAmbitoAndPregressoAndNotEliminato(cnmDEnte, cnmDAmbito, dataDiscriminantePregresso);
			}
		}
		else if (filterDataValidita)
			cnmREnteNormaList = cnmREnteNormaRepository.findByCnmDEnteAndCnmDAmbitoAndFineValiditaAndNotEliminato(cnmDEnte, cnmDAmbito, new Date());
		else
			cnmREnteNormaList = cnmREnteNormaRepository.findByCnmDEnteAndCnmDAmbitoAndNotEliminato(cnmDEnte, cnmDAmbito);

		if (cnmREnteNormaList != null && !cnmREnteNormaList.isEmpty())
			for (CnmREnteNorma cnmREnteNorma : cnmREnteNormaList)
				normaVOList.add(normaEntityMapper.mapEntityToVO(cnmREnteNorma.getCnmDNorma()));

		return normaVOList;
	}

	@Override
	public List<ArticoloVO> getArticoliByIdNormaAndEnte(Long idNorma, Long idEnte, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento) {
		if (idNorma == null)
			throw new RuntimeException("idNorma non deve essere null");
		if (idEnte == null)
			throw new RuntimeException("idEnte non deve essere null");
		if (filterDataValidita == null)
			throw new RuntimeException("filterDataValidita non deve essere null");

		CnmDNorma cnmDNorma = cnmDNormaRepository.findOne(idNorma.intValue());
		if (cnmDNorma == null)
			throw new RuntimeException("idNorma inesistente");

		CnmDEnte cnmDEnte = cnmDEnteRepository.findOne(idEnte);
		if (cnmDEnte == null)
			throw new RuntimeException("idEnte inesistente");

		List<CnmDArticolo> articoloList;
		if(filterPregresso) {
			Date dataDiscriminantePregresso = cnmCParametroRepository.findOne(Constants.ID_DATA_DISCRIMINANTE_GESTIONE_PREGRESSO).getValoreDate();
			if(dataAccertamento!=null) {
				if(dataAccertamento.after(dataDiscriminantePregresso)) {
					throw new BusinessException(ErrorCode.DATA_ACCERTAMENTO_NON_PREGRESSA);
				}
				CnmREnteNorma cnmREnteNorma = cnmREnteNormaRepository.findByCnmDNormaAndCnmDEnteFineValiditaAndNotEliminato(cnmDNorma, cnmDEnte, dataAccertamento);
				if (cnmREnteNorma == null)
					return new ArrayList<>();
				articoloList = cnmDArticoloRepository.findByCnmREnteNormaAndFineValiditaAndNotEliminato(cnmREnteNorma, dataAccertamento);
			}else {
				CnmREnteNorma cnmREnteNorma = cnmREnteNormaRepository.findByCnmDNormaAndCnmDEntePregressoAndNotEliminato(cnmDNorma, cnmDEnte, dataDiscriminantePregresso);
				if (cnmREnteNorma == null)
					return new ArrayList<>();
				articoloList = cnmDArticoloRepository.findByCnmREnteNormaAndPregressoAndNotEliminato(cnmREnteNorma, dataDiscriminantePregresso);
			}
			
		}
		else if (filterDataValidita) {
			CnmREnteNorma cnmREnteNorma = cnmREnteNormaRepository.findByCnmDNormaAndCnmDEnteFineValiditaAndNotEliminato(cnmDNorma, cnmDEnte, new Date());
			if (cnmREnteNorma == null)
				return new ArrayList<>();
			articoloList = cnmDArticoloRepository.findByCnmREnteNormaAndFineValiditaAndNotEliminato(cnmREnteNorma, new Date());
		} else {
			CnmREnteNorma cnmREnteNorma = cnmREnteNormaRepository.findByCnmDNormaAndCnmDEnteAndNotEliminato(cnmDNorma, cnmDEnte);
			if (cnmREnteNorma == null)
				throw new RuntimeException("cnmREnteNorma inesistente");
			articoloList = cnmDArticoloRepository.findByCnmREnteNormaAndNotEliminato(cnmREnteNorma);
		}

		return articoloEntityMapper.mapListEntityToListVO(articoloList);
	}

	@Override
	public List<CommaVO> getCommaByIdArticolo(Long idArticolo, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento) {
		if (idArticolo == null)
			throw new RuntimeException("idArticolo non deve essere null");
		if (filterDataValidita == null)
			throw new RuntimeException("filterDataValidita non deve essere null");
		CnmDArticolo cnmDArticolo = cnmDArticoloRepository.findOne(idArticolo.intValue());
		if (cnmDArticolo == null)
			throw new RuntimeException("idArticolo inesistente");

		List<CnmDComma> commaList;
		if(filterPregresso) {
			Date dataDiscriminantePregresso = cnmCParametroRepository.findOne(Constants.ID_DATA_DISCRIMINANTE_GESTIONE_PREGRESSO).getValoreDate();
			if(dataAccertamento!=null) {
				if(dataAccertamento.after(dataDiscriminantePregresso)) {
					throw new BusinessException(ErrorCode.DATA_ACCERTAMENTO_NON_PREGRESSA);
				}
				commaList = cnmDCommaRepository.findByCnmDArticoloAndFineValiditaAndNotEliminato(cnmDArticolo, dataAccertamento);
			} else {
				commaList = cnmDCommaRepository.findByCnmDArticoloAndPregressoAndNotEliminato(cnmDArticolo, dataDiscriminantePregresso);
			}
			
		}else {
			if (filterDataValidita)
				commaList = cnmDCommaRepository.findByCnmDArticoloAndFineValiditaAndNotEliminato(cnmDArticolo, new Date());
			else
				commaList = cnmDCommaRepository.findByCnmDArticoloAndNotEliminato(cnmDArticolo);
		}
		return commaEntityMapper.mapListEntityToListVO(commaList);
	}

	@Override
	public List<LetteraVO> getLetteraByIdComma(Long idComma, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento) {
		if (idComma == null)
			throw new RuntimeException("idComma non deve essere null");
		if (filterDataValidita == null)
			throw new RuntimeException("filterDataValidita non deve essere null");
		CnmDComma cnmDComma = cnmDCommaRepository.findOne(idComma.intValue());
		if (cnmDComma == null)
			throw new RuntimeException("idComma inesistente");

		List<CnmDLettera> letteraList;
		if(filterPregresso) {
			Date dataDiscriminantePregresso = cnmCParametroRepository.findOne(Constants.ID_DATA_DISCRIMINANTE_GESTIONE_PREGRESSO).getValoreDate();
			if(dataAccertamento!=null) {
				if(dataAccertamento.after(dataDiscriminantePregresso)) {
					throw new BusinessException(ErrorCode.DATA_ACCERTAMENTO_NON_PREGRESSA);
				}
				letteraList = cnmDLetteraRepository.findByCnmDCommaAndFineValiditaAndNotEliminato(cnmDComma, dataAccertamento);
			}else {
				letteraList = cnmDLetteraRepository.findByCnmDCommaAndPregressoAndNotEliminato(cnmDComma, dataDiscriminantePregresso);
			}
		}else {
			if (filterDataValidita)
				letteraList = cnmDLetteraRepository.findByCnmDCommaAndFineValiditaAndNotEliminato(cnmDComma, new Date());
			else
				letteraList = cnmDLetteraRepository.findByCnmDCommaAndNotEliminato(cnmDComma);
		}
		
		return letteraEntityMapper.mapListEntityToListVO(letteraList);
	}

	@Override
	public List<CnmDLettera> getLettereByEnte(Long idEnte, Boolean filterDataValidita) {
		if (filterDataValidita == null)
			throw new RuntimeException("filterDataValidita non deve essere null");
		if (idEnte == null)
			throw new RuntimeException("idEnte non deve essere null");
		if (idEnte != null) {
			CnmDEnte cnmDEnte = cnmDEnteRepository.findOne(idEnte);
			if (cnmDEnte == null)
				throw new RuntimeException("idEnte inesistente");

			if (filterDataValidita) {
				List<CnmREnteNorma> cnmREnteNormaList = cnmREnteNormaRepository.findByCnmDEnteAndFineValiditaAndNotEliminato(cnmDEnte, new Date());
				if (cnmREnteNormaList != null && !cnmREnteNormaList.isEmpty()) {
					List<CnmDArticolo> articoli = cnmDArticoloRepository.findByCnmREnteNormaInAndFineValiditaAndNotEliminato(cnmREnteNormaList);
					List<CnmDComma> comma = cnmDCommaRepository.findByCnmDArticoloInAndFineValiditaAndNotEliminato(articoli);
					return cnmDLetteraRepository.findByCnmDCommaInAndFineValiditaAndNotEliminato(comma);
				}
			} else {
				List<CnmREnteNorma> cnmREnteNormaList = cnmREnteNormaRepository.findByCnmDEnteAndNotEliminato(cnmDEnte);
				if (cnmREnteNormaList != null && !cnmREnteNormaList.isEmpty()) {
					List<CnmDArticolo> articoli = cnmDArticoloRepository.findByCnmREnteNormaInAndNotEliminato(cnmREnteNormaList);
					List<CnmDComma> comma = cnmDCommaRepository.findByCnmDArticoloInAndNotEliminato(articoli);
					return cnmDLetteraRepository.findByCnmDCommaInAndNotEliminato(comma);
				}
			}
		}
		return null;
	}

	@Override
	public List<CnmDLettera> getLettereByIdNormaAndEnte(Long idNorma, Long idEnte, Boolean filterDataValidita) {
		if (filterDataValidita == null)
			throw new RuntimeException("filterDataValidita non deve essere null");
		if (idNorma == null || idEnte == null)
			throw new SecurityException("idNorma o idEnte non valorizzato");

		CnmDNorma cnmDNorma = cnmDNormaRepository.findOne(idNorma.intValue());
		CnmDEnte cnmDEnte = cnmDEnteRepository.findOne(idEnte);
		if (cnmDNorma == null && cnmDEnte == null)
			throw new SecurityException("cnmDNorma o cnmDEnte null");

		List<CnmDComma> commaList;
		List<CnmDLettera> output;
		if (filterDataValidita) {
			CnmREnteNorma cnmREnteNorma = cnmREnteNormaRepository.findByCnmDNormaAndCnmDEnteFineValiditaAndNotEliminato(cnmDNorma, cnmDEnte, new Date());
			if (cnmREnteNorma == null)
				throw new RuntimeException("cnmREnteNorma inesistente");
			List<CnmDArticolo> articoli = cnmDArticoloRepository.findByCnmREnteNormaAndFineValiditaAndNotEliminato(cnmREnteNorma, new Date());
			commaList = cnmDCommaRepository.findByCnmDArticoloInAndFineValiditaAndNotEliminato(articoli);
			output = cnmDLetteraRepository.findByCnmDCommaInAndFineValiditaAndNotEliminato(commaList);
		} else {
			CnmREnteNorma cnmREnteNorma = cnmREnteNormaRepository.findByCnmDNormaAndCnmDEnteAndNotEliminato(cnmDNorma, cnmDEnte);
			if (cnmREnteNorma == null)
				throw new RuntimeException("cnmREnteNorma inesistente");
			List<CnmDArticolo> articoli = cnmDArticoloRepository.findByCnmREnteNormaAndNotEliminato(cnmREnteNorma);
			commaList = cnmDCommaRepository.findByCnmDArticoloInAndNotEliminato(articoli);
			output = cnmDLetteraRepository.findByCnmDCommaInAndNotEliminato(commaList);
		}

		return output;

	}
}
