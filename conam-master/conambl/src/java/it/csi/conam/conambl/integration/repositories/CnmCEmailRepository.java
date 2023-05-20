/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;
/**
 * @author Lucio Rosadini
 *
 */


import it.csi.conam.conambl.integration.entity.CnmCEmail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnmCEmailRepository extends CrudRepository<CnmCEmail, Long> {
	
	@Query("Select u from CnmCEmail u where u.emailParamName=?1")
	CnmCEmail findByParamName(String emailParamName);
	
	@Query("Select u from CnmCEmail u where u.idEmailParam=?1")
	CnmCEmail findByParamId(Long idEmailParam);

}
