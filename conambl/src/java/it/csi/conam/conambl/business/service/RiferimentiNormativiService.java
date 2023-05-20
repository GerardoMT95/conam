/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service;

import it.csi.conam.conambl.integration.entity.CnmDLettera;
import it.csi.conam.conambl.vo.leggi.*;

import java.util.Date;
import java.util.List;

public interface RiferimentiNormativiService {

	List<AmbitoVO> getAmbitiByIdEnte(Long idEnte, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento);

	List<NormaVO> getNormeByIdAmbitoAndIdEnte(Integer idAmbito, Long idEnte, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento);

	List<LetteraVO> getLetteraByIdComma(Long idComma, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento);

	List<CommaVO> getCommaByIdArticolo(Long idArticolo, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento);

	List<CnmDLettera> getLettereByEnte(Long idEnte, Boolean filterDataValidita);

	List<ArticoloVO> getArticoliByIdNormaAndEnte(Long idNorma, Long idEnte, Boolean filterDataValidita, Boolean filterPregresso, Date dataAccertamento);

	List<CnmDLettera> getLettereByIdNormaAndEnte(Long idNorma, Long idEnte, Boolean filterDataValidita);

}
