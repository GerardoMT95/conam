/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.security;

import it.csi.conam.conambl.vo.leggi.EnteVO;
import it.csi.iride2.policy.entity.Identita;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class UserDetails extends User {

	private static final long serialVersionUID = -5207688672564713789L;

	private Identita identita;
	private Long idUser;
	private Long idGruppo;
	private List<EnteVO> entiAccertatore;
	private List<EnteVO> entiLegge;
	private String rai;

	public UserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, Identita identita, Long idUser, Long idGruppo, List<EnteVO> entiAccertatore, List<EnteVO> entiLegge, String rai) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.identita = identita;
		this.idUser = idUser;
		this.entiAccertatore = entiAccertatore;
		this.entiLegge = entiLegge;
		this.idGruppo = idGruppo;
		this.rai = rai;
	}

	public Identita getIdentita() {
		return identita;
	}

	public Long getIdUser() {
		return idUser;
	}

	public List<EnteVO> getEntiAccertatore() {
		return entiAccertatore;
	}

	public List<EnteVO> getEntiLegge() {
		return entiLegge;
	}

	public void setIdentita(Identita identita) {
		this.identita = identita;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public void setEntiAccertatore(List<EnteVO> entiAccertatore) {
		this.entiAccertatore = entiAccertatore;
	}

	public void setEntiLegge(List<EnteVO> entiLegge) {
		this.entiLegge = entiLegge;
	}

	public Long getIdGruppo() {
		return idGruppo;
	}

	public void setIdGruppo(Long idGruppo) {
		this.idGruppo = idGruppo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((entiAccertatore == null) ? 0 : entiAccertatore.hashCode());
		result = prime * result + ((entiLegge == null) ? 0 : entiLegge.hashCode());
		result = prime * result + ((idGruppo == null) ? 0 : idGruppo.hashCode());
		result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
		result = prime * result + ((identita == null) ? 0 : identita.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetails other = (UserDetails) obj;
		if (entiAccertatore == null) {
			if (other.entiAccertatore != null)
				return false;
		} else if (!entiAccertatore.equals(other.entiAccertatore))
			return false;
		if (entiLegge == null) {
			if (other.entiLegge != null)
				return false;
		} else if (!entiLegge.equals(other.entiLegge))
			return false;
		if (idGruppo == null) {
			if (other.idGruppo != null)
				return false;
		} else if (!idGruppo.equals(other.idGruppo))
			return false;
		if (idUser == null) {
			if (other.idUser != null)
				return false;
		} else if (!idUser.equals(other.idUser))
			return false;
		if (identita == null) {
			if (other.identita != null)
				return false;
		} else if (!identita.equals(other.identita))
			return false;
		return true;
	}

	public String getRai() {
		return rai;
	}

	public void setRai(String rai) {
		this.rai = rai;
	}

}
