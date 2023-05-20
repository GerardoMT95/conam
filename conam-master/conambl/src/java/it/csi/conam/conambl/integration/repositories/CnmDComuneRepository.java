/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.conam.conambl.integration.entity.CnmDComune;
import it.csi.conam.conambl.integration.entity.CnmDProvincia;

@Repository
public interface CnmDComuneRepository extends CrudRepository<CnmDComune, Long> {

	@Query("Select u from CnmDComune u where u.cnmDProvincia=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW())) ORDER BY u.denomComune ASC")
	List<CnmDComune> findByCnmDProvinciaAndFineValiditaOrderByDenomComuneAsc(CnmDProvincia cnmDProvincia);

	@Query("Select u from CnmDComune u where u.cnmDProvincia=?1 ORDER BY u.denomComune ASC")
	List<CnmDComune> findByCnmDProvinciaOrderByDenomComuneAsc(CnmDProvincia cnmDProvincia);

	@Query("Select u from CnmDComune u where u.codIstatComune=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()))")
	CnmDComune findByCodIstatComuneAndFineValidita(String codIstatComune);

	@Query("Select u from CnmDComune u where u.codIstatComune=?1 and ((u.fineValidita is null or u.fineValidita>?2) and (u.inizioValidita is null or u.inizioValidita<=?2))")
	CnmDComune findByCodIstatComuneAndFineValiditaDataNascita(String codIstatComune, Date dataNascita);

	CnmDComune findByIdComune(Long idComune);

	@Query("Select u from CnmDComune u where u.cnmDProvincia=?1 and ((u.fineValidita is null or u.fineValidita>?2) and (u.inizioValidita is null or u.inizioValidita<=?2)) ORDER BY u.denomComune ASC")
	List<CnmDComune> findByCnmDProvinciaAndValidaOrderByDenomComuneAsc(CnmDProvincia cnmDProvincia,
			Date convertToDateViaInstant);
	
	@Query("Select u from CnmDComune u where u.cnmDProvincia in ?1 and ((u.fineValidita is null or u.fineValidita>?2) and (u.inizioValidita is null or u.inizioValidita<=?2)) ORDER BY u.denomComune ASC")
	List<CnmDComune> findByCnmDProvinciasInAndValidaOrderByDenomComuneAsc(List<CnmDProvincia> cnmDProvincias,
			Date convertToDateViaInstant);

	@Query("Select u from CnmDComune u where u.cnmDProvincia in ?1 ORDER BY u.denomComune ASC")
	List<CnmDComune> findByCnmDProvinciasInOrderByDenomComuneAsc(List<CnmDProvincia> cnmDProvincias);

	@Query("Select u from CnmDComune u where u.idComune=?1 and ((u.fineValidita is null or u.fineValidita>?2) and (u.inizioValidita is null or u.inizioValidita<=?2))")
	CnmDComune findByidComuneAndData(long idComune, Date data);


}
