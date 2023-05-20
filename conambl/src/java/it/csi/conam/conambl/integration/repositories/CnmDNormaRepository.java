/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDAmbito;
import it.csi.conam.conambl.integration.entity.CnmDNorma;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmDNormaRepository extends CrudRepository<CnmDNorma, Integer> {

	@Query("Select u from CnmDNorma u where u.riferimentoNormativo=?1 and (u.eliminato is null or u.eliminato=false)")
	CnmDNorma findByRiferimentoNormativoAndNotEliminato(String riferimentoNormativo);
	
	
	@Query("Select u from CnmDNorma u where u.riferimentoNormativo=?1 and (u.eliminato is null or u.eliminato=false) and cnmDAmbito = ?2")
	CnmDNorma findByRiferimentoNormativoAndNotEliminatoAndNotAmbito(String riferimentoNormativo, CnmDAmbito ambito);
	
	@Query("Select u.cnmDAmbito from CnmDNorma u where u.cnmDAmbito  in ?1 ")
	List<CnmDAmbito> findByCnmDAmbitoIn(List<CnmDAmbito> ambiti);

}
