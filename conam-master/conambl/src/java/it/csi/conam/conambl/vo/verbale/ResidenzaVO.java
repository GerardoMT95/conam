/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.luoghi.NazioneVO;
import it.csi.conam.conambl.vo.luoghi.ProvinciaVO;
import it.csi.conam.conambl.vo.luoghi.RegioneVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * @author Paolo Piedepalumbo
 * @date 14/09/2020
 */
public class ResidenzaVO extends ParentVO {

	private static final long serialVersionUID = 6974001856644552788L;

	private Integer id;
	private BigDecimal idStas;
	private RegioneVO regioneResidenza;
	private ComuneVO comuneResidenza;
	private ProvinciaVO provinciaResidenza;
	private String indirizzoResidenza;
	private String civicoResidenza;
	private String cap;
	private NazioneVO nazioneResidenza;
	private String denomComuneResidenzaEstero;

	private Boolean residenzaEstera;

//	private String indirizzoResidenzaStas;
//	private String civicoResidenzaStas;
//	private String capStas;

	public ResidenzaVO() {
		super();
	}

	public BigDecimal getIdStas() {
		return idStas;
	}

	public void setIdStas(BigDecimal idStas) {
		this.idStas = idStas;
	}

	public RegioneVO getRegioneResidenza() {
		return regioneResidenza;
	}

	public ComuneVO getComuneResidenza() {
		return comuneResidenza;
	}

	public ProvinciaVO getProvinciaResidenza() {
		return provinciaResidenza;
	}

	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}

	public String getCivicoResidenza() {
		return civicoResidenza;
	}

	public void setRegioneResidenza(RegioneVO regioneResidenza) {
		this.regioneResidenza = regioneResidenza;
	}

	public void setComuneResidenza(ComuneVO comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public void setProvinciaResidenza(ProvinciaVO provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}

	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}

	public void setCivicoResidenza(String civicoResidenza) {
		this.civicoResidenza = civicoResidenza;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public NazioneVO getNazioneResidenza() {
		return nazioneResidenza;
	}

	public void setNazioneResidenza(NazioneVO nazioneResidenza) {
		this.nazioneResidenza = nazioneResidenza;
	}

	public String getDenomComuneResidenzaEstero() {
		return denomComuneResidenzaEstero;
	}

	public void setDenomComuneResidenzaEstero(String denomComuneResidenzaEstero) {
		this.denomComuneResidenzaEstero = denomComuneResidenzaEstero;
	}

	public Boolean getResidenzaEstera() {
		if (residenzaEstera == null)
			return Boolean.FALSE;
		else
			return residenzaEstera;
	}

	public void setResidenzaEstera(Boolean residenzaEstera) {
		this.residenzaEstera = residenzaEstera;
	}

//	public String getIndirizzoResidenzaStas() {
//		return indirizzoResidenzaStas;
//	}
//
//	public void setIndirizzoResidenzaStas(String indirizzoResidenzaStas) {
//		this.indirizzoResidenzaStas = indirizzoResidenzaStas;
//	}
//
//	public String getCivicoResidenzaStas() {
//		return civicoResidenzaStas;
//	}
//
//	public void setCivicoResidenzaStas(String civicoResidenzaStas) {
//		this.civicoResidenzaStas = civicoResidenzaStas;
//	}
//
//	public String getCapStas() {
//		return capStas;
//	}
//
//	public void setCapStas(String capStas) {
//		this.capStas = capStas;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cap == null) ? 0 : cap.hashCode());
//		result = prime * result + ((capStas == null) ? 0 : capStas.hashCode());
		result = prime * result + ((civicoResidenza == null) ? 0 : civicoResidenza.hashCode());
//		result = prime * result + ((civicoResidenzaStas == null) ? 0 : civicoResidenzaStas.hashCode());
		result = prime * result + ((comuneResidenza == null) ? 0 : comuneResidenza.hashCode());
		result = prime * result + ((denomComuneResidenzaEstero == null) ? 0 : denomComuneResidenzaEstero.hashCode());
		result = prime * result + ((indirizzoResidenza == null) ? 0 : indirizzoResidenza.hashCode());
		result = prime * result + ((nazioneResidenza == null) ? 0 : nazioneResidenza.hashCode());
		result = prime * result + ((provinciaResidenza == null) ? 0 : provinciaResidenza.hashCode());
		result = prime * result + ((regioneResidenza == null) ? 0 : regioneResidenza.hashCode());
		result = prime * result + ((residenzaEstera == null) ? 0 : residenzaEstera.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResidenzaVO other = (ResidenzaVO) obj;
		if (cap == null) {
			if (other.cap != null)
				return false;
		} else if (!cap.equals(other.cap))
			return false;
//		if (capStas == null) {
//			if (other.capStas != null)
//				return false;
//		} else if (!capStas.equals(other.capStas))
//			return false;
		if (civicoResidenza == null) {
			if (other.civicoResidenza != null)
				return false;
		} else if (!civicoResidenza.equals(other.civicoResidenza))
			return false;
//		if (civicoResidenzaStas == null) {
//			if (other.civicoResidenzaStas != null)
//				return false;
//		} else if (!civicoResidenzaStas.equals(other.civicoResidenzaStas))
//			return false;
		if (comuneResidenza == null) {
			if (other.comuneResidenza != null)
				return false;
		} else if (!comuneResidenza.equals(other.comuneResidenza))
			return false;
		if (denomComuneResidenzaEstero == null) {
			if (other.denomComuneResidenzaEstero != null)
				return false;
		} else if (!denomComuneResidenzaEstero.equals(other.denomComuneResidenzaEstero))
			return false;
		if (indirizzoResidenza == null) {
			if (other.indirizzoResidenza != null)
				return false;
		} else if (!indirizzoResidenza.equals(other.indirizzoResidenza))
			return false;
		if (nazioneResidenza == null) {
			if (other.nazioneResidenza != null)
				return false;
		} else if (!nazioneResidenza.equals(other.nazioneResidenza))
			return false;
		if (provinciaResidenza == null) {
			if (other.provinciaResidenza != null)
				return false;
		} else if (!provinciaResidenza.equals(other.provinciaResidenza))
			return false;
		if (regioneResidenza == null) {
			if (other.regioneResidenza != null)
				return false;
		} else if (!regioneResidenza.equals(other.regioneResidenza))
			return false;
		if (residenzaEstera == null) {
			if (other.residenzaEstera != null)
				return false;
		} else if (!residenzaEstera.equals(other.residenzaEstera))
			return false;
		return true;
	}

}
