/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.helper.impl;

import it.csi.conam.conambl.business.service.util.UtilsCnmCProprietaService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.beans.Metadati;
import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.doqui.bean.IndexManagementPojo;
import it.csi.conam.conambl.integration.doqui.common.StatoRichiestaService;
import it.csi.conam.conambl.integration.doqui.entity.*;
import it.csi.conam.conambl.integration.doqui.exception.FruitoreException;
import it.csi.conam.conambl.integration.doqui.helper.CommonManageDocumentoHelper;
import it.csi.conam.conambl.integration.doqui.repositories.*;
import it.csi.conam.conambl.integration.doqui.service.IndexManagementService;
import it.csi.conam.conambl.integration.doqui.utils.XmlSerializer;
import it.csi.conam.conambl.integration.entity.CnmCProprieta.PropKey;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.security.UserDetails;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;


public class CommonManageDocumentoHelperImpl implements CommonManageDocumentoHelper {

	
	
//	protected static final String LOGGER_PREFIX = DoquiConstants.LOGGER_PREFIX + ".helper";	
//	protected static final Logger log = Logger.getLogger(LOGGER_PREFIX);
	private static Logger log = Logger.getLogger(CommonManageDocumentoHelperImpl.class);
	
	@Autowired
	private IndexManagementService indexManagementService;
	
	// REPOSITORY
	@Autowired
	private CnmDTipoDocumentoRepository 		cnmDTipoDocumentoRepository;
	@Autowired
	private CnmDTipoFornitoreRepository 		cnmDTipoFornitoreRepository;
	@Autowired
	private CnmTDocumentoRepository    			cnmTDocumentoRepository;
	@Autowired
	private CnmRRichiestaDocumentoRepository	cnmRRichiestaDocumentoRepository;
	@Autowired
	private CnmRTipoDocPadreFiglioRepository 	cnmRTipoDocPadreFiglioRepository;

	// 20200630_LC
	// 20200630_LC
	@Autowired
	private CnmDStatoRichiestaRepository cnmDStatoRichiestaRepository;
	// 20200630_LC
	@Autowired
	private CnmDServizioRepository cnmDServizioRepository;

	// 20200702_LC
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
		
	@Autowired
	private StatoRichiestaService statoRichiestaService;
	
	@Autowired
	private UtilsCnmCProprietaService utilsCnmCProprietaService;

	// 20200630_LC
	@Autowired
	private UtilsDate utilsDate;


	
	public IndexManagementService getIndexManagementService() {
		return indexManagementService;
	}
	public void setIndexManagementService(
			IndexManagementService indexManagementService) {
		this.indexManagementService = indexManagementService;
	}
	
	public StatoRichiestaService getStatoRichiestaService() {
		return statoRichiestaService;
	}
	public void setStatoRichiestaService(StatoRichiestaService statoRichiestaService) {
		this.statoRichiestaService = statoRichiestaService;
	}
	
	
	protected boolean isAsciiPrintable(String text){
		String method = "isAsciiPrintable";
		if(log.isDebugEnabled()){
			log.debug(method + ". text = " + text);
		}
		return StringUtils.isAsciiPrintable(text);
	}
	
	

	/*
	 * (non-Javadoc)
	 * @see it.csi.stacore.stadoc.business.stadoc.helper.CommonManageDocumentoHelper#getFruitore()
	 */
	
	CnmTFruitore cnmTFruitore = null;
	public CnmTFruitore getFruitore() throws FruitoreException {
		String method = "getFruitore";
		
		if(cnmTFruitore != null) {
			if(log.isDebugEnabled()){
				log.debug(method + ". CnmTFruitore \n " + XmlSerializer.objectToXml(cnmTFruitore));
			}
			return cnmTFruitore;
		}
		
		try
		{	
			String idAooActa = utilsCnmCProprietaService.getProprieta(PropKey.ACTA_ID_AOO);	
			String idStrutturaActa = utilsCnmCProprietaService.getProprieta(PropKey.ACTA_ID_STRUTTURA);	
			String idNodoActa = utilsCnmCProprietaService.getProprieta(PropKey.ACTA_ID_NODO);	
//			String idAooActa = "231";	// (_OLD_)
//			String idStrutturaActa = "712";	// (_OLD_)
//			String idNodoActa = "727";	// (_OLD_)
			String codFruitore = utilsCnmCProprietaService.getProprieta(PropKey.ACTA_CODE_FRUITORE);
			String cfActa = utilsCnmCProprietaService.getProprieta(PropKey.ACTA_CF);
			String repositoryIndex = utilsCnmCProprietaService.getProprieta(PropKey.INDEX_REPOSITORY);
			String passwordIndex = utilsCnmCProprietaService.getProprieta(PropKey.INDEX_PASSWORD);
			String usernameIndex = utilsCnmCProprietaService.getProprieta(PropKey.INDEX_USERNAME);
			String fruitoreIndex = utilsCnmCProprietaService.getProprieta(PropKey.INDEX_FRUITORE);	// 20200629_LC
			String customModelIndex = utilsCnmCProprietaService.getProprieta(PropKey.INDEX_CUSTOM_MODEL);	// 20200629_LC	
			String descrFruitore = codFruitore;
			
			cnmTFruitore = new CnmTFruitore(1, codFruitore, descrFruitore, cfActa, idAooActa, idStrutturaActa, idNodoActa, repositoryIndex, usernameIndex, passwordIndex, fruitoreIndex, customModelIndex);
			if(log.isDebugEnabled()){
				log.debug(method + ". CnmTFruitore \n " + XmlSerializer.objectToXml(cnmTFruitore));
			}
			
		}
		catch (Exception e)
		{
			log.error(method + ". Accesso al DAO Fallito " + e);
			throw new FruitoreException(e.getMessage());
		}

		return cnmTFruitore;
	
	}
	
	public CnmDTipoDocumento getTipoDocumento(String codice) throws FruitoreException {
		String method = "getTipoDocumento";
		CnmDTipoDocumento dto = null;
		try
		{
			
			List<CnmDTipoDocumento> l = cnmDTipoDocumentoRepository.findByCodTipoDocumento(codice);
//			List<DocDTipoDocumentoDto> l = docDTipoDocumentoDao.findByCodice(codice);
			if(log.isDebugEnabled()){
				log.debug(method + ". CnmDTipoDocumento \n " + XmlSerializer.objectToXml(l));
			}
			
			if (l.size() == 0) 
				throw new FruitoreException("Tipo documento non censito in anagrafica");
			else if(l.size() == 1) {
				dto = l.get(0);
			}
			else {
				throw new FruitoreException("Esistono "  + l.size() + " con lo stesso codice");
			}
		}
		catch (Exception e)
		{
			log.error(method + ". Accesso al DAO Fallito " + e);
			throw new FruitoreException(e.getMessage());
		}

		return dto;
	
	}
	public CnmDTipoDocumento getTipoDocumentoByIdDocumento(String idDocumento) throws FruitoreException {
		String method = "getTipoDocumento";
		CnmDTipoDocumento dto = null;
		try
		{
			List<CnmDTipoDocumento> l = cnmDTipoDocumentoRepository.findByIdDocumento(Integer.parseInt(idDocumento));	// 20200713_LC
//			List<DocDTipoDocumentoDto> l = docDTipoDocumentoDao.findByIdArchiviazione(idArchiviazione);
			if(log.isDebugEnabled()){
				log.debug(method + ". CnmDTipoDocumento \n " + XmlSerializer.objectToXml(l));
			}
			
			if (l.size() == 0) 
				throw new FruitoreException("Tipo documento non censito in anagrafica");
			else if(l.size() == 1) {
				dto = l.get(0);
			}
			else {
				throw new FruitoreException("Esistono "  + l.size() + " con lo stesso codice");
			}
		}
		catch (Exception e)
		{
			log.error(method + ". Accesso al DAO Fallito " + e);
			throw new FruitoreException(e.getMessage());
		}

		return dto;
	
	}
	
	
	
	public void changeRiferimentiFornitoreDocumento(Integer idDocumento, String uid, String protocollo ) throws Exception {
		String method = "changeRiferimentiFornitoreDocumento";
		if(log.isDebugEnabled()){
			log.debug(method + ". idDocumento  = " + idDocumento);
			log.debug(method + ". uid          = " + uid);
			log.debug(method + ". protocollo   = " + protocollo);
		}
		
		// 20200711_LC 
		CnmTUser cnmTUser = null;
		if (SecurityUtils.getAuthentication()==null) {
			cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
		} else {
			UserDetails user = SecurityUtils.getUser();
			cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
		}
		
		try {
			
			CnmTDocumento dto = cnmTDocumentoRepository.findOne(idDocumento);
			dto.setIdentificativoFornitore(uid);
			dto.setProtocolloFornitore(protocollo);
			dto.setCnmTUser1(cnmTUser);		// 20200702_LC
			
			cnmTDocumentoRepository.save(dto);
			
			log.debug(method + ". riferimenti fornitore cambiati correttamente");
		}
		catch (Exception e) {
			log.error(method + ". Accesso al DAO Fallito " + e);
			throw new Exception(e.getMessage());
		}	
	}
	
	public void changeRiferimentiFornitoreDocumentoConKeyActa(Integer idDocumento, String uid, String protocollo , String classificazioneId, String registrazioneId, String objectIdClassificazione, String objectIdDocumento) throws Exception {
		String method = "changeRiferimentiFornitoreDocumentoConKeyActa";
		if(log.isDebugEnabled()){
			log.debug(method + ". idDocumento  			  = " + idDocumento);
			log.debug(method + ". uid          			  = " + uid);
			log.debug(method + ". protocollo   			  = " + protocollo);
			log.debug(method + ". classificazioneId       = " + classificazioneId);
			log.debug(method + ". registrazioneId         = " + registrazioneId);
			log.debug(method + ". objectIdClassificazione = " + objectIdClassificazione);
			log.debug(method + ". registrazioneId         = " + objectIdDocumento);
		}
		
		// 20200711_LC 
		CnmTUser cnmTUser = null;
		if (SecurityUtils.getAuthentication()==null) {
			cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
		} else {
			UserDetails user = SecurityUtils.getUser();
			cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
		}
		
		
		try {
			
			CnmTDocumento dto = cnmTDocumentoRepository.findOne(idDocumento);
			dto.setIdentificativoFornitore(uid);
			dto.setProtocolloFornitore(protocollo);
			dto.setClassificazioneId(classificazioneId);
			dto.setRegistrazioneId(registrazioneId);
			dto.setObjectidclassificazione(objectIdClassificazione);
			dto.setObjectiddocumento(objectIdDocumento);
			dto.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));		// 20200630_LC
			
			//TODO RAFFAELLA MODELLARE IL DTO INSERENDO I DUE NUOVI CAMPI: objectIdClassificazione e objectIdDocumento creati da simo sulla tabella doc_t_documento
			
			

			dto.setCnmTUser1(cnmTUser);		// 20200702_LC  
			
			cnmTDocumentoRepository.save(dto);
			
			log.debug(method + ". riferimenti fornitore cambiati correttamente");
		}
		catch (Exception e) {
			log.error(method + ". Accesso al DAO Fallito " + e);
			throw new Exception(e.getMessage());
		}	
	}
	public CnmTDocumento getRiferimentiFornitoreDocumento(Integer idDocumento) throws FruitoreException 
	{
		String method = "getRiferimentiFornitoreDocumento";
		if(log.isDebugEnabled())
			log.debug(method + ". idDocumento = " + idDocumento);
		
		CnmTDocumento cnmTDocumento = null;
		
		try
		{
		
			cnmTDocumento = cnmTDocumentoRepository.findOne(idDocumento);
					
			if(log.isDebugEnabled())
				log.debug(method + ". cnmTDocumento \n " + XmlSerializer.objectToXml(cnmTDocumento));
			
			if (cnmTDocumento == null) 
				throw new FruitoreException("Richiesta non censita in anagrafica ");
		
		}
		catch (Exception e)
		{
			log.error(method + ". Accesso al DAO Fallito " + e);
			throw new FruitoreException(e.getMessage());
		}

		return cnmTDocumento;
	
	}
	
	/**
	 * 
	 * @param idFruitore
	 * @param idServizio
	 * @return
	 */
	public CnmTRichiesta createCnmTRichiesta(long idFruitore, long idServizio){
		CnmTRichiesta cnmTRichiesta = new CnmTRichiesta();
//		cnmTRichiestaDto.setIdRichiesta(getDocTRichiestaDaoIncrementer().nextLongValue());
		cnmTRichiesta.setIdFruitore(idFruitore);
		
		// 20200711_LC 
		CnmTUser cnmTUser = null;
		if (SecurityUtils.getAuthentication()==null) {
			cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
		} else {
			UserDetails user = SecurityUtils.getUser();
			cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
		}
		
		// 20200630_LC
		CnmDStatoRichiesta statoRichiesta = cnmDStatoRichiestaRepository.findOne(DoquiConstants.STATO_RICHIESTA_INVIATA);
		CnmDServizio servizio = cnmDServizioRepository.findOne(idServizio);		
		//cnmTRichiesta.setIdServizio(idServizio);
		//cnmTRichiesta.setIdStatoRichiesta(STATO_RICHIESTA_INVIATA);
		cnmTRichiesta.setCnmDServizio(servizio);
		cnmTRichiesta.setCnmDStatoRichiesta(statoRichiesta);
		
		// 20200630_LC
		//cnmTRichiesta.setDataInsRichiesta(DateFormat.getCurrentSqlDate());
		//cnmTRichiesta.setDataUpdRichiesta(DateFormat.getCurrentSqlDate());
		cnmTRichiesta.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
		cnmTRichiesta.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));
		cnmTRichiesta.setCnmTUser2(cnmTUser);		// 20200702_LC user inserimento 
		
		return cnmTRichiesta;
	}
	
	public IndexManagementPojo createIndexManagement(String folder, byte[] file, String nomeFile, Metadati metadati,
			CnmTDocumento cnmTDocumento, CnmTFruitore cnmTFruitore, CnmDTipoDocumento cnmDTipoDocumento, String indexType){
		
		IndexManagementPojo imp = new IndexManagementPojo();
		
		// 20200615_LC
		imp.setIdentificativoFornitore(cnmTDocumento.getIdentificativoFornitore());
		
		// 20200629_LC
		imp.setCustomModel(cnmTFruitore.getCustomModelIndex());
		
		imp.setFolder(folder);
		// imp.setFruitore(codiceFruitore);
		imp.setFruitore(cnmTFruitore.getFruitoreIndex());	// 20200629_LC
		imp.setIdDocumento(cnmTDocumento.getIdentificativoArchiviazione());
		
		imp.setUsr(cnmTFruitore.getUsernameIndex());
		imp.setPsw(cnmTFruitore.getPasswordIndex());
		imp.setRepostory(cnmTFruitore.getRepositoryIndex());
		
		if(cnmDTipoDocumento  != null){
			imp.setDocumentRoot(cnmDTipoDocumento.getRoot());
			imp.setTipoDocumento(cnmDTipoDocumento.getDescrTipoDocumento());
		}
		
		imp.setUtenteApplicativo(cnmTFruitore.getDescrFruitore());
		imp.setFile(file);
		imp.setNomeFile(nomeFile);
		
		// 20200714_LC
		if (indexType!=null) {
		imp.setIndexType(indexType);
		}
		
		if(metadati != null) {
//			imp.setCodiceFiscale(metadati.getCodiceFiscale());
			imp.setIdEntitaFruitore(metadati.getIdEntitaFruitore());
//			imp.setTarga(metadati.getTarga());
		}
		return imp;
	}
	public CnmDTipoDocumentoRepository getCnmDTipoDocumentoRepository() {
		return cnmDTipoDocumentoRepository;
	}
	public CnmDTipoFornitoreRepository getCnmDTipoFornitoreRepository() {
		return cnmDTipoFornitoreRepository;
	}
	public CnmTDocumentoRepository getCnmTDocumentoRepository() {
		return cnmTDocumentoRepository;
	}
	public CnmRRichiestaDocumentoRepository getCnmRRichiestaDocumentoRepository() {
		return cnmRRichiestaDocumentoRepository;
	}
	public CnmRTipoDocPadreFiglioRepository getCnmRTipoDocPadreFiglioRepository() {
		return cnmRTipoDocPadreFiglioRepository;
	}
	
	// 20200708_LC
	public void changeRiferimentiFornitoreDocumentoPostSpostamento(Integer idDocumento, String uid, String protocollo , String classificazioneId, String registrazioneId, String objectIdClassificazione, String objectIdDocumento, String parolaChiave) throws Exception {
		String method = "changeRiferimentiFornitoreDocumentoConKeyActa";
		if(log.isDebugEnabled()){
			log.debug(method + ". idDocumento  			  = " + idDocumento);
			log.debug(method + ". uid          			  = " + uid);
			log.debug(method + ". protocollo   			  = " + protocollo);
			log.debug(method + ". classificazioneId       = " + classificazioneId);
			log.debug(method + ". registrazioneId         = " + registrazioneId);
			log.debug(method + ". objectIdClassificazione = " + objectIdClassificazione);
			log.debug(method + ". registrazioneId         = " + objectIdDocumento);
		}
		
		// 20200711_LC 
		CnmTUser cnmTUser = null;
		if (SecurityUtils.getAuthentication()==null) {
			cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
		} else {
			UserDetails user = SecurityUtils.getUser();
			cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
		}
		
		
		try {
			
			CnmTDocumento dto = cnmTDocumentoRepository.findOne(idDocumento);
			
			if (uid!=null)
			dto.setIdentificativoFornitore(uid);
			
			if (protocollo!=null)
			dto.setProtocolloFornitore(protocollo);
			
			if (classificazioneId!=null)
			dto.setClassificazioneId(classificazioneId);
			
			if (registrazioneId!=null)
			dto.setRegistrazioneId(registrazioneId);
			
			if (objectIdClassificazione!=null)
			dto.setObjectidclassificazione(objectIdClassificazione);
			
			if (objectIdDocumento!=null)
			dto.setObjectiddocumento(objectIdDocumento);
			
			dto.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));		// 20200630_LC
				
			// 20200804_LC (solo per copia)
			if (parolaChiave!=null)
			dto.setIdentificativoArchiviazione(parolaChiave);				

			dto.setCnmTUser1(cnmTUser);		// 20200702_LC 
			
			cnmTDocumentoRepository.save(dto);
			
			log.debug(method + ". riferimenti fornitore cambiati correttamente");
		}
		catch (Exception e) {
			log.error(method + ". Accesso al DAO Fallito " + e);
			throw new Exception(e.getMessage());
		}	
	}

	
	// 20200713_LC

	public String componiParolaChiave(String idDocumentoConam) {
		return DoquiConstants.PREFIX_PAROLA_CHIAVE+idDocumentoConam;
	}
	

}
