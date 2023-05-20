/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;

@Repository
public interface CnmTOrdinanzaRepository extends CrudRepository<CnmTOrdinanza, Integer>, JpaSpecificationExecutor<CnmTOrdinanza> {

	CnmTOrdinanza findByNumDeterminazione(String numDeterminazione);

	@Query(" select distinct o from "//
			+ "CnmTOrdinanza o join "//
			+ "o.cnmRAllegatoOrdinanzas ao join "//
			+ "ao.cnmTAllegato a join "//
			+ "a.cnmDStatoAllegato sa "//
			+ "where sa.idStatoAllegato=?1 and a.dataOraInsert >= ?2")
	List<CnmTOrdinanza> findCnmTOrdinanzaAndIdStatoAllegato(Long statoAvviaSpostamentoActa, Timestamp fromDate, Pageable pageable);

	// 20210426_LC lotto2scenario6
	@Query(" select distinct o from "//
			+ "CnmTOrdinanza o join "//
			+ "o.cnmROrdinanzaVerbSogs ovs join "//
			+ "ovs.cnmRAllegatoOrdVerbSogs aovs join "//
			+ "aovs.cnmTAllegato a join "//
			+ "a.cnmDStatoAllegato sa "//
			+ "where sa.idStatoAllegato=?1 and a.dataOraInsert >= ?2")
	List<CnmTOrdinanza> findCnmTOrdinanzaSogAndIdStatoAllegato(Long statoAvviaSpostamentoActa, Timestamp fromDate, Pageable pageable);
	
	
	@Query("select o from CnmTOrdinanza o join o.cnmROrdinanzaVerbSogs ovb where ovb.idOrdinanzaVerbSog=?1")
	CnmTOrdinanza findByIdOrdinanzaVerbSog(Integer idOrdinanzaVerbSog);

	@Query("select count(o) from CnmTOrdinanza o join o.cnmDStatoOrdinanza s join o.cnmDTipoOrdinanza t "
			+ " where s.idStatoOrdinanza in ?3 and o.dataOrdinanza between ?1 and ?2 and t.idTipoOrdinanza <> 1 ")
	Long countByDataOrdinanzaAndStatiOrdinanzaIn(Date inizio, Date fine, List<Long> statiOrdinanza);
}
