/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.verbale;

import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.request.ParentRequest;
import it.csi.conam.conambl.vo.leggi.AmbitoVO;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import it.csi.conam.conambl.vo.leggi.NormaVO;
import it.csi.conam.conambl.vo.verbale.StatoVerbaleVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class DatiVerbaleRequest extends ParentRequest {

	private static final long serialVersionUID = -9081500001773481835L;

	private String numeroProtocollo;
	private String numeroVerbale;
	private EnteVO ente;
	private AmbitoVO ambito;
	private NormaVO norma;
	private List<StatoVerbaleVO> stato;
	private boolean pregresso;
	private List<Long> listaStatiManuali;
	private Boolean statoManualeDiCompetenza;

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public String getNumeroVerbale() {
		return numeroVerbale;
	}

	public NormaVO getNorma() {
		return norma;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public void setNumeroVerbale(String numeroVerbale) {
		this.numeroVerbale = numeroVerbale;
	}

	public void setNorma(NormaVO norma) {
		this.norma = norma;
	}

	public EnteVO getEnte() {
		return ente;
	}

	public void setEnte(EnteVO ente) {
		this.ente = ente;
	}

	public List<StatoVerbaleVO> getStato() {
		return stato;
	}

	public void setStato(List<StatoVerbaleVO> stato) {
		this.stato = stato;
	}

	public boolean isPregresso() {
		return pregresso;
	}

	public void setPregresso(boolean pregresso) {
		this.pregresso = pregresso;
	}

	public AmbitoVO getAmbito() {
		return ambito;
	}

	public void setAmbito(AmbitoVO ambito) {
		this.ambito = ambito;
	}

	public void setStatoManualeDiCompetenza(Boolean statoManualeDiCompetenza) {
		this.statoManualeDiCompetenza = statoManualeDiCompetenza;
		if (this.statoManualeDiCompetenza)
			listaStatiManuali = Constants.LISTA_STATI_MANUALI_NON_DI_COMPETENZA;
		else
			listaStatiManuali = Constants.LISTA_STATI_MANUALI_DEVOLUTI_PER_COMPETENZA;
	}

	public List<Long> getListaStatiManuali() {
		return listaStatiManuali;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
