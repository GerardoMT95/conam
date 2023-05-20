/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.aspect;

import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.annotation.LoggingType;
import it.csi.conam.conambl.common.annotation.NoLogging;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.util.performance.StopWatch;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Random;

@Aspect
@Order(value = 10)
public class LoggingAspect {

	private static Logger logger = Logger.getLogger(Constants.COMPONENT_NAME + ".tracer");
	private static Random random;
	private static ObjectMapper mapper;
	private StopWatch stopWatch = null;

	{
		mapper = new ObjectMapper();
		random = new Random();
	}

	@Around(value = "execution(* it.csi.conam.conambl.business.service.impl..*(..))", argNames = "joinPoint")
	public Object aroundManagerCall(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean logInput = logger.isDebugEnabled();
		boolean logOutput = logger.isDebugEnabled();
		boolean measureTiming = logger.isInfoEnabled();
		return logInputOutput("service", joinPoint, logInput, logOutput, measureTiming);
	}

	@Around(value = "execution(* it.csi.conam.conambl.integration.repositories..*(..))", argNames = "joinPoint")
	public Object aroundDAOCall(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean logInput = logger.isDebugEnabled();
		boolean logOutput = logger.isDebugEnabled();
		boolean measureTiming = logger.isInfoEnabled();
		return logInputOutput("repository", joinPoint, logInput, logOutput, measureTiming);
	}

	@Around(value = "execution(* it.csi.conam.conambl.business.facade.impl..*(..))", argNames = "joinPoint")
	public Object aroundFacadeCall(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean logInput = logger.isDebugEnabled();
		boolean logOutput = logger.isDebugEnabled();
		boolean measureTiming = logger.isInfoEnabled();
		return logInputOutput("facade", joinPoint, logInput, logOutput, measureTiming);
	}

	@Around(value = "execution(* it.csi.conam.conambl.integration.mapper.impl..*(..))", argNames = "joinPoint")
	public Object aroundMapperCall(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean logInput = logger.isDebugEnabled();
		boolean logOutput = logger.isDebugEnabled();
		boolean measureTiming = logger.isInfoEnabled();
		return logInputOutput("mapper", joinPoint, logInput, logOutput, measureTiming);
	}

	@Around(value = "execution(* it.csi.conam.conambl.business.doqui.service.impl..*(..))", argNames = "joinPoint")
	public Object aroundManagerDoquiCall(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean logInput = logger.isDebugEnabled();
		boolean logOutput = logger.isDebugEnabled();
		boolean measureTiming = logger.isInfoEnabled();
		return logInputOutput("service", joinPoint, logInput, logOutput, measureTiming);
	}

	@Around(value = "execution(* it.csi.conam.conambl.integration.doqui.repositories..*(..))", argNames = "joinPoint")
	public Object aroundDAODoquiCall(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean logInput = logger.isDebugEnabled();
		boolean logOutput = logger.isDebugEnabled();
		boolean measureTiming = logger.isInfoEnabled();
		return logInputOutput("repository", joinPoint, logInput, logOutput, measureTiming);
	}

	
	private Object logInputOutput(String sourceType, ProceedingJoinPoint joinPoint, boolean logInput, boolean logOutput, boolean measureTiming) throws Throwable {

		final String IDENTATION_PARAM = "aspectLogIdentationLevel";

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = null;
		if (requestAttributes != null)
			request = ((ServletRequestAttributes) requestAttributes).getRequest();

		Integer identationLevel = 0;
		if (request != null && request.getAttribute(IDENTATION_PARAM) != null)
			identationLevel = (Integer) request.getAttribute(IDENTATION_PARAM);
		String rai = getRequestRai(request);

		if (request != null)
			request.setAttribute(IDENTATION_PARAM, identationLevel + 1);

		String raiPrefix = "[RAI" + rai + "] ";

		StringBuilder identation = new StringBuilder();

		for (int i = 0; i < identationLevel; i++) {
			identation.append("|    ");
		}

		String openingTag = "/----------------------------------------------------";
		String closingTag = "\\----------------------------------------------------";
		String identationFirst = identation.toString();

		StringBuilder identedSourceType = new StringBuilder(sourceType);
		if (identedSourceType.length() < 10) {
			int toAdd = (10 - identedSourceType.length());
			for (int i = 0; i < toAdd; i++) {
				identedSourceType.append(" ");
			}
		}

		String logStr;
		Object result = null;

		String logPrefixFirst = raiPrefix + "[ " + identedSourceType + "] - " + identationFirst + "| ";
		String logPrefix = raiPrefix + "[ " + identedSourceType + "] - " + identation + "| ";
		String logPrefixHeader = raiPrefix + "[ " + identedSourceType + "] - " + identation + "";

		String signatureName = getTargetObject(joinPoint.getTarget()).getSimpleName() + "." + joinPoint.getSignature().getName();

		logger.info(logPrefixHeader + openingTag);

		if (logInput) {
			logStr = String.format(logPrefixFirst + "%s INPUT : ( ", signatureName);
			if (isEnableLoggingForMethod(joinPoint, TypeLogging.REQUEST)) {
				Object[] args = joinPoint.getArgs();
				for (int i = 0; i < args.length; i++) {
					if (args[i] != null) {
						logStr += serializeParameter(args[i]) + ", ";
					}
				}
				if (args.length > 0) {
					logStr = logStr.substring(0, logStr.length() - 2);
				}
			}
			logStr += " )";

			logger.info(logStr);
		} else {
			logStr = String.format(logPrefixFirst + "%s START", signatureName);
			logger.info(logStr);
		}

		Throwable threw = null;
		Long startTime = measureTiming ? new Date().getTime() : null;

		try {
			result = joinPoint.proceed();
		} catch (Throwable e) {
			threw = e;
		}

		if (measureTiming) {
			if (isEnableLoggingForMethod(joinPoint, TypeLogging.MEASURE_TIME)) {
				logStr = String.format(logPrefix + "%s DURATION : ", signatureName);
				logStr += ((new Date().getTime()) - startTime) + " ms";
				logger.info(logStr);
			}
		}

		if (threw != null) {
			if (threw instanceof BusinessException) {
				logStr = String.format(logPrefix + "%s END WITH BUSINESS EXCEPTION : %s", signatureName, ((BusinessException) threw).getCodice());
				logger.info(logStr);
			} else {
				logStr = String.format(logPrefix + "%s THREW EXCEPTION : ", signatureName);
				logStr += threw.getMessage();
				logger.error(logStr, threw);
			}

		} else {
			if (logOutput) {
				logStr = String.format(logPrefix + "%s OUTPUT : ", signatureName);
				if (isEnableLoggingForMethod(joinPoint, TypeLogging.RESPONSE))
					logStr += serializeParameter(result);
				logger.info(logStr);
			} else {
				logStr = String.format(logPrefix + "%s FINISHED", signatureName);
				logger.info(logStr);
			}
		}
		if (requestAttributes != null)
			request.setAttribute(IDENTATION_PARAM, identationLevel);

		logger.info(logPrefixHeader + closingTag);

		if (threw != null) {
			throw threw;
		}

		return result;
	}

	private boolean isEnableLoggingForMethod(ProceedingJoinPoint joinPoint, TypeLogging log) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		NoLogging noLogging = method.getAnnotation(NoLogging.class);
		if (noLogging == null)
			return true;

		if (noLogging.request().equals(LoggingType.NONE) && log.equals(TypeLogging.REQUEST))
			return false;
		if (noLogging.response().equals(LoggingType.NONE) && log.equals(TypeLogging.RESPONSE))
			return false;
		if (noLogging.measureTime().equals(LoggingType.NONE) && log.equals(TypeLogging.MEASURE_TIME))
			return false;

		return true;
	}

	protected Class<?> getTargetObject(Object proxy) throws Exception {
		// AopUtils.getTargetClass(joinPoint.getTarget())

		if (AopUtils.isJdkDynamicProxy(proxy)) {
			while ((AopUtils.isJdkDynamicProxy(proxy))) {
				return getTargetObject(((Advised) proxy).getTargetSource().getTarget());
			}
			return proxy.getClass();
		} else if (AopUtils.isCglibProxy(proxy)) {
			Class<?> proxyClass = proxy.getClass().getSuperclass();
			while (AopUtils.isCglibProxy(proxyClass)) {
				proxyClass = proxy.getClass().getSuperclass();
			}
			return proxyClass;
		} else if (proxy.getClass().getCanonicalName().contains("com.sun.proxy.$Proxy")) {
			Class<?>[] interfaces = proxy.getClass().getInterfaces();
			if (interfaces.length > 0) {
				return interfaces[0];
			} else {
				return proxy.getClass();
			}
		} else {
			return proxy.getClass();
		}
	}

	private String getRequestRai(HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String rai = String.valueOf(-Double.valueOf(Math.floor(100000 + random.nextDouble() * 800000)).intValue());
		// se non ho sessione genero un ray casuale
		if (auth == null)
			return rai;
		else {
			UserDetails user = (UserDetails) auth.getPrincipal();
			return user.getRai();
		}
	}

	private String serializeParameter(Object raw) {
		if (raw == null)
			return "null";
		try {
			if (raw instanceof String) {
				return "\"" + raw + "\"";
			} else if (raw instanceof Object) {
				if (raw.getClass().getName().startsWith("it.csi")) {
					return raw.getClass().getSimpleName() + ":" + mapper.writeValueAsString(raw);
				} else {
					return raw.toString();
				}
			} else {
				return String.valueOf(raw);
			}
		} catch (Exception e) {
			return String.valueOf(raw);
		}
	}

	enum TypeLogging {
		REQUEST("request"), //
		RESPONSE("response"), //
		MEASURE_TIME("measure");

		private String type;

		TypeLogging(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}

	
	@Around(value = "execution(* it.csi.conam.conambl.web..*(..))", argNames = "joinPoint")
	public Object aroundRestStopWatchCall(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "request";
		return stopWatch(joinPoint, type);
	}
	
	@Around(value = "execution(* it.csi.conam.conambl.integration.doqui.service.impl..*(..))", argNames = "joinPoint")
	public Object aroundManagerStopWatchCall(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "service";
		return stopWatch(joinPoint, type);
	}
	
	@Around(value = "execution(* it.csi.conam.conambl.integration.repositories..*(..))", argNames = "joinPoint")
	public Object aroundDAOStopWatchCall(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "repository";
		return stopWatch(joinPoint, type);
	}
	
	@Around(value = "execution(* it.csi.conam.conambl.integration.doqui.repositories..*(..))", argNames = "joinPoint")
	public Object aroundDAODoquiStopWatchCall(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "repository";
		return stopWatch(joinPoint, type);
	}
	
	
	private Object stopWatch(ProceedingJoinPoint joinPoint, String type) throws Throwable {
		String method = joinPoint.getSignature().getName();
		String className = joinPoint.getSignature().getDeclaringTypeName();
		try {
			stopWatch = new StopWatch(Constants.COMPONENT_NAME);
			stopWatch.start();
			Object rval = joinPoint.proceed();
			return rval;
		} finally {
			stopWatch.stop();
			stopWatch.dumpElapsed(className, method, "invocazione " + type + " [conam]::[" + method + "]", "(Interceptor)");
		}
	}

}
