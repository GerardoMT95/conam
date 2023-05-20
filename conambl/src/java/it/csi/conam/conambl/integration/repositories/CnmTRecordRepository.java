/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.*;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmTRecordRepository extends CrudRepository<CnmTRecord, Integer>, JpaSpecificationExecutor<CnmTRecord> {

	List<CnmTRecord> findByCnmTRiscossione(CnmTRiscossione cnmTRiscossione);

	List<CnmTRecord> findByCnmTFile(CnmTFile cnmTFile);

	List<CnmTRecord> findByCnmTFileOrderByCodicePartitaAscOrdineAsc(CnmTFile cnmTFile);

	List<CnmTRecord> findByCnmTRecordN3In(List<CnmTRecordN3> cnmTRecordN3s);

	CnmTRecord findByCnmTRecordN2(CnmTRecordN2 cnmTRecordN2);
}
