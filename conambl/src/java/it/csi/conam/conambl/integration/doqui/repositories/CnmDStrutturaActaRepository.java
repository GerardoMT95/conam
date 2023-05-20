/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.repositories;


import it.csi.conam.conambl.integration.doqui.entity.CnmDStrutturaActa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CnmDStrutturaActaRepository extends CrudRepository<CnmDStrutturaActa, Integer> {


}
