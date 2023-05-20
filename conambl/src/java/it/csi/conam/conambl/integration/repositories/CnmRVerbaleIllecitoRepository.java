/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDLettera;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleIllecito;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmRVerbaleIllecitoRepository extends JpaRepository<CnmRVerbaleIllecito, Integer> {

	List<CnmRVerbaleIllecito> findByCnmTVerbale(CnmTVerbale cnmTVerbale);

	List<CnmRVerbaleIllecito> findByCnmDLettera(CnmDLettera cnmDLettera);

	@Query("select count(u) from CnmRVerbaleIllecito u where u.cnmDLettera=?1")
	Long countByCnmDLettera(CnmDLettera cnmDLettera);

}
