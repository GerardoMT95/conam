/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDArticolo;
import it.csi.conam.conambl.integration.entity.CnmDComma;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CnmDCommaRepository extends CrudRepository<CnmDComma, Integer> {

//	@Query("Select u from CnmDComma u where u.cnmDArticolo=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW())) and (u.eliminato is null or u.eliminato=false) ")
//	List<CnmDComma> findByCnmDArticoloAndFineValiditaAndNotEliminato(CnmDArticolo cnmDArticolo);

	@Query("Select u from CnmDComma u where u.cnmDArticolo=?1 and ((u.fineValidita is null or u.fineValidita>?2) and (u.inizioValidita is null or u.inizioValidita<=?2)) and (u.eliminato is null or u.eliminato=false) ")
	List<CnmDComma> findByCnmDArticoloAndFineValiditaAndNotEliminato(CnmDArticolo cnmDArticolo, Date dataAccertamento);

	@Query("Select u from CnmDComma u where u.cnmDArticolo=?1 and (u.eliminato is null or u.eliminato=false) ")
	List<CnmDComma> findByCnmDArticoloAndNotEliminato(CnmDArticolo cnmDArticolo);

	@Query("Select u from CnmDComma u where u.cnmDArticolo=?1 and  (u.inizioValidita is null or u.inizioValidita<=?2) and (u.eliminato is null or u.eliminato=false) ")
	List<CnmDComma> findByCnmDArticoloAndPregressoAndNotEliminato(CnmDArticolo cnmDArticolo, Date dataDeterminante);

	@Query("Select u from CnmDComma u where u.cnmDArticolo in ?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW())) and (u.eliminato is null or u.eliminato=false)")
	List<CnmDComma> findByCnmDArticoloInAndFineValiditaAndNotEliminato(List<CnmDArticolo> articoli);

	@Query("Select u from CnmDComma u where u.cnmDArticolo = ?1 and  u.descComma=?2  and (u.eliminato is null or u.eliminato=false)")
	CnmDComma findByCnmDArticoloAndDescCommaAndNotEliminato(CnmDArticolo cnmDArticolo, String descComma);

	@Query("Select u from CnmDComma u where u.cnmDArticolo in ?1 and (u.eliminato is null or u.eliminato=false)")
	List<CnmDComma> findByCnmDArticoloInAndNotEliminato(List<CnmDArticolo> articoli);

}
