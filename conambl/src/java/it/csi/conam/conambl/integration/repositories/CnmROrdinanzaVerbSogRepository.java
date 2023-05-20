/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.*;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Repository
public interface CnmROrdinanzaVerbSogRepository extends CrudRepository<CnmROrdinanzaVerbSog, Integer>, JpaSpecificationExecutor<CnmROrdinanzaVerbSog> {

	List<CnmROrdinanzaVerbSog> findByCnmRVerbaleSoggettoIn(List<CnmRVerbaleSoggetto> cnmRVerbaleSoggetto);

	List<CnmROrdinanzaVerbSog> findByCnmRAllegatoOrdVerbSogsIn(List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogs);

	List<CnmROrdinanzaVerbSog> findByCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza);

	// 20210504_LC Jira 138	-	con ordinanze annullamento non e sempre OneToOne, torna una lista
	List<CnmROrdinanzaVerbSog> findByCnmRVerbaleSoggetto(CnmRVerbaleSoggetto cnmRVerbaleSoggetto);	

	@Query(value = "select ovs.* from cnm_r_ordinanza_verb_sog ovs join cnm_t_riscossione ris on ris.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog where ris.id_riscossione = ?1 ", nativeQuery = true)
	CnmROrdinanzaVerbSog findByCnmTRiscossioneByIdRiscossione(Integer idRiscossione);

	@Query("SELECT COUNT(*) FROM CnmROrdinanzaVerbSog u where u.cnmTOrdinanza=?1")
	long countSoggettiByCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza);

	List<CnmROrdinanzaVerbSog> findByCodPosizioneDebitoriaIn(List<String> codicePosizioneDebitoriaPianoRatList);

	@Query(value = "select ovs.id_verbale_soggetto from cnm_r_ordinanza_verb_sog as ovs where ovs.id_ordinanza_verb_sog=?1", nativeQuery = true)
	Integer findCnmRVerbaleSoggettoByIdOrdinanzaVerbSog(Integer idOrdinanzaVerbSog);

	@Query("select af.valoreNumber from CnmROrdinanzaVerbSog ovs join ovs.cnmRAllegatoOrdVerbSogs aovs join aovs.cnmTAllegato a join a.cnmTAllegatoFields af  join a.cnmDTipoAllegato ta join af.cnmCField f where ovs=?1 and ta.idTipoAllegato=?2 and f.idField=?3")
	BigDecimal findValoreNumberByCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog, Long idTipoAllegato, Long idField);

	List<CnmROrdinanzaVerbSog> findByCnmTSollecitosIn(List<CnmTSollecito> cnmTSollecitos);

	@Query(value = "select ovs.* from cnm_t_allegato_field af join cnm_r_allegato_ord_verb_sog aovs on af.id_allegato=aovs.id_allegato "
			+ "join cnm_r_ordinanza_verb_sog ovs on aovs.id_ordinanza_verb_sog=ovs.id_ordinanza_verb_sog "
			+ "join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog=ovs.id_stato_ord_verb_sog "
			+ "where af.id_field=12 and af.valore_number=sovs.id_elemento_elenco and sovs.id_stato_ord_verb_sog=?1 ", nativeQuery = true)
	List<CnmROrdinanzaVerbSog> findByIdStatoSentenza(Long idStatoSentenza);

	@Query("select ovs from CnmROrdinanzaVerbSog ovs join ovs.cnmRAllegatoOrdVerbSogs aovs join ovs.cnmTOrdinanza o join o.cnmDTipoOrdinanza t "
			+ "join aovs.cnmTAllegato a join a.cnmTAllegatoFields af join af.cnmCField f join ovs.cnmDStatoOrdVerbSog sovs "
			+ "where af.valoreData is not null and f.idField=13 and af.valoreData between ?1 and ?2 and t.idTipoOrdinanza <> 1 and sovs.idStatoOrdVerbSog <> 2 ")
	List<CnmROrdinanzaVerbSog> findByDataSentenza(Date inizio, Date fine);

	@Query(value = "select ovs.* from cnm_t_allegato_field af join cnm_r_allegato_ord_verb_sog aovs on af.id_allegato=aovs.id_allegato join cnm_r_ordinanza_verb_sog ovs on aovs.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog "
			+ " join cnm_t_sollecito s on s.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog join cnm_t_ordinanza o on o.id_ordinanza = ovs.id_ordinanza"
			+ " join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog "
			+ " where af.valore_data is not null and af.id_field=13 and af.valore_data between ?1 and  ?2 and o.id_tipo_ordinanza <> 1 and sovs.id_stato_ord_verb_sog <> 2 ", nativeQuery = true)
	List<CnmROrdinanzaVerbSog> findByDataSentenzaSollecito(Date inizio, Date fine);

	@Query(value = "select ovs.* from cnm_r_ordinanza_verb_sog ovs join cnm_r_allegato_ord_verb_sog aovs on aovs.id_ordinanza_verb_sog=ovs.id_ordinanza_verb_sog "
			+ " join cnm_t_allegato_field af on af.id_allegato=aovs.id_allegato join cnm_t_allegato_field af2 on af.id_allegato=af2.id_allegato "
			+ " join cnm_t_ordinanza o on o.id_ordinanza = ovs.id_ordinanza join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog "
			+ "where af.id_field=12 and af2.id_field=13 and af.valore_number=?3 and af2.valore_data between ?1 and ?2 and o.id_tipo_ordinanza <> 1 and sovs.id_stato_ord_verb_sog <> 2 ", nativeQuery = true)
	List<CnmROrdinanzaVerbSog> findByDataSentenzaAndIdStatoSentenza(Date inizio, Date fine, Long idStatoSentenza);

	@Query(value = "select ovs.* from cnm_t_allegato_field af join cnm_r_allegato_ord_verb_sog aovs on af.id_allegato=aovs.id_allegato "
			+ " join cnm_r_ordinanza_verb_sog ovs on aovs.id_ordinanza_verb_sog=ovs.id_ordinanza_verb_sog join cnm_t_ordinanza o on o.id_ordinanza = ovs.id_ordinanza "
			+ " join cnm_t_allegato_field af2 on af.id_allegato=af2.id_allegato join cnm_t_sollecito s on s.id_ordinanza_verb_sog = ovs.id_ordinanza_verb_sog "
			+ " join cnm_d_stato_ord_verb_sog sovs on sovs.id_stato_ord_verb_sog = ovs.id_stato_ord_verb_sog "
			+ " where af.id_field=12 and af2.id_field=13 and af.valore_number= ?3 and af2.valore_data between ?1  and ?2 and o.id_tipo_ordinanza <> 1 and sovs.id_stato_ord_verb_sog <> 2 ", nativeQuery = true)
	List<CnmROrdinanzaVerbSog> findByDataSentenzaAndIdStatoSentenzaSollecito(Date inizio, Date fine, Long idStatoSentenza);

}
