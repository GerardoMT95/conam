/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.specification;

import it.csi.conam.conambl.integration.entity.CnmDComune;
import it.csi.conam.conambl.integration.entity.CnmDNazione;
import it.csi.conam.conambl.integration.entity.CnmTPersona;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.util.DateConamUtils;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CnmTSoggettoSpecification {

	public static Specification<CnmTSoggetto> findSoggettoFisicoByCodFiscaleOrModuloRicerca(String codFiscale, //
			String cognome, //
			String nome, //
			Long idComuneNascita, //
			String sesso, //
			Date dataNascita, //
			Long idNazioneNascita) {
		return new Specification<CnmTSoggetto>() {
			@Override
			public Predicate toPredicate(Root<CnmTSoggetto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();

				if (StringUtils.isNotEmpty(codFiscale))
					predicates.add(builder.equal(root.get("codiceFiscale"), codFiscale.toUpperCase()));
				else {

					if (StringUtils.isNotEmpty(cognome))
						predicates.add(builder.equal(root.get("cognome"), cognome.toUpperCase()));
					else
						throw new RuntimeException("cognome non valorizzato");
					if (StringUtils.isNotEmpty(nome))
						predicates.add(builder.equal(root.get("nome"), nome.toUpperCase()));
					else
						throw new RuntimeException("nome non valorizzato");

					if (dataNascita != null || idComuneNascita != null || idNazioneNascita != null || sesso != null) {
						final Join<CnmTSoggetto, CnmTPersona> cnmTPersonaJoin = root.join("cnmTPersona");
						if (dataNascita != null)
							predicates.add(builder.equal(cnmTPersonaJoin.get("dataNascita"), dataNascita));

						if (idComuneNascita != null) {
							final Join<CnmTPersona, CnmDComune> cnmTComuneNascitaJoin = cnmTPersonaJoin.join("cnmDComune");
							predicates.add(builder.equal(cnmTComuneNascitaJoin.get("idComune"), idComuneNascita));
						} else if (idNazioneNascita != null) {
							final Join<CnmTPersona, CnmDNazione> cnmTNazioneNascitaJoin = cnmTPersonaJoin.join("cnmDNazione");
							predicates.add(builder.equal(cnmTNazioneNascitaJoin.get("idNazione"), idNazioneNascita));
						}

						if (StringUtils.isNotEmpty(sesso))
							predicates.add(builder.equal(cnmTPersonaJoin.get("sesso"), sesso));
					}
				}

				return builder.and(predicates.toArray(new Predicate[0]));
			}

		};
	}

	public static Specification<CnmTSoggetto> findSocietaByCodFiscaleOrPivaOrRagioneSociale(String codFiscale, //
			String partitaIva, //
			String ragioneSociale, //
			boolean isRicerca//
	) {
		return new Specification<CnmTSoggetto>() {
			@Override
			public Predicate toPredicate(Root<CnmTSoggetto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();

				if (StringUtils.isNotEmpty(codFiscale))
					predicates.add(builder.equal(root.get("codiceFiscaleGiuridico"), codFiscale.toUpperCase()));
				if (StringUtils.isNotEmpty(partitaIva))
					predicates.add(builder.equal(root.get("partitaIva"), partitaIva.toUpperCase()));
				if (StringUtils.isNotEmpty(ragioneSociale)) {
					if (isRicerca)
						predicates.add(builder.like(root.get("ragioneSociale"), "%" + ragioneSociale.toUpperCase() + "%"));
					else
						predicates.add(builder.equal(root.get("ragioneSociale"), ragioneSociale.toUpperCase()));
				}
				return builder.and(predicates.toArray(new Predicate[0]));
			}

		};
	}
	

	private static Boolean validateString(String stringa) {
		return (stringa != null && StringUtils.isNotEmpty(stringa));
	}
	

	public static Specification<CnmTSoggetto> findSoggetti(
		String codiceFiscale,
		String codiceFiscalePG,
		String partitaIva,
		String cognome,
		String nome,
		String ragioneSociale,
		Date dataNascita
			
	) {
		return new Specification<CnmTSoggetto>() {
			@Override
			public Predicate toPredicate(Root<CnmTSoggetto> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();
				
				
				if (validateString(cognome))
					predicates.add(
						builder.equal(
							root.get("cognome"),
							cognome.toUpperCase()
						)
					);
				
				if (validateString(nome))
					predicates.add(
						builder.equal(
							root.get("nome"),
							nome.toUpperCase()
						)
					);
				
				
				if (dataNascita != null) {
					final Join<CnmTSoggetto, CnmTPersona> cnmTPersonaJoin = root.join("cnmTPersona");
					predicates.add(
						builder.equal(
							cnmTPersonaJoin.get("dataNascita"),
							dataNascita
						)
					);
				}
				
				if (validateString(codiceFiscale))
					predicates.add(
						builder.equal(
							root.get("codiceFiscale"),
							codiceFiscale
						)
					);
				
				if (validateString(codiceFiscalePG))
					predicates.add(
						builder.equal(
							root.get("codiceFiscaleGiuridico"),
							codiceFiscalePG
						)
					);
				
				if (validateString(ragioneSociale))
					predicates.add(
						builder.equal(
							root.get("ragioneSociale"),
							ragioneSociale
						)
					);
				
				if (validateString(partitaIva))
					predicates.add(
						builder.equal(
							root.get("partitaIva"),
							partitaIva
						)
					);
				return builder.and(predicates.toArray(new Predicate[0]));
			}

		};
	} 

	public static Specification<CnmTSoggetto> findSoggetti(MinSoggettoVO minSoggettoVO) {
		String codiceFiscale = null;
		String codiceFiscalePG = null;
		String partitaIva = null;
		String cognome = null;
		String nome = null;
		String ragioneSociale = null;
		Date dataNascita = null;
		
		// 20210521_LC segnalazione 20210521H1222
		if (minSoggettoVO.getPersonaFisica()) {
			codiceFiscale = minSoggettoVO.getCodiceFiscale();			
		} else {
			codiceFiscalePG = minSoggettoVO.getCodiceFiscale();
		}
		
		partitaIva = minSoggettoVO.getPartitaIva();
		cognome = minSoggettoVO.getCognome();
		nome = minSoggettoVO.getNome();
		ragioneSociale = minSoggettoVO.getRagioneSociale();
		dataNascita = DateConamUtils.convertToDate(minSoggettoVO.getDataNascita());
		
		return findSoggetti(
			codiceFiscale,
			codiceFiscalePG,
			partitaIva,
			cognome,
			nome,
			ragioneSociale,
			dataNascita
				
		);
	}

}
