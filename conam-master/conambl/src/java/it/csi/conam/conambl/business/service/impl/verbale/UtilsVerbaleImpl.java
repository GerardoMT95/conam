/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import it.csi.conam.conambl.business.service.verbale.UtilsVerbale;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.leggi.RiferimentiNormativiVO;
import it.csi.conam.conambl.vo.verbale.VerbaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 21 feb 2019
 */
@Service
public class UtilsVerbaleImpl implements UtilsVerbale {

	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;
	@Autowired
	private CnmDEnteRepository cnmDEnteRepository;
	@Autowired
	private CnmDLetteraRepository cnmDLetteraRepository;
	
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmRAllegatoOrdVerbSogRepository cnmRAllegatoOrdVerbSogRepository;
	@Autowired
	private CnmRAllegatoVerbSogRepository cnmRAllegatoVerbSogRepository;
	@Autowired
	private CnmRAllegatoVerbaleRepository cnmRAllegatoVerbaleRepository;
	@Autowired
	private CnmRAllegatoOrdinanzaRepository cnmRAllegatoOrdinanzaRepository;
	

	@Override
	public CnmTVerbale validateAndGetCnmTVerbale(Integer idVerbale) {
		if (idVerbale == null)
			throw new IllegalArgumentException("idVerbale non valorizzato");

		CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findOne(idVerbale);
		if (cnmTVerbale == null)
			throw new IllegalArgumentException("cnmTVerbale non valorizzato");

		return cnmTVerbale;
	}

	@Override
	public CnmDEnte getEnteAccertatore(VerbaleVO verbale, UserDetails user) {
		Long idEnte;
		if (verbale.getEnteAccertatore() != null)
			idEnte = verbale.getEnteAccertatore().getId();
		else
			idEnte = user.getEntiAccertatore().get(0).getId();

		return cnmDEnteRepository.findOne(idEnte);

	}

	@Override
	public CnmRVerbaleIllecito getNewCnmRVerbaleIllecito(CnmTUser cnmTUser, CnmTVerbale cnmTVerbale, Timestamp now, RiferimentiNormativiVO rif) {
		CnmRVerbaleIllecito cnmRVerbaleIllecito = new CnmRVerbaleIllecito();
		cnmRVerbaleIllecito.setCnmDLettera(cnmDLetteraRepository.findOne(rif.getLettera().getId().intValue()));
		cnmRVerbaleIllecito.setCnmTUser2(cnmTUser);
		cnmRVerbaleIllecito.setCnmTVerbale(cnmTVerbale);
		cnmRVerbaleIllecito.setDataOraInsert(now);
		return cnmRVerbaleIllecito;
	}
	
//	20201022_ET filtro per estrapolare solo la lista degli allegati legati al verbale passato in input
	@Override
	public List<CnmTAllegato> filtraAllegatiPerVerbale(Integer idVerbale, List<CnmTAllegato> allegatiDaFiltrare) {
		List<CnmTAllegato> listAllegatiDaRimuovere = new ArrayList<>();
		
		for(CnmTAllegato allegato:allegatiDaFiltrare) {
			CnmRAllegatoVerbale rAllegatoVerbale = cnmRAllegatoVerbaleRepository.findByCnmTAllegato(allegato);
			if(rAllegatoVerbale!=null) {
				if(rAllegatoVerbale.getId() !=null && !idVerbale.equals(rAllegatoVerbale.getId().getIdVerbale())) {
					listAllegatiDaRimuovere.add(allegato);
				}
				//aggiungo i continue perche se ho trovato l'allegato in una tabella sicuramente non lo trovo nelle altre
				continue;
			}
			//oltre alla rAllegatoVerbale devo cercare su rAllegatoVErbaleSoggetto e rAllegatoOrdinanza
			List<CnmRAllegatoOrdinanza> listaAllegatoOrdinanza = cnmRAllegatoOrdinanzaRepository.findByCnmTAllegato(allegato);
			if(listaAllegatoOrdinanza!=null && !listaAllegatoOrdinanza.isEmpty()) {
				innerLoop:
				for(CnmRAllegatoOrdinanza rAllegatoOrd: listaAllegatoOrdinanza) {
					List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(rAllegatoOrd.getCnmTOrdinanza());
					if(cnmROrdinanzaVerbSogList!=null && !cnmROrdinanzaVerbSogList.isEmpty()) {
						List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmROrdinanzaVerbSogsIn(cnmROrdinanzaVerbSogList);
						if(cnmRVerbaleSoggettoList != null && !cnmRVerbaleSoggettoList.isEmpty() && !idVerbale.equals(cnmRVerbaleSoggettoList.get(0).getCnmTVerbale().getIdVerbale())) {
							listAllegatiDaRimuovere.add(allegato);
							break innerLoop;
						}
					}
				}
				//aggiungo i continue perche se ho trovato l'allegato in una tabella sicuramente non lo trovo nelle altre
				continue;
			}
			
			List<CnmRAllegatoOrdVerbSog> cnmROrdinanzaVerbSogList = cnmRAllegatoOrdVerbSogRepository.findByCnmTAllegato(allegato);
			if(cnmROrdinanzaVerbSogList!=null && !cnmROrdinanzaVerbSogList.isEmpty()) {
				innerLoop:
				for(CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog: cnmROrdinanzaVerbSogList) {
					CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmRAllegatoOrdVerbSog.getCnmROrdinanzaVerbSog();
					if(cnmROrdinanzaVerbSog != null ) {
						List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmROrdinanzaVerbSogsIn(new ArrayList<>(Arrays.asList(cnmROrdinanzaVerbSog)));
						//prendo il primo elemento, tanto anche se ce ne sono diversi, dovrebbero essere legati tutti allo stesso verbale
						if(cnmRVerbaleSoggettoList!=null && !cnmRVerbaleSoggettoList.isEmpty() && !idVerbale.equals(cnmRVerbaleSoggettoList.get(0).getCnmTVerbale().getIdVerbale())) {
							listAllegatiDaRimuovere.add(allegato);
							break innerLoop;
						}
					}
				}
				//aggiungo i continue perche se ho trovato l'allegato in una tabella sicuramente non lo trovo nelle altre
				continue;
			} 
			List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogList = cnmRAllegatoVerbSogRepository.findByCnmTAllegato(allegato);
			if(cnmRAllegatoVerbSogList!=null && !cnmRAllegatoVerbSogList.isEmpty()) {
				innerLoop:
				for(CnmRAllegatoVerbSog cnmRAllegatoVerbSog: cnmRAllegatoVerbSogList) {
					CnmRVerbaleSoggetto cnmRVerbaleSoggetto = cnmRAllegatoVerbSog.getCnmRVerbaleSoggetto();
					if(cnmRVerbaleSoggetto!= null && cnmRVerbaleSoggetto.getCnmTVerbale()!=null && !idVerbale.equals(cnmRVerbaleSoggetto.getCnmTVerbale().getIdVerbale())) {
						listAllegatiDaRimuovere.add(allegato);
						break innerLoop;
					}
				}
			}
		}
		if(!listAllegatiDaRimuovere.isEmpty())
			allegatiDaFiltrare.removeAll(listAllegatiDaRimuovere);
			
		return allegatiDaFiltrare;
	}
	
}
