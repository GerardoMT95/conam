/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmTNotifica;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmTPianoRate;
import it.csi.conam.conambl.integration.entity.CnmTSollecito;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository
public interface CnmTNotificaRepository extends CrudRepository<CnmTNotifica, Integer> {

	// per come fatto il db potrebbe restituirne + di una tuttavia me ne aspetto
	// solo una
	List<CnmTNotifica> findByCnmTOrdinanzas(CnmTOrdinanza cnmTOrdinanza);

	List<CnmTNotifica> findByCnmTPianoRates(CnmTPianoRate cnmTPianoRate);

	CnmTNotifica findByNumeroRaccomandata(BigDecimal numeroRaccomandata);

	List<CnmTNotifica> findByCnmTSollecitos(CnmTSollecito cnmTSollecito);

	/*
	 * RICERCA RISCOSSIONE -> RISCOSSIONE COATTIVA
	 */
	@Query(value = "SELECT COUNT (ovs.id_ordinanza_verb_sog) " + //
			"FROM cnm_t_notifica n " + //
			"join cnm_r_notifica_ordinanza rno on rno.id_notifica = n.id_notifica " + //
			"join cnm_t_ordinanza o on o.id_ordinanza = rno.id_ordinanza " + //
			"join cnm_r_ordinanza_verb_sog ovs on ovs.id_ordinanza = o.id_ordinanza  " + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog " + //
			"left outer join cnm_t_riscossione r on r.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog " + //
			"where n.data_notifica is not null and n.data_notifica between ?1 and ?2   " + //
			"and r.id_riscossione is null and sovs.id_stato_ord_verb_sog not in (2,3,7,8,9) " + //
			"and o.id_stato_ordinanza <> 1 and (o.id_stato_ordinanza = 2 or o.id_stato_ordinanza = 4)  ", nativeQuery = true)
	BigInteger countByDataNotificaRisCoattiva(Date inizio, Date fine);

	/*
	 * RICERCA RISCOSSIONE -> SOLLECITO PAGAMENTO
	 */
	@Query(value = "SELECT COUNT (ovs.id_ordinanza_verb_sog) " + //
			"FROM cnm_t_notifica n " + //
			"join cnm_r_notifica_ordinanza rno on rno.id_notifica = n.id_notifica " + //
			"join cnm_t_ordinanza o on o.id_ordinanza = rno.id_ordinanza " + //
			"join cnm_r_ordinanza_verb_sog ovs on ovs.id_ordinanza = o.id_ordinanza  " + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog " + //
			"join cnm_r_allegato_ordinanza rao on rao.id_ordinanza = o.id_ordinanza " + //
			"join cnm_t_allegato a on a.id_allegato = rao.id_allegato " + //
			"where n.data_notifica is not null and n.data_notifica between  ?1 and ?2  " + //
			"and sovs.id_stato_ord_verb_sog not in (2,3,7,8,9) and o.id_tipo_ordinanza <> 1 and a.id_tipo_allegato = 19  and a.numero_protocollo is not null ", nativeQuery = true)
	BigInteger countByDataNotifica(Date inizio, Date fine);

	/*
	 * RICERCA GESTIONE PAGAMENTI -> RICONCILIAZIONE MANUALE SOLLECITO PAGAMENTO
	 */
	@Query(value = " SELECT COUNT (ovs.id_ordinanza_verb_sog) " + //
			"FROM cnm_t_notifica n " + //
			"join cnm_r_notifica_ordinanza rno on rno.id_notifica = n.id_notifica " + //
			"join cnm_t_ordinanza o on o.id_ordinanza = rno.id_ordinanza " + //
			"join cnm_r_ordinanza_verb_sog ovs on ovs.id_ordinanza = o.id_ordinanza  " + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog   " + //
			"where n.data_notifica is not null and n.data_notifica between ?1 and ?2  " + //
			"and sovs.id_stato_ord_verb_sog not in (2,3,7,8,9) and o.id_tipo_ordinanza <> 1   ", nativeQuery = true)
	BigInteger countByDataNotificaSollecito(Date inizio, Date fine);

	/*
	 * RICERCA GESTIONE PAGAMENTI -> CREA NUOVO PIANO DI RATEIZZAZIONE
	 */
	@Query(value = "SELECT COUNT (ovs.id_ordinanza_verb_sog) " + //
			"FROM cnm_t_notifica n " + //
			"join cnm_r_notifica_ordinanza rno on rno.id_notifica = n.id_notifica " + //
			"join cnm_t_ordinanza o on o.id_ordinanza = rno.id_ordinanza " + //
			"join cnm_r_ordinanza_verb_sog ovs on ovs.id_ordinanza = o.id_ordinanza  " + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog " + //
			"join cnm_r_allegato_ordinanza rao on rao.id_ordinanza = o.id_ordinanza " + //
			"join cnm_t_allegato a on a.id_allegato = rao.id_allegato " + //
			"left outer join cnm_r_sogg_rata sr on sr.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog " + //
			"where n.data_notifica is not null and n.data_notifica between ?1 and ?2 " + //
			"and sovs.id_stato_ord_verb_sog not in (2,3,7,8,9) and o.id_tipo_ordinanza <> 1 and a.id_tipo_allegato = 19 " + //
			"and sr.id_ordinanza_verb_sog is null and a.numero_protocollo is not null ", nativeQuery = true)
	BigInteger countByDataNotificaCreaPiano(Date inizio, Date fine);

}
