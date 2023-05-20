/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDEnte;
import it.csi.conam.conambl.integration.entity.CnmRUserEnte;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Repository
public interface CnmRUserEnteRepository extends JpaRepository<CnmRUserEnte, Long> {

	@Query("Select u from CnmRUserEnte u where u.cnmTUser=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW())) and u.cnmDUtilizzo.idUtilizzo=?2")
	List<CnmRUserEnte> findByCnmTUserAndTipoUtilizzoAndFineValidita(CnmTUser cnmTUser, Long idUtilizzo);

	@Query("Select u from CnmRUserEnte u where u.cnmDEnte=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW())) and u.cnmDUtilizzo.idUtilizzo=?2")
	List<CnmRUserEnte> findByCnmDEnteAndTipoUtilizzoAndFineValidita(CnmDEnte cnmDEnte, Long idUtilizzo);

}
