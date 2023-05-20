/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.calendario;

import it.csi.conam.conambl.vo.ParentVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Paolo Piedepalumbo
 */
public class CalendarReminderDataVO extends ParentVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1760963110290816143L;
	private Long giorniMailUdienza;
	private Long giorniMailDocumentazione;

	public CalendarReminderDataVO(long giorniMailUdienza, long giorniMailDocumentazione) {
		this.giorniMailUdienza = giorniMailUdienza;
		this.giorniMailDocumentazione = giorniMailDocumentazione;
	}

	public Long getGiorniMailUdienza() {
		return giorniMailUdienza;
	}

	public void setGiorniMailUdienza(Long giorniMailUdienza) {
		this.giorniMailUdienza = giorniMailUdienza;
	}

	public Long getGiorniMailDocumentazione() {
		return giorniMailDocumentazione;
	}

	public void setGiorniMailDocumentazione(Long giorniMailDocumentazione) {
		this.giorniMailDocumentazione = giorniMailDocumentazione;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
