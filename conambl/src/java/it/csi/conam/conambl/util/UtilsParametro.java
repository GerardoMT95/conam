/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.util;

import com.google.common.base.Predicate;
import it.csi.conam.conambl.integration.entity.CnmCParametro;

/**
 * @author riccardo.bova
 * @date 29 gen 2019
 */
public class UtilsParametro {

	public static Predicate<CnmCParametro> findByIdParametro(long idParametro) {
		return new Predicate<CnmCParametro>() {
			@Override
			public boolean apply(CnmCParametro input) {
				return input.getIdParametro() == idParametro;
			}
		};
	}
}
