/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.ws.impl.stas;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.integration.entity.CnmDComune;
import it.csi.conam.conambl.integration.entity.CnmDNazione;
import it.csi.conam.conambl.integration.entity.CnmSComune;
import it.csi.conam.conambl.integration.mapper.entity.*;
import it.csi.conam.conambl.integration.mapper.ws.stas.CommonSoggettoWsOutputMapper;
import it.csi.conam.conambl.integration.repositories.CnmDComuneRepository;
import it.csi.conam.conambl.integration.repositories.CnmDNazioneRepository;
import it.csi.conam.conambl.integration.repositories.CnmSComuneRepository;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.gmscore.dto.Civico;
import it.csi.gmscore.dto.IndirizzoNazionale;
import it.csi.gmscore.dto.Luogo;
import it.csi.gmscore.dto.LuogoNazionale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonSoggettoWsOutputMapperImpl implements CommonSoggettoWsOutputMapper {

	@Autowired
	private NazioneEntityMapper nazioneEntityMapper;
	@Autowired
	private ComuneEntityMapper comuneEntityMapper;
	@Autowired
	private SComuneEntityMapper sComuneEntityMapper;
	@Autowired
	private ProvinciaEntityMapper provinciaEntityMapper;
	@Autowired
	private RegioneEntityMapper regioneEntityMapper;
	@Autowired
	private CnmDComuneRepository cnmDComuneRepository;
	@Autowired
	private CnmSComuneRepository cnmSComuneRepository;
	@Autowired
	private CnmDNazioneRepository cnmDNazioneRepository;
	@Autowired
	private UtilsDate utilsDate;

	@Override
	public void mapResidenza(SoggettoVO soggetto, IndirizzoNazionale indirizzoResidenza) {
		if (indirizzoResidenza == null)
			return;

		if (indirizzoResidenza.getVia() != null)
			soggetto.setIndirizzoResidenza(indirizzoResidenza.getVia().getViaStringa());
		Civico civico = indirizzoResidenza.getCivico();
		if (civico != null)
			soggetto.setCivicoResidenza(civico.getCivicoInStringa());
		Luogo luogo = indirizzoResidenza.getLuogoIndirizzo();
		if (luogo != null && luogo.getComune() != null) {
			CnmDComune cnmDComune = cnmDComuneRepository.findByCodIstatComuneAndFineValidita(luogo.getComune().getIstat());
			if (cnmDComune != null) {
				soggetto.setComuneResidenza(comuneEntityMapper.mapEntityToVO(cnmDComune));
				soggetto.setProvinciaResidenza(provinciaEntityMapper.mapEntityToVO(cnmDComune.getCnmDProvincia()));
				soggetto.setRegioneResidenza(regioneEntityMapper.mapEntityToVO(cnmDComune.getCnmDProvincia().getCnmDRegione()));
			}
		}
		soggetto.setCap(indirizzoResidenza.getCap());
		soggetto.setResidenzaEstera(false);
	}

	@Override
	public void mapLuogoNascita(SoggettoVO soggetto, LuogoNazionale luogoNascita) {
		if (luogoNascita == null)
			return;

		if (luogoNascita.isLuogoNazionale()) {
			CnmDComune cnmDComune = cnmDComuneRepository.findByCodIstatComuneAndFineValiditaDataNascita(luogoNascita.getComune().getIstat(), utilsDate.asDate(soggetto.getDataNascita()));

			if (cnmDComune != null) {
				soggetto.setComuneNascita(comuneEntityMapper.mapEntityToVO(cnmDComune));
				soggetto.setProvinciaNascita(provinciaEntityMapper.mapEntityToVO(cnmDComune.getCnmDProvincia()));
				soggetto.setRegioneNascita(regioneEntityMapper.mapEntityToVO(cnmDComune.getCnmDProvincia().getCnmDRegione()));
				soggetto.setNazioneNascitaEstera(false);
			} else {
				CnmSComune cnmSComune = cnmSComuneRepository.findByCodIstatComuneAndFineValiditaDataNascita(luogoNascita.getComune().getIstat(), utilsDate.asDate(soggetto.getDataNascita()));
				if (cnmSComune != null) {
					soggetto.setComuneNascita(sComuneEntityMapper.mapEntityToVO(cnmSComune));
					soggetto.setProvinciaNascita(provinciaEntityMapper.mapEntityToVO(cnmSComune.getCnmDProvincia()));
					soggetto.setRegioneNascita(regioneEntityMapper.mapEntityToVO(cnmSComune.getCnmDProvincia().getCnmDRegione()));
					soggetto.setNazioneNascitaEstera(false);
				}
			}
		} else {
			soggetto.setNazioneNascita(nazioneEntityMapper.mapEntityToVO(cnmDNazioneRepository.findByDenomNazioneAndFineValidita(luogoNascita.getNazione())));
			soggetto.setNazioneNascitaEstera(false);
		}

	}

	@Override
	public void mapLuogoNascita(SoggettoVO soggetto, Luogo luogoNascita) {
		if (luogoNascita == null)
			return;
		if (luogoNascita.isLuogoNazionale()) {
			CnmDComune cnmDComune = cnmDComuneRepository.findByCodIstatComuneAndFineValiditaDataNascita(luogoNascita.getComune().getIstat(), utilsDate.asDate(soggetto.getDataNascita()));
			if (cnmDComune != null) {
				soggetto.setComuneNascita(comuneEntityMapper.mapEntityToVO(cnmDComune));
				soggetto.setProvinciaNascita(provinciaEntityMapper.mapEntityToVO(cnmDComune.getCnmDProvincia()));
				soggetto.setRegioneNascita(regioneEntityMapper.mapEntityToVO(cnmDComune.getCnmDProvincia().getCnmDRegione()));
				soggetto.setNazioneNascitaEstera(false);
			} else {
				CnmSComune cnmSComune = cnmSComuneRepository.findByCodIstatComuneAndFineValiditaDataNascita(luogoNascita.getComune().getIstat(), utilsDate.asDate(soggetto.getDataNascita()));
				if (cnmSComune != null) {
					soggetto.setComuneNascita(sComuneEntityMapper.mapEntityToVO(cnmSComune));
					soggetto.setProvinciaNascita(provinciaEntityMapper.mapEntityToVO(cnmSComune.getCnmDProvincia()));
					soggetto.setRegioneNascita(regioneEntityMapper.mapEntityToVO(cnmSComune.getCnmDProvincia().getCnmDRegione()));
					soggetto.setNazioneNascitaEstera(false);
				}
			}
		} else {
			soggetto.setNazioneNascita(nazioneEntityMapper.mapEntityToVO(cnmDNazioneRepository.findByDenomNazioneAndFineValidita(luogoNascita.getNazione())));
			soggetto.setNazioneNascitaEstera(false);
		}
		if (soggetto.getIdStas() != null) {
			if (soggetto.getRegioneNascita() != null) {
				soggetto.setIsRegioneNascitaFromStas(true);
			} else {
				soggetto.setIsRegioneNascitaFromStas(false);
			}
		}
	}

	@Override
	public void mapLuogoOrigineEstero(SoggettoVO soggetto, Luogo luogoOrigine) {
		String[] vett = luogoOrigine.getComune().getDescrizione().split("[\\(||//)]");
		String comune = vett[0];

		CnmDNazione cnmDNazione = null;
		if (luogoOrigine.getComune().getIstat() != null)
			cnmDNazione = cnmDNazioneRepository.findByCodIstatNazione(luogoOrigine.getComune().getIstat());
		if (cnmDNazione != null) {
			soggetto.setNazioneNascita(nazioneEntityMapper.mapEntityToVO(cnmDNazione));
			if (vett.length == 2)
				soggetto.setDenomComuneNascitaEstero(comune);
			soggetto.setNazioneNascitaEstera(true);
			soggetto.setComuneNascita(null);
		}
		if (soggetto.getIdStas() != null) {
			if (soggetto.getNazioneNascita() != null) {
				soggetto.setIsNazioneNascitaFromStas(true);
			} else {
				soggetto.setIsNazioneNascitaFromStas(false);
			}
			if (soggetto.getDenomComuneNascitaEstero() != null) {
				soggetto.setIsComuneNascitaEsteroFromStas(true);
			} else {
				soggetto.setIsComuneNascitaEsteroFromStas(false);
			}
		}
	}
}
