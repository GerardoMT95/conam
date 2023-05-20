/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.util;

import com.google.common.base.Predicate;
import it.csi.conam.conambl.integration.entity.CnmTAllegatoField;

/**
 * @author riccardo.bova
 * @date 29 gen 2019
 */
public class UtilsFieldAllegato {

	public static Predicate<CnmTAllegatoField> findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(long idField) {
		return new Predicate<CnmTAllegatoField>() {
			public boolean apply(CnmTAllegatoField cnmTAllegatoField) {
				return idField == cnmTAllegatoField.getCnmCField().getIdField();
			}
		};
	}

}
