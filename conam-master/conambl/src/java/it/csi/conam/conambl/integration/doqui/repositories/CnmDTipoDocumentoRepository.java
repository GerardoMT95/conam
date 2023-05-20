/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.repositories;

import it.csi.conam.conambl.integration.doqui.entity.CnmDTipoDocumento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmDTipoDocumentoRepository extends CrudRepository<CnmDTipoDocumento, Long> {

	List<CnmDTipoDocumento> findByCodTipoDocumento(String codTipoDocumento);
	
	//@Query(value = "select dtd from CnmDTipoDocumento dtd, CnmTDocumento td where dtd.idTipoDocumento = td.idTipoDocumento and td.identificativoArchiviazione = ?1")
	//@Query(value = "select dtd from cnm_d_tipo_documento dtd, cnm_t_documento td where dtd.id_tipo_documento = td.id_tipo_documento and td.identificativo_archiviazione = ?1", nativeQuery = true)	// 20200630_LC
	
	//@Query(value = "select cnmDTipoDocumento from CnmTDocumento td where td.identificativoArchiviazione = ?1")
	// List<CnmDTipoDocumento> findByIdArchiviazione(String idArchiviazione);
	
	// 20200713_LC
	@Query(value = "select cnmDTipoDocumento from CnmTDocumento td where td.idDocumento = ?1")
	List<CnmDTipoDocumento> findByIdDocumento(int idDocumento);

}
