/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDStatoPianoRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmDStatoPianoRateRepository extends CrudRepository<CnmDStatoPianoRate, Long> {

	@Query("select sp from CnmDStatoPianoRate sp where sp.idStatoPianoRate in ?1")
	List<CnmDStatoPianoRate> findByIdStatoPianoRateIn(List<Long> idStatoPianoRates);

	List<CnmDStatoPianoRate> findByIdStatoPianoRateNotIn(List<Long> statiToExclude);
}
