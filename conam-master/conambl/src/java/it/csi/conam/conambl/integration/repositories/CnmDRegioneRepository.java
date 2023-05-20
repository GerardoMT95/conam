/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDRegione;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmDRegioneRepository extends CrudRepository<CnmDRegione, Long> {

	@Query("Select u from CnmDRegione u where ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()))  ORDER BY u.denomRegione ASC")
	List<CnmDRegione> findAllByFineValiditaOrderByDenomRegioneAsc();

	@Query("Select u from CnmDRegione u ORDER BY u.denomRegione ASC")
	List<CnmDRegione> findAllOrderByDenomRegioneAsc();

}
