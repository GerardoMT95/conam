/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.specification;

import it.csi.conam.conambl.integration.entity.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CnmDTipoAllegatoSpecification {

	public static Specification<CnmDTipoAllegato> findBy(Long idTipoAllegato, Long idCategoriaAllegato, Long idUtilizzoAllegato, CnmDStatoVerbale cnmDStatoVerbale,
			CnmDStatoOrdinanza cnmDStatoOrdinanza) {
		return new Specification<CnmDTipoAllegato>() {
			@Override
			public Predicate toPredicate(Root<CnmDTipoAllegato> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();
				final Join<CnmDTipoAllegato, CnmDCategoriaAllegato> cnmDCategoriaAllegatoJoin = root.join("cnmDCategoriaAllegato");
				final Join<CnmDTipoAllegato, CnmDUtilizzoAllegato> cnmDUtilizzoAllegatoJoin = root.join("cnmDUtilizzoAllegato");

				if (idCategoriaAllegato != null)
					predicates.add(builder.equal(cnmDCategoriaAllegatoJoin.get("idCategoriaAllegato"), idCategoriaAllegato));

				if (idUtilizzoAllegato != null)
					predicates.add(builder.equal(cnmDUtilizzoAllegatoJoin.get("idUtilizzoAllegato"), idUtilizzoAllegato));

				if (cnmDStatoVerbale != null) {
					predicates.add(builder.equal(root.join("cnmDStatoVerbales"), cnmDStatoVerbale));
				}
				if (cnmDStatoOrdinanza != null) {
					predicates.add(builder.equal(root.join("cnmDStatoOrdinanzas"), cnmDStatoOrdinanza));
				}
				if (idTipoAllegato != null) {
					predicates.add(builder.equal(root.get("idTipoAllegato"), idTipoAllegato));
				}

				return builder.and(predicates.toArray(new Predicate[0]));
			}

		};
	}

}
