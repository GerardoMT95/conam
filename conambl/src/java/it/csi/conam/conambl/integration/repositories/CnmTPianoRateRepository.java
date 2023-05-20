/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmTPianoRate;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository
public interface CnmTPianoRateRepository extends CrudRepository<CnmTPianoRate, Integer>, JpaSpecificationExecutor<CnmTPianoRate> {

	@Query(value = "select count(distinct pr.id_piano_rate) " + //
			"from cnm_t_piano_rate pr " + //
			"join cnm_d_stato_piano_rate spr on  pr.id_stato_piano_rate = spr.id_stato_piano_rate " + //
			"join cnm_t_rata r on r.id_piano_rate = pr.id_piano_rate " + //
			"join cnm_r_sogg_rata sr on sr.id_rata = r.id_rata " + //
			"join cnm_r_ordinanza_verb_sog ovs on ovs.id_ordinanza_verb_sog = sr.id_ordinanza_verb_sog " + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog " + //
			"where spr.id_stato_piano_rate in ?3 and cast(pr.data_ora_insert as date) between ?1 and ?2  " + //
			"and sovs.id_stato_ord_verb_sog not in ?4 ", nativeQuery = true)
	BigInteger countByDataCreazioneAndIdStatoPianoIn(Date inizio, Date fine, List<Long> idStatoPianos, List<Long> statoOridnanzaVerSog);

	@Query(value = "select pr.* " + //
			"from cnm_t_piano_rate pr " + //
			"join cnm_d_stato_piano_rate spr on  pr.id_stato_piano_rate = spr.id_stato_piano_rate " + //
			"join cnm_t_rata r on r.id_piano_rate = pr.id_piano_rate " + //
			"join cnm_r_sogg_rata sr on sr.id_rata = r.id_rata " + //
			"join cnm_r_ordinanza_verb_sog ovs on ovs.id_ordinanza_verb_sog = sr.id_ordinanza_verb_sog " + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog " + //
			"where spr.id_stato_piano_rate in ?3 and cast(pr.data_ora_insert as date) between ?1 and ?2  " + //
			"and sovs.id_stato_ord_verb_sog not in ?4 ", nativeQuery = true)
	List<CnmTPianoRate> findByDataCreazioneAndIdStatoPianoIn(Date inizio, Date fine, List<Long> idStatoPianos, List<Long> statoOridnanzaVerSog);

	@Query(value = "select count(distinct pr.id_piano_rate) " + //
			"from cnm_t_piano_rate pr " + //
			"join cnm_d_stato_piano_rate spr on pr.id_stato_piano_rate = spr.id_stato_piano_rate " + //
			"join cnm_t_rata r on r.id_piano_rate = pr.id_piano_rate " + //
			"join cnm_r_sogg_rata sr on sr.id_rata = r.id_rata " + //
			"join cnm_r_ordinanza_verb_sog ovs on ovs.id_ordinanza_verb_sog = sr.id_ordinanza_verb_sog " + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog " + //
			"join cnm_t_ordinanza o on o.id_ordinanza = ovs.id_ordinanza " + //
			"join cnm_r_notifica_ordinanza rno on rno.id_ordinanza = o.id_ordinanza " + //
			"join cnm_t_notifica n on n.id_notifica = rno.id_notifica " + //
			"where sovs.id_stato_ord_verb_sog not in ?4 and spr.id_stato_piano_rate in ?3 " + //
			"and n.data_notifica between ?1 and ?2  ", nativeQuery = true)
	BigInteger countByDataNotificaOrdinanzaAndIdStatoPianoIn(Date inizio, Date fine, List<Long> idStatoPianos, List<Long> statoOridnanzaVerSog);

	@Query(value = "select count(distinct pr.id_piano_rate) " + //
			"from cnm_t_piano_rate pr " + //
			"join cnm_d_stato_piano_rate spr on pr.id_stato_piano_rate = spr.id_stato_piano_rate " + //
			"join cnm_t_rata r on r.id_piano_rate = pr.id_piano_rate " + //
			"join cnm_r_sogg_rata sr on sr.id_rata = r.id_rata " + //
			"join cnm_r_ordinanza_verb_sog ovs on ovs.id_ordinanza_verb_sog = sr.id_ordinanza_verb_sog " + //
			"join cnm_r_allegato_ord_verb_sog aovs on ovs.id_ordinanza_verb_sog = aovs.id_ordinanza_verb_sog " + //
			"join cnm_t_allegato a on a.id_allegato = aovs.id_allegato " + //
			"join cnm_t_allegato_field af on af.id_allegato = a.id_allegato " + //
			"join cnm_c_field f on af.id_field = f.id_field " + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog " + //
			"where sovs.id_stato_ord_verb_sog not in ?4 and spr.id_stato_piano_rate in ?3 " + //
			"and af.id_field=13  and af.valore_data between  ?1 and ?2   ", nativeQuery = true)
	BigInteger countByDataSentenzaAndIdStatoPianoIn(Date inizio, Date fine, List<Long> idStatoPianos, List<Long> statoOridnanzaVerSog);

	@Query(value = "select count(distinct pr.id_piano_rate) " + //
			"from cnm_t_piano_rate pr " + //
			"join cnm_d_stato_piano_rate spr on pr.id_stato_piano_rate = spr.id_stato_piano_rate " + //
			"join cnm_t_rata r on r.id_piano_rate = pr.id_piano_rate " + //
			"join cnm_r_sogg_rata sr on sr.id_rata = r.id_rata " + //
			"join cnm_r_ordinanza_verb_sog ovs on ovs.id_ordinanza_verb_sog = sr.id_ordinanza_verb_sog " + //
			"join cnm_r_allegato_ord_verb_sog aovs on ovs.id_ordinanza_verb_sog = aovs.id_ordinanza_verb_sog " + //
			"join cnm_t_allegato a on a.id_allegato = aovs.id_allegato  " + //
			"join cnm_t_allegato_field af on af.id_allegato = a.id_allegato  " + //
			"join cnm_t_allegato_field af2 on af.id_allegato=af2.id_allegato " + //
			"join cnm_c_field f on af.id_field = f.id_field  " + //
			"join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog " + //
			"join cnm_d_elemento_elenco e on e.id_elemento_elenco = sovs.id_elemento_elenco " + //
			"where af.id_field=12 and sovs.id_stato_ord_verb_sog not in ?5 and e.id_elemento_elenco = af.valore_number " + //
			"and spr.id_stato_piano_rate in ?4 and af2.id_field=13  and af2.valore_data between  ?1 and ?2 " + //
			"and af.valore_number = ?3 ", nativeQuery = true)
	BigInteger countByDataSentenzaAndIdStatoSentenzaAndIdStatoPianoIn(Date inizio, Date fine, BigDecimal idStatoSentenza, List<Long> idStatoPianos, List<Long> statoOridnanzaVerSog);

}
