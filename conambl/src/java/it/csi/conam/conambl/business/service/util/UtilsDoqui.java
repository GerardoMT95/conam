/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.util;

import it.csi.conam.conambl.common.exception.FileP7MNotSignedException;
import it.csi.conam.conambl.integration.entity.*;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 15 mar 2019
 */
public interface UtilsDoqui {

	String createOrGetfolder(CnmTVerbale cnmTVerbale);

	String createOrGetfolder(CnmROrdinanzaVerbSog CnmROrdinanzaVerbSog);

	String createOrGetfolder(CnmTOrdinanza cnmTOrdinanza);

	String createOrGetfolder(CnmTPianoRate cnmTPianoRate);

	String createOrGetfolder(CnmTSollecito cnmTSollecito);

	String createOrGetfolder(CnmRVerbaleSoggetto cnmRVerbaleSoggetto);

	String createIdEntitaFruitore(CnmTVerbale cnmTVerbale, CnmDTipoAllegato cnmDTipoAllegato);

	String createIdEntitaFruitore(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog, CnmDTipoAllegato cnmDTipoAllegato);

	String createIdEntitaFruitore(CnmTOrdinanza cnmTOrdinanza, CnmDTipoAllegato cnmDTipoAllegato);

	String createIdEntitaFruitore(CnmRVerbaleSoggetto cnmRVerbaleSoggetto, CnmDTipoAllegato cnmDTipoAllegato);

	String createIdEntitaFruitore(CnmTPianoRate cnmTPianoRate, CnmDTipoAllegato cnmDTipoAllegato);

	String createIdEntitaFruitore(CnmTSollecito cnmTSollecito, CnmDTipoAllegato cnmDTipoAllegato);

	String getSoggettoActa(CnmTVerbale cnmTVerbale);

	String getSoggettoActa(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog);

	String getSoggettoActa(CnmTOrdinanza cnmTOrdinanza);

	String getSoggettoActa(CnmRVerbaleSoggetto cnmRVerbaleSoggetto);

	String getSoggettoActa(CnmTPianoRate cnmTPianoRate);

	String getSoggettoActa(CnmTSollecito cnmTSollecito);

	String getRootActa(CnmTVerbale cnmTVerbale);

	String getRootActa(CnmRVerbaleSoggetto cnmRVerbaleSoggetto);

	String getRootActa(CnmTSollecito cnmTSollecito);

	String getRootActa(CnmTPianoRate cnmTPianoRate);

	String getRootActa(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog);

	String getRootActa(CnmTOrdinanza cnmTOrdinanza);

	String getMimeType(String fileName);

	String getClassificazione(CnmTPianoRate dto);

	String getClassificazione(CnmTOrdinanza dto);

	String getClassificazione(CnmTSollecito dto);

	String getClassificazione(CnmRVerbaleSoggetto cnmRVerbaleSoggetto);

	String getFn(CnmTPianoRate dto);

	String getFn(CnmTOrdinanza dto);

	String getFn(CnmTSollecito dto);

	String getFn(CnmRVerbaleSoggetto cnmRVerbaleSoggetto);
	
	// 20200804_LC per multitipo
	String createIdEntitaFruitore(CnmTVerbale cnmTVerbale, List<CnmTAllegato> cnmTAllegatoMultiTipoList);
	
	// 20201026 PP- serve a controllare se un file e' firmato e se le firme sono valide
	public void checkFileSign(byte[] document, String nomeFile) throws FileP7MNotSignedException ;
	
	String createIdEntitaFruitoreScrittoDifensivo(CnmTScrittoDifensivo cnmTScrittoDifensivo);	
	
	String createOrGetfolderScrittoDifensivo();
	
	String getSoggettoActaScrittoDifensivo(CnmTScrittoDifensivo cnmTScrittoDifensivo);
	
	String getRootActaScrittoDifensivo(CnmTScrittoDifensivo cnmTScrittoDifensivo);
}
