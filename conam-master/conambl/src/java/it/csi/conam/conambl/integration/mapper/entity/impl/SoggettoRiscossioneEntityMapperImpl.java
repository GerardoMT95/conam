/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.business.service.ordinanza.OrdinanzaService;
import it.csi.conam.conambl.business.service.pianorateizzazione.PianoRateizzazioneService;
import it.csi.conam.conambl.business.service.sollecito.SollecitoService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.*;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoFieldRepository;
import it.csi.conam.conambl.integration.repositories.CnmTNotificaRepository;
import it.csi.conam.conambl.integration.repositories.CnmTOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmTRiscossioneRepository;
import it.csi.conam.conambl.vo.riscossione.SoggettiRiscossioneCoattivaVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class SoggettoRiscossioneEntityMapperImpl implements SoggettoRiscossioneEntityMapper {

	@Autowired
	private RuoloSoggettoEntityMapper ruoloSoggettoEntityMapper;
	@Autowired
	private StatoSoggettoOrdinanzaMapper statoSoggettoOrdinanzaMapper;
	@Autowired
	private StatoOrdinanzaMapper statoOrdinanzaMapper;
	@Autowired
	private StatoRiscossioneMapper statoRiscossioneMapper;
	@Autowired
	private PianoRateizzazioneService pianoRateizzazioneService;
	@Autowired
	private OrdinanzaService ordinanzaService;
	@Autowired
	private SollecitoService sollecitoService;
	@Autowired
	private CnmTRiscossioneRepository cnmTRiscossioneRepository;
	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CnmTAllegatoFieldRepository cnmTAllegatoFieldRepository;
	@Autowired
	private CnmTNotificaRepository cnmTNotificaRepository;
	// @Autowired
	// private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private MinVerbaleEntityMapper minVerbaleEntityMapper;

	@Override
	public SoggettiRiscossioneCoattivaVO mapCnmROrdinanzaVerbSogToSoggettiRiscossioneCoattivaVO(CnmROrdinanzaVerbSog dto) {
		SoggettiRiscossioneCoattivaVO soggettiRiscossioneCoattivaVO = new SoggettiRiscossioneCoattivaVO();
		
		CnmTSoggetto cnmTSoggetto = dto.getCnmRVerbaleSoggetto().getCnmTSoggetto();
		CnmTPersona cnmTPersona = cnmTSoggetto.getCnmTPersona();
		CnmTSocieta cnmTSocieta = cnmTSoggetto.getCnmTSocieta();
		if (dto.getCnmRVerbaleSoggetto().getCnmDRuoloSoggetto().getIdRuoloSoggetto() == Constants.VERBALE_SOGGETTO_RUOLO_OBBLIGATO_IN_SOLIDO_ID)
			return null;
		if (cnmTPersona != null) {
			soggettiRiscossioneCoattivaVO.setCodiceFiscale(cnmTSoggetto.getCodiceFiscale());
			soggettiRiscossioneCoattivaVO.setCognome(cnmTSoggetto.getCognome());
			soggettiRiscossioneCoattivaVO.setNome(cnmTSoggetto.getNome());
			soggettiRiscossioneCoattivaVO.setPersonaFisica(true);
		} else if (cnmTSocieta != null) {
			soggettiRiscossioneCoattivaVO.setPartitaIva(cnmTSoggetto.getPartitaIva());
			soggettiRiscossioneCoattivaVO.setCodiceFiscale(cnmTSoggetto.getCodiceFiscaleGiuridico());
			soggettiRiscossioneCoattivaVO.setPersonaFisica(false);
		} else {
			boolean persFisica = !StringUtils.isBlank(cnmTSoggetto.getCodiceFiscale());
			soggettiRiscossioneCoattivaVO.setPersonaFisica(persFisica);
			soggettiRiscossioneCoattivaVO.setCodiceFiscale(persFisica ? cnmTSoggetto.getCodiceFiscale() : cnmTSoggetto.getCodiceFiscaleGiuridico());
			soggettiRiscossioneCoattivaVO.setCognome(cnmTSoggetto.getCognome());
			soggettiRiscossioneCoattivaVO.setNome(cnmTSoggetto.getNome());
			soggettiRiscossioneCoattivaVO.setPartitaIva(cnmTSoggetto.getPartitaIva());
		}
		soggettiRiscossioneCoattivaVO.setId(dto.getCnmRVerbaleSoggetto().getCnmTSoggetto().getIdSoggetto());
		soggettiRiscossioneCoattivaVO.setStatoSoggettoOrdinanza(statoSoggettoOrdinanzaMapper.mapEntityToVO(dto.getCnmDStatoOrdVerbSog()));
		soggettiRiscossioneCoattivaVO.setRuolo(ruoloSoggettoEntityMapper.mapEntityToVO(dto.getCnmRVerbaleSoggetto().getCnmDRuoloSoggetto()));
		soggettiRiscossioneCoattivaVO.setStatoOrdinanza(statoOrdinanzaMapper.mapEntityToVO(dto.getCnmTOrdinanza().getCnmDStatoOrdinanza()));
		soggettiRiscossioneCoattivaVO.setIdSoggettoOrdinanza(dto.getIdOrdinanzaVerbSog());
		soggettiRiscossioneCoattivaVO.setNumeroDeterminazione(dto.getCnmTOrdinanza().getNumDeterminazione());

		soggettiRiscossioneCoattivaVO.setImportoOrdinanzaResponse(ordinanzaService.getImportoOrdinanzaByCnmROrdinanzaVerbSog(dto));
		soggettiRiscossioneCoattivaVO.setImportoPianoResponse(pianoRateizzazioneService.getImportiPianoByCnmROrdinanzaVerbSog(dto));
		soggettiRiscossioneCoattivaVO.setImportoSollecitoResponse(sollecitoService.getImportiSollecitoByCnmROrdinanzaVerbSog(dto));

		soggettiRiscossioneCoattivaVO.setId(dto.getIdOrdinanzaVerbSog());
		soggettiRiscossioneCoattivaVO.setImportoSanzione(soggettiRiscossioneCoattivaVO.getImportoSanzione());
		soggettiRiscossioneCoattivaVO.setVerbale(minVerbaleEntityMapper.mapEntityToVO(dto.getCnmRVerbaleSoggetto().getCnmTVerbale(), 0L));
		return soggettiRiscossioneCoattivaVO;
	}

	@Override
	public List<SoggettiRiscossioneCoattivaVO> mapListCnmROrdinanzaVerbSogToListSoggettiRiscossioneCoattivaVO(List<CnmROrdinanzaVerbSog> tl) {
		List<SoggettiRiscossioneCoattivaVO> v = new ArrayList<>();
		for (CnmROrdinanzaVerbSog t : tl) {
			SoggettiRiscossioneCoattivaVO soggettiRiscossioneCoattivaVO = mapCnmROrdinanzaVerbSogToSoggettiRiscossioneCoattivaVO(t);
			if (soggettiRiscossioneCoattivaVO == null)
				continue;
			CnmTRiscossione cnmTRiscossione = cnmTRiscossioneRepository.findByCnmROrdinanzaVerbSog(t);
			if (cnmTRiscossione != null) {
				cnmTRiscossione = cnmTRiscossioneRepository.findOne(cnmTRiscossione.getIdRiscossione());
				soggettiRiscossioneCoattivaVO.setIdRiscossione(cnmTRiscossione.getIdRiscossione());
				soggettiRiscossioneCoattivaVO.setStatoRiscossione(statoRiscossioneMapper.mapEntityToVO(cnmTRiscossione.getCnmDStatoRiscossione()));
				soggettiRiscossioneCoattivaVO.setImportoSanzione(cnmTRiscossione.getImportoSanzione() != null ? cnmTRiscossione.getImportoSanzione() : getImporto("S", t));
				soggettiRiscossioneCoattivaVO.setImportoSpeseNotifica(cnmTRiscossione.getImportoSpeseNotifica() != null ? cnmTRiscossione.getImportoSpeseNotifica() : getImporto("N", t));
				soggettiRiscossioneCoattivaVO.setImportoSpeseLegali(cnmTRiscossione.getImportoSpeseLegali() != null ? cnmTRiscossione.getImportoSpeseLegali() : getImporto("L", t));
			}
			v.add(soggettiRiscossioneCoattivaVO);
		}
		return v;
	}

	private BigDecimal getImporto(String tipo, CnmROrdinanzaVerbSog t) {
		BigDecimal importo = new BigDecimal(0);

		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findByIdOrdinanzaVerbSog(t.getIdOrdinanzaVerbSog());

		if (tipo.equals("S") && cnmTOrdinanza != null) {
			importo = cnmTOrdinanza.getImportoOrdinanza();
		} else if (tipo.equals("N") && cnmTOrdinanza != null) {
			List<CnmTNotifica> cnmTNotifica = cnmTNotificaRepository.findByCnmTOrdinanzas(cnmTOrdinanza);
			if (cnmTNotifica != null && cnmTNotifica.size() > 0)
				importo = cnmTNotifica.get(0).getImportoSpeseNotifica();
		} else if (tipo.equals("L")) {
			List<CnmTAllegatoField> cnmTAllegato = cnmTAllegatoFieldRepository.findByIdOrdinanzaVerbSog(t.getIdOrdinanzaVerbSog());
			importo = cnmTAllegato != null && cnmTAllegato.size() > 0 ? cnmTAllegato.get(0).getValoreNumber() : new BigDecimal(0);
		}

		return importo;
	}

	@Override
	public SoggettiRiscossioneCoattivaVO mapCnmTRiscossioneToSoggettiRiscossioneCoattivaVO(CnmTRiscossione cnmTRiscossione) {
		SoggettiRiscossioneCoattivaVO s = mapCnmROrdinanzaVerbSogToSoggettiRiscossioneCoattivaVO(cnmTRiscossione.getCnmROrdinanzaVerbSog());
		s.setStatoRiscossione(statoRiscossioneMapper.mapEntityToVO(cnmTRiscossione.getCnmDStatoRiscossione()));
		s.setImportoRiscosso(cnmTRiscossione.getImportoRiscosso());
		s.setImportoSanzione(cnmTRiscossione.getImportoSanzione());
		s.setImportoSpeseLegali(cnmTRiscossione.getImportoSpeseLegali());
		s.setImportoSpeseNotifica(cnmTRiscossione.getImportoSpeseNotifica());

		if (cnmTRiscossione.getCnmROrdinanzaVerbSog() != null && cnmTRiscossione.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto() != null
				&& cnmTRiscossione.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTSoggetto() != null)
			s.setId(cnmTRiscossione.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTSoggetto().getIdSoggetto());
		else
			s.setId(cnmTRiscossione.getCnmROrdinanzaVerbSog().getIdOrdinanzaVerbSog());

		s.setIdRiscossione(cnmTRiscossione.getIdRiscossione());
		return s;
	}

	@Override
	public List<SoggettiRiscossioneCoattivaVO> mapListCnmTRiscossioneToListSoggettiRiscossioneCoattivaVO(List<CnmTRiscossione> tl) {
		List<SoggettiRiscossioneCoattivaVO> v = new ArrayList<>();
		for (CnmTRiscossione t : tl) {
			v.add(mapCnmTRiscossioneToSoggettiRiscossioneCoattivaVO(t));
		}
		return v;
	}

}
