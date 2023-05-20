/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.luoghi.NazioneVO;
import it.csi.conam.conambl.vo.luoghi.ProvinciaVO;
import it.csi.conam.conambl.vo.luoghi.RegioneVO;
import it.csi.conam.conambl.vo.ordinanza.StatoOrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.StatoSoggettoOrdinanzaVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public class SoggettoVO extends MinSoggettoVO {

	private static final long serialVersionUID = 6974001856644552788L;

	private RegioneVO regioneResidenza;
	private ComuneVO comuneResidenza;
	private ProvinciaVO provinciaResidenza;
	private String indirizzoResidenza;
	private String civicoResidenza;
	private String cap;
	private NazioneVO nazioneResidenza;
	private String denomComuneResidenzaEstero;
	private RuoloSoggettoVO ruolo;
	private BigDecimal idStas;
	private Integer idSoggettoVerbale;
	private Integer idSoggettoOrdinanza;// idOrdinanzaVerbaleSoggetto
	private String numDetOrdinanza;
	private StatoOrdinanzaVO statoOrdinanza;
	private StatoSoggettoOrdinanzaVO statoSoggettoOrdinanza;
	private boolean superatoIlMassimo;
	private String noteSoggetto;
	private Boolean residenzaEstera;

	private String indirizzoResidenzaStas;
	private String civicoResidenzaStas;
	private String capStas;

	private Boolean pianoRateizzazioneCreato;
	private Boolean verbaleAudizioneCreato;
	private Integer idAllegatoVerbaleAudizione;

	private BigDecimal importoSpeseProcessuali;
	private BigDecimal importoTitoloSanzione;

	private Boolean comuneNascitaValido = true;

	private MinVerbaleVO verbale;
	
	// 20210330_LC
	private String idPianoRateizzazione;
	
	private DatiRelataNotificaVO relataNotifica;
	
	public SoggettoVO() {
		super();
	}

	public SoggettoVO(MinSoggettoVO minSoggetto) {
		super(minSoggetto);
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

	public RuoloSoggettoVO getRuolo() {
		return ruolo;
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

	public void setRuolo(RuoloSoggettoVO ruolo) {
		this.ruolo = ruolo;
	}

	public BigDecimal getIdStas() {
		return idStas;
	}

	public void setIdStas(BigDecimal idStas) {
		this.idStas = idStas;
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

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public Integer getIdAllegatoVerbaleAudizione() {
		return idAllegatoVerbaleAudizione;
	}

	public void setIdAllegatoVerbaleAudizione(Integer idAllegatoVerbaleAudizione) {
		this.idAllegatoVerbaleAudizione = idAllegatoVerbaleAudizione;
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

	public String getIndirizzoResidenzaStas() {
		return indirizzoResidenzaStas;
	}

	public void setIndirizzoResidenzaStas(String indirizzoResidenzaStas) {
		this.indirizzoResidenzaStas = indirizzoResidenzaStas;
	}

	public String getCivicoResidenzaStas() {
		return civicoResidenzaStas;
	}

	public void setCivicoResidenzaStas(String civicoResidenzaStas) {
		this.civicoResidenzaStas = civicoResidenzaStas;
	}

	public String getCapStas() {
		return capStas;
	}

	public void setCapStas(String capStas) {
		this.capStas = capStas;
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

	public MinVerbaleVO getVerbale() {
		return verbale;
	}

	public void setVerbale(MinVerbaleVO verbale) {
		this.verbale = verbale;
	}

	public String getIdPianoRateizzazione() {
		return idPianoRateizzazione;
	}

	public void setIdPianoRateizzazione(String idPianoRateizzazione) {
		this.idPianoRateizzazione = idPianoRateizzazione;
	}

	public DatiRelataNotificaVO getRelataNotifica() {
		return relataNotifica;
	}

	public void setRelataNotifica(DatiRelataNotificaVO relataNotifica) {
		this.relataNotifica = relataNotifica;
	}

}
