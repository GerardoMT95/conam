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

public class CnmTSoggettoOrdinanzaVerbaleSpecification {

	public static Specification<CnmROrdinanzaVerbSog> findSoggettoBy(
		String numeroDeterminazione,
		String numeroProtocolloAllegato,
		Long tipoAllegato,
		String numeroProtocolloPiano, 
		Boolean ordinanzaProtocollata,
		Boolean ordinanzeSollecitate,
		Long idStatoSentenza,
		Date dataNotifica,
		Date dataNotificaA,
		Long idStatoOrdinanza,
		Date dataSentenzaDa,
		Date dataSentenzaA,
		String tipoRicerca,
		boolean isPregresso,
		VerbaleSearchParam parametriVerbale,
		Date dataCreazionePianoDa,
		Date dataCreazionePianoA
	){
		return new Specification<CnmROrdinanzaVerbSog>() {
			@Override
			public Predicate toPredicate(Root<CnmROrdinanzaVerbSog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();
				// ORDINANZA
				final Join<CnmROrdinanzaVerbSog, CnmTOrdinanza> cnmTOrdinanzaJoin = root.join("cnmTOrdinanza");
				final Join<CnmROrdinanzaVerbSog, CnmDStatoOrdVerbSog> cnmDStatoOrdVerbSogJoin = root.join("cnmDStatoOrdVerbSog");
				final Join<CnmTOrdinanza, CnmDStatoOrdinanza> cnmDStatoOrdinanzaJoin = cnmTOrdinanzaJoin.join("cnmDStatoOrdinanza");

				if (!isPregresso) {
					final Join<CnmTOrdinanza, CnmDTipoOrdinanza> cnmDTipoOrdinanzaJoin = cnmTOrdinanzaJoin.join("cnmDTipoOrdinanza");
					predicates.add(builder.notEqual(cnmDTipoOrdinanzaJoin.get("idTipoOrdinanza"), 1));

					if (ordinanzaProtocollata) {
						final Join<CnmTOrdinanza, CnmRAllegatoOrdinanza> cnmRAllegatoOrdinanzaJoin = cnmTOrdinanzaJoin.join("cnmRAllegatoOrdinanzas");
						final Join<CnmRAllegatoOrdinanza, CnmTAllegato> cnmTAllegatoJoin = cnmRAllegatoOrdinanzaJoin.join("cnmTAllegato");
						final Join<CnmTAllegato, CnmDTipoAllegato> cnmDTipoAllegatoJoin = cnmTAllegatoJoin.join("cnmDTipoAllegato");
						Predicate p1 = builder.equal(cnmDTipoAllegatoJoin.get("idTipoAllegato"), TipoAllegato.LETTERA_ORDINANZA.getId());
						Predicate p2 = builder.isNotNull(cnmTAllegatoJoin.get("numeroProtocollo"));
						predicates.add(builder.and(p1, p2));
					}
				}

				//VERBALE - Lucio Rosadini - 2021/03/25
				if (parametriVerbale != null) {
					final Join<CnmROrdinanzaVerbSog, CnmRVerbaleSoggetto> cnmRVerbaleSoggettoJoin = root.join("cnmRVerbaleSoggetto");
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

					if(parametriVerbale.getStatoManuale() != null && !parametriVerbale.getStatoManuale().isEmpty()) {
						final Join<CnmTVerbale, CnmDStatoManuale> cnmDStatoManualeJoin = cnmRVerbaleJoin.join("cnmDStatoManuale");
						
						List<Long> idList = new ArrayList<Long>();
						for (CnmDStatoManuale statoManuale:parametriVerbale.getStatoManuale()) {
							idList.add(statoManuale.getId());
						}

						predicates.add(cnmDStatoManualeJoin.get("id").in(idList));
					}
				}
				
				
				// in caso di RICERCA_SOLLECITO_RATE, deve tornare solo piani con almeno 1 rata non pagata
				if (tipoRicerca != null && (tipoRicerca.equals("CREA_PIANO") 
						|| tipoRicerca != null && tipoRicerca.equals("RICERCA_SOLLECITO") 
						|| tipoRicerca != null && tipoRicerca.equals("RICERCA_SOLLECITO_RATE") )) {
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 2));
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 3));
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 7));
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 8));
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 9));
				} else if (tipoRicerca != null && (tipoRicerca.equals("RICERCA_RISCOSSIONE_COATTIVA"))) {
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 2));
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 3));
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 7));
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 8));
					predicates.add(builder.notEqual(cnmDStatoOrdVerbSogJoin.get("idStatoOrdVerbSog"), 9));
					predicates.add(builder.notEqual(cnmDStatoOrdinanzaJoin.get("idStatoOrdinanza"), 1));
					predicates.add(builder.notEqual(cnmDStatoOrdinanzaJoin.get("idStatoOrdinanza"), 3));
					predicates.add(builder.notEqual(cnmDStatoOrdinanzaJoin.get("idStatoOrdinanza"), 5));
					predicates.add(builder.notEqual(cnmDStatoOrdinanzaJoin.get("idStatoOrdinanza"), 6));

					final Join<CnmROrdinanzaVerbSog, CnmTRiscossione> CnmTRiscossioneJoin = root.join("cnmTRiscossione", JoinType.LEFT);
					predicates.add(builder.isNull(CnmTRiscossioneJoin.get("idRiscossione")));
				}
				
				
				if (tipoRicerca != null && tipoRicerca.equals("CREA_PIANO")) {
					final Join<CnmROrdinanzaVerbSog, CnmRSoggRata> CnmRSoggRataJoin = root.join("cnmRSoggRatas", JoinType.LEFT);
					predicates.add(builder.isNull(CnmRSoggRataJoin.get("cnmROrdinanzaVerbSog")));
				}

				if (StringUtils.isNotEmpty(numeroDeterminazione))
					predicates.add(builder.equal(cnmTOrdinanzaJoin.get("numDeterminazione"), numeroDeterminazione.toUpperCase()));

				if (StringUtils.isNotEmpty(numeroProtocolloAllegato) || idStatoSentenza != null) {
					final Join<CnmROrdinanzaVerbSog, CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogJoin = root.join("cnmRAllegatoOrdVerbSogs");
					final Join<CnmRAllegatoOrdVerbSog, CnmTAllegato> cnmTAllegatoJoin = cnmRAllegatoOrdVerbSogJoin.join("cnmTAllegato");
					final Join<CnmTAllegato, CnmDTipoAllegato> cnmDTipoAllegatoJoin = cnmTAllegatoJoin.join("cnmDTipoAllegato");
					if (StringUtils.isNotEmpty(numeroProtocolloAllegato)) {
						Predicate p1 = builder.equal(cnmTAllegatoJoin.get("numeroProtocollo"), numeroProtocolloAllegato.toUpperCase());
						Predicate p2 = builder.equal(cnmDTipoAllegatoJoin.get("idTipoAllegato"), tipoAllegato);
						predicates.add(builder.and(p1, p2));
					}
					if (idStatoSentenza != null) {
						final Join<CnmTAllegato, CnmTAllegatoField> cnmTAllegatoFieldJoin = cnmTAllegatoJoin.join("cnmTAllegatoFields");
						final Join<CnmTAllegatoField, CnmCField> cnmCFieldJoin = cnmTAllegatoFieldJoin.join("cnmCField");
						final Join<CnmDStatoOrdVerbSog, CnmDElementoElenco> cnmDElementoElencoJoin = cnmDStatoOrdVerbSogJoin.join("cnmDElementoElenco");
						Predicate p1 = builder.equal(cnmCFieldJoin.get("idField"), Constants.ID_FIELD_ESITO_SENTENZA);
						Predicate p2 = builder.equal(cnmDTipoAllegatoJoin.get("idTipoAllegato"), tipoAllegato);
						Predicate p3 = builder.equal(cnmTAllegatoFieldJoin.get("valoreNumber"), idStatoSentenza);
						Predicate p4 = builder.equal(cnmDElementoElencoJoin.get("idElementoElenco"), cnmTAllegatoFieldJoin.get("valoreNumber"));

						predicates.add(builder.and(p1, p2, p3, p4));
					}
				}

				if (dataSentenzaDa != null && dataSentenzaA != null) {
					final Join<CnmROrdinanzaVerbSog, CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogJoin = root.join("cnmRAllegatoOrdVerbSogs");
					final Join<CnmRAllegatoOrdVerbSog, CnmTAllegato> cnmTAllegatoJoin = cnmRAllegatoOrdVerbSogJoin.join("cnmTAllegato");
					final Join<CnmTAllegato, CnmTAllegatoField> cnmTAllegatoFieldJoin = cnmTAllegatoJoin.join("cnmTAllegatoFields");
					final Join<CnmTAllegatoField, CnmCField> cnmCFieldJoin = cnmTAllegatoFieldJoin.join("cnmCField");
					Predicate p1 = builder.equal(cnmCFieldJoin.get("idField"), Constants.ID_FIELD_DATA_SENTENZA);
					Predicate p2 = builder.between(cnmTAllegatoFieldJoin.get("valoreData"), dataSentenzaDa, dataSentenzaA);

					predicates.add(builder.and(p1, p2));
				}
				
				
				
				// 20210329_LC sollecito piano rate 
				if (tipoRicerca != null && tipoRicerca.equals("RICERCA_SOLLECITO_RATE")) {
					
					// solo se ci sono rate non pagate
					final Join<CnmROrdinanzaVerbSog, CnmRSoggRata> cnmRSoggRataJoin = root.join("cnmRSoggRatas");
					final Join<CnmRSoggRata, CnmDStatoRata> cnmDStatoRataJoin = cnmRSoggRataJoin.join("cnmDStatoRata");
					predicates.add(builder.equal(cnmDStatoRataJoin.get("idStatoRata"), Constants.ID_STATO_RATA_NON_PAGATO));
					
					// eventuale filtro per data su piano
					if ( dataCreazionePianoDa != null && dataCreazionePianoA != null) {
						final Join<CnmRSoggRata, CnmTRata> cnmTRataJoin = cnmRSoggRataJoin.join("cnmTRata");
						final Join<CnmTRata, CnmTPianoRate> cnmTPianoRateJoin = cnmTRataJoin.join("cnmTPianoRate");
						predicates.add(builder.between(cnmTPianoRateJoin.get("dataOraInsert"), dataCreazionePianoDa, dataCreazionePianoA));
					}
					
					// eventuale filtro per num protocollo piano
					if ( numeroProtocolloPiano != null && StringUtils.isNotBlank(numeroProtocolloPiano)) {
						// 20210415 PP - modificata join per reperire il numero protocollo dall'allegato del piano rate
						final Join<CnmRSoggRata, CnmTRata> cnmTRataJoin = cnmRSoggRataJoin.join("cnmTRata");
						final Join<CnmTRata, CnmTPianoRate> cnmTPianoRateJoin = cnmTRataJoin.join("cnmTPianoRate");
						final Join<CnmTPianoRate, CnmRAllegatoPianoRate> cnmRAllegatoPianoRateJoin = cnmTPianoRateJoin.join("cnmRAllegatoPianoRates");
						final Join<CnmRAllegatoPianoRate, CnmTAllegato> cnmTAllegatoJoin = cnmRAllegatoPianoRateJoin.join("cnmTAllegato");
						predicates.add(builder.equal(cnmTAllegatoJoin.get("numeroProtocollo"), numeroProtocolloPiano.toUpperCase()));
					}
			
				}
				

				if (ordinanzeSollecitate != null && ordinanzeSollecitate) {
					root.join("cnmTSollecitos");
					query.distinct(true);
				}

				if (idStatoOrdinanza != null) {
					final Join<CnmTOrdinanza, CnmDStatoOrdinanza> cnmTStatoOrdinanzaJoin = cnmTOrdinanzaJoin.join("cnmDStatoOrdinanza");
					predicates.add(builder.equal(cnmTStatoOrdinanzaJoin.get("idStatoOrdinanza"), idStatoOrdinanza));
				}

				if (dataNotifica != null && dataNotificaA != null) {
					final Join<CnmTOrdinanza, CnmTNotifica> cnmTNotificaJoin = cnmTOrdinanzaJoin.join("cnmTNotificas");
					Predicate date = builder.between(cnmTNotificaJoin.get("dataNotifica"), dataNotifica, dataNotificaA);
					predicates.add(date);
				}

				query.distinct(true);
				return builder.and(predicates.toArray(new Predicate[0]));
			}

		};
	}
	
	
	// da RiscossioneServiceImpl
	public static Specification<CnmROrdinanzaVerbSog> findSoggettoBy(
		String numeroDeterminazione,
		String numeroProtocolloAllegato,
		Long tipoAllegato,
		Boolean ordinanzaProtocollata,
		Boolean ordinanzeSollecitate,
		Long idStatoSentenza,
		Date dataNotifica,
		Date dataNotificaA,
		Long idStatoOrdinanza,
		Date dataSentenzaDa,
		Date dataSentenzaA,
		String tipoRicerca,
		VerbaleSearchParam parametriVerbale
	) {
		return findSoggettoBy(
			numeroDeterminazione,
			numeroProtocolloAllegato,
			tipoAllegato,
			null,
			ordinanzaProtocollata,
			ordinanzeSollecitate,
			idStatoSentenza,
			dataNotifica,
			dataNotificaA,
			idStatoOrdinanza,
			dataSentenzaDa,
			dataSentenzaA,
			tipoRicerca,
			false,
			parametriVerbale,
			null,
			null
		);
	}
	
	public static Specification<CnmROrdinanzaVerbSog> findSoggettoPregressiBy(
		String numeroDeterminazione,
		String numeroProtocolloAllegato,
		Long tipoAllegato,
		String numeroProtocolloPiano, 
		Boolean ordinanzaProtocollata,
		Boolean ordinanzeSollecitate,
		Long idStatoSentenza,
		Date dataNotifica,
		Date dataNotificaA,
		Long idStatoOrdinanza,
		Date dataSentenzaDa,
		Date dataSentenzaA,
		String tipoRicerca,
		Date dataCreazionePianoDa,
		Date dataCreazionePianoA
	) {
		return findSoggettoBy(
			numeroDeterminazione,
			numeroProtocolloAllegato,
			tipoAllegato,
			numeroProtocolloPiano,
			ordinanzaProtocollata,
			ordinanzeSollecitate,
			idStatoSentenza,
			dataNotifica,
			dataNotificaA,
			idStatoOrdinanza,
			dataSentenzaDa,
			dataSentenzaA,
			tipoRicerca,
			true,
			null,
			dataCreazionePianoDa,
			dataCreazionePianoA
		);
	}

}
