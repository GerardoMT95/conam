/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import it.csi.conam.conambl.vo.ordinanza.StatoOrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.StatoSoggettoOrdinanzaVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Paolo Piedeplaumbo
 * @date 14/09/2020
 */
public class SoggettoPregressiVO extends MinSoggettoVO {

	private static final long serialVersionUID = 6974001856644552788L;

	private RuoloSoggettoVO ruolo;
	private Integer id;
	private Integer idSoggettoVerbale;
	private Integer idSoggettoOrdinanza;// idOrdinanzaVerbaleSoggetto
	private String numDetOrdinanza;
	private StatoOrdinanzaVO statoOrdinanza;
	private StatoSoggettoOrdinanzaVO statoSoggettoOrdinanza;
	private boolean superatoIlMassimo;
	private String noteSoggetto;
	
	private Boolean pianoRateizzazioneCreato;
	private Boolean verbaleAudizioneCreato;
	private Integer idAllegatoVerbaleAudizione;

	private BigDecimal importoSpeseProcessuali;
	private BigDecimal importoTitoloSanzione;

	private Boolean comuneNascitaValido = true;

	private List<ResidenzaVO> residenzaList= new ArrayList<ResidenzaVO>();
	
	public SoggettoPregressiVO() {
		super();
	}

	public SoggettoPregressiVO(MinSoggettoVO minSoggetto) {
		super(minSoggetto);
	}

	public RuoloSoggettoVO getRuolo() {
		return ruolo;
	}

	public void setRuolo(RuoloSoggettoVO ruolo) {
		this.ruolo = ruolo;
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

	public Integer getIdSoggettoVerbale() {
		return idSoggettoVerbale;
	}

	public void setIdSoggettoVerbale(Integer idSoggettoVerbale) {
		this.idSoggettoVerbale = idSoggettoVerbale;
	}

	public Integer getIdSoggettoOrdinanza() {
		return idSoggettoOrdinanza;
	}

	public void setIdSoggettoOrdinanza(Integer idSoggettoOrdinanza) {
		this.idSoggettoOrdinanza = idSoggettoOrdinanza;
	}

	public StatoSoggettoOrdinanzaVO getStatoSoggettoOrdinanza() {
		return statoSoggettoOrdinanza;
	}

	public void setStatoSoggettoOrdinanza(StatoSoggettoOrdinanzaVO statoSoggettoOrdinanza) {
		this.statoSoggettoOrdinanza = statoSoggettoOrdinanza;
	}

	public String getNoteSoggetto() {
		return noteSoggetto;
	}

	public void setNoteSoggetto(String noteSoggetto) {
		this.noteSoggetto = noteSoggetto;
	}

	public Boolean getPianoRateizzazioneCreato() {
		return pianoRateizzazioneCreato;
	}

	public void setPianoRateizzazioneCreato(Boolean pianoRateizzazioneCreato) {
		this.pianoRateizzazioneCreato = pianoRateizzazioneCreato;
	}

	public BigDecimal getImportoSpeseProcessuali() {
		return importoSpeseProcessuali;
	}

	public void setImportoSpeseProcessuali(BigDecimal importoSpeseProcessuali) {
		this.importoSpeseProcessuali = importoSpeseProcessuali;
	}

	public BigDecimal getImportoTitoloSanzione() {
		return importoTitoloSanzione;
	}

	public void setImportoTitoloSanzione(BigDecimal importoTitoloSanzione) {
		this.importoTitoloSanzione = importoTitoloSanzione;
	}

	public Boolean getVerbaleAudizioneCreato() {
		return verbaleAudizioneCreato;
	}

	public void setVerbaleAudizioneCreato(Boolean verbaleAudizioneCreato) {
		this.verbaleAudizioneCreato = verbaleAudizioneCreato;
	}

	public Integer getIdAllegatoVerbaleAudizione() {
		return idAllegatoVerbaleAudizione;
	}

	public void setIdAllegatoVerbaleAudizione(Integer idAllegatoVerbaleAudizione) {
		this.idAllegatoVerbaleAudizione = idAllegatoVerbaleAudizione;
	}

	public String getNumDetOrdinanza() {
		return numDetOrdinanza;
	}

	public void setNumDetOrdinanza(String numDetOrdinanza) {
		this.numDetOrdinanza = numDetOrdinanza;
	}

	public StatoOrdinanzaVO getStatoOrdinanza() {
		return statoOrdinanza;
	}

	public void setStatoOrdinanza(StatoOrdinanzaVO statoOrdinanza) {
		this.statoOrdinanza = statoOrdinanza;
	}

	public boolean isSuperatoIlMassimo() {
		return superatoIlMassimo;
	}

	public void setSuperatoIlMassimo(boolean superatoIlMassimo) {
		this.superatoIlMassimo = superatoIlMassimo;
	}

	public Boolean getComuneNascitaValido() {
		return comuneNascitaValido;
	}

	public void setComuneNascitaValido(Boolean comuneNascitaValido) {
		this.comuneNascitaValido = comuneNascitaValido;
	}

	public List<ResidenzaVO> getResidenzaList() {
		return residenzaList;
	}

	public void setResidenzaList(List<ResidenzaVO> residenzaList) {
		this.residenzaList = residenzaList;
	}

	public void addResidenza(ResidenzaVO residenza) {
		if(!residenzaList.contains(residenza)) {
			residenzaList.add(residenza);
		}
	}
}
