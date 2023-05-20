/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.CnmRSoggRata;
import it.csi.conam.conambl.integration.entity.CnmTRata;
import it.csi.conam.conambl.vo.pianorateizzazione.RataVO;

import java.util.Collection;
import java.util.List;

public class UtilsRata {
	
	public static String formattaCodiceAvvisoPerLaVisualizzazione(String codiceAvviso){
		String s = "";
		if(codiceAvviso != null && codiceAvviso.length()>16){
			s = codiceAvviso.substring(0, 4) +  " " + codiceAvviso.substring(4, 8) + " "	+ 
				 codiceAvviso.substring(8, 12)+ " " + codiceAvviso.substring(12, 16) + " " + codiceAvviso.substring(16, codiceAvviso.length());
		}		
		return s;
	}

	public static Collection<RataVO> filtraRateModificabili(List<RataVO> rate, Integer numRate) {
		return Collections2.filter(rate, new Predicate<RataVO>() {
			@Override
			public boolean apply(RataVO input) {
				int numeroRata = input.getNumeroRata().intValue();
				return numeroRata == 1 || numeroRata == numRate;
			}
		});
	}

	public static RataVO findRata(Collection<RataVO> rate, Predicate<RataVO> predicate) {
		return Iterables.tryFind(rate, predicate).orNull();
	}

	public static CnmTRata findCnmTRata(Collection<CnmTRata> rate, Predicate<CnmTRata> predicate) {
		return Iterables.tryFind(rate, predicate).orNull();
	}

	public static Predicate<CnmTRata> numCnmTRataPredicate(int numRata) {
		return new Predicate<CnmTRata>() {
			@Override
			public boolean apply(CnmTRata input) {
				return input.getNumeroRata().intValue() == numRata;
			}
		};
	}

	public static Predicate<RataVO> numRataPredicate(int numRata) {
		return new Predicate<RataVO>() {
			@Override
			public boolean apply(RataVO input) {
				return input.getNumeroRata().intValue() == numRata;
			}
		};
	}

	public static Predicate<CnmRSoggRata> filtraRatePagate() {
		return new Predicate<CnmRSoggRata>() {
			@Override
			public boolean apply(CnmRSoggRata input) {
				long idStatoRata = input.getCnmDStatoRata().getIdStatoRata();
				return !(idStatoRata == Constants.ID_STATO_RATA_PAGATO_OFFLINE || idStatoRata == Constants.ID_STATO_RATA_PAGATO_ONLINE || idStatoRata == Constants.ID_STATO_RATA_ESTINTO);
			}
		};
	}

}
