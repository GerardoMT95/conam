/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale.allegato;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.web.serializer.CustomDateTimeDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateTimeSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.time.LocalDateTime;

/**
 * @author riccardo.bova
 * @date 16 nov 2018
 */
public class AllegatoVO extends ParentVO {

	private static final long serialVersionUID = -7714298531166937914L;

	private Integer id;
	private String nome;
	private TipoAllegatoVO tipo;
	private String numProtocollo;
	private String idActa;
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private LocalDateTime dataOraCaricamento;
	private String utente;
	private String numeroDeterminazioneOrdinanza;
	private Long idCategoria;// 1,2,3
	private String nomeCognomeRagioneSocialeSoggetti;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoAllegatoVO getTipo() {
		return tipo;
	}

	public void setTipo(TipoAllegatoVO tipo) {
		this.tipo = tipo;
	}

	public String getNumProtocollo() {
		return numProtocollo;
	}

	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}

	public LocalDateTime getDataOraCaricamento() {
		return dataOraCaricamento;
	}

	public void setDataOraCaricamento(LocalDateTime dataOraCaricamento) {
		this.dataOraCaricamento = dataOraCaricamento;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public String getNumeroDeterminazioneOrdinanza() {
		return numeroDeterminazioneOrdinanza;
	}

	public void setNumeroDeterminazioneOrdinanza(String numeroDeterminazioneOrdinanza) {
		this.numeroDeterminazioneOrdinanza = numeroDeterminazioneOrdinanza;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNomeCognomeRagioneSocialeSoggetti() {
		return nomeCognomeRagioneSocialeSoggetti;
	}

	public void setNomeCognomeRagioneSocialeSoggetti(String nomeCognomeRagioneSocialeSoggetti) {
		this.nomeCognomeRagioneSocialeSoggetti = nomeCognomeRagioneSocialeSoggetti;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getIdActa() {
		return idActa;
	}

	public void setIdActa(String idActa) {
		this.idActa = idActa;
	}

}
