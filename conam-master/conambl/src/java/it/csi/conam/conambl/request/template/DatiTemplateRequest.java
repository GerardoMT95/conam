/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.template;

import it.csi.conam.conambl.request.ParentRequest;
import it.csi.conam.conambl.vo.template.DatiTemplateCompilatiVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 09 gen 2019
 */
public class DatiTemplateRequest extends ParentRequest {

	private static final long serialVersionUID = 4146891494491345353L;

	// common
	@NotNull(message = "RESCON17")
	private Integer codiceTemplate;

	// template rateizzazione
	private Integer idPiano;

	// template lettera accompagnamento
	private Integer idOrdinanza;

	// template lettera accompagnamento
	private Integer idSollecito;

	// template convocazione audizione per utenti esterni
	private Integer idVerbale;
	
	private List<SoggettoVO> soggettoList;
	
	private List<Integer> idVerbaleSoggettoList;

	// solo in fase di stampa
	private DatiTemplateCompilatiVO datiTemplateCompilatiVO;

	public Integer getIdPiano() {
		return idPiano;
	}

	public Integer getCodiceTemplate() {
		return codiceTemplate;
	}

	public void setIdPiano(Integer idPiano) {
		this.idPiano = idPiano;
	}

	public void setCodiceTemplate(Integer codiceTemplate) {
		this.codiceTemplate = codiceTemplate;
	}

	public DatiTemplateCompilatiVO getDatiTemplateCompilatiVO() {
		return datiTemplateCompilatiVO;
	}

	public void setDatiTemplateCompilatiVO(DatiTemplateCompilatiVO datiTemplateCompilatiVO) {
		this.datiTemplateCompilatiVO = datiTemplateCompilatiVO;
	}

	public Integer getIdOrdinanza() {
		return idOrdinanza;
	}

	public void setIdOrdinanza(Integer idOrdinanza) {
		this.idOrdinanza = idOrdinanza;
	}

	public Integer getIdSollecito() {
		return idSollecito;
	}

	public void setIdSollecito(Integer idSollecito) {
		this.idSollecito = idSollecito;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public List<Integer> getIdVerbaleSoggettoList() {
		return idVerbaleSoggettoList;
	}

	public void setIdVerbaleSoggettoList(List<Integer> idVerbaleSoggettoList) {
		this.idVerbaleSoggettoList = idVerbaleSoggettoList;
	}

	public Integer getIdVerbale() {
		return idVerbale;
	}

	public void setIdVerbale(Integer idVerbale) {
		this.idVerbale = idVerbale;
	}

	public List<SoggettoVO> getSoggettoList() {
		return soggettoList;
	}

	public void setSoggettoList(List<SoggettoVO> soggettoList) {
		this.soggettoList = soggettoList;
	}

}
