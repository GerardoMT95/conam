/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.repositories;

import it.csi.conam.conambl.integration.doqui.entity.CnmTDocumento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmTDocumentoRepository extends CrudRepository<CnmTDocumento, Integer> {

//	void updateColumnsRiferimentiFornitore(CnmTDocumento dto);
//
//	void updateColumnsRiferimentiFornitoreConKeyActa(CnmTDocumento dto);
//
//	CnmTDocumento findById(Long idDocumento);
	
	// 20200714_LC inutile
	// @Query(value = "select td from CnmTDocumento td where td.identificativoArchiviazione = ?1")
	//List<CnmTDocumento> findByIdArchiviazione(int idArchiviazione);
	
//	List<CnmTDocumento> findByObjectiddocumentoAndIdentificativoEntitaFruitoreStartsWith(String objectiddocumento, String identificativoEntitaFruitoreStart);
	
	List<CnmTDocumento> findByObjectiddocumentoAndIdDocumentoIn(String objectiddocumento, List<Integer> idDocumentoList);
	
	List<CnmTDocumento> findByObjectiddocumento(String objectiddocumento);
	
	List<CnmTDocumento> findByObjectidclassificazione(String objectidclassificazione);

}
