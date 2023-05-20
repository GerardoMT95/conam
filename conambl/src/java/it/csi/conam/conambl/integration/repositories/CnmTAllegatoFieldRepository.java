/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTAllegatoField;

@Repository
public interface CnmTAllegatoFieldRepository extends CrudRepository<CnmTAllegatoField, Integer> {

	List<CnmTAllegatoField> findByCnmTAllegato(CnmTAllegato cnmTAllegato);

	@Query("Select af from CnmROrdinanzaVerbSog o join o.cnmRAllegatoOrdVerbSogs rao join rao.cnmTAllegato a join a.cnmTAllegatoFields af join af.cnmCField cf where o.idOrdinanzaVerbSog = ?1 and cf.idField = 15 ")
	List<CnmTAllegatoField> findByIdOrdinanzaVerbSog(Integer idOrdinanzaVerbSog);

	@Query(value = "Select SUM(af.valore_number) from cnm_t_allegato_field af, cnm_t_allegato a, cnm_r_allegato_verbale av where "
			+ "af.id_allegato = a.id_allegato and "
			+ "a.id_allegato = av.id_allegato and "
			+ "av.id_verbale = ?1 and "
			+ "a.id_tipo_allegato = 7 and "
			+ "af.id_field = 8 ", nativeQuery = true)
	BigDecimal getImportoPagatoByIdVerbale(Integer idVerbale);

	
	/*
	 * RICERCA RISCOSSIONE -> RISCOSSIONE COATTIVA
	 */
	@Query(value = "SELECT COUNT (ovs.id_ordinanza_verb_sog) " + //
			"from cnm_t_allegato_field af  " + //
			"join cnm_t_allegato_field af2 on af.id_allegato=af2.id_allegato " + //
			"join cnm_r_allegato_ord_verb_sog aovs on af.id_allegato=aovs.id_allegato " + //
			"join cnm_r_ordinanza_verb_sog ovs on aovs.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog " + //
			"join cnm_t_ordinanza o on o.id_ordinanza = ovs.id_ordinanza " + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog " + //
			"join cnm_r_verbale_soggetto vs on ovs.id_verbale_soggetto = vs.id_verbale_soggetto " + //
			"left outer join cnm_t_riscossione r on r.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog " + //
			"where af.id_field=12 and af2.id_field=13  and af2.valore_data between  ?1 and  ?2   " + //
			"and sovs.id_stato_ord_verb_sog not in (2,7,8) and r.id_riscossione is null " + //
			"and o.id_tipo_ordinanza not in (1,3,5,6) ", nativeQuery = true)
	BigInteger countByDataSentenzaRisCoattiva(Date inizio, Date fine);

	@Query(value = "SELECT COUNT (ovs.id_ordinanza_verb_sog) " + //
			"from cnm_t_allegato_field af  " + //
			"join cnm_t_allegato_field af2 on af.id_allegato=af2.id_allegato " + //
			"join cnm_r_allegato_ord_verb_sog aovs on af.id_allegato=aovs.id_allegato " + //
			"join cnm_r_ordinanza_verb_sog ovs on aovs.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog " + //
			"join cnm_t_ordinanza o on o.id_ordinanza = ovs.id_ordinanza " + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog " + //
			"join cnm_r_verbale_soggetto vs on ovs.id_verbale_soggetto = vs.id_verbale_soggetto " + //
			"join cnm_t_allegato a on a.id_allegato = aovs.id_allegato " + //
			"join cnm_d_elemento_elenco e on sovs.id_elemento_elenco = e.id_elemento_elenco " + //
			"left outer join cnm_t_riscossione r on r.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog " + //
			"where af.id_field=12 and af2.id_field=13  and af2.valore_data between  ?1 and  ?2  and af.valore_number=?3 " + //
			"and sovs.id_stato_ord_verb_sog not in (2,7,8) and r.id_riscossione is null " + //
			"and o.id_tipo_ordinanza not in (1,3,5,6) " + //
			"and a.id_tipo_allegato = 14 and e.id_elemento_elenco = af.valore_number ", nativeQuery = true)
	BigInteger countByDataSentenzaAndIdStatoSentenzaRisCoattiva(Date inizio, Date fine, Long idStatoSentenza);

	/*
	 * RICERCA RISCOSSIONE -> SOLLECITO PAGAMENTO
	 */
	@Query(value = "SELECT COUNT (ovs.id_ordinanza_verb_sog) " + //
			"from cnm_t_allegato_field af " + //
			"join cnm_t_allegato_field af2 on af.id_allegato=af2.id_allegato " + //
			"join cnm_r_allegato_ord_verb_sog aovs on af.id_allegato=aovs.id_allegato  " + //
			"join cnm_r_ordinanza_verb_sog ovs on aovs.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog  " + //
			"join cnm_t_ordinanza o on o.id_ordinanza = ovs.id_ordinanza \r\n" + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog  " + //
			"join cnm_r_verbale_soggetto vs on ovs.id_verbale_soggetto = vs.id_verbale_soggetto " + //
			"join cnm_r_allegato_ordinanza rao on rao.id_ordinanza = o.id_ordinanza " + //
			"join cnm_t_allegato a on a.id_allegato = rao.id_allegato " + //
			"where af.id_field=12 and af2.id_field=13  and af2.valore_data between  ?1 and  ?2  " + //
			"and a.id_tipo_allegato = 19 and sovs.id_stato_ord_verb_sog not in (2,3,7,8,9) and o.id_tipo_ordinanza <> 1  and a.numero_protocollo is not null ", nativeQuery = true)
	BigInteger countByDataSentenza(Date inizio, Date fine);

	@Query(value = "SELECT COUNT (ovs.id_ordinanza_verb_sog) " + //
			"from cnm_t_allegato_field af " + //
			"join cnm_t_allegato_field af2 on af.id_allegato=af2.id_allegato " + //
			"join cnm_r_allegato_ord_verb_sog aovs on af.id_allegato=aovs.id_allegato  " + //
			"join cnm_r_ordinanza_verb_sog ovs on aovs.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog  " + //
			"join cnm_t_ordinanza o on o.id_ordinanza = ovs.id_ordinanza \r\n" + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog  " + //
			"join cnm_r_verbale_soggetto vs on ovs.id_verbale_soggetto = vs.id_verbale_soggetto " + //
			"join cnm_r_allegato_ordinanza rao on rao.id_ordinanza = o.id_ordinanza " + //
			"join cnm_t_allegato a on a.id_allegato = rao.id_allegato " + //
			"join cnm_d_elemento_elenco e on sovs.id_elemento_elenco = e.id_elemento_elenco " + //
			"where af.id_field=12 and af2.id_field=13  and af2.valore_data between  ?1 and  ?2  and af.valore_number=?3 " + //
			"and a.id_tipo_allegato = 19 and sovs.id_stato_ord_verb_sog not in (2,3,7,8,9) and o.id_tipo_ordinanza <> 1  and a.numero_protocollo is not null " + //
			"and a.id_tipo_allegato = 14 and e.id_elemento_elenco = af.valore_number ", nativeQuery = true)
	BigInteger countByDataSentenzaAndIdStatoSentenza(Date inizio, Date fine, Long idStatoSentenza);

	/*
	 * RICERCA GESTIONE PAGAMENTI -> CREA NUOVO PIANO DI RATEIZZAZIONE
	 */

	@Query(value = "SELECT COUNT (ovs.id_ordinanza_verb_sog) " + //
			"from cnm_t_allegato_field af " + //
			"join cnm_t_allegato_field af2 on af.id_allegato=af2.id_allegato " + //
			"join cnm_r_allegato_ord_verb_sog aovs on af.id_allegato=aovs.id_allegato  " + //
			"join cnm_r_ordinanza_verb_sog ovs on aovs.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog  " + //
			"join cnm_t_ordinanza o on o.id_ordinanza = ovs.id_ordinanza  " + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog  " + //
			"join cnm_r_verbale_soggetto vs on ovs.id_verbale_soggetto = vs.id_verbale_soggetto " + //
			"join cnm_r_allegato_ordinanza rao on rao.id_ordinanza = o.id_ordinanza " + //
			"join cnm_t_allegato a on a.id_allegato = rao.id_allegato " + //
			"left outer join cnm_r_sogg_rata sr on sr.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog " + //
			"where af.id_field=12 and af2.id_field=13  and af2.valore_data between ?1 and  ?2  " + //
			"and a.id_tipo_allegato = 19 and sovs.id_stato_ord_verb_sog not in (2,3,7,8,9) and o.id_tipo_ordinanza <> 1  " + //
			"and sr.id_ordinanza_verb_sog is  null and a.numero_protocollo is not null ", nativeQuery = true)
	BigInteger countByDataSentenzaCreaPiano(Date inizio, Date fine);

	@Query(value = "SELECT COUNT (ovs.id_ordinanza_verb_sog) " + //
			"from cnm_t_allegato_field af " + //
			"join cnm_t_allegato_field af2 on af.id_allegato=af2.id_allegato " + //
			"join cnm_r_allegato_ord_verb_sog aovs on af.id_allegato=aovs.id_allegato  " + //
			"join cnm_r_ordinanza_verb_sog ovs on aovs.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog  " + //
			"join cnm_t_ordinanza o on o.id_ordinanza = ovs.id_ordinanza  " + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog  " + //
			"join cnm_r_verbale_soggetto vs on ovs.id_verbale_soggetto = vs.id_verbale_soggetto " + //
			"join cnm_r_allegato_ordinanza rao on rao.id_ordinanza = o.id_ordinanza " + //
			"join cnm_t_allegato a on a.id_allegato = aovs.id_allegato " + //
			"join cnm_d_elemento_elenco e on sovs.id_elemento_elenco = e.id_elemento_elenco " + //
			"left outer join cnm_r_sogg_rata sr on sr.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog " + //
			"where af.id_field=12 and af2.id_field=13  and af2.valore_data between ?1 and  ?2  and af.valore_number=?3 " + //
			"and a.id_tipo_allegato = 19 and sovs.id_stato_ord_verb_sog not in (2,3,7,8,9) and o.id_tipo_ordinanza <> 1  " + //
			"and sr.id_ordinanza_verb_sog is  null and a.numero_protocollo is not null " + //
			"and a.id_tipo_allegato = 14 and e.id_elemento_elenco = af.valore_number ", nativeQuery = true)
	BigInteger countByDataSentenzaAndIdStatoSentenzaCreaPiano(Date inizio, Date fine, Long idStatoSentenza);

	/*
	 * RICERCA GESTIONE PAGAMENTI -> RICONCILIAZIONE MANUALE SOLLECITO PAGAMENTO
	 */

	@Query(value = " SELECT COUNT (ovs.id_ordinanza_verb_sog) " + //
			"from cnm_t_allegato_field af " + //
			"join cnm_r_allegato_ord_verb_sog aovs on af.id_allegato=aovs.id_allegato " + //
			"join cnm_c_field f on f.id_field=af.id_field " + //
			"join cnm_r_ordinanza_verb_sog ovs on aovs.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog " + //
			"join cnm_t_ordinanza o on o.id_ordinanza = ovs.id_ordinanza " + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog " + //
			"join cnm_r_verbale_soggetto vs on ovs.id_verbale_soggetto = vs.id_verbale_soggetto " + //
			"where af.valore_data is not null and f.id_field=13 and af.valore_data between ?1 and ?2 " + //
			"and sovs.id_stato_ord_verb_sog not in (2,3,7,8,9) and o.id_tipo_ordinanza <> 1   ", nativeQuery = true)
	BigInteger countByDataSentenzaSollecito(Date inizio, Date fine);

	@Query(value = " SELECT COUNT (ovs.id_ordinanza_verb_sog) " + //
			"from cnm_t_allegato_field af " + //
			"join cnm_r_allegato_ord_verb_sog aovs on af.id_allegato=aovs.id_allegato " + //
			"join cnm_c_field f on f.id_field=af.id_field " + //
			"join cnm_r_ordinanza_verb_sog ovs on aovs.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog " + //
			"join cnm_t_ordinanza o on o.id_ordinanza = ovs.id_ordinanza " + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog " + //
			"join cnm_r_verbale_soggetto vs on ovs.id_verbale_soggetto = vs.id_verbale_soggetto " + //
			"join cnm_t_allegato a on a.id_allegato = aovs.id_allegato " + //
			"join cnm_d_elemento_elenco e on sovs.id_elemento_elenco = e.id_elemento_elenco " + //
			"where af.valore_data is not null and f.id_field=13 and af.valore_data between ?1 and ?2 and af.valore_number=?3 " + //
			"and sovs.id_stato_ord_verb_sog not in (2,3,7,8,9) and o.id_tipo_ordinanza <> 1   " + //
			"and a.id_tipo_allegato = 14 and e.id_elemento_elenco = af.valore_number ", nativeQuery = true)
	BigInteger countByDataSentenzaAndIdStatoSentenzaSollecito(Date inizio, Date fine, Long idStatoSentenza);
}
