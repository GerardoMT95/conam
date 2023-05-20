/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business;

import it.csi.conam.conambl.util.SpringInjectorInterceptor;
import it.csi.conam.conambl.util.SpringSupportedResource;
import it.csi.conam.conambl.web.*;
import it.csi.conam.conambl.web.provider.ContentTypeSetter;
import it.csi.conam.conambl.web.provider.ExceptionHandler;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("restfacade")
public class RestApplication extends Application {
	private Set<Object> singletons = new HashSet<>();
	private Set<Class<?>> classes = new HashSet<>();

	public RestApplication() {
		singletons.add(new CalendarResource());
		singletons.add(new ExceptionHandler());
		singletons.add(new ContentTypeSetter());
		singletons.add(new SpringInjectorInterceptor());
		singletons.add(new LuoghiResource());
		singletons.add(new VerbaleResource());
		singletons.add(new OrdinanzaResource());
		singletons.add(new RiferimentiNormativiResource());
		singletons.add(new UserResource());
		singletons.add(new AllegatoResource());
		singletons.add(new PianoRateizzazioneResource());
		singletons.add(new ProntuarioResource());
		singletons.add(new TemplateResource());
		singletons.add(new SollecitoResource());
		singletons.add(new NotificaResource());
		singletons.add(new RiscossioneResource());
		singletons.add(new SoggettoResource());
		singletons.add(new VerbalePregressiResource());
		singletons.add(new OrdinanzaPregressiResource());
		singletons.add(new SollecitoPregressiResource());
		singletons.add(new PianoRateizzazionePregressiResource());
		singletons.add(new ScrittoDifensivoResource());
		
		for (Object c : singletons) {
			if (c instanceof SpringSupportedResource) {
				SpringApplicationContextHelper.registerRestEasyController(c);
			}
		}

		// classes.add(new ExceptionHandlerResource.class);
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
