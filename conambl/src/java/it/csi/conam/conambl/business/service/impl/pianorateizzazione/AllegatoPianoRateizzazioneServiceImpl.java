/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.pianorateizzazione;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import it.csi.conam.conambl.business.facade.EPayServiceFacade;
import it.csi.conam.conambl.business.facade.StadocServiceFacade;
import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.common.CommonBollettiniService;
import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.pianorateizzazione.AllegatoPianoRateizzazioneService;
import it.csi.conam.conambl.business.service.pianorateizzazione.UtilsPianoRateizzazioneService;
import it.csi.conam.conambl.business.service.util.UtilsCodeWriter;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.common.*;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.SoggettoEntityMapper;
import it.csi.conam.conambl.integration.mapper.ws.epay.EPayWsInputMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.jasper.BollettinoJasper;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.UtilsParametro;
import it.csi.conam.conambl.util.UtilsRata;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.template.DatiTemplateVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author riccardo.bova
 * @date 18 gen 2019
 */
@Service
public class AllegatoPianoRateizzazioneServiceImpl implements AllegatoPianoRateizzazioneService {

	private static final Logger logger = Logger.getLogger(AllegatoPianoRateizzazioneServiceImpl.class);

	@Autowired
	private CnmRAllegatoPianoRateRepository cnmRAllegatoPianoRateRepository;
	@Autowired
	private CnmTPianoRateRepository cnmTPianoRateRepository;
	@Autowired
	private CommonAllegatoService commonAllegatoService;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmTRataRepository cnmTRataRepository;
	@Autowired
	private UtilsCodeWriter utilsCodeWriter;
	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;
	@Autowired
	private CnmCParametroRepository cnmCParametroRepository;
	@Autowired
	private SoggettoEntityMapper soggettoEntityMapper;
	@Autowired
	private EPayWsInputMapper ePayWsInputMapper;
	@Autowired
	private EPayServiceFacade ePayServiceFacade;
	@Autowired
	private CommonBollettiniService commonBollettiniService;
	@Autowired
	private UtilsPianoRateizzazioneService utilsPianoRateizzazione;
	@Autowired
	private CnmDStatoPianoRateRepository cnmDStatoPianoRateRepository;
	@Autowired
	private UtilsDoqui utilsDoqui;
	@Autowired
	private CnmDTipoAllegatoRepository cnmDTipoAllegatoRepository;

	@Autowired
	private CommonSoggettoService commonSoggettoService;

	@Override
	@Transactional
	public void inviaRichiestaBollettiniByIdPiano(Integer idPiano) {
		CnmTPianoRate cnmTPianoRate = utilsPianoRateizzazione.validateAndGetCnmTPianoRate(idPiano);
		List<CnmTRata> cnmTRataList = cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate);

		// creo i bollettini solo se la data scadenza è diversa da null
		if (cnmTRataList.get(0).getDataScadenza() == null)
			throw new SecurityException("non è possibile creare i bollettini per il seguente piano");
		if (StringUtils.isNotEmpty(cnmTPianoRate.getCodMessaggioEpay()))
			throw new SecurityException("Richiesta dei bollettini effettuata");

		List<CnmRSoggRata> cnmRSoggRataList = cnmRSoggRataRepository.findByCnmTRataIn(cnmTRataList);

		for (CnmRSoggRata c : cnmRSoggRataList) {
			CnmTSoggetto cnmTSoggetto = c.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTSoggetto();
			String codiceFiscale = StringUtils.defaultString(cnmTSoggetto.getCodiceFiscale(), cnmTSoggetto.getCodiceFiscaleGiuridico());
			String piva = cnmTSoggetto.getPartitaIva();
			c.setCodPosizioneDebitoria(
					commonBollettiniService.generaCodicePosizioneDebitoria(StringUtils.defaultString(codiceFiscale, piva), c.getCnmTRata().getNumeroRata(), Constants.CODICE_PIANO_RATEIZZAZIONE));
		}
		cnmRSoggRataList = cnmRSoggRataRepository.save(cnmRSoggRataList);

		cnmTPianoRate.setCodMessaggioEpay(commonBollettiniService.generaCodiceMessaggioEpay(cnmTPianoRate.getIdPianoRate(), Constants.CODICE_PIANO_RATEIZZAZIONE));
		cnmTPianoRateRepository.save(cnmTPianoRate);

		ePayServiceFacade.inserisciListaDiCarico(ePayWsInputMapper.mapRateSoggettoToWsMapper(cnmRSoggRataList));

	}

	@Override
	@Transactional
	public void creaBollettiniByCnmRSoggRata(List<CnmRSoggRata> rateList) {
		if (rateList == null || rateList.isEmpty())
			throw new IllegalArgumentException("rateList is empty");

		rateList.sort(new Comparator<CnmRSoggRata>() {
			public int compare(CnmRSoggRata cnmRSoggRata1, CnmRSoggRata cnmRSoggRata2) {
				return cnmRSoggRata1.getCnmTRata().getNumeroRata().compareTo(cnmRSoggRata2.getCnmTRata().getNumeroRata());
			}
		});
		ListMultimap<CnmROrdinanzaVerbSog, CnmRSoggRata> rateListMultimap = Multimaps.index(rateList, new Function<CnmRSoggRata, CnmROrdinanzaVerbSog>() {
			public CnmROrdinanzaVerbSog apply(CnmRSoggRata cnmRSoggRata) {
				return cnmRSoggRata.getCnmROrdinanzaVerbSog();
			}
		});

		// recupero parametri
		List<CnmCParametro> cnmCParametroList = cnmCParametroRepository.findByIdParametroIn(Constants.PARAMETRI_BOLLETTINI);
		String numeroContoPostale = "";
		String oggettoPagamento = "";
		String cfEnteCreditore = "";
		String enteCreditore = "";
		String cbill = "";
		String intestatarioContoCorrentePostale = "";
		String autorizzazione = "";
		CnmCParametro cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_NUMERO_CONTO_POSTALE)).orNull();
		if (cnmCParametro != null)
			numeroContoPostale = cnmCParametro.getValoreString();
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_OGGETTO_PAGAMENTO_RATEIZZAZIONE)).orNull();
		if (cnmCParametro != null)
			oggettoPagamento = cnmCParametro.getValoreString();
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_CODICE_FISCALE_ENTE_CREDITORE)).orNull();
		if (cnmCParametro != null)
			cfEnteCreditore = cnmCParametro.getValoreString();
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_ENTE_CREDITORE)).orNull();
		if (cnmCParametro != null)
			enteCreditore = cnmCParametro.getValoreString();
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_CBILL)).orNull();
		if (cnmCParametro != null)
			cbill = cnmCParametro.getValoreString();
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_INTESTATARIO_VERSAMENTO_POSTALE)).orNull();
		if (cnmCParametro != null)
			intestatarioContoCorrentePostale = cnmCParametro.getValoreString();
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_AUTORIZZAZIONE)).orNull();
		if (cnmCParametro != null)
			autorizzazione = cnmCParametro.getValoreString();

		List<BollettinoJasper> bollettini = new ArrayList<>();

		Set<CnmROrdinanzaVerbSog> keySet = rateListMultimap.keySet();

		
		CnmTPianoRate cnmTPianoRate = null;
		for (CnmROrdinanzaVerbSog s : keySet) {
			
			CnmTSoggetto cnmTSoggettoToMap = s.getCnmRVerbaleSoggetto().getCnmTSoggetto();
			SoggettoVO soggetto = soggettoEntityMapper.mapEntityToVO(cnmTSoggettoToMap);
			
			// 20201217_LC - JIRA 118
			CnmTVerbale cnmTVerbale = s.getCnmRVerbaleSoggetto().getCnmTVerbale();
			if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
				soggetto = commonSoggettoService.attachResidenzaPregressi(soggetto, cnmTSoggettoToMap, cnmTVerbale.getIdVerbale());
			}				


			String denominazione = soggetto.getPersonaFisica() ? soggetto.getNome() + " " + soggetto.getCognome() : soggetto.getRagioneSociale();
			String codiceFiscaleSoggetto = StringUtils.defaultIfEmpty(soggetto.getPartitaIva(), soggetto.getCodiceFiscale());
			List<CnmRSoggRata> listaRate = rateListMultimap.get(s);

			BollettinoJasper bollettino = new BollettinoJasper();
			for (int i = 0; i < listaRate.size(); i++) {
				CnmRSoggRata cnmRSoggRata = listaRate.get(i);
				CnmTRata cnmTRata = cnmRSoggRata.getCnmTRata();
				BigDecimal importo = cnmTRata.getImportoRata();
				String codiceAvviso = cnmRSoggRata.getCodAvviso();
				String textDataMatrix = commonBollettiniService.createTextDataMatrix(codiceAvviso, numeroContoPostale, importo, cfEnteCreditore, codiceFiscaleSoggetto, denominazione,
						oggettoPagamento);
				if (i % 2 != 0) {
					bollettino.setQrcode2(
							utilsCodeWriter.convertCodeToBufferImage(utilsCodeWriter.generateQRCodeImage(commonBollettiniService.createTextQrCode(codiceAvviso, cfEnteCreditore, importo), 140, 140)));
					bollettino.setDataMatrix2(utilsCodeWriter.convertCodeToBufferImage(utilsCodeWriter.generateDataMatrixImage(textDataMatrix, 70, 70)));
					bollettino.setNumRata2(cnmTRata.getNumeroRata());
					bollettino.setImportoRata2(importo);
					bollettino.setDataScadenzaRata2(utilsDate.asLocalDate(cnmTRata.getDataScadenza()));
					bollettino.setCodiceAvvisoRata2(UtilsRata.formattaCodiceAvvisoPerLaVisualizzazione(codiceAvviso));
					bollettino = new BollettinoJasper();
					continue;
				} else {
					bollettino.setQrcode1(
							utilsCodeWriter.convertCodeToBufferImage(utilsCodeWriter.generateQRCodeImage(commonBollettiniService.createTextQrCode(codiceAvviso, cfEnteCreditore, importo), 140, 140)));
					bollettino.setDataMatrix1(utilsCodeWriter.convertCodeToBufferImage(utilsCodeWriter.generateDataMatrixImage(textDataMatrix, 70, 70)));
					bollettino.setNumRata1(cnmTRata.getNumeroRata());
					bollettino.setImportoRata1(importo);
					bollettino.setDataScadenzaRata1(utilsDate.asLocalDate(cnmTRata.getDataScadenza()));
					bollettino.setCodiceAvvisoRata1(UtilsRata.formattaCodiceAvvisoPerLaVisualizzazione(codiceAvviso));
					bollettino.setOggettoPagamento(oggettoPagamento);
					bollettino.setCfEnteCreditore(cfEnteCreditore);
					bollettino.setDenominazioneSoggetto(denominazione);
					bollettino.setEnteCreditore(enteCreditore);
					bollettino.setCbill(cbill);
					bollettino.setNumeroContoPostale(numeroContoPostale);
					bollettino.setIntestatarioContoCorrentePostale(intestatarioContoCorrentePostale);
					bollettino.setAutorizzazione(autorizzazione);
					bollettini.add(bollettino);
				}
				// assegno l'id piano
				if (cnmTPianoRate == null) {
					cnmTPianoRate = cnmTRata.getCnmTPianoRate();
				}

			}
		}

		byte[] file = commonBollettiniService.printBollettini(bollettini, Report.REPORT_STAMPA_BOLLETTINI_RATEIZZAZIONE);

		CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(Constants.CFEPAY);
		if (cnmTUser == null)
			logger.error("L'utente epay non è censito sul db ");

		String nome = "Bollettini_piano_rateizzazione_" + rateList.get(0).getCnmROrdinanzaVerbSog().getCnmTOrdinanza().getNumDeterminazione();
		String nomeBollettiniPiano = verificaNome(nome) + ".pdf";
		salvaAllegatoPiano(cnmTPianoRate, file, cnmTUser, nomeBollettiniPiano, TipoAllegato.BOLLETTINI_RATEIZZAZIONE, false, false, false);

	}

	@Override
	public CnmTAllegato salvaLetteraPiano(Integer idPiano, byte[] file, UserDetails userDetails) {
		CnmTPianoRate cnmTPianoRate = utilsPianoRateizzazione.validateAndGetCnmTPianoRate(idPiano);

		if (isLetteraPianoCreata(cnmTPianoRate))
			throw new SecurityException("lettera del piano esistente");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());

		List<CnmRSoggRata> soggRateList = cnmRSoggRataRepository.findByCnmTRataIn(cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate));
		String nome = "Lettera_piano_rateizzazione_" + soggRateList.get(0).getCnmROrdinanzaVerbSog().getCnmTOrdinanza().getNumDeterminazione();
		String nomeLetteraPiano = verificaNome(nome) + ".pdf";

		CnmTAllegato cnmTAllegato = salvaAllegatoPiano(cnmTPianoRate, file, cnmTUser, nomeLetteraPiano, TipoAllegato.LETTERA_RATEIZZAZIONE, true, true, false);

		CnmDStatoPianoRate cnmDStatoPianoRate = cnmDStatoPianoRateRepository.findOne(Constants.ID_STATO_PIANO_PROTOCOLLATO);
		cnmTPianoRate.setCnmDStatoPianoRate(cnmDStatoPianoRate);
		cnmTPianoRateRepository.save(cnmTPianoRate);

		return cnmTAllegato;
	}

	private CnmTAllegato salvaAllegatoPiano(CnmTPianoRate cnmTPianoRate, byte[] file, CnmTUser cnmTUser, String nomeFile, TipoAllegato tipoAllegato, boolean protocolla,
			boolean isProtocollazioneInUscita, boolean isMaster) {
		if (cnmTPianoRate == null)
			throw new IllegalArgumentException("id ==null");
		if (nomeFile == null)
			throw new IllegalArgumentException("nomeFile ==null");
		if (cnmTUser == null)
			throw new IllegalArgumentException("cnmTUser ==null");
		if (tipoAllegato == null)
			throw new IllegalArgumentException("tipoAllegato ==null");

		String tipoActa = null;

		if (tipoAllegato.getId() == TipoAllegato.LETTERA_RATEIZZAZIONE.getId()) {
			tipoActa = StadocServiceFacade.TIPOLOGIA_DOC_ACTA_DOC_USCITA_SENZA_ALLEGATI_GENERERATI;
		}

		String folder = null;
		String idEntitaFruitore = null;
		TipoProtocolloAllegato tipoProtocolloAllegato = TipoProtocolloAllegato.NON_PROTOCOLLARE;
		String soggettoActa = null;
		String rootActa = null;
		List<CnmTSoggetto> cnmTSoggettoList = null;
		if (protocolla) {
			folder = utilsDoqui.createOrGetfolder(cnmTPianoRate);
			idEntitaFruitore = utilsDoqui.createIdEntitaFruitore(cnmTPianoRate, cnmDTipoAllegatoRepository.findOne(tipoAllegato.getId()));
			tipoProtocolloAllegato = TipoProtocolloAllegato.PROTOCOLLARE;
			soggettoActa = utilsDoqui.getSoggettoActa(cnmTPianoRate);
			rootActa = utilsDoqui.getRootActa(cnmTPianoRate);

			List<CnmTRata> cnmTRataList = cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate);
			List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmRSoggRataRepository.findDistinctCnmROrdinanzaVerbSogByCnmTRataIn(cnmTRataList);
			List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmROrdinanzaVerbSogsIn(cnmROrdinanzaVerbSogList);
			cnmTSoggettoList = cnmTSoggettoRepository.findByCnmRVerbaleSoggettosIn(cnmRVerbaleSoggettoList);

		}
		CnmTAllegato cnmTAllegato = commonAllegatoService.salvaAllegato(file, nomeFile, tipoAllegato.getId(), null, cnmTUser, tipoProtocolloAllegato, folder, idEntitaFruitore, isMaster,
				isProtocollazioneInUscita, soggettoActa, rootActa, 0, 0, tipoActa, cnmTSoggettoList);

		// aggiungo alla tabella
		CnmRAllegatoPianoRatePK cnmRAllegatoPianoRatePK = new CnmRAllegatoPianoRatePK();
		cnmRAllegatoPianoRatePK.setIdAllegato(cnmTAllegato.getIdAllegato());
		cnmRAllegatoPianoRatePK.setIdPianoRate(cnmTPianoRate.getIdPianoRate());

		CnmRAllegatoPianoRate cnmRAllegatoPianoRate = new CnmRAllegatoPianoRate();
		cnmRAllegatoPianoRate.setId(cnmRAllegatoPianoRatePK);
		cnmRAllegatoPianoRate.setCnmTAllegato(cnmTAllegato);
		cnmRAllegatoPianoRate.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
		cnmRAllegatoPianoRate.setCnmTUser(cnmTUser);
		cnmRAllegatoPianoRateRepository.save(cnmRAllegatoPianoRate);

		return cnmTAllegato;
	}

	@Override
	public List<DocumentoScaricatoVO> downloadLetteraPiano(Integer idPiano) {
		// 20200824_LC nuovo type per gestione documento multiplo
		try {
			return getAllegatoByIdPiano(idPiano, TipoAllegato.LETTERA_RATEIZZAZIONE);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("lettera del piano non trovata");
		}
	}

	@Override
	public List<DocumentoScaricatoVO> downloadBollettiniByIdPiano(Integer idPiano) {
		// 20200824_LC nuovo type per gestione documento multiplo
		try {
			// 20200825_LC
			List<DocumentoScaricatoVO> encodedDocs = getAllegatoByIdPiano(idPiano, TipoAllegato.BOLLETTINI_RATEIZZAZIONE);
			return encodedDocs;	
//			return getAllegatoByIdPiano(idPiano, TipoAllegato.BOLLETTINI_RATEIZZAZIONE);
		} catch (FileNotFoundException e) {
			throw new BusinessException(ErrorCode.BOLLETTINI_NON_ANCORA_GENERATI_RATEIZZAZIONE);
		}
	}

	private List<DocumentoScaricatoVO> getAllegatoByIdPiano(Integer idPiano, TipoAllegato tipoAllegato) throws FileNotFoundException {
		// 20200824_LC nuovo type per gestione documento multiplo
		if (idPiano == null)
			throw new IllegalArgumentException("id ==null");
		if (tipoAllegato == null)
			throw new IllegalArgumentException("tipoAllegato ==null");
		CnmTPianoRate cnmTPianoRate = cnmTPianoRateRepository.findOne(idPiano);
		if (cnmTPianoRate == null)
			throw new IllegalArgumentException("cnmTPianoRate ==null");

		Integer idAllegato = null;
		List<CnmRAllegatoPianoRate> cnmRAllegatoPianoList = cnmRAllegatoPianoRateRepository.findByCnmTPianoRate(cnmTPianoRate);
		if (cnmRAllegatoPianoList != null && !cnmRAllegatoPianoList.isEmpty()) {
			for (CnmRAllegatoPianoRate allegato : cnmRAllegatoPianoList) {
				if (allegato.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == tipoAllegato.getId())
					idAllegato = allegato.getCnmTAllegato().getIdAllegato();
			}
		}

		if (idAllegato == null)
			throw new FileNotFoundException(Long.valueOf(tipoAllegato.getId()).toString());

		return commonAllegatoService.downloadAllegatoById(idAllegato);
	}

	@Override
	public boolean isLetteraPianoCreata(CnmTPianoRate cnmTPianoRate) {
		List<CnmRAllegatoPianoRate> cnmRAllegatoPianoList = cnmRAllegatoPianoRateRepository.findByCnmTPianoRate(cnmTPianoRate);
		if (cnmRAllegatoPianoList != null && !cnmRAllegatoPianoList.isEmpty()) {
			for (CnmRAllegatoPianoRate allegato : cnmRAllegatoPianoList) {
				if (allegato.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.LETTERA_RATEIZZAZIONE.getId())
					return true;
			}
		}
		return false;
	}

	@Override
	public IsCreatedVO isLetteraPianoSaved(Integer idPiano) {
		IsCreatedVO isCreatedVO = new IsCreatedVO();
		isCreatedVO.setCreated(false);
		CnmTPianoRate cnmTPianoRate = cnmTPianoRateRepository.findOne(idPiano);
		if (cnmTPianoRate != null) {
			List<CnmRAllegatoPianoRate> cnmRAllegatoPianoRates = cnmRAllegatoPianoRateRepository.findByCnmTPianoRate(cnmTPianoRate);
			if (cnmRAllegatoPianoRates != null && !cnmRAllegatoPianoRates.isEmpty()) {
				for (CnmRAllegatoPianoRate a : cnmRAllegatoPianoRates) {
					if (a.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.LETTERA_RATEIZZAZIONE.getId()) {
						isCreatedVO.setCreated(true);
					}
				}
			}
		}
		return isCreatedVO;
	}

	@Override
	public DatiTemplateVO nomeLetteraPiano(Integer idPiano) {
		DatiTemplateVO vo = new DatiTemplateVO();

		if (idPiano == null)
			throw new IllegalArgumentException("id ==null");

		CnmTPianoRate cnmTPianoRate = utilsPianoRateizzazione.validateAndGetCnmTPianoRate(idPiano);
		if (cnmTPianoRate == null)
			throw new IllegalArgumentException("cnmTPianoRate ==null");

		List<CnmRSoggRata> soggRateList = cnmRSoggRataRepository.findByCnmTRataIn(cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate));

		String nome = "Lettera_piano_rateizzazione_" + soggRateList.get(0).getCnmROrdinanzaVerbSog().getCnmTOrdinanza().getNumDeterminazione();
		vo.setNome(verificaNome(nome) + ".pdf");

		return vo;
	}

	public String verificaNome(String nome) {
		Pattern pattern = Pattern.compile("[^A-Za-z0-9]+");
		String nomePDF = "";

		for (int i = 0; i < nome.length(); i++) {
			Matcher matcher = pattern.matcher("" + nome.charAt(i));
			if (matcher.matches()) {
				nomePDF = nomePDF + "_";
			} else {
				nomePDF = nomePDF + nome.charAt(i);
			}
		}

		return nomePDF;
	}

}
