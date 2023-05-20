/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmTRecordRitorno;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmTRecordRitornoRepository extends CrudRepository<CnmTRecordRitorno, Long>, JpaSpecificationExecutor<CnmTRecordRitorno> {

	@Query(value = "select rr.* from cnm_t_riscossione ris join cnm_t_record r on r.id_riscossione = ris.id_riscossione join cnm_t_record_ritorno rr on rr.id_record = r.id_record where ris.id_riscossione = ?1 ", nativeQuery = true)
	List<CnmTRecordRitorno> findByCnmRiscossione(Integer idRiscossione);

}
