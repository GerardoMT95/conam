/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

import java.util.List;

import it.csi.conam.conambl.vo.AzioneVO;
import it.csi.conam.conambl.vo.ParentVO;

/**
 * @author riccardo.bova
 * @date 21 nov 2018
 */
public class AzioneVerbaleResponse extends ParentVO {

	private static final long serialVersionUID = 5502699540493590617L;

	private List<AzioneVO> azioneList;
	private Boolean aggiungiAllegatoEnable;
	private Boolean modificaVerbaleEnable;
	private Boolean eliminaAllegatoEnable;
	private Boolean riepilogoOrdinanzaEnable;

	public List<AzioneVO> getAzioneList() {
		return azioneList;
	}

	public Boolean getAggiungiAllegatoEnable() {
		return aggiungiAllegatoEnable;
	}

	public void setAzioneList(List<AzioneVO> azioneList) {
		this.azioneList = azioneList;
	}

	public Boolean getModificaVerbaleEnable() {
		return modificaVerbaleEnable;
	}

	public void setModificaVerbaleEnable(Boolean modificaVerbaleEnable) {
		this.modificaVerbaleEnable = modificaVerbaleEnable;
	}

	public void setAggiungiAllegatoEnable(Boolean aggiungiAllegatoEnable) {
		this.aggiungiAllegatoEnable = aggiungiAllegatoEnable;
	}

	public Boolean getEliminaAllegatoEnable() {
		return eliminaAllegatoEnable;
	}

	public void setEliminaAllegatoEnable(Boolean eliminaAllegatoEnable) {
		this.eliminaAllegatoEnable = eliminaAllegatoEnable;
	}

	public Boolean getRiepilogoOrdinanzaEnable() {
		return riepilogoOrdinanzaEnable;
	}

	public void setRiepilogoOrdinanzaEnable(Boolean riepilogoOrdinanzaEnable) {
		this.riepilogoOrdinanzaEnable = riepilogoOrdinanzaEnable;
	}

}
