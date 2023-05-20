/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.verbale;

import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoFieldVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.RiepilogoAllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import java.util.List;

public interface AllegatoVerbaleService {

	RiepilogoAllegatoVO getAllegatiByIdVerbale(Integer id, UserDetails userDetails, boolean includiControlloUtenteProprietario);

	RiepilogoAllegatoVO getAllegatiByIdVerbale(Integer id, UserDetails userDetails, boolean includiControlloUtenteProprietario, boolean verbaleAudizione);

	List<TipoAllegatoVO> getTipologiaAllegatiByCnmVerbale(CnmTVerbale cnmTVerbale);

	List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliVerbale(Integer id, String tipoDocumento, boolean aggiungiCategoriaEmail);

	List<TipoAllegatoVO> getTipologiaAllegatiVerbale(String tipoDocumento);

	AllegatoVO salvaAllegato(List<InputPart> data, List<InputPart> file, UserDetails userDetails, boolean pregresso);

	void eliminaAllegato(Integer idVerbale, Integer idAllegato, UserDetails userDetails);

	void controllaRegoleFieldAllegatiVerbale(CnmTVerbale cnmTVerbale, Long idTipoAllegato, List<AllegatoFieldVO> configAllegato);

	AllegatoVO salvaAllegatiMultipli(List<InputPart> data, List<InputPart> file, UserDetails userDetails, boolean pregresso);

	IsCreatedVO isConvocazioneCreated(IsCreatedVO request);

	IsCreatedVO isVerbaleAudizioneCreated(IsCreatedVO request);
	
	// 20200706_LC
	MessageVO salvaAllegatoProtocollatoVerbale(SalvaAllegatiProtocollatiRequest request, UserDetails userDetails, boolean pregresso);

	List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliVerbalePregressi(Integer id, String tipoDocumento,
			boolean aggiungiCategoriaEmail);

}
