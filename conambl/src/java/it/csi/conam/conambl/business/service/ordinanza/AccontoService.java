/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.ordinanza;

import it.csi.conam.conambl.request.ordinanza.SalvaAccontoRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.ordinanza.AccontoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import java.util.List;

public interface AccontoService {
	AccontoVO salvaAcconto(SalvaAccontoRequest request,UserDetails userDetails);
	AccontoVO salvaAcconto(List<InputPart> data, List<InputPart> file, UserDetails userDetails);

}
