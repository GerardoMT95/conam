/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.apimanager;

import it.csi.conam.conambl.vo.verbale.ResidenzaVO;
import it.csi.conam.conambl.vo.verbale.SoggettoPregressiVO;
import it.csi.wso2.apiman.oauth2.helper.GenericWrapperFactoryBean;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;
import it.csi.wso2.apiman.oauth2.helper.TokenRetryManager;
import it.csi.wso2.apiman.oauth2.helper.WsProvider;
import it.csi.wso2.apiman.oauth2.helper.extra.cxf.CxfImpl;
import it.doqui.acta.acaris.repositoryservice.AcarisException;
import it.doqui.acta.acaris.repositoryservice.RepositoryServicePort;
import it.doqui.acta.actasrv.client.AcarisServiceClient;
import it.doqui.acta.actasrv.dto.acaris.type.archive.AcarisRepositoryEntryType;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

public class TestAPI {

	// copiato da EipMockServiceAxisPortType_EipMockServiceAxisHttpSoap11Endpoint_Client
//    private static final QName SERVICE_NAME = new QName("http://mockservice.wso2eip.csi.it", "EipMockServiceAxis");

    // copiato da EipMockServiceAxis
//    public final static URL WSDL_LOCATION;
    public static OauthHelper oauth;

// copiato con modifiche da  EipMockServiceAxis
// inizializzo la location del wsdl usando il classloader e non il filesystem
// la directory in cui repereire il wsdl deve essere inserita nel classpath
// questo � in linea a quanto avviene in un application server
    
    static {
//    	URL url = AcarisServiceClient..class.getResource("");
//        URL url = EipMockServiceAxis.class.getResource("/EipMockServiceAxis.wsdl");

//        if (url == null) {
//        	System.out.println("Can not initialize the default wsdl");
//
//        }       
//        WSDL_LOCATION = url;

    	//hCE3PaxXi1n8jrKM__yf7IBnrB4a
//        oauth = new OauthHelper("http://tst-api-ent.ecosis.csi.it/api/token",
//        		                "F85WO8XZEGilCTy03o6K5PbKkhYa",
//        		                "F05_YUkEFkszMkbIfc4_uBD9eXwa");

        oauth = new OauthHelper("https://tst-api-piemonte.ecosis.csi.it/token",
        		                "MzFG7GUx7sr1EFcyahAcP8n6c6Ya",
        		                "hCE3PaxXi1n8jrKM__yf7IBnrB4a");
    }
    
	public static void main(String ... args) {
		
		SoggettoPregressiVO s = new SoggettoPregressiVO();
		s.getResidenzaList().add(new ResidenzaVO());
		org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(s));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		TestAPI me = new TestAPI();
//		me.exec();
	}
	
	private void exec() {

        try {
        	
        	RepositoryServicePort port = AcarisServiceClient.getRepositoryServiceAPI("tst-applogic.reteunitaria.piemonte.it", "/actasrv", 80);
//			RepositoryServicePort port = AcarisServiceClient.getRepositoryServiceAPI("tst-api-piemonte.ecosis.csi.it/documentale/acaris-test-repository", "/v1", 80);
	//		AcarisServiceClient ss = new AcarisServiceClient(WSDL_LOCATION, SERVICE_NAME);
	//        EipMockServiceAxisPortType port = ss.getEipMockServiceAxisHttpSoap11Endpoint();  
	        
	        System.out.println("TokenTetry version : " + OauthHelper.getVersion());
		    
			TokenRetryManager trm = new TokenRetryManager();
			System.out.println(trm.getVersion());
			trm.setOauthHelper(oauth);
			WsProvider wsp = new CxfImpl();
			System.out.println("WsProvider id " + wsp.getProviderId());
			
			trm.setWsProvider(wsp);
	/*
	 * supponiamo di dover impiegare un header JASS per autenticazione
	 * username/password
	 */
			String encoded = new String(Base64.encodeBase64("User:mypass".getBytes()));
			System.out.println("encodedBytes " + encoded);
			
			trm.putHeader(TokenRetryManager.X_AUTH, "Basic " + encoded);
			
			GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
			gwfb.setEndPoint("http://tst-api-piemonte.ecosis.csi.it/documentale/acaris-test-repository/v1");
			gwfb.setWrappedInterface(RepositoryServicePort.class);
			gwfb.setPort(port);
	// port non ha piu' nessun uso 
	// quindi lo annullo per evitare di usarlo 
			port = null;
			gwfb.setTokenRetryManager(trm);
		
        	RepositoryServicePort wrapped = (RepositoryServicePort) gwfb.create();
        	for (int i = 1; i <= 10; i++) {
        		String request = "ciao " + i;
        		System.out.println("request " + request);
        		AcarisRepositoryEntryType[] response = wrapped.getRepositories();
        		for(AcarisRepositoryEntryType t : response) {
        			System.out.println("Response from service is: " + t.getRepositoryName());
        			Thread.sleep(500);
        		}
        	}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (AcarisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
