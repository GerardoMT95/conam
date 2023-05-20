/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.notifica.NotificaService;
import it.csi.conam.conambl.business.service.sollecito.AllegatoSollecitoService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.ROrdinanzaVerbSogEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.SollecitoEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoSollecitoEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.TipoSollecitoEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmTSoggettoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;

@Component
public class SollecitoEntityMapperImpl implements SollecitoEntityMapper {

	@Autowired
	private StatoSollecitoEntityMapper statoSollecitoEntityMapper;
	@Autowired
	private TipoSollecitoEntityMapper tipoSollecitoEntityMapper;
	@Autowired
	private UtilsDate utilsDate;

	@Autowired
	private AllegatoSollecitoService allegatoSollecitoService;

	@Autowired
	private NotificaService notificaService;

	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	
	@Autowired
	private ROrdinanzaVerbSogEntityMapper rOrdinanzaVerbSogEntityMapper;

	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;
	@Autowired
	private CommonSoggettoService commonSoggettoService;
	

	@Override
	public SollecitoVO mapEntityToVO(CnmTSollecito dto) {
		if (dto == null)
			return null;
		SollecitoVO sollecitoVO = new SollecitoVO();
		sollecitoVO.setDataScadenza(utilsDate.asLocalDate(dto.getDataScadenzaRata()));
		sollecitoVO.setIdSollecito(dto.getIdSollecito());
		sollecitoVO.setImportoSollecitato(dto.getImportoSollecito());
		sollecitoVO.setMaggiorazione(dto.getMaggiorazione());
		sollecitoVO.setNumeroProtocollo(dto.getNumeroProtocollo());
		sollecitoVO.setStatoSollecito(statoSollecitoEntityMapper.mapEntityToVO(dto.getCnmDStatoSollecito()));
		sollecitoVO.setTipoSollecito(tipoSollecitoEntityMapper.mapEntityToVO(dto.getCnmDTipoSollecito()));
		sollecitoVO.setIdSoggettoOrdinanza(dto.getCnmROrdinanzaVerbSog().getIdOrdinanzaVerbSog());
		sollecitoVO.setIsNotificaCreata(notificaService.isNotificaCreata(dto));
		sollecitoVO.setDataFineValidita(utilsDate.asLocalDate(dto.getDataFineValidita()));

		if (dto.getCnmTUser2() != null) {
			UserDetails user = SecurityUtils.getUser();
			CnmTUser cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
			long id = cnmTUser.getIdUser();

			if (id == dto.getCnmTUser2().getIdUser())
				sollecitoVO.setIsCreatoDalloUserCorrente(true);
			else
				sollecitoVO.setIsCreatoDalloUserCorrente(false);
		}

		long idStato = dto.getCnmDStatoSollecito().getIdStatoSollecito();
		sollecitoVO.setIsRiconciliaEnable(
				idStato == Constants.ID_STATO_SOLLECITO_NON_NOTIFICATO || idStato == Constants.ID_STATO_SOLLECITO_NOTIFICATO || idStato == Constants.ID_STATO_SOLLECITO_PROTOCOLLATO);

		CnmTAllegato cnmTAllegato = allegatoSollecitoService.getAllegatoSollecito(dto, TipoAllegato.LETTERA_SOLLECITO);

		if (cnmTAllegato != null) {
			sollecitoVO.setNumeroProtocollo(cnmTAllegato.getNumeroProtocollo());
		} else {
			CnmTAllegato cnmTAllegatoRate = allegatoSollecitoService.getAllegatoSollecito(dto, TipoAllegato.LETTERA_SOLLECITO_RATE);
			if (cnmTAllegatoRate != null) {
				sollecitoVO.setNumeroProtocollo(cnmTAllegatoRate.getNumeroProtocollo());
			}
		}
		
		

		boolean richiestaInviata = allegatoSollecitoService.isRichiestaBollettiniInviata(dto);
		boolean bollettinoDaCreare = !richiestaInviata && (dto.getCnmDStatoSollecito().getIdStatoSollecito() == Constants.ID_STATO_SOLLECITO_PROTOCOLLATO
				|| dto.getCnmDStatoSollecito().getIdStatoSollecito() == Constants.ID_STATO_SOLLECITO_NOTIFICATO) && dto.getDataScadenzaRata() != null;
		boolean dowloadBollettiniEnable = richiestaInviata && dto.getDataScadenzaRata() != null;

		sollecitoVO.setBollettinoDaCreare(bollettinoDaCreare);
		sollecitoVO.setDownloadBollettiniEnable(dowloadBollettiniEnable);
		
		// 20201127_LC-	soggetto		
		CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = dto.getCnmROrdinanzaVerbSog();
		SoggettoVO soggetto = rOrdinanzaVerbSogEntityMapper.mapEntityToVO(cnmROrdinanzaVerbSog);

		// 20201217_LC - JIRA 118
		CnmTVerbale cnmTVerbale = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTVerbale();
		CnmTSoggetto cnmTSoggetto = cnmTSoggettoRepository.findOne(soggetto.getId());	
		if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
			soggetto = commonSoggettoService.attachResidenzaPregressi(soggetto, cnmTSoggetto, cnmTVerbale.getIdVerbale());
		}	
		
		sollecitoVO.setSoggetto(soggetto);

		return sollecitoVO;
		
	}

	@Override
	public CnmTSollecito mapVOtoEntity(SollecitoVO vo) {
		CnmTSollecito cnmTSollecito = new CnmTSollecito();
		mapVOtoEntityUpdate(vo, cnmTSollecito);
		return cnmTSollecito;
	}

	@Override
	public CnmTSollecito mapVOtoEntityUpdate(SollecitoVO vo, CnmTSollecito cnmTSollecito) {
		cnmTSollecito.setMaggiorazione(vo.getMaggiorazione());
		cnmTSollecito.setImportoSollecito(vo.getImportoSollecitato() != null ? vo.getImportoSollecitato().setScale(2, RoundingMode.HALF_UP) : null);
		cnmTSollecito.setDataScadenzaRata(utilsDate.asDate(vo.getDataScadenza()));
		cnmTSollecito.setDataFineValidita(utilsDate.asDate(vo.getDataFineValidita()));
		return cnmTSollecito;
	}

}
