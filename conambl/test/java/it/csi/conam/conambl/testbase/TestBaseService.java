/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.testbase;

import it.csi.conam.conambl.security.IdentityDetails;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.security.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author riccardo.bova
 * @date 05 dic 2017
 */
@ContextConfiguration(locations = { "classpath*:it/csi/conam/conambl/testContext/applicationContextTest.xml" })
@PropertySource("classpath*:it/csi/conam/conambl/testContext/enviromentTest.properties")
public class TestBaseService {

	@Autowired
	private UserDetailsService userDetailsService;

	public TestBaseService() {
		// aggiunge al classpath i file pa pd
		File file = new File("");
		file = new File(file.getAbsolutePath() + "/build/temp/conf/web/conambl/rest/WEB-INF/def_pa_pd/");
		try {
			URL url = file.toURI().toURL();
			URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			Method method;

			method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			method.setAccessible(true);
			method.invoke(classLoader, url);
		} catch (MalformedURLException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public UserDetails mockProfileByToken(String token) {
		IdentityDetails identity = new IdentityDetails();
		identity.setIdentity(token);
		return userDetailsService.caricaUtenteDaIdentita(identity);

	}

}
