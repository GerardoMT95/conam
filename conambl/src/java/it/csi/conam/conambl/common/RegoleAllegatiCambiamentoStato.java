/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common;

import java.util.List;

public class RegoleAllegatiCambiamentoStato {

	public static final Long[] STATO_VERBALE_ACQUISITO = { TipoAllegato.RAPPORTO_TRASMISSIONE.getId(), TipoAllegato.VERBALE_ACCERTAMENTO.getId() };
	public static final Long[] STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO = { TipoAllegato.RAPPORTO_TRASMISSIONE.getId(), TipoAllegato.VERBALE_ACCERTAMENTO.getId() };
	public static final Long[] STATO_VERBALE_ACQUISITO_CON_PAGAMENTO = { TipoAllegato.RAPPORTO_TRASMISSIONE.getId(), TipoAllegato.VERBALE_ACCERTAMENTO.getId(),
			TipoAllegato.RICEVUTA_PAGAMENTO_VERBALE.getId() };
	public static final Long[] STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI = { TipoAllegato.RAPPORTO_TRASMISSIONE.getId(), TipoAllegato.VERBALE_ACCERTAMENTO.getId(),
			TipoAllegato.SCRITTI_DIFENSIVI.getId() };
	public static final Long STATO_VERBALE_ARCHIVIAZIONE = TipoAllegato.ISTANZA_AUTOTUTELA.getId();

	// ritorna lo stato desiderato se rispetta i requisiti
	public static Long statoAvanzabile(Long statoDesiderato, List<Long> idTipologieAllegatiList, Long... regoleStatiAvanzabili) {
		for (Long idTipo : regoleStatiAvanzabili) {
			if (!idTipologieAllegatiList.contains(idTipo))
				statoDesiderato = null;
		}
		return statoDesiderato;
	}
}
