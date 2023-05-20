/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmTAcconto;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmTAccontoRepository extends CrudRepository<CnmTAcconto, Integer>, JpaSpecificationExecutor<CnmTVerbale> {
	
	@Query(value = "select ac.* from cnm_t_acconto ac where ac.id_ordinanza = ?1", nativeQuery = true)
	List<CnmTAcconto> findByIdOrdinanza(Integer idOrdinanza);
}
