/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmTUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Repository
public interface CnmTUserRepository extends CrudRepository<CnmTUser, Long> {

	@Query("Select u from CnmTUser u where u.codiceFiscale=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()))")
	CnmTUser findByCodiceFiscaleAndFineValidita(String codiceFiscale);

}
