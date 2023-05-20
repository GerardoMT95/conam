/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.facade.impl;

import it.csi.conam.conambl.business.facade.StadocServiceFacade;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.config.Config;
import it.csi.conam.conambl.common.exception.RemoteWebServiceException;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.beans.*;
import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.entity.CnmCParametro;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.repositories.CnmCParametroRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.security.UserDetails;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 09 mag 2018
 */
@Service
public class StadocServiceFacadeImpl implements StadocServiceFacade, InitializingBean {

	@Autowired
	private Config config;
	@Autowired
	private UtilsDoqui utilsDoqui;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CnmCParametroRepository cnmCParametroRepository;

	private static Logger logger = Logger.getLogger(StadocServiceFacadeImpl.class);

	private StadocStadocSoapBindingStub binding;

	public static final String TOPOLOGIA_SOGGETTO_MITTENTE = "MITTENTE";
	public static final String TOPOLOGIA_SOGGETTO_DESTINATARIO = "DESTINATARIO";
	public static final String TOPOLOGIA_SOGGETTO_TRASGRESSORE = "TRASGRESSORE";
	public static final String TOPOLOGIA_SOGGETTO_CANCELLERIA = "CANCELLERIA";
	public static final String TOPOLOGIA_SOGGETTO_GIUDICE = "GIUDICE";

	private static final String CODICE_FRUITORE = "CONAM";
	private static final String TIPO_DOCUMENTO_INDEX = "CONAM_DOC";
	private static final String TIPO_DOCUMENTO_ACTA = "CONAM_ACTA";
	private static final String TIPO_DOCUMENTO_CONAM_1 = "CONAM_A1";
	private static final String TIPO_DOCUMENTO_CONAM_2 = "CONAM_A2";
	//private static final String MIME_TYPE = "application/pdf";

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

		String tipoDoc = TIPO_DOCUMENTO_ACTA;
		if (tipoDocumento == TipoAllegato.CONTRODEDUZIONE.getId() || tipoDocumento == TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId()
				|| tipoDocumento == TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId() || tipoDocumento == TipoAllegato.COMPARSA_ALLEGATO.getId()) {
			tipoDoc = TIPO_DOCUMENTO_CONAM_1;

		} else if (tipoDocumento == TipoAllegato.LETTERA_ORDINANZA.getId() || tipoDocumento == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
				|| tipoDocumento == TipoAllegato.LETTERA_RATEIZZAZIONE.getId() || tipoDocumento == TipoAllegato.LETTERA_SOLLECITO.getId()
				|| tipoDocumento == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()) {
			tipoDoc = TIPO_DOCUMENTO_CONAM_2;
		}

		RequestProtocollaDocumentoFisico request = new RequestProtocollaDocumentoFisico();

		request.setCodiceFruitore(CODICE_FRUITORE);
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
		}
		
		//JIRA - gestione metadati
		//--------------------------------------------------------------------------
		//In caso di doc con allegati e' NECESSARIO l'inserimento della MAX_NUMERO
		//altrimenti il servizio acaris va in errore
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
		CnmTUser cnmTUser = null;
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

		if (cnmTSoggettoList != null && cnmTSoggettoList.size() > 0)
			logger.info("[protocollaDocumentoFisico] -> " + "tipoDocumento :: " + tipoDocumento + " cnmTSoggettoList.size() " + cnmTSoggettoList.size());

		logger.info("[protocollaDocumentoFisico] -> " + "tipoDocumento :: " + tipoDocumento + "- tipoDocActa :: " + tipoDocActa + " - REQUEST :: " + request);

		try {
			return binding.protocollaDocumentoFisico(request);
		} catch (RemoteException e) {
			logger.error("Remote Exception:", e);
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

	@Override
	public void afterPropertiesSet() throws Exception {
		StadocStadocServiceLocator locator = new StadocStadocServiceLocator();
		String endpointUrl = config.getStadocServiceEndpointUrl();
		locator.setstadocStadocEndpointAddress(endpointUrl);
		binding = (StadocStadocSoapBindingStub) locator.getstadocStadoc();
	}

	@Override
	public ResponseSalvaDocumento salvaDocumentoIndex(String descrizionTipologiaAllegati, byte[] document, String fileName, String idEntitaFruitore) {
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

		LocalDate date = LocalDate.now();
		request.setCodiceFruitore(CODICE_FRUITORE);
		request.setTipoDocumento(TIPO_DOCUMENTO_INDEX);
		request.setFolder(descrizionTipologiaAllegati.replace(" ", "_").toUpperCase() + "_" + date.getYear());

		d.setFile(document);
		d.setNomeFile(fileName);

		// metadati
		Metadati md = new Metadati();
		md.setIdEntitaFruitore(idEntitaFruitore);

		request.setMetadati(md);
		request.setDocumento(d);

		try {
			return binding.salvaDocumento(request);
		} catch (RemoteException e) {
			logger.error("Remote Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_SALVA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_SALVA_DOCUMENTO_NON_DISPONIBILE);
		}
	}

	@Override
	public ResponseRicercaAllegato recuperaDocumentoIndex(String idDocumento) {
		if (idDocumento == null)
			throw new IllegalArgumentException("idDocumento non valido");

		RequestRicercaAllegato request = new RequestRicercaAllegato();

		request.setCodiceFruitore(CODICE_FRUITORE);
		request.setIdDocumento(idDocumento);

		try {
			return binding.ricercaAllegato(request);
		} catch (RemoteException e) {
			logger.error("Remote Exception:", e);
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

		request.setCodiceFruitore(CODICE_FRUITORE);
		request.setIdDocumento(idIndex);

		try {
			return binding.eliminaDocumento(request);
		} catch (RemoteException e) {
			logger.error("Remote Exception:", e);
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
			String soggettoActa, String rootActa, long tipoDocumento, String tipoDocActa, List<CnmTSoggetto> cnmTSoggettoList) {

		RequestAggiungiAllegato request = new RequestAggiungiAllegato();

		// **********METADATI
		MetadatiAllegato metadatiAllegato = new MetadatiAllegato();
		metadatiAllegato.setIdEntitaFruitore(idEntitaFruitore);

		Documento documento = new Documento();
		if (document == null) {
			documento.setIdDocumento(idArchivioAllegato);
		} else {
			documento.setFile(document);
			documento.setNomeFile(nomeFile);
		}

		request.setCodiceFruitore(CODICE_FRUITORE);
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
			if (tipoDocumento == TipoAllegato.COMPARSA_ALLEGATO.getId()) {
				request.setAutoreFisico("NULL_VALUE");
				request.setDestinatarioGiuridico("NULL_VALUE");
			}
		}

		if (cnmTSoggettoList != null && cnmTSoggettoList.size() > 0)
			logger.info("[aggiungiAllegato] -> " + "tipoDocumento :: " + tipoDocumento + " cnmTSoggettoList.size() " + cnmTSoggettoList.size());

		logger.info("[aggiungiAllegato] -> " + "tipoDocumento :: " + tipoDocumento + " - tipoDocActa :: " + tipoDocActa + " - REQUEST :: " + request);

		try {
			return binding.aggiungiAllegato(request);
		} catch (RemoteException e) {
			logger.error("Remote Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_ELIMINA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_ELIMINA_DOCUMENTO_NON_DISPONIBILE);
		}
	}

	@Override
	public ResponseRicercaDocumento recuperaDocumentoActa(String idDocumento) {
		if (idDocumento == null)
			throw new IllegalArgumentException("idDocumento non valido");

		RequestRicercaDocumento request = new RequestRicercaDocumento();

		request.setCodiceFruitore(CODICE_FRUITORE);
		request.setIdDocumento(idDocumento);

		try {
			return binding.ricercaDocumento(request);
		} catch (RemoteException e) {
			logger.error("Remote Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_RECUPERA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_RECUPERA_DOCUMENTO_NON_DISPONIBILE);
		}

	}

	@Override
	public ResponseArchiviaDocumento archiviaDocumentoFisico(byte[] document, String nomeFile, String folder, String rootActa, int numeroAllegati, String idEntitaFruitore, long tipoDocumento) {
		if (folder == null)
			throw new IllegalArgumentException("folder non valido");
		if (document == null)
			throw new IllegalArgumentException("document non valido");
		if (nomeFile == null)
			throw new IllegalArgumentException("nomeFile non valido");
		if (rootActa == null)
			throw new IllegalArgumentException("rootActa non valida");
		if (tipoDocumento == 0)
			throw new IllegalArgumentException("tipoDocumento non valido");

		String tipoDoc = TIPO_DOCUMENTO_ACTA;

		ResponseArchiviaDocumento respose = null;

		RequestArchiviaDocumentoFisico request = new RequestArchiviaDocumentoFisico();
		request.setCodiceFruitore(CODICE_FRUITORE);

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
		doc.setNumeroAllegati(numeroAllegati);
		request.setDocumento(doc);

		logger.info("[archiviaDocumentoFisico] -> " + "tipoDocumento :: " + tipoDocumento + " - REQUEST :: " + request);

		try {
			respose = binding.archiviaDocumentoFisico(request);
		} catch (RemoteException e) {
			logger.error("Remote Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_SALVA_DOCUMENTO_NON_DISPONIBILE);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RemoteWebServiceException(ErrorCode.DOQUI_SALVA_DOCUMENTO_NON_DISPONIBILE);
		}

		return respose;
	}

}
