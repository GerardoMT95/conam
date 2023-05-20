/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.specification;

import it.csi.conam.conambl.integration.entity.CnmDGruppo;
import it.csi.conam.conambl.integration.entity.CnmTCalendario;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CnmTCalendarioSpecification {

	public static Specification<CnmTCalendario> findEventsInCalendarioBy(Timestamp inizioUdienza, Timestamp fineUdienza, CnmDGruppo cnmDGruppo, Integer id) {
		return new Specification<CnmTCalendario>() {
			@Override
			public Predicate toPredicate(Root<CnmTCalendario> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();

				final Join<CnmTCalendario, CnmTUser> cnmTUserJoin = root.join("cnmTUser2");

				if (inizioUdienza != null)
					predicates.add(builder.greaterThanOrEqualTo(root.get("inizioUdienza"), inizioUdienza));

				if (fineUdienza != null)
					predicates.add(builder.lessThanOrEqualTo(root.get("fineUdienza"), fineUdienza));

				if (cnmDGruppo != null) {
					predicates.add(builder.equal(cnmTUserJoin.get("cnmDGruppo"), cnmDGruppo));
				}

				if (id != null) {
					predicates.add(builder.notEqual(root.get("idCalendario"), id));
				}

				return builder.and(predicates.toArray(new Predicate[0]));
			}

		};
	}

}
