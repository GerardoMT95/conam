/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service;

import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.luoghi.NazioneVO;
import it.csi.conam.conambl.vo.luoghi.ProvinciaVO;
import it.csi.conam.conambl.vo.luoghi.RegioneVO;

import java.util.Date;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 23 mar 2018
 */
public interface LuoghiService {

	List<RegioneVO> getRegioni();

	List<ProvinciaVO> getProviceByIdRegione(Long idRegione);

	List<ComuneVO> getComuniByIdProvincia(Long idProvincia);

	List<RegioneVO> getRegioniNascita();

	List<ProvinciaVO> getProviceByIdRegioneNascita(Long idRegione);

	List<ComuneVO> getComuniByIdProvinciaNascita(Long idProvincia);

	List<NazioneVO> getNazioni();

	List<ProvinciaVO> getProviceByIdRegione(Long idRegione, Date dataOraAccertamento);

	List<ComuneVO> getComuniByIdProvincia(Long idProvincia, Date dataOraAccertamento);
	
	List<ComuneVO> getComuniEnte(Date dataOraAccertamento);

}
