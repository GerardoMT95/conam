/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity;

import it.csi.conam.conambl.integration.entity.CnmTPianoRate;
import it.csi.conam.conambl.integration.mapper.EntityMapper;
import it.csi.conam.conambl.vo.pianorateizzazione.PianoRateizzazioneVO;

/**
 * @author riccardo.bova
 * @date 15 gen 2019
 */
public interface PianoRateizzazioneEntityMapper extends EntityMapper<CnmTPianoRate, PianoRateizzazioneVO> {

	void mapVOtoEntityUpdate(PianoRateizzazioneVO vo, CnmTPianoRate cnmTPianoRate);
}
