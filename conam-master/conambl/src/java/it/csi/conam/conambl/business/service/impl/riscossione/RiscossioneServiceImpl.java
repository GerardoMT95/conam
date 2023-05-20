/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.riscossione;

import it.csi.conam.conambl.business.service.riscossione.RiscossioneService;
import it.csi.conam.conambl.business.service.riscossione.SorisService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.verbale.SoggettoVerbaleService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.SoggettoEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.SoggettoRiscossioneEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.VerbaleEntityMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.integration.specification.CnmTRiscossioneSpecification;
import it.csi.conam.conambl.integration.specification.CnmTSoggettoOrdinanzaVerbaleSpecification;
import it.csi.conam.conambl.request.riscossione.RicercaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.request.riscossione.RicercaStoricaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.request.riscossione.SalvaSoggettiRiscossioneCoattivaRequest;
import it.csi.conam.conambl.request.verbale.DatiVerbaleRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.VerbaleSearchParam;
import it.csi.conam.conambl.vo.common.SelectVO;
import it.csi.conam.conambl.vo.riscossione.InvioMassivoVO;
import it.csi.conam.conambl.vo.riscossione.SoggettiRiscossioneCoattivaVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.helpers.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author riccardo.bova
 * @date 13 mar 2019
 */
@Service
public class RiscossioneServiceImpl implements RiscossioneService {

	private static int DIM_INDIRIZZO = 30;
	private static int DIM_NOME = 20;
	private static int DIM_COGNOME = 21;
	private static int DIM_LOCALITA_FRAZIONARIA = 21;
	private static int DIM_DENO_SOCIETA = 21;
	private static int DIM_TITOLO = 21;

	@Autowired
	private SoggettoVerbaleService soggettoVerbaleService;
	@Autowired
	private CnmDNazioneRepository cmDNazioneRepository;
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmTAllegatoFieldRepository cnmTAllegatoFieldRepository;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private SoggettoRiscossioneEntityMapper soggettoRiscossioneEntityMapper;
	@Autowired
	private SoggettoEntityMapper soggettoEntityMapper;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CnmTRiscossioneRepository cnmTRiscossioneRepository;
	@Autowired
	private CnmDStatoRiscossioneRepository cnmDStatoRiscossioneRepository;
	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CnmTNotificaRepository cnmTNotificaRepository;
	@Autowired
	private CnmTFileRepository cnmTFileRepository;
	@Autowired
	private CnmDStatoFileRepository cnmDStatoFileRepository;
	@Autowired
	private CnmDTipoFileRepository cnmDTipoFileRepository;
	@Autowired
	private CnmTRecordRepository cnmTRecordRepository;
	@Autowired
	private CnmDTipoRecordRepository cnmDTipoRecordRepository;
	@Autowired
	private CnmDTipoTributoRepository cnmDTipoTributoRepository;
	@Autowired
	private CnmTRecordN2Repository cnmTRecordN2Repository;
	@Autowired
	private CnmTRecordN3Repository cnmTRecordN3Repository;
	@Autowired
	private CnmTRecordN4Repository cnmTRecordN4Repository;
	@Autowired
	private SorisService sorisService;
	@Autowired
	private CnmDComuneRepository cnmDComuneRepository;

	@Autowired
	private VerbaleEntityMapper verbaleEntityMapper;
	
	
	private static Long COUNT = new Long(100);

	//TODO LUCIO
	@Override
	public List<SoggettiRiscossioneCoattivaVO> ricercaOrdinanzaSoggetti(RicercaSoggettiRiscossioneCoattivaRequest request) {
		Long count = new Long(0);

		if (request.getDataNotificaDa() != null && request.getDataNotificaA() != null) {
			count = cnmTNotificaRepository.countByDataNotificaRisCoattiva(utilsDate.asDate(request.getDataNotificaDa()), utilsDate.asDate(request.getDataNotificaA())).longValue();
		}

		if (request.getDatiVerbale() == null) request.setDatiVerbale(new DatiVerbaleRequest());
		
		
		if (request.getDataSentenzaDa() != null && request.getDataSentenzaA() != null) {
			if (request.getStatoSentenza() == null) {
				count = cnmTAllegatoFieldRepository.countByDataSentenzaRisCoattiva(utilsDate.asDate(request.getDataSentenzaDa()), utilsDate.asDate(request.getDataSentenzaA())).longValue();
			} else {
				count = cnmTAllegatoFieldRepository.countByDataSentenzaAndIdStatoSentenzaRisCoattiva(utilsDate.asDate(request.getDataSentenzaDa()), utilsDate.asDate(request.getDataSentenzaA()),
						request.getStatoSentenza().getId()).longValue();
			}
		}

		if (count > COUNT) {
			List<SoggettiRiscossioneCoattivaVO> esito = new ArrayList<SoggettiRiscossioneCoattivaVO>();
			SoggettiRiscossioneCoattivaVO vo = new SoggettiRiscossioneCoattivaVO();
			vo.setSuperatoIlMassimo(true);
			esito.add(vo);
			return esito;
		}

		Long idStatoSentenza = request.getStatoSentenza() != null ? request.getStatoSentenza().getId() : null;
		Long idStatoOrdinanza = request.getStatoOrdinanza() != null ? request.getStatoOrdinanza().getId() : null;

		VerbaleSearchParam parametriVerbale = verbaleEntityMapper.getVerbaleParamFromRequest(request.getDatiVerbale());
		
		List<CnmROrdinanzaVerbSog> ordVerbSog = cnmROrdinanzaVerbSogRepository.findAll(
			CnmTSoggettoOrdinanzaVerbaleSpecification.findSoggettoBy(//
				request.getNumeroDeterminazione(), //
				request.getNumeroProtocolloSentenza(), //
				TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getId(), //
				false, //
				false, //
				idStatoSentenza, //
				utilsDate.asDate(request.getDataNotificaDa()), //
				utilsDate.asDate(request.getDataNotificaA()), //
				idStatoOrdinanza, //
				utilsDate.asDate(request.getDataSentenzaDa()), //
				utilsDate.asDate(request.getDataSentenzaA()), 
				"RICERCA_RISCOSSIONE_COATTIVA",
				parametriVerbale
			)
		);//

		return soggettoRiscossioneEntityMapper.mapListCnmROrdinanzaVerbSogToListSoggettiRiscossioneCoattivaVO(ordVerbSog);
	}

	@Override
	@Transactional
	public List<SoggettiRiscossioneCoattivaVO> aggiungiInListaRiscossione(List<Integer> request, UserDetails userDetails) {
		if (request == null || request.isEmpty())
			throw new IllegalArgumentException("lista non valorizzata");

		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs = (List<CnmROrdinanzaVerbSog>) cnmROrdinanzaVerbSogRepository.findAll(request);
		if (cnmROrdinanzaVerbSogs.isEmpty())
			throw new IllegalArgumentException("i soggetti non esistono");

		List<CnmTRiscossione> riscossione = cnmTRiscossioneRepository.findByCnmROrdinanzaVerbSogIn(cnmROrdinanzaVerbSogs);
		if (!riscossione.isEmpty())
			throw new SecurityException("Attenzione uno più soggetti è già in lista riscossione");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmDStatoRiscossione cnmDStatoRiscossione = cnmDStatoRiscossioneRepository.findOne(Constants.ID_STATO_RISCOSSIONE_BOZZA);

		List<CnmTRiscossione> cnmTRiscossiones = new ArrayList<>();
		for (CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog : cnmROrdinanzaVerbSogs) {
			CnmTRiscossione cnmTRiscossione = new CnmTRiscossione();
			cnmTRiscossione.setCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);
			cnmTRiscossione.setCnmDStatoRiscossione(cnmDStatoRiscossione);
			cnmTRiscossione.setCnmTUser2(cnmTUser);
			cnmTRiscossione.setDataOraInsert(now);
			cnmTRiscossiones.add(cnmTRiscossione);
		}
		cnmTRiscossioneRepository.save(cnmTRiscossiones);

		// T_FILE
		CnmDStatoFile cnmDStatoFile = cnmDStatoFileRepository.findOne(Constants.ID_STATO_FILE_INIZIALIZZATO);
		List<CnmTFile> cnmTFileList = cnmTFileRepository.findByCnmDStatoFile(cnmDStatoFile);
		CnmTFile cnmTFile = null;

		if (cnmTFileList != null && cnmTFileList.size() > 0) {
			cnmTFile = cnmTFileList.get(0);
		}

		if (cnmTFile == null) {
			cnmTFile = new CnmTFile();
			cnmTFile.setCnmDStatoFile(cnmDStatoFile);
			CnmDTipoFile cnmDTipoFile = cnmDTipoFileRepository.findOne(Constants.ID_TIPO_FILE_TRACCIATO_290);
			cnmTFile.setCnmDTipoFile(cnmDTipoFile);
			cnmTFile.setCnmTUser(cnmTUser);
			cnmTFile.setDataOraInsert(now);
			// id_riscossione
			cnmTFileRepository.save(cnmTFile);
		}

		List<SoggettiRiscossioneCoattivaVO> soggRiscossione = soggettoRiscossioneEntityMapper.mapListCnmROrdinanzaVerbSogToListSoggettiRiscossioneCoattivaVO(cnmROrdinanzaVerbSogs);
		for (SoggettiRiscossioneCoattivaVO e : soggRiscossione) {
			SoggettoVO soggVO = soggettoVerbaleService.ricercaSoggetto(e, userDetails);
			// e.setPartitaIva(soggVO.getPartitaIva());
			// e.setIndirizzoResidenza(soggVO.getIndirizzoResidenza());
			// e.setCivicoResidenza(soggVO.getCivicoResidenza());
			// e.setCap(soggVO.getCap());
			// e.setComuneResidenza(soggVO.getComuneResidenza());
			// e.setSesso(soggVO.getSesso());
			// e.setDataNascita(soggVO.getDataNascita());
			// e.setComuneNascita(soggVO.getComuneNascita());
			// e.setRagioneSociale(soggVO.getRagioneSociale());
			// e.setId(soggVO.getId());
			Integer idVerbaleSoggetto = cnmROrdinanzaVerbSogRepository.findCnmRVerbaleSoggettoByIdOrdinanzaVerbSog(e.getIdSoggettoOrdinanza());
			Integer idVerbale = cnmRVerbaleSoggettoRepository.findCnmTVerbaleByCnmRVerbaleSoggetto(idVerbaleSoggetto);
			List<SoggettoVO> coObbligati = soggettoEntityMapper
					.mapListEntityToListVO(cnmRVerbaleSoggettoRepository.findCnmTSoggettoByIdVerbaleAndIdRuoloSoggetto(idVerbale, Constants.VERBALE_SOGGETTO_RUOLO_OBBLIGATO_IN_SOLIDO_ID));
			CnmTRiscossione cnmTRiscossione = cnmTRiscossioneRepository.findByIdRiscossione(e.getIdRiscossione());

			String codicePartita = RandomStringUtils.randomAlphanumeric(14).toUpperCase();// this.generaCodice(cnmTFile);

			// T_RECORD_FILE_N2
			CnmTRecord cnmTRecord2 = new CnmTRecord();
			CnmDTipoRecord cnmDTipoRecordN2 = cnmDTipoRecordRepository.findOne(Constants.ID_TIPO_RECORD_N2);
			cnmTRecord2.setCnmDTipoRecord(cnmDTipoRecordN2);
			cnmTRecord2.setCnmTFile(cnmTFile);
			Integer ordine = 1;
			cnmTRecord2.setOrdine(ordine++);
			cnmTRecord2.setCnmTUser(cnmTUser);
			cnmTRecord2.setDataOraInsert(now);
			cnmTRecord2.setCnmTRiscossione(cnmTRiscossione);
			cnmTRecord2.setCodicePartita(codicePartita);
			cnmTRecordRepository.save(cnmTRecord2);

			// N2
			CnmTRecordN2 cnmTRecordN2 = new CnmTRecordN2();

			cnmTRecordN2.setIdentificativoSoggetto(soggVO.getCodiceFiscale() != null ? soggVO.getCodiceFiscale() : soggVO.getPartitaIva());

			if (soggVO.getResidenzaEstera()) {
				cnmTRecordN2.setLocalitaFrazione(soggVO.getDenomComuneResidenzaEstero());

				CnmDNazione cmDNazione = cmDNazioneRepository.findByIdSoggetto(soggVO.getId());
				cnmTRecordN2.setCodBelfioreComune(cmDNazione != null ? cmDNazione.getCodBelfioreNazione() : "");
				cnmTRecordN2.setIndirizzo(troncaString(soggVO.getIndirizzoResidenza(), DIM_INDIRIZZO));
				if (soggVO.getCap() != null && soggVO.getCap().length() == 5)
					cnmTRecordN2.setCap(soggVO.getCap());
				else
					cnmTRecordN2.setCap("00000");
			} else {
				cnmTRecordN2.setCodBelfioreComune(soggVO.getComuneResidenza().getCodBelfiore());
				cnmTRecordN2.setLocalitaFrazione(troncaString(soggVO.getComuneResidenza().getDenominazione(), DIM_LOCALITA_FRAZIONARIA));

				cnmTRecordN2.setIndirizzo(troncaString(soggVO.getIndirizzoResidenza(), DIM_INDIRIZZO));
				cnmTRecordN2.setCap(soggVO.getCap());
			}

			if (soggVO.getNazioneNascita() != null && soggVO.getNazioneNascitaEstera()) {
				CnmDNazione cmDNazione = cmDNazioneRepository.findByDenomNazione(soggVO.getNazioneNascita().getDenominazione());
				cnmTRecordN2.setCodBelfioreNascita(cmDNazione != null ? cmDNazione.getCodBelfioreNazione() : "");
			} else if (soggVO.getComuneNascita() != null) {
				CnmDComune cnmDComune = cnmDComuneRepository.findByIdComune(soggVO.getComuneNascita().getId());
				cnmTRecordN2.setCodBelfioreNascita(cnmDComune != null ? cnmDComune.getCodBelfioreComune() : "");
			}

			Matcher matcher = null;
			Pattern pattern = Pattern.compile("^[^0-9]*([0-9]*).*$");
			if (soggVO.getCivicoResidenza() != null) {
				matcher = pattern.matcher(soggVO.getCivicoResidenza());
				matcher.matches();
				cnmTRecordN2.setNumCivico(null != matcher.group(1) ? new BigDecimal(matcher.group(1)) : null);
				cnmTRecordN2.setLetteraNumCivico(matcher.groupCount() > 1 ? matcher.group(2) : null);
			}

			if (soggVO.getPersonaFisica()) {
				cnmTRecordN2.setSocieta(BigDecimal.ONE);
				cnmTRecordN2.setCognome(troncaString(soggVO.getCognome(), DIM_COGNOME));
				cnmTRecordN2.setNome(troncaString(soggVO.getNome(), DIM_NOME));
				cnmTRecordN2.setSesso(soggVO.getSesso());
				cnmTRecordN2.setDataNascita(soggVO.getDataNascita().format(CustomDateSerializer.FORMATTER_DATE));

			} else {
				cnmTRecordN2.setSocieta(new BigDecimal("2"));
				String ragioneSociale = StringUtils.normalizeSpace(soggVO.getRagioneSociale());
				cnmTRecordN2.setDenomSocieta(troncaString(ragioneSociale, DIM_DENO_SOCIETA));
			}
			if (!coObbligati.isEmpty())
				cnmTRecordN2.setPresenzaObbligato(Constants.OBBLIGATO_PRESENTE);
			else
				cnmTRecordN2.setPresenzaObbligato(Constants.OBBLIGATO_NON_PRESENTE);
			cnmTRecord2.setCnmTRecordN2(cnmTRecordN2);
			cnmTRecordN2.setCnmTRecord(cnmTRecord2);
			cnmTRecordN2Repository.save(cnmTRecordN2);
			// cnmTRecordRepository.save(cnmTRecord2);

			// N3
			if (cnmTRecordN2.getPresenzaObbligato().equals(Constants.OBBLIGATO_PRESENTE)) {
				CnmDTipoRecord cnmTTipoRecordN3 = cnmDTipoRecordRepository.findOne(Constants.ID_TIPO_RECORD_N3);
				for (SoggettoVO c : coObbligati) {
					// T_RECORD_FILE_N3
					CnmTRecord cnmTRecord3 = new CnmTRecord();
					cnmTRecord3.setCnmDTipoRecord(cnmTTipoRecordN3);
					cnmTRecord3.setCnmTFile(cnmTFile);
					cnmTRecord3.setOrdine(ordine++);
					cnmTRecord3.setCnmTUser(cnmTUser);
					cnmTRecord3.setDataOraInsert(now);
					cnmTRecord3.setCnmTRiscossione(cnmTRiscossione);
					cnmTRecord3.setCodicePartita(codicePartita);
					cnmTRecordRepository.save(cnmTRecord3);

					CnmTRecordN3 cnmTRecordN3 = new CnmTRecordN3();
					cnmTRecordN3.setIdentificativoSoggetto(c.getCodiceFiscale() != null ? c.getCodiceFiscale() : c.getPartitaIva());

					if (c.getResidenzaEstera()) {
						cnmTRecordN3.setLocalitaFrazione(troncaString(c.getDenomComuneResidenzaEstero(), DIM_LOCALITA_FRAZIONARIA));

						CnmDNazione cmDNazione = cmDNazioneRepository.findByIdSoggetto(c.getId());
						cnmTRecordN3.setCodBelfioreComune(cmDNazione != null ? cmDNazione.getCodBelfioreNazione() : "");
						if (c.getCap() != null && c.getCap().length() == 5)
							cnmTRecordN3.setCap(c.getCap());
						else
							cnmTRecordN3.setCap("00000");
					} else {
						cnmTRecordN3.setCodBelfioreComune(c.getComuneResidenza().getCodBelfiore());
						cnmTRecordN3.setLocalitaFrazione(troncaString(c.getComuneResidenza().getDenominazione(), DIM_LOCALITA_FRAZIONARIA));

						cnmTRecordN3.setIndirizzo(troncaString(c.getIndirizzoResidenza(), DIM_INDIRIZZO));
						cnmTRecordN3.setCap(c.getCap());
					}

					if (soggVO.getNazioneNascitaEstera() && c.getNazioneNascita() != null) {
						CnmDNazione cmDNazione = cmDNazioneRepository.findByDenomNazione(c.getNazioneNascita().getDenominazione());
						cnmTRecordN3.setCodBelfioreNascita(cmDNazione != null ? cmDNazione.getCodBelfioreNazione() : "");
					} else if (c.getComuneNascita() != null) {
						CnmDComune cnmDComune = cnmDComuneRepository.findByIdComune(c.getComuneNascita().getId());
						cnmTRecordN3.setCodBelfioreNascita(cnmDComune != null ? cnmDComune.getCodBelfioreComune() : "");
					}

					if (c.getCivicoResidenza() != null) {
						matcher = pattern.matcher(c.getCivicoResidenza());
						matcher.matches();
						cnmTRecordN3.setNumCivico(null != matcher.group(1) ? new BigDecimal(matcher.group(1)) : null);
						cnmTRecordN3.setLetteraNumCivico(matcher.groupCount() > 1 ? matcher.group(2) : null);
					}

					if (c.getPersonaFisica()) {
						cnmTRecordN3.setSocieta(BigDecimal.ONE);
						cnmTRecordN3.setCognome(troncaString(c.getCognome(), DIM_COGNOME));
						cnmTRecordN3.setNome(troncaString(c.getNome(), DIM_NOME));
						cnmTRecordN3.setSesso(c.getSesso());
						cnmTRecordN3.setDataNascita(c.getDataNascita().format(CustomDateSerializer.FORMATTER_DATE));

					} else {
						cnmTRecordN3.setSocieta(new BigDecimal("2"));
						String ragioneSociale = StringUtils.normalizeSpace(c.getRagioneSociale());
						if (ragioneSociale != null && ragioneSociale.length() > 76) {
							ragioneSociale = ragioneSociale.substring(0, 76);
						}

						cnmTRecordN3.setDenomSocieta(troncaString(ragioneSociale, DIM_DENO_SOCIETA));
					}
					cnmTRecordN3.setCnmTRecord(cnmTRecord3);
					cnmTRecord3.setCnmTRecordN3(cnmTRecordN3);
					cnmTRecordN3Repository.save(cnmTRecordN3);
					// cnmTRecordRepository.save(cnmTRecord3);
				}
			}
			// CREATE N4 ID_TIPO_TRIBUTO_IMPORTO_SANZIONE
			CnmTRecord cnmTRecord41 = new CnmTRecord();
			CnmDTipoRecord cnmDTipoRecord = cnmDTipoRecordRepository.findOne(Constants.ID_TIPO_RECORD_N4);
			cnmTRecord41.setCnmDTipoRecord(cnmDTipoRecord);
			cnmTRecord41.setCnmTFile(cnmTRecord2.getCnmTFile());
			cnmTRecord41.setCnmTRiscossione(cnmTRiscossione);
			cnmTRecord41.setCnmTUser(cnmTUser);
			cnmTRecord41.setCodicePartita(codicePartita);
			cnmTRecord41.setDataOraInsert(now);
			cnmTRecord41.setOrdine(ordine++);

			CnmTRecordN4 cnmTRecordN41 = new CnmTRecordN4();
			CnmDTipoTributo cnmDTipoTributo = cnmDTipoTributoRepository.findOne(Constants.ID_TIPO_TRIBUTO_IMPORTO_SANZIONE);
			cnmTRecordN41.setCnmDTipoTributo(cnmDTipoTributo);

			Calendar c = Calendar.getInstance();
			CnmTOrdinanza cnmTOrdinanza = cnmTRiscossione.getCnmROrdinanzaVerbSog().getCnmTOrdinanza();
			String dataDecorrenzaInteressi = null;
			Date date = null;
			if (cnmTOrdinanza.getCnmDStatoOrdinanza() != null && cnmTOrdinanza.getCnmDStatoOrdinanza().getIdStatoOrdinanza() == 2) {
				date = cnmTOrdinanza.getDataDeterminazione();
			} else {
				List<CnmTNotifica> cnmTNotifica = cnmTNotificaRepository.findByCnmTOrdinanzas(cnmTOrdinanza);
				if (cnmTNotifica != null && cnmTNotifica.size() > 0) {
					date = cnmTNotifica.get(0).getDataNotifica();
				}
			}

			if (date != null) {
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				calendar.add(Calendar.DATE, 30);
				date = calendar.getTime();
				dataDecorrenzaInteressi = format(date, "ddMMyyyy");
			}

			c.setTime(cnmTOrdinanza.getDataDeterminazione());
			cnmTRecordN41.setAnnoTributo(new BigDecimal(c.get(Calendar.YEAR)));
			cnmTRecordN41.setDataDecorrenzaInteressi(dataDecorrenzaInteressi);

			cnmTRecordN41.setImposta(e.getImportoSanzione() == null ? new BigDecimal(0) : e.getImportoSanzione());
			cnmTRecordN41.setTitolo(troncaString(cnmTOrdinanza.getNumDeterminazione(), DIM_TITOLO));
			cnmTRecordRepository.save(cnmTRecord41);
			cnmTRecordN41.setCnmTRecord(cnmTRecord41);
			cnmTRecord41.setCnmTRecordN4(cnmTRecordN41);
			cnmTRecordN4Repository.save(cnmTRecordN41);

			// CREATE N4 ID_TIPO_TRIBUTO_IMPORTO_SPESE_NOTIFICA
			CnmTRecord cnmTRecord42 = new CnmTRecord();
			cnmTRecord42.setCnmDTipoRecord(cnmDTipoRecord);
			cnmTRecord42.setCnmTFile(cnmTRecord2.getCnmTFile());
			cnmTRecord42.setCnmTRiscossione(cnmTRiscossione);
			cnmTRecord42.setCnmTUser(cnmTUser);
			cnmTRecord42.setCodicePartita(codicePartita);
			cnmTRecord42.setDataOraInsert(now);
			cnmTRecord42.setOrdine(ordine++);

			CnmTRecordN4 cnmTRecordN42 = new CnmTRecordN4();
			cnmDTipoTributo = cnmDTipoTributoRepository.findOne(Constants.ID_TIPO_TRIBUTO_IMPORTO_SPESE_NOTIFICA);
			cnmTRecordN42.setCnmDTipoTributo(cnmDTipoTributo);
			cnmTRecordN42.setAnnoTributo(new BigDecimal(c.get(Calendar.YEAR)));
			cnmTRecordN42.setImposta(e.getImportoSpeseNotifica() == null ? new BigDecimal(0) : e.getImportoSpeseNotifica());
			cnmTRecordN42.setTitolo(troncaString(cnmTOrdinanza.getNumDeterminazione(), DIM_TITOLO));

			cnmTRecordRepository.save(cnmTRecord42);
			cnmTRecordN42.setCnmTRecord(cnmTRecord42);
			cnmTRecord42.setCnmTRecordN4(cnmTRecordN42);
			cnmTRecordN4Repository.save(cnmTRecordN42);

			// CREATE N4 ID_TIPO_TRIBUTO_IMPORTO_SPESE_NOTIFICA
			CnmTRecord cnmTRecord43 = new CnmTRecord();
			cnmTRecord43.setCnmDTipoRecord(cnmDTipoRecord);
			cnmTRecord43.setCnmTFile(cnmTRecord2.getCnmTFile());
			cnmTRecord43.setCnmTRiscossione(cnmTRiscossione);
			cnmTRecord43.setCnmTUser(cnmTUser);
			cnmTRecord43.setCodicePartita(codicePartita);
			cnmTRecord43.setDataOraInsert(now);
			cnmTRecord43.setOrdine(ordine++);

			CnmTRecordN4 cnmTRecordN43 = new CnmTRecordN4();
			cnmDTipoTributo = cnmDTipoTributoRepository.findOne(Constants.ID_TIPO_TRIBUTO_IMPORTO_SPESE_LEGALI);
			cnmTRecordN43.setCnmDTipoTributo(cnmDTipoTributo);

			cnmTRecordN43.setAnnoTributo(new BigDecimal(c.get(Calendar.YEAR)));
			cnmTRecordN43.setImposta(e.getImportoSpeseLegali() == null ? new BigDecimal(0) : e.getImportoSpeseLegali());
			cnmTRecordN43.setTitolo(troncaString(cnmTOrdinanza.getNumDeterminazione(), DIM_TITOLO));

			cnmTRecordRepository.save(cnmTRecord43);
			cnmTRecordN43.setCnmTRecord(cnmTRecord43);
			cnmTRecord43.setCnmTRecordN4(cnmTRecordN43);
			cnmTRecordN4Repository.save(cnmTRecordN43);
		}
		return soggRiscossione;
	}

	private String format(Date date, String pattern) {
		String formattedDate = "";
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			formattedDate = format.format(date);
		}
		return formattedDate;
	}

	@Override
	public List<SoggettiRiscossioneCoattivaVO> getSoggettiRiscossione(boolean isBozza, Long idStato) {

		CnmDStatoRiscossione cnmDStatoRiscossione = null;
		List<CnmTRiscossione> cnmTRiscossiones = null;
		if (isBozza) {
			cnmDStatoRiscossione = new CnmDStatoRiscossione();
			cnmDStatoRiscossione.setIdStatoRiscossione(Constants.ID_STATO_FILE_INIZIALIZZATO);
			cnmDStatoRiscossione.setDescStatoRiscossione(Constants.DESCR_STATO_FILE_INIZIALIZZATO);

			cnmTRiscossiones = cnmTRiscossioneRepository.findByCnmDStatoRiscossione(cnmDStatoRiscossione);
		} else {

			if (idStato == 2 || idStato == 3) {
				cnmTRiscossiones = new ArrayList<CnmTRiscossione>();
				List<CnmTRiscossione> riscossiones = cnmTRiscossioneRepository.findByCnmTRiscossioneByStatoFile(idStato.longValue());
				Map<Integer, CnmTRiscossione> map = new HashMap<Integer, CnmTRiscossione>();
				if (riscossiones != null && riscossiones.size() > 0) {
					for (CnmTRiscossione r : riscossiones) {
						if (map.isEmpty()) {
							map.put(r.getIdRiscossione(), r);
							cnmTRiscossiones.add(r);
						} else if (!map.containsKey(r.getIdRiscossione())) {
							cnmTRiscossiones.add(r);
							map.put(r.getIdRiscossione(), r);
						}
					}
				}
			} else {
				cnmDStatoRiscossione = new CnmDStatoRiscossione();
				cnmDStatoRiscossione.setIdStatoRiscossione(idStato);
				cnmTRiscossiones = cnmTRiscossioneRepository.findByCnmDStatoRiscossione(cnmDStatoRiscossione);
			}

		}

		List<SoggettiRiscossioneCoattivaVO> soggettiRiscossioneCoattivaVO = soggettoRiscossioneEntityMapper.mapListCnmTRiscossioneToListSoggettiRiscossioneCoattivaVO(cnmTRiscossiones);
		Date dataDecorrenzaInteressi;

		if (cnmTRiscossiones != null && cnmTRiscossiones.size() > 0) {
			for (CnmTRiscossione r : cnmTRiscossiones) {
				CnmROrdinanzaVerbSog ordinanzaVerbaleSog = cnmROrdinanzaVerbSogRepository.findOne(r.getCnmROrdinanzaVerbSog().getIdOrdinanzaVerbSog());
				if (ordinanzaVerbaleSog != null && ordinanzaVerbaleSog.getCnmRVerbaleSoggetto() != null) {
					CnmRVerbaleSoggetto verbaleSoggetto = cnmRVerbaleSoggettoRepository.findOne(ordinanzaVerbaleSog.getCnmRVerbaleSoggetto().getIdVerbaleSoggetto());
					ordinanzaVerbaleSog.setCnmRVerbaleSoggetto(verbaleSoggetto);
					r.setCnmROrdinanzaVerbSog(ordinanzaVerbaleSog);
				}
			}
		}

		CnmDStatoFile cnmDStatoFile = new CnmDStatoFile();
		cnmDStatoFile.setDescStatoFile(Constants.DESCR_STATO_FILE_IN_ATTESA_DI_INVIO);
		cnmDStatoFile.setIdStatoFile(Constants.ID_STATO_FILE_IN_ATTESA_DI_INVIO);
		List<CnmTFile> cnmTFileList = cnmTFileRepository.findByCnmDStatoFile(cnmDStatoFile);
		CnmTFile cnmTFile = null;

		if (cnmTFileList != null && cnmTFileList.size() > 0) {
			cnmTFile = cnmTFileList.get(0);
		}

		for (SoggettiRiscossioneCoattivaVO s : soggettiRiscossioneCoattivaVO) {
			CnmTRiscossione cnmTRiscossione = cnmTRiscossioneRepository.findByIdRiscossione(s.getIdRiscossione());
			CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findByIdOrdinanzaVerbSog(cnmTRiscossione.getCnmROrdinanzaVerbSog().getIdOrdinanzaVerbSog());
			if (s.getImportoSanzione() == null)
				s.setImportoSanzione(BigDecimal.ZERO);
			if (s.getImportoSpeseLegali() == null)
				s.setImportoSpeseLegali(BigDecimal.ZERO);
			if (s.getImportoSpeseNotifica() == null)
				s.setImportoSpeseNotifica(BigDecimal.ZERO);
			if (s.getImportoSpeseLegali() == null)
				s.setImportoSpeseLegali(BigDecimal.ZERO);

			List<CnmTNotifica> notifiche = cnmTNotificaRepository.findByCnmTOrdinanzas(cnmTOrdinanza);
			if (notifiche != null && notifiche.size() == 1) {
				dataDecorrenzaInteressi = utilsDate.add30DaysToDate(notifiche.get(0).getDataNotifica());
				s.setDataDecorrenzaInteressi(utilsDate.asLocalDate(dataDecorrenzaInteressi));
				s.setImportoSpeseNotifica(notifiche.get(0).getImportoSpeseNotifica());
			}
			if (cnmTRiscossione.getImportoSanzione() != null)
				s.setImportoSanzione(cnmTRiscossione.getImportoSanzione());
			if (cnmTRiscossione.getImportoSpeseLegali() != null)
				s.setImportoSpeseLegali(cnmTRiscossione.getImportoSpeseLegali());
			if (cnmROrdinanzaVerbSogRepository.findValoreNumberByCnmROrdinanzaVerbSog(cnmTRiscossione.getCnmROrdinanzaVerbSog(), TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getId(),
					Constants.ID_FIELD_IMPORTO_SPESE_PROCESSUALI_SENTENZA) != null)
				s.setImportoSpeseLegali(cnmROrdinanzaVerbSogRepository.findValoreNumberByCnmROrdinanzaVerbSog(cnmTRiscossione.getCnmROrdinanzaVerbSog(), TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getId(),
						Constants.ID_FIELD_IMPORTO_SPESE_PROCESSUALI_SENTENZA));

			if (cnmTFile != null) {
				s.setEnableInvioMassivo(true);
			}

		}

		return soggettiRiscossioneCoattivaVO;
	}

	@Override
	public List<SelectVO> getstatiRiscossioneCoattiva() {
		List<SelectVO> stati = new ArrayList<SelectVO>();

		Iterable<CnmDStatoRiscossione> cnmDStatoRiscossioneList = cnmDStatoRiscossioneRepository.findAll();
		for (Iterator<CnmDStatoRiscossione> it = cnmDStatoRiscossioneList.iterator(); it.hasNext();) {
			CnmDStatoRiscossione tmp = it.next();
			if (tmp.getIdStatoRiscossione() != Constants.ID_STATO_FILE_INIZIALIZZATO) {
				SelectVO s = new SelectVO();
				s.setDenominazione(tmp.getDescStatoRiscossione());
				s.setId(tmp.getIdStatoRiscossione());

				stati.add(s);
			}
		}

		return stati;
	}

	@Override
	@Transactional
	public void deleteSoggettoRiscossione(Integer idRiscossione) {
		if (idRiscossione == null)
			throw new IllegalArgumentException("idRiscossione non valorizzata");

		CnmTRiscossione riscossione = cnmTRiscossioneRepository.findOne(idRiscossione);
		if (riscossione == null)
			throw new SecurityException("riscossione non trovato");

		if (riscossione.getCnmDStatoRiscossione().getIdStatoRiscossione() != Constants.ID_STATO_RISCOSSIONE_BOZZA)
			throw new SecurityException("id stato non valido");

		List<CnmTRecord> cnmTRecords = cnmTRecordRepository.findByCnmTRiscossione(riscossione);
		CnmTFile cnmTFile = cnmTFileRepository.findByCnmTRecords(cnmTRecords.iterator().next());

		for (CnmTRecord r : cnmTRecords) {
			if (r.getCnmTRecordN4() != null)
				cnmTRecordN4Repository.delete(r.getCnmTRecordN4());
			if (r.getCnmTRecordN3() != null)
				cnmTRecordN3Repository.delete(r.getCnmTRecordN3());
			if (r.getCnmTRecordN2() != null)
				cnmTRecordN2Repository.delete(r.getCnmTRecordN2());
		}
		cnmTRecordRepository.delete(cnmTRecords);

		List<CnmTRecord> cnmTRecordAll = cnmTRecordRepository.findByCnmTFile(cnmTFile);

		if (cnmTRecordAll.isEmpty()) {
			cnmTFileRepository.delete(cnmTFile);
		}
		cnmTRiscossioneRepository.delete(riscossione);

	}

	@Override
	@Transactional
	public SoggettiRiscossioneCoattivaVO salvaSoggettoRiscossione(SalvaSoggettiRiscossioneCoattivaRequest request, UserDetails userDetails) {
		if (request == null)
			throw new IllegalArgumentException("request non valorizzata");
		if (request.getIdRiscossione() == null)
			throw new IllegalArgumentException("getIdSoggettoOrdinanza non valorizzata");

		CnmTRiscossione riscossione = cnmTRiscossioneRepository.findOne(request.getIdRiscossione());
		if (riscossione == null)
			throw new SecurityException("riscossione non trovato");

		if (riscossione.getCnmDStatoRiscossione().getIdStatoRiscossione() != Constants.ID_STATO_RISCOSSIONE_BOZZA)
			throw new SecurityException("id stato non valido");

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());

		riscossione.setCnmTUser1(cnmTUser);
		riscossione.setDataOraUpdate(now);
		riscossione.setImportoSanzione(request.getImportoSanzione());
		riscossione.setImportoSpeseNotifica(request.getImportoSpeseNotifica());
		riscossione.setImportoSpeseLegali(request.getImportoSpeseLegali());
		riscossione = cnmTRiscossioneRepository.save(riscossione);

		List<CnmTRecord> cnmTRecords = cnmTRecordRepository.findByCnmTRiscossione(riscossione);
		for (CnmTRecord r : cnmTRecords) {
			if (r.getCnmDTipoRecord().getIdTipoRecord() == Constants.ID_TIPO_RECORD_N4) {
				CnmTRecordN4 cnmTRecordN4 = r.getCnmTRecordN4();
				if (cnmTRecordN4.getCnmDTipoTributo().getIdTipoTributo() == Constants.ID_TIPO_TRIBUTO_IMPORTO_SANZIONE) {
					cnmTRecordN4.setImposta(request.getImportoSanzione());
					cnmTRecordN4.setDataDecorrenzaInteressi(request.getDataDecorrenzaInteressi().format(CustomDateSerializer.FORMATTER_DATE));
				}
				if (cnmTRecordN4.getCnmDTipoTributo().getIdTipoTributo() == Constants.ID_TIPO_TRIBUTO_IMPORTO_SPESE_NOTIFICA) {
					cnmTRecordN4.setImposta(request.getImportoSpeseNotifica());
				}
				if (cnmTRecordN4.getCnmDTipoTributo().getIdTipoTributo() == Constants.ID_TIPO_TRIBUTO_IMPORTO_SPESE_LEGALI) {
					cnmTRecordN4.setImposta(request.getImportoSpeseLegali());
				}
				cnmTRecordN4Repository.save(cnmTRecordN4);
			}
		}
		SoggettiRiscossioneCoattivaVO sogg = soggettoRiscossioneEntityMapper.mapCnmTRiscossioneToSoggettiRiscossioneCoattivaVO(riscossione);
		sogg.setDataDecorrenzaInteressi(request.getDataDecorrenzaInteressi());
		return sogg;
	}

	@Override
	public List<SoggettiRiscossioneCoattivaVO> getSoggettiStoriciRiscossione(RicercaStoricaSoggettiRiscossioneCoattivaRequest request) {
		List<CnmTRiscossione> cnmTRiscossiones = cnmTRiscossioneRepository.findAll(CnmTRiscossioneSpecification.findBy(//
				null, //
				true, //
				request.getCodiceFiscaleGiuridico(), //
				request.getCodiceFiscaleFisico(), //
				request.getNumeroDeterminazioneOrdinanza()));
		return soggettoRiscossioneEntityMapper.mapListCnmTRiscossioneToListSoggettiRiscossioneCoattivaVO(cnmTRiscossiones);
	}

	@Override
	@Transactional
	public InvioMassivoVO invioSoggettiInRiscossione(UserDetails userDetails) {
		InvioMassivoVO invio = new InvioMassivoVO();
		Date data = Calendar.getInstance().getTime();
		String now = this.format(data, "yyMMdd");
		CnmDStatoFile fileAperto = cnmDStatoFileRepository.findOne(Constants.ID_STATO_FILE_INIZIALIZZATO);
		List<CnmTFile> cnmTFileList = cnmTFileRepository.findByCnmDStatoFile(fileAperto);
		CnmTFile cnmTFile = null;

		if (cnmTFileList != null && cnmTFileList.size() > 0) {
			cnmTFile = cnmTFileList.get(0);
		}

		if (cnmTFile == null) {
			invio.setEsito("File non trovato");
			return invio;
		}

		try {
			InputStream tracciato = sorisService.creaTracciato(cnmTFile);

			if (tracciato == null) {
				invio.setEsito("Errore durante la creazione del tracciato");
				return invio;
			}

			cnmTFile.setFileIntero(IOUtils.toString(tracciato));
			cnmTFile.setNomeFile("PP5L0290.D" + now + ".PR" + cnmTFile.getIdFile() + ".V01.txt");
			cnmTFile.setVersione("01");
		} catch (IOException e) {
			invio.setEsito("Errore durante la creazione del tracciato");
			return invio;
		}
		CnmDStatoFile fileChiuso = cnmDStatoFileRepository.findOne(Constants.ID_STATO_FILE_IN_ATTESA_DI_INVIO);
		cnmTFile.setCnmDStatoFile(fileChiuso);
		cnmTFileRepository.save(cnmTFile);

		try {
			CnmDStatoRiscossione cnmDStatoRiscossione = new CnmDStatoRiscossione();
			cnmDStatoRiscossione.setIdStatoRiscossione(Constants.ID_STATO_FILE_INIZIALIZZATO);
			cnmDStatoRiscossione.setDescStatoRiscossione(Constants.DESCR_STATO_FILE_INIZIALIZZATO);
			List<CnmTRiscossione> cnmTRiscossioneList = cnmTRiscossioneRepository.findByCnmDStatoRiscossione(cnmDStatoRiscossione);

			cnmDStatoRiscossione = new CnmDStatoRiscossione();
			cnmDStatoRiscossione.setIdStatoRiscossione(Constants.ID_STATO_FILE_IN_ATTESA_DI_INVIO);
			cnmDStatoRiscossione.setDescStatoRiscossione(Constants.DESCR_STATO_FILE_IN_ATTESA_DI_INVIO);

			if (cnmTRiscossioneList != null && cnmTRiscossioneList.size() > 0) {
				for (CnmTRiscossione r : cnmTRiscossioneList) {
					r.setCnmDStatoRiscossione(cnmDStatoRiscossione);
				}
			}

		} catch (Exception e) {
			invio.setEsito("Impossibile  aggiornare lo stato dei soggetti in riscossione ");
			return invio;
		}

		return invio;
	}

	/*private String generaCodice(CnmTFile cnmTFile) {
		String codicePartita;
		Set<String> codiciPartita = new HashSet<>(FluentIterable.from(cnmTRecordRepository.findByCnmTFile(cnmTFile)).transform(new Function<CnmTRecord, String>() {
			@Override
			public String apply(CnmTRecord e) {
				return e.getCodicePartita();
			}
		}).toSet());
		boolean found = true;
		do {
			codicePartita = RandomStringUtils.randomAlphanumeric(14).toUpperCase();
			if (!codiciPartita.contains(codicePartita)) {
				found = false;
			}
		} while (found);
		return codicePartita;
	}*/

	private String troncaString(String string, int dim) {

		if (string != null && string.length() > dim) {
			return string.substring(0, dim);
		}

		return string;
	}

}
