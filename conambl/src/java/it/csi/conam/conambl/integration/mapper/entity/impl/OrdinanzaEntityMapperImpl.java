/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import com.google.common.collect.Iterables;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.AccontoEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.OrdinanzaEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoOrdinanzaMapper;
import it.csi.conam.conambl.integration.mapper.entity.TipoOrdinanzaMapper;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmROrdinanzaFiglioRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAccontoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTNotificaRepository;
import it.csi.conam.conambl.util.UtilsTipoAllegato;
import it.csi.conam.conambl.vo.ordinanza.MinOrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.OrdinanzaVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class OrdinanzaEntityMapperImpl implements OrdinanzaEntityMapper {

	@Autowired
	private CnmTAccontoRepository cnmTAccontoRepository;

	@Autowired
	private CnmTNotificaRepository cnmTNotificaRepository;
	
	/*LUCIO - 2021/04/19 - Gestione pagamenti definiti in autonomia (Scenario 8)*/
	private class ValoriOrdinanzaHelper{
		
		private List<CnmTNotifica> cnmTNotificaList;
		
		private List<CnmTAcconto> cnmTAccontoList;
		
		private CnmTOrdinanza cnmTOrdinanza;
		
		private BigDecimal speseNotifica = null;
		
		private BigDecimal importoPagato = null;
		
		private BigDecimal importoTotaleDaPagare = null;
		
		private BigDecimal importoResiduo = null;
		

		public List<CnmTAcconto> getCnmTAccontoList(){
			return cnmTAccontoList;
		}

		public ValoriOrdinanzaHelper(CnmTOrdinanza cnmTOrdinanza) {
			this.setCnmTOrdinanza(cnmTOrdinanza);
			cnmTNotificaList = cnmTNotificaRepository.findByCnmTOrdinanzas(cnmTOrdinanza);
			cnmTAccontoList = cnmTAccontoRepository.findByIdOrdinanza(cnmTOrdinanza.getIdOrdinanza());
			getImportoResiduo();
		}

		public void setCnmTOrdinanza(CnmTOrdinanza cnmTOrdinanza) {
			this.cnmTOrdinanza = cnmTOrdinanza;
		}

		public BigDecimal getSpeseNotifica() {
			if (speseNotifica == null) {
				speseNotifica = BigDecimal.ZERO;
				for (CnmTNotifica notifica: cnmTNotificaList) {
					speseNotifica = speseNotifica.add(notifica.getImportoSpeseNotifica());
				}
			}
			return speseNotifica;
		}

		public BigDecimal getImportoPagato() {
			if (importoPagato == null) {
				importoPagato = BigDecimal.ZERO;
				for (CnmTAcconto acconto: cnmTAccontoList) {
					importoPagato = importoPagato.add(acconto.getImporto());
				}
			}
			return importoPagato;
		}

		public BigDecimal getImportoTotaleDaPagare() {
			if (importoTotaleDaPagare == null) {
				importoTotaleDaPagare = cnmTOrdinanza.getImportoOrdinanza();
				if (importoTotaleDaPagare == null) importoTotaleDaPagare = BigDecimal.ZERO;
				importoTotaleDaPagare = importoTotaleDaPagare.add(getSpeseNotifica());
			}
			return importoTotaleDaPagare;
		}

		public BigDecimal getImportoResiduo() {
			if (importoResiduo == null) {
				importoResiduo = getImportoTotaleDaPagare();
				importoResiduo = importoResiduo.subtract(getImportoPagato());
			}
			return importoResiduo;
		}
	}
	/*LUCIO - 2021/04/19 - FINE Gestione pagamenti definiti in autonomia (Scenario 8)*/
	
	@Autowired
	private UtilsDate utilsDate;

	@Autowired
	private CnmRAllegatoOrdinanzaRepository cnmRAllegatoOrdinanzaRepository;

	@Autowired
	private StatoOrdinanzaMapper statoOrdinanzaMapper;

	@Autowired
	private TipoOrdinanzaMapper tipoOrdinanzaMapper;
	

	@Autowired
	private CnmROrdinanzaFiglioRepository cnmROrdinanzaFiglioRepository;
	

	@Autowired
	private AccontoEntityMapper accontoEntityMapper;
	
	@Override
	public MinOrdinanzaVO mapEntityToVO(CnmTOrdinanza dto){
		return mapEntityToVOLocal(dto);
	}
	
	private OrdinanzaVO mapEntityToVOLocal(CnmTOrdinanza dto) {
		OrdinanzaVO ordinanzaVO = new OrdinanzaVO();
		ordinanzaVO.setDataDeterminazione(utilsDate.asLocalDate(dto.getDataDeterminazione()));
		ordinanzaVO.setDataOrdinanza(utilsDate.asLocalDate(dto.getDataOrdinanza()));
		ordinanzaVO.setDataProtocollo(utilsDate.asLocalDateTime(dto.getDataOraProtocollo()));
		ordinanzaVO.setId(Long.valueOf(dto.getIdOrdinanza()));
		ordinanzaVO.setNumDeterminazione(dto.getNumDeterminazione());
		ordinanzaVO.setNumeroProtocollo(dto.getNumeroProtocollo());
		ordinanzaVO.setStato(statoOrdinanzaMapper.mapEntityToVO(dto.getCnmDStatoOrdinanza()));
		ordinanzaVO.setTipo(tipoOrdinanzaMapper.mapEntityToVO(dto.getCnmDTipoOrdinanza()));
		ordinanzaVO.setImportoOrdinanza(dto.getImportoOrdinanza());
		ordinanzaVO.setDataScadenza(utilsDate.asLocalDate(dto.getDataScadenzaOrdinanza()));
		ordinanzaVO.setPregresso(dto.isFlagDocumentoPregresso());
		
		ordinanzaVO.setDataFineValidita(utilsDate.asLocalDate(dto.getDataFineValidita()));
		
		// 20210325 lotto2scenario7
		List<CnmROrdinanzaFiglio> cnmROrdinanzaFiglioList = cnmROrdinanzaFiglioRepository.findByCnmTOrdinanzaFiglio(dto);
		if (cnmROrdinanzaFiglioList != null && !cnmROrdinanzaFiglioList.isEmpty()) {
			String numProt = cnmROrdinanzaFiglioList.get(0).getCnmTOrdinanza().getNumeroProtocollo();
			String dettaglio = "";
			if (numProt != null && StringUtils.isNotBlank(numProt)) {
				dettaglio = "Determina n. " + cnmROrdinanzaFiglioList.get(0).getCnmTOrdinanza().getNumDeterminazione() + 
						   " Protocollo n. " + cnmROrdinanzaFiglioList.get(0).getCnmTOrdinanza().getNumeroProtocollo();
			} else {
				dettaglio = "Determina n. " + cnmROrdinanzaFiglioList.get(0).getCnmTOrdinanza().getNumDeterminazione() + 
						   " NON PROTOCOLLATA";
			}
			ordinanzaVO.setDettaglioOrdinanzaAnnullata(dettaglio);
		}
		
		List<CnmRAllegatoOrdinanza> cnmRAllegatoOrdinanzaList = cnmRAllegatoOrdinanzaRepository.findByCnmTOrdinanza(dto);
		CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza = null;
		if(dto.isFlagDocumentoPregresso()) {
			// 20210304_LC ordinanza di annullamento non piò essere un documento pregresso
			TipoAllegato tipoAllegato = dto.getCnmDTipoOrdinanza().getIdTipoOrdinanza() == Constants.ID_TIPO_ORDINANZA_INGIUNZIONE?TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO:TipoAllegato.ORDINANZA_ARCHIVIAZIONE;
			cnmRAllegatoOrdinanza = Iterables
					.tryFind(cnmRAllegatoOrdinanzaList, UtilsTipoAllegato.findCnmRAllegatoOrdinanzaInCnmRAllegatoOrdinanzasByTipoAllegato(tipoAllegato)).orNull();
		} else {
			cnmRAllegatoOrdinanza = Iterables
					.tryFind(cnmRAllegatoOrdinanzaList, UtilsTipoAllegato.findCnmRAllegatoOrdinanzaInCnmRAllegatoOrdinanzasByTipoAllegato(TipoAllegato.LETTERA_ORDINANZA)).orNull();
		}
		
		if (cnmRAllegatoOrdinanza != null) {
			CnmTAllegato cnmTAllegato = cnmRAllegatoOrdinanza.getCnmTAllegato();
			ordinanzaVO.setNumeroProtocollo(cnmTAllegato.getNumeroProtocollo());
			ordinanzaVO.setDataProtocollo(utilsDate.asLocalDateTime(cnmTAllegato.getDataOraProtocollo()));
		}

		return ordinanzaVO;
	}


	@Override
	public OrdinanzaVO mapEntityToFullVO(CnmTOrdinanza dto) {
		OrdinanzaVO ordinanzaVO = mapEntityToVOLocal(dto);
		ValoriOrdinanzaHelper valoriOrdinanza = new ValoriOrdinanzaHelper(dto);
		ordinanzaVO.setImportoPagato(valoriOrdinanza.getImportoPagato());
		ordinanzaVO.setImportoResiduo(valoriOrdinanza.getImportoResiduo());
		ordinanzaVO.setImportoTotaleDaPagare(valoriOrdinanza.getImportoTotaleDaPagare());
		ordinanzaVO.setListaAcconti(
			accontoEntityMapper.mapEntityListToVOList(
				valoriOrdinanza.getCnmTAccontoList()
			)
		);
		return ordinanzaVO;
	}
	
	
	@Override
	public CnmTOrdinanza mapVOtoEntity(MinOrdinanzaVO ordinanza) {
		CnmTOrdinanza cnmTOrdinanza = new CnmTOrdinanza();
		mapVOtoEntityUpdate(ordinanza, cnmTOrdinanza);
		return cnmTOrdinanza;
	}
	
	@Override
	public CnmTOrdinanza mapVOtoEntityUpdate(MinOrdinanzaVO ordinanza, CnmTOrdinanza cnmTOrdinanza) {
		cnmTOrdinanza.setDataDeterminazione(utilsDate.asDate(ordinanza.getDataDeterminazione()));
		cnmTOrdinanza.setDataOrdinanza(utilsDate.asDate(ordinanza.getDataOrdinanza()));
		cnmTOrdinanza.setImportoOrdinanza(ordinanza.getImportoOrdinanza() != null ? ordinanza.getImportoOrdinanza().setScale(2, RoundingMode.HALF_UP) : null);
		cnmTOrdinanza.setNumDeterminazione(ordinanza.getNumDeterminazione().toUpperCase());
		cnmTOrdinanza.setDataScadenzaOrdinanza(utilsDate.asDate(ordinanza.getDataScadenza()));
		cnmTOrdinanza.setDataFineValidita(utilsDate.asDate(ordinanza.getDataFineValidita()));
		
		return cnmTOrdinanza;
	}


}
