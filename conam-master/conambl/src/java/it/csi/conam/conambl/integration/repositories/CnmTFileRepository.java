/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDStatoFile;
import it.csi.conam.conambl.integration.entity.CnmTFile;
import it.csi.conam.conambl.integration.entity.CnmTRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmTFileRepository extends CrudRepository<CnmTFile, Integer>, JpaSpecificationExecutor<CnmTFile> {

	List<CnmTFile> findByCnmDStatoFile(CnmDStatoFile cnmDStatoFile);

	CnmTFile findByCnmTRecords(CnmTRecord cnmTRecord);
}
