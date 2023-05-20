/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.riscossione;

import it.csi.conam.conambl.integration.entity.CnmTFile;

import java.io.InputStream;

/**
 * @author riccardo.bova
 * @date 15 apr 2019
 */
public interface SorisService {

	InputStream creaTracciato(CnmTFile cnmTFile);

}
