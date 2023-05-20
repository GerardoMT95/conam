/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the cnm_t_verbale database table.
 * 
 */
@Entity
@Table(name="cnm_t_verbale")
@NamedQuery(name="CnmTVerbale.findAll", query="SELECT c FROM CnmTVerbale c")
public class CnmTVerbale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_verbale")
	private Integer idVerbale;

	private Boolean contestato;

	@Column(name="data_ora_accertamento")
	private Timestamp dataOraAccertamento;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_protocollo")
	private Timestamp dataOraProtocollo;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

//	@Column(name="data_ora_violazione")
	@Column(name="data_ora_processo_verbale")
	private Timestamp dataOraViolazione;

	@Column(name="importo_verbale")
	private BigDecimal importoVerbale;

	@Column(name="localita_violazione")
	private String localitaViolazione;

	@Column(name="num_verbale")
	private String numVerbale;

	@Column(name="numero_protocollo")
	private String numeroProtocollo;

	//bi-directional many-to-one association to CnmRAllegatoVerbale
	@OneToMany(mappedBy="cnmTVerbale")
	private List<CnmRAllegatoVerbale> cnmRAllegatoVerbales;

	//bi-directional many-to-one association to CnmRFunzionarioIstruttore
	@OneToMany(mappedBy="cnmTVerbale")
	private List<CnmRFunzionarioIstruttore> cnmRFunzionarioIstruttores;

	//bi-directional many-to-one association to CnmRVerbaleIllecito
	@OneToMany(mappedBy="cnmTVerbale")
	private List<CnmRVerbaleIllecito> cnmRVerbaleIllecitos;

	//bi-directional many-to-one association to CnmRVerbaleSoggetto
	@OneToMany(mappedBy="cnmTVerbale")
	private List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettos;

	//bi-directional many-to-one association to CnmSStatoVerbale
	@OneToMany(mappedBy="cnmTVerbale")
	private List<CnmSStatoVerbale> cnmSStatoVerbales;

	//bi-directional many-to-one association to CnmDComune
	@ManyToOne
	@JoinColumn(name="id_comune_violazione")
	private CnmDComune cnmDComune;

	//bi-directional many-to-one association to CnmDEnte
	@ManyToOne
	@JoinColumn(name="id_ente_accertatore")
	private CnmDEnte cnmDEnte;

	//bi-directional many-to-one association to CnmDStatoVerbale
	@ManyToOne
	@JoinColumn(name="id_stato_verbale")
	private CnmDStatoVerbale cnmDStatoVerbale;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

	//bi-directional many-to-one association to CnmDStatoPregresso
	@ManyToOne
	@JoinColumn(name="id_stato_pregresso")
	private CnmDStatoPregresso cnmDStatoPregresso;


	//bi-directional many-to-one association to CnmDStatoManuale
	@ManyToOne
	@JoinColumn(name="id_stato_manuale")
	private CnmDStatoManuale cnmDStatoManuale;
	
	@ManyToOne
	@JoinColumn(name="id_comune_ente")
	private CnmDComune cnmDComuneEnte;
	
	public CnmTVerbale() {
	}

	public Integer getIdVerbale() {
		return this.idVerbale;
	}

	public void setIdVerbale(Integer idVerbale) {
		this.idVerbale = idVerbale;
	}

	public Boolean getContestato() {
		return this.contestato;
	}

	public void setContestato(Boolean contestato) {
		this.contestato = contestato;
	}

	public Timestamp getDataOraAccertamento() {
		return this.dataOraAccertamento;
	}

	public void setDataOraAccertamento(Timestamp dataOraAccertamento) {
		this.dataOraAccertamento = dataOraAccertamento;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public Timestamp getDataOraProtocollo() {
		return this.dataOraProtocollo;
	}

	public void setDataOraProtocollo(Timestamp dataOraProtocollo) {
		this.dataOraProtocollo = dataOraProtocollo;
	}

	public Timestamp getDataOraUpdate() {
		return this.dataOraUpdate;
	}

	public void setDataOraUpdate(Timestamp dataOraUpdate) {
		this.dataOraUpdate = dataOraUpdate;
	}

	public Timestamp getDataOraViolazione() {
		return this.dataOraViolazione;
	}

	public void setDataOraViolazione(Timestamp dataOraViolazione) {
		this.dataOraViolazione = dataOraViolazione;
	}

	public BigDecimal getImportoVerbale() {
		return this.importoVerbale;
	}

	public void setImportoVerbale(BigDecimal importoVerbale) {
		this.importoVerbale = importoVerbale;
	}

	public String getLocalitaViolazione() {
		return this.localitaViolazione;
	}

	public void setLocalitaViolazione(String localitaViolazione) {
		this.localitaViolazione = localitaViolazione;
	}

	public String getNumVerbale() {
		return this.numVerbale;
	}

	public void setNumVerbale(String numVerbale) {
		this.numVerbale = numVerbale;
	}

	public String getNumeroProtocollo() {
		return this.numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public List<CnmRAllegatoVerbale> getCnmRAllegatoVerbales() {
		return this.cnmRAllegatoVerbales;
	}

	public void setCnmRAllegatoVerbales(List<CnmRAllegatoVerbale> cnmRAllegatoVerbales) {
		this.cnmRAllegatoVerbales = cnmRAllegatoVerbales;
	}

	public CnmRAllegatoVerbale addCnmRAllegatoVerbale(CnmRAllegatoVerbale cnmRAllegatoVerbale) {
		getCnmRAllegatoVerbales().add(cnmRAllegatoVerbale);
		cnmRAllegatoVerbale.setCnmTVerbale(this);

		return cnmRAllegatoVerbale;
	}

	public CnmRAllegatoVerbale removeCnmRAllegatoVerbale(CnmRAllegatoVerbale cnmRAllegatoVerbale) {
		getCnmRAllegatoVerbales().remove(cnmRAllegatoVerbale);
		cnmRAllegatoVerbale.setCnmTVerbale(null);

		return cnmRAllegatoVerbale;
	}

	public List<CnmRFunzionarioIstruttore> getCnmRFunzionarioIstruttores() {
		return this.cnmRFunzionarioIstruttores;
	}

	public void setCnmRFunzionarioIstruttores(List<CnmRFunzionarioIstruttore> cnmRFunzionarioIstruttores) {
		this.cnmRFunzionarioIstruttores = cnmRFunzionarioIstruttores;
	}

	public CnmRFunzionarioIstruttore addCnmRFunzionarioIstruttore(CnmRFunzionarioIstruttore cnmRFunzionarioIstruttore) {
		getCnmRFunzionarioIstruttores().add(cnmRFunzionarioIstruttore);
		cnmRFunzionarioIstruttore.setCnmTVerbale(this);

		return cnmRFunzionarioIstruttore;
	}

	public CnmRFunzionarioIstruttore removeCnmRFunzionarioIstruttore(CnmRFunzionarioIstruttore cnmRFunzionarioIstruttore) {
		getCnmRFunzionarioIstruttores().remove(cnmRFunzionarioIstruttore);
		cnmRFunzionarioIstruttore.setCnmTVerbale(null);

		return cnmRFunzionarioIstruttore;
	}

	public List<CnmRVerbaleIllecito> getCnmRVerbaleIllecitos() {
		return this.cnmRVerbaleIllecitos;
	}

	public void setCnmRVerbaleIllecitos(List<CnmRVerbaleIllecito> cnmRVerbaleIllecitos) {
		this.cnmRVerbaleIllecitos = cnmRVerbaleIllecitos;
	}

	public CnmRVerbaleIllecito addCnmRVerbaleIllecito(CnmRVerbaleIllecito cnmRVerbaleIllecito) {
		getCnmRVerbaleIllecitos().add(cnmRVerbaleIllecito);
		cnmRVerbaleIllecito.setCnmTVerbale(this);

		return cnmRVerbaleIllecito;
	}

	public CnmRVerbaleIllecito removeCnmRVerbaleIllecito(CnmRVerbaleIllecito cnmRVerbaleIllecito) {
		getCnmRVerbaleIllecitos().remove(cnmRVerbaleIllecito);
		cnmRVerbaleIllecito.setCnmTVerbale(null);

		return cnmRVerbaleIllecito;
	}

	public List<CnmRVerbaleSoggetto> getCnmRVerbaleSoggettos() {
		return this.cnmRVerbaleSoggettos;
	}

	public void setCnmRVerbaleSoggettos(List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettos) {
		this.cnmRVerbaleSoggettos = cnmRVerbaleSoggettos;
	}

	public CnmRVerbaleSoggetto addCnmRVerbaleSoggetto(CnmRVerbaleSoggetto cnmRVerbaleSoggetto) {
		getCnmRVerbaleSoggettos().add(cnmRVerbaleSoggetto);
		cnmRVerbaleSoggetto.setCnmTVerbale(this);

		return cnmRVerbaleSoggetto;
	}

	public CnmRVerbaleSoggetto removeCnmRVerbaleSoggetto(CnmRVerbaleSoggetto cnmRVerbaleSoggetto) {
		getCnmRVerbaleSoggettos().remove(cnmRVerbaleSoggetto);
		cnmRVerbaleSoggetto.setCnmTVerbale(null);

		return cnmRVerbaleSoggetto;
	}

	public List<CnmSStatoVerbale> getCnmSStatoVerbales() {
		return this.cnmSStatoVerbales;
	}

	public void setCnmSStatoVerbales(List<CnmSStatoVerbale> cnmSStatoVerbales) {
		this.cnmSStatoVerbales = cnmSStatoVerbales;
	}

	public CnmSStatoVerbale addCnmSStatoVerbale(CnmSStatoVerbale cnmSStatoVerbale) {
		getCnmSStatoVerbales().add(cnmSStatoVerbale);
		cnmSStatoVerbale.setCnmTVerbale(this);

		return cnmSStatoVerbale;
	}

	public CnmSStatoVerbale removeCnmSStatoVerbale(CnmSStatoVerbale cnmSStatoVerbale) {
		getCnmSStatoVerbales().remove(cnmSStatoVerbale);
		cnmSStatoVerbale.setCnmTVerbale(null);

		return cnmSStatoVerbale;
	}

	public CnmDComune getCnmDComune() {
		return this.cnmDComune;
	}

	public void setCnmDComune(CnmDComune cnmDComune) {
		this.cnmDComune = cnmDComune;
	}

	public CnmDEnte getCnmDEnte() {
		return this.cnmDEnte;
	}

	public void setCnmDEnte(CnmDEnte cnmDEnte) {
		this.cnmDEnte = cnmDEnte;
	}

	public CnmDStatoVerbale getCnmDStatoVerbale() {
		return this.cnmDStatoVerbale;
	}

	public void setCnmDStatoVerbale(CnmDStatoVerbale cnmDStatoVerbale) {
		this.cnmDStatoVerbale = cnmDStatoVerbale;
	}

	public CnmTUser getCnmTUser1() {
		return this.cnmTUser1;
	}

	public void setCnmTUser1(CnmTUser cnmTUser1) {
		this.cnmTUser1 = cnmTUser1;
	}

	public CnmTUser getCnmTUser2() {
		return this.cnmTUser2;
	}

	public void setCnmTUser2(CnmTUser cnmTUser2) {
		this.cnmTUser2 = cnmTUser2;
	}

	public CnmDStatoPregresso getCnmDStatoPregresso() {
		return cnmDStatoPregresso;
	}

	public void setCnmDStatoPregresso(CnmDStatoPregresso cnmDStatoPregresso) {
		this.cnmDStatoPregresso = cnmDStatoPregresso;
	}

	public CnmDStatoManuale getCnmDStatoManuale() {
		return cnmDStatoManuale;
	}

	public void setCnmDStatoManuale(CnmDStatoManuale statoManuale) {
		this.cnmDStatoManuale = statoManuale;
	}

	public CnmDComune getCnmDComuneEnte() {
		return cnmDComuneEnte;
	}

	public void setCnmDComuneEnte(CnmDComune cnmDComuneEnte) {
		this.cnmDComuneEnte = cnmDComuneEnte;
	}

}
