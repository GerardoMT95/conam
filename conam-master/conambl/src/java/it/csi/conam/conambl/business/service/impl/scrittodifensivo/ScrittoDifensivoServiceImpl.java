/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.scrittodifensivo;

import it.csi.conam.conambl.business.facade.DoquiServiceFacade;
import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.scrittodifensivo.ScrittoDifensivoService;
import it.csi.conam.conambl.business.service.scrittodifensivo.UtilsScrittoDifensivoService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.business.service.util.UtilsTraceCsiLogAuditService;
import it.csi.conam.conambl.business.service.verbale.UtilsVerbale;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.TipoProtocolloAllegato;
import it.csi.conam.conambl.integration.beans.ResponseSpostaDocumento;
import it.csi.conam.conambl.integration.doqui.entity.CnmTDocumento;
import it.csi.conam.conambl.integration.doqui.repositories.CnmTDocumentoRepository;
import it.csi.conam.conambl.integration.doqui.utils.DateFormat;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.entity.CsiLogAudit.TraceOperation;
import it.csi.conam.conambl.integration.mapper.entity.ScrittoDifensivoEntityMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.request.scrittodifensivo.SalvaScrittoDifensivoRequest;
import it.csi.conam.conambl.response.SalvaScrittoDifensivoResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.UploadUtils;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.verbale.DocumentoProtocollatoVO;
import it.csi.conam.conambl.vo.verbale.ScrittoDifensivoVO;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Service
public class ScrittoDifensivoServiceImpl implements ScrittoDifensivoService {

	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private UtilsDoqui utilsDoqui;
	@Autowired
	private UtilsVerbale utilsVerbale;
	@Autowired
	private UtilsScrittoDifensivoService utilsScrittoDifensivo;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CnmTScrittoDifensivoRepository cnmTScrittoDifensivoRepository;
	@Autowired
	private CnmDTipoAllegatoRepository cnmDTipoAllegatoRepository;
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;
	@Autowired
	private CnmTDocumentoRepository cnmTDocumentoRepository;
	@Autowired
	private ScrittoDifensivoEntityMapper scrittoDifensivoEntityMapper;;
	@Autowired
	private CommonAllegatoService commonAllegatoService;
	@Autowired
	private CnmDStatoAllegatoRepository cnmDStatoAllegatoRepository;
	@Autowired
	private DoquiServiceFacade doquiServiceFacade;
	@Autowired
	private UtilsTraceCsiLogAuditService utilsTraceCsiLogAuditService;
	@Autowired
	private CnmDModalitaCaricamentoRepository cnmDModalitaCaricamentoRepository;
	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;
	
	
	
	
	
	@Override
	public SalvaScrittoDifensivoResponse salvaScrittoDifensivo(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		
		SalvaScrittoDifensivoResponse response = new SalvaScrittoDifensivoResponse();
		SalvaScrittoDifensivoRequest request = commonAllegatoService.getRequest(data, file, SalvaScrittoDifensivoRequest.class);
		
		if (request == null)
			throw new IllegalArgumentException("request is null");	
				
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmTScrittoDifensivo cnmTScrittoDifensivo;
		
		ScrittoDifensivoVO scrittoDifensivoVO = request.getScrittoDifensivo();		
		if (scrittoDifensivoVO == null)
			throw new IllegalArgumentException("scrittoDifensivoVO is null");
		
		byte[] byteFile = request.getFile();
		
		DocumentoProtocollatoVO documentoProtocollato = request.getDocumentoProtocollato();
		String nomeFile = request.getScrittoDifensivo().getNomeFile();
		
		
		
		if (request.getScrittoDifensivo().getId() == null) {
						
			// NUOVO SCRITTO -------------------------------------------------
			cnmTScrittoDifensivo = scrittoDifensivoEntityMapper.mapVOtoEntity(scrittoDifensivoVO); // crea + riempie solo campi editati dall'utente (numVerbaleAccertamento e nomi e ambito)
			cnmTScrittoDifensivo.setFlagAssociato(false);
			
			
			// data - user insert
			cnmTScrittoDifensivo.setDataOraInsert(now);
			cnmTScrittoDifensivo.setCnmTUser2(cnmTUser);

			
			CnmTAllegato cnmTAllegato = new CnmTAllegato();
			if (byteFile == null && documentoProtocollato != null) {
				
				// ALLEGATO DA ACTA
				CnmDTipoAllegato cnmDTipoAllegato = cnmDTipoAllegatoRepository.findOne(TipoAllegato.SCRITTI_DIFENSIVI.getId());	
				cnmTAllegato.setCnmDTipoAllegato(cnmDTipoAllegato);
				cnmTAllegato.setNomeFile(documentoProtocollato.getFilename());
				cnmTAllegato.setIdIndex(null);
				String numProtocollo = StringUtils.isBlank(documentoProtocollato.getRegistrazioneId())?documentoProtocollato.getNumProtocolloMaster():documentoProtocollato.getNumProtocollo();
				cnmTAllegato.setNumeroProtocollo(numProtocollo);
				if (StringUtils.isNotBlank(documentoProtocollato.getRegistrazioneId()) && StringUtils.isNotBlank(documentoProtocollato.getDataOraProtocollo())) {  				
					Date date = utilsDate.getDate(documentoProtocollato.getDataOraProtocollo(), DateFormat.DATE_FORMAT_DDMMYY);
					cnmTAllegato.setDataOraProtocollo(new Timestamp(date.getTime()));
				} 	
				cnmTAllegato.setCnmTUser2(cnmTUser);
				cnmTAllegato.setDataOraInsert(now);			
				cnmTAllegato.setFlagRecuperatoPec(true);
				cnmTAllegato.setFlagDocumentoPregresso(false);
				cnmTAllegato.setIdActa(documentoProtocollato.getIdActa());	
				cnmTAllegato.setIdActaMaster(null);	
				cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO_IN_ALTRA_STRUTTURA));
				cnmTAllegato.setObjectidSpostamentoActa(documentoProtocollato.getObjectIdDocumento());	
				

				cnmTScrittoDifensivo.setObjectidActa(documentoProtocollato.getObjectIdDocumento());
				cnmTScrittoDifensivo.setNomeFile(documentoProtocollato.getFilename());
				cnmTScrittoDifensivo.setCnmTAllegato(cnmTAllegato);
				cnmTScrittoDifensivo.setNumeroProtocollo(numProtocollo);
				cnmTScrittoDifensivo.setDataOraProtocollo(now);
				
				// 20210517_LC modalita caricamento
				CnmDModalitaCaricamento docDaActa = cnmDModalitaCaricamentoRepository.findOne(Constants.ID_MODALITA_CARICAMENTO_DA_ACTA);
				if (docDaActa == null) throw new SecurityException("idModalitaCaricamento inesistente");
				cnmTScrittoDifensivo.setCnmDModalitaCaricamento(docDaActa);

				// per sposta o copia Acta
				String folder = utilsDoqui.createOrGetfolderScrittoDifensivo();  // fodler temporaneo (la sua parola chiave) nel quale mettere gli scritti difensivi
				String idEntitaFruitore =  utilsDoqui.createIdEntitaFruitoreScrittoDifensivo(cnmTScrittoDifensivo);	// è l'oggetto del documento su Acta
				String soggettoActa = null; //utilsDoqui.getSoggettoActaScrittoDifensivo(cnmTScrittoDifensivo);	// lo usa come soggetto della EVENTUALE nuova cartella (si vede nel nome da interfaccia)
				String rootActa = null;	//utilsDoqui.getRootActaScrittoDifensivo(cnmTScrittoDifensivo);	// è il folder parent del folde rin cui vanno messi gli scritti difensivi		
				
				ResponseSpostaDocumento responseSposta = null;
				String idDocumentoConam = null;
				MessageVO messaggio = null;
				
				// 20210526_LC gestisce casi di protocollo gia presente in CnmTScrittoDifensivo (e quindi nel folder CONAMSCR)
				List<CnmTScrittoDifensivo> cnmTScrittoDifensivoStessoProtocolloList = cnmTScrittoDifensivoRepository.findByNumeroProtocollo(numProtocollo);
				if (cnmTScrittoDifensivoStessoProtocolloList != null && !cnmTScrittoDifensivoStessoProtocolloList.isEmpty()) {

					// se il rpotocollo deriva da un caricamento da dispositivo, torna msg errore (e quindi puo esserci solo uno, ecco perche il get(0))
					if (cnmTScrittoDifensivoStessoProtocolloList.get(0).getCnmDModalitaCaricamento().getIdModalitaCaricamento() == Constants.ID_MODALITA_CARICAMENTO_DA_DISPOSITVO) {
						CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.PROTOCOLLO_GIA_SCRITTO_DIFENSIVO);
						if(cnmDMessaggio!=null) {
							messaggio = new MessageVO(cnmDMessaggio.getDescMessaggio(), cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
						}else {
							throw new SecurityException("Messaggio non trovato");
						}
						response.setErrorMsg(messaggio);
						return response;
						
					}
					
					
					
					List<CnmTScrittoDifensivo> stessoSD = cnmTScrittoDifensivoRepository.findByObjectidActa(documentoProtocollato.getObjectIdDocumento());
					
					if (stessoSD != null && !stessoSD.isEmpty()) {
						// gia inserito questo protocollo-documento
						idDocumentoConam = stessoSD.get(0).getCnmTAllegato().getIdActa();
						
					} else {
						// gia inseriti documenti di stesso protocollo, ma non questo: prendo objectIdDocumento dalla cnmTdocumento
						List<CnmTDocumento> cnmTDocumentoList = cnmTDocumentoRepository.findByObjectiddocumento(documentoProtocollato.getObjectIdDocumento());
						
						if (cnmTDocumentoList != null && !cnmTDocumentoList.isEmpty())
							idDocumentoConam = String.valueOf(cnmTDocumentoList.get(0).getIdDocumento());
						
					}
					

					// protocollo gia in CnmTScrittoDifensivo a seguito di sposta/copia
					// non fa la copia perche è gia presente, e prende dall'allegato di questo s.d. l'idActa per il nuovo allegato

					
					
				} else if (cnmTAllegatoRepository.findByNumeroProtocolloAndObjectidSpostamentoActaIsNull(numProtocollo).isEmpty()) {
					// se è vuota sposta (protocollo non presente in Conam)

					responseSposta = doquiServiceFacade.spostaDocumentoProtocollatoTemporaneo(
							folder, 
							nomeFile,
							idEntitaFruitore,
							true, false, 
							soggettoActa,
							rootActa, 
							cnmDTipoAllegato.getIdTipoAllegato(), 
							numProtocollo);		

					idDocumentoConam = responseSposta.getIdDocumento();
					String operationToTrace =  TraceOperation.INSERIMENTO_ALLEGATO_TI.getOperation();
					String objIdDocumentoToTrace = responseSposta.getObjectIdDocumentoToTraceList().get(0); // un solo documento
					utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"objectIdDocumento="+objIdDocumentoToTrace, Thread.currentThread().getStackTrace()[1].getMethodName(), "spostaDocumentoSuNuovaStruttura");

				} else {
					// se non è vuota copia (protocollo già presente in Conam)

					responseSposta = doquiServiceFacade.copiaDocumentoProtocollatoTemporaneo(
							folder, 
							nomeFile,
							idEntitaFruitore,
							true, false, 
							soggettoActa,
							rootActa, 
							cnmDTipoAllegato.getIdTipoAllegato(), 
							numProtocollo);	
				
					idDocumentoConam = responseSposta.getIdDocumento();
					String operationToTrace = TraceOperation.INSERIMENTO_ALLEGATO_CI.getOperation();
					String objIdDocumentoToTrace = responseSposta.getObjectIdDocumentoToTraceList().get(0); // un solo documento
					utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"objectIdDocumento="+objIdDocumentoToTrace, Thread.currentThread().getStackTrace()[1].getMethodName(), "copiaDocumentoSuNuovaStruttura");	
						
				}
		
				// aggiorna allegato dopo (eventuale) sposytamento
				cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
				cnmTAllegato.setObjectidSpostamentoActa(null);
				cnmTAllegato.setIdActa(idDocumentoConam);
				cnmTAllegato.setDataOraUpdate(now);					
				cnmTAllegato.setCnmTUser1(cnmTUser);	
				cnmTAllegatoRepository.save(cnmTAllegato);

				
				
				
				
				
				
				
				
			} else if (byteFile != null && documentoProtocollato == null) {
				// ALLEGATO DA FILE SYSTEM
				
				// controlli dimensione e firma
				UploadUtils.checkDimensioneAllegato(byteFile);
				utilsDoqui.checkFileSign(byteFile, request.getScrittoDifensivo().getNomeFile());
				
				// parametri per protocollazione Acta
				Long idTipoAllegato = TipoAllegato.SCRITTI_DIFENSIVI.getId();
				String folder = utilsDoqui.createOrGetfolderScrittoDifensivo();    // fodler temporaneo (la sua parola chiave) nel quale mettere gli scritti difensivi
				String idEntitaFruitore =  utilsDoqui.createIdEntitaFruitoreScrittoDifensivo(cnmTScrittoDifensivo);	// è l'oggetto del documento su Acta
				String soggettoActa = null; //utilsDoqui.getSoggettoActaScrittoDifensivo(cnmTScrittoDifensivo);	// lo usa come soggetto della EVENTUALE nuova cartella (si vede nel nome da interfaccia)
				String rootActa = null;	// utilsDoqui.getRootActaScrittoDifensivo(cnmTScrittoDifensivo);		// è il folder parent del folde rin cui vanno messi gli scritti difensivi			
		
				cnmTAllegato = commonAllegatoService.salvaAllegato(byteFile, nomeFile, idTipoAllegato, null, cnmTUser, TipoProtocolloAllegato.PROTOCOLLARE,
						folder, idEntitaFruitore, false, false, soggettoActa, rootActa, 0, 0, DoquiServiceFacade.TIPOLOGIA_DOC_ACTA_DOC_INGRESSO_SENZA_ALLEGATI, null); 

				CnmTDocumento documentoFromAllegato = cnmTDocumentoRepository.findOne(Integer.parseInt(cnmTAllegato.getIdActa()));
				if (documentoFromAllegato == null)
					throw new IllegalArgumentException("documento inesistsente su conam");
				
				cnmTScrittoDifensivo.setObjectidActa(documentoFromAllegato.getObjectiddocumento());
				cnmTScrittoDifensivo.setNomeFile(nomeFile);
				cnmTScrittoDifensivo.setCnmTAllegato(cnmTAllegato);
				cnmTScrittoDifensivo.setNumeroProtocollo(cnmTAllegato.getNumeroProtocollo());
				cnmTScrittoDifensivo.setDataOraProtocollo(now);
				
				// 20210517_LC modalita caricamento
				CnmDModalitaCaricamento docDaDispositivo = cnmDModalitaCaricamentoRepository.findOne(Constants.ID_MODALITA_CARICAMENTO_DA_DISPOSITVO);
				if (docDaDispositivo == null) throw new SecurityException("idModalitaCaricamento inesistente");
				cnmTScrittoDifensivo.setCnmDModalitaCaricamento(docDaDispositivo);
				
				
			} else {
				throw new IllegalArgumentException("solo uno tra file e documentoProtocollato deve essere != null");	
			}

			
			// save
			cnmTScrittoDifensivo = cnmTScrittoDifensivoRepository.save(cnmTScrittoDifensivo);
			

				
			
			
			
			
			
			
			
			
		} else {
		
			// UPDATE SCRITTO ESISTENTE ---------------------------------------
			cnmTScrittoDifensivo = utilsScrittoDifensivo.validateAndGetCnmTScrittoDifensivo(scrittoDifensivoVO.getId().intValue());		
			if (cnmTScrittoDifensivo.isFlagAssociato()) throw new IllegalArgumentException("scritto associato, impossibile modificare");	
		
			cnmTScrittoDifensivo = scrittoDifensivoEntityMapper.mapVOtoEntityUpdate(scrittoDifensivoVO, cnmTScrittoDifensivo); // update campi editati dall'utente (numVerbaleAccertamento e nomi e ambito)		
			
			
			// data - user update
			cnmTScrittoDifensivo.setDataOraUpdate(now);
			cnmTScrittoDifensivo.setCnmTUser1(cnmTUser);

			// save
			cnmTScrittoDifensivo = cnmTScrittoDifensivoRepository.save(cnmTScrittoDifensivo);
				
		}
		
		
		
		
		

		response.setIdScrittoDifensivo(cnmTScrittoDifensivo.getIdScrittoDifensivo());
		
		return response;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public ScrittoDifensivoVO dettaglioScrittoDifensivo(Integer idScrittoDifensivo, UserDetails userDetails) {		
		CnmTScrittoDifensivo cnmTScrittoDifensivo = utilsScrittoDifensivo.validateAndGetCnmTScrittoDifensivo(idScrittoDifensivo);			
		ScrittoDifensivoVO scrittoDifensivoVO = scrittoDifensivoEntityMapper.mapEntityToVO(cnmTScrittoDifensivo);
		return scrittoDifensivoVO;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void associaScrittoDifensivo(CnmTScrittoDifensivo cnmTScrittoDifensivo, CnmTVerbale cnmTVerbale, CnmTUser cnmTUser) {
		if (cnmTScrittoDifensivo.isFlagAssociato()) throw new IllegalArgumentException("scritto già associato");
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());			
		
		// update scritto difensivo
		cnmTScrittoDifensivo.setFlagAssociato(true);
		cnmTScrittoDifensivo.setDataOraUpdate(now);
		cnmTScrittoDifensivo.setCnmTUser1(cnmTUser);
		cnmTScrittoDifensivo = cnmTScrittoDifensivoRepository.save(cnmTScrittoDifensivo);
	
		// lo spostamento lo fa come di consueto nella salvaAllegaProtocollato
		// qui aggiorna solo la cnm_t_scritto_difensivo

	}



	
	
	
	
	
	
	
	
	
	@Override
	public void associaScrittoDifensivoById(Integer idScrittoDifensivo, Integer idVerbale, UserDetails userDetails) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(idVerbale);
		CnmTScrittoDifensivo cnmTScrittoDifensivo = utilsScrittoDifensivo.validateAndGetCnmTScrittoDifensivo(idScrittoDifensivo);
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		associaScrittoDifensivo(cnmTScrittoDifensivo,cnmTVerbale,cnmTUser);		
	}
	
	
	

	
}
