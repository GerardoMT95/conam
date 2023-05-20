/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo;

import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
public class ProfiloVO extends ParentVO {

	private static final long serialVersionUID = -3179072238186450660L;

	private List<String> useCase;
	private String codiceFiscale;
	private String nome;
	private String cognome;
	private Long idGruppo;
	private List<EnteVO> entiAccertatore;
	private List<EnteVO> entiLegge;
	private String rai;
	private MessageVO homeMessage;

	public List<String> getUseCase() {
		return useCase;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setUseCase(List<String> useCase) {
		this.useCase = useCase;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Long getIdGruppo() {
		return idGruppo;
	}

	public void setIdGruppo(Long idGruppo) {
		this.idGruppo = idGruppo;
	}

	public List<EnteVO> getEntiAccertatore() {
		return entiAccertatore;
	}

	public List<EnteVO> getEntiLegge() {
		return entiLegge;
	}

	public void setEntiAccertatore(List<EnteVO> entiAccertatore) {
		this.entiAccertatore = entiAccertatore;
	}

	public void setEntiLegge(List<EnteVO> entiLegge) {
		this.entiLegge = entiLegge;
	}

	public String getRai() {
		return rai;
	}

	public void setRai(String rai) {
		this.rai = rai;
	}

	public MessageVO getHomeMessage() {
		return homeMessage;
	}

	public void setHomeMessage(MessageVO homeMessage) {
		this.homeMessage = homeMessage;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
