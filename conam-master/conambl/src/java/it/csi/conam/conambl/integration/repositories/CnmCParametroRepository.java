/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmCParametro;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmCParametroRepository extends CrudRepository<CnmCParametro, Long> {

	@Query("Select u from CnmCParametro u where u.idParametro=?1 and (u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW())")
	CnmCParametro findByIdParametro(Long id);

	@Query("Select u from CnmCParametro u where u.idParametro in ?1 and (u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW())")
	List<CnmCParametro> findByIdParametroIn(List<Long> id);

}
