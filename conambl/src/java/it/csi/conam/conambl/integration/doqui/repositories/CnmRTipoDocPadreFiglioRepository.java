/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.repositories;

import it.csi.conam.conambl.integration.doqui.entity.CnmRTipoDocPadreFiglio;
import it.csi.conam.conambl.integration.doqui.entity.CnmRTipoDocPadreFiglioPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



 
 // 20200608_LC
 
@Repository
public interface CnmRTipoDocPadreFiglioRepository extends JpaRepository<CnmRTipoDocPadreFiglio, CnmRTipoDocPadreFiglioPK> {
	
	@Query(value = "select rpf from cnm_r_tipo_doc_padre_figlio rpf where rpf.id_tipo_doc_padre=?1 and rpf.id_tipo_doc_figlio=?2", nativeQuery = true)
	CnmRTipoDocPadreFiglio findByIdTipoDocPadreAndIdTipoDocFiglio(long idTipoDocPadre, long idTipoDocFiglio);
	
}
