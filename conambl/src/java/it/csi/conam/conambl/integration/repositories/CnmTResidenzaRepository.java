/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmTResidenza;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmTResidenzaRepository extends CrudRepository<CnmTResidenza, Integer> {

	// 20200914 PP - seleziono solo la residenza di default del soggetto 
	@Query(value = "select tr from CnmTResidenza tr where tr.cnmTSoggetto = ?1 and tr.idVerbale = 0")
	CnmTResidenza findByCnmTSoggetto(CnmTSoggetto cnmTSoggetto);

	// 20200914 PP - seleziono le residenze modificate per i verbali
	@Query(value = "select tr from CnmTResidenza tr where tr.cnmTSoggetto = ?1 and tr.idVerbale != 0")
	List<CnmTResidenza> getByCnmTSoggettoPregressi(CnmTSoggetto cnmTSoggetto);
	
	// 20200914 PP - seleziono la residenze specifica per il verbale pregresso
	CnmTResidenza findByCnmTSoggettoAndIdVerbale(CnmTSoggetto cnmTSoggetto, Integer idVerbale );
	
	
}
