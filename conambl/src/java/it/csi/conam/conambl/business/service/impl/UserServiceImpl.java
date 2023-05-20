/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl;

import it.csi.conam.conambl.business.service.UserService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.CnmDMessaggio;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.repositories.CnmCParametroRepository;
import it.csi.conam.conambl.integration.repositories.CnmDMessaggioRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.security.AppGrantedAuthority;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.ProfiloVO;
import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.iride2.policy.entity.Identita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CnmTUserRepository cnmTUserRepository;

	@Autowired
	private CnmCParametroRepository cnmCParametroRepository;

	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;
	
	@Override
	public ProfiloVO getProfilo(UserDetails userDetails) {
		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		Identita identita = userDetails.getIdentita();

		ProfiloVO profilo = new ProfiloVO();
		profilo.setCodiceFiscale(identita.getCodFiscale());
		profilo.setCognome(cnmTUser.getCognome());
		profilo.setNome(cnmTUser.getNome());
		profilo.setEntiAccertatore(userDetails.getEntiAccertatore());
		profilo.setEntiLegge(userDetails.getEntiLegge());

		profilo.setIdGruppo(userDetails.getIdGruppo());

		List<String> useCase = new ArrayList<>();
		for (GrantedAuthority aut : userDetails.getAuthorities()) {
			AppGrantedAuthority app = (AppGrantedAuthority) aut;
			useCase.add(app.getCodice());
		}
		profilo.setUseCase(useCase);
		profilo.setRai(userDetails.getRai());
		
		// 20201123 PP - controllo se e' stata superata la data_termine_recupero_pregresso, se si imposto messaggio da visualizzare sulla home
		Date dataTerminePregresso = cnmCParametroRepository.findOne(Constants.ID_DATA_TERMINE_GESTIONE_PREGRESSO).getValoreDate();
		if(new Date().after(dataTerminePregresso)) {
			CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio("ENDRECPREG");
			if(cnmDMessaggio!=null) {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				String msg = String.format(cnmDMessaggio.getDescMessaggio(), format.format(dataTerminePregresso));
				profilo.setHomeMessage(new MessageVO(msg, cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio()));
			}
		}

		return profilo;
	}

}
