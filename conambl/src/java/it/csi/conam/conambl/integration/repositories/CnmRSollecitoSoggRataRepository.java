/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmRSollecitoSoggRataRepository extends JpaRepository<CnmRSollecitoSoggRata, CnmRSollecitoSoggRataPK>, JpaSpecificationExecutor<CnmRSollecitoSoggRata> {

	List<CnmRSollecitoSoggRata> findByCnmTSollecito(CnmTSollecito cnmTSollecito);

	List<CnmRSollecitoSoggRata> findByCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog);

	List<CnmRSollecitoSoggRata> findByCnmROrdinanzaVerbSogIn(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList);

	@Query("Select distinct ssr.cnmTSollecito from CnmRSollecitoSoggRata ssr where ssr.cnmTRata in (?1)")
	List<CnmTSollecito> findDistinctCnmTSollecitoByCnmTRataIn(List<CnmTRata> cnmTRataList);
	
}
