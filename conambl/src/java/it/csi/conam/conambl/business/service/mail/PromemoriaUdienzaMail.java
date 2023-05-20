/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.mail;

import it.csi.conam.conambl.integration.entity.CnmTCalendario;

import java.util.Date;

/**
 * @author Paolo Piedepalumbo
 * 
 */
public class PromemoriaUdienzaMail extends PromemoriaMail {
	private static final long serialVersionUID = 3413369407180825094L;
	
	public PromemoriaUdienzaMail(
		CnmTCalendario cnmTCalendario
	) {
		super(cnmTCalendario);
	}
	public PromemoriaUdienzaMail(
		String eMailTo,
		String autoritaGiudiziaria,
		Date data,
		String giudiceCompetente,
		String luogoUdienza,
		String note,
		CnmTCalendario cnmTCalendario
	) {
		super(cnmTCalendario);
		setAutoritaGiudiziaria(autoritaGiudiziaria);
		setData(data);
		setGiudiceCompetente(giudiceCompetente);
		setLuogoUdienza(luogoUdienza);
		setNote(note);
	}
}
