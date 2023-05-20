/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import it.csi.conam.conambl.business.service.verbale.UtilsVerbale;
import it.csi.conam.conambl.business.service.verbale.VerbalePregressiService;
import it.csi.conam.conambl.business.service.verbale.VerbaleService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.common.exception.VerbalePregressoValidationException;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.EnteEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.VerbaleEntityMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import it.csi.conam.conambl.vo.leggi.RiferimentiNormativiVO;
import it.csi.conam.conambl.vo.verbale.VerbaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author Paolo Piedepalumbo
 * @date 14 oct 2020
 */
@Service
public class VerbalePregressiServiceImpl implements VerbalePregressiService {

	@Autowired
	private VerbaleService verbaleService;

	@Autowired
	private CnmDCommaRepository cnmDCommaRepository;
	
	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;
	
	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;

	@Autowired
	private CnmTResidenzaRepository cnmTResidenzaRepository;
	
	@Autowired
	private CnmCParametroRepository cnmCParametroRepository;

	@Autowired
	private CnmDComuneRepository cnmDComuneRepository;
	@Autowired
	private VerbaleEntityMapper verbaleEntityMapper;
	@Autowired
	private CnmRVerbaleIllecitoRepository cnmRVerbaleIllecitoRepository;
	@Autowired
	private UtilsVerbale utilsVerbale;
	@Autowired
	private EnteEntityMapper enteEntityMapper;

	
	@Override
	@Transactional
	public Integer salvaVerbale(VerbaleVO verbale, UserDetails userDetails) throws VerbalePregressoValidationException{
		
		String message = "";
		
		// 20210429_LC Jira129: numVerbale non univoco, usa id
		
		LocalDateTime dataOraAccertamento = verbale.getDataOraAccertamento();
		Date dataOraAccertamentoDate = convertToDateViaInstant(dataOraAccertamento);
		
		boolean valid = true;
		
		// 0 - controllo se la data accertamento e' antecedente alla data discriminante
		message = checkDataAccertamentoValida(message, dataOraAccertamentoDate);
		
		// 1 - controllare validita' norme
		List<RiferimentiNormativiVO> normList = verbale.getRiferimentiNormativi();
		for(RiferimentiNormativiVO norm : normList) {
			CnmDComma commaDB = cnmDCommaRepository.findOne(norm.getComma().getId().intValue());
			if((commaDB.getInizioValidita() != null && commaDB.getInizioValidita().after(dataOraAccertamentoDate)) 
					|| (commaDB.getFineValidita() != null && commaDB.getFineValidita().before(dataOraAccertamentoDate)) ) {
				message += "<br>- La norma ["+norm.toStringForMessage()+"] non risulta valida alla data di accertamento del verbale";
				valid = false;
			}
		}
		
		// 2 - controllare validita' indirizzo violazione
		if(verbale.getComune() != null) {
			CnmDComune cnmDComune = cnmDComuneRepository.findByIdComune(verbale.getComune().getId());
			if((cnmDComune.getCnmDProvincia().getInizioValidita() != null && cnmDComune.getCnmDProvincia().getInizioValidita().after(dataOraAccertamentoDate)) 
					|| (cnmDComune.getCnmDProvincia().getFineValidita() != null && cnmDComune.getCnmDProvincia().getFineValidita().before(dataOraAccertamentoDate)) ) {
				message += "<br>- La provincia di violazione non risulta valida alla data di accertamento del verbale";
				valid = false;
			}
			if((cnmDComune.getInizioValidita() != null && cnmDComune.getInizioValidita().after(dataOraAccertamentoDate)) || 
					(cnmDComune.getFineValidita() != null && cnmDComune.getFineValidita().before(dataOraAccertamentoDate)) ) {
				message += "<br>- Il comune di violazione non risulta valido alla data di accertamento del verbale";
				valid = false;
			}
		}
		
		// 3 - controllare validita' indirizzi su soggetti 
		if(verbale.getId() != null) {
			// se sto facendo update
			CnmTVerbale verbaleDb = cnmTVerbaleRepository.findOne(verbale.getId().intValue());
			if (verbaleDb == null) throw new BusinessException("cnmnTVerbale null");
			List<CnmRVerbaleSoggetto> soggettoList = verbaleDb.getCnmRVerbaleSoggettos();
			for(CnmRVerbaleSoggetto cnmRVerbaleSoggetto : soggettoList) {
				CnmTSoggetto soggettoDb = cnmRVerbaleSoggetto.getCnmTSoggetto();
				CnmTResidenza cnmTResidenza = cnmTResidenzaRepository.findByCnmTSoggettoAndIdVerbale(soggettoDb, verbaleDb.getIdVerbale());
				if(cnmTResidenza != null && cnmTResidenza.getCnmDComune() != null && cnmTResidenza.getCnmDComune().getCnmDProvincia() != null) {
					if(( cnmTResidenza.getCnmDComune().getCnmDProvincia().getInizioValidita() != null && cnmTResidenza.getCnmDComune().getCnmDProvincia().getInizioValidita().after(dataOraAccertamentoDate)) 
							|| (cnmTResidenza.getCnmDComune().getCnmDProvincia().getFineValidita() != null && cnmTResidenza.getCnmDComune().getCnmDProvincia().getFineValidita().before(dataOraAccertamentoDate)) ) {
						message += "<br>- La provincia di residenza del soggetto ["+soggettoDb.getCognome() + soggettoDb.getNome()+"] non risulta valida alla data di accertamento del verbale";
						valid = false;
					}
					if((cnmTResidenza.getCnmDComune().getInizioValidita() != null && cnmTResidenza.getCnmDComune().getInizioValidita().after(dataOraAccertamentoDate)) 
							|| (cnmTResidenza.getCnmDComune().getFineValidita() != null && cnmTResidenza.getCnmDComune().getFineValidita().before(dataOraAccertamentoDate)) ) {
						message += "<br>- Il comune di residenza del soggetto ["+soggettoDb.getCognome() + soggettoDb.getNome()+"] non risulta valido alla data di accertamento del verbale";
						valid = false;
					}
				}
			}
		}
		
		if(!valid) {
			CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.VERIFICA_DATE_FASCICOLO_PREGRESSO);
			String msg=cnmDMessaggio.getDescMessaggio();
			msg = String.format(msg, message);
			throw new VerbalePregressoValidationException(msg);
		}
		verbale.setPregresso(true);
		return verbaleService.salvaVerbale(verbale, userDetails);
		
	}

	private String checkDataAccertamentoValida(String message, Date dataOraAccertamentoDate)
			throws VerbalePregressoValidationException {
		Date dataDiscriminantePregresso = cnmCParametroRepository.findOne(Constants.ID_DATA_DISCRIMINANTE_GESTIONE_PREGRESSO).getValoreDate();
		if(dataOraAccertamentoDate.after(dataDiscriminantePregresso)) {
			message += "<br>- La data di accertamento risulta maggiore della data discriminante per il recupero pregresso ["+dataDiscriminantePregresso+"]";

			CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(ErrorCode.VERIFICA_DATE_FASCICOLO_PREGRESSO);
			String msg=cnmDMessaggio.getDescMessaggio();
			msg = String.format(msg, message);
			throw new VerbalePregressoValidationException(msg);
		}
		return message;
	}
	
	Date convertToDateViaInstant(LocalDateTime dateToConvert) {
	    return java.util.Date
	      .from(dateToConvert.atZone(ZoneId.systemDefault())
	      .toInstant());
	}

	@Override
	public void checkDatiVerbale(VerbaleVO verbale, UserDetails userDetails) throws VerbalePregressoValidationException {
		
		LocalDateTime dataOraAccertamento = verbale.getDataOraAccertamento();
		Date dataOraAccertamentoDate = convertToDateViaInstant(dataOraAccertamento);
		
		// 0 - controllo se la data accertamento e' antecedente alla data discriminante
		checkDataAccertamentoValida("", dataOraAccertamentoDate);
		
	}

	@Override
	public VerbaleVO getVerbaleById(Integer id, UserDetails userDetails, boolean includeEliminati, boolean includiControlloUtenteProprietario, boolean filtraNormeScadute) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);
		SecurityUtils.verbaleSecurityView(cnmTVerbale, getEnteLeggeByCnmTVerbale(cnmTVerbale));

		if (cnmTVerbale.getCnmTUser2().getIdUser() != userDetails.getIdUser() && includiControlloUtenteProprietario)
			throw new RuntimeException("l'utente  non può accedere a questo verbale");

//		if (cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() != Constants.STATO_VERBALE_INCOMPLETO && !includeEliminati)
//			throw new SecurityException("il verbale non è nello stato corretto per essere visualizzato");

		filtraNormeScadute = false;
		
		return verbaleEntityMapper.mapEntityToVO(cnmTVerbale, filtraNormeScadute);

	}

	public EnteVO getEnteLeggeByCnmTVerbale(CnmTVerbale cnmTVerbale) {
		List<CnmRVerbaleIllecito> cnmRVerbaleIllecitoList = cnmRVerbaleIllecitoRepository.findByCnmTVerbale(cnmTVerbale);
		if (cnmRVerbaleIllecitoList != null && !cnmRVerbaleIllecitoList.isEmpty()) {
			CnmRVerbaleIllecito cnmRVerbaleIllecito = cnmRVerbaleIllecitoList.get(0);
			CnmDLettera cnmDLettera = cnmRVerbaleIllecito.getCnmDLettera();
			CnmDComma cnmDComma = cnmDLettera.getCnmDComma();
			CnmDArticolo cnmDArticolo = cnmDComma.getCnmDArticolo();
			CnmREnteNorma cnmREnteNorma = cnmDArticolo.getCnmREnteNorma();
			return enteEntityMapper.mapEntityToVO(cnmREnteNorma.getCnmDEnte());
		}
		throw new RuntimeException("Ente non trovato , ERRORE");
	}
}
