/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmTRecord;
import it.csi.conam.conambl.integration.entity.CnmTRecordN2;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnmTRecordN2Repository extends CrudRepository<CnmTRecordN2, Integer>, JpaSpecificationExecutor<CnmTRecordN2> {

	@Query("select rec from CnmTRecordN2 rn join rn.cnmTRecord rec join rec.cnmTRiscossione ris where ris.idRiscossione=?1")
	CnmTRecord findByIdRiscossione(Integer idRiscossione);

	CnmTRecordN2 findByCnmTRecord(CnmTRecord cnmTRecord);
}
