/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmCProprieta;
import org.springframework.data.repository.CrudRepository;

public interface CnmCProprietaRepository  extends CrudRepository<CnmCProprieta, Long> {

}
