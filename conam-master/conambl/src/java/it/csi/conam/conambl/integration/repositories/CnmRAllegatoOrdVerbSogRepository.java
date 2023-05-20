/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Repository
public interface CnmRAllegatoOrdVerbSogRepository extends CrudRepository<CnmRAllegatoOrdVerbSog, CnmRAllegatoOrdVerbSogPK> {

	List<CnmRAllegatoOrdVerbSog> findByCnmROrdinanzaVerbSogIn(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSog);

	List<CnmRAllegatoOrdVerbSog> findByCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog);

	List<CnmRAllegatoOrdVerbSog> findByCnmTAllegato(CnmTAllegato cnmTAllegato);

	@Query("select aovs from CnmRAllegatoOrdVerbSog aovs join aovs.cnmTAllegato a join a.cnmDTipoAllegato ta join aovs.cnmROrdinanzaVerbSog ovs join ovs.cnmTOrdinanza o where o=?1 and ta=?2")
	List<CnmRAllegatoOrdVerbSog> findByCnmTOrdinanzaAndCnmDTipoAllegato(CnmTOrdinanza cnmTOrdinanza, CnmDTipoAllegato cnmDTipoAllegato);
	
	@Query("select aovs.cnmTAllegato from CnmRAllegatoOrdVerbSog aovs join aovs.cnmROrdinanzaVerbSog ovs join ovs.cnmTOrdinanza o where o=?1")
	List<CnmTAllegato> findCnmTAllegatosByCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza);
	
}
