/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

import it.csi.conam.conambl.business.facade.DoquiServiceFacade;
import it.csi.conam.conambl.business.facade.StadocServiceFacade;
import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.ordinanza.OrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.UtilsOrdinanza;
import it.csi.conam.conambl.business.service.scrittodifensivo.ScrittoDifensivoService;
import it.csi.conam.conambl.business.service.util.UtilsCnmCProprietaService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.business.service.util.UtilsTraceCsiLogAuditService;
import it.csi.conam.conambl.business.service.verbale.SoggettoService;
import it.csi.conam.conambl.business.service.verbale.UtilsVerbale;
import it.csi.conam.conambl.business.service.verbale.VerbaleService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.TipoProtocolloAllegato;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.integration.beans.Documento;
import it.csi.conam.conambl.integration.beans.ResponseArchiviaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseEliminaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseRicercaAllegato;
import it.csi.conam.conambl.integration.beans.ResponseRicercaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseRicercaDocumentoMultiplo;
import it.csi.conam.conambl.integration.beans.ResponseSalvaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseSpostaDocumento;
import it.csi.conam.conambl.integration.doqui.utils.DateFormat;
import it.csi.conam.conambl.integration.entity.CnmCField;
import it.csi.conam.conambl.integration.entity.CnmCProprieta.PropKey;
import it.csi.conam.conambl.integration.entity.CnmDAmbito;
import it.csi.conam.conambl.integration.entity.CnmDElementoElenco;
import it.csi.conam.conambl.integration.entity.CnmDMessaggio;
import it.csi.conam.conambl.integration.entity.CnmDStatoAllegato;
import it.csi.conam.conambl.integration.entity.CnmDStatoOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmDTipoAllegato;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoOrdVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoOrdinanzaPK;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbSogPK;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbalePK;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleIllecito;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTAllegatoField;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmTScrittoDifensivo;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.entity.CsiLogAudit.TraceOperation;
import it.csi.conam.conambl.integration.mapper.entity.ConfigAllegatoEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.ElementoElencoEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmCFieldRepository;
import it.csi.conam.conambl.integration.repositories.CnmCParametroRepository;
import it.csi.conam.conambl.integration.repositories.CnmDElementoElencoRepository;
import it.csi.conam.conambl.integration.repositories.CnmDMessaggioRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoManualeRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmDTipoAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoOrdVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmROrdinanzaVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleIllecitoRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleSoggettoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoFieldRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmTScrittoDifensivoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTVerbaleRepository;
import it.csi.conam.conambl.request.ParentRequest;
import it.csi.conam.conambl.request.SalvaAllegatiMultipliRequest;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.request.SalvaAllegatoRequest;
import it.csi.conam.conambl.response.RicercaProtocolloSuActaResponse;
import it.csi.conam.conambl.util.UploadUtils;
import it.csi.conam.conambl.util.UtilsTipoAllegato;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.common.SelectVO;
import it.csi.conam.conambl.vo.verbale.DocumentoProtocollatoVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoFieldVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoMultiploVO;
import it.csi.conam.conambl.vo.verbale.allegato.ConfigAllegatoVO;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class CommonAllegatoServiceImpl implements CommonAllegatoService {

	@Autowired
	private CnmDTipoAllegatoRepository cnmDTipoAllegatoRepository;
	@Autowired
	private CnmCFieldRepository cnmCFieldRepository;
	@Autowired
	private CnmTAllegatoFieldRepository cnmTAllegatoFieldRepository;
	@Autowired
	private ConfigAllegatoEntityMapper configAllegatoEntityMapper;

	@Autowired
	private CnmDElementoElencoRepository cnmDElementoElencoRepository;
	@Autowired
	private ElementoElencoEntityMapper elementoElencoEntityMapper;
	
	@Autowired
	private StadocServiceFacade stadocServiceFacade;
	@Autowired
	private CnmDStatoAllegatoRepository cnmDStatoAllegatoRepository;
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;
	@Autowired
	private UtilsDoqui utilsDoqui;
	@Autowired
	private UtilsDate utilsDate;

	@Autowired
	private CnmRAllegatoVerbaleRepository cnmRAllegatoVerbaleRepository;
	/*@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;*/
	
	@Autowired
	private DoquiServiceFacade doquiServiceFacade;
	
	@Autowired
	private UtilsCnmCProprietaService utilsCnmCProprietaService;

	// 20200622_LC
	@Autowired
	private UtilsTraceCsiLogAuditService utilsTraceCsiLogAuditService;
	
	// 20200715_LC
	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CnmRAllegatoOrdinanzaRepository cnmRAllegatoOrdinanzaRepository;
	@Autowired
	private CnmDStatoOrdinanzaRepository cnmDStatoOrdinanzaRepository;
	@Autowired
	private OrdinanzaService ordinanzaService;
	@Autowired
	private ScrittoDifensivoService scrittoDifensivoService;
	
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmRAllegatoOrdVerbSogRepository cnmRAllegatoOrdVerbSogRepository;
	@Autowired
	private CnmRAllegatoVerbSogRepository cnmRAllegatoVerbSogRepository;
	
	// 20200903_LC gestione pregresso
	@Autowired
	private CnmCParametroRepository cnmCParametroRepository;
	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;
	@Autowired
	private CnmTScrittoDifensivoRepository cnmTScrittoDifensivoRepository;
	@Autowired
	private CnmRVerbaleIllecitoRepository cnmRVerbaleIllecitoRepository;

	@Autowired
	private CnmDStatoVerbaleRepository cnmDStatoVerbaleRepository;
	
	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;
	
	@Autowired
	private UtilsVerbale utilsVerbale;

	@Autowired
	private VerbaleService verbaleService;

	@Autowired
	private CnmDStatoManualeRepository cnmDStatoManualeRepository;

	@Autowired
	private SoggettoService soggettoService;
	@Autowired
	private UtilsOrdinanza utilsOrdinanza;
	
	// 20201127_LC cache ricerca protocollo
	//CRP    private Map<String, RicercaProtocolloSuActaResponse> cacheRicercaProtocollo = new HashMap<String, RicercaProtocolloSuActaResponse>();
	
 
	private static Logger logger = Logger.getLogger(CommonAllegatoServiceImpl.class);

	
	
	private boolean isDoquiDirect() {
		 return Boolean.valueOf(utilsCnmCProprietaService.getProprieta(PropKey.IS_DOQUI_DIRECT));
	}

    
	@Override
	public List<DocumentoScaricatoVO> downloadAllegatoById(Integer id) {
		if(isDoquiDirect()) {
			return downloadAllegatoById_Doqui(id);
		}else {
			// 20200804_LC per compilazione (non funzionante e non utilizzato)
			List<DocumentoScaricatoVO> listStadoc = new ArrayList<DocumentoScaricatoVO>();
			DocumentoScaricatoVO docStadoc = new DocumentoScaricatoVO();
			docStadoc.setFile(downloadAllegatoById_Stadoc(id));
			listStadoc.add(docStadoc);
			return listStadoc;		 
			//return downloadAllegatoById_Stadoc(id);
		}
	}
	
	private byte[] downloadAllegatoById_Stadoc(Integer id) {
		if (id == null)
			throw new IllegalArgumentException("id non valorizzato");
		CnmTAllegato cnmTAllegato = cnmTAllegatoRepository.findOne(id);	
		cnmTAllegato = validateRequestAllegato(cnmTAllegato);
		if (cnmTAllegato.getIdIndex() != null) {
			ResponseRicercaAllegato response = stadocServiceFacade.recuperaDocumentoIndex(cnmTAllegato.getIdIndex());
			if (response != null && response.getDocumento() != null && response.getDocumento().getFile() != null)
				return response.getDocumento().getFile();
			else
				throw new RuntimeException("documento non trovato su index");
		} else if (cnmTAllegato.getIdActa() != null) {
			ResponseRicercaDocumento response = stadocServiceFacade.recuperaDocumentoActa(cnmTAllegato.getIdActa());
			if (response != null && response.getDocumento() != null && response.getDocumento().getFile() != null)
				if (Constants.DOCUMENTI_CREATI_DA_CONAM.contains(cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato()))
					return addProtocolloToDocument(response.getDocumento().getFile(), cnmTAllegato.getNumeroProtocollo(), cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato());
				else
					return response.getDocumento().getFile();
			else
				throw new RuntimeException("documento non trovato su acta");
		} else
			throw new RuntimeException("idIndex e Acta  non valorizzati");

	}

 
 
 
	// 20200803_LC gestione documento multiplo
	private List<DocumentoScaricatoVO> downloadAllegatoById_Doqui(Integer id) {
		if (id == null)
			throw new IllegalArgumentException("id non valorizzato");
		CnmTAllegato cnmTAllegato = cnmTAllegatoRepository.findOne(id);
		cnmTAllegato = validateRequestAllegato(cnmTAllegato);
  
		// 20200804_LC ID_INDEX						 
		if (cnmTAllegato.getIdIndex() != null) {
   
			ResponseRicercaAllegato responseIndex = doquiServiceFacade.recuperaDocumentoIndex(cnmTAllegato.getIdIndex(), cnmTAllegato.getCnmDTipoAllegato().getIndexType());
   
			if (responseIndex != null && responseIndex.getDocumento() != null && responseIndex.getDocumento().getFile() != null) {
				List<DocumentoScaricatoVO> response = new ArrayList<DocumentoScaricatoVO>();
				DocumentoScaricatoVO doc = new DocumentoScaricatoVO();
				doc.setFile(responseIndex.getDocumento().getFile());	// basta tornare il byte[]
				response.add(doc);
				return response;
			}
			else {
				throw new RuntimeException("documento non trovato su index");
			}
		} 
		
		
		// 20200804_LC ID_ACTA
		else if (cnmTAllegato.getIdActa() != null) {
   
			ResponseRicercaDocumentoMultiplo response = doquiServiceFacade.recuperaDocumentoActa(cnmTAllegato.getIdActa());

			if (response != null && response.getSottoDocumenti() != null) {
				
				// 20210114 - JIRA 120 - non aggiunge numProtocollo se allegato pregresso
				if (Constants.DOCUMENTI_CREATI_DA_CONAM.contains(cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato()) && !cnmTAllegato.isFlagDocumentoPregresso()) {
					for (int i=0; i < response.getSottoDocumenti().size(); i++) {
						Documento doc = response.getSottoDocumenti().get(i);
						doc.setFile(addProtocolloToDocument(doc.getFile(), cnmTAllegato.getNumeroProtocollo(), cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato()));
						response.getSottoDocumenti().set(i, doc);
					}
					// return addProtocolloToDocument(response.getDocumento().getFile(), cnmTAllegato.getNumeroProtocollo(), cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato());					
				}
					List<DocumentoScaricatoVO> docScaricatoVOList = new ArrayList<DocumentoScaricatoVO>();
					
					for (Documento doc:response.getSottoDocumenti()) {
						DocumentoScaricatoVO docScaricatoVO = new DocumentoScaricatoVO();
						docScaricatoVO.setFile(doc.getFile());
						docScaricatoVO.setNomeFile(doc.getNomeFile());
						docScaricatoVO.setObjectIdDocumentoFisico(doc.getObjectIdDocumentoFisico());
						docScaricatoVOList.add(docScaricatoVO);
					}
					
					return docScaricatoVOList;	
			
			}
																										
																											 
																																								   
			else {
											  
	   
				throw new RuntimeException("documento non trovato su acta per parola chiave");
			}
		}  
		
		
		// 20200804_LC ID INDEX ED ACTA NULL, DOWNLOAD BY ID
		else if (cnmTAllegato.getObjectidSpostamentoActa() != null) {
			// 20200721_LC
			// se entrambi sono NULL significa che il doc è stato associato ma non spostato - ricava objectId da CnmTAllegato e chiama il download per objectId
			List<DocumentoScaricatoVO> response = downloadAllegatoByObjectIdDocumento(cnmTAllegato.getObjectidSpostamentoActa());
			//if (response != null && response.getFile() != null) 
			if (response != null ) {
				return response;	
				
			} else {
				 throw new RuntimeException("documento non trovato su acta per objectIdDocumento");			
			}
		} 
		
		// IMPOSSIBILE EFFETTUARE IL DOWNLOAD
		else {
			throw new RuntimeException("idIndex, idActa e objectIdSpostamentoActa non valorizzati");			
		}
		
	}

 
 
 
 
	@Override
	@Transactional
	public CnmTAllegato salvaAllegato(
		byte[] file,
		String filename,
		Long idTipoAllegato,
		List<AllegatoFieldVO> configAllegato,
		CnmTUser cnmTUser,
		TipoProtocolloAllegato tipoProtocolloAllegato,
		String folder,
		String idEntitaFruitore,
		boolean isMaster,
		boolean protocollazioneInUscita,
		String soggettoActa,
		String rootActa,
		int numeroAllegati,
		Integer idVerbaleAudizione,
		String tipoActa,
		List<CnmTSoggetto> cnmTSoggettoList
	) {
		if(isDoquiDirect()) {
			return salvaAllegato_Doqui(file, filename, idTipoAllegato, configAllegato, cnmTUser, tipoProtocolloAllegato, folder, idEntitaFruitore, isMaster, protocollazioneInUscita, soggettoActa, rootActa, numeroAllegati, idVerbaleAudizione, tipoActa, cnmTSoggettoList);
		}else {
			return salvaAllegato_Stadoc(file, filename, idTipoAllegato, configAllegato, cnmTUser, tipoProtocolloAllegato, folder, idEntitaFruitore, isMaster, protocollazioneInUscita, soggettoActa, rootActa, numeroAllegati, idVerbaleAudizione, tipoActa, cnmTSoggettoList);
		}
		
	}
	
	private CnmTAllegato salvaAllegato_Stadoc(byte[] file, String filename, Long idTipoAllegato, List<AllegatoFieldVO> configAllegato, CnmTUser cnmTUser, TipoProtocolloAllegato tipoProtocolloAllegato,
			String folder, String idEntitaFruitore, boolean isMaster, boolean protocollazioneInUscita, String soggettoActa, String rootActa, int numeroAllegati, Integer idVerbaleAudizione,
			String tipoActa, List<CnmTSoggetto> cnmTSoggettoList) {	
		CnmDTipoAllegato cnmDTipoAllegato = cnmDTipoAllegatoRepository.findOne(idTipoAllegato);
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		if (cnmDTipoAllegato == null)
			throw new SecurityException("id tipo allegato non trovato");

		CnmTAllegato cnmTAllegato = new CnmTAllegato();
		if (idVerbaleAudizione != null && idVerbaleAudizione != 0)
			cnmTAllegato.setIdAllegato(idVerbaleAudizione);
		if (tipoProtocolloAllegato.getId() == TipoProtocolloAllegato.PROTOCOLLARE.getId()) {
			if (folder == null)
				throw new IllegalArgumentException("folder non valorizzato");
			CnmDStatoAllegato cnmDStatoAllegato = cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO);
			ResponseProtocollaDocumento responseProtocollaDocumento = stadocServiceFacade.protocollaDocumentoFisico(folder, file, filename, idEntitaFruitore, isMaster, protocollazioneInUscita,
					soggettoActa, rootActa, idTipoAllegato.longValue(), tipoActa, cnmTSoggettoList, null);
			cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegato);
			cnmTAllegato.setIdActa(responseProtocollaDocumento.getIdDocumento());
			cnmTAllegato.setIdIndex(null);
			cnmTAllegato.setNumeroProtocollo(responseProtocollaDocumento.getProtocollo());
			cnmTAllegato.setDataOraProtocollo(now);
		} else if (tipoProtocolloAllegato.getId() == TipoProtocolloAllegato.NON_PROTOCOLLARE.getId()) {
			CnmDStatoAllegato cnmDStatoAllegato = cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_NON_PROTOCOLLARE);
			ResponseSalvaDocumento responseSalvaDocumento = stadocServiceFacade.salvaDocumentoIndex(cnmDTipoAllegato.getDescTipoAllegato(), file, filename, "");
			cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegato);
			cnmTAllegato.setIdActa(null);
			cnmTAllegato.setIdIndex(responseSalvaDocumento.getIdDocumento());
		} else if (tipoProtocolloAllegato.getId() == TipoProtocolloAllegato.DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO.getId()) {
			CnmDStatoAllegato cnmDStatoAllegato = cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO);
			ResponseSalvaDocumento responseSalvaDocumento = stadocServiceFacade.salvaDocumentoIndex(cnmDTipoAllegato.getDescTipoAllegato(), file, filename, "");
			cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegato);
			cnmTAllegato.setIdActa(null);
			cnmTAllegato.setIdIndex(responseSalvaDocumento.getIdDocumento());
		} else if (tipoProtocolloAllegato.getId() == TipoProtocolloAllegato.SALVA_MULTI_SENZA_PROTOCOLARE.getId()) {
			CnmDStatoAllegato cnmDStatoAllegato = cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_NON_PROTOCOLLARE);
			if (isMaster) {
				ResponseArchiviaDocumento responseArchiviaDocumento = stadocServiceFacade.archiviaDocumentoFisico(file, filename, folder, rootActa, numeroAllegati, idEntitaFruitore,
						idTipoAllegato.longValue());
				cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegato);
				cnmTAllegato.setIdActa(responseArchiviaDocumento.getIdDocumento());
				cnmTAllegato.setIdIndex(null);
			} else {
				ResponseSalvaDocumento responseSalvaDocumento = stadocServiceFacade.salvaDocumentoIndex(cnmDTipoAllegato.getDescTipoAllegato(), file, filename, "");
				cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegato);
				cnmTAllegato.setIdActa(null);
				cnmTAllegato.setIdIndex(responseSalvaDocumento.getIdDocumento());
			}
		}

		cnmTAllegato.setCnmDTipoAllegato(cnmDTipoAllegato);
		cnmTAllegato.setDataOraInsert(now);
		cnmTAllegato.setNomeFile(filename);
		cnmTAllegato.setCnmTUser2(cnmTUser);
		cnmTAllegato = cnmTAllegatoRepository.save(cnmTAllegato);

		if (configAllegato == null || configAllegato.isEmpty())
			return cnmTAllegato;

		List<CnmTAllegatoField> cnmTAllegatoFieldList = new ArrayList<>();
		for (AllegatoFieldVO c : configAllegato) {
			CnmTAllegatoField cnmTAllegatoField = new CnmTAllegatoField();
			cnmTAllegatoField.setCnmTAllegato(cnmTAllegato);
			cnmTAllegatoField.setCnmTUser2(cnmTUser);
			cnmTAllegatoField.setDataOraInsert(now);
			Long idField = c.getIdField();
			if (idField == null)
				throw new IllegalArgumentException("Errore field type non valorizzato");

			CnmCField cnmCField = cnmCFieldRepository.findOne(idField);
			cnmTAllegatoField.setCnmCField(cnmCField);
			Long idFieldType = cnmCField.getCnmCFieldType().getIdFieldType();
			if (idFieldType == Constants.FIELD_TYPE_BOOLEAN) {
				cnmTAllegatoField.setValoreBoolean(c.getBooleanValue());
			}
			if (idFieldType == Constants.FIELD_TYPE_NUMERIC || idFieldType == Constants.FIELD_TYPE_ELENCO) {
				cnmTAllegatoField.setValoreNumber(c.getNumberValue());
			}
			if (idFieldType == Constants.FIELD_TYPE_STRING) {
				cnmTAllegatoField.setValoreString(c.getStringValue());
			}
			if (idFieldType == Constants.FIELD_TYPE_DATA_ORA) {
				cnmTAllegatoField.setValoreDataOra(utilsDate.asTimeStamp(c.getDateTimeValue()));
			}

			if (idFieldType == Constants.FIELD_TYPE_DATA) {
				cnmTAllegatoField.setValoreData(utilsDate.asDate(c.getDateValue()));
			}			
			if (idFieldType == Constants.FIELD_TYPE_ELENCO_SOGGETTI) {
				if(c.getNumberValue()!= null) {
					cnmTAllegatoField.setValoreNumber(c.getNumberValue());
				}
			}
			cnmTAllegatoFieldList.add(cnmTAllegatoField);
		}
		if (!cnmTAllegatoFieldList.isEmpty())
			cnmTAllegatoFieldRepository.save(cnmTAllegatoFieldList);

		return cnmTAllegato;
	}
	

	private CnmTAllegato salvaAllegato_Doqui(byte[] file, String filename, Long idTipoAllegato, List<AllegatoFieldVO> configAllegato, CnmTUser cnmTUser, TipoProtocolloAllegato tipoProtocolloAllegato,
			String folder, String idEntitaFruitore, boolean isMaster, boolean protocollazioneInUscita, String soggettoActa, String rootActa, int numeroAllegati, Integer idVerbaleAudizione,
			String tipoActa, List<CnmTSoggetto> cnmTSoggettoList) {	
		CnmDTipoAllegato cnmDTipoAllegato = cnmDTipoAllegatoRepository.findOne(idTipoAllegato);
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		String operationToTrace = null;
		
		if (cnmDTipoAllegato == null)
			throw new SecurityException("id tipo allegato non trovato");

		CnmTAllegato cnmTAllegato = new CnmTAllegato();
		if (idVerbaleAudizione != null && idVerbaleAudizione != 0)
			cnmTAllegato.setIdAllegato(idVerbaleAudizione);
		if (tipoProtocolloAllegato.getId() == TipoProtocolloAllegato.PROTOCOLLARE.getId()) {
			if (folder == null)
				throw new IllegalArgumentException("folder non valorizzato");
			CnmDStatoAllegato cnmDStatoAllegato = cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO);
			ResponseProtocollaDocumento responseProtocollaDocumento = doquiServiceFacade.protocollaDocumentoFisico(folder, file, filename, idEntitaFruitore, isMaster, protocollazioneInUscita,
					soggettoActa, rootActa, idTipoAllegato.longValue(), tipoActa, cnmTSoggettoList, null);
			cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegato);
			cnmTAllegato.setIdActa(responseProtocollaDocumento.getIdDocumento());
			
			// 20201120_LC	traccia inserimento allegato in ACTA post protocollaDOcumentoFisico
			operationToTrace = cnmTAllegato.isFlagDocumentoPregresso() ? TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_ALLEGATO.getOperation();
			utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"objectIdDocumento="+responseProtocollaDocumento.getObjectIdDocumento(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmDTipoAllegato.getDescTipoAllegato());
			
			//20200727_ET aggiunto per segnalazione JIRA: http://jiraprod.csi.it:8083/browse/CONAM-77
			//mi recupero l'allegato e se esiste in tabella un idIndex, prima di resettarlo effettuo la cancellazione su INDEX
			if(cnmTAllegato.getIdAllegato()!=null && cnmTAllegato.getIdAllegato()>0) {
				CnmTAllegato cnmTAllegatoSaved = cnmTAllegatoRepository.findOne(cnmTAllegato.getIdAllegato());
				if(cnmTAllegatoSaved != null && StringUtils.isNotBlank(cnmTAllegatoSaved.getIdIndex())) {
					logger.debug("provo a eliminare da INDEX l'allegato con idIndex : " + cnmTAllegatoSaved.getIdIndex());
					try {
						doquiServiceFacade.eliminaDocumentoIndex(cnmTAllegatoSaved.getIdIndex());
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.SPOSTAMENTO_ALLEGATO_DA_INDEX.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());
					} catch (Exception e) {
						logger.error("Non riesco ad eliminare da INDEX l'allegato con idIndex: " + cnmTAllegatoSaved.getIdIndex());
					}
				}
			}
			
			cnmTAllegato.setIdIndex(null);
			cnmTAllegato.setNumeroProtocollo(responseProtocollaDocumento.getProtocollo());
			cnmTAllegato.setDataOraProtocollo(now);
		} else if (tipoProtocolloAllegato.getId() == TipoProtocolloAllegato.NON_PROTOCOLLARE.getId()) {
			CnmDStatoAllegato cnmDStatoAllegato = cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_NON_PROTOCOLLARE);
			ResponseSalvaDocumento responseSalvaDocumento = doquiServiceFacade.salvaDocumentoIndex(cnmDTipoAllegato.getDescTipoAllegato(), file, filename, "", cnmDTipoAllegato.getIndexType());
			cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegato);
			cnmTAllegato.setIdActa(null);
			cnmTAllegato.setIdIndex(responseSalvaDocumento.getIdDocumento());
		} else if (tipoProtocolloAllegato.getId() == TipoProtocolloAllegato.DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO.getId()) {
			CnmDStatoAllegato cnmDStatoAllegato = cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO);
			ResponseSalvaDocumento responseSalvaDocumento = doquiServiceFacade.salvaDocumentoIndex(cnmDTipoAllegato.getDescTipoAllegato(), file, filename, "", cnmDTipoAllegato.getIndexType());
			cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegato);
			cnmTAllegato.setIdActa(null);
			cnmTAllegato.setIdIndex(responseSalvaDocumento.getIdDocumento());
		} else if (tipoProtocolloAllegato.getId() == TipoProtocolloAllegato.SALVA_MULTI_SENZA_PROTOCOLARE.getId()) {
			CnmDStatoAllegato cnmDStatoAllegato = cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_NON_PROTOCOLLARE);
			if (isMaster) {
				ResponseArchiviaDocumento responseArchiviaDocumento = doquiServiceFacade.archiviaDocumentoFisico(file, filename, folder, rootActa, numeroAllegati, idEntitaFruitore,
						idTipoAllegato.longValue(), isMaster, null, null);
				cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegato);
				cnmTAllegato.setIdActa(responseArchiviaDocumento.getIdDocumento());
				cnmTAllegato.setIdIndex(null);
				
				// 20201120_LC	traccia inserimento allegato in ACTA post archiviaDOcumentoFisico
				operationToTrace = cnmTAllegato.isFlagDocumentoPregresso() ? TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_ALLEGATO.getOperation();
				utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"objectIdDocumento="+responseArchiviaDocumento.getObjectIdDocumento(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmDTipoAllegato.getDescTipoAllegato());

				
			} else {
				ResponseSalvaDocumento responseSalvaDocumento = doquiServiceFacade.salvaDocumentoIndex(cnmDTipoAllegato.getDescTipoAllegato(), file, filename, "", cnmDTipoAllegato.getIndexType());
				cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegato);
				cnmTAllegato.setIdActa(null);
				cnmTAllegato.setIdIndex(responseSalvaDocumento.getIdDocumento());
			}
		}

		cnmTAllegato.setCnmDTipoAllegato(cnmDTipoAllegato);
		cnmTAllegato.setDataOraInsert(now);
		cnmTAllegato.setNomeFile(filename);
		cnmTAllegato.setCnmTUser2(cnmTUser);
		cnmTAllegato = cnmTAllegatoRepository.save(cnmTAllegato);

		// 20201120_LC traccia inserimento allegato in CONAM
		operationToTrace = cnmTAllegato.isFlagDocumentoPregresso() ? TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_ALLEGATO.getOperation();
		utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmDTipoAllegato.getDescTipoAllegato());
		

		if (configAllegato == null || configAllegato.isEmpty())
			return cnmTAllegato;

		List<CnmTAllegatoField> cnmTAllegatoFieldList = new ArrayList<>();
		for (AllegatoFieldVO c : configAllegato) {
			CnmTAllegatoField cnmTAllegatoField = new CnmTAllegatoField();
			cnmTAllegatoField.setCnmTAllegato(cnmTAllegato);
			cnmTAllegatoField.setCnmTUser2(cnmTUser);
			cnmTAllegatoField.setDataOraInsert(now);
			Long idField = c.getIdField();
			if (idField == null)
				throw new IllegalArgumentException("Errore field type non valorizzato");

			CnmCField cnmCField = cnmCFieldRepository.findOne(idField);
			cnmTAllegatoField.setCnmCField(cnmCField);
			Long idFieldType = cnmCField.getCnmCFieldType().getIdFieldType();
			if (idFieldType == Constants.FIELD_TYPE_BOOLEAN) {
				cnmTAllegatoField.setValoreBoolean(c.getBooleanValue());
			}
			if (idFieldType == Constants.FIELD_TYPE_NUMERIC || idFieldType == Constants.FIELD_TYPE_ELENCO) {
				cnmTAllegatoField.setValoreNumber(c.getNumberValue());
			}
			if (idFieldType == Constants.FIELD_TYPE_STRING) {
				cnmTAllegatoField.setValoreString(c.getStringValue());
			}
			if (idFieldType == Constants.FIELD_TYPE_DATA_ORA) {
				cnmTAllegatoField.setValoreDataOra(utilsDate.asTimeStamp(c.getDateTimeValue()));
			}
			if (idFieldType == Constants.FIELD_TYPE_DATA) {
				cnmTAllegatoField.setValoreData(utilsDate.asDate(c.getDateValue()));
			}			
			if (idFieldType == Constants.FIELD_TYPE_ELENCO_SOGGETTI) {
				if(c.getNumberValue()!= null) {
					cnmTAllegatoField.setValoreNumber(c.getNumberValue());
				}
			}
			cnmTAllegatoFieldList.add(cnmTAllegatoField);
		}
		if (!cnmTAllegatoFieldList.isEmpty())
			cnmTAllegatoFieldRepository.save(cnmTAllegatoFieldList);

		return cnmTAllegato;
	}

	@Override
	@Transactional
	public void eliminaAllegatoBy(CnmTAllegato cnmTAllegato) {
		if(isDoquiDirect()) {
			eliminaAllegatoBy_Doqui(cnmTAllegato);
		}else {
			eliminaAllegatoBy_Stadoc(cnmTAllegato);
		}
	}
	
	private void eliminaAllegatoBy_Stadoc(CnmTAllegato cnmTAllegato) {
		String idIndex = validateRequestAllegatoAndGetIdIndex(cnmTAllegato);
		ResponseEliminaDocumento response = stadocServiceFacade.eliminaDocumentoIndex(idIndex);
		if (response == null)
			throw new RuntimeException("documento non trovato su per idIndex:" + idIndex);

		List<CnmTAllegatoField> field = cnmTAllegatoFieldRepository.findByCnmTAllegato(cnmTAllegato);
		if (field != null && !field.isEmpty())
			cnmTAllegatoFieldRepository.delete(field);

		cnmTAllegatoRepository.delete(cnmTAllegato);

	}

	private void eliminaAllegatoBy_Doqui(CnmTAllegato cnmTAllegato) {
		// 20200722_LC gestire il caso di elimina quando il documento è già su acta (aggiunto tramite ricercaProtocollo)?
		// cancella solo dal DB COnam ma non da Acta (trasparente per l'utente)
		
		String idIndex = validateRequestAllegatoAndGetIdIndex(cnmTAllegato);
		
//		if (idIndex == null)
//			throw new RuntimeException("impossibile cancellare il documento: documento non presente in Index.");	
		
		ResponseEliminaDocumento response = null;
		
		if (idIndex!=null) {
			response = doquiServiceFacade.eliminaDocumentoIndex(idIndex);	
			
			if (response == null)
				throw new RuntimeException("documento non trovato su per idIndex:" + idIndex);		
	
		}	

		// 20200722_LC cancella da Conam ma non da Acta (non può)
		List<CnmTAllegatoField> field = cnmTAllegatoFieldRepository.findByCnmTAllegato(cnmTAllegato);
		if (field != null && !field.isEmpty())
			cnmTAllegatoFieldRepository.delete(field);

		cnmTAllegatoRepository.delete(cnmTAllegato);

	}

	private String validateRequestAllegatoAndGetIdIndex(CnmTAllegato cnmTAllegato) {
		cnmTAllegato = validateRequestAllegato(cnmTAllegato);

		// 20200722_LC per ora cancella solo dal DB se idIndex è null
//		if (cnmTAllegato.getIdIndex() == null)
//			throw new RuntimeException("id index non valorizzato ");

		return cnmTAllegato.getIdIndex();
	}

	private CnmTAllegato validateRequestAllegato(CnmTAllegato cnmTAllegato) {
		if (cnmTAllegato == null)
			throw new SecurityException("id allegato non esistente");
		return cnmTAllegato;
	}

	@Override
	public List<SelectVO> getDecodificaSelectAllegato(Long decodificaSelect) {
		if (decodificaSelect == null)
			throw new IllegalArgumentException("decodifica select == null");
		List<CnmDElementoElenco> cnmDElementoElencos = cnmDElementoElencoRepository.findByIdElenco(new BigDecimal(decodificaSelect));
		return elementoElencoEntityMapper.mapListEntityToListVO(cnmDElementoElencos);
	}
	
	@Override
	public List<SelectVO> getDecodificaSelectSoggettiAllegato(Integer idVerbale) {
		List<SelectVO> soggettiRelataVO = null;
		if (idVerbale == null)
			throw new IllegalArgumentException("idverbale select soggetti == null");
		List<SoggettoVO> soggettiRelata = soggettoService.ricercaSoggettiRelata(idVerbale, null);
		if(soggettiRelata!= null && soggettiRelata.size()>0) {
			soggettiRelataVO = new ArrayList<SelectVO>();
			for(SoggettoVO soggettoRelata : soggettiRelata) {
				SelectVO soggettoVO = new SelectVO();
				soggettoVO.setDenominazione(soggettoRelata.getCognome() + " " +soggettoRelata.getNome());
				soggettoVO.setId(new Long(soggettoRelata.getIdSoggettoVerbale()));
				soggettiRelataVO.add(soggettoVO);
			}
		}
		return soggettiRelataVO;
	}

	@Override
	public List<ConfigAllegatoVO> getConfigAllegati() {
		return configAllegatoEntityMapper.mapListEntityToListVO(cnmCFieldRepository.findAllByFineValidita());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ParentRequest> T getRequest(List<InputPart> data, List<InputPart> file, Class<? extends ParentRequest> generic, boolean multi) {
		T request = null;
		Map<String, byte[]> fileMap = new HashMap<>();

		// Recupero dal multipart i file caricati
		if (file != null && !file.isEmpty()) {
			try {
				for (InputPart inputPart : file) {
					InputStream inputStream = inputPart.getBody(InputStream.class, null);
					fileMap.put(UploadUtils.getFileName(inputPart), IOUtils.toByteArray(inputStream));
				}
			} catch (IOException e) {
				throw new RuntimeException("Errore estrazione file in upload", e);
			}
		}
		// Recupero dal multipart gli input dei form
		if (data != null && !data.isEmpty()) {
			StringBuilder body = new StringBuilder();
			try {
				for (InputPart aut : data) {
					body.append(aut.getBodyAsString());
				}
				ObjectMapper mapper = new ObjectMapper();
				request = (T) mapper.readValue(body.toString(), generic);
			} catch (IOException e) {
				throw new RuntimeException("Errore estrazione data di upload", e);
			}
		}

		// 20210430_LC	-	gestisce chiamate senza "file"
		if (file != null && !file.isEmpty()) {
			Map.Entry<String, byte[]> entry = fileMap.entrySet().iterator().next();
			if (!multi) {
				PropertyAccessor myAccessor = PropertyAccessorFactory.forDirectFieldAccess(request);
				myAccessor.setPropertyValue("file", entry.getValue());
			} else {
				SalvaAllegatiMultipliRequest allegati = (SalvaAllegatiMultipliRequest) request;
				for (AllegatoMultiploVO allegato : allegati.getAllegati()) {
					if (fileMap.containsKey(allegato.getFilename()))
						allegato.setFile(fileMap.get(allegato.getFilename()));
				}
			}		
		} 

		return request;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ParentRequest> T getRequest(List<InputPart> data, List<InputPart> file, Class<? extends ParentRequest> generic) {
		return getRequest(data, file, generic, false);
	}

	@Override
	@Transactional
	public ResponseProtocollaDocumento avviaProtocollazione(List<CnmRAllegatoVerbale> cnmRAllegatoVerbaleList, CnmTUser cnmTUser) {
		if(isDoquiDirect()) {
			return avviaProtocollazione_Doqui(cnmRAllegatoVerbaleList, cnmTUser);	// 20200706_LC
		}else {
			return avviaProtocollazione_Stadoc(cnmRAllegatoVerbaleList);
		}
	}
	
	
	
	private ResponseProtocollaDocumento avviaProtocollazione_Stadoc(List<CnmRAllegatoVerbale> cnmRAllegatoVerbaleList) {
		CnmRAllegatoVerbale cnmRAllegatoVerbale = Iterables.tryFind(cnmRAllegatoVerbaleList, UtilsTipoAllegato.findAllegatoInCnmRAllegatoVerbaleByTipoAllegato(TipoAllegato.RAPPORTO_TRASMISSIONE))
				.orNull();
		if (cnmRAllegatoVerbale == null)
			throw new RuntimeException("verbale di trasmissione non trovato");
		CnmTAllegato cnmTAllegato = cnmRAllegatoVerbale.getCnmTAllegato();
		if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() != Constants.STATO_ALLEGATO_DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO)
			throw new RuntimeException("Rapporto di trasmissione non è nello stato corretto");

		// preparazione parametri protocollazione
		CnmTVerbale cnmTVerbale = cnmRAllegatoVerbale.getCnmTVerbale();
		// ResponseRicercaAllegato document =
		// stadocServiceFacade.recuperaDocumentoIndex(cnmTAllegato.getIdIndex());
		if (cnmTAllegato.getIdIndex() == null)
			throw new RuntimeException("documento non trovato su index");
		ResponseProtocollaDocumento responseProtocollaDocumento = stadocServiceFacade.protocollaDocumentoFisico(utilsDoqui.createOrGetfolder(cnmTVerbale), null, cnmTAllegato.getNomeFile(),
				utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()), true, false, utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale),
				cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), StadocServiceFacade.TIPOLOGIA_DOC_ACTA_MASTER_INGRESSO_CON_ALLEGATI, null, cnmTAllegato.getIdIndex());

		cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
		cnmTAllegato.setIdActa(responseProtocollaDocumento.getIdDocumento());
		cnmTAllegato.setIdIndex(null);
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		cnmTAllegato.setDataOraUpdate(now);

		List<CnmTAllegato> cnmTAllegatoList = new ArrayList<>();
		CnmDStatoAllegato cnmDStatoAllegato = cnmDStatoAllegatoRepository.findOne(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA);
		for (CnmRAllegatoVerbale cnmRAllegatoVerbaleT : cnmRAllegatoVerbaleList) {
			CnmTAllegato cnmTAllegatoT = cnmRAllegatoVerbaleT.getCnmTAllegato();
			boolean rapportoTrasmissione = cnmTAllegatoT.getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.RAPPORTO_TRASMISSIONE.getId();
			boolean statoDaProtocollareInAcquisizione = cnmTAllegatoT.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO;
			if (!rapportoTrasmissione && statoDaProtocollareInAcquisizione) {
				cnmTAllegatoT.setCnmDStatoAllegato(cnmDStatoAllegato);
				cnmTAllegatoList.add(cnmTAllegatoT);
			}
		}

		cnmTAllegatoList.add(cnmTAllegato);
		cnmTAllegatoRepository.save(cnmTAllegatoList);

		return responseProtocollaDocumento;
	}

	private ResponseProtocollaDocumento avviaProtocollazione_Doqui(List<CnmRAllegatoVerbale> cnmRAllegatoVerbaleList, CnmTUser cnmTUser) {
		CnmRAllegatoVerbale cnmRAllegatoVerbale = Iterables.tryFind(cnmRAllegatoVerbaleList, UtilsTipoAllegato.findAllegatoInCnmRAllegatoVerbaleByTipoAllegato(TipoAllegato.RAPPORTO_TRASMISSIONE))
				.orNull();
		if (cnmRAllegatoVerbale == null)
			throw new RuntimeException("verbale di trasmissione non trovato");
		
		ResponseProtocollaDocumento responseProtocollaDocumento = null;		// 20200706_LC
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

		List<CnmTAllegato> cnmTAllegatoList = new ArrayList<>();

		CnmTAllegato cnmTAllegato = cnmRAllegatoVerbale.getCnmTAllegato();
		
		// può essere in stato 2,3 ma anche in stato 7 (nel caso si debba spostare)
		if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() != Constants.STATO_ALLEGATO_PROTOCOLLATO && 
				cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() != Constants.STATO_ALLEGATO_DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO && 
				cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() != Constants.STATO_ALLEGATO_PROTOCOLLATO_IN_ALTRA_STRUTTURA)
			throw new RuntimeException("Rapporto di trasmissione non e' nello stato corretto");
		
		
		// preparazione parametri protocollazione
		CnmTVerbale cnmTVerbale = cnmRAllegatoVerbale.getCnmTVerbale();

		Map<String,String> protocolliGestiti= new HashMap<>();
		
		// 20201123_LC
		String operationToTrace = null;

		if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_PROTOCOLLATO_IN_ALTRA_STRUTTURA
				||cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_PROTOCOLLATO) {
			// DOCUMENTO GIA PROTOCOLLATO (IN ALTRA STRUTTURA)			
			
			responseProtocollaDocumento = gestisciDocumentoProtocollato(cnmTAllegato, cnmTVerbale, protocolliGestiti);				
			tracciaSuCsiLogAudit(protocolliGestiti, cnmTAllegato, responseProtocollaDocumento != null ? responseProtocollaDocumento.getObjectIdDocumentoToTraceList() : null);
			if(responseProtocollaDocumento == null) {
				responseProtocollaDocumento = new ResponseProtocollaDocumento();
			}
			responseProtocollaDocumento.setProtocollo(cnmTAllegato.getNumeroProtocollo());
			
			
			
			// aggiornare allegati (vanno in stato 2), ma hanno già numero e data protocollo. non solo il RapportoTrasmissione, ma tutti
			for (CnmRAllegatoVerbale cnmRAllegatoVerbaleTs : cnmRAllegatoVerbaleList) {
				CnmTAllegato cnmTAllegatoTs = cnmRAllegatoVerbaleTs.getCnmTAllegato();				
				
//				20201014_ET  JIRA CONAM-98 potrebbero esserci degli allegati che non sono associati al protocollo, sono quindi da protocollare
				if(cnmTAllegatoTs.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO) {
					
					ResponseProtocollaDocumento responseProtocollaDocumentoAllegato = doquiServiceFacade.protocollaDocumentoFisico(utilsDoqui.createOrGetfolder(cnmTVerbale), null, cnmTAllegatoTs.getNomeFile(),
							utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegatoTs.getCnmDTipoAllegato()), false, false, utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale),
							cnmTAllegatoTs.getCnmDTipoAllegato().getIdTipoAllegato(), DoquiServiceFacade.TIPOLOGIA_DOC_ACTA_DOC_INGRESSO_SENZA_ALLEGATI, null, cnmTAllegatoTs.getIdIndex());
			
					cnmTAllegatoTs.setIdActa(responseProtocollaDocumentoAllegato.getIdDocumento());
					
					try {
						doquiServiceFacade.eliminaDocumentoIndex(cnmTAllegatoTs.getIdIndex());
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.SPOSTAMENTO_ALLEGATO_DA_INDEX.getOperation(),cnmTAllegatoTs.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegatoTs.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegatoTs.getCnmDTipoAllegato().getDescTipoAllegato());
					} catch (Exception e) {
						logger.error("Non riesco ad eliminare l'allegato con idIndex :: " + cnmTAllegatoTs.getIdIndex());
					}
					cnmTAllegatoTs.setIdIndex(null);					
					cnmTAllegatoTs.setNumeroProtocollo(responseProtocollaDocumentoAllegato.getProtocollo());
					cnmTAllegatoTs.setDataOraProtocollo(now);
					
					// 20201120_LC traccia inserimento su ACTA allegato "jira conam-98" (non sono da contrassegnare con "TI" o "CI")
					operationToTrace = cnmTAllegatoTs.isFlagDocumentoPregresso() ? TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_ALLEGATO.getOperation();
					utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"objectIdDocumento="+responseProtocollaDocumentoAllegato.getObjectIdDocumento(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegatoTs.getCnmDTipoAllegato().getDescTipoAllegato());
				
					// 20201120_LC traccia inserimento su ACTA fascicolo "jira conam-98" (non sono da contrassegnare con "TI" o "CI")
					if (responseProtocollaDocumentoAllegato.getIdFolder() != null) {
						operationToTrace = cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() > 1 ? TraceOperation.INSERIMENTO_FASCICOLO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_FASCICOLO.getOperation();
						utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"idFolder="+responseProtocollaDocumentoAllegato.getIdFolder(), Thread.currentThread().getStackTrace()[1].getMethodName(), "creaFascicoloSuActa");
					}
					
				} 
				else {
					
					//20201021_ET devo controllare che l'allegato appartenga al protocollo gia spostato, se non e' cosi devo spostare anche questo nuovo protocollo
					if(!protocolliGestiti.containsKey(cnmTAllegatoTs.getNumeroProtocollo())) {
						logger.debug("trovato un nuovo protocollo:"+cnmTAllegatoTs.getNumeroProtocollo()+" diverso da quello del rapporto di trasmissione: "+responseProtocollaDocumento.getProtocollo());
						ResponseProtocollaDocumento responseProtocollaDocumentoAltroProt = gestisciDocumentoProtocollato(cnmTAllegatoTs, cnmTVerbale, protocolliGestiti);
						tracciaSuCsiLogAudit(protocolliGestiti, cnmTAllegatoTs, responseProtocollaDocumentoAltroProt != null ? responseProtocollaDocumentoAltroProt.getObjectIdDocumentoToTraceList() : null);	
					}
						
				}
				cnmTAllegatoTs.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
				cnmTAllegatoTs.setObjectidSpostamentoActa(null);
				cnmTAllegatoTs.setDataOraUpdate(now);					
				cnmTAllegatoTs.setCnmTUser1(cnmTUser);	
					
				// 20200722_LC non lo fa qui, lo fa prima quando li assegni al verbale - per essere spostato il doc deve già essere assegnato al verbale
				// trace allegati appena spostati con la moveDocument
				//utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO.getOperation(),cnmTAllegatoTs.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegatoTs.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName());
				
				cnmTAllegatoList.add(cnmTAllegatoTs);				
			}
			
			// 20211203 PP - aggiungo agli allegati anche le relate di notifica in stato (2 o 7)
			List<CnmTAllegato> allegatiRelata = cnmTAllegatoRepository.findAllegatiRelataGiaProtocollati(cnmTVerbale.getIdVerbale());
			for (CnmTAllegato cnmTAllegatoTs : allegatiRelata) {			
				
//				20201014_ET  JIRA CONAM-98 potrebbero esserci degli allegati che non sono associati al protocollo, sono quindi da protocollare
				if(cnmTAllegatoTs.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO) {
					
					ResponseProtocollaDocumento responseProtocollaDocumentoAllegato = doquiServiceFacade.protocollaDocumentoFisico(utilsDoqui.createOrGetfolder(cnmTVerbale), null, cnmTAllegatoTs.getNomeFile(),
							utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegatoTs.getCnmDTipoAllegato()), false, false, utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale),
							cnmTAllegatoTs.getCnmDTipoAllegato().getIdTipoAllegato(), DoquiServiceFacade.TIPOLOGIA_DOC_ACTA_DOC_INGRESSO_SENZA_ALLEGATI, null, cnmTAllegatoTs.getIdIndex());
			
					cnmTAllegatoTs.setIdActa(responseProtocollaDocumentoAllegato.getIdDocumento());
					
					try {
						doquiServiceFacade.eliminaDocumentoIndex(cnmTAllegatoTs.getIdIndex());
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.SPOSTAMENTO_ALLEGATO_DA_INDEX.getOperation(),cnmTAllegatoTs.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegatoTs.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegatoTs.getCnmDTipoAllegato().getDescTipoAllegato());
					} catch (Exception e) {
						logger.error("Non riesco ad eliminare l'allegato con idIndex :: " + cnmTAllegatoTs.getIdIndex());
					}
					cnmTAllegatoTs.setIdIndex(null);					
					cnmTAllegatoTs.setNumeroProtocollo(responseProtocollaDocumentoAllegato.getProtocollo());
					cnmTAllegatoTs.setDataOraProtocollo(now);
					
					// 20201120_LC traccia inserimento su ACTA allegato "jira conam-98" (non sono da contrassegnare con "TI" o "CI")
					operationToTrace = cnmTAllegatoTs.isFlagDocumentoPregresso() ? TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_ALLEGATO.getOperation();
					utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"objectIdDocumento="+responseProtocollaDocumentoAllegato.getObjectIdDocumento(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegatoTs.getCnmDTipoAllegato().getDescTipoAllegato());
				
					// 20201120_LC traccia inserimento su ACTA fascicolo "jira conam-98" (non sono da contrassegnare con "TI" o "CI")
					if (responseProtocollaDocumentoAllegato.getIdFolder() != null) {
						operationToTrace = cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() > 1 ? TraceOperation.INSERIMENTO_FASCICOLO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_FASCICOLO.getOperation();
						utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"idFolder="+responseProtocollaDocumentoAllegato.getIdFolder(), Thread.currentThread().getStackTrace()[1].getMethodName(), "creaFascicoloSuActa");
					}
					
				} 
				else {
					
					//20201021_ET devo controllare che l'allegato appartenga al protocollo gia spostato, se non e' cosi devo spostare anche questo nuovo protocollo
					if(!protocolliGestiti.containsKey(cnmTAllegatoTs.getNumeroProtocollo())) {
						logger.debug("trovato un nuovo protocollo:"+cnmTAllegatoTs.getNumeroProtocollo()+" diverso da quello del rapporto di trasmissione: "+responseProtocollaDocumento.getProtocollo());
						ResponseProtocollaDocumento responseProtocollaDocumentoAltroProt = gestisciDocumentoProtocollato(cnmTAllegatoTs, cnmTVerbale, protocolliGestiti);
						tracciaSuCsiLogAudit(protocolliGestiti, cnmTAllegatoTs, responseProtocollaDocumentoAltroProt != null ? responseProtocollaDocumentoAltroProt.getObjectIdDocumentoToTraceList() : null);	
					}
						
				}
				cnmTAllegatoTs.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
				cnmTAllegatoTs.setObjectidSpostamentoActa(null);
				cnmTAllegatoTs.setDataOraUpdate(now);					
				cnmTAllegatoTs.setCnmTUser1(cnmTUser);	
					
				// 20200722_LC non lo fa qui, lo fa prima quando li assegni al verbale - per essere spostato il doc deve già essere assegnato al verbale
				// trace allegati appena spostati con la moveDocument
				//utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO.getOperation(),cnmTAllegatoTs.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegatoTs.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName());
				
				cnmTAllegatoList.add(cnmTAllegatoTs);				
			}
						
			// 20211126 PP
			// cerco anche la relata di notifica in stato 3 per protocollare
			CnmDStatoAllegato cnmDStatoAllegato = cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO);
			allegatiRelata = cnmTAllegatoRepository.findAllegatiRelataDaProtocollare(cnmTVerbale.getIdVerbale());
			for(CnmTAllegato cnmTAllegatoTs : allegatiRelata) {
				ResponseProtocollaDocumento responseProtocollaDocumentoAllegato = doquiServiceFacade.protocollaDocumentoFisico(utilsDoqui.createOrGetfolder(cnmTVerbale), null, cnmTAllegatoTs.getNomeFile(),
						utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegatoTs.getCnmDTipoAllegato()), false, false, utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale),
						cnmTAllegatoTs.getCnmDTipoAllegato().getIdTipoAllegato(), DoquiServiceFacade.TIPOLOGIA_DOC_ACTA_DOC_INGRESSO_SENZA_ALLEGATI, null, cnmTAllegatoTs.getIdIndex());
		
				cnmTAllegatoTs.setIdActa(responseProtocollaDocumentoAllegato.getIdDocumento());
				
				try {
					doquiServiceFacade.eliminaDocumentoIndex(cnmTAllegatoTs.getIdIndex());
					utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.SPOSTAMENTO_ALLEGATO_DA_INDEX.getOperation(),cnmTAllegatoTs.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegatoTs.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegatoTs.getCnmDTipoAllegato().getDescTipoAllegato());
				} catch (Exception e) {
					logger.error("Non riesco ad eliminare l'allegato con idIndex :: " + cnmTAllegatoTs.getIdIndex());
				}
				cnmTAllegatoTs.setIdIndex(null);					
				cnmTAllegatoTs.setNumeroProtocollo(responseProtocollaDocumentoAllegato.getProtocollo());
				cnmTAllegatoTs.setDataOraProtocollo(now);

				cnmTAllegatoTs.setCnmDStatoAllegato(cnmDStatoAllegato);			
				cnmTAllegatoTs.setCnmTUser1(cnmTUser);
				cnmTAllegatoList.add(cnmTAllegatoTs);
				
				// 20201120_LC traccia inserimento su ACTA allegato "jira conam-98" (non sono da contrassegnare con "TI" o "CI")
				operationToTrace = cnmTAllegatoTs.isFlagDocumentoPregresso() ? TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_ALLEGATO.getOperation();
				utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"objectIdDocumento="+responseProtocollaDocumentoAllegato.getObjectIdDocumento(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegatoTs.getCnmDTipoAllegato().getDescTipoAllegato());
			
				// 20201120_LC traccia inserimento su ACTA fascicolo "jira conam-98" (non sono da contrassegnare con "TI" o "CI")
				if (responseProtocollaDocumentoAllegato.getIdFolder() != null) {
					operationToTrace = cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() > 1 ? TraceOperation.INSERIMENTO_FASCICOLO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_FASCICOLO.getOperation();
					utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"idFolder="+responseProtocollaDocumentoAllegato.getIdFolder(), Thread.currentThread().getStackTrace()[1].getMethodName(), "creaFascicoloSuActa");
				}
			}
			
						
		} else {
			// DOCUMENTO DA PROTOCOLLARE
			// 20210804 CR_107 PP - Per consentire di inserire il numero di protocollo anche sugli allegati, non protocollo in questo momento, ma solo dopo aver spostato gli allegati trmite lo scheduler
			
			ResponseArchiviaDocumento responseArchiviaDocumento = doquiServiceFacade.archiviaDocumentoFisico(null, cnmTAllegato.getNomeFile(), utilsDoqui.createOrGetfolder(cnmTVerbale), 
					utilsDoqui.getRootActa(cnmTVerbale), 0, utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()),
					cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), true, cnmTAllegato.getIdIndex(), utilsDoqui.getSoggettoActa(cnmTVerbale));
			
			responseProtocollaDocumento = new ResponseProtocollaDocumento();
			responseProtocollaDocumento.setIdDocumento(responseArchiviaDocumento.getIdDocumento());
			responseProtocollaDocumento.setIdFolder(responseArchiviaDocumento.getIdFolder());
			responseProtocollaDocumento.setObjectIdDocumento(responseArchiviaDocumento.getObjectIdDocumento());
			
//			responseProtocollaDocumento = doquiServiceFacade.protocollaDocumentoFisico(utilsDoqui.createOrGetfolder(cnmTVerbale), null, cnmTAllegato.getNomeFile(),
//					utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()), true, false, utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale),
//					cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), DoquiServiceFacade.TIPOLOGIA_DOC_ACTA_MASTER_INGRESSO_CON_ALLEGATI, null, cnmTAllegato.getIdIndex());
	
			cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_NON_PROTOCOLLARE));
			cnmTAllegato.setObjectidSpostamentoActa(null);
			cnmTAllegato.setIdActa(responseProtocollaDocumento.getIdDocumento());
			
			// 20201120_LC	traccia inserimento su ACTA rapporto trasmissione
			operationToTrace = cnmTAllegato.isFlagDocumentoPregresso() ? TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_ALLEGATO.getOperation();
			utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"objectIdDocumento="+responseProtocollaDocumento.getObjectIdDocumento(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

			// 20201120_LC traccia inserimento su ACTA fascicolo standard
			if (responseProtocollaDocumento.getIdFolder() != null) {
				operationToTrace = cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() > 1 ? TraceOperation.INSERIMENTO_FASCICOLO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_FASCICOLO.getOperation();
				utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"idFolder="+responseProtocollaDocumento.getIdFolder(), Thread.currentThread().getStackTrace()[1].getMethodName(), "creaFascicoloSuActa");
			
			}


			
			//20200724_ET aggiunto per segnalazione JIRA: http://jiraprod.csi.it:8083/browse/CONAM-76
			try {
				doquiServiceFacade.eliminaDocumentoIndex(cnmTAllegato.getIdIndex());
				utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.SPOSTAMENTO_ALLEGATO_DA_INDEX.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());
			} catch (Exception e) {
				logger.error("Non riesco ad eliminare l'allegato con idIndex :: " + cnmTAllegato.getIdIndex());
			}
			cnmTAllegato.setIdIndex(null);
			cnmTAllegato.setDataOraUpdate(now);			
			
			// 20210804_PP - la protocollazione viene fatta successivamente
//			cnmTAllegato.setNumeroProtocollo(responseProtocollaDocumento.getProtocollo());
//			cnmTAllegato.setDataOraProtocollo(now);
	
			//List<CnmTAllegato> cnmTAllegatoList = new ArrayList<>();
			CnmDStatoAllegato cnmDStatoAllegato = cnmDStatoAllegatoRepository.findOne(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA);
			for (CnmRAllegatoVerbale cnmRAllegatoVerbaleT : cnmRAllegatoVerbaleList) {
				CnmTAllegato cnmTAllegatoT = cnmRAllegatoVerbaleT.getCnmTAllegato();
				boolean rapportoTrasmissione = cnmTAllegatoT.getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.RAPPORTO_TRASMISSIONE.getId();
				boolean statoDaProtocollareInAcquisizione = cnmTAllegatoT.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO;
				boolean statoProtocollatoInAltraStruttura = cnmTAllegatoT.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_PROTOCOLLATO_IN_ALTRA_STRUTTURA;
				if (!rapportoTrasmissione && statoDaProtocollareInAcquisizione) {
					cnmTAllegatoT.setCnmDStatoAllegato(cnmDStatoAllegato);			
					cnmTAllegatoT.setCnmTUser1(cnmTUser);		 // 20200706_LC 
					cnmTAllegatoList.add(cnmTAllegatoT);
				}
				
				//20200723_ET aggiunto per gestire il caso in cui il rapporto di trasmissione e' cartaceo e altri doc sono gia protocollati
					else if (!rapportoTrasmissione && statoProtocollatoInAltraStruttura) {
	//					20201014_ET  richiamato metodo unico che in base al protocollo determina se serve spostare o copiare i documenti
	//					doquiServiceFacade.spostaDocumentoProtocollato(utilsStadoc.createOrGetfolder(cnmTVerbale), cnmTAllegatoT.getNomeFile(),
	//							utilsStadoc.createIdEntitaFruitore(cnmTVerbale, cnmTAllegatoT.getCnmDTipoAllegato()), true, false, utilsStadoc.getSoggettoActa(cnmTVerbale), utilsStadoc.getRootActa(cnmTVerbale), 
	//							cnmTAllegatoT.getCnmDTipoAllegato().getIdTipoAllegato(), cnmTAllegatoT.getNumeroProtocollo(), cnmTVerbale.getIdVerbale());

						ResponseProtocollaDocumento responseProtocollaDocumentoGiaProt = gestisciDocumentoProtocollato(cnmTAllegatoT, cnmTVerbale, protocolliGestiti);
						tracciaSuCsiLogAudit(protocolliGestiti, cnmTAllegatoT, responseProtocollaDocumentoGiaProt != null ? responseProtocollaDocumentoGiaProt.getObjectIdDocumentoToTraceList() : null);
						
						cnmTAllegatoT.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));			
						cnmTAllegatoT.setObjectidSpostamentoActa(null);
						cnmTAllegatoT.setCnmTUser1(cnmTUser);		 // 20200706_LC 
						cnmTAllegatoList.add(cnmTAllegatoT);
					}
			
				
			}
			
			// cerco anche la relata di notifica in stato 3 per metterla in stato 5
			List<CnmTAllegato> allegatiRelata = cnmTAllegatoRepository.findAllegatiRelataDaProtocollare(cnmTVerbale.getIdVerbale());
			for(CnmTAllegato cnmTAllegatoT : allegatiRelata) {
				cnmTAllegatoT.setCnmDStatoAllegato(cnmDStatoAllegato);			
				cnmTAllegatoT.setCnmTUser1(cnmTUser);
				cnmTAllegatoList.add(cnmTAllegatoT);
			}
			
			// 20201120_LC per CONAM è già tracciato
			// utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());
	
			cnmTAllegatoList.add(cnmTAllegato);
			
			
		}
		
				
		cnmTAllegatoRepository.save(cnmTAllegatoList);

		return responseProtocollaDocumento;
	}

	private void tracciaSuCsiLogAudit(Map<String, String> protocolliGestiti, CnmTAllegato cnmTAllegatoTs, List<String> objectIdDocumentoToTraceList) {

		if (objectIdDocumentoToTraceList != null) {

			for (String idToTrace : objectIdDocumentoToTraceList) {

				if(Constants.OPERAZIONE_COPIA_INCOLLA.equals(protocolliGestiti.get(cnmTAllegatoTs.getNumeroProtocollo()))) {
					if(cnmTAllegatoTs.isFlagDocumentoPregresso())
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO_CI.getOperation(),Constants.OGGETTO_ACTA,"objectIdDocumento="+idToTrace, Thread.currentThread().getStackTrace()[1].getMethodName(), "copiaDocumentoSuNuovaStruttura");
					else
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO_CI.getOperation(),Constants.OGGETTO_ACTA,"objectIdDocumento="+idToTrace, Thread.currentThread().getStackTrace()[1].getMethodName(), "copiaDocumentoSuNuovaStruttura");
				} else if (Constants.OPERAZIONE_TAGLIA_INCOLLA.equals(protocolliGestiti.get(cnmTAllegatoTs.getNumeroProtocollo()))) {
					if(cnmTAllegatoTs.isFlagDocumentoPregresso())
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO_TI.getOperation(),Constants.OGGETTO_ACTA,"objectIdDocumento="+idToTrace, Thread.currentThread().getStackTrace()[1].getMethodName(), "spostaDocumentoSuNuovaStruttura");
					else
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO_TI.getOperation(),Constants.OGGETTO_ACTA,"objectIdDocumento="+idToTrace, Thread.currentThread().getStackTrace()[1].getMethodName(), "spostaDocumentoSuNuovaStruttura");
				}
				
			}
			
		}
	

	}
	
	
	
	
	//	20201014_ET  creato metodo unico che in base al protocollo determina se serve spostare o copiare i documenti
	private ResponseProtocollaDocumento gestisciDocumentoProtocollato (CnmTAllegato cnmTAllegato,
			CnmTVerbale cnmTVerbale, Map<String,String> protocolliGestiti) {
		logger.debug("gestisciDocumentoProtocollato per il verbale "+cnmTVerbale.getIdVerbale()+" e allegato = "+ cnmTAllegato.getIdAllegato() +" con getObjectidSpostamentoActa = "+cnmTAllegato.getObjectidSpostamentoActa());
		ResponseProtocollaDocumento responseProtocollaDocumento = null;
		if(protocolliGestiti.containsKey(cnmTAllegato.getNumeroProtocollo())) return null;
		// 20200728_LC verifica presenza del protocollo in Conam
		if (cnmTAllegatoRepository.findByNumeroProtocolloAndObjectidSpostamentoActaIsNull(cnmTAllegato.getNumeroProtocollo()).isEmpty()) {
			// se è vuota sposta (protocollo non presente in Conam)
			
			// responseSpostaDOcumento extends responseProtocollaDocumento
			responseProtocollaDocumento = doquiServiceFacade.spostaDocumentoProtocollato(utilsDoqui.createOrGetfolder(cnmTVerbale), cnmTAllegato.getNomeFile(),
					utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()), true, false, utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale), 
					cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), cnmTAllegato.getNumeroProtocollo(), cnmTVerbale.getIdVerbale());	

//CRP			if (cacheRicercaProtocollo.containsKey(cnmTAllegato.getNumeroProtocollo()))
//CRP				cacheRicercaProtocollo.remove(cnmTAllegato.getNumeroProtocollo());
			
			protocolliGestiti.put(cnmTAllegato.getNumeroProtocollo(), Constants.OPERAZIONE_TAGLIA_INCOLLA);
			
			// 20201123_LC traccia inserimento su ACTA fascicolo post TI/CI	-	se il folder è stato creato
			if (responseProtocollaDocumento.getIdFolder() != null) {
				String operationToTrace = cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() > 1 ? TraceOperation.INSERIMENTO_FASCICOLO_PREGRESSO_TI.getOperation() : TraceOperation.INSERIMENTO_FASCICOLO_TI.getOperation();
				utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"idFolder="+responseProtocollaDocumento.getIdFolder(), Thread.currentThread().getStackTrace()[1].getMethodName(), "creaFascicoloSuActa");			
			}
			
//			if(cnmTAllegato.isFlagDocumentoPregresso())
//				utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO_TI.getOperation(),Constants.OGGETTO_ACTA,"objectIdDocumento="+cnmTAllegato.getObjectidSpostamentoActa(), Thread.currentThread().getStackTrace()[1].getMethodName(), null);
		} else {
			// se non è vuota copia (protocollo già presente in Conam)
			
			// responseSpostaDOcumento extends responseProtocollaDocumento
			if(checkDocumentoGiaCopiato(cnmTAllegato.getNumeroProtocollo(), cnmTVerbale.getIdVerbale(), cnmTAllegato.getIdAllegato())) {
				logger.info("Documento gia' copiato nel fascicolo, non serve aggiungere la classificazione per il verbale "+cnmTVerbale.getIdVerbale());
				protocolliGestiti.put(cnmTAllegato.getNumeroProtocollo(), "");
			}else {
				responseProtocollaDocumento = doquiServiceFacade.copiaDocumentoProtocollato(utilsDoqui.createOrGetfolder(cnmTVerbale), cnmTAllegato.getNomeFile(),
					utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()), true, false, utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale), 
					cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), cnmTAllegato.getNumeroProtocollo(), cnmTVerbale.getIdVerbale());	
//				if(cnmTAllegato.isFlagDocumentoPregresso())
//					utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO_CI.getOperation(),Constants.OGGETTO_ACTA,"objectIdDocumento="+cnmTAllegato.getObjectidSpostamentoActa(), Thread.currentThread().getStackTrace()[1].getMethodName(), null);
				protocolliGestiti.put(cnmTAllegato.getNumeroProtocollo(), Constants.OPERAZIONE_COPIA_INCOLLA);
				
				// 20201123_LC traccia inserimento su ACTA fascicolo post TI/CI	-	se il folder è stato creato
				if (responseProtocollaDocumento.getIdFolder() != null) {
					String operationToTrace = cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() > 1 ? TraceOperation.INSERIMENTO_FASCICOLO_PREGRESSO_CI.getOperation() : TraceOperation.INSERIMENTO_FASCICOLO_CI.getOperation();
					utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"idFolder="+responseProtocollaDocumento.getIdFolder(), Thread.currentThread().getStackTrace()[1].getMethodName(), "creaFascicoloSuActa");			
				}
				
			}
							
		}
		
		
		logger.debug("chiavi dei protocolliGestiti:"+protocolliGestiti.keySet());
		logger.debug("valori dei protocolliGestiti:"+protocolliGestiti.values());
		return responseProtocollaDocumento;
		
	}

	
	private boolean checkDocumentoGiaCopiato(String protocollo, Integer idVerbale, Integer idAllegato) {
		boolean giaCopiato = false;
		List<CnmTAllegato> listGiaSalvato = cnmTAllegatoRepository.findByNumeroProtocolloAndObjectidSpostamentoActaIsNull(protocollo);
		giaSalvatoLoop:
			for(CnmTAllegato allegato:listGiaSalvato) {

				// 20211203 PP - jira-195 - solo se l'allegato che sto controllato non è proprio quello che voglio spostare/copiare su Acta
				if(idAllegato != null && idAllegato!= allegato.getIdAllegato()) {
					
					CnmRAllegatoVerbale findByCnmTAllegato = cnmRAllegatoVerbaleRepository.findByCnmTAllegato(allegato);
					if(findByCnmTAllegato!=null) {
						if(idVerbale.equals(findByCnmTAllegato.getCnmTVerbale().getIdVerbale())) {
							giaCopiato = true;
							break giaSalvatoLoop;
						}
					}else {
						//se non sta sulla rAllegatoVerbale devo cercare su rAllegatoVErbaleSoggetto e rAllegatoOrdinanza
						List<CnmRAllegatoOrdinanza> listaAllegatoOrdinanza = cnmRAllegatoOrdinanzaRepository.findByCnmTAllegato(allegato);
						if(listaAllegatoOrdinanza!=null && !listaAllegatoOrdinanza.isEmpty()) {
							for(CnmRAllegatoOrdinanza rAllegatoOrd: listaAllegatoOrdinanza) {
								List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(rAllegatoOrd.getCnmTOrdinanza());
								List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmROrdinanzaVerbSogsIn(cnmROrdinanzaVerbSogList);
								if(idVerbale.equals(cnmRVerbaleSoggettoList.get(0).getCnmTVerbale().getIdVerbale())) {
									giaCopiato = true;
									break giaSalvatoLoop;
								}
							}
						}else {
							List<CnmRAllegatoOrdVerbSog> cnmROrdinanzaVerbSogList = cnmRAllegatoOrdVerbSogRepository.findByCnmTAllegato(allegato);
							if(cnmROrdinanzaVerbSogList!=null && !cnmROrdinanzaVerbSogList.isEmpty()) {
								for(CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog: cnmROrdinanzaVerbSogList) {
									CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmRAllegatoOrdVerbSog.getCnmROrdinanzaVerbSog();
									List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmROrdinanzaVerbSogsIn(new ArrayList<>(Arrays.asList(cnmROrdinanzaVerbSog)));
									//prendo il primo elemento, tanto anche se ce ne sono diversi, dovrebbero essere legati tutti allo stesso verbale
									if(idVerbale.equals(cnmRVerbaleSoggettoList.get(0).getCnmTVerbale().getIdVerbale())) {
										giaCopiato = true;
										break giaSalvatoLoop;
									}
								}
							} else {
								List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogList = cnmRAllegatoVerbSogRepository.findByCnmTAllegato(allegato);
								if(cnmRAllegatoVerbSogList!=null && !cnmRAllegatoVerbSogList.isEmpty()) {
									for(CnmRAllegatoVerbSog cnmRAllegatoVerbSog: cnmRAllegatoVerbSogList) {
										CnmRVerbaleSoggetto cnmRVerbaleSoggetto = cnmRAllegatoVerbSog.getCnmRVerbaleSoggetto();
										if(cnmRVerbaleSoggetto!= null && cnmRVerbaleSoggetto.getCnmTVerbale()!=null && idVerbale.equals(cnmRVerbaleSoggetto.getCnmTVerbale().getIdVerbale())) {
											giaCopiato = true;
											break giaSalvatoLoop;
										}
									}
								}
							}
						}
					}
				}
			}
		return giaCopiato;
	}
	
	private List<CnmTAllegato> getAllegatiGiaCopiati(String protocollo, Integer idVerbale) {
		List<CnmTAllegato> listGiaSalvato = cnmTAllegatoRepository.findByNumeroProtocolloAndObjectidSpostamentoActaIsNull(protocollo);
		return utilsVerbale.filtraAllegatiPerVerbale(idVerbale, listGiaSalvato);
	}

	/*private void moveAllegatoToActa(CnmTAllegato cnmTAllegato, CnmTVerbale cnmTVerbale) {
		if(isDoquiDirect()) {
			moveAllegatoToActa_Doqui(cnmTAllegato, cnmTVerbale);
		}else {
			moveAllegatoToActa_Stadoc(cnmTAllegato, cnmTVerbale);
		}
	}
	private void moveAllegatoToActa_Stadoc(CnmTAllegato cnmTAllegato, CnmTVerbale cnmTVerbale) {

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmDStatoAllegato avvioSpostamentoActa = cnmDStatoAllegatoRepository.findOne(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA);
		ResponseAggiungiAllegato responseAggiungiAllegato = null;					
		try {
			responseAggiungiAllegato = stadocServiceFacade.aggiungiAllegato(null, cnmTAllegato.getNomeFile(), cnmTAllegato.getIdIndex(), cnmTAllegato.getIdActaMaster(),
					utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()), "", utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale),
					cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), StadocServiceFacade.TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_USCITA, null);
		} catch (Exception e) {
			logger.error("Non riesco ad aggiungere l'allegato al master", e);
			cnmTAllegato.setCnmDStatoAllegato(avvioSpostamentoActa);
			cnmTAllegato.setDataOraUpdate(now);
			updateCnmDStatoAllegato(cnmTAllegato);
		}
		if (responseAggiungiAllegato != null) {
			logger.info("Spostato allegato con id" + cnmTAllegato.getIdAllegato() + "e di tipo:" + cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

			String idIndex = cnmTAllegato.getIdIndex();
			cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_MULTI_NON_PROTOCOLLARE));
			cnmTAllegato.setIdActa(responseAggiungiAllegato.getIdDocumento());
			cnmTAllegato.setIdIndex(null);
			cnmTAllegato.setDataOraUpdate(now);
			updateCnmDStatoAllegato(cnmTAllegato);

			try {
				stadocServiceFacade.eliminaDocumentoIndex(idIndex);
			} catch (Exception e) {
				logger.error("Non riesco ad eliminare l'allegato con idIndex :: " + idIndex);
			}

		}

		CnmRAllegatoVerbale findByCnmTAllegato = cnmRAllegatoVerbaleRepository.findByCnmTAllegato(cnmTAllegato);
		if (findByCnmTAllegato == null || findByCnmTAllegato.getId() == null) {
			logger.info("Mi prendo il master x idActa :: " + cnmTAllegato.getIdActa());
			CnmTVerbale master = cnmTVerbaleRepository.findByIdActa(cnmTAllegato.getIdActaMaster());
			logger.info("id verbale Master :: " + master.getIdVerbale());

			CnmRAllegatoVerbale cnmRAllegatoVerbale = new CnmRAllegatoVerbale();
			CnmRAllegatoVerbalePK cnmRAllegatoVerbalePK = new CnmRAllegatoVerbalePK();
			cnmRAllegatoVerbalePK.setIdAllegato(cnmTAllegato.getIdAllegato());
			cnmRAllegatoVerbalePK.setIdVerbale(master.getIdVerbale());
			cnmRAllegatoVerbale.setCnmTUser(master.getCnmTUser2());
			cnmRAllegatoVerbale.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
			cnmRAllegatoVerbale.setId(cnmRAllegatoVerbalePK);
			cnmRAllegatoVerbaleRepository.save(cnmRAllegatoVerbale);
		}
	}
	

	private void moveAllegatoToActa_Doqui(CnmTAllegato cnmTAllegato, CnmTVerbale cnmTVerbale) {

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmDStatoAllegato avvioSpostamentoActa = cnmDStatoAllegatoRepository.findOne(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA);
		ResponseAggiungiAllegato responseAggiungiAllegato = null;					
		try {
			responseAggiungiAllegato = doquiServiceFacade.aggiungiAllegato(null, cnmTAllegato.getNomeFile(), cnmTAllegato.getIdIndex(), cnmTAllegato.getIdActaMaster(),
					utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()), "", utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale),
					cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), DoquiServiceFacade.TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_USCITA, null);
		} catch (Exception e) {
			logger.error("Non riesco ad aggiungere l'allegato al master", e);
			cnmTAllegato.setCnmDStatoAllegato(avvioSpostamentoActa);
			cnmTAllegato.setDataOraUpdate(now);
			updateCnmDStatoAllegato(cnmTAllegato);
		}
		if (responseAggiungiAllegato != null) {
			logger.info("Spostato allegato con id" + cnmTAllegato.getIdAllegato() + "e di tipo:" + cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

			String idIndex = cnmTAllegato.getIdIndex();
			cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_MULTI_NON_PROTOCOLLARE));
			cnmTAllegato.setIdActa(responseAggiungiAllegato.getIdDocumento());
			cnmTAllegato.setIdIndex(null);
			cnmTAllegato.setDataOraUpdate(now);
			updateCnmDStatoAllegato(cnmTAllegato);

			try {
				doquiServiceFacade.eliminaDocumentoIndex(idIndex);
				utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.SPOSTAMENTO_ALLEGATO_DA_INDEX.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());
			} catch (Exception e) {
				logger.error("Non riesco ad eliminare l'allegato con idIndex :: " + idIndex);
			}

		}

		CnmRAllegatoVerbale findByCnmTAllegato = cnmRAllegatoVerbaleRepository.findByCnmTAllegato(cnmTAllegato);
		if (findByCnmTAllegato == null || findByCnmTAllegato.getId() == null) {
			logger.info("Mi prendo il master x idActa :: " + cnmTAllegato.getIdActa());
			CnmTVerbale master = cnmTVerbaleRepository.findByIdActa(cnmTAllegato.getIdActaMaster());
			logger.info("id verbale Master :: " + master.getIdVerbale());

			CnmRAllegatoVerbale cnmRAllegatoVerbale = new CnmRAllegatoVerbale();
			CnmRAllegatoVerbalePK cnmRAllegatoVerbalePK = new CnmRAllegatoVerbalePK();
			cnmRAllegatoVerbalePK.setIdAllegato(cnmTAllegato.getIdAllegato());
			cnmRAllegatoVerbalePK.setIdVerbale(master.getIdVerbale());
			cnmRAllegatoVerbale.setCnmTUser(master.getCnmTUser2());
			cnmRAllegatoVerbale.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
			cnmRAllegatoVerbale.setId(cnmRAllegatoVerbalePK);
			cnmRAllegatoVerbaleRepository.save(cnmRAllegatoVerbale);
		}
	}*/

	@Override
	public byte[] addProtocolloToDocument(byte[] src, String text, long tipoAllegato) {
		if (tipoAllegato == TipoAllegato.VERBALE_AUDIZIONE.getId())
			return addProtocolloToDocument(src, text, 90f, 559f, 230f, 659f);
		else if (tipoAllegato == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId())
			return addProtocolloToDocument(src, text, 90f, 559f, 230f, 659f);
		else if (tipoAllegato == TipoAllegato.LETTERA_ORDINANZA.getId())
			return addProtocolloToDocument(src, text, 90f, 559f, 230f, 629f);
		else if (tipoAllegato == TipoAllegato.LETTERA_RATEIZZAZIONE.getId())
			return addProtocolloToDocument(src, text, 90f, 559f, 230f, 659f);
		else if (tipoAllegato == TipoAllegato.LETTERA_SOLLECITO.getId())
			return addProtocolloToDocument(src, text, 90f, 559f, 230f, 659f);
		else if (tipoAllegato == TipoAllegato.LETTERA_SOLLECITO_RATE.getId())
			return addProtocolloToDocument(src, text, 90f, 559f, 230f, 629f);
		else
			throw new IllegalArgumentException("tipo Allegato non valorizzato");
	}

	private byte[] addProtocolloToDocument(byte[] src, String text, float llx, float lly, float urx, float ury) {
		PdfReader reader = null;
		ByteArrayOutputStream baos;
		try {
			reader = new PdfReader(src);
			baos = new ByteArrayOutputStream();
			PdfStamper stamper = new PdfStamper(reader, baos);
			PdfContentByte cb = stamper.getOverContent(1);
			BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", BaseFont.EMBEDDED);
			Font f = new Font(bf, 10);
			/*
			 * llx - lower left x - lly - lower left y - urx - upper right x -
			 * ury - upper right y
			 */
			ColumnText ct = new ColumnText(cb);
			ct.setSimpleColumn(llx, lly, urx, ury);
			Paragraph pz = new Paragraph(text, f);
			ct.addElement(pz);
			ct.go();
			stamper.close();
			reader.close();
		} catch (IOException | DocumentException e) {
			logger.error("Errore durante la scrittura del numero protocollo sul pdf", e);
			if (reader != null)
				reader.close();
			return src;
		}
		return baos.toByteArray();

	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<CnmTAllegato> updateCnmDStatoAllegatoInCoda(List<CnmTAllegato> cnmTAllegatos) {
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmDStatoAllegato cnmDStatoAllegatoInCoda = cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_IN_CODA);
		for (CnmTAllegato cnmTAllegato : cnmTAllegatos) {
			if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_AVVIA_SPOSTAMENTO_ACTA) {
				logger.info("Trovato allegato con id" + cnmTAllegato.getIdAllegato() + "in stato:" + cnmTAllegato.getCnmDStatoAllegato().getDescStatoAllegato());
				cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoInCoda);
				cnmTAllegato.setDataOraUpdate(now);
			}
		}
		return (List<CnmTAllegato>) cnmTAllegatoRepository.save(cnmTAllegatos);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public CnmTAllegato updateCnmDStatoAllegato(CnmTAllegato cnmTAllegato) {
		return cnmTAllegatoRepository.save(cnmTAllegato);
	}
	
	@Override
	public RicercaProtocolloSuActaResponse ricercaProtocolloSuACTA(String numProtocollo, Integer idVerbale, Boolean flagPregresso) {
		
		if (numProtocollo == null)
			throw new IllegalArgumentException("numProtocollo non valorizzato");
		if (flagPregresso == null)
			throw new IllegalArgumentException("flagPregresso non valorizzato");
		
//CRP		if (cacheRicercaProtocollo.containsKey(numProtocollo))
//CRP			return cacheRicercaProtocollo.get(numProtocollo);			

		
		// 20200903_LC
		MessageVO messaggio = null;	
		Date dataProtocolloRicercato = null;	
		RicercaProtocolloSuActaResponse response = new RicercaProtocolloSuActaResponse();
		
		
		// 20210427_LC eventuale scritto difensivo da associare
		List<CnmTScrittoDifensivo> cnmTScrittoDifensivoDaAssociareList = cnmTScrittoDifensivoRepository.findByNumeroProtocolloAndFlagAssociatoIsFalse(numProtocollo);
		
		
		// 20200907_LC gestione pregresso (msg se protocollo già presente)
		List<CnmTAllegato> listAllegatiProt = cnmTAllegatoRepository.findByNumeroProtocollo(numProtocollo);
		if (flagPregresso && listAllegatiProt != null && !listAllegatiProt.isEmpty()) {
			// tornare msg configurato SRCHPROT02
			CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.PROTOCOLLO_GIA_PRESENTE_IN_CONAM);
			if(cnmDMessaggio!=null) {
				messaggio = new MessageVO(cnmDMessaggio.getDescMessaggio(), cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
			}else {
				throw new SecurityException("Messaggio non trovato");
			}
			// 20200922_LC
			// no return, continua normalmente (il msg è solo di alert)
			response.setMessaggio(messaggio);
		}
		
		
		List<DocumentoProtocollatoVO> ricercaProtocolloSuACTA = doquiServiceFacade.ricercaProtocolloSuACTA(numProtocollo);
		utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.RICERCA_PROTOCOLLO.getOperation(), "cnm_t_verbale", "id_verbale="+idVerbale, Thread.currentThread().getStackTrace()[1].getMethodName(), null);
		
		if(ricercaProtocolloSuACTA==null) {
			
			// JIRA CONAM-105
			// 20201119_LC se esito ricerca NULL: torna msg SRCHPROT03

			throw new BusinessException(ErrorCode.PROTOCOLLO_NON_PRESENTE_IN_ACTA);
//			CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.PROTOCOLLO_NON_PRESENTE_IN_ACTA);
//			if(cnmDMessaggio!=null) {
//				messaggio = new MessageVO(cnmDMessaggio.getDescMessaggio(), cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
//			}else {
//				throw new SecurityException("Messaggio non trovato");
//			}
//			response.setMessaggio(messaggio);
			
		} else {
			// 20201002_ET aggiunto check sul flag flagRecuperatoPec come da CDU-17 aggiornato
			// 20210517_LC non torna l'errore se si tratta di un protocollo presente nella CnmTScrittoDifensivo (a perscindere dal flagAssociata)
			List<CnmTScrittoDifensivo> cnmTScrittoDifensivoList = cnmTScrittoDifensivoRepository.findByNumeroProtocollo(numProtocollo);
			if(!checkProtocolloRecuperatoDaPec(numProtocollo) && (cnmTScrittoDifensivoList == null || cnmTScrittoDifensivoList.isEmpty())) {
				throw new BusinessException(ErrorCode.PROTOCOLLO_NON_RECUPERATO_DA_PEC);
			}
			
			response.setDocumentoProtocollatoVOList(new ArrayList<DocumentoProtocollatoVO>());	// 20200903_LC
			
			//20201020_ET l'idVerbale puo essere null solo in caso di verbali pregressi, in quel caso visto che il verbale e' nuovo giaPresenteSuActa e giaSalvato saranno sempre a false
			boolean isNuovoVerbale = false;
			if(idVerbale==null && flagPregresso)
				isNuovoVerbale = true;
			
			// 20210429_LC i caso di scritto difensivo (senza verbale) idVerbale è null ma non siamo nel pregresso, quindi si controlla che non sia NULL
			
			// e' stato gia' incluso nel fascicolo su ACTA	
			boolean giaPresenteSuActa = false;
			if (idVerbale != null)
				giaPresenteSuActa = isNuovoVerbale?false:checkDocumentoGiaCopiato(numProtocollo, idVerbale, null);
			
			// 20200723_LC_P2 se l'id è già presente in CnmTAllegato allora torna il booleano true, altrimenti false
			for (DocumentoProtocollatoVO docDaActa:ricercaProtocolloSuACTA) {	
				docDaActa.setGiaPresenteSuActa(giaPresenteSuActa);
				docDaActa.setGiaSalvato(false);
				
				//20201020_ET faccio il controllo degli allegati gia salvati solo per verbali gia esistenti, per i nuovi non ha senso
				if(!isNuovoVerbale && idVerbale != null) {
					List<CnmTAllegato> listGiaSalvato =	cnmTAllegatoRepository.findByObjectidSpostamentoActa(docDaActa.getObjectIdDocumento());
	
//					va controllato se l'allegato e' associato al verbale interessato
					giaSalvatoLoop:
					for(CnmTAllegato allegato:listGiaSalvato) {
						CnmRAllegatoVerbale findByCnmTAllegato = cnmRAllegatoVerbaleRepository.findByCnmTAllegato(allegato);
						if(findByCnmTAllegato!=null) {
							if(idVerbale.equals(findByCnmTAllegato.getCnmTVerbale().getIdVerbale())) {
								docDaActa.setGiaSalvato(true);	
								break giaSalvatoLoop;
							}
						}else {
							//se non sta sulla rAllegatoVerbale devo cercare su rAllegatoVErbaleSoggetto e rAllegatoOrdinanza
							List<CnmRAllegatoOrdinanza> listaAllegatoOrdinanza = cnmRAllegatoOrdinanzaRepository.findByCnmTAllegato(allegato);
							if(listaAllegatoOrdinanza!=null && !listaAllegatoOrdinanza.isEmpty()) {
								for(CnmRAllegatoOrdinanza rAllegatoOrd: listaAllegatoOrdinanza) {
									List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(rAllegatoOrd.getCnmTOrdinanza());
									List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmROrdinanzaVerbSogsIn(cnmROrdinanzaVerbSogList);
									if(idVerbale.equals(cnmRVerbaleSoggettoList.get(0).getCnmTVerbale().getIdVerbale())) {
										docDaActa.setGiaSalvato(true);	
										break giaSalvatoLoop;
									}
								}
							}else {
								List<CnmRAllegatoOrdVerbSog> cnmROrdinanzaVerbSogList = cnmRAllegatoOrdVerbSogRepository.findByCnmTAllegato(allegato);
								if(cnmROrdinanzaVerbSogList!=null && !cnmROrdinanzaVerbSogList.isEmpty()) {
									for(CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog: cnmROrdinanzaVerbSogList) {
										CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmRAllegatoOrdVerbSog.getCnmROrdinanzaVerbSog();
										List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmROrdinanzaVerbSogsIn(new ArrayList<>(Arrays.asList(cnmROrdinanzaVerbSog)));
										//prendo il primo elemento, tanto anche se ce ne sono diversi, dovrebbero essere legati tutti allo stesso verbale
										if(idVerbale.equals(cnmRVerbaleSoggettoList.get(0).getCnmTVerbale().getIdVerbale())) {
											docDaActa.setGiaSalvato(true);
											break giaSalvatoLoop;
										}
									}
								}
							}
						}
					}
				}
				// 20200903_LC recupero dataProtocollo per check pregresso di dopo
				if (!StringUtils.isBlank(docDaActa.getRegistrazioneId())) {
					try {
						dataProtocolloRicercato = new SimpleDateFormat("dd/MM/yyyy").parse(docDaActa.getDataOraProtocollo());
					} catch (ParseException e) {
						logger.error("Non riesco a ricavare la data del protocollo.");
					}
				}
				
//					20200726_ET aggiunta lista allegati che possono essere inclusi piu volte
				docDaActa.setTipiAllegatoDuplicabili(Arrays.asList(TipoAllegato.SCRITTI_DIFENSIVI.getId(), 
																	TipoAllegato.CONTRODEDUZIONE.getId(),
																	TipoAllegato.COMPARSA.getId(),
																	TipoAllegato.RELATA_NOTIFICA.getId()));
			}
				
		}
		
		// setta lista nella response
		response.setDocumentoProtocollatoVOList(ricercaProtocolloSuACTA);
		
		
		
		
		
		
		// 20210426_LC controllo ambito per eventuale scritto difensivo non ancora associato (e solo epr caricati da file system)
		if (cnmTScrittoDifensivoDaAssociareList != null && !cnmTScrittoDifensivoDaAssociareList.isEmpty() && idVerbale != null) {
			// 20210517_LC solo se non proviene già da Acta

			boolean sdAmbitiNonCoincidenti = true;
			// 20210526_LC per ognuno degli SD controlla l'ambito, se almeno uno e' uguale torna msg, altrimenti cancella response
			for (CnmTScrittoDifensivo scritto : cnmTScrittoDifensivoDaAssociareList) {
				
				if (scritto.getCnmDModalitaCaricamento().getIdModalitaCaricamento() != Constants.ID_MODALITA_CARICAMENTO_DA_ACTA) {

					CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(idVerbale);	
					List<CnmRVerbaleIllecito> cnmRVerbaleIllecitos = cnmRVerbaleIllecitoRepository.findByCnmTVerbale(cnmTVerbale);
					CnmDAmbito cnmDAmbitoVerbale = cnmRVerbaleIllecitos.get(0).getCnmDLettera().getCnmDComma().getCnmDArticolo().getCnmREnteNorma().getCnmDNorma().getCnmDAmbito();	
					if (scritto.getCnmDAmbito().getIdAmbito().equals(cnmDAmbitoVerbale.getIdAmbito())) {
						
						// ambito stesso del verbale, ricerca OK e mostra il msg
						// tale msg sovrascrive quello di "protocollo già presente in conam" in caso di pregresso
						CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.SCRITTO_DIFENSIVO_DA_ASSOCIARE);
						if(cnmDMessaggio!=null) {
							sdAmbitiNonCoincidenti = false;
							messaggio = new MessageVO(cnmDMessaggio.getDescMessaggio(), cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
						}else {
							throw new SecurityException("Messaggio non trovato");
						}
						response.setMessaggio(messaggio);
					} 
					
				}								
				
			}
			
			// se nessuno SD ha stesso ambito del verbale (tutti gli ambiti sono diversi), elimina risultati da output ricerca
			// get(0) in quanto se caricato da dispositivo puo essere al massimo uno
			if (sdAmbitiNonCoincidenti && cnmTScrittoDifensivoDaAssociareList.get(0).getCnmDModalitaCaricamento().getIdModalitaCaricamento() != Constants.ID_MODALITA_CARICAMENTO_DA_ACTA)
				response.setDocumentoProtocollatoVOList(new ArrayList<DocumentoProtocollatoVO>());

		}
		
		

		
		
		
		
		// 20200903_LC gestione pregresso
		// se dataProtocollo è > dataDiscriminante, torna null ed il messaggio SRCHPROT01
		Date dataDiscriminantePregresso = cnmCParametroRepository.findOne(Constants.ID_DATA_DISCRIMINANTE_GESTIONE_PREGRESSO).getValoreDate();	
		
		if (flagPregresso && dataProtocolloRicercato != null && dataProtocolloRicercato.after(dataDiscriminantePregresso)) 	{		
			// tornare msg configurato SRCHPROT01
			CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.PROTOCOLLO_RECUPERATO_NON_PREGRESSO);
			if(cnmDMessaggio!=null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				String msg = String.format(cnmDMessaggio.getDescMessaggio(), sdf.format(dataDiscriminantePregresso), sdf.format(dataProtocolloRicercato));
				messaggio = new MessageVO(msg, cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
			}else {
				throw new SecurityException("Messaggio non trovato");
			}
			// costruisce nuova response
			response.setDocumentoProtocollatoVOList(new ArrayList<DocumentoProtocollatoVO>());
			response.setMessaggio(messaggio);
			
//CRP			if (!cacheRicercaProtocollo.containsKey(numProtocollo))
//CRP				cacheRicercaProtocollo.put(numProtocollo, response);
			
			return response;
		}	
			
//CRP		if (!cacheRicercaProtocollo.containsKey(numProtocollo))
//CRP			cacheRicercaProtocollo.put(numProtocollo, response);
		
		return response;
	}

	
	//20201002_ET 
		private boolean checkProtocolloRecuperatoDaPec(String protocollo) {
			boolean recuperatoDaPec = true;
			List<CnmTAllegato> listGiaSalvato = cnmTAllegatoRepository.findByNumeroProtocollo(protocollo);
			//non serve ciclo for, basta che prendo il 1 elemento della lista, perche per lo stesso protocollo tutti gli allegati devono avere la stessa valorizzazione del flag
			if(listGiaSalvato!=null && !listGiaSalvato.isEmpty()) {
				recuperatoDaPec=listGiaSalvato.get(0).isFlagRecuperatoPec();
			}
//			for(CnmTAllegato allegato:listGiaSalvato) {
//				if(!allegato.isFlagRecuperatoPec()) {
//					nonRecuperatoDaPec = true;
//					break;
//				}
//			}
			return recuperatoDaPec;
		}
		


		
		
	// 20200711_LC
	public List<DocumentoScaricatoVO> downloadAllegatoByObjectIdDocumento(String objectIdDocumento) {
		if (objectIdDocumento == null)
			throw new IllegalArgumentException("objectIdDocumento non valorizzato");

		
		ResponseRicercaDocumentoMultiplo response = doquiServiceFacade.recuperaDocumentoActaByObjectIdDocumento(objectIdDocumento);
			
   
		//if (response != null && response.getDocumento() != null && response.getDocumento().getFile() != null) {
			if (response != null && response.getSottoDocumenti() != null) {										  
				// addProtocollo non va invocato (si usa per documenti non ancora in Conam)
				//if (Constants.DOCUMENTI_CREATI_DA_CONAM.contains(cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato()))
					//return addProtocolloToDocument(response.getDocumento().getFile(), cnmTAllegato.getNumeroProtocollo(), cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato());
				//else
				
				// 20200622_LC TEST TRACE
				utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.VISUALIZZA_ANTEPRIMA_DOCUMENTO.getOperation(),"ACTA","objectIdDocumento="+objectIdDocumento.substring(0, Math.min(objectIdDocumento.length(), 10))+"...", Thread.currentThread().getStackTrace()[1].getMethodName(), null);
		
				// 20200803_LC gestione documento multiplo
				List<DocumentoScaricatoVO> docScaricatoVOList = new ArrayList<DocumentoScaricatoVO>();
				
				for (Documento doc:response.getSottoDocumenti()) {
					DocumentoScaricatoVO docScaricatoVO = new DocumentoScaricatoVO();
					docScaricatoVO.setFile(doc.getFile());
					docScaricatoVO.setNomeFile(doc.getNomeFile());
					docScaricatoVO.setObjectIdDocumentoFisico(doc.getObjectIdDocumentoFisico());
					docScaricatoVOList.add(docScaricatoVO);
				}
					return docScaricatoVOList;
					
			}
			else
			{
				throw new RuntimeException("documento non trovato su acta");
			}

	}
	

	
	
	
	
	
	// 20200825_LC
	public List<DocumentoScaricatoVO> downloadAllegatoByObjectIdDocumentoFisico(String objectIdDocumentoFisico) {
		if (objectIdDocumentoFisico == null)
			throw new IllegalArgumentException("objectIdDocumentoFisico non valorizzato");

		
		ResponseRicercaDocumentoMultiplo response = doquiServiceFacade.recuperaDocumentoActaByObjectIdDocumentoFisico(objectIdDocumentoFisico);
			
   
		//if (response != null && response.getDocumento() != null && response.getDocumento().getFile() != null) {
			if (response != null && response.getSottoDocumenti() != null) {										  
				// addProtocollo non va invocato (si usa per documenti non ancora in Conam)
				//if (Constants.DOCUMENTI_CREATI_DA_CONAM.contains(cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato()))
					//return addProtocolloToDocument(response.getDocumento().getFile(), cnmTAllegato.getNumeroProtocollo(), cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato());
				//else
				
				// 20200622_LC TEST TRACE
				utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.VISUALIZZA_ANTEPRIMA_DOCUMENTO.getOperation(),"ACTA","objectIdDocumentoFisico="+objectIdDocumentoFisico.substring(0, Math.min(objectIdDocumentoFisico.length(), 10))+"...", Thread.currentThread().getStackTrace()[1].getMethodName(), null);
		
				// 20200803_LC gestione documento multiplo
				List<DocumentoScaricatoVO> docScaricatoVOList = new ArrayList<DocumentoScaricatoVO>();
				
				for (Documento doc:response.getSottoDocumenti()) {
					DocumentoScaricatoVO docScaricatoVO = new DocumentoScaricatoVO();
					docScaricatoVO.setFile(doc.getFile());
					docScaricatoVO.setNomeFile(doc.getNomeFile());
					docScaricatoVO.setObjectIdDocumentoFisico(doc.getObjectIdDocumentoFisico());
					docScaricatoVOList.add(docScaricatoVO);
				}
					return docScaricatoVOList;
					
			}
			else
			{
				throw new RuntimeException("documento non trovato su acta");
			}

	}
	
	
	
	
	
	
	// --
	
	
	
	
	
	@Override
	@Transactional
	public List<CnmTAllegato> salvaAllegatoProtocollatoVerbale(SalvaAllegatiProtocollatiRequest request, CnmTUser cnmTUser, CnmTVerbale cnmTVerbale, boolean pregresso) {
		
		List<CnmTAllegato> response = new ArrayList<CnmTAllegato>();

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		
		// 20200706_LC
		// i controlli di sicurezza li fa nel service
		
		// 20200711_LC c'è un solo documento da processare
		
		//per ciascun documento inserisco in cnm_t_allegato e cnm_r_allegato_verbale (nel service)

		DocumentoProtocollatoVO doc = request.getDocumentoProtocollato();	
//			--------
		// categoria allegato	
		CnmDTipoAllegato cnmDTipoAllegato = null;
		List<SalvaAllegatoRequest> allegati = request.getAllegati();
//		List<TipoAllegatoVO> tipiAllegato = request.getTipiAllegato();
		String protocollo = StringUtils.isBlank(doc.getRegistrazioneId())?doc.getNumProtocolloMaster():doc.getNumProtocollo();
		//20200918_ET controllo se il fascicolo del verbale contiene gia' degli allegati relativi a quel protocollo
		List<CnmTAllegato> allegatiGiaSalvati = getAllegatiGiaCopiati(protocollo, cnmTVerbale.getIdVerbale());
		boolean fascicoloProtocollatoGiaSpostato=false;
		if(allegatiGiaSalvati!=null && !allegatiGiaSalvati.isEmpty()) fascicoloProtocollatoGiaSpostato = true;
		String idActaMaster=null;
		if(fascicoloProtocollatoGiaSpostato) {
			idActaMaster = getIdActaMasterDaAllegatiSalvati(allegatiGiaSalvati);
		}
		
		// 20211221 PP - controllo la validità delle relate, poichè se ci sono + selate non possono avere lo stesso soggetto
		if(!allegatiRelataValid(allegati)) {
//			CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.STESSO_SOGGETTO_PIU_RELATE);
			throw new BusinessException(ErrorCode.STESSO_SOGGETTO_PIU_RELATE);
		}
		
		for (SalvaAllegatoRequest allegato : allegati) {
			cnmDTipoAllegato = cnmDTipoAllegatoRepository.findOne(allegato.getIdTipoAllegato());			
			if (cnmDTipoAllegato == null)
				throw new SecurityException("id tipo allegato non trovato");
			
			// va fatta una insert anche sulla cnmTdocumento (la fa nel manage dello spostamento, finchè non si spostano non ci sono documenti)
			CnmTAllegato cnmTAllegato = new CnmTAllegato();
			cnmTAllegato.setCnmDTipoAllegato(cnmDTipoAllegato);

			// 20200825_LC gestione nome_file per doc multiplo 
			if (doc.getFilenamesDocMultiplo() == null || doc.getFilenamesDocMultiplo().isEmpty() ) {
				cnmTAllegato.setNomeFile(doc.getFilename());
			} else  {
				String nomeFileComposto = "Documento multiplo - ";
				for (String singleFilename : doc.getFilenamesDocMultiplo()) {
					nomeFileComposto = nomeFileComposto + singleFilename + ", ";
				}
				nomeFileComposto = nomeFileComposto.substring(0, nomeFileComposto.length() - 2);
				if (nomeFileComposto.length()>100) {
					nomeFileComposto=nomeFileComposto.substring(0,99);
				}
				cnmTAllegato.setNomeFile(nomeFileComposto);				
			}
			cnmTAllegato.setIdIndex(null);
			
			// 20200709_LC  valorizza numero protocollo solo per il "master"
			//20200723_ET valorizziamo sempre il numero protocollo, anche per gli allegati legati al master, altrimenti nel riepilogo vedremmo il protocollo del rapporto di trasmissione
			cnmTAllegato.setNumeroProtocollo(protocollo);			
			if (StringUtils.isNotBlank(doc.getRegistrazioneId()) && StringUtils.isNotBlank(doc.getDataOraProtocollo())) {  				
				Date date = utilsDate.getDate(doc.getDataOraProtocollo(), DateFormat.DATE_FORMAT_DDMMYY);
				cnmTAllegato.setDataOraProtocollo(new Timestamp(date.getTime()));
			} 						
							
			cnmTAllegato.setCnmTUser2(cnmTUser);			// 20200706_LC
			cnmTAllegato.setDataOraInsert(now);			
			
			//20200916_ET se il fascicolo contiene gia' i documenti relativi a quel protocollo inserire record in TAllegato e TDocumento utilizzando i dati in entrata, perche il doc e' gia' presente su ACTA
			if(fascicoloProtocollatoGiaSpostato) {
//				devo solo fare una insert sulla cnmTDocumento prima di salvare l'allegato per avere l'id acta corretto 
				ResponseSpostaDocumento resp = doquiServiceFacade.salvaAllegatoGiaPresenteNelFascicoloActa(doc, cnmDTipoAllegato, cnmTVerbale, cnmTUser, allegatiGiaSalvati);
				if(StringUtils.isBlank(doc.getRegistrazioneId())) { // solo per gli allegati devo salvare anche idActaMaster
					cnmTAllegato.setIdActaMaster(idActaMaster);	
				}
				cnmTAllegato.setIdActa(resp.getIdDocumento());			
				cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));	// 20200706_LC sono allegati già spostati nel fascicolo
				cnmTAllegato.setObjectidSpostamentoActa(null);
			} else {
				cnmTAllegato.setIdActa(doc.getIdActa());	// sarà null qui, aggiorna dopo lo spostamento			
				cnmTAllegato.setIdActaMaster(null);			// sarà null qui, aggiorna dopo lo spostamento					
				cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO_IN_ALTRA_STRUTTURA));	// 20200706_LC sono allegati già protocolalti ma in altra struttura
				cnmTAllegato.setObjectidSpostamentoActa(doc.getObjectIdDocumento());	// 20200708_LC si salva l'id in modo da avere una corrispondenza coi nuovi documenti dopo lo spostamento	
			}
			// 20201001_ET
			cnmTAllegato.setFlagRecuperatoPec(true);
			
			// 20201021 PP - imposto flagPregresso
			cnmTAllegato.setFlagDocumentoPregresso(pregresso);
			
			cnmTAllegatoRepository.save(cnmTAllegato);
			
			// 20200722_LC trace allegati creati (anche se non ancroa spostati nella struttura acta di Conam)
			if(pregresso)
				utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmDTipoAllegato.getDescTipoAllegato());
			else
				utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmDTipoAllegato.getDescTipoAllegato());
				
			
			List<CnmTAllegatoField> cnmTAllegatoFieldList = new ArrayList<>();
			List<AllegatoFieldVO> configAllegato = allegato.getAllegatoField();
			Integer idVerbaleSoggetto = null;
			if(configAllegato != null) {

				for (AllegatoFieldVO c : configAllegato) {
					CnmTAllegatoField cnmTAllegatoField = new CnmTAllegatoField();
					cnmTAllegatoField.setCnmTAllegato(cnmTAllegato);
					cnmTAllegatoField.setCnmTUser2(cnmTUser);
					cnmTAllegatoField.setDataOraInsert(now);
					Long idField = c.getIdField();
					if (idField == null)
						throw new IllegalArgumentException("Errore field type non valorizzato");

					CnmCField cnmCField = cnmCFieldRepository.findOne(idField);
					cnmTAllegatoField.setCnmCField(cnmCField);
					Long idFieldType = cnmCField.getCnmCFieldType().getIdFieldType();
					if (idFieldType == Constants.FIELD_TYPE_BOOLEAN) {
						cnmTAllegatoField.setValoreBoolean(c.getBooleanValue());
					}
					if (idFieldType == Constants.FIELD_TYPE_NUMERIC || idFieldType == Constants.FIELD_TYPE_ELENCO) {
						cnmTAllegatoField.setValoreNumber(c.getNumberValue());
					}
					if (idFieldType == Constants.FIELD_TYPE_STRING) {
						cnmTAllegatoField.setValoreString(c.getStringValue());
					}
					if (idFieldType == Constants.FIELD_TYPE_DATA_ORA) {
						cnmTAllegatoField.setValoreDataOra(utilsDate.asTimeStamp(c.getDateTimeValue()));
					}
					if (idFieldType == Constants.FIELD_TYPE_DATA) {
						cnmTAllegatoField.setValoreData(utilsDate.asDate(c.getDateValue()));
					}					
					if (idFieldType == Constants.FIELD_TYPE_ELENCO_SOGGETTI) {
						if(c.getNumberValue()!= null) {
							idVerbaleSoggetto = c.getNumberValue().intValue();
							cnmTAllegatoField.setValoreNumber(c.getNumberValue());
						}
					}
					
					cnmTAllegatoFieldList.add(cnmTAllegatoField);
				}
				
				
				// 20200717_LC
				utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_METADATI_ALLEGATO.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmDTipoAllegato.getDescTipoAllegato());
							
				
			}
			
			if (!cnmTAllegatoFieldList.isEmpty())
				cnmTAllegatoFieldRepository.save(cnmTAllegatoFieldList);
			if(cnmDTipoAllegato.getIdTipoAllegato() == TipoAllegato.RELATA_NOTIFICA.getId()) {
				if(idVerbaleSoggetto != null) {
					CnmRAllegatoVerbSog cnmRAllegatoVerbSog = new CnmRAllegatoVerbSog();
					CnmRAllegatoVerbSogPK cnmRAllegatoVerbSogPK = new CnmRAllegatoVerbSogPK();
					cnmRAllegatoVerbSogPK.setIdAllegato(cnmTAllegato.getIdAllegato());
					cnmRAllegatoVerbSogPK.setIdVerbaleSoggetto(idVerbaleSoggetto);
					cnmRAllegatoVerbSog.setCnmTUser(cnmTUser);
					cnmRAllegatoVerbSog.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
					cnmRAllegatoVerbSog.setId(cnmRAllegatoVerbSogPK);
					cnmRAllegatoVerbSogRepository.save(cnmRAllegatoVerbSog);
				}
			}else {
				CnmRAllegatoVerbale cnmRAllegatoVerbale = new CnmRAllegatoVerbale();
				CnmRAllegatoVerbalePK cnmRAllegatoVerbalePK = new CnmRAllegatoVerbalePK();
				cnmRAllegatoVerbalePK.setIdAllegato(cnmTAllegato.getIdAllegato());
				cnmRAllegatoVerbalePK.setIdVerbale(request.getIdVerbale());
				cnmRAllegatoVerbale.setCnmTUser(cnmTUser);
				cnmRAllegatoVerbale.setDataOraInsert(now);
				cnmRAllegatoVerbale.setId(cnmRAllegatoVerbalePK);
				cnmRAllegatoVerbaleRepository.save(cnmRAllegatoVerbale);
			}
			
			// 20200706_LC
			response.add(cnmTAllegato);
		}
		
		
		// 20210426_LC (associazione in caso di scritto difensivo)
		// 20210526_LC solo per lo (gli) SD che ha lo stesso objectId e ambito corretto
		//CnmTScrittoDifensivo cnmTScrittoDifensivo = cnmTScrittoDifensivoRepository.findByNumeroProtocolloAndFlagAssociatoIsFalse(protocollo); 
		List<CnmTScrittoDifensivo> scrittiDaAssociare = cnmTScrittoDifensivoRepository.findByNumeroProtocolloAndObjectidActaAndFlagAssociatoIsFalse(protocollo, doc.getObjectIdDocumento()); 
		
		if (scrittiDaAssociare != null && !scrittiDaAssociare.isEmpty()) {	

			List<CnmRVerbaleIllecito> cnmRVerbaleIllecitos = cnmRVerbaleIllecitoRepository.findByCnmTVerbale(cnmTVerbale);
			CnmDAmbito cnmDAmbitoVerbale = cnmRVerbaleIllecitos.get(0).getCnmDLettera().getCnmDComma().getCnmDArticolo().getCnmREnteNorma().getCnmDNorma().getCnmDAmbito();	
						
			for (CnmTScrittoDifensivo scritto : scrittiDaAssociare) {
				if (scritto.getCnmDAmbito().getIdAmbito().equals(cnmDAmbitoVerbale.getIdAmbito()))
					scrittoDifensivoService.associaScrittoDifensivo(scritto, cnmTVerbale, cnmTUser);
			}			
			
		}

		
		
		// 20200720_LC se si sta processando un allegato al master non deve spostare
		// 20201106_ET per i PREGRESSI sposto anche se sto processando un allegato
		if ((!StringUtils.isBlank(doc.getRegistrazioneId()) && !fascicoloProtocollatoGiaSpostato)
				|| (pregresso && !fascicoloProtocollatoGiaSpostato)) {
			
			// avvia lo spostamento - SOLO SE IL FASCICOLO E' ACQUISITO
			if (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()==Constants.STATO_VERBALE_ACQUISITO ||
					cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()==Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO ||
					cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()==Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI ||
					cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()==Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA ||
					cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()==Constants.STATO_VERBALE_ORDINANZA ||
					cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()==Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO ||
					cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale()==Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO)
				avviaSpostamentoPerFascicoloGiaAcquisito(response, cnmTVerbale, cnmTUser);	
							
		}

		return response;	
		
	}
	
	private boolean allegatiRelataValid(List<SalvaAllegatoRequest> allegati) {
		List<Long> soggettiRelate = new ArrayList<Long>();
		for (SalvaAllegatoRequest allegato : allegati) {
			if(allegato.getIdTipoAllegato() == TipoAllegato.RELATA_NOTIFICA.getId()) {

				Long idSoggetto = getIdSoggettoRelata(allegato);
				if(soggettiRelate.contains(idSoggetto)) {
					return false;
				}else {
					soggettiRelate.add(idSoggetto);
				}
			}
			
		}
		return true;
	}


	private Long getIdSoggettoRelata(SalvaAllegatoRequest allegato) {
		
		List<AllegatoFieldVO> configAllegato = allegato.getAllegatoField();
		if(configAllegato != null) {

			for (AllegatoFieldVO c : configAllegato) {
				Long idField = c.getIdField();
				if (idField == null)
					return null;

				CnmCField cnmCField = cnmCFieldRepository.findOne(idField);
				Long idFieldType = cnmCField.getCnmCFieldType().getIdFieldType();
				if (idFieldType == Constants.FIELD_TYPE_ELENCO_SOGGETTI) {
					return c.getNumberValue().longValue();
				}			
			}
		}
		return null;
	}


	@Override
	@Transactional
	public CnmTAllegato salvaAllegatoProtocollatOrdinanzaSoggetto(SalvaAllegatiProtocollatiRequest request, CnmTUser cnmTUser, List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList, CnmTVerbale cnmTVerbale, boolean pregresso) {
		
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		
		DocumentoProtocollatoVO doc = request.getDocumentoProtocollato();	
//			--------
		// categoria allegato	
		CnmDTipoAllegato cnmDTipoAllegato = null;
		if(request.getAllegati()==null || request.getAllegati().isEmpty()) {
			throw new SecurityException("manca la tipologia allegato");
		}
		//in questo caso mi aspetto che ci sia un solo allegato
		SalvaAllegatoRequest allegato = request.getAllegati().get(0);
		cnmDTipoAllegato = cnmDTipoAllegatoRepository.findOne(allegato.getIdTipoAllegato());			
		if (cnmDTipoAllegato == null)
			throw new SecurityException("id tipo allegato non trovato");

		CnmTAllegato cnmTAllegato = new CnmTAllegato();
		cnmTAllegato.setCnmDTipoAllegato(cnmDTipoAllegato);
		
		
		// 20200826_LC gestione nome_file per doc multiplo 
		if (doc.getFilenamesDocMultiplo() == null || doc.getFilenamesDocMultiplo().isEmpty()) {
			cnmTAllegato.setNomeFile(doc.getFilename());
		} else  {
			String nomeFileComposto = "Documento multiplo - ";
			for (String singleFilename : doc.getFilenamesDocMultiplo()) {
				nomeFileComposto = nomeFileComposto + singleFilename + ", ";
			}
			nomeFileComposto = nomeFileComposto.substring(0, nomeFileComposto.length() - 2);
			if (nomeFileComposto.length()>100) {
				nomeFileComposto=nomeFileComposto.substring(0,99);
			}
			cnmTAllegato.setNomeFile(nomeFileComposto);				
		}
		//20200918_ET controllo se il fascicolo del verbale contiene gia' degli allegati relativi a quel protocollo
		String protocollo = StringUtils.isBlank(doc.getRegistrazioneId())?doc.getNumProtocolloMaster():doc.getNumProtocollo();
		List<CnmTAllegato> allegatiGiaSalvati = getAllegatiGiaCopiati(protocollo, cnmTVerbale.getIdVerbale());
		boolean fascicoloProtocollatoGiaSpostato=false;
		if(allegatiGiaSalvati!=null && !allegatiGiaSalvati.isEmpty()) fascicoloProtocollatoGiaSpostato = true;
		
		//20200916_ET se il fascicolo contiene gia' i documenti relativi a quel protocollo inserire record in TAllegato e TDocumento utilizzando i dati in entrata, perche il doc e' gia' presente su ACTA
		if(fascicoloProtocollatoGiaSpostato) {
//			devo solo fare una insert sulla cnmTDocumento prima di salvare l'allegato per avere l'id acta corretto 
			ResponseSpostaDocumento resp = doquiServiceFacade.salvaAllegatoGiaPresenteNelFascicoloActa(doc, cnmDTipoAllegato, cnmTVerbale, cnmTUser, allegatiGiaSalvati);
			String idActaMaster=null;
			if(StringUtils.isBlank(doc.getRegistrazioneId())) { // solo per gli allegati devo salvare anche idActaMaster
				idActaMaster = getIdActaMasterDaAllegatiSalvati(allegatiGiaSalvati);
			}
			cnmTAllegato.setIdActa(resp.getIdDocumento());			
			cnmTAllegato.setIdActaMaster(idActaMaster);							
			cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));	// 20200706_LC sono allegati già spostati nel fascicolo
			cnmTAllegato.setObjectidSpostamentoActa(null);
		} else {
			cnmTAllegato.setIdActa(doc.getIdActa());	// sarà null qui, aggiorna dopo lo spostamento			
			cnmTAllegato.setIdActaMaster(null);			// sarà null qui, aggiorna dopo lo spostamento					
			cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO_IN_ALTRA_STRUTTURA));	// 20200706_LC sono allegati già protocolalti ma in altra struttura
			cnmTAllegato.setObjectidSpostamentoActa(doc.getObjectIdDocumento());	// 20200708_LC si salva l'id in modo da avere una corrispondenza coi nuovi documenti dopo lo spostamento	
		}
		
		// 20200709_LC  valorizza numero protocollo solo per il "master"
		//20200723_ET valorizziamo sempre il numero protocollo, anche per gli allegati legati al master, altrimenti nel riepilogo vedremmo il protocollo del rapporto di trasmissione
		cnmTAllegato.setNumeroProtocollo(protocollo);			
		if (StringUtils.isNotBlank(doc.getRegistrazioneId()) && StringUtils.isNotBlank(doc.getDataOraProtocollo())) {  				
			Date date = utilsDate.getDate(doc.getDataOraProtocollo(), DateFormat.DATE_FORMAT_DDMMYY);
			cnmTAllegato.setDataOraProtocollo(new Timestamp(date.getTime()));
		} 
		cnmTAllegato.setIdIndex(null);				
		cnmTAllegato.setCnmTUser2(cnmTUser);			// 20200706_LC
		cnmTAllegato.setDataOraInsert(now);		
		// 20201001_ET
		cnmTAllegato.setFlagRecuperatoPec(true);
		cnmTAllegato.setFlagDocumentoPregresso(pregresso);
		cnmTAllegatoRepository.save(cnmTAllegato);
		
		
		
		// 20200722_LC trace allegati creati (anche se non ancroa spostati nella struttura acta di Conam)
		if(pregresso)
			utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmDTipoAllegato.getDescTipoAllegato());
		else
			utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmDTipoAllegato.getDescTipoAllegato());

	
		
		
		
		// per la DISPOSIZIONE_DEL_GIUDICE serve gestire anche insert su CnmTAllegatoField
		List<CnmTAllegatoField> cnmTAllegatoFieldList = new ArrayList<>();
		List<AllegatoFieldVO> configAllegato = allegato.getAllegatoField();
		if(configAllegato != null) {
			for (AllegatoFieldVO c : configAllegato) {
				CnmTAllegatoField cnmTAllegatoField = new CnmTAllegatoField();
				cnmTAllegatoField.setCnmTAllegato(cnmTAllegato);
				cnmTAllegatoField.setCnmTUser2(cnmTUser);
				cnmTAllegatoField.setDataOraInsert(now);
				Long idField = c.getIdField();
				if (idField == null)
					throw new IllegalArgumentException("Errore field type non valorizzato");

				CnmCField cnmCField = cnmCFieldRepository.findOne(idField);
				cnmTAllegatoField.setCnmCField(cnmCField);
				Long idFieldType = cnmCField.getCnmCFieldType().getIdFieldType();
				if (idFieldType == Constants.FIELD_TYPE_BOOLEAN) {
					cnmTAllegatoField.setValoreBoolean(c.getBooleanValue());
				}
				if (idFieldType == Constants.FIELD_TYPE_NUMERIC || idFieldType == Constants.FIELD_TYPE_ELENCO) {
					cnmTAllegatoField.setValoreNumber(c.getNumberValue());
				}
				if (idFieldType == Constants.FIELD_TYPE_STRING) {
					cnmTAllegatoField.setValoreString(c.getStringValue());
				}
				if (idFieldType == Constants.FIELD_TYPE_DATA_ORA) {
					cnmTAllegatoField.setValoreDataOra(utilsDate.asTimeStamp(c.getDateTimeValue()));
				}

				if (idFieldType == Constants.FIELD_TYPE_DATA) {
					cnmTAllegatoField.setValoreData(utilsDate.asDate(c.getDateValue()));
				}			
				if (idFieldType == Constants.FIELD_TYPE_ELENCO_SOGGETTI) {
					if(c.getNumberValue()!= null) {
						cnmTAllegatoField.setValoreNumber(c.getNumberValue());
					}
				}
				cnmTAllegatoFieldList.add(cnmTAllegatoField);
			}
			
			// 20200717_LC
			utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_METADATI_ALLEGATO.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmDTipoAllegato.getDescTipoAllegato());
	
		}
		
		if (!cnmTAllegatoFieldList.isEmpty())
			cnmTAllegatoFieldRepository.save(cnmTAllegatoFieldList);
		
		List<CnmTAllegato> cnmTAllegatoList = new ArrayList<>(); 
		cnmTAllegatoList.add(cnmTAllegato);
		
		// 20200720_LC se si sta processando un allegato al master non deve spostare
		// 20201106_ET per i PREGRESSI sposto anche se sto processando un allegato
		if ((!StringUtils.isBlank(doc.getRegistrazioneId()) && !fascicoloProtocollatoGiaSpostato)
				|| (pregresso && !fascicoloProtocollatoGiaSpostato)) {
			
			// essendo allegati ad ordinanza il fascicolo è già acquisito
			avviaSpostamentoPerFascicoloGiaAcquisito(cnmTAllegatoList, cnmTVerbale, cnmTUser);
			
		}
		
		
		
		
		return cnmTAllegato;
		
	}
	
	// solo per il pregresso
	@Override
	@Transactional
	public CnmTAllegato salvaAllegatoProtocollatoVerbaleSoggetto(SalvaAllegatiProtocollatiRequest request, CnmTUser cnmTUser, List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList) {
		
		CnmTVerbale cnmTVerbale = cnmRVerbaleSoggettoList.get(0).getCnmTVerbale();
		if(cnmTVerbale==null) {
			throw new SecurityException("cnmTVerbale non valorizzato");
		}
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		
		DocumentoProtocollatoVO doc = request.getDocumentoProtocollato();	
//			--------
		// categoria allegato	
		CnmDTipoAllegato cnmDTipoAllegato = null;
		if(request.getAllegati()==null || request.getAllegati().isEmpty()) {
			throw new SecurityException("manca la tipologia allegato");
		}
		//in questo caso mi aspetto che ci sia un solo allegato
		SalvaAllegatoRequest allegato = request.getAllegati().get(0);
		cnmDTipoAllegato = cnmDTipoAllegatoRepository.findOne(allegato.getIdTipoAllegato());			
		if (cnmDTipoAllegato == null)
			throw new SecurityException("id tipo allegato non trovato");

		CnmTAllegato cnmTAllegato = new CnmTAllegato();
		cnmTAllegato.setCnmDTipoAllegato(cnmDTipoAllegato);
		
		
		// 20200826_LC gestione nome_file per doc multiplo 
		if (doc.getFilenamesDocMultiplo() == null || doc.getFilenamesDocMultiplo().isEmpty()) {
			cnmTAllegato.setNomeFile(doc.getFilename());
		} else  {
			String nomeFileComposto = "Documento multiplo - ";
			for (String singleFilename : doc.getFilenamesDocMultiplo()) {
				nomeFileComposto = nomeFileComposto + singleFilename + ", ";
			}
			nomeFileComposto = nomeFileComposto.substring(0, nomeFileComposto.length() - 2);
			if (nomeFileComposto.length()>100) {
				nomeFileComposto=nomeFileComposto.substring(0,99);
			}
			cnmTAllegato.setNomeFile(nomeFileComposto);				
		}
		//20200918_ET controllo se il fascicolo del verbale contiene gia' degli allegati relativi a quel protocollo
		String protocollo = StringUtils.isBlank(doc.getRegistrazioneId())?doc.getNumProtocolloMaster():doc.getNumProtocollo();
		List<CnmTAllegato> allegatiGiaSalvati = getAllegatiGiaCopiati(protocollo, cnmTVerbale.getIdVerbale());
		boolean fascicoloProtocollatoGiaSpostato=false;
		if(allegatiGiaSalvati!=null && !allegatiGiaSalvati.isEmpty()) fascicoloProtocollatoGiaSpostato = true;
		
		//20200916_ET se il fascicolo contiene gia' i documenti relativi a quel protocollo inserire record in TAllegato e TDocumento utilizzando i dati in entrata, perche il doc e' gia' presente su ACTA
		if(fascicoloProtocollatoGiaSpostato) {
//			devo solo fare una insert sulla cnmTDocumento prima di salvare l'allegato per avere l'id acta corretto 
			ResponseSpostaDocumento resp = doquiServiceFacade.salvaAllegatoGiaPresenteNelFascicoloActa(doc, cnmDTipoAllegato, cnmTVerbale, cnmTUser, allegatiGiaSalvati);
			String idActaMaster=null;
			if(StringUtils.isBlank(doc.getRegistrazioneId())) { // solo per gli allegati devo salvare anche idActaMaster
				idActaMaster = getIdActaMasterDaAllegatiSalvati(allegatiGiaSalvati);
			}
			cnmTAllegato.setIdActa(resp.getIdDocumento());			
			cnmTAllegato.setIdActaMaster(idActaMaster);							
			cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));	// 20200706_LC sono allegati già spostati nel fascicolo
			cnmTAllegato.setObjectidSpostamentoActa(null);
		} else {
			cnmTAllegato.setIdActa(doc.getIdActa());	// sarà null qui, aggiorna dopo lo spostamento			
			cnmTAllegato.setIdActaMaster(null);			// sarà null qui, aggiorna dopo lo spostamento					
			cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO_IN_ALTRA_STRUTTURA));	// 20200706_LC sono allegati già protocolalti ma in altra struttura
			cnmTAllegato.setObjectidSpostamentoActa(doc.getObjectIdDocumento());	// 20200708_LC si salva l'id in modo da avere una corrispondenza coi nuovi documenti dopo lo spostamento	
		}
		
		// 20200709_LC  valorizza numero protocollo solo per il "master"
		//20200723_ET valorizziamo sempre il numero protocollo, anche per gli allegati legati al master, altrimenti nel riepilogo vedremmo il protocollo del rapporto di trasmissione
		cnmTAllegato.setNumeroProtocollo(protocollo);			
		if (StringUtils.isNotBlank(doc.getRegistrazioneId()) && StringUtils.isNotBlank(doc.getDataOraProtocollo())) {  				
			Date date = utilsDate.getDate(doc.getDataOraProtocollo(), DateFormat.DATE_FORMAT_DDMMYY);
			cnmTAllegato.setDataOraProtocollo(new Timestamp(date.getTime()));
		} 
		cnmTAllegato.setIdIndex(null);				
		cnmTAllegato.setCnmTUser2(cnmTUser);			// 20200706_LC
		cnmTAllegato.setDataOraInsert(now);		
		// 20201001_ET
		cnmTAllegato.setFlagRecuperatoPec(true);
		cnmTAllegato.setFlagDocumentoPregresso(true);
		cnmTAllegatoRepository.save(cnmTAllegato);
		
		utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmDTipoAllegato.getDescTipoAllegato());

		List<CnmTAllegato> cnmTAllegatoList = new ArrayList<>(); 
		cnmTAllegatoList.add(cnmTAllegato);

		if ((!StringUtils.isBlank(doc.getRegistrazioneId()) && !fascicoloProtocollatoGiaSpostato)) {
			
			// essendo allegati ad ordinanza il fascicolo è già acquisito
			avviaSpostamentoPerFascicoloGiaAcquisito(cnmTAllegatoList, cnmTVerbale, cnmTUser);
			
		}
		
		return cnmTAllegato;
		
	}
	
	
	
	
	@Override
	@Transactional
	public List<CnmTAllegato> salvaAllegatoProtocollatoOrdinanza(SalvaAllegatiProtocollatiRequest request, CnmTUser cnmTUser, CnmTOrdinanza cnmTOrdinanza, CnmTVerbale cnmTVerbale, boolean pregresso) {

		List<CnmTAllegato> response = new ArrayList<CnmTAllegato>();

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

		DocumentoProtocollatoVO doc = request.getDocumentoProtocollato();	
//			--------
		// categoria allegato	
		CnmDTipoAllegato cnmDTipoAllegato = null;
		List<SalvaAllegatoRequest> allegati = request.getAllegati();
//		List<TipoAllegatoVO> tipiAllegato = request.getTipiAllegato();
		
		//20200918_ET controllo se il fascicolo del verbale contiene gia' degli allegati relativi a quel protocollo
		String protocollo = StringUtils.isBlank(doc.getRegistrazioneId())?doc.getNumProtocolloMaster():doc.getNumProtocollo();
		List<CnmTAllegato> allegatiGiaSalvati = getAllegatiGiaCopiati(protocollo, cnmTVerbale.getIdVerbale());
		boolean fascicoloProtocollatoGiaSpostato=false;
		if(allegatiGiaSalvati!=null && !allegatiGiaSalvati.isEmpty()) fascicoloProtocollatoGiaSpostato = true;
		String idActaMaster=null;
		if(fascicoloProtocollatoGiaSpostato) {
			idActaMaster = getIdActaMasterDaAllegatiSalvati(allegatiGiaSalvati);
		}
		
		for (SalvaAllegatoRequest allegato : allegati) {
			cnmDTipoAllegato = cnmDTipoAllegatoRepository.findOne(allegato.getIdTipoAllegato());			
			if (cnmDTipoAllegato == null)
				throw new SecurityException("id tipo allegato non trovato");
			
			CnmTAllegato cnmTAllegato = new CnmTAllegato();
			cnmTAllegato.setCnmDTipoAllegato(cnmDTipoAllegato);

			
			
			// 20200826_LC gestione nome_file per doc multiplo 
			if (doc.getFilenamesDocMultiplo() == null || doc.getFilenamesDocMultiplo().isEmpty()) {
				cnmTAllegato.setNomeFile(doc.getFilename());
			} else  {
				String nomeFileComposto = "Documento multiplo - ";
				for (String singleFilename : doc.getFilenamesDocMultiplo()) {
					nomeFileComposto = nomeFileComposto + singleFilename + ", ";
				}
				nomeFileComposto = nomeFileComposto.substring(0, nomeFileComposto.length() - 2);
				if (nomeFileComposto.length()>100) {
					nomeFileComposto=nomeFileComposto.substring(0,99);
				}
				cnmTAllegato.setNomeFile(nomeFileComposto);				
			}
			
			
			
			//20200916_ET se il fascicolo contiene gia' i documenti relativi a quel protocollo inserire record in TAllegato e TDocumento utilizzando i dati in entrata, perche il doc e' gia' presente su ACTA
			if(fascicoloProtocollatoGiaSpostato) {
//				devo solo fare una insert sulla cnmTDocumento prima di salvare l'allegato per avere l'id acta corretto 
				ResponseSpostaDocumento resp = doquiServiceFacade.salvaAllegatoGiaPresenteNelFascicoloActa(doc, cnmDTipoAllegato, cnmTVerbale, cnmTUser, allegatiGiaSalvati);
				if(StringUtils.isBlank(doc.getRegistrazioneId())) { // solo per gli allegati devo salvare anche idActaMaster
					cnmTAllegato.setIdActaMaster(idActaMaster);
				}
				cnmTAllegato.setIdActa(resp.getIdDocumento());												
				cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));	// 20200706_LC sono allegati già spostati nel fascicolo
				cnmTAllegato.setObjectidSpostamentoActa(null);
			} else {
				cnmTAllegato.setIdActa(doc.getIdActa());	// sarà null qui, aggiorna dopo lo spostamento			
				cnmTAllegato.setIdActaMaster(null);			// sarà null qui, aggiorna dopo lo spostamento					
				cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO_IN_ALTRA_STRUTTURA));	// 20200706_LC sono allegati già protocolalti ma in altra struttura
				cnmTAllegato.setObjectidSpostamentoActa(doc.getObjectIdDocumento());	// 20200708_LC si salva l'id in modo da avere una corrispondenza coi nuovi documenti dopo lo spostamento	
			}
			
			// 20200709_LC  valorizza numero protocollo solo per il "master"
			//20200723_ET valorizziamo sempre il numero protocollo, anche per gli allegati legati al master, altrimenti nel riepilogo vedremmo il protocollo del rapporto di trasmissione
			cnmTAllegato.setNumeroProtocollo(protocollo);			
			if (StringUtils.isNotBlank(doc.getRegistrazioneId()) && StringUtils.isNotBlank(doc.getDataOraProtocollo())) {  				
				Date date = utilsDate.getDate(doc.getDataOraProtocollo(), DateFormat.DATE_FORMAT_DDMMYY);
				cnmTAllegato.setDataOraProtocollo(new Timestamp(date.getTime()));
			} 
			cnmTAllegato.setIdIndex(null);				
			
							
			cnmTAllegato.setCnmTUser2(cnmTUser);
			cnmTAllegato.setDataOraInsert(now);			
			cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO_IN_ALTRA_STRUTTURA));	// 20200706_LC sono allegati già protocolalti ma in altra struttura
//			cnmTAllegato.setObjectidSpostamentoActa(doc.getObjectIdDocumento());	// 20200708_LC si salva l'id in modo da avere una corrispondenza coi nuovi documenti dopo lo spostamento
			// 20201001_ET
			cnmTAllegato.setFlagRecuperatoPec(true);
			cnmTAllegato.setFlagDocumentoPregresso(pregresso);
			cnmTAllegatoRepository.save(cnmTAllegato);
	
			
			
			// 20200722_LC trace allegati creati (anche se non ancroa spostati nella struttura acta di Conam)
			if(pregresso)
				utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmDTipoAllegato.getDescTipoAllegato());
			else
				utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName(), cnmDTipoAllegato.getDescTipoAllegato());
	
			
			
			
			// anche nella SalvaAllegatoOrdinanza già esistente:						
			if (TipoAllegato.OPPOSIZIONE_GIURISDIZIONALE.getId() == allegato.getIdTipoAllegato() && !pregresso) {
				CnmDStatoOrdinanza cnmDStatoOrdinanza = cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_RICORSO_IN_ATTO);
				ordinanzaService.saveSStatoOrdinanza(cnmTOrdinanza, cnmTUser);
				cnmTOrdinanza.setCnmDStatoOrdinanza(cnmDStatoOrdinanza);
				cnmTOrdinanza.setCnmTUser1(cnmTUser);
				cnmTOrdinanza.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));
				cnmTOrdinanzaRepository.save(cnmTOrdinanza);
			}
			
		
			CnmRAllegatoOrdinanzaPK cnmRAllegatoOrdinanzaPK = new CnmRAllegatoOrdinanzaPK();
			cnmRAllegatoOrdinanzaPK.setIdAllegato(cnmTAllegato.getIdAllegato());
			cnmRAllegatoOrdinanzaPK.setIdOrdinanza(cnmTOrdinanza.getIdOrdinanza());

			CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza = new CnmRAllegatoOrdinanza();
			cnmRAllegatoOrdinanza.setId(cnmRAllegatoOrdinanzaPK);
			cnmRAllegatoOrdinanza.setCnmTAllegato(cnmTAllegato);
			cnmRAllegatoOrdinanza.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
			cnmRAllegatoOrdinanza.setCnmTUser(cnmTUser);
			cnmRAllegatoOrdinanzaRepository.save(cnmRAllegatoOrdinanza);


			response.add(cnmTAllegato);
				
				
		
		}
		
	
		// 20200720_LC se si sta processando un allegato al master non deve spostare
		// 20201106_ET per i PREGRESSI sposto anche se sto processando un allegato
		if ((!StringUtils.isBlank(doc.getRegistrazioneId()) && !fascicoloProtocollatoGiaSpostato)
				|| (pregresso && !fascicoloProtocollatoGiaSpostato)) {

			// essendo allegati ad ordinanza il fascicolo è già acquisito
			avviaSpostamentoPerFascicoloGiaAcquisito(response, cnmTVerbale, cnmTUser);
			
		}
		
		return response;
		
	}

	private String getIdActaMasterDaAllegatiSalvati(List<CnmTAllegato> allegatiGiaSalvati) {
		String idActaMaster = null;
		for(CnmTAllegato allegatoSalvato:allegatiGiaSalvati) {
			if(StringUtils.isBlank(allegatoSalvato.getIdActaMaster())) { //prendo il master, che deve esserci per forza
				idActaMaster = allegatoSalvato.getIdActa(); //e del master prendo l'idActa
				break;
			}
		}
		return idActaMaster;
	}
	
	
	
	
	
	// 20200716_LC
	@Override
	public void avviaSpostamentoPerFascicoloGiaAcquisito(List<CnmTAllegato> cnmTAllegatoMultiTipoList, CnmTVerbale cnmTVerbale, CnmTUser cnmTUser) {
		
		// cnmTAllegatoMultiTipoList sono tutti gli allegati pertinenti ad un solo documento da spostare (multitipo)
		// la cosa problematica da gestire è la possibile presenza di >1 tipoAllegato per un documento (solo in caso di allegati al verbale, all'ordinanza è solo 1 sempre)
		// e quindi la valorizzazione di idTipoALlegato e idEntitaFruitore da passare a doquiServiceFacade.spostaDocumentoProtocollato
		
		//ResponseProtocollaDocumento responseProtocollaDocumento = null;
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

		List<CnmTAllegato> cnmTAllegatoList = new ArrayList<>();
		String operationToTrace = null;
							

		// può essere solo in stato 7	
		for  (CnmTAllegato cnmTAllegatoTs : cnmTAllegatoMultiTipoList) {
			if (cnmTAllegatoTs.getCnmDStatoAllegato().getIdStatoAllegato() != Constants.STATO_ALLEGATO_PROTOCOLLATO_IN_ALTRA_STRUTTURA)
				throw new RuntimeException("Allegato in stato non corretto");
		}
		
				
		// ------------------------------
		
		if (cnmTVerbale==null) {
			throw new RuntimeException("Verbale non presente");
		}
		
		
																						 
		
		// se ci sono piu allegati nella lista, va gestito il caso di documenti con più tipi		
		// input per spostamento - decidere come gestire il multitipo
		String nomeFile = cnmTAllegatoMultiTipoList.get(0).getNomeFile();	// questa è uguale per tutti (il documento su acta è solo uno)
		String numeroProtocollo = cnmTAllegatoMultiTipoList.get(0).getNumeroProtocollo();	// questa è uguale per tutti (il documento su acta è solo uno)
		long idTipoAllegato = cnmTAllegatoMultiTipoList.get(0).getCnmDTipoAllegato().getIdTipoAllegato();	// gli serve per definire il tipoDocumentoActa, dovrebbe andar bene sempre il 27, verificare (controdeduzione è 29), uno qualsiasi
		
		// 20200720_LC se size>1 ci scrive documento conetnente N allegati
		String idEntitaFruitore = null;
		if (cnmTAllegatoMultiTipoList.size()==1) {
			idEntitaFruitore = utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegatoMultiTipoList.get(0).getCnmDTipoAllegato());
		}else {
			idEntitaFruitore = utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegatoMultiTipoList);
		}
		
		
		String objIdDocumentoToTrace = cnmTAllegatoMultiTipoList.get(0).getObjectidSpostamentoActa();
		
		// 20200728_LC verifica presenza del protocollo in Conam
		if (cnmTAllegatoRepository.findByNumeroProtocolloAndObjectidSpostamentoActaIsNull(cnmTAllegatoMultiTipoList.get(0).getNumeroProtocollo()).isEmpty()) {
			// se è vuota sposta (protocollo non presente in Conam)
			

			
				
			// responseSpostaDOcumento extends responseProtocollaDocumento
			//responseProtocollaDocumento =
			doquiServiceFacade.spostaDocumentoProtocollato(
					utilsDoqui.createOrGetfolder(cnmTVerbale), 
					nomeFile,
					idEntitaFruitore,
					true, false, 
					utilsDoqui.getSoggettoActa(cnmTVerbale),
					utilsDoqui.getRootActa(cnmTVerbale), 
					idTipoAllegato, 
					numeroProtocollo, 
					cnmTVerbale.getIdVerbale());			
			
//CRP			if (cacheRicercaProtocollo.containsKey(numeroProtocollo))
//CRP				cacheRicercaProtocollo.remove(numeroProtocollo);
			
			for(CnmTAllegato allegato: cnmTAllegatoMultiTipoList) {
					operationToTrace = allegato.isFlagDocumentoPregresso() ? TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO_TI.getOperation() : TraceOperation.INSERIMENTO_ALLEGATO_TI.getOperation();
					utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"objectIdDocumento="+objIdDocumentoToTrace, Thread.currentThread().getStackTrace()[1].getMethodName(), "spostaDocumentoSuNuovaStruttura");
			}

		} else {
			// se non è vuota copia (protocollo già presente in Conam)
			if(checkDocumentoGiaCopiato(numeroProtocollo, cnmTVerbale.getIdVerbale(), null)) {
				logger.info("Documento gia' copiato nel fascicolo, non serve aggiungere la classificazione");
			}else {
				// responseSpostaDOcumento extends responseProtocollaDocumento
				//responseProtocollaDocumento = 
				doquiServiceFacade.copiaDocumentoProtocollato(
						utilsDoqui.createOrGetfolder(cnmTVerbale), 
					nomeFile,
					idEntitaFruitore,
					true, false, 
					utilsDoqui.getSoggettoActa(cnmTVerbale),
					utilsDoqui.getRootActa(cnmTVerbale), 
					idTipoAllegato, 
					numeroProtocollo, 
					cnmTVerbale.getIdVerbale());	
				
				for(CnmTAllegato allegato: cnmTAllegatoMultiTipoList) {
						operationToTrace = allegato.isFlagDocumentoPregresso() ? TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO_CI.getOperation() : TraceOperation.INSERIMENTO_ALLEGATO_CI.getOperation();
						utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"objectIdDocumento="+objIdDocumentoToTrace, Thread.currentThread().getStackTrace()[1].getMethodName(), "copiaDocumentoSuNuovaStruttura");
				}
				
			}
		}
		
		
		
		
		
		
		
		// aggiorna allegati con stato 2
		for (CnmTAllegato cnmTAllegatoTs : cnmTAllegatoMultiTipoList) {
			cnmTAllegatoTs.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
			cnmTAllegatoTs.setObjectidSpostamentoActa(null);
			cnmTAllegatoTs.setDataOraUpdate(now);					
			cnmTAllegatoTs.setCnmTUser1(cnmTUser);	
						
			// 20200722_LC non qui, ma quando si crea l'allegato (quindi in salvaAllegatoProtocollato
			// trace allegati appena assegnati al verbale e spostati in acta
			// utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO.getOperation(),cnmTAllegatoTs.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegatoTs.getIdAllegato(), Thread.currentThread().getStackTrace()[1].getMethodName());
			
			cnmTAllegatoList.add(cnmTAllegatoTs);
			
		}
		

		
		cnmTAllegatoRepository.save(cnmTAllegatoList);

		// return responseProtocollaDocumento;
	}


	
	
	
	
	
	
	// 20200827_LC
	public List<DocumentoScaricatoVO> getDocFisiciByIdAllegato(Integer id) {
		if (id == null)
			throw new IllegalArgumentException("id non valorizzato");
		CnmTAllegato cnmTAllegato = cnmTAllegatoRepository.findOne(id);
		cnmTAllegato = validateRequestAllegato(cnmTAllegato);
  		
		// solo i documenti su Acta possono essere multipli
		if (cnmTAllegato.getIdIndex() != null) {
				throw new RuntimeException("Documento non presente in Acta");
		} 
		
		else if (cnmTAllegato.getIdActa() != null) {
   
			ResponseRicercaDocumentoMultiplo response = doquiServiceFacade.recuperaDocumentoActa(cnmTAllegato.getIdActa());

			if (response != null && response.getSottoDocumenti() != null) {
				
					List<DocumentoScaricatoVO> docScaricatoVOList = new ArrayList<DocumentoScaricatoVO>();
					
					for (Documento doc:response.getSottoDocumenti()) {
						DocumentoScaricatoVO docScaricatoVO = new DocumentoScaricatoVO();
						//docScaricatoVO.setFile(doc.getFile());
						docScaricatoVO.setNomeFile(doc.getNomeFile());
						docScaricatoVO.setObjectIdDocumentoFisico(doc.getObjectIdDocumentoFisico());
						docScaricatoVOList.add(docScaricatoVO);
					}
					
					return docScaricatoVOList;	
			
			} else {

				throw new RuntimeException("documento non trovato su acta per parola chiave");
			}
		}  
		
		
		else if (cnmTAllegato.getObjectidSpostamentoActa() != null) {

			// se entrambi sono NULL significa che il doc è stato associato ma non spostato - ricava objectId da CnmTAllegato e chiama il download per objectId
			List<DocumentoScaricatoVO> response = downloadAllegatoByObjectIdDocumento(cnmTAllegato.getObjectidSpostamentoActa());
			
			if (response != null ) {
				return response;	
				
			} else {
				 throw new RuntimeException("documento non trovato su acta per objectIdDocumento");			
			}
		} 
		
		// IMPOSSIBILE EFFETTUARE IL DOWNLOAD
		else {
			throw new RuntimeException("idActa e objectIdSpostamentoActa non valorizzati");			
		}
		
	}
	// 20200711_LC
	public List<DocumentoScaricatoVO> getDocFisiciByObjectIdDocumento(String objectIdDocumento) {
		if (objectIdDocumento == null)
			throw new IllegalArgumentException("objectIdDocumento non valorizzato");
		
		ResponseRicercaDocumentoMultiplo response = doquiServiceFacade.recuperaDocumentoActaByObjectIdDocumento(objectIdDocumento);

			if (response != null && response.getSottoDocumenti() != null) {										  

				List<DocumentoScaricatoVO> docScaricatoVOList = new ArrayList<DocumentoScaricatoVO>();
				
				for (Documento doc:response.getSottoDocumenti()) {
					DocumentoScaricatoVO docScaricatoVO = new DocumentoScaricatoVO();
					//docScaricatoVO.setFile(doc.getFile());
					docScaricatoVO.setNomeFile(doc.getNomeFile());
					docScaricatoVO.setObjectIdDocumentoFisico(doc.getObjectIdDocumentoFisico());
					docScaricatoVOList.add(docScaricatoVO);
				}
					return docScaricatoVOList;
					
			}
			else
			{
				throw new RuntimeException("documento non trovato su acta");
			}

	}
	

	@Override
	@Transactional
	public ResponseProtocollaDocumento avviaProtocollazioneDocumentoEsistente(CnmTAllegato cnmTAllegato, CnmTUser cnmTUser) {
		if(isDoquiDirect()) {
			return avviaProtocollazioneDocumentoEsistente_Doqui(cnmTAllegato, cnmTUser);
		}
		return null;
//		else {
//			return avviaProtocollazione_Stadoc(cnmRAllegatoVerbaleList);
//		}
	}
	

	private ResponseProtocollaDocumento avviaProtocollazioneDocumentoEsistente_Doqui(CnmTAllegato cnmTAllegato, CnmTUser cnmTUser) {
		
		// entra qui dentro con l'allegato master che va protocollato (in modo che il protocollo venga asseganto anche a tutti i suoi allegati)
		// ISTANZA RATEIZZAZIONE (gia presente in acta ma non protocollata, il batch ci ha appena spostato tutti i suoi allegati, ed ora si procede alal protocollazione di tutti quanti)
		
		ResponseProtocollaDocumento responseProtocollaDocumento = null;		// 20200706_LC
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());

		List<CnmTAllegato> cnmTAllegatoList = new ArrayList<>();
		
		// può essere solo in stato 1
		if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() != Constants.STATO_ALLEGATO_NON_PROTOCOLLARE)
			throw new RuntimeException("Allegato master non e' nello stato corretto");
				
		// definizione di folder, entitaFruitore, soggetto e root a partire dall'ordinanza (invece che dal verbale come di solito, essendo l'ISTANZA un allgeato dell'ordinanza)
		// in caso di gestione di altri tipi allegato inserire blocchi in questo if
		
		
		String folder;
		String idEntitaFruitore;
		String soggettoActa;
		String rootActa;
		CnmTVerbale cnmTVerbale = null;
		if (cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.ISTANZA_RATEIZZAZIONE.getId()) {
			CnmTOrdinanza cnmTOrdinanza = cnmRAllegatoOrdVerbSogRepository.findByCnmTAllegato(cnmTAllegato).get(0).getCnmROrdinanzaVerbSog().getCnmTOrdinanza(); 
			folder = utilsDoqui.createOrGetfolder(cnmTOrdinanza);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");	
//			String formattedDate = dateFormatter.format(new Date());
			idEntitaFruitore = utilsDoqui.createIdEntitaFruitore(cnmTOrdinanza, cnmTAllegato.getCnmDTipoAllegato()); // + " [" + formattedDate + "]";
			soggettoActa = utilsDoqui.getSoggettoActa(cnmTOrdinanza);
			rootActa = utilsDoqui.getRootActa(cnmTOrdinanza);			
		}else if (cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.RAPPORTO_TRASMISSIONE.getId()) {
			cnmTVerbale = cnmRAllegatoVerbaleRepository.findByCnmTAllegato(cnmTAllegato).getCnmTVerbale(); 
			folder = utilsDoqui.createOrGetfolder(cnmTVerbale);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");	
//			String formattedDate = dateFormatter.format(new Date());
			idEntitaFruitore = utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()); // + " [" + formattedDate + "]";
			soggettoActa = utilsDoqui.getSoggettoActa(cnmTVerbale);
			rootActa = utilsDoqui.getRootActa(cnmTVerbale);			
		} else if (cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.LETTERA_ORDINANZA.getId()) {
			CnmTOrdinanza cnmTOrdinanza = cnmRAllegatoOrdinanzaRepository.findByCnmTAllegato(cnmTAllegato).get(0).getCnmTOrdinanza(); 
			folder = utilsDoqui.createOrGetfolder(cnmTOrdinanza);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");	
//			String formattedDate = dateFormatter.format(new Date());
			idEntitaFruitore = utilsDoqui.createIdEntitaFruitore(cnmTOrdinanza, cnmTAllegato.getCnmDTipoAllegato()); // + " [" + formattedDate + "]";
			soggettoActa = utilsDoqui.getSoggettoActa(cnmTOrdinanza);
			rootActa = utilsDoqui.getRootActa(cnmTOrdinanza);			
		} else if (cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.COMPARSA.getId()) {
			cnmTVerbale = cnmRAllegatoVerbaleRepository.findByCnmTAllegato(cnmTAllegato).getCnmTVerbale(); 
			folder = utilsDoqui.createOrGetfolder(cnmTVerbale);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyy hh:mm:ss");	
//			String formattedDate = dateFormatter.format(new Date());
			idEntitaFruitore = utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()); // + " [" + formattedDate + "]";
			soggettoActa = utilsDoqui.getSoggettoActa(cnmTVerbale);
			rootActa = utilsDoqui.getRootActa(cnmTVerbale);			
		}else{
			throw new SecurityException("tipo alleggato non corretto");
		}



		if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_NON_PROTOCOLLARE) {
			// DOCUMENTO DA PROTOCOLLARE
			responseProtocollaDocumento = doquiServiceFacade.protocollaDocumentoFisicoEsistente(folder, cnmTAllegato.getNomeFile(),
					idEntitaFruitore, true, false, soggettoActa, rootActa,
					cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), DoquiServiceFacade.TIPOLOGIA_DOC_ACTA_MASTER_INGRESSO_CON_ALLEGATI, null, cnmTAllegato.getIdActa());
			
			// nessun tracciamento (questo allegato già esiste in acta)
			
			cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
			cnmTAllegato.setObjectidSpostamentoActa(null);
			cnmTAllegato.setIdActa(responseProtocollaDocumento.getIdDocumento());
			cnmTAllegato.setDataOraUpdate(now);			
			
			// 20200703_LC
			cnmTAllegato.setNumeroProtocollo(responseProtocollaDocumento.getProtocollo());
			cnmTAllegato.setDataOraProtocollo(now);
	
			// numProtocollo su altri allegati lo setta nellos cheduler
			
			cnmTAllegatoList.add(cnmTAllegato);		
			
			if (cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.RAPPORTO_TRASMISSIONE.getId()) {
				//20210806 PP - salvo sul verbale il numero protocollo e il nuovo stato
				verbaleService.salvaNumeroProtocollo(cnmTVerbale.getIdVerbale(), responseProtocollaDocumento.getProtocollo(), cnmTUser);
			}
			
		}		
				
		cnmTAllegatoRepository.save(cnmTAllegatoList);

		return responseProtocollaDocumento;
	}

	
	
	
	
	
	
	
}
