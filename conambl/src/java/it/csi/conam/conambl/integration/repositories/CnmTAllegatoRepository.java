/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbSog;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;

@Repository
public interface CnmTAllegatoRepository extends CrudRepository<CnmTAllegato, Integer> {

	/*
	 * @Query("select a " + // "from CnmTAllegato a " + //
	 * "join CnmRAllegatoVerbSog avs " + // "join CnmRVerbaleSoggetto vs " + //
	 * "where vs.idVerbale = ?1 and a.idIndex is not null ") List<CnmTAllegato>
	 * findCnmTAllegatoByIdVerbale(Integer idVerbale);
	 */

	List<CnmTAllegato> findByCnmRAllegatoVerbSogsIn(List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs);

	@Query(value = "select a.* from cnm_t_allegato a where a.id_stato_allegato = 5 and id_tipo_allegato = 28 and a.id_acta_master is not null and a.data_ora_insert >= ?1", nativeQuery = true)
	List<CnmTAllegato> findAllegatiComparsaByStato(Timestamp timestamp);
	

	@Query(value = "select a.* from cnm_t_allegato a "
			+ "join cnm_r_allegato_verb_sog b on a.id_allegato = b.id_allegato "
			+ "join cnm_r_verbale_soggetto c on b.id_verbale_soggetto = c.id_verbale_soggetto "
			+ "where a.id_stato_allegato = 5 and a.id_tipo_allegato = 5 and c.id_verbale = ?1", nativeQuery = true)
	List<CnmTAllegato> findAllegatiRelataAvviaSpostamento(Integer idVerbale);

	@Query(value = "select a.* from cnm_t_allegato a "
			+ "join cnm_r_allegato_verb_sog b on a.id_allegato = b.id_allegato "
			+ "join cnm_r_verbale_soggetto c on b.id_verbale_soggetto = c.id_verbale_soggetto "
			+ "where a.id_stato_allegato = 3 and a.id_tipo_allegato = 5 and c.id_verbale = ?1", nativeQuery = true)
	List<CnmTAllegato> findAllegatiRelataDaProtocollare(Integer idVerbale);

	@Query(value = "select a.* from cnm_t_allegato a "
			+ "join cnm_r_allegato_verb_sog b on a.id_allegato = b.id_allegato "
			+ "join cnm_r_verbale_soggetto c on b.id_verbale_soggetto = c.id_verbale_soggetto "
			+ "where a.id_stato_allegato in (2,7) and a.id_tipo_allegato = 5 and c.id_verbale = ?1", nativeQuery = true)
	List<CnmTAllegato> findAllegatiRelataGiaProtocollati(Integer idVerbale);

	
	@Query(value = "select a.* from cnm_t_allegato a " + //
			"join cnm_r_allegato_piano_rate apr on apr.id_allegato = a.id_allegato " + //
			"join cnm_t_piano_rate rat on rat.id_piano_rate = apr.id_piano_rate " + //
			"join cnm_t_rata r on r.id_piano_rate = rat.id_piano_rate " + //
			"join cnm_r_sogg_rata sr on sr.id_rata = r.id_rata " + //
			"join cnm_r_ordinanza_verb_sog ovs on ovs.id_ordinanza_verb_sog = sr.id_ordinanza_verb_sog " + //
			"join cnm_t_ordinanza o on o.id_ordinanza = ovs.id_ordinanza " + //
			"where  a.id_tipo_allegato in (17,18) and o.id_ordinanza = ?1 ", nativeQuery = true)
	List<CnmTAllegato> findAllegatiPiano(Integer idOrdinanza);

	@Query(value = "select a.* from cnm_t_allegato a " + //
			"join cnm_r_allegato_sollecito ras on a.id_allegato = ras.id_allegato " + //
			"join cnm_t_sollecito s on s.id_sollecito = ras.id_sollecito " + //
			"join cnm_r_ordinanza_verb_sog ovs on ovs.id_ordinanza_verb_sog = s.id_ordinanza_verb_sog " + //
			"join cnm_t_ordinanza o on o.id_ordinanza = ovs.id_ordinanza " + //
			"where a.id_tipo_allegato in (20,36) and o.id_ordinanza = ?1 ", nativeQuery = true)
	List<CnmTAllegato> findAllegatiSollecito(Integer idOrdinanza);
	
	
	// 20200708_LCs
	List<CnmTAllegato> findByObjectidSpostamentoActa(String objectidSpostamentoActa);
	
	// 20200728_LC
	List<CnmTAllegato> findByNumeroProtocolloAndObjectidSpostamentoActaIsNull(String numeroProtocollo);

	// 20200907_LC
	List<CnmTAllegato> findByNumeroProtocollo(String numeroProtocollo);
	
	
	// 20210422_LC
	@Query(value = "select a.* from cnm_t_allegato a " + //
			"join cnm_r_allegato_ordinanza ao on ao.id_allegato = a.id_allegato " + //
			"where  a.id_tipo_allegato in (11,12,34,35) and ao.id_ordinanza = ?1 ", nativeQuery = true)
	List<CnmTAllegato> findAllegatiLetteraOrdinanza(Integer idOrdinanza);
	
	@Query(value = "select a.* from cnm_t_allegato a " + //
			"join cnm_r_allegato_ord_verb_sog aovs on aovs.id_allegato = a.id_allegato " + //
			"join cnm_r_ordinanza_verb_sog ovs on ovs.id_ordinanza_verb_sog = aovs.id_ordinanza_verb_sog " + //
			"where  a.id_tipo_allegato in (38) and ovs.id_ordinanza = ?1 ", nativeQuery = true)
	List<CnmTAllegato> findAllegatiIstanzaOrdinanza(Integer idOrdinanza);

	@Query(value = "select a.* from cnm_t_allegato a " + //
			"join cnm_r_allegato_verbale av on av.id_allegato = a.id_allegato " + //
			"where  a.id_tipo_allegato in (28) and av.id_verbale = ?1 ", nativeQuery = true)
	List<CnmTAllegato> findAllegatiIstanzaVerbale(Integer idverbale);

	@Query(value = "select a.* from cnm_t_allegato a " + //
			"join cnm_r_allegato_ord_verb_sog aovs on aovs.id_allegato = a.id_allegato " + //
			"join cnm_r_ordinanza_verb_sog ovs on ovs.id_ordinanza_verb_sog = aovs.id_ordinanza_verb_sog " + //
			"where  a.id_tipo_allegato in (34) and ovs.id_ordinanza = ?1 ", nativeQuery = true)
	List<CnmTAllegato> findAllegatiIstanzaOrdinanzaAnnullamento(Integer idOrdinanza);
	
	
	// 20210524_LC lotto2scenario6
	@Query(value = "select a.* from cnm_t_allegato a " + //
			"join cnm_r_allegato_piano_rate apr on apr.id_allegato = a.id_allegato " + //
			"join cnm_t_piano_rate rat on rat.id_piano_rate = apr.id_piano_rate " + //
			"join cnm_t_rata r on r.id_piano_rate = rat.id_piano_rate " + //
			"join cnm_r_sogg_rata sr on sr.id_rata = r.id_rata " + //
			"join cnm_r_ordinanza_verb_sog ovs on ovs.id_ordinanza_verb_sog = sr.id_ordinanza_verb_sog " + //
			"where  a.id_tipo_allegato in (17,18) and ovs.id_ordinanza_verb_sog = ?1 ", nativeQuery = true)
	List<CnmTAllegato> findAllegatiPianoByIdOrdVerbSog(Integer idOrdinanzaVerbaleSoggetto);

	@Query(value = "select a.* from cnm_t_allegato a " + //
			"join cnm_r_allegato_sollecito ras on a.id_allegato = ras.id_allegato " + //
			"join cnm_t_sollecito s on s.id_sollecito = ras.id_sollecito " + //
			"join cnm_r_ordinanza_verb_sog ovs on ovs.id_ordinanza_verb_sog = s.id_ordinanza_verb_sog " + //
			"where a.id_tipo_allegato in (20,36) and ovs.id_ordinanza_verb_sog = ?1 ", nativeQuery = true)
	List<CnmTAllegato> findAllegatiSollecitoByIdOrdVerbSog(Integer idOrdinanzaVerbaleSoggetto);

	CnmTAllegato findByIdActa(String idActa);
}
