/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.sollecito;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;

import it.csi.conam.conambl.business.facade.EPayServiceFacade;
import it.csi.conam.conambl.business.facade.StadocServiceFacade;
import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.common.CommonBollettiniService;
import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.sollecito.AllegatoSollecitoService;
import it.csi.conam.conambl.business.service.sollecito.UtilsSollecitoService;
import it.csi.conam.conambl.business.service.util.UtilsCodeWriter;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.Report;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.TipoProtocolloAllegato;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.integration.entity.CnmCParametro;
import it.csi.conam.conambl.integration.entity.CnmDStatoSollecito;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoPianoRate;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoPianoRatePK;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoSollecito;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoSollecitoPK;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRSollecitoSoggRata;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTSollecito;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.mapper.entity.SoggettoEntityMapper;
import it.csi.conam.conambl.integration.mapper.ws.epay.EPayWsInputMapper;
import it.csi.conam.conambl.integration.repositories.CnmCParametroRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoSollecitoRepository;
import it.csi.conam.conambl.integration.repositories.CnmDTipoAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoPianoRateRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoSollecitoRepository;
import it.csi.conam.conambl.integration.repositories.CnmROrdinanzaVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRSollecitoSoggRataRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleSoggettoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTSoggettoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTSollecitoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.jasper.BollettinoJasper;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.UtilsParametro;
import it.csi.conam.conambl.util.UtilsRata;
import it.csi.conam.conambl.util.UtilsTipoAllegato;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.template.DatiTemplateVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

/**
 * @author riccardo.bova
 * @date 18 gen 2019
 */
@Service
public class AllegatoSollecitoServiceImpl implements AllegatoSollecitoService {

	@Autowired
	private CnmTSollecitoRepository cnmTSollecitoRepository;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CommonAllegatoService commonAllegatoService;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmRAllegatoSollecitoRepository cnmRAllegatoSollecitoRepository;
	@Autowired
	private CnmDStatoSollecitoRepository cnmDStatoSollecitoRepository;

	@Autowired
	private CommonBollettiniService commonBollettiniService;
	@Autowired
	private EPayServiceFacade ePayServiceFacade;
	@Autowired
	private EPayWsInputMapper ePayWsInputMapper;
	@Autowired
	private CnmCParametroRepository cnmCParametroRepository;
	@Autowired
	private SoggettoEntityMapper soggettoEntityMapper;
	@Autowired
	private UtilsCodeWriter utilsCodeWriter;

	@Autowired
	private UtilsSollecitoService utilsSollecito;
	@Autowired
	private UtilsDoqui utilsDoqui;
	@Autowired
	private CnmDTipoAllegatoRepository cnmDTipoAllegatoRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;
	@Autowired
	private CnmRAllegatoPianoRateRepository cnmRAllegatoPianoRateRepository;
	@Autowired
	private CnmRSollecitoSoggRataRepository cnmRSollecitoSoggRataRepository;

	@Autowired
	private CommonSoggettoService commonSoggettoService;
	

	private static final Logger logger = Logger.getLogger(AllegatoSollecitoServiceImpl.class);

	@Override
	@Transactional
	public CnmTAllegato salvaLetteraSollecito(Integer idSollecito, byte[] file, UserDetails userDetails) {
		CnmTSollecito cnmTSollecito = utilsSollecito.validateAndGetCnmTSollecito(idSollecito);

		if (isAllegatoSollecitoCreato(cnmTSollecito, TipoAllegato.LETTERA_SOLLECITO))
			throw new SecurityException("lettera del sollecito esistente");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());

		String nome = "Lettera_sollecito_" + cnmTSollecito.getCnmROrdinanzaVerbSog().getCnmTOrdinanza().getNumDeterminazione();
		String nomeFile = verificaNome(nome) + ".pdf";

		CnmTAllegato cnmTAllegato = salvaAllegatoSollecito(cnmTSollecito, file, cnmTUser, nomeFile, TipoAllegato.LETTERA_SOLLECITO, true, true, false);

		// aggiorno stato sollecito
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmDStatoSollecito cnmDStatoSollecito = cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_SOLLECITO_PROTOCOLLATO);
		cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecito);
		cnmTSollecito.setCnmTUser1(cnmTUser);
		cnmTSollecito.setDataOraUpdate(now);
		cnmTSollecitoRepository.save(cnmTSollecito);

		return cnmTAllegato;
	}
	
	
	// 20210401_LC lotto2scenario5
	@Override
	@Transactional
	public CnmTAllegato salvaLetteraSollecitoRate(Integer idSollecito, byte[] file, UserDetails userDetails) {
		CnmTSollecito cnmTSollecito = utilsSollecito.validateAndGetCnmTSollecito(idSollecito);

		if (isAllegatoSollecitoCreato(cnmTSollecito, TipoAllegato.LETTERA_SOLLECITO_RATE))
			throw new SecurityException("lettera del sollecito rate esistente");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());

		String nome = "Lettera_sollecito_rate_" + cnmTSollecito.getCnmROrdinanzaVerbSog().getCnmTOrdinanza().getNumDeterminazione();
		String nomeFile = verificaNome(nome) + ".pdf";

		CnmTAllegato cnmTAllegato = salvaAllegatoSollecito(cnmTSollecito, file, cnmTUser, nomeFile, TipoAllegato.LETTERA_SOLLECITO_RATE, true, true, false);

		// aggiorno stato sollecito
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmDStatoSollecito cnmDStatoSollecito = cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_SOLLECITO_PROTOCOLLATO);
		cnmTSollecito.setCnmDStatoSollecito(cnmDStatoSollecito);
		cnmTSollecito.setCnmTUser1(cnmTUser);
		cnmTSollecito.setDataOraUpdate(now);
		cnmTSollecitoRepository.save(cnmTSollecito);

		return cnmTAllegato;
	}

	@Override
	public List<DocumentoScaricatoVO> downloadLetteraSollecito(Integer idSollecito) {
		// 20200824_LC nuovo type per gestione documento multiplo
		CnmTSollecito cnmTSollecito = utilsSollecito.validateAndGetCnmTSollecito(idSollecito);
		
		TipoAllegato tipo = TipoAllegato.LETTERA_SOLLECITO;
		if (cnmTSollecito.getCnmDTipoSollecito().getIdTipoSollecito() == Constants.ID_TIPO_SOLLECITO_RATE) tipo = TipoAllegato.LETTERA_SOLLECITO_RATE;
		
		try {
			return downloadAllegatoSollecito(idSollecito, tipo);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Lettera non creata");
		}

	}

	
	@Override
	public boolean isRichiestaBollettiniInviata(CnmTSollecito cnmTSollecito) {
		return cnmTSollecito.getCodMessaggioEpay() != null;
	}

	@Override
	public boolean isAllegatoSollecitoCreato(CnmTSollecito cnmTSollecito, TipoAllegato tipo) {
		return getAllegatoSollecito(cnmTSollecito, tipo) != null ? true : false;
	}

	@Override
	public CnmTAllegato getAllegatoSollecito(CnmTSollecito cnmTSollecito, TipoAllegato tipo) {
		CnmTAllegato cnmTAllegato = null;
		List<CnmRAllegatoSollecito> cnmRAllegatoSollecitoList = cnmRAllegatoSollecitoRepository.findByCnmTSollecito(cnmTSollecito);
		if (cnmRAllegatoSollecitoList != null && !cnmRAllegatoSollecitoList.isEmpty()) {
			for (CnmRAllegatoSollecito allegato : cnmRAllegatoSollecitoList) {
				if (allegato.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == tipo.getId())
					cnmTAllegato = allegato.getCnmTAllegato();
			}
		}
		return cnmTAllegato;
	}

	private List<DocumentoScaricatoVO> downloadAllegatoSollecito(Integer idSollecito, TipoAllegato tipo) throws FileNotFoundException {
		// 20200824_LC nuovo type per gestione documento multiplo
		CnmTSollecito cnmTSollecito = utilsSollecito.validateAndGetCnmTSollecito(idSollecito);

		CnmTAllegato cnmTAllegato = getAllegatoSollecito(cnmTSollecito, tipo);

		if (cnmTAllegato == null)
			throw new FileNotFoundException("allegato sollecito non trovata");

		return commonAllegatoService.downloadAllegatoById(cnmTAllegato.getIdAllegato());
	}

	private CnmTAllegato salvaAllegatoSollecito(CnmTSollecito cnmTSollecito, byte[] file, CnmTUser cnmTUser, String nomeFile, TipoAllegato tipoAllegato, boolean protocolla,
			boolean isProtocollazioneInUscita, boolean isMaster) {
		if (cnmTSollecito == null)
			throw new IllegalArgumentException("id ==null");
		if (nomeFile == null)
			throw new IllegalArgumentException("nomeFile ==null");
		if (cnmTUser == null)
			throw new IllegalArgumentException("cnmTUser ==null");
		if (tipoAllegato == null)
			throw new IllegalArgumentException("tipoAllegato ==null");

		String tipoActa = null;
		if (tipoAllegato.getId() == TipoAllegato.LETTERA_SOLLECITO.getId() || tipoAllegato.getId() == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()) {
			tipoActa = StadocServiceFacade.TIPOLOGIA_DOC_ACTA_DOC_USCITA_SENZA_ALLEGATI_GENERERATI;
		}

		String folder = null;
		String idEntitaFruitore = null;
		TipoProtocolloAllegato tipoProtocolloAllegato = TipoProtocolloAllegato.NON_PROTOCOLLARE;
		String soggettoActa = null;
		String rootActa = null;
		List<CnmTSoggetto> cnmTSoggettoList = null;
		if (protocolla) {
			folder = utilsDoqui.createOrGetfolder(cnmTSollecito);
			idEntitaFruitore = utilsDoqui.createIdEntitaFruitore(cnmTSollecito, cnmDTipoAllegatoRepository.findOne(tipoAllegato.getId()));
			tipoProtocolloAllegato = TipoProtocolloAllegato.PROTOCOLLARE;
			soggettoActa = utilsDoqui.getSoggettoActa(cnmTSollecito);
			rootActa = utilsDoqui.getRootActa(cnmTSollecito);

			List<CnmTSollecito> cnmTSollecitoList = new ArrayList<CnmTSollecito>();
			cnmTSollecitoList.add(cnmTSollecito);
			List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTSollecitosIn(cnmTSollecitoList);
			List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmROrdinanzaVerbSogsIn(cnmROrdinanzaVerbSogList);
			cnmTSoggettoList = cnmTSoggettoRepository.findByCnmRVerbaleSoggettosIn(cnmRVerbaleSoggettoList);

		}
		CnmTAllegato cnmTAllegato = commonAllegatoService.salvaAllegato(file, nomeFile, tipoAllegato.getId(), null, cnmTUser, tipoProtocolloAllegato, folder, idEntitaFruitore, isMaster,
				isProtocollazioneInUscita, soggettoActa, rootActa, 0, 0, tipoActa, cnmTSoggettoList);

		// aggiungo alla tabella
		CnmRAllegatoSollecitoPK cnmRAllegatoSollecitoPK = new CnmRAllegatoSollecitoPK();
		cnmRAllegatoSollecitoPK.setIdAllegato(cnmTAllegato.getIdAllegato());
		cnmRAllegatoSollecitoPK.setIdSollecito(cnmTSollecito.getIdSollecito());

		CnmRAllegatoSollecito cnmRAllegatoSollecito = new CnmRAllegatoSollecito();
		cnmRAllegatoSollecito.setId(cnmRAllegatoSollecitoPK);
		cnmRAllegatoSollecito.setCnmTAllegato(cnmTAllegato);
		cnmRAllegatoSollecito.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
		cnmRAllegatoSollecito.setCnmTUser(cnmTUser);
		cnmRAllegatoSollecitoRepository.save(cnmRAllegatoSollecito);
		
		
		// 20210402_LC se SollecitoRate, allora nuove relazioni: allegato-pianorate e allegato-verbale
		if (tipoAllegato.getId() == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()) {
			
			List<CnmRSollecitoSoggRata> cnmRSollecitoSoggRataList = cnmRSollecitoSoggRataRepository.findByCnmTSollecito(cnmTSollecito);
			if (cnmRSollecitoSoggRataList == null || cnmRSollecitoSoggRataList.isEmpty()) 
				throw new SecurityException("cnmRSollecitoSoggRataList is null");	
		
			CnmRAllegatoPianoRatePK cnmRAllegatoPianoRatePK = new CnmRAllegatoPianoRatePK();
			cnmRAllegatoPianoRatePK.setIdAllegato(cnmTAllegato.getIdAllegato());
			cnmRAllegatoPianoRatePK.setIdPianoRate(cnmRSollecitoSoggRataList.get(0).getCnmTRata().getCnmTPianoRate().getIdPianoRate());

			CnmRAllegatoPianoRate cnmRAllegatoPianoRate = new CnmRAllegatoPianoRate();
			cnmRAllegatoPianoRate.setId(cnmRAllegatoPianoRatePK);
			cnmRAllegatoPianoRate.setCnmTAllegato(cnmTAllegato);
			cnmRAllegatoPianoRate.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
			cnmRAllegatoPianoRate.setCnmTUser(cnmTUser);
			cnmRAllegatoPianoRateRepository.save(cnmRAllegatoPianoRate);	
		}

		return cnmTAllegato;
	}

	@Override
	@Transactional
	public void inviaRichiestaBollettiniByIdSollecito(Integer idSollecito) {
		CnmTSollecito cnmTSollecito = utilsSollecito.validateAndGetCnmTSollecito(idSollecito);

		if (StringUtils.isNotEmpty(cnmTSollecito.getCodMessaggioEpay()))
			throw new SecurityException("Richiesta dei bollettini effettuata");

		CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmTSollecito.getCnmROrdinanzaVerbSog();

		CnmTSoggetto cnmTSoggetto = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTSoggetto();
		String codiceFiscale = StringUtils.defaultString(cnmTSoggetto.getCodiceFiscale(), cnmTSoggetto.getCodiceFiscaleGiuridico());
		String piva = cnmTSoggetto.getPartitaIva();
		cnmTSollecito.setCodPosizioneDebitoria(commonBollettiniService.generaCodicePosizioneDebitoria(StringUtils.defaultString(codiceFiscale, piva), BigDecimal.ONE, Constants.CODICE_SOLLECITO));
		cnmTSollecito.setCodMessaggioEpay(commonBollettiniService.generaCodiceMessaggioEpay(idSollecito, Constants.CODICE_SOLLECITO));

		cnmTSollecito = cnmTSollecitoRepository.save(cnmTSollecito);

		if (cnmTSollecito.getDataScadenzaRata() != null)
			ePayServiceFacade.inserisciListaDiCarico(ePayWsInputMapper.mapRataSollecitoToWsMapper(cnmTSollecito));

	}

	@Override
	public List<DocumentoScaricatoVO> dowloadBollettiniSollecito(Integer idSollecito) {
		// 20200824_LC nuovo type per gestione documento multiplo
		try {

			// 20210402_LC bolelttini sollecito rate
			CnmTSollecito cnmTSollecito = utilsSollecito.validateAndGetCnmTSollecito(idSollecito);
			List<DocumentoScaricatoVO> encodedDocs = null;
			if (cnmTSollecito.getCnmDTipoSollecito().getIdTipoSollecito() == Constants.ID_TIPO_SOLLECITO_RATE) {
				encodedDocs = downloadAllegatoSollecito(idSollecito, TipoAllegato.BOLLETTINI_ORDINANZA_SOLLECITO_RATE);
			} else {
				encodedDocs = downloadAllegatoSollecito(idSollecito, TipoAllegato.BOLLETTINI_ORDINANZA_SOLLECITO);
			}

			return encodedDocs;				

		} catch (FileNotFoundException e) {
			throw new BusinessException(ErrorCode.BOLLETTINI_NON_ANCORA_GENERATI_SOLLECITO);
		}
	}

	@Override
	public void creaBollettiniByCnmTSollecito(CnmTSollecito cnmTSollecito) {
		if (cnmTSollecito == null)
			throw new IllegalArgumentException("cnmTSollecito is empty");

		// recupero parametri
		List<CnmCParametro> cnmCParametroList = cnmCParametroRepository.findByIdParametroIn(Constants.PARAMETRI_BOLLETTINI);
		String numeroContoPostale = "";
		String oggettoPagamento = "";
		String cfEnteCreditore = "";
		String enteCreditore = "";
		String cbill = "";
		String intestatarioContoCorrentePostale = "";
		String autorizzazione = "";
		String infoEnte = "";
		String settoreEnte = "";
		
		CnmCParametro cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_NUMERO_CONTO_POSTALE)).orNull();
		if (cnmCParametro != null)
			numeroContoPostale = cnmCParametro.getValoreString();


		// 20210402_LC oggetto per sollecito rate	
		if (cnmTSollecito.getCnmDTipoSollecito().getIdTipoSollecito() == Constants.ID_TIPO_SOLLECITO_RATE) {
			cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_OGGETTO_PAGAMENTO_SOLLECITO_RATE)).orNull();
			List<CnmRSollecitoSoggRata> cnmRSollecitoSoggRataList = cnmRSollecitoSoggRataRepository.findByCnmTSollecito(cnmTSollecito);
			if (cnmCParametro != null) {
				// 20210427_LC
				CnmRAllegatoSollecito cnmRAllegatoSollecito = Iterables.tryFind(cnmRSollecitoSoggRataList.get(0).getCnmTSollecito().getCnmRAllegatoSollecitos(), UtilsTipoAllegato.findAllegatoInCnmRAllegatoSollecitoByTipoAllegato(TipoAllegato.LETTERA_SOLLECITO_RATE))
						.orNull();
				
				oggettoPagamento = String.format(cnmCParametro.getValoreString(), cnmRAllegatoSollecito!=null?cnmRAllegatoSollecito.getCnmTAllegato().getNumeroProtocollo():null, cnmTSollecito.getCnmROrdinanzaVerbSog().getCnmTOrdinanza().getNumDeterminazione());
			}
		} else {
			cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_OGGETTO_PAGAMENTO_SOLLECITO)).orNull();
			if (cnmCParametro != null)
				oggettoPagamento = cnmCParametro.getValoreString();
		}

		
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
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_INFO_ENTE)).orNull();
		if (cnmCParametro != null)
			infoEnte = cnmCParametro.getValoreString();
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_SETTORE_ENTE)).orNull();
		if (cnmCParametro != null)
			settoreEnte = cnmCParametro.getValoreString();

		List<BollettinoJasper> bollettini = new ArrayList<>();

		CnmTSoggetto cnmTSoggettoToMap = cnmTSollecito.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTSoggetto();
		SoggettoVO soggetto = soggettoEntityMapper.mapEntityToVO(cnmTSoggettoToMap);
		
		
		
		// 20201217_LC - JIRA 118
		CnmTVerbale cnmTVerbale = cnmTSollecito.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTVerbale();
		if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
			soggetto = commonSoggettoService.attachResidenzaPregressi(soggetto, cnmTSoggettoToMap, cnmTVerbale.getIdVerbale());
		}	
		
		
		
		String denominazione = soggetto.getPersonaFisica() ? soggetto.getNome() + " " + soggetto.getCognome() : soggetto.getRagioneSociale();
		String codiceFiscaleSoggetto = StringUtils.defaultIfEmpty(soggetto.getPartitaIva(), soggetto.getCodiceFiscale());

		BollettinoJasper bollettino = new BollettinoJasper();

		BigDecimal importoSollecito = cnmTSollecito.getImportoSollecito() != null ? cnmTSollecito.getImportoSollecito() : BigDecimal.ZERO;
		BigDecimal importoMaggiorazione = cnmTSollecito.getMaggiorazione() != null ? cnmTSollecito.getMaggiorazione() : BigDecimal.ZERO;
		BigDecimal totale = importoSollecito.add(importoMaggiorazione);
		String codiceAvviso = cnmTSollecito.getCodAvviso();
		String textDataMatrix = commonBollettiniService.createTextDataMatrix(codiceAvviso, numeroContoPostale, totale, cfEnteCreditore, codiceFiscaleSoggetto, denominazione, oggettoPagamento);

		bollettino.setQrcode1(utilsCodeWriter.convertCodeToBufferImage(utilsCodeWriter.generateQRCodeImage(commonBollettiniService.createTextQrCode(codiceAvviso, cfEnteCreditore, totale), 140, 140)));
		bollettino.setDataMatrix1(utilsCodeWriter.convertCodeToBufferImage(utilsCodeWriter.generateDataMatrixImage(textDataMatrix, 70, 70)));
		bollettino.setNumRata1(BigDecimal.ONE);
		bollettino.setImportoRata1(totale);
		bollettino.setDataScadenzaRata1(utilsDate.asLocalDate(cnmTSollecito.getDataScadenzaRata()));
		bollettino.setCodiceAvvisoRata1(UtilsRata.formattaCodiceAvvisoPerLaVisualizzazione(codiceAvviso));
		bollettino.setOggettoPagamento(oggettoPagamento);
		bollettino.setCfEnteCreditore(cfEnteCreditore);
		bollettino.setDenominazioneSoggetto(denominazione);
		bollettino.setEnteCreditore(enteCreditore);
		bollettino.setCbill(cbill);
		bollettino.setNumeroContoPostale(numeroContoPostale);
		bollettino.setIntestatarioContoCorrentePostale(intestatarioContoCorrentePostale);
		bollettino.setAutorizzazione(autorizzazione);
		bollettino.setCfEnteDebitore(codiceFiscaleSoggetto);
		bollettino.setIndirizzoEnteDebitore(soggetto.getIndirizzoResidenza() + ", " + soggetto.getCivicoResidenza());
		String comuneResidenza = soggetto.getComuneResidenza() != null ? soggetto.getComuneResidenza().getDenominazione() : "";
		String provinciaResidenza = soggetto.getProvinciaResidenza() != null ? soggetto.getProvinciaResidenza().getDenominazione() : "";
		if (comuneResidenza != null && provinciaResidenza != null)
			bollettino.setComuneEnteDebitore(comuneResidenza + " (" + provinciaResidenza + ")");

		bollettino.setInfoEnte(infoEnte);
		bollettino.setSettoreEnte(settoreEnte);

		bollettini.add(bollettino);

		byte[] file = commonBollettiniService.printBollettini(bollettini, Report.REPORT_STAMPA_BOLLETTINO_ORDINANZA_SOLLECITO);

		CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(Constants.CFEPAY);
		if (cnmTUser == null)
			logger.error("L'utente epay non è censito sul db ");

		// 20210402_LC sollecito rate
		if (cnmTSollecito.getCnmDTipoSollecito().getIdTipoSollecito() == Constants.ID_TIPO_SOLLECITO_RATE) {
			salvaAllegatoSollecito(cnmTSollecito, file, cnmTUser, "Bollettini_sollecito_rate.pdf", TipoAllegato.BOLLETTINI_ORDINANZA_SOLLECITO_RATE, false, false, false);
		} else {
			salvaAllegatoSollecito(cnmTSollecito, file, cnmTUser, "Bollettini_sollecito.pdf", TipoAllegato.BOLLETTINI_ORDINANZA_SOLLECITO, false, false, false);
		}
		

	}

	@Override
	public IsCreatedVO isLetteraSollecitoSaved(Integer idSollecito) {
		IsCreatedVO isCreatedVO = new IsCreatedVO();
		isCreatedVO.setCreated(false);
		CnmTSollecito cnmTSollecito = cnmTSollecitoRepository.findOne(idSollecito);
		if (cnmTSollecito != null) {
			List<CnmRAllegatoSollecito> cnmRAllegatoSollecitos = cnmRAllegatoSollecitoRepository.findByCnmTSollecito(cnmTSollecito);
			if (cnmRAllegatoSollecitos != null && !cnmRAllegatoSollecitos.isEmpty()) {
				for (CnmRAllegatoSollecito a : cnmRAllegatoSollecitos) {
					if (a.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.LETTERA_SOLLECITO.getId()
							|| a.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()) {
						isCreatedVO.setCreated(true);
					}
				}
			}
		}
		return isCreatedVO;
	}

	@Override
	public DatiTemplateVO nomeLetteraSollecito(Integer idSollecito) {
		DatiTemplateVO vo = new DatiTemplateVO();

		if (idSollecito == null)
			throw new IllegalArgumentException("idSollecito is empty");

		CnmTSollecito cnmTSollecito = utilsSollecito.validateAndGetCnmTSollecito(idSollecito);

		String nome = "Lettera_sollecito_" + cnmTSollecito.getCnmROrdinanzaVerbSog().getCnmTOrdinanza().getNumDeterminazione();
		vo.setNome(verificaNome(nome) + ".pdf");
		return vo;
	}

	// 20210401_LC lotto2scenario5
	@Override
	public DatiTemplateVO nomeLetteraSollecitoRate(Integer idSollecito) {
		DatiTemplateVO vo = new DatiTemplateVO();

		if (idSollecito == null)
			throw new IllegalArgumentException("idSollecito is empty");

		CnmTSollecito cnmTSollecito = utilsSollecito.validateAndGetCnmTSollecito(idSollecito);

		String nome = "Lettera_sollecito_rate_" + cnmTSollecito.getCnmROrdinanzaVerbSog().getCnmTOrdinanza().getNumDeterminazione();
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
