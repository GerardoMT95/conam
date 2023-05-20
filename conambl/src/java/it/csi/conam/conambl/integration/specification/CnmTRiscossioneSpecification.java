/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.specification;

import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.web.serializer.CustomDateTimeSerializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CnmTRiscossioneSpecification {

	public static Specification<CnmTRiscossione> findBy(//
			List<CnmDStatoRiscossione> cnmDStatoRiscossiones, //
			boolean isRicercaStorica, //
			String codiceFiscaleGiuridico, //
			String codiceFiscaleFisico, //
			String numeroDeterminazioneOrdinanza) {
		return new Specification<CnmTRiscossione>() {
			@Override
			public Predicate toPredicate(Root<CnmTRiscossione> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();

				// campi ricerca non storica
				if (!isRicercaStorica) {
					LocalDateTime now = LocalDateTime.now();
					LocalDateTime localDateTime = LocalDateTime.of(now.getDayOfYear() - 1, 1, 1, 0, 0);
					// gestire data invio
					predicates.add(builder.or(
							builder.greaterThanOrEqualTo(root.get("dataOraUpdate"),
									new Timestamp(Date.from(localDateTime.atZone(CustomDateTimeSerializer.LOCAL_TIME_ZONE.toZoneId()).toInstant()).getTime())),
							builder.isNull(root.get("dataOraUpdate"))));
				}
				if (cnmDStatoRiscossiones != null) {
					predicates.add(root.get("cnmDStatoRiscossione").in(cnmDStatoRiscossiones));
				}

				if (StringUtils.isNotEmpty(codiceFiscaleFisico) || StringUtils.isNotEmpty(codiceFiscaleGiuridico)) {
					final Join<CnmTRiscossione, CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogJoin = root.join("cnmROrdinanzaVerbSog");
					final Join<CnmROrdinanzaVerbSog, CnmRVerbaleSoggetto> cnmRVerbaleSogJoin = cnmROrdinanzaVerbSogJoin.join("cnmRVerbaleSoggetto");
					final Join<CnmRVerbaleSoggetto, CnmTSoggetto> cnmTSoggettoJoin = cnmRVerbaleSogJoin.join("cnmTSoggetto");
					if (StringUtils.isNotEmpty(codiceFiscaleFisico))
						predicates.add(builder.equal(cnmTSoggettoJoin.get("codiceFiscale"), codiceFiscaleFisico));
					if (StringUtils.isNotEmpty(codiceFiscaleGiuridico))
						predicates.add(builder.equal(cnmTSoggettoJoin.get("codiceFiscaleGiuridico"), codiceFiscaleGiuridico));
				}

				if (StringUtils.isNotEmpty(numeroDeterminazioneOrdinanza)) {
					final Join<CnmTRiscossione, CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogJoin = root.join("cnmROrdinanzaVerbSog");
					final Join<CnmROrdinanzaVerbSog, CnmTOrdinanza> cnmTOrdinanzaJoin = cnmROrdinanzaVerbSogJoin.join("cnmTOrdinanza");
					predicates.add(builder.equal(cnmTOrdinanzaJoin.get("numDeterminazione"), numeroDeterminazioneOrdinanza));
				}

				return builder.and(predicates.toArray(new Predicate[0]));
			}

		};
	}

}
