/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import it.csi.conam.conambl.vo.IstruttoreVO;
import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import it.csi.conam.conambl.vo.leggi.RiferimentiNormativiVO;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.luoghi.ProvinciaVO;
import it.csi.conam.conambl.vo.luoghi.RegioneVO;
import it.csi.conam.conambl.web.serializer.CustomDateTimeDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateTimeSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public class VerbaleVO extends ParentVO {

	private static final long serialVersionUID = -884217214905682332L;

	private Long id;
	private String numeroProtocollo;
	@NotNull(message = "VOCON01")
	private String numero;
	@NotNull(message = "VOCON02")
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private LocalDateTime dataOraViolazione;
	private EnteVO enteAccertatore;
	@NotNull(message = "VOCON03")
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private LocalDateTime dataOraAccertamento;
	@NotNull(message = "VOCON04")
	private Double importo;
	private Double importoResiduo;
	private StatoVerbaleVO stato;
	private ComuneVO comune;
	@NotNull(message = "VOCON05")
	private ProvinciaVO provincia;
	private RegioneVO regione;
	@NotNull(message = "VOCON06")
	private String indirizzo;
	private Boolean contestato;
	@NotNull(message = "VOCON07")
	private List<RiferimentiNormativiVO> riferimentiNormativi;
	private EnteVO enteRiferimentiNormativi;
	private IstruttoreVO istruttoreAssegnato;
	private IstruttoreVO istruttoreCreatore;
	private boolean pregresso;
	//Messaggio conferma per stato manuale
	private StatoManualeVO statoManuale;
	
	private ComuneVO comuneEnte;
	
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public String getNumero() {
		return numero;
	}

	public LocalDateTime getDataOraViolazione() {
		return dataOraViolazione;
	}

	public EnteVO getEnteAccertatore() {
		return enteAccertatore;
	}

	public Double getImporto() {
		return importo;
	}

	public StatoVerbaleVO getStato() {
		return stato;
	}

	public ComuneVO getComune() {
		return comune;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public Boolean getContestato() {
		return contestato;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setDataOraViolazione(LocalDateTime dataOraViolazione) {
		this.dataOraViolazione = dataOraViolazione;
	}

	public void setEnteAccertatore(EnteVO enteAccertatore) {
		this.enteAccertatore = enteAccertatore;
	}

	public LocalDateTime getDataOraAccertamento() {
		return dataOraAccertamento;
	}

	public void setDataOraAccertamento(LocalDateTime dataOraAccertamento) {
		this.dataOraAccertamento = dataOraAccertamento;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public void setStato(StatoVerbaleVO stato) {
		this.stato = stato;
	}

	public void setComune(ComuneVO comune) {
		this.comune = comune;
	}

	public void setContestato(Boolean contestato) {
		this.contestato = contestato;
	}

	public List<RiferimentiNormativiVO> getRiferimentiNormativi() {
		return riferimentiNormativi;
	}

	public void setRiferimentiNormativi(List<RiferimentiNormativiVO> riferimentiNormativi) {
		this.riferimentiNormativi = riferimentiNormativi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProvinciaVO getProvincia() {
		return provincia;
	}

	public RegioneVO getRegione() {
		return regione;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}

	public void setRegione(RegioneVO regione) {
		this.regione = regione;
	}

	public EnteVO getEnteRiferimentiNormativi() {
		return enteRiferimentiNormativi;
	}

	public void setEnteRiferimentiNormativi(EnteVO enteRiferimentiNormativi) {
		this.enteRiferimentiNormativi = enteRiferimentiNormativi;
	}

	public IstruttoreVO getIstruttoreAssegnato() {
		return istruttoreAssegnato;
	}

	public void setIstruttoreAssegnato(IstruttoreVO istruttoreAssegnato) {
		this.istruttoreAssegnato = istruttoreAssegnato;
	}
	
	public IstruttoreVO getIstruttoreCreatore() {
		return istruttoreCreatore;
	}

	public void setIstruttoreCreatore(IstruttoreVO istruttoreCreatore) {
		this.istruttoreCreatore = istruttoreCreatore;
	}

	public boolean isPregresso() {
		return pregresso;
	}

	public void setPregresso(boolean pregresso) {
		this.pregresso = pregresso;
	}

	public StatoManualeVO getStatoManuale() {
		return statoManuale;
	}

	public void setStatoManuale(StatoManualeVO statoManuale) {
		this.statoManuale = statoManuale;
	}
	
	public Double getImportoResiduo() {
		return importoResiduo;
	}

	public void setImportoResiduo(Double importoResiduo) {
		this.importoResiduo = importoResiduo;
	}

	public ComuneVO getComuneEnte() {
		return comuneEnte;
	}

	public void setComuneEnte(ComuneVO comuneEnte) {
		this.comuneEnte = comuneEnte;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
