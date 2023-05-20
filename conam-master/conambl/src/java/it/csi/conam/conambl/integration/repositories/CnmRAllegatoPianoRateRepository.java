/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmRAllegatoPianoRate;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoPianoRatePK;
import it.csi.conam.conambl.integration.entity.CnmTPianoRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmRAllegatoPianoRateRepository extends CrudRepository<CnmRAllegatoPianoRate, CnmRAllegatoPianoRatePK> {

	List<CnmRAllegatoPianoRate> findByCnmTPianoRate(CnmTPianoRate cnmTPianoRate);

}
