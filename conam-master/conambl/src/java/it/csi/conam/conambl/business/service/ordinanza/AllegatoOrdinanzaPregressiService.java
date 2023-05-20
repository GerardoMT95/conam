/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.ordinanza;

import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;

import java.util.List;

public interface AllegatoOrdinanzaPregressiService {

	List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliByOrdinanza(Integer idOrdinanza, String tipoDocumento, boolean aggiungiCategoriaEmail);
}
