/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.specification;

import it.csi.conam.conambl.integration.entity.CnmDAmbito;
import it.csi.conam.conambl.integration.entity.CnmTScrittoDifensivo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CnmTScrittoDifensivoSpecification {

	public static Specification<CnmTScrittoDifensivo> findScrittoBy(
		String numeroProtocollo,
		String nome,
		String cognome,
		String ragioneSociale,
		List<CnmDAmbito> cnmDAmbitoList,
		Date dataScrittoDa,
		Date dataScrittoA,
		String tipoRicerca
	) {
		return new Specification<CnmTScrittoDifensivo>() {
			@Override
			public Predicate toPredicate(Root<CnmTScrittoDifensivo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				List<Predicate> predicates = new ArrayList<>();
//				final Join<CnmTScrittoDifensivo, CnmDAmbito> cnmDAmbitoJoin = root.join("cnmDAmbito");
				
				// 20210426_LC non dovrebbe mai entrarci (l'associazione si fa direttamente da aggiungi allegato come per le altre fattispecie di allegati)
				if (tipoRicerca != null && tipoRicerca.equals("ASSOCIA_SCRITTO")) {								
					// solo memorie ancora "disponibili" (non associate)
//					predicates.add(builder.equal(root.get("flagAssociato"), false));	
					// solo di ambiti == quello del verbale? da verificare
//					if (cnmDAmbitoList != null && !cnmDAmbitoList.isEmpty()) {
//						predicates.add(cnmDAmbitoJoin.in(cnmDAmbitoList));		
//					}										
				} 

				
				if (StringUtils.isNotEmpty(numeroProtocollo)) {
					predicates.add(builder.equal(root.get("numeroProtocollo"), numeroProtocollo));
				} 

				
				
				if (nome != null && cognome != null) {
					predicates.add(builder.equal(root.get("nome"), nome.toUpperCase()));
					predicates.add(builder.equal(root.get("cognome"), cognome.toUpperCase()));
				}
				
				if (ragioneSociale != null) {
					predicates.add(builder.equal(root.get("ragioneSociale"), ragioneSociale.toUpperCase()));
				}				
				
				
				if (dataScrittoDa != null && dataScrittoA != null) {
					Predicate date = builder.between(root.get("dataOraInsert"), dataScrittoDa, dataScrittoA);
					predicates.add(date);
				}
				
				query.distinct(true);
				return builder.and(predicates.toArray(new Predicate[0]));
			}

		};
	}

}
