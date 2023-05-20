/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.verbale;

import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiSoggettiRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.common.MessageVO;

public interface AllegatoVerbaleSoggettoPregressiService {

	MessageVO salvaVerbaleAudizione(SalvaAllegatiProtocollatiSoggettiRequest request, UserDetails user);

	MessageVO salvaConvocazioneAudizione(SalvaAllegatiProtocollatiSoggettiRequest request, UserDetails user);

}
