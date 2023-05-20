/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDComma;
import it.csi.conam.conambl.integration.entity.CnmDLettera;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CnmDLetteraRepository extends CrudRepository<CnmDLettera, Integer> {

//	@Query("Select u from CnmDLettera u where u.cnmDComma=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW())) and (u.eliminato is null or u.eliminato=false)")
//	List<CnmDLettera> findByCnmDCommaAndFineValiditaAndNotEliminato(CnmDComma cnmDComma);

	@Query("Select u from CnmDLettera u where u.cnmDComma=?1 and ((u.fineValidita is null or u.fineValidita>?2) and (u.inizioValidita is null or u.inizioValidita<=?2)) and (u.eliminato is null or u.eliminato=false)")
	List<CnmDLettera> findByCnmDCommaAndFineValiditaAndNotEliminato(CnmDComma cnmDComma, Date dataAccertamento);

	@Query("Select u from CnmDLettera u where u.cnmDComma in ?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW())) and (u.eliminato is null or u.eliminato=false)")
	List<CnmDLettera> findByCnmDCommaInAndFineValiditaAndNotEliminato(List<CnmDComma> comma);

	@Query("Select u from CnmDLettera u where u.cnmDComma=?1 and u.lettera=?2  and (u.eliminato is null or u.eliminato=false)")
	CnmDLettera findByCnmDCommaAndLetteraAndNotEliminato(CnmDComma cnmDComma, String descLettera);

	@Query("Select u from CnmDLettera u where u.cnmDComma=?1 and (u.eliminato is null or u.eliminato=false)")
	List<CnmDLettera> findByCnmDCommaAndNotEliminato(CnmDComma cnmDComma);
	
	@Query("Select u from CnmDLettera u where u.cnmDComma=?1 and (u.inizioValidita is null or u.inizioValidita<=?2) and (u.eliminato is null or u.eliminato=false)")
	List<CnmDLettera> findByCnmDCommaAndPregressoAndNotEliminato(CnmDComma cnmDComma, Date dataDiscriminante);


	@Query("Select u from CnmDLettera u where u.cnmDComma in ?1  and (u.eliminato is null or u.eliminato=false)")
	List<CnmDLettera> findByCnmDCommaInAndNotEliminato(List<CnmDComma> comma);
}
