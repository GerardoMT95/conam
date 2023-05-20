/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDProvincia;
import it.csi.conam.conambl.integration.entity.CnmSComune;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CnmSComuneRepository extends CrudRepository<CnmSComune, Long> {

	@Query("Select u from CnmSComune u where u.codIstatComune=?1 and ((u.fineValidita is null or u.fineValidita>?2) and (u.inizioValidita is null or u.inizioValidita<=?2))")
	CnmSComune findByCodIstatComuneAndFineValiditaDataNascita(String codIstatComune, Date dataNascita);

	@Query("Select u from CnmSComune u where u.cnmDProvincia=?1 ORDER BY u.denomComune ASC")
	List<CnmSComune> findByCnmDProvinciaOrderByDenomComuneAsc(CnmDProvincia cnmDProvincia);

	@Query(value = "select c.* from  cnm_s_comune c where c.id_comune = ?1 and ?2 between c.inizio_validita and c.fine_validita", nativeQuery = true)
	CnmSComune findByidComuneAndDataNascita(long idComune, Date dataNascita);
	
	@Query(value = "select c.* from  cnm_s_comune c where c.id_comune = ?1 and ?2 between c.inizio_validita and c.fine_validita", nativeQuery = true)
	CnmSComune findByidComuneValidoInData(long idComune, Date data);
}
