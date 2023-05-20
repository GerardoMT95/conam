/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDStatoSollecito;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmDStatoSollecitoRepository extends CrudRepository<CnmDStatoSollecito, Long> {
	
	// 20200713_LC
	@Query(value = "select ss from CnmDStatoSollecito ss where ss.idStatoSollecito > ?1")
	List<CnmDStatoSollecito> findAllWhereStatoMoreThan(long lowerBound);

	List<CnmDStatoSollecito> findByIdStatoSollecitoIn(List<Long> statiToInclude);

}
