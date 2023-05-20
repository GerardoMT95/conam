/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.ordinanza;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import it.csi.conam.conambl.business.service.ordinanza.AllegatoOrdinanzaPregressiService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.mapper.entity.TipoAllegatoEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmDTipoAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmROrdinanzaVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmTOrdinanzaRepository;
import it.csi.conam.conambl.integration.specification.CnmDTipoAllegatoSpecification;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class AllegatoOrdinanzaPregressiServiceImpl implements AllegatoOrdinanzaPregressiService {

	@Autowired
	private TipoAllegatoEntityMapper tipoAllegatoEntityMapper;
	@Autowired
	private CnmDTipoAllegatoRepository cnmDTipoAllegatoRepository;
	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CnmRAllegatoOrdinanzaRepository cnmRAllegatoOrdinanzaRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;

	/*@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CommonAllegatoService commonAllegatoService;*/
	//private static final Logger logger = Logger.getLogger(AllegatoOrdinanzaPregressiServiceImpl.class);

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
		

		// 20201126 PP - aggiungo DISPOSIZIONE_DEL_GIUDICE solo se l'ordinanaza e' in stato RICORSO_IN_ATTO
		if(cnmTOrdinanza.getCnmDStatoOrdinanza().getIdStatoOrdinanza() == Constants.ID_STATO_ORDINANZA_RICORSO_IN_ATTO) {
			addTipoIfNotExists(listAllegabili, TipoAllegato.DISPOSIZIONE_DEL_GIUDICE.getId());
		}
		addTipoIfNotExists(listAllegabili, TipoAllegato.RICEVUTA_PAGAMENTO_ORDINANZA.getId());
		addTipoIfNotExists(listAllegabili, TipoAllegato.LETTERA_RATEIZZAZIONE.getId());	
		
		List<TipoAllegatoVO> listAllegati = getTipologiaAllegatiOrdinanzaByCnmTOrdinanza(cnmTOrdinanza);

		//20201120 PP - aggiungo LETTERA_RATEIZZAZIONE se esiste gia' una LETTERA_ORDINANZA per la creazione del piano
//		for(TipoAllegatoVO tipoAllegato : listAllegati) {
//			if(tipoAllegato.getId() == TipoAllegato.LETTERA_ORDINANZA.getId()) {
//				listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(TipoAllegato.LETTERA_RATEIZZAZIONE.getId())));
//				break;
//			}
//		}
		
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
	

	private void addTipoIfNotExists(List<TipoAllegatoVO> listAllegabili, long id) {
		boolean idFound = false;
		for(TipoAllegatoVO allegabile : listAllegabili) {
			if(allegabile.getId() == id) {
				idFound = true;
				break;
			}
		}
		if(!idFound) {
			listAllegabili.add(tipoAllegatoEntityMapper.mapEntityToVO(cnmDTipoAllegatoRepository.findOne(id)));
		}
	}


//	@Override
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
	

}
