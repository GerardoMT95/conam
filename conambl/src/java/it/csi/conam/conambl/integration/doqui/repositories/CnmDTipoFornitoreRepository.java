/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.repositories;

import it.csi.conam.conambl.integration.doqui.entity.CnmDTipoFornitore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnmDTipoFornitoreRepository extends CrudRepository<CnmDTipoFornitore, Long> {

	//CnmDTipoFornitore findById(int idFornitore);

}
