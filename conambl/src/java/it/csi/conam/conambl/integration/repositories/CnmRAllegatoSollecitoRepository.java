/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmRAllegatoSollecito;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoSollecitoPK;
import it.csi.conam.conambl.integration.entity.CnmTSollecito;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmRAllegatoSollecitoRepository extends CrudRepository<CnmRAllegatoSollecito, CnmRAllegatoSollecitoPK> {

	List<CnmRAllegatoSollecito> findByCnmTSollecito(CnmTSollecito cnmTSollecito);

}
