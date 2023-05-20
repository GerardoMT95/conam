/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.sollecito;

import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTSollecito;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.template.DatiTemplateVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;

import java.util.List;

public interface AllegatoSollecitoService {

	CnmTAllegato salvaLetteraSollecito(Integer idSollecito, byte[] response, UserDetails user);

	boolean isAllegatoSollecitoCreato(CnmTSollecito cnmTSollecito, TipoAllegato tipo);

	// 20200824_LC	-byte[]
	List<DocumentoScaricatoVO> downloadLetteraSollecito(Integer idSollecito);

	CnmTAllegato getAllegatoSollecito(CnmTSollecito cnmTSollecito, TipoAllegato tipo);

	void inviaRichiestaBollettiniByIdSollecito(Integer idSollecito);

	// 20200824_LC	-byte[]
	List<DocumentoScaricatoVO> dowloadBollettiniSollecito(Integer idSollecito);

	void creaBollettiniByCnmTSollecito(CnmTSollecito cnmTSollecito);

	boolean isRichiestaBollettiniInviata(CnmTSollecito cnmTSollecito);

	IsCreatedVO isLetteraSollecitoSaved(Integer idSollecito);

	DatiTemplateVO nomeLetteraSollecito(Integer idSollecito);

	DatiTemplateVO nomeLetteraSollecitoRate(Integer idSollecito);
	
	CnmTAllegato salvaLetteraSollecitoRate(Integer idSollecito, byte[] response, UserDetails user);
}
