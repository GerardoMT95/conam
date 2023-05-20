/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import it.csi.conam.conambl.business.facade.StasServFacade;
import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleSoggettoService;
import it.csi.conam.conambl.business.service.verbale.SoggettoVerbaleService;
import it.csi.conam.conambl.business.service.verbale.UtilsVerbale;
import it.csi.conam.conambl.business.service.verbale.VerbaleService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.*;
import it.csi.conam.conambl.integration.mapper.ws.stas.AnagraficaWsOutputMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.StringConamUtils;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.RuoloSoggettoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoPregressiVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.gmscore.dto.Anagrafica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class SoggettoVerbaleServiceImpl implements SoggettoVerbaleService {

	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CnmSComuneRepository cnmSComuneRepository;
	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;
	@Autowired
	private CnmTSocietaRepository cnmTSocietaRepository;
	@Autowired
	private CnmTPersonaRepository cnmTPersonaRepository;
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private CnmDRuoloSoggettoRepository cnmDRuoloSoggettoRepository;
	@Autowired
	private CnmDNazioneRepository cnmDNazioneRepository;
	@Autowired
	private CnmDComuneRepository cnmDComuneRepository;
	@Autowired
	private CnmTResidenzaRepository cnmTResidenzaRepository;
	@Autowired
	private CommonSoggettoService commonSoggettoService;
	@Autowired
	private SoggettoEntityMapper soggettoEntityMapper;
	@Autowired
	private RuoloSoggettoEntityMapper ruoloSoggettoEntityMapper;
	@Autowired
	private ResidenzaEntityMapper residenzaEntityMapper;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;
	@Autowired
	private CnmRAllegatoVerbSogRepository cnmRAllegatoVerbSogRepository;
	@Autowired
	private VerbaleService verbaleService;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmRAllegatoOrdVerbSogRepository cnmRAllegatoOrdVerbSogRepository;
	@Autowired
	private UtilsVerbale utilsVerbale;
	@Autowired
	private AllegatoVerbaleSoggettoService allegatoVerbaleSoggettoService;
	@Autowired
	private StasServFacade stasServFacade;
	@Autowired
	private AnagraficaWsOutputMapper anagraficaWsOutputMapper;
	@Autowired
	private SoggettoPregressiEntityMapper soggettoPregressiEntityMapper;

	@Autowired
	private RegioneEntityMapper regioneEntityMapper;
	@Autowired
	private ProvinciaEntityMapper provinciaEntityMapper;
	@Autowired
	private ComuneEntityMapper comuneEntityMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public SoggettoVO salvaSoggetto(Integer id, SoggettoVO soggetto, UserDetails userDetails) {
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);

		// commentato per junit
		if (!SecurityUtils.isEnteValido(cnmTVerbale.getCnmDEnte().getIdEnte()))
			throw new SecurityException("non si ha i permessi per vedere il verbale");

		if (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() != Constants.STATO_VERBALE_INCOMPLETO
				&& cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() != Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO)
			throw new SecurityException("Il verbale non è in stato completo");

		CnmTSoggetto sogDb = commonSoggettoService.getSoggettoFromDb(soggetto, false);

		// controllo soggetto gia associato
		BigDecimal idStas = soggetto.getIdStas();
		Integer idSoggettoDb = sogDb != null && sogDb.getIdSoggetto() != null ? sogDb.getIdSoggetto() : null;
		if (idStas != null || idSoggettoDb != null) {
			controllaSoggettoAssociato(cnmTVerbale, idStas, idSoggettoDb);
		}

		CnmTSoggetto cnmTSoggetto = null;
		SoggettoVO sogStas = commonSoggettoService.getSoggettoFromStas((MinSoggettoVO) soggetto, userDetails.getIdentita().toString());
		// caso censito post inserimento db su stas
		if (sogDb != null && idStas != null) {
			cnmTSoggetto = commonSoggettoService.updateSoggettoDBWithIdStas(cnmTSoggetto, cnmTUser, soggettoEntityMapper.mapEntityToVO(sogDb), sogStas, soggetto);
		} else if (idStas != null) {
			cnmTSoggetto = cnmTSoggettoRepository.findByIdStas(idStas);
			if (cnmTSoggetto == null) {
				cnmTSoggetto = new CnmTSoggetto();
				cnmTSoggetto.setIdStas(idStas);
				cnmTSoggetto.setCnmTPersona(null);
				boolean isPersonaFisica = soggetto.getPersonaFisica() != null ? soggetto.getPersonaFisica() : false;
				if (isPersonaFisica) {
					cnmTSoggetto.setCodiceFiscale(soggetto.getCodiceFiscale());
					cnmTSoggetto.setCognome(StringConamUtils.nullOrUppercase(soggetto.getCognome()));
					cnmTSoggetto.setNome(StringConamUtils.nullOrUppercase(soggetto.getNome()));
					if (soggetto.getDenomComuneNascitaEstero() != null && sogStas.getDenomComuneNascitaEstero() == null) {
						CnmTPersona cnmTPersona = new CnmTPersona();
						cnmTPersona.setDenomComuneNascitaEstero(soggetto.getDenomComuneNascitaEstero());
						if (sogStas.getNazioneNascita() == null) {
							cnmTPersona.setCnmDNazione(cnmDNazioneRepository.findByIdNazione(soggetto.getNazioneNascita().getId()));
						}
						cnmTPersona.setCnmTUser2(cnmTUser);
						cnmTPersona.setDataOraInsert(now);
						cnmTPersonaRepository.save(cnmTPersona);
						cnmTSoggetto.setCnmTPersona(cnmTPersona);
					}
					if (soggetto.getRegioneNascita().getId() != null && sogStas.getRegioneNascita() == null) {
						CnmTPersona cnmTPersona = new CnmTPersona();
						cnmTPersona.setCnmDComune(cnmDComuneRepository.findByIdComune(soggetto.getComuneNascita().getId()));
						cnmTPersona.setCnmTUser2(cnmTUser);
						cnmTPersona.setDataOraInsert(now);
						cnmTPersonaRepository.save(cnmTPersona);
						cnmTSoggetto.setCnmTPersona(cnmTPersona);
					}
				} else {
					cnmTSoggetto.setCodiceFiscaleGiuridico(soggetto.getCodiceFiscale());
					cnmTSoggetto.setPartitaIva(soggetto.getPartitaIva());
					cnmTSoggetto.setRagioneSociale(soggetto.getRagioneSociale());
				}
				cnmTSoggetto.setCnmTSocieta(null);
				cnmTSoggetto.setCnmTUser2(cnmTUser);
				cnmTSoggetto.setDataOraInsert(now);
				cnmTSoggetto = cnmTSoggettoRepository.save(cnmTSoggetto);
			}
		} else if (sogDb != null) {
			// se in update ho id stas sul db non aggiorno mai il soggetto
			if (sogDb.getIdStas() == null) {
				soggettoEntityMapper.mapVOtoUpdateEntity(soggetto, sogDb);
				CnmTPersona cnmTPersona = sogDb.getCnmTPersona();
				CnmTSocieta cnmTSocieta = sogDb.getCnmTSocieta();
				if (cnmTPersona != null) {
					cnmTPersona.setCnmTUser1(cnmTUser);
					cnmTPersona.setDataOraUpdate(now);
					if (soggetto.getNazioneNascitaEstera() != null && soggetto.getNazioneNascitaEstera()) {
						CnmDNazione cnmDNazione = cnmDNazioneRepository.findOne(soggetto.getNazioneNascita().getId());
						cnmTPersona.setCnmDNazione(cnmDNazione);
						cnmTPersona.setDenomComuneNascitaEstero(soggetto.getDenomComuneNascitaEstero());
					}
					cnmTPersona = cnmTPersonaRepository.save(cnmTPersona);
				} else if (cnmTSocieta != null) {
					cnmTSocieta.setCnmTUser1(cnmTUser);
					cnmTSocieta.setDataOraUpdate(now);
					cnmTSocietaRepository.save(cnmTSocieta);
				}
				sogDb.setCnmTPersona(cnmTPersona);
				sogDb.setCnmTSocieta(cnmTSocieta);
				sogDb.setCnmTUser1(cnmTUser);
				sogDb.setDataOraUpdate(now);
				cnmTSoggetto = cnmTSoggettoRepository.save(sogDb);
			} else
				cnmTSoggetto = sogDb;
		} else {

			// 20210423 PP - Fix in caso di nascita estera (legata alla JIRA CONAM-131)
			if (soggetto.getComuneNascita() != null && soggetto.getComuneNascita().getId() != null &&soggetto.getDataNascita() != null) {
				CnmSComune comuneS = cnmSComuneRepository.findByidComuneAndDataNascita(soggetto.getComuneNascita().getId(), utilsDate.asDate(soggetto.getDataNascita()));
				if (comuneS != null && comuneS.getCnmDProvincia() != null && comuneS.getCnmDProvincia().getIdProvincia() != soggetto.getProvinciaNascita().getId()) {
					SoggettoVO sog = new SoggettoVO();
					sog.setComuneNascitaValido(false);
					return sog;
				}
			}

			cnmTSoggetto = soggettoEntityMapper.mapVOtoEntity(soggetto);
			CnmTPersona cnmTPersona = cnmTSoggetto.getCnmTPersona();
			CnmTSocieta cnmTSocieta = cnmTSoggetto.getCnmTSocieta();
			if (cnmTPersona != null) {
				cnmTPersona.setCnmTUser2(cnmTUser);
				cnmTPersona.setDataOraInsert(now);
				if (soggetto.getNazioneNascitaEstera() != null && soggetto.getNazioneNascitaEstera()) {
					CnmDNazione cnmDNazione = cnmDNazioneRepository.findOne(soggetto.getNazioneNascita().getId());
					cnmTPersona.setCnmDNazione(cnmDNazione);
					cnmTPersona.setDenomComuneNascitaEstero(soggetto.getDenomComuneNascitaEstero());
				}
				cnmTPersona = cnmTPersonaRepository.save(cnmTPersona);
			} else if (cnmTSocieta != null) {
				cnmTSocieta.setCnmTUser2(cnmTUser);
				cnmTSocieta.setDataOraInsert(now);
				cnmTSocietaRepository.save(cnmTSocieta);
			}
			cnmTSoggetto.setIdStas(idStas);
			cnmTSoggetto.setCnmTPersona(cnmTPersona);
			cnmTSoggetto.setCnmTSocieta(cnmTSocieta);
			cnmTSoggetto.setCnmTUser2(cnmTUser);
			cnmTSoggetto.setDataOraInsert(now);
			cnmTSoggetto = cnmTSoggettoRepository.save(cnmTSoggetto);
		}

		CnmRVerbaleSoggetto cnmRVerbaleSoggetto = new CnmRVerbaleSoggetto();
		cnmRVerbaleSoggetto.setCnmTVerbale(cnmTVerbale);
		CnmDRuoloSoggetto cnmDRuoloSoggetto = cnmDRuoloSoggettoRepository.findOne(soggetto.getRuolo().getId());
		cnmRVerbaleSoggetto.setCnmDRuoloSoggetto(cnmDRuoloSoggetto);
		cnmRVerbaleSoggetto.setCnmTUser(cnmTUser);
		cnmRVerbaleSoggetto.setCnmTSoggetto(cnmTSoggetto);
		cnmRVerbaleSoggetto.setNote(soggetto.getNoteSoggetto());
		cnmRVerbaleSoggetto.setDataOraInsert(now);
		cnmRVerbaleSoggetto = cnmRVerbaleSoggettoRepository.save(cnmRVerbaleSoggetto);

		CnmTResidenza cnmTResidenza = cnmTResidenzaRepository.findByCnmTSoggetto(cnmTSoggetto);
		if (cnmTResidenza == null) {
			cnmTResidenza = new CnmTResidenza();
			cnmTResidenza.setCnmTUser2(cnmTUser);
			cnmTResidenza.setDataOraInsert(now);
		} else {
			cnmTResidenza.setCnmTUser1(cnmTUser);
			cnmTResidenza.setDataOraUpdate(now);
		}

		// 20210610_LC Jira 147 (non crea residenza se nazione estera è null)
		if (soggetto != null && soggetto.getResidenzaEstera() && soggetto.getNazioneResidenza() != null && soggetto.getNazioneResidenza().getId() != null) {
			cnmTResidenza.setCnmDNazione(cnmDNazioneRepository.findOne(soggetto.getNazioneResidenza().getId()));
			cnmTResidenza.setDenomComuneResidenzaEstero(soggetto.getDenomComuneResidenzaEstero());
			cnmTResidenza.setIndirizzoResidenza(soggetto.getIndirizzoResidenza());
			cnmTResidenza.setNumeroCivicoResidenza(soggetto.getCivicoResidenza());
			cnmTResidenza.setCapResidenza(soggetto.getCap());
			cnmTResidenza.setCnmDComune(null);
			cnmTResidenza.setResidenzaEstera(true);
			cnmTResidenza.setCnmTSoggetto(cnmTSoggetto);
			cnmTResidenzaRepository.save(cnmTResidenza);
		} else if (idStas == null && soggetto != null && soggetto.getComuneResidenza() != null && soggetto.getComuneResidenza().getId() != null) {
			cnmTResidenza.setCnmDComune(cnmDComuneRepository.findOne(soggetto.getComuneResidenza().getId()));
			cnmTResidenza.setIndirizzoResidenza(soggetto.getIndirizzoResidenza());
			cnmTResidenza.setNumeroCivicoResidenza(soggetto.getCivicoResidenza());
			cnmTResidenza.setCapResidenza(soggetto.getCap());
			cnmTResidenza.setCnmDNazione(null);
			cnmTResidenza.setDenomComuneResidenzaEstero(null);
			cnmTResidenza.setResidenzaEstera(false);
			cnmTResidenza.setCnmTSoggetto(cnmTSoggetto);
			cnmTResidenzaRepository.save(cnmTResidenza);
		}
		// 20210421_LC Jira 131
		// se residenza.comune.id è NULL, non viene creata una nuova cnmTResidenza legata al soggetto

		SoggettoVO sog = soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);
		sog.setIdSoggettoVerbale(cnmRVerbaleSoggetto.getIdVerbaleSoggetto());
		sog.setRuolo(ruoloSoggettoEntityMapper.mapEntityToVO(cnmDRuoloSoggetto));
		sog.setNoteSoggetto(soggetto.getNoteSoggetto());
		if (soggetto.getNazioneNascitaEstera() != null && soggetto.getNazioneNascitaEstera()) {
			sog.setNazioneNascita(soggetto.getNazioneNascita());
			sog.setDenomComuneNascitaEstero(soggetto.getDenomComuneNascitaEstero());
		}
		return sog;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public SoggettoVO salvaSoggettoPregressi(Integer id, SoggettoVO soggetto, UserDetails userDetails) {
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);

		// commentato per junit
		if (!SecurityUtils.isEnteValido(cnmTVerbale.getCnmDEnte().getIdEnte()))
			throw new SecurityException("non si ha i permessi per vedere il verbale");

		if (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() != Constants.STATO_VERBALE_INCOMPLETO
				&& cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() != Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO)
			throw new SecurityException("Il verbale non è in stato completo");

		CnmTSoggetto sogDb = commonSoggettoService.getSoggettoFromDb(soggetto, false);

		// controllo soggetto gia associato
		BigDecimal idStas = soggetto.getIdStas();
		Integer idSoggettoDb = sogDb != null && sogDb.getIdSoggetto() != null ? sogDb.getIdSoggetto() : null;
		if (idStas != null || idSoggettoDb != null) {
			controllaSoggettoAssociato(cnmTVerbale, idStas, idSoggettoDb);
		}

		CnmTSoggetto cnmTSoggetto = null;
		SoggettoVO sogStas = commonSoggettoService.getSoggettoFromStas((MinSoggettoVO) soggetto, userDetails.getIdentita().toString());
		// caso censito post inserimento db su stas
		if (sogDb != null && idStas != null) {
			cnmTSoggetto = commonSoggettoService.updateSoggettoDBWithIdStas(cnmTSoggetto, cnmTUser, soggettoEntityMapper.mapEntityToVO(sogDb), sogStas, soggetto);
		} else if (idStas != null) {
			cnmTSoggetto = cnmTSoggettoRepository.findByIdStas(idStas);
			if (cnmTSoggetto == null) {
				cnmTSoggetto = new CnmTSoggetto();
				cnmTSoggetto.setIdStas(idStas);
				cnmTSoggetto.setCnmTPersona(null);
				boolean isPersonaFisica = soggetto.getPersonaFisica() != null ? soggetto.getPersonaFisica() : false;
				if (isPersonaFisica) {
					cnmTSoggetto.setCodiceFiscale(soggetto.getCodiceFiscale());
					cnmTSoggetto.setCognome(StringConamUtils.nullOrUppercase(soggetto.getCognome()));
					cnmTSoggetto.setNome(StringConamUtils.nullOrUppercase(soggetto.getNome()));
					if (soggetto.getDenomComuneNascitaEstero() != null && sogStas.getDenomComuneNascitaEstero() == null) {
						CnmTPersona cnmTPersona = new CnmTPersona();
						cnmTPersona.setDenomComuneNascitaEstero(soggetto.getDenomComuneNascitaEstero());
						if (sogStas.getNazioneNascita() == null && soggetto.getNazioneNascita().getId() != null) {
							cnmTPersona.setCnmDNazione(cnmDNazioneRepository.findByIdNazione(soggetto.getNazioneNascita().getId()));
						}
						cnmTPersona.setCnmTUser2(cnmTUser);
						cnmTPersona.setDataOraInsert(now);
						cnmTPersonaRepository.save(cnmTPersona);
						cnmTSoggetto.setCnmTPersona(cnmTPersona);
					}
					if (soggetto.getRegioneNascita().getId() != null && sogStas.getRegioneNascita() == null && soggetto.getComuneNascita() != null && soggetto.getComuneNascita().getId() != null) {
						CnmTPersona cnmTPersona = new CnmTPersona();
						cnmTPersona.setCnmDComune(cnmDComuneRepository.findByIdComune(soggetto.getComuneNascita().getId()));
						cnmTPersona.setCnmTUser2(cnmTUser);
						cnmTPersona.setDataOraInsert(now);
						cnmTPersonaRepository.save(cnmTPersona);
						cnmTSoggetto.setCnmTPersona(cnmTPersona);
					}
				} else {
					cnmTSoggetto.setCodiceFiscaleGiuridico(soggetto.getCodiceFiscale());
					cnmTSoggetto.setPartitaIva(soggetto.getPartitaIva());
					cnmTSoggetto.setRagioneSociale(soggetto.getRagioneSociale());
				}
				cnmTSoggetto.setCnmTSocieta(null);
				cnmTSoggetto.setCnmTUser2(cnmTUser);
				cnmTSoggetto.setDataOraInsert(now);
				cnmTSoggetto = cnmTSoggettoRepository.save(cnmTSoggetto);
			}
		} else if (sogDb != null) {
			// se in update ho id stas sul db non aggiorno mai il soggetto
			if (sogDb.getIdStas() == null) {
				soggettoEntityMapper.mapVOtoUpdateEntity(soggetto, sogDb);
				CnmTPersona cnmTPersona = sogDb.getCnmTPersona();
				CnmTSocieta cnmTSocieta = sogDb.getCnmTSocieta();
				if (cnmTPersona != null) {
					cnmTPersona.setCnmTUser1(cnmTUser);
					cnmTPersona.setDataOraUpdate(now);
					if (soggetto.getNazioneNascitaEstera() != null && soggetto.getNazioneNascitaEstera() && soggetto.getNazioneNascita() != null && soggetto.getNazioneNascita().getId() != null) {
						CnmDNazione cnmDNazione = cnmDNazioneRepository.findOne(soggetto.getNazioneNascita().getId());
						cnmTPersona.setCnmDNazione(cnmDNazione);
						cnmTPersona.setDenomComuneNascitaEstero(soggetto.getDenomComuneNascitaEstero());
					}
					cnmTPersona = cnmTPersonaRepository.save(cnmTPersona);
				} else if (cnmTSocieta != null) {
					cnmTSocieta.setCnmTUser1(cnmTUser);
					cnmTSocieta.setDataOraUpdate(now);
					cnmTSocietaRepository.save(cnmTSocieta);
				}
				sogDb.setCnmTPersona(cnmTPersona);
				sogDb.setCnmTSocieta(cnmTSocieta);
				sogDb.setCnmTUser1(cnmTUser);
				sogDb.setDataOraUpdate(now);
				cnmTSoggetto = cnmTSoggettoRepository.save(sogDb);
			} else
				cnmTSoggetto = sogDb;
		} else {

			if (soggetto.getComuneNascita() != null && soggetto.getComuneNascita().getId() != null && soggetto.getDataNascita() != null) {
				CnmSComune comuneS = cnmSComuneRepository.findByidComuneAndDataNascita(soggetto.getComuneNascita().getId(), utilsDate.asDate(soggetto.getDataNascita()));
				if (comuneS != null && comuneS.getCnmDProvincia() != null && comuneS.getCnmDProvincia().getIdProvincia() != soggetto.getProvinciaNascita().getId()) {
					SoggettoVO sog = new SoggettoVO();
					sog.setComuneNascitaValido(false);
					return sog;
				}
			}

			cnmTSoggetto = soggettoEntityMapper.mapVOtoEntity(soggetto);
			CnmTPersona cnmTPersona = cnmTSoggetto.getCnmTPersona();
			CnmTSocieta cnmTSocieta = cnmTSoggetto.getCnmTSocieta();
			if (cnmTPersona != null) {
				cnmTPersona.setCnmTUser2(cnmTUser);
				cnmTPersona.setDataOraInsert(now);
				if (soggetto.getNazioneNascitaEstera() != null && soggetto.getNazioneNascitaEstera() && soggetto.getNazioneNascita() != null && soggetto.getNazioneNascita().getId() != null) {
					CnmDNazione cnmDNazione = cnmDNazioneRepository.findOne(soggetto.getNazioneNascita().getId());
					cnmTPersona.setCnmDNazione(cnmDNazione);
					cnmTPersona.setDenomComuneNascitaEstero(soggetto.getDenomComuneNascitaEstero());
				}
				cnmTPersona = cnmTPersonaRepository.save(cnmTPersona);
			} else if (cnmTSocieta != null) {
				cnmTSocieta.setCnmTUser2(cnmTUser);
				cnmTSocieta.setDataOraInsert(now);
				cnmTSocietaRepository.save(cnmTSocieta);
			}
			cnmTSoggetto.setIdStas(idStas);
			cnmTSoggetto.setCnmTPersona(cnmTPersona);
			cnmTSoggetto.setCnmTSocieta(cnmTSocieta);
			cnmTSoggetto.setCnmTUser2(cnmTUser);
			cnmTSoggetto.setDataOraInsert(now);
			cnmTSoggetto = cnmTSoggettoRepository.save(cnmTSoggetto);
		}

		CnmRVerbaleSoggetto cnmRVerbaleSoggetto = new CnmRVerbaleSoggetto();
		cnmRVerbaleSoggetto.setCnmTVerbale(cnmTVerbale);
		CnmDRuoloSoggetto cnmDRuoloSoggetto = cnmDRuoloSoggettoRepository.findOne(soggetto.getRuolo().getId());
		cnmRVerbaleSoggetto.setCnmDRuoloSoggetto(cnmDRuoloSoggetto);
		cnmRVerbaleSoggetto.setCnmTUser(cnmTUser);
		cnmRVerbaleSoggetto.setCnmTSoggetto(cnmTSoggetto);
		cnmRVerbaleSoggetto.setNote(soggetto.getNoteSoggetto());
		cnmRVerbaleSoggetto.setDataOraInsert(now);
		cnmRVerbaleSoggetto = cnmRVerbaleSoggettoRepository.save(cnmRVerbaleSoggetto);

		// 20200914 - PP - creo nuova residenza legata al verbale
//		CnmTResidenza cnmTResidenza = new CnmTResidenza();
//		cnmTResidenza.setCnmTUser2(cnmTUser);
//		cnmTResidenza.setDataOraInsert(now);
//
//		cnmTResidenza.setCnmDNazione(null);
//
//		if (soggetto.getResidenzaEstera()) {
//			cnmTResidenza.setResidenzaEstera(true);
//			cnmTResidenza.setDenomComuneResidenzaEstero(soggetto.getDenomComuneResidenzaEstero());
//		}else {
//			cnmTResidenza.setResidenzaEstera(false);			
//		}
//		cnmTResidenza.setIndirizzoResidenza(soggetto.getIndirizzoResidenza());
//		cnmTResidenza.setNumeroCivicoResidenza(soggetto.getCivicoResidenza());
//		cnmTResidenza.setCapResidenza(soggetto.getCap());
//		
//		// 20210226_LC Jira 122 evita errore per id comune null (non dovrebbe mai arrivarci)
//		if(soggetto.getComuneResidenza().getId() != null) {
//			cnmTResidenza.setCnmDComune(cnmDComuneRepository.findOne(soggetto.getComuneResidenza().getId()));
//		}
//		cnmTResidenza.setCnmTSoggetto(cnmTSoggetto);
//		cnmTResidenza.setIdVerbale(id);
//		cnmTResidenzaRepository.save(cnmTResidenza);
		
		// 20210611_LC Jira 147 replica flusso non pregresso
		CnmTResidenza cnmTResidenza = cnmTResidenzaRepository.findByCnmTSoggetto(cnmTSoggetto);
		if (cnmTResidenza == null) {
			cnmTResidenza = new CnmTResidenza();
			cnmTResidenza.setCnmTUser2(cnmTUser);
			cnmTResidenza.setDataOraInsert(now);
		} else {
			cnmTResidenza.setCnmTUser1(cnmTUser);
			cnmTResidenza.setDataOraUpdate(now);
		}

		if (soggetto != null && soggetto.getResidenzaEstera() && soggetto.getNazioneResidenza() != null && soggetto.getNazioneResidenza().getId() != null) {
			cnmTResidenza.setCnmDNazione(cnmDNazioneRepository.findOne(soggetto.getNazioneResidenza().getId()));
			cnmTResidenza.setDenomComuneResidenzaEstero(soggetto.getDenomComuneResidenzaEstero());
			cnmTResidenza.setIndirizzoResidenza(soggetto.getIndirizzoResidenza());
			cnmTResidenza.setNumeroCivicoResidenza(soggetto.getCivicoResidenza());
			cnmTResidenza.setCapResidenza(soggetto.getCap());
			cnmTResidenza.setCnmDComune(null);
			cnmTResidenza.setResidenzaEstera(true);
			cnmTResidenza.setCnmTSoggetto(cnmTSoggetto);
			cnmTResidenza.setIdVerbale(id);	// .
			cnmTResidenzaRepository.save(cnmTResidenza);
		} else if (soggetto != null && soggetto.getComuneResidenza() != null && soggetto.getComuneResidenza().getId() != null) {	// .
			cnmTResidenza.setCnmDComune(cnmDComuneRepository.findOne(soggetto.getComuneResidenza().getId()));
			cnmTResidenza.setIndirizzoResidenza(soggetto.getIndirizzoResidenza());
			cnmTResidenza.setNumeroCivicoResidenza(soggetto.getCivicoResidenza());
			cnmTResidenza.setCapResidenza(soggetto.getCap());
			cnmTResidenza.setCnmDNazione(null);
			cnmTResidenza.setDenomComuneResidenzaEstero(null);
			cnmTResidenza.setResidenzaEstera(false);
			cnmTResidenza.setCnmTSoggetto(cnmTSoggetto);
			cnmTResidenza.setIdVerbale(id); // .
			cnmTResidenzaRepository.save(cnmTResidenza);
		}
		
		
		SoggettoVO sog = soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);
		
		// 20200921 - PP - setto sul soggetto la residenza salvata
		if (cnmTResidenza != null && cnmTResidenza.getCnmDComune() != null) {
			sog.setRegioneNascita(regioneEntityMapper.mapEntityToVO(cnmTResidenza.getCnmDComune().getCnmDProvincia().getCnmDRegione()));
			sog.setProvinciaNascita(provinciaEntityMapper.mapEntityToVO(cnmTResidenza.getCnmDComune().getCnmDProvincia()));
			sog.setComuneNascita(comuneEntityMapper.mapEntityToVO(cnmTResidenza.getCnmDComune()));
		}
		
		if (cnmTResidenza != null) {
			sog.setIndirizzoResidenza(cnmTResidenza.getIndirizzoResidenza());
			sog.setCivicoResidenza(cnmTResidenza.getNumeroCivicoResidenza());
			sog.setCap(cnmTResidenza.getCapResidenza());
		}

		
		sog.setIdSoggettoVerbale(cnmRVerbaleSoggetto.getIdVerbaleSoggetto());
		sog.setRuolo(ruoloSoggettoEntityMapper.mapEntityToVO(cnmDRuoloSoggetto));
		sog.setNoteSoggetto(soggetto.getNoteSoggetto());
		if (soggetto.getNazioneNascitaEstera() != null && soggetto.getNazioneNascitaEstera()) {
			sog.setNazioneNascita(soggetto.getNazioneNascita());
			sog.setDenomComuneNascitaEstero(soggetto.getDenomComuneNascitaEstero());
		}
		return sog;
	}

	private void controllaSoggettoAssociato(CnmTVerbale cnmTVerbale, BigDecimal idStas, Integer idSog) {
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		if (cnmRVerbaleSoggettoList != null && !cnmRVerbaleSoggettoList.isEmpty()) {
			for (CnmRVerbaleSoggetto cnmRVerbaleSoggetto : cnmRVerbaleSoggettoList) {
				CnmTSoggetto cnmTSoggetto = cnmRVerbaleSoggetto.getCnmTSoggetto();
				if (idStas != null && cnmTSoggetto.getIdStas() != null && idStas.longValue() == cnmTSoggetto.getIdStas().longValue())
					throw new BusinessException(ErrorCode.INSERISCI_SOG_GIA_ASSOCIATO_IDSTAS);
				if (idSog != null && cnmTSoggetto.getIdSoggetto().intValue() == idSog.intValue())
					throw new BusinessException(ErrorCode.INSERISCI_SOG_GIA_ASSOCIATO_ID);
			}
		}
	}

	@Override
	public List<SoggettoVO> getSoggettiByIdVerbale(Integer id, UserDetails userDetails, Boolean includiControlloUtenteProprietario) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);

		if (includiControlloUtenteProprietario == null)
			throw new IllegalArgumentException("includiControlloUtenteProprietario is null");

		SecurityUtils.verbaleSecurityView(cnmTVerbale, verbaleService.getEnteLeggeByCnmTVerbale(cnmTVerbale));

		if (cnmTVerbale.getCnmTUser2().getIdUser() != userDetails.getIdUser() && includiControlloUtenteProprietario)
			throw new RuntimeException("l'utente  non può accedere a questo verbale");

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		List<SoggettoVO> soggettoVOList = new ArrayList<>();
		for (CnmRVerbaleSoggetto c : cnmRVerbaleSoggettoList) {
			SoggettoVO sog = soggettoEntityMapper.mapEntityToVO(c.getCnmTSoggetto());
			
			// 20200923 - PP - se e' pregresso, inserirsco sul soggetto la residenza legata al verbale e non quella stas
			if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
				sog = commonSoggettoService.attachResidenzaPregressi(sog, c.getCnmTSoggetto(), id);
			}
			
			sog.setRuolo(ruoloSoggettoEntityMapper.mapEntityToVO(c.getCnmDRuoloSoggetto()));
			sog.setNoteSoggetto(c.getNote());
			sog.setIdSoggettoVerbale(c.getIdVerbaleSoggetto());
			List<CnmROrdinanzaVerbSog> ords = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggetto(c);	
			CnmROrdinanzaVerbSog ord = ords.size()==1 ? ords.get(0) : null; // 20210504_LC Jira 138
			sog.setIdSoggettoOrdinanza(ord != null ? ord.getIdOrdinanzaVerbSog() : null);
			sog.setVerbaleAudizioneCreato(allegatoVerbaleSoggettoService.isAllegatoVerbaleSoggettoCreato(c, TipoAllegato.VERBALE_AUDIZIONE));
			sog.setIdAllegatoVerbaleAudizione(allegatoVerbaleSoggettoService.getIdVerbaleAudizione(c, TipoAllegato.VERBALE_AUDIZIONE));
			soggettoVOList.add(sog);
		}

		return soggettoVOList;

	}

	@Override
	public List<SoggettoVO> getSoggettiByIdVerbalePregressi(Integer id, UserDetails userDetails, Boolean includiControlloUtenteProprietario) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);

		if (includiControlloUtenteProprietario == null)
			throw new IllegalArgumentException("includiControlloUtenteProprietario is null");

		SecurityUtils.verbaleSecurityView(cnmTVerbale, verbaleService.getEnteLeggeByCnmTVerbale(cnmTVerbale));

		if (cnmTVerbale.getCnmTUser2().getIdUser() != userDetails.getIdUser() && includiControlloUtenteProprietario)
			throw new RuntimeException("l'utente  non può accedere a questo verbale");

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		List<SoggettoVO> soggettoVOList = new ArrayList<>();
		for (CnmRVerbaleSoggetto c : cnmRVerbaleSoggettoList) {
			SoggettoVO sog = soggettoEntityMapper.mapEntityToVO(c.getCnmTSoggetto());

			sog = commonSoggettoService.attachResidenzaPregressi(sog, c.getCnmTSoggetto(), id);
			
			sog.setRuolo(ruoloSoggettoEntityMapper.mapEntityToVO(c.getCnmDRuoloSoggetto()));
			sog.setNoteSoggetto(c.getNote());
			sog.setIdSoggettoVerbale(c.getIdVerbaleSoggetto());
			List<CnmROrdinanzaVerbSog> ords = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggetto(c);	
			CnmROrdinanzaVerbSog ord = ords.size()==1 ? ords.get(0) : null; // 20210504_LC Jira 138
			sog.setIdSoggettoOrdinanza(ord != null ? ord.getIdOrdinanzaVerbSog() : null);
			sog.setVerbaleAudizioneCreato(allegatoVerbaleSoggettoService.isAllegatoVerbaleSoggettoCreato(c, TipoAllegato.VERBALE_AUDIZIONE));
			sog.setIdAllegatoVerbaleAudizione(allegatoVerbaleSoggettoService.getIdVerbaleAudizione(c, TipoAllegato.VERBALE_AUDIZIONE));
			soggettoVOList.add(sog);
		}

		return soggettoVOList;

	}
	

	@Override
	public List<SoggettoVO> getSoggettiByIdVerbaleConvocazione(Integer id, UserDetails userDetails, Boolean includiControlloUtenteProprietario) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);

		if (includiControlloUtenteProprietario == null)
			throw new IllegalArgumentException("includiControlloUtenteProprietario is null");

		SecurityUtils.verbaleSecurityView(cnmTVerbale, verbaleService.getEnteLeggeByCnmTVerbale(cnmTVerbale));

		if (cnmTVerbale.getCnmTUser2().getIdUser() != userDetails.getIdUser() && includiControlloUtenteProprietario)
			throw new RuntimeException("l'utente  non può accedere a questo verbale");

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		List<SoggettoVO> soggettoVOList = new ArrayList<>();
		for (CnmRVerbaleSoggetto c : cnmRVerbaleSoggettoList) {
			SoggettoVO sog = soggettoEntityMapper.mapEntityToVO(c.getCnmTSoggetto());
			
			// 20200923 - PP - se e' pregresso, inserirsco sul soggetto la residenza legata al verbale e non quella stas
			if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
				sog = commonSoggettoService.attachResidenzaPregressi(sog, c.getCnmTSoggetto(), id);
			}
			
			sog.setRuolo(ruoloSoggettoEntityMapper.mapEntityToVO(c.getCnmDRuoloSoggetto()));
			sog.setNoteSoggetto(c.getNote());
			sog.setIdSoggettoVerbale(c.getIdVerbaleSoggetto());
			List<CnmROrdinanzaVerbSog> ords = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggetto(c);
			CnmROrdinanzaVerbSog ord = ords.size()==1 ? ords.get(0) : null; // 20210504_LC Jira 138
			sog.setIdSoggettoOrdinanza(ord != null ? ord.getIdOrdinanzaVerbSog() : null);
			sog.setVerbaleAudizioneCreato(allegatoVerbaleSoggettoService.isAllegatoVerbaleSoggettoCreato(c, TipoAllegato.CONVOCAZIONE_AUDIZIONE));
			sog.setIdAllegatoVerbaleAudizione(allegatoVerbaleSoggettoService.getIdVerbaleAudizione(c, TipoAllegato.CONVOCAZIONE_AUDIZIONE));
			soggettoVOList.add(sog);

		}

		return soggettoVOList;

	}

	@Override
	public List<SoggettoVO> getSoggettiByIdVerbaleAudizione(Integer id) {
		List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs = cnmRAllegatoVerbSogRepository.findByCnmTAllegato(cnmTAllegatoRepository.findOne(id));
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = new ArrayList<>();
		for (CnmRAllegatoVerbSog a : cnmRAllegatoVerbSogs) {
			cnmRVerbaleSoggettoList.add(a.getCnmRVerbaleSoggetto());
		}

		List<SoggettoVO> soggettoVOList = new ArrayList<>();
		for (CnmRVerbaleSoggetto c : cnmRVerbaleSoggettoList) {
			SoggettoVO sog = soggettoEntityMapper.mapEntityToVO(c.getCnmTSoggetto());

			// 20200923 - PP - se e' pregresso, inserirsco sul soggetto la residenza legata al verbale e non quella stas
			if(c.getCnmTVerbale().getCnmDStatoPregresso() != null && c.getCnmTVerbale().getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
				sog = commonSoggettoService.attachResidenzaPregressi(sog, c.getCnmTSoggetto(), id);
			}
			
			sog.setRuolo(ruoloSoggettoEntityMapper.mapEntityToVO(c.getCnmDRuoloSoggetto()));
			sog.setNoteSoggetto(c.getNote());
			sog.setIdSoggettoVerbale(c.getIdVerbaleSoggetto());
			List<CnmROrdinanzaVerbSog> ords = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggetto(c);
			CnmROrdinanzaVerbSog ord = ords.size()==1 ? ords.get(0) : null; // 20210504_LC Jira 138
			sog.setIdSoggettoOrdinanza(ord != null ? ord.getIdOrdinanzaVerbSog() : null);
			sog.setVerbaleAudizioneCreato(allegatoVerbaleSoggettoService.isAllegatoVerbaleSoggettoCreato(c, TipoAllegato.VERBALE_AUDIZIONE));
			sog.setIdAllegatoVerbaleAudizione(allegatoVerbaleSoggettoService.getIdVerbaleAudizione(c, TipoAllegato.VERBALE_AUDIZIONE));
			soggettoVOList.add(sog);
		}

		return soggettoVOList;

	}

	@Override
	public void eliminaSoggettoByIdVerbaleSoggetto(Integer id, UserDetails userDetails) {
		if (id == null)
			throw new IllegalArgumentException("verbale is null");
		CnmRVerbaleSoggetto cnmRVerbaleSoggetto = cnmRVerbaleSoggettoRepository.findOne(id);
		if (cnmRVerbaleSoggetto == null)
			throw new SecurityException("Soggetto non associato al verbale");

		if (cnmRVerbaleSoggetto.getCnmTVerbale().getCnmDStatoVerbale().getIdStatoVerbale() != Constants.STATO_VERBALE_INCOMPLETO
				&& cnmRVerbaleSoggetto.getCnmTVerbale().getCnmDStatoVerbale().getIdStatoVerbale() != Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO)
			throw new SecurityException("verbale non in stato incompleto");

		CnmTSoggetto cnmTSoggetto = cnmRVerbaleSoggetto.getCnmTSoggetto();

		
		// 20210226_LC Jira 121 - elimina tutte le relazioni tra soggetto e verbale
		
		// rAllegatoVerbSOg
		List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogList = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggetto(cnmRVerbaleSoggetto);
		if (cnmRAllegatoVerbSogList != null && cnmRAllegatoVerbSogList.size() > 0) {
			for (CnmRAllegatoVerbSog cnmRAllegatoVerbSog : cnmRAllegatoVerbSogList) {
				cnmRAllegatoVerbSogRepository.delete(cnmRAllegatoVerbSog);
			}
		}
		
		// rOrdinanzaVerbSOg
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggetto(cnmRVerbaleSoggetto);	// 20210504_LC Jira 138
		if (cnmROrdinanzaVerbSogList != null && !cnmROrdinanzaVerbSogList.isEmpty()) {
			for (CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog : cnmROrdinanzaVerbSogList) {
				// rAllegatoOrdVerbSOg
				List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogList = cnmRAllegatoOrdVerbSogRepository.findByCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);						
				if (cnmRAllegatoOrdVerbSogList != null && cnmRAllegatoOrdVerbSogList.size() > 0) {
					for (CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog : cnmRAllegatoOrdVerbSogList)
						cnmRAllegatoOrdVerbSogRepository.delete(cnmRAllegatoOrdVerbSog);
				}			
				cnmROrdinanzaVerbSogRepository.delete(cnmROrdinanzaVerbSog);				
			}
		}
		

		
		cnmRVerbaleSoggettoRepository.delete(cnmRVerbaleSoggetto);

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettos = cnmRVerbaleSoggettoRepository.findByCnmTSoggetto(cnmTSoggetto);
		if (cnmRVerbaleSoggettos == null || cnmRVerbaleSoggettos.size() == 0) {
			CnmTPersona cnmTPersona = cnmTSoggetto.getCnmTPersona();
			CnmTSocieta cnmTSocieta = cnmTSoggetto.getCnmTSocieta();
			CnmTResidenza cnmTResidenza = cnmTResidenzaRepository.findByCnmTSoggetto(cnmTSoggetto);
			if (cnmTResidenza != null) {
				cnmTResidenzaRepository.delete(cnmTResidenza);
			}
			cnmTSoggettoRepository.delete(cnmTSoggetto);
			if (cnmTPersona != null) {
				cnmTPersonaRepository.delete(cnmTPersona);
			} else if (cnmTSocieta != null) {
				cnmTSocietaRepository.delete(cnmTSocieta);
			}
		}
		
		// 20200921 - PP - Elimino sul soggetto la residenza salvata per il verbale
		// 20210226_LC Jira 121 - idVerbale
		CnmTResidenza residenza = cnmTResidenzaRepository.findByCnmTSoggettoAndIdVerbale(cnmTSoggetto, cnmRVerbaleSoggetto.getCnmTVerbale().getIdVerbale());
		if(residenza != null) {
			cnmTResidenzaRepository.delete(residenza);
		}
	}

	@Override
	public SoggettoVO ricercaSoggetto(MinSoggettoVO minSoggettoVO, UserDetails userDetails) {
		SoggettoVO soggettoStas = commonSoggettoService.getSoggettoFromStas(minSoggettoVO, userDetails.getIdentita().toString());
		SoggettoVO soggettoDb = soggettoEntityMapper.mapEntityToVO(commonSoggettoService.getSoggettoFromDb(minSoggettoVO, false));

		if (soggettoDb != null) {
			if (soggettoDb.getResidenzaEstera()) {
				if (soggettoDb.getIdStas() != null) {
					if (soggettoStas != null) {
						CnmTResidenza cnmTResidenza = cnmTResidenzaRepository.findByCnmTSoggetto(cnmTSoggettoRepository.findByIdStas(soggettoStas.getIdStas()));
						soggettoStas.setIndirizzoResidenzaStas(soggettoStas.getIndirizzoResidenza());
						soggettoStas.setCivicoResidenzaStas(soggettoStas.getCivicoResidenza());
						soggettoStas.setCapStas(soggettoStas.getCap());
						soggettoStas = residenzaEntityMapper.mapEntitytoVOUpdate(soggettoStas, cnmTResidenza);
						soggettoStas.setId(soggettoDb.getId());
						return soggettoStas;
					}
				}
			}
			if (soggettoDb.getNazioneNascitaEstera() == null || soggettoDb.getNazioneNascitaEstera() == false) {
				soggettoDb.setNazioneNascitaEstera(false);
				if (soggettoDb.getIdStas() != null) {
					if (soggettoStas.getRegioneNascita() == null) {
						soggettoStas.setRegioneNascita(soggettoDb.getRegioneNascita());
						soggettoStas.setProvinciaNascita(soggettoDb.getProvinciaNascita());
						soggettoStas.setComuneNascita(soggettoDb.getComuneNascita());
						soggettoStas.setId(soggettoDb.getId());
					}
				}
			} else if (soggettoDb.getNazioneNascitaEstera()) {
				if (soggettoDb.getIdStas() != null) {
					soggettoStas.setDenomComuneNascitaEstero(soggettoDb.getDenomComuneNascitaEstero());
					soggettoStas.setNazioneNascitaEstera(true);
					if (soggettoStas.getNazioneNascita() == null)
						soggettoStas.setNazioneNascita(soggettoDb.getNazioneNascita());
					soggettoStas.setId(soggettoDb.getId());
				}
			}
		}

		if (soggettoStas != null)
			return soggettoStas;
		if (soggettoDb != null)
			return soggettoDb;

		return new SoggettoVO(minSoggettoVO);

	}

	@Override
	public SoggettoPregressiVO ricercaSoggettoPregressi(MinSoggettoVO minSoggettoVO, UserDetails userDetails) {
		SoggettoVO soggetto = ricercaSoggetto(minSoggettoVO, userDetails);
		SoggettoPregressiVO soggettoPregressiVO = soggettoPregressiEntityMapper.createSoggettoPregressiVO(soggetto, null);

		return soggettoPregressiVO;

	}
	
	@Override
	public SoggettoVO ricercaSoggettoPerPIva(MinSoggettoVO minSoggettoVO, UserDetails userDetails) {
		Anagrafica[] stas = stasServFacade.ricercaSoggettoCF(minSoggettoVO.getPartitaIva(), userDetails.getIdentita().toString());
		List<SoggettoVO> soggettoList = null;
		if (stas != null && stas.length > 0)
			soggettoList = anagraficaWsOutputMapper.mapArrayWsTypeToListVO(stas);
		return soggettoList != null && soggettoList.size() > 0 ? soggettoList.get(0) : null;
	}

	@Override
	public SoggettoPregressiVO ricercaSoggettoPerPIvaPregressi(MinSoggettoVO minSoggettoVO, UserDetails userDetails) {
		SoggettoVO soggetto = ricercaSoggettoPerPIva(minSoggettoVO, userDetails);
		SoggettoPregressiVO soggettoPregressiVO = soggettoPregressiEntityMapper.createSoggettoPregressiVO(soggetto, null);

		return soggettoPregressiVO;
	}

	@Override
	public List<RuoloSoggettoVO> getRuoliSoggetto() {
		return ruoloSoggettoEntityMapper.mapListEntityToListVO((List<CnmDRuoloSoggetto>) cnmDRuoloSoggettoRepository.findAll());
	}

}
