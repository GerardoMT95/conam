/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import com.google.common.collect.Iterables;
import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.notifica.NotificaService;
import it.csi.conam.conambl.business.service.pianorateizzazione.AllegatoPianoRateizzazioneService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.*;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.util.UtilsRata;
import it.csi.conam.conambl.util.UtilsSoggetto;
import it.csi.conam.conambl.util.UtilsTipoAllegato;
import it.csi.conam.conambl.vo.pianorateizzazione.PianoRateizzazioneVO;
import it.csi.conam.conambl.vo.pianorateizzazione.RataVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PianoRateizzazioneEntityMapperImpl implements PianoRateizzazioneEntityMapper {

	@Autowired
	private StatoPianoRateEntityMapper statoPianoRateEntityMapper;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;
	@Autowired
	private RataEntityMapper rataEntityMapper;
	@Autowired
	private CnmTRataRepository cnmTRataRepository;
	@Autowired
	private AllegatoPianoRateizzazioneService allegatoPianoRateizzazioneService;
	@Autowired
	private StatoRataEntityMapper statoRataEntityMapper;
	@Autowired
	private CnmRAllegatoPianoRateRepository cnmRAllegatoPianoRateRepository;
	@Autowired
	private ROrdinanzaVerbSogEntityMapper rOrdinanzaVerbSogEntityMapper;
	@Autowired
	private CnmDStatoRataRepository cnmDStatoRataRepository;
	@Autowired
	private NotificaService notificaService;

	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;
	@Autowired
	private CommonSoggettoService commonSoggettoService;

	private static Logger logger = Logger.getLogger(PianoRateizzazioneEntityMapperImpl.class);

	@Override
	public PianoRateizzazioneVO mapEntityToVO(CnmTPianoRate dto) {
		if (dto == null)
			return null;
		PianoRateizzazioneVO pianoRateizzazioneVO = new PianoRateizzazioneVO();
		pianoRateizzazioneVO.setDataCreazione(utilsDate.asLocalDateTime(dto.getDataOraInsert()));
		pianoRateizzazioneVO.setId(dto.getIdPianoRate());
		pianoRateizzazioneVO.setNumeroRate(dto.getNumeroRate());

		BigDecimal speseNotifica = dto.getImportoSpeseNotifica() != null ? dto.getImportoSpeseNotifica() : BigDecimal.ZERO;
		BigDecimal importoSpeseProcessuali = dto.getImportoSpeseProcessuali() != null ? dto.getImportoSpeseProcessuali() : BigDecimal.ZERO;
		BigDecimal importoSanzione = dto.getImportoSanzione() != null ? dto.getImportoSanzione() : BigDecimal.ZERO;
		BigDecimal importoMaggiorazione = dto.getImportoMaggiorazione() != null ? dto.getImportoMaggiorazione() : BigDecimal.ZERO;
		BigDecimal totale = speseNotifica.add(importoSpeseProcessuali).add(importoSanzione).add(importoMaggiorazione);
		pianoRateizzazioneVO.setImportoSpeseNotifica(dto.getImportoSpeseNotifica());
		pianoRateizzazioneVO.setImportoSpeseProcessuali(dto.getImportoSpeseProcessuali());
		pianoRateizzazioneVO.setImportoSanzione(importoSanzione);
		pianoRateizzazioneVO.setImportoMaggiorazione(importoMaggiorazione);
		pianoRateizzazioneVO.setSaldo(totale);

		List<SoggettoVO> soggettoList = new ArrayList<>();
		List<CnmTRata> cnmTRataList = cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(dto);
		if (cnmTRataList.isEmpty())
			throw new IllegalArgumentException("rate list is empty");

		// lista rate con stato. se sono in definizione le rate sono singole
		List<RataVO> rataList = new ArrayList<>();
		List<CnmRSoggRata> cnmRSoggRataList = cnmRSoggRataRepository.findCnmRSoggRataByCnmTRataInOrderByNumeroRata(cnmTRataList);

		
		
		// soggetti
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmRSoggRataRepository.findDistinctCnmROrdinanzaVerbSogByCnmTRataIn(cnmTRataList);
		
		
		for (CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog : cnmROrdinanzaVerbSogList) {
			SoggettoVO sog = rOrdinanzaVerbSogEntityMapper.mapEntityToVO(cnmROrdinanzaVerbSog);
			
			// 20201217_LC - JIRA 118
			CnmTVerbale cnmTVerbale = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTVerbale();
			CnmTSoggetto cnmTSoggetto = cnmTSoggettoRepository.findOne(sog.getId());
			if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
				sog = commonSoggettoService.attachResidenzaPregressi(sog, cnmTSoggetto, cnmTVerbale.getIdVerbale());
			}			
		
			
			soggettoList.add(sog);
		}
		pianoRateizzazioneVO.setSoggetti(soggettoList);

		long statoPiano = dto.getCnmDStatoPianoRate().getIdStatoPianoRate();
		boolean pianoInDefinizione = statoPiano == Constants.ID_STATO_PIANO_IN_DEFINIZIONE;
		LocalDate dataScadenzaPrimaRata;
		RataVO rata1;
		if (pianoInDefinizione) {
			CnmDStatoRata cnmDStatoRata = cnmDStatoRataRepository.findOne(Constants.ID_STATO_RATA_NON_PAGATO);
			for (CnmTRata cnmTRata : cnmTRataList) {
				RataVO rata = rataEntityMapper.mapEntityToVO(cnmTRata);
				rata.setStato(statoRataEntityMapper.mapEntityToVO(cnmDStatoRata));
				rata.setIsRiconciliaEnable(Boolean.FALSE);
				rata.setIsEditEnable(Boolean.FALSE);
				rata.setDataFineValidita(utilsDate.asLocalDate(cnmTRata.getDataFineValidita()));
				rataList.add(rata);
			}
			rata1 = UtilsRata.findRata(rataList, UtilsRata.numRataPredicate(1));
			RataVO rataN = UtilsRata.findRata(rataList, UtilsRata.numRataPredicate(dto.getNumeroRate().intValue()));
			dataScadenzaPrimaRata = rata1.getDataScadenza();
			rata1.setIsEditEnable(Boolean.TRUE);
			rataN.setIsEditEnable(Boolean.TRUE);
		} else {
			SoggettoVO sog = null;
			for (CnmRSoggRata cnmRSoggRata : cnmRSoggRataList) {
				RataVO rata = rataEntityMapper.mapEntityToVO(cnmRSoggRata.getCnmTRata());
				rata.setStato(statoRataEntityMapper.mapEntityToVO(cnmRSoggRata.getCnmDStatoRata()));
				rata.setIsRiconciliaEnable(cnmRSoggRata.getCnmDStatoRata().getIdStatoRata() == Constants.ID_STATO_RATA_NON_PAGATO ? Boolean.TRUE : Boolean.FALSE);
				CnmTSoggetto cnmTSoggetto = cnmRSoggRata.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTSoggetto();
				String identificativoSoggetto = cnmTSoggetto.getCodiceFiscale() != null ? cnmTSoggetto.getCodiceFiscale() : cnmTSoggetto.getCodiceFiscaleGiuridico();
				logger.info("************* SOGGETTO: " + identificativoSoggetto + ", STATO RATA: " + cnmRSoggRata.getCnmDStatoRata().getDescStatoRata() + ", ISRICONCILIAENABLE: "
						+ rata.getIsRiconciliaEnable());
				rata.setIsEditEnable(Boolean.FALSE);
				rata.setImportoPagato(cnmRSoggRata.getImportoPagato());
				rata.setDataPagamento(utilsDate.asLocalDate(cnmRSoggRata.getDataPagamento()));
				Integer idSoggetto = cnmRSoggRata.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTSoggetto().getIdSoggetto();
				if (sog == null || idSoggetto != sog.getId())
					sog = UtilsSoggetto.findSoggetto(soggettoList, UtilsSoggetto.findByIdSoggetto(idSoggetto));
				rata.setIdentificativoSoggetto(sog.getPersonaFisica() ? sog.getNome() + " " + sog.getCognome() : sog.getRagioneSociale());
				rata.setIdOrdinanzaVerbaleSoggetto(cnmRSoggRata.getId().getIdOrdinanzaVerbSog());
				rata.setIuv(cnmRSoggRata.getCodIuv());
				rata.setCodiceAvviso(UtilsRata.formattaCodiceAvvisoPerLaVisualizzazione(cnmRSoggRata.getCodAvviso()));
				rataList.add(rata);
			}
			rata1 = UtilsRata.findRata(rataList, UtilsRata.numRataPredicate(1));
			dataScadenzaPrimaRata = rata1.getDataScadenza();
		}
		pianoRateizzazioneVO.setRate(rataList);
		pianoRateizzazioneVO.setDataScadenzaPrimaRata(dataScadenzaPrimaRata);

		pianoRateizzazioneVO.setStato(statoPianoRateEntityMapper.mapEntityToVO(dto.getCnmDStatoPianoRate()));

		boolean isLetteraCreata = allegatoPianoRateizzazioneService.isLetteraPianoCreata(dto);
		pianoRateizzazioneVO.setIsLetteraCreata(isLetteraCreata);

		pianoRateizzazioneVO.setIsNotificaCreata(notificaService.isNotificaCreata(dto));
		boolean isNotificaEnable = statoPiano == Constants.ID_STATO_PIANO_NON_NOTIFICATO || statoPiano == Constants.ID_STATO_PIANO_NOTIFICATO || statoPiano == Constants.ID_STATO_PIANO_PROTOCOLLATO;
		pianoRateizzazioneVO.setIsNotificaEnable(isNotificaEnable);

		List<CnmRAllegatoPianoRate> cnmRAllegatoPianoRateList = cnmRAllegatoPianoRateRepository.findByCnmTPianoRate(dto);
		CnmRAllegatoPianoRate cnmRAllegatoPianoRate = Iterables
				.tryFind(cnmRAllegatoPianoRateList, UtilsTipoAllegato.findCnmRAllegatoPianoRateInCnmRAllegatoPianoRatesByTipoAllegato(TipoAllegato.LETTERA_RATEIZZAZIONE)).orNull();

		if (cnmRAllegatoPianoRate != null) {
			CnmTAllegato cnmTAllegato = cnmRAllegatoPianoRate.getCnmTAllegato();
			pianoRateizzazioneVO.setNumProtocollo(cnmTAllegato.getNumeroProtocollo());
		}

		pianoRateizzazioneVO.setIsRichiestaBollettiniCreata(StringUtils.isNotEmpty(dto.getCodMessaggioEpay()));
		boolean dataScadenzaRate = cnmTRataList.get(0).getDataScadenza() != null;
		pianoRateizzazioneVO.setIsBollettiniEnable(dataScadenzaRate && isLetteraCreata);
		return pianoRateizzazioneVO;
	}

	@Override
	public CnmTPianoRate mapVOtoEntity(PianoRateizzazioneVO vo) {
		CnmTPianoRate cnmTPianoRate = new CnmTPianoRate();
		cnmTPianoRate.setImportoSanzione(vo.getImportoSanzione() != null ? vo.getImportoSanzione().setScale(2, RoundingMode.HALF_UP) : null);
		cnmTPianoRate.setImportoSpeseNotifica(vo.getImportoSpeseNotifica() != null ? vo.getImportoSpeseNotifica().setScale(2, RoundingMode.HALF_UP) : null);
		cnmTPianoRate.setImportoSpeseProcessuali(vo.getImportoSpeseProcessuali() != null ? vo.getImportoSpeseProcessuali().setScale(2, RoundingMode.HALF_UP) : null);
		cnmTPianoRate.setImportoMaggiorazione(vo.getImportoMaggiorazione() != null ? vo.getImportoMaggiorazione().setScale(2, RoundingMode.HALF_UP) : null);

		cnmTPianoRate.setNumeroRate(vo.getNumeroRate());

		return cnmTPianoRate;
	}

	@Override
	public void mapVOtoEntityUpdate(PianoRateizzazioneVO vo, CnmTPianoRate cnmTPianoRate) {
		cnmTPianoRate.setImportoSanzione(vo.getImportoSanzione() != null ? vo.getImportoSanzione().setScale(2, RoundingMode.HALF_UP) : null);
		cnmTPianoRate.setImportoSpeseNotifica(vo.getImportoSpeseNotifica() != null ? vo.getImportoSpeseNotifica().setScale(2, RoundingMode.HALF_UP) : null);
		cnmTPianoRate.setImportoSpeseProcessuali(vo.getImportoSpeseProcessuali() != null ? vo.getImportoSpeseProcessuali().setScale(2, RoundingMode.HALF_UP) : null);
		cnmTPianoRate.setImportoMaggiorazione(vo.getImportoMaggiorazione() != null ? vo.getImportoMaggiorazione().setScale(2, RoundingMode.HALF_UP) : null);
		cnmTPianoRate.setNumeroRate(vo.getNumeroRate());

	}

}
