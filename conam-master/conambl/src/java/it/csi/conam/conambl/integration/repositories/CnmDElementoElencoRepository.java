/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDElementoElenco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CnmDElementoElencoRepository extends CrudRepository<CnmDElementoElenco, Long> {

	List<CnmDElementoElenco> findByIdElenco(BigDecimal idElenco);
	
	CnmDElementoElenco findByIdElencoAndIdElementoElenco(BigDecimal idElenco, long idElementoElenco);
}
