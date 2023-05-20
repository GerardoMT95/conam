/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDAmbito;
import it.csi.conam.conambl.integration.entity.CnmDEnte;
import it.csi.conam.conambl.integration.entity.CnmDNorma;
import it.csi.conam.conambl.integration.entity.CnmREnteNorma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Repository
public interface CnmREnteNormaRepository extends JpaRepository<CnmREnteNorma, Integer> {

//	@Query("Select u from CnmREnteNorma u where u.cnmDEnte=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW())) and (u.eliminato is null or u.eliminato=false)")
//	List<CnmREnteNorma> findByCnmDEnteAndFineValiditaAndNotEliminato(CnmDEnte cnmDEnte);

	@Query("Select u from CnmREnteNorma u where u.cnmDEnte=?1 and ((u.fineValidita is null or u.fineValidita>?2) and (u.inizioValidita is null or u.inizioValidita<=?2)) and (u.eliminato is null or u.eliminato=false)")
	List<CnmREnteNorma> findByCnmDEnteAndFineValiditaAndNotEliminato(CnmDEnte cnmDEnte, Date dataAccertamento);

	@Query("Select u from CnmREnteNorma u where u.cnmDEnte=?1 and (u.inizioValidita is null or u.inizioValidita<?2)) and (u.eliminato is null or u.eliminato=false)")
	List<CnmREnteNorma> findByCnmDEnteAndPregressoAndNotEliminato(CnmDEnte cnmDEnte, Date dataDiscriminantePregresso);
	
//	@Query("Select u from CnmREnteNorma u " //
//			+ "join u.cnmDNorma n " //
//			+ "where u.cnmDEnte=?1 and n.cnmDAmbito=?2 "//
//			+ "and ((u.fineValidita is null or u.fineValidita>NOW()) "//
//			+ "and (u.inizioValidita is null or u.inizioValidita<=NOW())) " //
//			+ "and (u.eliminato is null or u.eliminato=false)")
//	List<CnmREnteNorma> findByCnmDEnteAndCnmDAmbitoAndFineValiditaAndNotEliminato(CnmDEnte cnmDEnte, CnmDAmbito cnmDAmbito);
	
	@Query("Select u from CnmREnteNorma u " //
			+ "join u.cnmDNorma n " //
			+ "where u.cnmDEnte=?1 and n.cnmDAmbito=?2 "//
			+ "and ((u.fineValidita is null or u.fineValidita>?3) "//
			+ "and (u.inizioValidita is null or u.inizioValidita<=?3)) " //
			+ "and (u.eliminato is null or u.eliminato=false)")
	List<CnmREnteNorma> findByCnmDEnteAndCnmDAmbitoAndFineValiditaAndNotEliminato(CnmDEnte cnmDEnte, CnmDAmbito cnmDAmbito, Date dataAccertamento);

	@Query("Select u from CnmREnteNorma u " //
			+ "join u.cnmDNorma n " //
			+ "where u.cnmDEnte=?1 and n.cnmDAmbito=?2 "//
			+ "and (u.inizioValidita is null or u.inizioValidita<?3)) " //
			+ "and (u.eliminato is null or u.eliminato=false)")
	List<CnmREnteNorma> findByCnmDEnteAndCnmDAmbitoAndPregressoAndNotEliminato(CnmDEnte cnmDEnte, CnmDAmbito cnmDAmbito, Date dataDiscriminantePregresso);

//	@Query("Select u from CnmREnteNorma u where u.cnmDNorma=?1 and u.cnmDEnte=?2 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()))  and (u.eliminato is null or u.eliminato=false)")
//	CnmREnteNorma findByCnmDNormaAndCnmDEnteFineValiditaAndNotEliminato(CnmDNorma cnmDNorma, CnmDEnte cnmDEnte);

	@Query("Select u from CnmREnteNorma u where u.cnmDNorma=?1 and u.cnmDEnte=?2 and ((u.fineValidita is null or u.fineValidita>?3) and (u.inizioValidita is null or u.inizioValidita<=?3))  and (u.eliminato is null or u.eliminato=false)")
	CnmREnteNorma findByCnmDNormaAndCnmDEnteFineValiditaAndNotEliminato(CnmDNorma cnmDNorma, CnmDEnte cnmDEnte, Date dataAccertamento);

	@Query("Select u from CnmREnteNorma u where u.cnmDNorma=?1 and u.cnmDEnte=?2 and (u.inizioValidita is null or u.inizioValidita<?3))  and (u.eliminato is null or u.eliminato=false)")
	CnmREnteNorma findByCnmDNormaAndCnmDEntePregressoAndNotEliminato(CnmDNorma cnmDNorma, CnmDEnte cnmDEnte, Date dataDiscriminantePregresso);

	@Query("Select u from CnmREnteNorma u where u.cnmDEnte=?1 and (u.eliminato is null or u.eliminato=false)")
	List<CnmREnteNorma> findByCnmDEnteAndNotEliminato(CnmDEnte cnmDEnte);

	@Query("Select u from CnmREnteNorma u where u.cnmDNorma=?1 and u.cnmDEnte=?2 and (u.eliminato is null or u.eliminato=false)")
	CnmREnteNorma findByCnmDNormaAndCnmDEnteAndNotEliminato(CnmDNorma cnmDNorma, CnmDEnte cnmDEnte);

	@Query("Select u from CnmREnteNorma u " //
			+ "join u.cnmDNorma n " //
			+ "where u.cnmDEnte=?1 and n.cnmDAmbito=?2 "//
			+ "and (u.eliminato is null or u.eliminato=false)")
	List<CnmREnteNorma> findByCnmDEnteAndCnmDAmbitoAndNotEliminato(CnmDEnte cnmDEnte, CnmDAmbito cnmDAmbito);
}
