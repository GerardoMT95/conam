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

public class CnmTPianoRateSpecification {

	public static Specification<CnmTPianoRate> findPianoBy(
		String numeroDeterminazione,
		String numeroProtocolloAllegato,
		Long tipoAllegato,
		String numeroProtocolloPiano,
		List<Long> statoPiano,
		Date dataNotificaA,
		Date dataNotificaDa,
		Date dataSentenzaA,
		Date dataSentenzaDa,
		Long idStatoSentenza,
		String tipoRicerca
	) {
		VerbaleSearchParam parametriVerbale = null;
		return findPianoBy(
			numeroDeterminazione,
			numeroProtocolloAllegato,
			tipoAllegato,
			numeroProtocolloPiano,
			statoPiano,
			dataNotificaA,
			dataNotificaDa,
			dataSentenzaA,
			dataSentenzaDa,
			idStatoSentenza,
			tipoRicerca,
			parametriVerbale
		);
	}
	
	
	public static Specification<CnmTPianoRate> findPianoBy(
		String numeroDeterminazione,
		String numeroProtocolloAllegato,
		Long tipoAllegato,
		String numeroProtocolloPiano,
		List<Long> statoPiano,
		Date dataNotificaA,
		Date dataNotificaDa,
		Date dataSentenzaA,
		Date dataSentenzaDa,
		Long idStatoSentenza,
		String tipoRicerca,
		VerbaleSearchParam parametriVerbale
	) {
		return new Specification<CnmTPianoRate>() {
			@Override
			public Predicate toPredicate(Root<CnmTPianoRate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

				List<Predicate> predicates = new ArrayList<>();

				final Join<CnmTPianoRate, CnmDStatoPianoRate> cnmDStatoPianoRateJoin = root.join("cnmDStatoPianoRate");
				final Join<CnmTPianoRate, CnmTRata> cnmTRataJoin = root.join("cnmTRatas");
				final Join<CnmTRata, CnmRSoggRata> cnmRSoggRataJoin = cnmTRataJoin.join("cnmRSoggRatas");
				final Join<CnmRSoggRata, CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogJoin = cnmRSoggRataJoin.join("cnmROrdinanzaVerbSog");

				final Join<CnmROrdinanzaVerbSog, CnmDStatoOrdVerbSog> cnmDStatoOrdVerbSogJoin = cnmROrdinanzaVerbSogJoin.join("cnmDStatoOrdVerbSog");

				if (tipoRicerca != null && tipoRicerca.equals("RICONCILIA_PIANO")) {
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 2));
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 3));
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 7));
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 8));
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 9));
				} else if (tipoRicerca != null && tipoRicerca.equals("RICERCA_PIANO")) {
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 3));
				}

				if (StringUtils.isNotEmpty(numeroDeterminazione)) {
					// ORDINANZA
					final Join<CnmROrdinanzaVerbSog, CnmTOrdinanza> cnmTOrdinanzaJoin = cnmROrdinanzaVerbSogJoin.join("cnmTOrdinanza");
					predicates.add(builder.equal(cnmTOrdinanzaJoin.get("numDeterminazione"), numeroDeterminazione.toUpperCase()));
				} else if (StringUtils.isNotEmpty(numeroProtocolloAllegato) && tipoAllegato != null) {
					// ALLEGATI
					final Join<CnmROrdinanzaVerbSog, CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogJoin = cnmROrdinanzaVerbSogJoin.join("cnmRAllegatoOrdVerbSogs");
					final Join<CnmRAllegatoOrdVerbSog, CnmTAllegato> cnmTAllegatoJoin = cnmRAllegatoOrdVerbSogJoin.join("cnmTAllegato");
					final Join<CnmTAllegato, CnmDTipoAllegato> cnmDTipoAllegatoJoin = cnmTAllegatoJoin.join("cnmDTipoAllegato");
					Predicate p1 = builder.equal(cnmTAllegatoJoin.get("numeroProtocollo"), numeroProtocolloAllegato.toUpperCase());
					Predicate p2 = builder.equal(cnmDTipoAllegatoJoin.get("idTipoAllegato"), tipoAllegato);
					predicates.add(builder.and(p1, p2));
				} else if (StringUtils.isNotEmpty(numeroProtocolloPiano)) {
					final Join<CnmTPianoRate, CnmRAllegatoPianoRate> cnmRAllegatoPianoRateJoin = root.join("cnmRAllegatoPianoRates");
					final Join<CnmRAllegatoPianoRate, CnmTAllegato> cnmTAllegatoJoin = cnmRAllegatoPianoRateJoin.join("cnmTAllegato");
					final Join<CnmTAllegato, CnmDTipoAllegato> cnmDTipoAllegatoJoin = cnmTAllegatoJoin.join("cnmDTipoAllegato");
					predicates.add(builder.equal(cnmTAllegatoJoin.get("numeroProtocollo"), numeroProtocolloPiano.toUpperCase()));
					predicates.add(builder.equal(cnmDTipoAllegatoJoin.get("idTipoAllegato"), TipoAllegato.LETTERA_RATEIZZAZIONE.getId()));
				}

				if (idStatoSentenza != null) {
					final Join<CnmROrdinanzaVerbSog, CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogJoin = cnmROrdinanzaVerbSogJoin.join("cnmRAllegatoOrdVerbSogs");
					final Join<CnmRAllegatoOrdVerbSog, CnmTAllegato> cnmTAllegatoJoin = cnmRAllegatoOrdVerbSogJoin.join("cnmTAllegato");
					final Join<CnmTAllegato, CnmTAllegatoField> cnmTAllegatoFieldJoin = cnmTAllegatoJoin.join("cnmTAllegatoFields");
					final Join<CnmTAllegatoField, CnmCField> cnmCFieldJoin = cnmTAllegatoFieldJoin.join("cnmCField");
					final Join<CnmDStatoOrdVerbSog, CnmDElementoElenco> cnmDElementoElencoJoin = cnmDStatoOrdVerbSogJoin.join("cnmDElementoElenco");
					predicates.add(builder.equal(cnmCFieldJoin.get("idField"), Constants.ID_FIELD_ESITO_SENTENZA));
					predicates.add(builder.equal(cnmTAllegatoFieldJoin.get("valoreNumber"), idStatoSentenza));
					predicates.add(builder.equal(cnmDElementoElencoJoin.get("idElementoElenco"), cnmTAllegatoFieldJoin.get("valoreNumber")));
				}


				
				//VERBALE - Lucio Rosadini - 2021/03/25
				if (parametriVerbale != null) {
					final Join<CnmROrdinanzaVerbSog, CnmRVerbaleSoggetto> cnmRVerbaleSoggettoJoin = cnmROrdinanzaVerbSogJoin.join("cnmRVerbaleSoggetto");
					final Join<CnmRVerbaleSoggetto, CnmTVerbale> cnmRVerbaleJoin = cnmRVerbaleSoggettoJoin.join("cnmTVerbale");

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
				
				
				
				if (statoPiano != null) {
					List<Predicate> statoPredicate = new ArrayList<>();
					for (Long id : statoPiano) {
						statoPredicate.add(builder.equal(cnmDStatoPianoRateJoin.get("idStatoPianoRate"), id));
					}
					predicates.add(builder.or(statoPredicate.toArray(new Predicate[0])));
				}

				if (dataNotificaDa != null && dataNotificaA != null) {
					final Join<CnmROrdinanzaVerbSog, CnmTOrdinanza> cnmTOrdinanzaJoin = cnmROrdinanzaVerbSogJoin.join("cnmTOrdinanza");
					final Join<CnmTOrdinanza, CnmTNotifica> cnmTNotificaJoin = cnmTOrdinanzaJoin.join("cnmTNotificas");
					Predicate date = builder.between(cnmTNotificaJoin.get("dataNotifica"), dataNotificaDa, dataNotificaA);
					predicates.add(date);
				}
				if (dataSentenzaDa != null && dataSentenzaA != null) {
					final Join<CnmROrdinanzaVerbSog, CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogJoin = cnmROrdinanzaVerbSogJoin.join("cnmRAllegatoOrdVerbSogs");
					final Join<CnmRAllegatoOrdVerbSog, CnmTAllegato> cnmTAllegatoJoin = cnmRAllegatoOrdVerbSogJoin.join("cnmTAllegato");
					final Join<CnmTAllegato, CnmTAllegatoField> cnmTAllegatoFieldJoin = cnmTAllegatoJoin.join("cnmTAllegatoFields");
					final Join<CnmTAllegatoField, CnmCField> cnmCFieldJoin = cnmTAllegatoFieldJoin.join("cnmCField");
					Predicate p1 = builder.equal(cnmCFieldJoin.get("idField"), Constants.ID_FIELD_DATA_SENTENZA);
					Predicate p2 = builder.between(cnmTAllegatoFieldJoin.get("valoreData"), dataSentenzaDa, dataSentenzaA);

					predicates.add(builder.and(p1, p2));
				}

				query.distinct(true);
				return builder.and(predicates.toArray(new Predicate[0]));
			}

		};
	}

}
