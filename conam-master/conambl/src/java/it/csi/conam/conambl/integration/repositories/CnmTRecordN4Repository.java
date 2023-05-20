/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmTRecord;
import it.csi.conam.conambl.integration.entity.CnmTRecordN4;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnmTRecordN4Repository extends CrudRepository<CnmTRecordN4, Integer>, JpaSpecificationExecutor<CnmTRecordN4> {

	CnmTRecordN4 findByCnmTRecord(CnmTRecord cnmTRecord);

}
