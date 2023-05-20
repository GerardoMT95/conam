/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.repositories;

import it.csi.conam.conambl.integration.doqui.entity.CnmTRichiesta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnmTRichiestaRepository extends CrudRepository<CnmTRichiesta, Integer>{

//	CnmTRichiesta findById(Long idRichiesta);
//
//	void updateColumnsStatoRichiesta(CnmTRichiesta dto);

}
