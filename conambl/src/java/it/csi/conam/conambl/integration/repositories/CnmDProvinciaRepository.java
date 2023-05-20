/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDProvincia;
import it.csi.conam.conambl.integration.entity.CnmDRegione;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CnmDProvinciaRepository extends CrudRepository<CnmDProvincia, Long> {

	@Query("Select u from CnmDProvincia u where u.cnmDRegione=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()))  ORDER BY u.denomProvincia ASC")
	List<CnmDProvincia> findByCnmDRegioneAndFineValiditaOrderByDenomProvinciaAsc(CnmDRegione cnmRegione);

	@Query("Select u from CnmDProvincia u where u.cnmDRegione=?1 ORDER BY u.denomProvincia ASC")
	List<CnmDProvincia> findByCnmDRegioneOrderByDenomProvinciaAsc(CnmDRegione cnmRegione);
	
	@Query("Select u from CnmDProvincia u where u.cnmDRegione=?1 and ((u.fineValidita is null or u.fineValidita>?2) and (u.inizioValidita is null or u.inizioValidita<=?2))  ORDER BY u.denomProvincia ASC")
	List<CnmDProvincia> findByCnmDRegioneAndValidaInDataOrderByDenomProvinciaAsc(CnmDRegione cnmRegione, Date dataOraAccertamento);


}
