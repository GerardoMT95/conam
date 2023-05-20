/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import it.csi.conam.conambl.business.service.RiferimentiNormativiService;
import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.verbale.RicercaVerbaleService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.MinVerbaleEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoManualeEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoVerbaleEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.VerbaleEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmDEnteRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoManualeRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmTVerbaleRepository;
import it.csi.conam.conambl.integration.specification.CnmTVerbaleSpecification;
import it.csi.conam.conambl.request.SoggettoRequest;
import it.csi.conam.conambl.request.verbale.DatiVerbaleRequest;
import it.csi.conam.conambl.request.verbale.RicercaVerbaleRequest;
import it.csi.conam.conambl.security.AppGrantedAuthority;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.VerbaleSearchParam;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.MinVerbaleVO;
import it.csi.conam.conambl.vo.verbale.StatoManualeVO;
import it.csi.conam.conambl.vo.verbale.StatoVerbaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class RicercaVerbaleServiceImpl implements RicercaVerbaleService {
	@Autowired
	private StatoManualeEntityMapper statoManaualeEntityMapper;

	@Autowired
	private CnmDStatoManualeRepository cnmDStatoManualeRepository;
	
	@Autowired
	private StatoVerbaleEntityMapper statoVerbaleEntityMapper;

	@Autowired
	private RiferimentiNormativiService riferimentiNormativiService;

	@Autowired
	private CnmDStatoVerbaleRepository cnmDStatoVerbaleRepository;

	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;

	@Autowired
	private CnmDEnteRepository cnmDEnteRepository;

	@Autowired
	private CommonSoggettoService commonSoggettoService;

	@Autowired
	private MinVerbaleEntityMapper minVerbaleEntityMapper;


	@Autowired
	private VerbaleEntityMapper verbaleEntityMapper;
	
	@Override
	public List<StatoVerbaleVO> getStatiRicercaVerbale() {
		return statoVerbaleEntityMapper.mapListEntityToListVO((List<CnmDStatoVerbale>) cnmDStatoVerbaleRepository.findAll());
	}

	@Override
	public List<StatoManualeVO> getStatiManuali() {
		return statoManaualeEntityMapper.mapListEntityToListVO((List<CnmDStatoManuale>) cnmDStatoManualeRepository.findAll());
	}


	@Override
	public List<MinVerbaleVO> ricercaVerbale(RicercaVerbaleRequest request, UserDetails userDetails) {
		DatiVerbaleRequest dati = request.getDatiVerbale();
		List<SoggettoRequest> soggetti = request.getSoggettoVerbale();
		List<MinVerbaleVO> verbaliList = new ArrayList<>();

		if (dati == null && soggetti == null) {
			throw new IllegalArgumentException("Nessun Parametro di ricerca valorizzato");
		}

		List<CnmDEnte> enteAccertatore = null;
		AppGrantedAuthority appGrantedAuthority = SecurityUtils.getRuolo(userDetails.getAuthorities());

		List<CnmDLettera> lettera = null;

		//Avra una sola legge
		if (
			appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_AMMINISTRATIVO) ||
			appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ISTRUTTORE)
		) {
			List<EnteVO> enteLegge = userDetails.getEntiLegge();
			if (enteLegge != null && !enteLegge.isEmpty() && enteLegge.size() == 1) {
				lettera = riferimentiNormativiService.getLettereByEnte(enteLegge.get(0).getId(), false);
			} else
				throw new SecurityException("Errore l'utente ammistritativo o funzionario deve avere un solo ente legge, quello di sua competenza");
		} else if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ACCERTATORE)) {
			List<Long> idEnteList = new ArrayList<>();
			for (EnteVO ente : userDetails.getEntiAccertatore()) {
				idEnteList.add(ente.getId());
			}
			enteAccertatore = (List<CnmDEnte>) cnmDEnteRepository.findAll(idEnteList);
		} else
			throw new SecurityException("Ruolo non riconosciuto dal sistema");

		//RECUPERO I PARAMETRI PER IL FILTRO DEL VERBALE
		VerbaleSearchParam parametriVerbale = verbaleEntityMapper.getVerbaleParamFromRequest(dati);

		if (dati == null) parametriVerbale.setLettera(lettera);

		List<CnmTSoggetto> trasgressore = new ArrayList<>();
		List<CnmTSoggetto> obbligatoInSolido = new ArrayList<>();
		if (soggetti != null && !soggetti.isEmpty()) {
			for (SoggettoRequest s : soggetti) {
				CnmTSoggetto cnmTSoggetto = null;
				String tipo = s.getTipoSoggetto();
				if ((cnmTSoggetto = commonSoggettoService.getSoggettoFromDb(new MinSoggettoVO(s), true)) == null)
					return verbaliList;
				if (tipo != null && tipo.equals(Constants.TYPE_SOGGETTO_RICERCA_TRASGRESSORE))
					trasgressore.add(cnmTSoggetto);
				if (tipo != null && tipo.equals(Constants.TYPE_SOGGETTO_RICERCA_OBBLIGATO_IN_SOLIDO))
					obbligatoInSolido.add(cnmTSoggetto);
			}

		}

		List<CnmTVerbale> verb = cnmTVerbaleRepository.findAll(
			CnmTVerbaleSpecification.findBy(
				trasgressore,
				obbligatoInSolido,
				enteAccertatore,
				parametriVerbale
			)
		);
		return minVerbaleEntityMapper.mapListEntityToListVO(verb, userDetails.getIdUser());

	}

}
