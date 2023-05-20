/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import it.csi.conam.conambl.business.service.ProntuarioService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.*;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.request.leggi.RicercaLeggeProntuarioRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.leggi.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class ProntuarioServiceImpl implements ProntuarioService {

	@Autowired
	private CnmREnteNormaRepository cnmREnteNormaRepository;
	@Autowired
	private CnmDEnteRepository cnmDEnteRepository;
	@Autowired
	private CnmDArticoloRepository cnmDArticoloRepository;
	@Autowired
	private CnmDCommaRepository cnmDCommaRepository;
	@Autowired
	private CnmDLetteraRepository cnmDLetteraRepository;
	@Autowired
	private CnmRVerbaleIllecitoRepository cnmRVerbaleIllecitoRepository;
	@Autowired
	private CnmDNormaRepository cnmDNormaRepository;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CnmDAmbitoRepository cnmDAmbitoRepository;

	@Autowired
	private UtilsDate utilsDate;

	@Autowired
	private LetteraEntityMapper letteraEntityMapper;
	@Autowired
	private CommaEntityMapper commaEntityMapper;
	@Autowired
	private ArticoloEntityMapper articoloEntityMapper;
	@Autowired
	private NormaEntityMapper normaEntityMapper;
	@Autowired
	private EnteEntityMapper enteEntityMapper;
	@Autowired
	private AmbitoEntityMapper ambitoEntityMapper;
	@Autowired
	private ProntuarioEntityMapper prontuarioEntityMapper;

	@Override
	public List<ProntuarioVO> ricercaLeggeProntuario(RicercaLeggeProntuarioRequest request) {
		if (request.getAmbito() == null)
			throw new IllegalArgumentException("ambito is null");
		if (request.getEnteLegge() == null)
			throw new IllegalArgumentException("ente legge is null");
		if (request.getAmbito().getId() == null)
			throw new IllegalArgumentException("id ambito is null");
		if (request.getEnteLegge().getId() == null)
			throw new IllegalArgumentException("id legge is null");

		List<ProntuarioVO> prontuarioList = new ArrayList<>();
		CnmDEnte cnmDEnte = cnmDEnteRepository.findOne(request.getEnteLegge().getId());
		CnmDAmbito cnmDAmbito = cnmDAmbitoRepository.findOne(request.getAmbito().getId().intValue());
		List<CnmREnteNorma> leggeList = cnmREnteNormaRepository.findByCnmDEnteAndCnmDAmbitoAndNotEliminato(cnmDEnte, cnmDAmbito);
		if (leggeList == null || leggeList.isEmpty())
			return prontuarioList;

		List<CnmDArticolo> articoloList = cnmDArticoloRepository.findByCnmREnteNormaInAndNotEliminato(leggeList);
		List<CnmDComma> commaList = cnmDCommaRepository.findByCnmDArticoloInAndNotEliminato(articoloList);
		List<CnmDLettera> lettereList = cnmDLetteraRepository.findByCnmDCommaInAndNotEliminato(commaList);
		for (CnmDLettera l : lettereList) {
			ProntuarioVO p = prontuarioEntityMapper.mapEntityToVO(l);
			p.setEnteLegge(enteEntityMapper.mapEntityToVO(cnmDEnte));
			prontuarioList.add(p);
		}

		return prontuarioList;
	}

	@Override
	@Transactional
	public ProntuarioVO salvaLeggeProntuario(ProntuarioVO prontuario, UserDetails userDetails) {
		if (prontuario == null)
			throw new IllegalArgumentException("prontuario is null");
		EnteVO ente = prontuario.getEnteLegge();
		if (ente == null || ente.getId() == null)
			throw new IllegalArgumentException("ente is null");
		AmbitoVO ambito = prontuario.getAmbito();
		if (ambito == null || ambito.getId() == null)
			throw new IllegalArgumentException("ambito is null");
		NormaVO norma = prontuario.getNorma();
		if (norma == null || StringUtils.isEmpty(norma.getDenominazione()))
			throw new IllegalArgumentException("norma is null");
		ArticoloVO articolo = prontuario.getArticolo();
		if (articolo == null || StringUtils.isEmpty(articolo.getDenominazione()))
			throw new IllegalArgumentException("articolo is null");

		// conf parametri
		norma.setDenominazione(norma.getDenominazione().toUpperCase());
		articolo.setDenominazione(articolo.getDenominazione().toUpperCase());
		CommaVO comma = prontuario.getComma() != null ? prontuario.getComma() : new CommaVO();
		LetteraVO lettera = prontuario.getLettera() != null ? prontuario.getLettera() : new LetteraVO();

		if (StringUtils.isEmpty(comma.getDenominazione())) {
			comma.setDenominazione(Constants.NO_COMMA);
			lettera.setDenominazione(Constants.NO_LETTERA);
		} else {
			comma.setDenominazione(comma.getDenominazione().toUpperCase());
			if (StringUtils.isEmpty(lettera.getDenominazione()))
				lettera.setDenominazione(Constants.NO_LETTERA);
			else
				lettera.setDenominazione(lettera.getDenominazione().toUpperCase());
		}
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());

		CnmDEnte cnmDEnte = cnmDEnteRepository.findOne(ente.getId());
		CnmDAmbito cnmDAmbito = cnmDAmbitoRepository.findOne(ambito.getId().intValue());

		// INSERIMENTO
		if (lettera.getId() == null) {
//			CnmDNorma cnmDNormaDB = cnmDNormaRepository.findByRiferimentoNormativoAndNotEliminato(norma.getDenominazione());
			
			// 20211213 PP - CONAM-168 Controllo se esiste già la norma, ma sullo stesso ambito
			CnmDNorma cnmDNormaDB = cnmDNormaRepository.findByRiferimentoNormativoAndNotEliminatoAndNotAmbito(norma.getDenominazione(), cnmDAmbito);
			// caso non trovo norma inserisco
			if (cnmDNormaDB == null) {
				CnmDNorma cnmDNorma = normaEntityMapper.mapVOtoEntity(norma);
				cnmDNorma.setCnmTUser2(cnmTUser);
				cnmDNorma.setDataOraInsert(now);
				cnmDNorma.setCnmDAmbito(cnmDAmbito);
				cnmDNorma = cnmDNormaRepository.save(cnmDNorma);

				CnmREnteNorma cnmREnteNorma = insertCnmREnteNorma(cnmDEnte, cnmDNorma, now, cnmTUser, norma);
				CnmDArticolo cnmDArticolo = insertCnmDArticolo(cnmREnteNorma, now, cnmTUser, articolo);
				CnmDComma cnmDComma = insertCnmDComma(cnmDArticolo, now, cnmTUser, comma);
				CnmDLettera cnmDLettera = insertCnmDLettera(cnmDComma, now, cnmTUser, lettera);

				ProntuarioVO p = prontuarioEntityMapper.mapEntityToVO(cnmDLettera);
				p.setEnteLegge(enteEntityMapper.mapEntityToVO(cnmDEnte));
				return p;

			} else {
				CnmREnteNorma cnmREnteNorma = cnmREnteNormaRepository.findByCnmDNormaAndCnmDEnteAndNotEliminato(cnmDNormaDB, cnmDEnte);
				// la norma c'è ma non è legata all'ente inserisco
				if (cnmREnteNorma == null)
					cnmREnteNorma = insertCnmREnteNorma(cnmDEnte, cnmDNormaDB, now, cnmTUser, norma);

				CnmDArticolo cnmDArticoloDB = cnmDArticoloRepository.findByDescArticoloAndCnmREnteNormaAndNotEliminato(articolo.getDenominazione(), cnmREnteNorma);
				if (cnmDArticoloDB == null) {
					CnmDArticolo cnmDArticolo = insertCnmDArticolo(cnmREnteNorma, now, cnmTUser, articolo);
					CnmDComma cnmDComma = insertCnmDComma(cnmDArticolo, now, cnmTUser, comma);
					CnmDLettera cnmDLettera = insertCnmDLettera(cnmDComma, now, cnmTUser, lettera);

					ProntuarioVO p = prontuarioEntityMapper.mapEntityToVO(cnmDLettera);
					p.setEnteLegge(enteEntityMapper.mapEntityToVO(cnmDEnte));
					return p;
				}

				CnmDComma cnmDCommaDB = cnmDCommaRepository.findByCnmDArticoloAndDescCommaAndNotEliminato(cnmDArticoloDB, comma.getDenominazione());
				if (cnmDCommaDB == null) {
					CnmDComma cnmDComma = insertCnmDComma(cnmDArticoloDB, now, cnmTUser, comma);
					CnmDLettera cnmDLettera = insertCnmDLettera(cnmDComma, now, cnmTUser, lettera);

					ProntuarioVO p = prontuarioEntityMapper.mapEntityToVO(cnmDLettera);
					p.setEnteLegge(enteEntityMapper.mapEntityToVO(cnmDEnte));
					return p;
				}

				CnmDLettera cnmDLetteraDB = cnmDLetteraRepository.findByCnmDCommaAndLetteraAndNotEliminato(cnmDCommaDB, lettera.getDenominazione());
				if (cnmDLetteraDB == null) {
					CnmDLettera cnmDLettera = insertCnmDLettera(cnmDCommaDB, now, cnmTUser, lettera);

					ProntuarioVO p = prontuarioEntityMapper.mapEntityToVO(cnmDLettera);
					p.setEnteLegge(enteEntityMapper.mapEntityToVO(cnmDEnte));
					return p;
				} else
					throw new BusinessException(ErrorCode.INSERIMENTO_LEGGE);
			}
		}
		// modifica
		else {
			if (lettera.getId() == null)
				throw new IllegalArgumentException("id lettera =null");
			if (comma.getId() == null)
				throw new IllegalArgumentException("id comma =null");
			if (articolo.getId() == null)
				throw new IllegalArgumentException("id articolo =null");
			if (norma.getId() == null)
				throw new IllegalArgumentException("id norma =null");
			CnmDNorma cnmDNormaDB = cnmDNormaRepository.findOne(norma.getId().intValue());
			CnmREnteNorma cnmREnteNorma = cnmREnteNormaRepository.findByCnmDNormaAndCnmDEnteAndNotEliminato(cnmDNormaDB, cnmDEnte);
			cnmREnteNorma.setFineValidita(utilsDate.asDate(norma.getDataFineValidita()));

			//20201110 PP - Jira CONAM-102 - consente di prendere in input anche la data di inizio validita' norma
			cnmREnteNorma.setFineValidita(utilsDate.asDate(norma.getDataFineValidita()));
			if(norma.getDataInizioValidita()!=null) {
				cnmREnteNorma.setInizioValidita(utilsDate.asDate(norma.getDataInizioValidita()));
			}
			cnmREnteNorma.setCnmTUser1(cnmTUser);
			cnmREnteNorma.setDataOraUpdate(now);
			cnmREnteNormaRepository.save(cnmREnteNorma);

			CnmDArticolo cnmDArticolo = cnmDArticoloRepository.findOne(articolo.getId().intValue());
			cnmDArticolo.setFineValidita(utilsDate.asDate(articolo.getDataFineValidita()));
			//20201110 PP - Jira CONAM-102 - consente di prendere in input anche la data di inizio validita' norma
			if(articolo.getDataInizioValidita()!=null) {
				cnmDArticolo.setInizioValidita(utilsDate.asDate(articolo.getDataInizioValidita()));
			}
			cnmDArticolo.setCnmTUser1(cnmTUser);
			cnmDArticolo.setDataOraUpdate(now);
			cnmDArticoloRepository.save(cnmDArticolo);

			CnmDComma cnmDComma = cnmDCommaRepository.findOne(comma.getId().intValue());
			cnmDComma.setFineValidita(utilsDate.asDate(comma.getDataFineValidita()));
			//20201110 PP - Jira CONAM-102 - consente di prendere in input anche la data di inizio validita' norma
			if(comma.getDataInizioValidita()!=null) {
				cnmDComma.setInizioValidita(utilsDate.asDate(comma.getDataInizioValidita()));
			}
			cnmDComma.setCnmTUser1(cnmTUser);
			cnmDComma.setDataOraUpdate(now);
			cnmDCommaRepository.save(cnmDComma);

			CnmDLettera cnmDLettera = cnmDLetteraRepository.findOne(lettera.getId().intValue());
			if (cnmDLettera == null)
				throw new SecurityException("Lettera non valida");
			cnmDLettera.setCnmTUser1(cnmTUser);
			cnmDLettera.setDataOraUpdate(now);
			cnmDLettera.setDescIllecito(lettera.getDescrizioneIllecito());
			cnmDLettera.setImportoRidotto(lettera.getImportoMisuraRidotta().setScale(2, RoundingMode.HALF_UP));
			cnmDLettera.setSanzioneMassima(lettera.getSanzioneMassima());
			cnmDLettera.setSanzioneMinima(lettera.getSanzioneMinima());
			cnmDLettera.setScadenzaImporti(utilsDate.asDate(lettera.getScadenzaImporti()));
			cnmDLettera.setFineValidita(utilsDate.asDate(lettera.getDataFineValidita()));
			//20201110 PP - Jira CONAM-102 - consente di prendere in input anche la data di inizio validita' norma
			if(lettera.getDataInizioValidita()!=null) {
				cnmDLettera.setInizioValidita(utilsDate.asDate(lettera.getDataInizioValidita()));
			}
			cnmDLettera = cnmDLetteraRepository.save(cnmDLettera);

			ProntuarioVO p = prontuarioEntityMapper.mapEntityToVO(cnmDLettera);
			p.setEnteLegge(enteEntityMapper.mapEntityToVO(cnmDEnte));
			return p;
		}
	}

	@Override
	public void eliminaLeggeProntuario(Long idLettera, UserDetails userDetails) {
		if (idLettera == null)
			throw new IllegalArgumentException("idLettera is null");

		CnmDLettera cnmDLettera = cnmDLetteraRepository.findOne(idLettera.intValue());
		if (cnmDLettera == null)
			throw new IllegalArgumentException("lettera inesistente");
		Long numVerbIllecito = cnmRVerbaleIllecitoRepository.countByCnmDLettera(cnmDLettera);
		if (numVerbIllecito != null && numVerbIllecito > 0)
			throw new SecurityException("Il riferimento normativo è associato ad un verbale");

		CnmDComma cnmDComma = cnmDLettera.getCnmDComma();
		CnmDArticolo cnmDArticolo = cnmDComma.getCnmDArticolo();
		CnmREnteNorma cnmREnteNorma = cnmDArticolo.getCnmREnteNorma();
		CnmDNorma cnmDNorma = cnmREnteNorma.getCnmDNorma();

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());

		// lettera
		cnmDLettera.setEliminato(Boolean.TRUE);
		cnmDLettera.setDataOraUpdate(now);
		cnmDLettera.setCnmTUser1(cnmTUser);

		List<CnmDLettera> cnmDLetteraList = cnmDLetteraRepository.findByCnmDCommaAndFineValiditaAndNotEliminato(cnmDComma, now);
		if (cnmDLetteraList.size() > 1) {
			cnmDLetteraRepository.save(cnmDLettera);
			return;
		}

		// comma
		cnmDComma.setEliminato(Boolean.TRUE);
		cnmDComma.setDataOraUpdate(now);
		cnmDComma.setCnmTUser1(cnmTUser);

		List<CnmDComma> cnmDCommaList = cnmDCommaRepository.findByCnmDArticoloAndFineValiditaAndNotEliminato(cnmDArticolo, now);
		if (cnmDCommaList.size() > 1) {
			cnmDLetteraRepository.save(cnmDLettera);
			cnmDCommaRepository.save(cnmDComma);
			return;
		}

		// articolo
		cnmDArticolo.setEliminato(Boolean.TRUE);
		cnmDArticolo.setDataOraUpdate(now);
		cnmDArticolo.setCnmTUser1(cnmTUser);
		List<CnmDArticolo> cnmDArticoloList = cnmDArticoloRepository.findByCnmREnteNormaAndFineValiditaAndNotEliminato(cnmREnteNorma, now);
		if (cnmDArticoloList.size() > 1) {
			cnmDLetteraRepository.save(cnmDLettera);
			cnmDCommaRepository.save(cnmDComma);
			cnmDArticoloRepository.save(cnmDArticolo);
			return;
		}

		// ente norma e norma
		cnmREnteNorma.setEliminato(Boolean.TRUE);
		cnmREnteNorma.setDataOraUpdate(now);
		cnmREnteNorma.setCnmTUser1(cnmTUser);
		cnmDNorma.setEliminato(Boolean.TRUE);
		cnmDNorma.setDataOraUpdate(now);
		cnmDNorma.setCnmTUser1(cnmTUser);

		cnmDLetteraRepository.save(cnmDLettera);
		cnmDCommaRepository.save(cnmDComma);
		cnmDArticoloRepository.save(cnmDArticolo);
		cnmREnteNormaRepository.save(cnmREnteNorma);
		cnmDNormaRepository.save(cnmDNorma);

	}

	@Override
	public List<AmbitoVO> getAmbiti() {
		return ambitoEntityMapper.mapListEntityToListVO(cnmDAmbitoRepository.findAllNotEliminato());
	}

	@Override
	public List<AmbitoVO> getAmbitiEliminabili() {

		List<CnmDAmbito> cnmDAmbitoListDB = cnmDAmbitoRepository.findAllNotEliminato();
		if (cnmDAmbitoListDB == null || cnmDAmbitoListDB.isEmpty())
			return new ArrayList<>();

		Map<Integer, CnmDAmbito> map = new HashMap<Integer, CnmDAmbito>();
		for (CnmDAmbito ambito : cnmDAmbitoListDB) {
			map.put(ambito.getIdAmbito(), ambito);
		}

		List<CnmDAmbito> ambiti = new ArrayList<CnmDAmbito>();
		List<CnmDAmbito> ambitiConNorma = cnmDAmbitoRepository.findAllConNorma();
		if (ambitiConNorma != null && !ambitiConNorma.isEmpty()) {
			for (CnmDAmbito ambito : ambitiConNorma) {
				if (map.containsKey(ambito.getIdAmbito()))
					map.remove(ambito.getIdAmbito());
			}
		}

		if (map.isEmpty())
			return new ArrayList<>();

		for (Map.Entry<Integer, CnmDAmbito> entry : map.entrySet()) {
			ambiti.add(entry.getValue());
		}

		List<AmbitoVO> ambitoVo = ambitoEntityMapper.mapListEntityToListVO(ambiti);

		return ambitoVo;
	}

	@Override
	public void eliminaAmbito(Integer idAmbito, UserDetails userDetails) {
		if (idAmbito == null)
			throw new IllegalArgumentException("idAmbito is null");
		CnmDAmbito cnmDAmbito = cnmDAmbitoRepository.findOne(idAmbito);
		if (cnmDAmbito == null)
			throw new IllegalArgumentException("cnmDAmbito inesistente");

		List<CnmDAmbito> ambito = cnmDAmbitoRepository.findByIdAmbito(idAmbito);

		List<AmbitoVO> ambitiEliminabili = getAmbitiEliminabili();
		if (ambitiEliminabili == null || ambitiEliminabili.isEmpty()) {
			throw new SecurityException("cnmDAmbito inesistente");
		}

		AmbitoVO ambitoVO = Iterables.tryFind(ambitiEliminabili, new Predicate<AmbitoVO>() {
			public boolean apply(AmbitoVO ambito) {
				return idAmbito.longValue() == ambito.getId();
			}
		}).orNull();
		if (ambitoVO == null)
			throw new SecurityException("cnmDAmbito non eliminabile");

		if (ambito == null || ambito.isEmpty()) {
			cnmDAmbitoRepository.delete(cnmDAmbito);
		} else {
			cnmDAmbito.setEliminato(true);
			cnmDAmbitoRepository.save(cnmDAmbito);
		}
	}

	@Override
	public void salvaAmbito(AmbitoVO ambito, UserDetails userDetails) {
		if (StringUtils.isEmpty(ambito.getDenominazione()))
			throw new IllegalArgumentException("ambito is empty");

		String descAmbito = ambito.getDenominazione().toUpperCase();
		List<CnmDAmbito> cnmDAmbitoList = cnmDAmbitoRepository.findByDescAmbitoAndNotEliminato(descAmbito);
		if (cnmDAmbitoList != null && !cnmDAmbitoList.isEmpty())
			throw new BusinessException(ErrorCode.INSERIMENTO_AMBITO);

		CnmDAmbito cnmDAmbito = ambitoEntityMapper.mapVOtoEntity(ambito);
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		cnmDAmbito.setCnmTUser2(cnmTUser);
		cnmDAmbito.setDataOraInsert(now);
		//CONAM-79
		if(cnmDAmbito.getEliminato()==null) {
			cnmDAmbito.setEliminato(false);
		}	
		
		cnmDAmbitoRepository.save(cnmDAmbito);		
	}

	private CnmREnteNorma insertCnmREnteNorma(CnmDEnte cnmDEnte, CnmDNorma cnmDNorma, Timestamp now, CnmTUser cnmTUser, NormaVO norma) {
		CnmREnteNorma cnmREnteNorma = new CnmREnteNorma();
		cnmREnteNorma.setCnmDEnte(cnmDEnte);
		cnmREnteNorma.setCnmDNorma(cnmDNorma);
		cnmREnteNorma.setCnmTUser2(cnmTUser);
		cnmREnteNorma.setDataOraInsert(now);
		if(norma.getDataInizioValidita()!=null) {
			cnmREnteNorma.setInizioValidita(utilsDate.asDate(norma.getDataInizioValidita()));
		}
//		cnmREnteNorma.setInizioValidita(now);
		cnmREnteNorma.setFineValidita(utilsDate.asDate(norma.getDataFineValidita()));
		return cnmREnteNormaRepository.save(cnmREnteNorma);
	}

	private CnmDArticolo insertCnmDArticolo(CnmREnteNorma cnmREnteNorma, Timestamp now, CnmTUser cnmTUser, ArticoloVO articolo) {
		CnmDArticolo cnmDArticolo = articoloEntityMapper.mapVOtoEntity(articolo);
		cnmDArticolo.setCnmTUser2(cnmTUser);
		cnmDArticolo.setDataOraInsert(now);
		cnmDArticolo.setCnmREnteNorma(cnmREnteNorma);
		if(articolo.getDataInizioValidita()!=null) {
			cnmDArticolo.setInizioValidita(utilsDate.asDate(articolo.getDataInizioValidita()));
		}
		return cnmDArticoloRepository.save(cnmDArticolo);
	}

	private CnmDComma insertCnmDComma(CnmDArticolo cnmDArticolo, Timestamp now, CnmTUser cnmTUser, CommaVO comma) {
		CnmDComma cnmDComma = commaEntityMapper.mapVOtoEntity(comma);
		cnmDComma.setCnmTUser2(cnmTUser);
		cnmDComma.setDataOraInsert(now);
		cnmDComma.setCnmDArticolo(cnmDArticolo);
		if(comma.getDataInizioValidita()!=null) {
			cnmDComma.setInizioValidita(utilsDate.asDate(comma.getDataInizioValidita()));
		}
		return cnmDCommaRepository.save(cnmDComma);
	}

	private CnmDLettera insertCnmDLettera(CnmDComma cnmDComma, Timestamp now, CnmTUser cnmTUser, LetteraVO lettera) {
		CnmDLettera cnmDLettera = letteraEntityMapper.mapVOtoEntity(lettera);
		cnmDLettera.setCnmTUser2(cnmTUser);
		cnmDLettera.setDataOraInsert(now);
		cnmDLettera.setCnmDComma(cnmDComma);
		if(lettera.getDataInizioValidita()!=null) {
			cnmDLettera.setInizioValidita(utilsDate.asDate(lettera.getDataInizioValidita()));
		}
		return cnmDLetteraRepository.save(cnmDLettera);
	}

}
