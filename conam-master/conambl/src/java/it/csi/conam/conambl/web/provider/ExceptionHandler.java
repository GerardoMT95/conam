/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web.provider;

import it.csi.conam.conambl.business.SpringApplicationContextHelper;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.common.exception.FileP7MNotSignedException;
import it.csi.conam.conambl.common.exception.FileSizeException;
import it.csi.conam.conambl.common.exception.RemoteWebServiceException;
import it.csi.conam.conambl.integration.entity.CnmDMessaggio;
import it.csi.conam.conambl.integration.entity.CnmDTipoMessaggio;
import it.csi.conam.conambl.integration.mapper.entity.MessaggioEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmDMessaggioRepository;
import it.csi.conam.conambl.integration.repositories.CnmDTipoMessaggioRepository;
import it.csi.conam.conambl.vo.ExceptionVO;
import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.jboss.resteasy.spi.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@SuppressWarnings("deprecation")
@Provider
public class ExceptionHandler implements ExceptionMapper<RuntimeException> {

	private Logger logger = LoggerFactory.getLogger(Constants.HANDLER_EXCEPTION);

	public Response toResponse(RuntimeException exception) {
		if (exception instanceof RemoteWebServiceException)
			return handleRemoteWebServiceException((RemoteWebServiceException) exception);
		else if (exception instanceof NotFoundException)
			return handleNotFoundException((NotFoundException) exception);
		else if (exception instanceof MethodConstraintViolationException)
			return handleValidationException((MethodConstraintViolationException) exception);
		else if (exception instanceof FileSizeException)
			return handleFileSizeException((FileSizeException) exception);
		// 20200907 PP
		else if (exception instanceof FileP7MNotSignedException)
			return handleFileP7MException((FileP7MNotSignedException) exception);
		
		else if (exception instanceof BusinessException)
			return handleBusinessException((BusinessException) exception);
		else if (exception instanceof AccessDeniedException)
			return handleAccessDeniedException((AccessDeniedException) exception);
		else
			return handleGenericException(exception);
	}

	private Response handleRemoteWebServiceException(RemoteWebServiceException exception) {
		return Response.ok(retrieveError(exception.getCodice())).build();
	}

	private Response handleFileSizeException(FileSizeException ex) {
		ExceptionVO exceptionVO = new ExceptionVO(ex.getMessage(), ErrorCode.FILE_SIZE_ERR, null);
		return Response.status(HttpStatus.REQUEST_ENTITY_TOO_LARGE.value()).entity(exceptionVO).build();
	}

	// 20200907 PP - restituisco messaggio in caso di P7M non firmato 
	private Response handleFileP7MException(FileP7MNotSignedException ex) {
		return Response.ok(new ExceptionVO("", ex.getMessage(), ErrorCode.ERROR_DANGER_DESC)).build();
	}
	
	private Response handleBusinessException(BusinessException exception) {
		return Response.ok(retrieveError(exception.getCodice())).build();
	}

	private Response handleNotFoundException(NotFoundException exception) {
		logger.error("Chiamato url non censito: " + exception.getMessage());
		return Response.status(HttpStatus.NOT_FOUND.value()).build();
	}

	protected Response handleGenericException(RuntimeException exception) {
		return Response.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
	}

	// ECCEZIONE DI SPRING SECURITY NOT AUTHORIZED 403
	protected Response handleAccessDeniedException(AccessDeniedException exception) {
		return Response.status(HttpStatus.FORBIDDEN.value()).entity(new ExceptionVO(ErrorCode.ACCESSO_NON_AUTORIZZATO, exception.getMessage(), ErrorCode.ERROR_DANGER_DESC)).build();
	}

	// VALIDAZIONE RESTEASY
	protected Response handleValidationException(MethodConstraintViolationException exception) {
		logger.error("VALIDATION RESTEASY EXCEPTION", exception);
		String codice = ErrorCode.ERRORE_GENERICO;
		if (exception.getConstraintViolations() != null && !exception.getConstraintViolations().isEmpty()) {
			for (MethodConstraintViolation<?> method : exception.getConstraintViolations()) {
				codice = method.getMessage();
				break;
			}
		}
		retrieveError(codice);
		return Response.status(HttpStatus.OK.value()).entity(retrieveError(codice)).build();
	}

	private ExceptionVO retrieveError(String codice) {
		if (codice == null)
			return new ExceptionVO("Attenzione si verificato un errore generico!", "errore sconosciuto", ErrorCode.ERROR_DANGER_DESC);
		CnmDMessaggio cnmDMessaggio = getCnmDMessaggioRepository().findByCodMessaggio(codice);
		if (cnmDMessaggio == null && !codice.equals(ErrorCode.ERRORE_GENERICO)) {
			logger.warn("Codice messaggio non trovato: "+codice);
			cnmDMessaggio = new CnmDMessaggio();
			cnmDMessaggio.setCodMessaggio(codice);
			cnmDMessaggio.setDescMessaggio("Non trovato");
			CnmDTipoMessaggio cnmDTipoMessaggio = getCnmDTipoMessaggioRepository().findOne(ErrorCode.ERROR_DANGER_ID);
			cnmDMessaggio.setCnmDTipoMessaggio(cnmDTipoMessaggio);
			getCnmDMessaggioRepository().save(cnmDMessaggio);
			return new ExceptionVO(codice, "errore sconosciuto", ErrorCode.ERROR_DANGER_DESC);

		}
		return getMessaggioEntityMapper().mapEntityToVO(cnmDMessaggio);
	}

	public CnmDMessaggioRepository getCnmDMessaggioRepository() {
		return (CnmDMessaggioRepository) SpringApplicationContextHelper.getBean("CnmDMessaggioRepository");
	}

	public MessaggioEntityMapper getMessaggioEntityMapper() {
		return (MessaggioEntityMapper) SpringApplicationContextHelper.getBean("messaggioEntityMapperImpl");
	}

	public CnmDTipoMessaggioRepository getCnmDTipoMessaggioRepository() {
		return (CnmDTipoMessaggioRepository) SpringApplicationContextHelper.getBean("cnmDTipoMessaggioRepository");
	}

}
