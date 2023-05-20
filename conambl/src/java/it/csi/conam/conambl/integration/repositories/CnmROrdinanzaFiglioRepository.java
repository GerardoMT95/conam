/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmROrdinanzaFiglio;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaFiglioPK;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CnmROrdinanzaFiglioRepository extends CrudRepository<CnmROrdinanzaFiglio, CnmROrdinanzaFiglioPK> {

	List<CnmROrdinanzaFiglio> findByCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza);

	List<CnmROrdinanzaFiglio> findByCnmTOrdinanzaFiglio(CnmTOrdinanza cnmTOrdinanza);
}
