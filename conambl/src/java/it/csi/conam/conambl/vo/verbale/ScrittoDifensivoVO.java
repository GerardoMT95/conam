/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.leggi.AmbitoVO;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import it.csi.conam.conambl.vo.leggi.RiferimentiNormativiVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.web.serializer.CustomDateTimeDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateTimeSerializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.time.LocalDateTime;

public class ScrittoDifensivoVO extends ParentVO {

	private static final long serialVersionUID = 6974001856644552788L;

	private Integer id;
	private String nomeFile;
	private String numVerbaleAccertamento;
	private String nome;
	private String cognome;
	private String ragioneSociale;
	private String numeroProtocollo;
	private Boolean flagAssociato;

	private AllegatoVO allegato;
	private AmbitoVO ambito;
	private EnteVO enteRiferimentiNormativi;

	private RiferimentiNormativiVO riferimentoNormativo;

	private ModalitaCaricamentoVO modalitaCaricamento;

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private LocalDateTime dataProtocollo;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getNumVerbaleAccertamento() {
		return numVerbaleAccertamento;
	}
	public void setNumVerbaleAccertamento(String numVerbaleAccertamento) {
		this.numVerbaleAccertamento = numVerbaleAccertamento;
	}
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	public AllegatoVO getAllegato() {
		return allegato;
	}
	public void setAllegato(AllegatoVO allegato) {
		this.allegato = allegato;
	}
	public AmbitoVO getAmbito() {
		return ambito;
	}
	public void setAmbito(AmbitoVO ambito) {
		this.ambito = ambito;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getRagioneSociale() {
		return ragioneSociale;
	}
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	public Boolean getFlagAssociato() {
		return flagAssociato;
	}
	public void setFlagAssociato(Boolean flagAssociato) {
		this.flagAssociato = flagAssociato;
	}
	public EnteVO getEnteRiferimentiNormativi() {
		return enteRiferimentiNormativi;
	}
	public void setEnteRiferimentiNormativi(EnteVO enteRiferimentiNormativi) {
		this.enteRiferimentiNormativi = enteRiferimentiNormativi;
	}
	public RiferimentiNormativiVO getRiferimentoNormativo() {
		return riferimentoNormativo;
	}
	public void setRiferimentoNormativo(RiferimentiNormativiVO riferimentoNormativo) {
		this.riferimentoNormativo = riferimentoNormativo;
	}
	public LocalDateTime getDataProtocollo() {
		return dataProtocollo;
	}
	public void setDataProtocollo(LocalDateTime dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	public ModalitaCaricamentoVO getModalitaCaricamento() {
		return modalitaCaricamento;
	}
	public void setModalitaCaricamento(ModalitaCaricamentoVO modalitaCaricamento) {
		this.modalitaCaricamento = modalitaCaricamento;
	}


	



}
