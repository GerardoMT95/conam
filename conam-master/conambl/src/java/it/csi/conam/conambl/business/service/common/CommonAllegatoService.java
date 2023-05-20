/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.common;

import it.csi.conam.conambl.common.TipoProtocolloAllegato;
import it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.request.ParentRequest;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.response.RicercaProtocolloSuActaResponse;
import it.csi.conam.conambl.vo.common.SelectVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoFieldVO;
import it.csi.conam.conambl.vo.verbale.allegato.ConfigAllegatoVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import java.util.List;

public interface CommonAllegatoService {

	List<DocumentoScaricatoVO> downloadAllegatoById(Integer id);	// 20200803_LC

	<T extends ParentRequest> T getRequest(List<InputPart> data, List<InputPart> file, Class<? extends ParentRequest> generic, boolean multi);

	<T extends ParentRequest> T getRequest(List<InputPart> data, List<InputPart> file, Class<? extends ParentRequest> generic);

	void eliminaAllegatoBy(CnmTAllegato cnmTAllegato);

	List<ConfigAllegatoVO> getConfigAllegati();

	List<SelectVO> getDecodificaSelectAllegato(Long decodificaSelect);
	
	List<SelectVO> getDecodificaSelectSoggettiAllegato(Integer idVerbale);

	ResponseProtocollaDocumento avviaProtocollazione(List<CnmRAllegatoVerbale> cnmRAllegatoVerbaleList, CnmTUser cnmTUser);		// 20200706_LC aggiunto user

	byte[] addProtocolloToDocument(byte[] src, String text, long tipoAllegato);

	CnmTAllegato salvaAllegato(byte[] file, String filename, Long idTipoAllegato, List<AllegatoFieldVO> configAllegato, CnmTUser cnmTUser, TipoProtocolloAllegato tipoProtocolloAllegato, String folder,
			String idEntitaFruitore, boolean isMaster, boolean protocollazioneInUscita, String soggettoActa, String rootActa, int numeroAllegati, Integer idVerbaleAudizione, String tipoActa,
			List<CnmTSoggetto> cnmTSoggettoList);
	
	// 20200903_LC gestione pregresso (nuova response)
	RicercaProtocolloSuActaResponse ricercaProtocolloSuACTA(String numProtocollo, Integer idVerbale, Boolean flagPregresso);
	
	// 20200706_LC
	List<CnmTAllegato> salvaAllegatoProtocollatoVerbale(SalvaAllegatiProtocollatiRequest request, CnmTUser cnmTUser, CnmTVerbale cnmTVerbale, boolean pregresso);

	// 20200717_LC
	List<DocumentoScaricatoVO> downloadAllegatoByObjectIdDocumento(String objectIdDocumento);
	
	CnmTAllegato salvaAllegatoProtocollatOrdinanzaSoggetto(SalvaAllegatiProtocollatiRequest request, CnmTUser cnmTUser, List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList, CnmTVerbale cnmTVerbale, boolean pregresso);
	
	// 2020015_LC
	List<CnmTAllegato> salvaAllegatoProtocollatoOrdinanza(SalvaAllegatiProtocollatiRequest request, CnmTUser cnmTUser, CnmTOrdinanza cnmTOrdinanza, CnmTVerbale cnmTVerbale, boolean pregresso);

	// 20200716_LC
	void avviaSpostamentoPerFascicoloGiaAcquisito(List<CnmTAllegato> cnmTAllegatoMultiTipoList, CnmTVerbale cnmTVerbale, CnmTUser cnmTUser);
	
	// 20200825_LC
	List<DocumentoScaricatoVO> downloadAllegatoByObjectIdDocumentoFisico(String objectIdDocumentoFisico);
	
	
	// 20200827_LC
	List<DocumentoScaricatoVO> getDocFisiciByIdAllegato(Integer id);

	// 20200827_LC
	List<DocumentoScaricatoVO> getDocFisiciByObjectIdDocumento(String objectIdDocumento);
	
	// 20201130 PP - solo per pregresso
	public CnmTAllegato salvaAllegatoProtocollatoVerbaleSoggetto(SalvaAllegatiProtocollatiRequest request, CnmTUser cnmTUser, List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList);

	// 20210312 PP - Per protocollare master non protocollati ai quali sono stati agganciati gli allegati
	// Per implementazione CR Jira_92 (riportare su allegati al master il numero di protocollo)
	public ResponseProtocollaDocumento avviaProtocollazioneDocumentoEsistente(CnmTAllegato cnmTAllegato, CnmTUser cnmTUser);
	
	
	
	
	
}
