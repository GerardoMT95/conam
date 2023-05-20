/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.specification;

import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.util.VerbaleSearchParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CnmTOrdinanzaSpecification {


	public static Specification<CnmTOrdinanza> findBy(
		List<CnmTSoggetto> trasgressore,
		List<CnmTSoggetto> obbligatoInSolido,
		String numeroDeterminazione,
		Boolean ordinanzaProtocollata,
		Date dataOrdinanzaDa,
		Date dataOrdinanzaA,
		List<Long> idStatiOrdinanza,
		String tipoRicerca,
		Boolean annullamento,
		Boolean perAcconto,
		VerbaleSearchParam parametriVerbale
	){
		return new Specification<CnmTOrdinanza>() {
			@Override
			public Predicate toPredicate(
				Root<CnmTOrdinanza> root,
				CriteriaQuery<?> query,
				CriteriaBuilder builder
			) {
				List<Predicate> predicates = new ArrayList<>();
				final Join<CnmTOrdinanza, CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogJoin = root.join("cnmROrdinanzaVerbSogs");
				final Join<CnmTOrdinanza, CnmDTipoOrdinanza> cnmDTipoOrdinanzaJoin = root.join("cnmDTipoOrdinanza");
				final Join<CnmROrdinanzaVerbSog, CnmRVerbaleSoggetto> cnmRVerbaleSoggettoJoin = cnmROrdinanzaVerbSogJoin.join("cnmRVerbaleSoggetto");
				final Join<CnmRVerbaleSoggetto, CnmDRuoloSoggetto> cnmDRuoloSoggettoJoin = cnmRVerbaleSoggettoJoin.join("cnmDRuoloSoggetto");
				final Join<CnmRVerbaleSoggetto, CnmTSoggetto> cnmRSoggettoJoin = cnmRVerbaleSoggettoJoin.join("cnmTSoggetto");
				final Join<CnmRVerbaleSoggetto, CnmTVerbale> cnmRVerbaleJoin = cnmRVerbaleSoggettoJoin.join("cnmTVerbale");

				if (!(tipoRicerca != null && tipoRicerca.equals("RICERCA_ORDINANZA")))
					predicates.add(builder.notEqual(cnmDTipoOrdinanzaJoin.get("idTipoOrdinanza"), 1));


				if (trasgressore != null && !trasgressore.isEmpty()) {
					Subquery<CnmROrdinanzaVerbSog> subqueryROrdinanzaVerbaleSoggetto = query.subquery(CnmROrdinanzaVerbSog.class);
					Root<CnmROrdinanzaVerbSog> rootsubqueryROrdinanzaVerbaleSoggetto = subqueryROrdinanzaVerbaleSoggetto.from(CnmROrdinanzaVerbSog.class);
					Predicate predicate = builder.and(cnmRSoggettoJoin.in(trasgressore), builder.equal(cnmDRuoloSoggettoJoin.get("idRuoloSoggetto"), Constants.VERBALE_SOGGETTO_RUOLO_TRASGRESSORE_ID));
					subqueryROrdinanzaVerbaleSoggetto.select(rootsubqueryROrdinanzaVerbaleSoggetto.get("cnmTOrdinanza")).where(predicate).distinct(true);
					predicates.add(root.in(subqueryROrdinanzaVerbaleSoggetto));
				}

				if (obbligatoInSolido != null && !obbligatoInSolido.isEmpty()) {
					Subquery<CnmROrdinanzaVerbSog> subqueryROrdinanzaVerbaleSoggetto = query.subquery(CnmROrdinanzaVerbSog.class);
					Root<CnmROrdinanzaVerbSog> rootsubqueryROrdinanzaVerbaleSoggetto = subqueryROrdinanzaVerbaleSoggetto.from(CnmROrdinanzaVerbSog.class);
					Predicate predicate = builder.and(cnmRSoggettoJoin.in(obbligatoInSolido),
							builder.equal(cnmDRuoloSoggettoJoin.get("idRuoloSoggetto"), Constants.VERBALE_SOGGETTO_RUOLO_OBBLIGATO_IN_SOLIDO_ID));
					subqueryROrdinanzaVerbaleSoggetto.select(rootsubqueryROrdinanzaVerbaleSoggetto.get("cnmTOrdinanza")).where(predicate).distinct(true);
					predicates.add(root.in(subqueryROrdinanzaVerbaleSoggetto));
				}

				if (StringUtils.isNotEmpty(numeroDeterminazione))
					predicates.add(builder.equal(root.get("numDeterminazione"), numeroDeterminazione.toUpperCase()));
				
				
				//VERBALE - LUCIO ROSADINI - 2021/03/25
				if (parametriVerbale != null) {
					if (StringUtils.isNotEmpty(parametriVerbale.getNumeroVerbale())) {
						Subquery<CnmROrdinanzaVerbSog> subqueryROrdinanzaVerbaleSoggetto = query.subquery(CnmROrdinanzaVerbSog.class);
						Root<CnmROrdinanzaVerbSog> rootsubqueryROrdinanzaVerbaleSoggetto = subqueryROrdinanzaVerbaleSoggetto.from(CnmROrdinanzaVerbSog.class);
						Predicate predicate = builder.equal(cnmRVerbaleJoin.get("numVerbale"), parametriVerbale.getNumeroVerbale().toUpperCase());
						subqueryROrdinanzaVerbaleSoggetto.select(rootsubqueryROrdinanzaVerbaleSoggetto.get("cnmTOrdinanza")).where(predicate).distinct(true);
						predicates.add(root.in(subqueryROrdinanzaVerbaleSoggetto));
					}
	
					if (parametriVerbale.getLettera() != null && !parametriVerbale.getLettera().isEmpty()) {
						final Join<CnmTVerbale, CnmRVerbaleIllecito> cnmRVerbaleIllecitoJoin = cnmRVerbaleJoin.join("cnmRVerbaleIllecitos");
						final Join<CnmRVerbaleIllecito, CnmDLettera> cnmDLetteraJoin = cnmRVerbaleIllecitoJoin.join("cnmDLettera");
						predicates.add(cnmDLetteraJoin.in(parametriVerbale.getLettera()));
					}
					
					//20201119_ET aggiunto filtro per fascicoli pregressi in lavorazione/lavorati
					if(parametriVerbale.getStatiPregresso() != null && !parametriVerbale.getStatiPregresso().isEmpty()) {
						Subquery<CnmROrdinanzaVerbSog> subqueryROrdinanzaVerbaleSoggetto = query.subquery(CnmROrdinanzaVerbSog.class);
						Root<CnmROrdinanzaVerbSog> rootsubqueryROrdinanzaVerbaleSoggetto = subqueryROrdinanzaVerbaleSoggetto.from(CnmROrdinanzaVerbSog.class);

						Predicate predicate = cnmRVerbaleJoin.get("cnmDStatoPregresso").in(parametriVerbale.getStatiPregresso());

						subqueryROrdinanzaVerbaleSoggetto.select(rootsubqueryROrdinanzaVerbaleSoggetto.get("cnmTOrdinanza")).where(predicate).distinct(true);
						predicates.add(root.in(subqueryROrdinanzaVerbaleSoggetto));
					}


					if(parametriVerbale.getStatoManuale() != null && !parametriVerbale.getStatoManuale().isEmpty()) {
						/*final Join<CnmTVerbale, CnmDStatoManuale> cnmDStatoManualeJoin = cnmRVerbaleJoin.join("cnmDStatoManuale");
						predicates.add(cnmDStatoManualeJoin.in(parametriVerbale.getStatoManuale()));*/
						final Join<CnmTVerbale, CnmDStatoManuale> cnmDStatoManualeJoin = cnmRVerbaleJoin.join("cnmDStatoManuale");
						
						List<Long> idList = new ArrayList<Long>();
						for (CnmDStatoManuale statoManuale:parametriVerbale.getStatoManuale()) {
							idList.add(statoManuale.getId());
						}

						predicates.add(cnmDStatoManualeJoin.get("id").in(idList));
						
						
					}
				}
				
				// 20210325_LC se ordinanza annullamento, non mostro le ordinanza di annullamento gia esistenti				
				if (annullamento) {
					Predicate perdAnn1 = builder.notEqual(cnmDTipoOrdinanzaJoin.get("idTipoOrdinanza"), Constants.ID_TIPO_ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE);
					Predicate perdAnn2 = builder.notEqual(cnmDTipoOrdinanzaJoin.get("idTipoOrdinanza"), Constants.ID_TIPO_ORDINANZA_ANNULLAMENTO_INGIUNZIONE);					
					predicates.add(builder.and(perdAnn1, perdAnn2));				
				}

				// 20210524_LC	si stanno cercando solo ordinanze di ingiunzione ed annullamento-ingiunzione
				if (perAcconto) {
					Predicate perdAcc1 = builder.notEqual(cnmDTipoOrdinanzaJoin.get("idTipoOrdinanza"), Constants.ID_TIPO_ORDINANZA_ARCHIVIATO);
					Predicate perdAcc2 = builder.notEqual(cnmDTipoOrdinanzaJoin.get("idTipoOrdinanza"), Constants.ID_TIPO_ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE);					
					predicates.add(builder.and(perdAcc1, perdAcc2));				
				}
				
				if (ordinanzaProtocollata) {
					final Join<CnmTOrdinanza, CnmRAllegatoOrdinanza> cnmRAllegatoOrdinanzaJoin = root.join("cnmRAllegatoOrdinanzas");
					final Join<CnmRAllegatoOrdinanza, CnmTAllegato> cnmTAllegatoJoin = cnmRAllegatoOrdinanzaJoin.join("cnmTAllegato");
					final Join<CnmTAllegato, CnmDTipoAllegato> cnmDTipoAllegatoJoin = cnmTAllegatoJoin.join("cnmDTipoAllegato");
					Predicate p1 = builder.equal(cnmDTipoAllegatoJoin.get("idTipoAllegato"), TipoAllegato.LETTERA_ORDINANZA.getId());
					Predicate p2 = builder.isNotNull(cnmTAllegatoJoin.get("numeroProtocollo"));
					predicates.add(builder.and(p1, p2));
				}

				if (idStatiOrdinanza != null) {
					List<Predicate> statoPredicate = new ArrayList<>();
					final Join<CnmTOrdinanza, CnmDStatoOrdinanza> cnmDStatoOrdinanzaJoin = root.join("cnmDStatoOrdinanza");
					for (Long id : idStatiOrdinanza) {
						statoPredicate.add(builder.equal(cnmDStatoOrdinanzaJoin.get("idStatoOrdinanza"), id));
					}
					predicates.add(builder.or(statoPredicate.toArray(new Predicate[0])));
				}
				if (dataOrdinanzaDa != null && dataOrdinanzaA != null) {
					Predicate date = builder.between(root.get("dataOrdinanza"), dataOrdinanzaDa, dataOrdinanzaA);
					predicates.add(date);
				}
				query.distinct(true);
				return builder.and(predicates.toArray(new Predicate[0]));
			}
		};
	}

}
