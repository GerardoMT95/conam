/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.PrintException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import it.csi.conam.conambl.business.service.TemplateService;
import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.ordinanza.AllegatoOrdinanzaService;
import it.csi.conam.conambl.business.service.pianorateizzazione.AllegatoPianoRateizzazioneService;
import it.csi.conam.conambl.business.service.sollecito.AllegatoSollecitoService;
import it.csi.conam.conambl.business.service.util.UtilsReport;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleSoggettoService;
import it.csi.conam.conambl.common.Report;
import it.csi.conam.conambl.integration.beans.Soggetto;
import it.csi.conam.conambl.integration.entity.CnmCParametro;
import it.csi.conam.conambl.integration.entity.CnmDMessaggio;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmTPianoRate;
import it.csi.conam.conambl.integration.entity.CnmTSollecito;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.mapper.entity.DatiTemplateVOEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmCParametroRepository;
import it.csi.conam.conambl.integration.repositories.CnmDMessaggioRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleSoggettoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmTPianoRateRepository;
import it.csi.conam.conambl.integration.repositories.CnmTSollecitoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.request.template.DatiTemplateRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.luoghi.ProvinciaVO;
import it.csi.conam.conambl.vo.template.DatiTemplateCompilatiVO;
import it.csi.conam.conambl.vo.template.DatiTemplateVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author riccardo.bova
 * @date 09 gen 2019
 */
@Service
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	private CnmTPianoRateRepository cnmTPianoRateRepository;
	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private DatiTemplateVOEntityMapper datiTemplateResponseEntityMapper;
	@Autowired
	private UtilsReport utilsReport;
	@Autowired
	private AllegatoPianoRateizzazioneService allegatoPianoRateizzazioneService;
	@Autowired
	private AllegatoOrdinanzaService allegatoOrdinanzaService;
	@Autowired
	private CnmTSollecitoRepository cnmTSollecitoRepository;
	@Autowired
	private AllegatoSollecitoService allegatoSollecitoService;
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private AllegatoVerbaleSoggettoService allegatoVerbaleSoggettoService;
	@Autowired
	private CommonAllegatoService commonAllegatoService;
	@Autowired
	private CnmCParametroRepository cnmCParametroRepository;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;

	@Override
	public DatiTemplateVO getDatiTemplate(DatiTemplateRequest request, UserDetails userDetails ) {
		if (request.getCodiceTemplate() == null)
			throw new IllegalArgumentException("codice template non valorizzato");

		Report report = Report.getByCodiceFrontend(request.getCodiceTemplate());
		DatiTemplateVO response = null;
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());	
		
		
		switch (report) {
		case REPORT_CONVOCAZIONE_AUDIZIONE:
			if(request.getIdVerbale() == null) {
				response = getDatiTemplateConvocazioneleAudizione(request.getIdVerbaleSoggettoList());
			}else {
				// 20210906 PP - Convocazione audizione utenti esterni
				response = getDatiTemplateConvocazioneAudizioneEsterni(request.getIdVerbale(), request.getSoggettoList());
			}
			break;
		case REPORT_VERBALE_AUDIZIONE:
			response = getDatiTemplateVerbaleAudizione(request.getIdVerbaleSoggettoList());
			break;
		case REPORT_LETTERA_ACCOMPAGNAMENTO_ARCHIVIAZIONE:			
			response = getDatiTemplateLetteraOrdinanza(request.getIdOrdinanza());		
			response.setFunzionario(this.ricavaInizialiFunzionario(cnmTUser.getNome(), cnmTUser.getCognome()));					
			break;
		case REPORT_LETTERA_ACCOMPAGNAMENTO_INGIUNZIONE:			
			response = getDatiTemplateLetteraOrdinanza(request.getIdOrdinanza());		
			response.setFunzionario(this.ricavaInizialiFunzionario(cnmTUser.getNome(), cnmTUser.getCognome()));					
			break;
		case REPORT_LETTERA_RATEIZZAZIONE:
			response = getDatiTemplateRateizzazione(request.getIdPiano());
			break;
		case REPORT_LETTERA_SOLLECITO:
			response = getDatiTemplateSollecitoPagamento(request.getIdSollecito());
			break;
		case REPORT_LETTERA_ACCOMPAGNAMENTO_ANNULLAMENTO:			
			response = getDatiTemplateLetteraOrdinanza(request.getIdOrdinanza());			
			response.setFunzionario(this.ricavaInizialiFunzionario(cnmTUser.getNome(), cnmTUser.getCognome()));	
			break;
		case REPORT_LETTERA_SOLLECITO_RATE:
			response = getDatiTemplateSollecitoPagamento(request.getIdSollecito());
			response.setFunzionario(this.ricavaInizialiFunzionario(cnmTUser.getNome(), cnmTUser.getCognome()));		
			break;
		default:
			throw new IllegalArgumentException("codice report non trovato");
		}

		return response;
	}

	@Override
	public byte[] stampaTemplate(DatiTemplateRequest request, UserDetails user) {
		if (request.getCodiceTemplate() == null)
			throw new IllegalArgumentException("codice template non valorizzato");

		Report report = Report.getByCodiceFrontend(request.getCodiceTemplate());

		
		byte[] response;
		CnmTAllegato cnmTAllegato;
		switch (report) {
		case REPORT_CONVOCAZIONE_AUDIZIONE:
			List<CnmTAllegato> cnmTAllegatos2;
			if(request.getIdVerbale()==null) {
				response = stampaTemplateByCodice(request, report.getCodiceDB(), getDatiTemplateConvocazioneleAudizione(request.getIdVerbaleSoggettoList()), user);
				cnmTAllegatos2 = allegatoVerbaleSoggettoService.salvaConvocazioneAudizione(request.getIdVerbaleSoggettoList(), response, user);
			}else {
				// 20210906 PP - convocazione audizione soggetti esterni
				response = stampaTemplateByCodice(request, report.getCodiceDB(), getDatiTemplateConvocazioneAudizioneEsterni(request.getIdVerbale(), request.getSoggettoList()), user);
				cnmTAllegatos2 = allegatoVerbaleSoggettoService.salvaConvocazioneAudizioneEsterni(request.getIdVerbale(), request.getSoggettoList(), response, user);
			}
			cnmTAllegato = cnmTAllegatos2.get(0);
			break;
		case REPORT_VERBALE_AUDIZIONE:
			response = stampaTemplateByCodice(request, report.getCodiceDB(), getDatiTemplateVerbaleAudizione(request.getIdVerbaleSoggettoList()), user);
			List<CnmTAllegato> cnmTAllegatos = allegatoVerbaleSoggettoService.salvaVerbaleAudizione(request.getIdVerbaleSoggettoList(), response, user);
			cnmTAllegato = cnmTAllegatos.get(0);
			break;
		case REPORT_LETTERA_ACCOMPAGNAMENTO_ARCHIVIAZIONE:
			response = stampaTemplateByCodice(request, report.getCodiceDB(), getDatiTemplateLetteraOrdinanza(request.getIdOrdinanza()), user);
			cnmTAllegato = allegatoOrdinanzaService.salvaLetteraOrdinanza(request.getIdOrdinanza(), response, user);
			break;
		case REPORT_LETTERA_ACCOMPAGNAMENTO_INGIUNZIONE:
			response = stampaTemplateByCodice(request, report.getCodiceDB(), getDatiTemplateLetteraOrdinanza(request.getIdOrdinanza()), user);
			cnmTAllegato = allegatoOrdinanzaService.salvaLetteraOrdinanza(request.getIdOrdinanza(), response, user);
			break;
		case REPORT_LETTERA_RATEIZZAZIONE:
			response = stampaTemplateByCodice(request, report.getCodiceDB(), getDatiTemplateRateizzazione(request.getIdPiano()), user);
			cnmTAllegato = allegatoPianoRateizzazioneService.salvaLetteraPiano(request.getIdPiano(), response, user);
			break;
		case REPORT_LETTERA_SOLLECITO:
			response = stampaTemplateByCodice(request, report.getCodiceDB(), getDatiTemplateSollecitoPagamento(request.getIdSollecito()), user);
			cnmTAllegato = allegatoSollecitoService.salvaLetteraSollecito(request.getIdSollecito(), response, user);
			break;
		case REPORT_LETTERA_ACCOMPAGNAMENTO_ANNULLAMENTO:	
			response = stampaTemplateByCodice(request, report.getCodiceDB(), getDatiTemplateLetteraOrdinanza(request.getIdOrdinanza()), user);
			cnmTAllegato = allegatoOrdinanzaService.salvaLetteraOrdinanza(request.getIdOrdinanza(), response, user);
			break;
		case REPORT_LETTERA_SOLLECITO_RATE:
			response = stampaTemplateByCodice(request, report.getCodiceDB(), getDatiTemplateSollecitoPagamento(request.getIdSollecito()), user);
			cnmTAllegato = allegatoSollecitoService.salvaLetteraSollecitoRate(request.getIdSollecito(), response, user);
			break;
		default:
			throw new IllegalArgumentException("codice report non trovato");
		}

		// 20210114 - JIRA 120 - non aggiunge numProtocollo se allegato pregresso
		if (cnmTAllegato != null && !cnmTAllegato.isFlagDocumentoPregresso())
			response = commonAllegatoService.addProtocolloToDocument(response, cnmTAllegato.getNumeroProtocollo(), cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato());

		return response;

	}

	@Override
	public DatiTemplateVO nomiTemplate(DatiTemplateRequest request, UserDetails userDetails) {
		if (request.getCodiceTemplate() == null)
			throw new IllegalArgumentException("codice template non valorizzato");

		Report report = Report.getByCodiceFrontend(request.getCodiceTemplate());
		DatiTemplateVO response = null;

		switch (report) {
		case REPORT_CONVOCAZIONE_AUDIZIONE:
			response = allegatoVerbaleSoggettoService.nomeConvocazioneAudizione(request.getIdVerbaleSoggettoList());
			break;
		case REPORT_VERBALE_AUDIZIONE:
			response = allegatoVerbaleSoggettoService.nomeVerbaleAudizione(request.getIdVerbaleSoggettoList());
			break;
		case REPORT_LETTERA_ACCOMPAGNAMENTO_ARCHIVIAZIONE:
			response = allegatoOrdinanzaService.nomeLetteraOrdinanza(request.getIdOrdinanza());
			break;
		case REPORT_LETTERA_ACCOMPAGNAMENTO_INGIUNZIONE:
			response = allegatoOrdinanzaService.nomeLetteraOrdinanza(request.getIdOrdinanza());
			break;
		case REPORT_LETTERA_RATEIZZAZIONE:
			response = allegatoPianoRateizzazioneService.nomeLetteraPiano(request.getIdPiano());
			break;
		case REPORT_LETTERA_SOLLECITO:
			response = allegatoSollecitoService.nomeLetteraSollecito(request.getIdSollecito());
			break;
		case REPORT_LETTERA_ACCOMPAGNAMENTO_ANNULLAMENTO:
			response = allegatoOrdinanzaService.nomeLetteraOrdinanza(request.getIdOrdinanza());
			break;
		case REPORT_LETTERA_SOLLECITO_RATE:
			response = allegatoSollecitoService.nomeLetteraSollecitoRate(request.getIdSollecito());
			break;
		default:
			throw new IllegalArgumentException("codice report non trovato");
		}
		return response;
	}

	// 20200824_LC uovo type per gestione documento multiplo
	@Override
	public List<DocumentoScaricatoVO> downloadTemplate(DatiTemplateRequest request, UserDetails userDetails) {
		if (request.getCodiceTemplate() == null)
			throw new IllegalArgumentException("codice template non valorizzato");

		Report report = Report.getByCodiceFrontend(request.getCodiceTemplate());
		
		List<DocumentoScaricatoVO> response = null;

		switch (report) {
		case REPORT_CONVOCAZIONE_AUDIZIONE:
			response = allegatoVerbaleSoggettoService.downloadConvocazioneAudizione(request.getIdVerbaleSoggettoList());
			break;
		case REPORT_VERBALE_AUDIZIONE:
			response = allegatoVerbaleSoggettoService.downloadVerbaleAudizione(request.getIdVerbaleSoggettoList());
			break;
		case REPORT_LETTERA_ACCOMPAGNAMENTO_ARCHIVIAZIONE:
			response = allegatoOrdinanzaService.downloadLetteraOrdinanza(request.getIdOrdinanza());
			break;
		case REPORT_LETTERA_ACCOMPAGNAMENTO_INGIUNZIONE:
			response = allegatoOrdinanzaService.downloadLetteraOrdinanza(request.getIdOrdinanza());
			break;
		case REPORT_LETTERA_RATEIZZAZIONE:
			response = allegatoPianoRateizzazioneService.downloadLetteraPiano(request.getIdPiano());
			break;
		case REPORT_LETTERA_SOLLECITO:
			response = allegatoSollecitoService.downloadLetteraSollecito(request.getIdSollecito());
			break;
		case REPORT_LETTERA_ACCOMPAGNAMENTO_ANNULLAMENTO:
			response = allegatoOrdinanzaService.downloadLetteraOrdinanza(request.getIdOrdinanza());
			break;
		case REPORT_LETTERA_SOLLECITO_RATE:
			response = allegatoSollecitoService.downloadLetteraSollecito(request.getIdSollecito());
			break;
		default:
			throw new IllegalArgumentException("codice report non trovato");
		}
		return response;
	}
	
	private String ricavaInizialiFunzionario(String nome, String Cognome) {
		String iniziali= "";		
		
		if(nome == null || Cognome == null ||
		   nome.isEmpty()|| Cognome.isEmpty()) {
			return iniziali;
		}	
		
		//Gestione nominativo costituito da piu nomi		
		String[] listaNomi = nome.split(" ");
		for(String nomeItem : listaNomi) {
			iniziali=iniziali.concat(nomeItem.substring(0, 1).toUpperCase());
		}
		
		iniziali = iniziali.concat(Cognome.substring(0, 1));
		
		return iniziali;
	}

	private byte[] stampaTemplateByCodice(DatiTemplateRequest request, String codice, DatiTemplateVO dati, UserDetails user) {
		DatiTemplateCompilatiVO datiTemplateCompilatiVO = request.getDatiTemplateCompilatiVO();
		if (datiTemplateCompilatiVO == null)
			throw new IllegalArgumentException("ddatiTemplateCompilatiVO = null");

		byte[] report;
		Map<String, Object> jasperParam = new HashMap<>();

		// compilati da utente
		jasperParam.put("oggetto", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getOggetto()));
		jasperParam.put("descrizione", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getDescrizione()));
		jasperParam.put("fn", StringUtils.trimToEmpty(dati.getFn()));

		jasperParam.put("articolo", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getArticolo()));

		jasperParam.put("indirizzoAvvocato", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getIndirizzoAvvocato()));
		jasperParam.put("comuneAvvocato", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getComuneAvvocato()));
		jasperParam.put("mailAvvocato", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getMailAvvocato()));
		jasperParam.put("studioAvvocato", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getStudioAvvocato()));
		jasperParam.put("scadenzaPagamento", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getScadenzaPagamento()));
		jasperParam.put("riferimentoNormativo", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getRiferimentoNormativo()));

		troncaRagioneSociale(dati.getListaSoggetti());
		// caricati da db
		
		
		// 20210225_LC  (jira119 / Evolutiva E4) - CAP nei documenti creati da Conam	
		// workaround (per non modificare template jasper): CAP concatenato prima del comuneResidenza
		// 20210707_LC Jira 154 solo se comune e provincia presenti, altrimenti li istanzia vuoti (il report li vuole)
		// 20211124_LC Jira 177 "" a posto di possibili null
		for (SoggettoVO soggetto : dati.getListaSoggetti()) {
			
			if (soggetto.getComuneResidenza() != null) {
				String cap = soggetto.getCap() != null ? soggetto.getCap() : "";
				String comune = soggetto.getComuneResidenza().getDenominazione() != null ? soggetto.getComuneResidenza().getDenominazione() : "";
				soggetto.getComuneResidenza().setDenominazione(cap + " " + comune);
			} else {
				soggetto.setComuneResidenza(new ComuneVO());
				soggetto.getComuneResidenza().setDenominazione(""); 
			}
			
			// 20210315_LC Jira 124 - corretta formattazione cap + comune + provincia: da denominazione proncia a "(SIGLA)"
			if (soggetto.getProvinciaResidenza() != null) {
				String provincia = soggetto.getProvinciaResidenza().getSigla() != null ? soggetto.getProvinciaResidenza().getSigla() : "";
				soggetto.getProvinciaResidenza().setDenominazione("(" + provincia + ")");
			} else {
				soggetto.setProvinciaResidenza(new ProvinciaVO());
				soggetto.getProvinciaResidenza().setDenominazione("");
			}
			
			// 20211128_LC Jira 177 - gestione civico null
			if (soggetto.getCivicoResidenza() == null) soggetto.setCivicoResidenza(""); ;
		}

		
		jasperParam.put("listaSoggetti", new JRBeanCollectionDataSource(dati.getListaSoggetti()));
		jasperParam.put("importoTotale", dati.getImportoTotale());
		jasperParam.put("numeroRate", dati.getNumeroRate());
		jasperParam.put("scadenzaDefinita", dati.getScadenzaDefinita());
		jasperParam.put("importoPrimaRata", dati.getImportoPrimaRata());
		jasperParam.put("scadenzaPrimaRata", dati.getScadenzaPrimaRata());
		jasperParam.put("importoAltreRate", dati.getImportoAltreRate());
		jasperParam.put("importoUltimaRata", dati.getImportoUltimaRata());
		jasperParam.put("dirigente", StringUtils.trimToEmpty(dati.getDirigente()));
		jasperParam.put("classificazione", StringUtils.trimToEmpty(dati.getClassificazione()));
		
		CnmTUser cnmTUser = cnmTUserRepository.findOne(user.getIdUser());	
		jasperParam.put("funzionario", this.ricavaInizialiFunzionario(cnmTUser.getNome(), cnmTUser.getCognome()));		
		
		jasperParam.put("idMailSettoreTributi", dati.getMailSettoreTributi());

		jasperParam.put("processiVerbali", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getProcessiVerbali()));
		jasperParam.put("nomeAvvocato", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getNomeAvvocato()));
		jasperParam.put("indirizzo", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getIndirizzo()));
		jasperParam.put("oraInizio", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getOraInizio()));
		jasperParam.put("anno", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getAnno()));
		jasperParam.put("mese", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getMese()));
		jasperParam.put("giorno", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getGiorno()));
		jasperParam.put("dichiara", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getDichiara()));
		jasperParam.put("intestazione", getIntestazioneSoggetti(dati.getListaSoggetti()));
		jasperParam.put("comparizione", getComparizione(dati.getListaSoggetti()));
		jasperParam.put("soggettiString", getSoggettiConcatenati(dati.getListaSoggetti()));
		
		// 20210308_LC lotto2scenario7 lettera ordinanza annullamento
		if (codice.equals(Report.REPORT_LETTERA_ACCOMPAGNAMENTO_ANNULLAMENTO.getCodiceDB())) {			
							
			// editabili dall'utente - 20210330 se NULL : ""
			jasperParam.put("direzioneLettera", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getDirezioneLettera()) 
					? request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getDirezioneLettera() : "");	
			jasperParam.put("settoreLettera", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getSettoreLettera()) 
					? request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getSettoreLettera() : "");	
			jasperParam.put("mailLettera", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getMailLettera()) 
					? request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getMailLettera() : "");	
			jasperParam.put("dataLettera", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getDataLettera()) 
					? request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getDataLettera() : "");	
			jasperParam.put("oggettoLettera", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getOggettoLettera()) 
					? request.getDatiTemplateCompilatiVO().getOggettoLettera() : "");	
			jasperParam.put("corpoLettera", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getCorpoLettera()) 
					? request.getDatiTemplateCompilatiVO().getCorpoLettera() : "");			
			jasperParam.put("salutiLettera", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getSalutiLettera()) 
					? request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getSalutiLettera() : "");	
			jasperParam.put("dirigenteLettera", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getDirigenteLettera()) 
					? request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getDirigenteLettera() : "");	
			jasperParam.put("bloccoFirmaOmessa", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getBloccoFirmaOmessa()) 
					? request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getBloccoFirmaOmessa() : "");	
			jasperParam.put("inizialiLettera", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getInizialiLettera()) 
					? request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getInizialiLettera() : "");				
			jasperParam.put("sedeEnteRiga1", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getSedeEnteRiga1()) 
					? request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getSedeEnteRiga1() : "");	
			jasperParam.put("sedeEnteRiga2", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getSedeEnteRiga2()) 
					? request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getSedeEnteRiga2() : "");	
			jasperParam.put("sedeEnteRiga3", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getSedeEnteRiga3()) 
					? request.getDatiTemplateCompilatiVO().getDatiLetteraAnnullamento().getSedeEnteRiga3() : "");	
			
			
			jasperParam.put("indirizzoOrganoAccertatoreRiga1", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga1()) 
					? request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga1() : "");				
			jasperParam.put("indirizzoOrganoAccertatoreRiga2", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga2()) 
					? request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga2() : "");				
			jasperParam.put("indirizzoOrganoAccertatoreRiga3", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga3()) 
					? request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga3() : "");				
			jasperParam.put("testoLibero", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getTestoLibero()) 
					? request.getDatiTemplateCompilatiVO().getTestoLibero() : "");			
			
			if (StringUtils.isBlank(request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga1()) &&
					StringUtils.isBlank(request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga2()) &&
					StringUtils.isBlank(request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga3()) &&
					StringUtils.isBlank(request.getDatiTemplateCompilatiVO().getTestoLibero())) {
				jasperParam.put("intestazioneOrganoAccertatore", "");				
			} else {
				jasperParam.put("intestazioneOrganoAccertatore", "e p.c.");		
			}
			// --
			
		}
		
		
		// 20210326_LC lotto2scenarioC lettera ordinanza archiviazione
		if (codice.equals(Report.REPORT_LETTERA_ACCOMPAGNAMENTO_ARCHIVIAZIONE.getCodiceDB())) {	

			jasperParam.put("indirizzoOrganoAccertatoreRiga1", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga1()) 
					? request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga1() : "");				
			jasperParam.put("indirizzoOrganoAccertatoreRiga2", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga2()) 
					? request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga2() : "");			
			jasperParam.put("indirizzoOrganoAccertatoreRiga3", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga3()) 
					? request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga3() : "");				
			jasperParam.put("testoLibero", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getTestoLibero()) 
					? request.getDatiTemplateCompilatiVO().getTestoLibero() : "");			
			
			if (StringUtils.isBlank(request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga1()) &&
					StringUtils.isBlank(request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga2()) &&
					StringUtils.isBlank(request.getDatiTemplateCompilatiVO().getIndirizzoOrganoAccertatoreRiga3()) &&
					StringUtils.isBlank(request.getDatiTemplateCompilatiVO().getTestoLibero())) {
				jasperParam.put("intestazioneOrganoAccertatore", "");				
			} else {
				jasperParam.put("intestazioneOrganoAccertatore", "e p.c.");		
			}
			// --
			
		}

		
		
		// 20210401_LC lotto2scenario5 lettera sollecito rate
		if (codice.equals(Report.REPORT_LETTERA_SOLLECITO_RATE.getCodiceDB())) {	
			
			jasperParam.put("oggettoLettera", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getOggettoLettera()) 
					? request.getDatiTemplateCompilatiVO().getOggettoLettera() : "");	
			jasperParam.put("corpoLettera", StringUtils.isNotBlank(request.getDatiTemplateCompilatiVO().getCorpoLettera()) 
					? request.getDatiTemplateCompilatiVO().getCorpoLettera() : "");		
			
			jasperParam.put("dirigenteLettera", dati.getDirigenteLettera());
			
		}
		

		if (codice.equals(Report.REPORT_CONVOCAZIONE_AUDIZIONE.getCodiceDB())) {
			if (dati.getListaSoggetti().size() > 1) {
				jasperParam.put("sp1", "alle S.S.V.V.");
				jasperParam.put("sp2", " sono convocati");
				jasperParam.put("sp3", "le S.S.V.V. non comparissero");
			} else {
				jasperParam.put("sp1", "alla S.V.");
				jasperParam.put("sp2", " è convocata");
				jasperParam.put("sp3", "la S.V. non comparisse");
			}
		}

		
		
		
		try {
			report = utilsReport.printReportPDF(codice, jasperParam, null);
		} catch (PrintException | IOException | SQLException | JRException e) {
			throw new RuntimeException(e);
		}
		return report;
	}

	private void troncaRagioneSociale(List<SoggettoVO> listaSoggetti) {
		for (SoggettoVO sog : listaSoggetti)
			if (StringUtils.isNotEmpty(sog.getRagioneSociale()) && sog.getRagioneSociale().length() > 50)
				sog.setRagioneSociale(StringUtils.abbreviate(sog.getRagioneSociale(), 50));
	}

	private String getSoggettiConcatenati(List<SoggettoVO> listaSoggetti) {
		int size = listaSoggetti.size();
		StringBuilder nome = new StringBuilder();
		int count = 0;
		for (SoggettoVO s : listaSoggetti) {
			StringBuilder nomeLocal = new StringBuilder(s.getPersonaFisica() ? s.getNome() + " " + s.getCognome() : s.getRagioneSociale());
			if (size != count + 1)
				nomeLocal.append(", ");
			nome.append(nomeLocal);
			count++;
		}
		return nome.toString();

	}

	private String getComparizione(List<SoggettoVO> listaSoggetti) {
		java.util.Collection<SoggettoVO> personeFisiche = getPersoneFisiche(listaSoggetti);
		java.util.Collection<SoggettoVO> personeGiuridiche = personeGiuridiche(listaSoggetti);

		int size = listaSoggetti.size();
		boolean isPersonaFisica = personeFisiche.size() == 1 && personeFisiche.size() == size;
		boolean isPersonaGiuridica = personeGiuridiche.size() == 1 && personeGiuridiche.size() == size;

		if (isPersonaFisica)
			return listaSoggetti.get(0).getSesso().equals("M") ? " è comparso il signor " : " è comparsa la signora ";
		else if (isPersonaGiuridica)
			return " è comparsa l'azienda ";
		else if (personeFisiche.size() == size)
			return "sono comparsi i signori";
		else if (personeGiuridiche.size() == size)
			return "sono comparse le aziende";
		else
			return "sono comparsi i soggetti";

	}

	private java.util.Collection<SoggettoVO> personeGiuridiche(List<SoggettoVO> listaSoggetti) {
		return Collections2.filter(listaSoggetti, new Predicate<SoggettoVO>() {
			@Override
			public boolean apply(SoggettoVO arg0) {
				return !arg0.getPersonaFisica();
			}
		});
	}

	private java.util.Collection<SoggettoVO> getPersoneFisiche(List<SoggettoVO> listaSoggetti) {
		return Collections2.filter(listaSoggetti, new Predicate<SoggettoVO>() {
			@Override
			public boolean apply(SoggettoVO arg0) {
				return arg0.getPersonaFisica();
			}
		});
	}

	private String getIntestazioneSoggetti(List<SoggettoVO> listaSoggetti) {
		java.util.Collection<SoggettoVO> personeFisiche = getPersoneFisiche(listaSoggetti);
		java.util.Collection<SoggettoVO> personeGiuridiche = personeGiuridiche(listaSoggetti);

		int size = listaSoggetti.size();
		boolean isPersonaFisica = personeFisiche.size() == 1 && personeFisiche.size() == size;
		boolean isPersonaGiuridica = personeGiuridiche.size() == 1 && personeGiuridiche.size() == size;

		if (isPersonaFisica)
			return listaSoggetti.get(0).getSesso().equals("M") ? "Al Signor" : "Alla Sig.ra";
		else if (isPersonaGiuridica)
			return "All'Azienda";
		else if (personeFisiche.size() == size)
			return "Ai Signori";
		else if (personeGiuridiche.size() == size)
			return "Alle Aziende";
		else
			return "Ai Soggetti";
	}

	private DatiTemplateVO getDatiTemplateRateizzazione(Integer idPiano) {
		if (idPiano == null)
			throw new IllegalArgumentException("idPiano non valorizzato");

		CnmTPianoRate cnmTPianoRate = cnmTPianoRateRepository.findOne(idPiano);

		return datiTemplateResponseEntityMapper.mapEntityToVO(cnmTPianoRate);
	}

	private DatiTemplateVO getDatiTemplateLetteraOrdinanza(Integer idOrdinanza) {
		if (idOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza non valorizzato");

		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);

		return datiTemplateResponseEntityMapper.mapEntityToVO(cnmTOrdinanza);
	}

	private DatiTemplateVO getDatiTemplateSollecitoPagamento(Integer idSollecito) {
		if (idSollecito == null)
			throw new IllegalArgumentException("idSollecito non valorizzato");

		CnmTSollecito cnmTSollecito = cnmTSollecitoRepository.findOne(idSollecito);

		return datiTemplateResponseEntityMapper.mapEntityToVO(cnmTSollecito);
	}

	private DatiTemplateVO getDatiTemplateVerbaleAudizione(List<Integer> idVerbaleSoggettoList) {
		if (idVerbaleSoggettoList == null || idVerbaleSoggettoList.isEmpty())
			throw new IllegalArgumentException("idVerbaleSoggettoList non valorizzato");

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = (List<CnmRVerbaleSoggetto>) cnmRVerbaleSoggettoRepository.findAll(idVerbaleSoggettoList);
		return datiTemplateResponseEntityMapper.mapEntityToVO(cnmRVerbaleSoggettoList);
	}

	private DatiTemplateVO getDatiTemplateConvocazioneleAudizione(List<Integer> idVerbaleSoggettoList) {
		if (idVerbaleSoggettoList == null || idVerbaleSoggettoList.isEmpty())
			throw new IllegalArgumentException("idVerbaleSoggettoList non valorizzato");

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = (List<CnmRVerbaleSoggetto>) cnmRVerbaleSoggettoRepository.findAll(idVerbaleSoggettoList);
		CnmCParametro parametro = cnmCParametroRepository.findByIdParametro(new Long(2));
		DatiTemplateVO datiTemplate = datiTemplateResponseEntityMapper.mapEntityToVO(cnmRVerbaleSoggettoList);
		datiTemplate.setDirigente(parametro.getValoreString());
		return datiTemplate;
	}
	
	private DatiTemplateVO getDatiTemplateConvocazioneAudizioneEsterni(Integer idVerbale, List<SoggettoVO> soggettoList) {
		if (soggettoList == null || soggettoList.isEmpty())
			throw new IllegalArgumentException("soggettoList non valorizzato");

		CnmCParametro parametro = cnmCParametroRepository.findByIdParametro(new Long(2));
		DatiTemplateVO datiTemplate = datiTemplateResponseEntityMapper.mapEntityToVO(idVerbale, soggettoList);
		datiTemplate.setDirigente(parametro.getValoreString());
		return datiTemplate;
	}

	@Override
	public MessageVO getMessaggioByCodice(String codice) {
		CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(codice);
		MessageVO messaggioVO = null;
		
		if(cnmDMessaggio != null) {
			messaggioVO = new MessageVO(cnmDMessaggio.getDescMessaggio(), cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
		}
		
		return messaggioVO;
	}
}
