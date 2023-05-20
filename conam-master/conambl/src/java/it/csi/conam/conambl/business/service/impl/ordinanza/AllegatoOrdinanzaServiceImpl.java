/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.ordinanza;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.csi.conam.conambl.business.facade.EPayServiceFacade;
import it.csi.conam.conambl.business.facade.StadocServiceFacade;
import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.common.CommonBollettiniService;
import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.ordinanza.AllegatoOrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.OrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.StatoPagamentoOrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.UtilsOrdinanza;
import it.csi.conam.conambl.business.service.util.UtilsCodeWriter;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.common.*;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.AllegatoEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.SoggettoEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.TipoAllegatoEntityMapper;
import it.csi.conam.conambl.integration.mapper.ws.epay.EPayWsInputMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.integration.specification.CnmDTipoAllegatoSpecification;
import it.csi.conam.conambl.jasper.BollettinoJasper;
import it.csi.conam.conambl.request.SalvaAllegatiMultipliRequest;
import it.csi.conam.conambl.request.SalvaAllegatiProtocollatiRequest;
import it.csi.conam.conambl.request.SalvaAllegatoRequest;
import it.csi.conam.conambl.request.ordinanza.SalvaAllegatoOrdinanzaRequest;
import it.csi.conam.conambl.request.ordinanza.SalvaAllegatoOrdinanzaVerbaleSoggettoRequest;
import it.csi.conam.conambl.scheduled.AllegatoScheduledService;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.*;
import it.csi.conam.conambl.vo.IsCreatedVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.template.DatiTemplateVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoFieldVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoMultiploVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class AllegatoOrdinanzaServiceImpl implements AllegatoOrdinanzaService {

	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private TipoAllegatoEntityMapper tipoAllegatoEntityMapper;
	@Autowired
	private CnmDTipoAllegatoRepository cnmDTipoAllegatoRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmRAllegatoOrdVerbSogRepository cnmRAllegatoOrdVerbSogRepository;
	@Autowired
	private CnmRAllegatoOrdinanzaRepository cnmRAllegatoOrdinanzaRepository;
	@Autowired
	private CommonAllegatoService commonAllegatoService;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private AllegatoEntityMapper allegatoEntityMapper;
	@Autowired
	private CnmDElementoElencoRepository cnmDElementoElencoRepository;
	@Autowired
	private CnmDStatoAllegatoRepository cnmDStatoAllegatoRepository;
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;

	@Autowired
	private CnmDStatoOrdVerbSogRepository cnmDStatoOrdVerbSogRepository;
	@Autowired
	private CnmTAllegatoFieldRepository cnmTAllegatoFieldRepository;
	@Autowired
	private CommonBollettiniService commonBollettiniService;
	@Autowired
	private EPayServiceFacade ePayServiceFacade;
	@Autowired
	private EPayWsInputMapper ePayWsInputMapper;
	@Autowired
	private CnmCParametroRepository cnmCParametroRepository;
	@Autowired
	private SoggettoEntityMapper soggettoEntityMapper;
	@Autowired
	private UtilsCodeWriter utilsCodeWriter;
	@Autowired
	private UtilsOrdinanza utilsOrdinanza;
	@Autowired
	private UtilsDoqui utilsDoqui;
	@Autowired
	private AllegatoScheduledService allegatoScheduledService;
	@Autowired
	private CnmDStatoOrdinanzaRepository cnmDStatoOrdinanzaRepository;
	@Autowired
	private StatoPagamentoOrdinanzaService statoPagamentoOrdinanzaService;
	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;
	@Autowired
	private OrdinanzaService ordinanzaService;
	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;
	
	@Autowired
	private CommonSoggettoService commonSoggettoService;

	private static final Logger logger = Logger.getLogger(AllegatoOrdinanzaServiceImpl.class);

	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiOrdinanzaByCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza) {
		List<TipoAllegatoVO> arr = new ArrayList<>();
		List<CnmRAllegatoOrdinanza> cnmRAllegatoOrdinanzaList = cnmRAllegatoOrdinanzaRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		if (cnmRAllegatoOrdinanzaList != null && !cnmRAllegatoOrdinanzaList.isEmpty()) {
			for (CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza : cnmRAllegatoOrdinanzaList) {
				arr.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmRAllegatoOrdinanza.getCnmTAllegato().getCnmDTipoAllegato()));
			}
		}

		return arr;
	}

	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliByOrdinanza(Integer idOrdinanza, String tipoDocumento, boolean aggiungiCategoriaEmail) {
	
		if (idOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza è null");
		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new SecurityException("cnmTOrdinanza non trovato");

		TipoAllegato tipo = TipoAllegato.getTipoAllegatoByTipoDocumento(tipoDocumento);
		Long idTipoDocumento = tipo != null ? tipo.getId() : null;
		
		//CONAM-85: nessun filtro in base allo stato dell'ordinanza
		List<TipoAllegatoVO> listAllegabili = tipoAllegatoEntityMapper.mapListEntityToListVO(cnmDTipoAllegatoRepository.findAll(CnmDTipoAllegatoSpecification.findBy(//
				idTipoDocumento, // idtipoallegato
				null, // categoria non necessaria per i tipi
				Constants.ID_UTILIZZO_ALLEGATO_ORDINANZA, // utilizzo
				null, // stato verbale
				null // stato ordinanza
				//cnmTOrdinanza.getCnmDStatoOrdinanza() 
		)));

		List<TipoAllegatoVO> listAllegati = getTipologiaAllegatiOrdinanzaByCnmTOrdinanza(cnmTOrdinanza);

		if (tipoDocumento != null && tipoDocumento.equals(TipoAllegato.OPPOSIZIONE_GIURISDIZIONALE.getTipoDocumento())) {
			if (cnmTOrdinanza.getCnmDTipoOrdinanza().getIdTipoOrdinanza() == Constants.ID_TIPO_ORDINANZA_ARCHIVIATO) {
				return new ArrayList<>();
			}

			//20200729_ET aggiunto blocco per gestione tipi doc EMAIL
			if(aggiungiCategoriaEmail)
				listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.EMAIL_GIURISDIZIONALE_ORD.getId())));
			
			long nSoggetti = cnmROrdinanzaVerbSogRepository.countSoggettiByCnmTOrdinanza(cnmTOrdinanza);
			if (listAllegati != null && !listAllegati.isEmpty()) {
				Collection<TipoAllegatoVO> result = Collections2.filter(listAllegati, new Predicate<TipoAllegatoVO>() {
					@Override
					public boolean apply(TipoAllegatoVO input) {
						return input.getId() == TipoAllegato.OPPOSIZIONE_GIURISDIZIONALE.getId();
					}
				});
				long countRicorsoAllegati = 0;
				if (result != null && !result.isEmpty())
					countRicorsoAllegati = result.size();

				if (nSoggetti > countRicorsoAllegati)
					return listAllegabili;
				else {
					//20200806_ET aggiunto if per risolvere il problema di tendina per la scelta doc vuota in caso di email master
					if(aggiungiCategoriaEmail)
						return new ArrayList<>(Arrays.asList(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.EMAIL_GIURISDIZIONALE_ORD.getId()))));
					else
						return new ArrayList<>();
				}
			}

			return listAllegabili;
		}

		if (tipoDocumento != null && tipoDocumento.equals(TipoAllegato.COMUNICAZIONI_DALLA_CANCELLERIA.getTipoDocumento())) {
			List<TipoAllegatoVO> all = this.getTipologiaAllegatiAllegabiliByOrdinanza(idOrdinanza, TipoAllegato.COMUNICAZIONI_ALLA_CANCELLERIA.getTipoDocumento(), aggiungiCategoriaEmail);
			for (TipoAllegatoVO a : all) {
				listAllegabili.add(a);
			}
			return listAllegabili;
		}
		if (tipoDocumento != null && tipoDocumento.equals(TipoAllegato.COMUNICAZIONI_ALLA_CANCELLERIA.getTipoDocumento())) {

			return listAllegabili;
		}

		listAllegabili.removeAll(listAllegati);

		return listAllegabili;
	}

	private List<TipoAllegatoVO> getTipologiaAllegatiOrdinanzaSoggettByCnmTOrdinanzaSoggetto(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		if (cnmROrdinanzaVerbSog == null)
			throw new SecurityException("cnmROrdinanzaVerbSog non trovato");

		List<TipoAllegatoVO> arr = new ArrayList<>();
		List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogList = cnmRAllegatoOrdVerbSogRepository.findByCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);
		if (cnmRAllegatoOrdVerbSogList != null && !cnmRAllegatoOrdVerbSogList.isEmpty()) {
			for (CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog : cnmRAllegatoOrdVerbSogList) {
				arr.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmRAllegatoOrdVerbSog.getCnmTAllegato().getCnmDTipoAllegato()));
			}
		}

		return arr;
	}

	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliByOrdinanzaSoggetto(List<Integer> idSoggettoOrdinanzaList, String tipoDocumento, boolean aggiungiCategoriaEmail) {
		if (idSoggettoOrdinanzaList == null)
			throw new IllegalArgumentException("idSoggettoOrdinanza e' null");

		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = (List<CnmROrdinanzaVerbSog>) cnmROrdinanzaVerbSogRepository.findAll(idSoggettoOrdinanzaList);
		if (cnmROrdinanzaVerbSogList == null || cnmROrdinanzaVerbSogList.isEmpty())
			throw new SecurityException("cnmROrdinanzaVerbSog non trovato");

		List<TipoAllegatoVO> listAllegabili = new ArrayList<>();
		for (CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog : cnmROrdinanzaVerbSogList) {
			List<TipoAllegatoVO> list = getTipologiaAllegatiAllegabiliByOrdinanzaSoggetto(cnmROrdinanzaVerbSog, tipoDocumento, aggiungiCategoriaEmail);
			for (TipoAllegatoVO t : list) {
				if (!listAllegabili.contains(t))
					listAllegabili.add(t);
			}
		}

		return listAllegabili;
	}

	private List<TipoAllegatoVO> getTipologiaAllegatiAllegabiliByOrdinanzaSoggetto(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog, String tipoDocumento, boolean aggiungiCategoriaEmail) {
		TipoAllegato tipo = TipoAllegato.getTipoAllegatoByTipoDocumento(tipoDocumento);
		Long idTipoDocumento = tipo != null ? tipo.getId() : null;

		CnmTOrdinanza cnmTOrdinanza = cnmROrdinanzaVerbSog.getCnmTOrdinanza();
		List<TipoAllegatoVO> listAllegabili = tipoAllegatoEntityMapper.mapListEntityToListVO(cnmDTipoAllegatoRepository.findAll(CnmDTipoAllegatoSpecification.findBy(//
				idTipoDocumento, // idtipoallegato
				null, // categoria non necessaria per i tipi
				Constants.ID_UTILIZZO_ALLEGATO_ORDINANZA_SOGGETTO, // utilizzo
				null, // stato verbale
				cnmTOrdinanza.getCnmDStatoOrdinanza() // stato ordinanza
		)));

		if (tipoDocumento != null && tipoDocumento.equals(TipoAllegato.ISTANZA_RATEIZZAZIONE.getTipoDocumento()) && aggiungiCategoriaEmail) {
			//20200729_ET aggiunto blocco per gestione tipi doc EMAIL
			listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.EMAIL_GIURISDIZIONALE_ORD_SOGG.getId())));
			return listAllegabili;
		}

		List<TipoAllegatoVO> listAllegati;

		// se soggetto arch non allego nulla
		if (cnmROrdinanzaVerbSog.getCnmDStatoOrdVerbSog().getIdStatoOrdVerbSog() == Constants.ID_STATO_ORDINANZA_VERB_SOGG_ARCHIVIATO)
			listAllegati = listAllegabili;
		// se tipo valorizzato e ammetto multipli annullo tutti quelli allegati
		else if (tipo != null && tipo.isAllegabileMultiplo())
			listAllegati = new ArrayList<>();
		else
			listAllegati = getTipologiaAllegatiOrdinanzaSoggettByCnmTOrdinanzaSoggetto(cnmROrdinanzaVerbSog);

		listAllegabili.removeAll(listAllegati);
		
		//20200729_ET aggiunto blocco per gestione tipi doc EMAIL
		if (tipoDocumento != null && tipoDocumento.equals(TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getTipoDocumento()) && aggiungiCategoriaEmail) {
			listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.EMAIL_GIURISDIZIONALE_ORD_SOGG.getId())));
			return listAllegabili;
		}
		
		
		// 20210304_LC ordinanza di annullamento allegabile solo con la specifica funzionalita
		// verificare se non sia inutile (dovrebbe già non esser presente nella list)
		TipoAllegatoVO ordAnnArc = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId()));
		listAllegabili.remove(ordAnnArc);
		TipoAllegatoVO ordAnnIng = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId()));
		listAllegabili.remove(ordAnnIng);
		
		// 20210426_LC aggiunge istanza_allegato solo se istanza già presente
		TipoAllegatoVO istanza = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.ISTANZA_RATEIZZAZIONE.getId()));
		TipoAllegatoVO istanzaAllegato = tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.ISTANZA_ALLEGATO.getId()));
		listAllegabili.remove(istanzaAllegato);		
		if(listAllegati.contains(istanza)) {
			listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.ISTANZA_ALLEGATO.getId())));
		} 

		return listAllegabili;
	}

	@Override
	@Transactional
	public AllegatoVO salvaAllegatoOrdinanza(List<InputPart> data, List<InputPart> file, UserDetails userDetails, boolean pregresso) {
		Long idUser = userDetails.getIdUser();
		SalvaAllegatoOrdinanzaRequest request = commonAllegatoService.getRequest(data, file, SalvaAllegatoOrdinanzaRequest.class);
		byte[] byteFile = request.getFile();
		String fileName = request.getFilename();
		Long idTipoAllegato = request.getIdTipoAllegato();
		Integer idOrdinanza = request.getIdOrdinanza();
		List<AllegatoFieldVO> configAllegato = request.getAllegatoField();
		CnmTUser cnmTUser = cnmTUserRepository.findOne(idUser);

		// controlli sicurezza
		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new SecurityException("cnmTOrdinanza non trovato");

		if(!pregresso) {
			List<TipoAllegatoVO> allegati = getTipologiaAllegatiAllegabiliByOrdinanza(idOrdinanza, TipoAllegato.getTipoDocumentoByIdTipoDocumento(idTipoAllegato), false);
			TipoAllegatoVO allegato = Iterables.tryFind(allegati, UtilsTipoAllegato.findAllegatoInTipoAllegatiByIdTipoAllegato(idTipoAllegato)).orNull();
			if (allegato == null)
				throw new SecurityException("non è possibile allegare questo tipo  allegato");
		}

		if(!pregresso && !(TipoAllegato.RICEVUTA_PAGAMENTO_ORDINANZA.getId() == idTipoAllegato)) {
			// controllo dimensione allegato
			UploadUtils.checkDimensioneAllegato(byteFile);
		}
		// 20201026 PP- controllo se e' stato caricato un file firmato , con firma non valida senza firma
		utilsDoqui.checkFileSign(byteFile, fileName);
		
		CnmDStatoOrdinanza stato = cnmTOrdinanza.getCnmDStatoOrdinanza();
		
		boolean isProtocollazioneInUscita = (TipoAllegato.COMUNICAZIONI_ALLA_CANCELLERIA.getId() == idTipoAllegato) ? true : false;
		CnmTAllegato cnmTAllegato = salvaAllegatoOrdinanza(
			cnmTOrdinanza,
			byteFile,
			cnmTUser,
			fileName,
			configAllegato,
			TipoAllegato.getTipoDocumentoById(idTipoAllegato),
			true,
			isProtocollazioneInUscita,
			false
		);

		if(pregresso) {
			// riporto lo stato a quello precedente
			cnmTOrdinanza.setCnmDStatoOrdinanza(stato);
			cnmTOrdinanzaRepository.save(cnmTOrdinanza);
		}
		
		if (TipoAllegato.OPPOSIZIONE_GIURISDIZIONALE.getId() == idTipoAllegato) {
			CnmDStatoOrdinanza cnmDStatoOrdinanza = cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_RICORSO_IN_ATTO);
			ordinanzaService.saveSStatoOrdinanza(cnmTOrdinanza, cnmTUser);
			cnmTOrdinanza.setCnmDStatoOrdinanza(cnmDStatoOrdinanza);
			cnmTOrdinanza.setCnmTUser1(cnmTUser);
			cnmTOrdinanza.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));
			cnmTOrdinanzaRepository.save(cnmTOrdinanza);
		}

		return allegatoEntityMapper.mapEntityToVO(cnmTAllegato);
	}

	@Override
	public void salvaAllegatoOrdinanzaSoggetto(List<InputPart> data, List<InputPart> file, UserDetails userDetails, boolean pregresso) {
		Long idUser = userDetails.getIdUser();
		SalvaAllegatoOrdinanzaVerbaleSoggettoRequest request = commonAllegatoService.getRequest(data, file, SalvaAllegatoOrdinanzaVerbaleSoggettoRequest.class);
		byte[] byteFile = request.getFile();
		String fileName = request.getFilename();
		Long idTipoAllegato = request.getIdTipoAllegato() != null ? request.getIdTipoAllegato() : new Long(22);

		List<AllegatoFieldVO> configAllegato = request.getAllegatoField();
		CnmTUser cnmTUser = cnmTUserRepository.findOne(idUser);
		boolean nofile = false;

		// sicurezza
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = (List<CnmROrdinanzaVerbSog>) cnmROrdinanzaVerbSogRepository.findAll(request.getIdOrdinanzaVerbaleSoggetto());
		if (cnmROrdinanzaVerbSogList == null || cnmROrdinanzaVerbSogList.isEmpty())
			throw new SecurityException("cnmROrdinanzaVerbSog non trovato");

		// caso file fittizio
		if (fileName == null) {
			String s = "";
			for (AllegatoFieldVO all : configAllegato) {
				if (all.getFieldType().getId() == Constants.FIELD_TYPE_BOOLEAN) {
					s += all.getBooleanValue().toString() + " ";
				} else if (all.getFieldType().getId() == Constants.FIELD_TYPE_NUMERIC || all.getFieldType().getId() == Constants.FIELD_TYPE_ELENCO) {
					NumberFormat n = NumberFormat.getCurrencyInstance(Locale.ITALY);
					double money = all.getNumberValue().doubleValue();
					String string = n.format(money);
					s += "IMPORTO PAGATO: " + string + " ";
				} else if (all.getFieldType().getId() == Constants.FIELD_TYPE_STRING) {
					s += "CONTO CORRENTE VERSAMENTO: " + all.getStringValue().toString() + " ";
				} else if (all.getFieldType().getId() == Constants.FIELD_TYPE_DATA_ORA) {
					s += all.getDateTimeValue().toString() + " ";
				} else if (all.getFieldType().getId() == Constants.FIELD_TYPE_DATA) {
					s += "DATA PAGAMENTO: " + all.getDateValue().toString() + " ";
				}
			}
			CnmTSoggetto cnmTSoggetto = cnmROrdinanzaVerbSogList.get(0).getCnmRVerbaleSoggetto().getCnmTSoggetto();
			String cfPartitaIva = cnmTSoggetto.getCodiceFiscale() != null ? cnmTSoggetto.getCodiceFiscale()
					: (cnmTSoggetto.getCodiceFiscaleGiuridico() != null ? cnmTSoggetto.getCodiceFiscaleGiuridico() : cnmTSoggetto.getPartitaIva());
			byteFile = s.getBytes();
			fileName = "Promemoria_pagamento_" + verificaNome(cnmROrdinanzaVerbSogList.get(0).getCnmTOrdinanza().getNumDeterminazione()) + "_" + cfPartitaIva + ".txt";
			nofile = true;
		}

		List<TipoAllegatoVO> allegati = getTipologiaAllegatiAllegabiliByOrdinanzaSoggetto(request.getIdOrdinanzaVerbaleSoggetto(), TipoAllegato.getTipoDocumentoByIdTipoDocumento(idTipoAllegato), false);
		TipoAllegatoVO allegato = Iterables.tryFind(allegati, UtilsTipoAllegato.findAllegatoInTipoAllegatiByIdTipoAllegato(idTipoAllegato)).orNull();
		if (allegato == null)
			throw new SecurityException("non è possibile allegare questo tipo  allegato");

		CnmTAllegato cnmTAllegato = null;

		// t_allegao file -> id file 10

		if (nofile) {
			cnmTAllegato = commonAllegatoService.salvaAllegato(byteFile, fileName, idTipoAllegato, configAllegato, cnmTUser, TipoProtocolloAllegato.NON_PROTOCOLLARE,
					utilsDoqui.createOrGetfolder(cnmROrdinanzaVerbSogList.get(0)), //
					utilsDoqui.createIdEntitaFruitore(cnmROrdinanzaVerbSogList.get(0), //
							cnmDTipoAllegatoRepository.findOne(idTipoAllegato)),
					false, false, //
					utilsDoqui.getSoggettoActa(cnmROrdinanzaVerbSogList.get(0)), //
					utilsDoqui.getRootActa(cnmROrdinanzaVerbSogList.get(0)), 0, 0, null, null);
		} else {
			List<CnmTSoggetto> cnmTSoggettoList = null;
			if (cnmROrdinanzaVerbSogList == null || cnmROrdinanzaVerbSogList.size() == 0) {
				List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogList = cnmRAllegatoOrdVerbSogRepository.findByCnmTAllegato(cnmTAllegato);
				cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmRAllegatoOrdVerbSogsIn(cnmRAllegatoOrdVerbSogList);
			}

			List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmROrdinanzaVerbSogsIn(cnmROrdinanzaVerbSogList);
			cnmTSoggettoList = cnmTSoggettoRepository.findByCnmRVerbaleSoggettosIn(cnmRVerbaleSoggettoList);

			cnmTAllegato = commonAllegatoService.salvaAllegato(byteFile, fileName, idTipoAllegato, configAllegato, cnmTUser, TipoProtocolloAllegato.PROTOCOLLARE,
					utilsDoqui.createOrGetfolder(cnmROrdinanzaVerbSogList.get(0)), //
					utilsDoqui.createIdEntitaFruitore(cnmROrdinanzaVerbSogList.get(0), //
							cnmDTipoAllegatoRepository.findOne(idTipoAllegato)),
					false, false, //
					utilsDoqui.getSoggettoActa(cnmROrdinanzaVerbSogList.get(0)), //
					utilsDoqui.getRootActa(cnmROrdinanzaVerbSogList.get(0)), 0, 0, StadocServiceFacade.TIPOLOGIA_DOC_ACTA_DOC_INGRESSO_SENZA_ALLEGATI, cnmTSoggettoList);
		}
		
		if(pregresso) {
			// in caso di pregresso setto il flag correttamente
			cnmTAllegato.setFlagDocumentoPregresso(true);
			cnmTAllegatoRepository.save(cnmTAllegato);
		}

		if (cnmROrdinanzaVerbSogList.isEmpty())
			throw new RuntimeException("errore impossibile che sia vuota");

		for (CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog : cnmROrdinanzaVerbSogList) {
			if (TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getId() == cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato()) {
				List<CnmTAllegatoField> cnmTAllegatoFieldList = cnmTAllegatoFieldRepository.findByCnmTAllegato(cnmTAllegato);
				CnmTAllegatoField field = Iterables.tryFind(cnmTAllegatoFieldList, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_ESITO_SENTENZA))
						.orNull();
				if (field != null && field.getValoreNumber() != null) {
					BigDecimal idElenco = new BigDecimal(field.getCnmCField().getCnmDElenco().getIdElenco());
					List<CnmDElementoElenco> cnmDElementoElencoList = cnmDElementoElencoRepository.findByIdElenco(idElenco);
					CnmDElementoElenco cnmDElementoElenco = Iterables.tryFind(cnmDElementoElencoList, new Predicate<CnmDElementoElenco>() {
						public boolean apply(CnmDElementoElenco cnmDElementoElenco) {
							return cnmDElementoElenco.getIdElementoElenco() == field.getValoreNumber().longValue();
						}
					}).orNull();
					CnmDStatoOrdVerbSog cnmDStatoOrdVerbSog = cnmDStatoOrdVerbSogRepository.findByCnmDElementoElenco(cnmDElementoElenco);

					if (cnmDStatoOrdVerbSog != null) {
						cnmROrdinanzaVerbSog.setCnmDStatoOrdVerbSog(cnmDStatoOrdVerbSog);
						cnmROrdinanzaVerbSog.setCnmTUser2(cnmTUser);
						cnmROrdinanzaVerbSog.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));
						cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.save(cnmROrdinanzaVerbSog);
					} else
						throw new RuntimeException("Lo stato ordinanza verbale soggetto non esiste sul db");
				}
			}
			if (TipoAllegato.RICEVUTA_PAGAMENTO_ORDINANZA.getId() == cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato()) {
				List<CnmTAllegatoField> cnmTAllegatoFieldList = cnmTAllegatoFieldRepository.findByCnmTAllegato(cnmTAllegato);
				CnmTAllegatoField dataPagamento = Iterables
						.tryFind(cnmTAllegatoFieldList, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_DATA_PAGAMENTO_RICEVUTA_ORDINANZA)).orNull();
				CnmTAllegatoField importoPagato = Iterables
						.tryFind(cnmTAllegatoFieldList, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_IMPORTO_PAGATO_RICEVUTA_ORDINANZA)).orNull();
				if (importoPagato != null)
					cnmROrdinanzaVerbSog.setImportoPagato(importoPagato.getValoreNumber() != null ? importoPagato.getValoreNumber().setScale(2, RoundingMode.HALF_UP) : null);
				if (dataPagamento != null)
					cnmROrdinanzaVerbSog.setDataPagamento(dataPagamento.getValoreData());
				cnmROrdinanzaVerbSog.setCnmDStatoOrdVerbSog(cnmDStatoOrdVerbSogRepository.findOne(Constants.ID_STATO_ORDINANZA_VERB_SOGG_PAGATO_OFFLINE));
				cnmROrdinanzaVerbSog.setCnmTUser1(cnmTUser);
				cnmROrdinanzaVerbSog.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));
				cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.save(cnmROrdinanzaVerbSog);
				if(!pregresso)
					statoPagamentoOrdinanzaService.verificaTerminePagamentoOrdinanza(cnmROrdinanzaVerbSog, cnmTUser);
			}
			CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog = new CnmRAllegatoOrdVerbSog();
			CnmRAllegatoOrdVerbSogPK cnmRAllegatoOrdVerbSogPK = new CnmRAllegatoOrdVerbSogPK();
			cnmRAllegatoOrdVerbSogPK.setIdAllegato(cnmTAllegato.getIdAllegato());
			cnmRAllegatoOrdVerbSogPK.setIdOrdinanzaVerbSog(cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog());
			cnmRAllegatoOrdVerbSog.setCnmTUser(cnmTUser);
			cnmRAllegatoOrdVerbSog.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
			cnmRAllegatoOrdVerbSog.setId(cnmRAllegatoOrdVerbSogPK);
			cnmRAllegatoOrdVerbSogRepository.save(cnmRAllegatoOrdVerbSog);
		}
	}

	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiCreaOrdinanza() {
		return tipoAllegatoEntityMapper.mapListEntityToListVO(((List<CnmDTipoAllegato>) cnmDTipoAllegatoRepository.findAll(Constants.ALLEGATI_ALLEGABILI_IN_CREAZIONE_ORDINANZA)));
	}
	
	// 20210304_LC lotto2scenario7
	@Override
	public List<TipoAllegatoVO> getTipologiaAllegatiCreaOrdinanzaAnnullamento(Integer idOrdinanzaAnnullata) {		
		
		CnmTOrdinanza cnmTOrdinanzaAnnullata = cnmTOrdinanzaRepository.findOne(idOrdinanzaAnnullata);
		if (cnmTOrdinanzaAnnullata == null)
			throw new SecurityException("cnmTOrdinanza non trovato");
		
		if (cnmTOrdinanzaAnnullata.getCnmDTipoOrdinanza().getIdTipoOrdinanza() == Constants.ID_TIPO_ORDINANZA_ARCHIVIATO) {
			
			// se ordinanza annullata == ordinanza archiviazione: stato possibile INGIUNZIONE
			return tipoAllegatoEntityMapper.mapListEntityToListVO(((List<CnmDTipoAllegato>) cnmDTipoAllegatoRepository.findAll(Constants.ALLEGATI_ALLEGABILI_IN_CREAZIONE_ORDINANZA_ANNULLAMENTO_INGIUNZIONE)));
			
		} else if (cnmTOrdinanzaAnnullata.getCnmDTipoOrdinanza().getIdTipoOrdinanza() == Constants.ID_TIPO_ORDINANZA_INGIUNZIONE){
			
			// se ordinanza annullata == ordinanza ingiunzione: controllo sugli stati soggetti
			List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanzaAnnullata);
			if (cnmROrdinanzaVerbSogList == null || cnmROrdinanzaVerbSogList.isEmpty())
				throw new SecurityException("cnmROrdinanzaVerbSogList non trovato");
			
				int countIngiunzione = 0;
				int countArchiviazione = 0;
				for (CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog : cnmROrdinanzaVerbSogList) {
					if (cnmROrdinanzaVerbSog.getCnmDStatoOrdVerbSog().getIdStatoOrdVerbSog() == Constants.ID_STATO_ORDINANZA_VERB_SOGG_ARCHIVIATO) {
						countArchiviazione += 1;
					} else if (cnmROrdinanzaVerbSog.getCnmDStatoOrdVerbSog().getIdStatoOrdVerbSog() == Constants.ID_STATO_ORDINANZA_VERB_SOGG_INGIUNZIONE) {
						countIngiunzione += 1;
					}
				}
								
				if (cnmROrdinanzaVerbSogList.size() == countIngiunzione) {
					// se tutti i soggetti sono in stato ingiunzione: torna annullamento-archiviazione
					return tipoAllegatoEntityMapper.mapListEntityToListVO(((List<CnmDTipoAllegato>) cnmDTipoAllegatoRepository.findAll(Constants.ALLEGATI_ALLEGABILI_IN_CREAZIONE_ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE)));
					
				} else if (cnmROrdinanzaVerbSogList.size() == countArchiviazione) {
					// se tutti i soggetti sono in stato archiviato: torna annullamento-ingiunzione
					return tipoAllegatoEntityMapper.mapListEntityToListVO(((List<CnmDTipoAllegato>) cnmDTipoAllegatoRepository.findAll(Constants.ALLEGATI_ALLEGABILI_IN_CREAZIONE_ORDINANZA_ANNULLAMENTO_INGIUNZIONE)));
					
				} else {
					// se ci sono stati diversi: torna entrambe
					return tipoAllegatoEntityMapper.mapListEntityToListVO(((List<CnmDTipoAllegato>) cnmDTipoAllegatoRepository.findAll(Constants.ALLEGATI_ALLEGABILI_IN_CREAZIONE_ORDINANZA_ANNULLAMENTO)));
					
				}
							
		} else {
			throw new SecurityException("Tipo ordinanza non valido");
		}
	
	}
	

	@Override
	public Map<Long, List<AllegatoVO>> getMapCategoriaAllegatiByIdOrdinanza(List<Integer> idOrdinanzaList) {
		if (idOrdinanzaList == null || idOrdinanzaList.isEmpty())
			throw new IllegalArgumentException("idOrdinanzaList == null");

		Map<Long, List<AllegatoVO>> finalMap = null;
		// rimuovo eventuali idOrdinanza duplicati
		List<Integer> list = Lists.newArrayList(Sets.newLinkedHashSet(idOrdinanzaList));
		for (Integer idOrdinanza : list) {
			Map<Long, List<AllegatoVO>> temp = getMapCategoriaAllegatiByIdOrdinanza(idOrdinanza);
			if (finalMap == null)
				finalMap = temp;
			else {
				for (Map.Entry<Long, List<AllegatoVO>> entry : temp.entrySet()) {
					List<AllegatoVO> all = finalMap.get(entry.getKey());
					if (all == null) {
						finalMap.put(entry.getKey(), entry.getValue());
					} else
						all.addAll(entry.getValue());
				}
			}

		}

		return finalMap;
	}

	@Override
	public Map<Long, List<AllegatoVO>> getMapCategoriaAllegatiByIdOrdinanza(Integer idOrdinanza) {
		Map<Long, List<AllegatoVO>> allegatoCategoriaMap = new HashMap<>();

		if (idOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza == null");

		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza == null");

		List<CnmRAllegatoOrdinanza> cnmRAllegatoOrdinanzaList = cnmRAllegatoOrdinanzaRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		if (cnmRAllegatoOrdinanzaList != null && !cnmRAllegatoOrdinanzaList.isEmpty()) {
			for (CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza : cnmRAllegatoOrdinanzaList) {
				CnmTAllegato cnmTAllegato = cnmRAllegatoOrdinanza.getCnmTAllegato();
				AllegatoVO al = allegatoEntityMapper.mapEntityToVO(cnmTAllegato);
				al.setNumeroDeterminazioneOrdinanza(cnmRAllegatoOrdinanza.getCnmTOrdinanza().getNumDeterminazione());
				Long categoria = cnmTAllegato.getCnmDTipoAllegato().getCnmDCategoriaAllegato().getIdCategoriaAllegato();
				addToMap(allegatoCategoriaMap, categoria, al);
			}
		}

		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		if (cnmROrdinanzaVerbSogList != null && !cnmROrdinanzaVerbSogList.isEmpty()) {
			List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogList = cnmRAllegatoOrdVerbSogRepository.findByCnmROrdinanzaVerbSogIn(cnmROrdinanzaVerbSogList);
			if (cnmRAllegatoOrdVerbSogList != null && !cnmRAllegatoOrdVerbSogList.isEmpty()) {
				for (CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog : cnmRAllegatoOrdVerbSogList) {
					String numDet = cnmRAllegatoOrdVerbSog.getCnmROrdinanzaVerbSog().getCnmTOrdinanza().getNumDeterminazione();
					CnmTAllegato cnmTAllegato = cnmRAllegatoOrdVerbSog.getCnmTAllegato();
					AllegatoVO al = allegatoEntityMapper.mapEntityToVO(cnmTAllegato);
					al.setNumeroDeterminazioneOrdinanza(numDet);
					Long categoria = cnmTAllegato.getCnmDTipoAllegato().getCnmDCategoriaAllegato().getIdCategoriaAllegato();
					addToMap(allegatoCategoriaMap, categoria, al);
				}
			}
		}
		
		//Marts - INIZIO
		List<CnmTAllegato> allegati = new ArrayList<CnmTAllegato>();		
		allegati.addAll(cnmTAllegatoRepository.findAllegatiPiano(idOrdinanza));
		Iterator<CnmTAllegato> iterCnmTAllegato = allegati.iterator();
		while(iterCnmTAllegato.hasNext()) {
			CnmTAllegato cnmTAllegato = iterCnmTAllegato.next();
			AllegatoVO al = allegatoEntityMapper.mapEntityToVO(cnmTAllegato);
			al.setNumeroDeterminazioneOrdinanza(cnmTOrdinanza.getNumDeterminazione());
			Long categoria = cnmTAllegato.getCnmDTipoAllegato().getCnmDCategoriaAllegato().getIdCategoriaAllegato();
			addToMap(allegatoCategoriaMap, categoria, al);
		}
		//Marts - FINE

		return allegatoCategoriaMap;
	}
	
	
	// 20210524_LC lotto2scenario6
	@Override
	public Map<Long, List<AllegatoVO>> getMapCategoriaAllegatiByIdOrdinanzaVerbaleSoggetto(Integer idOrdinanzaVerbaleSoggetto) {
		Map<Long, List<AllegatoVO>> allegatoCategoriaMap = new HashMap<>();

		if (idOrdinanzaVerbaleSoggetto == null)
			throw new IllegalArgumentException("idOrdinanzaVerbaleSoggetto == null");


		CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.findOne(idOrdinanzaVerbaleSoggetto);
		if (cnmROrdinanzaVerbSog != null) {
			List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogList = cnmRAllegatoOrdVerbSogRepository.findByCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);
			if (cnmRAllegatoOrdVerbSogList != null && !cnmRAllegatoOrdVerbSogList.isEmpty()) {
				for (CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog : cnmRAllegatoOrdVerbSogList) {
					String numDet = cnmRAllegatoOrdVerbSog.getCnmROrdinanzaVerbSog().getCnmTOrdinanza().getNumDeterminazione();
					CnmTAllegato cnmTAllegato = cnmRAllegatoOrdVerbSog.getCnmTAllegato();
					AllegatoVO al = allegatoEntityMapper.mapEntityToVO(cnmTAllegato);
					al.setNumeroDeterminazioneOrdinanza(numDet);
					Long categoria = cnmTAllegato.getCnmDTipoAllegato().getCnmDCategoriaAllegato().getIdCategoriaAllegato();
					addToMap(allegatoCategoriaMap, categoria, al);
				}
			}
		}
		
		//Marts - INIZIO
		List<CnmTAllegato> allegati = new ArrayList<CnmTAllegato>();		
		allegati.addAll(cnmTAllegatoRepository.findAllegatiPianoByIdOrdVerbSog(idOrdinanzaVerbaleSoggetto));
		Iterator<CnmTAllegato> iterCnmTAllegato = allegati.iterator();
		while(iterCnmTAllegato.hasNext()) {
			CnmTAllegato cnmTAllegato = iterCnmTAllegato.next();
			AllegatoVO al = allegatoEntityMapper.mapEntityToVO(cnmTAllegato);
			al.setNumeroDeterminazioneOrdinanza(cnmROrdinanzaVerbSog.getCnmTOrdinanza().getNumDeterminazione());
			Long categoria = cnmTAllegato.getCnmDTipoAllegato().getCnmDCategoriaAllegato().getIdCategoriaAllegato();
			addToMap(allegatoCategoriaMap, categoria, al);
		}
		//Marts - FINE

		return allegatoCategoriaMap;
	}
	
	

	@Override
	public boolean isLetteraOrdinanzaCreata(CnmTOrdinanza cnmTOrdinanza) {
		List<CnmRAllegatoOrdinanza> cnmRAllegatoOrdinanzaList = cnmRAllegatoOrdinanzaRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		if (cnmRAllegatoOrdinanzaList != null && !cnmRAllegatoOrdinanzaList.isEmpty()) {
			for (CnmRAllegatoOrdinanza allegato : cnmRAllegatoOrdinanzaList) {
				if (allegato.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.LETTERA_ORDINANZA.getId())
					return true;
			}
		}
		return false;
	}

	@Override
	public List<AllegatoVO> getAllegatiByIdOrdinanza(Integer idOrdinanza) {
		Map<Long, List<AllegatoVO>> mappaAllegatiOrdinanza = getMapCategoriaAllegatiByIdOrdinanza(idOrdinanza);
		if (mappaAllegatiOrdinanza.isEmpty())
			return new ArrayList<>();

		List<AllegatoVO> finalAllegatiList = new ArrayList<>();
		for (Map.Entry<Long, List<AllegatoVO>> entry : mappaAllegatiOrdinanza.entrySet()) {
			finalAllegatiList.addAll(entry.getValue());
		}

		List<CnmTAllegato> allegati = new ArrayList<CnmTAllegato>();
		allegati.addAll(cnmTAllegatoRepository.findAllegatiSollecito(idOrdinanza));	// 20210402_LC sia Sollecito che SollecitoRate
		//allegati.addAll(cnmTAllegatoRepository.findAllegatiPiano(idOrdinanza));
		Map<Integer, CnmTAllegato> map = new HashMap<Integer, CnmTAllegato>();
		if (allegati != null && !allegati.isEmpty()) {
			for (CnmTAllegato cnmTAllegato : allegati) {
				if (map.isEmpty()) {
					finalAllegatiList.add(allegatoEntityMapper.mapEntityToVO(cnmTAllegato));
					map.put(cnmTAllegato.getIdAllegato(), cnmTAllegato);
				} else if (!map.containsKey(cnmTAllegato.getIdAllegato())) {
					finalAllegatiList.add(allegatoEntityMapper.mapEntityToVO(cnmTAllegato));
					map.put(cnmTAllegato.getIdAllegato(), cnmTAllegato);
				}

			}

		}

		
		return eliminaDuplicati(finalAllegatiList);
	}

	
	// 20210524_LC lotto2scenario6
	@Override
	public List<AllegatoVO> getAllegatiByIdOrdinanzaVerbaleSoggetto(List<Integer> idOrdinanzaVerbaleSoggettoList) {
		

		List<AllegatoVO> finalAllegatiList = new ArrayList<>();
		
		for (Integer idOVS : idOrdinanzaVerbaleSoggettoList) {
			
			Map<Long, List<AllegatoVO>> mappaAllegatiOrdinanza = getMapCategoriaAllegatiByIdOrdinanzaVerbaleSoggetto(idOVS);
			if (mappaAllegatiOrdinanza.isEmpty())
				//return new ArrayList<>();
				continue;

			for (Map.Entry<Long, List<AllegatoVO>> entry : mappaAllegatiOrdinanza.entrySet()) {
				finalAllegatiList.addAll(entry.getValue());
			}

			List<CnmTAllegato> allegati = new ArrayList<CnmTAllegato>();
			allegati.addAll(cnmTAllegatoRepository.findAllegatiSollecitoByIdOrdVerbSog(idOVS));	// 20210402_LC sia Sollecito che SollecitoRate
			//allegati.addAll(cnmTAllegatoRepository.findAllegatiPiano(idOrdinanza));
			Map<Integer, CnmTAllegato> map = new HashMap<Integer, CnmTAllegato>();
			if (allegati != null && !allegati.isEmpty()) {
				for (CnmTAllegato cnmTAllegato : allegati) {
					if (map.isEmpty()) {
						finalAllegatiList.add(allegatoEntityMapper.mapEntityToVO(cnmTAllegato));
						map.put(cnmTAllegato.getIdAllegato(), cnmTAllegato);
					} else if (!map.containsKey(cnmTAllegato.getIdAllegato())) {
						finalAllegatiList.add(allegatoEntityMapper.mapEntityToVO(cnmTAllegato));
						map.put(cnmTAllegato.getIdAllegato(), cnmTAllegato);
					}

				}

			}
			
		}
		
		
	
		return eliminaDuplicati(finalAllegatiList);
	}
	
	
	
	
	
	private List<AllegatoVO> eliminaDuplicati(List<AllegatoVO> finalAllegatiList) {
		Map<Integer, AllegatoVO> AllegatoVOMap = new HashMap<Integer, AllegatoVO>();
		for(AllegatoVO allegato : finalAllegatiList) {
			if(!AllegatoVOMap.containsKey(allegato.getId())) {
				AllegatoVOMap.put(allegato.getId(), allegato);
			}
		}
		return new ArrayList<AllegatoVO>(AllegatoVOMap.values());
	}

	private void addToMap(Map<Long, List<AllegatoVO>> allegatoCategoriaMap, Long categoria, AllegatoVO al) {
		List<AllegatoVO> allegatoVOList = allegatoCategoriaMap.get(categoria);
		if (allegatoVOList == null) {
			allegatoVOList = new ArrayList<>();
			allegatoCategoriaMap.put(categoria, allegatoVOList);
		}
		for (AllegatoVO a : allegatoVOList) {
			if (a.getId() == al.getId())
				return;
		}
		allegatoVOList.add(al);
	}

	@Override
	@Transactional
	public CnmTAllegato salvaLetteraOrdinanza(Integer idOrdinanza, byte[] file, UserDetails user) {
		if (idOrdinanza == null)
			throw new IllegalArgumentException("id ==null");
		if (file == null)
			throw new IllegalArgumentException("file==null");
		if (user == null)
			throw new IllegalArgumentException("user ==null");

		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza ==null");

		if (isLetteraOrdinanzaCreata(cnmTOrdinanza))
			throw new SecurityException("lettera ordinanza esistente");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(user.getIdUser());

		String nome = "Lettera_ordinanza_" + cnmTOrdinanza.getNumDeterminazione();
		String nomeFile = verificaNome(nome) + ".pdf";

		CnmTAllegato cnmTAllegatoMaster = salvaAllegatoOrdinanza(cnmTOrdinanza, file, cnmTUser, nomeFile, null, TipoAllegato.LETTERA_ORDINANZA, true, true, true);

		// protocollolato il master metto i documenti in fase di spostamento su
		// acta
		List<CnmRAllegatoOrdinanza> cnmRAllegatoOrdinanzaList = cnmRAllegatoOrdinanzaRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		List<CnmTAllegato> cnmTAllegatoList = new ArrayList<>();
		CnmDStatoAllegato cnmDStatoAllegato = cnmDStatoAllegatoRepository.findOne(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA);
		for (CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza : cnmRAllegatoOrdinanzaList) {
			CnmTAllegato cnmTAllegatoT = cnmRAllegatoOrdinanza.getCnmTAllegato();
			boolean letteraOrdinanza = cnmTAllegatoT.getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.LETTERA_ORDINANZA.getId();
			boolean statoDaProtocollareInIstanteSuccessivo = cnmTAllegatoT.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO;
			if (!letteraOrdinanza && statoDaProtocollareInIstanteSuccessivo) {
				cnmTAllegatoT.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));
				cnmTAllegatoT.setCnmDStatoAllegato(cnmDStatoAllegato);
				cnmTAllegatoList.add(cnmTAllegatoT);
			}
		}

		cnmTAllegatoList.add(cnmTAllegatoMaster);
		cnmTAllegatoList = (List<CnmTAllegato>) cnmTAllegatoRepository.save(cnmTAllegatoList);

		return Iterables.tryFind(cnmTAllegatoList, UtilsTipoAllegato.findCnmTAllegatoInCnmTAllegatosByTipoAllegato(TipoAllegato.LETTERA_ORDINANZA)).orNull();

	}

	private CnmTAllegato salvaAllegatoOrdinanza(
		CnmTOrdinanza cnmTOrdinanza,
		byte[] file,
		CnmTUser cnmTUser,
		String nomeFile,
		List<AllegatoFieldVO> configAllegato,
		TipoAllegato tipoAllegato,
		boolean protocolla,
		boolean isProtocollazioneInUscita,
		boolean isMaster
	) {
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza ==null");
		if (nomeFile == null)
			throw new IllegalArgumentException("nomeFile ==null");
		if (cnmTUser == null)
			throw new IllegalArgumentException("cnmTUser ==null");
		if (tipoAllegato == null)
			throw new IllegalArgumentException("tipoAllegato ==null");

		String tipoActa = null;
		if (tipoAllegato.getId() == TipoAllegato.COMUNICAZIONI_ALLA_CANCELLERIA.getId()) {
			tipoActa = StadocServiceFacade.TIPOLOGIA_DOC_ACTA_DOC_USCITA_SENZA_ALLEGATI_CARICATI;
		} else if (tipoAllegato.getId() == TipoAllegato.COMUNICAZIONI_DALLA_CANCELLERIA.getId() || //
				tipoAllegato.getId() == TipoAllegato.ISTANZA_RATEIZZAZIONE.getId() || //
				tipoAllegato.getId() == TipoAllegato.OPPOSIZIONE_GIURISDIZIONALE.getId() || //
				tipoAllegato.getId() == TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getId()) {
			tipoActa = StadocServiceFacade.TIPOLOGIA_DOC_ACTA_DOC_INGRESSO_SENZA_ALLEGATI;
		} else if (tipoAllegato.getId() == TipoAllegato.LETTERA_ORDINANZA.getId()) {
			tipoActa = StadocServiceFacade.TIPOLOGIA_DOC_ACTA_MASTER_USCITA_CON_ALLEGATI;
		}

		String folder = null;
		String idEntitaFruitore = null;
		TipoProtocolloAllegato tipoProtocolloAllegato = TipoProtocolloAllegato.NON_PROTOCOLLARE;
		String soggettoActa = null;
		String rootActa = null;
		CnmTAllegato cnmTAllegato = null;
		if (protocolla) {
			folder = utilsDoqui.createOrGetfolder(cnmTOrdinanza);
			idEntitaFruitore = utilsDoqui.createIdEntitaFruitore(cnmTOrdinanza, cnmDTipoAllegatoRepository.findOne(tipoAllegato.getId()));
			tipoProtocolloAllegato = TipoProtocolloAllegato.PROTOCOLLARE;
			soggettoActa = utilsDoqui.getSoggettoActa(cnmTOrdinanza);
			rootActa = utilsDoqui.getRootActa(cnmTOrdinanza);

			List<CnmTSoggetto> cnmTSoggettoList = null;
			List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
			if (cnmROrdinanzaVerbSogList == null || cnmROrdinanzaVerbSogList.size() == 0) {
				List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogList = cnmRAllegatoOrdVerbSogRepository.findByCnmTAllegato(cnmTAllegato);
				cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmRAllegatoOrdVerbSogsIn(cnmRAllegatoOrdVerbSogList);
			}

			List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmROrdinanzaVerbSogsIn(cnmROrdinanzaVerbSogList);
			cnmTSoggettoList = cnmTSoggettoRepository.findByCnmRVerbaleSoggettosIn(cnmRVerbaleSoggettoList);

			// 20210831 PP - CR_107 non devo protoollare il master se è LETTERA_ORDINANZA, poichè sarà fatto dal batch dopo aver aggiunto gli allegati
			
			if (tipoAllegato.getId() == TipoAllegato.LETTERA_ORDINANZA.getId()) {
				tipoProtocolloAllegato = TipoProtocolloAllegato.SALVA_MULTI_SENZA_PROTOCOLARE;
			}
			
			cnmTAllegato = commonAllegatoService.salvaAllegato(file, nomeFile, tipoAllegato.getId(), configAllegato, cnmTUser, tipoProtocolloAllegato, folder, idEntitaFruitore, isMaster,
					isProtocollazioneInUscita, soggettoActa, rootActa, 0, 0, tipoActa, cnmTSoggettoList);

		} else {
			cnmTAllegato = commonAllegatoService.salvaAllegato(file, nomeFile, tipoAllegato.getId(), configAllegato, cnmTUser, tipoProtocolloAllegato, folder, idEntitaFruitore, isMaster,
					isProtocollazioneInUscita, soggettoActa, rootActa, 0, 0, tipoActa, null);
		}

		// aggiungo alla tabella
		CnmRAllegatoOrdinanzaPK cnmRAllegatoOrdinanzaPK = new CnmRAllegatoOrdinanzaPK();
		cnmRAllegatoOrdinanzaPK.setIdAllegato(cnmTAllegato.getIdAllegato());
		cnmRAllegatoOrdinanzaPK.setIdOrdinanza(cnmTOrdinanza.getIdOrdinanza());

		CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza = new CnmRAllegatoOrdinanza();
		cnmRAllegatoOrdinanza.setId(cnmRAllegatoOrdinanzaPK);
		cnmRAllegatoOrdinanza.setCnmTAllegato(cnmTAllegato);
		cnmRAllegatoOrdinanza.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
		cnmRAllegatoOrdinanza.setCnmTUser(cnmTUser);
		cnmRAllegatoOrdinanzaRepository.save(cnmRAllegatoOrdinanza);

		return cnmTAllegato;
	}

	@Transactional
	@Override
	public void inviaRichiestaBollettiniByIdOrdinanza(Integer idOrdinanza) {
		if (idOrdinanza == null)
			throw new IllegalArgumentException("id ==null");
		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza ==null");
		if (StringUtils.isNotEmpty(cnmTOrdinanza.getCodMessaggioEpay()))
			throw new SecurityException("Richiesta dei bollettini effettuata");

		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);

		List<CnmROrdinanzaVerbSog> soggettiBollettini = new ArrayList<>();
		for (CnmROrdinanzaVerbSog c : cnmROrdinanzaVerbSogList) {
			if (c.getCnmDStatoOrdVerbSog().getIdStatoOrdVerbSog() != Constants.ID_STATO_ORDINANZA_VERB_SOGG_ARCHIVIATO) {
				CnmTSoggetto cnmTSoggetto = c.getCnmRVerbaleSoggetto().getCnmTSoggetto();
				String codiceFiscale = StringUtils.defaultString(cnmTSoggetto.getCodiceFiscale(), cnmTSoggetto.getCodiceFiscaleGiuridico());
				String piva = cnmTSoggetto.getPartitaIva();
				c.setCodPosizioneDebitoria(commonBollettiniService.generaCodicePosizioneDebitoria(StringUtils.defaultString(codiceFiscale, piva), BigDecimal.ONE, Constants.CODICE_ORDINANZA));
				soggettiBollettini.add(c);
			}
		}

		soggettiBollettini = (List<CnmROrdinanzaVerbSog>) cnmROrdinanzaVerbSogRepository.save(soggettiBollettini);

		cnmTOrdinanza.setCodMessaggioEpay(commonBollettiniService.generaCodiceMessaggioEpay(idOrdinanza, Constants.CODICE_ORDINANZA));
		cnmTOrdinanza = cnmTOrdinanzaRepository.save(cnmTOrdinanza);

		if (cnmTOrdinanza.getDataScadenzaOrdinanza() != null)
			ePayServiceFacade.inserisciListaDiCarico(ePayWsInputMapper.mapRataSoggettiToWsMapper(soggettiBollettini));

	}

	@Override
	public void creaBollettiniByCnmROrdinanzaVerbSog(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList) {
		if (cnmROrdinanzaVerbSogList == null || cnmROrdinanzaVerbSogList.isEmpty())
			throw new IllegalArgumentException("cnmROrdinanzaVerbSogList is empty");

		// recupero parametri
		List<CnmCParametro> cnmCParametroList = cnmCParametroRepository.findByIdParametroIn(Constants.PARAMETRI_BOLLETTINI);
		String numeroContoPostale = "";
		String oggettoPagamento = "";
		String cfEnteCreditore = "";
		String enteCreditore = "";
		String cbill = "";
		String intestatarioContoCorrentePostale = "";
		String autorizzazione = "";
		String infoEnte = "";
		String settoreEnte = "";
		CnmCParametro cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_NUMERO_CONTO_POSTALE)).orNull();
		if (cnmCParametro != null)
			numeroContoPostale = cnmCParametro.getValoreString();
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_OGGETTO_PAGAMENTO_ORDINANZA)).orNull();
		if (cnmCParametro != null)
			oggettoPagamento = cnmCParametro.getValoreString();
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_CODICE_FISCALE_ENTE_CREDITORE)).orNull();
		if (cnmCParametro != null)
			cfEnteCreditore = cnmCParametro.getValoreString();
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_ENTE_CREDITORE)).orNull();
		if (cnmCParametro != null)
			enteCreditore = cnmCParametro.getValoreString();
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_CBILL)).orNull();
		if (cnmCParametro != null)
			cbill = cnmCParametro.getValoreString();
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_INTESTATARIO_VERSAMENTO_POSTALE)).orNull();
		if (cnmCParametro != null)
			intestatarioContoCorrentePostale = cnmCParametro.getValoreString();
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_AUTORIZZAZIONE)).orNull();
		if (cnmCParametro != null)
			autorizzazione = cnmCParametro.getValoreString();
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_INFO_ENTE)).orNull();
		if (cnmCParametro != null)
			infoEnte = cnmCParametro.getValoreString();
		cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_SETTORE_ENTE)).orNull();
		if (cnmCParametro != null)
			settoreEnte = cnmCParametro.getValoreString();

		List<BollettinoJasper> bollettini = new ArrayList<>();	

		CnmTOrdinanza cnmTOrdinanza = cnmROrdinanzaVerbSogList.get(0).getCnmTOrdinanza();
		
		BigDecimal importNotifica = BigDecimal.ZERO;
		if(cnmTOrdinanza.getCnmTNotificas()!=null && !cnmTOrdinanza.getCnmTNotificas().isEmpty()) {
			Iterator<CnmTNotifica> iterNotifica = cnmTOrdinanza.getCnmTNotificas().iterator();
			while(iterNotifica.hasNext()) {
				CnmTNotifica notificaItem = iterNotifica.next();
				importNotifica = importNotifica.add(notificaItem.getImportoSpeseNotifica());
			}			
		}
		
		
		for (CnmROrdinanzaVerbSog s : cnmROrdinanzaVerbSogList) {
			SoggettoVO soggetto = soggettoEntityMapper.mapEntityToVO(s.getCnmRVerbaleSoggetto().getCnmTSoggetto());
			
			// 20201217_LC - JIRA 118
			CnmTVerbale cnmTVerbale = s.getCnmRVerbaleSoggetto().getCnmTVerbale();
			CnmTSoggetto cnmTSoggetto = cnmTSoggettoRepository.findOne(soggetto.getId());
			if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
				soggetto = commonSoggettoService.attachResidenzaPregressi(soggetto, cnmTSoggetto, cnmTVerbale.getIdVerbale());
			}			
						
			
			
			String denominazione = soggetto.getPersonaFisica() ? soggetto.getNome() + " " + soggetto.getCognome() : soggetto.getRagioneSociale();
			String codiceFiscaleSoggetto = StringUtils.defaultIfEmpty(soggetto.getPartitaIva(), soggetto.getCodiceFiscale());
			if (!soggetto.getPersonaFisica()) {
				soggetto.setNoteSoggetto(s.getCnmRVerbaleSoggetto().getNote());
			}
			BollettinoJasper bollettino = new BollettinoJasper();

			BigDecimal importo = cnmTOrdinanza.getImportoOrdinanza();			
			String codiceAvviso = s.getCodAvviso();
			String textDataMatrix = commonBollettiniService.createTextDataMatrix(codiceAvviso, numeroContoPostale, importo, cfEnteCreditore, codiceFiscaleSoggetto, denominazione, oggettoPagamento);

			bollettino.setQrcode1(
					utilsCodeWriter.convertCodeToBufferImage(utilsCodeWriter.generateQRCodeImage(commonBollettiniService.createTextQrCode(codiceAvviso, cfEnteCreditore, importo), 140, 140)));
			bollettino.setDataMatrix1(utilsCodeWriter.convertCodeToBufferImage(utilsCodeWriter.generateDataMatrixImage(textDataMatrix, 70, 70)));
			bollettino.setNumRata1(BigDecimal.ONE);
			bollettino.setImportoRata1(importo.add(importNotifica));
			bollettino.setDataScadenzaRata1(utilsDate.asLocalDate(cnmTOrdinanza.getDataScadenzaOrdinanza()));
			bollettino.setCodiceAvvisoRata1(UtilsRata.formattaCodiceAvvisoPerLaVisualizzazione(codiceAvviso));
			bollettino.setOggettoPagamento(oggettoPagamento);
			bollettino.setCfEnteCreditore(cfEnteCreditore);
			bollettino.setDenominazioneSoggetto(denominazione);
			bollettino.setEnteCreditore(enteCreditore);
			bollettino.setCbill(cbill);
			bollettino.setNumeroContoPostale(numeroContoPostale);
			bollettino.setIntestatarioContoCorrentePostale(intestatarioContoCorrentePostale);
			bollettino.setAutorizzazione(autorizzazione);
			bollettino.setCfEnteDebitore(codiceFiscaleSoggetto);
			bollettino.setIndirizzoEnteDebitore(soggetto.getIndirizzoResidenza() + ", " + soggetto.getCivicoResidenza());
			String comuneResidenza = soggetto.getComuneResidenza() != null ? soggetto.getComuneResidenza().getDenominazione() : null;
			String provinciaResidenza = soggetto.getProvinciaResidenza() != null ? soggetto.getProvinciaResidenza().getDenominazione() : null;
			if (comuneResidenza != null && provinciaResidenza != null)
				bollettino.setComuneEnteDebitore(comuneResidenza + " (" + provinciaResidenza + ")");
			bollettino.setInfoEnte(infoEnte);
			bollettino.setSettoreEnte(settoreEnte);

			bollettini.add(bollettino);

		}

		byte[] file = commonBollettiniService.printBollettini(bollettini, Report.REPORT_STAMPA_BOLLETTINO_ORDINANZA_SOLLECITO);

		CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(Constants.CFEPAY);
		if (cnmTUser == null)
			logger.error("L'utente epay non è censito sul db ");

		String nomeF = "Bollettini_ordinanza_" + cnmTOrdinanza.getNumDeterminazione();
		String nome = verificaNome(nomeF) + ".pdf";
		salvaAllegatoOrdinanza(cnmTOrdinanza, file, cnmTUser, nome, null, TipoAllegato.BOLLETTINI_ORDINANZA_SOLLECITO, false, false, false);
	}

	@Override
	public List<DocumentoScaricatoVO> downloadBollettiniByIdOrdinanza(Integer idOrdinanza) {
		// 20200824_LC nuovo type per gestione documento multiplo
		try {
			// 20200825_LC 
			List<DocumentoScaricatoVO> encodedDocs = getAllegatoByIdOrdinanza(idOrdinanza, TipoAllegato.BOLLETTINI_ORDINANZA_SOLLECITO);
			return encodedDocs;			
//			return getAllegatoByIdOrdinanza(idOrdinanza, TipoAllegato.BOLLETTINI_ORDINANZA_SOLLECITO);
		} catch (FileNotFoundException e) {
			throw new BusinessException(ErrorCode.BOLLETTINI_NON_ANCORA_GENERATI_ORDINANZA);
		}
	}

	@Override
	public List<DocumentoScaricatoVO> downloadLetteraOrdinanza(Integer idOrdinanza) {
		// 20200824_LC nuovo type per gestione documento multiplo
		try {
			return getAllegatoByIdOrdinanza(idOrdinanza, TipoAllegato.LETTERA_ORDINANZA);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Lettera non creata");
		}

	}
	


	private List<DocumentoScaricatoVO> getAllegatoByIdOrdinanza(Integer idOrdinanza, TipoAllegato tipoAllegato) throws FileNotFoundException {
		// 20200824_LC nuovo type per gestione documento multiplo
		if (tipoAllegato == null)
			throw new IllegalArgumentException("tipoAllegato ==null");

		CnmTOrdinanza cnmTOrdinanza = utilsOrdinanza.validateAndGetCnmTOrdinanza(idOrdinanza);

		Integer idAllegato = null;
		List<CnmRAllegatoOrdinanza> cnmRAllegatoOrdinanzaList = cnmRAllegatoOrdinanzaRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		if (cnmRAllegatoOrdinanzaList != null && !cnmRAllegatoOrdinanzaList.isEmpty()) {
			for (CnmRAllegatoOrdinanza allegato : cnmRAllegatoOrdinanzaList) {
				if (allegato.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == tipoAllegato.getId())
					idAllegato = allegato.getCnmTAllegato().getIdAllegato();
			}
		}

		if (idAllegato == null)
			throw new FileNotFoundException(Long.valueOf(tipoAllegato.getId()).toString());

		return commonAllegatoService.downloadAllegatoById(idAllegato);
	}

	@Override
	public boolean isRichiestaBollettiniInviata(CnmTOrdinanza cnmTOrdinanza) {
		return cnmTOrdinanza.getCodMessaggioEpay() != null;
	}

	@Override
	public IsCreatedVO isLetteraSaved(IsCreatedVO request) {
		Integer idOrdinanza = request.getId();
		IsCreatedVO isCreatedVO = new IsCreatedVO();
		isCreatedVO.setCreated(false);
		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza != null) {
			List<CnmTAllegato> cnmTAllegatos = cnmRAllegatoOrdinanzaRepository.findCnmTAllegatosByCnmTOrdinanza(cnmTOrdinanza);
			if (cnmTAllegatos != null && !cnmTAllegatos.isEmpty()) {
				for (CnmTAllegato al : cnmTAllegatos) {
					if (al.getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.LETTERA_ORDINANZA.getId()) {
						isCreatedVO.setCreated(true);
					}
				}
			}
		}
		return isCreatedVO;
	}

	@Override
	public DatiTemplateVO nomeLetteraOrdinanza(Integer idOrdinanza) {
		DatiTemplateVO vo = new DatiTemplateVO();

		if (idOrdinanza == null)
			throw new IllegalArgumentException("id ==null");

		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza ==null");

		String nome = "Lettera_ordinanza_" + cnmTOrdinanza.getNumDeterminazione();
		vo.setNome(verificaNome(nome) + ".pdf");

		return vo;
	}

	public String verificaNome(String nome) {
		Pattern pattern = Pattern.compile("[^A-Za-z0-9]+");
		String nomePDF = "";

		for (int i = 0; i < nome.length(); i++) {
			Matcher matcher = pattern.matcher("" + nome.charAt(i));
			if (matcher.matches()) {
				nomePDF = nomePDF + "_";
			} else {
				nomePDF = nomePDF + nome.charAt(i);
			}
		}

		return nomePDF;
	}

	@Override
	@Transactional
	public MessageVO salvaAllegatoProtocollatoOrdinanzaSoggetto(SalvaAllegatiProtocollatiRequest request, UserDetails userDetails, boolean pregresso) {
		
		// la lettera ordinanza deve andare sulla RallegatoOrdinanza
		Long idTipoAllegato = request.getAllegati().get(0).getIdTipoAllegato();
		if(pregresso && idTipoAllegato == TipoAllegato.LETTERA_ORDINANZA.getId()) {
			
			List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = (List<CnmROrdinanzaVerbSog>) cnmROrdinanzaVerbSogRepository.findAll(request.getIdOrdinanzaVerbaleSoggetto());
			
			request.setIdOrdinanza(cnmROrdinanzaVerbSogList.get(0).getCnmTOrdinanza().getIdOrdinanza());
			return salvaAllegatoProtocollatoOrdinanza(request, userDetails, pregresso);
		}
		Long idUser = userDetails.getIdUser();
//		Long idTipoAllegato = request.getIdTipoAllegato() != null ? request.getIdTipoAllegato() : new Long(22);

//		List<AllegatoFieldVO> configAllegato = request.getAllegatoField();
		CnmTUser cnmTUser = cnmTUserRepository.findOne(idUser);

		// sicurezza
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = (List<CnmROrdinanzaVerbSog>) cnmROrdinanzaVerbSogRepository.findAll(request.getIdOrdinanzaVerbaleSoggetto());
		if (cnmROrdinanzaVerbSogList == null || cnmROrdinanzaVerbSogList.isEmpty())
			throw new SecurityException("cnmROrdinanzaVerbSog non trovato");
		
		if(request.getAllegati()==null || request.getAllegati().isEmpty()) {
			throw new SecurityException("manca la tipologia allegato");
		}

		if(request.getDocumentoProtocollato()==null) {
			throw new SecurityException("Allegati gia protocollati non trovati");
		}
		
		if(!pregresso) {
	//		per la ricerca prendo il primo elemento request.getAllegati().get(0).getIdTipoAllegato() perche do per scontato che in questo caso ci sia sempre un solo elemento nella lista
			List<TipoAllegatoVO> allegati = getTipologiaAllegatiAllegabiliByOrdinanzaSoggetto(request.getIdOrdinanzaVerbaleSoggetto(), TipoAllegato.getTipoDocumentoByIdTipoDocumento(request.getAllegati().get(0).getIdTipoAllegato()), true); 
			TipoAllegatoVO allegato = Iterables.tryFind(allegati, UtilsTipoAllegato.findAllegatoInTipoAllegatiByIdTipoAllegato(request.getAllegati().get(0).getIdTipoAllegato())).orNull();
			if (allegato == null)
				throw new SecurityException("non è possibile allegare questo tipo  allegato");
		}
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmROrdinanzaVerbSogsIn(cnmROrdinanzaVerbSogList);
		//prendo il primo elemento, tanto anche se ce ne sono diversi, dovrebbero essere legati tutti allo stesso verbale
		CnmTVerbale cnmTVerbale = cnmRVerbaleSoggettoList.get(0).getCnmTVerbale();
//		List<CnmTSoggetto> cnmTSoggettoList = cnmTSoggettoRepository.findByCnmRVerbaleSoggettosIn(cnmRVerbaleSoggettoList);

		CnmTAllegato cnmTAllegato = commonAllegatoService.salvaAllegatoProtocollatOrdinanzaSoggetto(request, cnmTUser, cnmROrdinanzaVerbSogList, cnmTVerbale, pregresso);
		
		for (CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog : cnmROrdinanzaVerbSogList) {
			if (TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getId() == cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato()) {
				List<CnmTAllegatoField> cnmTAllegatoFieldList = cnmTAllegatoFieldRepository.findByCnmTAllegato(cnmTAllegato);
				CnmTAllegatoField field = Iterables.tryFind(cnmTAllegatoFieldList, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_ESITO_SENTENZA))
						.orNull();
				if (field != null && field.getValoreNumber() != null) {
					BigDecimal idElenco = new BigDecimal(field.getCnmCField().getCnmDElenco().getIdElenco());
					List<CnmDElementoElenco> cnmDElementoElencoList = cnmDElementoElencoRepository.findByIdElenco(idElenco);
					CnmDElementoElenco cnmDElementoElenco = Iterables.tryFind(cnmDElementoElencoList, new Predicate<CnmDElementoElenco>() {
						public boolean apply(CnmDElementoElenco cnmDElementoElenco) {
							return cnmDElementoElenco.getIdElementoElenco() == field.getValoreNumber().longValue();
						}
					}).orNull();
					CnmDStatoOrdVerbSog cnmDStatoOrdVerbSog = cnmDStatoOrdVerbSogRepository.findByCnmDElementoElenco(cnmDElementoElenco);

					if (cnmDStatoOrdVerbSog != null) {
						cnmROrdinanzaVerbSog.setCnmDStatoOrdVerbSog(cnmDStatoOrdVerbSog);
						cnmROrdinanzaVerbSog.setCnmTUser2(cnmTUser);
						cnmROrdinanzaVerbSog.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));
						cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.save(cnmROrdinanzaVerbSog);
					} else
						throw new RuntimeException("Lo stato ordinanza verbale soggetto non esiste sul db");
				}
			}
			CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog = new CnmRAllegatoOrdVerbSog();
			CnmRAllegatoOrdVerbSogPK cnmRAllegatoOrdVerbSogPK = new CnmRAllegatoOrdVerbSogPK();
			cnmRAllegatoOrdVerbSogPK.setIdAllegato(cnmTAllegato.getIdAllegato());
			cnmRAllegatoOrdVerbSogPK.setIdOrdinanzaVerbSog(cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog());
			cnmRAllegatoOrdVerbSog.setCnmTUser(cnmTUser);
			cnmRAllegatoOrdVerbSog.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
			cnmRAllegatoOrdVerbSog.setId(cnmRAllegatoOrdVerbSogPK);
			cnmRAllegatoOrdVerbSogRepository.save(cnmRAllegatoOrdVerbSog);
		}
		MessageVO response = null;
		//20200918_ET se il protocollo e' stato gia' associato al verbale, niente messaggio!! quindi controllo anche isGiaPresenteSuActa
		if(!request.getDocumentoProtocollato().isGiaPresenteSuActa() && StringUtils.isBlank(request.getDocumentoProtocollato().getRegistrazioneId())) {
			CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.ALLEGATO_SALVATO_MASTER_DA_SALVARE);
			if(cnmDMessaggio!=null) {
				String msg=cnmDMessaggio.getDescMessaggio();
				msg = String.format(msg, request.getDocumentoProtocollato().getFilenameMaster(), cnmTVerbale.getNumVerbale());
				response = new MessageVO(msg, cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
			}else {
				throw new SecurityException("Messaggio non trovato");
			}
		}
		
		return response;
		
	}
	

	// 20200715_LC
	public MessageVO salvaAllegatoProtocollatoOrdinanza(SalvaAllegatiProtocollatiRequest request, UserDetails userDetails, boolean pregresso) {
		Long idUser = userDetails.getIdUser();
		CnmTUser cnmTUser = cnmTUserRepository.findOne(idUser);
		//Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		Integer idOrdinanza = request.getIdOrdinanza();
//		List<AllegatoVO> response = new ArrayList<AllegatoVO>();
		


		// controlli sicurezza
		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new SecurityException("Ordinanza non trovata");

		if(request.getDocumentoProtocollato()==null) {
			throw new SecurityException("Allegati gia protocollati non trovati");
		}
		
		if(request.getAllegati()==null) {
			throw new SecurityException("Tipi degli allegati gia protocollati non trovati");
		}	

		// controllo che fa anche nelle salvaAllegatoOrdinanza, deve farlo per ogni documento protocollato (che ha tipo allegato diverso)
		for ( SalvaAllegatoRequest singoloAllegato : request.getAllegati()) {
			long idTipoAllegato = singoloAllegato.getIdTipoAllegato();
			List<TipoAllegatoVO> allegati = getTipologiaAllegatiAllegabiliByOrdinanza(idOrdinanza, TipoAllegato.getTipoDocumentoByIdTipoDocumento(idTipoAllegato), true);
			TipoAllegatoVO allegato = Iterables.tryFind(allegati, UtilsTipoAllegato.findAllegatoInTipoAllegatiByIdTipoAllegato(idTipoAllegato)).orNull();
			if (allegato == null)
				throw new SecurityException("non è possibile allegare questo tipo  allegato");		
		}		

		
		// verificare che effettivamente ordinanza-verbale sia ManyToOne (a quel punto perndo uno qualsiasi dei verbali della lista risultante qui sotto)
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmROrdinanzaVerbSogsIn(cnmROrdinanzaVerbSogList);
		CnmTVerbale cnmTVerbale = cnmRVerbaleSoggettoList.get(0).getCnmTVerbale();
		// --

		//List<CnmTAllegato> cnmTallegatoList =
		commonAllegatoService.salvaAllegatoProtocollatoOrdinanza(request, cnmTUser, cnmTOrdinanza, cnmTVerbale, pregresso);
	
//		for (CnmTAllegato all : cnmTallegatoList) {	
//			
//			response.add(allegatoEntityMapper.mapEntityToVO(all));
//		}	
		
		MessageVO response = null;
		//20200918_ET se il protocollo e' stato gia' associato al verbale, niente messaggio!! quindi controllo anche isGiaPresenteSuActa
		if(!request.getDocumentoProtocollato().isGiaPresenteSuActa() && StringUtils.isBlank(request.getDocumentoProtocollato().getRegistrazioneId())) {
			CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.ALLEGATO_SALVATO_MASTER_DA_SALVARE);
			if(cnmDMessaggio!=null) {
				String msg=cnmDMessaggio.getDescMessaggio();
				msg = String.format(msg, request.getDocumentoProtocollato().getFilenameMaster(), cnmTVerbale.getNumVerbale());
				response = new MessageVO(msg, cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
			}else {
				throw new SecurityException("Messaggio non trovato");
			}
		}
		
		return response;
	}
	
	@Override
	public Map<Long, List<AllegatoVO>> getMapTipoAllegatiByIdOrdinanza(Integer idOrdinanza) {
		Map<Long, List<AllegatoVO>> tipoAllegatoMap = new HashMap<>();

		CnmTOrdinanza cnmTOrdinanza = utilsOrdinanza.validateAndGetCnmTOrdinanza(idOrdinanza);

		List<CnmRAllegatoOrdinanza> cnmRAllegatoOrdinanzaList = cnmRAllegatoOrdinanzaRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		if (cnmRAllegatoOrdinanzaList != null && !cnmRAllegatoOrdinanzaList.isEmpty()) {
			for (CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza : cnmRAllegatoOrdinanzaList) {
				CnmTAllegato cnmTAllegato = cnmRAllegatoOrdinanza.getCnmTAllegato();
				AllegatoVO al = allegatoEntityMapper.mapEntityToVO(cnmTAllegato);
				al.setNumeroDeterminazioneOrdinanza(cnmRAllegatoOrdinanza.getCnmTOrdinanza().getNumDeterminazione());
//				Long categoria = cnmTAllegato.getCnmDTipoAllegato().getCnmDCategoriaAllegato().getIdCategoriaAllegato();
				Long tipoAllegato = cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato();
				addToMap(tipoAllegatoMap, tipoAllegato, al);
			}
		}

		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		if (cnmROrdinanzaVerbSogList != null && !cnmROrdinanzaVerbSogList.isEmpty()) {
			List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogList = cnmRAllegatoOrdVerbSogRepository.findByCnmROrdinanzaVerbSogIn(cnmROrdinanzaVerbSogList);
			if (cnmRAllegatoOrdVerbSogList != null && !cnmRAllegatoOrdVerbSogList.isEmpty()) {
				for (CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog : cnmRAllegatoOrdVerbSogList) {
					String numDet = cnmRAllegatoOrdVerbSog.getCnmROrdinanzaVerbSog().getCnmTOrdinanza().getNumDeterminazione();
					CnmTAllegato cnmTAllegato = cnmRAllegatoOrdVerbSog.getCnmTAllegato();
					AllegatoVO al = allegatoEntityMapper.mapEntityToVO(cnmTAllegato);
					al.setNumeroDeterminazioneOrdinanza(numDet);
//					Long categoria = cnmTAllegato.getCnmDTipoAllegato().getCnmDCategoriaAllegato().getIdCategoriaAllegato();
//					addToMap(allegatoCategoriaMap, categoria, al);
					Long tipoAllegato = cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato();
					addToMap(tipoAllegatoMap, tipoAllegato, al);
				}
			}
		}
		
		//Marts - INIZIO
		List<CnmTAllegato> allegati = new ArrayList<CnmTAllegato>();		
		allegati.addAll(cnmTAllegatoRepository.findAllegatiPiano(idOrdinanza));
		Iterator<CnmTAllegato> iterCnmTAllegato = allegati.iterator();
		while(iterCnmTAllegato.hasNext()) {
			CnmTAllegato cnmTAllegato = iterCnmTAllegato.next();
			AllegatoVO al = allegatoEntityMapper.mapEntityToVO(cnmTAllegato);
			al.setNumeroDeterminazioneOrdinanza(cnmTOrdinanza.getNumDeterminazione());
//			Long categoria = cnmTAllegato.getCnmDTipoAllegato().getCnmDCategoriaAllegato().getIdCategoriaAllegato();
//			addToMap(allegatoCategoriaMap, categoria, al);
			Long tipoAllegato = cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato();
			addToMap(tipoAllegatoMap, tipoAllegato, al);
		}
		//Marts - FINE

		return tipoAllegatoMap;
	}
	
	
	
	// 20210425_LC lotto2scenario6 - istanza + allegati (protocollati insieme dal batch)

	@Override
	public List<MessageVO> salvaAllegatiMultipli(List<InputPart> data, List<InputPart> file, UserDetails userDetails, boolean pregresso) {

		SalvaAllegatiMultipliRequest request = commonAllegatoService.getRequest(data, file, SalvaAllegatiMultipliRequest.class, true);
		
		if (request == null || request.getAllegati() == null || request.getAllegati().size() == 0)
			throw new SecurityException("allegato non trovato");
		if (request.getIdOrdinanzaVerbaleSoggettoList() == null || request.getIdOrdinanzaVerbaleSoggettoList().isEmpty())
			throw new IllegalArgumentException("idOrdinanzaVerbaleSoggettoList è null");
		
		Long idUser = userDetails.getIdUser();
		CnmTUser cnmTUser = cnmTUserRepository.findOne(idUser);
		String idActa = null;
		List<MessageVO> msgResponseList = new ArrayList<MessageVO>();
		
		List<AllegatoMultiploVO> listaAllegati = request.getAllegati();
		
	
		// gestione eventuali >1 soggetti
		for (Integer idOrdVerbSog : request.getIdOrdinanzaVerbaleSoggettoList()) {
			
			CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.findOne(idOrdVerbSog);
			if (cnmROrdinanzaVerbSog == null) throw new SecurityException("cnmROrdinanzaVerbSogList non trovato");
	
			CnmTOrdinanza cnmTOrdinanza = cnmROrdinanzaVerbSog.getCnmTOrdinanza();
			if (cnmTOrdinanza == null) throw new SecurityException("ordinanza non trovato");
			CnmDStatoOrdinanza stato = cnmTOrdinanza.getCnmDStatoOrdinanza();
			
			
			List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmROrdinanzaVerbSogsIn(Collections.singletonList(cnmROrdinanzaVerbSog));
			List<CnmTSoggetto> soggetti = cnmTSoggettoRepository.findByCnmRVerbaleSoggettosIn(cnmRVerbaleSoggettoList);
				
			CnmTAllegato cnmTAllegato = null;
			
			CnmDTipoAllegato cnmDTipoAllegatoMaster = cnmDTipoAllegatoRepository.findOne(TipoAllegato.ISTANZA_RATEIZZAZIONE.getId());
			CnmDTipoAllegato cnmDTipoAllegatoAllegato = cnmDTipoAllegatoRepository.findOne(TipoAllegato.ISTANZA_ALLEGATO.getId());
			String entitaFruitoreMaster = utilsDoqui.createIdEntitaFruitore(cnmTOrdinanza, cnmDTipoAllegatoMaster);
			String entitaFruitoreAllegato = utilsDoqui.createIdEntitaFruitore(cnmTOrdinanza, cnmDTipoAllegatoAllegato);
			String folder = utilsDoqui.createOrGetfolder(cnmTOrdinanza);
			String rootActa = utilsDoqui.getRootActa(cnmTOrdinanza);
			String soggettoActa = utilsDoqui.getSoggettoActa(cnmTOrdinanza);
	
			
			
			// gestione messaggi di warning
			List<String> protocolliMasterSelezionati = new ArrayList<String>();	// protocolli per i quali si e selezionato il master (tutti i doc ad essi relativi non neessitano di msg finale)
			for(AllegatoMultiploVO allegato : listaAllegati) {
				if (allegato.getDocumentoProtocollato() != null) {
					if (allegato.getDocumentoProtocollato().getNumProtocollo() != null) {
						protocolliMasterSelezionati.add(allegato.getDocumentoProtocollato().getNumProtocollo());
					}
				}				
			}
			
			
			
			
			// GESTIONE PER ISTANZA DI RATEIZZAZIONE ED ALLEGATI (ids 26, 38)
			if (isMasterPresent(listaAllegati)) {
				// c'è almeno un master
				
				for (AllegatoMultiploVO allegato : listaAllegati) {
					if (allegato.isMaster()) {
						
						if (allegato.getDocumentoProtocollato() != null) {
							// allegato ricercato su acta
							String numProtocolloAllegato = allegato.getDocumentoProtocollato().getNumProtocollo() != null ? allegato.getDocumentoProtocollato().getNumProtocollo() : allegato.getDocumentoProtocollato().getNumProtocolloMaster();
							SalvaAllegatiProtocollatiRequest salvaAllegatoProtocollatoRequest = getNewSalvaAllegatiProtocollatiRequest(request.getIdVerbale(), idOrdVerbSog, allegato);
							MessageVO  resp = salvaAllegatoProtocollatoOrdinanzaSoggetto(salvaAllegatoProtocollatoRequest, userDetails, pregresso);
							if (resp!=null && !(protocolliMasterSelezionati.contains(numProtocolloAllegato)) )
								msgResponseList.add(resp);					
						} else {
							// allegato da FS
							int numeroAllegati = listaAllegati.size() - 1;
							cnmTAllegato = salvaSingoloAllegatoMulti(allegato, cnmTUser, folder, soggettoActa, rootActa, numeroAllegati, entitaFruitoreMaster, pregresso, soggetti, TipoProtocolloAllegato.PROTOCOLLARE);	// 20210701_LC Jira 158	-	protocollare
							idActa = cnmTAllegato.getIdActa();	
							
							CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog = new CnmRAllegatoOrdVerbSog();
							CnmRAllegatoOrdVerbSogPK cnmRAllegatoOrdVerbSogPK = new CnmRAllegatoOrdVerbSogPK();
							cnmRAllegatoOrdVerbSogPK.setIdAllegato(cnmTAllegato.getIdAllegato());
							cnmRAllegatoOrdVerbSogPK.setIdOrdinanzaVerbSog(cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog());
							cnmRAllegatoOrdVerbSog.setCnmTUser(cnmTUser);
							cnmRAllegatoOrdVerbSog.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
							cnmRAllegatoOrdVerbSog.setId(cnmRAllegatoOrdVerbSogPK);
							cnmRAllegatoOrdVerbSogRepository.save(cnmRAllegatoOrdVerbSog);
						}	
	
						break;
					}
				}
	
				if (listaAllegati.size() >= 2) {
					CnmDStatoAllegato avviospostamentoActa = cnmDStatoAllegatoRepository.findOne(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA);
					Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
	
					for (AllegatoMultiploVO allegato : listaAllegati) {
						if (!allegato.isMaster()) {
							
							if (allegato.getDocumentoProtocollato() != null) {
								// allegato ricercato su acta	
								String numProtocolloAllegato = allegato.getDocumentoProtocollato().getNumProtocollo() != null ? allegato.getDocumentoProtocollato().getNumProtocollo() : allegato.getDocumentoProtocollato().getNumProtocolloMaster();
								SalvaAllegatiProtocollatiRequest salvaAllegatoProtocollatoRequest = getNewSalvaAllegatiProtocollatiRequest(request.getIdVerbale(), idOrdVerbSog, allegato);
								MessageVO  resp = salvaAllegatoProtocollatoOrdinanzaSoggetto(salvaAllegatoProtocollatoRequest, userDetails, pregresso);
								if (resp!=null && !(protocolliMasterSelezionati.contains(numProtocolloAllegato))) 
									msgResponseList.add(resp);			
							} else {
								// allegato da FS		
								int numeroAllegati = 0;
								cnmTAllegato = salvaSingoloAllegatoMulti(allegato, cnmTUser, folder, soggettoActa, rootActa, numeroAllegati, entitaFruitoreAllegato, pregresso, soggetti, TipoProtocolloAllegato.SALVA_MULTI_SENZA_PROTOCOLARE);	// 20210701_LC Jira 155	-	protocollare
								cnmTAllegato.setCnmDStatoAllegato(avviospostamentoActa);
								cnmTAllegato.setDataOraUpdate(now);
								cnmTAllegato.setIdActaMaster(idActa);
								allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);
								
								CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog = new CnmRAllegatoOrdVerbSog();
								CnmRAllegatoOrdVerbSogPK cnmRAllegatoOrdVerbSogPK = new CnmRAllegatoOrdVerbSogPK();
								cnmRAllegatoOrdVerbSogPK.setIdAllegato(cnmTAllegato.getIdAllegato());
								cnmRAllegatoOrdVerbSogPK.setIdOrdinanzaVerbSog(cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog());
								cnmRAllegatoOrdVerbSog.setCnmTUser(cnmTUser);
								cnmRAllegatoOrdVerbSog.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
								cnmRAllegatoOrdVerbSog.setId(cnmRAllegatoOrdVerbSogPK);
								cnmRAllegatoOrdVerbSogRepository.save(cnmRAllegatoOrdVerbSog);		
							}		
							
						}
					}
				}
			
			} else {
				
				// solo allegati, vengono protocolalti ed avranno un protocollo != da quello del master (istanza rateizzazione, già presente)				
				for (AllegatoMultiploVO allegato : listaAllegati) {					
					if (allegato.getDocumentoProtocollato() != null) {
						// allegato ricercato su acta		
						String numProtocolloAllegato = allegato.getDocumentoProtocollato().getNumProtocollo() != null ? allegato.getDocumentoProtocollato().getNumProtocollo() : allegato.getDocumentoProtocollato().getNumProtocolloMaster();	
						SalvaAllegatiProtocollatiRequest salvaAllegatoProtocollatoRequest = getNewSalvaAllegatiProtocollatiRequest(request.getIdVerbale(), idOrdVerbSog, allegato);
						MessageVO  resp = salvaAllegatoProtocollatoOrdinanzaSoggetto(salvaAllegatoProtocollatoRequest, userDetails, pregresso);
						if (resp!=null && !(protocolliMasterSelezionati.contains(numProtocolloAllegato))) 
							msgResponseList.add(resp);			
					} else {
						// allegato da FS				
						cnmTAllegato = salvaSingoloAllegatoMulti(allegato, cnmTUser, folder, soggettoActa, rootActa, 0, entitaFruitoreAllegato, pregresso, soggetti, TipoProtocolloAllegato.NON_PROTOCOLLARE);
					}					
				}			
			}
		
			
			
			
			
			if(pregresso) {
				// riporto lo stato a quello precedente
				cnmTOrdinanza.setCnmDStatoOrdinanza(stato);
				cnmTOrdinanzaRepository.save(cnmTOrdinanza);
			}

	
		}
		
		// contiene solo i msg per allegati i cui master non sono stati spostati
		return msgResponseList;
	}


	
	
	
	
	
	
	
	
	private CnmTAllegato salvaSingoloAllegatoMulti(AllegatoMultiploVO allegato, CnmTUser cnmTUser, String folder, String soggettoActa, String rootActa, int numeroAllegati, String fruitore, boolean pregresso, List<CnmTSoggetto> soggetti, TipoProtocolloAllegato tipoProtocollazione) {
		CnmTAllegato cnmTAllegato = null;

		byte[] byteFile = allegato.getFile();
		String fileName = allegato.getFilename();
		Long idTipoAllegato = allegato.getIdTipoAllegato(); // deve essere 26 per master e 38 per allegati (se siamo in gestione istanza)

		// controllo dimensione allegato
		UploadUtils.checkDimensioneAllegato(byteFile);

		// 20201026 PP- controllo se e' stato caricato un file firmato , con firma non valida senza firma
		utilsDoqui.checkFileSign(byteFile, fileName);
		
		// 20210701_LC Jira 158 - documento senza allegati (tipologiaDocSenzaAllegati + gli si passa isMaster==FALSE altrimenti lo classifica in Acta come documento con allegati: anche quando è un master, in Acta deve essere un documento singolo)
		cnmTAllegato = commonAllegatoService.salvaAllegato(byteFile, fileName, idTipoAllegato, null, cnmTUser, tipoProtocollazione, folder, fruitore,
				allegato.isMaster(), false, soggettoActa, rootActa, numeroAllegati, 0, StadocServiceFacade.TIPOLOGIA_DOC_ACTA_DOC_INGRESSO_SENZA_ALLEGATI, soggetti);	

		// 20201021 PP - Imposto il flag pregresso sull'allegato
		cnmTAllegato.setFlagDocumentoPregresso(pregresso);
		cnmTAllegatoRepository.save(cnmTAllegato);
		
		return cnmTAllegato;

	}


	private boolean isMasterPresent(List<AllegatoMultiploVO> allegati) {
		
		for (AllegatoMultiploVO allegato : allegati) {
			if (allegato.isMaster()) return true;
		}
		
		return false;
	}
	
	
	

	private SalvaAllegatiProtocollatiRequest getNewSalvaAllegatiProtocollatiRequest(Integer idVerbale, Integer idOrdinanzaVerbSog, AllegatoMultiploVO allegato) {
		
		SalvaAllegatiProtocollatiRequest response = new SalvaAllegatiProtocollatiRequest();

		List<Integer> listIdOVS = Collections.singletonList(idOrdinanzaVerbSog);
		response.setIdOrdinanzaVerbaleSoggetto(listIdOVS);
		response.setIdVerbale(idVerbale);
		response.setDocumentoProtocollato(allegato.getDocumentoProtocollato());

		SalvaAllegatoRequest salvaAllegatoRequest = new SalvaAllegatoRequest();
		salvaAllegatoRequest.setFile(allegato.getFile());
		salvaAllegatoRequest.setFilename(allegato.getFilename());
		salvaAllegatoRequest.setIdTipoAllegato(allegato.getIdTipoAllegato());
		List<SalvaAllegatoRequest> SalvaAllegatoRequestList = Collections.singletonList(salvaAllegatoRequest);
				
		response.setAllegati(SalvaAllegatoRequestList);
	
		return response;
	}
		
	
	
	
}
