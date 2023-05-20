/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDArticolo;
import it.csi.conam.conambl.integration.entity.CnmREnteNorma;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CnmDArticoloRepository extends CrudRepository<CnmDArticolo, Integer> {

	@Query("Select u from CnmDArticolo u where u.cnmREnteNorma=?1 and ((u.fineValidita is null or u.fineValidita>?2) and (u.inizioValidita is null or u.inizioValidita<=?2))  and (u.eliminato is null or u.eliminato=false)")
	List<CnmDArticolo> findByCnmREnteNormaAndFineValiditaAndNotEliminato(CnmREnteNorma cnmREnteNorma, Date dataValidita);

	@Query("Select u from CnmDArticolo u where u.cnmREnteNorma=?1 and (u.inizioValidita is null or u.inizioValidita<=?2))  and (u.eliminato is null or u.eliminato=false)")
	List<CnmDArticolo> findByCnmREnteNormaAndPregressoAndNotEliminato(CnmREnteNorma cnmREnteNorma, Date dataDiscriminantePregresso);

	@Query("Select u from CnmDArticolo u where u.cnmREnteNorma in ?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW())) and (u.eliminato is null or u.eliminato=false)")
	List<CnmDArticolo> findByCnmREnteNormaInAndFineValiditaAndNotEliminato(List<CnmREnteNorma> cnmREnteNormaList);

	@Query("Select u from CnmDArticolo u where u.descArticolo = ?1 and u.cnmREnteNorma = ?2  and (u.eliminato is null or u.eliminato=false)")
	CnmDArticolo findByDescArticoloAndCnmREnteNormaAndNotEliminato(String descArticolo, CnmREnteNorma cnmREnteNorma);

	@Query("Select u from CnmDArticolo u where u.cnmREnteNorma=?1  and (u.eliminato is null or u.eliminato=false)")
	List<CnmDArticolo> findByCnmREnteNormaAndNotEliminato(CnmREnteNorma cnmREnteNorma);

	@Query("Select u from CnmDArticolo u where u.cnmREnteNorma in ?1 and  (u.eliminato is null or u.eliminato=false)")
	List<CnmDArticolo> findByCnmREnteNormaInAndNotEliminato(List<CnmREnteNorma> cnmREnteNormaList);
}
