/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.ordinanza;

import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.template.DatiTemplateVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import java.util.List;
import java.util.Map;

public interface AllegatoOrdinanzaService {

	List<TipoAllegatoVO> getTipologiaAllegatiOrdinanzaByCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza);

	List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliByOrdinanza(Integer idOrdinanza, String tipoDocumento, boolean aggiungiCategoriaEmail);

	List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliByOrdinanzaSoggetto(List<Integer> idSoggettoOrdinanza, String tipoDocumento, boolean aggiungiCategoriaEmail);

	List<TipoAllegatoVO> getTipologiaAllegatiCreaOrdinanza();

	List<AllegatoVO> getAllegatiByIdOrdinanza(Integer idOrdinanza);

	List<AllegatoVO> getAllegatiByIdOrdinanzaVerbaleSoggetto(List<Integer> idOrdinanzaVerbaleSoggettoList);

	Map<Long, List<AllegatoVO>> getMapCategoriaAllegatiByIdOrdinanza(Integer idOrdinanza);

	Map<Long, List<AllegatoVO>> getMapCategoriaAllegatiByIdOrdinanzaVerbaleSoggetto(Integer idOrdinanzaVerbaleSoggetto);

	Map<Long, List<AllegatoVO>> getMapCategoriaAllegatiByIdOrdinanza(List<Integer> idOrdinanza);

	boolean isLetteraOrdinanzaCreata(CnmTOrdinanza cnmTOrdinanza);

	boolean isRichiestaBollettiniInviata(CnmTOrdinanza cnmTOrdinanza);

	CnmTAllegato salvaLetteraOrdinanza(Integer idOrdinanza, byte[] file, UserDetails user);

	// 20200824_LC	-byte[]
	List<DocumentoScaricatoVO> downloadLetteraOrdinanza(Integer idOrdinanza);

	void creaBollettiniByCnmROrdinanzaVerbSog(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList);

	// 20200824_LC	-byte[]
	List<DocumentoScaricatoVO> downloadBollettiniByIdOrdinanza(Integer idOrdinanza);

	void inviaRichiestaBollettiniByIdOrdinanza(Integer idOrdinanza);

	AllegatoVO salvaAllegatoOrdinanza(List<InputPart> data, List<InputPart> file, UserDetails userDetails, boolean pregresso);

	void salvaAllegatoOrdinanzaSoggetto(List<InputPart> data, List<InputPart> file, UserDetails userDetails, boolean pregresso);

	MessageVO salvaAllegatoProtocollatoOrdinanzaSoggetto(SalvaAllegatiProtocollatiRequest request, UserDetails userDetails, boolean pregresso);

	IsCreatedVO isLetteraSaved(IsCreatedVO request);

	DatiTemplateVO nomeLetteraOrdinanza(Integer idOrdinanza);

	// 20200715_LC
	MessageVO salvaAllegatoProtocollatoOrdinanza(SalvaAllegatiProtocollatiRequest request, UserDetails userDetails, boolean pregresso);

	Map<Long, List<AllegatoVO>> getMapTipoAllegatiByIdOrdinanza(Integer idOrdinanza);
	
	List<TipoAllegatoVO> getTipologiaAllegatiCreaOrdinanzaAnnullamento(Integer idOrdinanzaAnnullata);

	List<MessageVO> salvaAllegatiMultipli(List<InputPart> data, List<InputPart> file, UserDetails userDetails, boolean pregresso);
}
