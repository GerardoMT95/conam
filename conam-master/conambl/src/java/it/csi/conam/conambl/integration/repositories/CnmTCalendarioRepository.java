/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmTCalendario;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CnmTCalendarioRepository extends CrudRepository<CnmTCalendario, Integer>, JpaSpecificationExecutor<CnmTCalendario> {
	
	@Query("Select u from CnmTCalendario u where u.dataPromemoriaUdienza >=?1 and u.dataPromemoriaUdienza <=?2")
	public List<CnmTCalendario> findUdienzaNotificationToSend(Date fromDateTime, Date toDitetime);
	
	@Query("Select u from CnmTCalendario u where u.dataPromemoriaDocumentazione >=?1 and u.dataPromemoriaDocumentazione <=?2")
	public List<CnmTCalendario> findDocumentazioneNotificationToSend(Date fromDateTime, Date toDitetime);
}
