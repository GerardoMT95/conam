/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;

import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleSoggettoService;
import it.csi.conam.conambl.business.service.verbale.SoggettoService;
import it.csi.conam.conambl.business.service.verbale.UtilsSoggetto;
import it.csi.conam.conambl.business.service.verbale.UtilsVerbale;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.entity.CnmDElementoElenco;
import it.csi.conam.conambl.integration.entity.CnmDTipoAllegato;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTAllegatoField;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.mapper.entity.SoggettoEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.SoggettoPregressiEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmDElementoElencoRepository;
import it.csi.conam.conambl.integration.repositories.CnmDTipoAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleSoggettoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoFieldRepository;
import it.csi.conam.conambl.integration.repositories.CnmTSoggettoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTVerbaleRepository;
import it.csi.conam.conambl.integration.specification.CnmTSoggettoSpecification;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.UtilsFieldAllegato;
import it.csi.conam.conambl.vo.verbale.DatiRelataNotificaVO;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoPregressiVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

@Service
public class SoggettoServiceImpl implements SoggettoService {

	@Autowired
	private SoggettoEntityMapper soggettoEntityMapper;
	@Autowired
	private SoggettoPregressiEntityMapper soggettoPregressiEntityMapper;
	@Autowired
	private UtilsSoggetto utilsSoggetto;
	/*@Autowired
	private SoggettoVerbaleService soggettoVerbaleService;*/
	@Autowired
	private CommonSoggettoService commonSoggettoService;
	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;
	@Autowired
	private AllegatoVerbaleSoggettoService allegatoVerbaleSoggettoService;
	@Autowired
	private UtilsVerbale utilsVerbale;
	
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	
	@Autowired
	private CnmRAllegatoVerbSogRepository cnmRAllegatoVerbSogRepository;
	
	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;
	
	@Autowired
	private CnmDTipoAllegatoRepository cnmDTipoAllegatoRepository;
	
	@Autowired
	private CnmTAllegatoFieldRepository cnmTAllegatoFieldRepository;
	
	@Autowired
	private CnmDElementoElencoRepository cnmDElementoElencoRepository;

	/*LUCIO 2021/04/23 - FINE Gestione casi di recidività*/
	public List<SoggettoVO> ricercaSoggetti(MinSoggettoVO soggetto, UserDetails userDetails){
		List<CnmTSoggetto> soggetti = cnmTSoggettoRepository.findAll(
			CnmTSoggettoSpecification.findSoggetti(soggetto)
		);
		return soggettoEntityMapper.mapListEntityToListVO(soggetti);
	}
	/*LUCIO 2021/04/23 - Gestione casi di recidività*/
	@Override
	public SoggettoVO getSoggettoById(Integer id, UserDetails userDetails, boolean includiControlloUtenteProprietario) {
		CnmTSoggetto cnmTSoggetto = utilsSoggetto.validateAndGetCnmTSoggetto(id);

		return soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);

	}
	
	@Override
	public SoggettoPregressiVO getSoggettoPregressiById(Integer id, UserDetails userDetails, boolean includiControlloUtenteProprietario) {
		CnmTSoggetto cnmTSoggetto = utilsSoggetto.validateAndGetCnmTSoggetto(id);

		return soggettoPregressiEntityMapper.mapEntityToVO(cnmTSoggetto);

	}

	@Override
	public SoggettoVO getSoggettoByIdAndIdVerbale(Integer id, Integer idVerbale, UserDetails userDetails, boolean includiControlloUtenteProprietario) {
		CnmTSoggetto cnmTSoggetto = utilsSoggetto.validateAndGetCnmTSoggetto(id);

		SoggettoVO soggetto = soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);
		soggetto = commonSoggettoService.attachResidenzaPregressi(soggetto, cnmTSoggetto, idVerbale);
		soggetto = attachDatiRelataNotifica(soggetto, cnmTSoggetto, idVerbale);
		
		return soggetto;

	}
	private SoggettoVO attachDatiRelataNotifica(SoggettoVO soggetto, CnmTSoggetto cnmTSoggetto, Integer idVerbale) {
		
		CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findOne(idVerbale);
		CnmRVerbaleSoggetto cnmRVerbaleSoggetto = cnmRVerbaleSoggettoRepository.findByCnmTVerbaleAndCnmTSoggetto(cnmTVerbale, cnmTSoggetto);
		if(cnmRVerbaleSoggetto!= null) {
			CnmDTipoAllegato cnmDTipoAllegato = cnmDTipoAllegatoRepository.findOne(TipoAllegato.RELATA_NOTIFICA.getId());
			List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggettoAndCnmTAllegato(cnmRVerbaleSoggetto, cnmDTipoAllegato);
			if(cnmRAllegatoVerbSogs != null && cnmRAllegatoVerbSogs.size()>0) {
				CnmRAllegatoVerbSog cnmRAllegatoVerbSog = cnmRAllegatoVerbSogs.get(0);
				cnmRAllegatoVerbSog.getCnmTAllegato();
				
				DatiRelataNotificaVO datiRelataNotificaVO = new DatiRelataNotificaVO();
				List<CnmTAllegatoField> field = cnmTAllegatoFieldRepository.findByCnmTAllegato(cnmRAllegatoVerbSog.getCnmTAllegato());
				
				CnmTAllegatoField numeroRaccomandata = Iterables.tryFind(field, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_NUMERO_RACC_NOTIFICA))
						.orNull();
				datiRelataNotificaVO.setNumeroRaccomandata(numeroRaccomandata != null ? numeroRaccomandata.getValoreNumber() : null);
				
				CnmTAllegatoField dataSpedizione = Iterables.tryFind(field, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_DATA_SPEDIZIONE_NOTIFICA))
						.orNull();
				datiRelataNotificaVO.setDataSpedizione(dataSpedizione!=null&&dataSpedizione.getValoreData()!=null? new SimpleDateFormat("dd/MM/YYYY").format(dataSpedizione.getValoreData()):null);
				
				CnmTAllegatoField dataNotifica = Iterables.tryFind(field, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_DATA_NOTIFICA))
						.orNull();
				datiRelataNotificaVO.setDataNotifica(dataNotifica!=null&&dataNotifica.getValoreData()!=null?new SimpleDateFormat("dd/MM/YYYY").format(dataNotifica.getValoreData()):null);
				// new SimpleDateFormat("DD/MM/YYYY").format(dataNotifica.getValoreData())
				CnmTAllegatoField speseNotifica = Iterables.tryFind(field, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_SPESE_NOTIFICA))
						.orNull();
				datiRelataNotificaVO.setImportoSpeseNotifica(speseNotifica!=null?speseNotifica.getValoreNumber():null);
				
				CnmTAllegatoField modalitaNotifica = Iterables.tryFind(field, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_MODALITA_NOTIFICA))
						.orNull();
				
				if(modalitaNotifica!=null && modalitaNotifica.getValoreNumber()!=null) {
					if(modalitaNotifica.getCnmCField()!=null && modalitaNotifica.getCnmCField().getCnmDElenco() != null) {
						BigDecimal elenco = new BigDecimal(modalitaNotifica.getCnmCField().getCnmDElenco().getIdElenco());
						CnmDElementoElenco cnmDElementoElenco = cnmDElementoElencoRepository.findByIdElencoAndIdElementoElenco(elenco, modalitaNotifica.getValoreNumber().longValue());
						datiRelataNotificaVO.setModalita(cnmDElementoElenco!=null?cnmDElementoElenco.getDescElementoElenco():null);
					}
				}
				
				CnmTAllegatoField notificata = Iterables.tryFind(field, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_NOTIFICATA_NOTIFICA))
						.orNull();
				datiRelataNotificaVO.setNotificata(notificata!=null?notificata.getValoreBoolean():null);
				
				soggetto.setRelataNotifica(datiRelataNotificaVO);				
			}
		}
		
		return soggetto;
	}
	@Override
	public List<SoggettoVO> ricercaSoggettiRelata(Integer idVerbale, UserDetails userDetails) {
		List<SoggettoVO> soggettiRelata = null;
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(idVerbale);
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettosWithoutAllegato = allegatoVerbaleSoggettoService.findCnmRVerbaleSoggettosWithoutAllegato(cnmTVerbale, TipoAllegato.RELATA_NOTIFICA);
		if(cnmRVerbaleSoggettosWithoutAllegato != null && cnmRVerbaleSoggettosWithoutAllegato.size() > 0) {
			soggettiRelata = new ArrayList<SoggettoVO>();
			for(CnmRVerbaleSoggetto cnmRVerbaleSoggetto : cnmRVerbaleSoggettosWithoutAllegato) {
				SoggettoVO soggettoVO = soggettoEntityMapper.mapEntityToVO(cnmRVerbaleSoggetto.getCnmTSoggetto());
				soggettoVO.setIdSoggettoVerbale(cnmRVerbaleSoggetto.getIdVerbaleSoggetto());
				soggettiRelata.add(soggettoVO);
			}
		}
		return soggettiRelata;
	}
}
