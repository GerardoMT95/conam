/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CnmTVerbaleRepository extends CrudRepository<CnmTVerbale, Integer>, JpaSpecificationExecutor<CnmTVerbale> {

	CnmTVerbale findByNumVerbale(String numeroVerbale);

	@Query(" select distinct v from "//
			+ "CnmTVerbale v	join "//
			+ "v.cnmRAllegatoVerbales av join "//
			+ "av.cnmTAllegato a join "//
			+ "a.cnmDStatoAllegato sa	"//
			+ "where sa.idStatoAllegato=?1 and a.dataOraInsert >= ?2")
	List<CnmTVerbale> findCnmTVerbaleAndIdStatoAllegato(Long idStatoAllegato, Timestamp timestamp, Pageable pageable);

	CnmTVerbale findByIdVerbale(Integer idVerbale);

	@Query(value = "select distinct v.* " + //
			"from cnm_t_verbale v " + //
			"join cnm_r_allegato_verbale r on r.id_verbale = v.id_verbale " + //
			"join cnm_t_allegato a on a.id_allegato = r.id_allegato " + //
			"where a.id_acta = ?1 ", nativeQuery = true)
	CnmTVerbale findByIdActa(String idActa);
	
	@Query(value = "select distinct v.* " + //
			"from cnm_t_verbale v " + //
			"where v.id_stato_verbale in (12, 13, 14, 15, 18, 19) ", nativeQuery = true)
	List<CnmTVerbale> findCnmTVerbalePending();


	
}
