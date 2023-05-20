/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity;

import it.csi.conam.conambl.integration.entity.CnmTCalendario;
import it.csi.conam.conambl.integration.mapper.EntityMapper;
import it.csi.conam.conambl.vo.calendario.CalendarEventVO;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public interface CalendarioEntityMapper extends EntityMapper<CnmTCalendario, CalendarEventVO> {

	CnmTCalendario mapVOtoEntityUpdate(CalendarEventVO vo, CnmTCalendario dto);

}
