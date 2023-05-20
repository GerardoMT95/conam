/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Repository
public interface CnmRVerbaleSoggettoRepository extends JpaRepository<CnmRVerbaleSoggetto, Integer> {

	List<CnmRVerbaleSoggetto> findByCnmTVerbale(CnmTVerbale cnmTVerbale);

	CnmRVerbaleSoggetto findByCnmTVerbaleAndCnmTSoggetto(CnmTVerbale cnmTVerbale, CnmTSoggetto cnmTSoggetto);

	List<CnmRVerbaleSoggetto> findByCnmRAllegatoVerbSogsIn(List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs);

	List<CnmRVerbaleSoggetto> findByCnmTSoggetto(CnmTSoggetto cnmTSoggetto);

	@Query(value = "select sv.* from cnm_r_verbale_soggetto sv where sv.id_soggetto = ?1", nativeQuery = true)
	List<CnmRVerbaleSoggetto> findVerbaleSoggettoByIdSoggetto(Integer idSoggetto);

	@Query(value = "select sv.* from cnm_r_verbale_soggetto sv where sv.recidivo = true and sv.id_soggetto = ?1", nativeQuery = true)
	List<CnmRVerbaleSoggetto> findVerbaleSoggettoRecidivoByIdSoggetto(Integer idSoggetto);

	@Query(value = "select sv.* from cnm_r_verbale_soggetto sv where sv.recidivo = false and sv.id_soggetto = ?1", nativeQuery = true)
	List<CnmRVerbaleSoggetto> findVerbaleSoggettoNonRecidivoByIdSoggetto(Integer idSoggetto);
	
	List<CnmRVerbaleSoggetto> findByCnmROrdinanzaVerbSogsIn(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs);

	@Query(value = "select vs.id_verbale from cnm_r_verbale_soggetto as vs where vs.id_verbale_soggetto=?1", nativeQuery = true)
	Integer findCnmTVerbaleByCnmRVerbaleSoggetto(Integer idVerbaleSoggetto);

	@Query("select vs.cnmTSoggetto from CnmRVerbaleSoggetto vs join vs.cnmTSoggetto s join vs.cnmTVerbale v join vs.cnmDRuoloSoggetto r where v.idVerbale=?1 and r.idRuoloSoggetto=?2")
	List<CnmTSoggetto> findCnmTSoggettoByIdVerbaleAndIdRuoloSoggetto(Integer idVerbale, Long idRuoloSoggetto);

}
