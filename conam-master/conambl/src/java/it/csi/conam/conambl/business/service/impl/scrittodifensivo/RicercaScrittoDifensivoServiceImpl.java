/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.scrittodifensivo;

import it.csi.conam.conambl.business.service.scrittodifensivo.RicercaScrittoDifensivoService;
import it.csi.conam.conambl.business.service.verbale.UtilsVerbale;
import it.csi.conam.conambl.integration.entity.CnmDAmbito;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleIllecito;
import it.csi.conam.conambl.integration.entity.CnmTScrittoDifensivo;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.mapper.entity.ScrittoDifensivoEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleIllecitoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTScrittoDifensivoRepository;
import it.csi.conam.conambl.integration.specification.CnmTScrittoDifensivoSpecification;
import it.csi.conam.conambl.request.scrittodifensivo.RicercaScrittoDifensivoRequest;
import it.csi.conam.conambl.vo.verbale.ScrittoDifensivoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RicercaScrittoDifensivoServiceImpl implements RicercaScrittoDifensivoService {

	@Autowired
	private CnmTScrittoDifensivoRepository cnmTScrittoDifensivoRepository;
	@Autowired
	private ScrittoDifensivoEntityMapper scrittoDifensivoEntityMapper;
	@Autowired
	private CnmRVerbaleIllecitoRepository cnmRVerbaleIllecitoRepository;
	@Autowired
	private UtilsVerbale utilsVerbale;

	
	
	@Override
	public List<ScrittoDifensivoVO> ricercaScrittoDifensivo(RicercaScrittoDifensivoRequest request) {
		if (request == null) throw new IllegalArgumentException("request is null");
		if (request.getTipoRicerca() == null) throw new IllegalArgumentException("tipoRicerca is null");
		
		// 20210419: se idVerbale = null, allora è ricerca "base" ("RICERCA_SCRITTO"), altrimenti è ricerca per associazione ("ASSOCIA_SCRITTO")
		List<CnmDAmbito> acronimoAmbitoList = null;
		if (request.getIdVerbale() != null && request.getTipoRicerca().equals("ASSOCIA_SCRITTO")) {
			List<CnmDAmbito>  acronimoAmbitoListTemp = new ArrayList<CnmDAmbito>();
			CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(request.getIdVerbale());
			List<CnmRVerbaleIllecito> cnmRVerbaleIllecitos = cnmRVerbaleIllecitoRepository.findByCnmTVerbale(cnmTVerbale);
			for (CnmRVerbaleIllecito cnmRVerbaleIllecito : cnmRVerbaleIllecitos) {
				CnmDAmbito cnmDAmbitoTemp = cnmRVerbaleIllecito.getCnmDLettera().getCnmDComma().getCnmDArticolo().getCnmREnteNorma().getCnmDNorma().getCnmDAmbito();
				acronimoAmbitoListTemp.add(cnmDAmbitoTemp);
			}
			acronimoAmbitoList = acronimoAmbitoListTemp.stream().distinct().collect(Collectors.toList());
		}		
		
		List<CnmTScrittoDifensivo> cnmTScrittoDifensivoList = 
				cnmTScrittoDifensivoRepository.findAll(
						CnmTScrittoDifensivoSpecification.findScrittoBy(
								request.getNumeroProtocollo(), 
								request.getNome(), 
								request.getCognome(), 
								request.getRagioneSociale(), 
								acronimoAmbitoList, 
								null, 	// data scritto DA
								null, 	// data scritto A
								request.getTipoRicerca()
							)
						);
		
		return scrittoDifensivoEntityMapper.mapListEntityToListVO(cnmTScrittoDifensivoList);
			
	}
	

	
	
	
	
	
	
	
	
	
	
}
