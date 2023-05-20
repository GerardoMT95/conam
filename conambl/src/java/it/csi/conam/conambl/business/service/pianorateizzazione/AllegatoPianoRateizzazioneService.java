/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.pianorateizzazione;

import it.csi.conam.conambl.integration.entity.CnmRSoggRata;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTPianoRate;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.template.DatiTemplateVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;

import java.util.List;

public interface AllegatoPianoRateizzazioneService {

	void inviaRichiestaBollettiniByIdPiano(Integer idPiano);

	// 20200824_LC	-byte[]
	List<DocumentoScaricatoVO> downloadBollettiniByIdPiano(Integer idPiano);

	CnmTAllegato salvaLetteraPiano(Integer idPiano, byte[] response, UserDetails user);

	boolean isLetteraPianoCreata(CnmTPianoRate cnmTPianoRate);

	// 20200824_LC	-byte[]
	List<DocumentoScaricatoVO> downloadLetteraPiano(Integer idPiano);

	void creaBollettiniByCnmRSoggRata(List<CnmRSoggRata> rateList);

	IsCreatedVO isLetteraPianoSaved(Integer idPiano);

	DatiTemplateVO nomeLetteraPiano(Integer idPiano);

}
