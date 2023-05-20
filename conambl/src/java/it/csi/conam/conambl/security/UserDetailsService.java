/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.security;

import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.EnteEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmRUseCaseRuoloRepository;
import it.csi.conam.conambl.integration.repositories.CnmRUserEnteRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import it.csi.iride2.policy.entity.Identita;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classe che si occupa di cercare l'utente il cui CF viene restituito da Iride Carica inoltre i ruoli dell'utente
 * 
 * @author lorenzo.fantini
 * 
 * 
 */
@Service
public class UserDetailsService implements ShibbolethDetailService {

	private Logger logger = LoggerFactory.getLogger(Constants.HANDLER_SECURITY);

	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CnmRUseCaseRuoloRepository cnmRUseCaseRuoloRepository;
	@Autowired
	private CnmRUserEnteRepository cnmRUserEnteRepository;

	@Autowired
	private EnteEntityMapper enteDEntityMapper;

	@Override
	public UserDetails caricaUtenteDaIdentita(IdentityDetails identityDetails) throws UsernameNotFoundException {

		logger.debug("[UserDetailsService::caricaUtenteDaIdentita] loadUserIdentity::" + (identityDetails != null ? identityDetails.getIdentity() : "null"));

		List<GrantedAuthority> auths = new ArrayList<>();
		Identita identitaOrch;

		long id = 9999L;
		Long idGruppo = null;
		List<EnteVO> entiAccertatore = null;
		List<EnteVO> entiLegge = null;
		String ray = generateRay();
		try {
			identitaOrch = getIdentitaFromToken(identityDetails.getIdentity());
			CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(identitaOrch.getCodFiscale());
			if (cnmTUser != null) {
				id = cnmTUser.getIdUser();
				idGruppo = cnmTUser.getCnmDGruppo() != null ? cnmTUser.getCnmDGruppo().getIdGruppo() : 9999L;
				auths = findUseCasesForIdentita(identitaOrch, cnmTUser);
				entiAccertatore = findEntiAccertatoreForUser(cnmTUser);
				entiLegge = findEntiLeggeForUser(cnmTUser);
			}

		} catch (Exception e) {
			logger.error("Si e verificato un errore in fase di autenticazione", e);
			throw new SecurityException(e);
		}

		return this.createUserDetails(identitaOrch, auths, id, idGruppo, entiAccertatore, entiLegge, ray);
	}

	private String generateRay() {
		Random random = new Random();
		return String.valueOf(Double.valueOf(Math.floor(100000 + random.nextDouble() * 800000)).intValue());
	}

	private List<EnteVO> findEntiAccertatoreForUser(CnmTUser cnmTUser) {
		List<CnmRUserEnte> cnmRUserEnteList = cnmRUserEnteRepository.findByCnmTUserAndTipoUtilizzoAndFineValidita(cnmTUser, Constants.ID_UTILIZZO_ENTE_ACCERTATORE);
		return convertCnmRUserEnteInEnteVO(cnmRUserEnteList);
	}

	private List<EnteVO> findEntiLeggeForUser(CnmTUser cnmTUser) {
		List<CnmRUserEnte> cnmRUserEnteList = cnmRUserEnteRepository.findByCnmTUserAndTipoUtilizzoAndFineValidita(cnmTUser, Constants.ID_UTILIZZO_ENTE_LEGGE);
		return convertCnmRUserEnteInEnteVO(cnmRUserEnteList);
	}

	private List<EnteVO> convertCnmRUserEnteInEnteVO(List<CnmRUserEnte> cnmRUserEnteList) {
		List<EnteVO> enti = new ArrayList<>();
		if (cnmRUserEnteList == null || cnmRUserEnteList.isEmpty())
			throw new SecurityException("L'utente risulta censito sul db ma non ha l'associazioni con le leggi");
		for (CnmRUserEnte cnmRUserEnte : cnmRUserEnteList) {
			enti.add(enteDEntityMapper.mapEntityToVO(cnmRUserEnte.getCnmDEnte()));
		}
		return enti;
	}

	public List<GrantedAuthority> findUseCasesForIdentita(Identita identita, CnmTUser cnmTUser) {
		List<GrantedAuthority> sga = new ArrayList<>();
		if (cnmTUser != null) {
			List<CnmRUseCaseRuolo> cnmRUseCaseRuoloList = cnmRUseCaseRuoloRepository.findByCnmDRuoloAndFineValidita(cnmTUser.getCnmDRuolo());
			if (null != cnmRUseCaseRuoloList) {
				for (CnmRUseCaseRuolo cnmRUseCaseRuolo : cnmRUseCaseRuoloList) {
					CnmDUseCase cnmDUseCase = cnmRUseCaseRuolo.getCnmDUseCase();
					sga.add(new AppGrantedAuthority(cnmDUseCase.getCodUseCase(), cnmDUseCase.getDescUseCase(), AppGrantedAuthority.TIPO_AUTHORITY_USE_CASE_DB));
				}
			}
			CnmDRuolo cnmDRuolo = cnmTUser.getCnmDRuolo();
			sga.add(new AppGrantedAuthority(Long.toString(cnmDRuolo.getIdRuolo()), cnmDRuolo.getDescRuolo(), AppGrantedAuthority.TIPO_AUTHORITY_RUOLO_DB));
		}

		sga.add(new AppGrantedAuthority("UTENTE", "UTENTE", AppGrantedAuthority.TIPO_AUTHORITY_ALTRO));

		return sga;
	}

	// Metodo di utilita
	private static Identita getIdentitaFromToken(String token)
			// throws MalformedIdTokenException
			throws Exception {
		Identita identita = new Identita();

		int slash1Index = token.indexOf('/');
		if (slash1Index == -1)
			// throw new MalformedIdTokenException(token);
			throw new RuntimeException("Errore in parsing token");
		identita.setCodFiscale(token.substring(0, slash1Index));
		int slash2Index = token.indexOf('/', slash1Index + 1);
		if (slash2Index == -1)
			// throw new MalformedIdTokenException(token);
			throw new RuntimeException("Errore in parsing token");
		identita.setNome(token.substring(slash1Index + 1, slash2Index));
		int slash3Index = token.indexOf('/', slash2Index + 1);
		if (slash3Index == -1)
			// throw new MalformedIdTokenException(token);
			throw new RuntimeException("Errore in parsing token");
		identita.setCognome(token.substring(slash2Index + 1, slash3Index));
		int slash4Index = token.indexOf('/', slash3Index + 1);
		if (slash4Index == -1)
			// throw new MalformedIdTokenException(token);
			throw new RuntimeException("Errore in parsing token");
		identita.setIdProvider(token.substring(slash3Index + 1, slash4Index));
		int slash5Index = token.indexOf('/', slash4Index + 1);
		if (slash5Index == -1)
			// throw new MalformedIdTokenException(token);
			throw new RuntimeException("Errore in parsing token");
		identita.setTimestamp(token.substring(slash4Index + 1, slash5Index));
		int slash6Index = token.indexOf('/', slash5Index + 1);
		if (slash6Index == -1) {
			// throw new MalformedIdTokenException(token);
			throw new RuntimeException("Errore in parsing token");
		} else {
			identita.setLivelloAutenticazione(Integer.parseInt(token.substring(slash5Index + 1, slash6Index)));
			identita.setMac(token.substring(slash6Index + 1));
		}
		return identita;
	}

	protected UserDetails createUserDetails(Identita identita, List<GrantedAuthority> combinedAuthorities, Long idUser, Long idGruppo, List<EnteVO> entiAccertatore, List<EnteVO> entiLeggi,
			String ray) {
		return new UserDetails(identita.getNome() + " " + identita.getCognome(), "", true, true, true, true, combinedAuthorities, identita, idUser, idGruppo, entiAccertatore, entiLeggi, ray);
	}

}
