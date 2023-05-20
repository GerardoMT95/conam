/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmTPianoRate;
import it.csi.conam.conambl.integration.entity.CnmTRata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmTRataRepository extends JpaRepository<CnmTRata, Integer> {

	List<CnmTRata> findByCnmTPianoRateOrderByNumeroRataAsc(CnmTPianoRate dto);

	@Query("select r " + "from CnmRSoggRata sr join sr.cnmROrdinanzaVerbSog ovs join sr.cnmTRata r join sr.cnmDStatoRata dsr where dsr.idStatoRata = 1 and ovs.idOrdinanzaVerbSog = ?1 ")
	List<CnmTRata> findByIdOrdinanzaVerbSogStatoRata(Integer idOrdinanzaVerbSog);

}
