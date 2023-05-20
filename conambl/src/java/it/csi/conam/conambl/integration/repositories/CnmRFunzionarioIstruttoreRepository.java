/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmRFunzionarioIstruttore;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnmRFunzionarioIstruttoreRepository extends CrudRepository<CnmRFunzionarioIstruttore, Integer> {

	@Query("Select u from CnmRFunzionarioIstruttore u where u.cnmTVerbale=?1 and ((u.fineAssegnazione is null or u.fineAssegnazione>NOW()) and (u.inizioAssegnazione is null or u.inizioAssegnazione<=NOW()))")
	CnmRFunzionarioIstruttore findByCnmTVerbaleAndDataAssegnazione(CnmTVerbale cnmTVerbale);

	CnmRFunzionarioIstruttore findByCnmTUserAndCnmTVerbale(CnmTUser cnmTUserIstruttore, CnmTVerbale cnmTVerbale);
}
