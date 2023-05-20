/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDStatoSollecito;
import it.csi.conam.conambl.integration.entity.CnmDTipoSollecito;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmTSollecito;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmTSollecitoRepository extends CrudRepository<CnmTSollecito, Integer> {

	List<CnmTSollecito> findByCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog);

	List<CnmTSollecito> findByCnmROrdinanzaVerbSogInAndCnmDStatoSollecitoIn(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs, List<CnmDStatoSollecito> cnmDStatoSollecitos);

	List<CnmTSollecito> findByCnmROrdinanzaVerbSogInAndCnmDStatoSollecitoNotIn(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs, List<CnmDStatoSollecito> cnmDStatoSollecitos);

	List<CnmTSollecito> findByCnmROrdinanzaVerbSogAndCnmDStatoSollecitoNotIn(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog, List<CnmDStatoSollecito> cnmDStatoSollecitos);

	List<CnmTSollecito> findByCodPosizioneDebitoriaIn(List<String> codicePosizioneDebitoriaSollecitoList);

	List<CnmTSollecito> findByCnmROrdinanzaVerbSogIn(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSog);
	
	// 20210402_LC filtra per tipo sollecito
	List<CnmTSollecito> findByCnmROrdinanzaVerbSogAndCnmDTipoSollecito(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog, CnmDTipoSollecito cnmDTipoSollecito);
	List<CnmTSollecito> findByCnmROrdinanzaVerbSogInAndCnmDStatoSollecitoNotInAndCnmDTipoSollecito(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs, List<CnmDStatoSollecito> cnmDStatoSollecitos, CnmDTipoSollecito cnmDTipoSollecito);
	List<CnmTSollecito> findByCnmROrdinanzaVerbSogAndCnmDStatoSollecitoNotInAndCnmDTipoSollecito(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog, List<CnmDStatoSollecito> cnmDStatoSollecitos, CnmDTipoSollecito cnmDTipoSollecito);
	List<CnmTSollecito> findByCnmROrdinanzaVerbSogInAndCnmDTipoSollecito(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSog, CnmDTipoSollecito cnmDTipoSollecito);
	
}
