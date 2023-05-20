/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.ws.impl.stas;

import it.csi.conam.conambl.integration.entity.CnmDComune;
import it.csi.conam.conambl.integration.mapper.ws.stas.ModuloRicercaWsInputMapper;
import it.csi.conam.conambl.integration.repositories.CnmDComuneRepository;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.gmscore.dto.LuogoNascitaFiltroRicerca;
import it.csi.gmscore.dto.ModuloRicercaPF;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ModuloRicercaWsInputMapperImpl implements ModuloRicercaWsInputMapper {

	@Autowired
	private CnmDComuneRepository cnmDComuneRepository;

	@Override
	public ModuloRicercaPF mapVOtoWsType(MinSoggettoVO vo) {
		if (vo == null)
			return null;
		ModuloRicercaPF moduloRicercaPF = new ModuloRicercaPF();
		moduloRicercaPF.setCognome(StringUtils.isNotEmpty(vo.getCognome()) ? vo.getCognome().toUpperCase() : null);
		moduloRicercaPF.setNome(StringUtils.isNotEmpty(vo.getNome()) ? vo.getNome().toUpperCase() : null);
		moduloRicercaPF.setSesso(StringUtils.isNotEmpty(vo.getSesso()) ? vo.getSesso().toUpperCase() : null);

		boolean isNazioneNascitaEstera = vo.getNazioneNascitaEstera() != null && vo.getNazioneNascitaEstera() ;

		if (!isNazioneNascitaEstera) {
			if (vo.getComuneNascita() != null) {
				CnmDComune comune = cnmDComuneRepository.findOne(vo.getComuneNascita().getId());
				LuogoNascitaFiltroRicerca.creaLuogoNascitaNazionale(comune.getDenomComune().toUpperCase(), "ITALIA");
			}
		} else {
			// TODO STATO ESTERO
			// LuogoNascitaFiltroRicerca.creaLuogoNascitaEstero(descComune,
			// descProvOStatoEstero);
		}

		if (vo.getDataNascita() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ModuloRicercaPF.FORMATO_DATA_NASCITA_COMPLETA);
			String formatDateTime = vo.getDataNascita().format(formatter);
			moduloRicercaPF.setDataNascita(formatDateTime);
		}
		return moduloRicercaPF;
	}

}
