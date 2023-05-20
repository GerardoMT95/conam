/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.ws.impl.epay;

import com.google.common.collect.Iterables;
import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.epay.to.*;
import it.csi.conam.conambl.integration.mapper.entity.SoggettoEntityMapper;
import it.csi.conam.conambl.integration.mapper.ws.epay.EPayWsInputMapper;
import it.csi.conam.conambl.integration.repositories.CnmCParametroRepository;
import it.csi.conam.conambl.integration.repositories.CnmRSollecitoSoggRataRepository;
import it.csi.conam.conambl.util.UtilsParametro;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author riccardo.bova
 * @date 31 gen 2019
 */

@Component
public class EPayWsInputMapperImpl implements EPayWsInputMapper {

	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private SoggettoEntityMapper soggettoEntityMapper;
	@Autowired
	private CnmCParametroRepository cnmCParametroRepository;
	@Autowired
	private CommonSoggettoService commonSoggettoService;
	@Autowired
	private CnmRSollecitoSoggRataRepository cnmRSollecitoSoggRataRepository;
	
	private static final List<Long> PARAMETRI = Arrays.asList(Constants.ID_CODICE_VERSAMENTO_PIEMONTE_PAY, //
			Constants.ID_CODICE_FISCALE_ENTE_CREDITORE, //
			Constants.ID_OGGETTO_PAGAMENTO_RATEIZZAZIONE, //
			Constants.ID_OGGETTO_PAGAMENTO_ORDINANZA, //
			Constants.ID_OGGETTO_PAGAMENTO_SOLLECITO, //
			Constants.ID_OGGETTO_PAGAMENTO_SOLLECITO_RATE);

	@Override
	public InserisciListaDiCaricoRequest mapRateSoggettoToWsMapper(List<CnmRSoggRata> rate) {
		InserisciListaDiCaricoRequest inserisciListaDiCaricoRequest = new InserisciListaDiCaricoRequest();

		ListaDiCarico listaDiCarico = new ListaDiCarico();
		TestataListaCarico testataListaCarico = new TestataListaCarico();
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

		String descrizioneCausaleVersamento = null;
		List<CnmCParametro> cnmCParametroList = cnmCParametroRepository.findByIdParametroIn(PARAMETRI);

		CnmCParametro cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_OGGETTO_PAGAMENTO_RATEIZZAZIONE)).orNull();
		if (cnmCParametro != null)
			descrizioneCausaleVersamento = cnmCParametro.getValoreString();

		BigDecimal importoTotale = BigDecimal.ZERO;

		SoggettoType soggettoPagatore = null;
		int idOrdinanzaVerbSog = -2;

		List<PosizioneDaInserireType> posInserireTypeList = new ArrayList<>();
		String idMessaggio = rate.get(0).getCnmTRata().getCnmTPianoRate().getCodMessaggioEpay();
		for (CnmRSoggRata cnmRSoggRata : rate) {
			CnmTRata cnmTRata = cnmRSoggRata.getCnmTRata();
			CnmTSoggetto cnmTSoggetto = cnmRSoggRata.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTSoggetto();
			// per non rimappare 20 volte lo stesso soggetto
			if (idOrdinanzaVerbSog != cnmRSoggRata.getCnmROrdinanzaVerbSog().getIdOrdinanzaVerbSog()) {
				idOrdinanzaVerbSog = cnmRSoggRata.getCnmROrdinanzaVerbSog().getIdOrdinanzaVerbSog();
				
				SoggettoVO soggettoVoToMap = soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);
				// 20201217_LC - JIRA 118
				CnmTVerbale cnmTVerbale = cnmRSoggRata.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTVerbale();				
				if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
					soggettoVoToMap = commonSoggettoService.attachResidenzaPregressi(soggettoVoToMap, cnmTSoggetto, cnmTVerbale.getIdVerbale());
				}												
				soggettoPagatore = mapSoggettoVOtoSoggettoType(soggettoVoToMap);
				
			}

			PosizioneDaInserireType posizioneDaInserireType = new PosizioneDaInserireType();
			// posizioneDaInserireType.setComponentiImporto(componentiImporto);//
			// non obbligatorio
			/*if (cnmTRata.getDataScadenza() != null) {
				Calendar dataFineValidita = Calendar.getInstance();
				dataFineValidita.setTime(cnmTRata.getDataScadenza());
				dataFineValidita.add(Calendar.YEAR, 3);
				posizioneDaInserireType.setDataFineValidita(dataFineValidita.getTime());
				Calendar dataScadenza = Calendar.getInstance();
				dataScadenza.setTime(cnmTRata.getDataScadenza());
				posizioneDaInserireType.setAnnoRiferimento(cnmTRata.getDataScadenza() != null ? BigInteger.valueOf(dataScadenza.get(Calendar.YEAR)) : null);
			}*/
			
			Date dataFineValidita = cnmRSoggRata.getCnmTRata().getDataFineValidita();
			if (dataFineValidita != null) {
				posizioneDaInserireType.setDataFineValidita(dataFineValidita);
				posizioneDaInserireType.setAnnoRiferimento(utilsDate.getYear(dataFineValidita));				
			}
			
			
			posizioneDaInserireType.setDataInizioValidita(now);
			posizioneDaInserireType.setDataScadenza(cnmTRata.getDataScadenza());
			posizioneDaInserireType.setDescrizioneCausaleVersamento(descrizioneCausaleVersamento);
			posizioneDaInserireType.setIdPosizioneDebitoria(cnmRSoggRata.getCodPosizioneDebitoria());
			posizioneDaInserireType.setImportoTotale(cnmTRata.getImportoRata().setScale(2, RoundingMode.HALF_UP));

			// posizioneDaInserireType.setNotePerIlPagatore(notePerIlPagatore);
			// non serve
			posizioneDaInserireType.setSoggettoPagatore(soggettoPagatore);
			posizioneDaInserireType.setDescrizioneRata(cnmTRata.getNumeroRata().toString());// verificare

			importoTotale = importoTotale.add(cnmTRata.getImportoRata());

			posInserireTypeList.add(posizioneDaInserireType);
		}

		// testataListaDicarico
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_CODICE_FISCALE_ENTE_CREDITORE)).orNull();
		if (cnmCParametro != null)
			testataListaCarico.setCFEnteCreditore(cnmCParametro.getValoreString());
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_CODICE_VERSAMENTO_PIEMONTE_PAY)).orNull();
		if (cnmCParametro != null)
			testataListaCarico.setCodiceVersamento(cnmCParametro.getValoreString());
		testataListaCarico.setImportoTotaleListaDiCarico(importoTotale.setScale(2, RoundingMode.HALF_UP));
		testataListaCarico.setNumeroPosizioniDebitorie(BigInteger.valueOf(posInserireTypeList.size()));
		testataListaCarico.setIdMessaggio(idMessaggio);

		listaDiCarico.setPosizioniDaInserire(posInserireTypeList.toArray(new PosizioneDaInserireType[0]));
		inserisciListaDiCaricoRequest.setListaDiCarico(listaDiCarico);
		inserisciListaDiCaricoRequest.setTestata(testataListaCarico);

		return inserisciListaDiCaricoRequest;
	}

	private SoggettoType mapSoggettoVOtoSoggettoType(SoggettoVO soggetto) {
		SoggettoType soggettoPagatore = new SoggettoType();

		// soggettoPagatore.setCAP(); non lo abbiamo sul nostro DB
		soggettoPagatore.setCivico(soggetto.getCivicoResidenza());
		soggettoPagatore.setIndirizzo(soggetto.getIndirizzoResidenza());
		soggettoPagatore.setLocalita(soggetto.getComuneResidenza() != null ? soggetto.getComuneResidenza().getDenominazione() : null);
		soggettoPagatore.setNazione(soggetto.getNazioneNascita() != null ? soggetto.getNazioneNascita().getDenominazione().substring(0, 2) : null);
		soggettoPagatore.setProvincia(soggetto.getProvinciaResidenza() != null ? soggetto.getProvinciaResidenza().getDenominazione() : null);
		soggettoPagatore.setIdentificativoUnivocoFiscale(StringUtils.defaultString(soggetto.getCodiceFiscale(), soggetto.getPartitaIva()));
		if (soggetto.getPersonaFisica()) {
			PersonaFisicaType personaFisica = new PersonaFisicaType();
			personaFisica.setCognome(soggetto.getCognome());
			personaFisica.setNome(soggetto.getNome());
			soggettoPagatore.setPersonaFisica(personaFisica);
		} else {
			PersonaGiuridicaType personaGiuridica = new PersonaGiuridicaType();
			personaGiuridica.setRagioneSociale(StringUtils.abbreviate(soggetto.getRagioneSociale(), 70));
			soggettoPagatore.setPersonaGiuridica(personaGiuridica);
		}

		return soggettoPagatore;
	}

	@Override
	public InserisciListaDiCaricoRequest mapRataSoggettiToWsMapper(
		List<CnmROrdinanzaVerbSog> soggettiOrdinanza
	) {
		InserisciListaDiCaricoRequest inserisciListaDiCaricoRequest = new InserisciListaDiCaricoRequest();

		ListaDiCarico listaDiCarico = new ListaDiCarico();
		TestataListaCarico testataListaCarico = new TestataListaCarico();
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

		String descrizioneCausaleVersamento = null;
		List<CnmCParametro> cnmCParametroList = cnmCParametroRepository.findByIdParametroIn(PARAMETRI);

		CnmCParametro cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_OGGETTO_PAGAMENTO_ORDINANZA)).orNull();
		if (cnmCParametro != null)
			descrizioneCausaleVersamento = cnmCParametro.getValoreString();

		BigDecimal importoTotale = BigDecimal.ZERO;

		SoggettoType soggettoPagatore;
		List<PosizioneDaInserireType> posInserireTypeList = new ArrayList<>();

		CnmTOrdinanza cnmTOrdinanza = soggettiOrdinanza.get(0).getCnmTOrdinanza();
		String idMessaggio = cnmTOrdinanza.getCodMessaggioEpay();
		
		BigDecimal importNotifica = BigDecimal.ZERO;
		if(cnmTOrdinanza.getCnmTNotificas()!=null && !cnmTOrdinanza.getCnmTNotificas().isEmpty()) {
			Iterator<CnmTNotifica> iterNotifica = cnmTOrdinanza.getCnmTNotificas().iterator();
			while(iterNotifica.hasNext()) {
				CnmTNotifica notificaItem = iterNotifica.next();
				importNotifica = importNotifica.add(notificaItem.getImportoSpeseNotifica());
			}			
		}

		for (CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog : soggettiOrdinanza) {
			CnmTSoggetto cnmTSoggetto = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTSoggetto();
					
			// 20201217_LC - JIRA 118			
			SoggettoVO soggettoVoToMap = soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);
			CnmTVerbale cnmTVerbale = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTVerbale();			
			if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
				soggettoVoToMap = commonSoggettoService.attachResidenzaPregressi(soggettoVoToMap, cnmTSoggetto, cnmTVerbale.getIdVerbale());
			}						
			soggettoPagatore = mapSoggettoVOtoSoggettoType(soggettoVoToMap);

			
			PosizioneDaInserireType posizioneDaInserireType = new PosizioneDaInserireType();
			/*if (cnmTOrdinanza.getDataScadenzaOrdinanza() != null) {
				Calendar dataFineValidita = Calendar.getInstance();
				dataFineValidita.setTime(cnmTOrdinanza.getDataScadenzaOrdinanza());
				dataFineValidita.add(Calendar.YEAR, 3);
				posizioneDaInserireType.setDataFineValidita(dataFineValidita.getTime());
				Calendar dataScadenza = Calendar.getInstance();
				dataScadenza.setTime(cnmTOrdinanza.getDataScadenzaOrdinanza());
				posizioneDaInserireType.setAnnoRiferimento(cnmTOrdinanza.getDataScadenzaOrdinanza() != null ? BigInteger.valueOf(dataScadenza.get(Calendar.YEAR)) : null);
			}*/
			Date dataFineValidita = cnmTOrdinanza.getDataFineValidita();
			if (dataFineValidita != null) {
				posizioneDaInserireType.setDataFineValidita(dataFineValidita);
				posizioneDaInserireType.setAnnoRiferimento(utilsDate.getYear(dataFineValidita));
			}
			
			
			posizioneDaInserireType.setDataInizioValidita(now);
			posizioneDaInserireType.setDataScadenza(cnmTOrdinanza.getDataScadenzaOrdinanza());
			posizioneDaInserireType.setDescrizioneCausaleVersamento(descrizioneCausaleVersamento);
			posizioneDaInserireType.setIdPosizioneDebitoria(cnmROrdinanzaVerbSog.getCodPosizioneDebitoria());
			posizioneDaInserireType.setImportoTotale(cnmTOrdinanza.getImportoOrdinanza().setScale(2, RoundingMode.HALF_UP).add(importNotifica));

			posizioneDaInserireType.setSoggettoPagatore(soggettoPagatore);
			posizioneDaInserireType.setDescrizioneRata(BigDecimal.ONE.toString());		
						
			importoTotale = importoTotale.add(cnmTOrdinanza.getImportoOrdinanza().add(importNotifica));

			posInserireTypeList.add(posizioneDaInserireType);
		}	
		

		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_CODICE_FISCALE_ENTE_CREDITORE)).orNull();
		if (cnmCParametro != null)
			testataListaCarico.setCFEnteCreditore(cnmCParametro.getValoreString());
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_CODICE_VERSAMENTO_PIEMONTE_PAY)).orNull();
		if (cnmCParametro != null)
			testataListaCarico.setCodiceVersamento(cnmCParametro.getValoreString());
		testataListaCarico.setImportoTotaleListaDiCarico(importoTotale.setScale(2, RoundingMode.HALF_UP));
		testataListaCarico.setNumeroPosizioniDebitorie(BigInteger.valueOf(posInserireTypeList.size()));
		testataListaCarico.setIdMessaggio(idMessaggio);

		listaDiCarico.setPosizioniDaInserire(posInserireTypeList.toArray(new PosizioneDaInserireType[0]));
		inserisciListaDiCaricoRequest.setListaDiCarico(listaDiCarico);
		inserisciListaDiCaricoRequest.setTestata(testataListaCarico);

		return inserisciListaDiCaricoRequest;
	}

	@Override
	public InserisciListaDiCaricoRequest mapRataSollecitoToWsMapper(
		CnmTSollecito cnmTSollecito
	) {
		InserisciListaDiCaricoRequest inserisciListaDiCaricoRequest = new InserisciListaDiCaricoRequest();

		ListaDiCarico listaDiCarico = new ListaDiCarico();
		TestataListaCarico testataListaCarico = new TestataListaCarico();
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

		String descrizioneCausaleVersamento = null;
		List<CnmCParametro> cnmCParametroList = cnmCParametroRepository.findByIdParametroIn(PARAMETRI);

		CnmCParametro cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_OGGETTO_PAGAMENTO_SOLLECITO)).orNull();
		if (cnmCParametro != null)
			descrizioneCausaleVersamento = cnmCParametro.getValoreString();

		if (cnmTSollecito.getCnmDTipoSollecito().getIdTipoSollecito() == Constants.ID_TIPO_SOLLECITO_RATE) {
			cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_OGGETTO_PAGAMENTO_SOLLECITO_RATE)).orNull();
			List<CnmRSollecitoSoggRata> cnmRSollecitoSoggRataList = cnmRSollecitoSoggRataRepository.findByCnmTSollecito(cnmTSollecito);
			if (cnmCParametro != null)
//				descrizioneCausaleVersamento = cnmCParametro.getValoreString() + cnmRSollecitoSoggRataList.get(0).getCnmTRata().getCnmTPianoRate().getNumeroProtocollo();	// 20210427_LC 
				descrizioneCausaleVersamento = String.format(cnmCParametro.getValoreString(), cnmRSollecitoSoggRataList.get(0).getCnmTRata().getCnmTPianoRate().getNumeroProtocollo(), cnmTSollecito.getCnmROrdinanzaVerbSog().getCnmTOrdinanza().getNumDeterminazione());
		}
	
			
		SoggettoType soggettoPagatore;
		List<PosizioneDaInserireType> posInserireTypeList = new ArrayList<>();

		String idMessaggio = cnmTSollecito.getCodMessaggioEpay();

		CnmTSoggetto cnmTSoggetto = cnmTSollecito.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTSoggetto();
					
		// 20201217_LC - JIRA 118			
		SoggettoVO soggettoVoToMap = soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);
		CnmTVerbale cnmTVerbale = cnmTSollecito.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTVerbale();		
		if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
			soggettoVoToMap = commonSoggettoService.attachResidenzaPregressi(soggettoVoToMap, cnmTSoggetto, cnmTVerbale.getIdVerbale());
		}						
		soggettoPagatore = mapSoggettoVOtoSoggettoType(soggettoVoToMap);
		
		
		
		
		PosizioneDaInserireType posizioneDaInserireType = new PosizioneDaInserireType();
		if (cnmTSollecito.getDataScadenzaRata() != null) {
			/*Calendar dataFineValidita = Calendar.getInstance();
			dataFineValidita.setTime(cnmTSollecito.getDataScadenzaRata());
			dataFineValidita.add(Calendar.YEAR, 3);
			posizioneDaInserireType.setDataFineValidita(dataFineValidita.getTime());
			Calendar dataScadenza = Calendar.getInstance();
			dataScadenza.setTime(cnmTSollecito.getDataScadenzaRata());
			posizioneDaInserireType.setAnnoRiferimento(cnmTSollecito.getDataScadenzaRata() != null ? BigInteger.valueOf(dataScadenza.get(Calendar.YEAR)) : null);

			posizioneDaInserireType.setDataInizioValidita(now);*/
			posizioneDaInserireType.setDataScadenza(cnmTSollecito.getDataScadenzaRata());
		}

		Date dataFineValidita = cnmTSollecito.getDataFineValidita();
		if (dataFineValidita != null) {
			posizioneDaInserireType.setDataFineValidita(dataFineValidita);
			posizioneDaInserireType.setAnnoRiferimento(utilsDate.getYear(dataFineValidita));
		}
		

		posizioneDaInserireType.setDataInizioValidita(now);

		posizioneDaInserireType.setDescrizioneCausaleVersamento(descrizioneCausaleVersamento);
		posizioneDaInserireType.setIdPosizioneDebitoria(cnmTSollecito.getCodPosizioneDebitoria());

		BigDecimal importoSollecito = cnmTSollecito.getImportoSollecito() != null ? cnmTSollecito.getImportoSollecito() : BigDecimal.ZERO;
		BigDecimal importoMaggiorazione = cnmTSollecito.getMaggiorazione() != null ? cnmTSollecito.getMaggiorazione() : BigDecimal.ZERO;
		BigDecimal importoTotale = importoSollecito.add(importoMaggiorazione);
		posizioneDaInserireType.setImportoTotale(importoTotale.setScale(2, RoundingMode.HALF_UP));

		posizioneDaInserireType.setSoggettoPagatore(soggettoPagatore);
		posizioneDaInserireType.setDescrizioneRata(BigDecimal.ONE.toString());

		posInserireTypeList.add(posizioneDaInserireType);

		// testata
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_CODICE_FISCALE_ENTE_CREDITORE)).orNull();
		if (cnmCParametro != null)
			testataListaCarico.setCFEnteCreditore(cnmCParametro.getValoreString());
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_CODICE_VERSAMENTO_PIEMONTE_PAY)).orNull();
		if (cnmCParametro != null)
			testataListaCarico.setCodiceVersamento(cnmCParametro.getValoreString());
		testataListaCarico.setImportoTotaleListaDiCarico(importoTotale.setScale(2, RoundingMode.HALF_UP));
		testataListaCarico.setNumeroPosizioniDebitorie(BigInteger.valueOf(posInserireTypeList.size()));
		testataListaCarico.setIdMessaggio(idMessaggio);

		listaDiCarico.setPosizioniDaInserire(posInserireTypeList.toArray(new PosizioneDaInserireType[0]));
		inserisciListaDiCaricoRequest.setListaDiCarico(listaDiCarico);
		inserisciListaDiCaricoRequest.setTestata(testataListaCarico);
		return inserisciListaDiCaricoRequest;
	}

}
