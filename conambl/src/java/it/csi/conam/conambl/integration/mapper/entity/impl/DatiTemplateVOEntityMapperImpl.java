/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import com.google.common.collect.Iterables;
import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.DatiTemplateVOEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.OrdinanzaEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.RataEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.SoggettoEntityMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.util.UtilsParametro;
import it.csi.conam.conambl.util.UtilsRata;
import it.csi.conam.conambl.vo.pianorateizzazione.RataVO;
import it.csi.conam.conambl.vo.template.DatiTemplateVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 18 gen 2019
 */
@Component
public class DatiTemplateVOEntityMapperImpl implements DatiTemplateVOEntityMapper {

	@Autowired
	private CnmCParametroRepository cnmCParametroRepository;
	@Autowired
	private CnmTRataRepository cnmTRataRepository;
	@Autowired
	private RataEntityMapper rataEntityMapper;
	@Autowired
	private SoggettoEntityMapper soggettoEntityMapper;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private OrdinanzaEntityMapper ordinanzaEntityMapper;
	@Autowired
	private CommonSoggettoService commonSoggettoService;

	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;
	@Autowired
	private UtilsDoqui utilsDoqui;
	
	@Autowired
	private CnmROrdinanzaFiglioRepository cnmROrdinanzaFiglioRepository;
	@Autowired
	private CnmRSollecitoSoggRataRepository cnmRSollecitoSoggRataRepository;

	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;
	

	private static final List<Long> PARAMETRI = Arrays.asList(Constants.ID_DIRIGENTE, Constants.ID_MAIL_SETTORE_TRIBUTI);
	private static final List<Long> PARAMETRI_LETANN = Arrays.asList(Constants.ID_DIREZIONE, Constants.ID_SETTORE, Constants.ID_DIRIGENTE_SETTORE, 
			Constants.ID_SEDE_ENTE, Constants.ID_OGGETTO_PRECOMIPLATO, Constants.ID_ORGANO_ACCERTATORE, Constants.ID_CORPO_LETTERA_ANN, Constants.ID_SALUTI_LETTERA_ANN);
	private static final List<Long> PARAMETRI_LETARC = Arrays.asList(Constants.ID_ORGANO_ACCERTATORE);
	private static final List<Long> PARAMETRI_LETSOLRATE_PARZIALE = Arrays.asList(Constants.ID_OGGETTO_LETSOLRATE_PARZIALE, Constants.ID_CORPO1_LETSOLRATE_PARZIALE, Constants.ID_CORPO2_LETSOLRATE_PARZIALE, Constants.ID_DIRIGENTE_SETTORE);
	private static final List<Long> PARAMETRI_LETSOLRATE_NP = Arrays.asList(Constants.ID_OGGETTO_LETSOLRATE_NP, Constants.ID_CORPO1_LETSOLRATE_NP, Constants.ID_CORPO2_LETSOLRATE_NP, Constants.ID_DIRIGENTE_SETTORE);
	
	@Override
	public DatiTemplateVO mapEntityToVO(CnmTPianoRate dto) {
		if (dto == null)
			return null;

		DatiTemplateVO response = new DatiTemplateVO();

		List<SoggettoVO> soggettoList = new ArrayList<>();

		List<CnmTRata> cnmTRataList = cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(dto);
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmRSoggRataRepository.findDistinctCnmROrdinanzaVerbSogByCnmTRataIn(cnmTRataList);
		for (CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog : cnmROrdinanzaVerbSogList) {
			
			CnmTSoggetto cnmTSoggetto = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTSoggetto();
			SoggettoVO sog = soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);

			
			// 20201217_LC - JIRA 118
			CnmTVerbale cnmTVerbale = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTVerbale();
			if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
				sog = commonSoggettoService.attachResidenzaPregressi(sog, cnmTSoggetto, cnmTVerbale.getIdVerbale());
			}
			
			
			sog.setIdSoggettoOrdinanza(cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog());
			sog.setIdSoggettoVerbale(cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getIdVerbaleSoggetto());
			soggettoList.add(sog);
		}
		response.setListaSoggetti(soggettoList);

		BigDecimal speseNotifica = dto.getImportoSpeseNotifica() != null ? dto.getImportoSpeseNotifica() : BigDecimal.ZERO;
		BigDecimal importoSpeseProcessuali = dto.getImportoSpeseProcessuali() != null ? dto.getImportoSpeseProcessuali() : BigDecimal.ZERO;
		BigDecimal importoSanzione = dto.getImportoSanzione() != null ? dto.getImportoSanzione() : BigDecimal.ZERO;
		BigDecimal importoMaggiorazione = dto.getImportoMaggiorazione() != null ? dto.getImportoMaggiorazione() : BigDecimal.ZERO;
		BigDecimal totale = speseNotifica.add(importoSpeseProcessuali).add(importoSanzione).add(importoMaggiorazione);
		response.setImportoTotale(totale.setScale(2, RoundingMode.HALF_UP));
		response.setNumeroRate(dto.getNumeroRate());

		List<RataVO> rate = rataEntityMapper.mapListEntityToListVO(cnmTRataList);

		RataVO rata1 = UtilsRata.findRata(rate, UtilsRata.numRataPredicate(1));
		RataVO rata2 = UtilsRata.findRata(rate, UtilsRata.numRataPredicate(2));
		RataVO rataN = UtilsRata.findRata(rate, UtilsRata.numRataPredicate(dto.getNumeroRate().intValue()));

		response.setImportoPrimaRata(rata1.getImportoRata().setScale(2, RoundingMode.HALF_UP));
		response.setImportoUltimaRata(rataN.getImportoRata().setScale(2, RoundingMode.HALF_UP));
		response.setImportoAltreRate(rata2.getImportoRata().setScale(2, RoundingMode.HALF_UP));
		response.setScadenzaPrimaRata(rata1.getDataScadenza());
		response.setScadenzaDefinita(rata1.getDataScadenza() != null ? Boolean.TRUE : Boolean.FALSE);
		response.setFn(utilsDoqui.getFn(dto));
		response.setClassificazione(utilsDoqui.getClassificazione(dto));

		mapCommonParameter(response);

		return response;
	}

	
	
	
	
	
	private void mapCommonParameter(DatiTemplateVO response) {
		List<CnmCParametro> cnmCParametroList = cnmCParametroRepository.findByIdParametroIn(PARAMETRI);
		
		CnmCParametro cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_DIRIGENTE)).orNull();
		if (cnmCParametro != null)
			response.setDirigente(cnmCParametro.getValoreString());
		
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_MAIL_SETTORE_TRIBUTI)).orNull();
		if (cnmCParametro != null)
			response.setMailSettoreTributi(cnmCParametro.getValoreString());		
	}
	
	
	private void mapLetteraAnnullamentoParameters(DatiTemplateVO response) {
		List<CnmCParametro> cnmCParametroList = cnmCParametroRepository.findByIdParametroIn(PARAMETRI_LETANN);
		
		// 20210312_Lc 8 nuovi parametri (per ora usati solo in ordinanza annullametno)
		CnmCParametro cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_DIREZIONE)).orNull();
		if (cnmCParametro != null)
			response.setDirezione(cnmCParametro.getValoreString());
		
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_SETTORE)).orNull();
		if (cnmCParametro != null)
			response.setSettore(cnmCParametro.getValoreString());		
		
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_DIRIGENTE_SETTORE)).orNull();
		if (cnmCParametro != null)
			response.setDirigenteLettera(cnmCParametro.getValoreString());			
		
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_SEDE_ENTE)).orNull();
		if (cnmCParametro != null)
			response.setSedeEnte(cnmCParametro.getValoreString());					
		
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_OGGETTO_PRECOMIPLATO)).orNull();
		if (cnmCParametro != null) {
			String oggetto = String.format(cnmCParametro.getValoreString(), 
					response.getOrdinanza().getNumDeterminazione(), 
					String.valueOf(response.getOrdinanza().getDataOrdinanza().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))), 
					response.getOrdinanzaAnnullata().getNumDeterminazione(), 
					String.valueOf(response.getOrdinanzaAnnullata().getDataOrdinanza().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
					
			// lista nome+cognome soggetti nell'oggetto
			String listaNomi = " ";
			for (SoggettoVO sogg : response.getListaSoggetti()) {
				listaNomi = listaNomi + sogg.getNome() + " " + sogg.getCognome() + ", ";
			}
			listaNomi = listaNomi.substring(0, listaNomi.length()-2);			
			response.setOggettoLettera(oggetto+listaNomi+".");		
		}		
		
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_ORGANO_ACCERTATORE)).orNull();
		if (cnmCParametro != null)
			response.setInfoOrganoAccertatore(cnmCParametro.getValoreString());	
		
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_CORPO_LETTERA_ANN)).orNull();
		if (cnmCParametro != null)
			response.setCorpoLettera(cnmCParametro.getValoreString());	
		
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_SALUTI_LETTERA_ANN)).orNull();
		if (cnmCParametro != null)
			response.setSalutiLettera(cnmCParametro.getValoreString());			
		
	}
	

	
	private void mapLetteraArchiviazioneParameters(DatiTemplateVO response) {
		List<CnmCParametro> cnmCParametroList = cnmCParametroRepository.findByIdParametroIn(PARAMETRI_LETARC);
		
		// 20210326_LC info organo accertatoer anche per lettera archiviazione		
		CnmCParametro cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_ORGANO_ACCERTATORE)).orNull();
		if (cnmCParametro != null)
			response.setInfoOrganoAccertatore(cnmCParametro.getValoreString());			
	}

	
	
	@Override
	public DatiTemplateVO mapEntityToVO(CnmTOrdinanza dto) {
		if (dto == null)
			return null;

		DatiTemplateVO response = new DatiTemplateVO();
		response.setNumeroProtocollo(dto.getNumeroProtocollo());

		List<SoggettoVO> soggettoList = new ArrayList<>();
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(dto);
		for (CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog : cnmROrdinanzaVerbSogList) {
			

			CnmTSoggetto cnmTSoggetto = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTSoggetto();
			SoggettoVO sog = soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);

			
			// 20201217_LC - JIRA 118
			CnmTVerbale cnmTVerbale = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTVerbale();
			if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
				sog = commonSoggettoService.attachResidenzaPregressi(sog, cnmTSoggetto, cnmTVerbale.getIdVerbale());
			}
			
			
			sog.setIdSoggettoOrdinanza(cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog());
			sog.setIdSoggettoVerbale(cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getIdVerbaleSoggetto());
			soggettoList.add(sog);
		}
		

		response.setListaSoggetti(soggettoList);
		response.setClassificazione(utilsDoqui.getClassificazione(dto));
		response.setFn(utilsDoqui.getFn(dto));
		mapCommonParameter(response);
		
		
		
		// 20210325_LC lotto2scenario7
		List<CnmROrdinanzaFiglio> cnmROrdinanzaFiglioList = cnmROrdinanzaFiglioRepository.findByCnmTOrdinanzaFiglio(dto);
		if (cnmROrdinanzaFiglioList != null && !cnmROrdinanzaFiglioList.isEmpty()) {
			CnmTOrdinanza cnmTOrdinanzaToMap = cnmROrdinanzaFiglioList.get(0).getCnmTOrdinanza();
			response.setOrdinanzaAnnullata(ordinanzaEntityMapper.mapEntityToVO(cnmTOrdinanzaToMap));
			response.setOrdinanza(ordinanzaEntityMapper.mapEntityToVO(dto));
			mapLetteraAnnullamentoParameters(response);						
		}
		
		
		// 20210326_LC lotto2scenarioC
		if (dto.getCnmDTipoOrdinanza().getIdTipoOrdinanza() == Constants.ID_TIPO_ORDINANZA_ARCHIVIATO) {
			mapLetteraArchiviazioneParameters(response);
		}
		
		
		

		return response;
	}

	@Override
	public DatiTemplateVO mapEntityToVO(CnmTSollecito dto) {
		if (dto == null)
			return null;

		DatiTemplateVO response = new DatiTemplateVO();

		List<SoggettoVO> soggettoList = new ArrayList<>();
		CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = dto.getCnmROrdinanzaVerbSog();
		
		CnmTSoggetto cnmTSoggetto =cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTSoggetto();
		SoggettoVO sog = soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);
				
		
		// 20201217_LC - JIRA 118
		CnmTVerbale cnmTVerbale = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTVerbale();
		if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
			sog = commonSoggettoService.attachResidenzaPregressi(sog, cnmTSoggetto, cnmTVerbale.getIdVerbale());
		}
		
		
		sog.setIdSoggettoOrdinanza(cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog());
		sog.setIdSoggettoVerbale(cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getIdVerbaleSoggetto());
		soggettoList.add(sog);

		BigDecimal importo = dto.getImportoSollecito() != null ? dto.getImportoSollecito() : BigDecimal.ZERO;
		BigDecimal maggioranza = dto.getMaggiorazione() != null ? dto.getMaggiorazione() : BigDecimal.ZERO;

		response.setListaSoggetti(soggettoList);
		response.setImportoTotale(importo.add(maggioranza).setScale(2, RoundingMode.HALF_UP));

		response.setClassificazione(utilsDoqui.getClassificazione(dto));
		response.setFn(utilsDoqui.getFn(dto));
		mapCommonParameter(response);
		
		
		
		// 20210401_LC	se è un sollecito pagameto rate, aggiunge nuovi aprametri (distinti tra pagamento parziale o n.p.)
		boolean isSollecitoRate = false;	
		boolean isPagamentoParziale = false;
		CnmTOrdinanza cnmTOrdinanza = null;
		Date dataUltimaRataPagata = null;	
		BigDecimal totDaPagare = BigDecimal.ZERO;	
		Integer nRateDaPagare = 0;
		
		if (dto.getCnmDTipoSollecito().getIdTipoSollecito() == Constants.ID_TIPO_SOLLECITO_RATE) {
			List<CnmRSollecitoSoggRata> cnmRSollecitoSoggRataList = cnmRSollecitoSoggRataRepository.findByCnmTSollecito(dto);
			isSollecitoRate = true;	
			cnmTOrdinanza = cnmRSollecitoSoggRataList.get(0).getCnmROrdinanzaVerbSog().getCnmTOrdinanza();
			
			for (CnmRSollecitoSoggRata sollSoggRata : cnmRSollecitoSoggRataList) {
				CnmRSoggRata soggRata = cnmRSoggRataRepository.findByCnmROrdinanzaVerbSogAndCnmTRata(sollSoggRata.getCnmROrdinanzaVerbSog(), sollSoggRata.getCnmTRata());
				// calcolo data ultima pagata, n. ancora da pagare, eur ancora da pagare				
				if (soggRata.getCnmDStatoRata().getIdStatoRata() == Constants.ID_STATO_RATA_PAGATO_ONLINE || soggRata.getCnmDStatoRata().getIdStatoRata() == Constants.ID_STATO_RATA_PAGATO_OFFLINE) {
					isPagamentoParziale = true;					
					if (dataUltimaRataPagata == null) {
						dataUltimaRataPagata = soggRata.getDataPagamento();
					} else {
						
						if (soggRata.getDataPagamento().after(dataUltimaRataPagata))
							dataUltimaRataPagata = soggRata.getDataPagamento();					
					}
				
				} else if (soggRata.getCnmDStatoRata().getIdStatoRata() == Constants.ID_STATO_RATA_NON_PAGATO) {
					totDaPagare = totDaPagare.add(soggRata.getCnmTRata().getImportoRata());
					nRateDaPagare += 1;					
				}			
			}						
		}	
		
		if (isSollecitoRate) {					
			if (isPagamentoParziale) {	
				SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");							
				mapLetteraSollecitoRateParzialeParameters(response, cnmTOrdinanza, formatDate.format(dataUltimaRataPagata), totDaPagare.toString(), String.valueOf(nRateDaPagare));				
			} else {				
				mapLetteraSollecitoRateNPParameters(response, cnmTOrdinanza);				
			}		
		}
		

		return response;
	}


	
	private void mapLetteraSollecitoRateParzialeParameters(DatiTemplateVO response, CnmTOrdinanza cnmTOrdinanza, String dataUltimaRataPagata, String totDaPagare, String nRateDaPagare) {
		List<CnmCParametro> cnmCParametroList = cnmCParametroRepository.findByIdParametroIn(PARAMETRI_LETSOLRATE_PARZIALE);
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");	
		
		CnmCParametro cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_OGGETTO_LETSOLRATE_PARZIALE)).orNull();
		if (cnmCParametro != null) {
			String oggetto = String.format(cnmCParametro.getValoreString(), cnmTOrdinanza.getNumDeterminazione(), formatDate.format(cnmTOrdinanza.getDataOrdinanza()));		
			response.setOggettoLettera(oggetto);
		}
		
		String corpo = "";
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_CORPO1_LETSOLRATE_PARZIALE)).orNull();
		if (cnmCParametro != null) {
			corpo = String.format(cnmCParametro.getValoreString(), dataUltimaRataPagata, totDaPagare, nRateDaPagare);
		}
		
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_CORPO2_LETSOLRATE_PARZIALE)).orNull();
		if (cnmCParametro != null) {
			corpo = corpo + "\n\n" + cnmCParametro.getValoreString();
		}	
		
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_DIRIGENTE_SETTORE)).orNull();
		if (cnmCParametro != null)
			response.setDirigenteLettera(cnmCParametro.getValoreString());	
		
		response.setCorpoLettera(corpo);
		
	}
	

	
	private void mapLetteraSollecitoRateNPParameters(DatiTemplateVO response, CnmTOrdinanza cnmTOrdinanza) {
		List<CnmCParametro> cnmCParametroList = cnmCParametroRepository.findByIdParametroIn(PARAMETRI_LETSOLRATE_NP);
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");	
			
		CnmCParametro cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_OGGETTO_LETSOLRATE_NP)).orNull();
		if (cnmCParametro != null) {
			String oggetto = String.format(cnmCParametro.getValoreString(), cnmTOrdinanza.getNumDeterminazione(), formatDate.format(cnmTOrdinanza.getDataOrdinanza()));		
			response.setOggettoLettera(oggetto);
		}	
		
		String corpo = "";
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_CORPO1_LETSOLRATE_NP)).orNull();
		if (cnmCParametro != null) {
			corpo = cnmCParametro.getValoreString();
		}	
		
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_CORPO2_LETSOLRATE_NP)).orNull();
		if (cnmCParametro != null) {
			corpo = corpo + "\n\n" + cnmCParametro.getValoreString();	
		}	
		
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_DIRIGENTE_SETTORE)).orNull();
		if (cnmCParametro != null)
			response.setDirigenteLettera(cnmCParametro.getValoreString());	
		
		response.setCorpoLettera(corpo);
		
	}
	
	
	
	
	@Override
	public DatiTemplateVO mapEntityToVO(List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList) {
		if (cnmRVerbaleSoggettoList == null)
			return null;

		DatiTemplateVO response = new DatiTemplateVO();
		List<SoggettoVO> soggettoList = new ArrayList<>();

		for (CnmRVerbaleSoggetto cnmRVerbaleSoggetto : cnmRVerbaleSoggettoList) {
			
			CnmTSoggetto cnmTSoggetto =cnmRVerbaleSoggetto.getCnmTSoggetto();
			SoggettoVO sog = soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);
			
			// 20201217_LC - JIRA 118
			CnmTVerbale cnmTVerbale = cnmRVerbaleSoggetto.getCnmTVerbale();
			if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
				sog = commonSoggettoService.attachResidenzaPregressi(sog, cnmTSoggetto, cnmTVerbale.getIdVerbale());
			}			


			sog.setIdSoggettoVerbale(cnmRVerbaleSoggetto.getIdVerbaleSoggetto());
			soggettoList.add(sog);
		}
		response.setListaSoggetti(soggettoList);
		response.setClassificazione(utilsDoqui.getClassificazione(cnmRVerbaleSoggettoList.get(0)));
		response.setFn(utilsDoqui.getFn(cnmRVerbaleSoggettoList.get(0)));
		return response;
	}






	@Override
	public DatiTemplateVO mapEntityToVO(Integer idVerbale, List<SoggettoVO> soggettoList) {
		if (soggettoList == null)
			return null;

		DatiTemplateVO response = new DatiTemplateVO();

		response.setListaSoggetti(soggettoList);
		CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findByIdVerbale(idVerbale);
		CnmRVerbaleSoggetto cnmRVerbaleSoggetto = new CnmRVerbaleSoggetto();
		cnmRVerbaleSoggetto.setCnmTVerbale(cnmTVerbale);
		
		response.setClassificazione(utilsDoqui.getClassificazione(cnmRVerbaleSoggetto));
		response.setFn(utilsDoqui.getFn(cnmRVerbaleSoggetto));
		return response;
	}

}
