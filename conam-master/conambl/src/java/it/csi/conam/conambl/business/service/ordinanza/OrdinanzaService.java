/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.ordinanza;

import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.response.DatiSentenzaResponse;
import it.csi.conam.conambl.response.ImportoResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.ordinanza.OrdinanzaVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import java.util.List;

public interface OrdinanzaService {

	void testIfOrdinanzaPagata(Integer idOrdinanza, UserDetails userDetails);

	void testIfOrdinanzaPagata(CnmTOrdinanza ordinanza, UserDetails userDetails);

	OrdinanzaVO dettaglioOrdinanzaById(Integer idOrdinanza, UserDetails userDetails);

	OrdinanzaVO dettaglioOrdinanzaByIdOrdinanzaSoggetti(List<Integer> idOrdinanzaSoggetti, UserDetails utente);

	Integer salvaOrdinanza(List<InputPart> data, List<InputPart> file, UserDetails userDetails);

	DatiSentenzaResponse getDatiSentenzaByIdOrdinanzaVerbaleSoggetto(Integer idOrdinanzaVerbaleSoggetto);

	ImportoResponse getImportoOrdinanzaByCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog dto);

	void saveSStatoOrdinanza(CnmTOrdinanza cnmTOrdinanza, CnmTUser cnmTUser);

	Integer salvaOrdinanzaAnnullamento(List<InputPart> data, List<InputPart> file, UserDetails userDetails);

}
