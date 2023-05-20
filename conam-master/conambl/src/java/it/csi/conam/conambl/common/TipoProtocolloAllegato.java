/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common;

public enum TipoProtocolloAllegato {
	NON_PROTOCOLLARE(1), DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO(2), PROTOCOLLARE(3), SALVA_MULTI_SENZA_PROTOCOLARE(4);

	TipoProtocolloAllegato(long id) {
		this.id = id;
	}

	private final long id;

	public long getId() {
		return id;
	}

}
