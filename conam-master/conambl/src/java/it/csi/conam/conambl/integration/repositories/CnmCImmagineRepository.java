/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.custom.CnmCImmagine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmCImmagineRepository extends CrudRepository<CnmCImmagine, Long> {

	CnmCImmagine findByCodImmagine(String codImmagine);

	List<CnmCImmagine> findByCodImmagineIn(List<String> codImmagine);

}
