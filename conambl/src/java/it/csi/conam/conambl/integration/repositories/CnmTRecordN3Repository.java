/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmTRecord;
import it.csi.conam.conambl.integration.entity.CnmTRecordN3;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnmTRecordN3Repository extends CrudRepository<CnmTRecordN3, Integer>, JpaSpecificationExecutor<CnmTRecordN3> {

	CnmTRecordN3 findByCnmTRecord(CnmTRecord cnmTRecord);

}
