/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui;


import it.csi.wso2.apiman.oauth2.helper.GenericWrapperFactoryBean;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;
import it.csi.wso2.apiman.oauth2.helper.TokenRetryManager;
import it.csi.wso2.apiman.oauth2.helper.WsProvider;
import it.csi.wso2.apiman.oauth2.helper.extra.cxf.CxfImpl;
import it.doqui.acta.acaris.backofficeservice.BackOfficeServicePort;
import it.doqui.acta.acaris.documentservice.DocumentServicePort;
import it.doqui.acta.acaris.managementservice.ManagementServicePort;
import it.doqui.acta.acaris.multifilingservice.MultifilingServicePort;
import it.doqui.acta.acaris.navigationservice.NavigationServicePort;
import it.doqui.acta.acaris.objectservice.ObjectServicePort;
import it.doqui.acta.acaris.officialbookservice.OfficialBookServicePort;
import it.doqui.acta.acaris.relationshipsservice.RelationshipsServicePort;
import it.doqui.acta.acaris.repositoryservice.RepositoryServicePort;
import it.doqui.acta.acaris.smsservice.SMSServicePort;
import it.doqui.acta.acaris.subjectregistryservice.SubjectRegistryServicePort;
import it.doqui.acta.actasrv.client.AcarisServiceClient;
import it.doqui.acta.actasrv.dto.acaris.type.archive.AcarisRepositoryEntryType;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.ClientApplicationInfo;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.PrincipalExtResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.*;
import org.apache.log4j.Logger;

import java.util.Base64;

public class DoquiService {
	
	// 20200719_LC
	private static Logger log = Logger.getLogger(DoquiService.class);
	
	
	// 20200719 PP
	//private String protocol = "http://";
	private String server;
	private String context;
	private int port;
	private boolean isWS;
	private boolean isApiManagerEnabled;

	private SubjectRegistryServicePort srService;
	private OfficialBookServicePort obService;
	private BackOfficeServicePort bkoService;
	private RepositoryServicePort repositoryService;
	private ObjectServicePort objectService; 
	private DocumentServicePort documentService; 
	private ManagementServicePort managementService; 
	private NavigationServicePort navigationService; 
	private RelationshipsServicePort relationshipsService; 
	private SMSServicePort smsService;
	
	// 2020078_LC
	private MultifilingServicePort multifilingService;

	
	
	// 20200719_LC
    public OauthHelper 	apiManagerOauth;
    public String 		apiManagerUser;
    public String 		apiManagerPassword;
    
    private String rootUrlApiManager;
    private String rootUrlApiManagerEnd;

    // 20200723_LC
//    private String rootUrlApiManager = "http://tst-api-piemonte.ecosis.csi.it/documentale/acaris-test-";

    
    
//    public static OauthHelper oauth;
//    
//	static {			
//        oauth = new OauthHelper("http://tst-api-ent.ecosis.csi.it/api/token",
//        "F85WO8XZEGilCTy03o6K5PbKkhYa",
//        "F05_YUkEFkszMkbIfc4_uBD9eXwa");
//	    }

    
    
    // 20200719_LC not used
//	public DoquiService(boolean isWS) {
//		this("tst-applogic.reteunitaria.piemonte.it", "/actasrv/", 80, isWS);
//	}
	
	
	
	
	
	// 20200719_LC nuovi params apiManager
	public DoquiService(String server, String context, int port, boolean isWS, OauthHelper apiManagerOauth, String apiManagerUser, String apiManagerPassword, boolean isApiManEnabled, String rootUrlApiManager, String rootUrlApiManagerEnd) {
		this.server = server;
		this.context = context;
		this.port = port;
		this.isWS = isWS;
		
		// 20200719_LC
		this.apiManagerOauth = apiManagerOauth;
		this.apiManagerUser = apiManagerUser;
		this.apiManagerPassword = apiManagerPassword;	
		
		this.isApiManagerEnabled = isApiManEnabled;
		this.rootUrlApiManager = rootUrlApiManager;
        this.rootUrlApiManagerEnd = rootUrlApiManagerEnd;
	}
	
	private Object getGenericWrapperFactoryBean(Object portObj, Class<?> _class, String service) throws ClassNotFoundException {

		//20200728 se non devo usare apimanager non serve settare tutte queste cose che stanno nel metodo
		if(!isApiManagerEnabled) {
			return portObj;
		}

		TokenRetryManager trm = new TokenRetryManager();
		//System.out.println(trm.getVersion());
		trm.setOauthHelper(apiManagerOauth);
		WsProvider wsp = new CxfImpl();
		//System.out.println("WsProvider id " + wsp.getProviderId());
		
		trm.setWsProvider(wsp);
/*
 * supponiamo di dover impiegare un header JASS per autenticazione
 * username/password
 */
		// 20200719_LC
		String encoded = new String(Base64.getEncoder().encode((apiManagerUser+":"+apiManagerPassword).getBytes()));
		//System.out.println("encodedBytes " + encoded);
		
		trm.putHeader(TokenRetryManager.X_AUTH, "Basic " + encoded);
		
		GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
//		String endPoint = protocol+server+context+service+"WS";
		//System.out.println("endPoint " + endPoint);
		
		// gestita fine della url diversa)
		//String endPoint = rootUrlApiManager+service+"/v1";
		String endPoint = rootUrlApiManager+service+rootUrlApiManagerEnd;
		
		gwfb.setEndPoint(endPoint);
		
		// 20200719_LC
		Class<?>[] interfaces = _class.getInterfaces();
		gwfb.setWrappedInterface(interfaces[0]);
		
		
		gwfb.setPort(portObj);
// port non ha piu' nessun uso 
// quindi lo annullo per evitare di usarlo 
		portObj = null;
		gwfb.setTokenRetryManager(trm);
		
		
		return gwfb.create();
	}
	
	
	public ObjectIdType getRepositoryId(String repositoryName) {
		
		ObjectIdType repositoryId = null;
		AcarisRepositoryEntryType[] repEntries = null;
		
		try {
			repEntries = getRepositoryServicePort().getRepositories();
		} catch (it.doqui.acta.acaris.repositoryservice.AcarisException acEx) {
			if (acEx.getMessage() != null && acEx.getFaultInfo() != null) {
				log.error("acEx.getMessage(): " + acEx.getMessage());
				log.error("acEx.getFaultInfo().getErrorCode(): " + acEx.getFaultInfo().getErrorCode());
				log.error("acEx.getFaultInfo().getPropertyName(): " + acEx.getFaultInfo().getPropertyName());
				log.error("acEx.getFaultInfo().getObjectId(): " + acEx.getFaultInfo().getObjectId());
				log.error("acEx.getFaultInfo().getExceptionType(): " + acEx.getFaultInfo().getExceptionType());
				log.error("acEx.getFaultInfo().getClassName(): " + acEx.getFaultInfo().getClassName());
			} else {
				log.error(" fatal application exception ");
			}
		} catch (Exception ex) {
			//ex.printStackTrace();
			log.error("ex.getMessage() " + ex.getMessage(), ex);
		}
		
		for (AcarisRepositoryEntryType entry : repEntries) {
			// System.out.println(repository.getRepositoryName());
			if (entry.getRepositoryName() != null && entry.getRepositoryName().startsWith(repositoryName, 0)) {
				repositoryId = entry.getRepositoryId();
				break;
			}
		}

		if (repositoryId == null) {
			throw new IllegalArgumentException("Errore: repository " + repositoryName + " non trovato");
		}

		return repositoryId;
	}
	
	public PrincipalIdType getPrincipalExt(ObjectIdType repId, String cf, long idAoo, long idStruttura, long idNodo, String appKey) {
		
		IdAOOType aoo = new IdAOOType();
		aoo.setValue(idAoo);

		IdStrutturaType struttura = new IdStrutturaType();
		struttura.setValue(idStruttura);

		IdNodoType nodo = new IdNodoType();
		nodo.setValue(idNodo);
		
		CodiceFiscaleType codFiscale = new CodiceFiscaleType();
		codFiscale.setValue(cf);

		ClientApplicationInfo cai = new ClientApplicationInfo();
		cai.setAppKey(appKey);

		PrincipalIdType principalId = null;
		try {
			PrincipalExtResponseType[] principal = getBackOfficeServicePort().getPrincipalExt(repId, codFiscale, aoo,
					struttura, nodo, cai);
			principalId = principal[0].getPrincipalId();

		} catch (it.doqui.acta.acaris.backofficeservice.AcarisException acEx) {
			log.error("acEx.getMessage(): " + acEx.getMessage());
			log.error("acEx.getFaultInfo().getErrorCode(): " + acEx.getFaultInfo().getErrorCode());
			log.error("acEx.getFaultInfo().getPropertyName(): " + acEx.getFaultInfo().getPropertyName());
			log.error("acEx.getFaultInfo().getObjectId(): " + acEx.getFaultInfo().getObjectId());
			log.error("acEx.getFaultInfo().getExceptionType(): " + acEx.getFaultInfo().getExceptionType());
			log.error("acEx.getFaultInfo().getClassName(): " + acEx.getFaultInfo().getClassName());
			log.error("acEx.getFaultInfo().getTechnicalInfo: " + acEx.getFaultInfo().getTechnicalInfo());
		} catch (Exception e) {
			log.error("e.getMessage(): " + e.getMessage(), e);
		}

		return principalId;
	}
	
	
	public SubjectRegistryServicePort getSubjectRegistryServicePort() throws it.doqui.acta.acaris.subjectregistryservice.AcarisException {
		
		
		if (srService == null) {
			srService = AcarisServiceClient.getSubjectRegistryServiceAPI(server, context, port);
			try {
				srService = (SubjectRegistryServicePort)getGenericWrapperFactoryBean(srService, srService.getClass(), "subjectregistry");
			} catch (ClassNotFoundException e) {
				log.error("Exception = " + e.getMessage());
			}
		}

		return srService;
	}

	
	public OfficialBookServicePort getOfficialBookServicePort() throws it.doqui.acta.acaris.officialbookservice.AcarisException {
		if (obService == null) {
			obService = AcarisServiceClient.getOfficialBookServiceAPI(server, context, port);
			try {
				obService = (OfficialBookServicePort)getGenericWrapperFactoryBean(obService, obService.getClass(), "officialbook");
			} catch (ClassNotFoundException e) {
				log.error("Exception = " + e.getMessage());
			}
		}

		return obService;
	}
	
	public BackOfficeServicePort getBackOfficeServicePort() throws it.doqui.acta.acaris.backofficeservice.AcarisException {
		if (bkoService == null) {
			bkoService = AcarisServiceClient.getBackofficeServiceAPI(server, context, port);
			try {
				
				bkoService = (BackOfficeServicePort)getGenericWrapperFactoryBean(bkoService, bkoService.getClass(), "backoffice");
			} catch (ClassNotFoundException e) {
				log.error("Exception = " + e.getMessage());
			}
		}

		return bkoService;
	}
	
	public ObjectServicePort getObjectServicePort() throws it.doqui.acta.acaris.objectservice.AcarisException {
		if (objectService == null) {
			objectService = AcarisServiceClient.getObjectServiceAPI(server, context, port, isWS);
			try {
				objectService = (ObjectServicePort)getGenericWrapperFactoryBean(objectService, objectService.getClass(), "object");
			} catch (ClassNotFoundException e) {
				log.error("Exception = " + e.getMessage());
			}
		}

		return objectService;
	}

	public RepositoryServicePort getRepositoryServicePort()	throws it.doqui.acta.acaris.repositoryservice.AcarisException {
		if (repositoryService == null) {
			repositoryService = AcarisServiceClient.getRepositoryServiceAPI(server, context, port);
			try {
	
				
				// 20200719_LC
				// ---------------------------------------------------------------------------------------------------------------
				// ---------------------------------------------------------------------------------------------------------------
				// implementazione specifica per repositoryServicePort
//				TokenRetryManager trm = new TokenRetryManager();
//				System.out.println(trm.getVersion());
//				trm.setOauthHelper(oauth);
//				WsProvider wsp = new CxfImpl();
//				System.out.println("WsProvider id " + wsp.getProviderId());
//				
//				trm.setWsProvider(wsp);
//	
//				String encoded = new String(Base64.getEncoder().encode("User:mypass".getBytes()));
//				System.out.println("encodedBytes " + encoded);
//				
//				trm.putHeader(TokenRetryManager.X_AUTH, "Basic " + encoded);
//				
//				GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
//				String endPoint = "http://"+server+context+"repositoryWS";
//				System.out.println("endPoint " + endPoint);
//				gwfb.setEndPoint(endPoint);
//				gwfb.setWrappedInterface(it.doqui.acta.acaris.repositoryservice.RepositoryServicePort.class);
//				gwfb.setPort(repositoryService);
//				
//				// port non ha piu' nessun uso 
//				// quindi lo annullo per evitare di usarlo 
//				repositoryService = null;
//				gwfb.setTokenRetryManager(trm);
//				
//				repositoryService = (RepositoryServicePort)gwfb.create();
				// ---------------------------------------------------------------------------------------------------------------
				// ---------------------------------------------------------------------------------------------------------------

				
				
				repositoryService = (RepositoryServicePort)getGenericWrapperFactoryBean(repositoryService, repositoryService.getClass(), "repository");				
			} catch (ClassNotFoundException e) {
				log.error("Exception = " + e.getMessage());
			}
		}

		return repositoryService;
	}
	
	public DocumentServicePort getDocumentServicePort() throws it.doqui.acta.acaris.documentservice.AcarisException {
		if (documentService == null) {
			documentService = AcarisServiceClient.getDocumentServiceAPI(server, context, port, isWS);
			try {
				documentService = (DocumentServicePort)getGenericWrapperFactoryBean(documentService, documentService.getClass(), "document");
			} catch (ClassNotFoundException e) {
				log.error("Exception = " + e.getMessage());
			}
		}

		return documentService;
	}
	
	public ManagementServicePort getManagementServicePort() throws it.doqui.acta.acaris.managementservice.AcarisException {
		if (managementService == null) {
			managementService = AcarisServiceClient.getManagementServiceAPI(server, context, port);
			try {
				managementService = (ManagementServicePort)getGenericWrapperFactoryBean(managementService, managementService.getClass(), "management");
			} catch (ClassNotFoundException e) {
				log.error("Exception = " + e.getMessage());
			}
		}

		return managementService;
	}

	public NavigationServicePort getNavigationServicePort() throws it.doqui.acta.acaris.navigationservice.AcarisException {
		if (navigationService == null) {
			navigationService = AcarisServiceClient.getNavigationServiceAPI(server, context, port);
			try {
				navigationService = (NavigationServicePort)getGenericWrapperFactoryBean(navigationService, navigationService.getClass(), "navigation");
			} catch (ClassNotFoundException e) {
				log.error("Exception = " + e.getMessage());
			}
		}

		return navigationService;
	}
	
	public SMSServicePort getSmsServicePort() throws it.doqui.acta.acaris.smsservice.AcarisException {
		if (smsService == null) {
			smsService = AcarisServiceClient.getSmsServiceAPI(server, context, port);
			try {
				smsService = (SMSServicePort)getGenericWrapperFactoryBean(smsService, smsService.getClass(), "sms");
			} catch (ClassNotFoundException e) {
				log.error("Exception = " + e.getMessage());
			}
		}

		return smsService;
	}

	public RelationshipsServicePort getRelationshipsServicePort() throws it.doqui.acta.acaris.relationshipsservice.AcarisException {
		if (relationshipsService == null) {
			relationshipsService = AcarisServiceClient.getRelationshipsServiceAPI(server, context, port, isWS);
			try {
				String service="relationships";
				if(isApiManagerEnabled) {
					service="relationship";
				}
				relationshipsService = (RelationshipsServicePort)getGenericWrapperFactoryBean(relationshipsService, relationshipsService.getClass(), service);
			} catch (ClassNotFoundException e) {
				log.error("Exception = " + e.getMessage());
			}
		}

		return relationshipsService;
	}
	
	public boolean isWS() {
		return isWS;
	}

	public void setWS(boolean isWS) {
		this.isWS = isWS;
	}
	
//	public UtilsCnmCProprietaService getUtilsCnmCProprietaService() {
//		return (UtilsCnmCProprietaService) SpringApplicationContextHelper.getBean("UtilsCnmCProprietaService");
//	}
//	
	
	
	
	// 20200728_LC
	public MultifilingServicePort getMultifilingServicePort() throws it.doqui.acta.acaris.multifilingservice.AcarisException {
		if (multifilingService == null) {
			multifilingService = AcarisServiceClient.getMultifillingServiceAPI(server, context, port);
			try {
				multifilingService = (MultifilingServicePort)getGenericWrapperFactoryBean(multifilingService, multifilingService.getClass(), "multifilling");
			} catch (ClassNotFoundException e) {
				log.error("Exception = " + e.getMessage());
			}
		}

		return multifilingService;
	}
}
