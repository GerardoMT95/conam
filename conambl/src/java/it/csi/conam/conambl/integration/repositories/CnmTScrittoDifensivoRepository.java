/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmTScrittoDifensivo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmTScrittoDifensivoRepository extends CrudRepository<CnmTScrittoDifensivo, Integer>, JpaSpecificationExecutor<CnmTScrittoDifensivo> {
	
	List<CnmTScrittoDifensivo> findByNumeroProtocolloAndFlagAssociatoIsFalse(String numeroProtocollo);
	
	List<CnmTScrittoDifensivo> findByNumeroProtocollo(String numeroProtocollo);
	
	List<CnmTScrittoDifensivo> findByObjectidActa(String objectidActa);

	List<CnmTScrittoDifensivo> findByNumeroProtocolloAndObjectidActaAndFlagAssociatoIsFalse(String numeroProtocollo, String objectidActa);
}
