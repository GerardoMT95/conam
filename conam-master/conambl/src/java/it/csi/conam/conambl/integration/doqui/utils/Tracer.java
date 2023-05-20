/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.utils;

import org.apache.log4j.Logger;

public class Tracer {

	private static final String separator = "::";		

	private static final String prefix = "[";
	private static final String suffix = "]";



	public static void debug(Logger log, String clazz, String methodName, String msg) {
		if (log.isDebugEnabled()) {
			log.debug(createOutput(clazz, methodName, msg));
		}
	}
	
	public static void info(Logger log, String clazz, String methodName, String msg) {
		if (log.isInfoEnabled()) {
			log.info(createOutput(clazz, methodName, msg));
		}
	}
	
	public static void warn(Logger log, String clazz, String methodName, String msg) {
		log.warn(createOutput(clazz, methodName, msg));	
	}
	
	public static void warn(Logger log, String clazz, String methodName, String msg, Throwable e) {
		log.warn(createOutput(clazz, methodName, msg),e);	
	}
	public static void error(Logger log, String clazz, String methodName, String msg, Throwable e) {
		log.error(createOutput(clazz, methodName, msg),e);	
	}
	public static void error(Logger log, String clazz, String methodName, String msg) {
		log.error(createOutput(clazz, methodName, msg));	
	}
	
	public static void fatal(Logger log, String clazz, String methodName, String msg) {
		log.fatal(createOutput(clazz, methodName, msg));	
	}
	
	public static void trace(Logger log, String clazz, String methodName, String msg) {
		log.trace(createOutput(clazz, methodName, msg));	
	}

	private static StringBuffer createOutput(String className, String methodName, String msg)  {

		int index = className.lastIndexOf(".");
		if(index != -1){
			className = className.substring(index + 1, className.length());

		}
		StringBuffer sb = new StringBuffer(separator);
		sb.append(prefix);
		sb.append(className);
		sb.append(separator);
		sb.append(methodName);
		sb.append(suffix);
		sb.append(" ");
		if (msg!=null) {
			sb.append(msg);	
		}
		return sb;	
	}


}
