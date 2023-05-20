/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import it.csi.conam.conambl.integration.doqui.entity.CnmTDocumento;
import it.csi.conam.conambl.integration.doqui.entity.CnmTRichiesta;
import it.csi.conam.conambl.integration.entity.custom.CnmCReport;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the cnm_t_user database table.
 * 
 */
@Entity
@Table(name = "cnm_t_user")
@NamedQuery(name = "CnmTUser.findAll", query = "SELECT c FROM CnmTUser c")
public class CnmTUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private long idUser;

	@Column(name = "codice_fiscale")
	private String codiceFiscale;

	@Temporal(TemporalType.DATE)
	@Column(name = "fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name = "inizio_validita")
	private Date inizioValidita;

	private String nome;
	private String cognome;

	// bi-directional many-to-one association to CnmCReport
	@OneToMany(mappedBy = "cnmTUser")
	private List<CnmCReport> cnmCReports;

	// bi-directional many-to-one association to CnmDAmbito
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmDAmbito> cnmDAmbitos1;

	// bi-directional many-to-one association to CnmDAmbito
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmDAmbito> cnmDAmbitos2;

	// bi-directional many-to-one association to CnmDArticolo
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmDArticolo> cnmDArticolos1;

	// bi-directional many-to-one association to CnmDArticolo
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmDArticolo> cnmDArticolos2;

	// bi-directional many-to-one association to CnmDComma
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmDComma> cnmDCommas1;

	// bi-directional many-to-one association to CnmDComma
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmDComma> cnmDCommas2;

	// bi-directional many-to-one association to CnmDLettera
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmDLettera> cnmDLetteras1;

	// bi-directional many-to-one association to CnmDLettera
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmDLettera> cnmDLetteras2;

	// bi-directional many-to-one association to CnmDNorma
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmDNorma> cnmDNormas1;

	// bi-directional many-to-one association to CnmDNorma
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmDNorma> cnmDNormas2;

	// bi-directional many-to-one association to CnmRAllegatoOrdVerbSog
	@OneToMany(mappedBy = "cnmTUser")
	private List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogs;

	// bi-directional many-to-one association to CnmRAllegatoOrdinanza
	@OneToMany(mappedBy = "cnmTUser")
	private List<CnmRAllegatoOrdinanza> cnmRAllegatoOrdinanzas;

	// bi-directional many-to-one association to CnmRAllegatoPianoRate
	@OneToMany(mappedBy = "cnmTUser")
	private List<CnmRAllegatoPianoRate> cnmRAllegatoPianoRates;

	// bi-directional many-to-one association to CnmRAllegatoSollecito
	@OneToMany(mappedBy = "cnmTUser")
	private List<CnmRAllegatoSollecito> cnmRAllegatoSollecitos;

	// bi-directional many-to-one association to CnmRAllegatoVerbSog
	@OneToMany(mappedBy = "cnmTUser")
	private List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs;

	// bi-directional many-to-one association to CnmRAllegatoVerbale
	@OneToMany(mappedBy = "cnmTUser")
	private List<CnmRAllegatoVerbale> cnmRAllegatoVerbales;

	// bi-directional many-to-one association to CnmREnteNorma
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmREnteNorma> cnmREnteNormas1;

	// bi-directional many-to-one association to CnmREnteNorma
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmREnteNorma> cnmREnteNormas2;

	// bi-directional many-to-one association to CnmRFunzionarioIstruttore
	@OneToMany(mappedBy = "cnmTUser")
	private List<CnmRFunzionarioIstruttore> cnmRFunzionarioIstruttores;

	// bi-directional many-to-one association to CnmROrdinanzaVerbSog
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs1;

	// bi-directional many-to-one association to CnmROrdinanzaVerbSog
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs2;

	// bi-directional many-to-one association to CnmRSoggRata
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmRSoggRata> cnmRSoggRatas1;

	// bi-directional many-to-one association to CnmRSoggRata
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmRSoggRata> cnmRSoggRatas2;

	// bi-directional many-to-one association to CnmRUserEnte
	@OneToMany(mappedBy = "cnmTUser")
	private List<CnmRUserEnte> cnmRUserEntes;

	// bi-directional many-to-one association to CnmRVerbaleIllecito
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmRVerbaleIllecito> cnmRVerbaleIllecitos1;

	// bi-directional many-to-one association to CnmRVerbaleIllecito
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmRVerbaleIllecito> cnmRVerbaleIllecitos2;

	// bi-directional many-to-one association to CnmRVerbaleSoggetto
	@OneToMany(mappedBy = "cnmTUser")
	private List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettos;

	// bi-directional many-to-one association to CnmSStatoOrdinanza
	@OneToMany(mappedBy = "cnmTUser")
	private List<CnmSStatoOrdinanza> cnmSStatoOrdinanzas;

	// bi-directional many-to-one association to CnmSStatoVerbale
	@OneToMany(mappedBy = "cnmTUser")
	private List<CnmSStatoVerbale> cnmSStatoVerbales;

	// bi-directional many-to-one association to CnmTAllegato
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTAllegato> cnmTAllegatos1;

	// bi-directional many-to-one association to CnmTAllegato
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTAllegato> cnmTAllegatos2;

	// bi-directional many-to-one association to CnmTAllegatoField
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTAllegatoField> cnmTAllegatoFields1;

	// bi-directional many-to-one association to CnmTAllegatoField
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTAllegatoField> cnmTAllegatoFields2;

	// bi-directional many-to-one association to CnmTCalendario
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTCalendario> cnmTCalendarios1;

	// bi-directional many-to-one association to CnmTCalendario
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTCalendario> cnmTCalendarios2;

	// bi-directional many-to-one association to CnmTNotifica
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTNotifica> cnmTNotificas1;

	// bi-directional many-to-one association to CnmTNotifica
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTNotifica> cnmTNotificas2;

	// bi-directional many-to-one association to CnmTOrdinanza
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTOrdinanza> cnmTOrdinanzas1;

	// bi-directional many-to-one association to CnmTOrdinanza
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTOrdinanza> cnmTOrdinanzas2;

	// bi-directional many-to-one association to CnmTPersona
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTPersona> cnmTPersonas1;

	// bi-directional many-to-one association to CnmTPersona
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTPersona> cnmTPersonas2;

	// bi-directional many-to-one association to CnmTPianoRate
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTPianoRate> cnmTPianoRates1;

	// bi-directional many-to-one association to CnmTPianoRate
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTPianoRate> cnmTPianoRates2;

	// bi-directional many-to-one association to CnmTRata
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTRata> cnmTRatas1;

	// bi-directional many-to-one association to CnmTRata
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTRata> cnmTRatas2;

	// bi-directional many-to-one association to CnmTRiscossione
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTRiscossione> cnmTRiscossiones1;

	// bi-directional many-to-one association to CnmTRiscossione
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTRiscossione> cnmTRiscossiones2;

	// bi-directional many-to-one association to CnmTSocieta
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTSocieta> cnmTSocietas1;

	// bi-directional many-to-one association to CnmTSocieta
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTSocieta> cnmTSocietas2;

	// bi-directional many-to-one association to CnmTSoggetto
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTSoggetto> cnmTSoggettos1;

	// bi-directional many-to-one association to CnmTSoggetto
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTSoggetto> cnmTSoggettos2;

	// bi-directional many-to-one association to CnmTSollecito
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTSollecito> cnmTSollecitos1;

	// bi-directional many-to-one association to CnmTSollecito
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTSollecito> cnmTSollecitos2;

	// bi-directional many-to-one association to CnmDGruppo
	@ManyToOne
	@JoinColumn(name = "id_gruppo")
	private CnmDGruppo cnmDGruppo;

	// bi-directional many-to-one association to CnmDRuolo
	@ManyToOne
	@JoinColumn(name = "id_ruolo")
	private CnmDRuolo cnmDRuolo;

	// bi-directional many-to-one association to CnmTVerbale
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTVerbale> cnmTVerbales1;

	// bi-directional many-to-one association to CnmTVerbale
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTVerbale> cnmTVerbales2;


	// bi-directional many-to-one association to CnmTScrittoDifensivo
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTScrittoDifensivo> cnmTScrittoDifensivos1;

	// bi-directional many-to-one association to CnmTScrittoDifensivo
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTScrittoDifensivo> cnmTScrittoDifensivos2;


	// bi-directional many-to-one association to CnmRScrittoIllecito
//	@OneToMany(mappedBy = "cnmTUser1")
//	private List<CnmRScrittoIllecito> cnmRScrittoIllecitos1;

	// bi-directional many-to-one association to CnmRScrittoIllecito
//	@OneToMany(mappedBy = "cnmTUser2")
//	private List<CnmRScrittoIllecito> cnmRScrittoIllecitos2;
	
	
	// 20200702_LC

	// bi-directional many-to-one association to CnmTDocumento
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTDocumento> cnmTDocumentos1;

	// bi-directional many-to-one association to CnmTDocumento
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTDocumento> cnmTDocumentos2;

	// bi-directional many-to-one association to CnmTRichiesta
	@OneToMany(mappedBy = "cnmTUser1")
	private List<CnmTRichiesta> cnmTRichiestas1;

	// bi-directional many-to-one association to CnmTRichiesta
	@OneToMany(mappedBy = "cnmTUser2")
	private List<CnmTRichiesta> cnmTRichiestas2;
	
	
	
	// --
	
	

	public CnmTUser() {
	}

	public long getIdUser() {
		return this.idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getFineValidita() {
		return this.fineValidita;
	}

	public void setFineValidita(Date fineValidita) {
		this.fineValidita = fineValidita;
	}

	public Date getInizioValidita() {
		return this.inizioValidita;
	}

	public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<CnmCReport> getCnmCReports() {
		return this.cnmCReports;
	}

	public void setCnmCReports(List<CnmCReport> cnmCReports) {
		this.cnmCReports = cnmCReports;
	}

	public CnmCReport addCnmCReport(CnmCReport cnmCReport) {
		getCnmCReports().add(cnmCReport);
		cnmCReport.setCnmTUser(this);

		return cnmCReport;
	}

	public CnmCReport removeCnmCReport(CnmCReport cnmCReport) {
		getCnmCReports().remove(cnmCReport);
		cnmCReport.setCnmTUser(null);

		return cnmCReport;
	}

	public List<CnmDAmbito> getCnmDAmbitos1() {
		return this.cnmDAmbitos1;
	}

	public void setCnmDAmbitos1(List<CnmDAmbito> cnmDAmbitos1) {
		this.cnmDAmbitos1 = cnmDAmbitos1;
	}

	public CnmDAmbito addCnmDAmbitos1(CnmDAmbito cnmDAmbitos1) {
		getCnmDAmbitos1().add(cnmDAmbitos1);
		cnmDAmbitos1.setCnmTUser1(this);

		return cnmDAmbitos1;
	}

	public CnmDAmbito removeCnmDAmbitos1(CnmDAmbito cnmDAmbitos1) {
		getCnmDAmbitos1().remove(cnmDAmbitos1);
		cnmDAmbitos1.setCnmTUser1(null);

		return cnmDAmbitos1;
	}

	public List<CnmDAmbito> getCnmDAmbitos2() {
		return this.cnmDAmbitos2;
	}

	public void setCnmDAmbitos2(List<CnmDAmbito> cnmDAmbitos2) {
		this.cnmDAmbitos2 = cnmDAmbitos2;
	}

	public CnmDAmbito addCnmDAmbitos2(CnmDAmbito cnmDAmbitos2) {
		getCnmDAmbitos2().add(cnmDAmbitos2);
		cnmDAmbitos2.setCnmTUser2(this);

		return cnmDAmbitos2;
	}

	public CnmDAmbito removeCnmDAmbitos2(CnmDAmbito cnmDAmbitos2) {
		getCnmDAmbitos2().remove(cnmDAmbitos2);
		cnmDAmbitos2.setCnmTUser2(null);

		return cnmDAmbitos2;
	}

	public List<CnmDArticolo> getCnmDArticolos1() {
		return this.cnmDArticolos1;
	}

	public void setCnmDArticolos1(List<CnmDArticolo> cnmDArticolos1) {
		this.cnmDArticolos1 = cnmDArticolos1;
	}

	public CnmDArticolo addCnmDArticolos1(CnmDArticolo cnmDArticolos1) {
		getCnmDArticolos1().add(cnmDArticolos1);
		cnmDArticolos1.setCnmTUser1(this);

		return cnmDArticolos1;
	}

	public CnmDArticolo removeCnmDArticolos1(CnmDArticolo cnmDArticolos1) {
		getCnmDArticolos1().remove(cnmDArticolos1);
		cnmDArticolos1.setCnmTUser1(null);

		return cnmDArticolos1;
	}

	public List<CnmDArticolo> getCnmDArticolos2() {
		return this.cnmDArticolos2;
	}

	public void setCnmDArticolos2(List<CnmDArticolo> cnmDArticolos2) {
		this.cnmDArticolos2 = cnmDArticolos2;
	}

	public CnmDArticolo addCnmDArticolos2(CnmDArticolo cnmDArticolos2) {
		getCnmDArticolos2().add(cnmDArticolos2);
		cnmDArticolos2.setCnmTUser2(this);

		return cnmDArticolos2;
	}

	public CnmDArticolo removeCnmDArticolos2(CnmDArticolo cnmDArticolos2) {
		getCnmDArticolos2().remove(cnmDArticolos2);
		cnmDArticolos2.setCnmTUser2(null);

		return cnmDArticolos2;
	}

	public List<CnmDComma> getCnmDCommas1() {
		return this.cnmDCommas1;
	}

	public void setCnmDCommas1(List<CnmDComma> cnmDCommas1) {
		this.cnmDCommas1 = cnmDCommas1;
	}

	public CnmDComma addCnmDCommas1(CnmDComma cnmDCommas1) {
		getCnmDCommas1().add(cnmDCommas1);
		cnmDCommas1.setCnmTUser1(this);

		return cnmDCommas1;
	}

	public CnmDComma removeCnmDCommas1(CnmDComma cnmDCommas1) {
		getCnmDCommas1().remove(cnmDCommas1);
		cnmDCommas1.setCnmTUser1(null);

		return cnmDCommas1;
	}

	public List<CnmDComma> getCnmDCommas2() {
		return this.cnmDCommas2;
	}

	public void setCnmDCommas2(List<CnmDComma> cnmDCommas2) {
		this.cnmDCommas2 = cnmDCommas2;
	}

	public CnmDComma addCnmDCommas2(CnmDComma cnmDCommas2) {
		getCnmDCommas2().add(cnmDCommas2);
		cnmDCommas2.setCnmTUser2(this);

		return cnmDCommas2;
	}

	public CnmDComma removeCnmDCommas2(CnmDComma cnmDCommas2) {
		getCnmDCommas2().remove(cnmDCommas2);
		cnmDCommas2.setCnmTUser2(null);

		return cnmDCommas2;
	}

	public List<CnmDLettera> getCnmDLetteras1() {
		return this.cnmDLetteras1;
	}

	public void setCnmDLetteras1(List<CnmDLettera> cnmDLetteras1) {
		this.cnmDLetteras1 = cnmDLetteras1;
	}

	public CnmDLettera addCnmDLetteras1(CnmDLettera cnmDLetteras1) {
		getCnmDLetteras1().add(cnmDLetteras1);
		cnmDLetteras1.setCnmTUser1(this);

		return cnmDLetteras1;
	}

	public CnmDLettera removeCnmDLetteras1(CnmDLettera cnmDLetteras1) {
		getCnmDLetteras1().remove(cnmDLetteras1);
		cnmDLetteras1.setCnmTUser1(null);

		return cnmDLetteras1;
	}

	public List<CnmDLettera> getCnmDLetteras2() {
		return this.cnmDLetteras2;
	}

	public void setCnmDLetteras2(List<CnmDLettera> cnmDLetteras2) {
		this.cnmDLetteras2 = cnmDLetteras2;
	}

	public CnmDLettera addCnmDLetteras2(CnmDLettera cnmDLetteras2) {
		getCnmDLetteras2().add(cnmDLetteras2);
		cnmDLetteras2.setCnmTUser2(this);

		return cnmDLetteras2;
	}

	public CnmDLettera removeCnmDLetteras2(CnmDLettera cnmDLetteras2) {
		getCnmDLetteras2().remove(cnmDLetteras2);
		cnmDLetteras2.setCnmTUser2(null);

		return cnmDLetteras2;
	}

	public List<CnmDNorma> getCnmDNormas1() {
		return this.cnmDNormas1;
	}

	public void setCnmDNormas1(List<CnmDNorma> cnmDNormas1) {
		this.cnmDNormas1 = cnmDNormas1;
	}

	public CnmDNorma addCnmDNormas1(CnmDNorma cnmDNormas1) {
		getCnmDNormas1().add(cnmDNormas1);
		cnmDNormas1.setCnmTUser1(this);

		return cnmDNormas1;
	}

	public CnmDNorma removeCnmDNormas1(CnmDNorma cnmDNormas1) {
		getCnmDNormas1().remove(cnmDNormas1);
		cnmDNormas1.setCnmTUser1(null);

		return cnmDNormas1;
	}

	public List<CnmDNorma> getCnmDNormas2() {
		return this.cnmDNormas2;
	}

	public void setCnmDNormas2(List<CnmDNorma> cnmDNormas2) {
		this.cnmDNormas2 = cnmDNormas2;
	}

	public CnmDNorma addCnmDNormas2(CnmDNorma cnmDNormas2) {
		getCnmDNormas2().add(cnmDNormas2);
		cnmDNormas2.setCnmTUser2(this);

		return cnmDNormas2;
	}

	public CnmDNorma removeCnmDNormas2(CnmDNorma cnmDNormas2) {
		getCnmDNormas2().remove(cnmDNormas2);
		cnmDNormas2.setCnmTUser2(null);

		return cnmDNormas2;
	}

	public List<CnmRAllegatoOrdVerbSog> getCnmRAllegatoOrdVerbSogs() {
		return this.cnmRAllegatoOrdVerbSogs;
	}

	public void setCnmRAllegatoOrdVerbSogs(List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogs) {
		this.cnmRAllegatoOrdVerbSogs = cnmRAllegatoOrdVerbSogs;
	}

	public CnmRAllegatoOrdVerbSog addCnmRAllegatoOrdVerbSog(CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog) {
		getCnmRAllegatoOrdVerbSogs().add(cnmRAllegatoOrdVerbSog);
		cnmRAllegatoOrdVerbSog.setCnmTUser(this);

		return cnmRAllegatoOrdVerbSog;
	}

	public CnmRAllegatoOrdVerbSog removeCnmRAllegatoOrdVerbSog(CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog) {
		getCnmRAllegatoOrdVerbSogs().remove(cnmRAllegatoOrdVerbSog);
		cnmRAllegatoOrdVerbSog.setCnmTUser(null);

		return cnmRAllegatoOrdVerbSog;
	}

	public List<CnmRAllegatoOrdinanza> getCnmRAllegatoOrdinanzas() {
		return this.cnmRAllegatoOrdinanzas;
	}

	public void setCnmRAllegatoOrdinanzas(List<CnmRAllegatoOrdinanza> cnmRAllegatoOrdinanzas) {
		this.cnmRAllegatoOrdinanzas = cnmRAllegatoOrdinanzas;
	}

	public CnmRAllegatoOrdinanza addCnmRAllegatoOrdinanza(CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza) {
		getCnmRAllegatoOrdinanzas().add(cnmRAllegatoOrdinanza);
		cnmRAllegatoOrdinanza.setCnmTUser(this);

		return cnmRAllegatoOrdinanza;
	}

	public CnmRAllegatoOrdinanza removeCnmRAllegatoOrdinanza(CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza) {
		getCnmRAllegatoOrdinanzas().remove(cnmRAllegatoOrdinanza);
		cnmRAllegatoOrdinanza.setCnmTUser(null);

		return cnmRAllegatoOrdinanza;
	}

	public List<CnmRAllegatoPianoRate> getCnmRAllegatoPianoRates() {
		return this.cnmRAllegatoPianoRates;
	}

	public void setCnmRAllegatoPianoRates(List<CnmRAllegatoPianoRate> cnmRAllegatoPianoRates) {
		this.cnmRAllegatoPianoRates = cnmRAllegatoPianoRates;
	}

	public CnmRAllegatoPianoRate addCnmRAllegatoPianoRate(CnmRAllegatoPianoRate cnmRAllegatoPianoRate) {
		getCnmRAllegatoPianoRates().add(cnmRAllegatoPianoRate);
		cnmRAllegatoPianoRate.setCnmTUser(this);

		return cnmRAllegatoPianoRate;
	}

	public CnmRAllegatoPianoRate removeCnmRAllegatoPianoRate(CnmRAllegatoPianoRate cnmRAllegatoPianoRate) {
		getCnmRAllegatoPianoRates().remove(cnmRAllegatoPianoRate);
		cnmRAllegatoPianoRate.setCnmTUser(null);

		return cnmRAllegatoPianoRate;
	}

	public List<CnmRAllegatoSollecito> getCnmRAllegatoSollecitos() {
		return this.cnmRAllegatoSollecitos;
	}

	public void setCnmRAllegatoSollecitos(List<CnmRAllegatoSollecito> cnmRAllegatoSollecitos) {
		this.cnmRAllegatoSollecitos = cnmRAllegatoSollecitos;
	}

	public CnmRAllegatoSollecito addCnmRAllegatoSollecito(CnmRAllegatoSollecito cnmRAllegatoSollecito) {
		getCnmRAllegatoSollecitos().add(cnmRAllegatoSollecito);
		cnmRAllegatoSollecito.setCnmTUser(this);

		return cnmRAllegatoSollecito;
	}

	public CnmRAllegatoSollecito removeCnmRAllegatoSollecito(CnmRAllegatoSollecito cnmRAllegatoSollecito) {
		getCnmRAllegatoSollecitos().remove(cnmRAllegatoSollecito);
		cnmRAllegatoSollecito.setCnmTUser(null);

		return cnmRAllegatoSollecito;
	}

	public List<CnmRAllegatoVerbSog> getCnmRAllegatoVerbSogs() {
		return this.cnmRAllegatoVerbSogs;
	}

	public void setCnmRAllegatoVerbSogs(List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs) {
		this.cnmRAllegatoVerbSogs = cnmRAllegatoVerbSogs;
	}

	public CnmRAllegatoVerbSog addCnmRAllegatoVerbSog(CnmRAllegatoVerbSog cnmRAllegatoVerbSog) {
		getCnmRAllegatoVerbSogs().add(cnmRAllegatoVerbSog);
		cnmRAllegatoVerbSog.setCnmTUser(this);

		return cnmRAllegatoVerbSog;
	}

	public CnmRAllegatoVerbSog removeCnmRAllegatoVerbSog(CnmRAllegatoVerbSog cnmRAllegatoVerbSog) {
		getCnmRAllegatoVerbSogs().remove(cnmRAllegatoVerbSog);
		cnmRAllegatoVerbSog.setCnmTUser(null);

		return cnmRAllegatoVerbSog;
	}

	public List<CnmRAllegatoVerbale> getCnmRAllegatoVerbales() {
		return this.cnmRAllegatoVerbales;
	}

	public void setCnmRAllegatoVerbales(List<CnmRAllegatoVerbale> cnmRAllegatoVerbales) {
		this.cnmRAllegatoVerbales = cnmRAllegatoVerbales;
	}

	public CnmRAllegatoVerbale addCnmRAllegatoVerbale(CnmRAllegatoVerbale cnmRAllegatoVerbale) {
		getCnmRAllegatoVerbales().add(cnmRAllegatoVerbale);
		cnmRAllegatoVerbale.setCnmTUser(this);

		return cnmRAllegatoVerbale;
	}

	public CnmRAllegatoVerbale removeCnmRAllegatoVerbale(CnmRAllegatoVerbale cnmRAllegatoVerbale) {
		getCnmRAllegatoVerbales().remove(cnmRAllegatoVerbale);
		cnmRAllegatoVerbale.setCnmTUser(null);

		return cnmRAllegatoVerbale;
	}

	public List<CnmREnteNorma> getCnmREnteNormas1() {
		return this.cnmREnteNormas1;
	}

	public void setCnmREnteNormas1(List<CnmREnteNorma> cnmREnteNormas1) {
		this.cnmREnteNormas1 = cnmREnteNormas1;
	}

	public CnmREnteNorma addCnmREnteNormas1(CnmREnteNorma cnmREnteNormas1) {
		getCnmREnteNormas1().add(cnmREnteNormas1);
		cnmREnteNormas1.setCnmTUser1(this);

		return cnmREnteNormas1;
	}

	public CnmREnteNorma removeCnmREnteNormas1(CnmREnteNorma cnmREnteNormas1) {
		getCnmREnteNormas1().remove(cnmREnteNormas1);
		cnmREnteNormas1.setCnmTUser1(null);

		return cnmREnteNormas1;
	}

	public List<CnmREnteNorma> getCnmREnteNormas2() {
		return this.cnmREnteNormas2;
	}

	public void setCnmREnteNormas2(List<CnmREnteNorma> cnmREnteNormas2) {
		this.cnmREnteNormas2 = cnmREnteNormas2;
	}

	public CnmREnteNorma addCnmREnteNormas2(CnmREnteNorma cnmREnteNormas2) {
		getCnmREnteNormas2().add(cnmREnteNormas2);
		cnmREnteNormas2.setCnmTUser2(this);

		return cnmREnteNormas2;
	}

	public CnmREnteNorma removeCnmREnteNormas2(CnmREnteNorma cnmREnteNormas2) {
		getCnmREnteNormas2().remove(cnmREnteNormas2);
		cnmREnteNormas2.setCnmTUser2(null);

		return cnmREnteNormas2;
	}

	public List<CnmRFunzionarioIstruttore> getCnmRFunzionarioIstruttores() {
		return this.cnmRFunzionarioIstruttores;
	}

	public void setCnmRFunzionarioIstruttores(List<CnmRFunzionarioIstruttore> cnmRFunzionarioIstruttores) {
		this.cnmRFunzionarioIstruttores = cnmRFunzionarioIstruttores;
	}

	public CnmRFunzionarioIstruttore addCnmRFunzionarioIstruttore(CnmRFunzionarioIstruttore cnmRFunzionarioIstruttore) {
		getCnmRFunzionarioIstruttores().add(cnmRFunzionarioIstruttore);
		cnmRFunzionarioIstruttore.setCnmTUser(this);

		return cnmRFunzionarioIstruttore;
	}

	public CnmRFunzionarioIstruttore removeCnmRFunzionarioIstruttore(CnmRFunzionarioIstruttore cnmRFunzionarioIstruttore) {
		getCnmRFunzionarioIstruttores().remove(cnmRFunzionarioIstruttore);
		cnmRFunzionarioIstruttore.setCnmTUser(null);

		return cnmRFunzionarioIstruttore;
	}

	public List<CnmROrdinanzaVerbSog> getCnmROrdinanzaVerbSogs1() {
		return this.cnmROrdinanzaVerbSogs1;
	}

	public void setCnmROrdinanzaVerbSogs1(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs1) {
		this.cnmROrdinanzaVerbSogs1 = cnmROrdinanzaVerbSogs1;
	}

	public CnmROrdinanzaVerbSog addCnmROrdinanzaVerbSogs1(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSogs1) {
		getCnmROrdinanzaVerbSogs1().add(cnmROrdinanzaVerbSogs1);
		cnmROrdinanzaVerbSogs1.setCnmTUser1(this);

		return cnmROrdinanzaVerbSogs1;
	}

	public CnmROrdinanzaVerbSog removeCnmROrdinanzaVerbSogs1(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSogs1) {
		getCnmROrdinanzaVerbSogs1().remove(cnmROrdinanzaVerbSogs1);
		cnmROrdinanzaVerbSogs1.setCnmTUser1(null);

		return cnmROrdinanzaVerbSogs1;
	}

	public List<CnmROrdinanzaVerbSog> getCnmROrdinanzaVerbSogs2() {
		return this.cnmROrdinanzaVerbSogs2;
	}

	public void setCnmROrdinanzaVerbSogs2(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs2) {
		this.cnmROrdinanzaVerbSogs2 = cnmROrdinanzaVerbSogs2;
	}

	public CnmROrdinanzaVerbSog addCnmROrdinanzaVerbSogs2(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSogs2) {
		getCnmROrdinanzaVerbSogs2().add(cnmROrdinanzaVerbSogs2);
		cnmROrdinanzaVerbSogs2.setCnmTUser2(this);

		return cnmROrdinanzaVerbSogs2;
	}

	public CnmROrdinanzaVerbSog removeCnmROrdinanzaVerbSogs2(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSogs2) {
		getCnmROrdinanzaVerbSogs2().remove(cnmROrdinanzaVerbSogs2);
		cnmROrdinanzaVerbSogs2.setCnmTUser2(null);

		return cnmROrdinanzaVerbSogs2;
	}

	public List<CnmRSoggRata> getCnmRSoggRatas1() {
		return this.cnmRSoggRatas1;
	}

	public void setCnmRSoggRatas1(List<CnmRSoggRata> cnmRSoggRatas1) {
		this.cnmRSoggRatas1 = cnmRSoggRatas1;
	}

	public CnmRSoggRata addCnmRSoggRatas1(CnmRSoggRata cnmRSoggRatas1) {
		getCnmRSoggRatas1().add(cnmRSoggRatas1);
		cnmRSoggRatas1.setCnmTUser1(this);

		return cnmRSoggRatas1;
	}

	public CnmRSoggRata removeCnmRSoggRatas1(CnmRSoggRata cnmRSoggRatas1) {
		getCnmRSoggRatas1().remove(cnmRSoggRatas1);
		cnmRSoggRatas1.setCnmTUser1(null);

		return cnmRSoggRatas1;
	}

	public List<CnmRSoggRata> getCnmRSoggRatas2() {
		return this.cnmRSoggRatas2;
	}

	public void setCnmRSoggRatas2(List<CnmRSoggRata> cnmRSoggRatas2) {
		this.cnmRSoggRatas2 = cnmRSoggRatas2;
	}

	public CnmRSoggRata addCnmRSoggRatas2(CnmRSoggRata cnmRSoggRatas2) {
		getCnmRSoggRatas2().add(cnmRSoggRatas2);
		cnmRSoggRatas2.setCnmTUser2(this);

		return cnmRSoggRatas2;
	}

	public CnmRSoggRata removeCnmRSoggRatas2(CnmRSoggRata cnmRSoggRatas2) {
		getCnmRSoggRatas2().remove(cnmRSoggRatas2);
		cnmRSoggRatas2.setCnmTUser2(null);

		return cnmRSoggRatas2;
	}

	public List<CnmRUserEnte> getCnmRUserEntes() {
		return this.cnmRUserEntes;
	}

	public void setCnmRUserEntes(List<CnmRUserEnte> cnmRUserEntes) {
		this.cnmRUserEntes = cnmRUserEntes;
	}

	public CnmRUserEnte addCnmRUserEnte(CnmRUserEnte cnmRUserEnte) {
		getCnmRUserEntes().add(cnmRUserEnte);
		cnmRUserEnte.setCnmTUser(this);

		return cnmRUserEnte;
	}

	public CnmRUserEnte removeCnmRUserEnte(CnmRUserEnte cnmRUserEnte) {
		getCnmRUserEntes().remove(cnmRUserEnte);
		cnmRUserEnte.setCnmTUser(null);

		return cnmRUserEnte;
	}

	public List<CnmRVerbaleIllecito> getCnmRVerbaleIllecitos1() {
		return this.cnmRVerbaleIllecitos1;
	}

	public void setCnmRVerbaleIllecitos1(List<CnmRVerbaleIllecito> cnmRVerbaleIllecitos1) {
		this.cnmRVerbaleIllecitos1 = cnmRVerbaleIllecitos1;
	}

	public CnmRVerbaleIllecito addCnmRVerbaleIllecitos1(CnmRVerbaleIllecito cnmRVerbaleIllecitos1) {
		getCnmRVerbaleIllecitos1().add(cnmRVerbaleIllecitos1);
		cnmRVerbaleIllecitos1.setCnmTUser1(this);

		return cnmRVerbaleIllecitos1;
	}

	public CnmRVerbaleIllecito removeCnmRVerbaleIllecitos1(CnmRVerbaleIllecito cnmRVerbaleIllecitos1) {
		getCnmRVerbaleIllecitos1().remove(cnmRVerbaleIllecitos1);
		cnmRVerbaleIllecitos1.setCnmTUser1(null);

		return cnmRVerbaleIllecitos1;
	}

	public List<CnmRVerbaleIllecito> getCnmRVerbaleIllecitos2() {
		return this.cnmRVerbaleIllecitos2;
	}

	public void setCnmRVerbaleIllecitos2(List<CnmRVerbaleIllecito> cnmRVerbaleIllecitos2) {
		this.cnmRVerbaleIllecitos2 = cnmRVerbaleIllecitos2;
	}

	public CnmRVerbaleIllecito addCnmRVerbaleIllecitos2(CnmRVerbaleIllecito cnmRVerbaleIllecitos2) {
		getCnmRVerbaleIllecitos2().add(cnmRVerbaleIllecitos2);
		cnmRVerbaleIllecitos2.setCnmTUser2(this);

		return cnmRVerbaleIllecitos2;
	}

	public CnmRVerbaleIllecito removeCnmRVerbaleIllecitos2(CnmRVerbaleIllecito cnmRVerbaleIllecitos2) {
		getCnmRVerbaleIllecitos2().remove(cnmRVerbaleIllecitos2);
		cnmRVerbaleIllecitos2.setCnmTUser2(null);

		return cnmRVerbaleIllecitos2;
	}

	public List<CnmRVerbaleSoggetto> getCnmRVerbaleSoggettos() {
		return this.cnmRVerbaleSoggettos;
	}

	public void setCnmRVerbaleSoggettos(List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettos) {
		this.cnmRVerbaleSoggettos = cnmRVerbaleSoggettos;
	}

	public CnmRVerbaleSoggetto addCnmRVerbaleSoggetto(CnmRVerbaleSoggetto cnmRVerbaleSoggetto) {
		getCnmRVerbaleSoggettos().add(cnmRVerbaleSoggetto);
		cnmRVerbaleSoggetto.setCnmTUser(this);

		return cnmRVerbaleSoggetto;
	}

	public CnmRVerbaleSoggetto removeCnmRVerbaleSoggetto(CnmRVerbaleSoggetto cnmRVerbaleSoggetto) {
		getCnmRVerbaleSoggettos().remove(cnmRVerbaleSoggetto);
		cnmRVerbaleSoggetto.setCnmTUser(null);

		return cnmRVerbaleSoggetto;
	}

	public List<CnmSStatoOrdinanza> getCnmSStatoOrdinanzas() {
		return this.cnmSStatoOrdinanzas;
	}

	public void setCnmSStatoOrdinanzas(List<CnmSStatoOrdinanza> cnmSStatoOrdinanzas) {
		this.cnmSStatoOrdinanzas = cnmSStatoOrdinanzas;
	}

	public CnmSStatoOrdinanza addCnmSStatoOrdinanza(CnmSStatoOrdinanza cnmSStatoOrdinanza) {
		getCnmSStatoOrdinanzas().add(cnmSStatoOrdinanza);
		cnmSStatoOrdinanza.setCnmTUser(this);

		return cnmSStatoOrdinanza;
	}

	public CnmSStatoOrdinanza removeCnmSStatoOrdinanza(CnmSStatoOrdinanza cnmSStatoOrdinanza) {
		getCnmSStatoOrdinanzas().remove(cnmSStatoOrdinanza);
		cnmSStatoOrdinanza.setCnmTUser(null);

		return cnmSStatoOrdinanza;
	}

	public List<CnmSStatoVerbale> getCnmSStatoVerbales() {
		return this.cnmSStatoVerbales;
	}

	public void setCnmSStatoVerbales(List<CnmSStatoVerbale> cnmSStatoVerbales) {
		this.cnmSStatoVerbales = cnmSStatoVerbales;
	}

	public CnmSStatoVerbale addCnmSStatoVerbale(CnmSStatoVerbale cnmSStatoVerbale) {
		getCnmSStatoVerbales().add(cnmSStatoVerbale);
		cnmSStatoVerbale.setCnmTUser(this);

		return cnmSStatoVerbale;
	}

	public CnmSStatoVerbale removeCnmSStatoVerbale(CnmSStatoVerbale cnmSStatoVerbale) {
		getCnmSStatoVerbales().remove(cnmSStatoVerbale);
		cnmSStatoVerbale.setCnmTUser(null);

		return cnmSStatoVerbale;
	}

	public List<CnmTAllegato> getCnmTAllegatos1() {
		return this.cnmTAllegatos1;
	}

	public void setCnmTAllegatos1(List<CnmTAllegato> cnmTAllegatos1) {
		this.cnmTAllegatos1 = cnmTAllegatos1;
	}

	public CnmTAllegato addCnmTAllegatos1(CnmTAllegato cnmTAllegatos1) {
		getCnmTAllegatos1().add(cnmTAllegatos1);
		cnmTAllegatos1.setCnmTUser1(this);

		return cnmTAllegatos1;
	}

	public CnmTAllegato removeCnmTAllegatos1(CnmTAllegato cnmTAllegatos1) {
		getCnmTAllegatos1().remove(cnmTAllegatos1);
		cnmTAllegatos1.setCnmTUser1(null);

		return cnmTAllegatos1;
	}

	public List<CnmTAllegato> getCnmTAllegatos2() {
		return this.cnmTAllegatos2;
	}

	public void setCnmTAllegatos2(List<CnmTAllegato> cnmTAllegatos2) {
		this.cnmTAllegatos2 = cnmTAllegatos2;
	}

	public CnmTAllegato addCnmTAllegatos2(CnmTAllegato cnmTAllegatos2) {
		getCnmTAllegatos2().add(cnmTAllegatos2);
		cnmTAllegatos2.setCnmTUser2(this);

		return cnmTAllegatos2;
	}

	public CnmTAllegato removeCnmTAllegatos2(CnmTAllegato cnmTAllegatos2) {
		getCnmTAllegatos2().remove(cnmTAllegatos2);
		cnmTAllegatos2.setCnmTUser2(null);

		return cnmTAllegatos2;
	}

	public List<CnmTAllegatoField> getCnmTAllegatoFields1() {
		return this.cnmTAllegatoFields1;
	}

	public void setCnmTAllegatoFields1(List<CnmTAllegatoField> cnmTAllegatoFields1) {
		this.cnmTAllegatoFields1 = cnmTAllegatoFields1;
	}

	public CnmTAllegatoField addCnmTAllegatoFields1(CnmTAllegatoField cnmTAllegatoFields1) {
		getCnmTAllegatoFields1().add(cnmTAllegatoFields1);
		cnmTAllegatoFields1.setCnmTUser1(this);

		return cnmTAllegatoFields1;
	}

	public CnmTAllegatoField removeCnmTAllegatoFields1(CnmTAllegatoField cnmTAllegatoFields1) {
		getCnmTAllegatoFields1().remove(cnmTAllegatoFields1);
		cnmTAllegatoFields1.setCnmTUser1(null);

		return cnmTAllegatoFields1;
	}

	public List<CnmTAllegatoField> getCnmTAllegatoFields2() {
		return this.cnmTAllegatoFields2;
	}

	public void setCnmTAllegatoFields2(List<CnmTAllegatoField> cnmTAllegatoFields2) {
		this.cnmTAllegatoFields2 = cnmTAllegatoFields2;
	}

	public CnmTAllegatoField addCnmTAllegatoFields2(CnmTAllegatoField cnmTAllegatoFields2) {
		getCnmTAllegatoFields2().add(cnmTAllegatoFields2);
		cnmTAllegatoFields2.setCnmTUser2(this);

		return cnmTAllegatoFields2;
	}

	public CnmTAllegatoField removeCnmTAllegatoFields2(CnmTAllegatoField cnmTAllegatoFields2) {
		getCnmTAllegatoFields2().remove(cnmTAllegatoFields2);
		cnmTAllegatoFields2.setCnmTUser2(null);

		return cnmTAllegatoFields2;
	}

	public List<CnmTCalendario> getCnmTCalendarios1() {
		return this.cnmTCalendarios1;
	}

	public void setCnmTCalendarios1(List<CnmTCalendario> cnmTCalendarios1) {
		this.cnmTCalendarios1 = cnmTCalendarios1;
	}

	public CnmTCalendario addCnmTCalendarios1(CnmTCalendario cnmTCalendarios1) {
		getCnmTCalendarios1().add(cnmTCalendarios1);
		cnmTCalendarios1.setCnmTUser1(this);

		return cnmTCalendarios1;
	}

	public CnmTCalendario removeCnmTCalendarios1(CnmTCalendario cnmTCalendarios1) {
		getCnmTCalendarios1().remove(cnmTCalendarios1);
		cnmTCalendarios1.setCnmTUser1(null);

		return cnmTCalendarios1;
	}

	public List<CnmTCalendario> getCnmTCalendarios2() {
		return this.cnmTCalendarios2;
	}

	public void setCnmTCalendarios2(List<CnmTCalendario> cnmTCalendarios2) {
		this.cnmTCalendarios2 = cnmTCalendarios2;
	}

	public CnmTCalendario addCnmTCalendarios2(CnmTCalendario cnmTCalendarios2) {
		getCnmTCalendarios2().add(cnmTCalendarios2);
		cnmTCalendarios2.setCnmTUser2(this);

		return cnmTCalendarios2;
	}

	public CnmTCalendario removeCnmTCalendarios2(CnmTCalendario cnmTCalendarios2) {
		getCnmTCalendarios2().remove(cnmTCalendarios2);
		cnmTCalendarios2.setCnmTUser2(null);

		return cnmTCalendarios2;
	}

	public List<CnmTNotifica> getCnmTNotificas1() {
		return this.cnmTNotificas1;
	}

	public void setCnmTNotificas1(List<CnmTNotifica> cnmTNotificas1) {
		this.cnmTNotificas1 = cnmTNotificas1;
	}

	public CnmTNotifica addCnmTNotificas1(CnmTNotifica cnmTNotificas1) {
		getCnmTNotificas1().add(cnmTNotificas1);
		cnmTNotificas1.setCnmTUser1(this);

		return cnmTNotificas1;
	}

	public CnmTNotifica removeCnmTNotificas1(CnmTNotifica cnmTNotificas1) {
		getCnmTNotificas1().remove(cnmTNotificas1);
		cnmTNotificas1.setCnmTUser1(null);

		return cnmTNotificas1;
	}

	public List<CnmTNotifica> getCnmTNotificas2() {
		return this.cnmTNotificas2;
	}

	public void setCnmTNotificas2(List<CnmTNotifica> cnmTNotificas2) {
		this.cnmTNotificas2 = cnmTNotificas2;
	}

	public CnmTNotifica addCnmTNotificas2(CnmTNotifica cnmTNotificas2) {
		getCnmTNotificas2().add(cnmTNotificas2);
		cnmTNotificas2.setCnmTUser2(this);

		return cnmTNotificas2;
	}

	public CnmTNotifica removeCnmTNotificas2(CnmTNotifica cnmTNotificas2) {
		getCnmTNotificas2().remove(cnmTNotificas2);
		cnmTNotificas2.setCnmTUser2(null);

		return cnmTNotificas2;
	}

	public List<CnmTOrdinanza> getCnmTOrdinanzas1() {
		return this.cnmTOrdinanzas1;
	}

	public void setCnmTOrdinanzas1(List<CnmTOrdinanza> cnmTOrdinanzas1) {
		this.cnmTOrdinanzas1 = cnmTOrdinanzas1;
	}

	public CnmTOrdinanza addCnmTOrdinanzas1(CnmTOrdinanza cnmTOrdinanzas1) {
		getCnmTOrdinanzas1().add(cnmTOrdinanzas1);
		cnmTOrdinanzas1.setCnmTUser1(this);

		return cnmTOrdinanzas1;
	}

	public CnmTOrdinanza removeCnmTOrdinanzas1(CnmTOrdinanza cnmTOrdinanzas1) {
		getCnmTOrdinanzas1().remove(cnmTOrdinanzas1);
		cnmTOrdinanzas1.setCnmTUser1(null);

		return cnmTOrdinanzas1;
	}

	public List<CnmTOrdinanza> getCnmTOrdinanzas2() {
		return this.cnmTOrdinanzas2;
	}

	public void setCnmTOrdinanzas2(List<CnmTOrdinanza> cnmTOrdinanzas2) {
		this.cnmTOrdinanzas2 = cnmTOrdinanzas2;
	}

	public CnmTOrdinanza addCnmTOrdinanzas2(CnmTOrdinanza cnmTOrdinanzas2) {
		getCnmTOrdinanzas2().add(cnmTOrdinanzas2);
		cnmTOrdinanzas2.setCnmTUser2(this);

		return cnmTOrdinanzas2;
	}

	public CnmTOrdinanza removeCnmTOrdinanzas2(CnmTOrdinanza cnmTOrdinanzas2) {
		getCnmTOrdinanzas2().remove(cnmTOrdinanzas2);
		cnmTOrdinanzas2.setCnmTUser2(null);

		return cnmTOrdinanzas2;
	}

	public List<CnmTPersona> getCnmTPersonas1() {
		return this.cnmTPersonas1;
	}

	public void setCnmTPersonas1(List<CnmTPersona> cnmTPersonas1) {
		this.cnmTPersonas1 = cnmTPersonas1;
	}

	public CnmTPersona addCnmTPersonas1(CnmTPersona cnmTPersonas1) {
		getCnmTPersonas1().add(cnmTPersonas1);
		cnmTPersonas1.setCnmTUser1(this);

		return cnmTPersonas1;
	}

	public CnmTPersona removeCnmTPersonas1(CnmTPersona cnmTPersonas1) {
		getCnmTPersonas1().remove(cnmTPersonas1);
		cnmTPersonas1.setCnmTUser1(null);

		return cnmTPersonas1;
	}

	public List<CnmTPersona> getCnmTPersonas2() {
		return this.cnmTPersonas2;
	}

	public void setCnmTPersonas2(List<CnmTPersona> cnmTPersonas2) {
		this.cnmTPersonas2 = cnmTPersonas2;
	}

	public CnmTPersona addCnmTPersonas2(CnmTPersona cnmTPersonas2) {
		getCnmTPersonas2().add(cnmTPersonas2);
		cnmTPersonas2.setCnmTUser2(this);

		return cnmTPersonas2;
	}

	public CnmTPersona removeCnmTPersonas2(CnmTPersona cnmTPersonas2) {
		getCnmTPersonas2().remove(cnmTPersonas2);
		cnmTPersonas2.setCnmTUser2(null);

		return cnmTPersonas2;
	}

	public List<CnmTPianoRate> getCnmTPianoRates1() {
		return this.cnmTPianoRates1;
	}

	public void setCnmTPianoRates1(List<CnmTPianoRate> cnmTPianoRates1) {
		this.cnmTPianoRates1 = cnmTPianoRates1;
	}

	public CnmTPianoRate addCnmTPianoRates1(CnmTPianoRate cnmTPianoRates1) {
		getCnmTPianoRates1().add(cnmTPianoRates1);
		cnmTPianoRates1.setCnmTUser1(this);

		return cnmTPianoRates1;
	}

	public CnmTPianoRate removeCnmTPianoRates1(CnmTPianoRate cnmTPianoRates1) {
		getCnmTPianoRates1().remove(cnmTPianoRates1);
		cnmTPianoRates1.setCnmTUser1(null);

		return cnmTPianoRates1;
	}

	public List<CnmTPianoRate> getCnmTPianoRates2() {
		return this.cnmTPianoRates2;
	}

	public void setCnmTPianoRates2(List<CnmTPianoRate> cnmTPianoRates2) {
		this.cnmTPianoRates2 = cnmTPianoRates2;
	}

	public CnmTPianoRate addCnmTPianoRates2(CnmTPianoRate cnmTPianoRates2) {
		getCnmTPianoRates2().add(cnmTPianoRates2);
		cnmTPianoRates2.setCnmTUser2(this);

		return cnmTPianoRates2;
	}

	public CnmTPianoRate removeCnmTPianoRates2(CnmTPianoRate cnmTPianoRates2) {
		getCnmTPianoRates2().remove(cnmTPianoRates2);
		cnmTPianoRates2.setCnmTUser2(null);

		return cnmTPianoRates2;
	}

	public List<CnmTRata> getCnmTRatas1() {
		return this.cnmTRatas1;
	}

	public void setCnmTRatas1(List<CnmTRata> cnmTRatas1) {
		this.cnmTRatas1 = cnmTRatas1;
	}

	public CnmTRata addCnmTRatas1(CnmTRata cnmTRatas1) {
		getCnmTRatas1().add(cnmTRatas1);
		cnmTRatas1.setCnmTUser1(this);

		return cnmTRatas1;
	}

	public CnmTRata removeCnmTRatas1(CnmTRata cnmTRatas1) {
		getCnmTRatas1().remove(cnmTRatas1);
		cnmTRatas1.setCnmTUser1(null);

		return cnmTRatas1;
	}

	public List<CnmTRata> getCnmTRatas2() {
		return this.cnmTRatas2;
	}

	public void setCnmTRatas2(List<CnmTRata> cnmTRatas2) {
		this.cnmTRatas2 = cnmTRatas2;
	}

	public CnmTRata addCnmTRatas2(CnmTRata cnmTRatas2) {
		getCnmTRatas2().add(cnmTRatas2);
		cnmTRatas2.setCnmTUser2(this);

		return cnmTRatas2;
	}

	public CnmTRata removeCnmTRatas2(CnmTRata cnmTRatas2) {
		getCnmTRatas2().remove(cnmTRatas2);
		cnmTRatas2.setCnmTUser2(null);

		return cnmTRatas2;
	}

	public List<CnmTRiscossione> getCnmTRiscossiones1() {
		return this.cnmTRiscossiones1;
	}

	public void setCnmTRiscossiones1(List<CnmTRiscossione> cnmTRiscossiones1) {
		this.cnmTRiscossiones1 = cnmTRiscossiones1;
	}

	public CnmTRiscossione addCnmTRiscossiones1(CnmTRiscossione cnmTRiscossiones1) {
		getCnmTRiscossiones1().add(cnmTRiscossiones1);
		cnmTRiscossiones1.setCnmTUser1(this);

		return cnmTRiscossiones1;
	}

	public CnmTRiscossione removeCnmTRiscossiones1(CnmTRiscossione cnmTRiscossiones1) {
		getCnmTRiscossiones1().remove(cnmTRiscossiones1);
		cnmTRiscossiones1.setCnmTUser1(null);

		return cnmTRiscossiones1;
	}

	public List<CnmTRiscossione> getCnmTRiscossiones2() {
		return this.cnmTRiscossiones2;
	}

	public void setCnmTRiscossiones2(List<CnmTRiscossione> cnmTRiscossiones2) {
		this.cnmTRiscossiones2 = cnmTRiscossiones2;
	}

	public CnmTRiscossione addCnmTRiscossiones2(CnmTRiscossione cnmTRiscossiones2) {
		getCnmTRiscossiones2().add(cnmTRiscossiones2);
		cnmTRiscossiones2.setCnmTUser2(this);

		return cnmTRiscossiones2;
	}

	public CnmTRiscossione removeCnmTRiscossiones2(CnmTRiscossione cnmTRiscossiones2) {
		getCnmTRiscossiones2().remove(cnmTRiscossiones2);
		cnmTRiscossiones2.setCnmTUser2(null);

		return cnmTRiscossiones2;
	}

	public List<CnmTSocieta> getCnmTSocietas1() {
		return this.cnmTSocietas1;
	}

	public void setCnmTSocietas1(List<CnmTSocieta> cnmTSocietas1) {
		this.cnmTSocietas1 = cnmTSocietas1;
	}

	public CnmTSocieta addCnmTSocietas1(CnmTSocieta cnmTSocietas1) {
		getCnmTSocietas1().add(cnmTSocietas1);
		cnmTSocietas1.setCnmTUser1(this);

		return cnmTSocietas1;
	}

	public CnmTSocieta removeCnmTSocietas1(CnmTSocieta cnmTSocietas1) {
		getCnmTSocietas1().remove(cnmTSocietas1);
		cnmTSocietas1.setCnmTUser1(null);

		return cnmTSocietas1;
	}

	public List<CnmTSocieta> getCnmTSocietas2() {
		return this.cnmTSocietas2;
	}

	public void setCnmTSocietas2(List<CnmTSocieta> cnmTSocietas2) {
		this.cnmTSocietas2 = cnmTSocietas2;
	}

	public CnmTSocieta addCnmTSocietas2(CnmTSocieta cnmTSocietas2) {
		getCnmTSocietas2().add(cnmTSocietas2);
		cnmTSocietas2.setCnmTUser2(this);

		return cnmTSocietas2;
	}

	public CnmTSocieta removeCnmTSocietas2(CnmTSocieta cnmTSocietas2) {
		getCnmTSocietas2().remove(cnmTSocietas2);
		cnmTSocietas2.setCnmTUser2(null);

		return cnmTSocietas2;
	}

	public List<CnmTSoggetto> getCnmTSoggettos1() {
		return this.cnmTSoggettos1;
	}

	public void setCnmTSoggettos1(List<CnmTSoggetto> cnmTSoggettos1) {
		this.cnmTSoggettos1 = cnmTSoggettos1;
	}

	public CnmTSoggetto addCnmTSoggettos1(CnmTSoggetto cnmTSoggettos1) {
		getCnmTSoggettos1().add(cnmTSoggettos1);
		cnmTSoggettos1.setCnmTUser1(this);

		return cnmTSoggettos1;
	}

	public CnmTSoggetto removeCnmTSoggettos1(CnmTSoggetto cnmTSoggettos1) {
		getCnmTSoggettos1().remove(cnmTSoggettos1);
		cnmTSoggettos1.setCnmTUser1(null);

		return cnmTSoggettos1;
	}

	public List<CnmTSoggetto> getCnmTSoggettos2() {
		return this.cnmTSoggettos2;
	}

	public void setCnmTSoggettos2(List<CnmTSoggetto> cnmTSoggettos2) {
		this.cnmTSoggettos2 = cnmTSoggettos2;
	}

	public CnmTSoggetto addCnmTSoggettos2(CnmTSoggetto cnmTSoggettos2) {
		getCnmTSoggettos2().add(cnmTSoggettos2);
		cnmTSoggettos2.setCnmTUser2(this);

		return cnmTSoggettos2;
	}

	public CnmTSoggetto removeCnmTSoggettos2(CnmTSoggetto cnmTSoggettos2) {
		getCnmTSoggettos2().remove(cnmTSoggettos2);
		cnmTSoggettos2.setCnmTUser2(null);

		return cnmTSoggettos2;
	}

	public List<CnmTSollecito> getCnmTSollecitos1() {
		return this.cnmTSollecitos1;
	}

	public void setCnmTSollecitos1(List<CnmTSollecito> cnmTSollecitos1) {
		this.cnmTSollecitos1 = cnmTSollecitos1;
	}

	public CnmTSollecito addCnmTSollecitos1(CnmTSollecito cnmTSollecitos1) {
		getCnmTSollecitos1().add(cnmTSollecitos1);
		cnmTSollecitos1.setCnmTUser1(this);

		return cnmTSollecitos1;
	}

	public CnmTSollecito removeCnmTSollecitos1(CnmTSollecito cnmTSollecitos1) {
		getCnmTSollecitos1().remove(cnmTSollecitos1);
		cnmTSollecitos1.setCnmTUser1(null);

		return cnmTSollecitos1;
	}

	public List<CnmTSollecito> getCnmTSollecitos2() {
		return this.cnmTSollecitos2;
	}

	public void setCnmTSollecitos2(List<CnmTSollecito> cnmTSollecitos2) {
		this.cnmTSollecitos2 = cnmTSollecitos2;
	}

	public CnmTSollecito addCnmTSollecitos2(CnmTSollecito cnmTSollecitos2) {
		getCnmTSollecitos2().add(cnmTSollecitos2);
		cnmTSollecitos2.setCnmTUser2(this);

		return cnmTSollecitos2;
	}

	public CnmTSollecito removeCnmTSollecitos2(CnmTSollecito cnmTSollecitos2) {
		getCnmTSollecitos2().remove(cnmTSollecitos2);
		cnmTSollecitos2.setCnmTUser2(null);

		return cnmTSollecitos2;
	}

	public CnmDGruppo getCnmDGruppo() {
		return this.cnmDGruppo;
	}

	public void setCnmDGruppo(CnmDGruppo cnmDGruppo) {
		this.cnmDGruppo = cnmDGruppo;
	}

	public CnmDRuolo getCnmDRuolo() {
		return this.cnmDRuolo;
	}

	public void setCnmDRuolo(CnmDRuolo cnmDRuolo) {
		this.cnmDRuolo = cnmDRuolo;
	}

	public List<CnmTVerbale> getCnmTVerbales1() {
		return this.cnmTVerbales1;
	}

	public void setCnmTVerbales1(List<CnmTVerbale> cnmTVerbales1) {
		this.cnmTVerbales1 = cnmTVerbales1;
	}

	public CnmTVerbale addCnmTVerbales1(CnmTVerbale cnmTVerbales1) {
		getCnmTVerbales1().add(cnmTVerbales1);
		cnmTVerbales1.setCnmTUser1(this);

		return cnmTVerbales1;
	}

	public CnmTVerbale removeCnmTVerbales1(CnmTVerbale cnmTVerbales1) {
		getCnmTVerbales1().remove(cnmTVerbales1);
		cnmTVerbales1.setCnmTUser1(null);

		return cnmTVerbales1;
	}

	public List<CnmTVerbale> getCnmTVerbales2() {
		return this.cnmTVerbales2;
	}

	public void setCnmTVerbales2(List<CnmTVerbale> cnmTVerbales2) {
		this.cnmTVerbales2 = cnmTVerbales2;
	}

	public CnmTVerbale addCnmTVerbales2(CnmTVerbale cnmTVerbales2) {
		getCnmTVerbales2().add(cnmTVerbales2);
		cnmTVerbales2.setCnmTUser2(this);

		return cnmTVerbales2;
	}

	public CnmTVerbale removeCnmTVerbales2(CnmTVerbale cnmTVerbales2) {
		getCnmTVerbales2().remove(cnmTVerbales2);
		cnmTVerbales2.setCnmTUser2(null);

		return cnmTVerbales2;
	}

	
	
	
	public List<CnmTDocumento> getCnmTDocumentos1() {
		return this.cnmTDocumentos1;
	}

	public void setCnmTDocumentos1(List<CnmTDocumento> cnmTDocumentos1) {
		this.cnmTDocumentos1 = cnmTDocumentos1;
	}

	public CnmTDocumento addCnmTDocumentos1(CnmTDocumento cnmTDocumentos1) {
		getCnmTDocumentos1().add(cnmTDocumentos1);
		cnmTDocumentos1.setCnmTUser1(this);

		return cnmTDocumentos1;
	}

	public CnmTDocumento removeCnmTDocumentos1(CnmTDocumento cnmTDocumentos1) {
		getCnmTDocumentos1().remove(cnmTDocumentos1);
		cnmTDocumentos1.setCnmTUser1(null);

		return cnmTDocumentos1;
	}

	public List<CnmTDocumento> getCnmTDocumentos2() {
		return this.cnmTDocumentos2;
	}

	public void setCnmTDocumentos2(List<CnmTDocumento> cnmTDocumentos2) {
		this.cnmTDocumentos2 = cnmTDocumentos2;
	}

	public CnmTDocumento addCnmTDocumentos2(CnmTDocumento cnmTDocumentos2) {
		getCnmTDocumentos2().add(cnmTDocumentos2);
		cnmTDocumentos2.setCnmTUser2(this);

		return cnmTDocumentos2;
	}

	public CnmTDocumento removeCnmTDocumentos2(CnmTDocumento cnmTDocumentos2) {
		getCnmTDocumentos2().remove(cnmTDocumentos2);
		cnmTDocumentos2.setCnmTUser2(null);

		return cnmTDocumentos2;
	}
	
	
	public List<CnmTRichiesta> getCnmTRichiestas1() {
		return this.cnmTRichiestas1;
	}

	public void setCnmTRichiestas1(List<CnmTRichiesta> cnmTRichiestas1) {
		this.cnmTRichiestas1 = cnmTRichiestas1;
	}

	public CnmTRichiesta addCnmTRichiestas1(CnmTRichiesta cnmTRichiestas1) {
		getCnmTRichiestas1().add(cnmTRichiestas1);
		cnmTRichiestas1.setCnmTUser1(this);

		return cnmTRichiestas1;
	}

	public CnmTRichiesta removeCnmTRichiestas1(CnmTRichiesta cnmTRichiestas1) {
		getCnmTRichiestas1().remove(cnmTRichiestas1);
		cnmTRichiestas1.setCnmTUser1(null);

		return cnmTRichiestas1;
	}

	public List<CnmTRichiesta> getCnmTRichiestas2() {
		return this.cnmTRichiestas2;
	}

	public void setCnmTRichiestas2(List<CnmTRichiesta> cnmTRichiestas2) {
		this.cnmTRichiestas2 = cnmTRichiestas2;
	}

	public CnmTRichiesta addCnmTRichiestas2(CnmTRichiesta cnmTRichiestas2) {
		getCnmTRichiestas2().add(cnmTRichiestas2);
		cnmTRichiestas2.setCnmTUser2(this);

		return cnmTRichiestas2;
	}

	public CnmTRichiesta removeCnmTRichiestas2(CnmTRichiesta cnmTRichiestas2) {
		getCnmTRichiestas2().remove(cnmTRichiestas2);
		cnmTRichiestas2.setCnmTUser2(null);

		return cnmTRichiestas2;
	}


	public List<CnmTScrittoDifensivo> getCnmTScrittoDifensivos1() {
		return cnmTScrittoDifensivos1;
	}

	public void setCnmTScrittoDifensivos1(List<CnmTScrittoDifensivo> cnmTScrittoDifensivos1) {
		this.cnmTScrittoDifensivos1 = cnmTScrittoDifensivos1;
	}

	public List<CnmTScrittoDifensivo> getCnmTScrittoDifensivos2() {
		return cnmTScrittoDifensivos2;
	}

	public void setCnmTScrittoDifensivos2(List<CnmTScrittoDifensivo> cnmTScrittoDifensivos2) {
		this.cnmTScrittoDifensivos2 = cnmTScrittoDifensivos2;
	}

//	public List<CnmRScrittoIllecito> getCnmRScrittoIllecitos1() {
//		return cnmRScrittoIllecitos1;
//	}
//
//	public void setCnmRScrittoIllecitos1(List<CnmRScrittoIllecito> cnmRScrittoIllecitos1) {
//		this.cnmRScrittoIllecitos1 = cnmRScrittoIllecitos1;
//	}
//
//	public List<CnmRScrittoIllecito> getCnmRScrittoIllecitos2() {
//		return cnmRScrittoIllecitos2;
//	}
//
//	public void setCnmRScrittoIllecitos2(List<CnmRScrittoIllecito> cnmRScrittoIllecitos2) {
//		this.cnmRScrittoIllecitos2 = cnmRScrittoIllecitos2;
//	}
	
	
}
