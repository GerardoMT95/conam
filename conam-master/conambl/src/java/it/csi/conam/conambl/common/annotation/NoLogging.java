/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author riccardo.bova
 * @date 06 feb 2019
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface NoLogging {

	LoggingType response() default LoggingType.NONE;

	LoggingType request() default LoggingType.NONE;

	LoggingType measureTime() default LoggingType.NONE;

}
