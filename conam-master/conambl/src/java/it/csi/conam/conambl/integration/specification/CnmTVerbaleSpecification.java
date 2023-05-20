/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.specification;

import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.util.VerbaleSearchParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CnmTVerbaleSpecification {
	
	public static Specification<CnmTVerbale> findBy(
		List<CnmTSoggetto> trasgressore,
		List<CnmTSoggetto> obbligatoInSolido,
		List<CnmDEnte> enteAccertatore,
		VerbaleSearchParam parametriVerbale
	){
		if (parametriVerbale == null) parametriVerbale = new VerbaleSearchParam();
		
		return findBy(
			trasgressore,
			obbligatoInSolido,
			parametriVerbale.getNumeroProtocollo(),
			parametriVerbale.getNumeroVerbale(),
			parametriVerbale.getLettera(),
			parametriVerbale.getAmbito(),
			parametriVerbale.getStatoVerbale(),
			parametriVerbale.getStatoManuale(),
			enteAccertatore,
			parametriVerbale.getStatiPregresso()
		);
	};
	
	
	public static Specification<CnmTVerbale> findBy(
		List<CnmTSoggetto> trasgressore, // soggetti trasgressori
		List<CnmTSoggetto> obbligatoInSolido, // soggetti obbligati
		String numeroPrototocollo, //
		String numeroVerbale, //
		List<CnmDLettera> lettera, // lettera selezionata dal FE
		CnmDAmbito ambito, // ambito selezionato dal FE
		List<CnmDStatoVerbale> statoVerbs, // stato verbale
		List<CnmDStatoManuale> statoManuale,
		List<CnmDEnte> enteAccertatore, // valorizzato solo per utenti accertatori
		List<CnmDStatoPregresso> statiPregresso // lista stati pregresso
	) {
		return new Specification<CnmTVerbale>() {
			@Override
			public Predicate toPredicate(Root<CnmTVerbale> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();

				if (lettera != null && !lettera.isEmpty()) {
					final Join<CnmTVerbale, CnmRVerbaleIllecito> cnmRVerbaleIllecitoJoin = root.join("cnmRVerbaleIllecitos");
					final Join<CnmRVerbaleIllecito, CnmDLettera> cnmDLetteraJoin = cnmRVerbaleIllecitoJoin.join("cnmDLettera");
					predicates.add(cnmDLetteraJoin.in(lettera));
				}
				
				// 20211125_LC Jira 184 - ricerca per ambito
				if (ambito != null) {
					final Join<CnmTVerbale, CnmRVerbaleIllecito> cnmRVerbaleIllecitoJoin = root.join("cnmRVerbaleIllecitos");
					final Join<CnmRVerbaleIllecito, CnmDLettera> cnmDLetteraJoin = cnmRVerbaleIllecitoJoin.join("cnmDLettera");
					final Join<CnmDLettera, CnmDComma> cnmDCommaJoin = cnmDLetteraJoin.join("cnmDComma");
					final Join<CnmDComma, CnmDArticolo> cnmDArticoloJoin = cnmDCommaJoin.join("cnmDArticolo");
					final Join<CnmDArticolo, CnmREnteNorma> cnmREnteNormaJoin = cnmDArticoloJoin.join("cnmREnteNorma");
					final Join<CnmREnteNorma, CnmDNorma> cnmDNormaJoin = cnmREnteNormaJoin.join("cnmDNorma");
					final Join<CnmDNorma, CnmDAmbito> cnmDAmbitoJoin = cnmDNormaJoin.join("cnmDAmbito");
					predicates.add(cnmDAmbitoJoin.in(ambito));
				}
				
				if (trasgressore != null && !trasgressore.isEmpty()) {
					Subquery<CnmRVerbaleSoggetto> subqueryRVerbaleSoggetto = query.subquery(CnmRVerbaleSoggetto.class);
					Root<CnmRVerbaleSoggetto> rootSubqueryRVerbaleSoggetto = subqueryRVerbaleSoggetto.from(CnmRVerbaleSoggetto.class);
					final Join<CnmRVerbaleSoggetto, CnmDRuoloSoggetto> cnmDRuoloSoggettoJoin = rootSubqueryRVerbaleSoggetto.join("cnmDRuoloSoggetto");
					final Join<CnmRVerbaleSoggetto, CnmTSoggetto> cnmRSoggettoJoin = rootSubqueryRVerbaleSoggetto.join("cnmTSoggetto");
					Predicate predicate = builder.and(cnmRSoggettoJoin.in(trasgressore), builder.equal(cnmDRuoloSoggettoJoin.get("idRuoloSoggetto"), Constants.VERBALE_SOGGETTO_RUOLO_TRASGRESSORE_ID));
					subqueryRVerbaleSoggetto.select(rootSubqueryRVerbaleSoggetto.get("cnmTVerbale")).where(predicate).distinct(true);
					predicates.add(root.in(subqueryRVerbaleSoggetto));
				}
				if (obbligatoInSolido != null && !obbligatoInSolido.isEmpty()) {
					Subquery<CnmRVerbaleSoggetto> subqueryRVerbaleSoggetto = query.subquery(CnmRVerbaleSoggetto.class);
					Root<CnmRVerbaleSoggetto> rootSubqueryRVerbaleSoggetto = subqueryRVerbaleSoggetto.from(CnmRVerbaleSoggetto.class);
					final Join<CnmRVerbaleSoggetto, CnmDRuoloSoggetto> cnmDRuoloSoggettoJoin = rootSubqueryRVerbaleSoggetto.join("cnmDRuoloSoggetto");
					final Join<CnmRVerbaleSoggetto, CnmTSoggetto> cnmRSoggettoJoin = rootSubqueryRVerbaleSoggetto.join("cnmTSoggetto");
					Predicate predicate = builder.and(cnmRSoggettoJoin.in(obbligatoInSolido),
							builder.equal(cnmDRuoloSoggettoJoin.get("idRuoloSoggetto"), Constants.VERBALE_SOGGETTO_RUOLO_OBBLIGATO_IN_SOLIDO_ID));
					subqueryRVerbaleSoggetto.select(rootSubqueryRVerbaleSoggetto.get("cnmTVerbale")).where(predicate).distinct(true);
					predicates.add(root.in(subqueryRVerbaleSoggetto));
				}

				if (numeroPrototocollo != null)
					predicates.add(builder.equal(root.get("numeroProtocollo"), numeroPrototocollo));

				if (StringUtils.isNotEmpty(numeroVerbale))
					predicates.add(builder.equal(root.get("numVerbale"), numeroVerbale.toUpperCase()));

				if(statiPregresso != null && !statiPregresso.isEmpty()) {
					predicates.add(root.get("cnmDStatoPregresso").in(statiPregresso));
				}

				if (statoVerbs != null) {
					predicates.add(root.get("cnmDStatoVerbale").in(statoVerbs));
				}

				/*if (statoManuale != null) {
					predicates.add(root.get("cnmDStatoManuale").in(statoManuale));
				}*/
				
				if(statoManuale != null && !statoManuale.isEmpty()) {
					final Join<CnmTVerbale, CnmDStatoManuale> cnmDStatoManualeJoin = root.join("cnmDStatoManuale");
					List<Long> idList = new ArrayList<Long>();
					for (CnmDStatoManuale stManuale:statoManuale) {
						idList.add(stManuale.getId());
					}
					predicates.add(cnmDStatoManualeJoin.get("id").in(idList));
				}
				

				// valorizzato solo per gli accertatori
				if (enteAccertatore != null && !enteAccertatore.isEmpty()) {
					final Join<CnmTVerbale, CnmDEnte> enteAccertatoreJoin = root.join("cnmDEnte");
					predicates.add(enteAccertatoreJoin.in(enteAccertatore));
				}

				query.distinct(true);

				return builder.and(predicates.toArray(new Predicate[0]));
			}

		};
	}

}
