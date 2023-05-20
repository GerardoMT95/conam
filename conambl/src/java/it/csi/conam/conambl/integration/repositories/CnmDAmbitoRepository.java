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
public interface CnmDAmbitoRepository extends CrudRepository<CnmDAmbito, Integer> {

	@Query("Select u from CnmDAmbito u where u.descAmbito = ?1 and (u.eliminato is null or u.eliminato=false)")
	List<CnmDAmbito> findByDescAmbitoAndNotEliminato(String descAmbito);

	@Query("Select u from CnmDAmbito u where   (u.eliminato is null or u.eliminato=false)")
	List<CnmDAmbito> findAllNotEliminato();

	@Query("select a from CnmDNorma n join n.cnmDAmbito a where n=?1 and (a.eliminato is null or a.eliminato=false)")
	CnmDAmbito findByCnmDNormaAndNotEliminato(CnmDNorma cnmDNorma);

	@Query(value = "select  a.* from cnm_d_norma n join cnm_d_ambito a on a.id_ambito = n.id_ambito where n.eliminato = false or n.eliminato is null", nativeQuery = true)
	List<CnmDAmbito> findAllConNorma();

	@Query(value = "select  a.* from cnm_d_norma n join cnm_d_ambito a on a.id_ambito = n.id_ambito where a.id_ambito = ?1 ", nativeQuery = true)
	List<CnmDAmbito> findByIdAmbito(Integer idAmbito);
}
