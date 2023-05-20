/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDElementoElenco;
import it.csi.conam.conambl.integration.entity.CnmDStatoOrdVerbSog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmDStatoOrdVerbSogRepository extends CrudRepository<CnmDStatoOrdVerbSog, Long> {

	CnmDStatoOrdVerbSog findByCnmDElementoElenco(CnmDElementoElenco cnmDElementoElenco);

	List<CnmDStatoOrdVerbSog> findByCnmDElementoElencoIn(List<CnmDElementoElenco> cnmDElementoElenco);

}
