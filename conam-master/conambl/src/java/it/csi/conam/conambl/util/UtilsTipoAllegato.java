/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.util;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;

/**
 * @author riccardo.bova
 * @date 29 gen 2019
 */
public class UtilsTipoAllegato {

	public static Predicate<CnmRAllegatoOrdinanza> findCnmRAllegatoOrdinanzaInCnmRAllegatoOrdinanzasByTipoAllegato(TipoAllegato tipoAllegato) {
		return new Predicate<CnmRAllegatoOrdinanza>() {
			@Override
			public boolean apply(CnmRAllegatoOrdinanza input) {
				return input.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == tipoAllegato.getId();
			}
		};
	}

	public static Predicate<CnmRAllegatoOrdVerbSog> findCnmRAllegatoOrdVerbSogInCnmRAllegatoOrdVerbSogsByTipoAllegato(TipoAllegato tipoAllegato) {
		return new Predicate<CnmRAllegatoOrdVerbSog>() {
			@Override
			public boolean apply(CnmRAllegatoOrdVerbSog input) {
				return input.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == tipoAllegato.getId();
			}
		};
	}

	public static Predicate<CnmTAllegato> findCnmTAllegatoInCnmTAllegatosByTipoAllegato(TipoAllegato tipoAllegato) {
		return new Predicate<CnmTAllegato>() {
			@Override
			public boolean apply(CnmTAllegato input) {
				return input.getCnmDTipoAllegato().getIdTipoAllegato() == tipoAllegato.getId();
			}
		};
	}

	public static Predicate<CnmRAllegatoPianoRate> findCnmRAllegatoPianoRateInCnmRAllegatoPianoRatesByTipoAllegato(TipoAllegato tipoAllegato) {
		return new Predicate<CnmRAllegatoPianoRate>() {
			@Override
			public boolean apply(CnmRAllegatoPianoRate input) {
				return input.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == tipoAllegato.getId();
			}
		};
	}

	public static Predicate<CnmRAllegatoVerbale> findAllegatoInCnmRAllegatoVerbaleByTipoAllegato(TipoAllegato tipoAllegato) {
		return new Predicate<CnmRAllegatoVerbale>() {
			@Override
			public boolean apply(CnmRAllegatoVerbale input) {
				return input.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == tipoAllegato.getId();
			}
		};
	}

	public static Predicate<CnmRAllegatoSollecito> findAllegatoInCnmRAllegatoSollecitoByTipoAllegato(TipoAllegato tipoAllegato) {
		return new Predicate<CnmRAllegatoSollecito>() {
			@Override
			public boolean apply(CnmRAllegatoSollecito input) {
				return input.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == tipoAllegato.getId();
			}
		};
	}

	public static Predicate<TipoAllegatoVO> findAllegatoInTipoAllegatiByIdTipoAllegato(Long idTipoAllegato) {
		return new Predicate<TipoAllegatoVO>() {
			public boolean apply(TipoAllegatoVO tipoAllegatoVO) {
				return idTipoAllegato.equals(tipoAllegatoVO.getId());
			}
		};
	}

	public static Function<TipoAllegatoVO, Long> converTipoAllegatoToLong() {
		return new Function<TipoAllegatoVO, Long>() {
			@Override
			public Long apply(TipoAllegatoVO input) {
				return input.getId();
			}
		};
	}
}
