/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.conam.conambl.business.service.RiferimentiNormativiService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.CnmDAmbito;
import it.csi.conam.conambl.integration.entity.CnmDArticolo;
import it.csi.conam.conambl.integration.entity.CnmDComma;
import it.csi.conam.conambl.integration.entity.CnmDLettera;
import it.csi.conam.conambl.integration.entity.CnmDNorma;
import it.csi.conam.conambl.integration.entity.CnmDStatoManuale;
import it.csi.conam.conambl.integration.entity.CnmDStatoPregresso;
import it.csi.conam.conambl.integration.entity.CnmDStatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmREnteNorma;
import it.csi.conam.conambl.integration.entity.CnmRFunzionarioIstruttore;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleIllecito;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.mapper.entity.AmbitoEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.ArticoloEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.CommaEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.ComuneEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.EnteEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.IstruttoreEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.LetteraEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.NormaEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.ProvinciaEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.RegioneEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoManualeEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoVerbaleEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.VerbaleEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmDAmbitoRepository;
import it.csi.conam.conambl.integration.repositories.CnmDComuneRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoManualeRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoPregressoRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmRFunzionarioIstruttoreRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleIllecitoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoFieldRepository;
import it.csi.conam.conambl.request.verbale.DatiVerbaleRequest;
import it.csi.conam.conambl.util.VerbaleSearchParam;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import it.csi.conam.conambl.vo.leggi.RiferimentiNormativiVO;
import it.csi.conam.conambl.vo.verbale.MinVerbaleVO;
import it.csi.conam.conambl.vo.verbale.StatoVerbaleVO;
import it.csi.conam.conambl.vo.verbale.VerbaleVO;

@Component
public class VerbaleEntityMapperImpl implements VerbaleEntityMapper {

	@Autowired
	private StatoVerbaleEntityMapper statoEntityMapper;
	@Autowired
	private StatoManualeEntityMapper statoManualeEntityMapper;
	@Autowired
	private ComuneEntityMapper comuneEntityMapper;
	@Autowired
	private ProvinciaEntityMapper provinciaEntityMapper;
	@Autowired
	private RegioneEntityMapper regioneEntityMapper;
	@Autowired
	private EnteEntityMapper enteEntityMapper;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmDStatoVerbaleRepository cnmDStatoVerbaleRepository;
	@Autowired
	private CnmDComuneRepository cnmDComuneRepository;
	
	
	@Autowired
	private CnmRVerbaleIllecitoRepository cnmRVerbaleIlecitoRepository;
	@Autowired
	private NormaEntityMapper normaEntityMapper;
	@Autowired
	private AmbitoEntityMapper ambitoEntityMapper;
	@Autowired
	private ArticoloEntityMapper articoloEntityMapper;
	@Autowired
	private LetteraEntityMapper letteraEntityMapper;
	@Autowired
	private CommaEntityMapper commaEntityMapper;
	@Autowired
	private CnmRFunzionarioIstruttoreRepository cnmRFunzionarioIstruttoreRepository;
	@Autowired
	private IstruttoreEntityMapper istruttoreEntityMapper;


	@Autowired
	private CnmDStatoManualeRepository cnmDStatoManualeRepository;
	@Autowired
	private CnmDAmbitoRepository cnmDAmbitoRepository;
	
	
	//LUCIO ROSADINI
	@Autowired
	private RiferimentiNormativiService riferimentiNormativiService;

	@Autowired
	private CnmDStatoPregressoRepository cnmDStatoPregressoRepository;
	
	@Autowired
	private CnmTAllegatoFieldRepository cnmTAllegatoFieldRepository;
	
	
	@Override
	public VerbaleVO mapEntityToVO(CnmTVerbale dto) {
		return mapEntityToVO(dto, false);
	}
	

	@Override
	public MinVerbaleVO mapEntityToMinVO(CnmTVerbale dto) {
		if (dto == null)
			return null;
		MinVerbaleVO verbale = new MinVerbaleVO();

		verbale.setEnteAccertatore(enteEntityMapper.mapEntityToVO(dto.getCnmDEnte()));
		verbale.setId(dto.getIdVerbale().longValue());
		verbale.setNumero(dto.getNumVerbale());
		StatoVerbaleVO stato = statoEntityMapper.mapEntityToVO(dto.getCnmDStatoVerbale());
		verbale.setStato(stato);
		verbale.setId(dto.getIdVerbale().longValue());
		verbale.setNumeroProtocollo(dto.getNumeroProtocollo());
		verbale.setDataCaricamento(utilsDate.asLocalDateTime(dto.getDataOraInsert()));
		verbale.setUser(dto.getCnmTUser2().getNome() + " " + dto.getCnmTUser2().getCognome());

		verbale.setModificabile(Boolean.FALSE);

		verbale.setStatoManuale(statoManualeEntityMapper.mapEntityToVO(dto.getCnmDStatoManuale()));
		
		
		return verbale;
	}
	
	
	@Override
	public VerbaleVO mapEntityToVO(CnmTVerbale dto, boolean filtraNormeScadute) {
		if (dto == null)
			return null;
		VerbaleVO verbale = new VerbaleVO();
		verbale.setComune(comuneEntityMapper.mapEntityToVO(dto.getCnmDComune()));
		verbale.setProvincia(provinciaEntityMapper.mapEntityToVO(dto.getCnmDComune().getCnmDProvincia()));
		verbale.setRegione(regioneEntityMapper.mapEntityToVO(dto.getCnmDComune().getCnmDProvincia().getCnmDRegione()));
		verbale.setContestato(dto.getContestato());
		verbale.setDataOraAccertamento(utilsDate.asLocalDateTime(dto.getDataOraAccertamento()));
		verbale.setDataOraViolazione(utilsDate.asLocalDateTime(dto.getDataOraViolazione()));
		verbale.setEnteAccertatore(enteEntityMapper.mapEntityToVO(dto.getCnmDEnte()));
		verbale.setId(dto.getIdVerbale().longValue());
		verbale.setImporto(dto.getImportoVerbale().doubleValue());
		
		// 20210921 PP - valorizzo importo residuo sottraendo gli importi già pagati
		verbale.setImportoResiduo(verbale.getImporto());
		try {
			BigDecimal importoPagato = cnmTAllegatoFieldRepository.getImportoPagatoByIdVerbale(dto.getIdVerbale());
			verbale.setImportoResiduo(verbale.getImportoResiduo()-importoPagato.intValue());
		}catch(Throwable t) {}

		
		verbale.setIndirizzo(dto.getLocalitaViolazione());
		verbale.setNumero(dto.getNumVerbale());
		verbale.setNumeroProtocollo(dto.getNumeroProtocollo());
		verbale.setStato(statoEntityMapper.mapEntityToVO(dto.getCnmDStatoVerbale()));
		verbale.setStatoManuale(statoManualeEntityMapper.mapEntityToVO(dto.getCnmDStatoManuale()));
		
		
		
		
		//verbale.setStatoManuale(statoManualeEntityMapper.mapEntityToVO(dto.getStatoManuale()));
		verbale.setContestato(dto.getContestato());
		verbale.setId(dto.getIdVerbale().longValue());
		List<CnmRVerbaleIllecito> cnmRVerbaleIllecitoList = cnmRVerbaleIlecitoRepository.findByCnmTVerbale(dto);
		List<RiferimentiNormativiVO> listRifNorm = new ArrayList<>();
		EnteVO enteRiferimentiNormativi = null;
		if (cnmRVerbaleIllecitoList != null && !cnmRVerbaleIllecitoList.isEmpty()) {
			for (CnmRVerbaleIllecito cnmRVerbaleIllecito : cnmRVerbaleIllecitoList) {
				RiferimentiNormativiVO rifNorm = new RiferimentiNormativiVO();
				CnmDLettera cnmDLettera = cnmRVerbaleIllecito.getCnmDLettera();
				CnmDComma cnmDComma = cnmDLettera.getCnmDComma();
				CnmDArticolo cnmDArticolo = cnmDComma.getCnmDArticolo();
				CnmREnteNorma cnmREnteNorma = cnmDArticolo.getCnmREnteNorma();
				CnmDNorma cnmDNorma = cnmREnteNorma.getCnmDNorma();
				CnmDAmbito cnmDAmbito = cnmDNorma.getCnmDAmbito();
				if (enteRiferimentiNormativi == null) {
					enteRiferimentiNormativi = enteEntityMapper.mapEntityToVO(cnmREnteNorma.getCnmDEnte());
				}
				if (!filtraNormeScadute) {
					rifNorm.setLettera(letteraEntityMapper.mapEntityToVO(cnmDLettera));
					rifNorm.setArticolo(articoloEntityMapper.mapEntityToVO(cnmDArticolo));
					rifNorm.setComma(commaEntityMapper.mapEntityToVO(cnmDComma));
					rifNorm.setNorma(normaEntityMapper.mapEntityToVO(cnmDNorma));
					rifNorm.setAmbito(ambitoEntityMapper.mapEntityToVO(cnmDAmbito));
					rifNorm.setId(cnmRVerbaleIllecito.getIdVerbaleIllecito());
					listRifNorm.add(rifNorm);
				} else {
					rifNorm.setAmbito(ambitoEntityMapper.mapEntityToVO(cnmDAmbito));
					LocalDate dataFineValiditaEnteNorma = utilsDate.asLocalDate(cnmREnteNorma.getFineValidita());
					LocalDate now = LocalDate.now();
					if (dataFineValiditaEnteNorma == null || dataFineValiditaEnteNorma.isAfter(now)) {
						rifNorm.setNorma(normaEntityMapper.mapEntityToVO(cnmDNorma));
						rifNorm.setId(cnmRVerbaleIllecito.getIdVerbaleIllecito());
						LocalDate dataFineValiditaArticolo = utilsDate.asLocalDate(cnmDArticolo.getFineValidita());
						if (dataFineValiditaArticolo == null || dataFineValiditaArticolo.isAfter(now)) {
							rifNorm.setArticolo(articoloEntityMapper.mapEntityToVO(cnmDArticolo));
							LocalDate dataFineValiditaComma = utilsDate.asLocalDate(cnmDComma.getFineValidita());
							if (dataFineValiditaComma == null || dataFineValiditaComma.isAfter(now)) {
								rifNorm.setComma(commaEntityMapper.mapEntityToVO(cnmDComma));
								LocalDate dataFineValiditaLettera = utilsDate.asLocalDate(cnmDLettera.getFineValidita());
								if (dataFineValiditaLettera == null || dataFineValiditaLettera.isAfter(now))
									rifNorm.setLettera(letteraEntityMapper.mapEntityToVO(cnmDLettera));
							}
						}
						listRifNorm.add(rifNorm);
					}
				}

			}
		}
		verbale.setEnteRiferimentiNormativi(enteRiferimentiNormativi);
		verbale.setRiferimentiNormativi(listRifNorm);

		CnmRFunzionarioIstruttore cnmRFunzionarioIstruttore = cnmRFunzionarioIstruttoreRepository.findByCnmTVerbaleAndDataAssegnazione(dto);
		if (cnmRFunzionarioIstruttore != null)
			verbale.setIstruttoreAssegnato(istruttoreEntityMapper.mapEntityToVO(cnmRFunzionarioIstruttore.getCnmTUser()));
		
		verbale.setIstruttoreCreatore(istruttoreEntityMapper.mapEntityToVO(dto.getCnmTUser2()));

		if(dto.getCnmDStatoPregresso() != null && dto.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
			verbale.setPregresso(true);
		}
		
		// 20211105 PP - nuovo campo Comune Ente
		if(dto.getCnmDComuneEnte() != null) {
			verbale.setComuneEnte(comuneEntityMapper.mapEntityToVO(dto.getCnmDComuneEnte()));
			verbale.getEnteAccertatore().setDenominazione(verbale.getEnteAccertatore().getDenominazione() + " - " + dto.getCnmDComuneEnte().getDenomComune());
		}
		
		return verbale;
	}
	

	
	
	@Override
	public CnmTVerbale mapVOtoEntity(VerbaleVO vo) {
		if (vo == null)
			return null;

		CnmTVerbale cnmTVerbale = new CnmTVerbale();
		mapVOtoEntityUpdate(vo, cnmTVerbale);
		cnmTVerbale.setCnmDStatoVerbale(cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_INCOMPLETO));
		cnmTVerbale.setContestato(vo.getContestato());
		cnmTVerbale.setNumVerbale(vo.getNumero().toUpperCase());
		cnmTVerbale.setContestato(vo.getContestato() != null ? vo.getContestato() : Boolean.FALSE);
		return cnmTVerbale;
	}

	@Override
	public CnmTVerbale mapVOtoEntityUpdate(VerbaleVO vo, CnmTVerbale cnmTVerbale) {
		if (vo == null)
			return null;

		cnmTVerbale.setContestato(vo.getContestato() != null ? vo.getContestato() : Boolean.FALSE);
		cnmTVerbale.setDataOraAccertamento(utilsDate.asTimeStamp(vo.getDataOraAccertamento()));
		cnmTVerbale.setNumeroProtocollo(vo.getNumeroProtocollo());
		cnmTVerbale.setDataOraViolazione(utilsDate.asTimeStamp(vo.getDataOraViolazione()));
		cnmTVerbale.setImportoVerbale(new BigDecimal(vo.getImporto()).setScale(2, RoundingMode.HALF_UP));
		cnmTVerbale.setCnmDComune(cnmDComuneRepository.findOne(vo.getComune().getId()));
		cnmTVerbale.setLocalitaViolazione(vo.getIndirizzo());
		Long statoManualeId = Constants.ID_STATO_MANUALE_DEFAULT;
		if (vo.getStatoManuale() != null){
			statoManualeId = vo.getStatoManuale().getId();
		}
		cnmTVerbale.setCnmDStatoManuale(cnmDStatoManualeRepository.findOne(statoManualeId));
		if(vo.getComuneEnte()!=null && vo.getComuneEnte().getId() != null) {
			cnmTVerbale.setCnmDComuneEnte(cnmDComuneRepository.findOne(vo.getComuneEnte().getId()));
		}else {
			cnmTVerbale.setCnmDComuneEnte(null);
		}
		
		return cnmTVerbale;
	}

	public VerbaleSearchParam getVerbaleParamFromRequest(DatiVerbaleRequest dati) {
		
		VerbaleSearchParam parametri = new VerbaleSearchParam();

		if (dati != null) {
			// se ho selezionato una legge in quel caso cerco solo quelle della
			// legge in questione
			if (dati.getNorma() != null && dati.getEnte() != null) {
				parametri.setLettera(
					riferimentiNormativiService.getLettereByIdNormaAndEnte(
						dati.getNorma().getId(),
						dati.getEnte().getId(),
						false
					)
				);
					
			}
			if (dati.getStato() != null && !dati.getStato().isEmpty()) {
				List<Long> idS = new ArrayList<Long>();
				for (StatoVerbaleVO s : dati.getStato())
					idS.add(s.getId());
				parametri.setStatoVerbale((List<CnmDStatoVerbale>) cnmDStatoVerbaleRepository.findAll(idS));
			}

			//FILTRO STATI MANUALI
			if (dati.getListaStatiManuali() != null && !dati.getListaStatiManuali().isEmpty()) {
				parametri.setStatoManuale((List<CnmDStatoManuale>) cnmDStatoManualeRepository.findAll(dati.getListaStatiManuali()));
			}
			parametri.setNumeroProtocollo(dati.getNumeroProtocollo());
			parametri.setNumeroVerbale(dati.getNumeroVerbale());
			parametri.setStatiPregresso(getStatiPregresso(dati.isPregresso()));
			
			// 20211125_LC Jira 184 - ricerca per ambito
			if (dati.getAmbito() != null && dati.getAmbito().getId() != null) parametri.setAmbito(cnmDAmbitoRepository.findOne(dati.getAmbito().getId().intValue()));
			
		}
		return parametri;
	}
	private List<CnmDStatoPregresso> getStatiPregressoFromIdList(List<Long> idList){
		return (List<CnmDStatoPregresso>) cnmDStatoPregressoRepository.findAll(idList);
	}

	private List<CnmDStatoPregresso> getStatiPregresso(boolean isPregresso){
		List<Long> listaStatiPregressi = Constants.LISTA_STATI_LAVORATO;
		if(isPregresso) listaStatiPregressi = Constants.LISTA_STATI_IN_LAVORAZIONE;
		return getStatiPregressoFromIdList(listaStatiPregressi);
	}
	
}
