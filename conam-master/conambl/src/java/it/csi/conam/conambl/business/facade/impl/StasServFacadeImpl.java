/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.facade.impl;

import it.csi.conam.conambl.business.facade.StasServFacade;
import it.csi.csi.porte.InfoPortaDelegata;
import it.csi.csi.porte.proxy.PDProxy;
import it.csi.csi.util.xml.PDConfigReader;
import it.csi.gmscore.dto.Anagrafica;
import it.csi.gmscore.dto.ModuloRicercaPF;
import it.csi.gmscore.dto.SoggettoImpresa;
import it.csi.gmscore.dto.SoggettoPF;
import it.csi.gmscore.exception.csi.*;
import it.csi.gmssrvreg.client.interfaceCSI.ServiziTauITF;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * @author riccardo.bova
 * @date 24 ott 2018
 */
@Service
public class StasServFacadeImpl implements StasServFacade {

	private static final Logger logger = Logger.getLogger(StasServFacadeImpl.class);

	private ServiziTauITF serviziTauITF;

	@PostConstruct
	public void getServizioGMS() throws RuntimeException {
		ClassLoader loader = getClass().getClassLoader();
		InfoPortaDelegata info;
		try {
			InputStream in = loader.getResourceAsStream("defpd_stas.xml");
			info = PDConfigReader.read(in);
			serviziTauITF = (ServiziTauITF) PDProxy.newInstance(info);
		} catch (Exception e) {
			throw new RuntimeException("Problemi nel contattare il servizio GMS", e);
		}
	}

	@Override
	public Anagrafica[] ricercaSoggettoCF(String cf, String token) {
		try {
			return serviziTauITF.ricercaSoggettoCF(cf, token);
//			return serviziTauITF.ricercaSoggettoCF(cf, "AAAAAA00A11B000J/Csi Piemonte/Demo 21/IPA/20201113113025/2/V6tDIGFKORVDrBW+NOWURg==");
//			return serviziTauITF.ricercaSoggettoCF(cf, "AAAAAA00B77B000F/Csi Piemonte/Demo 20/IPA/20210311120727/2/Ev+zD2iSWVJZXUhr0ImBDw==");


		} catch (CodiceFiscaleNonValidoCsiException | SoggettoNonTrovatoCsiException | GMSServizioIndisponibileException | UtenteNonAbilitatoCsiException e) {
			if (e instanceof CodiceFiscaleNonValidoCsiException)
				logger.info("Il codice fiscale non è valido ");
			if (e instanceof SoggettoNonTrovatoCsiException)
				logger.info("Il soggetto non è stato trovato su stas");
			if (e instanceof GMSServizioIndisponibileException)
				logger.error("Servizio non disponibile", e);
			if (e instanceof UtenteNonAbilitatoCsiException)
				throw new RuntimeException("UtenteNonAbilitatoCsiException", e);
		}
		return null;
	}

	@Override
	public SoggettoPF[] ricercaPersonaFisicaCompleta(ModuloRicercaPF moduloRicerca, String identitaIride) {
		try {
			return serviziTauITF.ricercaPersonaFisicaCompleta(moduloRicerca, identitaIride);
		} catch (OggettoInputNonValidoCsiException | UtenteNonAbilitatoCsiException | GMSServizioIndisponibileException | SoggettoNonTrovatoCsiException e) {
			if (e instanceof OggettoInputNonValidoCsiException)
				throw new RuntimeException(e);
			if (e instanceof UtenteNonAbilitatoCsiException)
				throw new RuntimeException(e);
			if (e instanceof GMSServizioIndisponibileException)
				logger.error("Servizio non disponibile", e);
			if (e instanceof SoggettoNonTrovatoCsiException)
				logger.info("Il soggetto non è stato trovato su stas");
		}

		return null;
	}

	@Override
	public SoggettoImpresa[] ricercaImpresa(String denominazione, String identitaIride) {
		try {
			return serviziTauITF.ricercaImpresa(denominazione, identitaIride);
		} catch (OggettoInputNonValidoCsiException | SoggettoNonTrovatoCsiException | UtenteNonAbilitatoCsiException | GMSServizioIndisponibileException e) {
			if (e instanceof OggettoInputNonValidoCsiException)
				throw new RuntimeException(e);
			if (e instanceof UtenteNonAbilitatoCsiException)
				throw new RuntimeException(e);
			if (e instanceof GMSServizioIndisponibileException)
				logger.error("Servizio non disponibile", e);
			if (e instanceof SoggettoNonTrovatoCsiException)
				logger.info("Il soggetto non è stato trovato su stas");
		}
		return null;
	}

	@Override
	public Anagrafica ricercaSoggettoID(Long id) {
		try {
			return serviziTauITF.ricercaSoggettoID(id);
		} catch (SoggettoNonTrovatoCsiException | IdInputNonValidoCsiException | GMSServizioIndisponibileException e) {
			if (e instanceof SoggettoNonTrovatoCsiException)
				logger.info("Il soggetto non è stato trovato su stas");
			if (e instanceof IdInputNonValidoCsiException)
				throw new RuntimeException(e);
			if (e instanceof GMSServizioIndisponibileException)
				logger.info("Servizio non disponibile", e);
		}
		return null;

	}

}
