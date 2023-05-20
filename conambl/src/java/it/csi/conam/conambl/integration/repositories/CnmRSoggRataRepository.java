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
public interface CnmRSoggRataRepository extends JpaRepository<CnmRSoggRata, CnmRSoggRataPK>, JpaSpecificationExecutor<CnmRSoggRata> {

	List<CnmRSoggRata> findByCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog);

	List<CnmRSoggRata> findByCnmROrdinanzaVerbSogIn(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList);

	List<CnmRSoggRata> findByCnmROrdinanzaVerbSogInAndCnmDStatoRataNotIn(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList, List<CnmDStatoRata> cnmDStatoRatas);

	List<CnmRSoggRata> findByCnmROrdinanzaVerbSogAndCnmDStatoRataNotIn(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog, List<CnmDStatoRata> cnmDStatoRatas);

	@Query("Select distinct u.cnmROrdinanzaVerbSog from CnmRSoggRata u where u.cnmTRata in (?1)")
	List<CnmROrdinanzaVerbSog> findDistinctCnmROrdinanzaVerbSogByCnmTRataIn(List<CnmTRata> cnmTRataList);

	@Query("Select u from CnmRSoggRata u where u.cnmTRata in (?1)")
	List<CnmRSoggRata> findByCnmTRataIn(List<CnmTRata> cnmTRataList);

	List<CnmRSoggRata> findByCodPosizioneDebitoriaIn(List<String> codPosizioneDebitoria);

	@Query("Select u from CnmRSoggRata u join u.cnmTRata r where u.cnmTRata in (?1) order by r.numeroRata asc ")
	List<CnmRSoggRata> findCnmRSoggRataByCnmTRataInOrderByNumeroRata(List<CnmTRata> cnmTRataList);

	CnmRSoggRata findByCnmROrdinanzaVerbSogAndCnmTRata(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog, CnmTRata cnmTRata);

}
