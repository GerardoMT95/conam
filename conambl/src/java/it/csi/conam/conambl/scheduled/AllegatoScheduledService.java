/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.scheduled;

import it.csi.conam.conambl.integration.entity.CnmTAllegato;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 mar 2019
 */

public interface AllegatoScheduledService {

	List<CnmTAllegato> updateCnmDStatoAllegatoInCoda(List<CnmTAllegato> cnmTAllegatos);

	CnmTAllegato updateCnmDStatoAllegato(CnmTAllegato cnmTAllegato);

}
