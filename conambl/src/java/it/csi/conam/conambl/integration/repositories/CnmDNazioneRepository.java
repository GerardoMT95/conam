/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDNazione;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmDNazioneRepository extends CrudRepository<CnmDNazione, Long> {

	@Query("Select u from CnmDNazione u where (u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()) and u.idNazione <> 1 ORDER BY u.denomNazione ASC")
	List<CnmDNazione> findAllByFineValiditaOrderByDenomNazioneAsc();

	@Query("Select u from CnmDNazione u where u.denomNazione=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()))")
	CnmDNazione findByDenomNazioneAndFineValidita(String denomNazione);

	@Query("Select u from CnmDNazione u where u.denomNazione=?1")
	CnmDNazione findByDenomNazione(String denomNazione);

	CnmDNazione findByCodIstatNazione(String codIstatNazione);

	CnmDNazione findByIdNazione(Long idNazione);

	@Query("Select n from CnmTSoggetto s join s.cnmTResidenzas r join r.cnmDNazione n where s.idSoggetto = ?1 ")
	CnmDNazione findByIdSoggetto(Integer idSoggetto);

}
