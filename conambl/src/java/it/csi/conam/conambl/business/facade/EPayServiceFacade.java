/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.facade;

import it.csi.conam.conambl.integration.epay.to.InserisciListaDiCaricoRequest;

/**
 * @author riccardo.bova
 * @date 09 mag 2018
 */
public interface EPayServiceFacade {

	void inserisciListaDiCarico(InserisciListaDiCaricoRequest inserisciListaDiCaricoRequest);

}
