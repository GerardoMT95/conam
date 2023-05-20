/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.facade.impl;

import it.csi.conam.conambl.business.facade.DoquiServiceFacade;
import it.csi.conam.conambl.business.service.util.UtilsCnmCProprietaService;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.exception.RemoteWebServiceException;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.beans.*;
import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.doqui.exception.AggiungiAllegatoException;
import it.csi.conam.conambl.integration.doqui.exception.ArchiviaDocumentoException;
import it.csi.conam.conambl.integration.doqui.exception.EliminaDocumentoException;
import it.csi.conam.conambl.integration.doqui.exception.ProtocollaDocumentoException;
import it.csi.conam.conambl.integration.doqui.exception.RicercaAllegatoException;
import it.csi.conam.conambl.integration.doqui.exception.RicercaDocumentoException;
import it.csi.conam.conambl.integration.doqui.exception.SalvaDocumentoException;
import it.csi.conam.conambl.integration.doqui.exception.*;
import it.csi.conam.conambl.integration.doqui.helper.*;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.entity.CnmCProprieta.PropKey;
import it.csi.conam.conambl.integration.repositories.CnmCParametroRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.DocumentUtils;
import it.csi.conam.conambl.vo.verbale.DocumentoProtocollatoVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DoquiServiceFacadeImpl implements DoquiServiceFacade, InitializingBean {

	/*@Autowired
	private Config config;*/
	@Autowired
	private UtilsDoqui utilsDoqui;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CnmCParametroRepository cnmCParametroRepository;
	@Autowired
	private UtilsCnmCProprietaService utilsCnmCProprietaService;
	
	private static Logger logger = Logger.getLogger(DoquiServiceFacadeImpl.class);

	@Autowired
	private ManageArchiviaDocumentoHelper manageArchiviaDocumentoHelper;

	@Autowired
	private ManageProtocollaDocumentoHelper manageProtocollaDocumentoHelper;

	@Autowired
	private ManageRicercaDocumentoHelper manageRicercaDocumentoHelper;

	@Autowired
	private ManageDocumentoHelper manageDocumentoHelper;

	@Autowired
	private ManageAggiungiAllegatoHelper manageAggiungiAllegatoHelper;

	// 20200706_LC
	@Autowired
	private ManageSpostaDocumentoHelper manageSpostaDocumentoHelper;
		
//	private StadocStadocSoapBindingStub binding;

	public static final String TOPOLOGIA_SOGGETTO_MITTENTE = "MITTENTE";
	public static final String TOPOLOGIA_SOGGETTO_DESTINATARIO = "DESTINATARIO";
	public static final String TOPOLOGIA_SOGGETTO_TRASGRESSORE = "TRASGRESSORE";
	public static final String TOPOLOGIA_SOGGETTO_CANCELLERIA = "CANCELLERIA";
	public static final String TOPOLOGIA_SOGGETTO_GIUDICE = "GIUDICE";
	
	// 20200709_LC  gestiti in costanti
//	private static final String TIPO_DOCUMENTO_INDEX = "CONAM_DOC";
//	private static final String TIPO_DOCUMENTO_ACTA_FIGLIO = "CONAM_DOC";
//	private static final String TIPO_DOCUMENTO_ACTA = "CONAM_ACTA";
//	private static final String TIPO_DOCUMENTO_CONAM_1 = "CONAM_A1";
//	private static final String TIPO_DOCUMENTO_CONAM_2 = "CONAM_A2";
//	private static final String CODICE_FRUITORE = "CONAM";

	private static final Long ID_DIRIGENTE = new Long(15);
	private static final String AUTORE_REGIONE_PIEMONTE = "REGIONE PIEMONTE";

	private static final int MAX_NUMERO_ALLEGATI = 20;

	@Override
	public ResponseProtocollaDocumento protocollaDocumentoFisico(String folder, byte[] document, String nomeFile, String idEntitaFruitore, boolean isMaster, boolean isProtocollazioneInUscita,
			String soggettoActa, String rootActa, long tipoDocumento, String tipoDocActa, List<CnmTSoggetto> cnmTSoggettoList, String idIndex) {
		if (folder == null)
			throw new IllegalArgumentException("folder non valido");
		if (document == null && StringUtils.isBlank(idIndex))
			throw new IllegalArgumentException("document non valido");
		if (nomeFile == null)
			throw new IllegalArgumentException("nomeFile non valido");
		if (tipoDocumento == 0)
			throw new IllegalArgumentException("tipoDocumento non valido");

		logger.info("[protocollaDocumentoFisico] -> nomeFile :: " + nomeFile);

		// 20200708 PP - se doc .p7m, oppure se risulta firmato digitalmente 
		boolean isDocSigned = DocumentUtils.isDocumentSigned(document, nomeFile);
				
		String tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA;
		if (tipoDocumento == TipoAllegato.CONTRODEDUZIONE.getId() || tipoDocumento == TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId()
				|| tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId()	|| tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId()				
				|| tipoDocumento == TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId() || tipoDocumento == TipoAllegato.COMPARSA_ALLEGATO.getId() || tipoDocumento == TipoAllegato.ISTANZA_ALLEGATO.getId()) {
			tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_1_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_1;
		} else if (tipoDocumento == TipoAllegato.LETTERA_ORDINANZA.getId() || tipoDocumento == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
				|| tipoDocumento == TipoAllegato.LETTERA_RATEIZZAZIONE.getId() || tipoDocumento == TipoAllegato.LETTERA_SOLLECITO.getId()
				|| tipoDocumento == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()) {
			tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_2_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_2;
		}

		RequestProtocollaDocumentoFisico request = new RequestProtocollaDocumentoFisico();

		request.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);
		request.setTipoDocumento(tipoDoc);

		// fascicolo
		request.setFolder(folder);

		// **********SOGGETTO
		Soggetto soggetto = new Soggetto();
		// **********METADATI
		Metadati metadati = new Metadati();

		// protocollazione in ingresso o in uscita di un documento
		if (isProtocollazioneInUscita) {
			soggetto.setTipologia(TOPOLOGIA_SOGGETTO_DESTINATARIO);
		} else
			soggetto.setTipologia(TOPOLOGIA_SOGGETTO_MITTENTE);
		soggetto.setCognome("CONAM");
		soggetto.setNome("CONAM");
		request.setSoggetto(soggetto);
		String mineType = utilsDoqui.getMimeType(nomeFile);
		request.setMimeType(mineType);

		// dossier
		request.setRootActa(rootActa);
		// soggetto
		request.setSoggettoActa(soggettoActa);

		// metadati
		metadati.setIdEntitaFruitore(idEntitaFruitore);
		request.setMetadati(metadati);

		// **********DOCUMENTO
		Documento documento = new Documento();
		if (document != null) {
			documento.setNomeFile(nomeFile);
			documento.setFile(document);
		} else {
			documento.setIdDocumento(idIndex);
			documento.setNomeFile(nomeFile);			
		}
		
		//JIRA - gestione metadati
		//--------------------------------------------------------------------------
		//In caso di doc con allegati e' NECESSARIO l'inserimento della MAX_NUMERO
		//altrimentio il servizio acaris va in errore

		if (isMaster)
			documento.setNumeroAllegati(MAX_NUMERO_ALLEGATI);

		request.setDocumento(documento);

		// autore fisico
		request.setAutoreFisico("NULL_VALUE"); // si fa così per forzare a null

		// destinatario giuridico
		request.setDestinatarioGiuridico(AUTORE_REGIONE_PIEMONTE);

		// applicativo alimentante
		request.setApplicativoAlimentante("CONAM");

		// mittenti esterni
		request.setMittentiEsterni("ENTE ACCERTATORE");
		request.setProtocollazioneInUscitaSenzaDocumento(true);

		// 20200711_LC 
		CnmTUser cnmTUser;
		if (SecurityUtils.getAuthentication()==null) {
			cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
		} else {
			UserDetails user = SecurityUtils.getUser();
			cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
		}
		

		CnmCParametro cnmCParametro = cnmCParametroRepository.findOne(ID_DIRIGENTE);

		if (tipoDocActa != null && tipoDocActa.equals(TIPOLOGIA_DOC_ACTA_DOC_INGRESSO_SENZA_ALLEGATI)) {
			if (tipoDocumento == TipoAllegato.SCRITTI_DIFENSIVI.getId() || //
					tipoDocumento == TipoAllegato.ISTANZA_RATEIZZAZIONE.getId() || //
					tipoDocumento == TipoAllegato.OPPOSIZIONE_GIURISDIZIONALE.getId()) {
				request.setMittentiEsterni(TOPOLOGIA_SOGGETTO_TRASGRESSORE);
			} else if (tipoDocumento == TipoAllegato.COMUNICAZIONI_DALLA_CANCELLERIA.getId()) {
				request.setMittentiEsterni(TOPOLOGIA_SOGGETTO_CANCELLERIA);
			} else if (tipoDocumento == TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getId()) {
				request.setMittentiEsterni(TOPOLOGIA_SOGGETTO_GIUDICE);
			}

		} else if (tipoDocActa != null && tipoDocActa.equals(TIPOLOGIA_DOC_ACTA_MASTER_USCITA_CON_ALLEGATI)) {
			setSoggetto(cnmTSoggettoList, request);
			setDestinazione(cnmTSoggettoList, request);
			request.setAutoreGiuridico(AUTORE_REGIONE_PIEMONTE);
			request.setOriginatore(cnmCParametro.getValoreString()); // dirigente
			request.setAutoreFisico(AUTORE_REGIONE_PIEMONTE);
			request.setAutoreGiuridico(cnmCParametro.getValoreString());
		} else if (tipoDocActa != null && tipoDocActa.equals(TIPOLOGIA_DOC_ACTA_DOC_USCITA_SENZA_ALLEGATI_GENERERATI)) {
			setSoggetto(cnmTSoggettoList, request);
			setDestinazione(cnmTSoggettoList, request);
			request.setMittentiEsterni(null);
			request.setAutoreFisico(cnmCParametro.getValoreString());
			request.setAutoreGiuridico(AUTORE_REGIONE_PIEMONTE);
			if (tipoDocumento == TipoAllegato.VERBALE_AUDIZIONE.getId()) {
				request.setOriginatore(cnmTUser.getNome() + " " + cnmTUser.getCognome()); // utente
			} else {
				request.setOriginatore(cnmCParametro.getValoreString()); // dirigente
			}
		} else if (tipoDocActa != null && tipoDocActa.equals(TIPOLOGIA_DOC_ACTA_DOC_USCITA_SENZA_ALLEGATI_CARICATI)) {
			request.setAutoreFisico(cnmTUser.getNome() + " " + cnmTUser.getCognome());
			request.setDestinatarioFisico(null);
			request.setDestinatarioGiuridico(TOPOLOGIA_SOGGETTO_CANCELLERIA);
			request.setMittentiEsterni(null);
			request.getSoggetto().setTipologia(TOPOLOGIA_SOGGETTO_DESTINATARIO);
			request.getSoggetto().setCognome(null);
			request.getSoggetto().setNome(null);
			request.getSoggetto().setDenominazione(TOPOLOGIA_SOGGETTO_CANCELLERIA);
			request.setAutoreGiuridico(AUTORE_REGIONE_PIEMONTE);
			request.setOriginatore(cnmTUser.getNome() + " " + cnmTUser.getCognome()); // utente
		}
		
		// 20200731_LC 
		// request.setCollocazioneCartacea("");
		
		//JIRA - gestione metadati
		//--------------------------------------------------------------------------
		request.setCollocazioneCartacea(DoquiConstants.COLLOCAZIONE_CARTACEA);
		
		
		// lotto2scenario1 gestione folder temp per scritti difensivi (se allegato è scritto difensivo e folder è quello configurato)
		String folderTemp = utilsCnmCProprietaService.getProprieta(PropKey.ACTA_TEMP_FOLDER);
		if (tipoDocumento == TipoAllegato.SCRITTI_DIFENSIVI.getId() && folder.equals(folderTemp)) {
			request.setParolaChiaveFolderTemp(folder);
		} else {
			request.setParolaChiaveFolderTemp(null);	
		}

		
		
        // 20211014_LC Jira CONAM- 140
        if (tipoDocumento == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
        		||tipoDocumento == TipoAllegato.LETTERA_ORDINANZA.getId()
        		||tipoDocumento == TipoAllegato.LETTERA_RATEIZZAZIONE.getId()
        		||tipoDocumento == TipoAllegato.LETTERA_SOLLECITO.getId()
        		||tipoDocumento == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()
        		||tipoDocumento == TipoAllegato.VERBALE_AUDIZIONE.getId()
        		||tipoDocumento == TipoAllegato.COMUNICAZIONI_ALLA_CANCELLERIA.getId()
        		||tipoDocumento == TipoAllegato.COMPARSA.getId()) {
        	request.setDataTopica(DoquiConstants.LUOGO);
        	request.setDataCronica(new Date()); // 
        }

		
		
		
		


		if (cnmTSoggettoList != null && cnmTSoggettoList.size() > 0)
			logger.info("[protocollaDocumentoFisico] -> " + "tipoDocumento :: " + tipoDocumento + " cnmTSoggettoList.size() " + cnmTSoggettoList.size());

		logger.info("[protocollaDocumentoFisico] -> " + "tipoDocumento :: " + tipoDocumento + "- tipoDocActa :: " + tipoDocActa + " - REQUEST :: " + request);

		try {
			return manageProtocollaDocumentoHelper.protocollaDocumentoFisico(request);
		} catch (ProtocollaDocumentoException e) {
			logger.error("Protocolla Documento Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_PROTOCOLLA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_PROTOCOLLA_DOCUMENTO_NON_DISPONIBILE);
		}
	}


	@Override
	public ResponseProtocollaDocumento protocollaDocumentoFisicoEsistente(String folder, String nomeFile, String idEntitaFruitore, boolean isMaster, boolean isProtocollazioneInUscita,
			String soggettoActa, String rootActa, long tipoDocumento, String tipoDocActa, List<CnmTSoggetto> cnmTSoggettoList, String idDocumentoActa) {
		if (folder == null)
			throw new IllegalArgumentException("folder non valido");
		if (nomeFile == null)
			throw new IllegalArgumentException("nomeFile non valido");
		if (tipoDocumento == 0)
			throw new IllegalArgumentException("tipoDocumento non valido");

		logger.info("[protocollaDocumentoFisico] -> nomeFile :: " + nomeFile);

//		String tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA;
//		if (tipoDocumento == TipoAllegato.CONTRODEDUZIONE.getId() || tipoDocumento == TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId()
//				|| tipoDocumento == TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId() || tipoDocumento == TipoAllegato.COMPARSA_ALLEGATO.getId() || tipoDocumento == TipoAllegato.ISTANZA_ALLEGATO.getId()) {
//			tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_1_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_1;
//		} else if (tipoDocumento == TipoAllegato.LETTERA_ORDINANZA.getId() || tipoDocumento == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
//				|| tipoDocumento == TipoAllegato.LETTERA_RATEIZZAZIONE.getId() || tipoDocumento == TipoAllegato.LETTERA_SOLLECITO.getId()) {
//			tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_2_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_2;
//		}

		RequestProtocollaDocumentoFisico request = new RequestProtocollaDocumentoFisico();

		request.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);
//		request.setTipoDocumento(tipoDoc);

		// fascicolo
		request.setFolder(folder);

		// **********SOGGETTO
		Soggetto soggetto = new Soggetto();
		// **********METADATI
		Metadati metadati = new Metadati();

		// protocollazione in ingresso o in uscita di un documento
		if (isProtocollazioneInUscita) {
			soggetto.setTipologia(TOPOLOGIA_SOGGETTO_DESTINATARIO);
		} else
			soggetto.setTipologia(TOPOLOGIA_SOGGETTO_MITTENTE);
		soggetto.setCognome("CONAM");
		soggetto.setNome("CONAM");
		request.setSoggetto(soggetto);
		String mineType = utilsDoqui.getMimeType(nomeFile);
		request.setMimeType(mineType);

		// dossier
		request.setRootActa(rootActa);
		// soggetto
		request.setSoggettoActa(soggettoActa);

		// metadati
		metadati.setIdEntitaFruitore(idEntitaFruitore);
		request.setMetadati(metadati);

//		// **********DOCUMENTO
//		Documento documento = new Documento();
//		if (document != null) {
//			documento.setNomeFile(nomeFile);
//			documento.setFile(document);
//		} else {
//			documento.setIdDocumento(idIndex);
//			documento.setNomeFile(nomeFile);			
//		}
		
		//JIRA - gestione metadati
		//--------------------------------------------------------------------------
		//In caso di doc con allegati e' NECESSARIO l'inserimento della MAX_NUMERO
		//altrimentio il servizio acaris va in errore

//		if (isMaster)
//			documento.setNumeroAllegati(MAX_NUMERO_ALLEGATI);
//
//		request.setDocumento(documento);

		// autore fisico
		request.setAutoreFisico("NULL_VALUE"); // si fa così per forzare a null

		// destinatario giuridico
		request.setDestinatarioGiuridico(AUTORE_REGIONE_PIEMONTE);

		// applicativo alimentante
		request.setApplicativoAlimentante("CONAM");

		// mittenti esterni
		request.setMittentiEsterni("ENTE ACCERTATORE");
		request.setProtocollazioneInUscitaSenzaDocumento(true);

		// 20200711_LC 
		CnmTUser cnmTUser;
		if (SecurityUtils.getAuthentication()==null) {
			cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
		} else {
			UserDetails user = SecurityUtils.getUser();
			cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
		}
		

		CnmCParametro cnmCParametro = cnmCParametroRepository.findOne(ID_DIRIGENTE);

		if (tipoDocActa != null && tipoDocActa.equals(TIPOLOGIA_DOC_ACTA_DOC_INGRESSO_SENZA_ALLEGATI)) {
			if (tipoDocumento == TipoAllegato.SCRITTI_DIFENSIVI.getId() || //
					tipoDocumento == TipoAllegato.ISTANZA_RATEIZZAZIONE.getId() || //
					tipoDocumento == TipoAllegato.OPPOSIZIONE_GIURISDIZIONALE.getId()) {
				request.setMittentiEsterni(TOPOLOGIA_SOGGETTO_TRASGRESSORE);
			} else if (tipoDocumento == TipoAllegato.COMUNICAZIONI_DALLA_CANCELLERIA.getId()) {
				request.setMittentiEsterni(TOPOLOGIA_SOGGETTO_CANCELLERIA);
			} else if (tipoDocumento == TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getId()) {
				request.setMittentiEsterni(TOPOLOGIA_SOGGETTO_GIUDICE);
			}

		} else if (tipoDocActa != null && tipoDocActa.equals(TIPOLOGIA_DOC_ACTA_MASTER_USCITA_CON_ALLEGATI)) {
			setSoggetto(cnmTSoggettoList, request);
			setDestinazione(cnmTSoggettoList, request);
			request.setAutoreGiuridico(AUTORE_REGIONE_PIEMONTE);
			request.setOriginatore(cnmCParametro.getValoreString()); // dirigente
			request.setAutoreFisico(AUTORE_REGIONE_PIEMONTE);
			request.setAutoreGiuridico(cnmCParametro.getValoreString());
		} else if (tipoDocActa != null && tipoDocActa.equals(TIPOLOGIA_DOC_ACTA_DOC_USCITA_SENZA_ALLEGATI_GENERERATI)) {
			setSoggetto(cnmTSoggettoList, request);
			setDestinazione(cnmTSoggettoList, request);
			request.setMittentiEsterni(null);
			request.setAutoreFisico(cnmCParametro.getValoreString());
			request.setAutoreGiuridico(AUTORE_REGIONE_PIEMONTE);
			if (tipoDocumento == TipoAllegato.VERBALE_AUDIZIONE.getId()) {
				request.setOriginatore(cnmTUser.getNome() + " " + cnmTUser.getCognome()); // utente
			} else {
				request.setOriginatore(cnmCParametro.getValoreString()); // dirigente
			}
		} else if (tipoDocActa != null && tipoDocActa.equals(TIPOLOGIA_DOC_ACTA_DOC_USCITA_SENZA_ALLEGATI_CARICATI)) {
			request.setAutoreFisico(cnmTUser.getNome() + " " + cnmTUser.getCognome());
			request.setDestinatarioFisico(null);
			request.setDestinatarioGiuridico(TOPOLOGIA_SOGGETTO_CANCELLERIA);
			request.setMittentiEsterni(null);
			request.getSoggetto().setTipologia(TOPOLOGIA_SOGGETTO_DESTINATARIO);
			request.getSoggetto().setCognome(null);
			request.getSoggetto().setNome(null);
			request.getSoggetto().setDenominazione(TOPOLOGIA_SOGGETTO_CANCELLERIA);
			request.setAutoreGiuridico(AUTORE_REGIONE_PIEMONTE);
			request.setOriginatore(cnmTUser.getNome() + " " + cnmTUser.getCognome()); // utente
		}
		
		// 20200731_LC 
		// request.setCollocazioneCartacea("");
		
		//JIRA - gestione metadati
		//--------------------------------------------------------------------------
		request.setCollocazioneCartacea(DoquiConstants.COLLOCAZIONE_CARTACEA);


		if (cnmTSoggettoList != null && cnmTSoggettoList.size() > 0)
			logger.info("[protocollaDocumentoFisico] -> " + "tipoDocumento :: " + tipoDocumento + " cnmTSoggettoList.size() " + cnmTSoggettoList.size());

		logger.info("[protocollaDocumentoFisico] -> " + "tipoDocumento :: " + tipoDocumento + "- tipoDocActa :: " + tipoDocActa + " - REQUEST :: " + request);

		try {
			return manageProtocollaDocumentoHelper.protocollaDocumentoFisicoEsistente(request, Integer.parseInt(idDocumentoActa));
		} catch (ProtocollaDocumentoException e) {
			logger.error("Protocolla Documento Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_PROTOCOLLA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_PROTOCOLLA_DOCUMENTO_NON_DISPONIBILE);
		}
	}

	
	private void setSoggetto(List<CnmTSoggetto> cnmTSoggettoList, RequestProtocollaDocumentoFisico request) {
		if (cnmTSoggettoList == null || cnmTSoggettoList.size() == 0)
			throw new IllegalArgumentException("cnmTSoggettoList non valido");

		String denominazione = null;
		boolean prima = true;
		for (CnmTSoggetto sog : cnmTSoggettoList) {
			if (StringUtils.isNotBlank(sog.getCodiceFiscale())) {
				if (prima) {
					denominazione = sog.getNome() + " " + sog.getCognome();
					prima = false;
				} else
					denominazione = denominazione + ", " + sog.getNome() + " " + sog.getCognome();
			} else if (StringUtils.isNotBlank(sog.getPartitaIva()) || StringUtils.isNotBlank(sog.getCodiceFiscaleGiuridico())) {
				if (prima) {
					denominazione = sog.getRagioneSociale();
					prima = false;
				} else
					denominazione = denominazione + ", " + sog.getRagioneSociale();
			}

			request.getSoggetto().setTipologia(TOPOLOGIA_SOGGETTO_DESTINATARIO);
			request.getSoggetto().setCognome(null);
			request.getSoggetto().setNome(null);
			request.getSoggetto().setDenominazione(denominazione);
		}

	}

	private void setDestinazione(List<CnmTSoggetto> cnmTSoggettoList, RequestProtocollaDocumentoFisico request) {
		if (cnmTSoggettoList == null || cnmTSoggettoList.size() == 0)
			throw new IllegalArgumentException("cnmTSoggettoList non valido");

		String destinatarioFisico = null, destinatarioGiuridico = null;
		boolean primoFisico = true, primoPiva = true;
		for (CnmTSoggetto sog : cnmTSoggettoList) {
			if (StringUtils.isNotBlank(sog.getCodiceFiscale())) {
				if (primoFisico) {
					destinatarioFisico = sog.getNome() + " " + sog.getCognome();
					primoFisico = false;
				} else
					destinatarioFisico = destinatarioFisico + ", " + sog.getNome() + " " + sog.getCognome();
			} else if (StringUtils.isNotBlank(sog.getPartitaIva()) || StringUtils.isNotBlank(sog.getCodiceFiscaleGiuridico())) {
				if (primoPiva) {
					destinatarioGiuridico = sog.getRagioneSociale();
					primoPiva = false;
				} else
					destinatarioGiuridico = destinatarioGiuridico + ", " + sog.getRagioneSociale();
			}

			request.setDestinatarioGiuridico(destinatarioGiuridico);
			request.setDestinatarioFisico(destinatarioFisico);
		}
	}
//
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		StadocStadocServiceLocator locator = new StadocStadocServiceLocator();
//		String endpointUrl = config.getStadocServiceEndpointUrl();
//		locator.setstadocStadocEndpointAddress(endpointUrl);
//		binding = (StadocStadocSoapBindingStub) locator.getstadocStadoc();
//	}

	@Override
	public ResponseSalvaDocumento salvaDocumentoIndex(String descrizionTipologiaAllegati, byte[] document, String fileName, String idEntitaFruitore, String indexType) {
		if (descrizionTipologiaAllegati == null)
			throw new IllegalArgumentException("anno non valido");
		if (document == null)
			throw new IllegalArgumentException("document non valido");
		if (fileName == null)
			throw new IllegalArgumentException("fileName non valido");
		if (idEntitaFruitore == null)
			throw new IllegalArgumentException("idEntitaFruitore non valido");

		// controllo mimeType
		utilsDoqui.getMimeType(fileName);

		RequestSalvaDocumento request = new RequestSalvaDocumento();
		Documento d = new Documento();

		//LocalDate date = LocalDate.now();
		request.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);
		
		// 20200713 PP - CDU-18 -  per corretta valorizzazione tipo documento nei nuovi documenti (compresa modifica per CDU18)
		boolean isDocSigned = DocumentUtils.isDocumentSigned(document, fileName);
		request.setTipoDocumento(isDocSigned?DoquiConstants.TIPO_DOCUMENTO_INDEX_SIGNED:DoquiConstants.TIPO_DOCUMENTO_INDEX);
		
		//request.setFolder(descrizionTipologiaAllegati.replace(" ", "_").toUpperCase() + "_" + date.getYear());
		request.setFolder(descrizionTipologiaAllegati.replace(" ", "_").toUpperCase());		// + "_" + date.getYear());		// 20200703_LC
		
		d.setFile(document);
		d.setNomeFile(fileName);

		// metadati
		Metadati md = new Metadati();
		md.setIdEntitaFruitore(idEntitaFruitore);

		request.setMetadati(md);
		request.setDocumento(d);
		
		// 20200714_LC
		request.setIndexType(indexType);

		try {
			//TODO -invocare i manager helper per index
			return manageDocumentoHelper.salvaDocumento(request);
		} catch (SalvaDocumentoException e) {
			logger.error("SalvaDocumento Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_SALVA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_SALVA_DOCUMENTO_NON_DISPONIBILE);
		}
	}

	@Override
	public ResponseRicercaAllegato recuperaDocumentoIndex(String idDocumento, String indexType) {
		if (idDocumento == null)
			throw new IllegalArgumentException("idDocumento non valido");

		RequestRicercaAllegato request = new RequestRicercaAllegato();

		request.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);
		request.setIdDocumento(idDocumento);
		
		// 20200714_LC
		request.setIndexType(indexType);

		try {
			//TODO -invocare i manager helper per index
			return manageDocumentoHelper.ricercaAllegato(request);
		} catch (RicercaAllegatoException e) {
			logger.error("RicercaAllegato Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_RECUPERA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_RECUPERA_DOCUMENTO_NON_DISPONIBILE);
		}

	}

	@Override
	public ResponseEliminaDocumento eliminaDocumentoIndex(String idIndex) {
		if (idIndex == null)
			throw new IllegalArgumentException("idDocumento non valido");

		RequestEliminaDocumento request = new RequestEliminaDocumento();

		request.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);
		request.setIdDocumento(idIndex);

		try {
			//TODO -invocare i manager helper per index
			return manageDocumentoHelper.eliminaDocumento(request);
		} catch (EliminaDocumentoException e) {
			logger.error("EliminaDocumento Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_ELIMINA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_ELIMINA_DOCUMENTO_NON_DISPONIBILE);
		}

	}

	// @Override
	// public ResponseProtocollaDocumento protocollaDocumentoMock() {
	// ResponseProtocollaDocumento responseProtocollaDocumento = new
	// ResponseProtocollaDocumento();
	// Random rand = new Random();
	// int value = rand.nextInt(100);
	// String s = RandomStringUtils.randomAlphabetic(4);
	// int value2 = rand.nextInt(100);
	// responseProtocollaDocumento.setProtocollo(value + s + value2);
	// return responseProtocollaDocumento;
	// }
	@Override
	public ResponseAggiungiAllegato aggiungiAllegato(byte[] document, String nomeFile, String idArchivioAllegato, String idArchivioPadre, String idEntitaFruitore, String pkAllegato,
			String soggettoActa, String rootActa, long tipoDocumento, String tipoDocActa, List<CnmTSoggetto> cnmTSoggettoList, Date dataCronica) {

		RequestAggiungiAllegato request = new RequestAggiungiAllegato();

		// **********METADATI
		MetadatiAllegato metadatiAllegato = new MetadatiAllegato();
		metadatiAllegato.setIdEntitaFruitore(idEntitaFruitore);

		Documento documento = new Documento();
		if (document == null) {
			documento.setIdDocumento(idArchivioAllegato);
			documento.setNomeFile(nomeFile);
		} else {
			documento.setFile(document);
			documento.setNomeFile(nomeFile);
		}

		request.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);
		request.setDocumento(documento);
		request.setIdArchivioAllegato(idArchivioAllegato);
		request.setIdArchivioPadre(idArchivioPadre);
		request.setMetadatiAllegato(metadatiAllegato);
		String mineType = utilsDoqui.getMimeType(nomeFile);
		request.setMimeType(mineType);
		request.setPkAllegato(pkAllegato);
		// dossier
		request.setRootActa(rootActa);
		// soggetto
		request.setSoggettoActa(soggettoActa);

		if (tipoDocActa != null && tipoDocActa.equals(TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_INGRESSO)) {
			request.setAutoreFisico("NULL_VALUE");
			request.setApplicativoAlimentante("CONAM");

			if (tipoDocumento == TipoAllegato.SCRITTI_DIFENSIVI.getId() || //
					tipoDocumento == TipoAllegato.ISTANZA_RATEIZZAZIONE.getId() || //
					tipoDocumento == TipoAllegato.OPPOSIZIONE_GIURISDIZIONALE.getId()) {
				request.setMittentiEsterni(TOPOLOGIA_SOGGETTO_TRASGRESSORE);
			} else if (tipoDocumento == TipoAllegato.COMUNICAZIONI_DALLA_CANCELLERIA.getId()) {
				request.setMittentiEsterni(TOPOLOGIA_SOGGETTO_CANCELLERIA);
			} else if (tipoDocumento == TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getId()) {
				request.setMittentiEsterni(TOPOLOGIA_SOGGETTO_GIUDICE);
			}
		} else if (tipoDocActa != null && tipoDocActa.equals(TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_USCITA)) {
			request.setAutoreGiuridico(AUTORE_REGIONE_PIEMONTE);
			// request.setAutoreFisico(????);
			request.setApplicativoAlimentante("CONAM");
			if (tipoDocumento == TipoAllegato.COMPARSA_ALLEGATO.getId() || tipoDocumento == TipoAllegato.ISTANZA_ALLEGATO.getId()) {
				request.setAutoreFisico("NULL_VALUE");
				request.setDestinatarioGiuridico("NULL_VALUE");
			}
		}
		
		
		// 20200711_LC per corretta valorizzazione tipo documento nei nuovi documenti (compresa modifica per CDU18)
		// 20210504_LC Jira 141	-	il controllo sulal firma lo fa dopo nell'helper
		String tipoDoc = DoquiConstants.TIPO_DOCUMENTO_ACTA;
		if (tipoDocumento == TipoAllegato.CONTRODEDUZIONE.getId() || tipoDocumento == TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId() 
				|| tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId()	|| tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId()		
				|| tipoDocumento == TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId() || tipoDocumento == TipoAllegato.COMPARSA_ALLEGATO.getId() || tipoDocumento == TipoAllegato.ISTANZA_ALLEGATO.getId()) {
			tipoDoc = DoquiConstants.TIPO_DOCUMENTO_CONAM_1;

		} else if (tipoDocumento == TipoAllegato.LETTERA_ORDINANZA.getId() || tipoDocumento == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
				|| tipoDocumento == TipoAllegato.LETTERA_RATEIZZAZIONE.getId() || tipoDocumento == TipoAllegato.LETTERA_SOLLECITO.getId()
				|| tipoDocumento == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()) {
			tipoDoc =DoquiConstants.TIPO_DOCUMENTO_CONAM_2;
		}
		request.setTipoDocumento(tipoDoc);
	
		
		// 20210413_LC 
        if (tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId()
                ||tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId()) {
    		request.setAutoreFisico("NULL_VALUE"); // si fa così per forzare a null
    		request.setDestinatarioGiuridico("NULL_VALUE");
            request.setCollocazioneCartacea(null);
            Soggetto soggetto = new Soggetto();
            soggetto.setTipologia(TOPOLOGIA_SOGGETTO_DESTINATARIO);
            soggetto.setCognome("CONAM");
            soggetto.setNome("CONAM");
            request.setSoggetto(soggetto);
            // 20211014_LC da lasciare NULL sulle ordinanza (valorizzare sulle rispettive lettere)
//            request.setDataTopica(DoquiConstants.LUOGO);
//            request.setDataCronica(dataCronica);
        }else {
            //JIRA - gestione metadati
            //--------------------------------------------------------------------------
            request.setCollocazioneCartacea(DoquiConstants.COLLOCAZIONE_CARTACEA);
        }
        
        
        // 20211014_LC Jira CONAM- 140
        if (tipoDocumento == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
        		||tipoDocumento == TipoAllegato.LETTERA_ORDINANZA.getId()
        		||tipoDocumento == TipoAllegato.LETTERA_RATEIZZAZIONE.getId()
        		||tipoDocumento == TipoAllegato.LETTERA_SOLLECITO.getId()
        		||tipoDocumento == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()
        		||tipoDocumento == TipoAllegato.VERBALE_AUDIZIONE.getId()
        		||tipoDocumento == TipoAllegato.COMUNICAZIONI_ALLA_CANCELLERIA.getId()
        		||tipoDocumento == TipoAllegato.COMPARSA.getId()) {
        	request.setDataTopica(DoquiConstants.LUOGO);
        	request.setDataCronica(new Date()); // 
        }


		if (cnmTSoggettoList != null && cnmTSoggettoList.size() > 0)
			logger.info("[aggiungiAllegato] -> " + "tipoDocumento :: " + tipoDocumento + " cnmTSoggettoList.size() " + cnmTSoggettoList.size());

		logger.info("[aggiungiAllegato] -> " + "tipoDocumento :: " + tipoDocumento + " - tipoDocActa :: " + tipoDocActa + " - REQUEST :: " + request);

		try {
			return manageAggiungiAllegatoHelper.aggiungiAllegato(request);
		} catch (AggiungiAllegatoException e) {
			logger.error("AggiungiAllegato Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_ELIMINA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_ELIMINA_DOCUMENTO_NON_DISPONIBILE);
		}
	}

	@Override
	public ResponseRicercaDocumentoMultiplo recuperaDocumentoActa(String idDocumento) {
		if (idDocumento == null)
			throw new IllegalArgumentException("idDocumento non valido");

		RequestRicercaDocumento request = new RequestRicercaDocumento();

		request.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);
		
		// 20200717_LC
		request.setIdDocumento(idDocumento);
		request.setParolaChiave(null);
		request.setObjectIdDocumento(null);
		request.setObjectIdDocumentoFisico(null);
		

		try {
			return manageRicercaDocumentoHelper.ricercaDocumento(request);
		} catch (RicercaDocumentoException e) {
			logger.error("RicercaDocumento Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_RECUPERA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_RECUPERA_DOCUMENTO_NON_DISPONIBILE);
		}

	}

	@Override
	public ResponseArchiviaDocumento archiviaDocumentoFisico(byte[] document, String nomeFile, String folder, String rootActa, int numeroAllegati, String idEntitaFruitore, long tipoDocumento, boolean isMaster
			, String idIndex, String soggettoActa) {
		if (folder == null)
			throw new IllegalArgumentException("folder non valido");
		if (document == null && StringUtils.isBlank(idIndex))
			throw new IllegalArgumentException("document non valido");
		if (nomeFile == null)
			throw new IllegalArgumentException("nomeFile non valido");
		if (rootActa == null)
			throw new IllegalArgumentException("rootActa non valida");
		if (tipoDocumento == 0)
			throw new IllegalArgumentException("tipoDocumento non valido");
		
		// 20210804 PP - recupero doc da index
		if(document == null) {
			RequestRicercaAllegato requestRicercaAllegato = new RequestRicercaAllegato();
			requestRicercaAllegato.setIdDocumento(idIndex); 
			requestRicercaAllegato.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);
			try {
				document = manageDocumentoHelper.ricercaAllegato(requestRicercaAllegato).getDocumento().getFile();
			} catch (RicercaAllegatoException e) {
				throw new IllegalArgumentException("index document non valido");
			}
		}

		// 20200708 PP - se il doc e .p7m, oppure se risulta firmato digitalmente
		boolean isDocSigned = DocumentUtils.isDocumentSigned(document, nomeFile);
		
		String tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA;

		if(!isMaster) {
			tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_FIGLIO_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA_FIGLIO;
		}
		ResponseArchiviaDocumento respose = null;

		RequestArchiviaDocumentoFisico request = new RequestArchiviaDocumentoFisico();
		request.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);

		request.setTipoDocumento(tipoDoc);
		request.setFolder(folder);
		request.setRootFolder(rootActa);
		request.setApplicativoAlimentante("CONAM");

		Metadati metadati = new Metadati();
		metadati.setIdEntitaFruitore(idEntitaFruitore);
		request.setMetadati(metadati);

		Soggetto soggetto = new Soggetto();
		soggetto.setCognome("CONAM");
		soggetto.setNome("CONAM");
		soggetto.setTipologia(TOPOLOGIA_SOGGETTO_MITTENTE);

		request.setSoggetto(soggetto);
		String mineType = utilsDoqui.getMimeType(nomeFile);
		request.setMimeType(mineType);


		// 20200711_LC 
		CnmTUser cnmTUser = null;
		if (SecurityUtils.getAuthentication()==null) {
			cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
		} else {
			UserDetails user = SecurityUtils.getUser();
			cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
		}

		request.setAutoreFisico(cnmTUser.getNome() + " " + cnmTUser.getCognome());
		request.setAutoreGiuridico(AUTORE_REGIONE_PIEMONTE);
		request.setOriginatore(cnmTUser.getNome() + " " + cnmTUser.getCognome());
		request.setDestinatarioFisico(TOPOLOGIA_SOGGETTO_TRASGRESSORE);
		request.setDestinatarioGiuridico(TOPOLOGIA_SOGGETTO_TRASGRESSORE);

		Documento doc = new Documento();
		doc.setFile(document);
		doc.setNomeFile(nomeFile);
		doc.setNumeroAllegati(MAX_NUMERO_ALLEGATI);
		request.setDocumento(doc);
		
		// 20200731_LC 
		// request.setCollocazioneCartacea("");

		//JIRA - gestione metadati
		//--------------------------------------------------------------------------
		request.setCollocazioneCartacea(DoquiConstants.COLLOCAZIONE_CARTACEA);

		
        // 20211014_LC Jira CONAM- 140
        if (tipoDocumento == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
        		||tipoDocumento == TipoAllegato.LETTERA_ORDINANZA.getId()
        		||tipoDocumento == TipoAllegato.LETTERA_RATEIZZAZIONE.getId()
        		||tipoDocumento == TipoAllegato.LETTERA_SOLLECITO.getId()
        		||tipoDocumento == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()
        		||tipoDocumento == TipoAllegato.VERBALE_AUDIZIONE.getId()
        		||tipoDocumento == TipoAllegato.COMUNICAZIONI_ALLA_CANCELLERIA.getId()
        		||tipoDocumento == TipoAllegato.COMPARSA.getId()) {
        	request.setDataTopica(DoquiConstants.LUOGO);
        	request.setDataCronica(new Date()); // 
        }


		logger.info("[archiviaDocumentoFisico] -> " + "tipoDocumento :: " + tipoDocumento + " - REQUEST :: " + request);

		try {
			respose = manageArchiviaDocumentoHelper.archiviaDocumentoFisico(request, soggettoActa);
		} catch (ArchiviaDocumentoException e) {
			logger.error("ArchiviaDocumento Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_SALVA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_SALVA_DOCUMENTO_NON_DISPONIBILE);
		}

		return respose;
	}
	
	@Override
	public List<DocumentoProtocollatoVO> ricercaProtocolloSuACTA(String numProtocollo) {
		if (StringUtils.isBlank(numProtocollo))
			throw new IllegalArgumentException("numProtocollo non valido");

		try {
			return manageRicercaDocumentoHelper.ricercaDocumentoProtocollato(numProtocollo, DoquiConstants.CODICE_FRUITORE);
		} catch (RicercaDocumentoException e) {
			logger.error("RicercaDocumento Exception:", e);
			if(e.getNestedExcClassName().equalsIgnoreCase("RicercaDocumentoNoDocElettronicoException")) {
				throw new RemoteWebServiceException(ErrorCode.DOQUI_RECUPERA_DOCUMENTO_NO_DOC_ELETTRONICO);
			}
			else {
				throw new RemoteWebServiceException(ErrorCode.DOQUI_RECUPERA_DOCUMENTO_NON_DISPONIBILE);
			}
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_RECUPERA_DOCUMENTO_NON_DISPONIBILE);
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	
	// 20200706_LC
	@Override
	public ResponseSpostaDocumento spostaDocumentoProtocollato(String folder, String nomeFile, String idEntitaFruitore, boolean isMaster, boolean isProtocollazioneInUscita,
			String soggettoActa, String rootActa, long tipoDocumento, String numeroProtocollo, int idVerbale) {

		if (folder == null)
			throw new IllegalArgumentException("folder non valido");
		if (nomeFile == null)
			throw new IllegalArgumentException("nomeFile non valido");
		if (tipoDocumento == 0)
			throw new IllegalArgumentException("tipoDocumento non valido");

		logger.info("[spostaDocumentoProtocollato] -> nomeFile :: " + nomeFile);
		

		

		// 20210524_LC in mancanza di byte[], true se è un p7m, false altrimenti
		boolean isDocSigned = false;
		if (nomeFile != null && nomeFile.toUpperCase().endsWith(".P7M"))
			isDocSigned = true;
		
		String tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA;
		if (tipoDocumento == TipoAllegato.CONTRODEDUZIONE.getId() || tipoDocumento == TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId() 
				|| tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId()	|| tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId()		
				|| tipoDocumento == TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId() || tipoDocumento == TipoAllegato.COMPARSA_ALLEGATO.getId() || tipoDocumento == TipoAllegato.ISTANZA_ALLEGATO.getId()) {
			tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_1_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_1;

		} else if (tipoDocumento == TipoAllegato.LETTERA_ORDINANZA.getId() || tipoDocumento == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
				|| tipoDocumento == TipoAllegato.LETTERA_RATEIZZAZIONE.getId() || tipoDocumento == TipoAllegato.LETTERA_SOLLECITO.getId()
				|| tipoDocumento == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()) {
			tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_2_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_2;
		}

		RequestSpostaDocumento request = new RequestSpostaDocumento();

		request.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);
		request.setTipoDocumento(tipoDoc);

		
		request.setFolder(folder);// folder di arrivo (nome)
		

		// dossier
		request.setRootActa(rootActa);

		// soggetto
		request.setSoggettoActa(soggettoActa);

		// protocollo
		request.setNumeroProtocollo(numeroProtocollo);

		// metadati
		Metadati metadati = new Metadati();
		metadati.setIdEntitaFruitore(idEntitaFruitore);
		request.setMetadati(metadati);
		
		// 20200708_LC id verbale
		request.setIdVerbale(String.valueOf(idVerbale));

		// **********DOCUMENTO
		Documento documento = new Documento();
		//documento.setIdDocumento(idIndex);
		
		documento.setNomeFile(nomeFile);	
		
		//JIRA - gestione metadati
		//--------------------------------------------------------------------------
		//In caso di doc con allegati e' NECESSARIO l'inserimento della MAX_NUMERO
		//altrimenti il servizio acaris va in errore
		if (isMaster)
			documento.setNumeroAllegati(MAX_NUMERO_ALLEGATI);
		
		request.setDocumento(documento);

		
		// protocollazione in ingresso o in uscita di un documento
		// **********SOGGETTO
		Soggetto soggetto = new Soggetto();
		if (isProtocollazioneInUscita) {
			soggetto.setTipologia(TOPOLOGIA_SOGGETTO_DESTINATARIO);
		} else
			soggetto.setTipologia(TOPOLOGIA_SOGGETTO_MITTENTE);
		soggetto.setCognome("CONAM");
		soggetto.setNome("CONAM");
		request.setSoggetto(soggetto);
		
		// 20200711_LC 
		/*CnmTUser cnmTUser = null;
		if (SecurityUtils.getAuthentication()==null) {
			cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
		} else {
			UserDetails user = SecurityUtils.getUser();
			cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
		}*/

		
		// 20210506_LC gestione folder temp per scritti difensivi (altro metodo)
		request.setParolaChiaveFolderTemp(null);
		
		
		logger.info("[spostaDocumentoProtocollato] -> " + "tipoDocumento :: " + tipoDocumento + "- REQUEST :: " + request);

		try {
			return manageSpostaDocumentoHelper.spostaDocumento(request);
		} catch (SpostaDocumentoException e) {
			logger.error("Sposta Documento Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_SPOSTA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_SPOSTA_DOCUMENTO_NON_DISPONIBILE);
		}	
		

		
	}
	
	// 20200717_LC
	@Override
	public ResponseRicercaDocumentoMultiplo recuperaDocumentoActaByObjectIdDocumento (String objectIdDocumento) {
		if (objectIdDocumento == null)
			throw new IllegalArgumentException("objectIdDocumento non valido");

		RequestRicercaDocumento request = new RequestRicercaDocumento();

		request.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);
		
		request.setIdDocumento(null);
		request.setParolaChiave(null);
		request.setObjectIdDocumento(objectIdDocumento);
		request.setObjectIdDocumentoFisico(null);

		try {
			return manageRicercaDocumentoHelper.ricercaDocumentoByObjectIdDocumento(request);
		} catch (RicercaDocumentoException e) {
			logger.error("RicercaDocumento Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_RECUPERA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_RECUPERA_DOCUMENTO_NON_DISPONIBILE);
		}

	}
	
	
	// 20200825_LC
	@Override
	public ResponseRicercaDocumentoMultiplo recuperaDocumentoActaByObjectIdDocumentoFisico (String objectIdDocumentoFisico) {
		if (objectIdDocumentoFisico == null)
			throw new IllegalArgumentException("objectIdDocumentoFisico non valido");

		RequestRicercaDocumento request = new RequestRicercaDocumento();

		request.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);
		
		request.setIdDocumento(null);
		request.setParolaChiave(null);
		request.setObjectIdDocumento(null);
		request.setObjectIdDocumentoFisico(objectIdDocumentoFisico);

		try {
			return manageRicercaDocumentoHelper.ricercaDocumentoByObjectIdDocumentoFisico(request);
		} catch (RicercaDocumentoException e) {
			logger.error("RicercaDocumento Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_RECUPERA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_RECUPERA_DOCUMENTO_NON_DISPONIBILE);
		}

	}
	
	
	
	
	
	
	
	
	// 20200728_LC
	@Override
	public ResponseSpostaDocumento copiaDocumentoProtocollato(String folder, String nomeFile, String idEntitaFruitore, boolean isMaster, boolean isProtocollazioneInUscita,
			String soggettoActa, String rootActa, long tipoDocumento, String numeroProtocollo, int idVerbale) {

		if (folder == null)
			throw new IllegalArgumentException("folder non valido");
		if (nomeFile == null)
			throw new IllegalArgumentException("nomeFile non valido");
		if (tipoDocumento == 0)
			throw new IllegalArgumentException("tipoDocumento non valido");

		logger.info("[copiaDocumentoProtocollato] -> nomeFile :: " + nomeFile);
		

		

		// 20210524_LC in mancanza di byte[], true se è un p7m, false altrimenti
		boolean isDocSigned = false;
		if (nomeFile != null && nomeFile.toUpperCase().endsWith(".P7M"))
			isDocSigned = true;
		
		String tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA;
		if (tipoDocumento == TipoAllegato.CONTRODEDUZIONE.getId() || tipoDocumento == TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId() 
				|| tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId()	|| tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId()		
				|| tipoDocumento == TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId() || tipoDocumento == TipoAllegato.COMPARSA_ALLEGATO.getId() || tipoDocumento == TipoAllegato.ISTANZA_ALLEGATO.getId()) {
			tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_1_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_1;

		} else if (tipoDocumento == TipoAllegato.LETTERA_ORDINANZA.getId() || tipoDocumento == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
				|| tipoDocumento == TipoAllegato.LETTERA_RATEIZZAZIONE.getId() || tipoDocumento == TipoAllegato.LETTERA_SOLLECITO.getId()
				|| tipoDocumento == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()) {
			tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_2_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_2;
		}

		RequestSpostaDocumento request = new RequestSpostaDocumento();

		request.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);
		request.setTipoDocumento(tipoDoc);

		
		request.setFolder(folder);// folder di arrivo (nome)
		

		// dossier
		request.setRootActa(rootActa);

		// soggetto
		request.setSoggettoActa(soggettoActa);

		// protocollo
		request.setNumeroProtocollo(numeroProtocollo);

		// metadati
		Metadati metadati = new Metadati();
		metadati.setIdEntitaFruitore(idEntitaFruitore);
		request.setMetadati(metadati);
		
		// 20200708_LC id verbale
		request.setIdVerbale(String.valueOf(idVerbale));

		// **********DOCUMENTO
		Documento documento = new Documento();
		//documento.setIdDocumento(idIndex);
		documento.setNomeFile(nomeFile);
		
		//JIRA - gestione metadati
		//--------------------------------------------------------------------------
		//In caso di doc con allegati e' NECESSARIO l'inserimento della MAX_NUMERO
		//altrimenti il servizio acaris va in errore
		if (isMaster)
			documento.setNumeroAllegati(MAX_NUMERO_ALLEGATI);
		
		request.setDocumento(documento);

		
		// protocollazione in ingresso o in uscita di un documento
		// **********SOGGETTO
		Soggetto soggetto = new Soggetto();
		if (isProtocollazioneInUscita) {
			soggetto.setTipologia(TOPOLOGIA_SOGGETTO_DESTINATARIO);
		} else
			soggetto.setTipologia(TOPOLOGIA_SOGGETTO_MITTENTE);
		soggetto.setCognome("CONAM");
		soggetto.setNome("CONAM");
		request.setSoggetto(soggetto);
		
		// 20200711_LC 
		/*CnmTUser cnmTUser = null;
		if (SecurityUtils.getAuthentication()==null) {
			cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
		} else {
			UserDetails user = SecurityUtils.getUser();
			cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
		}*/

		
		// 20210506_LC gestione folder temp per scritti difensivi (altro metodo)
		request.setParolaChiaveFolderTemp(null);
		
		
		logger.info("[copiaDocumentoProtocollato] -> " + "tipoDocumento :: " + tipoDocumento + "- REQUEST :: " + request);

		try {
			return manageSpostaDocumentoHelper.copiaDocumento(request);
		} catch (SpostaDocumentoException e) {
			logger.error("Sposta Documento Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_COPIA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_COPIA_DOCUMENTO_NON_DISPONIBILE);
		}	
		

		
	}
	
	
	
	
	@Override
	public ResponseSpostaDocumento salvaAllegatoGiaPresenteNelFascicoloActa(DocumentoProtocollatoVO doc, CnmDTipoAllegato cnmDTipoAllegato, CnmTVerbale cnmTVerbale, CnmTUser cnmTUser, List<CnmTAllegato> allegatiGiaSalvati) {
		
		try {
			return manageSpostaDocumentoHelper.salvaAllegatoGiaPresenteNelFascicoloActa(doc, cnmDTipoAllegato, cnmTVerbale, cnmTUser, allegatiGiaSalvati);
		} catch (SpostaDocumentoException e) {
			logger.error("Sposta Documento Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_COPIA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_COPIA_DOCUMENTO_NON_DISPONIBILE);
		}	
		
	}
	
	
	
	
	// 20210420_LC
	@Override
	public ResponseSpostaDocumento spostaDocumentoProtocollatoTemporaneo(String folder, String nomeFile, String idEntitaFruitore, boolean isMaster, boolean isProtocollazioneInUscita,
			String soggettoActa, String rootActa, long tipoDocumento, String numeroProtocollo) {

		if (folder == null)
			throw new IllegalArgumentException("folder non valido");
		if (nomeFile == null)
			throw new IllegalArgumentException("nomeFile non valido");
		if (tipoDocumento == 0)
			throw new IllegalArgumentException("tipoDocumento non valido");

		logger.info("[spostaDocumentoProtocollato] -> nomeFile :: " + nomeFile);
		

		

		// 20210524_LC in mancanza di byte[], true se è un p7m, false altrimenti
		boolean isDocSigned = false;
		if (nomeFile != null && nomeFile.toUpperCase().endsWith(".P7M"))
			isDocSigned = true;
		
		String tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA;
		if (tipoDocumento == TipoAllegato.CONTRODEDUZIONE.getId() || tipoDocumento == TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId() 
				|| tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId()	|| tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId()		
				|| tipoDocumento == TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId() || tipoDocumento == TipoAllegato.COMPARSA_ALLEGATO.getId() || tipoDocumento == TipoAllegato.ISTANZA_ALLEGATO.getId()) {
			tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_1_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_1;

		} else if (tipoDocumento == TipoAllegato.LETTERA_ORDINANZA.getId() || tipoDocumento == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
				|| tipoDocumento == TipoAllegato.LETTERA_RATEIZZAZIONE.getId() || tipoDocumento == TipoAllegato.LETTERA_SOLLECITO.getId()
				|| tipoDocumento == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()) {
			tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_2_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_2;
		}

		RequestSpostaDocumento request = new RequestSpostaDocumento();

		request.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);
		request.setTipoDocumento(tipoDoc);

		
		request.setFolder(folder);// folder di arrivo (nome)
		

		// dossier
		request.setRootActa(rootActa);

		// soggetto
		request.setSoggettoActa(soggettoActa);

		// protocollo
		request.setNumeroProtocollo(numeroProtocollo);

		// metadati
		Metadati metadati = new Metadati();
		metadati.setIdEntitaFruitore(idEntitaFruitore);
		request.setMetadati(metadati);
		
		// 20200708_LC id verbale
		//request.setIdVerbale(String.valueOf(idVerbale));

		// **********DOCUMENTO
		Documento documento = new Documento();
		//documento.setIdDocumento(idIndex);
		
		documento.setNomeFile(nomeFile);	
		
		//JIRA - gestione metadati
		//--------------------------------------------------------------------------
		//In caso di doc con allegati e' NECESSARIO l'inserimento della MAX_NUMERO
		//altrimenti il servizio acaris va in errore
		if (isMaster)
			documento.setNumeroAllegati(MAX_NUMERO_ALLEGATI);
		
		request.setDocumento(documento);

		
		// protocollazione in ingresso o in uscita di un documento
		// **********SOGGETTO
		Soggetto soggetto = new Soggetto();
		if (isProtocollazioneInUscita) {
			soggetto.setTipologia(TOPOLOGIA_SOGGETTO_DESTINATARIO);
		} else
			soggetto.setTipologia(TOPOLOGIA_SOGGETTO_MITTENTE);
		soggetto.setCognome("CONAM");
		soggetto.setNome("CONAM");
		request.setSoggetto(soggetto);
		
		// 20200711_LC 
		/*CnmTUser cnmTUser = null;
		if (SecurityUtils.getAuthentication()==null) {
			cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
		} else {
			UserDetails user = SecurityUtils.getUser();
			cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
		}*/

		
		// lotto2scenario1 gestione folder temp per scritti difensivi
		request.setParolaChiaveFolderTemp(folder);
		
		
		logger.info("[spostaDocumentoProtocollato] -> " + "tipoDocumento :: " + tipoDocumento + "- REQUEST :: " + request);

		try {
			return manageSpostaDocumentoHelper.spostaDocumento(request);
		} catch (SpostaDocumentoException e) {
			logger.error("Sposta Documento Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_SPOSTA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_SPOSTA_DOCUMENTO_NON_DISPONIBILE);
		}	
		

		
	}
	
	@Override
	public ResponseSpostaDocumento copiaDocumentoProtocollatoTemporaneo(String folder, String nomeFile, String idEntitaFruitore, boolean isMaster, boolean isProtocollazioneInUscita,
			String soggettoActa, String rootActa, long tipoDocumento, String numeroProtocollo) {

		if (folder == null)
			throw new IllegalArgumentException("folder non valido");
		if (nomeFile == null)
			throw new IllegalArgumentException("nomeFile non valido");
		if (tipoDocumento == 0)
			throw new IllegalArgumentException("tipoDocumento non valido");

		logger.info("[copiaDocumentoProtocollato] -> nomeFile :: " + nomeFile);
		

		

		// 20210524_LC in mancanza di byte[], true se è un p7m, false altrimenti
		boolean isDocSigned = false;
		if (nomeFile != null && nomeFile.toUpperCase().endsWith(".P7M"))
			isDocSigned = true;
		
		String tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA;
		if (tipoDocumento == TipoAllegato.CONTRODEDUZIONE.getId() || tipoDocumento == TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId() 
				|| tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId()	|| tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId()		
				|| tipoDocumento == TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId() || tipoDocumento == TipoAllegato.COMPARSA_ALLEGATO.getId() || tipoDocumento == TipoAllegato.ISTANZA_ALLEGATO.getId()) {
			tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_1_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_1;

		} else if (tipoDocumento == TipoAllegato.LETTERA_ORDINANZA.getId() || tipoDocumento == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
				|| tipoDocumento == TipoAllegato.LETTERA_RATEIZZAZIONE.getId() || tipoDocumento == TipoAllegato.LETTERA_SOLLECITO.getId()
				|| tipoDocumento == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()) {
			tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_2_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_2;
		}

		RequestSpostaDocumento request = new RequestSpostaDocumento();

		request.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);
		request.setTipoDocumento(tipoDoc);

		
		request.setFolder(folder);// folder di arrivo (nome)
		

		// dossier
		request.setRootActa(rootActa);

		// soggetto
		request.setSoggettoActa(soggettoActa);

		// protocollo
		request.setNumeroProtocollo(numeroProtocollo);

		// metadati
		Metadati metadati = new Metadati();
		metadati.setIdEntitaFruitore(idEntitaFruitore);
		request.setMetadati(metadati);
		
		// 20200708_LC id verbale
		//request.setIdVerbale(String.valueOf(idVerbale));

		// **********DOCUMENTO
		Documento documento = new Documento();
		//documento.setIdDocumento(idIndex);
		documento.setNomeFile(nomeFile);
		
		//JIRA - gestione metadati
		//--------------------------------------------------------------------------
		//In caso di doc con allegati e' NECESSARIO l'inserimento della MAX_NUMERO
		//altrimenti il servizio acaris va in errore
		if (isMaster)
			documento.setNumeroAllegati(MAX_NUMERO_ALLEGATI);
		
		request.setDocumento(documento);

		
		// protocollazione in ingresso o in uscita di un documento
		// **********SOGGETTO
		Soggetto soggetto = new Soggetto();
		if (isProtocollazioneInUscita) {
			soggetto.setTipologia(TOPOLOGIA_SOGGETTO_DESTINATARIO);
		} else
			soggetto.setTipologia(TOPOLOGIA_SOGGETTO_MITTENTE);
		soggetto.setCognome("CONAM");
		soggetto.setNome("CONAM");
		request.setSoggetto(soggetto);
		
		// 20200711_LC 
		/*CnmTUser cnmTUser = null;
		if (SecurityUtils.getAuthentication()==null) {
			cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
		} else {
			UserDetails user = SecurityUtils.getUser();
			cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
		}*/

		// lotto2scenario1 gestione folder temp per scritti difensivi
		request.setParolaChiaveFolderTemp(folder);
		
		
		logger.info("[copiaDocumentoProtocollato] -> " + "tipoDocumento :: " + tipoDocumento + "- REQUEST :: " + request);

		try {
			return manageSpostaDocumentoHelper.copiaDocumento(request);
		} catch (SpostaDocumentoException e) {
			logger.error("Sposta Documento Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_COPIA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_COPIA_DOCUMENTO_NON_DISPONIBILE);
		}	
		

		
	}
	
		
	
	
	
}
