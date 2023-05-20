/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CnmTSoggettoRepository extends JpaRepository<CnmTSoggetto, Integer>, JpaSpecificationExecutor<CnmTSoggetto> {

	CnmTSoggetto findByIdStas(BigDecimal idStas);

	List<CnmTSoggetto> findByCnmRVerbaleSoggettosIn(List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettos);

	CnmTSoggetto findByCodiceFiscale(String codiceFiscale);
	
	CnmTSoggetto findByPartitaIva(String partitaIva);

}
