/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.repositories;

import it.csi.conam.conambl.integration.doqui.entity.CnmRRichiestaDocumento;
import it.csi.conam.conambl.integration.doqui.entity.CnmRRichiestaDocumentoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


 
 // 20200608_LC
 
@Repository
public interface CnmRRichiestaDocumentoRepository extends JpaRepository<CnmRRichiestaDocumento, CnmRRichiestaDocumentoPK> {
	
	@Query(value = "select rrd from cnm_r_richiesta_documento rrd where rrd.id_richiesta=?1 and rrd.id_documento=?2", nativeQuery = true)
	CnmRRichiestaDocumento findByIdRichiestaAndIdDocumento(long idRichiesta, long idDocumento);
	
}
