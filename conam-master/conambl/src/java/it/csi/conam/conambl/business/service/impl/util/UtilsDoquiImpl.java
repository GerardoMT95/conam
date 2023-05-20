/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.util;

import it.csi.conam.conambl.business.service.util.UtilsCnmCProprietaService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.common.exception.FileP7MNotSignedException;
import it.csi.conam.conambl.integration.doqui.bean.IndexManagementPojo;
import it.csi.conam.conambl.integration.doqui.entity.CnmTFruitore;
import it.csi.conam.conambl.integration.doqui.exception.FruitoreException;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.csi.conam.conambl.integration.doqui.service.IndexManagementService;
import it.csi.conam.conambl.integration.entity.CnmCProprieta.PropKey;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.util.DocumentUtils;
import it.csi.conam.conambl.util.StadocMimeType;
import it.csi.conam.conambl.util.UtilsSoggetto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 21 feb 2019
 */
@Service
public class UtilsDoquiImpl implements UtilsDoqui {

	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private CnmRVerbaleIllecitoRepository cnmRVerbaleIllecitoRepository;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmTRataRepository cnmTRataRepository;
	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;

	@Autowired
	private IndexManagementService indexManagementService;

	@Autowired
	private UtilsCnmCProprietaService utilsCnmCProprietaService;

	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;
	
	private static final String SIGN_ERROR_MESSAGE = "SIGNERRORM";
	
	private String getAcronimoAmbitoVerbale(CnmTVerbale cnmTVerbale) {
		List<CnmRVerbaleIllecito> cnmRVerbaleIllecitos = cnmRVerbaleIllecitoRepository.findByCnmTVerbale(cnmTVerbale);
		CnmDAmbito cnmDAmbito = cnmRVerbaleIllecitos.get(0).getCnmDLettera().getCnmDComma().getCnmDArticolo().getCnmREnteNorma().getCnmDNorma().getCnmDAmbito();
		for (CnmRVerbaleIllecito cnmRVerbaleIllecito : cnmRVerbaleIllecitos) {
			CnmDAmbito cnmDAmbitoTemp = cnmRVerbaleIllecito.getCnmDLettera().getCnmDComma().getCnmDArticolo().getCnmREnteNorma().getCnmDNorma().getCnmDAmbito();
			if (!cnmDAmbito.getAcronimo().equals(cnmDAmbitoTemp.getAcronimo()))
				return Constants.ACRONIMO_AMBITO_MULTI;
		}
		return cnmDAmbito.getAcronimo();
	}

	private int getAnnoAccertamentoVerbale(CnmTVerbale cnmTVerbale) {
		return utilsDate.asLocalDate(cnmTVerbale.getDataOraAccertamento()).getYear();
	}

	// da gestire il recupero del folder da db
	@Override
	public String createOrGetfolder(CnmTVerbale cnmTVerbale) {
		if (cnmTVerbale == null)
			throw new IllegalArgumentException("cnmTVerbale=null");
		// 20210429_LC Jira129: paroalChiave folder comprende idVerbale (numVerbale non più univoco)
		String folder = getAcronimoAmbitoVerbale(cnmTVerbale) + "/" + getAnnoAccertamentoVerbale(cnmTVerbale) + " - " + cnmTVerbale.getNumVerbale() + " - " + cnmTVerbale.getIdVerbale();
		return StringUtils.abbreviate(folder, 100);
	}

	@Override
	public String createOrGetfolder(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		if (cnmROrdinanzaVerbSog == null)
			throw new IllegalArgumentException("cnmROrdinanzaVerbSog=null");
		CnmTVerbale cnmTVerbale = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTVerbale();
		return createOrGetfolder(cnmTVerbale);
	}

	@Override
	public String createOrGetfolder(CnmTOrdinanza cnmTOrdinanza) {
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza=null");
		List<CnmROrdinanzaVerbSog> soggetti = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		return createOrGetfolder(soggetti.get(0).getCnmRVerbaleSoggetto().getCnmTVerbale());
	}

	@Override
	public String createOrGetfolder(CnmTPianoRate cnmTPianoRate) {
		if (cnmTPianoRate == null)
			throw new IllegalArgumentException("cnmTPianoRate=null");
		List<CnmROrdinanzaVerbSog> ordVerbSog = cnmRSoggRataRepository.findDistinctCnmROrdinanzaVerbSogByCnmTRataIn(cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate));
		return createOrGetfolder(ordVerbSog.get(0));
	}

	@Override
	public String createOrGetfolder(CnmTSollecito cnmTSollecito) {
		if (cnmTSollecito == null)
			throw new IllegalArgumentException("cnmTSollecito=null");
		return createOrGetfolder(cnmTSollecito.getCnmROrdinanzaVerbSog());
	}

	@Override
	public String createOrGetfolder(CnmRVerbaleSoggetto cnmRVerbaleSoggetto) {
		if (cnmRVerbaleSoggetto == null)
			throw new IllegalArgumentException("cnmRVerbaleSoggetto=null");
		return createOrGetfolder(cnmRVerbaleSoggetto.getCnmTVerbale());
	}

	@Override
	public String createIdEntitaFruitore(CnmTVerbale cnmTVerbale, CnmDTipoAllegato cnmDTipoAllegato) {
		if (cnmDTipoAllegato == null)
			throw new IllegalArgumentException("cnmDTipoAllegato=null");
		if (cnmTVerbale == null)
			throw new IllegalArgumentException("cnmTVerbale=null");
		return cnmTVerbale.getIdVerbale() + " - " + cnmDTipoAllegato.getDescTipoAllegato();
	}

	@Override
	public String createIdEntitaFruitore(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog, CnmDTipoAllegato cnmDTipoAllegato) {
		if (cnmROrdinanzaVerbSog == null)
			throw new IllegalArgumentException("cnmROrdinanzaVerbSog=null");
		CnmTVerbale cnmTVerbale = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTVerbale();
		return createIdEntitaFruitore(cnmTVerbale, cnmDTipoAllegato);
	}

	@Override
	public String createIdEntitaFruitore(CnmTOrdinanza cnmTOrdinanza, CnmDTipoAllegato cnmDTipoAllegato) {
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza=null");
		List<CnmROrdinanzaVerbSog> soggetti = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		return createIdEntitaFruitore(soggetti.get(0).getCnmRVerbaleSoggetto().getCnmTVerbale(), cnmDTipoAllegato);
	}

	@Override
	public String createIdEntitaFruitore(CnmRVerbaleSoggetto cnmRVerbaleSoggetto, CnmDTipoAllegato cnmDTipoAllegato) {
		if (cnmRVerbaleSoggetto == null)
			throw new IllegalArgumentException("cnmRVerbaleSoggetto=null");
		return createIdEntitaFruitore(cnmRVerbaleSoggetto.getCnmTVerbale(), cnmDTipoAllegato);
	}

	@Override
	public String createIdEntitaFruitore(CnmTPianoRate cnmTPianoRate, CnmDTipoAllegato cnmDTipoAllegato) {
		if (cnmTPianoRate == null)
			throw new IllegalArgumentException("cnmTPianoRate=null");
		List<CnmROrdinanzaVerbSog> ordVerbSog = cnmRSoggRataRepository.findDistinctCnmROrdinanzaVerbSogByCnmTRataIn(cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate));
		return createIdEntitaFruitore(ordVerbSog.get(0), cnmDTipoAllegato);
	}

	@Override
	public String createIdEntitaFruitore(CnmTSollecito cnmTSollecito, CnmDTipoAllegato cnmDTipoAllegato) {
		if (cnmTSollecito == null)
			throw new IllegalArgumentException("cnmTSollecito=null");
		return createIdEntitaFruitore(cnmTSollecito.getCnmROrdinanzaVerbSog(), cnmDTipoAllegato);
	}

	@Override
	public String getSoggettoActa(CnmTVerbale cnmTVerbale) {
		List<String> nomeCognome = UtilsSoggetto.convertSoggettiToListString(cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale), Constants.VERBALE_SOGGETTO_RUOLO_TRASGRESSORE_ID);
		StringBuilder nome = new StringBuilder();
		int size = nomeCognome.size();
		for (int i = 0; i < size; i++) {
			nome.append(nomeCognome.get(i));
			if (i + 1 != size)
				nome.append("/");
		}
		return nome.toString();
	}

	@Override
	public String getSoggettoActa(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		if (cnmROrdinanzaVerbSog == null)
			throw new IllegalArgumentException("cnmROrdinanzaVerbSog=null");
		CnmTVerbale cnmTVerbale = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTVerbale();
		return getSoggettoActa(cnmTVerbale);
	}

	@Override
	public String getSoggettoActa(CnmTOrdinanza cnmTOrdinanza) {
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza=null");
		List<CnmROrdinanzaVerbSog> soggetti = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		return getSoggettoActa(soggetti.get(0).getCnmRVerbaleSoggetto().getCnmTVerbale());
	}

	@Override
	public String getSoggettoActa(CnmRVerbaleSoggetto cnmRVerbaleSoggetto) {
		if (cnmRVerbaleSoggetto == null)
			throw new IllegalArgumentException("cnmRVerbaleSoggetto=null");
		return getSoggettoActa(cnmRVerbaleSoggetto.getCnmTVerbale());
	}

	@Override
	public String getSoggettoActa(CnmTPianoRate cnmTPianoRate) {
		if (cnmTPianoRate == null)
			throw new IllegalArgumentException("cnmTPianoRate=null");
		List<CnmROrdinanzaVerbSog> ordVerbSog = cnmRSoggRataRepository.findDistinctCnmROrdinanzaVerbSogByCnmTRataIn(cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate));
		return getSoggettoActa(ordVerbSog.get(0));
	}

	@Override
	public String getSoggettoActa(CnmTSollecito cnmTSollecito) {
		if (cnmTSollecito == null)
			throw new IllegalArgumentException("cnmTSollecito=null");
		return getSoggettoActa(cnmTSollecito.getCnmROrdinanzaVerbSog());
	}

	// ACRONIMO+ANNO VERBALE
	@Override
	public String getRootActa(CnmTVerbale cnmTVerbale) {
		if (cnmTVerbale == null)
			throw new IllegalArgumentException("cnmTVerbale=null");
		return getAcronimoAmbitoVerbale(cnmTVerbale) + "-" + getAnnoAccertamentoVerbale(cnmTVerbale);
	}

	@Override
	public String getRootActa(CnmRVerbaleSoggetto cnmRVerbaleSoggetto) {
		if (cnmRVerbaleSoggetto == null)
			throw new IllegalArgumentException("cnmRVerbaleSoggetto=null");
		return getRootActa(cnmRVerbaleSoggetto.getCnmTVerbale());
	}

	@Override
	public String getRootActa(CnmTSollecito cnmTSollecito) {
		if (cnmTSollecito == null)
			throw new IllegalArgumentException("cnmTSollecito=null");
		return getRootActa(cnmTSollecito.getCnmROrdinanzaVerbSog());
	}

	@Override
	public String getRootActa(CnmTPianoRate cnmTPianoRate) {
		if (cnmTPianoRate == null)
			throw new IllegalArgumentException("cnmTPianoRate=null");
		List<CnmROrdinanzaVerbSog> ordVerbSog = cnmRSoggRataRepository.findDistinctCnmROrdinanzaVerbSogByCnmTRataIn(cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(cnmTPianoRate));
		return getRootActa(ordVerbSog.get(0));
	}

	@Override
	public String getRootActa(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		if (cnmROrdinanzaVerbSog == null)
			throw new IllegalArgumentException("cnmROrdinanzaVerbSog=null");
		CnmTVerbale cnmTVerbale = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTVerbale();
		return getRootActa(cnmTVerbale);
	}

	@Override
	public String getRootActa(CnmTOrdinanza cnmTOrdinanza) {
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza=null");
		List<CnmROrdinanzaVerbSog> soggetti = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		return getRootActa(soggetti.get(0).getCnmRVerbaleSoggetto().getCnmTVerbale());
	}

	@Override
	public String getMimeType(String fileName) {
		Path path = new File(fileName).toPath();
		String mimeType;
		// 20210421 PP - JIRA CONAM-134 - restituisco application/pdf in caso di file con estenzione ".PDF"
		if(path.toString().endsWith(".PDF")) {
			return StadocMimeType.APPLICATION_PDF.value();
		}
		try {
			mimeType = Files.probeContentType(path);
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.DOQUI_MIMETYPE_NON_ESTRATTO);
		}
		StadocMimeType stadocMimeType = StadocMimeType.fromValue(mimeType);
		if (stadocMimeType == null)
			throw new BusinessException(ErrorCode.DOQUI_MIMETYPE_NON_CORRETTO);

		return stadocMimeType.value();
	}

	private String getClassificazione(CnmTVerbale cnmTVerbale) {
		// 20210701_LC Jira 159	-	nella classificazione del documento creato da Conam: oggetto del folder e non parola chiave
		//String folder = createOrGetfolder(cnmTVerbale);
		String folder = getAcronimoAmbitoVerbale(cnmTVerbale) + "/" + getAnnoAccertamentoVerbale(cnmTVerbale) + " - " + cnmTVerbale.getNumVerbale();
		return "4.90." + Constants.PRODUCT_NAME + "." + folder.toUpperCase();

	}

	@Override
	public String getClassificazione(CnmTPianoRate dto) {
		if (dto == null)
			throw new IllegalArgumentException("cnmTPianoRate=null");
		List<CnmROrdinanzaVerbSog> ordVerbSog = cnmRSoggRataRepository.findDistinctCnmROrdinanzaVerbSogByCnmTRataIn(cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(dto));
		return getClassificazione(ordVerbSog.get(0).getCnmRVerbaleSoggetto());
	}

	@Override
	public String getClassificazione(CnmTOrdinanza cnmTOrdinanza) {
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza=null");
		List<CnmROrdinanzaVerbSog> soggetti = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		return getClassificazione(soggetti.get(0).getCnmRVerbaleSoggetto());
	}

	@Override
	public String getClassificazione(CnmTSollecito dto) {
		if (dto == null)
			throw new IllegalArgumentException("cnmTSollecito=null");
		return getClassificazione(dto.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto());
	}

	@Override
	public String getClassificazione(CnmRVerbaleSoggetto cnmRVerbaleSoggetto) {
		if (cnmRVerbaleSoggetto == null)
			throw new IllegalArgumentException("cnmRVerbaleSoggetto=null");
		return getClassificazione(cnmRVerbaleSoggetto.getCnmTVerbale());
	}

	@Override
	public String getFn(CnmTPianoRate dto) {
		if (dto == null)
			throw new IllegalArgumentException("cnmTPianoRate=null");
		List<CnmROrdinanzaVerbSog> ordVerbSog = cnmRSoggRataRepository.findDistinctCnmROrdinanzaVerbSogByCnmTRataIn(cnmTRataRepository.findByCnmTPianoRateOrderByNumeroRataAsc(dto));
		return getFn(ordVerbSog.get(0).getCnmRVerbaleSoggetto());
	}

	@Override
	public String getFn(CnmTOrdinanza cnmTOrdinanza) {
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza=null");
		List<CnmROrdinanzaVerbSog> soggetti = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		return getFn(soggetti.get(0).getCnmRVerbaleSoggetto());
	}

	@Override
	public String getFn(CnmTSollecito dto) {
		if (dto == null)
			throw new IllegalArgumentException("cnmTSollecito=null");
		return getFn(dto.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto());
	}

	@Override
	public String getFn(CnmRVerbaleSoggetto cnmRVerbaleSoggetto) {
		if (cnmRVerbaleSoggetto == null)
			throw new IllegalArgumentException("cnmRVerbaleSoggetto=null");
		return getFn(cnmRVerbaleSoggetto.getCnmTVerbale());
	}

	private String getFn(CnmTVerbale cnmTVerbale) {
		if (cnmTVerbale == null)
			throw new IllegalArgumentException("cnmTVerbale=null");
		String folder = getAcronimoAmbitoVerbale(cnmTVerbale) + "/" + getAnnoAccertamentoVerbale(cnmTVerbale) ;
		return folder.toUpperCase();

	}
	
	// 20200804_LC per multitipo
	@Override
	public String createIdEntitaFruitore(CnmTVerbale cnmTVerbale, List<CnmTAllegato> cnmTAllegatoMultiTipoList) {
		if (cnmTVerbale == null)
			throw new IllegalArgumentException("cnmTVerbale=null");
		String ret = cnmTVerbale.getIdVerbale() + " - ";
		for (CnmTAllegato all:cnmTAllegatoMultiTipoList) {
			ret =  ret + all.getCnmDTipoAllegato().getDescTipoAllegato() + "; ";
		}
		ret = ret.substring(0, ret.length()-2);
				
		return ret;
	}

	public void checkFileSign(byte[] document, String nomeFile) throws FileP7MNotSignedException {

		// TODO - 20201026 PP - Per abilitare il check delle firme su INDEX va commentata la riga successiva e decommentato il blocco :
		// INIZIO CONTROLLO FIRMA INDEX
		// FINE CONTROLLO FIRMA INDEX
		// Inoltre per completare il requisito sara' necessario eliminare il bypass dei passaggi di verifica inviati a Acta
		
//		DocumentUtils.checkFileSign(document, nomeFile);
		
		/* 
		 * INIZIO CONTROLLO FIRMA INDEX
		 */
		if(DocumentUtils.isDocumentSigned(document, nomeFile)) {

			try {
				IndexManagementPojo imp = new IndexManagementPojo();
				imp.setFile(document);
				imp.setNomeFile(nomeFile);
				CnmTFruitore cnmTFruitore = getFruitore();
				imp.setCustomModel(cnmTFruitore.getCustomModelIndex());
				imp.setFruitore(cnmTFruitore.getFruitoreIndex());
				imp.setUsr(cnmTFruitore.getUsernameIndex());
				imp.setPsw(cnmTFruitore.getPasswordIndex());
				imp.setRepostory(cnmTFruitore.getRepositoryIndex());
				imp.setUtenteApplicativo(cnmTFruitore.getDescrFruitore());
			
				
				indexManagementService.controllaFirmaDocumento(imp);
			} catch (IntegrationException|FruitoreException e) {
				throw new FileP7MNotSignedException(getSignedErrorMessage());
			}
		}
		
		/** FINE CONTROLLO FIRMA INDEX */ 
	}

	private String signErrorMessage;
	
	private String getSignedErrorMessage() {
		if(signErrorMessage != null) {
			return signErrorMessage;
		}
		
		CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(SIGN_ERROR_MESSAGE);
		signErrorMessage = cnmDMessaggio.getDescMessaggio();
		return signErrorMessage;
	}

	CnmTFruitore cnmTFruitore = null;
	public CnmTFruitore getFruitore() throws FruitoreException {
		//String method = "getFruitore";
		
		if(cnmTFruitore != null) {
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
			
		}
		catch (Exception e)
		{
			throw new FruitoreException(e.getMessage());
		}

		return cnmTFruitore;
	
	}
	
	
	// 20210409_LC lotto2scenario1 
	@Override
	public String createIdEntitaFruitoreScrittoDifensivo(CnmTScrittoDifensivo cnmTScrittoDifensivo) {
		if (cnmTScrittoDifensivo == null) throw new IllegalArgumentException("cnmTScrittoDifensivo is null");	
		return getSoggettoActaScrittoDifensivo(cnmTScrittoDifensivo) + " " + cnmTScrittoDifensivo.getCnmDAmbito().getAcronimo() + " Scritti difensivi";
	}
	
	@Override
	public String createOrGetfolderScrittoDifensivo() {
		// parola chiave del folder in cui protocollare gli scritti difensivi
		String folderName = utilsCnmCProprietaService.getProprieta(PropKey.ACTA_TEMP_FOLDER);
		return folderName;
	}
	
	@Override
	public String getSoggettoActaScrittoDifensivo(CnmTScrittoDifensivo cnmTScrittoDifensivo) {
		if (cnmTScrittoDifensivo == null) throw new IllegalArgumentException("cnmTScrittoDifensivo is null");			
		String soggettoActa = "Soggetto indefinito";		
		if (cnmTScrittoDifensivo.getCognome() != null && cnmTScrittoDifensivo.getNome() != null)
			soggettoActa = cnmTScrittoDifensivo.getCognome() + "  " + cnmTScrittoDifensivo.getNome();		
		return soggettoActa;
	}
	
	@Override
	public String getRootActaScrittoDifensivo(CnmTScrittoDifensivo cnmTScrittoDifensivo) {
		if (cnmTScrittoDifensivo == null) throw new IllegalArgumentException("cnmTScrittoDifensivo is null");
		if (cnmTScrittoDifensivo.getCnmDAmbito() == null) throw new IllegalArgumentException("cnmDAmbito is null");
		// 20210506_LC non si usa questo root, si cerca il fascicolo (libero) per parola chiave
		String root = cnmTScrittoDifensivo.getCnmDAmbito().getAcronimo() + "-" + utilsDate.asLocalDate(cnmTScrittoDifensivo.getDataOraInsert()).getYear();
		return root;
	}	
	
}
