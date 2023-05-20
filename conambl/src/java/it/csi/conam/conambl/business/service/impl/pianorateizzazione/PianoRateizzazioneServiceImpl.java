/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.pianorateizzazione;

import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.notifica.NotificaService;
import it.csi.conam.conambl.business.service.ordinanza.StatoPagamentoOrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.UtilsOrdinanza;
import it.csi.conam.conambl.business.service.pianorateizzazione.PianoRateizzazioneService;
import it.csi.conam.conambl.business.service.pianorateizzazione.UtilsPianoRateizzazioneService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.*;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.response.ImportoResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.UtilsRata;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import it.csi.conam.conambl.vo.pianorateizzazione.PianoRateizzazioneVO;
import it.csi.conam.conambl.vo.pianorateizzazione.RataVO;
import it.csi.conam.conambl.vo.pianorateizzazione.StatoPianoVO;
import it.csi.conam.conambl.vo.pianorateizzazione.StatoRataVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 03 dic 2018
 */
@Service
public class PianoRateizzazioneServiceImpl implements PianoRateizzazioneService {

	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmDStatoPianoRateRepository cnmDStatoPianoRateRepository;
	@Autowired
	private CnmDStatoRataRepository cnmDStatoRataRepository;
	@Autowired
	private StatoPianoRateEntityMapper statoPianoRateEntityMapper;
	@Autowired
	private SoggettoEntityMapper soggettoEntityMapper;
	@Autowired
	private RuoloSoggettoEntityMapper ruoloSoggettoEntityMapper;
	@Autowired
	private StatoRataEntityMapper statoRataEntityMapper;
	@Autowired
	private PianoRateizzazioneEntityMapper pianoRateizzazioneEntityMapper;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CnmTPianoRateRepository cnmTPianoRateRepository;
	@Autowired
	private RataEntityMapper rataEntityMapper;
	@Autowired
	private CnmTRataRepository cnmTRataRepository;
	@Autowired
	private UtilsPianoRateizzazioneService utilsPianoRateizzazione;
	@Autowired
	private StatoPagamentoOrdinanzaService statoPagamentoOrdinanzaService;
	@Autowired
	private CnmDStatoOrdVerbSogRepository cnmDStatoOrdVerbSogRepository;
	@Autowired
	private NotificaService motificaService;

	@Autowired
	private CommonSoggettoService commonSoggettoService;
	@Autowired
	private UtilsOrdinanza utilsOrdinanza;

	@Override
	public PianoRateizzazioneVO precompilaPiano(List<Integer> ordinanzaVerbaleSoggettoList, UserDetails userDetails, boolean pregresso) {
		if (ordinanzaVerbaleSoggettoList == null || ordinanzaVerbaleSoggettoList.isEmpty())
			throw new IllegalArgumentException("id ordinanzaVerbaleSoggettoList non valorizzata");

		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = (List<CnmROrdinanzaVerbSog>) cnmROrdinanzaVerbSogRepository.findAll(ordinanzaVerbaleSoggettoList);
		if (cnmROrdinanzaVerbSogList == null || cnmROrdinanzaVerbSogList.isEmpty())
			throw new IllegalArgumentException("cnmROrdinanzaVerbSoggetti==null");

		// Se esiste almeno una rata per i soggetti
		List<CnmRSoggRata> cnmRSoggRataList = cnmRSoggRataRepository.findByCnmROrdinanzaVerbSogIn(cnmROrdinanzaVerbSogList);
		if (cnmRSoggRataList != null && !cnmRSoggRataList.isEmpty())
			throw new SecurityException("Piano esistente per uno dei soggetti ricercati");

		PianoRateizzazioneVO pianoRateizzazioneVO = new PianoRateizzazioneVO();
		pianoRateizzazioneVO.setImportoSanzione(cnmROrdinanzaVerbSogList.get(0).getCnmTOrdinanza().getImportoOrdinanza());

		if(!pregresso) {
			StatoPianoVO stato = statoPianoRateEntityMapper.mapEntityToVO(cnmDStatoPianoRateRepository.findOne(Constants.ID_STATO_PIANO_IN_DEFINIZIONE));
			pianoRateizzazioneVO.setStato(stato);
		}else {
			StatoPianoVO stato = statoPianoRateEntityMapper.mapEntityToVO(cnmDStatoPianoRateRepository.findOne(Constants.ID_STATO_PIANO_CONSOLIDATO));
			pianoRateizzazioneVO.setStato(stato);
			
		}
		List<SoggettoVO> sog = new ArrayList<>();
		for (CnmROrdinanzaVerbSog c : cnmROrdinanzaVerbSogList) {
			
			CnmTSoggetto cnmTSoggettoToMap = c.getCnmRVerbaleSoggetto().getCnmTSoggetto();
			SoggettoVO soggetto = soggettoEntityMapper.mapEntityToVO(cnmTSoggettoToMap);
			
			// 20201217_LC - JIRA 118
			CnmTVerbale cnmTVerbale = c.getCnmRVerbaleSoggetto().getCnmTVerbale();
			if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
				soggetto = commonSoggettoService.attachResidenzaPregressi(soggetto, cnmTSoggettoToMap, cnmTVerbale.getIdVerbale());
			}	

			soggetto.setIdSoggettoOrdinanza(c.getIdOrdinanzaVerbSog());
			soggetto.setRuolo(ruoloSoggettoEntityMapper.mapEntityToVO(c.getCnmRVerbaleSoggetto().getCnmDRuoloSoggetto()));
			sog.add(soggetto);
		}
		pianoRateizzazioneVO.setSoggetti(sog);

		return pianoRateizzazioneVO;

	}

	@Override
	@Transactional
	public Integer creaPiano(Integer id, UserDetails userDetails) {
		CnmTPianoRate cnmTPianoRate = utilsPianoRateizzazione.validateAndGetCnmTPianoRate(id);
		if (cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() != Constants.ID_STATO_PIANO_IN_DEFINIZIONE || 
			cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() != Constants.ID_STATO_PIANO_NON_NOTIFICATO ||
			cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() != Constants.ID_STATO_PIANO_NOTIFICATO) 
		{
			Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

			CnmDStatoPianoRate cnmDStatoPianoRate = cnmDStatoPianoRateRepository.findOne(Constants.ID_STATO_PIANO_CONSOLIDATO);
			cnmTPianoRate.setCnmDStatoPianoRate(cnmDStatoPianoRate);
			CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
			cnmTPianoRate.setCnmTUser1(cnmTUser);
			cnmTPianoRate.setDataOraUpdate(now);

			cnmTPianoRateRepository.save(cnmTPianoRate);

			return id;
			
		}else {
			throw new SecurityException("piano in stato non valido ");
		}		
	}

	@Override
	public PianoRateizzazioneVO dettaglioPianoById(Integer idPiano, Boolean flagModifica, UserDetails userDetails) {
		CnmTPianoRate cnmTPianoRate = utilsPianoRateizzazione.validateAndGetCnmTPianoRate(idPiano);
		if (flagModifica == null)
			throw new IllegalArgumentException("flagModifica non valorizzato");

		if (flagModifica && cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() != Constants.ID_STATO_PIANO_IN_DEFINIZIONE 
							&& cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() != Constants.ID_STATO_PIANO_NON_NOTIFICATO )
			throw new SecurityException("non si può modificare il piano in questo stato");
				
		PianoRateizzazioneVO pianoVO = pianoRateizzazioneEntityMapper.mapEntityToVO(cnmTPianoRate);
		
		// 20201117 PP - per i pregressi non consento la compilazione della lettera e la creazione dei bollettini
		if(cnmTPianoRate.isFlagDocumentoPregresso()) {
			pianoVO.setIsLetteraCreata(true);
			pianoVO.setIsBollettiniEnable(false);
		}
		
		return pianoVO;
	}

	@Override
	@Transactional
	public Integer salvaPiano(PianoRateizzazioneVO piano, UserDetails userDetails) {
		if (piano == null)
			throw new IllegalArgumentException("piano = null");
		if (piano.getSoggetti() == null)
			throw new IllegalArgumentException("soggetti = null");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

		Integer idPianoRata = piano.getId();
		CnmTPianoRate cnmTPianoRate;
		List<CnmRSoggRata> cnmRSoggRataList;
		List<CnmTRata> cnmTRataList;

		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList;
		if (idPianoRata == null) {
			// controllo soggetti
			List<Integer> idOrdinanzaVerbaleSoggettoList = new ArrayList<>();
			for (SoggettoVO sog : piano.getSoggetti()) {
				idOrdinanzaVerbaleSoggettoList.add(sog.getIdSoggettoOrdinanza());
			}
			cnmROrdinanzaVerbSogList = (List<CnmROrdinanzaVerbSog>) cnmROrdinanzaVerbSogRepository.findAll(idOrdinanzaVerbaleSoggettoList);
			if (cnmROrdinanzaVerbSogList == null || cnmROrdinanzaVerbSogList.isEmpty())
				throw new IllegalArgumentException("cnmROrdinanzaVerbSoggetti==null");

			// Se esiste almeno una rata per i soggetti
			cnmRSoggRataList = cnmRSoggRataRepository.findByCnmROrdinanzaVerbSogIn(cnmROrdinanzaVerbSogList);
			if (cnmRSoggRataList != null && !cnmRSoggRataList.isEmpty())
				throw new SecurityException("Piano esistente per uno dei soggetti ricercati");


			cnmTPianoRate = pianoRateizzazioneEntityMapper.mapVOtoEntity(piano);
			cnmTPianoRate.setCnmDStatoPianoRate(cnmDStatoPianoRateRepository.findOne(Constants.ID_STATO_PIANO_IN_DEFINIZIONE));
			cnmTPianoRate.setCnmTUser2(cnmTUser);
			cnmTPianoRate.setDataOraInsert(now);
			cnmTPianoRate = cnmTPianoRateRepository.save(cnmTPianoRate);

			// parte comune a modifica e inserimento
			cnmTRataList = new ArrayList<>();
			for (RataVO rata : piano.getRate()) {
				CnmTRata cnmTRate = rataEntityMapper.mapVOtoEntity(rata);
				cnmTRate.setCnmTUser2(cnmTUser);
				cnmTRate.setDataOraInsert(now);
				cnmTRate.setCnmTPianoRate(cnmTPianoRate);
				cnmTRataList.add(cnmTRate);
			}
			cnmTRataList = cnmTRataRepository.save(cnmTRataList);

		} else {
			cnmTPianoRate = cnmTPianoRateRepository.findOne(idPianoRata);
			if (cnmTPianoRate == null)
				throw new IllegalArgumentException("id non valido ");
			if (cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() != Constants.ID_STATO_PIANO_IN_DEFINIZIONE)
				throw new SecurityException("piano in stato non valido ");

			pianoRateizzazioneEntityMapper.mapVOtoEntityUpdate(piano, cnmTPianoRate);
			cnmTPianoRate.setCnmTUser1(cnmTUser);
			cnmTPianoRate.setDataOraUpdate(now);
			cnmTPianoRate = cnmTPianoRateRepository.save(cnmTPianoRate);

			// cerco le rate associate al piano
			cnmTRataList = cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate);

			// cerco i soggetti
			cnmROrdinanzaVerbSogList = cnmRSoggRataRepository.findDistinctCnmROrdinanzaVerbSogByCnmTRataIn(cnmTRataList);

			// elimino i soggetti dalla r rata
			cnmRSoggRataList = cnmRSoggRataRepository.findByCnmTRataIn(cnmTRataList);
			cnmRSoggRataRepository.delete(cnmRSoggRataList);

			List<RataVO> rateRicalcolate = piano.getRate();
			int differenza = rateRicalcolate.size() - cnmTRataList.size();

			List<CnmTRata> cnmTRataListDelete = new ArrayList<>();
			if (differenza > 0) {
				for (int i = 0; i < differenza; i++) {
					cnmTRataList.add(new CnmTRata());
				}
			} else if (differenza < 0) {
				int index = rateRicalcolate.size() - 1;
				for (int i = index; i > index + differenza; i--) {
					cnmTRataListDelete.add(cnmTRataList.get(i));
					cnmTRataList.remove(cnmTRataList.get(i));
				}
			}

			// elimino le rate obsolete
			cnmTRataRepository.delete(cnmTRataListDelete);
			cnmTRataRepository.flush();

			List<CnmTRata> cnmTRataUpdate = new ArrayList<>();
			for (int i = 0; i < rateRicalcolate.size(); i++) {
				CnmTRata cnmTRate = rataEntityMapper.mapVOtoEntityUpdate(rateRicalcolate.get(i), cnmTRataList.get(i));
				if (cnmTRate.getIdRata() == null) {
					cnmTRate.setCnmTUser2(cnmTUser);
					cnmTRate.setDataOraInsert(now);
				} else {
					cnmTRate.setCnmTUser1(cnmTUser);
					cnmTRate.setDataOraUpdate(now);
				}
				cnmTRate.setCnmTPianoRate(cnmTPianoRate);
				cnmTRataUpdate.add(cnmTRate);
			}

			cnmTRataList = cnmTRataRepository.save(cnmTRataUpdate);
			cnmRSoggRataRepository.flush();

		}
		// parte comune a modifica e inserimento
		cnmRSoggRataList = new ArrayList<>();
		for (CnmROrdinanzaVerbSog sog : cnmROrdinanzaVerbSogList) {
			for (CnmTRata cnmTRata : cnmTRataList) {
				CnmRSoggRata cnmRSoggRata = new CnmRSoggRata();

				CnmRSoggRataPK cnmRSoggRataPK = new CnmRSoggRataPK();
				cnmRSoggRataPK.setIdOrdinanzaVerbSog(sog.getIdOrdinanzaVerbSog());
				cnmRSoggRataPK.setIdRata(cnmTRata.getIdRata());

				cnmRSoggRata.setId(cnmRSoggRataPK);
				cnmRSoggRata.setCnmDStatoRata(cnmDStatoRataRepository.findOne(Constants.ID_STATO_RATA_NON_PAGATO));
				cnmRSoggRata.setCnmTUser1(cnmTUser);
				cnmRSoggRata.setDataOraInsert(now);
				cnmRSoggRataList.add(cnmRSoggRata);
			}
		}
		cnmRSoggRataRepository.save(cnmRSoggRataList);
				
		//Save della notifca se presente
		if(piano.getImportoSpeseNotifica()!=null) {
			NotificaVO notifica = new NotificaVO();
			notifica.setIdPiano(cnmTPianoRate.getIdPianoRate());
			notifica.setImportoSpeseNotifica(piano.getImportoSpeseNotifica());
			motificaService.salvaNotifica(notifica, userDetails);
		}

		return cnmTPianoRate.getIdPianoRate();

	}

	@Override
	public PianoRateizzazioneVO calcolaRate(PianoRateizzazioneVO piano) {
		if (piano == null)
			throw new IllegalArgumentException("piano = null");
		if (piano.getNumeroRate() == null)
			throw new IllegalArgumentException("il numero delle rate è null");
		if (!(piano.getNumeroRate().compareTo(Constants.NUM_RATE_MINIMO) >= 0 && piano.getNumeroRate().compareTo(Constants.NUM_RATE_MASSIMO) <= 0))
			throw new IllegalArgumentException("il numero delle rate non è compreso tra " + Constants.NUM_RATE_MINIMO.toString() + " e " + Constants.NUM_RATE_MASSIMO.toString());
		if (piano.getImportoSanzione() == null)
			throw new IllegalArgumentException("importo a sanzione  è null");
		if (piano.getImportoSpeseProcessuali() == null)
			throw new IllegalArgumentException("importo spese processuali  è null");
		if (piano.getImportoSpeseNotifica() == null)
			throw new IllegalArgumentException("importo spese notifica  è null");

		List<RataVO> rate = new ArrayList<>();

		StatoRataVO stato = statoRataEntityMapper.mapEntityToVO(cnmDStatoRataRepository.findOne(Constants.ID_STATO_RATA_NON_PAGATO));
		BigDecimal totaleRateCalcolate = BigDecimal.ZERO;
		RataVO primaRata = null;
		
		BigDecimal importoMaggiorazione = (piano.getImportoMaggiorazione()==null)?BigDecimal.ZERO:piano.getImportoMaggiorazione();

		LocalDate dataScadenza = piano.getDataScadenzaPrimaRata();
		for (int i = 1; i <= piano.getNumeroRate().intValue(); i++) {
			RataVO rata = new RataVO();
			BigDecimal importoRata;
			if (i == 1) {
				primaRata = rata;
				importoRata = piano.getImportoSanzione().add(piano.getImportoSpeseProcessuali().add(importoMaggiorazione)).divide(piano.getNumeroRate(), MathContext.DECIMAL128).add(piano.getImportoSpeseNotifica());
			} else
				importoRata = piano.getImportoSanzione().add(piano.getImportoSpeseProcessuali().add(importoMaggiorazione)).divide(piano.getNumeroRate(), MathContext.DECIMAL128);

			if (importoRata.compareTo(Constants.IMPORTO_MINIMO_RATA) < 0)
				throw new BusinessException(ErrorCode.CALCOLA_RATE_MINORE_DI_IMPORTO_MINIMO);

			rata.setNumeroRata(new BigDecimal(i));
			BigDecimal importo = importoRata.setScale(2, RoundingMode.UP);
			totaleRateCalcolate = totaleRateCalcolate.add(importo);
			rata.setImportoRata(importo);
			rata.setStato(stato);
			rata.setDataScadenza(dataScadenza);
			if (dataScadenza != null)
				dataScadenza = dataScadenza.plusMonths(1);
			rate.add(rata);
		}

		RataVO rata1 = UtilsRata.findRata(rate, UtilsRata.numRataPredicate(1));
		RataVO rataN = UtilsRata.findRata(rate, UtilsRata.numRataPredicate(piano.getNumeroRate().intValue()));
		rata1.setIsEditEnable(Boolean.TRUE);
		rataN.setIsEditEnable(Boolean.TRUE);

		BigDecimal saldo = piano.getImportoSanzione().add(piano.getImportoSpeseNotifica()).add(piano.getImportoSpeseProcessuali()).add(importoMaggiorazione);
		BigDecimal totaleAggiuntivo = saldo.subtract(totaleRateCalcolate);
		primaRata.setImportoRata(primaRata.getImportoRata().add(totaleAggiuntivo).setScale(2, RoundingMode.UP));
		piano.setRate(rate);
		piano.setSaldo(saldo);
		return piano;
	}

	@Override
	public PianoRateizzazioneVO ricalcolaRate(PianoRateizzazioneVO piano) {
		if (piano == null)
			throw new IllegalArgumentException("piano = null");
		List<RataVO> ratePostModifica = piano.getRate();

		List<RataVO> ratePreModifica = calcolaRate(piano).getRate();

		Collection<RataVO> ratePreModificaModificabili = UtilsRata.filtraRateModificabili(ratePreModifica, piano.getNumeroRate().intValue());
		Collection<RataVO> ratePostModificaModificabili = UtilsRata.filtraRateModificabili(ratePostModifica, piano.getNumeroRate().intValue());

		RataVO rata1Pre = UtilsRata.findRata(ratePreModificaModificabili, UtilsRata.numRataPredicate(1));
		RataVO rata1Post = UtilsRata.findRata(ratePostModificaModificabili, UtilsRata.numRataPredicate(1));
		RataVO rataNPost = UtilsRata.findRata(ratePostModificaModificabili, UtilsRata.numRataPredicate(piano.getNumeroRate().intValue()));
		RataVO rataNPre = UtilsRata.findRata(ratePreModificaModificabili, UtilsRata.numRataPredicate(piano.getNumeroRate().intValue()));

		if (piano.getId() != null) {
			CnmTPianoRate cnmTPianoRate = cnmTPianoRateRepository.findOne(piano.getId());
			if (cnmTPianoRate == null)
				throw new IllegalArgumentException("id non valido ");
			if (cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() != Constants.ID_STATO_PIANO_IN_DEFINIZIONE)
				throw new SecurityException("piano in stato non valido ");
			List<CnmTRata> listaRateDB = cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate);

			rata1Pre = rataEntityMapper.mapEntityToVO(UtilsRata.findCnmTRata(listaRateDB, UtilsRata.numCnmTRataPredicate(1)));
			rataNPre = rataEntityMapper.mapEntityToVO(UtilsRata.findCnmTRata(listaRateDB, UtilsRata.numCnmTRataPredicate(cnmTPianoRate.getNumeroRate().intValue())));
		}

		if (rata1Post.getImportoRata().compareTo(piano.getSaldo()) >= 0)
			throw new BusinessException(ErrorCode.PRIMA_RATA_UGUALE_A_SALDO);
		if (rataNPost.getImportoRata().compareTo(piano.getSaldo()) >= 0)
			throw new BusinessException(ErrorCode.ULTIMA_RATA_UGUALE_A_SALDO);
		if (rata1Post.getImportoRata().compareTo(Constants.IMPORTO_MINIMO_RATA) < 0)
			throw new BusinessException(ErrorCode.PRIMA_RATA_MINORE_DI_IMPORTO_MINIMO);
		if (rataNPost.getImportoRata().compareTo(Constants.IMPORTO_MINIMO_RATA) < 0)
			throw new BusinessException(ErrorCode.ULTIMA_RATA_MINORE_DI_IMPORTO_MINIMO);

		BigDecimal totaleRateCalcolate = BigDecimal.ZERO;
		
		BigDecimal importoMaggiorazione = (piano.getImportoMaggiorazione()==null)?BigDecimal.ZERO:piano.getImportoMaggiorazione();

		if (rata1Pre.getImportoRata().compareTo(rata1Post.getImportoRata()) != 0) {
			BigDecimal importoPrimaRata = rata1Post.getImportoRata();
			for (RataVO rat : ratePostModifica) {
				BigDecimal importo;
				if (rat.getNumeroRata().intValue() != 1) {
					BigDecimal importoRata = piano.getImportoSanzione().add(piano.getImportoSpeseProcessuali()).add(piano.getImportoSpeseNotifica()).add(importoMaggiorazione).subtract(importoPrimaRata)
							.divide(piano.getNumeroRate().subtract(BigDecimal.ONE), MathContext.DECIMAL128);

					if (importoRata.compareTo(Constants.IMPORTO_MINIMO_RATA) < 0)
						throw new BusinessException(ErrorCode.CALCOLA_RATE_MINORE_DI_IMPORTO_MINIMO);

					importo = importoRata.setScale(2, RoundingMode.UP);

				} else
					importo = rat.getImportoRata().setScale(2, RoundingMode.UP);

				totaleRateCalcolate = totaleRateCalcolate.add(importo);
				rat.setImportoRata(importo);
			}
		} else if (rataNPre.getImportoRata().compareTo(rataNPost.getImportoRata()) != 0) {
			BigDecimal importoNRata = rataNPost.getImportoRata();
			for (RataVO rat : ratePostModifica) {
				BigDecimal importoRata;
				if (rat.getNumeroRata().intValue() == 1) {
					importoRata = piano.getImportoSanzione().add(piano.getImportoSpeseProcessuali()).add(importoMaggiorazione).subtract(importoNRata)
							.divide(piano.getNumeroRate().subtract(BigDecimal.ONE), MathContext.DECIMAL128).add(piano.getImportoSpeseNotifica());
				} else if (rat.getNumeroRata().intValue() != rataNPost.getNumeroRata().intValue()) {
					importoRata = piano.getImportoSanzione().add(piano.getImportoSpeseProcessuali()).add(importoMaggiorazione).subtract(importoNRata).divide(piano.getNumeroRate().subtract(BigDecimal.ONE),
							MathContext.DECIMAL128);
				} else {
					importoRata = rat.getImportoRata();
				}

				if (importoRata.compareTo(Constants.IMPORTO_MINIMO_RATA) < 0)
					throw new BusinessException(ErrorCode.CALCOLA_RATE_MINORE_DI_IMPORTO_MINIMO);

				BigDecimal importo = importoRata.setScale(2, RoundingMode.UP);
				totaleRateCalcolate = totaleRateCalcolate.add(importo);
				rat.setImportoRata(importo);
			}
		}

		// setto le approssimazioni
		BigDecimal saldo = piano.getImportoSanzione().add(piano.getImportoSpeseNotifica()).add(piano.getImportoSpeseProcessuali()).add(importoMaggiorazione); // 20210318_LC
		BigDecimal totaleAggiuntivo = saldo.subtract(totaleRateCalcolate);
		rata1Post.setImportoRata(rata1Post.getImportoRata().add(totaleAggiuntivo).setScale(2, RoundingMode.UP));

		rata1Post.setIsEditEnable(Boolean.FALSE);
		rataNPost.setIsEditEnable(Boolean.FALSE);

		piano.setRate(ratePostModifica);

		return piano;
	}

	@Override
	@Transactional
	public void deletePiano(Integer idPiano) {
		CnmTPianoRate cnmTPianoRate = utilsPianoRateizzazione.validateAndGetCnmTPianoRate(idPiano);
		if (cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate() != Constants.ID_STATO_PIANO_IN_DEFINIZIONE)
			throw new SecurityException("stato non congruente");

		// cerco le rate associate al piano
		List<CnmTRata> cnmTRataList = cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate);

		List<CnmRSoggRata> cnmRSoggRataList = cnmRSoggRataRepository.findByCnmTRataIn(cnmTRataList);
		if (cnmRSoggRataList == null || cnmRSoggRataList.isEmpty())
			throw new SecurityException("soggetti non collegati alle rate");

		List<CnmTRata> cnmTPianoRateList = cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate);
		if (cnmTPianoRateList == null || cnmTPianoRateList.isEmpty())
			throw new SecurityException("non ci sono rate collegate al piano");

		cnmRSoggRataRepository.delete(cnmRSoggRataList);
		cnmTRataRepository.delete(cnmTPianoRateList);
		cnmTPianoRateRepository.delete(cnmTPianoRate);

	}

	@Override
	@Transactional
	public RataVO riconcilaRata(RataVO rataVO, UserDetails userDetails) {
		if (rataVO == null)
			throw new IllegalArgumentException("rata non valorizzata");
		if (rataVO.getIdOrdinanzaVerbaleSoggetto() == null)
			throw new IllegalArgumentException("id ordinanza verbale soggetto non valorizzato");
		if (rataVO.getIdRata() == null)
			throw new IllegalArgumentException("id rata non valorizzato");

		CnmRSoggRataPK cnmRSoggRataPK = new CnmRSoggRataPK();
		cnmRSoggRataPK.setIdRata(rataVO.getIdRata());
		cnmRSoggRataPK.setIdOrdinanzaVerbSog(rataVO.getIdOrdinanzaVerbaleSoggetto());
		CnmRSoggRata cnmRSoggRata = cnmRSoggRataRepository.findOne(cnmRSoggRataPK);
		if (cnmRSoggRata == null)
			throw new SecurityException("rata non trovata");

		cnmRSoggRata.setImportoPagato(rataVO.getImportoPagato());
		cnmRSoggRata.setCnmDStatoRata(cnmDStatoRataRepository.findOne(Constants.ID_STATO_RATA_PAGATO_OFFLINE));
		cnmRSoggRata.setDataPagamento(utilsDate.asDate(rataVO.getDataPagamento()));

		cnmRSoggRata = cnmRSoggRataRepository.save(cnmRSoggRata);

		rataVO.setStato(statoRataEntityMapper.mapEntityToVO(cnmRSoggRata.getCnmDStatoRata()));
		rataVO.setIsRiconciliaEnable(Boolean.FALSE);

		// chiude lo stato ordinanza verbale soggetto
		CnmDStatoOrdVerbSog cnmDStatoOrdVerbSog = cnmDStatoOrdVerbSogRepository.findOne(Constants.ID_STATO_ORDINANZA_VERB_SOGG_PAGATO_OFFLINE);
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		statoPagamentoOrdinanzaService.verificaTerminePagamentoRate(cnmRSoggRata, cnmDStatoOrdVerbSog, cnmTUser);

		return rataVO;

	}

	@Override
	public ImportoResponse getImportiPianoByCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		ImportoResponse importoPianoResponse = new ImportoResponse();
		List<CnmRSoggRata> cnmRSoggRatas = cnmRSoggRataRepository.findByCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);
		BigDecimal importoDaPagare = BigDecimal.ZERO;
		BigDecimal importoPagato = BigDecimal.ZERO;

		if (!cnmRSoggRatas.isEmpty()) {
			CnmTPianoRate cnmTPianoRate = cnmRSoggRatas.get(0).getCnmTRata().getCnmTPianoRate();
			long statoPianoRate = cnmTPianoRate.getCnmDStatoPianoRate().getIdStatoPianoRate();
			// se lo stato è > di protocollato allora controllo le rate
			if (statoPianoRate == Constants.ID_STATO_PIANO_PROTOCOLLATO || statoPianoRate == Constants.ID_STATO_PIANO_NON_NOTIFICATO || statoPianoRate == Constants.ID_STATO_PIANO_NOTIFICATO)
				for (CnmRSoggRata cnmRSoggRata : cnmRSoggRatas) {
					importoPagato = importoPagato.add(cnmRSoggRata.getImportoPagato() != null ? cnmRSoggRata.getImportoPagato() : BigDecimal.ZERO);
					importoDaPagare = importoDaPagare.add(cnmRSoggRata.getCnmTRata().getImportoRata());
				}
		}

		importoPianoResponse.setImportoDaPagare(importoDaPagare);
		importoPianoResponse.setImportoPagato(importoPagato);

		return importoPianoResponse;
	}

}
