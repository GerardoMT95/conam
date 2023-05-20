/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmRAllegatoOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoOrdinanzaPK;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Repository
public interface CnmRAllegatoOrdinanzaRepository extends CrudRepository<CnmRAllegatoOrdinanza, CnmRAllegatoOrdinanzaPK> {

	List<CnmRAllegatoOrdinanza> findByCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza);

	List<CnmRAllegatoOrdinanza> findByCnmTOrdinanzaIn(List<CnmTOrdinanza> cnmTOrdinanza);

	@Query("select ao.cnmTAllegato from CnmRAllegatoOrdinanza ao where ao.cnmTOrdinanza=?1")
	List<CnmTAllegato> findCnmTAllegatosByCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza);
	
	List<CnmRAllegatoOrdinanza> findByCnmTAllegato(CnmTAllegato cnmTAllegato);
}
