/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui;

import it.csi.conam.conambl.business.service.util.UtilsCnmCProprietaService;
import it.csi.conam.conambl.integration.entity.CnmCProprieta.PropKey;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoquiServiceFactory {

	@Autowired
	private UtilsCnmCProprietaService utilsCnmCProprietaService;
	
	private DoquiService acarisService;
	
	// 20200612_LC	
	String indexServiceEndpointUrl;

	public DoquiService getAcarisService() {
		if(acarisService == null) {

			String actaServer = utilsCnmCProprietaService.getProprieta(PropKey.ACTA_SERVER);
			String actaContext = utilsCnmCProprietaService.getProprieta(PropKey.ACTA_CONTEXT);
			int actaPort = Integer.parseInt(utilsCnmCProprietaService.getProprieta(PropKey.ACTA_PORT));
			Boolean actaIsWS = Boolean.valueOf(utilsCnmCProprietaService.getProprieta(PropKey.ACTA_IS_WS));
			
			// 20200719_LC
			OauthHelper apiManagerOauth = new OauthHelper(utilsCnmCProprietaService.getProprieta(PropKey.APIMANAGER_OAUTHURL),
					utilsCnmCProprietaService.getProprieta(PropKey.APIMANAGER_CONSUMERKEY),
					utilsCnmCProprietaService.getProprieta(PropKey.APIMANAGER_CONSUMERSECRET));
			
			String apiManagerUser = 	"";
			String apiManagerPassword =	"";
			String apiManagerRootUrl = utilsCnmCProprietaService.getProprieta(PropKey.APIMANAGER_URL);
			String apiManagerRootUrlEnd = utilsCnmCProprietaService.getProprieta(PropKey.APIMANAGER_URL_END);
			
			boolean isApiManagerEnabled= Boolean.valueOf(utilsCnmCProprietaService.getProprieta(PropKey.APIMANAGER_ENABLED));
			if(isApiManagerEnabled) {
				// 20200723_LC
				apiManagerUser = 		utilsCnmCProprietaService.getProprieta(PropKey.APIMANAGER_CONSUMERKEY);
				apiManagerPassword =	utilsCnmCProprietaService.getProprieta(PropKey.APIMANAGER_CONSUMERSECRET);
			}
			
			
			acarisService = new DoquiService(actaServer, actaContext, actaPort, actaIsWS, apiManagerOauth, apiManagerUser, apiManagerPassword, isApiManagerEnabled, apiManagerRootUrl, apiManagerRootUrlEnd);
//			acarisService = new DoquiService("tst-applogic.reteunitaria.piemonte.it", "/actasrv/", 80, true);
		}
		return acarisService;
		
	}
	
	public String getIndexServiceEndpointUrl() {
		if(indexServiceEndpointUrl == null) {
			indexServiceEndpointUrl = utilsCnmCProprietaService.getProprieta(PropKey.INDEX_ENDPOINT);
		}
		return indexServiceEndpointUrl;
		
	}
	
	
		
}
