/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.aspect;

import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(value = 20)
public class BusinessExceptionInterceptor {

	@Around("execution(* it.csi.conam.conambl.business.service.impl..*.*(..))")
	public Object wrapException(ProceedingJoinPoint joinPoint) throws BusinessException, Throwable {
		System.out.println("\n\nPASSO DALL'INTERCEPTOR ECCEZIONI BUSINESS!!!!!!!!!!");
		Object result = null;

		try {
			// Invocazione del metodo di business
			result = joinPoint.proceed();
		} catch (RuntimeException exc) {
			System.out.println("RuntimeException :: " + exc.getStackTrace());
			throw new BusinessException(ErrorCode.ERRORE_GENERICO_SISTEMA);
		} catch (Exception exc) {
			System.out.println("Exception :: " + exc.getStackTrace());
			throw new BusinessException(ErrorCode.ERRORE_GENERICO_SISTEMA);
		}

		return result;
	}
}
