/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import it.csi.conam.conambl.business.service.LuoghiService;
import it.csi.conam.conambl.dispatcher.LuoghiDispatcher;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.luoghi.NazioneVO;
import it.csi.conam.conambl.vo.luoghi.ProvinciaVO;
import it.csi.conam.conambl.vo.luoghi.RegioneVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 23 mar 2018
 */

@Component
public class LuoghiDispatcherImpl implements LuoghiDispatcher {

	@Autowired
	private LuoghiService luoghiService;

	@Override
	public List<RegioneVO> getRegioni() {
		return luoghiService.getRegioni();
	}

	@Override
	public List<ProvinciaVO> getProviceByIdRegione(Long idRegione) {
		return luoghiService.getProviceByIdRegione(idRegione);
	}

	@Override
	public List<ComuneVO> getComuniByIdProvincia(Long idProvincia) {
		return luoghiService.getComuniByIdProvincia(idProvincia);
	}

	@Override
	public List<RegioneVO> getRegioniNascita() {
		return luoghiService.getRegioniNascita();
	}

	@Override
	public List<ProvinciaVO> getProviceByIdRegioneNascita(Long idRegione) {
		return luoghiService.getProviceByIdRegioneNascita(idRegione);
	}

	@Override
	public List<ComuneVO> getComuniByIdProvinciaNascita(Long idProvincia) {
		return luoghiService.getComuniByIdProvinciaNascita(idProvincia);
	}

	@Override
	public List<NazioneVO> getNazioni() {
		return luoghiService.getNazioni();
	}

	@Override
	public List<ProvinciaVO> getProviceByIdRegione(Long idRegione, Date dataOraAccertamento) {
		return luoghiService.getProviceByIdRegione(idRegione, dataOraAccertamento);
	}

	@Override
	public List<ComuneVO> getComuniByIdProvincia(Long idProvincia, Date dataOraAccertamento) {
		return luoghiService.getComuniByIdProvincia(idProvincia, dataOraAccertamento);
	}
	
	@Override
	public List<ComuneVO> getComuniEnte(Date dataOraAccertamento) {
		return luoghiService.getComuniEnte(dataOraAccertamento);
	}
}
