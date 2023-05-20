/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web.soap;

import it.csi.conam.conambl.business.service.epay.EpayService;
import it.csi.conam.conambl.integration.epay.from.EsitoAggiornaPosizioniDebitorieRequest;
import it.csi.conam.conambl.integration.epay.from.EsitoInserimentoListaDiCaricoRequest;
import it.csi.conam.conambl.integration.epay.from.ResponseType;
import it.csi.conam.conambl.integration.epay.from.TrasmettiNotifichePagamentoRequest;
import it.csi.conam.conambl.util.UtilsEpay;
import org.apache.cxf.interceptor.InInterceptors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * @author riccardo.bova
 * @date 04 mag 2018
 */

@InInterceptors(interceptors = { "org.apache.cxf.interceptor.LoggingInInterceptor" })
@WebService(targetNamespace = "http://www.csi.it/epay/epaywso/epaywso2enti/types")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE, use = SOAPBinding.Use.LITERAL, style = SOAPBinding.Style.DOCUMENT)
public class EpaySoapResource {

	@Autowired
	private EpayService epayService;
	
	protected static Logger logger = Logger.getLogger(EpaySoapResource.class);

	@WebMethod(action = "http://www.csi.it/epay/epaywso/service/EsitoInserimentoListaDiCarico")
	@WebResult(name = "EPaywsoServiceResponse", targetNamespace = "http://www.csi.it/epay/epaywso/types", partName = "parameters")
	public ResponseType esitoInserimentoListaDiCarico(
			@WebParam(partName = "parameters", name = "EsitoInserimentoListaDiCaricoRequest", targetNamespace = "http://www.csi.it/epay/epaywso/epaywso2enti/types") EsitoInserimentoListaDiCaricoRequest parameters) {
		ResponseType responseType;
		logger.info("chiamata epay. Resource esitoInserimentoListaDiCarico");
		try {
			responseType = epayService.esitoInserimentoListaDiCarico(parameters);
		} catch (Exception e) {
			responseType = UtilsEpay.getGenericErrorResult();
		}
		return responseType;

	}

	@WebMethod(action = "http://www.csi.it/epay/epaywso/service/EsitoAggiornaPosizioniDebitorie")
	@WebResult(name = "EPaywsoServiceResponse", targetNamespace = "http://www.csi.it/epay/epaywso/types", partName = "parameters")
	public ResponseType esitoAggiornaPosizioniDebitorie(
			@WebParam(partName = "parameters", name = "EsitoAggiornaPosizioniDebitorieRequest", targetNamespace = "http://www.csi.it/epay/epaywso/epaywso2enti/types") EsitoAggiornaPosizioniDebitorieRequest parameters) {
		ResponseType responseType;
		logger.info("chiamata epay. Resource esitoAggiornaPosizioniDebitorie");
		try {
			responseType = epayService.esitoAggiornaPosizioniDebitorie(parameters);
		} catch (Exception e) {
			responseType = UtilsEpay.getGenericErrorResult();
		}
		return responseType;

	}

	@WebMethod(action = "http://www.csi.it/epay/epaywso/service/TrasmettiNotifichePagamento")
	@WebResult(name = "EPaywsoServiceResponse", targetNamespace = "http://www.csi.it/epay/epaywso/types", partName = "parameters")
	public ResponseType trasmettiNotifichePagamento(
			@WebParam(partName = "parameters", name = "TrasmettiNotifichePagamentoRequest", targetNamespace = "http://www.csi.it/epay/epaywso/epaywso2enti/types") TrasmettiNotifichePagamentoRequest parameters) {
		ResponseType responseType;
		logger.info("chiamata epay. Resource trasmettiNotifichePagamento");
		try {
			responseType = epayService.trasmettiNotifichePagamento(parameters);
		} catch (Exception e) {
			responseType = UtilsEpay.getGenericErrorResult();
		}
		return responseType;

	}

	@WebMethod(exclude = true)
	@PostConstruct
	public void postConstruct() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

}
