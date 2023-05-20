/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import it.csi.conam.conambl.business.service.ordinanza.UtilsOrdinanza;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsTraceCsiLogAuditService;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleService;
import it.csi.conam.conambl.business.service.verbale.SoggettoVerbaleService;
import it.csi.conam.conambl.business.service.verbale.StoricizzazioneVerbaleService;
import it.csi.conam.conambl.business.service.verbale.UtilsVerbale;
import it.csi.conam.conambl.business.service.verbale.VerbaleService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.entity.CnmDArticolo;
import it.csi.conam.conambl.integration.entity.CnmDComma;
import it.csi.conam.conambl.integration.entity.CnmDComune;
import it.csi.conam.conambl.integration.entity.CnmDEnte;
import it.csi.conam.conambl.integration.entity.CnmDLettera;
import it.csi.conam.conambl.integration.entity.CnmDMessaggio;
import it.csi.conam.conambl.integration.entity.CnmDStatoManuale;
import it.csi.conam.conambl.integration.entity.CnmDStatoPregresso;
import it.csi.conam.conambl.integration.entity.CnmDStatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoOrdVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoOrdVerbSogPK;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoOrdinanzaPK;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmREnteNorma;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleIllecito;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.entity.CsiLogAudit.TraceOperation;
import it.csi.conam.conambl.integration.mapper.entity.EnteEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.VerbaleEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.VerbaleSoggettoEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmDComuneRepository;
import it.csi.conam.conambl.integration.repositories.CnmDLetteraRepository;
import it.csi.conam.conambl.integration.repositories.CnmDMessaggioRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoManualeRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoPregressoRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoOrdVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmROrdinanzaVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleIllecitoRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleSoggettoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.integration.repositories.CnmTVerbaleRepository;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import it.csi.conam.conambl.vo.leggi.RiferimentiNormativiVO;
import it.csi.conam.conambl.vo.verbale.RiepilogoVerbaleVO;
import it.csi.conam.conambl.vo.verbale.VerbaleSoggettoVO;
import it.csi.conam.conambl.vo.verbale.VerbaleSoggettoVORaggruppatoPerSoggetto;
import it.csi.conam.conambl.vo.verbale.VerbaleVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.RiepilogoAllegatoVO;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class VerbaleServiceImpl implements VerbaleService {

	@Autowired
	private CnmDStatoManualeRepository cnmDStatoManualeRepository;
	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CnmDStatoVerbaleRepository cnmDStatoVerbaleRepository;
	@Autowired
	private CnmDLetteraRepository cnmDLetteraRepository;
	@Autowired
	private CnmRVerbaleIllecitoRepository cnmRVerbaleIllecitoRepository;
	@Autowired
	private StoricizzazioneVerbaleService storicizzazioneVerbaleService;
	@Autowired
	private EnteEntityMapper enteEntityMapper;
	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CnmRAllegatoOrdinanzaRepository cnmRAllegatoOrdinanzaRepository;
	@Autowired
	private CnmRAllegatoOrdVerbSogRepository cnmRAllegatoOrdVerbSogRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmRAllegatoVerbaleRepository cnmRAllegatoVerbaleRepository;
	@Autowired
	private CnmRAllegatoVerbSogRepository cnmRAllegatoVerbSogRepository;
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private SoggettoVerbaleService soggettoVerbaleService;
	@Autowired
	private AllegatoVerbaleService allegatoVerbaleService;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private VerbaleEntityMapper verbaleEntityMapper;
	@Autowired
	private UtilsVerbale utilsVerbale;
	@Autowired
	private CnmDStatoPregressoRepository cnmDStatoPregressoRepository;
	

	@Autowired
	private UtilsOrdinanza utilsOrdinanza;
	
	@Autowired
	private UtilsTraceCsiLogAuditService utilsTraceCsiLogAuditService;
	
	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;

	@Autowired
	private VerbaleSoggettoEntityMapper verbaleSoggettoEntityMapper;

	@Autowired
	private CnmDComuneRepository cnmDComuneRepository;
	
	@Override
	@Transactional
	public Integer salvaVerbale(VerbaleVO verbale, UserDetails userDetails) {
		if (verbale == null)
			throw new IllegalArgumentException("verbale is null");

		if (verbale.getEnteAccertatore() != null && !SecurityUtils.isEnteValido(verbale.getEnteAccertatore().getId()))
			throw new SecurityException("L'utente non puo accedere a questo servizio: l'id ente non e valido");

		//20210911 PP - verifica validità comuneEnte
		if(verbale.getComuneEnte() != null && verbale.getComuneEnte().getId() != null) {
	
			CnmDComune comuneS = cnmDComuneRepository.findByidComuneAndData(verbale.getComuneEnte().getId(), utilsDate.asDate(verbale.getDataOraAccertamento()));
			if(comuneS == null) {
				throw new BusinessException(ErrorCode.ERRORE_COMUNE_ENTE);
			}
		}
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmTVerbale cnmTVerbale;

		// nuovo
		if (verbale.getId() == null) {
			
			// 20210429_LC	-	Jira129: numVerbale non univoco
//			if (cnmTVerbaleRepository.findByNumVerbale(verbale.getNumero().toUpperCase()) != null) throw new BusinessException(ErrorCode.INSERIMENTO_VERBALE_NUMERO_VERBALE_ESISTENTE);

			cnmTVerbale = verbaleEntityMapper.mapVOtoEntity(verbale);
			cnmTVerbale.setDataOraInsert(now);
			cnmTVerbale.setCnmTUser2(cnmTUser);

			CnmDEnte cnmDEnte = utilsVerbale.getEnteAccertatore(verbale, userDetails);
			cnmTVerbale.setCnmDEnte(cnmDEnte);
			CnmDStatoPregresso statoPregresso = cnmDStatoPregressoRepository.findOne(verbale.isPregresso()?Constants.STATO_PREGRESSO_IN_LAVORAZIONE:Constants.STATO_PREGRESSO_NON_PRESENTE);
			cnmTVerbale.setCnmDStatoPregresso(statoPregresso);
			cnmTVerbale = cnmTVerbaleRepository.save(cnmTVerbale);
			
			// 20201124_LC traccia inserimento su CONAM fascicolo
			String operationToTrace = cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() > 1 ? TraceOperation.INSERIMENTO_FASCICOLO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_FASCICOLO.getOperation();
			utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,cnmTVerbale.getClass().getAnnotation(Table.class).name(),"id_verbale="+cnmTVerbale.getIdVerbale(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTVerbale.getNumVerbale());


			List<CnmRVerbaleIllecito> cnmRVerbaleIllecitoList = new ArrayList<>();
			if (verbale.getRiferimentiNormativi() != null) {
				for (RiferimentiNormativiVO rif : verbale.getRiferimentiNormativi()) {
					CnmRVerbaleIllecito cnmRVerbaleIllecito = utilsVerbale.getNewCnmRVerbaleIllecito(cnmTUser, cnmTVerbale, now, rif);
					cnmRVerbaleIllecitoList.add(cnmRVerbaleIllecito);
				}
				cnmRVerbaleIllecitoRepository.save(cnmRVerbaleIllecitoList);
			}

		} else {
			cnmTVerbale = cnmTVerbaleRepository.findOne(verbale.getId().intValue());
			if (cnmTVerbale == null || !cnmTVerbale.getNumVerbale().equals(verbale.getNumero().toUpperCase())) {
				throw new BusinessException(ErrorCode.MODIFICA_VERBALE_NUMERO);
			}
			if (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() != Constants.STATO_VERBALE_INCOMPLETO
					&& cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() != Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO)
				throw new SecurityException("Verbale non modificabile");

			cnmTVerbale = verbaleEntityMapper.mapVOtoEntityUpdate(verbale, cnmTVerbale);
			cnmTVerbale.setDataOraUpdate(now);
			cnmTVerbale.setCnmTUser1(cnmTUser);

			CnmDEnte cnmDEnte = utilsVerbale.getEnteAccertatore(verbale, userDetails);
			cnmTVerbale.setCnmDEnte(cnmDEnte);
			cnmTVerbale = cnmTVerbaleRepository.save(cnmTVerbale);

			if (verbale.getRiferimentiNormativi() == null || verbale.getRiferimentiNormativi().isEmpty())
				throw new SecurityException("riferimenti normativi vuoti");
			List<CnmRVerbaleIllecito> cnmRVerbaleIllecitoList = cnmRVerbaleIllecitoRepository.findByCnmTVerbale(cnmTVerbale);
			List<CnmRVerbaleIllecito> cnmRVerbaleIllecitoDelete = new ArrayList<>();
			for (CnmRVerbaleIllecito r : cnmRVerbaleIllecitoList) {
				RiferimentiNormativiVO riferimentiNormativiVO = Iterables.tryFind(verbale.getRiferimentiNormativi(), new Predicate<RiferimentiNormativiVO>() {
					public boolean apply(RiferimentiNormativiVO riferimentiNormativiVO) {	
						return riferimentiNormativiVO.getLettera().getId().equals(new Long(r.getCnmDLettera().getIdLettera()));
					}
				}).orNull();

				// Se non lo trovo nella lista del FE devo cancellare
				if (riferimentiNormativiVO == null)
					cnmRVerbaleIllecitoDelete.add(r);
				// gestisce la cancellazione e reinserimento dello stesso
				if (riferimentiNormativiVO != null && riferimentiNormativiVO.getId() == null) {
					cnmRVerbaleIllecitoDelete.add(r);
				}
			}
			if (!cnmRVerbaleIllecitoDelete.isEmpty()) {
				cnmRVerbaleIllecitoRepository.delete(cnmRVerbaleIllecitoDelete);
				cnmRVerbaleIllecitoRepository.flush();
			}

			if (verbale.getRiferimentiNormativi() != null) {
				for (RiferimentiNormativiVO rif : verbale.getRiferimentiNormativi()) {
					CnmRVerbaleIllecito cnmRVerbaleIllecito;
					if (rif.getId() != null) {
						cnmRVerbaleIllecito = cnmRVerbaleIllecitoRepository.findOne(rif.getId());
						if (cnmRVerbaleIllecito == null) {
							cnmRVerbaleIllecito = utilsVerbale.getNewCnmRVerbaleIllecito(cnmTUser, cnmTVerbale, now, rif);
							cnmRVerbaleIllecitoRepository.save(cnmRVerbaleIllecito);
						} else {
							if (cnmRVerbaleIllecito.getCnmDLettera().getIdLettera() != rif.getLettera().getId().longValue()) {
								cnmRVerbaleIllecito.setCnmDLettera(cnmDLetteraRepository.findOne(rif.getLettera().getId().intValue()));
								cnmRVerbaleIllecito.setCnmTUser1(cnmTUser);
								cnmRVerbaleIllecitoRepository.save(cnmRVerbaleIllecito);
							}
						}
					} else {
						cnmRVerbaleIllecito = utilsVerbale.getNewCnmRVerbaleIllecito(cnmTUser, cnmTVerbale, now, rif);
						cnmRVerbaleIllecitoRepository.save(cnmRVerbaleIllecito);
					}
				}
			}
		}

		return cnmTVerbale.getIdVerbale();

	}

	@Transactional
	@Override
	public void eliminaVerbale(Integer id, UserDetails userDetails) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);

		if (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() != Constants.STATO_VERBALE_INCOMPLETO
				&& cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() != Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO)
			throw new SecurityException("Il verbale non e in stato incompleto ");

		if (!SecurityUtils.isEnteValido(cnmTVerbale.getCnmDEnte().getIdEnte()))
			throw new SecurityException("non si ha i permessi per eliminare il verbale");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());

		storicizzazioneVerbaleService.storicizzaStatoVerbale(cnmTVerbale, cnmTUser);

		cnmTVerbale.setCnmDStatoVerbale(cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ELIMINATO));
		cnmTVerbale.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));
		cnmTVerbale.setCnmTUser1(cnmTUser);
		cnmTVerbale.setNumVerbale("ELIMINATO-" + cnmTVerbale.getNumVerbale());
		cnmTVerbaleRepository.save(cnmTVerbale);
	}

	@Override
	public VerbaleVO getVerbaleById(Integer id, UserDetails userDetails, boolean includeEliminati, boolean includiControlloUtenteProprietario, boolean filtraNormeScadute) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);
		SecurityUtils.verbaleSecurityView(cnmTVerbale, getEnteLeggeByCnmTVerbale(cnmTVerbale));

		if (cnmTVerbale.getCnmTUser2().getIdUser() != userDetails.getIdUser() && includiControlloUtenteProprietario)
			throw new RuntimeException("l'utente non puo accedere a questo verbale");

		if (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() != Constants.STATO_VERBALE_INCOMPLETO
				&& cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() != Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO && !includeEliminati)
			throw new SecurityException("il verbale non e nello stato corretto per essere visualizzato");

		// se il verbale e' pregresso, prendo anche i riferimenti normativi scaduti
		if(cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
			filtraNormeScadute = false;
		}
		return verbaleEntityMapper.mapEntityToVO(cnmTVerbale, filtraNormeScadute);

	}

	@Override
	public EnteVO getEnteLeggeByCnmTVerbale(CnmTVerbale cnmTVerbale) {
		List<CnmRVerbaleIllecito> cnmRVerbaleIllecitoList = cnmRVerbaleIllecitoRepository.findByCnmTVerbale(cnmTVerbale);
		if (cnmRVerbaleIllecitoList != null && !cnmRVerbaleIllecitoList.isEmpty()) {
			CnmRVerbaleIllecito cnmRVerbaleIllecito = cnmRVerbaleIllecitoList.get(0);
			CnmDLettera cnmDLettera = cnmRVerbaleIllecito.getCnmDLettera();
			CnmDComma cnmDComma = cnmDLettera.getCnmDComma();
			CnmDArticolo cnmDArticolo = cnmDComma.getCnmDArticolo();
			CnmREnteNorma cnmREnteNorma = cnmDArticolo.getCnmREnteNorma();
			return enteEntityMapper.mapEntityToVO(cnmREnteNorma.getCnmDEnte());
		}
		throw new RuntimeException("Ente non trovato , ERRORE");
	}

	private void aggiungiSoggetti(AllegatoVO a) {
		List<String> nomeCognomeRagioneSociales = new ArrayList<>();
		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findByNumDeterminazione(a.getNumeroDeterminazioneOrdinanza());
		if (cnmTOrdinanza != null) {
			CnmRAllegatoOrdinanzaPK cnmRAllegatoOrdinanzaPK = new CnmRAllegatoOrdinanzaPK();
			cnmRAllegatoOrdinanzaPK.setIdAllegato(a.getId());
			cnmRAllegatoOrdinanzaPK.setIdOrdinanza(cnmTOrdinanza.getIdOrdinanza());
			CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza = cnmRAllegatoOrdinanzaRepository.findOne(cnmRAllegatoOrdinanzaPK);
			List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
			if (cnmRAllegatoOrdinanza != null) {
				if (cnmROrdinanzaVerbSogs != null && !cnmROrdinanzaVerbSogs.isEmpty()) {
					for (CnmROrdinanzaVerbSog ovs : cnmROrdinanzaVerbSogs) {
						if (a.getTipo().getId() == TipoAllegato.BOLLETTINI_ORDINANZA_SOLLECITO.getId()) {
							if (ovs.getCnmDStatoOrdVerbSog().getIdStatoOrdVerbSog() == Constants.ID_STATO_ORDINANZA_SOGGETTO_ARCHIVIATO) {
								continue;
							}
						}
						CnmTSoggetto cnmTSoggetto = ovs.getCnmRVerbaleSoggetto().getCnmTSoggetto();
						if (cnmTSoggetto.getNome() != null) {
							nomeCognomeRagioneSociales.add(cnmTSoggetto.getCognome() + " " + cnmTSoggetto.getNome());
						} else {
							nomeCognomeRagioneSociales.add(cnmTSoggetto.getRagioneSociale());
						}
					}
				}
			} else {
				CnmRAllegatoOrdVerbSogPK cnmRAllegatoOrdVerbSogPK = new CnmRAllegatoOrdVerbSogPK();
				cnmRAllegatoOrdVerbSogPK.setIdAllegato(a.getId());
				for (CnmROrdinanzaVerbSog ovs : cnmROrdinanzaVerbSogs) {
					cnmRAllegatoOrdVerbSogPK.setIdOrdinanzaVerbSog(ovs.getIdOrdinanzaVerbSog());
					CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog = cnmRAllegatoOrdVerbSogRepository.findOne(cnmRAllegatoOrdVerbSogPK);
					if (cnmRAllegatoOrdVerbSog != null) {
						CnmTSoggetto cnmTSoggetto = ovs.getCnmRVerbaleSoggetto().getCnmTSoggetto();
						if (cnmTSoggetto.getNome() != null) {
							nomeCognomeRagioneSociales.add(cnmTSoggetto.getCognome() + " " + cnmTSoggetto.getNome());
						} else {
							nomeCognomeRagioneSociales.add(cnmTSoggetto.getRagioneSociale());
						}
					}
				}

			}
		} else {
			CnmTAllegato cnmTAllegato = cnmTAllegatoRepository.findOne(a.getId());
			CnmRAllegatoVerbale cnmRAllegatoVerbale = cnmRAllegatoVerbaleRepository.findByCnmTAllegato(cnmTAllegato);
			if (cnmRAllegatoVerbale != null) {
				List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettos = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmRAllegatoVerbale.getCnmTVerbale());
				for (CnmRVerbaleSoggetto vs : cnmRVerbaleSoggettos) {
					CnmTSoggetto cnmTSoggetto = vs.getCnmTSoggetto();
					if (cnmTSoggetto.getNome() != null) {
						nomeCognomeRagioneSociales.add(cnmTSoggetto.getCognome() + " " + cnmTSoggetto.getNome());
					} else {
						nomeCognomeRagioneSociales.add(cnmTSoggetto.getRagioneSociale());
					}
				}
			} else {
				List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs = cnmRAllegatoVerbSogRepository.findByCnmTAllegato(cnmTAllegato);
				if (cnmRAllegatoVerbSogs != null) {
					for (CnmRAllegatoVerbSog avs : cnmRAllegatoVerbSogs) {
						CnmTSoggetto cnmTSoggetto = avs.getCnmRVerbaleSoggetto().getCnmTSoggetto();
						if (cnmTSoggetto.getNome() != null) {
							nomeCognomeRagioneSociales.add(cnmTSoggetto.getCognome() + " " + cnmTSoggetto.getNome());
						} else {
							nomeCognomeRagioneSociales.add(cnmTSoggetto.getRagioneSociale());
						}
					}
				}
			}
		}
		if (!nomeCognomeRagioneSociales.isEmpty()) {
			String string = "";
			for (String s : nomeCognomeRagioneSociales) {
				string += s + " - ";
			}
			a.setNomeCognomeRagioneSocialeSoggetti(string.substring(0, string.length() - 2).trim());
		}
	}

	@Override
	public RiepilogoVerbaleVO riepilogo(Integer id, UserDetails userDetails) {
		if (id == null)
			throw new RuntimeException("verbale is null");

		RiepilogoVerbaleVO riepilogoVerbale = new RiepilogoVerbaleVO();
		riepilogoVerbale.setVerbale(getVerbaleById(id, userDetails, true, false, false));
		riepilogoVerbale.setSoggetto(soggettoVerbaleService.getSoggettiByIdVerbale(id, userDetails, false));
		RiepilogoAllegatoVO allegato = allegatoVerbaleService.getAllegatiByIdVerbale(id, userDetails, false);

		List<AllegatoVO> istruttoria = allegato.getIstruttoria();
		List<AllegatoVO> allegati = new ArrayList<AllegatoVO>();
		Map<Integer, AllegatoVO> map = new HashMap<Integer, AllegatoVO>();
		for (AllegatoVO a : istruttoria) {
			if (map.size() == 0) {
				map.put(a.getId(), a);
				this.aggiungiSoggetti(a);
				allegati.add(a);
			} else {
				if (!map.containsKey(a.getId())) {
					map.put(a.getId(), a);
					this.aggiungiSoggetti(a);
					allegati.add(a);
				}
			}
		}
		allegato.setIstruttoria(allegati);
		riepilogoVerbale.setAllegati(allegato);

		return riepilogoVerbale;
	}

	@Override
	public RiepilogoVerbaleVO riepilogoVerbaleAudizione(Integer id, UserDetails userDetails) {
		if (id == null)
			throw new RuntimeException("verbale is null");

		RiepilogoVerbaleVO riepilogoVerbale = new RiepilogoVerbaleVO();
		riepilogoVerbale.setVerbale(getVerbaleById(id, userDetails, true, false, false));
		riepilogoVerbale.setSoggetto(soggettoVerbaleService.getSoggettiByIdVerbale(id, userDetails, false));

		RiepilogoAllegatoVO allegato = allegatoVerbaleService.getAllegatiByIdVerbale(id, userDetails, false, true);

		List<AllegatoVO> verbale = allegato.getVerbale();
		List<AllegatoVO> allegati = new ArrayList<AllegatoVO>();
		Map<Integer, AllegatoVO> map = new HashMap<Integer, AllegatoVO>();
		for (AllegatoVO a : verbale) {
			if (map.size() == 0) {
				map.put(a.getId(), a);
				allegati.add(a);
			} else {
				if (!map.containsKey(a.getId())) {
					map.put(a.getId(), a);
					allegati.add(a);
				}
			}
		}

		allegato.setVerbale(allegati);

		riepilogoVerbale.setAllegati(allegato);

		return riepilogoVerbale;
	}

	private CnmTVerbale getVerbaleByIdOrdinanzaPrivate(Integer idOrdinanza) {
		if (idOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza==null");
		
		CnmTOrdinanza cnmTOrdinanza = utilsOrdinanza.validateAndGetCnmTOrdinanza(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza==null");
		
		List<CnmROrdinanzaVerbSog>  cnmROrdVerbSog = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		if (cnmROrdVerbSog == null)
			throw new IllegalArgumentException("cnmROrdVerbSog==null");

		return cnmROrdVerbSog.get(0).getCnmRVerbaleSoggetto().getCnmTVerbale();
	}

	// 20201223_LC JIRA 118
	@Override
	public VerbaleVO getVerbaleByIdOrdinanza(Integer idOrdinanza) {
		CnmTVerbale verbale = getVerbaleByIdOrdinanzaPrivate(idOrdinanza);
		return verbaleEntityMapper.mapEntityToVO(verbale);
	}

	//Gestione Stato manuale

	@Override
	@Transactional
	public CnmTVerbale salvaStatoManuale(Integer idVerbale,Long idStatoManuale, UserDetails userDetails) {
		CnmTVerbale verbale = cnmTVerbaleRepository.findOne(idVerbale);
		if (verbale != null) {
			CnmDStatoManuale statoManuale = cnmDStatoManualeRepository.findOne(idStatoManuale);
			verbale.setCnmDStatoManuale(statoManuale);
			verbale = cnmTVerbaleRepository.save(verbale);
		}
		return verbale;
	}


	@Override
	@Transactional
	public CnmTVerbale salvaNumeroProtocollo(Integer idVerbale,String numeroProtocollo, CnmTUser cnmTUser) {
		CnmTVerbale verbale = cnmTVerbaleRepository.findOne(idVerbale);
		CnmDStatoVerbale cnmDStatoVerbaleNext = null;
		if(verbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_IN_ACQUISIZIONE) {
			cnmDStatoVerbaleNext = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO);
		} else if(verbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_IN_ACQUISIZIONE_CON_PAGAMENTO) {
			cnmDStatoVerbaleNext = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO);
		} else if(verbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_IN_ACQUISIZIONE_CON_SCRITTI_DIFENSIVI) {
			cnmDStatoVerbaleNext = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI);
		} else if(verbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_IN_ARCHIVIATO_IN_AUTOTUTELA) {
			cnmDStatoVerbaleNext = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA);
		} else if(verbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_PROTOCOLLAZIONE_IN_ATTESA_VERIFICA_PAGAMENTO) {
			cnmDStatoVerbaleNext = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO);
		} else if(verbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_PROTOCOLLAZIONE_PER_MANCANZA_CF) {
			cnmDStatoVerbaleNext = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO);
		}  
		
		if (verbale != null && cnmDStatoVerbaleNext!= null) {
			Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
			verbale.setCnmDStatoVerbale(cnmDStatoVerbaleNext);
			verbale.setNumeroProtocollo(numeroProtocollo);
			verbale.setDataOraProtocollo(now);
			verbale.setDataOraUpdate(now);
			verbale.setCnmTUser1(cnmTUser);
			verbale = cnmTVerbaleRepository.save(verbale);
		}
		return verbale;
	}


	private MessageVO getMessaggioStatoManuale(CnmDStatoManuale statoManuale) {
		if (
			statoManuale != null &&
			statoManuale.getId() == Constants.ID_STATO_MANUALE_NON_DI_COMPETENZA
		) {
			CnmDMessaggio messaggio = 
				cnmDMessaggioRepository.findByCodMessaggio(
					Constants.ID_MESSAGGIO_STATO_MANUALE_NON_DI_COMPETENZA
				);
			return new MessageVO(
				messaggio.getDescMessaggio(),
				messaggio.getCnmDTipoMessaggio().getDescTipoMessaggio()
			);
		}
		return null;
	}

	public MessageVO getMessaggioManualeByIdOrdinanza(Integer idOrdinanza) {
		CnmTVerbale verbale = getVerbaleByIdOrdinanzaPrivate(idOrdinanza);
		return getMessaggioStatoManuale(verbale.getCnmDStatoManuale());
	}
	
	public MessageVO getMessaggioManualeByIdVerbale(Integer idVerbale) {
		CnmTVerbale verbale = cnmTVerbaleRepository.findOne(idVerbale);
		return getMessaggioStatoManuale(verbale.getCnmDStatoManuale());
	}

	
	public MessageVO getMessaggioManualeByIdOrdinanzaVerbaleSoggetto(Integer IdOrdinanzaVerbaleSoggetto) {
		if (IdOrdinanzaVerbaleSoggetto == null) throw new IllegalArgumentException("IdOrdinanzaVerbaleSoggetto is null");
		CnmROrdinanzaVerbSog ordinanzaVerbaleSoggetto = cnmROrdinanzaVerbSogRepository.findOne(IdOrdinanzaVerbaleSoggetto);
		if (ordinanzaVerbaleSoggetto == null) throw new IllegalArgumentException("Verbale non trovato");
		CnmTVerbale verbale = ordinanzaVerbaleSoggetto.getCnmRVerbaleSoggetto().getCnmTVerbale();
		return getMessaggioStatoManuale(verbale.getCnmDStatoManuale());
	}	
	//FINE - Gestione Stato manuale

	/*LUCIO 2021/04/21 - Gestione casi di recidività*/
	@Override
	public List<VerbaleSoggettoVO> getVerbaleSoggettoByIdSoggetto(Integer idSoggetto){
		List<CnmRVerbaleSoggetto> verbaleSoggetto = cnmRVerbaleSoggettoRepository.findVerbaleSoggettoByIdSoggetto(idSoggetto);
		return verbaleSoggettoEntityMapper.mapListEntityToListVO(verbaleSoggetto);
	}

	@Override
	public VerbaleSoggettoVORaggruppatoPerSoggetto getVerbaleSoggettoRaggruppatoPerSoggettoByIdSoggetto(
		Integer idSoggetto,
		Boolean recidivo
	) {
		List<CnmRVerbaleSoggetto> verbaleSoggetto;
		if (recidivo == null) {
			verbaleSoggetto = cnmRVerbaleSoggettoRepository.findVerbaleSoggettoByIdSoggetto(idSoggetto);
		}else if (recidivo) {
			verbaleSoggetto = cnmRVerbaleSoggettoRepository.findVerbaleSoggettoRecidivoByIdSoggetto(idSoggetto);
		}else {
			verbaleSoggetto = cnmRVerbaleSoggettoRepository.findVerbaleSoggettoNonRecidivoByIdSoggetto(idSoggetto);
		}
		return verbaleSoggettoEntityMapper.mapEntityToVORaggruppatoPerSoggetto(verbaleSoggetto);
	}

	@Override
	@Transactional
	public VerbaleSoggettoVO setRecidivoVerbaleSoggetto(
		Integer idVerbaleSoggetto,
		Boolean recidivo,
		UserDetails userDetails
	) {
		CnmRVerbaleSoggetto verbaleSoggetto = cnmRVerbaleSoggettoRepository.findOne(idVerbaleSoggetto);
		if (verbaleSoggetto != null) {
			verbaleSoggetto.setRecidivo(recidivo);
			verbaleSoggetto = cnmRVerbaleSoggettoRepository.save(verbaleSoggetto);
		}
		return verbaleSoggettoEntityMapper.mapEntityToVO(verbaleSoggetto);
	}
	/*LUCIO 2021/04/21 - FINE Gestione casi di recidività*/
}
