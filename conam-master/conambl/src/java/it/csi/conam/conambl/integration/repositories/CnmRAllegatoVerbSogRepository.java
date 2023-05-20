/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.conam.conambl.integration.entity.CnmDTipoAllegato;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbSogPK;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Repository
public interface CnmRAllegatoVerbSogRepository extends CrudRepository<CnmRAllegatoVerbSog, CnmRAllegatoVerbSogPK> {

	List<CnmRAllegatoVerbSog> findByCnmRVerbaleSoggettoIn(List<CnmRVerbaleSoggetto> cnmRVerbaleSoggetto);

	List<CnmRAllegatoVerbSog> findByCnmRVerbaleSoggetto(CnmRVerbaleSoggetto cnmRVerbaleSoggetto);
	

	List<CnmRAllegatoVerbSog> findByCnmTAllegato(CnmTAllegato cnmTAllegato);
	
	@Query("select avs from CnmRAllegatoVerbSog avs where avs.cnmRVerbaleSoggetto = ?1 and avs.cnmTAllegato.cnmDTipoAllegato = ?2")
	List<CnmRAllegatoVerbSog> findByCnmRVerbaleSoggettoAndCnmTAllegato(CnmRVerbaleSoggetto cnmRVerbaleSoggetto, CnmDTipoAllegato cnmDTipoAllegato);
	
	@Query("select avs.cnmRVerbaleSoggetto from CnmRAllegatoVerbSog avs where avs.cnmRVerbaleSoggetto.cnmTVerbale = ?1 and avs.cnmTAllegato.cnmDTipoAllegato = ?2")
	List<CnmRVerbaleSoggetto> findCnmRVerbaleSoggettoByIdVerbaleAndIdTipoAllegato(CnmTVerbale cnmTVerbale, CnmDTipoAllegato cnmDTipoAllegato);
}
