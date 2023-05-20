/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.helper.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.beans.RequestAggiungiAllegato;
import it.csi.conam.conambl.integration.beans.RequestRicercaAllegato;
import it.csi.conam.conambl.integration.beans.ResponseAggiungiAllegato;
import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.doqui.bean.*;
import it.csi.conam.conambl.integration.doqui.entity.*;
import it.csi.conam.conambl.integration.doqui.exception.AggiungiAllegatoException;
import it.csi.conam.conambl.integration.doqui.helper.ManageAggiungiAllegatoHelper;
import it.csi.conam.conambl.integration.doqui.helper.ManageDocumentoHelper;
import it.csi.conam.conambl.integration.doqui.service.ActaManagementService;
import it.csi.conam.conambl.integration.doqui.utils.XmlSerializer;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.DocumentUtils;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumMimeTypeType;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ManageAggiungiAllegatoHelperImpl  extends CommonManageDocumentoHelperImpl implements ManageAggiungiAllegatoHelper
{

//	private static final String LOGGER_PREFIX = DoquiConstants.LOGGER_PREFIX + ".helper";
//	private static final Logger log = Logger.getLogger(LOGGER_PREFIX);
	private static Logger log = Logger.getLogger(ManageAggiungiAllegatoHelperImpl.class);

	@Autowired
	private ActaManagementService	actaManagementService;
	
	// 20200610_LC
	@Autowired
	private ManageDocumentoHelper manageDocumentoHelper;

	// 20200630_LC
	@Autowired
	private UtilsDate utilsDate;
	
	// 20200702_LC
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	
//	public ActaManagementService getActaManagementService() {
//		return actaManagementService;
//	}
//	public void setActaManagementService(ActaManagementService actaManagementService) {
//		this.actaManagementService = actaManagementService;
//	}
	
	
	@SuppressWarnings({ "unused", "static-access" })
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)	
	public ResponseAggiungiAllegato aggiungiAllegato(RequestAggiungiAllegato request/*,String rootActa, String soggettoActaFruitore*/) throws AggiungiAllegatoException
	{
		String method = "aggiungiAllegato";
		log.debug(method + ". BEGIN");
		
		ResponseAggiungiAllegato response =  new ResponseAggiungiAllegato();
		boolean containsError = false;
		boolean idDocumentoRequest = false;
		
		
		String rootActa = request.getRootActa();
		String soggettoActaFruitore = request.getSoggettoActa();
		
		CnmTRichiesta cnmTRichiestaN = null;

		try
		{
			// validazioni 	
			if(request == null) throw new AggiungiAllegatoException("Request non valorizzata");
			if(request.getIdArchivioPadre() == null) throw new AggiungiAllegatoException("Id archivio padre non valorizzato");
			if(request.getIdArchivioAllegato() == null) throw new AggiungiAllegatoException("Id archivio allegato non valorizzato");
			if(request.getDocumento() == null) throw new AggiungiAllegatoException("Documento da allegare su acta non valorizzato");
			if(request.getMimeType() == null) throw new AggiungiAllegatoException("MimeType non valorizzato");
			if(request.getPkAllegato() == null) throw new AggiungiAllegatoException("PkAllegato non valorizzato");
			if(request.getMetadatiAllegato() == null) throw new AggiungiAllegatoException("metadatiAllegato non valorizzato");
			
			try
			{
				EnumMimeTypeType.fromValue(request.getMimeType());
			}
			catch (IllegalArgumentException e) 
			{
				throw new AggiungiAllegatoException("MimeType non corretto");
			}
		
			String idArchivioAllegato = request.getIdArchivioAllegato();
			String idArchivioPadre = request.getIdArchivioPadre();
			
			
			// 20200731_LC
			//20200728_ET spostato tutto il blocco perche finche non recupero il doc da index non posso sapere se e' firmato
			
			if(StringUtils.isBlank(request.getDocumento().getNomeFile()) || request.getDocumento().getFile() == null) {
				RequestRicercaAllegato requestRicercaAllegato = new RequestRicercaAllegato();
				requestRicercaAllegato.setIdDocumento(request.getDocumento().getIdDocumento());
				requestRicercaAllegato.setCodiceFruitore(request.getCodiceFruitore());
				
				String idDocumento = request.getDocumento().getIdDocumento();
				int num = request.getDocumento().getNumeroAllegati();
				idDocumentoRequest = true;
				String nomeFile = request.getDocumento().getNomeFile();
				request.setDocumento(manageDocumentoHelper.ricercaAllegato(requestRicercaAllegato).getDocumento());	
				request.getDocumento().setIdDocumento(idDocumento);
				request.getDocumento().setNumeroAllegati(num);
				request.getDocumento().setNomeFile(nomeFile);
				
				boolean isDocSigned = DocumentUtils.isDocumentSigned(request.getDocumento().getFile(), nomeFile);
				
				String tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA;
				if (request.getTipoDocumento() == DoquiConstants.TIPO_DOCUMENTO_CONAM_1) {
					tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_1_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_1;
				} else if (request.getTipoDocumento() == DoquiConstants.TIPO_DOCUMENTO_CONAM_2) {
					tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_2_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_2;
				}
				request.setTipoDocumento(tipoDoc);
				
			}
			
			
			// 20210413	-	tipo documento
			CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumento(request.getTipoDocumento());
			
			//recupero tipo documento allegato
			CnmDTipoDocumento cnmDTipoDocumentoAllegato = getTipoDocumentoByIdDocumento(idArchivioAllegato);
			
			//recupero tipo documento padre
			CnmDTipoDocumento cnmDTipoDocumentoPadre = getTipoDocumentoByIdDocumento(idArchivioPadre);
			
			//verifico che tipo doc padre e tipo documento figlio siano compatibili
						
			// 20200610_LC
			CnmRTipoDocPadreFiglioPK cnmRTipoDocPadreFiglioPK = new CnmRTipoDocPadreFiglioPK();
			cnmRTipoDocPadreFiglioPK.setIdTipoDocPadre(cnmDTipoDocumentoPadre.getIdTipoDocumento());
			cnmRTipoDocPadreFiglioPK.setIdTipoDocFiglio(cnmDTipoDocumentoAllegato.getIdTipoDocumento());		
			
			
			CnmRTipoDocPadreFiglio cnmRTipoDocPadreFiglio = new CnmRTipoDocPadreFiglio();
			cnmRTipoDocPadreFiglio.setId(cnmRTipoDocPadreFiglioPK);
			cnmRTipoDocPadreFiglio.setTipoDocPadre(cnmDTipoDocumentoPadre);
			cnmRTipoDocPadreFiglio.setTipoDocFiglio(cnmDTipoDocumentoAllegato);
			
			boolean esito = false;
			List<CnmRTipoDocPadreFiglio> cnmRTipoDocPadreFiglioList = getCnmRTipoDocPadreFiglioRepository().findAll();
			
			log.debug(method + ". cnmRTipoDocPadreFiglioList =\n " + XmlSerializer.objectToXml(cnmRTipoDocPadreFiglioList));
			for(CnmRTipoDocPadreFiglio elemento: cnmRTipoDocPadreFiglioList){
				log.debug(method + ". elemento.getIdTipoDocPadre(): " + elemento.getTipoDocPadre());
				log.debug("cnmRTipoDocPadreFiglio.getIdTipoDocPadre(): " + cnmRTipoDocPadreFiglio.getTipoDocPadre());
				log.debug("elemento.getIdTipoDocFiglio(): " + elemento.getTipoDocFiglio());
				log.debug("cnmRTipoDocPadreFiglio.getIdTipoDocFiglio(): " + cnmRTipoDocPadreFiglio.getTipoDocFiglio());
				log.debug("elemento.getIdTipoDocPadre() == cnmRTipoDocPadreFiglio.getIdTipoDocPadre(): " + (elemento.getTipoDocPadre().getIdTipoDocumento() == cnmRTipoDocPadreFiglio.getTipoDocPadre().getIdTipoDocumento()));
				log.debug("elemento.getIdTipoDocPadre() == cnmRTipoDocPadreFiglio.getIdTipoDocPadre(): " + (elemento.getTipoDocFiglio().getIdTipoDocumento() == cnmRTipoDocPadreFiglio.getTipoDocFiglio().getIdTipoDocumento()));
				//log.debug("elemento.getIdTipoDocPadre() == cnmRTipoDocPadreFiglioDto.getIdTipoDocPadre(): " + (elemento.getIdTipoDocPadre().intValue() == cnmRTipoDocPadreFiglioDto.getIdTipoDocPadre().intValue()));
				if(elemento.getTipoDocPadre().getIdTipoDocumento() == cnmRTipoDocPadreFiglio.getTipoDocPadre().getIdTipoDocumento() && elemento.getTipoDocFiglio().getIdTipoDocumento() == cnmRTipoDocPadreFiglio.getTipoDocFiglio().getIdTipoDocumento()){
					esito = true;
					log.debug(method + ". esito: " + esito);
					break;
				}
			}
			
			log.debug(method + ". esito: " + esito);
			
			if(cnmRTipoDocPadreFiglioList!= null && !esito){
				throw new AggiungiAllegatoException("Tipo documento padre idTipoDocumento: " + cnmDTipoDocumentoPadre.getIdTipoDocumento() + " e tipo documento figlio idTipoDocumento: " 
			+ cnmDTipoDocumentoAllegato.getIdTipoDocumento() + " incompatibili.");
			}
			
			//recupero documento allegato
			CnmTDocumento cnmTDocumentoAllegato = getCnmTDocumentoRepository().findOne(Integer.parseInt(idArchivioAllegato));
			

			//recupero documento allegato 20200703_LC
			CnmTDocumento cnmTDocumentoMaster = getCnmTDocumentoRepository().findOne(Integer.parseInt(idArchivioPadre));

			if(cnmTDocumentoAllegato == null)  
				throw new AggiungiAllegatoException("Documento allegato con id archivio" + idArchivioAllegato + " non presente in archivio di stadoc");
			
			//recupero documento padre
			
			CnmTDocumento cnmTDocumentoPadre = getCnmTDocumentoRepository().findOne(Integer.parseInt(idArchivioPadre));
			if(cnmTDocumentoPadre == null)  
				throw new AggiungiAllegatoException("Documento padre con id archivio" + idArchivioPadre + " non presente in archivio di stadoc");
			
			if(cnmTDocumentoPadre.getObjectidclassificazione() == null) {  
				log.error(method + ". Attributo classificazione Id documento master non valorizzato: id documento master " + cnmTDocumentoPadre.getIdDocumento());
				throw new AggiungiAllegatoException("Objectidclassificazione documento master non valorizzata ");
			}
			// recupero fruitore allegato
			CnmTFruitore cnmTFruitore = getFruitore();
			
			//recupero tipo fornitore 
			CnmDTipoFornitore cnmDTipoFornitore = getCnmDTipoFornitoreRepository().findOne(DoquiConstants.FORNITORE_ACTA);
			
			//QUALE SERVIZIO CI VUOLE PER CREARE LA NUOVA RICHIESTA?? RIUTILIZZIAMO QUALCOSA CHE C'E' GIA' O NE INSERIAMO UNO AD HOC
			// RICHIESTA 
			
			CnmTFruitore cnmTFruitoreInInput = getFruitore();
			
			CnmTRichiesta cnmTRichiesta = createCnmTRichiesta(cnmTFruitoreInInput.getIdFruitore(), DoquiConstants.SERVIZIO_AGGIUNGI_ALLEGATO_ACTA);
			
			
			
			// DOCUMENTO DA ALLEGARE SU ACTA
			
			// 20200711_LC 
			CnmTUser cnmTUser = null;
			if (SecurityUtils.getAuthentication()==null) {
				cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
			} else {
				UserDetails user = SecurityUtils.getUser();
				cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
			}
			
			CnmTDocumento cnmTDocumento = new CnmTDocumento();
//			cnmTDocumento.setIdDocumento(getDocTDocumentoDaoIncrementer().nextLongValue());
				
			//cnmTDocumento.setFolder(cnmTDocumentoAllegato.getFolder());	
			cnmTDocumento.setFolder(cnmTDocumentoMaster.getFolder());		// folder del master per tutti gli allegati	
			
			
			// 20200709_LC
			// tipo documento nuovo (il vecchio era 22 poichè su Index)
			CnmDTipoDocumento cnmDTipoDocumentoAllegatoNew = getTipoDocumento(request.getTipoDocumento());
			cnmTDocumento.setCnmDTipoDocumento(cnmDTipoDocumentoAllegatoNew);	// 20200630_LC

			cnmTDocumento.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));	// 20200630_LC
			cnmTDocumento.setCnmTUser2(cnmTUser);		// 20200702_LC 
			
			// I METADATI SEGUENTI LI PRENDIAMO DALLA REQUEST POPOLATA DAL BATCH
//			cnmTDocumento.setCodiceFiscale(request.getMetadatiAllegato().getCodiceFiscale());
			cnmTDocumento.setIdentificativoEntitaFruitore(request.getMetadatiAllegato().getIdEntitaFruitore());
//			cnmTDocumento.setTarga(request.getMetadatiAllegato().getTarga());
			
			// 20200701_LC save anticipato
			
			cnmTDocumento = getCnmTDocumentoRepository().save(cnmTDocumento);
			cnmTDocumento.setIdentificativoArchiviazione(componiParolaChiave(String.valueOf(cnmTDocumento.getIdDocumento())));	// 20200713_LC
			CnmTDocumento cnmTDocumentoN = getCnmTDocumentoRepository().save(cnmTDocumento);
			cnmTRichiestaN = getStatoRichiestaService().insertRichiesta(cnmTRichiesta);
			
			
			//RICHIESTA DOCUMENTO

			// 20200610_LC
			CnmRRichiestaDocumentoPK cnmRRichiestaDocumentoPK = new CnmRRichiestaDocumentoPK();
			cnmRRichiestaDocumentoPK.setIdDocumento(cnmTDocumento.getIdDocumento());
			cnmRRichiestaDocumentoPK.setIdRichiesta(cnmTRichiesta.getIdRichiesta());
			
			CnmRRichiestaDocumento cnmRRichiestaDocumentoN = new CnmRRichiestaDocumento();
			cnmRRichiestaDocumentoN.setId(cnmRRichiestaDocumentoPK);
			cnmRRichiestaDocumentoN.setCnmTDocumento(cnmTDocumento);
			cnmRRichiestaDocumentoN.setCnmTRichiesta(cnmTRichiesta);


			
			// 20200731_LC nuovo metadato collocazioneCartacea
			request.setCollocazioneCartacea(cnmDTipoDocumentoAllegatoNew.getCollocazioneCartacea());
			
			
			
			//POJO
			UtenteActa utenteActa = new UtenteActa();
			utenteActa.setCodiceFiscale(cnmTFruitore.getCfActa());
			utenteActa.setIdAoo(new Integer(cnmTFruitore.getIdAooActa()));
			utenteActa.setIdNodo(new Integer(cnmTFruitore.getIdNodoActa()));
			utenteActa.setIdStruttura(new Integer(cnmTFruitore.getIdStrutturaActa()));
			utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
			utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());
			utenteActa.setDescEnte(cnmDTipoFornitore.getCodEnte());
			
			if(StringUtils.isNotEmpty(rootActa))
				utenteActa.setRootActa(rootActa);
			else
				utenteActa.setRootActa(cnmDTipoDocumento.getRoot());
			
			utenteActa.setIdvitalrecordcodetype(cnmDTipoDocumento.getIdVitalRecordCodeType());
			utenteActa.setIdStatoDiEfficacia(cnmDTipoDocumento.getIdStatoEfficacia());
			utenteActa.setDescFormaDocumentaria(cnmDTipoDocumento.getDescFormadocUscita());
			
			//POJO
			DocumentoElettronicoActa documentoElettronicoActa = new DocumentoElettronicoActa();
			documentoElettronicoActa.setIdDocumento(cnmTDocumento.getIdentificativoArchiviazione());
			documentoElettronicoActa.setFolder(cnmTDocumento.getFolder());
			documentoElettronicoActa.setDocumentoCartaceo(cnmDTipoDocumento.getCartaceo());
			
			// 20200731_LC (già presente)
			documentoElettronicoActa.setCollocazioneCartacea(request.getCollocazioneCartacea());
			
			if(StringUtils.isNotEmpty(soggettoActaFruitore))
				documentoElettronicoActa.setFruitore(soggettoActaFruitore);
			else
				documentoElettronicoActa.setFruitore(cnmTFruitore.getDescrFruitore());
			

			
			documentoElettronicoActa.setStream(request.getDocumento().getFile());
			documentoElettronicoActa.setNomeFile(request.getDocumento().getNomeFile());
			
			documentoElettronicoActa.setDescrizione(cnmDTipoDocumento.getDescrTipoDocumento());
			documentoElettronicoActa.setMimeType(request.getMimeType());
						
			
			MetadatiActa metadatiActa = new MetadatiActa();
			metadatiActa.setCodiceFiscale(request.getMetadatiAllegato().getCodiceFiscale());
			metadatiActa.setIdEntitaFruitore(request.getMetadatiAllegato().getIdEntitaFruitore());
			metadatiActa.setTarga(request.getMetadatiAllegato().getTarga());
			documentoElettronicoActa.setMetadatiActa(metadatiActa);
			
			// SOGGETTO ACTA
			if(request.getSoggetto() != null){
				SoggettoActa soggettoActa = new SoggettoActa();
				if(request.getSoggetto().TOPOLOGIA_SOGGETTO_DESTINATARIO.equals(request.getSoggetto().getTipologia()))
					soggettoActa.setMittente(false);
				else
					soggettoActa.setMittente(true);
				soggettoActa.setCognome(request.getSoggetto().getCognome());
				soggettoActa.setNome(request.getSoggetto().getNome());
				if(request.getSoggetto().getDenominazione() != null)
					soggettoActa.setDenominazione(request.getSoggetto().getDenominazione());
				
				documentoElettronicoActa.setSoggettoActa(soggettoActa);
			}
			else{
				SoggettoActa soggettoActa = new SoggettoActa();
				
				soggettoActa.setMittente(true);
				soggettoActa.setCognome(request.getMetadatiAllegato().getCognome());
				soggettoActa.setNome(request.getMetadatiAllegato().getNome());
				if (request.getMetadatiAllegato().getDenominazione() != null)
					soggettoActa.setDenominazione(request.getMetadatiAllegato().getDenominazione());
				
				documentoElettronicoActa.setSoggettoActa(soggettoActa);
			}
			
			if(request.getAutoreGiuridico() != null)
				documentoElettronicoActa.setAutoreGiuridico(request.getAutoreGiuridico());
			if(request.getAutoreFisico() != null)
				documentoElettronicoActa.setAutoreFisico(request.getAutoreFisico());
			if(request.getScrittore() != null)
				documentoElettronicoActa.setScrittore(request.getScrittore());
			if(request.getOriginatore() != null)
				documentoElettronicoActa.setOriginatore(request.getOriginatore());
			if(request.getDestinatarioGiuridico() != null)
				documentoElettronicoActa.setDestinatarioGiuridico(request.getDestinatarioGiuridico());
			if(request.getDestinatarioFisicoDenom() != null)
				documentoElettronicoActa.setDestinatarioFisico(request.getDestinatarioFisicoDenom());
			if(request.getDestinatarioFisicoCF() != null)
				documentoElettronicoActa.setCodiceFiscaleDestinatarioFisico(request.getDestinatarioFisicoCF());
	        if(request.getSoggettoProduttoreDenom() != null)
				documentoElettronicoActa.setSoggettoProduttore(request.getSoggettoProduttoreDenom());
	        if(request.getSoggettoProduttoreCF() != null)
				documentoElettronicoActa.setCodiceSoggettoProduttore(request.getSoggettoProduttoreCF());			
			if(request.getApplicativoAlimentante() != null)
				documentoElettronicoActa.setApplicativoAlimentante(request.getApplicativoAlimentante());
			else
				documentoElettronicoActa.setApplicativoAlimentante(cnmTFruitore.getDescrFruitore());
	        documentoElettronicoActa.setMittentiEsterni(request.getMittentiEsterni());
	        
	        
	        // 20210415_LC
	        if (request.getDataCronica() != null) documentoElettronicoActa.setDataCronica(request.getDataCronica());
	        if (request.getDataTopica() != null) documentoElettronicoActa.setDataTopica(request.getDataTopica());
	        
	        
	        
	        
			if(log.isDebugEnabled())
			{
				log.debug(method + ". UtenteActa =\n " + XmlSerializer.objectToXml(utenteActa));
				log.debug(method + ". DocumentoActa =\n " + XmlSerializer.objectToXml(documentoElettronicoActa));
			}
			
			// 20201006_LC save anticipato
			// CnmTDocumento cnmTDocumentoN = getCnmTDocumentoRepository().save(cnmTDocumento);
			// cnmTRichiestaN = getStatoRichiestaService().insertRichiesta(cnmTRichiesta);
			
			CnmRRichiestaDocumento cnmRRichiestaDocumento = getCnmRRichiestaDocumentoRepository().save(cnmRRichiestaDocumentoN);
			
			if(log.isDebugEnabled()){
				log.debug(method + ". inserito documento da allegare su acta        = " + cnmTDocumentoN.getIdDocumento());
				log.debug(method + ". inserita richiesta           = " + cnmTRichiestaN.getIdRichiesta());
				log.debug(method + ". inserita richiesta documento = " + cnmRRichiestaDocumento);
				
			}
			
			// 20200701_LC
			//String idDocumento = actaManagementService.aggiungiAllegato(documentoElettronicoActa, utenteActa, cnmTDocumentoPadre.getObjectidclassificazione(), cnmTDocumentoPadre.getProtocolloFornitore(), request.getPkAllegato());
			//log.debug(method + ". idDocumento =\n " + idDocumento);
			KeyDocumentoActa keyDocumentoActa = actaManagementService.aggiungiAllegato(documentoElettronicoActa, utenteActa, cnmTDocumentoPadre.getObjectidclassificazione(), cnmTDocumentoPadre.getProtocolloFornitore(), request.getPkAllegato());
			
			if(log.isDebugEnabled()){
				log.debug(method + ". KeyDocumentoActa =\n " + XmlSerializer.objectToXml(keyDocumentoActa));
			}
			
			
			if (keyDocumentoActa != null)
			{
				// 20200701_LC aggiorno i valori come accade in archivia e protocolla documento fisico
				// CAPIRE BENE COSA SUCCEDE LATO DB
//				changeRiferimentiFornitoreDocumentoConKeyActa(cnmTDocumento.getIdDocumento(), keyDocumentoActa.getUUIDDocumento(), keyDocumentoActa.getNumeroProtocollo(),
//															  keyDocumentoActa.getClassificazioneId(), keyDocumentoActa.getRegistrazioneId(),  keyDocumentoActa.getObjectIdClassificazione(), keyDocumentoActa.getObjectIdDocumento());
//				
				changeRiferimentiFornitoreDocumentoConKeyActa(cnmTDocumento.getIdDocumento(), keyDocumentoActa.getUUIDDocumento(), keyDocumentoActa.getNumeroProtocollo(),
															  keyDocumentoActa.getClassificazioneId(), keyDocumentoActa.getRegistrazioneId(),  keyDocumentoActa.getObjectIdClassificazione(), keyDocumentoActa.getObjectIdDocumento());
				
				getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_EVASA);
			
				//response.setIdDocumento(cnmTDocumentoN.getIdDocumento()+"");
				response.setIdDocumento(String.valueOf(cnmTDocumentoN.getIdDocumento()));	// 20200713_LC
				response.setObjectIdDocumento(String.valueOf(cnmTDocumentoN.getObjectiddocumento()));	// 20201123_LC
			}
			else 
				containsError = true;
			
			
			return response;
			
		}
		catch (Exception e) 
		{
			containsError = true;
			log.error(method + ". " + e);
			throw new AggiungiAllegatoException("Eccezione durante servizio aggiungi allegato",e);
		}
		finally
		{
			log.info(method + ". END");
		}
	}
	
	/*
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)	
	public ResponseAggiungiAllegato aggiungiAllegatoConam(RequestAggiungiAllegatoConam request)
			throws AggiungiAllegatoException {
		
		RequestAggiungiAllegato requestAggiungiAllegato=new RequestAggiungiAllegato();
		requestAggiungiAllegato.setCodiceFruitore(request.getCodiceFruitore());
		requestAggiungiAllegato.setDocumento(request.getDocumento());
		requestAggiungiAllegato.setIdArchivioAllegato(request.getIdArchivioAllegato());
		requestAggiungiAllegato.setIdArchivioPadre(request.getIdArchivioPadre());
		requestAggiungiAllegato.setMetadatiAllegato(request.getMetadatiAllegato());
		requestAggiungiAllegato.setMimeType(request.getMimeType());
		requestAggiungiAllegato.setPkAllegato(request.getPkAllegato());
		
		return aggiungiAllegato(requestAggiungiAllegato, request.getRootActa(),request.getSoggettoActa());
	}
	*/
}
